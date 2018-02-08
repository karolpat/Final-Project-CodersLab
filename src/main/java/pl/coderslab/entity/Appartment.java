package pl.coderslab.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

//@Entity
public class Appartment {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private long id;
//
//	@NotBlank
//	@Size(min=5, max=25)
//	private String name;
//	
//	@OneToOne
//	@JoinColumn(name="localization_id")
//	private Localization localization;
//	
//	private double rate;
//	
////	@ManyToOne
////	@JsonBackReference
////	private User owner;
//	
//	
//	@OneToMany
//	private List<Room> rooms;
//	
//	//------------------------------------
//
//	
//	
//	public long getId() {
//		return id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public Localization getLocalization() {
//		return localization;
//	}
//
//	public void setLocalization(Localization localization) {
//		this.localization = localization;
//	}
//
//	public double getRate() {
//		return rate;
//	}
//
//	public void setRate(double rate) {
//		this.rate = rate;
//	}
//
//
//	public List<Room> getRooms() {
//		return rooms;
//	}
//
//	public void setRooms(List<Room> rooms) {
//		this.rooms = rooms;
//	}
//
//	public User getOwner() {
//		return owner;
//	}
//
//	public void setOwner(User owner) {
//		this.owner = owner;
//	}
//
//	
	
	
}
