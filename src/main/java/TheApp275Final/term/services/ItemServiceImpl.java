package TheApp275Final.term.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.dao.ItemDao;
import TheApp275Final.term.model.Item;

@Service("itemService")
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemDao itemDao;
	
	@Override
	@Transactional
	public List<Item> getAllItems() {
		return itemDao.getAllItems();
	}
	
	@Override
	@Transactional
	public List<Item> getActiveItems() {
		return itemDao.getActiveItems();
	}
	
	@Override
	@Transactional
	public void deleteItem(Item item) {
		itemDao.deleteItem(item);
	}
	
	@Override
	@Transactional
	public void addItem(Item item){
		itemDao.addItem(item);
	}
	
	@Override
	@Transactional
	public byte[] getImage(int id){
		return null;
	}

}
