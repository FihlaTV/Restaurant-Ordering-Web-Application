package TheApp275Final.term.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ITEM_RATING")
public class ItemRating implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private long id;
	
	@ManyToOne(cascade = { CascadeType.ALL})
	@JoinColumn(name = "ITEM_NAME",referencedColumnName="ITEM_NAME",insertable=true,updatable=true)
	private Item item;
	
	@Column(name="RATING")
	private int rating;
	
	@Column(name="CUSTOMER_ID")
	private int customerId;
	
	@Column(name="ORDER_ID")
	private int orderId;
	
	public ItemRating(Item item, int rating, int customerId, int orderId) {
		super();
		this.item = item;
		this.rating = rating;
		this.customerId = customerId;
		this.orderId = orderId;
	}
	
	public ItemRating(){
		
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		if(item != null){
			System.out.println("itemName = > " + item.getItemName());
		}
		return "ItemRating [id=" + id + ", rating=" + rating + ", customerId=" + customerId + ", orderId=" + orderId
				+ "]";
	}
	
	
	
}
