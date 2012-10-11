/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;

/**
 * Factory to build Dali JAXB adapters for an {@link IWorkspace Eclipse workspace}:<ul>
 * <li>{@link org.eclipse.jpt.jaxb.core.JaxbWorkspace}
 * </ul>
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class WorkspaceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JaxbWorkspace.class
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
		if (adapterType == JaxbWorkspace.class) {
			return this.getJaxbWorkspace(workspace);
		}
		return null;
	}

	private JaxbWorkspace getJaxbWorkspace(IWorkspace workspace) {
		return JptJaxbCorePlugin.instance().getJaxbWorkspace(workspace);
	}
}
