/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.ResourceReference;

/**
 * Factory to build adapters for a {@link ResourceReference}:<ul>
 * <li>{@link IResource}
 * </ul>
 * See <code>org.eclipse.jpt.common.core plugin.xml</code>.
 */
public class ResourceReferenceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { IResource.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof ResourceReference) {
			return this.getAdapter((ResourceReference) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(ResourceReference resourceReference, Class <?>adapterType) {
		if (adapterType == IResource.class) {
			return resourceReference.getResource();
		}
		return null;
	}
}
