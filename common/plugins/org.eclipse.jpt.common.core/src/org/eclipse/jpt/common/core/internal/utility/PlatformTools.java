/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;

/**
 * A collection of utilities for dealing with the Eclipse platform API.
 */
public class PlatformTools {
	
	/**
	 * Return the {@link IContainer} with the workspace relative "full" path
	 */
	public static IContainer getContainer(IPath fullContainerPath) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// changed to handle non-workspace projects
		String projectName = fullContainerPath.segment(0).toString();
		IPath projectRelativePath = fullContainerPath.removeFirstSegments(1);
		IProject project = root.getProject(projectName);
		if (projectRelativePath.isEmpty()) {
			return project;
		}
		return project.getFolder(projectRelativePath);
	}
	
	/**
	 * Return the {@link IFile} with the workspace relative "full" path
	 */
	public static IFile getFile(IPath fullFilePath) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getFileForLocation(root.getLocation().append(fullFilePath));
	}
	
	/**
	 * Return the specified file's content type,
	 * using the Eclipse platform's content type manager.
	 */
	public static IContentType getContentType(IFile file) {
		String fileName = file.getName();
		InputStream fileContents = null;
		try {
			fileContents = file.getContents();
		} catch (CoreException ex) {
			// seems like we can ignore any exception that might occur here;
			// e.g. we get a FNFE if the workspace is out of synch with the O/S file system
			// JptCorePlugin.log(ex);

			// look for content type based on the file name only(?)
			return findContentTypeFor(fileName);
		}

		IContentType contentType = null;
		try {
			contentType = findContentTypeFor(fileContents, fileName);
		} catch (IOException ex) {
			JptCommonCorePlugin.log(ex);
		} finally {
			try {
				fileContents.close();
			} catch (IOException ex) {
				JptCommonCorePlugin.log(ex);
			}
		}
		return contentType;
	}

	private static IContentType findContentTypeFor(InputStream fileContents, String fileName) throws IOException {
		return getContentTypeManager().findContentTypeFor(fileContents, fileName);
	}

	private static IContentType findContentTypeFor(String fileName) {
		return getContentTypeManager().findContentTypeFor(fileName);
	}

	private static IContentTypeManager getContentTypeManager() {
		return Platform.getContentTypeManager();
	}

	private PlatformTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
