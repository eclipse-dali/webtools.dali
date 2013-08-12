/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.projects;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.tests.internal.TestCommand;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

/**
 * This builds and holds a "general" Eclipse project.
 * Support for adding natures, folders, and files.
 */
public class ProjectTestHarness {
	private final IProject project;

	/** carriage return */
	public static final String CR = StringTools.CR;

	
	// ********** builders **********
	
	public static ProjectTestHarness buildPlatformProject(String baseProjectName, boolean autoBuild)
			throws CoreException {
		return new ProjectTestHarness(baseProjectName, autoBuild);
	}
	
	
	// ********** constructors/initialization **********
	
	public ProjectTestHarness(String projectName, boolean autoBuild) throws CoreException {
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
		this.createProject(p);
		p.open(null);
		return p;
	}

	private void createProject(IProject p) {
		TestTools.execute(new CreateProjectCommand(p), 4);
	}

	protected static class CreateProjectCommand
		implements TestCommand
	{
		private final IProject project;
		protected CreateProjectCommand(IProject project) {
			super();
			this.project = project;
		}
		public void execute() throws Exception {
			this.project.create(null);
		}
	}


	// ********** public methods **********

	public IProject getProject() {
		return this.project;
	}

	public void addProjectNature(String natureID) throws CoreException {
		IProjectDescription description = this.project.getDescription();
		description.setNatureIds(ArrayTools.add(description.getNatureIds(), natureID));
		this.project.setDescription(description, null);
	}

	public void removeProjectNature(String natureID) throws CoreException {
		IProjectDescription description = this.project.getDescription();
		description.setNatureIds(ArrayTools.removeAllOccurrences(description.getNatureIds(), natureID));
		this.project.setDescription(description, null);
	}
}
