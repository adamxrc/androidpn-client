package org.androidpn.client;

import org.jivesoftware.smack.packet.IQ;

public class MessageIQ extends IQ {
	
	private String msgId;
	
	private String msgType;
	
	private String subject;
	
	private String body;
	
	private String from_user;
	
	private String to_user;
	
	private String sentDate;
	
	private String thread;
	
	public MessageIQ() {
		setType(IQ.Type.SET);
	}

	@Override
	public String getChildElementXML() {
		// TODO Auto-generated method stub
		StringBuilder buf = new StringBuilder();
        buf.append("<").append("query").append(" xmlns=\"").append(
                "androidpn:iq:message").append("\">");
        if (msgId != null) {
            buf.append("<msg_id>").append(msgId).append("</msg_id>");
        }
        if(msgType != null) {
        	buf.append("<msg_type>").append(msgType).append("</msg_type>");
        }
        if(from_user != null) {
        	buf.append("<from_user>").append(from_user).append("</from_user>");
        }
        if(to_user != null) {
        	buf.append("<to_user>").append(to_user).append("</to_user>");
        }
        if(subject != null) {
        	buf.append("<subject>").append(subject).append("</subject>");
        }
        if(body != null) {
        	buf.append("<body>").append(body).append("</body>");
        }
        if(sentDate != null) {
        	buf.append("<sent_date>").append(sentDate).append("</sent_date>");
        }
        if(thread != null) {
        	buf.append("<thread>").append(thread).append("</thread>");
        }
        buf.append("</query>");
        return buf.toString();
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
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

	public String getFromUser() {
		return from_user;
	}

	public void setFromUser(String from_user) {
		this.from_user = from_user;
	}

	public String getToUser() {
		return to_user;
	}

	public void setToUser(String to_user) {
		this.to_user = to_user;
	}

	public String getSentDate() {
		return sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

}
