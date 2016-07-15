/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.base;

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemTreeContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.JpaContextRoot;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.jpt.jpa.core.context.java.JarFile;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmManagedType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.internal.structure.JpaFileStructureItemContentProviderFactory;

/**
 * This factory builds item content providers for the JPA content in the
 * Project Explorer.
 */
public abstract class AbstractNavigatorItemContentProviderFactory
	implements ItemTreeContentProvider.Factory
{
	protected AbstractNavigatorItemContentProviderFactory() {
		super();
	}

	public ItemStructuredContentProvider buildProvider(Object input, ItemStructuredContentProvider.Manager manager) {
		if (input instanceof JpaContextRoot) {
			return this.buildItemStructuredContentProvider(input, this.buildJpaContextRootChildrenModel((JpaContextRoot) input), manager);
		}
		if (input instanceof PersistenceXml) {
			return this.buildItemStructuredContentProvider(input, this.buildPersistenceXmlChildrenModel((PersistenceXml) input), manager);
		}
		if (input instanceof PersistenceUnit) {
			return this.buildItemStructuredContentProvider(input, this.buildPersistenceUnitChildrenModel((PersistenceUnit) input), manager);
		}
		if (input instanceof OrmXml) {
			return this.buildItemStructuredContentProvider(input, this.buildOrmXmlChildrenModel((OrmXml) input), manager);
		}
		if (input instanceof JpaStructureNode) {
			return this.buildItemStructuredContentProvider(input, this.buildJpaStructureNodeChildrenModel((JpaStructureNode) input), manager);
		}
		return NullItemStructuredContentProvider.instance();
	}

	protected ItemStructuredContentProvider buildItemStructuredContentProvider(Object input, CollectionValueModel<?> childrenModel, ItemStructuredContentProvider.Manager manager) {
		return new ModelItemStructuredContentProvider(input, childrenModel, manager);
	}

	public ItemTreeContentProvider buildProvider(Object item, Object parent, ItemTreeContentProvider.Manager manager) {
		if (item instanceof JpaContextRoot) {
			return this.buildItemTreeContentProvider(item, parent, this.buildJpaContextRootChildrenModel((JpaContextRoot) item), manager);
		}
		if (item instanceof PersistenceXml) {
			return this.buildItemTreeContentProvider(item, parent, this.buildPersistenceXmlChildrenModel((PersistenceXml) item), manager);
		}
		if (item instanceof PersistenceUnit) {
			return this.buildItemTreeContentProvider(item, parent, this.buildPersistenceUnitChildrenModel((PersistenceUnit) item), manager);
		}
		if (item instanceof OrmXml) {
			return this.buildItemTreeContentProvider(item, parent, this.buildOrmXmlChildrenModel((OrmXml) item), manager);
		}
		if (item instanceof JpaStructureNode) {
			return this.buildItemTreeContentProvider(item, parent, this.buildJpaStructureNodeChildrenModel((JpaStructureNode) item), manager);
		}
		return NullItemTreeContentProvider.instance();
	}

	protected ItemTreeContentProvider buildItemTreeContentProvider(Object item, Object parent, CollectionValueModel<?> childrenModel, ItemTreeContentProvider.Manager manager) {
		return new ModelItemTreeContentProvider(item, parent, childrenModel, manager);
	}


	// ********** JPA context model root **********

	protected CollectionValueModel<PersistenceXml> buildJpaContextRootChildrenModel(JpaContextRoot jpaContextRoot) {
		return new PropertyCollectionValueModelAdapter<PersistenceXml>(this.buildJpaContextRootPersistenceXmlModel(jpaContextRoot));
	}

	protected PropertyValueModel<PersistenceXml> buildJpaContextRootPersistenceXmlModel(JpaContextRoot jpaContextRoot) {
		return new JpaContextRootPersistenceXml(jpaContextRoot);
	}

	public static class JpaContextRootPersistenceXml
		extends PropertyAspectAdapterXXXX<JpaContextRoot, PersistenceXml>
	{
		public JpaContextRootPersistenceXml(JpaContextRoot jpaContextModelRoot) {
			super(JpaContextRoot.PERSISTENCE_XML_PROPERTY, jpaContextModelRoot);
		}
		@Override
		protected PersistenceXml buildValue_() {
			return this.subject.getPersistenceXml();
		}
	}


	// ********** persistence.xml **********

	/**
	 * Skip the {@link Persistence} and return the {@link PersistenceUnit}s
	 */
	protected CollectionValueModel<PersistenceUnit> buildPersistenceXmlChildrenModel(PersistenceXml persistenceXml) {
		return new ListCollectionValueModelAdapter<PersistenceUnit>(this.buildPersistenceXmlChildrenModel_(persistenceXml));
	}

	protected ListValueModel<PersistenceUnit> buildPersistenceXmlChildrenModel_(PersistenceXml persistenceXml) {
		return new PersistencePersistenceUnitsModel(this.buildPersistenceXmlPersistenceModel(persistenceXml));
	}

	protected PropertyValueModel<Persistence> buildPersistenceXmlPersistenceModel(PersistenceXml persistenceXml) {
		return new PersistenceXmlPersistenceModel(persistenceXml);
	}

	public static class PersistencePersistenceUnitsModel
		extends ListAspectAdapter<Persistence, PersistenceUnit>
	{
		PersistencePersistenceUnitsModel(PropertyValueModel<Persistence> persistenceModel) {
			super(persistenceModel, Persistence.PERSISTENCE_UNITS_LIST);
		}

		@Override
		protected ListIterable<PersistenceUnit> getListIterable() {
			return this.subject.getPersistenceUnits();
		}

		@Override
		protected int size_() {
			return this.subject.getPersistenceUnitsSize();
		}
	}

	public static class PersistenceXmlPersistenceModel
		extends PropertyAspectAdapterXXXX<PersistenceXml, Persistence>
	{
		public PersistenceXmlPersistenceModel(PersistenceXml persistenceXml) {
			super(XmlFile.ROOT_PROPERTY, persistenceXml);
		}
		@Override
		protected Persistence buildValue_() {
			return this.subject.getRoot();
		}
	}


	// ********** persistence unit **********

	protected CollectionValueModel<JpaContextModel> buildPersistenceUnitChildrenModel(PersistenceUnit persistenceUnit) {
		ArrayList<CollectionValueModel<? extends JpaContextModel>> list = new ArrayList<CollectionValueModel<? extends JpaContextModel>>(4);
		this.addPersistenceUnitChildrenModelsTo(persistenceUnit, list);
		return CompositeCollectionValueModel.forModels(list);
	}

	protected void addPersistenceUnitChildrenModelsTo(PersistenceUnit persistenceUnit, ArrayList<CollectionValueModel<? extends JpaContextModel>> list) {
		list.add(this.buildPersistenceUnitNotNullSpecifiedMappingFilesModel(persistenceUnit));
		list.add(this.buildPersistenceUnitImpliedMappingFilesModel(persistenceUnit));
		list.add(this.buildPersistenceUnitNotNullJavaManagedTypesModel(persistenceUnit));
		list.add(this.buildPersistenceUnitNotNullJarFilesModel(persistenceUnit));
	}


	// ********** persistence unit - specified mapping files **********

	protected CollectionValueModel<MappingFile> buildPersistenceUnitNotNullSpecifiedMappingFilesModel(PersistenceUnit persistenceUnit) {
		return ListValueModelTools.filter(
				this.buildPersistenceUnitSpecifiedMappingFilesModel(persistenceUnit),
				PredicateTools.<MappingFile>isNotNull()
			);
	}

	protected ListValueModel<MappingFile> buildPersistenceUnitSpecifiedMappingFilesModel(PersistenceUnit persistenceUnit) {
		return new TransformationListValueModel<MappingFileRef, MappingFile>(
				this.buildPersistenceUnitSpecifiedMappingFileRefsModel(persistenceUnit),
				MappingFileRef.MAPPING_FILE_TRANSFORMER
			);
	}

	protected ListValueModel<MappingFileRef> buildPersistenceUnitSpecifiedMappingFileRefsModel(PersistenceUnit persistenceUnit) {
		return new ItemPropertyListValueModelAdapter<MappingFileRef>(
				this.buildPersistenceUnitSpecifiedMappingFileRefsModel_(persistenceUnit),
				MappingFileRef.MAPPING_FILE_PROPERTY
			);
	}

	protected ListValueModel<MappingFileRef> buildPersistenceUnitSpecifiedMappingFileRefsModel_(PersistenceUnit persistenceUnit) {
		return new SpecifiedMappingFileRefsModel(persistenceUnit);
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


	// ********** persistence unit - implied mapping file **********

	/**
	 * No need to filter this list model as it will be empty if the wrapped
	 * property model is <code>null</code>.
	 */
	protected CollectionValueModel<MappingFile> buildPersistenceUnitImpliedMappingFilesModel(PersistenceUnit persistenceUnit) {
		return new PropertyCollectionValueModelAdapter<MappingFile>(this.buildPersistenceUnitImpliedMappingFileModel(persistenceUnit));
	}

	protected PropertyValueModel<MappingFile> buildPersistenceUnitImpliedMappingFileModel(PersistenceUnit persistenceUnit) {
		return new PersistenceUnitImpliedMappingFileModel(this.buildPersistenceUnitImpliedMappingFileRefModel(persistenceUnit));
	}

	protected PropertyValueModel<MappingFileRef> buildPersistenceUnitImpliedMappingFileRefModel(PersistenceUnit persistenceUnit) {
		return new PersistenceUnitImpliedMappingFileRefModel(persistenceUnit);
	}

	public static class PersistenceUnitImpliedMappingFileModel
		extends PropertyAspectAdapterXXXX<MappingFileRef, MappingFile>
	{
		public PersistenceUnitImpliedMappingFileModel(PropertyValueModel<MappingFileRef> refModel) {
			super(refModel, MappingFileRef.MAPPING_FILE_PROPERTY);
		}
		@Override
		protected MappingFile buildValue_() {
			return this.subject.getMappingFile();
		}
	}

	public static class PersistenceUnitImpliedMappingFileRefModel
		extends PropertyAspectAdapterXXXX<PersistenceUnit, MappingFileRef>
	{
		public PersistenceUnitImpliedMappingFileRefModel(PersistenceUnit persistenceUnit) {
			super(PersistenceUnit.IMPLIED_MAPPING_FILE_REF_PROPERTY, persistenceUnit);
		}
		@Override
		protected MappingFileRef buildValue_() {
			return this.subject.getImpliedMappingFileRef();
		}
	}


	// ********** persistence unit - managed types **********

	protected CollectionValueModel<JavaManagedType> buildPersistenceUnitNotNullJavaManagedTypesModel(PersistenceUnit persistenceUnit) {
		return ListValueModelTools.filter(
				this.buildPersistenceUnitJavaManagedTypesModel(persistenceUnit),
				PredicateTools.<JavaManagedType>isNotNull()
			);
	}

	protected ListValueModel<JavaManagedType> buildPersistenceUnitJavaManagedTypesModel(PersistenceUnit persistenceUnit) {
		return new TransformationListValueModel<ClassRef, JavaManagedType>(
				this.buildPersistenceUnitClassRefsModel(persistenceUnit),
				TransformerTools.nullCheck(ClassRef.JAVA_MANAGED_TYPE_TRANSFORMER)
			);
	}

	protected ListValueModel<ClassRef> buildPersistenceUnitClassRefsModel(PersistenceUnit persistenceUnit) {
		return new ItemPropertyListValueModelAdapter<ClassRef>(
				this.buildPersistenceUnitClassRefsModel_(persistenceUnit),
				ClassRef.JAVA_MANAGED_TYPE_PROPERTY
			);
	}

	protected CollectionValueModel<ClassRef> buildPersistenceUnitClassRefsModel_(PersistenceUnit persistenceUnit) {
		ArrayList<CollectionValueModel<ClassRef>> list = new ArrayList<CollectionValueModel<ClassRef>>(2);
		list.add(this.buildPersistenceUnitSpecifiedClassRefsModel(persistenceUnit));
		list.add(this.buildPersistenceUnitImpliedClassRefsModel(persistenceUnit));
		return CompositeCollectionValueModel.forModels(list);
	}

	protected CollectionValueModel<ClassRef> buildPersistenceUnitSpecifiedClassRefsModel(PersistenceUnit persistenceUnit) {
		return new ListCollectionValueModelAdapter<ClassRef>(this.buildPersistenceUnitSpecifiedClassRefsModel_(persistenceUnit));
	}

	protected ListValueModel<ClassRef> buildPersistenceUnitSpecifiedClassRefsModel_(PersistenceUnit persistenceUnit) {
		return new PersistenceUnitSpecifiedClassRefsModel(persistenceUnit);
	}

	protected CollectionValueModel<ClassRef> buildPersistenceUnitImpliedClassRefsModel(PersistenceUnit persistenceUnit) {
		return new PersistenceUnitImpliedClassRefsModel(persistenceUnit);
	}

	public static class PersistenceUnitSpecifiedClassRefsModel
		extends ListAspectAdapter<PersistenceUnit, ClassRef>
	{
		public PersistenceUnitSpecifiedClassRefsModel(PersistenceUnit persistenceUnit) {
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

	public static class PersistenceUnitImpliedClassRefsModel
		extends CollectionAspectAdapter<PersistenceUnit, ClassRef>
	{
		public PersistenceUnitImpliedClassRefsModel(PersistenceUnit persistenceUnit) {
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


	// ********** persistence unit - jar files **********

	protected CollectionValueModel<JarFile> buildPersistenceUnitNotNullJarFilesModel(PersistenceUnit persistenceUnit) {
		return ListValueModelTools.filter(
				this.buildPersistenceUnitJarFilesModel(persistenceUnit),
				PredicateTools.<JarFile>isNotNull()
			);
	}

	protected ListValueModel<JarFile> buildPersistenceUnitJarFilesModel(PersistenceUnit persistenceUnit) {
		return new TransformationListValueModel<JarFileRef, JarFile>(
				this.buildPersistenceUnitJarFileRefsModel(persistenceUnit),
				TransformerTools.nullCheck(JarFileRef.JAR_FILE_TRANSFORMER)
			);
	}

	protected ListValueModel<JarFileRef> buildPersistenceUnitJarFileRefsModel(PersistenceUnit persistenceUnit) {
		return new ItemPropertyListValueModelAdapter<JarFileRef>(
				this.buildPersistenceUnitJarFileRefsModel_(persistenceUnit),
				JarFileRef.JAR_FILE_PROPERTY
			);
	}

	protected ListValueModel<JarFileRef> buildPersistenceUnitJarFileRefsModel_(PersistenceUnit persistenceUnit) {
		return new PersistenceUnitJarFileRefsModel(persistenceUnit);
	}

	public static class PersistenceUnitJarFileRefsModel
		extends ListAspectAdapter<PersistenceUnit, JarFileRef>
	{
		public PersistenceUnitJarFileRefsModel(PersistenceUnit persistenceUnit) {
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


	// ********** orm.xml **********

	/**
	 * Skip the {@link EntityMappings} and return the {@link OrmManagedType}s
	 */
	protected CollectionValueModel<OrmManagedType> buildOrmXmlChildrenModel(OrmXml ormXml) {
		return new ListCollectionValueModelAdapter<OrmManagedType>(this.buildOrmXmlChildrenModel_(ormXml));
	}

	protected ListValueModel<OrmManagedType> buildOrmXmlChildrenModel_(OrmXml ormXml) {
		return new EntityMappingsManagedTypesModel(this.buildOrmXmlEntityMappingsModel(ormXml));
	}

	protected PropertyValueModel<EntityMappings> buildOrmXmlEntityMappingsModel(OrmXml ormXml) {
		return new OrmXmlEntityMappingsModel(ormXml);
	}

	public static class EntityMappingsManagedTypesModel
		extends ListAspectAdapter<EntityMappings, OrmManagedType>
	{
		public EntityMappingsManagedTypesModel(PropertyValueModel<EntityMappings> entityMappingsModel) {
			super(entityMappingsModel, EntityMappings.MANAGED_TYPES_LIST);
		}

		@Override
		protected ListIterable<OrmManagedType> getListIterable() {
			return this.subject.getManagedTypes();
		}

		@Override
		protected int size_() {
			return this.subject.getManagedTypesSize();
		}
	}

	public static class OrmXmlEntityMappingsModel
		extends PropertyAspectAdapterXXXX<OrmXml, EntityMappings>
	{
		public OrmXmlEntityMappingsModel(OrmXml ormXml) {
			super(XmlFile.ROOT_PROPERTY, ormXml);
		}
		@Override
		protected EntityMappings buildValue_() {
			return this.subject.getRoot();
		}
	}


	// ********** JPA structure node **********

	protected CollectionValueModel<JpaStructureNode> buildJpaStructureNodeChildrenModel(JpaStructureNode node) {
		return new JpaFileStructureItemContentProviderFactory.JpaStructureNodeChildrenModel(node);
	}
}
