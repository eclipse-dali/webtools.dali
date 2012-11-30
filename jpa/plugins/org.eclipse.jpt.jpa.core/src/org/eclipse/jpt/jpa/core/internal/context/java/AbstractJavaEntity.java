/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.filter.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation.Supported;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.DiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlySecondaryTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaTable;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
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
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAttributeOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java entity
 */
public abstract class AbstractJavaEntity
	extends AbstractJavaTypeMapping<EntityAnnotation>
	implements JavaEntity, JavaCacheableHolder2_0, JavaGeneratorContainer.ParentAdapter, JavaQueryContainer.Owner
{
	protected String specifiedName;
	protected String defaultName;

	protected Entity rootEntity;
	protected final Vector<Entity> descendants = new Vector<Entity>();

	protected final JavaIdClassReference idClassReference;

	protected final JavaTable table;
	protected boolean specifiedTableIsAllowed;
	protected boolean tableIsUndefined;

	protected final ContextListContainer<JavaSecondaryTable, SecondaryTableAnnotation> specifiedSecondaryTableContainer;
	protected final Table.Owner specifiedSecondaryTableOwner;

	protected final PrimaryKeyJoinColumnOwner primaryKeyJoinColumnOwner;
	protected final ContextListContainer<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> specifiedPrimaryKeyJoinColumnContainer;
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
		// start with the entity as the root - it will be recalculated in update()
		this.rootEntity = this;
		this.specifiedSecondaryTableOwner = this.buildSpecifiedSecondaryTableOwner();
		this.specifiedSecondaryTableContainer = this.buildSpecifiedSecondaryTableContainer();
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.specifiedPrimaryKeyJoinColumnContainer = this.buildSpecifiedPrimaryKeyJoinColumnContainer();
		this.specifiedInheritanceStrategy = this.buildSpecifiedInheritanceStrategy();
		this.specifiedDiscriminatorValue = this.getDiscriminatorValueAnnotation().getValue();
		this.discriminatorColumn = this.buildDiscriminatorColumn();
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

		this.setDefaultInheritanceStrategy(this.buildDefaultInheritanceStrategy());

		this.table.update();
		this.setSpecifiedTableIsAllowed(this.buildSpecifiedTableIsAllowed());
		this.setTableIsUndefined(this.buildTableIsUndefined());

		this.updateNodes(this.getSecondaryTables());

		this.updateDefaultPrimaryKeyJoinColumn();
		this.updateNodes(this.getPrimaryKeyJoinColumns());

		this.discriminatorColumn.update();
		this.setSpecifiedDiscriminatorColumnIsAllowed(this.buildSpecifiedDiscriminatorColumnIsAllowed());
		this.setDiscriminatorColumnIsUndefined(this.buildDiscriminatorColumnIsUndefined());

		this.setDefaultDiscriminatorValue(this.buildDefaultDiscriminatorValue());
		this.setSpecifiedDiscriminatorValueIsAllowed(this.buildSpecifiedDiscriminatorValueIsAllowed());
		this.setDiscriminatorValueIsUndefined(this.buildDiscriminatorValueIsUndefined());

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
		return this.getJavaResourceType().getName();
	}


	// ********** root entity **********

	@Override
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
		if (!isRootEntity()) {
			return EmptyIterable.instance();
		}
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
		return ObjectTools.notEquals(typeName, entityTypeName) &&
				ObjectTools.equals(typeName, rootEntityTypeName);
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

	public ListIterable<JavaSecondaryTable> getSecondaryTables() {
		return this.getSpecifiedSecondaryTables();
	}

	public int getSecondaryTablesSize() {
		return this.getSpecifiedSecondaryTablesSize();
	}


	// ********** specified secondary tables **********

	public ListIterable<JavaSecondaryTable> getSpecifiedSecondaryTables() {
		return this.specifiedSecondaryTableContainer.getContextElements();
	}

	public int getSpecifiedSecondaryTablesSize() {
		return this.specifiedSecondaryTableContainer.getContextElementsSize();
	}

	public JavaSecondaryTable addSpecifiedSecondaryTable() {
		return this.addSpecifiedSecondaryTable(this.getSpecifiedSecondaryTablesSize());
	}

	public JavaSecondaryTable addSpecifiedSecondaryTable(int index) {
		SecondaryTableAnnotation annotation = this.addSecondaryTableAnnotation(index);
		return this.specifiedSecondaryTableContainer.addContextElement(index, annotation);
	}

	protected SecondaryTableAnnotation addSecondaryTableAnnotation(int index) {
		return (SecondaryTableAnnotation) this.getJavaResourceType().addAnnotation(index, SecondaryTableAnnotation.ANNOTATION_NAME);
	}

	public void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTableContainer.indexOfContextElement((JavaSecondaryTable) secondaryTable));
	}

	public void removeSpecifiedSecondaryTable(int index) {
		this.getJavaResourceType().removeAnnotation(index, SecondaryTableAnnotation.ANNOTATION_NAME);
		this.specifiedSecondaryTableContainer.removeContextElement(index);
	}

	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		this.getResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, SecondaryTableAnnotation.ANNOTATION_NAME);
		this.specifiedSecondaryTableContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaSecondaryTable buildSecondaryTable(SecondaryTableAnnotation secondaryTableAnnotation) {
		return this.getJpaFactory().buildJavaSecondaryTable(this, this.specifiedSecondaryTableOwner, secondaryTableAnnotation);
	}

	protected void syncSpecifiedSecondaryTables() {
		this.specifiedSecondaryTableContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<SecondaryTableAnnotation> getSecondaryTableAnnotations() {
		return this.getSecondaryTableAnnotations_();
	}

	protected ListIterable<SecondaryTableAnnotation> getSecondaryTableAnnotations_() {
		return new SubListIterableWrapper<NestableAnnotation, SecondaryTableAnnotation>(this.getNestableSecondaryTableAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestableSecondaryTableAnnotations_() {
		return this.getResourceAnnotatedElement().getAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaSecondaryTable, SecondaryTableAnnotation> buildSpecifiedSecondaryTableContainer() {
		SpecifiedSecondaryTableContainer container = new SpecifiedSecondaryTableContainer();
		container.initialize();
		return container;
	}

	/**
	 * specified secondary table container
	 */
	protected class SpecifiedSecondaryTableContainer
		extends ContextListContainer<JavaSecondaryTable, SecondaryTableAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_SECONDARY_TABLES_LIST;
		}
		@Override
		protected JavaSecondaryTable buildContextElement(SecondaryTableAnnotation resourceElement) {
			return AbstractJavaEntity.this.buildSecondaryTable(resourceElement);
		}
		@Override
		protected ListIterable<SecondaryTableAnnotation> getResourceElements() {
			return AbstractJavaEntity.this.getSecondaryTableAnnotations();
		}
		@Override
		protected SecondaryTableAnnotation getResourceElement(JavaSecondaryTable contextElement) {
			return contextElement.getTableAnnotation();
		}
	}

	protected Table.Owner buildSpecifiedSecondaryTableOwner() {
		return new SecondaryTableOwner();
	}


	// ********** primary key join columns **********

	public ListIterable<JavaPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumns() : this.getDefaultPrimaryKeyJoinColumns();
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumnsSize() : this.getDefaultPrimaryKeyJoinColumnsSize();
	}


	// ********** specified primary key join columns **********

	public ListIterable<JavaPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumnContainer.getContextElements();
	}

	public PrimaryKeyJoinColumn getSpecifiedPrimaryKeyJoinColumn(int index) {
		return this.specifiedPrimaryKeyJoinColumnContainer.get(index);
	}

	public int getSpecifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumnContainer.getContextElementsSize();
	}

	protected boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumnsSize() != 0;
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn() {
		return this.addSpecifiedPrimaryKeyJoinColumn(this.getSpecifiedPrimaryKeyJoinColumnsSize());
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		PrimaryKeyJoinColumnAnnotation annotation = this.addPrimaryKeyJoinColumnAnnotation(index);
		return this.specifiedPrimaryKeyJoinColumnContainer.addContextElement(index, annotation);
	}

	protected PrimaryKeyJoinColumnAnnotation addPrimaryKeyJoinColumnAnnotation(int index) {
		return (PrimaryKeyJoinColumnAnnotation) this.getJavaResourceType().addAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumnContainer.indexOfContextElement((JavaPrimaryKeyJoinColumn) joinColumn));
	}

	public void convertDefaultPrimaryKeyJoinColumnsToSpecified() {
		if (this.defaultPrimaryKeyJoinColumn == null) {
			throw new IllegalStateException("default PK join column is null"); //$NON-NLS-1$
		}
		// Add a PK join column by creating a specified one using the default one
		String columnName = this.defaultPrimaryKeyJoinColumn.getDefaultName();
		String referencedColumnName = this.defaultPrimaryKeyJoinColumn.getDefaultReferencedColumnName();

		PrimaryKeyJoinColumn pkJoinColumn = this.addSpecifiedPrimaryKeyJoinColumn(0);
		pkJoinColumn.setSpecifiedName(columnName);
		pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
	}

	public void clearSpecifiedPrimaryKeyJoinColumns() {
		for (int index = this.getSpecifiedPrimaryKeyJoinColumnsSize(); --index >= 0; ) {
			this.getJavaResourceType().removeAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		}
		this.specifiedPrimaryKeyJoinColumnContainer.clearContextList();
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		this.getJavaResourceType().removeAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		this.specifiedPrimaryKeyJoinColumnContainer.removeContextElement(index);
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.getJavaResourceType().moveAnnotation(targetIndex, sourceIndex, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		this.specifiedPrimaryKeyJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedPrimaryKeyJoinColumns() {
		this.specifiedPrimaryKeyJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<PrimaryKeyJoinColumnAnnotation> getPrimaryKeyJoinColumnAnnotations() {
		return getPrimaryKeyJoinColumnAnnotations_();
	}

	protected ListIterable<PrimaryKeyJoinColumnAnnotation> getPrimaryKeyJoinColumnAnnotations_() {
		return new SubListIterableWrapper<NestableAnnotation, PrimaryKeyJoinColumnAnnotation>(this.getNestablePrimaryKeyJoinColumnAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestablePrimaryKeyJoinColumnAnnotations_() {
		return this.getResourceAnnotatedElement().getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
	}

	protected PrimaryKeyJoinColumnOwner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected JavaPrimaryKeyJoinColumn buildSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation primaryKeyJoinColumnAnnotation) {
		return this.getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, primaryKeyJoinColumnAnnotation);
	}

	protected ContextListContainer<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> buildSpecifiedPrimaryKeyJoinColumnContainer() {
		SpecifiedPrimaryKeyJoinColumnContainer container = new SpecifiedPrimaryKeyJoinColumnContainer();
		container.initialize();
		return container;
	}

	/**
	 * specified primary key join column container
	 */
	protected class SpecifiedPrimaryKeyJoinColumnContainer
		extends ContextListContainer<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaPrimaryKeyJoinColumn buildContextElement(PrimaryKeyJoinColumnAnnotation resourceElement) {
			return AbstractJavaEntity.this.buildSpecifiedPrimaryKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<PrimaryKeyJoinColumnAnnotation> getResourceElements() {
			return AbstractJavaEntity.this.getPrimaryKeyJoinColumnAnnotations();
		}
		@Override
		protected PrimaryKeyJoinColumnAnnotation getResourceElement(JavaPrimaryKeyJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
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

	protected int getDefaultPrimaryKeyJoinColumnsSize() {
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
		return this.buildSpecifiedPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumnAnnotation(this.getJavaResourceType()));
	}


	// ********** inheritance strategy **********

	@Override
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
		return this.isRootEntity() ? InheritanceType.SINGLE_TABLE : this.rootEntity.getInheritanceStrategy();
	}


	// ********** inheritance annotation **********

	protected InheritanceAnnotation getInheritanceAnnotation() {
		return (InheritanceAnnotation) this.getJavaResourceType().getNonNullAnnotation(InheritanceAnnotation.ANNOTATION_NAME);
	}

	protected void removeInheritanceAnnotationIfUnset() {
		if (this.getInheritanceAnnotation().isUnset()) {
			this.removeInheritanceAnnotation();
		}
	}

	protected void removeInheritanceAnnotation() {
		this.getJavaResourceType().removeAnnotation(InheritanceAnnotation.ANNOTATION_NAME);
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
		return (DiscriminatorValueAnnotation) this.getJavaResourceType().getNonNullAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);
	}

	protected void removeDiscriminatorValueAnnotationIfUnset() {
		if (this.getDiscriminatorValueAnnotation().isUnset()) {
			this.removeDiscriminatorValueAnnotation();
		}
	}

	protected void removeDiscriminatorValueAnnotation() {
		this.getJavaResourceType().removeAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);
	}


	// ********** discriminator column **********

	public JavaDiscriminatorColumn getDiscriminatorColumn() {
		return this.discriminatorColumn;
	}

	protected JavaDiscriminatorColumn buildDiscriminatorColumn() {
		return this.getJpaFactory().buildJavaDiscriminatorColumn(this, this.buildDiscriminatorColumnOwner());
	}

	protected ReadOnlyNamedDiscriminatorColumn.Owner buildDiscriminatorColumnOwner() {
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
		return ! this.isTablePerClass() && this.isRootEntity();
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
		return this.getJpaFactory().buildJavaGeneratorContainer(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<Generator> getGenerators() {
		return new CompositeIterable<Generator>(
					super.getGenerators(),
					this.generatorContainer.getGenerators()
				);
	}


	// ********** generator container parent adapter **********

	public JpaContextNode getGeneratorContainerParent() {
		return this;  // no adapter
	}

	public JavaResourceType getResourceAnnotatedElement() {
		return this.getJavaResourceType();
	}

	public boolean parentSupportsGenerators() {
		return true;
	}


	// ********** query container **********

	public JavaQueryContainer getQueryContainer() {
		return this.queryContainer;
	}

	protected JavaQueryContainer buildQueryContainer() {
		return this.getJpaFactory().buildJavaQueryContainer(this, this);
	}

	public Iterable<Query> getQueries() {
		return this.queryContainer.getQueries();
	}


	// ********** associated tables **********

	@Override
	public Iterable<ReadOnlyTable> getAssociatedTables() {
		return new CompositeIterable<ReadOnlyTable>(this.table, this.getSecondaryTables());
	}


	@Override
	public Iterable<ReadOnlyTable> getAllAssociatedTables() {
		return new CompositeIterable<ReadOnlyTable>(this.allAssociatedTablesLists());
	}

	// TODO eliminate duplicate tables?
	protected Iterable<Iterable<ReadOnlyTable>> allAssociatedTablesLists() {
		return new TransformationIterable<TypeMapping, Iterable<ReadOnlyTable>>(this.getInheritanceHierarchy(), TypeMappingTools.ASSOCIATED_TABLES_TRANSFORMER);
	}

	@Override
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
		return this.tableIsUndefined || this.tableNameIsValid_(tableName);
	}

	protected boolean tableNameIsValid_(String tableName) {
		return this.connectionProfileIsActive() ?
				(this.resolveDbTable(tableName) != null) :
				this.tableNameIsAssociatedTable(tableName);
	}

	protected boolean tableNameIsAssociatedTable(String tableName) {
		//short-circuit for performance during validation, likely that the table is the primary table
		if (tableName != null && tableName.equals(this.getPrimaryTableName())) {
			return true;
		}
		return IterableTools.contains(this.getAllAssociatedTableNames(), tableName);
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
		//short-circuit for performance during validation, no reason to build all the iterables in getallAssociatedDbTables()
		//i think the real answer is not to be validating in this case, but i believe that would involve some api changes up in NamedColumnValidator
		if (getDataSource().connectionProfileIsActive()) {
			// matching database objects and identifiers is database platform-specific
			return this.getDataSource().selectTableForIdentifier(this.getAllAssociatedDbTables(), tableName);
		}
		return null;
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
	public JavaResourceType getJavaResourceType() {
		return super.getJavaResourceType();
	}


	// ********** attribute mappings **********

	@Override
	public Column resolveOverriddenColumn(String attributeName) {
		// check for an override before looking at attribute mappings
		AttributeOverride override = this.attributeOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		return (override != null) ? override.getColumn() : super.resolveOverriddenColumn(attributeName);
	}

	@Override
	public Iterable<String> getOverridableAttributeNames() {
		return this.isTablePerClass() ?
				super.getOverridableAttributeNames() :
				EmptyIterable.<String>instance();
	}

	@Override
	public Iterable<String> getOverridableAssociationNames() {
		return this.isTablePerClass() ?
				super.getOverridableAssociationNames() :
				EmptyIterable.<String>instance();
	}

	public AttributeMapping resolveAttributeMapping(String name) {
		for (AttributeMapping attributeMapping : this.getAllAttributeMappings()) {
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

	@Override
	public boolean isRootEntity() {
		return this == this.rootEntity;
	}

	/**
	 * Return whether the entity is a descendant in (as opposed to the root of)
	 * an inheritance hierarchy.
	 */
	protected boolean isDescendant() {
		return ! this.isRootEntity();
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
		return this.isRootEntity() &&
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
		return this.getJavaResourceType().isAbstract();
	}

	/**
	 * Return whether the entity's type is final.
	 */
	protected boolean isFinal() {
		return this.getJavaResourceType().isFinal();
	}

	/**
	 * Return whether the entity's type is a member of another type.
	 */
	protected boolean isMember() {
		return this.getJavaResourceType().getTypeBinding().isMemberTypeDeclaration();
	}

	/**
	 * Return whether the entity's type is static.
	 */
	protected boolean isStatic() {
		return this.getJavaResourceType().isStatic();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.table.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (JavaSecondaryTable secondaryTable : this.getSecondaryTables()) {
			result = secondaryTable.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		for (JavaPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
			result = pkJoinColumn.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		result = this.attributeOverrideContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.associationOverrideContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.discriminatorColumn.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.generatorContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		this.validatePrimaryKey(messages, reporter);
		this.validateTable(messages, reporter);
		for (JavaSecondaryTable secondaryTable : this.getSecondaryTables()) {
			secondaryTable.validate(messages, reporter);
		}
		this.validateInheritance(messages, reporter);
		for (JavaPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
			pkJoinColumn.validate(messages, reporter);
		}
		this.generatorContainer.validate(messages, reporter);
		this.queryContainer.validate(messages, reporter);
		this.attributeOverrideContainer.validate(messages, reporter);
		this.associationOverrideContainer.validate(messages, reporter);
		this.validateEntityName(messages);
		this.idClassReference.validate(messages, reporter);
	}

	@Override
	public boolean validatesAgainstDatabase() {
		return super.validatesAgainstDatabase() && ! this.isAbstractTablePerClass();
	}

	protected void validateEntityName(List<IMessage> messages) {
		if (StringTools.isBlank(this.getName())){
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.ENTITY_NAME_MISSING,
							new String[] {this.getPersistentType().getName()}, 
							this,
							this.getMappingAnnotation().getNameTextRange()
					)
			);
		}
	}

	public boolean supportsValidationMessages() {
		return MappingTools.nodeIsInternalSource(this, this.getJavaResourceType());
	}

	public TextRange getNameTextRange() {
		return this.getMappingAnnotation().getNameTextRange();
	}

	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter) {
		this.buildPrimaryKeyValidator().validate(messages, reporter);
	}

	protected JptValidator buildPrimaryKeyValidator() {
		return new GenericEntityPrimaryKeyValidator(this);
	}

	protected void validateTable(List<IMessage> messages, IReporter reporter) {
		if (this.isAbstractTablePerClass()) {
			if (this.table.isSpecifiedInResource()) {
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
		else if (!this.discriminatorColumn.isVirtual()) {
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
		if ((this.getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS) && this.isRootEntity()) {
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
		return this.getValidationTextRange(this.getDiscriminatorValueAnnotation().getTextRange());
	}

	protected TextRange getDiscriminatorColumnTextRange() {
		return this.getValidationTextRange(this.discriminatorColumn.getValidationTextRange());
	}

	protected TextRange getInheritanceStrategyTextRange() {
		return this.getValidationTextRange(this.getInheritanceAnnotation().getStrategyTextRange());
	}


	// ********** OrmOverrideContainer.Owner implementation **********

	/**
	 * some common behavior
	 */
	protected abstract class OverrideContainerOwner
		implements JavaOverrideContainer2_0.Owner
	{
		public JavaResourceMember getResourceMember() {
			return AbstractJavaEntity.this.getJavaResourceType();
		}

		public AbstractJavaEntity getTypeMapping() {
			return AbstractJavaEntity.this;
		}

		public TextRange getValidationTextRange() {
			return AbstractJavaEntity.this.getValidationTextRange();
		}

		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaEntity.this.getOverridableTypeMapping();
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? this.getAllOverridableNames_(overriddenTypeMapping) : EmptyIterable.<String>instance();
		}

		/**
		 * pre-condition: <code>typeMapping</code> is not <code>null</code>
		 */
		protected abstract Iterable<String> getAllOverridableNames_(TypeMapping overriddenTypeMapping);

		public String getDefaultTableName() {
			return AbstractJavaEntity.this.getPrimaryTableName();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractJavaEntity.this.tableNameIsInvalid(tableName);
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return AbstractJavaEntity.this.resolveDbTable(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return AbstractJavaEntity.this.getAllAssociatedTableNames();
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
		implements JavaAttributeOverrideContainer2_0.Owner
	{
		@Override
		protected Iterable<String> getAllOverridableNames_(TypeMapping overriddenTypeMapping) {
			return new FilteringIterable<String>(overriddenTypeMapping.getAllOverridableAttributeNames()) {
					@Override
					protected boolean accept(String attributeName) {
						return ! AttributeOverrideContainerOwner.this.getTypeMapping().attributeIsDerivedId(attributeName);
					}
				};
		}

		public Column resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container) {
			return new AttributeOverrideValidator((ReadOnlyAttributeOverride) override, (AttributeOverrideContainer) container, new MappedSuperclassOverrideDescriptionProvider());
		}
		
		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner owner) {
			return new AttributeOverrideColumnValidator((ReadOnlyAttributeOverride) override, column, new EntityTableDescriptionProvider());
		}
	}


	// ********** JavaAssociationOverrideContainer.Owner implementation **********

	protected class AssociationOverrideContainerOwner
		extends OverrideContainerOwner
		implements JavaAssociationOverrideContainer2_0.Owner
	{
		@Override
		protected Iterable<String> getAllOverridableNames_(TypeMapping overriddenTypeMapping) {
			return overriddenTypeMapping.getAllOverridableAssociationNames();
		}

		public Relationship resolveOverriddenRelationship(String attributeName) {
			return MappingTools.resolveOverriddenRelationship(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container) {
			return new AssociationOverrideValidator((ReadOnlyAssociationOverride) override, (AssociationOverrideContainer) container, new MappedSuperclassOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner owner) {
			return new AssociationOverrideJoinColumnValidator((ReadOnlyAssociationOverride) override, (ReadOnlyJoinColumn) column, (ReadOnlyJoinColumn.Owner) owner, new EntityTableDescriptionProvider());
		}

		public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
			return new AssociationOverrideJoinColumnValidator(override, column, owner, new JoinTableTableDescriptionProvider());
		}

		public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
			return new AssociationOverrideInverseJoinColumnValidator(override, column, owner, new JoinTableTableDescriptionProvider());
		}

		public JptValidator buildJoinTableValidator(ReadOnlyAssociationOverride override, ReadOnlyTable t) {
			return new AssociationOverrideJoinTableValidator(override, (ReadOnlyJoinTable) t);
		}
	}


	// ********** JavaNamedColumn.Owner implementation **********

	/**
	 * some common behavior
	 */
	protected abstract class NamedColumnOwner
		implements ReadOnlyNamedColumn.Owner
	{
		public String getDefaultTableName() {
			return AbstractJavaEntity.this.getPrimaryTableName();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return AbstractJavaEntity.this.resolveDbTable(tableName);
		}

		public TextRange getValidationTextRange() {
			return AbstractJavaEntity.this.getValidationTextRange();
		}
	}


	// ********** JavaBaseJoinColumn.Owner implementation **********

	protected class PrimaryKeyJoinColumnOwner
		extends NamedColumnOwner
		implements ReadOnlyBaseJoinColumn.Owner
	{
		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			Entity parentEntity = AbstractJavaEntity.this.getParentEntity();
			return (parentEntity == null) ? null : parentEntity.getPrimaryDbTable();
		}

		public int getJoinColumnsSize() {
			return AbstractJavaEntity.this.getPrimaryKeyJoinColumnsSize();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			if (this.getJoinColumnsSize() != 1) {
				return null;
			}
			Entity parentEntity = AbstractJavaEntity.this.getParentEntity();
			return (parentEntity == null) ? AbstractJavaEntity.this.getPrimaryKeyColumnName() : parentEntity.getPrimaryKeyColumnName();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return new EntityPrimaryKeyJoinColumnValidator((ReadOnlyBaseJoinColumn) column, this);
		}
	}


	// ********** JavaDiscriminatorColumn.Owner implementation **********

	protected class DiscriminatorColumnOwner
		extends NamedColumnOwner
		implements ReadOnlyNamedDiscriminatorColumn.Owner
	{
		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return this.isDescendant() ?
					this.getRootDiscriminatorColumn().getName() :
					this.discriminatorColumnIsUndefined() ? null : DiscriminatorColumn.DEFAULT_NAME;
		}

		public int getDefaultLength() {
			return this.isDescendant() ?
					this.getRootDiscriminatorColumn().getLength() :
					this.discriminatorColumnIsUndefined() ? 0 : ReadOnlyNamedDiscriminatorColumn.DEFAULT_LENGTH;
		}

		public DiscriminatorType getDefaultDiscriminatorType() {
			return this.isDescendant() ?
					this.getRootDiscriminatorColumn().getDiscriminatorType() :
					this.discriminatorColumnIsUndefined() ? null : ReadOnlyNamedDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
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

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return new DiscriminatorColumnValidator(column);
		}
	}


	// ********** table owner **********

	protected class TableOwner
		implements ReadOnlyTable.Owner
	{
		public JptValidator buildTableValidator(ReadOnlyTable t) {
			return new TableValidator(t);
		}
	}


	// ********** secondary table owner **********

	protected class SecondaryTableOwner
		implements ReadOnlyTable.Owner
	{
		public JptValidator buildTableValidator(ReadOnlyTable t) {
			return new SecondaryTableValidator((ReadOnlySecondaryTable) t);
		}
	}
}
