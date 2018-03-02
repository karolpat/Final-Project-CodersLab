package pl.coderslab.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	@NotBlank
	private String username;
	private String firstName;
	private String lastName;
	
	@JsonIgnore
	private LocalDate created;
	private String gender;
	private String country;
	private String city;
	private String street;
	private int phoneNumber;

	@NotBlank
	@Size(min = 7)
	private String password;

	@Column(nullable = false, unique = true)
	@NotBlank
	@Size(min = 4, max = 40)
	private String email;

	private boolean enabled = false;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_role")
	private Role role;

	@Column(columnDefinition = "Decimal(2,1) default 0.0")
	private double rate = 0;

	@ManyToMany(mappedBy = "host", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Room> roomsAsHost;

	@OneToMany(mappedBy = "owner")
	@JsonIgnore
	private List<Room> roomsAsOwner;

	@OneToOne
	@JsonIgnore
	private Image image;
	private boolean ownerReq = false;
	private boolean managerReq = false;
	private boolean enableReq = false;
	private boolean active = true;

	// ------------------------------------------------
	
	public User() {}
	
	public User(long id) {
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public boolean isEnableReq() {
		return enableReq;
	}

	public void setEnableReq(boolean enableReq) {
		this.enableReq = enableReq;
	}

	public boolean isManagerReq() {
		return managerReq;
	}

	public void setManagerReq(boolean managerReq) {
		this.managerReq = managerReq;
	}

	public boolean isOwnerReq() {
		return ownerReq;
	}

	public void setOwnerReq(boolean ownerReq) {
		this.ownerReq = ownerReq;
	}

	public List<Room> getRoomsAsOwner() {
		return roomsAsOwner;
	}

	public void setRoomsAsOwner(List<Room> roomsAsOwner) {
		this.roomsAsOwner = roomsAsOwner;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public List<Room> getRoomsAsHost() {
		return roomsAsHost;
	}

	public void setRoomsAsHost(List<Room> roomsAsHost) {
		this.roomsAsHost = roomsAsHost;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", created=" + created + ", gender=" + gender + ", country=" + country + ", city=" + city
				+ ", street=" + street + ", phoneNumber=" + phoneNumber + ", password=" + password + ", email=" + email
				+ ", enabled=" + enabled + ", role=" + role + ", rate=" + rate + ", roomsAsHost=" + roomsAsHost
				+ ", image=" + image + "]";
	}

}