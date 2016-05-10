package TheApp275Final.term.model;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ORDERS_ITEMS")
public class OrderItems {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ORDER_ITEM_ID")
    private Long orderItemId;
    
	@Column(name="item_name")
	private String itemName;
	
	@Column(name="category")
    private String category;
	
    @Column(name="unitPrice")
    private float unitPrice;
    
    @Column(name="calories")
    private int calories;
    
    @Column(name="preparationTime")
    private int preparationTime;
    
    @Column(name="quantity")
    private int quantity;
    
    @Column(name="status")
    private boolean status;
    
    @Column(name="picture")
    private byte[] picture;
    
	@ManyToOne(cascade = { CascadeType.ALL})
	@JoinColumn(name = "ORDER_ID",referencedColumnName="ORDER_ID",insertable=true,updatable=true)
    private Order order;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderItems [orderItemId=" + orderItemId + ", itemName=" + itemName + ", category=" + category
				+ ", unitPrice=" + unitPrice + ", calories=" + calories + ", preparationTime=" + preparationTime
				+ ", quantity=" + quantity + ", status=" + status + ", picture=" + Arrays.toString(picture) + ", order="
				+ order + "]";
	}
	
}
