/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.platform;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;

/**
 * Factory to build Dali adapters for an {@link IJavaElement}:<ul>
 * <li>{@link JpaPlatformDescription}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml</code>.
 */
public class JavaElementAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaPlatformDescription.class
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IJavaElement) {
			return this.getAdapter((IJavaElement) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(IJavaElement javaElement, Class<?> adapterType) {
		if (adapterType == JpaPlatformDescription.class) {
			return javaElement.getResource().getAdapter(JpaPlatformDescription.class);
		}
		return null;
	}
}
