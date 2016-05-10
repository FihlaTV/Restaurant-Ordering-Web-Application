package TheApp275Final.term.dao;

import java.util.List;

import TheApp275Final.term.model.Item;

public interface ItemDao {
	public List<Item> getAllItems();
	public void deleteItem(Item item);
	public void addItem(Item item);
	public byte[] getImage(int id);
	List<Item> getActiveItems();
}
