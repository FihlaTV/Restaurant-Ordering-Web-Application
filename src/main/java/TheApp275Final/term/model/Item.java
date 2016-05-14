package TheApp275Final.term.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="item")
public class Item implements Serializable {
	
	/*This entity saves data pertaining to any item and is mapped to the item table in the database*/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private long id;
	
	@Column(name="item_name")
	private String itemName;
	
    private String category;
    @Column(name="unit_price")
    private float unitPrice;
    private int calories;
    
    @Column(name="preparation_time")
    private int preparationTime;
    private boolean status;
    
    private byte[] picture;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "item",cascade={CascadeType.ALL})
    private List<ItemRating> ratings;
    
    public Item(){
    	
    }

    /*Getters and Setters of the profile variables*/
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the unitPrice
	 */
	public float getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the calories
	 */
	public int getCalories() {
		return calories;
	}

	/**
	 * @param calories the calories to set
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}

	/**
	 * @return the preparationTime
	 */
	public int getPreparationTime() {
		return preparationTime;
	}

	/**
	 * @param preparationTime the preparationTime to set
	 */
	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the picture
	 */
	public byte[] getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public List<ItemRating> getRatings() {
		return ratings;
	}

	public void setRatings(List<ItemRating> ratings) {
		this.ratings = ratings;
	}
	
}
