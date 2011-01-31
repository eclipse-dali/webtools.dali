/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.pde.core.project.IBundleProjectDescription;
import org.eclipse.pde.core.project.IBundleProjectService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class PluginResourceLocator
		extends SimpleJavaResourceLocator {
	
	@Override
	public IContainer getDefaultResourceLocation(IProject project) {
		try {
			IContainer root = getBundleRoot(project);
			return root.getFolder(META_INF_PATH);
		}
		catch (CoreException ce) {
			// fall through
			JptCommonCorePlugin.log(ce);
		}
		return super.getDefaultResourceLocation(project);
	}
	
	@Override
	public IPath getResourcePath(IProject project, IPath runtimePath) {
		try {
			IContainer root = getBundleRoot(project);
			IPath resourcePath = root.getFullPath().append(runtimePath);
			if (project.getWorkspace().getRoot().getFile(resourcePath).exists()) {
				return resourcePath;
			}
		}
		catch (CoreException ce) {
			// fall through
			JptCommonCorePlugin.log(ce);
		}
		return super.getResourcePath(project, runtimePath);
	}
	
	@Override
	public IPath getRuntimePath(IProject project, IPath resourcePath) {
		IFile file = PlatformTools.getFile(resourcePath);
		try {
			IContainer root = getBundleRoot(project);
			if (root.contains(file)) {
				return resourcePath.makeRelativeTo(root.getFullPath());
			}
		}
		catch (CoreException ce) {
			// fall through
			JptCommonCorePlugin.log(ce);
		}
		return super.getRuntimePath(project, resourcePath);
	}
	
	protected IContainer getBundleRoot(IProject project) 
			throws CoreException {
		
		IBundleProjectService service = getBundleProjectService();
		IBundleProjectDescription description = service.getDescription(project);
		IPath path = description.getBundleRoot();
		return (path == null) ? project : project.getFolder(path);
	}
	
	protected IBundleProjectService getBundleProjectService() {
		BundleContext context = JptCommonCorePlugin.instance().getBundle().getBundleContext();
		ServiceReference<IBundleProjectService> reference = context.getServiceReference(IBundleProjectService.class);
		if (reference == null) {
			return null;
		}
		IBundleProjectService service = context.getService(reference);
		if (service != null) {
			context.ungetService(reference);
		}
		return service;
	}
}
