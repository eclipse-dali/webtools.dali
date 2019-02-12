/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.internal.platform.InternalJaxbPlatformUiManager;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUiManager;

/**
 * Factory to build adapters for a {@link JaxbPlatform}:<ul>
 * <li>{@link JaxbPlatformUi}
 * </ul>
 * See <code>org.eclipse.jpt.jaxb.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * 
 * @see InternalJaxbPlatformUiManager
 */
public class JaxbPlatformAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { JaxbPlatformUi.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof JaxbPlatform) {
			return this.getAdapter((JaxbPlatform) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(JaxbPlatform jaxbPlatform, Class<?> adapterType) {
		if (adapterType == JaxbPlatformUi.class) {
			return this.getJaxbPlatformUi(jaxbPlatform);
		}
		return null;
	}

	private JaxbPlatformUi getJaxbPlatformUi(JaxbPlatform jaxbPlatform) {
		JaxbPlatformUiManager jaxbPlatformUiManager = this.getJaxbPlatformUiManager();
		return (jaxbPlatformUiManager == null) ? null : jaxbPlatformUiManager.getJaxbPlatformUi(jaxbPlatform);
	}

	private JaxbPlatformUiManager getJaxbPlatformUiManager() {
		JaxbWorkbench jaxbWorkbench = this.getJaxbWorkbench();
		return (jaxbWorkbench == null) ? null : jaxbWorkbench.getJaxbPlatformUiManager();
	}

	private JaxbWorkbench getJaxbWorkbench() {
		return WorkbenchTools.getAdapter(JaxbWorkbench.class);
	}
}
