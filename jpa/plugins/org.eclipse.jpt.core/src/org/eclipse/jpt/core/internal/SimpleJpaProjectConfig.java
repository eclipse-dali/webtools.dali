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

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Straightforward implementation of the JPA project config.
 */
public class SimpleJpaProjectConfig implements JpaProject.Config {
	protected IProject project;
	protected JpaPlatform jpaPlatform;
	protected String connectionProfileName;
	protected String userOverrideDefaultSchemaName;
	protected boolean discoverAnnotatedClasses;

	public SimpleJpaProjectConfig() {
		super();
	}

	public IProject getProject() {
		return this.project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public JpaPlatform getJpaPlatform() {
		return this.jpaPlatform;
	}

	public void setJpaPlatform(JpaPlatform jpaPlatform) {
		this.jpaPlatform = jpaPlatform;
	}

	public String getConnectionProfileName() {
		return this.connectionProfileName;
	}

	public void setConnectionProfileName(String connectionProfileName) {
		this.connectionProfileName = connectionProfileName;
	}
	
	public String getUserOverrideDefaultSchemaName() {
		return this.userOverrideDefaultSchemaName;
	}
	
	public void setUserOverrideDefaultSchemaName(String userOverrideDefaultSchemaName) {
		this.userOverrideDefaultSchemaName = userOverrideDefaultSchemaName;
	}
	
	public boolean discoverAnnotatedClasses() {
		return this.discoverAnnotatedClasses;
	}

	public void setDiscoverAnnotatedClasses(boolean discoverAnnotatedClasses) {
		this.discoverAnnotatedClasses = discoverAnnotatedClasses;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.project.getName());
	}

}
