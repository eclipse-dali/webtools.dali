/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.PathTools;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.common.componentcore.resources.IVirtualResource;

public class ModuleResourceLocator
	extends SimpleJavaResourceLocator
{
	/**
	 * Return the folder representing the <code>META-INF</code> runtime location.
	 */
	@Override
	public IContainer getDefaultLocation(IProject project) {
		return this.getRootFolder(project).getFolder(META_INF_PATH).getUnderlyingFolder();
	}
	
	@Override
	public IPath getWorkspacePath(IProject project, IPath runtimePath) {
		return this.getRootFolder(project).getFile(runtimePath).getWorkspaceRelativePath();
	}
	
	@Override
	public IPath getRuntimePath(IProject project, IPath resourcePath) {
		IVirtualFolder rootFolder = this.getRootFolder(project);
		IFile file = PathTools.getFile(resourcePath);
		IVirtualFile vFile = this.getVirtualFile(rootFolder, file);
		if (vFile != null) {
			return vFile.getRuntimePath().makeRelative();
		}
		// couldn't find it - try the super-case
		return super.getRuntimePath(project, resourcePath);
	}
	
	protected IVirtualFile getVirtualFile(IVirtualFolder vFolder, IFile file) {
		try {
			return this.getVirtualFile_(vFolder, file);
		} catch (CoreException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return null;
		}
	}

	protected IVirtualFile getVirtualFile_(IVirtualFolder vFolder, IFile file) throws CoreException {
		for (IVirtualResource vResource : vFolder.members()) {
			IVirtualFile vFile = this.getVirtualFile(vResource, file);
			if (vFile != null) {
				return vFile;
			}
		}
		return null;
	}

	protected IVirtualFile getVirtualFile(IVirtualResource vResource, IFile file) throws CoreException {
		switch (vResource.getType()) {
			case IVirtualResource.FILE:
				IVirtualFile vFile = (IVirtualFile) vResource;
				return file.equals(vFile.getUnderlyingResource()) ? vFile : null;
			case IVirtualResource.FOLDER:
				// recurse
				return this.getVirtualFile_((IVirtualFolder) vResource, file);
			default:
				return null;
		}
	}

	protected IVirtualFolder getRootFolder(IProject project) {
		return ComponentCore.createComponent(project).getRootFolder();
	}
}
