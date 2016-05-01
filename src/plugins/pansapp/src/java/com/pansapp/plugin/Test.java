package com.pansapp.plugin;

/*import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;*/

public class Test {

	public static void main(String[] args) {
		/*// TODO Auto-generated method stub
		System.out.println("HI");

		ConnectionConfiguration config = new ConnectionConfiguration("89.144.100.6", 5222);
		config.setSASLAuthenticationEnabled(false);
		config.setSecurityMode(SecurityMode.disabled);
		Connection connection = new XMPPConnection(config);
		try {
			connection.connect();
	        System.out.println("Connected: " + connection.isConnected());
			connection.login("server", "112233");
			System.out.println("isAuthenticated: " + connection.isAuthenticated());
			
			//Roster roster = connection.getRoster();			
				        //roster.createEntry("user", "user", null);
			
			ChatManager chatManager = connection.getChatManager();
			 Chat chat = chatManager.createChat("radi1", new MessageListener() {
				
				@Override
				public void processMessage(Chat chatObject, Message message) {
					// TODO Auto-generated method stub
					
					System.out.println("arg0"+chatObject.toString() + "arg1" + message.toXML());
					
				}
			});			 
			 chat.sendMessage("new");
			 try {
				Thread.sleep(15000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//connection.disconnect();
		}
*/
	}

}
