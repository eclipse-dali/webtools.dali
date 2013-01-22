/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.utility.internal.filter.SimpleFilter;
import org.eclipse.jpt.common.utility.internal.model.value.ElementPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.ui.JpaProjectModel;
import org.eclipse.jpt.jpa.ui.JpaProjectsModel;
import org.eclipse.jpt.jpa.ui.JpaRootContextNodeModel;

/**
 * Factory to build Dali adapters for an {@link IProject}:<ul>
 * <li>{@link org.eclipse.jpt.jpa.ui.JpaProjectModel JpaProjectModel} -
 *     This adapter will only return a JPA project if it is immediately
 *     available; but it will also notify listeners if the JPA project is
 *     ever created.
 *     This adapter should be used by any process that can temporarily ignore
 *     any uncreated JPA projects but should be notified if the JPA project
 *     <em>is</em> ever created (e.g. UI views).
 * <li>{@link org.eclipse.jpt.jpa.ui.JpaRootContextNodeModel JpaRootContextNodeModel} -
 *     This adapter is much like the {@link org.eclipse.jpt.jpa.ui.JpaProjectModel JpaProjectModel}
 *     adapter described above.
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class ProjectAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaProjectModel.class,
			JpaRootContextNodeModel.class
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
		if (adapterType == JpaProjectModel.class) {
			return this.getJpaProjectModel(project);
		}
		if (adapterType == JpaRootContextNodeModel.class) {
			return this.getJpaRootContextNodeModel(project);
		}
		return null;
	}

	private JpaProjectModel getJpaProjectModel(IProject project) {
		return new JpaProjectModelAdapter(this.getJpaProjectsModel(project.getWorkspace()), project);
	}

	private JpaProjectsModel getJpaProjectsModel(IWorkspace workspace) {
		return (JpaProjectsModel) workspace.getAdapter(JpaProjectsModel.class);
	}

	private JpaRootContextNodeModel getJpaRootContextNodeModel(IProject project) {
		return new JpaRootContextNodeModelAdapter(this.getJpaProjectModel(project));
	}


	// ********** JPA project model **********

	/**
	 * Implement a property value model for the JPA project corresponding to a
	 * {@link IProject project}. The model will fire change events when the
	 * corresponding JPA project is added or removed from the JPA project
	 * manager. This is useful for UI code that does not want to wait to
	 * retrieve a JPA project but wants to be notified when it is available.
	 * <p>
	 * Subclass {@link ElementPropertyValueModelAdapter} so we can
	 * implement {@link org.eclipse.jpt.jpa.ui.JpaProjectModel}.
	 * <p>
	 * <strong>NB:</strong> This model operates outside of all the other
	 * activity synchronized by the JPA project manager; but that should be OK
	 * since it will be kept synchronized with the JPA manager's collection of
	 * JPA projects in the end.
	 */
	/* CU private */ static class JpaProjectModelAdapter
		extends ElementPropertyValueModelAdapter<JpaProject>
		implements JpaProjectModel
	{
		JpaProjectModelAdapter(CollectionValueModel<JpaProject> jpaProjectsModel, IProject project) {
			super(jpaProjectsModel, new Predicate(project));
		}

		public IProject getProject() {
			return ((Predicate) this.predicate).getCriterion();
		}

		/* class private */ static class Predicate
			extends SimpleFilter<JpaProject, IProject>
		{
			private static final long serialVersionUID = 1L;
			Predicate(IProject project) {
				super(project);
			}
			@Override
			public boolean accept(JpaProject jpaProject) {
				return jpaProject.getProject().equals(this.criterion);
			}
		}
	}


	// ********** JPA root context node model **********

	/**
	 * Implement a property value model for the JPA root context node
	 * corresponding to a {@link JpaProject JPA project}. The model will fire
	 * change events when the corresponding JPA project is added or removed
	 * from the JPA project manager. This is useful for UI code that does not
	 * want to wait to retrieve a JPA root context node but wants to be notified
	 * when it is available.
	 * <p>
	 * <strong>NB:</strong> This model operates outside of all the other
	 * activity synchronized by the JPA project manager; but that should be OK
	 * since it will be kept synchronized with the JPA manager's collection of
	 * JPA projects in the end.
	 */
	/* CU private */ static class JpaRootContextNodeModelAdapter
		extends TransformationPropertyValueModel<JpaProject, JpaRootContextNode>
		implements JpaRootContextNodeModel
	{
		JpaRootContextNodeModelAdapter(JpaProjectModel jpaProjectsModel) {
			super(jpaProjectsModel, JpaProject.ROOT_CONTEXT_NODE_TRANSFORMER);
		}

		public IProject getProject() {
			return ((JpaProjectModel) this.valueModel).getProject();
		}
	}
}
