/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiManager;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.internal.platform.InternalJpaPlatformUiManager;

/**
 * Factory to build adapters for a {@link JpaPlatform}:<ul>
 * <li>{@link JpaPlatformUi}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * 
 * @see InternalJpaPlatformUiManager
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
		JpaPlatformUiManager jpaPlatformUiManager = this.getJpaPlatformUiManager();
		return (jpaPlatformUiManager == null) ? null : jpaPlatformUiManager.getJpaPlatformUi(jpaPlatform);
	}

	private JpaPlatformUiManager getJpaPlatformUiManager() {
		JpaWorkbench jpaWorkbench = this.getJpaWorkbench();
		return (jpaWorkbench == null) ? null : jpaWorkbench.getJpaPlatformUiManager();
	}

	private JpaWorkbench getJpaWorkbench() {
		return WorkbenchTools.getAdapter(JpaWorkbench.class);
	}
}
