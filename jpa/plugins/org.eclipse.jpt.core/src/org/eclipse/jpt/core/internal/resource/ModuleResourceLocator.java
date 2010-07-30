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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;

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
	
	/**
	 * Return the full resource path representing the given runtime location
	 */
	@Override
	public IPath getResourcePath(IProject project, IPath runtimePath) {
		IVirtualComponent component = ComponentCore.createComponent(project);
		return component.getRootFolder().getFile(runtimePath).getWorkspaceRelativePath();
	}
}
