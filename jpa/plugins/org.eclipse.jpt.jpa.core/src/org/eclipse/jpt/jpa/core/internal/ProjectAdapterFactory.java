/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Factory to build Dali adapters for an {@link IProject}:<ul>
 * <li>{@link org.eclipse.jpt.jpa.core.JpaProject.Reference JpaProject.Reference} -
 *     This adapter will always return a JPA project if it is available;
 *     even if it must wait for the JPA project to be created (note the
 *     {@link InterruptedException}).
 *     This adapter should be used by any process that cannot ignore any
 *     potential JPA projects (e.g. refactoring operations).
 * <li>{@link org.eclipse.jpt.jpa.core.JpaProject JpaProject} -
 *     This adapter will only return a JPA project if it is immediately
 *     available.
 *     This adapter should be used by any process that can ignore
 *     any uncreated JPA projects because it is demand-driven and re-queries
 *     for the JPA project every time it executes and its results are only
 *     temporary (e.g. UI menus).
 * </ul>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class ProjectAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaProject.Reference.class,
			JpaProject.class,
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IProject) {
			return this.getAdapter((IProject) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(IProject project, Class<?> adapterType) {
		if (adapterType == JpaProject.Reference.class) {
			return this.getJpaProjectReference(project);
		}
		if (adapterType == JpaProject.class) {
			return this.getJpaProject(project);
		}
		return null;
	}

	private JpaProject.Reference getJpaProjectReference(IProject project) {
		InternalJpaProjectManager jpaProjectManager = this.getJpaProjectManager(project);
		return (jpaProjectManager == null) ? null : new JpaProjectReference(jpaProjectManager, project);
	}

	private JpaProject getJpaProject(IProject project) {
		InternalJpaProjectManager jpaProjectManager = this.getJpaProjectManager(project);
		if (jpaProjectManager == null) {
			return null;
		}
		for (JpaProject jpaProject : jpaProjectManager.getJpaProjects_()) {
			if (jpaProject.getProject().equals(project)) {
				return jpaProject;
			}
		}
		return null;
	}

	private InternalJpaProjectManager getJpaProjectManager(IProject project) {
		return this.getJpaProjectManager(project.getWorkspace());
	}

	private InternalJpaProjectManager getJpaProjectManager(IWorkspace workspace) {
		return (InternalJpaProjectManager) workspace.getAdapter(JpaProjectManager.class);
	}


	// ********** JPA project reference **********

	/**
	 * @see InternalJpaProjectManager
	 */
	/* CU private */ static class JpaProjectReference
		implements JpaProject.Reference
	{
		private InternalJpaProjectManager jpaProjectManager;
		private final IProject project;

		JpaProjectReference(InternalJpaProjectManager jpaProjectManager, IProject project) {
			super();
			this.jpaProjectManager = jpaProjectManager;
			this.project = project;
		}

		public JpaProject getValue() throws InterruptedException {
			return this.jpaProjectManager.waitToGetJpaProject(this.project);
		}

		public JpaProject rebuild() throws InterruptedException {
			return this.jpaProjectManager.rebuildJpaProject(this.project);
		}

		public Iterable<IMessage> buildValidationMessages(IReporter reporter) throws InterruptedException {
			return this.jpaProjectManager.buildValidationMessages(this.project, reporter);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.project);
		}
	}
}
