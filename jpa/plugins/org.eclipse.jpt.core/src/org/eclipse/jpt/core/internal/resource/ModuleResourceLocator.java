/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.common.componentcore.resources.IVirtualResource;

public class ModuleResourceLocator
		extends SimpleJavaResourceLocator {
	
	/**
	 * Return the folder representing the "META-INF" runtime location
	 */
	@Override
	public IContainer getDefaultResourceLocation(IProject project) {
		IVirtualComponent component = ComponentCore.createComponent(project);
		return component.getRootFolder().getFolder(META_INF_PATH).getUnderlyingFolder();
	}
	
	@Override
	public IPath getResourcePath(IProject project, IPath runtimePath) {
		IVirtualComponent component = ComponentCore.createComponent(project);
		return component.getRootFolder().getFile(runtimePath).getWorkspaceRelativePath();
	}
	
	@Override
	public IPath getRuntimePath(IProject project, IPath resourcePath) {
		IFile file = PlatformTools.getFile(resourcePath);
		IVirtualComponent component = ComponentCore.createComponent(project);
		IVirtualFolder root = component.getRootFolder();
		IVirtualFile vFile = findVirtualFile(root, file);
		if (vFile != null) {
			return vFile.getRuntimePath().makeRelative();
		}
		// couldn't find it.  try the super-case
		return super.getRuntimePath(project, resourcePath);
	}
	
	private IVirtualFile findVirtualFile(IVirtualFolder vFolder, IFile file) {
		try {
			for (IVirtualResource vResource : vFolder.members()) {
				if (vResource.getType() == IVirtualResource.FILE) {
					IVirtualFile vFile = (IVirtualFile) vResource;
					if (file.equals(vFile.getUnderlyingResource())) {
						return vFile;
					}
				}
				else if (vResource.getType() == IVirtualResource.FOLDER) {
					IVirtualFile vFile = findVirtualFile((IVirtualFolder) vResource, file);
					if (vFile != null) {
						return vFile;
					}
				}
			}
		}
		catch (CoreException ce) {
			// fall through
			JptCorePlugin.log(ce);
		}
		return null;
	}
}
