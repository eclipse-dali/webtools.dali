/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.connection.Connection;

/**
 *  EclipseLinkDLLGenerator launches the EclipseLink DDL generator in a separate VM.
 *  For this it will create a Java Configuration named "Dali EclipseLink Table Generation" 
 *  in the target workspace (note: the configuration will be overridden at each run).
 *  It will than launch the configuration created with the login information from 
 *  the current Dali project, and passes it to EclipseLink.
 *  
 *  Pre-req in PDE environment:
 *  	org.eclipse.jpt.jpa.eclipselink.core.ddlgen.jar in ECLIPSE_HOME/plugins
 */
public class EclipseLinkDDLGenerator extends AbstractEclipseLinkDDLGenerator
{

	// ********** constructors **********
	
	public static void generate(String puName, JpaProject project, IProgressMonitor monitor) {
		if (puName == null || puName.length() == 0 || project == null) {
			throw new NullPointerException();
		}
		new EclipseLinkDDLGenerator(puName, project).generate(monitor);
	}

	private EclipseLinkDDLGenerator(String puName, JpaProject jpaProject) {
		super(puName, jpaProject);
	}

	@Override
	protected void buildConnectionProperties(Properties properties) {
		super.buildConnectionProperties(properties);
		ConnectionProfile cp = this.getConnectionProfile();

		this.putProperty(properties, 
			Connection.ECLIPSELINK_DRIVER,
			(cp == null) ? "" : cp.getDriverClassName()); //$NON-NLS-1$
		this.putProperty(properties,
			Connection.ECLIPSELINK_URL,
			(cp == null) ? "" : cp.getURL()); //$NON-NLS-1$
		this.putProperty(properties,
			Connection.ECLIPSELINK_USER,
			(cp == null) ? "" : cp.getUserName()); //$NON-NLS-1$
		this.putProperty(properties,
			Connection.ECLIPSELINK_PASSWORD,
			(cp == null) ? "" : cp.getUserPassword()); //$NON-NLS-1$
	}
}
