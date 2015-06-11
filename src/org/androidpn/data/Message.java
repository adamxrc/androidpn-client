package org.androidpn.data;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1460420804543428202L;
	
	private String id;
	private String type;
	private String from;//本地用户
	private String to;//远端用户
	private String sentDate;//消息日期
	private String subject;//消息标题
	private String body;//消息内容
	private String thread;
	private boolean isComMeg = true;// 是否为收到的消息
	private boolean isSent = false;
	private boolean isRead = false;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSentDate() {
		return sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isComMeg() {
		return isComMeg;
	}

	public void setComMeg(boolean isComMsg) {
		this.isComMeg = isComMsg;
	}

	public boolean isSent() {
		return isSent;
	}

	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}
	
	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Message() {
	}

	public Message(String from, String to, String sentDate, String subject, 
			String body, boolean isComMsg, boolean isSent) {
		super();
		this.from = from;
		this.to = to;
		this.sentDate = sentDate;
		this.subject = subject;
		this.body = body;
		this.isComMeg = isComMsg;
		this.isSent = isSent;
	}

}
