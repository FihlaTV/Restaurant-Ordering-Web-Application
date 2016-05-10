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
		List<Item> result = sessionFactory.getCurrentSession().createCriteria(Item.class)
				.add(Restrictions.eq("status", true)).addOrder(Order.asc("category")).list();
		return result;
	}

	@Override
	public void deleteItem(Item item) {
		sessionFactory.getCurrentSession().saveOrUpdate(item);
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

}
