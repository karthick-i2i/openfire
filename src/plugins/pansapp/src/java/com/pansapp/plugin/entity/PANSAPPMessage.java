package com.pansapp.plugin.entity;

import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class PANSAPPMessage.
 */
@XmlRootElement(name = "PANSAPPMessage")
public class PANSAPPMessage{
	private String username;
	private String messageId;	
	private Integer isSent;
	private String messageDeliveryStatus;

	public String getMessageDeliveryStatus() {
		return messageDeliveryStatus;
	}

	public void setMessageDeliveryStatus(String messageDeliveryStatus) {
		this.messageDeliveryStatus = messageDeliveryStatus;
	}

	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}	
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setIsSent(int isSent){
		this.isSent = isSent;
	}
	
	public int getIsSent(){
		return isSent;
	}
	
			
}
