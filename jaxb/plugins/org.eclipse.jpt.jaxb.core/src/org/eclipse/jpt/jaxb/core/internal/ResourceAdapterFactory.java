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
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;

/**
 * Factory to build Dali adapters for an {@link IResource}:<ul>
 * <li>{@link JaxbPlatformDescription}
 * </ul>
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml</code>.
 */
public class ResourceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JaxbPlatformDescription.class
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
		if (adapterType == JaxbPlatformDescription.class) {
			return this.getJaxbPlatformDescription(resource);
		}
		return null;
	}
	
	private JaxbPlatformDescription getJaxbPlatformDescription(IResource resource) {
		return JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatform(JptJaxbCorePlugin.getJaxbPlatformId(resource.getProject()));
	}
}
