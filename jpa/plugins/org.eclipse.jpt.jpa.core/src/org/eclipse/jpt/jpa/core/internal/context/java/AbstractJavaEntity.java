/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.HashBag;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
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
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaTable;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
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
import org.eclipse.jpt.jpa.core.internal.resource.java.NullPrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableHolder2_0;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTablesAnnotation;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java entity
 */
public abstract class AbstractJavaEntity
	extends AbstractJavaTypeMapping<EntityAnnotation>
	implements JavaEntity, JavaCacheableHolder2_0, JavaGeneratorContainer.Owner, JavaQueryContainer.Owner
{
	protected String specifiedName;
	protected String defaultName;

	protected Entity rootEntity;
	protected final Vector<Entity> descendants = new Vector<Entity>();

	protected final JavaIdClassReference idClassReference;

	protected final JavaTable table;
	protected boolean specifiedTableIsAllowed;
	protected boolean tableIsUndefined;

	protected final Vector<JavaSecondaryTable> specifiedSecondaryTables = new Vector<JavaSecondaryTable>();
	protected final SpecifiedSecondaryTableContainerAdapter specifiedSecondaryTableContainerAdapter = new SpecifiedSecondaryTableContainerAdapter();
	protected final Table.Owner specifiedSecondaryTableOwner;

	protected final PrimaryKeyJoinColumnOwner primaryKeyJoinColumnOwner;
	protected final Vector<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns = new Vector<JavaPrimaryKeyJoinColumn>();
	protected final SpecifiedPrimaryKeyJoinColumnContainerAdapter specifiedPrimaryKeyJoinColumnContainerAdapter = new SpecifiedPrimaryKeyJoinColumnContainerAdapter();
	protected JavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	protected InheritanceType specifiedInheritanceStrategy;
	protected InheritanceType defaultInheritanceStrategy;

	protected String specifiedDiscriminatorValue;
	protected String defaultDiscriminatorValue;
	protected boolean specifiedDiscriminatorValueIsAllowed;
	protected boolean discriminatorValueIsUndefined;

	protected final JavaDiscriminatorColumn discriminatorColumn;
	protected boolean specifiedDiscriminatorColumnIsAllowed;
	protected boolean discriminatorColumnIsUndefined;

	protected final JavaAttributeOverrideContainer attributeOverrideContainer;
	protected final JavaAssociationOverrideContainer associationOverrideContainer;

	protected final JavaGeneratorContainer generatorContainer;
	protected final JavaQueryContainer queryContainer;


	// ********** construction **********

	protected AbstractJavaEntity(JavaPersistentType parent, EntityAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.specifiedName = this.mappingAnnotation.getName();
		this.idClassReference = this.buildIdClassReference();
		this.table = this.buildTable();
		this.specifiedSecondaryTableOwner = this.buildSpecifiedSecondaryTableOwner();
		this.initializeSpecifiedSecondaryTables();
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.initializeSpecifiedPrimaryKeyJoinColumns();
		this.specifiedInheritanceStrategy = this.buildSpecifiedInheritanceStrategy();
		this.specifiedDiscriminatorValue = this.getDiscriminatorValueAnnotation().getValue();
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
		this.setSpecifiedName_(this.mappingAnnotation.getName());
		this.idClassReference.synchronizeWithResourceModel();
		this.table.synchronizeWithResourceModel();
		this.syncSpecifiedSecondaryTables();
		this.syncSpecifiedPrimaryKeyJoinColumns();
		this.setSpecifiedInheritanceStrategy_(this.buildSpecifiedInheritanceStrategy());
		this.setSpecifiedDiscriminatorValue_(this.getDiscriminatorValueAnnotation().getValue());
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

		this.updateNodes(this.getSecondaryTables());

		this.updateDefaultPrimaryKeyJoinColumn();
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
		this.mappingAnnotation.setName(name);
		this.setSpecifiedName_(name);
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
		return this.getResourcePersistentType().getName();
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
		Entity root = this;
		for (TypeMapping typeMapping : this.getAncestors()) {
			if (typeMapping instanceof Entity) {
				root = (Entity) typeMapping;
			}
		}
		return root;
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
				return AbstractJavaEntity.this.entityIsDescendant(entity);
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

	public JavaIdClassReference getIdClassReference() {
		return this.idClassReference;
	}

	protected JavaIdClassReference buildIdClassReference() {
		return new GenericJavaIdClassReference(this);
	}

	public JavaPersistentType getIdClass() {
		return this.idClassReference.getIdClass();
	}


	// ********** table **********

	public JavaTable getTable() {
		return this.table;
	}

	protected JavaTable buildTable() {
		return this.getJpaFactory().buildJavaTable(this, this.buildTableOwner());  
	}

	protected JavaTable.Owner buildTableOwner() {
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

	/**
	 * <ul>
	 * <li>If the entity is part of a single table inheritance hierarchy, the table
	 * name defaults to the root entity's table name.
	 * <li>If the entity is abstract and part of a table per class
	 * inheritance hierarchy, the table name defaults to null, as no table applies.
	 * <li>Otherwise, the table name defaults to the entity name.
	 * </ul>
	 */
	public String getDefaultTableName() {
		return this.isSingleTableDescendant() ?
				this.rootEntity.getTable().getName() :
				this.isAbstractTablePerClass() ? null : this.getName();
	}

	/**
	 * @see #getDefaultTableName()
	 */
	public String getDefaultSchema() {
		return this.isSingleTableDescendant() ?
				this.rootEntity.getTable().getSchema() :
				this.isAbstractTablePerClass() ? null : this.getContextDefaultSchema();
	}

	/**
	 * @see #getDefaultTableName()
	 */
	public String getDefaultCatalog() {
		return this.isSingleTableDescendant() ?
				this.rootEntity.getTable().getCatalog() :
				this.isAbstractTablePerClass() ? null : this.getContextDefaultCatalog();
	}


	// ********** secondary tables **********

	public ListIterator<JavaSecondaryTable> secondaryTables() {
		return this.specifiedSecondaryTables();
	}

	protected ListIterable<JavaSecondaryTable> getSecondaryTables() {
		return this.getSpecifiedSecondaryTables();
	}

	public int secondaryTablesSize() {
		return this.specifiedSecondaryTablesSize();
	}


	// ********** specified secondary tables **********

	public ListIterator<JavaSecondaryTable> specifiedSecondaryTables() {
		return this.getSpecifiedSecondaryTables().iterator();
	}

	protected ListIterable<JavaSecondaryTable> getSpecifiedSecondaryTables() {
		return new LiveCloneListIterable<JavaSecondaryTable>(this.specifiedSecondaryTables);
	}

	public int specifiedSecondaryTablesSize() {
		return this.specifiedSecondaryTables.size();
	}

	public JavaSecondaryTable addSpecifiedSecondaryTable() {
		return this.addSpecifiedSecondaryTable(this.specifiedSecondaryTables.size());
	}

	public JavaSecondaryTable addSpecifiedSecondaryTable(int index) {
		SecondaryTableAnnotation annotation = this.buildSecondaryTableAnnotation(index);
		return this.addSpecifiedSecondaryTable_(index, annotation);
	}

	protected SecondaryTableAnnotation buildSecondaryTableAnnotation(int index) {
		return (SecondaryTableAnnotation) this.getResourcePersistentType().addAnnotation(index, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
	}

	public void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}

	public void removeSpecifiedSecondaryTable(int index) {
		this.getResourcePersistentType().removeAnnotation(index, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		this.removeSpecifiedSecondaryTable_(index);
	}

	protected void removeSpecifiedSecondaryTable_(int index) {
		this.removeItemFromList(index, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
	}

	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		this.getResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, SecondaryTablesAnnotation.ANNOTATION_NAME);
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
	}

	protected void initializeSpecifiedSecondaryTables() {
		for (SecondaryTableAnnotation annotation : this.getSecondaryTableAnnotations()) {
			this.specifiedSecondaryTables.add(this.buildSecondaryTable(annotation));
		}
	}

	protected JavaSecondaryTable buildSecondaryTable(SecondaryTableAnnotation secondaryTableAnnotation) {
		return this.getJpaFactory().buildJavaSecondaryTable(this, this.specifiedSecondaryTableOwner, secondaryTableAnnotation);
	}

	protected void syncSpecifiedSecondaryTables() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedSecondaryTableContainerAdapter);
	}

	protected Iterable<SecondaryTableAnnotation> getSecondaryTableAnnotations() {
		return new SubIterableWrapper<NestableAnnotation, SecondaryTableAnnotation>(
				CollectionTools.iterable(this.secondaryTableAnnotations())
			);
	}

	protected Iterator<NestableAnnotation> secondaryTableAnnotations() {
		return this.getResourceAnnotatedElement().annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
	}

	protected void moveSpecifiedSecondaryTable_(int index, JavaSecondaryTable secondaryTable) {
		this.moveItemInList(index, secondaryTable, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
	}

	protected JavaSecondaryTable addSpecifiedSecondaryTable_(int index, SecondaryTableAnnotation secondaryTableAnnotation) {
		JavaSecondaryTable secondaryTable = this.buildSecondaryTable(secondaryTableAnnotation);
		this.addItemToList(index, secondaryTable, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
		return secondaryTable;
	}

	protected void removeSpecifiedSecondaryTable_(JavaSecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable_(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}

	/**
	 * specified secondary table container adapter
	 */
	protected class SpecifiedSecondaryTableContainerAdapter
		implements ContextContainerTools.Adapter<JavaSecondaryTable, SecondaryTableAnnotation>
	{
		public Iterable<JavaSecondaryTable> getContextElements() {
			return AbstractJavaEntity.this.getSpecifiedSecondaryTables();
		}
		public Iterable<SecondaryTableAnnotation> getResourceElements() {
			return AbstractJavaEntity.this.getSecondaryTableAnnotations();
		}
		public SecondaryTableAnnotation getResourceElement(JavaSecondaryTable contextElement) {
			return contextElement.getTableAnnotation();
		}
		public void moveContextElement(int index, JavaSecondaryTable element) {
			AbstractJavaEntity.this.moveSpecifiedSecondaryTable_(index, element);
		}
		public void addContextElement(int index, SecondaryTableAnnotation resourceElement) {
			AbstractJavaEntity.this.addSpecifiedSecondaryTable_(index, resourceElement);
		}
		public void removeContextElement(JavaSecondaryTable element) {
			AbstractJavaEntity.this.removeSpecifiedSecondaryTable_(element);
		}
	}

	protected Table.Owner buildSpecifiedSecondaryTableOwner() {
		return new SecondaryTableOwner();
	}


	// ********** primary key join columns **********

	public ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumns().iterator();
	}

	protected ListIterable<JavaPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumns() : this.getDefaultPrimaryKeyJoinColumns();
	}

	public int primaryKeyJoinColumnsSize() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.defaultPrimaryKeyJoinColumnsSize();
	}


	// ********** specified primary key join columns **********

	public ListIterator<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumns().iterator();
	}

	protected ListIterable<JavaPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return new LiveCloneListIterable<JavaPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	protected boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.size() != 0;
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn() {
		return this.addSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.size());
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		PrimaryKeyJoinColumnAnnotation annotation = this.buildPrimaryKeyJoinColumnAnnotation(index);
		return this.addSpecifiedPrimaryKeyJoinColumn_(index, annotation);
	}

	protected PrimaryKeyJoinColumnAnnotation buildPrimaryKeyJoinColumnAnnotation(int index) {
		return (PrimaryKeyJoinColumnAnnotation) this.getResourcePersistentType().addAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(primaryKeyJoinColumn));
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		this.getResourcePersistentType().removeAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		this.removeSpecifiedPrimaryKeyJoinColumn_(index);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.getResourcePersistentType().moveAnnotation(targetIndex, sourceIndex, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected PrimaryKeyJoinColumnOwner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected void initializeSpecifiedPrimaryKeyJoinColumns() {
		for (PrimaryKeyJoinColumnAnnotation annotation : this.getPrimaryKeyJoinColumnAnnotations()) {
			this.specifiedPrimaryKeyJoinColumns.add(this.buildPrimaryKeyJoinColumn(annotation));
		}
	}

	protected JavaPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation primaryKeyJoinColumnAnnotation) {
		return this.getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, primaryKeyJoinColumnAnnotation);
	}

	protected void syncSpecifiedPrimaryKeyJoinColumns() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedPrimaryKeyJoinColumnContainerAdapter);
	}

	protected Iterable<PrimaryKeyJoinColumnAnnotation> getPrimaryKeyJoinColumnAnnotations() {
		return new SubIterableWrapper<NestableAnnotation, PrimaryKeyJoinColumnAnnotation>(
				CollectionTools.iterable(this.primaryKeyJoinColumnAnnotations())
			);
	}

	protected Iterator<NestableAnnotation> primaryKeyJoinColumnAnnotations() {
		return this.getResourceAnnotatedElement().annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
	}

	protected void moveSpecifiedPrimaryKeyJoinColumn_(int index, JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.moveItemInList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn_(int index, PrimaryKeyJoinColumnAnnotation primaryKeyJoinColumnAnnotation) {
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = this.buildPrimaryKeyJoinColumn(primaryKeyJoinColumnAnnotation);
		this.addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
		return primaryKeyJoinColumn;
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn_(this.specifiedPrimaryKeyJoinColumns.indexOf(primaryKeyJoinColumn));
	}

	/**
	 * specified primary key join column container adapter
	 */
	protected class SpecifiedPrimaryKeyJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation>
	{
		public Iterable<JavaPrimaryKeyJoinColumn> getContextElements() {
			return AbstractJavaEntity.this.getSpecifiedPrimaryKeyJoinColumns();
		}
		public Iterable<PrimaryKeyJoinColumnAnnotation> getResourceElements() {
			return AbstractJavaEntity.this.getPrimaryKeyJoinColumnAnnotations();
		}
		public PrimaryKeyJoinColumnAnnotation getResourceElement(JavaPrimaryKeyJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
		public void moveContextElement(int index, JavaPrimaryKeyJoinColumn element) {
			AbstractJavaEntity.this.moveSpecifiedPrimaryKeyJoinColumn_(index, element);
		}
		public void addContextElement(int index, PrimaryKeyJoinColumnAnnotation resourceElement) {
			AbstractJavaEntity.this.addSpecifiedPrimaryKeyJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(JavaPrimaryKeyJoinColumn element) {
			AbstractJavaEntity.this.removeSpecifiedPrimaryKeyJoinColumn_(element);
		}
	}


	// ********** default primary key join column **********

	public JavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}

	protected void setDefaultPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn pkJoinColumn) {
		JavaPrimaryKeyJoinColumn old = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = pkJoinColumn;
		this.firePropertyChanged(DEFAULT_PRIMARY_KEY_JOIN_COLUMN_PROPERTY, old, pkJoinColumn);
	}

	protected ListIterable<JavaPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		return (this.defaultPrimaryKeyJoinColumn != null) ?
				new SingleElementListIterable<JavaPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn) :
				EmptyListIterable.<JavaPrimaryKeyJoinColumn>instance();
	}

	protected int defaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultPrimaryKeyJoinColumn() {
		if (this.buildsDefaultPrimaryKeyJoinColumn()) {
			if (this.defaultPrimaryKeyJoinColumn == null) {
				this.setDefaultPrimaryKeyJoinColumn(this.buildDefaultPrimaryKeyJoinColumn());
			} else {
				this.defaultPrimaryKeyJoinColumn.update();
			}
		} else {
			this.setDefaultPrimaryKeyJoinColumn(null);
		}
	}

	protected boolean buildsDefaultPrimaryKeyJoinColumn() {
		return ! this.hasSpecifiedPrimaryKeyJoinColumns();
	}

	protected JavaPrimaryKeyJoinColumn buildDefaultPrimaryKeyJoinColumn() {
		return this.buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumnAnnotation(this.getResourcePersistentType()));
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
			this.getInheritanceAnnotation().setStrategy(InheritanceType.toJavaResourceModel(inheritanceType));
			this.removeInheritanceAnnotationIfUnset();
			this.setSpecifiedInheritanceStrategy_(inheritanceType);
		}
	}

	protected void setSpecifiedInheritanceStrategy_(InheritanceType inheritanceType) {
		InheritanceType old = this.specifiedInheritanceStrategy;
		this.specifiedInheritanceStrategy = inheritanceType;
		this.firePropertyChanged(SPECIFIED_INHERITANCE_STRATEGY_PROPERTY, old, inheritanceType);
	}

	protected InheritanceType buildSpecifiedInheritanceStrategy() {
		return InheritanceType.fromJavaResourceModel(this.getInheritanceAnnotation().getStrategy());
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
		return this.isRoot() ? InheritanceType.SINGLE_TABLE : this.rootEntity.getInheritanceStrategy();
	}


	// ********** inheritance annotation **********

	protected InheritanceAnnotation getInheritanceAnnotation() {
		return (InheritanceAnnotation) this.getResourcePersistentType().getNonNullAnnotation(InheritanceAnnotation.ANNOTATION_NAME);
	}

	protected void removeInheritanceAnnotationIfUnset() {
		if (this.getInheritanceAnnotation().isUnset()) {
			this.removeInheritanceAnnotation();
		}
	}

	protected void removeInheritanceAnnotation() {
		this.getResourcePersistentType().removeAnnotation(InheritanceAnnotation.ANNOTATION_NAME);
	}


	// ********** discriminator value **********

	public String getDiscriminatorValue() {
		return (this.specifiedDiscriminatorValue != null) ? this.specifiedDiscriminatorValue : this.defaultDiscriminatorValue;
	}

	public String getSpecifiedDiscriminatorValue() {
		return this.specifiedDiscriminatorValue;
	}

	public void setSpecifiedDiscriminatorValue(String discriminatorValue) {
		if (this.valuesAreDifferent(this.specifiedDiscriminatorValue, discriminatorValue)) {
			this.getDiscriminatorValueAnnotation().setValue(discriminatorValue);
			this.removeDiscriminatorValueAnnotationIfUnset();
			this.setSpecifiedDiscriminatorValue_(discriminatorValue);
		}
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


	// ********** discriminator value annotation **********

	protected DiscriminatorValueAnnotation getDiscriminatorValueAnnotation() {
		return (DiscriminatorValueAnnotation) this.getResourcePersistentType().getNonNullAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);
	}

	protected void removeDiscriminatorValueAnnotationIfUnset() {
		if (this.getDiscriminatorValueAnnotation().isUnset()) {
			this.removeDiscriminatorValueAnnotation();
		}
	}

	protected void removeDiscriminatorValueAnnotation() {
		this.getResourcePersistentType().removeAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);
	}


	// ********** discriminator column **********

	public JavaDiscriminatorColumn getDiscriminatorColumn() {
		return this.discriminatorColumn;
	}

	protected JavaDiscriminatorColumn buildDiscriminatorColumn() {
		return this.getJpaFactory().buildJavaDiscriminatorColumn(this, this.buildDiscriminatorColumnOwner());
	}

	protected JavaDiscriminatorColumn.Owner buildDiscriminatorColumnOwner() {
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

	public JavaAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}

	protected JavaAttributeOverrideContainer buildAttributeOverrideContainer() {
		return this.getJpaFactory().buildJavaAttributeOverrideContainer(this, new AttributeOverrideContainerOwner());
	}

	public TypeMapping getOverridableTypeMapping() {
		PersistentType superPersistentType = this.getPersistentType().getSuperPersistentType();
		return (superPersistentType == null) ? null : superPersistentType.getMapping();
	}


	// ********** association override container **********

	public JavaAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}

	protected JavaAssociationOverrideContainer buildAssociationOverrideContainer() {
		return this.getJpaFactory().buildJavaAssociationOverrideContainer(this, new AssociationOverrideContainerOwner());
	}

	@Override
	public Relationship resolveOverriddenRelationship(String attributeName) {
		// check for an override before looking at attribute mappings
		AssociationOverride override = this.associationOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		return (override != null) ? override.getRelationship() : super.resolveOverriddenRelationship(attributeName);
	}


	// ********** generator container **********

	public JavaGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	protected JavaGeneratorContainer buildGeneratorContainer() {
		return this.getJpaFactory().buildJavaGeneratorContainer(this, this);
	}

	public JavaResourcePersistentMember getResourceAnnotatedElement() {
		return this.getResourcePersistentType();
	}


	// ********** query container **********

	public JavaQueryContainer getQueryContainer() {
		return this.queryContainer;
	}

	protected JavaQueryContainer buildQueryContainer() {
		return this.getJpaFactory().buildJavaQueryContainer(this, this);
	}


	// ********** associated tables **********

	@Override
	public Iterator<ReadOnlyTable> associatedTables() {
		return this.getAssociatedTables().iterator();
	}

	public Iterable<ReadOnlyTable> getAssociatedTables() {
		return new CompositeIterable<ReadOnlyTable>(this.table, this.getSecondaryTables());
	}

	@Override
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

	@Override
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
			protected org.eclipse.jpt.jpa.db.Table transform(ReadOnlyTable entityTable) {
				return entityTable.getDbTable();
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


	// ********** misc **********

	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public boolean isMapped() {
		return true;
	}

	@Override
	public JavaResourcePersistentType getResourcePersistentType() {
		return super.getResourcePersistentType();
	}


	// ********** attribute mappings **********

	@Override
	public Column resolveOverriddenColumn(String attributeName) {
		// check for an override before looking at attribute mappings
		AttributeOverride override = this.attributeOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		return (override != null) ? override.getColumn() : super.resolveOverriddenColumn(attributeName);
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

	/**
	 * Return whether the entity is a part of a "table per class"
	 * inheritance hierarchy.
	 */
	protected boolean isTablePerClass() {
		return this.getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS;
	}

	/**
	 * Return whether the entity is a part of a "table per class"
	 * inheritance hierarchy.
	 */
	protected boolean isTablePerClassDescendant() {
		return this.isTablePerClass() && this.isDescendant();
	}

	/**
	 * Return whether the type is abstract.
	 */
	protected boolean isAbstract() {
		return this.getResourcePersistentType().isAbstract();
	}

	/**
	 * Return whether the entity's type is final.
	 */
	protected boolean isFinal() {
		return this.getResourcePersistentType().isFinal();
	}

	/**
	 * Return whether the entity's type is a member of another type.
	 */
	protected boolean isMember() {
		return this.getResourcePersistentType().isMemberType();
	}

	/**
	 * Return whether the entity's type is static.
	 */
	protected boolean isStatic() {
		return this.getResourcePersistentType().isStatic();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.table.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaSecondaryTable secondaryTable : CollectionTools.iterable(this.secondaryTables())) {
			result = secondaryTable.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (JavaPrimaryKeyJoinColumn pkJoinColumn : CollectionTools.iterable(this.primaryKeyJoinColumns())) {
			result = pkJoinColumn.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		result = this.attributeOverrideContainer.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.associationOverrideContainer.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.discriminatorColumn.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.generatorContainer.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);

		this.validatePrimaryKey(messages, reporter, astRoot);
		this.validateTable(messages, reporter, astRoot);
		for (JavaSecondaryTable secondaryTable : this.getSecondaryTables()) {
			secondaryTable.validate(messages, reporter, astRoot);
		}
		this.validateInheritance(messages, reporter, astRoot);
		for (JavaPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
			pkJoinColumn.validate(messages, reporter, astRoot);
		}
		this.generatorContainer.validate(messages, reporter, astRoot);
		this.queryContainer.validate(messages, reporter, astRoot);
		this.attributeOverrideContainer.validate(messages, reporter, astRoot);
		this.associationOverrideContainer.validate(messages, reporter, astRoot);
		this.validateEntityName(messages, astRoot);
		this.validateDuplicateEntityNames(messages, astRoot);
		this.idClassReference.validate(messages, reporter, astRoot);
	}

	@Override
	public boolean validatesAgainstDatabase() {
		return super.validatesAgainstDatabase() && ! this.isAbstractTablePerClass();
	}

	protected void validateEntityName(List<IMessage> messages, CompilationUnit astRoot) {
		if (StringTools.stringIsEmpty(this.getName())){
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.ENTITY_NAME_MISSING,
							new String[] {this.getPersistentType().getName()}, 
							this,
							this.getMappingAnnotation().getNameTextRange(astRoot)
					)
			);
		}
	}

	protected void validateDuplicateEntityNames(List<IMessage> messages, CompilationUnit astRoot) {
		HashBag<String>  javaEntityNamesExclOverridden = new HashBag<String>();
		CollectionTools.addAll(javaEntityNamesExclOverridden, this.getPersistenceUnit().javaEntityNamesExclOverridden());
		HashBag<String>  ormEntityNames = new HashBag<String>();
		CollectionTools.addAll(ormEntityNames, this.getPersistenceUnit().ormEntityNames());
		String  javaEntityName = this.getName(); 
		if ((javaEntityName != null) 
				// Check whether or not this entity name has duplicates among 
				// the java entities that are not overridden by orm entities
				&& ((javaEntityNamesExclOverridden.count(javaEntityName) > 1)
						//Check whether or not this entity name has duplicates 
						//with the names of orm entities
						|| (ormEntityNames.contains(javaEntityName)))) {
								messages.add(
										DefaultJpaValidationMessages.buildMessage(
												IMessage.HIGH_SEVERITY,
												JpaValidationMessages.ENTITY_NAME_DUPLICATED,
												new String[] {javaEntityName}, 
												this, 
												this.getMappingAnnotation().getNameTextRange(astRoot)
										)
								);
		}
	}

	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.buildPrimaryKeyValidator(astRoot).validate(messages, reporter);
	}

	protected JptValidator buildPrimaryKeyValidator(CompilationUnit astRoot) {
		return new GenericEntityPrimaryKeyValidator(this, this.buildTextRangeResolver(astRoot));
	}

	@Override
	protected EntityTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaEntityTextRangeResolver(this, astRoot);
	}

	protected void validateTable(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (this.isAbstractTablePerClass()) {
			if (this.table.isSpecifiedInResource()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_ABSTRACT_TABLE_PER_CLASS_DEFINES_TABLE,
						new String[] {this.getName()},
						this,
						this.table.getValidationTextRange(astRoot)
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
						this.table.getValidationTextRange(astRoot)
					)
				);
			}
			return;
		}
		this.table.validate(messages, reporter, astRoot);
	}

	/**
	 * Return whether the entity specifies a table and it is a different table
	 * than the root entity's table.
	 */
	protected boolean specifiedTableDoesNotMatchRootTable() {
		return this.table.isSpecifiedInResource() &&
			(this.table.getDbTable() != this.getRootEntity().getTable().getDbTable());
	}

	protected void validateInheritance(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.validateInheritanceStrategy(messages, astRoot);
		this.validateDiscriminatorColumn(messages, reporter, astRoot);
		this.validateDiscriminatorValue(messages, astRoot);
	}

	protected void validateDiscriminatorColumn(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (this.specifiedDiscriminatorColumnIsAllowed && ! this.discriminatorColumnIsUndefined) {
			this.discriminatorColumn.validate(messages, reporter, astRoot);
		}
		else if (this.discriminatorColumn.isResourceSpecified()) {
			if (this.isDescendant()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_NON_ROOT_DISCRIMINATOR_COLUMN_DEFINED,
						new String[] {this.getName()},
						this,
						this.getDiscriminatorColumnTextRange(astRoot)
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
						this.getDiscriminatorColumnTextRange(astRoot)
					)
				);
			}
		}
	}

	protected void validateDiscriminatorValue(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.discriminatorValueIsUndefined && (this.specifiedDiscriminatorValue != null)) {
			if (this.isAbstract()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.ENTITY_ABSTRACT_DISCRIMINATOR_VALUE_DEFINED,
						new String[] {this.getName()},
						this,
						this.getDiscriminatorValueTextRange(astRoot)
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
						this.getDiscriminatorValueTextRange(astRoot)
					)
				);
			}
		}
	}

	protected void validateInheritanceStrategy(List<IMessage> messages, CompilationUnit astRoot) {
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
						this.getInheritanceStrategyTextRange(astRoot)
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
						this.getInheritanceStrategyTextRange(astRoot)
					)
				);
			}
		}
	}

	protected TextRange getDiscriminatorValueTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getDiscriminatorValueAnnotation().getTextRange(astRoot), astRoot);
	}

	protected TextRange getDiscriminatorColumnTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.discriminatorColumn.getValidationTextRange(astRoot), astRoot);
	}

	protected TextRange getInheritanceStrategyTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getInheritanceAnnotation().getStrategyTextRange(astRoot), astRoot);
	}


	// ********** OrmOverrideContainer.Owner implementation **********

	/**
	 * some common behavior
	 */
	protected abstract class OverrideContainerOwner
		implements JavaOverrideContainer.Owner
	{
		public JavaResourcePersistentMember getResourcePersistentMember() {
			return AbstractJavaEntity.this.getResourcePersistentType();
		}

		public JavaTypeMapping getTypeMapping() {
			return AbstractJavaEntity.this;
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaEntity.this.getValidationTextRange(astRoot);
		}

		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaEntity.this.getOverridableTypeMapping();
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
			return AbstractJavaEntity.this.getPrimaryTableName();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractJavaEntity.this.tableNameIsInvalid(tableName);
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return AbstractJavaEntity.this.resolveDbTable(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return AbstractJavaEntity.this.allAssociatedTableNames();
		}

		public String getPossiblePrefix() {
			return null;
		}

		public String getWritePrefix() {
			return null;
		}

		// no maps, so all overrides are relevant
		public boolean isRelevant(String overrideName) {
			return true;
		}

	}


	// ********** JavaAttributeOverrideContainer.Owner implementation **********

	protected class AttributeOverrideContainerOwner
		extends OverrideContainerOwner
		implements JavaAttributeOverrideContainer.Owner
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
		public Column resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AttributeOverrideValidator((AttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new MappedSuperclassOverrideDescriptionProvider());
		}
		
		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AttributeOverrideColumnValidator((AttributeOverride) override, column, textRangeResolver, new EntityTableDescriptionProvider());
		}
	}


	// ********** JavaAssociationOverrideContainer.Owner implementation **********

	protected class AssociationOverrideContainerOwner
		extends OverrideContainerOwner
		implements JavaAssociationOverrideContainer.Owner
	{
		@Override
		protected Iterator<String> allOverridableNames_(TypeMapping typeMapping) {
			return typeMapping.allOverridableAssociationNames();
		}

		public Relationship resolveOverriddenRelationship(String attributeName) {
			return MappingTools.resolveOverriddenRelationship(this.getOverridableTypeMapping(), attributeName);
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


	// ********** JavaNamedColumn.Owner implementation **********

	/**
	 * some common behavior
	 */
	protected abstract class NamedColumnOwner
		implements JavaNamedColumn.Owner
	{
		public TypeMapping getTypeMapping() {
			return AbstractJavaEntity.this;
		}

		public String getDefaultTableName() {
			return AbstractJavaEntity.this.getPrimaryTableName();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return AbstractJavaEntity.this.resolveDbTable(tableName);
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaEntity.this.getValidationTextRange(astRoot);
		}
	}


	// ********** JavaBaseJoinColumn.Owner implementation **********

	protected class PrimaryKeyJoinColumnOwner
		extends NamedColumnOwner
		implements JavaBaseJoinColumn.Owner
	{
		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			Entity parentEntity = AbstractJavaEntity.this.getParentEntity();
			return (parentEntity == null) ? null : parentEntity.getPrimaryDbTable();
		}

		public int joinColumnsSize() {
			return AbstractJavaEntity.this.primaryKeyJoinColumnsSize();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return AbstractJavaEntity.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}

		public String getDefaultColumnName() {
			if (this.joinColumnsSize() != 1) {
				return null;
			}
			Entity parentEntity = AbstractJavaEntity.this.getParentEntity();
			return (parentEntity == null) ? AbstractJavaEntity.this.getPrimaryKeyColumnName() : parentEntity.getPrimaryKeyColumnName();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new EntityPrimaryKeyJoinColumnValidator((BaseJoinColumn) column, this, (BaseJoinColumnTextRangeResolver) textRangeResolver);
		}
	}


	// ********** JavaDiscriminatorColumn.Owner implementation **********

	protected class DiscriminatorColumnOwner
		extends NamedColumnOwner
		implements JavaDiscriminatorColumn.Owner
	{
		public String getDefaultColumnName() {
			return this.isDescendant() ?
					this.getRootDiscriminatorColumn().getName() :
					this.discriminatorColumnIsUndefined() ? null : DiscriminatorColumn.DEFAULT_NAME;
		}

		public int getDefaultLength() {
			return this.isDescendant() ?
					this.getRootDiscriminatorColumn().getLength() :
					this.discriminatorColumnIsUndefined() ? 0 : DiscriminatorColumn.DEFAULT_LENGTH;
		}

		public DiscriminatorType getDefaultDiscriminatorType() {
			return this.isDescendant() ?
					this.getRootDiscriminatorColumn().getDiscriminatorType() :
					this.discriminatorColumnIsUndefined() ? null : DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
		}

		protected boolean isDescendant() {
			return AbstractJavaEntity.this.isDescendant();
		}

		protected DiscriminatorColumn getRootDiscriminatorColumn() {
			return AbstractJavaEntity.this.rootEntity.getDiscriminatorColumn();
		}

		protected boolean discriminatorColumnIsUndefined() {
			return AbstractJavaEntity.this.discriminatorColumnIsUndefined;
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new DiscriminatorColumnValidator(column, textRangeResolver);
		}
	}


	// ********** table owner **********

	protected class TableOwner implements Table.Owner {
		public JptValidator buildTableValidator(Table t, TableTextRangeResolver textRangeResolver) {
			return new TableValidator(t, textRangeResolver);
		}
	}


	// ********** secondary table owner **********

	protected class SecondaryTableOwner implements Table.Owner {
		public JptValidator buildTableValidator(Table t, TableTextRangeResolver textRangeResolver) {
			return new SecondaryTableValidator((SecondaryTable) t, textRangeResolver);
		}
	}
}
