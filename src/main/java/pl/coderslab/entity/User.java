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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDate;

@Entity(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String firstName;
	private String lastName;
	private LocalDate created;
	private String gender;	
	private String country;
	private String city;
	private String street;
	private String postalCode;
	private int phoneNumber;
	
	
	@NotBlank
	@Size(min=7)
	private String password;
	
	@Column(nullable=false, unique = true)
	@NotBlank
	@Size(min=4, max=40)
	private String email;
	
	private boolean enabled=true;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JoinColumn(name="user_role")
	private Role role;
	
	@Column(columnDefinition="Decimal(2,1) default 0.0")
	private double rate=0;
	
	@OneToMany
	@JoinTable(name="apartment_user", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="apartment_id"))
	private List<Appartment> appartments;
	
	@OneToMany
	@JoinTable(name="hotel_user", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="hotel_id"))
	private List<Hotel> hotels;
	
	@OneToMany
	@JoinTable(name="room_user", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="room_id"))
	private List<Room> rooms;
	
//	@OneToOne
//	@JoinColumn(name="localization_id")
//	private Localization localization;
		
	//-------------------------------------------------
	
	
	
	public double getRate() {
		return rate;
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
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
//	public Localization getLocalization() {
//		return localization;
//	}
//	public void setLocalization(Localization localization) {
//		this.localization = localization;
//	}
	public LocalDate getCreated() {
		return created;
	}
	public void setCreated(LocalDate format) {
		this.created = format;
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
	public void setRate(double rate) {
		this.rate = rate;
	}
	public List<Appartment> getAppartments() {
		return appartments;
	}
	public void setAppartments(List<Appartment> appartmentList) {
		this.appartments = appartmentList;
	}
	public List<Hotel> getHotels() {
		return hotels;
	}
	public void setHotels(List<Hotel> hotelList) {
		this.hotels = hotelList;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> roomList) {
		this.rooms = roomList;
	}
	public Long getId() {
		return id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role roles) {
		this.role = roles;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", created=" + created + ", gender=" + gender + ", country=" + country + ", city=" + city
				+ ", street=" + street + ", postalCode=" + postalCode + ", phoneNumber=" + phoneNumber + ", password="
				+ password + ", email=" + email + ", enabled=" + enabled + ", role=" + role + ", rate=" + rate
				+ ", appartments=" + appartments + ", hotels=" + hotels + ", rooms=" + rooms + "]";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}