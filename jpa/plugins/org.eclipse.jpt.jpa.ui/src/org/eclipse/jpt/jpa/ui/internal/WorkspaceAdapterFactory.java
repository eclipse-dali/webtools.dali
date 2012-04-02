/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.ui.JpaProjectsModel;

/**
 * Factory to build Dali adapters for an {@link IWorkspace}:<ul>
 * <li>{@link org.eclipse.jpt.jpa.ui.JpaProjectsModel JpaProjectsModel} -
 *     This adapter will only return a JPA project if it is immediately
 *     available; but it will also notify listeners if the JPA project is
 *     ever created.
 *     This adapter should be used by any process that can temporarily ignore
 *     any uncreated JPA projects but should be notified if the JPA project
 *     <em>is</em> ever created (e.g. UI views).
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class WorkspaceAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaProjectsModel.class
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
		if (adapterType == JpaProjectsModel.class) {
			return this.getJpaProjectsModel(workspace);
		}
		return null;
	}

	private JpaProjectsModel getJpaProjectsModel(IWorkspace workspace) {
		return new JpaProjectsModelAdapter(this.getJpaProjectManager(workspace));
	}

	private JpaProjectManager getJpaProjectManager(IWorkspace workspace) {
		return (JpaProjectManager) workspace.getAdapter(JpaProjectManager.class);
	}


	// ********** JPA projects model **********

	/**
	 * Adapt the JPA project manager's JPA projects list to the collection value
	 * model interface.
	 */
	/* CU private */ static class JpaProjectsModelAdapter
		extends CollectionAspectAdapter<JpaProjectManager, JpaProject>
		implements JpaProjectsModel
	{
		JpaProjectsModelAdapter(JpaProjectManager jpaProjectManager) {
			super(JpaProjectManager.JPA_PROJECTS_COLLECTION, jpaProjectManager);
		}

		@Override
		protected Iterable<JpaProject> getIterable() {
			return this.subject.getJpaProjects();
		}

		@Override
		protected int size_() {
			return this.subject.getJpaProjectsSize();
		}

		public IWorkspace getWorkspace() {
			return this.subject.getWorkspace();
		}
	}
}
