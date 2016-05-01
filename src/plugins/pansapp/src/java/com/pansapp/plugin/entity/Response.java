package com.pansapp.plugin.entity;


import java.util.List;
import java.util.ArrayList;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class PANSAPPMessage.
 */
@XmlRootElement(name = "Response")
public class Response{

	private boolean status;
	private String message;
	private PANSAPPMessage result;
	private List<PANSAPPMessage> resultList = new ArrayList<PANSAPPMessage>();
	
	public Response(boolean status, String message, PANSAPPMessage result, List<PANSAPPMessage> resultList){
		this.status = status;
		this.message = message;
		this.result = result;
		this.resultList = resultList;
	}
	
	
	public void setResultList(List<PANSAPPMessage> resultList){
		this.resultList = resultList;
	}
	
	public List<PANSAPPMessage> getResultList(){
		return resultList;
	}
	
	public void setResult(PANSAPPMessage result){
		this.result = result;
	}
	
	public PANSAPPMessage getResult(){
		return result;
	}
	
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setStatus(boolean status){
		this.status = status;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	
	
}
