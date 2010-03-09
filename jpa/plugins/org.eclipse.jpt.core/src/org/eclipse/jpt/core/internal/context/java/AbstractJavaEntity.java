/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.JpaPlatformVariation.Supported;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.internal.context.PrimaryKeyValidator;
import org.eclipse.jpt.core.internal.jpa1.context.GenericEntityPrimaryKeyValidator;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.java.JavaCacheableHolder2_0;
import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorValueAnnotation;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTablesAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaEntity
	extends AbstractJavaTypeMapping
	implements JavaEntity, JavaCacheableHolder2_0
{
	protected String specifiedName;
	
	protected String defaultName;
	
	protected final JavaIdClassReference idClassReference;
	
	protected final JavaTable table;
	
	protected boolean specifiedTableIsAllowed;
	
	protected boolean tableIsUndefined;
	
	protected final List<JavaSecondaryTable> specifiedSecondaryTables;
	
	protected final List<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
	
	protected JavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;
	
	protected InheritanceType specifiedInheritanceStrategy;
	
	protected InheritanceType defaultInheritanceStrategy;
	
	protected String defaultDiscriminatorValue;
	
	protected String specifiedDiscriminatorValue;
	
	protected boolean specifiedDiscriminatorValueIsAllowed;
	
	protected boolean discriminatorValueIsUndefined;
		
	protected final JavaDiscriminatorColumn discriminatorColumn;
	
	protected boolean specifiedDiscriminatorColumnIsAllowed;
	
	protected boolean discriminatorColumnIsUndefined;
	
	protected final JavaAttributeOverrideContainer attributeOverrideContainer;
	
	protected final JavaAssociationOverrideContainer associationOverrideContainer;
	
	protected final JavaQueryContainer queryContainer;
	
	protected final JavaGeneratorContainer generatorContainer;
	
	protected Entity rootEntity;
	
	
	protected AbstractJavaEntity(JavaPersistentType parent) {
		super(parent);
		this.idClassReference = buildIdClassReference();
		this.table = this.getJpaFactory().buildJavaTable(this);
		this.discriminatorColumn = buildJavaDiscriminatorColumn();
		this.specifiedSecondaryTables = new ArrayList<JavaSecondaryTable>();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<JavaPrimaryKeyJoinColumn>();
		this.attributeOverrideContainer = 
				getJpaFactory().buildJavaAttributeOverrideContainer(this, new AttributeOverrideContainerOwner());
		this.associationOverrideContainer = 
				getJpaFactory().buildJavaAssociationOverrideContainer(this, new AssociationOverrideContainerOwner());
		this.queryContainer = getJpaFactory().buildJavaQueryContainer(this);
		this.generatorContainer = getJpaFactory().buildJavaGeneratorContainer(this);
	}
	
	protected JavaIdClassReference buildIdClassReference() {
		return new GenericJavaIdClassReference(this);
	}
	
	protected JavaBaseJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}
	
	protected JavaDiscriminatorColumn buildJavaDiscriminatorColumn() {
		return this.getJpaFactory().buildJavaDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected JavaDiscriminatorColumn.Owner buildDiscriminatorColumnOwner() {
		return new JavaDiscriminatorColumn.Owner(){
			public String getDefaultTableName() {
				return AbstractJavaEntity.this.getPrimaryTableName();
			}

			public org.eclipse.jpt.db.Table getDbTable(String tableName) {
				return AbstractJavaEntity.this.getDbTable(tableName);
			}

			public TextRange getValidationTextRange(CompilationUnit astRoot) {
				return AbstractJavaEntity.this.getValidationTextRange(astRoot);
			}

			public TypeMapping getTypeMapping() {
				return AbstractJavaEntity.this;
			}
			
			public String getDefaultColumnName() {
				return isDescendant() ?
						getRootEntity().getDiscriminatorColumn().getName()
					:
						discriminatorColumnIsUndefined()? 
							null
						:
							DiscriminatorColumn.DEFAULT_NAME;
			}
			
			public int getDefaultLength() {
				return isDescendant() ?
					getRootEntity().getDiscriminatorColumn().getLength()
				:
					discriminatorColumnIsUndefined()? 
						0//TODO think i want to return null here
					:
						DiscriminatorColumn.DEFAULT_LENGTH;
			}
			
			public DiscriminatorType getDefaultDiscriminatorType() {
				return isDescendant() ?
					getRootEntity().getDiscriminatorColumn().getDiscriminatorType()
				:
					discriminatorColumnIsUndefined()? 
						null
					:
						DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
			}

			public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
				return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.DISCRIMINATOR_COLUMN_UNRESOLVED_NAME,
					new String[] {column.getName(), column.getDbTable().getName()}, 
					column, 
					textRange
				);
			}
		};
	}
	
	@Override
	public void initialize(JavaResourcePersistentType resourcePersistentType) {
		super.initialize(resourcePersistentType);
		
		this.specifiedName = this.getResourceName();
		this.defaultName = this.getResourceDefaultName();
		this.idClassReference.initialize();
		this.rootEntity = calculateRootEntity();
		this.defaultInheritanceStrategy = this.buildDefaultInheritanceStrategy();
		this.specifiedInheritanceStrategy = this.getResourceInheritanceStrategy(getResourceInheritance());
		this.specifiedDiscriminatorValueIsAllowed = this.buildSpecifiedDiscriminatorValueIsAllowed();
		this.discriminatorValueIsUndefined = this.buildDiscriminatorValueIsUndefined();
		this.specifiedDiscriminatorValue = this.getResourceDiscriminatorValue().getValue();
		this.defaultDiscriminatorValue = this.buildDefaultDiscriminatorValue();
		this.specifiedDiscriminatorColumnIsAllowed = this.buildSpecifiedDiscriminatorColumnIsAllowed();
		this.discriminatorColumnIsUndefined = this.buildDiscriminatorColumnIsUndefined();
		this.discriminatorColumn.initialize(this.getResourceDiscriminatorColumn());
		this.specifiedTableIsAllowed = this.buildSpecifiedTableIsAllowed();
		this.tableIsUndefined = this.buildTableIsUndefined();
		this.table.initialize(resourcePersistentType);
		this.initializeSecondaryTables();
		this.generatorContainer.initialize(resourcePersistentType);
		this.queryContainer.initialize(resourcePersistentType);
		this.initializePrimaryKeyJoinColumns();
		this.initializeDefaultPrimaryKeyJoinColumn();
		this.attributeOverrideContainer.initialize(resourcePersistentType);
		this.associationOverrideContainer.initialize(resourcePersistentType);
	}
	
	protected void initializeSecondaryTables() {
		for (Iterator<NestableAnnotation> stream = 
				this.javaResourcePersistentType.annotations(
					SecondaryTableAnnotation.ANNOTATION_NAME, 
					SecondaryTablesAnnotation.ANNOTATION_NAME); 
				stream.hasNext(); ) {
			this.specifiedSecondaryTables.add(
					buildSecondaryTable((SecondaryTableAnnotation) stream.next()));
		}
	}
	
	protected void initializePrimaryKeyJoinColumns() {
		for (Iterator<NestableAnnotation> stream = 
				this.javaResourcePersistentType.annotations(
					PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, 
					PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME); 
				stream.hasNext(); ) {
			this.specifiedPrimaryKeyJoinColumns.add(
					buildPrimaryKeyJoinColumn((PrimaryKeyJoinColumnAnnotation) stream.next()));
		}
	}
	
	protected boolean shouldBuildDefaultPrimaryKeyJoinColumn() {
		return !containsSpecifiedPrimaryKeyJoinColumns();
	}

	protected void initializeDefaultPrimaryKeyJoinColumn() {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			return;
		}
		this.defaultPrimaryKeyJoinColumn = buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumnAnnotation(this.javaResourcePersistentType));
	}	

	//query for the inheritance resource every time on setters.
	//call one setter and the inheritanceResource could change. 
	//You could call more than one setter before this object has received any notification
	//from the java resource model
	protected InheritanceAnnotation getResourceInheritance() {
		return (InheritanceAnnotation) this.javaResourcePersistentType.
				getNonNullAnnotation(InheritanceAnnotation.ANNOTATION_NAME);
	}
	
	protected DiscriminatorValueAnnotation getResourceDiscriminatorValue() {
		return (DiscriminatorValueAnnotation) this.javaResourcePersistentType.
				getNonNullAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);
	}
	
	protected DiscriminatorColumnAnnotation getResourceDiscriminatorColumn() {
		return (DiscriminatorColumnAnnotation) this.javaResourcePersistentType.
				getNonNullAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME);
	}
	
	@Override
	protected EntityAnnotation getResourceMappingAnnotation() {
		return (EntityAnnotation) super.getResourceMappingAnnotation();
	}
	
	// **************** AttributeOverrideContainer.Owner impl *****************

	public TypeMapping getTypeMapping() {
		return this;
	}
	
	public TypeMapping getOverridableTypeMapping() {
		PersistentType superPersistentType = getPersistentType().getSuperPersistentType();
		return superPersistentType == null ? null : superPersistentType.getMapping();
	}
	
	
	// **************** TypeMapping implementation ****************************
	
	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public boolean isMapped() {
		return true;
	}
	
	@Override
	public JavaPersistentType getIdClass() {
		return this.idClassReference.getIdClass();
	}

	@Override
	public String getPrimaryTableName() {
		return this.getTable().getName();
	}

	@Override
	public org.eclipse.jpt.db.Table getPrimaryDbTable() {
		return getTable().getDbTable();
	}

	@Override
	public org.eclipse.jpt.db.Table getDbTable(String tableName) {
		// matching database objects and identifiers is database platform-specific
		return this.getDataSource().selectDatabaseObjectForIdentifier(this.getAssociatedDbTablesIncludingInherited(), tableName);
	}

	protected Iterable<org.eclipse.jpt.db.Table> getAssociatedDbTablesIncludingInherited() {
		return new FilteringIterable<org.eclipse.jpt.db.Table>(this.getAssociatedDbTablesIncludingInherited_()) {
			@Override
			protected boolean accept(org.eclipse.jpt.db.Table t) {
				return t != null;
			}
		};
	}

	protected Iterable<org.eclipse.jpt.db.Table> getAssociatedDbTablesIncludingInherited_() {
		return new TransformationIterable<Table, org.eclipse.jpt.db.Table>(this.getAssociatedTablesIncludingInherited()) {
			@Override
			protected org.eclipse.jpt.db.Table transform(Table t) {
				return t.getDbTable();
			}
		};
	}

	@Override
	public Schema getDbSchema() {
		return getTable().getDbSchema();
	}


	//****************** JavaTypeMapping implementation *******************

	public String getAnnotationName() {
		return EntityAnnotation.ANNOTATION_NAME;
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return SUPPORTING_ANNOTATION_NAMES;
	}
	protected static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
			JPA.TABLE,
			JPA.SECONDARY_TABLE,
			JPA.SECONDARY_TABLES,
			JPA.PRIMARY_KEY_JOIN_COLUMN,
			JPA.PRIMARY_KEY_JOIN_COLUMNS,
			JPA.ID_CLASS,
			JPA.INHERITANCE,
			JPA.DISCRIMINATOR_VALUE,
			JPA.DISCRIMINATOR_COLUMN,
			JPA.SEQUENCE_GENERATOR,
			JPA.TABLE_GENERATOR,
			JPA.NAMED_QUERY,
			JPA.NAMED_QUERIES,
			JPA.NAMED_NATIVE_QUERY,
			JPA.NAMED_NATIVE_QUERIES,
			JPA.SQL_RESULT_SET_MAPPING,
			JPA.EXCLUDE_DEFAULT_LISTENERS,
			JPA.EXCLUDE_SUPERCLASS_LISTENERS,
			JPA.ENTITY_LISTENERS,
			JPA.PRE_PERSIST,
			JPA.POST_PERSIST,
			JPA.PRE_REMOVE,
			JPA.POST_REMOVE,
			JPA.PRE_UPDATE,
			JPA.POST_UPDATE,
			JPA.POST_LOAD,
			JPA.ATTRIBUTE_OVERRIDE,
			JPA.ATTRIBUTE_OVERRIDES,
			JPA.ASSOCIATION_OVERRIDE,
			JPA.ASSOCIATION_OVERRIDES
	};
	protected static final Iterable<String> SUPPORTING_ANNOTATION_NAMES = new ArrayIterable<String>(SUPPORTING_ANNOTATION_NAMES_ARRAY);
	
	
	// **************** name **************************************************
	
	public String getName() {
		return (this.getSpecifiedName() == null) ? this.getDefaultName() : this.getSpecifiedName();
	}
	
	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		this.getResourceMappingAnnotation().setName(newSpecifiedName);
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}
	
	protected void setSpecifiedName_(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}
	
	public String getDefaultName() {
		return this.defaultName;
	}
	
	protected/*private-protected*/ void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}
	
	
	// **************** id class **********************************************
	
	public JavaIdClassReference getIdClassReference() {
		return this.idClassReference;
	}
	
	
	// **************** table *************************************************
	
	public JavaTable getTable() {
		return this.table;
	}

	public ListIterator<JavaSecondaryTable> specifiedSecondaryTables() {
		return new CloneListIterator<JavaSecondaryTable>(this.specifiedSecondaryTables);
	}
	
	public int specifiedSecondaryTablesSize() {
		return this.specifiedSecondaryTables.size();
	}
	
	public JavaSecondaryTable addSpecifiedSecondaryTable(int index) {
		JavaSecondaryTable secondaryTable = getJpaFactory().buildJavaSecondaryTable(this);
		this.specifiedSecondaryTables.add(index, secondaryTable);
		SecondaryTableAnnotation secondaryTableResource = 
				(SecondaryTableAnnotation) this.javaResourcePersistentType.
					addAnnotation(
						index, SecondaryTableAnnotation.ANNOTATION_NAME, 
						SecondaryTablesAnnotation.ANNOTATION_NAME);
		secondaryTable.initialize(secondaryTableResource);
		fireItemAdded(SPECIFIED_SECONDARY_TABLES_LIST, index, secondaryTable);
		return secondaryTable;
	}

	public JavaSecondaryTable addSpecifiedSecondaryTable() {
		return this.addSpecifiedSecondaryTable(this.specifiedSecondaryTables.size());
	}
	
	protected void addSpecifiedSecondaryTable(int index, JavaSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	protected void addSpecifiedSecondaryTable(JavaSecondaryTable secondaryTable) {
		this.addSpecifiedSecondaryTable(this.specifiedSecondaryTables.size(), secondaryTable);
	}
	
	public void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}
	
	public void removeSpecifiedSecondaryTable(int index) {
		JavaSecondaryTable removedSecondaryTable = this.specifiedSecondaryTables.remove(index);
		this.javaResourcePersistentType.removeAnnotation(
				index, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(SPECIFIED_SECONDARY_TABLES_LIST, index, removedSecondaryTable);
	}
	
	protected void removeSpecifiedSecondaryTable_(JavaSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.specifiedSecondaryTables, SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedSecondaryTables, targetIndex, sourceIndex);
		this.javaResourcePersistentType.moveAnnotation(
				targetIndex, sourceIndex, SecondaryTablesAnnotation.ANNOTATION_NAME);
		fireItemMoved(SPECIFIED_SECONDARY_TABLES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<JavaSecondaryTable> secondaryTables() {
		return specifiedSecondaryTables();
	}

	public int secondaryTablesSize() {
		return specifiedSecondaryTablesSize();
	}

	public InheritanceType getInheritanceStrategy() {
		return (this.getSpecifiedInheritanceStrategy() == null) ? this.getDefaultInheritanceStrategy() : this.getSpecifiedInheritanceStrategy();
	}
	
	public InheritanceType getDefaultInheritanceStrategy() {
		return this.defaultInheritanceStrategy;
	}
	
	protected void setDefaultInheritanceStrategy(InheritanceType newInheritanceType) {
		InheritanceType oldInheritanceType = this.defaultInheritanceStrategy;
		this.defaultInheritanceStrategy = newInheritanceType;
		firePropertyChanged(DEFAULT_INHERITANCE_STRATEGY_PROPERTY, oldInheritanceType, newInheritanceType);
	}
	
	public InheritanceType getSpecifiedInheritanceStrategy() {
		return this.specifiedInheritanceStrategy;
	}
	
	public void setSpecifiedInheritanceStrategy(InheritanceType newInheritanceType) {
		InheritanceType oldInheritanceType = this.specifiedInheritanceStrategy;
		this.specifiedInheritanceStrategy = newInheritanceType;
		getResourceInheritance().setStrategy(InheritanceType.toJavaResourceModel(newInheritanceType));
		firePropertyChanged(SPECIFIED_INHERITANCE_STRATEGY_PROPERTY, oldInheritanceType, newInheritanceType);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedInheritanceStrategy_(InheritanceType newInheritanceType) {
		InheritanceType oldInheritanceType = this.specifiedInheritanceStrategy;
		this.specifiedInheritanceStrategy = newInheritanceType;
		firePropertyChanged(SPECIFIED_INHERITANCE_STRATEGY_PROPERTY, oldInheritanceType, newInheritanceType);
	}

	public JavaDiscriminatorColumn getDiscriminatorColumn() {
		return this.discriminatorColumn;
	}

	public String getDefaultDiscriminatorValue() {
		return this.defaultDiscriminatorValue;
	}

	protected void setDefaultDiscriminatorValue(String newDefaultDiscriminatorValue) {
		String oldDefaultDiscriminatorValue = this.defaultDiscriminatorValue;
		this.defaultDiscriminatorValue = newDefaultDiscriminatorValue;
		firePropertyChanged(DEFAULT_DISCRIMINATOR_VALUE_PROPERTY, oldDefaultDiscriminatorValue, newDefaultDiscriminatorValue);
	}

	public String getSpecifiedDiscriminatorValue() {
		return this.specifiedDiscriminatorValue;
	}

	public void setSpecifiedDiscriminatorValue(String newSpecifiedDiscriminatorValue) {
		String oldSpecifiedDiscriminatorValue = this.specifiedDiscriminatorValue;
		this.specifiedDiscriminatorValue = newSpecifiedDiscriminatorValue;
		getResourceDiscriminatorValue().setValue(newSpecifiedDiscriminatorValue);
		firePropertyChanged(SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY, oldSpecifiedDiscriminatorValue, newSpecifiedDiscriminatorValue);
	}

	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedDiscriminatorValue_(String newSpecifiedDiscriminatorValue) {
		String oldSpecifiedDiscriminatorValue = this.specifiedDiscriminatorValue;
		this.specifiedDiscriminatorValue = newSpecifiedDiscriminatorValue;
		firePropertyChanged(SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY, oldSpecifiedDiscriminatorValue, newSpecifiedDiscriminatorValue);
	}

	public String getDiscriminatorValue() {
		return (this.getSpecifiedDiscriminatorValue() == null) ? getDefaultDiscriminatorValue() : this.getSpecifiedDiscriminatorValue();
	}
	
	public boolean specifiedDiscriminatorValueIsAllowed() {
		return this.specifiedDiscriminatorValueIsAllowed;
	}
	
	protected void setSpecifiedDiscriminatorValueIsAllowed(boolean specifiedDiscriminatorValueIsAllowed) {
		boolean old = this.specifiedDiscriminatorValueIsAllowed;
		this.specifiedDiscriminatorValueIsAllowed = specifiedDiscriminatorValueIsAllowed;
		firePropertyChanged(Entity.SPECIFIED_DISCRIMINATOR_VALUE_IS_ALLOWED_PROPERTY, old, specifiedDiscriminatorValueIsAllowed);
	}

	public boolean discriminatorValueIsUndefined() {
		return this.discriminatorValueIsUndefined;
	}
	
	protected void setDiscriminatorValueIsUndefined(boolean discriminatorValueIsUndefined) {
		boolean old = this.discriminatorValueIsUndefined;
		this.discriminatorValueIsUndefined = discriminatorValueIsUndefined;
		firePropertyChanged(DISCRIMINATOR_VALUE_IS_UNDEFINED_PROPERTY, old, discriminatorValueIsUndefined);
	}
	
	public boolean specifiedDiscriminatorColumnIsAllowed() {
		return this.specifiedDiscriminatorColumnIsAllowed;
	}
	
	protected void setSpecifiedDiscriminatorColumnIsAllowed(boolean specifiedDiscriminatorColumnIsAllowed) {
		boolean old = this.specifiedDiscriminatorColumnIsAllowed;
		this.specifiedDiscriminatorColumnIsAllowed = specifiedDiscriminatorColumnIsAllowed;
		firePropertyChanged(SPECIFIED_DISCRIMINATOR_COLUMN_IS_ALLOWED_PROPERTY, old, specifiedDiscriminatorColumnIsAllowed);
	}
	
	public boolean discriminatorColumnIsUndefined() {
		return this.discriminatorColumnIsUndefined;
	}
	
	protected void setDiscriminatorColumnIsUndefined(boolean discriminatorColumnIsUndefined) {
		boolean old = this.discriminatorColumnIsUndefined;
		this.discriminatorColumnIsUndefined = discriminatorColumnIsUndefined;
		firePropertyChanged(DISCRIMINATOR_COLUMN_IS_UNDEFINED_PROPERTY, old, discriminatorColumnIsUndefined);
	}

	public boolean specifiedTableIsAllowed() {
		return this.specifiedTableIsAllowed;
	}
	
	protected void setSpecifiedTableIsAllowed(boolean specifiedTableIsAllowed) {
		boolean old = this.specifiedTableIsAllowed;
		this.specifiedTableIsAllowed = specifiedTableIsAllowed;
		firePropertyChanged(SPECIFIED_TABLE_IS_ALLOWED_PROPERTY, old, specifiedTableIsAllowed);
	}
	
	public boolean tableIsUndefined() {
		return this.tableIsUndefined;
	}
	
	protected void setTableIsUndefined(boolean tableIsUndefined) {
		boolean old = this.tableIsUndefined;
		this.tableIsUndefined = tableIsUndefined;
		firePropertyChanged(TABLE_IS_UNDEFINED_PROPERTY, old, tableIsUndefined);
	}
	
	public JavaGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}
	
	public ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumns() : this.defaultPrimaryKeyJoinColumns();
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.defaultPrimaryKeyJoinColumnsSize();
	}
	
	public ListIterator<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<JavaPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}
	
	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}
	
	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.specifiedPrimaryKeyJoinColumns.isEmpty();
	}	
	
	public JavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}
	
	protected void setDefaultPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn newPkJoinColumn) {
		JavaPrimaryKeyJoinColumn oldPkJoinColumn = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = newPkJoinColumn;
		firePropertyChanged(DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldPkJoinColumn, newPkJoinColumn);
	}

	protected ListIterator<JavaPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		if (this.defaultPrimaryKeyJoinColumn != null) {
			return new SingleElementListIterator<JavaPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn);
		}
		return EmptyListIterator.instance();
	}
	
	protected int defaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn oldDefaultPkJoinColumn = this.getDefaultPrimaryKeyJoinColumn();
		if (oldDefaultPkJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultPrimaryKeyJoinColumn = null;
		}
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, buildPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnResource = 
				(PrimaryKeyJoinColumnAnnotation) this.javaResourcePersistentType.
					addAnnotation(
						index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, 
						PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		primaryKeyJoinColumn.initialize(pkJoinColumnResource);
		this.fireItemAdded(SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		if (oldDefaultPkJoinColumn != null) {
			this.firePropertyChanged(DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldDefaultPkJoinColumn, null);
		}
		return primaryKeyJoinColumn;
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
		
	protected void addSpecifiedPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.addSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.size(), primaryKeyJoinColumn);
	}
		
	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(primaryKeyJoinColumn));
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		if (!containsSpecifiedPrimaryKeyJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultPrimaryKeyJoinColumn = buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumnAnnotation(this.javaResourcePersistentType));
		}
		this.javaResourcePersistentType.removeAnnotation(
				index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, 
				PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		fireItemRemoved(SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
		if (this.defaultPrimaryKeyJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(DEFAULT_PRIMARY_KEY_JOIN_COLUMN, null, this.defaultPrimaryKeyJoinColumn);
		}
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.javaResourcePersistentType.moveAnnotation(
				targetIndex, sourceIndex, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		moveItemInList(targetIndex, sourceIndex, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);		
	}
	
	public JavaAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}
	
	public JavaAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}
	
	public JavaQueryContainer getQueryContainer() {
		return this.queryContainer;
	}
	
	public Entity getParentEntity() {
		for (Iterator<PersistentType> stream = getPersistentType().ancestors(); stream.hasNext();) {
			TypeMapping typeMapping = stream.next().getMapping();
			if (typeMapping instanceof Entity) {
				return (Entity) typeMapping;
			}
		}
		return null;
	}
	
	public Entity getRootEntity() {
		return this.rootEntity;
	}

	public boolean isRoot() {
		return this == this.getRootEntity();
	}

	/**
	 * Return whether the entity is a descendant in (as opposed to the root of)
	 * an inheritance hierarchy.
	 */
	protected boolean isDescendant() {
		return ! this.isRoot();
	}
	
	/**
	 * Table name defaults to the entity name.
	 * If the entity is part of a single table inheritance hierarchy, table
	 * name defaults to the root entity's table name.
	 * If the entity is abstract and part of a table per class
	 * inheritance hierarchy, the table name defaults to null, no table applies
	 */
	public String getDefaultTableName() {
		return this.isSingleTableDescendant() ?
						this.getRootEntity().getTable().getName()
					:
						this.isAbstractTablePerClass() ?
								null
							:
								this.getName();
	}

	public String getDefaultSchema() {
		return this.isSingleTableDescendant() ?
						this.getRootEntity().getTable().getSchema()
					:
						this.isAbstractTablePerClass() ?
							null
						:
							this.getContextDefaultSchema();
	}

	public String getDefaultCatalog() {
		return this.isSingleTableDescendant() ?
						this.getRootEntity().getTable().getCatalog()
					:
						this.isAbstractTablePerClass() ?
							null
						:
							this.getContextDefaultCatalog();
	}

	/**
	 * Return whether the entity is a descendant of the root entity
	 * of a "single table" inheritance hierarchy.
	 */
	protected boolean isSingleTableDescendant() {
		return (this.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE)
					&& this.isDescendant();
	}
	
	/**
	 * Return whether the entity is the top of an inheritance hierarchy
	 * and has no descendants and no specified inheritance strategy has been defined.
	 */
	protected boolean isRootNoDescendantsNoStrategyDefined() {
		return isRoot() && !getPersistenceUnit().entityIsRootWithSubEntities(this.getName()) && getSpecifiedInheritanceStrategy() == null;
	}

	/**
	 * Return whether the entity is abstract and is a part of a 
	 * "table per class" inheritance hierarchy.
	 */
	protected boolean isAbstractTablePerClass() {
		return isAbstract() && isTablePerClass();
	}
	
	/**
	 * Return whether the entity is a part of a "table per class" 
	 * inheritance hierarchy.
	 */
	protected boolean isTablePerClass() {
		return (this.getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS);
	}
	
	/**
	 * Return whether the entity is a part of a "table per class" 
	 * inheritance hierarchy.
	 */
	protected boolean isTablePerClassDescendant() {
		return isTablePerClass() && isDescendant();
	}
	
	/**
	 * Return whether the type is abstract.
	 */
	protected boolean isAbstract() {
		return this.javaResourcePersistentType.isAbstract();
	}
	
	public String getPrimaryKeyColumnName() {
		return getPrimaryKeyColumnName(this);
	}
	
	/**
	 * Convenience implementation that is shared with ORM.
	 */
	public static String getPrimaryKeyColumnName(Entity entity) {
		String pkColumnName = null;
		for (Iterator<PersistentAttribute> stream = entity.getPersistentType().allAttributes(); stream.hasNext(); ) {
			PersistentAttribute attribute = stream.next();
			String current = attribute.getPrimaryKeyColumnName();
			if (current != null) {
				// 229423 - if the attribute is a primary key, but it has an attribute override,
				// use the override column instead
				AttributeOverride attributeOverride = entity.getAttributeOverrideContainer().getAttributeOverrideNamed(attribute.getName());
				if (attributeOverride != null) {
					current = attributeOverride.getColumn().getName();
				}
			}
			if (pkColumnName == null) {
				pkColumnName = current;
			}
			else if (current != null) {
				// if we encounter a composite primary key, return null
				return null;
			}
		}
		// if we encounter only a single primary key column name, return it
		return pkColumnName;
	}

	public PersistentAttribute getIdAttribute() {
		Iterable<AttributeMapping> idAttributeMappings = getAllAttributeMappings(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		if (CollectionTools.size(idAttributeMappings) != 1) {
			return null;
		}
		return idAttributeMappings.iterator().next().getPersistentAttribute();
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		if (tableIsUndefined()) {
			return false;
		}
		return ! CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
	}
	
	@Override
	public boolean shouldValidateAgainstDatabase() {
		return super.shouldValidateAgainstDatabase() && ! isAbstractTablePerClass();
	}
	
	@Override
	public Iterator<Table> associatedTables() {
		return new CompositeIterator<Table>(this.getTable(), this.secondaryTables());
	}

	@Override
	public Iterator<Table> associatedTablesIncludingInherited() {
		return this.getAssociatedTablesIncludingInherited().iterator();
	}

	protected Iterable<Table> getAssociatedTablesIncludingInherited() {
		return new CompositeIterable<Table>(new TransformationIterable<TypeMapping, Iterable<Table>>(this.getInheritanceHierarchy()) {
			@Override
			protected Iterable<Table> transform(TypeMapping mapping) {
				return new FilteringIterable<Table>(CollectionTools.iterable(mapping.associatedTables())) {
					@Override
					protected boolean accept(Table o) {
						return true;
						//TODO
						//filtering these out so as to avoid the duplicate table, root and children share the same table
						//return !(o instanceof SingleTableInheritanceChildTableImpl);
					}
				};
			}
		});
	}

	@Override
	public Iterator<String> associatedTableNamesIncludingInherited() {
		return this.nonNullTableNames(this.associatedTablesIncludingInherited());
	}

	protected Iterator<String> nonNullTableNames(Iterator<Table> tables) {
		return new FilteringIterator<String>(this.tableNames(tables)) {
			@Override
			protected boolean accept(String o) {
				return o != null;
			}
		};
	}

	protected Iterator<String> tableNames(Iterator<Table> tables) {
		return new TransformationIterator<Table, String>(tables) {
			@Override
			protected String transform(Table t) {
				return t.getName();
			}
		};
	}
	
	/**
	 * Return an iterator of Entities, each which inherits from the one before,
	 * and terminates at the root entity (or at the point of cyclicity).
	 */
	protected Iterator<TypeMapping> ancestors() {
		return new TransformationIterator<PersistentType, TypeMapping>(getPersistentType().ancestors()) {
			@Override
			protected TypeMapping transform(PersistentType type) {
				return type.getMapping();
			}
		};
	}
	
	@Override
	public Iterator<String> overridableAttributeNames() {
		if (!isTablePerClass()) {
			return EmptyIterator.instance();
		}
		return super.overridableAttributeNames();
	}
	
	@Override
	public Column resolveOverriddenColumn(String attributeName) {
		AttributeOverride override = getAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
		if (override != null && !override.isVirtual()) {
			return override.getColumn();
		}
		return super.resolveOverriddenColumn(attributeName);
	}
	
	@Override
	public RelationshipReference resolveRelationshipReference(String attributeName) {
		AssociationOverride override = getAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
		if (override != null && !override.isVirtual()) {
			return override.getRelationshipReference();
		}
		return super.resolveRelationshipReference(attributeName);
	}
	
	@Override
	public Iterator<String> overridableAssociationNames() {
		if (!isTablePerClass()) {
			return EmptyIterator.instance();
		}
		return super.overridableAssociationNames();
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
	
	@Override
	public void update(JavaResourcePersistentType resourcePersistentType) {
		super.update(resourcePersistentType);
		setSpecifiedName_(getResourceName());
		setDefaultName(getResourceDefaultName());
		this.idClassReference.update();
		updateRootEntity();
		updateInheritance(getResourceInheritance());
		updateDiscriminatorColumn();
		updateDiscriminatorValue(getResourceDiscriminatorValue());
		setSpecifiedTableIsAllowed(buildSpecifiedTableIsAllowed());
		setTableIsUndefined(buildTableIsUndefined());
		updateTable();
		updateSecondaryTables();
		this.generatorContainer.update(resourcePersistentType);
		this.queryContainer.update(resourcePersistentType);
		updateSpecifiedPrimaryKeyJoinColumns();
		updateDefaultPrimaryKeyJoinColumn();
		this.attributeOverrideContainer.update(resourcePersistentType);
		this.associationOverrideContainer.update(resourcePersistentType);
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		postUpdateDiscriminatorColumn();
		postUpdateDiscriminatorValue();
		this.associationOverrideContainer.postUpdate();
	}
	
	protected String getResourceName() {
		return this.getResourceMappingAnnotation().getName();
	}
	
	protected String getResourceDefaultName() {
		return this.javaResourcePersistentType.getName();
	}

	protected void updateTable() {
		getTable().update(this.javaResourcePersistentType);
	}
	
	protected void updateInheritance(InheritanceAnnotation inheritanceResource) {
		this.setSpecifiedInheritanceStrategy_(this.getResourceInheritanceStrategy(inheritanceResource));
		this.setDefaultInheritanceStrategy(this.buildDefaultInheritanceStrategy());
	}
	
	protected InheritanceType getResourceInheritanceStrategy(InheritanceAnnotation inheritanceResource) {
		return InheritanceType.fromJavaResourceModel(inheritanceResource.getStrategy());
	}
	
	protected InheritanceType buildDefaultInheritanceStrategy() {
		return this.isRoot() ? InheritanceType.SINGLE_TABLE : this.getRootEntity().getInheritanceStrategy();
	}
	
	protected void updateRootEntity() {
		//I am making an assumption here that we don't need property change notification for rootEntity, this might be wrong
		this.rootEntity = calculateRootEntity();
		if (this.rootEntity != this) {
			this.rootEntity.addSubEntity(this);
		}
	}
	
	protected Entity calculateRootEntity() {
		Entity root = this;
		for (Iterator<TypeMapping> stream = inheritanceHierarchy(); stream.hasNext();) {
			TypeMapping typeMapping = stream.next();
			if (typeMapping instanceof Entity) {
				root = (Entity) typeMapping;
			}
		}
		return root;
	}
	
	public void addSubEntity(Entity subEntity) {
		getPersistenceUnit().addRootEntityWithSubEntities(getName());
	}
	
	protected void updateDiscriminatorColumn() {
		this.setSpecifiedDiscriminatorColumnIsAllowed(this.buildSpecifiedDiscriminatorColumnIsAllowed());
		getDiscriminatorColumn().update(this.getResourceDiscriminatorColumn());
	}
	
	protected void postUpdateDiscriminatorColumn() {
		this.setDiscriminatorColumnIsUndefined(this.buildDiscriminatorColumnIsUndefined());
		this.getDiscriminatorColumn().postUpdate();
	}
	
	protected void updateDiscriminatorValue(DiscriminatorValueAnnotation discriminatorValueResource) {
		this.setSpecifiedDiscriminatorValueIsAllowed(this.buildSpecifiedDiscriminatorValueIsAllowed());
		this.setSpecifiedDiscriminatorValue_(discriminatorValueResource.getValue());
	}
	
	protected void postUpdateDiscriminatorValue() {
		this.setDiscriminatorValueIsUndefined(this.buildDiscriminatorValueIsUndefined());
		this.setDefaultDiscriminatorValue(this.buildDefaultDiscriminatorValue());
	}
	
	/**
	 * From the Spec:
	 * If the DiscriminatorValue annotation is not specified, a
	 * provider-specific function to generate a value representing
	 * the entity type is used for the value of the discriminator
	 * column. If the DiscriminatorType is STRING, the discriminator
	 * value default is the entity name.
	 * 
	 * TODO extension point for provider-specific function?
	 */
	protected String buildDefaultDiscriminatorValue() {
		if (discriminatorValueIsUndefined()) {
			return null;
		}
		if (this.getDiscriminatorType() != DiscriminatorType.STRING) {
			return null;
		}
		return this.getName();
	}

	protected DiscriminatorType getDiscriminatorType() {
		return this.getDiscriminatorColumn().getDiscriminatorType();
	}
	
	protected boolean buildSpecifiedDiscriminatorValueIsAllowed() {
		return !isTablePerClass() && !isAbstract();
	}
	
	protected boolean buildDiscriminatorValueIsUndefined() {
		return isTablePerClass() || isAbstract() || isRootNoDescendantsNoStrategyDefined();
	}
	
	protected boolean buildSpecifiedDiscriminatorColumnIsAllowed() {
		return !isTablePerClass() && isRoot();
	}
	
	protected boolean buildDiscriminatorColumnIsUndefined() {
		return isTablePerClass() || isRootNoDescendantsNoStrategyDefined();
	}
	
	protected boolean buildSpecifiedTableIsAllowed() {
		return !isAbstractTablePerClass() && !isSingleTableDescendant();
	}
	
	protected boolean buildTableIsUndefined() {
		return isAbstractTablePerClass();
	}
	
	protected void updateSecondaryTables() {
		ListIterator<JavaSecondaryTable> secondaryTables = specifiedSecondaryTables();
		Iterator<NestableAnnotation> resourceSecondaryTables = 
				this.javaResourcePersistentType.annotations(
					SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		
		while (secondaryTables.hasNext()) {
			JavaSecondaryTable secondaryTable = secondaryTables.next();
			if (resourceSecondaryTables.hasNext()) {
				secondaryTable.update((SecondaryTableAnnotation) resourceSecondaryTables.next());
			}
			else {
				removeSpecifiedSecondaryTable_(secondaryTable);
			}
		}
		
		while (resourceSecondaryTables.hasNext()) {
			addSpecifiedSecondaryTable(buildSecondaryTable((SecondaryTableAnnotation) resourceSecondaryTables.next()));
		}
	}

	protected JavaSecondaryTable buildSecondaryTable(SecondaryTableAnnotation secondaryTableResource) {
		JavaSecondaryTable secondaryTable = getJpaFactory().buildJavaSecondaryTable(this);
		secondaryTable.initialize(secondaryTableResource);
		return secondaryTable;
	}
	
	protected void updateSpecifiedPrimaryKeyJoinColumns() {
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		Iterator<NestableAnnotation> resourcePrimaryKeyJoinColumns = 
				this.javaResourcePersistentType.annotations(
					PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, 
					PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		while (primaryKeyJoinColumns.hasNext()) {
			JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
			if (resourcePrimaryKeyJoinColumns.hasNext()) {
				primaryKeyJoinColumn.update((PrimaryKeyJoinColumnAnnotation) resourcePrimaryKeyJoinColumns.next());
			}
			else {
				removeSpecifiedPrimaryKeyJoinColumn_(primaryKeyJoinColumn);
			}
		}
		
		while (resourcePrimaryKeyJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn((PrimaryKeyJoinColumnAnnotation) resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected JavaPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation primaryKeyJoinColumnResource) {
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, buildPrimaryKeyJoinColumnOwner());
		primaryKeyJoinColumn.initialize(primaryKeyJoinColumnResource);
		return primaryKeyJoinColumn;
	}

	protected void updateDefaultPrimaryKeyJoinColumn() {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			setDefaultPrimaryKeyJoinColumn(null);
			return;
		}
		if (getDefaultPrimaryKeyJoinColumn() == null) {
			this.setDefaultPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumnAnnotation(this.javaResourcePersistentType)));
		}
		else {
			this.defaultPrimaryKeyJoinColumn.update(new NullPrimaryKeyJoinColumnAnnotation(this.javaResourcePersistentType));
		}
	}
	
	
	//******************** Code Completion *************************

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getTable().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaSecondaryTable sTable : CollectionTools.iterable(this.secondaryTables())) {
			result = sTable.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (JavaPrimaryKeyJoinColumn column : CollectionTools.iterable(this.primaryKeyJoinColumns())) {
			result = column.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		result = this.getAttributeOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getAssociationOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getDiscriminatorColumn().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getGeneratorContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}
	
	
	//********** Validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		validatePrimaryKey(messages, reporter, astRoot);
		validateTable(messages, reporter, astRoot);
		for (Iterator<JavaSecondaryTable> stream = this.specifiedSecondaryTables(); stream.hasNext();) {
			stream.next().validate(messages, reporter, astRoot);
		}
		validateInheritance(messages, reporter, astRoot);
		getGeneratorContainer().validate(messages, reporter, astRoot);
		getQueryContainer().validate(messages, reporter, astRoot);
		getAttributeOverrideContainer().validate(messages, reporter, astRoot);
		getAssociationOverrideContainer().validate(messages, reporter, astRoot);
	}
	
	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		buildPrimaryKeyValidator(astRoot).validate(messages, reporter);
	}
	
	protected PrimaryKeyValidator buildPrimaryKeyValidator(CompilationUnit astRoot) {
		return new GenericEntityPrimaryKeyValidator(this, buildTextRangeResolver(astRoot));
	}
	
	protected PrimaryKeyTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaEntityTextRangeResolver(this, astRoot);
	}
	
	protected void validateTable(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (isAbstractTablePerClass()) {
			if (this.table.isResourceSpecified()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_ABSTRACT_TABLE_PER_CLASS_DEFINES_TABLE,
						new String[] {this.getName()},
						this,
						this.getTable().getValidationTextRange(astRoot)
					)
				);
			}			
			return;
		}
		if (isSingleTableDescendant()) {
			if (this.table.isResourceSpecified()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_SINGLE_TABLE_DESCENDANT_DEFINES_TABLE,
						new String[] {this.getName()},
						this,
						this.getTable().getValidationTextRange(astRoot)
					)
				);
			}
			return;
		}
		this.table.validate(messages, reporter, astRoot);
	}
	
	protected void validateInheritance(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		validateInheritanceStrategy(messages, astRoot);
		validateDiscriminatorColumn(messages, reporter, astRoot);
		validateDiscriminatorValue(messages, astRoot);
	}
	
	protected void validateDiscriminatorColumn(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (specifiedDiscriminatorColumnIsAllowed() && !discriminatorColumnIsUndefined()) {
			getDiscriminatorColumn().validate(messages, reporter, astRoot);
		}
		else if (getDiscriminatorColumn().isResourceSpecified()) {
			if (!isRoot()) {
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
			else if (isTablePerClass()) {
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
		if (discriminatorValueIsUndefined() && getSpecifiedDiscriminatorValue() != null) {
			if (isAbstract()) {
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
			else if (isTablePerClass()) {
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
		Supported tablePerConcreteClassInheritanceIsSupported = getJpaPlatformVariation().getTablePerConcreteClassInheritanceIsSupported();
		if (tablePerConcreteClassInheritanceIsSupported == Supported.YES) {
			return;
		}
		if ((getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS) && isRoot()) {
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
		return getResourceDiscriminatorValue().getTextRange(astRoot);
	}
	
	protected TextRange getDiscriminatorColumnTextRange(CompilationUnit astRoot) {
		return getDiscriminatorColumn().getValidationTextRange(astRoot);
	}
	
	protected TextRange getInheritanceStrategyTextRange(CompilationUnit astRoot) {
		return getResourceInheritance().getStrategyTextRange(astRoot);
	}
	
	
	// ********** association override container owner **********

	class AssociationOverrideContainerOwner implements JavaAssociationOverrideContainer.Owner {
		
		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaEntity.this.getOverridableTypeMapping();
		}
		
		public TypeMapping getTypeMapping() {
			return AbstractJavaEntity.this.getTypeMapping();
		}

		public RelationshipReference resolveRelationshipReference(String associationOverrideName) {
			return MappingTools.resolveRelationshipReference(getOverridableTypeMapping(), associationOverrideName);
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return AbstractJavaEntity.this.tableNameIsInvalid(tableName);
		}
		
		public Iterator<String> candidateTableNames() {
			return AbstractJavaEntity.this.associatedTableNamesIncludingInherited();
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return AbstractJavaEntity.this.getDbTable(tableName);
		}
		
		public String getDefaultTableName() {
			return AbstractJavaEntity.this.getPrimaryTableName();
		}

		public String getPossiblePrefix() {
			return null;
		}

		public String getWritePrefix() {
			return null;
		}

		public boolean isRelevant(String overrideName) {
			//no prefix, so always true
			return true;
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaEntity.this.getValidationTextRange(astRoot);
		}

		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID,
				new String[] {
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY}, 
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualOverrideColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID,
				new String[] {
					overrideName,
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY},
				column, 
				textRange
			);
		}

		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {overrideName, column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}

		public IMessage buildColumnUnresolvedReferencedColumnNameMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnUnresolvedReferencedColumnNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {column.getReferencedColumnName(), column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualColumnUnresolvedReferencedColumnNameMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {overrideName, column.getReferencedColumnName(), column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}

		public IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualUnspecifiedNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0], 
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualUnspecifiedNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {overrideName},
				column, 
				textRange
			);
		}
		
		public IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0], 
				column, 
				textRange
			);
		}
		protected IMessage buildVirtualUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {overrideName},
				column, 
				textRange
			);
		}
	}
	
	//********** AttributeOverrideContainer.Owner implementation *********	
	
	class AttributeOverrideContainerOwner implements JavaAttributeOverrideContainer.Owner {
		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaEntity.this.getOverridableTypeMapping();
		}
		
		public TypeMapping getTypeMapping() {
			return AbstractJavaEntity.this.getTypeMapping();
		}

		public Column resolveOverriddenColumn(String attributeOverrideName) {
			return MappingTools.resolveOverridenColumn(getOverridableTypeMapping(), attributeOverrideName);
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return AbstractJavaEntity.this.tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return AbstractJavaEntity.this.associatedTableNamesIncludingInherited();
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return AbstractJavaEntity.this.getDbTable(tableName);
		}
		
		public String getDefaultTableName() {
			return AbstractJavaEntity.this.getPrimaryTableName();
		}

		public String getPossiblePrefix() {
			return null;
		}

		public String getWritePrefix() {
			return null;
		}

		public boolean isRelevant(String overrideName) {
			//no prefix, so always true
			return true;
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaEntity.this.getValidationTextRange(astRoot);
		}

		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {overrideName, column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}

		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_TABLE_NOT_VALID,
				new String[] {
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY}, 
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID,
				new String[] {
					overrideName,
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY},
				column, 
				textRange
			);
		}
	}
	
	
	// ********** pk join column owner **********

	class PrimaryKeyJoinColumnOwner implements JavaBaseJoinColumn.Owner
	{
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaEntity.this.getValidationTextRange(astRoot);
		}

		public TypeMapping getTypeMapping() {
			return AbstractJavaEntity.this;
		}

		public String getDefaultTableName() {
			return AbstractJavaEntity.this.getPrimaryTableName();
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return AbstractJavaEntity.this.getDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			Entity parentEntity = AbstractJavaEntity.this.getParentEntity();
			return (parentEntity == null) ? null : parentEntity.getPrimaryDbTable();
		}

		public int joinColumnsSize() {
			return AbstractJavaEntity.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return AbstractJavaEntity.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			Entity parentEntity = AbstractJavaEntity.this.getParentEntity();
			return (parentEntity == null) ? getPrimaryKeyColumnName() : parentEntity.getPrimaryKeyColumnName();
		}

		public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			throw new UnsupportedOperationException("validation not supported yet: bug 148262"); //$NON-NLS-1$
		}

		public IMessage buildUnresolvedReferencedColumnNameMessage(BaseJoinColumn column, TextRange textRange) {
			throw new UnsupportedOperationException("validation not supported yet: bug 148262"); //$NON-NLS-1$
		}

		public IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange) {
			throw new UnsupportedOperationException("validation not supported yet: bug 148262"); //$NON-NLS-1$
		}
		
		public IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange) {
			throw new UnsupportedOperationException("validation not supported yet: bug 148262"); //$NON-NLS-1$
		}
	}
}
