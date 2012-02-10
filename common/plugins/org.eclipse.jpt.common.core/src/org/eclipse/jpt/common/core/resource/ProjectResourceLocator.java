/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

/**
 * See <code>org.eclipse.jpt.common.core/plugin.xml</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 * @see org.eclipse.jpt.common.core.internal.resource.ProjectAdapterFactory
 */
public interface ProjectResourceLocator {
	/**
	 * Return whether the specified container is an acceptable (non-Java)
	 * resource location for the locator's project.
	 */
	boolean resourceLocationIsValid(IContainer container);
	
	/**
	 * Return the default location in which to create new (non-Java) resources.
	 */
	IContainer getDefaultResourceLocation();
	
	/**
	 * Return the workspace relative absolute resource path best represented by
	 * the specified runtime path for the locator's project.
	 */
	IPath getResourcePath(IPath runtimePath);
	
	/**
	 * Return the runtime path best represented by the specified workspace
	 * relative absolute resource path for the locator's project.
	 */
	IPath getRuntimePath(IPath resourcePath);

	/**
	 * Return an {@link IFile} that best represents the specified runtime
	 * location.
	 */
	IFile getPlatformFile(IPath runtimePath);
}
