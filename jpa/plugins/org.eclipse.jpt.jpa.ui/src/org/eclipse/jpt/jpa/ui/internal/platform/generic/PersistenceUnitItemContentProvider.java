/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.filter.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceUnitItemContentProvider.ImpliedClassRefsModel;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceUnitItemContentProvider.ImpliedMappingFileRefModel;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceUnitItemContentProvider.JarFileRefsModel;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceUnitItemContentProvider.SpecifiedClassRefsModel;
import org.eclipse.jpt.jpa.ui.internal.structure.PersistenceUnitItemContentProvider.SpecifiedMappingFileRefsModel;

/**
 * Item content provider for project explorer.
 */
public class PersistenceUnitItemContentProvider
	extends AbstractItemTreeContentProvider<PersistenceUnit, JpaContextNode>
{
	public PersistenceUnitItemContentProvider(PersistenceUnit persistenceUnit, Manager manager) {
		super(persistenceUnit, manager);
	}

	public PersistenceXml getParent() {
		// skip the root 'persistence' element and return the persistence.xml file
		return this.item.getParent().getParent();
	}

	@Override
	protected CollectionValueModel<JpaContextNode> buildChildrenModel() {
		ArrayList<CollectionValueModel<? extends JpaContextNode>> list = new ArrayList<CollectionValueModel<? extends JpaContextNode>>(4);
		this.addChildrenModelsTo(list);
		return CompositeCollectionValueModel.forModels(list);
	}

	protected void addChildrenModelsTo(ArrayList<CollectionValueModel<? extends JpaContextNode>> list) {
		list.add(this.buildNotNullSpecifiedMappingFilesModel());
		list.add(this.buildImpliedMappingFilesModel());
		list.add(this.buildNotNullPersistentTypesModel());
		list.add(this.buildNotNullJarFilesModel());
	}


	// ********** specified mapping files **********

	protected CollectionValueModel<MappingFile> buildNotNullSpecifiedMappingFilesModel() {
		return new FilteringCollectionValueModel<MappingFile>(
				this.buildSpecifiedMappingFilesModel(),
				NotNullFilter.<MappingFile>instance()
			);
	}

	protected ListValueModel<MappingFile> buildSpecifiedMappingFilesModel() {
		return new TransformationListValueModel<MappingFileRef, MappingFile>(
				this.buildSpecifiedMappingFileRefsModel(),
				MappingFileRef.MAPPING_FILE_TRANSFORMER
			);
	}

	protected ListValueModel<MappingFileRef> buildSpecifiedMappingFileRefsModel() {
		return new ItemPropertyListValueModelAdapter<MappingFileRef>(
				this.buildSpecifiedMappingFileRefsModel_(),
				MappingFileRef.MAPPING_FILE_PROPERTY
			);
	}

	protected ListValueModel<MappingFileRef> buildSpecifiedMappingFileRefsModel_() {
		return new SpecifiedMappingFileRefsModel(this.item);
	}


	// ********** implied mapping file **********

	/**
	 * No need to filter this list model as it will be empty if the wrapped
	 * property model is <code>null</code>.
	 */
	protected CollectionValueModel<MappingFile> buildImpliedMappingFilesModel() {
		return new PropertyCollectionValueModelAdapter<MappingFile>(this.buildImpliedMappingFileModel());
	}

	protected PropertyValueModel<MappingFile> buildImpliedMappingFileModel() {
		return new ImpliedMappingFileModel(this.buildImpliedMappingFileRefModel());
	}

	public static class ImpliedMappingFileModel
		extends PropertyAspectAdapter<MappingFileRef, MappingFile>
	{
		public ImpliedMappingFileModel(PropertyValueModel<MappingFileRef> refModel) {
			super(refModel, MappingFileRef.MAPPING_FILE_PROPERTY);
		}
		@Override
		protected MappingFile buildValue_() {
			return this.subject.getMappingFile();
		}
	}

	protected PropertyValueModel<MappingFileRef> buildImpliedMappingFileRefModel() {
		return new ImpliedMappingFileRefModel(this.item);
	}


	// ********** persistent types **********

	protected CollectionValueModel<JavaPersistentType> buildNotNullPersistentTypesModel() {
		return new FilteringCollectionValueModel<JavaPersistentType>(
				this.buildPersistentTypesModel(),
				NotNullFilter.<JavaPersistentType>instance()
			);
	}

	protected ListValueModel<JavaPersistentType> buildPersistentTypesModel() {
		return new TransformationListValueModel<ClassRef, JavaPersistentType>(
				this.buildClassRefsModel(),
				ClassRef.JAVA_PERSISTENT_TYPE_TRANSFORMER
			);
	}

	protected ListValueModel<ClassRef> buildClassRefsModel() {
		return new ItemPropertyListValueModelAdapter<ClassRef>(
				this.buildClassRefsModel_(),
				ClassRef.JAVA_PERSISTENT_TYPE_PROPERTY
			);
	}

	protected CollectionValueModel<ClassRef> buildClassRefsModel_() {
		ArrayList<CollectionValueModel<ClassRef>> list = new ArrayList<CollectionValueModel<ClassRef>>(2);
		list.add(this.buildSpecifiedClassRefsModel());
		list.add(this.buildImpliedClassRefsModel());
		return CompositeCollectionValueModel.forModels(list);
	}

	protected CollectionValueModel<ClassRef> buildSpecifiedClassRefsModel() {
		return new ListCollectionValueModelAdapter<ClassRef>(this.buildSpecifiedClassRefsModel_());
	}

	protected ListValueModel<ClassRef> buildSpecifiedClassRefsModel_() {
		return new SpecifiedClassRefsModel(this.item);
	}

	protected CollectionValueModel<ClassRef> buildImpliedClassRefsModel() {
		return new ImpliedClassRefsModel(this.item);
	}


	// ********** jar files **********

	protected CollectionValueModel<JarFile> buildNotNullJarFilesModel() {
		return new FilteringCollectionValueModel<JarFile>(
				this.buildJarFilesModel(),
				NotNullFilter.<JarFile>instance()
			);
	}

	protected ListValueModel<JarFile> buildJarFilesModel() {
		return new TransformationListValueModel<JarFileRef, JarFile>(
				this.buildJarFileRefsModel(),
				JarFileRef.JAR_FILE_TRANSFORMER
			);
	}

	protected ListValueModel<JarFileRef> buildJarFileRefsModel() {
		return new ItemPropertyListValueModelAdapter<JarFileRef>(
				this.buildJarFileRefsModel_(),
				JarFileRef.JAR_FILE_PROPERTY
			);
	}

	protected ListValueModel<JarFileRef> buildJarFileRefsModel_() {
		return new JarFileRefsModel(this.item);
	}
}
