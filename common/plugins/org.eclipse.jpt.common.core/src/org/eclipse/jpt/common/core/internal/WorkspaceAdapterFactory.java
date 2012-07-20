/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;

/**
 * Factory to build Dali adapters for an {@link IWorkspace Eclipse workspace}:<ul>
 * <li>{@link org.eclipse.jpt.common.core.JptWorkspace}
 * </ul>
 * See <code>org.eclipse.jpt.common.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class WorkspaceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JptWorkspace.class
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
		if (adapterType == JptWorkspace.class) {
			return this.getJptWorkspace(workspace);
		}
		return null;
	}

	private JptWorkspace getJptWorkspace(IWorkspace workspace) {
		return JptCommonCorePlugin.instance().getJptWorkspace(workspace);
	}
}
