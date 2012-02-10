/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.common.utility.internal.SimpleFilter;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ElementPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
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
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
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

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		if (adaptableObject instanceof IFile) {
			return this.getAdapter((IFile) adaptableObject, adapterType);
		}
		return null;
	}
	
	private Object getAdapter(IFile file, Class<?> adapterType) {
		if (adapterType == JpaFileModel.class) {
			return this.getJpaFileModel(file);
		}
		return null;
	}
	
	private JpaFileModel getJpaFileModel(IFile file) {
		return new JpaFileModelAdapter(this.buildJpaFilesModel(file.getProject()), file);
	}

	private CollectionValueModel<JpaFile> buildJpaFilesModel(IProject project) {
		return new JpaFilesModel(this.getJpaProjectModel(project));
	}

	private JpaProjectModel getJpaProjectModel(IProject project) {
		return (JpaProjectModel) project.getAdapter(JpaProjectModel.class);
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


	// ********** JPA file model **********

	/**
	 * Implement a property value model for the JPA file corresponding to a
	 * {@link IFile file}. The model will fire change events when the
	 * corresponding JPA file is added or removed from the JPA project
	 * or when the JPA project is created or destroyed.
	 * This is useful for UI code that does not want to wait to
	 * retrieve a JPA file but wants to be notified when it is available.
	 * <p>
	 * Subclass {@link ElementPropertyValueModelAdapter} so we can
	 * implement {@link org.eclipse.jpt.jpa.ui.JpaFileModel}.
	 * 
	 * @see JpaProjectModel
	 */
	/* CU private */ static class JpaFileModelAdapter
		extends ElementPropertyValueModelAdapter<JpaFile>
		implements JpaFileModel
	{
		JpaFileModelAdapter(CollectionValueModel<JpaFile> jpaFilesModel, IFile file) {
			super(jpaFilesModel, new Predicate(file));
		}

		public IFile getFile() {
			return ((Predicate) this.predicate).getCriterion();
		}

		/* class private */ static class Predicate
			extends SimpleFilter<JpaFile, IFile>
		{
			Predicate(IFile file) {
				super(file);
			}
			@Override
			public boolean accept(JpaFile jpaFile) {
				return jpaFile.getFile().equals(this.criterion);
			}
		}
	}
}
