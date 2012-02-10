/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;

/**
 * Factory to build adapters for a {@link JpaFile}:<ul>
 * <li>{@link ItemTreeStateProviderFactoryProvider}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class JpaFileAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { ItemTreeStateProviderFactoryProvider.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof JpaFile) {
			return this.getAdapter((JpaFile) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(JpaFile jpaFile, Class<?> adapterType) {
		if (adapterType == ItemTreeStateProviderFactoryProvider.class) {
			return this.getJpaStructureViewFactoryProvider(jpaFile);
		}
		return null;
	}

	private ItemTreeStateProviderFactoryProvider getJpaStructureViewFactoryProvider(JpaFile jpaFile) {
		return this.getPlatformUi(jpaFile).getStructureViewFactoryProvider(jpaFile);
	}

	private JpaPlatformUi getPlatformUi(JpaFile jpaFile) {
		return (JpaPlatformUi) jpaFile.getJpaProject().getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}
}
