/*******************************************************************************
* Copyright (c) 2009, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.ddlgen;

import java.util.Properties;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.gen.JptGenerator;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.OutputMode;

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
public class EclipseLink2_0DDLGenerator extends AbstractEclipseLinkDDLGenerator
{
	static final String VALIDATION_MODE_PROPERTY = "javax.persistence.validation.mode"; 	  //$NON-NLS-1$

	// ********** constructors **********

	public EclipseLink2_0DDLGenerator(String puName, JpaProject jpaProject, OutputMode outputMode) {
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
			Customization.ECLIPSELINK_WEAVING,
			FALSE);
	}

	@Override
	protected void buildConnectionProperties(Properties properties) {
		super.buildConnectionProperties(properties);
		ConnectionProfile cp = this.getConnectionProfile();

		this.putProperty(properties, 
			JpaConnection2_0.PERSISTENCE_JDBC_DRIVER,
			(cp == null) ? "" : cp.getDriverClassName()); //$NON-NLS-1$
		this.putProperty(properties,
			JpaConnection2_0.PERSISTENCE_JDBC_URL,
			(cp == null) ? "" : cp.getURL()); //$NON-NLS-1$
		this.putProperty(properties,
			JpaConnection2_0.PERSISTENCE_JDBC_USER,
			(cp == null) ? "" : cp.getUserName()); //$NON-NLS-1$
		this.putProperty(properties,
			JpaConnection2_0.PERSISTENCE_JDBC_PASSWORD,
			(cp == null) ? "" : cp.getUserPassword()); //$NON-NLS-1$
	}
}
