/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;
import org.eclipse.ui.IWorkbench;

/**
 * Factory to build the Dali JAXB adapters for a {@link IWorkbench}:<ul>
 * <li>{@link JaxbWorkbench}
 * </ul>
 * See <code>org.eclipse.jpt.jaxb.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * 
 * @see JaxbWorkbench
 */
public class WorkbenchAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
		JaxbWorkbench.class
	};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IWorkbench) {
			return this.getAdapter((IWorkbench) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(IWorkbench workbench, Class<?> adapterType) {
		if (adapterType == JaxbWorkbench.class) {
			return this.getJaxbWorkbench(workbench);
		}
		return null;
	}

	private JaxbWorkbench getJaxbWorkbench(IWorkbench workbench) {
		return JptJaxbUiPlugin.instance().getJaxbWorkbench(workbench);
	}
}
