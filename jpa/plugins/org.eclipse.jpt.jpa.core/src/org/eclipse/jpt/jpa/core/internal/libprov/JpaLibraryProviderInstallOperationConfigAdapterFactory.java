/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.libprov;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.core.libprov.JpaLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;

/**
 * Factory to build Dali adapters for an {@link JpaLibraryProviderInstallOperationConfig}:<ul>
 * <li>{@link JpaPlatformDescription}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml</code>.
 */
public class JpaLibraryProviderInstallOperationConfigAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaPlatformDescription.class
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof JpaLibraryProviderInstallOperationConfig) {
			return this.getAdapter((JpaLibraryProviderInstallOperationConfig) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(JpaLibraryProviderInstallOperationConfig config, Class<?> adapterType) {
		if (adapterType == JpaPlatformDescription.class) {
			return config.getJpaPlatform();
		}
		return null;
	}
}
