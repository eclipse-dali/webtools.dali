/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jpt.db.internal.ConnectionProfile;

/**
 * 
 */
public interface IJpaDataSource extends IJpaNode {

	/**
	 * Return the data source's connection profile name.
	 * The connection profile is looked up based on this setting.
	 */
	String connectionProfileName();
	
	/**
	 * Set the data source's connection profile name.
	 * The connection profile is looked up based on this setting.
	 */
	void setConnectionProfileName(String connectionProfileName);
	
	/**
	 * ID string used when connectionProfileName property is changed
	 * @see org.eclipse.jpt.utility.internal.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener)
	 */
	public static final String CONNECTION_PROFILE_NAME_PROPERTY = "connectionProfileName";
	
	/**
	 * The data source's connection profile should never be null.
	 * If we do not have a connection, return a "null" connection profile.
	 */
	ConnectionProfile connectionProfile();
	
	/**
	 * ID string used when connectionProfile property is changed
	 * @see org.eclipse.jpt.utility.internal.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener)
	 */
	public static final String CONNECTION_PROFILE_PROPERTY = "connectionProfile";
	
	boolean isConnected();
	
	boolean hasAConnection();
	
	void dispose();

}
