/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

/**
 * {@link IPath} utility methods
 */
public class PathTools {

	// ********** paths **********

	/**
	 * Return the {@link IContainer} with the workspace-relative "full" path
	 */
	public static IContainer getContainer(IPath fullContainerPath) {
		// changed to handle non-workspace projects
		String projectName = fullContainerPath.segment(0).toString();
		IPath projectRelativePath = fullContainerPath.removeFirstSegments(1);
		IProject project = getWorkspaceRoot().getProject(projectName);
		return (projectRelativePath.isEmpty()) ? project : project.getFolder(projectRelativePath);
	}

	/**
	 * Return the {@link IFile} with the workspace relative "full" path
	 */
	public static IFile getFile(IPath fullFilePath) {
		// changed to handle non-workspace projects
		String projectName = fullFilePath.segment(0).toString();
		IPath projectRelativePath = fullFilePath.removeFirstSegments(1);
		IProject project = getWorkspaceRoot().getProject(projectName);
		return project.getFile(projectRelativePath);
	}

	private static IWorkspaceRoot getWorkspaceRoot() {
		return getWorkspace().getRoot();
	}

	static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}


	// ********** disabled constructor **********

	private PathTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
