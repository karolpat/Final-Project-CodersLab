package pl.coderslab.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.joda.time.LocalDate;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User sendTo;
	
	@OneToOne
	private User sendFrom;
	
	private LocalDate created;
	
	private String content;

	//----------------------------
	
	
	
	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getAuthor() {
		return sendTo;
	}

	public void setAuthor(User author) {
		this.sendTo = author;
	}

	public User getReceiver() {
		return sendFrom;
	}

	public void setReceiver(User receiver) {
		this.sendFrom = receiver;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}
	
	
	
}