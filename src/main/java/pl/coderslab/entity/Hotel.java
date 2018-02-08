package pl.coderslab.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank
	@Size(min = 5, max = 25)
	private String name;

	private double rating;

	@OneToOne
	@JoinColumn(name = "localization_id")
	private Localization address;

	@ManyToOne
	@JsonBackReference
	private User hOwner;

	@OneToMany
	@MapKeyColumn(name = "id")
	private Map<Long, Room> rooms = new HashMap<Long, Room>();

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hotel")
	@MapKeyColumn(name = "id")
	private Map<Long, Comment> comments = new HashMap<Long, Comment>();

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hotel")
	@MapKeyColumn(name = "id")
	private Map<Long, Image> images = new HashMap<Long, Image>();
	
	
	//----------------------------------------

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Localization getAddress() {
		return address;
	}

	public void setAddress(Localization address) {
		this.address = address;
	}

	public User getOwner() {
		return hOwner;
	}

	public void setOwner(User owner) {
		this.hOwner = owner;
	}

	public Map<Long, Room> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Long, Room> rooms) {
		this.rooms = rooms;
	}

	public Map<Long, Comment> getComments() {
		return comments;
	}

	public void setComments(Map<Long, Comment> comments) {
		this.comments = comments;
	}

	public Map<Long, Image> getImages() {
		return images;
	}

	public void setImages(Map<Long, Image> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", rating=" + rating + ", address=" + address + ", owner=" + hOwner
				+ ", rooms=" + rooms + ", comments=" + comments + ", images=" + images + "]";
	}
	
	

	
}
