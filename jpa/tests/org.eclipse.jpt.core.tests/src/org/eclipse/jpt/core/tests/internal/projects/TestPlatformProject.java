/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.projects;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * This builds and holds a "general" Eclipse project.
 * Support for adding natures, folders, and files.
 */
public class TestPlatformProject {
	private final IProject project;

	/** carriage return */
	public static final String CR = System.getProperty("line.separator");

	
	// ********** builders **********
	
	public static TestPlatformProject buildPlatformProject(String baseProjectName, boolean autoBuild)
			throws CoreException {
		return new TestPlatformProject(baseProjectName, autoBuild);
	}
	
	
	// ********** constructors/initialization **********
	
	public TestPlatformProject(String projectName, boolean autoBuild) throws CoreException {
		super();
		this.setAutoBuild(autoBuild);  // workspace-wide setting
		this.project = this.buildPlatformProject(projectName);
	}

	private void setAutoBuild(boolean autoBuild) throws CoreException {
		IWorkspaceDescription description = ResourcesPlugin.getWorkspace().getDescription();
		description.setAutoBuilding(autoBuild);
		ResourcesPlugin.getWorkspace().setDescription(description);
	}

	private IProject buildPlatformProject(String projectName) throws CoreException {
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		p.create(null);
		p.open(null);
		return p;
	}


	// ********** public methods **********

	public IProject getProject() {
		return this.project;
	}

	public void addProjectNature(String natureID) throws CoreException {
		IProjectDescription description = this.project.getDescription();
		description.setNatureIds(CollectionTools.add(description.getNatureIds(), natureID));
		this.project.setDescription(description, null);
	}

	public void removeProjectNature(String natureID) throws CoreException {
		IProjectDescription description = this.project.getDescription();
		description.setNatureIds(CollectionTools.removeAllOccurrences(description.getNatureIds(), natureID));
		this.project.setDescription(description, null);
	}

}
