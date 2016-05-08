package TheApp275Final.term.services;

import java.util.List;

import org.springframework.stereotype.Service;

import TheApp275Final.term.model.Item;

@Service
public interface ItemService {
	public List<Item> getAllItems();
	public void deleteItem(Item item);
	public void addItem(Item item);
	public byte[] getImage(int id);
}
