package TheApp275Final.term.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;
 
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
    @JoinColumn(name="PIPELINE_ID")
    private Pipeline pipeline;

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
     
 
    // Getter and Setter methods
}