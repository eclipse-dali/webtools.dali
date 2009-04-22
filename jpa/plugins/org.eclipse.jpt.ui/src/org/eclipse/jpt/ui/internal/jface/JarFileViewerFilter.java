/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * This filter will deny showing any file that are not JAR files or folders
 * that don't contain any JAR files in its sub-hierarchy. 
 */
public class JarFileViewerFilter 
	extends ViewerFilter 
{
	public JarFileViewerFilter() {
		super();
	}
	
	
	
	@Override
	public boolean select(
			Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IFile) {
			return isJarFile((IFile) element);
		}
		else if (element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			try {
				for (IResource each : folder.members()) {
					if (select(viewer, folder, each)) {
						return true;
					}
				}
			}
			catch (CoreException ce) {
				// just skip this one, then
			}
		}
		return false;
	}
	
	/* there doesn't seem to be a very good way of determining if a file is an
	 * actual jar file, so for now, if it's a file => true.
	 */
	protected boolean isJarFile(IFile file) {
		return true;
	}
}
