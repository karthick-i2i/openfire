package com.pansapp.plugin.entity;


import java.util.List;
import java.util.ArrayList;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class PANSAPPMessageRequest.
 */
@XmlRootElement(name = "PANSAPPMessageRequest")
public class PANSAPPMessageRequest{
	private List<PANSAPPMessage> usernames = new ArrayList<PANSAPPMessage>();

	public List<PANSAPPMessage> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<PANSAPPMessage> usernames) {
		this.usernames = usernames;
	}
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
	
}
