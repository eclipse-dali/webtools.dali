/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libprov;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;

/**
 * Factory to build Dali adapters for an {@link JaxbLibraryProviderInstallOperationConfig}:<ul>
 * <li>{@link JaxbPlatformConfig}
 * </ul>
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class JaxbLibraryProviderInstallOperationConfigAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JaxbPlatformConfig.class
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof JaxbLibraryProviderInstallOperationConfig) {
			return this.getAdapter((JaxbLibraryProviderInstallOperationConfig) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(JaxbLibraryProviderInstallOperationConfig config, Class<?> adapterType) {
		if (adapterType == JaxbPlatformConfig.class) {
			return config.getJaxbPlatformConfig();
		}
		return null;
	}
}
