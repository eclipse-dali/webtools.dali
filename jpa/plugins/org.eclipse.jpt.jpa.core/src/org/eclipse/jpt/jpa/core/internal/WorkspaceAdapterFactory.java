/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;

/**
 * Factory to build Dali JPA adapters for an {@link IWorkspace Eclipse workspace}:<ul>
 * <li>{@link org.eclipse.jpt.jpa.core.JpaWorkspace}
 * <li>{@link JpaProjectManager}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class WorkspaceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaWorkspace.class,
			JpaProjectManager.class
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
		if (adapterType == JpaWorkspace.class) {
			return this.getJpaWorkspace(workspace);
		}
		if (adapterType == JpaProjectManager.class) {
			return this.getJpaProjectManager(workspace);
		}
		return null;
	}

	private JpaWorkspace getJpaWorkspace(IWorkspace workspace) {
		return JptJpaCorePlugin.instance().getJpaWorkspace(workspace);
	}

	private JpaProjectManager getJpaProjectManager(IWorkspace workspace) {
		return this.getJpaWorkspace(workspace).getJpaProjectManager();
	}
}
