/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.jpt.jpa.db.internal.plugin.JptJpaDbPlugin;

/**
 * Factory to build Dali database adapters for an {@link IWorkspace}:<ul>
 * <li>{@link ConnectionProfileFactory}
 * </ul>
 * See <code>org.eclipse.jpt.db/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class WorkspaceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			ConnectionProfileFactory.class
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IWorkspace) {
			return this.getAdapter((IWorkspace) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(IWorkspace workspace, Class<?> adapterType) {
		if (adapterType == ConnectionProfileFactory.class) {
			return this.getConnectionProfileFactory(workspace);
		}
		return null;
	}
	
	private ConnectionProfileFactory getConnectionProfileFactory(IWorkspace workspace) {
		return JptJpaDbPlugin.instance().getConnectionProfileFactory(workspace);
	}
}
