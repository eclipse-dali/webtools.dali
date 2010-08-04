/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
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
public class XmlMappingFileViewerFilter
		extends ViewerFilter {
	
	private final JpaProject jpaProject;
	
	
	public XmlMappingFileViewerFilter(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
	}
	
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		if (element instanceof IFile) {
			return isMappingFile((IFile) element);
		}
		else if (element instanceof IContainer) {
			IContainer container = (IContainer) element;
			IProject project = this.jpaProject.getProject();
			if (JptCorePlugin.getResourceLocator(project).acceptResourceLocation(project, container)) {
				try {
					for (IResource resource : container.members()) {
						if (select(viewer, container, resource)) {
							return true;
						}
					}
				}
				catch (CoreException ce) {
					// fall through
					JptUiPlugin.log(ce);
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines whether the given file (an XML file) is a JPA mapping
	 * descriptor file. 
	 */
	private boolean isMappingFile(IFile file) {
		JpaFile jpaFile = this.jpaProject.getJpaFile(file);
		return jpaFile != null ? jpaFile.getContentType().isKindOf(JptCorePlugin.MAPPING_FILE_CONTENT_TYPE): false;
	}
}
