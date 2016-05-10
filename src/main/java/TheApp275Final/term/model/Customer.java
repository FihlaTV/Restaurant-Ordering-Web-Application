package TheApp275Final.term.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;
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
@Table(name="users")
public class Customer implements Serializable{
	
	private static final long serialVersionUID = 8532918155999949977L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="user_access_code")
	private String userAccessCode;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@OneToMany(mappedBy = "customer",cascade={CascadeType.ALL})
	private List<CustomerRole> customerRoles;
	
	public Customer(){}

	public Customer(String firstname, String lastname, String username, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
	}

	public Customer(String firstname, String lastname, String username, String password,String userAccessCode) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.userAccessCode =  userAccessCode;
	}
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserAccessCode() {
		return userAccessCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setUserAccessCode(String userAccessCode) {
		this.userAccessCode = userAccessCode;
	}
	
	public List<CustomerRole> getCustomerRoles() {
		return customerRoles;
	}
	
	public void setCustomerRoles(List<CustomerRole> customerRoles) {
		this.customerRoles = customerRoles;
	}

	@Override
	public String toString() {
		return "Customer [Id=" + Id + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
				+ ", password=" + password + ", userAccessCode=" + userAccessCode + ", enabled=" + enabled + "]";
	}
	
}
