/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.utility.internal.model.value.BasePluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaFileModel;
import org.eclipse.jpt.jpa.ui.JpaProjectModel;

/**
 * Factory to build Dali adapters for an {@link IFile}
 * (The comments for the adapters produced by {@link ProjectAdapterFactory}
 * are applicable here also):<ul>
 * <li>{@link org.eclipse.jpt.jpa.ui.JpaFileModel JpaFileModel}
 * </ul>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * 
 * @see ProjectAdapterFactory
 */
public class FileAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] {
			JpaFileModel.class,
		};

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof IFile) {
			return this.getAdapter((IFile) adaptableObject, adapterType);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getAdapter(IFile file, Class<T> adapterType) {
		if (adapterType == JpaFileModel.class) {
			return (T) this.getJpaFileModel(file);
		}
		return null;
	}
	
	private JpaFileModel getJpaFileModel(IFile file) {
		return new LocalJpaFileModel(file);
	}


	// ********** JPA file model **********

	/**
	 * Implement a property value model for the JPA file corresponding to a
	 * {@link IFile file}. The model will fire change events when the
	 * corresponding JPA file is added or removed from the JPA project
	 * or when the JPA project is created or destroyed.
	 * This is useful for UI code that does not want to wait to
	 * retrieve a JPA file but wants to be notified when it is available.
	 * <p>
	 * Subclass {@link PluggablePropertyValueModel} so we can
	 * implement {@link org.eclipse.jpt.jpa.ui.JpaFileModel}.
	 * 
	 * @see JpaProjectModel
	 */
	/* CU private */ static final class LocalJpaFileModel
		extends BasePluggablePropertyValueModel<JpaFile, PluggablePropertyValueModel.Adapter<JpaFile>>
		implements JpaFileModel
	{
		/**
		 * Get the JPA project corresponding to the file's project,
		 * then get that JPA project's JPA files, then filter down to those corresponding
		 * to the specified file, then get the single JPA file remaining.
		 * (At least we hope there is only a single JPA file remaining.)
		 */
		LocalJpaFileModel(IFile file) {
			super(CollectionValueModelTools.transformationPluggablePropertyValueModelAdapterFactory(
							CollectionValueModelTools.filter(
								new JpaFilesModel(file.getProject().getAdapter(JpaProjectModel.class)),
								new JpaFile.FileEquals(file)
							),
							TransformerTools.collectionSingleElementTransformer()
						)
					);
		}
	}


	// ********** JPA files model **********

	/**
	 * Adapt a JPA project's JPA files collection to the collection value model
	 * interface.
	 */
	/* CU private */ static class JpaFilesModel
		extends CollectionAspectAdapter<JpaProject, JpaFile>
	{
		JpaFilesModel(PropertyValueModel<JpaProject> jpaProjectModel) {
			super(jpaProjectModel, JpaProject.JPA_FILES_COLLECTION);
		}

		@Override
		protected Iterable<JpaFile> getIterable() {
			return this.subject.getJpaFiles();
		}

		@Override
		protected int size_() {
			return this.subject.getJpaFilesSize();
		}
	}
}
