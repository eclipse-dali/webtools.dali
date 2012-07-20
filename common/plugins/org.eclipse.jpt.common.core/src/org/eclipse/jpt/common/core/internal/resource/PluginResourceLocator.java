/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.pde.core.project.IBundleProjectService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class PluginResourceLocator
	extends SimpleJavaResourceLocator
{
	@Override
	public IContainer getDefaultResourceLocation(IProject project) {
		IContainer bundleRoot = this.getBundleRoot(project);
		return (bundleRoot != null) ?
				bundleRoot.getFolder(META_INF_PATH) :
				super.getDefaultResourceLocation(project);
	}

	@Override
	public IPath getResourcePath(IProject project, IPath runtimePath) {
		IContainer bundleRoot = this.getBundleRoot(project);
		if (bundleRoot != null) {
			IPath resourcePath = bundleRoot.getFullPath().append(runtimePath);
			if (project.getWorkspace().getRoot().getFile(resourcePath).exists()) {
				return resourcePath;
			}
		}
		return super.getResourcePath(project, runtimePath);
	}

	@Override
	public IPath getRuntimePath(IProject project, IPath resourcePath) {
		IContainer bundleRoot = this.getBundleRoot(project);
		if (bundleRoot != null) {
			IFile file = PlatformTools.getFile(resourcePath);
			if (bundleRoot.contains(file)) {
				return resourcePath.makeRelativeTo(bundleRoot.getFullPath());
			}
		}
		return super.getRuntimePath(project, resourcePath);
	}

	protected IContainer getBundleRoot(IProject project) {
		try {
			return this.getBundleRoot_(project);
		} catch (CoreException ex) {
			// problem creating description on an existing project
			JptCommonCorePlugin.instance().logError(ex);
			return null;
		}
	}

	protected IContainer getBundleRoot_(IProject project) throws CoreException {
		BundleContext bundleContext = JptCommonCorePlugin.instance().getBundle().getBundleContext();
		if (bundleContext == null) {
			return project;
		}
		ServiceReference<IBundleProjectService> serviceRef = bundleContext.getServiceReference(IBundleProjectService.class);
		if (serviceRef == null) {
			return project;
		}
		IBundleProjectService service = bundleContext.getService(serviceRef);
		if (service == null) {
			bundleContext.ungetService(serviceRef);
			return project;
		}
		IPath bundleRoot = service.getDescription(project).getBundleRoot();
		bundleContext.ungetService(serviceRef);
		return (bundleRoot == null) ? project : project.getFolder(bundleRoot);
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
