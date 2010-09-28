/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.filters;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filters out all containers i.e. all packages and folders
 */
public class ContainerFilter  extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parent, Object element) {
		boolean isContainer = element instanceof IContainer;
		
		if( ! isContainer && element instanceof IJavaElement) {
			int type= ((IJavaElement)element).getElementType();
			isContainer = (type == IJavaElement.JAVA_MODEL
						|| type == IJavaElement.JAVA_PROJECT
						|| type == IJavaElement.PACKAGE_FRAGMENT
						|| type ==IJavaElement.PACKAGE_FRAGMENT_ROOT);
		}
		return ! isContainer;
	}

	@Override
	public String toString() {
		return "Filter out Containers"; //$NON-NLS-1$
	}
}
