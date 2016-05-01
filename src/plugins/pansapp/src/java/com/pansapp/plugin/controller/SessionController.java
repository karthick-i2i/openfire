package com.pansapp.plugin.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.jivesoftware.openfire.SessionManager;
import com.pansapp.plugin.exceptions.ExceptionType;
import com.pansapp.plugin.exceptions.ServiceException;
import org.jivesoftware.openfire.session.ClientSession;
import org.jivesoftware.openfire.session.LocalClientSession;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Presence;
import org.xmpp.packet.StreamError;

/**
 * The Class SessionController.
 */
public class SessionController {
	/** The Constant INSTANCE. */
	public static final SessionController INSTANCE = new SessionController();

	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(SessionController.class);

	/**
	 * Gets the single instance of SessionController.
	 *
	 * @return single instance of SessionController
	 */
	public static SessionController getInstance() {
		return INSTANCE;
	}
	
}
