package pl.coderslab.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.joda.time.LocalDate;

@Entity
public class Advert {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private User owner;

	@OneToOne
	private Room room;
	
	
	//-----------------------------------------

	
	
	public long getId() {
		return id;
	}

//	public boolean isPromoted() {
//		return promoted;
//	}
//
//	public void setPromoted(boolean promoted) {
//		this.promoted = promoted;
//	}

	public void setId(long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Advert [id=" + id + ", owner=" + owner + ", room=" + room + "]";
	}

//	public User getHost() {
//		return host;
//	}
//
//	public void setHost(User host) {
//		this.host = host;
//	}
//
//	public boolean isAvailability() {
//		return availability;
//	}
//
//	public void setAvailability(boolean availability) {
//		this.availability = availability;
//	}


//	@Override
//	public String toString() {
//		return "Advert [id=" + id + ", owner=" + owner + ", host=" + host + ", availability=" + availability
//				+  "]";
//	}
	
	
}
