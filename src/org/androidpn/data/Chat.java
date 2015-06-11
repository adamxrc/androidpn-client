package org.androidpn.data;

import java.io.Serializable;
import java.util.*;

public class Chat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2395772934270226716L;
	
	private String type;
	private String local;
	private String remote;
	private String paticipants;
	private List<Message> messages;
	
	public Chat() {
		messages = new ArrayList<Message>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getRemote() {
		return remote;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public String getPaticipants() {
		return paticipants;
	}

	public void setPaticipants(String paticipants) {
		this.paticipants = paticipants;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
