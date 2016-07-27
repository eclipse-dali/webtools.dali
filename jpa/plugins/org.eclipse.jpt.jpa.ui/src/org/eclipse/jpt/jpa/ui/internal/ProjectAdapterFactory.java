/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.utility.internal.model.value.BasePluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaProjectModel;
import org.eclipse.jpt.jpa.ui.JpaProjectsModel;

/**
 * Factory to build Dali JPA adapters for an {@link IProject}:<ul>
 * <li>{@link org.eclipse.jpt.jpa.ui.JpaProjectModel JpaProjectModel} -
 *     This adapter will return a JPA project only if it is immediately
 *     available; but it will also notify listeners if the JPA project is
 *     ever created.<br>
 *     This adapter should be used by any process that can temporarily ignore
 *     any uncreated JPA projects but should be notified if the JPA project
 *     <em>is</em> ever created (e.g. UI views).
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 */
public class ProjectAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaProjectModel.class
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof IProject) {
			return this.getAdapter((IProject) adaptableObject, adapterType);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T> T getAdapter(IProject project, Class<T> adapterType) {
		if (adapterType == JpaProjectModel.class) {
			return (T) this.getJpaProjectModel(project);
		}
		return null;
	}

	private JpaProjectModel getJpaProjectModel(IProject project) {
		return new LocalJpaProjectModel(project);
	}


	// ********** JPA project model **********

	/**
	 * Implement a property value model for the JPA project corresponding to a
	 * {@link IProject project}. The model will fire change events when the
	 * corresponding JPA project is added or removed from the JPA project
	 * manager. This is useful for UI code that does not want to wait to
	 * retrieve a JPA project but wants to be notified when it is available.
	 * <p>
	 * Subclass {@link PluggablePropertyValueModel} so we can
	 * implement {@link org.eclipse.jpt.jpa.ui.JpaProjectModel}.
	 * <p>
	 * <strong>NB:</strong> This model operates outside of all the other
	 * activity synchronized by the JPA project manager; but that should be OK
	 * since it will be kept synchronized with the JPA manager's collection of
	 * JPA projects in the end.
	 */
	/* CU private */ static final class LocalJpaProjectModel
		extends BasePluggablePropertyValueModel<JpaProject, PluggablePropertyValueModel.Adapter<JpaProject>>
		implements JpaProjectModel
	{
		/**
		 * Get all the JPA projects, then filter down to those corresponding
		 * to the specified project, then get the single JPA project remaining.
		 * (At least we hope there is only a single JPA project remaining.)
		 */
		LocalJpaProjectModel(IProject project) {
			super(CollectionValueModelTools.transformationPluggablePropertyValueModelAdapterFactory(
							CollectionValueModelTools.filter(
								project.getWorkspace().getAdapter(JpaProjectsModel.class),
								new JpaProject.ProjectEquals(project)
							),
							TransformerTools.collectionSingleElementTransformer()
						)
					);
		}
	}
}
