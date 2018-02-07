package pl.coderslab.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Localization {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String country;
	
	private String city;
	
	private String street;
		
	private String postalCode;
	
	@OneToOne
	private Hotel hotel;
	
	@OneToOne
	private Appartment appartment;
	
	@OneToOne
	private User user;
	
	//-------------------------
	
	

	public long getId() {
		return id;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Appartment getAppartment() {
		return appartment;
	}

	public void setAppartment(Appartment appartment) {
		this.appartment = appartment;
	}

	public void setId(long id) {
		this.id = id;
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


	@Override
	public String toString() {
		return "Localization [id=" + id + ", country=" + country + ", city=" + city + ", street=" + street
				+  ", hotel=" + hotel + ", appartment=" + appartment + "]";
	}
	
	
}
