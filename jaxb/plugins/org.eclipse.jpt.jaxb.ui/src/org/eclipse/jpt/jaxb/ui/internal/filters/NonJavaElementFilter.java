/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * Code originate from org.eclipse.jdt.internal.ui.filters
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;


/**
 * Filters out all non-Java elements, and elements named package-info
 */
public class NonJavaElementFilter extends ViewerFilter {

	static public String FILE_TO_EXCLUDE = "package-info.java";   //$NON-NLS-1$
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof IJavaElement) {
			return(FILE_TO_EXCLUDE.equals(((IJavaElement)element).getElementName())) ?
					false : true;
		}

		if (element instanceof IResource) {
			IProject project= ((IResource)element).getProject();
			return project == null || !project.isOpen();
		}

		// Exclude all IStorage elements which are neither Java elements nor resources
		if (element instanceof IStorage)
			return false;

		return true;
	}
}
