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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jst.j2ee.internal.J2EEConstants;

public class WebModuleResourceLocator
	extends ModuleResourceLocator
{
	protected static final IPath WEB_INF_CLASSES_PATH = new Path(J2EEConstants.WEB_INF_CLASSES);
	protected static final IPath WEB_META_INF_PATH = WEB_INF_CLASSES_PATH.append(META_INF_PATH);

	/**
	 * Return the folder representing the <code>WEB-INF/classes/META-INF</code>
	 * location.
	 */
	@Override
	public IContainer getDefaultResourceLocation(IProject project) {
		return this.getRootFolder(project).getFolder(WEB_META_INF_PATH).getUnderlyingFolder();
	}

	/**
	 * Return the full resource path representing the specified runtime location
	 * appended to the <code>WEB-INF/classes</code> location.
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
		return WEB_INF_CLASSES_PATH.isPrefixOf(runtimePath) ?
				runtimePath.makeRelativeTo(WEB_INF_CLASSES_PATH) :
				runtimePath;
	}
}
