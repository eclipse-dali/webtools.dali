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
import org.eclipse.core.runtime.Path;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;

public class WebModuleResourceLocator
	extends ModuleResourceLocator{
	
	protected static IPath WEB_INF_CLASSES_PATH = new Path(J2EEConstants.WEB_INF_CLASSES);
	
	/**
	 * Return the folder representing the "WEB-INF/classes/META-INF" location
	 */
	@Override
	public IContainer getDefaultResourceLocation(IProject project) {
		IVirtualComponent component = ComponentCore.createComponent(project);
		return component.getRootFolder().getFolder(WEB_INF_CLASSES_PATH.append(META_INF_PATH)).getUnderlyingFolder();
	}
	
	/**
	 * Return the full resource path representing the given runtime location appended
	 * to the "WEB-INF/classes" location
	 */
	@Override
	public IPath getResourcePath(IProject project, IPath runtimePath) {
		return super.getResourcePath(project, WEB_INF_CLASSES_PATH.append(runtimePath));
	}
	
	/**
	 * 
	 */
	@Override
	public IPath getRuntimePath(IProject project, IPath resourcePath) {
		IPath runtimePath = super.getRuntimePath(project, resourcePath);
		if (WEB_INF_CLASSES_PATH.isPrefixOf(runtimePath)) {
			return runtimePath.makeRelativeTo(WEB_INF_CLASSES_PATH);
		}
		return runtimePath;
	}
}
