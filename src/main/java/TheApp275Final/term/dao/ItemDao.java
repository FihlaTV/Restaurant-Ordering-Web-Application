package TheApp275Final.term.dao;

import java.util.List;

import TheApp275Final.term.model.Item;
import TheApp275Final.term.model.ItemRating;
import TheApp275Final.term.model.OrderItems;

public interface ItemDao {
	public List<Item> getAllItems();
	public void deleteItem(Item item);
	public void addItem(Item item);
	public byte[] getImage(int id);
	List<Item> getActiveItems();
	public List<OrderItems> getRatingDetails(long l);
	public Item getItemByName(String name);
	public void setRatings(List<ItemRating> items);
}
