package org.androidpn.client;

import org.jivesoftware.smack.packet.IQ;

public class UserIQ extends IQ {
	
	private String id;
	
	private String userName;
	
	private String userStatus;
	
	public UserIQ() {
	}

	@Override
	public String getChildElementXML() {
		// TODO Auto-generated method stub
		StringBuilder buf = new StringBuilder();
        buf.append("<").append("user").append(" xmlns=\"").append(
                "androidpn:iq:user").append("\">");
        if (id != null) {
            buf.append("<id>").append(id).append("</id>");
        }
        buf.append("</").append("user").append("> ");
        return buf.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}
