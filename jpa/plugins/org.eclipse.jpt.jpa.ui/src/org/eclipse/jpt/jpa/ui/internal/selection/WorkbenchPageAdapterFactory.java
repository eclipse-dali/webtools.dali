/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.selection;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IWorkbenchPage;

/**
 * Factory to build JPA selection adapters for a {@link IWorkbenchPage}:<ul>
 * <li>{@link JpaSelectionManager}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * 
 * @see JpaWorkbenchSelectionManager
 */
public class WorkbenchPageAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { JpaSelectionManager.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IWorkbenchPage) {
			return this.getAdapter((IWorkbenchPage) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(IWorkbenchPage workbenchPage, Class<?> adapterType) {
		if (adapterType == JpaSelectionManager.class) {
			return this.getJpaSelectionManager(workbenchPage);
		}
		return null;
	}

	/**
	 * Never return <code>null</code>.
	 */
	private JpaSelectionManager getJpaSelectionManager(IWorkbenchPage workbenchPage) {
		JpaSelectionManager manager = JpaPageSelectionManager.forPage(workbenchPage);
		return (manager != null) ? manager : JpaSelectionManager.Null.instance();
	}
}
