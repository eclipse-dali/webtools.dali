/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jface;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;

/**
 * This filter will deny showing any file that are not XML mapping files or folders
 * that don't contain any XML mapping files in its sub-hierarchy. An XML mapping
 * file is one that has a corresponding JpaFile in the project with a mapping file
 * content type.
 * @see JptJpaCorePlugin.MAPPING_FILE_CONTENT_TYPE
 */
public class XmlMappingFileViewerFilter
		extends ViewerFilter {
	
	private final JpaProject jpaProject;
	private IContentType contentType;
	
	
	public XmlMappingFileViewerFilter(JpaProject jpaProject, IContentType contentType) {
		super();
		this.jpaProject = jpaProject;
		this.contentType = contentType;
	}
	
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		if (element instanceof IFile) {
			return isMappingFile((IFile) element);
		}
		else if (element instanceof IContainer) {
			IContainer container = (IContainer) element;
			IProject project = this.jpaProject.getProject();
			ProjectResourceLocator locator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
			if (locator.resourceLocationIsValid(container)) {
				try {
					for (IResource resource : container.members()) {
						if (select(viewer, container, resource)) {
							return true;
						}
					}
				}
				catch (CoreException ce) {
					// fall through
					JptJpaUiPlugin.log(ce);
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
		return jpaFile != null ? jpaFile.getContentType().isKindOf(contentType): false;
	}
}
