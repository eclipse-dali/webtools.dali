/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.projects;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
		return new TestPlatformProject(uniqueProjectName(baseProjectName), autoBuild);
	}
	
	public static String uniqueProjectName(String baseProjectName) {
		return baseProjectName + String.valueOf(System.currentTimeMillis()).substring(7);
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

	/**
	 * Create a folder with the specified name directly under the project.
	 */
	public IFolder createFolder(String folderName) throws CoreException {
		return this.createFolder(this.project, new Path(folderName));
	}

	/**
	 * Create a folder in the specified container with the specified name.
	 */
	public IFolder createFolder(IContainer container, String folderName) throws CoreException {
		return this.createFolder(container, new Path(folderName));
	}
	
	/**
	 * Create a folder in the specified container with the specified path.
	 */
	public IFolder createFolder(IContainer container, IPath folderPath) throws CoreException {
		IFolder folder = container.getFolder(folderPath);
		if ( ! folder.exists()) {
			folder.create(false, true, null);		// false = "no force"; true = "local"
		}
		return folder;
	}
	
	/**
	 * Create a file with the specified name and content directly under the project.
	 */
	public IFile createFile(String fileName, String content) throws CoreException {
		return this.createFile(this.project, fileName, content);
	}
	
	/**
	 * Create a file in the specified container with the specified name and content.
	 */
	public IFile createFile(IContainer container, String fileName, String content) throws CoreException {
		return createFile(container, new Path(fileName), content);
	}

	/**
	 * Create a file in the project with the specified [relative] path
	 * and content.
	 */
	public IFile createFile(IPath filePath, String content) throws CoreException {
		return this.createFile(this.project, filePath, content);
	}
	
	/**
	 * Create a file in the specified container with the specified path and content.
	 */
	public IFile createFile(IContainer container, IPath filePath, String content) throws CoreException {
		return this.createFile(container, filePath, new ByteArrayInputStream(content.getBytes()));
	}
	
	/**
	 * Create a file in the specified container with the specified path and contents.
	 */
	public IFile createFile(IContainer container, IPath filePath, InputStream content) throws CoreException {
		int pathCount = filePath.segmentCount() - 1;
		for (int i = 0; i < pathCount; i++) {
			container = container.getFolder(new Path(filePath.segment(i)));
			if ( ! container.exists()) {
				((IFolder) container).create(true, true, null);		// true = "force"; true = "local"
			}
		}

		IFile file = container.getFile(new Path(filePath.lastSegment()));
		if (file.exists()) {
			file.delete(true, null);		// true = "force"
		}
		file.create(content, true, null);		// true = "force"
		return file;
	}

}
