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

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.ui.JaxbProjectsModel;

/**
 * Factory to build Dali adapters for an {@link IWorkspace}:<ul>
 * <li>{@link org.eclipse.jpt.jaxb.ui.JaxbProjectsModel JaxbProjectsModel} -
 *     This adapter will only return a JAXB project if it is immediately
 *     available; but it will also notify listeners if the JAXB project is
 *     ever created.
 *     This adapter should be used by any process that can temporarily ignore
 *     any uncreated JAXB projects but should be notified if the JAXB project
 *     <em>is</em> ever created (e.g. UI views).
 * </ul>
 * See <code>org.eclipse.jpt.jaxb.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class WorkspaceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JaxbProjectsModel.class
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
		if (adapterType == JaxbProjectsModel.class) {
			return this.getJaxbProjectsModel(workspace);
		}
		return null;
	}

	private JaxbProjectsModel getJaxbProjectsModel(IWorkspace workspace) {
		return new JaxbProjectsModelAdapter(this.getJaxbProjectManager(workspace));
	}

	private JaxbProjectManager getJaxbProjectManager(IWorkspace workspace) {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace(workspace);
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace(IWorkspace workspace) {
		return (JaxbWorkspace) workspace.getAdapter(JaxbWorkspace.class);
	}


	// ********** JAXB projects model **********

	/**
	 * Adapt the JAXB project manager's JAXB projects list to the collection value
	 * model interface.
	 */
	/* CU private */ static class JaxbProjectsModelAdapter
		extends CollectionAspectAdapter<JaxbProjectManager, JaxbProject>
		implements JaxbProjectsModel
	{
		JaxbProjectsModelAdapter(JaxbProjectManager jaxbProjectManager) {
			super(JaxbProjectManager.JAXB_PROJECTS_COLLECTION, jaxbProjectManager);
		}

		@Override
		protected Iterable<JaxbProject> getIterable() {
			return this.subject.getJaxbProjects();
		}

		@Override
		protected int size_() {
			return this.subject.getJaxbProjectsSize();
		}

		public JaxbWorkspace getJaxbWorkspace() {
			return this.subject.getJaxbWorkspace();
		}
	}
}
