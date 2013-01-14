/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jaxb.core.JaxbPreferences;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;

/**
 * Factory to build Dali adapters for an {@link IResource}:<ul>
 * <li>{@link JaxbPlatformConfig}
 * </ul>
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class ResourceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JaxbPlatformConfig.class
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
		if (adapterType == JaxbPlatformConfig.class) {
			return this.getJaxbPlatformConfig(resource);
		}
		return null;
	}
	
	private JaxbPlatformConfig getJaxbPlatformConfig(IResource resource) {
		JaxbPlatformManager jpaPlatformManager = this.getJaxbPlatformManager(resource.getWorkspace());
		return (jpaPlatformManager == null) ? null : jpaPlatformManager.getJaxbPlatformConfig(JaxbPreferences.getJaxbPlatformID(resource.getProject()));
	}

	private JaxbPlatformManager getJaxbPlatformManager(IWorkspace workspace) {
		JaxbWorkspace jpaWorkspace = this.getJaxbWorkspace(workspace);
		return (jpaWorkspace == null) ? null : jpaWorkspace.getJaxbPlatformManager();
	}

	private JaxbWorkspace getJaxbWorkspace(IWorkspace workspace) {
		return (JaxbWorkspace) workspace.getAdapter(JaxbWorkspace.class);
	}
}
