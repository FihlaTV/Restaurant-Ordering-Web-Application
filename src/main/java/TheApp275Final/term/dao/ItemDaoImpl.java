package TheApp275Final.term.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.model.Item;
import TheApp275Final.term.model.ItemRating;
import TheApp275Final.term.model.OrderItems;

@Repository
@Transactional
public class ItemDaoImpl implements ItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Item> getAllItems() {
		@SuppressWarnings("unchecked")
		List<Item> result = sessionFactory.getCurrentSession().createCriteria(Item.class)
				.addOrder(Order.asc("category")).list();
		return result;
	}

	@Override
	public List<Item> getActiveItems() {
		@SuppressWarnings("unchecked")
		List<Item> resultItems = sessionFactory.getCurrentSession().createCriteria(Item.class)
				.add(Restrictions.eq("status", true)).addOrder(Order.asc("category")).list();
		return resultItems;
	}

	@Override
	public void deleteItem(Item item) {
		String query = "update item set status = ? where id = ?";
		sessionFactory.getCurrentSession().createSQLQuery(query).setParameter(0, item.isStatus()).setParameter(1, item.getId()).executeUpdate();
	}

	@Override
	public void addItem(Item item) {
		sessionFactory.getCurrentSession().saveOrUpdate(item);
	}

	@Override
	public byte[] getImage(int id) {
		String query = "select * from item i where i.id=" + id;
		Item item = (Item) sessionFactory.getCurrentSession().createSQLQuery(query).addEntity(Item.class)
				.uniqueResult();
		return item.getPicture();
	}
	
	@Override
	public List<OrderItems> getRatingDetails(long l) {
		String query = "select oi.* from ORDERS_ITEMS oi " 
						+ " inner join ORDERS o "
						+ " on o.ORDER_ID = oi.ORDER_ID"
						+ " where o.ORDER_USER_ID = "+l+" " 
						+ " and o.STATUS = 'F' "
						+ " and o.order_id not in (select ir.ORDER_ID from ITEM_RATING ir where ir.CUSTOMER_ID = "+l+") ";
		@SuppressWarnings("unchecked")
		List<OrderItems> items = sessionFactory.getCurrentSession().createSQLQuery(query).addEntity(OrderItems.class).list();
		return items;
	}
	
	@Override
	public void setRatings(List<ItemRating> items){
		for(ItemRating item:items){
			sessionFactory.getCurrentSession().saveOrUpdate(item);
		}
	}
	
	@Override
	public Item getItemByName(String name) {
		String query = "select * from item where item_name = '" + name + "' ";
		return (Item)sessionFactory.getCurrentSession().createSQLQuery(query).addEntity(Item.class).uniqueResult();
	}

}
