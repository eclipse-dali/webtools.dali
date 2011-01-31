/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.core.IResourcePart;

/**
 * Factory to build adapters for Eclipse resources:
 *   - resource part
 * 
 * See org.eclipse.jpt.common.core plugin.xml.
 */
public class ResourceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { IResourcePart.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("unchecked") Class adapterType) {
		if (adaptableObject instanceof IResource) {
			return this.getAdapter((IResource) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(final IResource resource, Class <?>adapterType) {
		if (adapterType == IResourcePart.class) {
			return new IResourcePart() {
				public IResource getResource() {
					return resource;
				}
			};
		}
		return null;
	}

}
