package com.pansapp.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.admin.AdminManager;
import org.jivesoftware.openfire.auth.AuthFactory;
import org.jivesoftware.openfire.auth.ConnectionException;
import org.jivesoftware.openfire.auth.InternalUnauthenticatedException;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

/**
 * The Class AuthFilter.
 */
public class AuthFilter implements ContainerRequestFilter {

	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

	/** The http request. */
	@Context
	private HttpServletRequest httpRequest;

	/** The plugin. */
	private PansappServicePlugin plugin = (PansappServicePlugin) XMPPServer.getInstance().getPluginManager()
			.getPlugin("pansapp");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.jersey.spi.container.ContainerRequestFilter#filter(com.sun.jersey
	 * .spi.container.ContainerRequest)
	 */
	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {
		// Let the preflight request through the authentication
		if ("OPTIONS".equals(containerRequest.getMethod())) {
			return containerRequest;
		}
		
		// Get the authentification passed in HTTP headers parameters
		String auth = containerRequest.getHeaderValue("authorization");

		if (auth == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		// HTTP Basic Auth or Shared Secret key
		if ("basic".equals(plugin.getHttpAuth())) {
			String[] usernameAndPassword = BasicAuth.decode(auth);

			// If username or password fail
			if (usernameAndPassword == null || usernameAndPassword.length != 2) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}

			boolean userAdmin = AdminManager.getInstance().isUserAdmin(usernameAndPassword[0], true);

			/*if (!userAdmin) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}*/

			try {
				AuthFactory.authenticate(usernameAndPassword[0], usernameAndPassword[1]);
			} catch (UnauthorizedException e) {
				LOG.warn("Wrong HTTP Basic Auth authorization", e);
				throw new WebApplicationException(Status.UNAUTHORIZED);
			} catch (ConnectionException e) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			} catch (InternalUnauthenticatedException e) {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		}
		return containerRequest;
	}
}
