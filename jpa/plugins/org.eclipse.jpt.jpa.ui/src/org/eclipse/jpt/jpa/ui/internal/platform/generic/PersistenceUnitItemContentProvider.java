/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;

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
		list.add(this.buildNotNullJavaManagedTypesModel());
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

	protected PropertyValueModel<MappingFileRef> buildImpliedMappingFileRefModel() {
		return new ImpliedMappingFileRefModel(this.item);
	}


	// ********** managed types **********

	protected CollectionValueModel<JavaManagedType> buildNotNullJavaManagedTypesModel() {
		return new FilteringCollectionValueModel<JavaManagedType>(
				this.buildJavaManagedTypesModel(),
				NotNullFilter.<JavaManagedType>instance()
			);
	}

	protected ListValueModel<JavaManagedType> buildJavaManagedTypesModel() {
		return new TransformationListValueModel<ClassRef, JavaManagedType>(
				this.buildClassRefsModel(),
				ClassRef.JAVA_MANAGED_TYPE_TRANSFORMER
			);
	}

	protected ListValueModel<ClassRef> buildClassRefsModel() {
		return new ItemPropertyListValueModelAdapter<ClassRef>(
				this.buildClassRefsModel_(),
				ClassRef.JAVA_MANAGED_TYPE_PROPERTY
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

	public static class SpecifiedMappingFileRefsModel
		extends ListAspectAdapter<PersistenceUnit, MappingFileRef>
	{
		public SpecifiedMappingFileRefsModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.SPECIFIED_MAPPING_FILE_REFS_LIST, persistenceUnit);
		}
	
		@Override
		protected ListIterable<MappingFileRef> getListIterable() {
			return this.subject.getSpecifiedMappingFileRefs();
		}
	
		@Override
		public int size_() {
			return this.subject.getSpecifiedMappingFileRefsSize();
		}
	}

	public static class ImpliedMappingFileRefModel
		extends PropertyAspectAdapter<PersistenceUnit, MappingFileRef>
	{
		public ImpliedMappingFileRefModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.IMPLIED_MAPPING_FILE_REF_PROPERTY, persistenceUnit);
		}
		@Override
		protected MappingFileRef buildValue_() {
			return this.subject.getImpliedMappingFileRef();
		}
	}

	public static class SpecifiedClassRefsModel
		extends ListAspectAdapter<PersistenceUnit, ClassRef>
	{
		public SpecifiedClassRefsModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.SPECIFIED_CLASS_REFS_LIST, persistenceUnit);
		}
	
		@Override
		protected ListIterable<ClassRef> getListIterable() {
			return this.subject.getSpecifiedClassRefs();
		}
	
		@Override
		public int size_() {
			return this.subject.getSpecifiedClassRefsSize();
		}
	}

	public static class ImpliedClassRefsModel
		extends CollectionAspectAdapter<PersistenceUnit, ClassRef>
	{
		public ImpliedClassRefsModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.IMPLIED_CLASS_REFS_COLLECTION, persistenceUnit);
		}
	
		@Override
		protected Iterable<ClassRef> getIterable() {
			return this.subject.getImpliedClassRefs();
		}
	
		@Override
		public int size_() {
			return this.subject.getImpliedClassRefsSize();
		}
	}

	public static class JarFileRefsModel
		extends ListAspectAdapter<PersistenceUnit, JarFileRef>
	{
		public JarFileRefsModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.JAR_FILE_REFS_LIST, persistenceUnit);
		}
	
		@Override
		protected ListIterable<JarFileRef> getListIterable() {
			return this.subject.getJarFileRefs();
		}
	
		@Override
		public int size_() {
			return this.subject.getJarFileRefsSize();
		}
	}

}
