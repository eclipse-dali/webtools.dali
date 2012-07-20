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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;

/**
 * Factory to build Dali adapters for an {@link IProject}:<ul>
 * <li>{@link org.eclipse.jpt.common.core.resource.ProjectResourceLocator}
 * </ul>
 * See <code>org.eclipse.jpt.common.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class ProjectAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			ProjectResourceLocator.class
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IProject) {
			return this.getAdapter((IProject) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(IProject project, Class<?> adapterType) {
		if (adapterType == ProjectResourceLocator.class) {
			return this.getProjectResourceLocator(project);
		}
		return null;
	}
	
	private ProjectResourceLocator getProjectResourceLocator(IProject project) {
		return new SimpleProjectResourceLocator(project);
	}
}
