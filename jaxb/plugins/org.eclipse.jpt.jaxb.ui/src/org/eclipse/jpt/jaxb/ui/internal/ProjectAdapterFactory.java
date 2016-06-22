/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPluggablePropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
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

	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof IProject) {
			return this.getAdapter((IProject) adaptableObject, adapterType);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T> T getAdapter(IProject project, Class<T> adapterType) {
		if (adapterType == JaxbProjectModel.class) {
			return (T) this.getJaxbProjectModel(project);
		}
		return null;
	}

	private JaxbProjectModel getJaxbProjectModel(IProject project) {
		return new LocalJaxbProjectModel(project);
	}


	// ********** JAXB project model **********

	/**
	 * Implement a property value model for the JAXB project corresponding to a
	 * {@link IProject project}. The model will fire change events when the
	 * corresponding JAXB project is added or removed from the JAXB project
	 * manager. This is useful for UI code that does not want to wait to
	 * retrieve a JAXB project but wants to be notified when it is available.
	 * <p>
	 * Subclass {@link PluggablePropertyValueModel} so we can
	 * implement {@link org.eclipse.jpt.jaxb.ui.JaxbProjectModel}.
	 * <p>
	 * <strong>NB:</strong> This model operates outside of all the other
	 * activity synchronized by the JAXB project manager; but that should be OK
	 * since it will be kept synchronized with the JAXB manager's collection of
	 * JAXB projects in the end.
	 */
	/* CU private */ static class LocalJaxbProjectModel
		extends PluggablePropertyValueModel<JaxbProject>
		implements JaxbProjectModel
	{
		/**
		 * Get all the JAXB projects, then filter down to those corresponding
		 * to the specified project, then get the single JAXB project remaining.
		 * (At least we hope there is only a single JAXB project remaining.)
		 */
		LocalJaxbProjectModel(IProject project) {
			super(new CollectionPluggablePropertyValueModelAdapter.Factory<>(
							CollectionValueModelTools.filter(
									project.getWorkspace().getAdapter(JaxbProjectsModel.class),
									new JaxbProject.ProjectEquals(project)
							),
							TransformerTools.collectionSingleElementTransformer()
						)
					);
		}
	}
}
