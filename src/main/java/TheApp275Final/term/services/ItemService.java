package TheApp275Final.term.services;

import java.util.List;

import org.springframework.stereotype.Service;

import TheApp275Final.term.model.Item;
import TheApp275Final.term.model.ItemRating;

@Service
public interface ItemService {
	public List<Item> getAllItems();
	public void deleteItem(Item item);
	public void addItem(Item item);
	public byte[] getImage(int id);
	List<Item> getActiveItems();
	public Item getItemByName(String name);
	public void setRatings(List<ItemRating> items);
}
