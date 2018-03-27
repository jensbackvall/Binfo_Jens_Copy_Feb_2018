package dk.binfo.models;

import java.util.Set;

import javax.persistence.*;

import org.springframework.data.annotation.Transient;
/**
 *  @author Patrick Klæbel
 *  @author Jens Bäckvall
 *  @author Steen Petersen
 *  @author Morten Olsen
 *
 */
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", unique = true, nullable = false)
	private int userId;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	@Transient
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "address")
    private String address;

	@Column(name = "post_code")
    private String postCode;

	@Column(name = "active")
	private boolean active;

	@Column(name = "connect_list")
	private boolean connectList;

	@Column(name = "internal_list")
	private boolean internalList;

	@Column(name = "family_list")
	private boolean familyList;

	@Column(name = "external_list")
	private boolean externalList;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@Column(name ="phone_number")
	private String phoneNumber;

	@Column(name = "my_apartment")
	private int myApartment;

    public int getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public int getMyApartment() {
		return myApartment;
	}

	public void setMyApartment(int myApartment) {
		this.myApartment = myApartment;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isConnectList() {
		return connectList;
	}

	public void setConnectList(boolean connectList) {
		this.connectList = connectList;
	}

	public boolean isInternalList() {
		return internalList;
	}

	public void setInternalList(boolean internalList) {
		this.internalList = internalList;
	}

	public boolean isFamilyList() {
		return familyList;
	}

	public void setFamilyList(boolean familyList) {
		this.familyList = familyList;
	}

	public boolean isExternalList() {
		return externalList;
	}

	public void setExternalList(boolean externalList) {
		this.externalList = externalList;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
