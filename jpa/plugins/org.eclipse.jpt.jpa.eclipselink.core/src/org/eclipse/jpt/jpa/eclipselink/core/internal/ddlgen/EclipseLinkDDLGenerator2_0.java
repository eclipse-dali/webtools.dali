/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.ddlgen;

import java.util.Properties;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOutputMode;

/**
 *  EclipseLink2_0DLLGenerator launches the EclipseLink DDL generator in a separate VM.
 *  For this it will create a Java Configuration named "Dali EclipseLink Table Generation" 
 *  in the target workspace (note: the configuration will be overridden at each run).
 *  It will than launch the configuration created with the login information from 
 *  the current Dali project, and passes it to EclipseLink.
 *  
 *  Pre-req in PDE environment:
 *  	org.eclipse.jpt.jpa.eclipselink.core.ddlgen.jar in ECLIPSE_HOME/plugins
 */
public class EclipseLinkDDLGenerator2_0 extends EclipseLinkAbstractDDLGenerator
{
	static final String VALIDATION_MODE_PROPERTY = "javax.persistence.validation.mode"; 	  //$NON-NLS-1$

	// ********** constructors **********

	public EclipseLinkDDLGenerator2_0(String puName, JpaProject jpaProject, EclipseLinkOutputMode outputMode) {
		super(puName, jpaProject, outputMode);
	}

	// ********** EclipseLink properties **********
	
	@Override
	protected void buildAllProperties(Properties properties) {
		super.buildAllProperties(properties);

		this.putProperty(properties,
			VALIDATION_MODE_PROPERTY,
			NONE);
	}

	@Override
	protected void buildCustomizationProperties(Properties properties) {
		super.buildCustomizationProperties(properties);
		
		this.putProperty(properties,
			EclipseLinkCustomization.ECLIPSELINK_WEAVING,
			FALSE);
	}

	@Override
	protected void buildConnectionProperties(Properties properties) {
		super.buildConnectionProperties(properties);
		ConnectionProfile cp = this.getConnectionProfile();

		this.putProperty(properties, 
			Connection2_0.PERSISTENCE_JDBC_DRIVER,
			(cp == null) ? "" : cp.getDriverClassName()); //$NON-NLS-1$
		this.putProperty(properties,
			Connection2_0.PERSISTENCE_JDBC_URL,
			(cp == null) ? "" : cp.getURL()); //$NON-NLS-1$
		this.putProperty(properties,
			Connection2_0.PERSISTENCE_JDBC_USER,
			(cp == null) ? "" : cp.getUserName()); //$NON-NLS-1$
		this.putProperty(properties,
			Connection2_0.PERSISTENCE_JDBC_PASSWORD,
			(cp == null) ? "" : cp.getUserPassword()); //$NON-NLS-1$
	}
}
