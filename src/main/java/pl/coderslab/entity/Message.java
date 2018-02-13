package pl.coderslab.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.joda.time.LocalDate;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User sendTo;
	
	@OneToOne
	private User sendFrom;
	
	private LocalDate created;
	
	private String content;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Chat chat;
	
	//----------------------------

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getSendTo() {
		return sendTo;
	}

	public void setSendTo(User sendTo) {
		this.sendTo = sendTo;
	}

	public User getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(User sendFrom) {
		this.sendFrom = sendFrom;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	
	
}