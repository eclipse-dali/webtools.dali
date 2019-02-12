/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.utility.internal.model.value.ElementPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.ui.JaxbProjectModel;
import org.eclipse.jpt.jaxb.ui.JaxbProjectsModel;

/**
 * Factory to build Dali JAXB adapters for an {@link IProject}:<ul>
 * <li>{@link org.eclipse.jpt.jaxb.ui.JaxbProjectModel JaxbProjectModel} -
 *     This adapter will only return a JAXB project if it is immediately
 *     available; but it will also notify listeners if the JAXB project is
 *     ever created.
 *     This adapter should be used by any process that can temporarily ignore
 *     any uncreated JAXB projects but should be notified if the JAXB project
 *     <em>is</em> ever created (e.g. UI views).
 * </ul>
 * See <code>org.eclipse.jpt.jaxb.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class ProjectAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JaxbProjectModel.class
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
		if (adapterType == JaxbProjectModel.class) {
			return this.getJaxbProjectModel(project);
		}
		return null;
	}

	private JaxbProjectModel getJaxbProjectModel(IProject project) {
		return new JaxbProjectModelAdapter(this.getJaxbProjectsModel(project.getWorkspace()), project);
	}

	private JaxbProjectsModel getJaxbProjectsModel(IWorkspace workspace) {
		return (JaxbProjectsModel) workspace.getAdapter(JaxbProjectsModel.class);
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
	 * implement {@link org.eclipse.jpt.jaxb.ui.JaxbProjectModel}.
	 * <p>
	 * <strong>NB:</strong> This model operates outside of all the other
	 * activity synchronized by the JPA project manager; but that should be OK
	 * since it will be kept synchronized with the JPA manager's collection of
	 * JPA projects in the end.
	 */
	/* CU private */ static class JaxbProjectModelAdapter
		extends ElementPropertyValueModelAdapter<JaxbProject>
		implements JaxbProjectModel
	{
		JaxbProjectModelAdapter(CollectionValueModel<JaxbProject> jpaProjectsModel, IProject project) {
			super(jpaProjectsModel, new JaxbProject.ProjectEquals(project));
		}

		public IProject getProject() {
			return ((JaxbProject.ProjectEquals) this.predicate).getCriterion();
		}
	}
}
