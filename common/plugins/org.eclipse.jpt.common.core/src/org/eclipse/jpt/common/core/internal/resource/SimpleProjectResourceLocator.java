/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.core.resource.ResourceLocator;
import org.eclipse.jpt.common.core.resource.ResourceLocatorManager;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * @see org.eclipse.jpt.common.core.internal.resource.ProjectAdapterFactory
 * @see ResourceLocatorManager
 */
public class SimpleProjectResourceLocator
	implements ProjectResourceLocator
{
	protected final IProject project;


	public SimpleProjectResourceLocator(IProject project) {
		super();
		this.project = project;
	}

	public boolean resourceLocationIsValid(IContainer container) {
		ResourceLocator resourceLocator = this.getResourceLocator();
		return (resourceLocator != null) && resourceLocator.locationIsValid(this.project, container);
	}

	public IContainer getDefaultResourceLocation() {
		ResourceLocator resourceLocator = this.getResourceLocator();
		return (resourceLocator == null) ? null : resourceLocator.getDefaultLocation(this.project);
	}

	public IPath getResourcePath(IPath runtimePath) {
		ResourceLocator resourceLocator = this.getResourceLocator();
		return (resourceLocator == null) ? null : resourceLocator.getWorkspacePath(this.project, runtimePath);
	}

	public IPath getRuntimePath(IPath resourcePath) {
		ResourceLocator resourceLocator = this.getResourceLocator();
		return (resourceLocator == null) ? null : resourceLocator.getRuntimePath(this.project, resourcePath);
	}

	public IFile getPlatformFile(IPath runtimePath) {
		IPath sourcePath = this.getResourcePath(runtimePath);
		return (sourcePath == null) ? null : this.project.getWorkspace().getRoot().getFile(sourcePath);
	}

	protected ResourceLocator getResourceLocator() {
		ResourceLocatorManager rlManager = this.getResourceLocatorManager();
		return (rlManager == null) ? null : rlManager.getResourceLocator(this.project);
	}

	private ResourceLocatorManager getResourceLocatorManager() {
		JptWorkspace jptWorkspace = this.getJptWorkspace();
		return (jptWorkspace == null) ? null : jptWorkspace.getResourceLocatorManager();
	}

	private JptWorkspace getJptWorkspace() {
		return (JptWorkspace) this.getWorkspace().getAdapter(JptWorkspace.class);
	}

	private IWorkspace getWorkspace() {
		return this.project.getWorkspace();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.project);
	}
}
