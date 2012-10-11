/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
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
		return this.getJaxbPlatformManager().getJaxbPlatformConfig(JaxbPreferences.getJaxbPlatformID(resource.getProject()));
	}

	private JaxbPlatformManager getJaxbPlatformManager() {
		return getJaxbWorkspace().getJaxbPlatformManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}
}
