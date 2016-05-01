package com.pansapp.plugin.service;

import javax.annotation.PostConstruct;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.GenericEntity;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import java.util.List;

import com.pansapp.plugin.BasicAuth;
import com.pansapp.plugin.controller.SessionController;
import com.pansapp.plugin.exceptions.ServiceException;

import com.pansapp.plugin.entity.PANSAPPMessage;
import com.pansapp.plugin.entity.PANSAPPMessageRequest;
import javax.ws.rs.core.Response;

@Path("pansapp/v1/chat")
public class SessionService {

	@SuppressWarnings("unused")
	private SessionController sessionController;

	@PostConstruct
	public void init() {
		sessionController = SessionController.getInstance();
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response sendMessage(PANSAPPMessageRequest request, @HeaderParam("authorization") String authorization)
			throws ServiceException {
		try {
			String[] usernameAndPassword = BasicAuth.decode(authorization);
			
			XMPPServer xmppServer = XMPPServer.getInstance();
			String serverName = xmppServer.getServerInfo().getXMPPDomain();			

			ConnectionConfiguration config = new ConnectionConfiguration(serverName, 5222);
			config.setSASLAuthenticationEnabled(false);
			config.setSecurityMode(SecurityMode.disabled);
			Connection connection = new XMPPConnection(config);
			connection.connect();
			connection.login(usernameAndPassword[0], usernameAndPassword[1]);
			ChatManager chatManager = connection.getChatManager();

			for (PANSAPPMessage pansappMessage : request.getUsernames()) {
				pansappMessage.setIsSent(0);
				
				Chat chat = chatManager.createChat(pansappMessage.getUsername(), new MessageListener() {
					@Override
					public void processMessage(Chat chatObject, Message message) {
						System.out.println("arg0" + chatObject.toString() + "arg1" + message.toXML());
					}
				});
				chat.sendMessage(request.getMessage());
				pansappMessage.setMessageId(pansappMessage.getMessageId());
			}
			
			connection.disconnect();
			GenericEntity<List<PANSAPPMessage>> list = new GenericEntity<List<PANSAPPMessage>>(request.getUsernames()) {};
			return Response.status(Response.Status.OK).entity(list).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failure" + e.getMessage()).build();
		}

	}

}
