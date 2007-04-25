/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.core.internal.JpaCoreMessages;
import org.eclipse.jpt.core.internal.JpaCorePlugin;
import org.eclipse.jpt.core.internal.platform.generic.GenericPlatform;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;

public class JpaFacetDataModelProvider
	extends FacetInstallDataModelProvider
	implements IJpaFacetDataModelProperties
{
	public JpaFacetDataModelProvider() {
		super();
	}
	
	
	@Override
	public Set getPropertyNames() {
		Set propertyNames = super.getPropertyNames();
		propertyNames.add(PLATFORM_ID);
		propertyNames.add(CONNECTION);
		propertyNames.add(JPA_LIBRARY);
		propertyNames.add(CREATE_ORM_XML);
		return propertyNames;
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (FACET_ID.equals(propertyName)) {
			return JpaCorePlugin.FACET_ID;
		}
		else if (PLATFORM_ID.equals(propertyName)) {
			return GenericPlatform.ID;
		}
		else if (CONNECTION.equals(propertyName)) {
			return "";
		}
		else if (JPA_LIBRARY.equals(propertyName)) {
			return "";
		}
		else if (CREATE_ORM_XML.equals(propertyName)) {
			return Boolean.TRUE;
		}
		else {
			return super.getDefaultProperty(propertyName);
		}
	}
	
	@Override
	public IStatus validate(String name) {
		if (PLATFORM_ID.equals(name)) {
			return validatePlatform(getStringProperty(name));
		}
		else if (CONNECTION.equals(name)) {
			return validateConnection(getStringProperty(name));
		}
		else if (JPA_LIBRARY.equals(name)) {
			return validateJpaLibrary(getStringProperty(name));
		}
		else {
			return super.validate(name);
		}
	}
	
	private IStatus validatePlatform(String platformId) {
		if (platformId == null || platformId.equals("")) {
			return new Status(IStatus.ERROR, JpaCorePlugin.PLUGIN_ID, JpaCoreMessages.VALIDATE_PLATFORM_NOT_SPECIFIED);
		}
		else {
			return OK_STATUS;
		}
	}
	
	private IStatus validateConnection(String connectionName) {
		if (connectionName == null || connectionName.equals("") || ! ConnectionProfileRepository.instance().getConnectionWithProfileNamed(connectionName).isConnected()) {
			return new Status(IStatus.INFO, JpaCorePlugin.PLUGIN_ID, JpaCoreMessages.VALIDATE_CONNECTION_NOT_CONNECTED);
		}
		else {
			return OK_STATUS;
		}
	}
	
	private IStatus validateJpaLibrary(String library) {
		// TODO
		return OK_STATUS;
	}
}
