/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.ui.IWorkbench;

/**
 * Factory to build the Dali JPA adapters for a {@link IWorkbench}:<ul>
 * <li>{@link JpaWorkbench}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * 
 * @see JpaWorkbench
 */
public class WorkbenchAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
		JpaWorkbench.class
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
		if (adapterType == JpaWorkbench.class) {
			return this.getJpaWorkbench(workbench);
		}
		return null;
	}

	private JpaWorkbench getJpaWorkbench(IWorkbench workbench) {
		return JptJpaUiPlugin.instance().getJpaWorkbench(workbench);
	}
}
