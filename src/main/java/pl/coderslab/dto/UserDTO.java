package pl.coderslab.dto;

import java.util.List;

import org.joda.time.LocalDate;

import pl.coderslab.entity.Image;
import pl.coderslab.entity.Role;
import pl.coderslab.entity.Room;

public class UserDTO {

	private Long id;

	private String username;

	private String firstName;
	private String lastName;
	private LocalDate created;
	private String gender;
	private String country;
	private String city;
	private String street;
	private int phoneNumber;

	private String password;

	private String email;

	private boolean enabled = false;

	private Role role;

	private double rate = 0;

	private List<Room> roomsAsHost;

	private List<Room> roomsAsOwner;

	private Image image;

	private boolean ownerReq = false;

	private boolean managerReq = false;

	private boolean enableReq = false;

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

	public List<Room> getRoomsAsOwner() {
		return roomsAsOwner;
	}

	public void setRoomsAsOwner(List<Room> roomsAsOwner) {
		this.roomsAsOwner = roomsAsOwner;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isOwnerReq() {
		return ownerReq;
	}

	public void setOwnerReq(boolean ownerReq) {
		this.ownerReq = ownerReq;
	}

	public boolean isManagerReq() {
		return managerReq;
	}

	public void setManagerReq(boolean managerReq) {
		this.managerReq = managerReq;
	}

	public boolean isEnableReq() {
		return enableReq;
	}

	public void setEnableReq(boolean enableReq) {
		this.enableReq = enableReq;
	}
	
		
}
