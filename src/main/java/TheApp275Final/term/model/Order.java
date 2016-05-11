package TheApp275Final.term.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
 
@Entity
@Table(name="ORDERS")
public class Order {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ORDER_ID")
    private Long orderId;
     
    @Column(name="TOTAL_PROC_TIME")
    private int totalProcTime;
     
    @Column(name="PICKUP_TIME")
    private LocalDateTime pickUpTime;
     
    @Column(name="ORDER_START_TIME")
    private LocalDateTime orderStartTime;
     
    @Column(name="ORDER_END_TIME")
    private LocalDateTime orderEndTime;
 
    @Column(name="STATUS")
    private char status;
    
    @ManyToOne
    @JoinColumn(name="ORDER_USER_ID")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name="PIPELINE_ID")
    private Pipeline pipeline;
    
    @Column(name = "ORDER_PLACEMENT_TIME", insertable = false, updatable = false)
    private LocalDateTime orderPlacementTime; 
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order",cascade={CascadeType.ALL})
	private List<OrderItems> orderItems;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getTotalProcTime() {
		return totalProcTime;
	}

	public void setTotalProcTime(int totalProcTime) {
		this.totalProcTime = totalProcTime;
	}

	public LocalDateTime getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(LocalDateTime pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public LocalDateTime getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(LocalDateTime orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public LocalDateTime getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(LocalDateTime orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Pipeline getPipeline() {
		return pipeline;
	}

	public void setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	public List<OrderItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}
     
	public void setOrderPlacementTime(LocalDateTime orderPlacementTime){
		this.orderPlacementTime = orderPlacementTime;
	}
	
	public LocalDateTime getOrderPlacementTime(){
		return orderPlacementTime;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
 
    // Getter and Setter methods
}