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

	@ManyToOne
	private User host;
	
	@OneToOne
	private Room room;
	
	private boolean availability;
	
	private LocalDate start;
	
	private LocalDate end;
	
	private boolean promoted;
	
	//-----------------------------------------

	
	
	public long getId() {
		return id;
	}

	public boolean isPromoted() {
		return promoted;
	}

	public void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Advert [id=" + id + ", owner=" + owner + ", host=" + host + ", availability=" + availability
				+ ", start=" + start + ", end=" + end + "]";
	}
	
	
}
