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
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jpt.common.ui.JptCommonUiPlugin;

/**
 * This filter will deny showing any file that are not JAR files or folders
 * that don't contain any JAR files in its sub-hierarchy. 
 */
public class ArchiveFileViewerFilter 
	extends ViewerFilter 
{
	private static final String[] archiveExtensions= { "jar", "zip" }; //$NON-NLS-1$ //$NON-NLS-2$ 
	
	
	public ArchiveFileViewerFilter() {
		super();
	}
	
	
	@Override
	public boolean select(
			Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IFile) {
			return isArchivePath(((IFile)element).getFullPath());
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
				JptCommonUiPlugin.log(ce);
			}
		}
		return false;
	}
	
	public static boolean isArchivePath(IPath path) {
		String ext= path.getFileExtension();
		if (ext != null && ext.length() != 0) {
			for (int i= 0; i < archiveExtensions.length; i++) {
				if (ext.equalsIgnoreCase(archiveExtensions[i])) {
					return true;
				}
			}
		}
		return false;
	}		
}
