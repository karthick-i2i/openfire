/**
 * $Revision: 1722 $
 * $Date: 2005-07-28 15:19:16 -0700 (Thu, 28 Jul 2005) $
 *
 * Copyright (C) 2005-2008 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pansapp.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import com.pansapp.plugin.entity.SystemProperties;
import com.pansapp.plugin.entity.SystemProperty;
import com.pansapp.plugin.exceptions.ExceptionType;
import com.pansapp.plugin.exceptions.ServiceException;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.PropertyEventDispatcher;
import org.jivesoftware.util.PropertyEventListener;
import org.jivesoftware.util.StringUtils;

import com.pansapp.plugin.service.JerseyWrapper;

/**
 * The Class PansappServicePlugin.
 */
public class PansappServicePlugin implements Plugin, PropertyEventListener {
	
	/** The Constant INSTANCE. */
	public static final PansappServicePlugin INSTANCE = new PansappServicePlugin();

	private static final String CUSTOM_AUTH_FILTER_PROPERTY_NAME = "plugin.pansapp.customAuthFilter";
		
	/** The http auth. */
	private String httpAuth;
	

	/**
	 * Gets the single instance of RESTServicePlugin.
	 *
	 * @return single instance of RESTServicePlugin
	 */
	public static PansappServicePlugin getInstance() {
		return INSTANCE;
	}

	public void initializePlugin(PluginManager manager, File pluginDirectory) {		
		// See if the HTTP Basic Auth is enabled or not.
		httpAuth = JiveGlobals.getProperty("plugin.pansapp.httpAuth", "basic");	
		// Listen to system property events
		PropertyEventDispatcher.addListener(this);
	}

	public void destroyPlugin() {
		// Stop listening to system property events
		PropertyEventDispatcher.removeListener(this);
	}

	/**
	 * Gets the system properties.
	 *
	 * @return the system properties
	 */
	public SystemProperties getSystemProperties() {
		SystemProperties systemProperties = new SystemProperties();
		List<SystemProperty> propertiesList = new ArrayList<SystemProperty>();
		
		for(String propertyKey : JiveGlobals.getPropertyNames()) {
			String propertyValue = JiveGlobals.getProperty(propertyKey);
			propertiesList.add(new SystemProperty(propertyKey, propertyValue));
		}
		systemProperties.setProperties(propertiesList);
		return systemProperties;

	}

	/**
	 * Gets the system property.
	 *
	 * @param propertyKey the property key
	 * @return the system property
	 * @throws ServiceException the service exception
	 */
	public SystemProperty getSystemProperty(String propertyKey) throws ServiceException {
		String propertyValue = JiveGlobals.getProperty(propertyKey);
		if(propertyValue != null) {
		return new SystemProperty(propertyKey, propertyValue);
		} else {
			throw new ServiceException("Could not find property", propertyKey, ExceptionType.PROPERTY_NOT_FOUND,
					Response.Status.NOT_FOUND);
		}
	}
	
	/**
	 * Creates the system property.
	 *
	 * @param systemProperty the system property
	 */
	public void createSystemProperty(SystemProperty systemProperty) {
		JiveGlobals.setProperty(systemProperty.getKey(), systemProperty.getValue());
	}
	
	/**
	 * Delete system property.
	 *
	 * @param propertyKey the property key
	 * @throws ServiceException the service exception
	 */
	public void deleteSystemProperty(String propertyKey) throws ServiceException {
		if(JiveGlobals.getProperty(propertyKey) != null) {
			JiveGlobals.deleteProperty(propertyKey);
		} else {
			throw new ServiceException("Could not find property", propertyKey, ExceptionType.PROPERTY_NOT_FOUND,
					Response.Status.NOT_FOUND);
		}
	}
	
	/**
	 * Update system property.
	 *
	 * @param propertyKey the property key
	 * @param systemProperty the system property
	 * @throws ServiceException the service exception
	 */
	public void updateSystemProperty(String propertyKey, SystemProperty systemProperty) throws ServiceException {
		if(JiveGlobals.getProperty(propertyKey) != null) {
			if(systemProperty.getKey().equals(propertyKey)) {
				JiveGlobals.setProperty(propertyKey, systemProperty.getValue());
			} else {
				throw new ServiceException("Path property name and entity property name doesn't match", propertyKey, ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION,
						Response.Status.BAD_REQUEST);
			}
		} else {
			throw new ServiceException("Could not find property for update", systemProperty.getKey(), ExceptionType.PROPERTY_NOT_FOUND,
					Response.Status.NOT_FOUND);
		}
	}

	
	/**
	 * Returns the loading status message.
	 *
	 * @return the loading status message.
	 */
	public String getLoadingStatusMessage() {
		return JerseyWrapper.getLoadingStatusMessage();
	}
	
	/**
	 * Reloads the Jersey wrapper.
	 */
	public String loadAuthenticationFilter(String customAuthFilterClassName) {
		return JerseyWrapper.tryLoadingAuthenticationFilter(customAuthFilterClassName);
	}
			

	/**
	 * Gets the http authentication mechanism.
	 *
	 * @return the http authentication mechanism
	 */
	public String getHttpAuth() {
		return httpAuth;
	}

	/**
	 * Sets the http auth.
	 *
	 * @param httpAuth the new http auth
	 */
	public void setHttpAuth(String httpAuth) {
		this.httpAuth = httpAuth;
		JiveGlobals.setProperty("plugin.restapi.httpAuth", httpAuth);
	}

	public void propertySet(String property, Map<String, Object> params) {
		if (property.equals("plugin.restapi.httpAuth")) {
			this.httpAuth = (String) params.get("value");
		}
	}

	public void propertyDeleted(String property, Map<String, Object> params) {
		if (property.equals("plugin.restapi.httpAuth")) {
			this.httpAuth = "basic";
		}
	}

	public void xmlPropertySet(String property, Map<String, Object> params) {
		// Do nothing
	}
	public void xmlPropertyDeleted(String property, Map<String, Object> params) {
		// Do nothing
	}
}
