/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*     
*     Code originate from XXXX
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.filters;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 *  NonArchiveOrExternalElementFilter
 */
public class NonArchiveOrExternalElementFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parent, Object element) {
		if(element instanceof IPackageFragmentRoot) {
			IPackageFragmentRoot root= (IPackageFragmentRoot) element;
			return !root.isArchive() && !root.isExternal();
		}
		return true;
	}

	@Override
	public String toString() {
		return "Filter out: Non-Archive and Non-External"; //$NON-NLS-1$
	}

}
