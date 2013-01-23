/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.platform;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;

/**
 * Factory to build Dali adapters for an {@link IResource}:<ul>
 * <li>{@link org.eclipse.jpt.jpa.core.JpaPlatform.Config}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class ResourceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaPlatform.Config.class
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IResource) {
			return this.getAdapter((IResource) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(IResource resource, Class<?> adapterType) {
		if (adapterType == JpaPlatform.Config.class) {
			return this.getJpaPlatformConfig(resource);
		}
		return null;
	}
	
	private JpaPlatform.Config getJpaPlatformConfig(IResource resource) {
		JpaPlatformManager jpaPlatformManager = this.getJpaPlatformManager(resource.getWorkspace());
		return (jpaPlatformManager == null) ? null : jpaPlatformManager.getJpaPlatformConfig(JpaPreferences.getJpaPlatformID(resource.getProject()));
	}

	private JpaPlatformManager getJpaPlatformManager(IWorkspace workspace) {
		JpaWorkspace jpaWorkspace = this.getJpaWorkspace(workspace);
		return (jpaWorkspace == null) ? null : jpaWorkspace.getJpaPlatformManager();
	}

	private JpaWorkspace getJpaWorkspace(IWorkspace workspace) {
		return (JpaWorkspace) workspace.getAdapter(JpaWorkspace.class);
	}
}
