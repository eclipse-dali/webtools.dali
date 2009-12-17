/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.ddlgen;

import java.util.Properties;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.ddlgen.EclipseLinkDDLGenerator;

/**
 *  EclipseLink2_0DDLGenerator
 */
public class EclipseLink2_0DDLGenerator extends EclipseLinkDDLGenerator
{
	static final String VALIDATION_MODE_PROPERTY = "javax.persistence.validation.mode"; 	  //$NON-NLS-1$
	
	// ********** constructors **********
	
	public static void generate(String puName, JpaProject project, String projectLocation, IProgressMonitor monitor) {
		if (puName == null || puName.length() == 0 || project == null) {
			throw new NullPointerException();
		}
		new EclipseLink2_0DDLGenerator(puName, project, projectLocation, monitor).generate();
	}

	private EclipseLink2_0DDLGenerator(String puName, JpaProject jpaProject, String projectLocation, IProgressMonitor monitor) {
		super(puName, jpaProject, projectLocation, monitor);
	}

	// ********** EclipseLink properties **********
	
	@Override
	protected void buildAllProperties(Properties properties, String projectLocation) {
		super.buildAllProperties(properties, projectLocation);

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
		ConnectionProfile cp = this.getConnectionProfile();

		this.putProperty(properties,  
			Connection.ECLIPSELINK_BIND_PARAMETERS,
			FALSE);
		this.putProperty(properties, 
			JpaConnection2_0.PERSISTENCE_JDBC_DRIVER,
			(cp == null) ? "" : cp.getDriverClassName());
		this.putProperty(properties,
			JpaConnection2_0.PERSISTENCE_JDBC_URL,
			(cp == null) ? "" : cp.getURL());
		this.putProperty(properties,
			JpaConnection2_0.PERSISTENCE_JDBC_USER,
			(cp == null) ? "" : cp.getUserName());
		this.putProperty(properties,
			JpaConnection2_0.PERSISTENCE_JDBC_PASSWORD,
			(cp == null) ? "" : cp.getUserPassword());
	}
}
