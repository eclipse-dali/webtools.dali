/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface ResourceLocator {
	
	/**
	 * Return whether the given container is an acceptable (non-java) resource 
	 * location for the given project
	 */
	boolean acceptResourceLocation(IProject project, IContainer container);
	
	/**
	 * Return the default location in which to create new (non-java) resources
	 */
	IContainer getDefaultResourceLocation(IProject project);
	
	/**
	 * Return the workspace relative absolute resource path best represented by the given 
	 * runtime path for the given project
	 */
	IPath getResourcePath(IProject project, IPath runtimePath);
	
	/**
	 * Return the runtime path best represented by the given workspace relative absolute
	 * resource path for the given project
	 */
	IPath getRuntimePath(IProject project, IPath resourcePath);
}
