/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;

/**
 * Straightforward implementation of the JPA project config.
 */
public class SimpleJpaProjectConfig
	implements JpaProject2_0.Config
{
	protected JpaProject.Manager jpaProjectManager;
	protected IProject project;
	protected JpaPlatform jpaPlatform;
	protected String connectionProfileName;
	protected String userOverrideDefaultCatalog;
	protected String userOverrideDefaultSchema;
	protected boolean discoverAnnotatedClasses;
	protected String metamodelSourceFolderName;


	public SimpleJpaProjectConfig() {
		super();
	}

	public JpaProject.Manager getJpaProjectManager() {
		return this.jpaProjectManager;
	}

	public void setJpaProjectManager(JpaProject.Manager jpaProjectManager) {
		this.jpaProjectManager = jpaProjectManager;
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
	
	public String getUserOverrideDefaultSchema() {
		return this.userOverrideDefaultSchema;
	}
	
	public void setUserOverrideDefaultSchema(String userOverrideDefaultSchema) {
		this.userOverrideDefaultSchema = userOverrideDefaultSchema;
	}
	
	public String getUserOverrideDefaultCatalog() {
		return this.userOverrideDefaultCatalog;
	}
	
	public void setUserOverrideDefaultCatalog(String userOverrideDefaultCatalog) {
		this.userOverrideDefaultCatalog = userOverrideDefaultCatalog;
	}
	
	public boolean discoverAnnotatedClasses() {
		return this.discoverAnnotatedClasses;
	}

	public void setDiscoverAnnotatedClasses(boolean discoverAnnotatedClasses) {
		this.discoverAnnotatedClasses = discoverAnnotatedClasses;
	}

	public String getMetamodelSourceFolderName() {
		return this.metamodelSourceFolderName;
	}

	public void setMetamodelSourceFolderName(String metamodelSourceFolderName) {
		this.metamodelSourceFolderName = metamodelSourceFolderName;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.project.getName());
	}
}
