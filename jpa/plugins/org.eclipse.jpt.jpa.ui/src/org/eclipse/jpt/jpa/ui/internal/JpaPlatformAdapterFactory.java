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
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.internal.platform.JpaPlatformUiRegistry;

/**
 * Factory to build adapters for a {@link JpaPlatform}:<ul>
 * <li>{@link JpaPlatformUi}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * 
 * @see JpaPlatformUiRegistry
 */
public class JpaPlatformAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { JpaPlatformUi.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof JpaPlatform) {
			return this.getAdapter((JpaPlatform) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(JpaPlatform jpaPlatform, Class<?> adapterType) {
		if (adapterType == JpaPlatformUi.class) {
			return this.getJpaPlatformUi(jpaPlatform);
		}
		return null;
	}

	private JpaPlatformUi getJpaPlatformUi(JpaPlatform jpaPlatform) {
		return JpaPlatformUiRegistry.instance().getJpaPlatformUi(jpaPlatform.getId());
	}
}
