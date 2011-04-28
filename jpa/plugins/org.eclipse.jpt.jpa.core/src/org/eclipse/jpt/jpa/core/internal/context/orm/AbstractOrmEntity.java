/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.HashBag;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.common.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation.Supported;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.DiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumn.Owner;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlySecondaryTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualSecondaryTable;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.EntityTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideInverseJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideJoinTableValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.DiscriminatorColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityPrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericEntityPrimaryKeyValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.JoinTableTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MappedSuperclassOverrideDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.SecondaryTableValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.TableValidator;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.Inheritance;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlIdClassContainer;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> entity
 */
public abstract class AbstractOrmEntity<X extends XmlEntity>
	extends AbstractOrmTypeMapping<X>
	implements OrmEntity, OrmCacheableHolder2_0, OrmIdClassReference.Owner
{
	protected String specifiedName;
	protected String defaultName;

	protected Entity rootEntity;
	protected final Vector<Entity> descendants = new Vector<Entity>();

	protected final OrmIdClassReference idClassReference;

	protected final OrmTable table;
	protected boolean specifiedTableIsAllowed;
	protected boolean tableIsUndefined;

	protected final Vector<OrmSecondaryTable> specifiedSecondaryTables = new Vector<OrmSecondaryTable>();
	protected final SpecifiedSecondaryTableContainerAdapter specifiedSecondaryTableContainerAdapter = new SpecifiedSecondaryTableContainerAdapter();

	protected final Vector<OrmVirtualSecondaryTable> virtualSecondaryTables = new Vector<OrmVirtualSecondaryTable>();
	protected final VirtualSecondaryTableContainerAdapter virtualSecondaryTableContainerAdapter = new VirtualSecondaryTableContainerAdapter();

	protected final PrimaryKeyJoinColumnOwner primaryKeyJoinColumnOwner;
	protected final Vector<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns = new Vector<OrmPrimaryKeyJoinColumn>();
	protected final SpecifiedPrimaryKeyJoinColumnContainerAdapter specifiedPrimaryKeyJoinColumnContainerAdapter = new SpecifiedPrimaryKeyJoinColumnContainerAdapter();

	// this is the default if there are Java columns
	protected final Vector<OrmVirtualPrimaryKeyJoinColumn> virtualPrimaryKeyJoinColumns = new Vector<OrmVirtualPrimaryKeyJoinColumn>();
	protected final VirtualPrimaryKeyJoinColumnContainerAdapter virtualPrimaryKeyJoinColumnContainerAdapter = new VirtualPrimaryKeyJoinColumnContainerAdapter();

	// this is the default if there are *no* Java columns
	protected ReadOnlyPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	protected InheritanceType specifiedInheritanceStrategy;
	protected InheritanceType defaultInheritanceStrategy;

	protected String specifiedDiscriminatorValue;
	protected String defaultDiscriminatorValue;
	protected boolean specifiedDiscriminatorValueIsAllowed;
	protected boolean discriminatorValueIsUndefined;

	protected final OrmDiscriminatorColumn discriminatorColumn;
	protected boolean specifiedDiscriminatorColumnIsAllowed;
	protected boolean discriminatorColumnIsUndefined;

	protected final OrmAttributeOverrideContainer attributeOverrideContainer;
	protected final OrmAssociationOverrideContainer associationOverrideContainer;

	protected final OrmGeneratorContainer generatorContainer;
	protected final OrmQueryContainer queryContainer;


	// ********** construction **********

	protected AbstractOrmEntity(OrmPersistentType parent, X xmlEntity) {
		super(parent, xmlEntity);
		this.specifiedName = xmlEntity.getName();
		this.idClassReference = this.buildIdClassReference();
		this.table = this.buildTable();
		this.initializeSpecifiedSecondaryTables();
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.initializeSpecifiedPrimaryKeyJoinColumns();
		this.specifiedInheritanceStrategy = this.buildSpecifiedInheritanceStrategy();
		this.specifiedDiscriminatorValue = xmlEntity.getDiscriminatorValue();
		this.discriminatorColumn = this.buildDiscriminatorColumn();
		// start with the entity as the root - it will be recalculated in update()
		this.rootEntity = this;
		this.attributeOverrideContainer = this.buildAttributeOverrideContainer();
		this.associationOverrideContainer = this.buildAssociationOverrideContainer();
		this.generatorContainer = this.buildGeneratorContainer();
		this.queryContainer = this.buildQueryContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();

		this.setSpecifiedName_(this.xmlTypeMapping.getName());

		this.idClassReference.synchronizeWithResourceModel();

		this.table.synchronizeWithResourceModel();

		this.syncSpecifiedSecondaryTables();

		this.syncSpecifiedPrimaryKeyJoinColumns();

		this.setSpecifiedInheritanceStrategy_(this.buildSpecifiedInheritanceStrategy());
		this.setSpecifiedDiscriminatorValue_(this.xmlTypeMapping.getDiscriminatorValue());
		this.discriminatorColumn.synchronizeWithResourceModel();

		this.attributeOverrideContainer.synchronizeWithResourceModel();
		this.associationOverrideContainer.synchronizeWithResourceModel();

		this.generatorContainer.synchronizeWithResourceModel();
		this.queryContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();

		this.setDefaultName(this.buildDefaultName());

		// calculate root entity early - other things depend on it
		this.setRootEntity(this.buildRootEntity());
		this.updateDescendants();

		this.idClassReference.update();

		this.table.update();
		this.setSpecifiedTableIsAllowed(this.buildSpecifiedTableIsAllowed());
		this.setTableIsUndefined(this.buildTableIsUndefined());

		this.updateVirtualSecondaryTables();
		this.updateNodes(this.getSecondaryTables());

		this.updateDefaultPrimaryKeyJoinColumns();
		this.updateNodes(this.getPrimaryKeyJoinColumns());

		this.setDefaultInheritanceStrategy(this.buildDefaultInheritanceStrategy());

		this.setDefaultDiscriminatorValue(this.buildDefaultDiscriminatorValue());
		this.setSpecifiedDiscriminatorValueIsAllowed(this.buildSpecifiedDiscriminatorValueIsAllowed());
		this.setDiscriminatorValueIsUndefined(this.buildDiscriminatorValueIsUndefined());

		this.discriminatorColumn.update();
		this.setSpecifiedDiscriminatorColumnIsAllowed(this.buildSpecifiedDiscriminatorColumnIsAllowed());
		this.setDiscriminatorColumnIsUndefined(this.buildDiscriminatorColumnIsUndefined());

		this.attributeOverrideContainer.update();
		this.associationOverrideContainer.update();

		this.generatorContainer.update();
		this.queryContainer.update();
	}


	// ********** name **********

	@Override
	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String name) {
		this.setSpecifiedName_(name);
		this.xmlTypeMapping.setName(name);
	}

	protected void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String name) {
		String old = this.defaultName;
		this.defaultName = name;
		this.firePropertyChanged(DEFAULT_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultName() {
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		if (javaEntity != null) {
			return javaEntity.getName();
		}
		String className = this.getClass_();
		return StringTools.stringIsEmpty(className) ? null : ClassName.getSimpleName(className);
	}


	// ********** root entity **********

	public Entity getRootEntity() {
		return this.rootEntity;
	}

	protected void setRootEntity(Entity entity) {
		Entity old = this.rootEntity;
		this.rootEntity = entity;
		this.firePropertyChanged(ROOT_ENTITY_PROPERTY, old, entity);
	}

	protected Entity buildRootEntity() {
		Entity result = this;
		for (TypeMapping typeMapping : this.getAncestors()) {
			if (typeMapping instanceof Entity) {
				result = (Entity) typeMapping;
			}
		}
		return result;
	}


	// ********** descendants **********

	public Iterable<Entity> getDescendants() {
		return new LiveCloneListIterable<Entity>(this.descendants);
	}

	protected void updateDescendants() {
		this.synchronizeCollection(this.buildDescendants(), this.descendants, DESCENDANTS_COLLECTION);
	}

	protected Iterable<Entity> buildDescendants() {
		return new FilteringIterable<Entity>(this.getPersistenceUnit().getEntities()) {
			@Override
			protected boolean accept(Entity entity) {
				return AbstractOrmEntity.this.entityIsDescendant(entity);
			}
		};
	}

	/**
	 * Return whether specified entity is a descendant of the entity.
	 */
	protected boolean entityIsDescendant(Entity entity) {
		String typeName = this.getPersistentType().getName();
		String entityTypeName = entity.getPersistentType().getName();
		String rootEntityTypeName = entity.getRootEntity().getPersistentType().getName();
		return Tools.valuesAreDifferent(typeName, entityTypeName) &&
				Tools.valuesAreEqual(typeName, rootEntityTypeName);
	}


	// ********** id class **********

	public OrmIdClassReference getIdClassReference() {
		return this.idClassReference;
	}

	protected OrmIdClassReference buildIdClassReference() {
		return new GenericOrmIdClassReference(this, this);
	}

	public XmlIdClassContainer getXmlIdClassContainer() {
		return this.getXmlTypeMapping();
	}

	public JavaIdClassReference getJavaIdClassReferenceForDefaults() {
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		return (javaEntity == null) ? null : javaEntity.getIdClassReference();
	}

	public JavaPersistentType getIdClass() {
		return this.idClassReference.getIdClass();
	}


	// ********** table **********

	public OrmTable getTable() {
		return this.table;
	}

	protected OrmTable buildTable() {
		return this.getContextNodeFactory().buildOrmTable(this, this.buildTableOwner());
	}

	protected Table.Owner buildTableOwner() {
		return new TableOwner();
	}

	public boolean specifiedTableIsAllowed() {
		return this.specifiedTableIsAllowed;
	}

	protected void setSpecifiedTableIsAllowed(boolean specifiedTableIsAllowed) {
		boolean old = this.specifiedTableIsAllowed;
		this.specifiedTableIsAllowed = specifiedTableIsAllowed;
		this.firePropertyChanged(SPECIFIED_TABLE_IS_ALLOWED_PROPERTY, old, specifiedTableIsAllowed);
	}

	protected boolean buildSpecifiedTableIsAllowed() {
		return ! this.isAbstractTablePerClass() && ! this.isSingleTableDescendant();
	}

	public boolean tableIsUndefined() {
		return this.tableIsUndefined;
	}

	protected void setTableIsUndefined(boolean tableIsUndefined) {
		boolean old = this.tableIsUndefined;
		this.tableIsUndefined = tableIsUndefined;
		this.firePropertyChanged(TABLE_IS_UNDEFINED_PROPERTY, old, tableIsUndefined);
	}

	protected boolean buildTableIsUndefined() {
		return this.isAbstractTablePerClass();
	}

	public String getDefaultTableName() {
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		if (javaEntity != null) {
			String javaName = javaEntity.getTable().getSpecifiedName();
			if ((javaName != null) && ! this.table.isSpecifiedInResource()) {
				return javaName;
			}
		}
		return this.isSingleTableDescendant() ?
				this.rootEntity.getTable().getName() :
				this.isAbstractTablePerClass() ?
						null :
						this.getName();
	}

	public String getDefaultSchema() {
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		if (javaEntity != null) {
			String javaSchema = javaEntity.getTable().getSpecifiedSchema();
			if ((javaSchema != null) && ! this.table.isSpecifiedInResource()) {
				return javaSchema;
			}
		}
		return this.isSingleTableDescendant() ?
				this.rootEntity.getTable().getSchema() :
				this.isAbstractTablePerClass() ?
						null :
						this.getContextDefaultSchema();
	}

	public String getDefaultCatalog() {
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		if (javaEntity != null) {
			String javaCatalog = javaEntity.getTable().getSpecifiedCatalog();
			if ((javaCatalog != null) && ! this.table.isSpecifiedInResource()) {
				return javaCatalog;
			}
		}
		return this.isSingleTableDescendant() ?
				this.rootEntity.getTable().getCatalog() :
				this.isAbstractTablePerClass() ?
						null :
						this.getContextDefaultCatalog();
	}

	protected static class TableOwner
		implements Table.Owner
	{
		public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
			return new TableValidator(table, textRangeResolver);
		}
	}


	// ********** secondary tables **********

	public ListIterator<ReadOnlySecondaryTable> secondaryTables() {
		return this.getSecondaryTables().iterator();
	}

	protected ListIterable<ReadOnlySecondaryTable> getSecondaryTables() {
		return this.specifiedSecondaryTables.isEmpty() ?
			this.getReadOnlyVirtualSecondaryTables() :
			this.getReadOnlySpecifiedSecondaryTables();
	}

	public int secondaryTablesSize() {
		return this.specifiedSecondaryTables.isEmpty() ?
			this.virtualSecondaryTables.size() :
			this.specifiedSecondaryTables.size();
	}


	// ********** specified secondary tables **********

	public ListIterator<OrmSecondaryTable> specifiedSecondaryTables() {
		return this.getSpecifiedSecondaryTables().iterator();
	}

	protected ListIterable<OrmSecondaryTable> getSpecifiedSecondaryTables() {
		return new LiveCloneListIterable<OrmSecondaryTable>(this.specifiedSecondaryTables);
	}

	protected ListIterable<ReadOnlySecondaryTable> getReadOnlySpecifiedSecondaryTables() {
		return new LiveCloneListIterable<ReadOnlySecondaryTable>(this.specifiedSecondaryTables);
	}

	public int specifiedSecondaryTablesSize() {
		return this.specifiedSecondaryTables.size();
	}

	public OrmSecondaryTable addSpecifiedSecondaryTable() {
		return this.addSpecifiedSecondaryTable(this.specifiedSecondaryTables.size());
	}

	/**
	 * no state check
	 */
	protected OrmSecondaryTable addSpecifiedSecondaryTable_() {
		return this.addSpecifiedSecondaryTable_(this.specifiedSecondaryTables.size());
	}

	/**
	 * @see #setSecondaryTablesAreDefinedInXml(boolean)
	 */
	public OrmSecondaryTable addSpecifiedSecondaryTable(int index) {
		if ( ! this.secondaryTablesAreDefinedInXml()) {
			throw new IllegalStateException("virtual secondary tables exist - call OrmEntity.setSecondaryTablesAreDefinedInXml(true) first"); //$NON-NLS-1$
		}
		return this.addSpecifiedSecondaryTable_(index);
	}

	/**
	 * no state check
	 */
	protected OrmSecondaryTable addSpecifiedSecondaryTable_(int index) {
		XmlSecondaryTable xmlSecondaryTable = this.buildXmlSecondaryTable();
		OrmSecondaryTable secondaryTable =  this.addSpecifiedSecondaryTable_(index, xmlSecondaryTable);
		this.xmlTypeMapping.getSecondaryTables().add(index, xmlSecondaryTable);
		return secondaryTable;
	}

	protected XmlSecondaryTable buildXmlSecondaryTable() {
		return OrmFactory.eINSTANCE.createXmlSecondaryTable();
	}

	public void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}

	public void removeSpecifiedSecondaryTable(int index) {
		this.removeSpecifiedSecondaryTable_(index);
		this.xmlTypeMapping.getSecondaryTables().remove(index);
	}

	protected void removeSpecifiedSecondaryTable_(int index) {
		this.removeItemFromList(index, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
	}

	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
		this.xmlTypeMapping.getSecondaryTables().move(targetIndex, sourceIndex);
	}

	protected void initializeSpecifiedSecondaryTables() {
		for (XmlSecondaryTable xmlTable : this.getXmlSecondaryTables()) {
			this.specifiedSecondaryTables.add(this.buildSecondaryTable(xmlTable));
		}
	}

	protected OrmSecondaryTable buildSecondaryTable(XmlSecondaryTable xmlSecondaryTable) {
		return this.getContextNodeFactory().buildOrmSecondaryTable(this, this.buildSecondaryTableOwner(), xmlSecondaryTable);
	}

	protected Table.Owner buildSecondaryTableOwner() {
		return new SecondaryTableOwner();
	}

	protected void clearSpecifiedSecondaryTables() {
		this.clearList(this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
		this.xmlTypeMapping.getSecondaryTables().clear();
	}

	protected void syncSpecifiedSecondaryTables() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedSecondaryTableContainerAdapter);
	}

	protected Iterable<XmlSecondaryTable> getXmlSecondaryTables() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlSecondaryTable>(this.xmlTypeMapping.getSecondaryTables());
	}

	protected void moveSpecifiedSecondaryTable_(int index, OrmSecondaryTable secondaryTable) {
		this.moveItemInList(index, secondaryTable, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
	}

	protected OrmSecondaryTable addSpecifiedSecondaryTable_(int index, XmlSecondaryTable xmlSecondaryTable) {
		OrmSecondaryTable secondaryTable = this.buildSecondaryTable(xmlSecondaryTable);
		this.addItemToList(index, secondaryTable, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
		return secondaryTable;
	}

	protected void removeSpecifiedSecondaryTable_(OrmSecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable_(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}

	/**
	 * specified secondary table container adapter
	 */
	protected class SpecifiedSecondaryTableContainerAdapter
		implements ContextContainerTools.Adapter<OrmSecondaryTable, XmlSecondaryTable>
	{
		public Iterable<OrmSecondaryTable> getContextElements() {
			return AbstractOrmEntity.this.getSpecifiedSecondaryTables();
		}
		public Iterable<XmlSecondaryTable> getResourceElements() {
			return AbstractOrmEntity.this.getXmlSecondaryTables();
		}
		public XmlSecondaryTable getResourceElement(OrmSecondaryTable contextElement) {
			return contextElement.getXmlTable();
		}
		public void moveContextElement(int index, OrmSecondaryTable element) {
			AbstractOrmEntity.this.moveSpecifiedSecondaryTable_(index, element);
		}
		public void addContextElement(int index, XmlSecondaryTable resourceElement) {
			AbstractOrmEntity.this.addSpecifiedSecondaryTable_(index, resourceElement);
		}
		public void removeContextElement(OrmSecondaryTable element) {
			AbstractOrmEntity.this.removeSpecifiedSecondaryTable_(element);
		}
	}

	protected static class SecondaryTableOwner
		implements Table.Owner
	{
		public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
			return new SecondaryTableValidator((SecondaryTable) table, textRangeResolver);
		}
	}


	// ********** virtual secondary tables **********

	public ListIterator<OrmVirtualSecondaryTable> virtualSecondaryTables() {
		return this.getVirtualSecondaryTables().iterator();
	}

	protected ListIterable<OrmVirtualSecondaryTable> getVirtualSecondaryTables() {
		return new LiveCloneListIterable<OrmVirtualSecondaryTable>(this.virtualSecondaryTables);
	}

	protected ListIterable<ReadOnlySecondaryTable> getReadOnlyVirtualSecondaryTables() {
		return new LiveCloneListIterable<ReadOnlySecondaryTable>(this.virtualSecondaryTables);
	}

	public int virtualSecondaryTablesSize() {
		return this.virtualSecondaryTables.size();
	}

	protected void clearVirtualSecondaryTables() {
		this.clearList(this.virtualSecondaryTables, VIRTUAL_SECONDARY_TABLES_LIST);
	}

	/**
	 * If there are any specified secondary tables, then there are no virtual
	 * secondary tables.
	 * If there are Java specified secondary tables, then those are the virtual
	 * secondary tables.
	 * @see #getJavaSecondaryTablesForVirtuals()
	 */
	protected void updateVirtualSecondaryTables() {
		ContextContainerTools.update(this.virtualSecondaryTableContainerAdapter);
	}

	protected Iterable<JavaSecondaryTable> getJavaSecondaryTablesForVirtuals() {
		if (this.specifiedSecondaryTables.size() > 0) {
			return EmptyIterable.instance();
		}
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		return (javaEntity == null) ?
				EmptyIterable.<JavaSecondaryTable>instance() :
				CollectionTools.iterable(javaEntity.secondaryTables());
	}

	protected void moveVirtualSecondaryTable(int index, OrmVirtualSecondaryTable secondaryTable) {
		this.moveItemInList(index, secondaryTable, this.virtualSecondaryTables, VIRTUAL_SECONDARY_TABLES_LIST);
	}

	protected OrmVirtualSecondaryTable addVirtualSecondaryTable(int index, JavaSecondaryTable javaSecondaryTable) {
		OrmVirtualSecondaryTable secondaryTable = this.buildVirtualSecondaryTable(javaSecondaryTable);
		this.addItemToList(index, secondaryTable, this.virtualSecondaryTables, VIRTUAL_SECONDARY_TABLES_LIST);
		return secondaryTable;
	}

	protected OrmVirtualSecondaryTable buildVirtualSecondaryTable(JavaSecondaryTable javaSecondaryTable) {
		return this.getContextNodeFactory().buildOrmVirtualSecondaryTable(this, javaSecondaryTable);
	}

	protected void removeVirtualSecondaryTable(OrmVirtualSecondaryTable secondaryTable) {
		this.removeItemFromList(secondaryTable, this.virtualSecondaryTables, VIRTUAL_SECONDARY_TABLES_LIST);
	}

	/**
	 * virtual secondary table container adapter
	 */
	protected class VirtualSecondaryTableContainerAdapter
		implements ContextContainerTools.Adapter<OrmVirtualSecondaryTable, JavaSecondaryTable>
	{
		public Iterable<OrmVirtualSecondaryTable> getContextElements() {
			return AbstractOrmEntity.this.getVirtualSecondaryTables();
		}
		public Iterable<JavaSecondaryTable> getResourceElements() {
			return AbstractOrmEntity.this.getJavaSecondaryTablesForVirtuals();
		}
		public JavaSecondaryTable getResourceElement(OrmVirtualSecondaryTable contextElement) {
			return contextElement.getOverriddenTable();
		}
		public void moveContextElement(int index, OrmVirtualSecondaryTable element) {
			AbstractOrmEntity.this.moveVirtualSecondaryTable(index, element);
		}
		public void addContextElement(int index, JavaSecondaryTable resourceElement) {
			AbstractOrmEntity.this.addVirtualSecondaryTable(index, resourceElement);
		}
		public void removeContextElement(OrmVirtualSecondaryTable element) {
			AbstractOrmEntity.this.removeVirtualSecondaryTable(element);
		}
	}


	// ********** secondary table transitions **********

	/**
	 * If the list of virtual secondary tables is empty, then either the
	 * secondary tables are defined in XML or there are no secondary tables at
	 * all (implying they are defined in XML).
	 */
	public boolean secondaryTablesAreDefinedInXml() {
		return this.virtualSecondaryTables.isEmpty();
	}

	public void setSecondaryTablesAreDefinedInXml(boolean defineInXml) {
		if (defineInXml != this.secondaryTablesAreDefinedInXml()) {
			this.setSecondaryTablesAreDefinedInXml_(defineInXml);
		}
	}

	protected void setSecondaryTablesAreDefinedInXml_(boolean defineInXml) {
		if (defineInXml) {
			this.specifySecondaryTablesInXml();
		} else {
			this.removeSecondaryTablesFromXml();
		}
	}

	/**
	 * This is used to take all the Java secondary tables and specify them in
	 * the XML. You must use {@link #setSecondaryTablesAreDefinedInXml(boolean)}
	 * before calling {@link #addSpecifiedSecondaryTable()}.
	 */
	protected void specifySecondaryTablesInXml() {
		Iterable<OrmVirtualSecondaryTable> oldVirtualSecondaryTables = new SnapshotCloneIterable<OrmVirtualSecondaryTable>(this.virtualSecondaryTables);
		for (OrmVirtualSecondaryTable oldVirtualSecondaryTable : oldVirtualSecondaryTables) {
			this.addSpecifiedSecondaryTable_().initializeFrom(oldVirtualSecondaryTable);
		}
		// the virtual secondary tables will be cleared during the update
	}

	protected void removeSecondaryTablesFromXml() {
		this.clearSpecifiedSecondaryTables();
		// the virtual secondary tables will be built during the update
	}


	// ********** primary key join columns **********

	public ListIterator<ReadOnlyPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumns().iterator();
	}

	protected ListIterable<ReadOnlyPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ?
				this.getDefaultPrimaryKeyJoinColumns() :
				this.getReadOnlySpecifiedPrimaryKeyJoinColumns();
	}

	public int primaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ?
				this.defaultPrimaryKeyJoinColumnsSize() :
				this.specifiedPrimaryKeyJoinColumnsSize();
	}

	protected OrmPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn xmlPkJoinColumn) {
		return this.getContextNodeFactory().buildOrmPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, xmlPkJoinColumn);
	}


	// ********** specified primary key join columns **********

	public ListIterator<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumns().iterator();
	}

	protected ListIterable<OrmPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return new LiveCloneListIterable<OrmPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	protected ListIterable<ReadOnlyPrimaryKeyJoinColumn> getReadOnlySpecifiedPrimaryKeyJoinColumns() {
		return new LiveCloneListIterable<ReadOnlyPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	public OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn() {
		return this.addSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.size());
	}

	public OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		this.clearDefaultPrimaryKeyJoinColumns(); // could leave for update?

		XmlPrimaryKeyJoinColumn xmlPkJoinColumn = this.buildXmlPrimaryKeyJoinColumn();
		OrmPrimaryKeyJoinColumn pkJoinColumn = this.addSpecifiedPrimaryKeyJoinColumn_(index, xmlPkJoinColumn);
		this.xmlTypeMapping.getPrimaryKeyJoinColumns().add(index, xmlPkJoinColumn);
		return pkJoinColumn;
	}

	protected XmlPrimaryKeyJoinColumn buildXmlPrimaryKeyJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn();
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(primaryKeyJoinColumn));
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		this.removeSpecifiedPrimaryKeyJoinColumn_(index);
		this.xmlTypeMapping.getPrimaryKeyJoinColumns().remove(index);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
		this.xmlTypeMapping.getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
	}

	protected PrimaryKeyJoinColumnOwner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected void initializeSpecifiedPrimaryKeyJoinColumns() {
		for (XmlPrimaryKeyJoinColumn xmlPkJoinColumn : this.getXmlPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(this.buildPrimaryKeyJoinColumn(xmlPkJoinColumn));
		}
	}

	protected void syncSpecifiedPrimaryKeyJoinColumns() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedPrimaryKeyJoinColumnContainerAdapter);
	}

	protected Iterable<XmlPrimaryKeyJoinColumn> getXmlPrimaryKeyJoinColumns() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlPrimaryKeyJoinColumn>(this.xmlTypeMapping.getPrimaryKeyJoinColumns());
	}

	protected void moveSpecifiedPrimaryKeyJoinColumn_(int index, OrmPrimaryKeyJoinColumn pkJoinColumn) {
		this.moveItemInList(index, pkJoinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn_(int index, XmlPrimaryKeyJoinColumn xmlPkJoinColumn) {
		OrmPrimaryKeyJoinColumn pkJoinColumn = this.buildPrimaryKeyJoinColumn(xmlPkJoinColumn);
		this.addItemToList(index, pkJoinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
		return pkJoinColumn;
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(OrmPrimaryKeyJoinColumn pkJoinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn_(this.specifiedPrimaryKeyJoinColumns.indexOf(pkJoinColumn));
	}

	/**
	 * specified primary key join column container adapter
	 */
	protected class SpecifiedPrimaryKeyJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmPrimaryKeyJoinColumn, XmlPrimaryKeyJoinColumn>
	{
		public Iterable<OrmPrimaryKeyJoinColumn> getContextElements() {
			return AbstractOrmEntity.this.getSpecifiedPrimaryKeyJoinColumns();
		}
		public Iterable<XmlPrimaryKeyJoinColumn> getResourceElements() {
			return AbstractOrmEntity.this.getXmlPrimaryKeyJoinColumns();
		}
		public XmlPrimaryKeyJoinColumn getResourceElement(OrmPrimaryKeyJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
		public void moveContextElement(int index, OrmPrimaryKeyJoinColumn element) {
			AbstractOrmEntity.this.moveSpecifiedPrimaryKeyJoinColumn_(index, element);
		}
		public void addContextElement(int index, XmlPrimaryKeyJoinColumn resourceElement) {
			AbstractOrmEntity.this.addSpecifiedPrimaryKeyJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(OrmPrimaryKeyJoinColumn element) {
			AbstractOrmEntity.this.removeSpecifiedPrimaryKeyJoinColumn_(element);
		}
	}


	// ********** default primary key join columns **********

	public ListIterator<ReadOnlyPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		return this.getDefaultPrimaryKeyJoinColumns().iterator();
	}

	protected ListIterable<ReadOnlyPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		int virtualSize = this.virtualPrimaryKeyJoinColumns.size();
		return (virtualSize != 0) ?
				this.getReadOnlyVirtualPrimaryKeyJoinColumns() :
				this.getReadOnlyDefaultPrimaryKeyJoinColumns();
	}

	public int defaultPrimaryKeyJoinColumnsSize() {
		int virtualSize = this.virtualPrimaryKeyJoinColumns.size();
		return (virtualSize != 0) ?
				virtualSize :
				(this.defaultPrimaryKeyJoinColumn != null) ? 1 : 0;
	}

	/**
	 * This is (blindly) called whenever a specified pk join column is added.
	 */
	protected void clearDefaultPrimaryKeyJoinColumns() {
		int virtualSize = this.virtualPrimaryKeyJoinColumns.size();
		if (virtualSize != 0) {
			this.clearVirtualPrimaryKeyJoinColumns();
		} else {
			if (this.defaultPrimaryKeyJoinColumn != null) {
				this.removeDefaultPrimaryKeyJoinColumn();
			} else {
				// nothing to clear
			}
		}
	}

	/**
	 * If there are any specified pk join columns, then there are no default
	 * pk join columns.
	 * If there are Java specified pk join columns, then those are the default
	 * pk join columns.
	 * Otherwise, there is a single, spec-defined, default pk join column.
	 */
	protected void updateDefaultPrimaryKeyJoinColumns() {
		if (this.specifiedPrimaryKeyJoinColumns.size() > 0) {
			// specified/java/default => specified
			this.clearDefaultPrimaryKeyJoinColumns();
		} else {
			// specified
			if (this.defaultPrimaryKeyJoinColumnsSize() == 0) {
				if (this.javaPrimaryKeyJoinColumnsWillBeDefaults()) {
					// specified => java
					this.initializeVirtualPrimaryKeyJoinColumns();
				} else {
					// specified => default
					this.addDefaultPrimaryKeyJoinColumn();
				}
			} else {
				// default
				if (this.defaultPrimaryKeyJoinColumn != null) {
					if (this.javaPrimaryKeyJoinColumnsWillBeDefaults()) {
						// default => java
						this.removeDefaultPrimaryKeyJoinColumn();
						this.initializeVirtualPrimaryKeyJoinColumns();
					} else {
						// default => default (no change)
					}
				// java
				} else {
					if (this.javaPrimaryKeyJoinColumnsWillBeDefaults()) {
						// java => java ("normal" update)
						this.updateVirtualPrimaryKeyJoinColumns();
					} else {
						// java => default
						this.clearVirtualPrimaryKeyJoinColumns();
						this.addDefaultPrimaryKeyJoinColumn();
					}
				}
			}
		}
	}

	protected void updateVirtualPrimaryKeyJoinColumns() {
		ContextContainerTools.update(this.virtualPrimaryKeyJoinColumnContainerAdapter);
	}

	/**
	 * Return whether we have Java pk join columns that will be used to populate
	 * our virtual pk join column collection.
	 */
	protected boolean javaPrimaryKeyJoinColumnsWillBeDefaults() {
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		return (javaEntity != null) && (javaEntity.primaryKeyJoinColumnsSize() > 0);
	}


	// ********** virtual primary key join columns **********

	protected ListIterable<OrmVirtualPrimaryKeyJoinColumn> getVirtualPrimaryKeyJoinColumns() {
		return new LiveCloneListIterable<OrmVirtualPrimaryKeyJoinColumn>(this.virtualPrimaryKeyJoinColumns);
	}

	protected ListIterable<ReadOnlyPrimaryKeyJoinColumn> getReadOnlyVirtualPrimaryKeyJoinColumns() {
		return new LiveCloneListIterable<ReadOnlyPrimaryKeyJoinColumn>(this.virtualPrimaryKeyJoinColumns);
	}

	protected void initializeVirtualPrimaryKeyJoinColumns() {
		for (JavaPrimaryKeyJoinColumn javaPkJoinColumn : this.getJavaPrimaryKeyJoinColumnsForVirtuals()) {
			this.addVirtualPrimaryKeyJoinColumn(this.buildVirtualPrimaryKeyJoinColumn(javaPkJoinColumn));
		}
	}

	protected void addVirtualPrimaryKeyJoinColumn(OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.addVirtualPrimaryKeyJoinColumn(this.virtualPrimaryKeyJoinColumns.size(), pkJoinColumn);
	}

	protected void addVirtualPrimaryKeyJoinColumn(int index, OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.addItemToList(index, pkJoinColumn, this.virtualPrimaryKeyJoinColumns, DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected void clearVirtualPrimaryKeyJoinColumns() {
		this.clearList(this.virtualPrimaryKeyJoinColumns, DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	/**
	 * This will only be called when there are Java pk join columns to return.
	 * @see #javaPrimaryKeyJoinColumnsWillBeDefaults()
	 */
	protected Iterable<JavaPrimaryKeyJoinColumn> getJavaPrimaryKeyJoinColumnsForVirtuals() {
		return CollectionTools.iterable(this.getJavaTypeMappingForDefaults().primaryKeyJoinColumns());
	}

	protected void moveVirtualPrimaryKeyJoinColumn(int index, OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.moveItemInList(index, pkJoinColumn, this.virtualPrimaryKeyJoinColumns, DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected OrmVirtualPrimaryKeyJoinColumn addVirtualPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn) {
		OrmVirtualPrimaryKeyJoinColumn pkJoinColumn = this.buildVirtualPrimaryKeyJoinColumn(javaPrimaryKeyJoinColumn);
		this.addVirtualPrimaryKeyJoinColumn(index, pkJoinColumn);
		return pkJoinColumn;
	}

	protected OrmVirtualPrimaryKeyJoinColumn buildVirtualPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn) {
		return this.getContextNodeFactory().buildOrmVirtualPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, javaPrimaryKeyJoinColumn);
	}

	protected void removeVirtualPrimaryKeyJoinColumn(OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.removeItemFromList(pkJoinColumn, this.virtualPrimaryKeyJoinColumns, DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	/**
	 * virtual primary key join column container adapter
	 */
	protected class VirtualPrimaryKeyJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmVirtualPrimaryKeyJoinColumn, JavaPrimaryKeyJoinColumn>
	{
		public Iterable<OrmVirtualPrimaryKeyJoinColumn> getContextElements() {
			return AbstractOrmEntity.this.getVirtualPrimaryKeyJoinColumns();
		}
		public Iterable<JavaPrimaryKeyJoinColumn> getResourceElements() {
			return AbstractOrmEntity.this.getJavaPrimaryKeyJoinColumnsForVirtuals();
		}
		public JavaPrimaryKeyJoinColumn getResourceElement(OrmVirtualPrimaryKeyJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
		public void moveContextElement(int index, OrmVirtualPrimaryKeyJoinColumn element) {
			AbstractOrmEntity.this.moveVirtualPrimaryKeyJoinColumn(index, element);
		}
		public void addContextElement(int index, JavaPrimaryKeyJoinColumn resourceElement) {
			AbstractOrmEntity.this.addVirtualPrimaryKeyJoinColumn(index, resourceElement);
		}
		public void removeContextElement(OrmVirtualPrimaryKeyJoinColumn element) {
			AbstractOrmEntity.this.removeVirtualPrimaryKeyJoinColumn(element);
		}
	}


	// ********** default primary key join column **********

	protected ListIterable<ReadOnlyPrimaryKeyJoinColumn> getReadOnlyDefaultPrimaryKeyJoinColumns() {
		return (this.defaultPrimaryKeyJoinColumn != null) ?
				new SingleElementListIterable<ReadOnlyPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn) :
				EmptyListIterable.<ReadOnlyPrimaryKeyJoinColumn>instance();
	}

	protected void addDefaultPrimaryKeyJoinColumn() {
		this.defaultPrimaryKeyJoinColumn = this.buildPrimaryKeyJoinColumn(null);
		this.fireItemAdded(DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST, 0, this.defaultPrimaryKeyJoinColumn);
	}

	protected void removeDefaultPrimaryKeyJoinColumn() {
		ReadOnlyPrimaryKeyJoinColumn old = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = null;
		this.fireItemRemoved(DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST, 0, old);
	}

	protected ReadOnlyPrimaryKeyJoinColumn buildDefaultPrimaryKeyJoinColumn() {
		return this.buildPrimaryKeyJoinColumn(null);
	}


	// ********** inheritance strategy **********

	public InheritanceType getInheritanceStrategy() {
		return (this.specifiedInheritanceStrategy != null) ? this.specifiedInheritanceStrategy : this.defaultInheritanceStrategy;
	}

	public InheritanceType getSpecifiedInheritanceStrategy() {
		return this.specifiedInheritanceStrategy;
	}

	public void setSpecifiedInheritanceStrategy(InheritanceType inheritanceType) {
		if (this.valuesAreDifferent(this.specifiedInheritanceStrategy, inheritanceType)) {
			Inheritance xmlInheritance = this.getXmlInheritanceForUpdate();
			this.setSpecifiedInheritanceStrategy_(inheritanceType);
			xmlInheritance.setStrategy(InheritanceType.toOrmResourceModel(inheritanceType));
			this.removeXmlInheritanceIfUnset();
		}
	}

	protected void setSpecifiedInheritanceStrategy_(InheritanceType inheritanceType) {
		InheritanceType old = this.specifiedInheritanceStrategy;
		this.specifiedInheritanceStrategy = inheritanceType;
		this.firePropertyChanged(SPECIFIED_INHERITANCE_STRATEGY_PROPERTY, old, inheritanceType);
	}

	protected InheritanceType buildSpecifiedInheritanceStrategy() {
		Inheritance xmlInheritance = this.xmlTypeMapping.getInheritance();
		return (xmlInheritance == null) ? null : InheritanceType.fromOrmResourceModel(xmlInheritance.getStrategy());
	}

	protected Inheritance getXmlInheritanceForUpdate() {
		Inheritance xmlInheritance = this.xmlTypeMapping.getInheritance();
		return (xmlInheritance != null) ? xmlInheritance : this.buildXmlInheritance();
	}

	protected Inheritance buildXmlInheritance() {
		Inheritance xmlInheritance = OrmFactory.eINSTANCE.createInheritance();
		this.xmlTypeMapping.setInheritance(xmlInheritance);
		return xmlInheritance;
	}

	protected void removeXmlInheritanceIfUnset() {
		if (this.xmlTypeMapping.getInheritance().isUnset()) {
			this.xmlTypeMapping.setInheritance(null);
		}
	}

	public InheritanceType getDefaultInheritanceStrategy() {
		return this.defaultInheritanceStrategy;
	}

	protected void setDefaultInheritanceStrategy(InheritanceType inheritanceType) {
		InheritanceType old = this.defaultInheritanceStrategy;
		this.defaultInheritanceStrategy = inheritanceType;
		this.firePropertyChanged(DEFAULT_INHERITANCE_STRATEGY_PROPERTY, old, inheritanceType);
	}

	protected InheritanceType buildDefaultInheritanceStrategy() {
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		if ((javaEntity != null) && (this.xmlTypeMapping.getInheritance() == null)) {
			return javaEntity.getInheritanceStrategy();
		}
		return this.isRoot() ?
				InheritanceType.SINGLE_TABLE :
				this.rootEntity.getInheritanceStrategy();
	}


	// ********** discriminator value **********

	public String getDiscriminatorValue() {
		return (this.specifiedDiscriminatorValue != null) ? this.specifiedDiscriminatorValue : this.defaultDiscriminatorValue;
	}

	public String getSpecifiedDiscriminatorValue() {
		return this.specifiedDiscriminatorValue;
	}

	public void setSpecifiedDiscriminatorValue(String discriminatorValue) {
		this.setSpecifiedDiscriminatorValue_(discriminatorValue);
		this.xmlTypeMapping.setDiscriminatorValue(discriminatorValue);
	}

	protected void setSpecifiedDiscriminatorValue_(String discriminatorValue) {
		String old = this.specifiedDiscriminatorValue;
		this.specifiedDiscriminatorValue = discriminatorValue;
		this.firePropertyChanged(SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY, old, discriminatorValue);
	}

	public String getDefaultDiscriminatorValue() {
		return this.defaultDiscriminatorValue;
	}

	protected void setDefaultDiscriminatorValue(String discriminatorValue) {
		String old = this.defaultDiscriminatorValue;
		this.defaultDiscriminatorValue = discriminatorValue;
		this.firePropertyChanged(DEFAULT_DISCRIMINATOR_VALUE_PROPERTY, old, discriminatorValue);
	}

	/**
	 * From the Spec:
	 * If the DiscriminatorValue annotation is not specified, a
	 * provider-specific function to generate a value representing
	 * the entity type is used for the value of the discriminator
	 * column. If the DiscriminatorType is STRING, the discriminator
	 * value default is the entity name.
	 */
	// TODO extension point for provider-specific function?
	protected String buildDefaultDiscriminatorValue() {
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		if (javaEntity != null) {
			return javaEntity.getDiscriminatorValue();
		}
		if (this.discriminatorValueIsUndefined) {
			return null;
		}
		return (this.getDiscriminatorType() == DiscriminatorType.STRING) ? this.getName() : null;
	}

	protected DiscriminatorType getDiscriminatorType() {
		return this.discriminatorColumn.getDiscriminatorType();
	}

	public boolean specifiedDiscriminatorValueIsAllowed() {
		return this.specifiedDiscriminatorValueIsAllowed;
	}

	protected void setSpecifiedDiscriminatorValueIsAllowed(boolean allowed) {
		boolean old = this.specifiedDiscriminatorValueIsAllowed;
		this.specifiedDiscriminatorValueIsAllowed = allowed;
		this.firePropertyChanged(SPECIFIED_DISCRIMINATOR_VALUE_IS_ALLOWED_PROPERTY, old, allowed);
	}

	protected boolean buildSpecifiedDiscriminatorValueIsAllowed() {
		return ! this.isTablePerClass() && ! this.isAbstract();
	}

	public boolean discriminatorValueIsUndefined() {
		return this.discriminatorValueIsUndefined;
	}

	protected void setDiscriminatorValueIsUndefined(boolean undefined) {
		boolean old = this.discriminatorValueIsUndefined;
		this.discriminatorValueIsUndefined = undefined;
		this.firePropertyChanged(DISCRIMINATOR_VALUE_IS_UNDEFINED_PROPERTY, old, undefined);
	}

	protected boolean buildDiscriminatorValueIsUndefined() {
		return this.isTablePerClass() ||
				this.isAbstract() ||
				this.isRootNoDescendantsNoStrategyDefined();
	}


	// ********** discriminator column **********

	public OrmDiscriminatorColumn getDiscriminatorColumn() {
		return this.discriminatorColumn;
	}

	protected OrmDiscriminatorColumn buildDiscriminatorColumn() {
		return this.getContextNodeFactory().buildOrmDiscriminatorColumn(this, this.buildDiscriminatorColumnOwner());
	}

	protected OrmDiscriminatorColumn.Owner buildDiscriminatorColumnOwner() {
		return new DiscriminatorColumnOwner();
	}

	public boolean specifiedDiscriminatorColumnIsAllowed() {
		return this.specifiedDiscriminatorColumnIsAllowed;
	}

	protected void setSpecifiedDiscriminatorColumnIsAllowed(boolean allowed) {
		boolean old = this.specifiedDiscriminatorColumnIsAllowed;
		this.specifiedDiscriminatorColumnIsAllowed = allowed;
		this.firePropertyChanged(SPECIFIED_DISCRIMINATOR_COLUMN_IS_ALLOWED_PROPERTY, old, allowed);
	}

	protected boolean buildSpecifiedDiscriminatorColumnIsAllowed() {
		return ! this.isTablePerClass() && this.isRoot();
	}

	public boolean discriminatorColumnIsUndefined() {
		return this.discriminatorColumnIsUndefined;
	}

	protected void setDiscriminatorColumnIsUndefined(boolean undefined) {
		boolean old = this.discriminatorColumnIsUndefined;
		this.discriminatorColumnIsUndefined = undefined;
		this.firePropertyChanged(DISCRIMINATOR_COLUMN_IS_UNDEFINED_PROPERTY, old, undefined);
	}

	protected boolean buildDiscriminatorColumnIsUndefined() {
		return this.isTablePerClass() ||
				this.isRootNoDescendantsNoStrategyDefined();
	}


	// ********** attribute override container **********

	public OrmAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}

	protected OrmAttributeOverrideContainer buildAttributeOverrideContainer() {
		return this.getContextNodeFactory().buildOrmAttributeOverrideContainer(this, new AttributeOverrideContainerOwner());
	}

	protected TypeMapping getOverridableTypeMapping() {
		PersistentType superPersistentType = this.getPersistentType().getSuperPersistentType();
		return (superPersistentType == null) ? null : superPersistentType.getMapping();
	}

	protected Column resolveOverriddenColumnForAttributeOverride(String attributeName) {
		if ( ! this.isMetadataComplete()) {
			JavaPersistentType javaType = this.getJavaPersistentType();
			if (javaType != null) {
				Column column = javaType.getMapping().resolveOverriddenColumn(attributeName);
				if (column != null) {
					return column;
				}
			}
		}
		return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
	}

	protected JavaReadOnlyAttributeOverride getJavaAttributeOverrideNamedForVirtual(String attributeName) {
		JavaEntity javaEntity = this.getJavaTypeMappingForDefaults();
		return (javaEntity == null) ? null : javaEntity.getAttributeOverrideContainer().getOverrideNamed(attributeName);
	}


	// ********** association override container **********

	public OrmAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}

	protected OrmAssociationOverrideContainer buildAssociationOverrideContainer() {
		return this.getContextNodeFactory().buildOrmAssociationOverrideContainer(this, new AssociationOverrideContainerOwner());
	}

	@Override
	public Relationship resolveOverriddenRelationship(String attributeName) {
		if (this.isJpa2_0Compatible()) {
			// strip off the first segment
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				AssociationOverride override = this.associationOverrideContainer.getSpecifiedOverrideNamed(attributeName.substring(dotIndex + 1));
				if (override != null) {
					return override.getRelationship();
				}
			}
		}
		return super.resolveOverriddenRelationship(attributeName);
	}

	protected Relationship resolveOverriddenRelationshipForAssociationOverride(String attributeName) {
		if ( ! this.isMetadataComplete()) {
			JavaPersistentType javaType = this.getJavaPersistentType();
			if (javaType != null) {
				Relationship relationship = javaType.getMapping().resolveOverriddenRelationship(attributeName);
				if (relationship != null) {
					return relationship;
				}
			}
		}
		return MappingTools.resolveOverriddenRelationship(this.getOverridableTypeMapping(), attributeName);
	}


	// ********** generator container **********

	public OrmGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	protected OrmGeneratorContainer buildGeneratorContainer() {
		return this.getContextNodeFactory().buildOrmGeneratorContainer(this, this.xmlTypeMapping);
	}


	// ********** query container **********

	public OrmQueryContainer getQueryContainer() {
		return this.queryContainer;
	}

	protected OrmQueryContainer buildQueryContainer() {
		return this.getContextNodeFactory().buildOrmQueryContainer(this, this.xmlTypeMapping);
	}


	// ********** associated tables **********

	public Iterator<ReadOnlyTable> associatedTables() {
		return this.getAssociatedTables().iterator();
	}

	public Iterable<ReadOnlyTable> getAssociatedTables() {
		return new CompositeIterable<ReadOnlyTable>(this.table, this.getSecondaryTables());
	}

	public Iterator<ReadOnlyTable> allAssociatedTables() {
		return new CompositeIterator<ReadOnlyTable>(this.allAssociatedTablesLists());
	}

	public Iterable<ReadOnlyTable> getAllAssociatedTables() {
		return CollectionTools.iterable(this.allAssociatedTables());
	}

	// TODO eliminate duplicate tables?
	protected Iterator<Iterator<ReadOnlyTable>> allAssociatedTablesLists() {
		return new TransformationIterator<TypeMapping, Iterator<ReadOnlyTable>>(this.inheritanceHierarchy(), TypeMappingTools.ASSOCIATED_TABLES_TRANSFORMER);
	}

	public Iterator<String> allAssociatedTableNames() {
		return this.getAllAssociatedTableNames().iterator();
	}

	public Iterable<String> getAllAssociatedTableNames() {
		return this.convertToNames(this.getAllAssociatedTables());
	}

	/**
	 * strip out <code>null</code> names
	 */
	protected Iterable<String> convertToNames(Iterable<ReadOnlyTable> tables) {
		return new FilteringIterable<String>(this.convertToNames_(tables), NotNullFilter.<String>instance());
	}

	protected Iterable<String> convertToNames_(Iterable<ReadOnlyTable> tables) {
		return new TransformationIterable<ReadOnlyTable, String>(tables) {
			@Override
			protected String transform(ReadOnlyTable t) {
				return t.getName();
			}
		};
	}

	public boolean tableNameIsInvalid(String tableName) {
		return ! this.tableNameIsValid(tableName);
	}

	protected boolean tableNameIsValid(String tableName) {
		return this.tableIsUndefined || CollectionTools.contains(this.getAllAssociatedTableNames(), tableName);
	}


	// ********** Java **********

	@Override
	public JavaEntity getJavaTypeMapping() {
		return (JavaEntity) super.getJavaTypeMapping();
	}

	@Override
	public JavaEntity getJavaTypeMappingForDefaults() {
		return (JavaEntity) super.getJavaTypeMappingForDefaults();
	}


	// ********** database **********

	@Override
	public String getPrimaryTableName() {
		return this.table.getName();
	}

	@Override
	public org.eclipse.jpt.jpa.db.Table getPrimaryDbTable() {
		return this.table.getDbTable();
	}

	@Override
	public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
		// matching database objects and identifiers is database platform-specific
		return this.getDataSource().selectTableForIdentifier(this.getAllAssociatedDbTables(), tableName);
	}

	/**
	 * strip out null db tables
	 */
	protected Iterable<org.eclipse.jpt.jpa.db.Table> getAllAssociatedDbTables() {
		return new FilteringIterable<org.eclipse.jpt.jpa.db.Table>(this.getAllAssociatedDbTables_(), NotNullFilter.<org.eclipse.jpt.jpa.db.Table>instance());
	}

	protected Iterable<org.eclipse.jpt.jpa.db.Table> getAllAssociatedDbTables_() {
		return new TransformationIterable<ReadOnlyTable, org.eclipse.jpt.jpa.db.Table>(this.getAllAssociatedTables()) {
			@Override
			protected org.eclipse.jpt.jpa.db.Table transform(ReadOnlyTable t) {
				return t.getDbTable();
			}
		};
	}

	@Override
	public Schema getDbSchema() {
		return this.table.getDbSchema();
	}


	// ********** primary key **********

	public String getPrimaryKeyColumnName() {
		return MappingTools.getPrimaryKeyColumnName(this);
	}

	public PersistentAttribute getIdAttribute() {
		Iterator<AttributeMapping> idAttributeMappings = this.getAllAttributeMappings(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY).iterator();
		if (idAttributeMappings.hasNext()) {
			PersistentAttribute attribute = idAttributeMappings.next().getPersistentAttribute();
			return idAttributeMappings.hasNext() ? null /*more than one*/: attribute;
		}
		return null;
	}


	// ********** key **********

	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}


	// ********** entity mappings **********

	public int getXmlSequence() {
		return 1;
	}

	public void addXmlTypeMappingTo(XmlEntityMappings entityMappings) {
		entityMappings.getEntities().add(this.xmlTypeMapping);
	}

	public void removeXmlTypeMappingFrom(XmlEntityMappings entityMappings) {
		entityMappings.getEntities().remove(this.xmlTypeMapping);
	}


	// ********** attribute mappings **********

	@Override
	public Column resolveOverriddenColumn(String attributeName) {
		if (this.isJpa2_0Compatible()) {
			// strip off the first segment
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				AttributeOverride override = this.attributeOverrideContainer.getSpecifiedOverrideNamed(attributeName.substring(dotIndex + 1));
				if (override != null) {
					return override.getColumn();
				}
			}
		}
		return super.resolveOverriddenColumn(attributeName);
	}

	@Override
	public Iterator<String> overridableAttributeNames() {
		return this.isTablePerClass() ?
				super.overridableAttributeNames() :
				EmptyIterator.<String>instance();
	}

	@Override
	public Iterator<String> overridableAssociationNames() {
		return this.isTablePerClass() ?
				super.overridableAssociationNames() :
				EmptyIterator.<String>instance();
	}

	public AttributeMapping resolveAttributeMapping(String name) {
		for (AttributeMapping attributeMapping : CollectionTools.iterable(this.allAttributeMappings())) {
			AttributeMapping resolvedMapping = attributeMapping.resolveAttributeMapping(name);
			if (resolvedMapping != null) {
				return resolvedMapping;
			}
		}
		return null;
	}


	// ********** inheritance **********

	public Entity getParentEntity() {
		for (TypeMapping typeMapping : this.getAncestors()) {
			if (typeMapping instanceof Entity) {
				return (Entity) typeMapping;
			}
		}
		return null;
	}

	/**
	 * Return whether the entity is the top of an inheritance hierarchy.
	 */
	public boolean isRoot() {
		return this == this.rootEntity;
	}

	/**
	 * Return whether the entity is a descendant in (as opposed to the root of)
	 * an inheritance hierarchy.
	 */
	protected boolean isDescendant() {
		return ! this.isRoot();
	}

	/**
	 * Return whether the entity is a descendant of the root entity
	 * of a "single table" inheritance hierarchy.
	 */
	protected boolean isSingleTableDescendant() {
		return this.isDescendant() &&
				(this.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE);
	}

	/**
	 * Return whether the entity is the top of an inheritance hierarchy
	 * and has no descendants and no specified inheritance strategy has been defined.
	 */
	protected boolean isRootNoDescendantsNoStrategyDefined() {
		return this.isRoot() &&
				this.descendants.isEmpty() &&
				(this.specifiedInheritanceStrategy == null);
	}

	/**
	 * Return whether the entity is abstract and is a part of a
	 * "table per class" inheritance hierarchy.
	 */
	protected boolean isAbstractTablePerClass() {
		return this.isAbstract() && this.isTablePerClass();
	}

	protected boolean resourceTableIsSpecified() {
		return this.table.isSpecifiedInResource() || this.javaResourceTableIsSpecified();
	}

	protected boolean javaResourceTableIsSpecified() {
		JavaEntity javaEntity = this.getJavaTypeMapping();
		return (javaEntity != null) && javaEntity.getTable().isSpecifiedInResource();
	}

	/**
	 * Return whether the entity is a part of a "table per class"
	 * inheritance hierarchy.
	 */
	protected boolean isTablePerClass() {
		return this.getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS;
	}

	/**
	 * Return whether the type is abstract; false if no java type exists.
	 */
	protected boolean isAbstract() {
		JavaResourcePersistentType jrpt = this.getJavaResourcePersistentType();
		return (jrpt != null) && jrpt.isAbstract();
	}

	/**
	 * Return whether the entity's type is abstract.
	 */
	protected boolean isFinal() {
		JavaResourcePersistentType jrpt = this.getJavaResourcePersistentType();
		return (jrpt != null) && jrpt.isFinal();
	}

	/**
	 * Return whether the entity's type is a member of another type.
	 */
	protected boolean isMember() {
		JavaResourcePersistentType jrpt = this.getJavaResourcePersistentType();
		return (jrpt != null) && jrpt.isMemberType();
	}

	/**
	 * Return whether the entity's type is static.
	 */
	protected boolean isStatic() {
		JavaResourcePersistentType jrpt = this.getJavaResourcePersistentType();
		return (jrpt != null) && jrpt.isStatic();
	}


	// ********** refactoring **********

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
					super.createRenameTypeEdits(originalType, newName),
					this.createIdClassRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createIdClassRenameTypeEdits(IType originalType, String newName) {
		return this.idClassReference.createRenameTypeEdits(originalType, newName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
					super.createMoveTypeEdits(originalType, newPackage),
					this.createIdClassMoveTypeEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createIdClassMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.idClassReference.createMoveTypeEdits(originalType, newPackage);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createIdClassRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createIdClassRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.idClassReference.createRenamePackageEdits(originalPackage, newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		this.validatePrimaryKey(messages, reporter);
		this.validateTable(messages, reporter);
		for (OrmSecondaryTable secondaryTable : this.getSpecifiedSecondaryTables()) {
			secondaryTable.validate(messages, reporter);
		}
		this.validateInheritance(messages, reporter);
		for (OrmPrimaryKeyJoinColumn pkJoinColumn : this.getSpecifiedPrimaryKeyJoinColumns()) {
			pkJoinColumn.validate(messages, reporter);
		}
		this.attributeOverrideContainer.validate(messages, reporter);
		this.associationOverrideContainer.validate(messages, reporter);
		this.generatorContainer.validate(messages, reporter);
		this.queryContainer.validate(messages, reporter);
		this.validateEntityName(messages);
		this.validateDuplicateEntityNames(messages);
		this.idClassReference.validate(messages, reporter);
	}

	protected void validateEntityName(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(this.getName())){
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.ENTITY_NAME_MISSING,
							new String[] {this.getClass_()}, 
							this,
							this.getNameTextRange()
					)
			);
		}
	}

	protected void validateDuplicateEntityNames(List<IMessage> messages) {
		HashBag<String>  javaEntityNamesExclOverridden = new HashBag<String>();
		CollectionTools.addAll(javaEntityNamesExclOverridden, this.getPersistenceUnit().javaEntityNamesExclOverridden());
		Map<String, Set<String>> map =this.getPersistenceUnit().mapEntityNameToClassNames();
		Set<String> classNames = map.get(this.getName());
		// Check whether or not this entity name has duplicates among the orm entities defined with different classes
		if (((classNames  != null) && (classNames.size() > 1)) || 
				// Check whether or not this entity name has duplicates among
				// the java entities that are not defined in the mapping files
				(javaEntityNamesExclOverridden.contains(this.getName()))) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.ENTITY_NAME_DUPLICATED,
							new String[] {this.getName()},
							this,
							this.getNameTextRange()
					)
			);
		}
	}

	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter) {
		this.buildPrimaryKeyValidator().validate(messages, reporter);
	}

	protected JptValidator buildPrimaryKeyValidator() {
		return new GenericEntityPrimaryKeyValidator(this, this.buildTextRangeResolver());
		// TODO - JPA 2.0 validation
	}

	@Override
	protected EntityTextRangeResolver buildTextRangeResolver() {
		return new OrmEntityTextRangeResolver(this);
	}

	protected void validateTable(List<IMessage> messages, IReporter reporter) {
		if (this.isAbstractTablePerClass()) {
			if (this.resourceTableIsSpecified()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_ABSTRACT_TABLE_PER_CLASS_DEFINES_TABLE,
						new String[] {this.getName()},
						this,
						this.table.getValidationTextRange()
					)
				);
			}
			return;
		}		
		if (this.isSingleTableDescendant() && this.getDataSource().connectionProfileIsActive()) {
			if (this.specifiedTableDoesNotMatchRootTable()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_SINGLE_TABLE_DESCENDANT_DEFINES_TABLE,
						new String[] {this.getName()},
						this,
						this.table.getValidationTextRange()
					)
				);
			}
			return;
		}
		this.table.validate(messages, reporter);
	}

	/**
	 * Return whether the entity specifies a table and it is a different table
	 * than the root entity's table.
	 */
	protected boolean specifiedTableDoesNotMatchRootTable() {
		return this.table.isSpecifiedInResource() &&
			(this.table.getDbTable() != this.getRootEntity().getTable().getDbTable());
	}

	protected void validateInheritance(List<IMessage> messages, IReporter reporter) {
		this.validateInheritanceStrategy(messages);
		this.validateDiscriminatorColumn(messages, reporter);
		this.validateDiscriminatorValue(messages);
	}

	protected void validateDiscriminatorColumn(List<IMessage> messages, IReporter reporter) {
		if (this.specifiedDiscriminatorColumnIsAllowed && ! this.discriminatorColumnIsUndefined) {
			this.discriminatorColumn.validate(messages, reporter);
		}
		else if (this.discriminatorColumn.isResourceSpecified()) {
			if (this.isDescendant()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_NON_ROOT_DISCRIMINATOR_COLUMN_DEFINED,
						new String[] {this.getName()},
						this,
						this.getDiscriminatorColumnTextRange()
					)
				);
			}
			else if (this.isTablePerClass()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_COLUMN_DEFINED,
						new String[] {this.getName()},
						this,
						this.getDiscriminatorColumnTextRange()
					)
				);

			}
		}
	}

	protected void validateDiscriminatorValue(List<IMessage> messages) {
		if (this.discriminatorValueIsUndefined && (this.specifiedDiscriminatorValue != null)) {
			if (this.isAbstract()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.ENTITY_ABSTRACT_DISCRIMINATOR_VALUE_DEFINED,
						new String[] {this.getName()},
						this,
						this.getDiscriminatorValueTextRange()
					)
				);
			}
			else if (this.isTablePerClass()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_VALUE_DEFINED,
						new String[] {this.getName()},
						this,
						this.getDiscriminatorValueTextRange()
					)
				);
			}
		}
	}

	protected void validateInheritanceStrategy(List<IMessage> messages) {
		Supported tablePerConcreteClassInheritanceIsSupported = this.getJpaPlatformVariation().getTablePerConcreteClassInheritanceIsSupported();
		if (tablePerConcreteClassInheritanceIsSupported == Supported.YES) {
			return;
		}
		if ((this.getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS) && this.isRoot()) {
			if (tablePerConcreteClassInheritanceIsSupported == Supported.NO) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_SUPPORTED_ON_PLATFORM,
						new String[] {this.getName()},
						this,
						this.getInheritanceStrategyTextRange()
					)
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.ENTITY_TABLE_PER_CLASS_NOT_PORTABLE_ON_PLATFORM,
						new String[] {this.getName()},
						this,
						this.getInheritanceStrategyTextRange()
					)
				);
			}
		}
	}

	protected TextRange getDiscriminatorValueTextRange() {
		return this.getValidationTextRange(this.xmlTypeMapping.getDiscriminatorValueTextRange());
	}

	protected TextRange getDiscriminatorColumnTextRange() {
		return this.getValidationTextRange(this.xmlTypeMapping.getDiscriminatorColumn().getValidationTextRange());
	}

	protected TextRange getInheritanceStrategyTextRange() {
		return this.getValidationTextRange(this.xmlTypeMapping.getInheritanceStrategyTextRange());
	}


	// ********** OrmOverrideContainer.Owner implementation **********

	/**
	 * some common behavior
	 */
	protected abstract class OverrideContainerOwner
		implements OrmOverrideContainer.Owner
	{
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmEntity.this;
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmEntity.this.getValidationTextRange();
		}

		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmEntity.this.getOverridableTypeMapping();
		}

		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? this.allOverridableNames_(typeMapping) : EmptyIterator.<String>instance();
		}

		/**
		 * pre-condition: <code>typeMapping</code> is not <code>null</code>
		 */
		protected abstract Iterator<String> allOverridableNames_(TypeMapping typeMapping);

		public String getDefaultTableName() {
			return AbstractOrmEntity.this.getPrimaryTableName();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractOrmEntity.this.tableNameIsInvalid(tableName);
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return AbstractOrmEntity.this.resolveDbTable(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return AbstractOrmEntity.this.allAssociatedTableNames();
		}
	}


	// ********** OrmAttributeOverrideContainer.Owner implementation **********

	protected class AttributeOverrideContainerOwner
		extends OverrideContainerOwner
		implements OrmAttributeOverrideContainer.Owner
	{
		@Override
		protected Iterator<String> allOverridableNames_(TypeMapping typeMapping) {
			final Collection<String> mappedByRelationshipAttributes = CollectionTools.collection(
					new TransformationIterator<SingleRelationshipMapping2_0, String>(this.getMapsIdRelationships()) {
						@Override
						protected String transform(SingleRelationshipMapping2_0 mapping) {
							return mapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getValue();
						}
					});
			return new FilteringIterator<String>(typeMapping.allOverridableAttributeNames()) {
					@Override
					protected boolean accept(String name) {
						if (mappedByRelationshipAttributes.isEmpty()) {
							return true;
						}
						// overridable names are (usually?) qualified with a container mapping,
						// which may also be the one mapped by a relationship
						int index = name.indexOf('.');
						String qualifier = (index > 0) ? name.substring(0, index) : name;
						return ! mappedByRelationshipAttributes.contains(qualifier);
					}
				};
		}

		protected Iterable<SingleRelationshipMapping2_0> getMapsIdRelationships() {
			return new FilteringIterable<SingleRelationshipMapping2_0>(this.getSingleRelationshipMappings()) {
					@Override
					protected boolean accept(SingleRelationshipMapping2_0 mapping) {
						return mapping.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy();
					}
				};
		}

		protected Iterable<SingleRelationshipMapping2_0> getSingleRelationshipMappings() {
			return new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(this.getSingleRelationshipMappings_());
		}

		@SuppressWarnings("unchecked")
		protected Iterable<AttributeMapping> getSingleRelationshipMappings_() {
			return new CompositeIterable<AttributeMapping>(
						this.getTypeMapping().getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
						this.getTypeMapping().getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)
					);
		}

		public EList<XmlAttributeOverride> getXmlOverrides() {
			return AbstractOrmEntity.this.xmlTypeMapping.getAttributeOverrides();
		}

		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AttributeOverrideValidator((AttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new MappedSuperclassOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AttributeOverrideColumnValidator((AttributeOverride) override, column, textRangeResolver, new EntityTableDescriptionProvider());
		}

		public Column resolveOverriddenColumn(String attributeName) {
			return AbstractOrmEntity.this.resolveOverriddenColumnForAttributeOverride(attributeName);
		}
	}


	// ********** OrmAssociationOverrideContainer.Owner implementation **********

	protected class AssociationOverrideContainerOwner
		extends OverrideContainerOwner
		implements OrmAssociationOverrideContainer.Owner
	{
		@Override
		protected Iterator<String> allOverridableNames_(TypeMapping typeMapping) {
			return typeMapping.allOverridableAssociationNames();
		}

		public EList<XmlAssociationOverride> getXmlOverrides() {
			return AbstractOrmEntity.this.xmlTypeMapping.getAssociationOverrides();
		}

		public Relationship resolveOverriddenRelationship(String attributeName) {
			return AbstractOrmEntity.this.resolveOverriddenRelationshipForAssociationOverride(attributeName);
		}

		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AssociationOverrideValidator((AssociationOverride) override, (AssociationOverrideContainer) container, textRangeResolver, new MappedSuperclassOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinColumnValidator((AssociationOverride) override, (JoinColumn) column, (JoinColumn.Owner) owner, (JoinColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
		}

		public JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinColumnValidator(override, column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
		}

		public JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideInverseJoinColumnValidator(override, column, owner, textRangeResolver, new JoinTableTableDescriptionProvider());
		}

		public JptValidator buildTableValidator(AssociationOverride override, Table t, TableTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinTableValidator(override, (JoinTable) t, textRangeResolver);
		}
	}


	// ********** OrmNamedColumn.Owner implementation **********

	/**
	 * some common behavior
	 */
	protected abstract class NamedColumnOwner
		implements OrmNamedColumn.Owner
	{
		public TypeMapping getTypeMapping() {
			return AbstractOrmEntity.this;
		}

		public String getDefaultTableName() {
			return AbstractOrmEntity.this.getPrimaryTableName();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return AbstractOrmEntity.this.resolveDbTable(tableName);
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmEntity.this.getValidationTextRange();
		}
	}


	// ********** OrmBaseJoinColumn.Owner implementation **********

	protected class PrimaryKeyJoinColumnOwner
		extends NamedColumnOwner
		implements OrmBaseJoinColumn.Owner
	{
		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			Entity parentEntity = AbstractOrmEntity.this.getParentEntity();
			return (parentEntity == null) ? null : parentEntity.getPrimaryDbTable();
		}

		public int joinColumnsSize() {
			return AbstractOrmEntity.this.primaryKeyJoinColumnsSize();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return CollectionTools.contains(AbstractOrmEntity.this.defaultPrimaryKeyJoinColumns(), joinColumn);
		}

		public String getDefaultColumnName() {
			if (this.joinColumnsSize() != 1) {
				return null;
			}
			Entity parentEntity = AbstractOrmEntity.this.getParentEntity();
			return (parentEntity == null) ? AbstractOrmEntity.this.getPrimaryKeyColumnName() : parentEntity.getPrimaryKeyColumnName();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new EntityPrimaryKeyJoinColumnValidator((BaseJoinColumn) column, this, (BaseJoinColumnTextRangeResolver) textRangeResolver);
		}
	}


	// ********** OrmDiscriminatorColumn.Owner implementation **********

	protected class DiscriminatorColumnOwner
		extends NamedColumnOwner
		implements OrmDiscriminatorColumn.Owner
	{
		public String getDefaultColumnName() {
			if (this.getXmlColumn() == null) {
				JavaEntity javaEntity = this.getJavaEntityForDefaults();
				if (javaEntity != null) {
					String name = javaEntity.getDiscriminatorColumn().getSpecifiedName();
					if (name != null) {
						return name;
					}
				}
			}
			return AbstractOrmEntity.this.isDescendant() ?
					this.getRootDiscriminatorColumn().getName() :
					this.isTablePerClass() ? null : DiscriminatorColumn.DEFAULT_NAME;
		}

		public int getDefaultLength() {
			if (this.getXmlColumn() == null) {
				JavaEntity javaEntity = this.getJavaEntityForDefaults();
				if (javaEntity != null) {
					Integer length = javaEntity.getDiscriminatorColumn().getSpecifiedLength();
					if (length != null) {
						return length.intValue();
					}
				}
			}
			return AbstractOrmEntity.this.isDescendant() ?
					this.getRootDiscriminatorColumn().getLength() :
					this.isTablePerClass() ? 0 : DiscriminatorColumn.DEFAULT_LENGTH;
		}

		public DiscriminatorType getDefaultDiscriminatorType() {
			if (this.getXmlColumn() == null) {
				JavaEntity javaEntity = this.getJavaEntityForDefaults();
				if (javaEntity != null) {
					DiscriminatorType dt = javaEntity.getDiscriminatorColumn().getSpecifiedDiscriminatorType();
					if (dt != null) {
						return dt;
					}
				}
			}
			return AbstractOrmEntity.this.isDescendant() ?
					this.getRootDiscriminatorColumn().getDiscriminatorType() :
					this.isTablePerClass() ? null : DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new DiscriminatorColumnValidator(column, textRangeResolver);
		}

		public XmlDiscriminatorColumn getXmlColumn() {
			return this.getXmlEntity().getDiscriminatorColumn();
		}

		public XmlDiscriminatorColumn buildXmlColumn() {
			XmlDiscriminatorColumn xmlColumn = OrmFactory.eINSTANCE.createXmlDiscriminatorColumn();
			this.getXmlEntity().setDiscriminatorColumn(xmlColumn);
			return xmlColumn;
		}

		public void removeXmlColumn() {
			this.getXmlEntity().setDiscriminatorColumn(null);
		}

		protected XmlEntity getXmlEntity() {
			return AbstractOrmEntity.this.getXmlTypeMapping();
		}

		protected DiscriminatorColumn getRootDiscriminatorColumn() {
			return AbstractOrmEntity.this.rootEntity.getDiscriminatorColumn();
		}

		protected boolean isMetadataComplete() {
			return AbstractOrmEntity.this.isMetadataComplete();
		}

		protected boolean isTablePerClass() {
			return AbstractOrmEntity.this.isTablePerClass();
		}

		protected JavaEntity getJavaEntityForDefaults() {
			return AbstractOrmEntity.this.getJavaTypeMappingForDefaults();
		}
	}

}
