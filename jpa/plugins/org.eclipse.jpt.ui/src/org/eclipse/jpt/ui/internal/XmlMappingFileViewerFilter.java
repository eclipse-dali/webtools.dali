/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.ui.JptUiPlugin;

/**
 * This filter will deny showing any file that are not XML mapping files or folders
 * that don't contain any XML mapping files in its sub-hierarchy. An XML mapping
 * file is one that has a corresponding JpaFile in the project with a mapping file
 * content type.
 * @see JptCorePlugin.MAPPING_FILE_CONTENT_TYPE
 */
public class XmlMappingFileViewerFilter extends ViewerFilter {

	private final IJavaProject javaProject;

	private final JpaProject jpaProject;
	
	public XmlMappingFileViewerFilter(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
		this.javaProject = jpaProject.getJavaProject();
	}

	/**
	 * Determines whether the given file (an XML file) is a JPA mapping
	 * descriptor file. 
	 */
	private boolean isMappingFile(IFile file) {
		JpaFile jpaFile = this.jpaProject.getJpaFile(file);
		return jpaFile != null ? jpaFile.getContentType().isKindOf(JptCorePlugin.MAPPING_FILE_CONTENT_TYPE): false;
	}

	@Override
	public boolean select(Viewer viewer,
	                      Object parentElement,
	                      Object element) {

		if (element instanceof IFile) {
			return isMappingFile((IFile) element);
		}
		else if (element instanceof IFolder) {
			IFolder folder = (IFolder) element;

			try {
				for (IClasspathEntry entry : this.javaProject.getRawClasspath()) {
					if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
						if (entry.getPath().isPrefixOf(folder.getFullPath().makeRelative())) {
							for (IResource resource : folder.members()) {
								if (select(viewer, folder, resource)) {
									return true;
								}
							}
						}
					}
				}
			}
			catch (JavaModelException e) {
				JptUiPlugin.log(e.getStatus());
			}
			catch (CoreException e) {
				JptUiPlugin.log(e.getStatus());
			}
		}

		return false;
	}
}
