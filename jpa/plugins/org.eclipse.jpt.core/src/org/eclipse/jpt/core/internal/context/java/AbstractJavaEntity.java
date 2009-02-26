/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaGenerator;
import org.eclipse.jpt.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.internal.resource.java.NullAssociationOverride;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorValueAnnotation;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NamedNativeQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTablesAnnotation;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaEntity
	extends AbstractJavaTypeMapping
	implements JavaEntity
{
	
	protected String specifiedName;

	protected String defaultName;

	protected final JavaTable table;

	protected final List<JavaSecondaryTable> specifiedSecondaryTables;

	protected final List<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;

	protected JavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	protected InheritanceType specifiedInheritanceStrategy;
	
	protected InheritanceType defaultInheritanceStrategy;

	protected String defaultDiscriminatorValue;

	protected boolean discriminatorValueAllowed;
	
	protected String specifiedDiscriminatorValue;
	
	protected final JavaDiscriminatorColumn discriminatorColumn;

	protected boolean discriminatorColumnAllowed;

	protected JavaSequenceGenerator sequenceGenerator;

	protected JavaTableGenerator tableGenerator;

	protected final List<JavaAttributeOverride> specifiedAttributeOverrides;

	protected final List<JavaAttributeOverride> virtualAttributeOverrides;

	protected final List<JavaAssociationOverride> specifiedAssociationOverrides;

	protected final List<JavaAssociationOverride> virtualAssociationOverrides;

	protected final List<JavaNamedQuery> namedQueries;

	protected final List<JavaNamedNativeQuery> namedNativeQueries;

	protected String idClass;

	
	protected AbstractJavaEntity(JavaPersistentType parent) {
		super(parent);
		this.table = getJpaFactory().buildJavaTable(this);
		this.discriminatorColumn = buildJavaDiscriminatorColumn();
		this.specifiedSecondaryTables = new ArrayList<JavaSecondaryTable>();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<JavaPrimaryKeyJoinColumn>();
		this.specifiedAttributeOverrides = new ArrayList<JavaAttributeOverride>();
		this.virtualAttributeOverrides = new ArrayList<JavaAttributeOverride>();
		this.namedQueries = new ArrayList<JavaNamedQuery>();
		this.namedNativeQueries = new ArrayList<JavaNamedNativeQuery>();
		this.specifiedAssociationOverrides = new ArrayList<JavaAssociationOverride>();
		this.virtualAssociationOverrides = new ArrayList<JavaAssociationOverride>();
	}
	
	protected JavaBaseJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}
	
	protected JavaDiscriminatorColumn buildJavaDiscriminatorColumn() {
		return getJpaFactory().buildJavaDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected JavaDiscriminatorColumn.Owner buildDiscriminatorColumnOwner() {
		return new JavaDiscriminatorColumn.Owner(){
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
						isTablePerClass() ? 
							null
						:
							DiscriminatorColumn.DEFAULT_NAME;
			}
			
			public int getDefaultLength() {
				return isDescendant() ?
					getRootEntity().getDiscriminatorColumn().getLength()
				:
					isTablePerClass() ? 
						0//TODO think i want to return null here
					:
						DiscriminatorColumn.DEFAULT_LENGTH;
			}
			
			public DiscriminatorType getDefaultDiscriminatorType() {
				return isDescendant() ?
					getRootEntity().getDiscriminatorColumn().getDiscriminatorType()
				:
					isTablePerClass() ? 
						null
					:
						DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
			}
		};
	}

	@Override
	public void initialize(JavaResourcePersistentType resourcePersistentType) {
		super.initialize(resourcePersistentType);
		
		this.specifiedName = this.getResourceName();
		this.defaultName = this.getResourceDefaultName();
		this.defaultInheritanceStrategy = this.buildDefaultInheritanceStrategy();
		this.specifiedInheritanceStrategy = this.getResourceInheritanceStrategy(getResourceInheritance());
		this.discriminatorValueAllowed = this.buildDiscriminatorValueIsAllowed();
		this.specifiedDiscriminatorValue = this.getResourceDiscriminatorValue().getValue();
		this.defaultDiscriminatorValue = this.buildDefaultDiscriminatorValue();
		this.discriminatorColumnAllowed = this.buildDiscriminatorColumnIsAllowed();
		this.discriminatorColumn.initialize(resourcePersistentType);
		this.table.initialize(resourcePersistentType);
		this.initializeSecondaryTables();
		this.initializeTableGenerator();
		this.initializeSequenceGenerator();
		this.initializePrimaryKeyJoinColumns();
		this.initializeDefaultPrimaryKeyJoinColumn();
		this.initializeSpecifiedAttributeOverrides();
		this.initializeVirtualAttributeOverrides();
		this.initializeSpecifiedAssociationOverrides();
		this.initializeDefaultAssociationOverrides();
		this.initializeNamedQueries();
		this.initializeNamedNativeQueries();
		this.initializeIdClass();
	}
	
	protected void initializeSecondaryTables() {
		for (ListIterator<NestableAnnotation> stream = this.javaResourcePersistentType.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME); stream.hasNext(); ) {
			this.specifiedSecondaryTables.add(buildSecondaryTable((SecondaryTableAnnotation) stream.next()));
		}
	}
	
	protected void initializeTableGenerator() {
		TableGeneratorAnnotation tableGeneratorResource = getResourceTableGenerator();
		if (tableGeneratorResource != null) {
			this.tableGenerator = buildTableGenerator(tableGeneratorResource);
		}
	}
	
	protected void initializeSequenceGenerator() {
		SequenceGeneratorAnnotation sequenceGeneratorResource = getResourceSequenceGenerator();
		if (sequenceGeneratorResource != null) {
			this.sequenceGenerator = buildSequenceGenerator(sequenceGeneratorResource);
		}
	}
	
	protected void initializePrimaryKeyJoinColumns() {
		for (ListIterator<NestableAnnotation> stream = this.javaResourcePersistentType.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME); stream.hasNext(); ) {
			this.specifiedPrimaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn((PrimaryKeyJoinColumnAnnotation) stream.next()));
		}
	}
	
	protected boolean shouldBuildDefaultPrimaryKeyJoinColumn() {
		return !containsSpecifiedPrimaryKeyJoinColumns();
	}

	protected void initializeDefaultPrimaryKeyJoinColumn() {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			return;
		}
		this.defaultPrimaryKeyJoinColumn = buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(this.javaResourcePersistentType));
	}	

	protected void initializeSpecifiedAttributeOverrides() {
		for (ListIterator<NestableAnnotation> stream = this.javaResourcePersistentType.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME); stream.hasNext(); ) {
			this.specifiedAttributeOverrides.add(buildAttributeOverride((AttributeOverrideAnnotation) stream.next()));
		}
	}
	
	protected void initializeVirtualAttributeOverrides() {
		for (PersistentAttribute persistentAttribute : CollectionTools.iterable(allOverridableAttributes())) {
			JavaAttributeOverride attributeOverride = getAttributeOverrideNamed(persistentAttribute.getName());
			if (attributeOverride == null) {
				this.virtualAttributeOverrides.add(buildVirtualAttributeOverride(persistentAttribute));
			}
		}
	}
	
	protected void initializeSpecifiedAssociationOverrides() {
		for (ListIterator<NestableAnnotation> stream = this.javaResourcePersistentType.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME); stream.hasNext(); ) {
			this.specifiedAssociationOverrides.add(buildAssociationOverride((AssociationOverrideAnnotation) stream.next()));
		}
	}
	
	protected void initializeDefaultAssociationOverrides() {
		for (Iterator<String> i = allOverridableAssociationNames(); i.hasNext(); ) {
			String associationName = i.next();
			JavaAssociationOverride associationOverride = getAssociationOverrideNamed(associationName);
			if (associationOverride == null) {
				this.virtualAssociationOverrides.add(buildAssociationOverride(new NullAssociationOverride(this.javaResourcePersistentType, associationName)));
			}
		}
	}

	protected void initializeNamedQueries() {
		for (ListIterator<NestableAnnotation> stream = this.javaResourcePersistentType.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME); stream.hasNext(); ) {
			this.namedQueries.add(buildNamedQuery((NamedQueryAnnotation) stream.next()));
		}
	}
	
	protected void initializeNamedNativeQueries() {
		for (ListIterator<NestableAnnotation> stream = this.javaResourcePersistentType.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME); stream.hasNext(); ) {
			this.namedNativeQueries.add(buildNamedNativeQuery((NamedNativeQueryAnnotation) stream.next()));
		}
	}

	//query for the inheritance resource every time on setters.
	//call one setter and the inheritanceResource could change. 
	//You could call more than one setter before this object has received any notification
	//from the java resource model
	protected InheritanceAnnotation getResourceInheritance() {
		return (InheritanceAnnotation) this.javaResourcePersistentType.getNonNullSupportingAnnotation(InheritanceAnnotation.ANNOTATION_NAME);
	}
	
	protected DiscriminatorValueAnnotation getResourceDiscriminatorValue() {
		return (DiscriminatorValueAnnotation) this.javaResourcePersistentType.getNonNullSupportingAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME);
	}

	protected void initializeIdClass() {
		IdClassAnnotation resourceIdClass = getResourceIdClass();
		if (resourceIdClass != null) {
			this.idClass = resourceIdClass.getValue();
		}
	}
	
	@Override
	protected EntityAnnotation getResourceMapping() {
		return (EntityAnnotation) super.getResourceMapping();
	}
	
	//****************** TypeMapping implemenation *******************
	
	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public boolean isMapped() {
		return true;
	}

	@Override
	public String getPrimaryTableName() {
		return this.getTable().getName();
	}

	@Override
	public org.eclipse.jpt.db.Table getPrimaryDbTable() {
		return getTable().getDbTable();
	}

	private static final org.eclipse.jpt.db.Table[] EMPTY_DB_TABLE_ARRAY = new org.eclipse.jpt.db.Table[0];

	@Override
	public org.eclipse.jpt.db.Table getDbTable(String tableName) {
		// the JPA platform searches database objects for us
		return this.getDataSource().selectDatabaseObjectForIdentifier(
						CollectionTools.array(this.associatedDbTablesIncludingInherited(), EMPTY_DB_TABLE_ARRAY),
						tableName
					);
	}

	protected Iterator<org.eclipse.jpt.db.Table> associatedDbTablesIncludingInherited() {
		return new FilteringIterator<org.eclipse.jpt.db.Table, org.eclipse.jpt.db.Table>(this.associatedDbTablesIncludingInherited_()) {
			@Override
			protected boolean accept(org.eclipse.jpt.db.Table t) {
				return t != null;
			}
		};
	}

	protected Iterator<org.eclipse.jpt.db.Table> associatedDbTablesIncludingInherited_() {
		return new TransformationIterator<Table, org.eclipse.jpt.db.Table>(this.associatedTablesIncludingInherited()) {
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


	//****************** IJavaTypeMapping implemenation *******************

	public String getAnnotationName() {
		return EntityAnnotation.ANNOTATION_NAME;
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
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
			JPA.ASSOCIATION_OVERRIDES);
	}

	//****************** IEntity implemenation *******************
	
	public String getName() {
		return (this.getSpecifiedName() == null) ? this.getDefaultName() : this.getSpecifiedName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		this.getResourceMapping().setName(newSpecifiedName);
		firePropertyChanged(Entity.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}
	
	protected void setSpecifiedName_(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(Entity.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}

	public String getDefaultName() {
		return this.defaultName;
	}
	
	protected/*private-protected*/ void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(Entity.DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}

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
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(index, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		secondaryTable.initialize(secondaryTableResource);
		fireItemAdded(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, secondaryTable);
		return secondaryTable;
	}

	public JavaSecondaryTable addSpecifiedSecondaryTable() {
		return this.addSpecifiedSecondaryTable(this.specifiedSecondaryTables.size());
	}
	
	protected void addSpecifiedSecondaryTable(int index, JavaSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.specifiedSecondaryTables, Entity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	protected void addSpecifiedSecondaryTable(JavaSecondaryTable secondaryTable) {
		this.addSpecifiedSecondaryTable(this.specifiedSecondaryTables.size(), secondaryTable);
	}
	
	public void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}
	
	public void removeSpecifiedSecondaryTable(int index) {
		JavaSecondaryTable removedSecondaryTable = this.specifiedSecondaryTables.remove(index);
		this.javaResourcePersistentType.removeSupportingAnnotation(index, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, removedSecondaryTable);
	}
	
	protected void removeSpecifiedSecondaryTable_(JavaSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.specifiedSecondaryTables, Entity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedSecondaryTables, targetIndex, sourceIndex);
		this.javaResourcePersistentType.moveSupportingAnnotation(targetIndex, sourceIndex, SecondaryTablesAnnotation.ANNOTATION_NAME);
		fireItemMoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, targetIndex, sourceIndex);		
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

	public boolean isDiscriminatorValueAllowed() {
		return this.discriminatorValueAllowed;
	}
	
	protected void setDiscriminatorValueAllowed(boolean newDiscriminatorValueAllowed) {
		boolean oldDiscriminatorValueAllowed = this.discriminatorValueAllowed;
		this.discriminatorValueAllowed = newDiscriminatorValueAllowed;
		firePropertyChanged(Entity.DISCRIMINATOR_VALUE_ALLOWED_PROPERTY, oldDiscriminatorValueAllowed, newDiscriminatorValueAllowed);
	}
	
	public boolean isDiscriminatorColumnAllowed() {
		return this.discriminatorColumnAllowed;
	}
	
	protected void setDiscriminatorColumnAllowed(boolean newDiscriminatorColumnAllowed) {
		boolean oldDiscriminatorColumnAllowed = this.discriminatorColumnAllowed;
		this.discriminatorColumnAllowed = newDiscriminatorColumnAllowed;
		firePropertyChanged(Entity.DISCRIMINATOR_COLUMN_ALLOWED_PROPERTY, oldDiscriminatorColumnAllowed, newDiscriminatorColumnAllowed);
	}
	
	public JavaTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists"); //$NON-NLS-1$
		}
		this.tableGenerator = getJpaFactory().buildJavaTableGenerator(this);
		TableGeneratorAnnotation tableGeneratorResource = (TableGeneratorAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		this.tableGenerator.initialize(tableGeneratorResource);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed"); //$NON-NLS-1$
		}
		JavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.javaResourcePersistentType.removeSupportingAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);
	}
	
	public JavaTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}
	
	protected void setTableGenerator(JavaTableGenerator newTableGenerator) {
		JavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}

	public JavaSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists"); //$NON-NLS-1$
		}
		this.sequenceGenerator = getJpaFactory().buildJavaSequenceGenerator(this);
		SequenceGeneratorAnnotation sequenceGeneratorResource = (SequenceGeneratorAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		this.sequenceGenerator.initialize(sequenceGeneratorResource);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed"); //$NON-NLS-1$
		}
		JavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.javaResourcePersistentType.removeSupportingAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator,null);
	}
	
	public JavaSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(JavaSequenceGenerator newSequenceGenerator) {
		JavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}
	
	protected final Iterator<JavaGenerator> generators() {
		ArrayList<JavaGenerator> generators = new ArrayList<JavaGenerator>();
		this.addGeneratorsTo(generators);
		return generators.iterator();
	}

	protected void addGeneratorsTo(ArrayList<JavaGenerator> generators) {
		if (this.sequenceGenerator != null) {
			generators.add(this.sequenceGenerator);
		}
		if (this.tableGenerator != null) {
			generators.add(this.tableGenerator);
		}
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
		firePropertyChanged(Entity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldPkJoinColumn, newPkJoinColumn);
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
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnResource = (PrimaryKeyJoinColumnAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		primaryKeyJoinColumn.initialize(pkJoinColumnResource);
		this.fireItemAdded(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		if (oldDefaultPkJoinColumn != null) {
			this.firePropertyChanged(Entity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldDefaultPkJoinColumn, null);
		}
		return primaryKeyJoinColumn;
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
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
			this.defaultPrimaryKeyJoinColumn = buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(this.javaResourcePersistentType));
		}
		this.javaResourcePersistentType.removeSupportingAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		fireItemRemoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
		if (this.defaultPrimaryKeyJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(Entity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, null, this.defaultPrimaryKeyJoinColumn);
		}
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.javaResourcePersistentType.moveSupportingAnnotation(targetIndex, sourceIndex, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		moveItemInList(targetIndex, sourceIndex, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);		
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<JavaAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<JavaAttributeOverride>(specifiedAttributeOverrides(), virtualAttributeOverrides());
	}
	
	public int attributeOverridesSize() {
		return this.specifiedAttributeOverridesSize() + this.virtualAttributeOverridesSize();
	}
	
	public ListIterator<JavaAttributeOverride> virtualAttributeOverrides() {
		return new CloneListIterator<JavaAttributeOverride>(this.virtualAttributeOverrides);
	}
	
	public int virtualAttributeOverridesSize() {
		return this.virtualAttributeOverrides.size();
	}
	
	public ListIterator<JavaAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<JavaAttributeOverride>(this.specifiedAttributeOverrides);
	}
	
	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	protected JavaAttributeOverride addSpecifiedAttributeOverride(int index) {
		JavaAttributeOverride attributeOverride = getJpaFactory().buildJavaAttributeOverride(this, createAttributeOverrideOwner());
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(index, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverride.initialize(attributeOverrideResource);
		this.fireItemAdded(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}
	
	protected JavaAttributeOverride setAttributeOverrideVirtual(boolean virtual, JavaAttributeOverride attributeOverride) {
		// Add a new attribute override
		if (virtual) {
			return setAttributeOverrideVirtual(attributeOverride);
		}
		return setAttributeOverrideSpecified(attributeOverride);
	}
	
	protected JavaAttributeOverride setAttributeOverrideVirtual(JavaAttributeOverride attributeOverride) {
		int index = this.specifiedAttributeOverrides.indexOf(attributeOverride);
		this.specifiedAttributeOverrides.remove(index);
		String attributeOverrideName = attributeOverride.getName();
		//add the virtual attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		JavaAttributeOverride virtualAttributeOverride = null;
		if (attributeOverrideName != null) {
			for (PersistentAttribute persistentAttribute : CollectionTools.iterable(allOverridableAttributes())) {
				if (persistentAttribute.getName().equals(attributeOverrideName)) {
					//store the virtualAttributeOverride so we can fire change notification later
					virtualAttributeOverride = buildVirtualAttributeOverride(persistentAttribute);
					this.virtualAttributeOverrides.add(virtualAttributeOverride);
					break;
				}
			}
		}

		this.javaResourcePersistentType.removeSupportingAnnotation(index, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		
		if (virtualAttributeOverride != null) {
			fireItemAdded(Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, virtualAttributeOverridesSize() - 1, virtualAttributeOverride);
		}
		return virtualAttributeOverride;
	}
	
	protected JavaAttributeOverride setAttributeOverrideSpecified(JavaAttributeOverride oldAttributeOverride) {
		int index = specifiedAttributeOverridesSize();
		JavaAttributeOverride newAttributeOverride = getJpaFactory().buildJavaAttributeOverride(this, createAttributeOverrideOwner());
		this.specifiedAttributeOverrides.add(index, newAttributeOverride);
		
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(index, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		newAttributeOverride.initialize(attributeOverrideResource);
		
		int defaultIndex = this.virtualAttributeOverrides.indexOf(oldAttributeOverride);
		this.virtualAttributeOverrides.remove(defaultIndex);

		newAttributeOverride.setName(oldAttributeOverride.getName());
		newAttributeOverride.getColumn().setSpecifiedName(oldAttributeOverride.getColumn().getName());
		
		this.fireItemRemoved(Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, defaultIndex, oldAttributeOverride);
		this.fireItemAdded(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, newAttributeOverride);		

		return newAttributeOverride;
	}
	
	protected AttributeOverride.Owner createAttributeOverrideOwner() {
		return new AttributeOverrideOwner();
	}
	
	protected void addSpecifiedAttributeOverride(int index, JavaAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void addSpecifiedAttributeOverride(JavaAttributeOverride attributeOverride) {
		this.addSpecifiedAttributeOverride(this.specifiedAttributeOverrides.size(), attributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride_(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.javaResourcePersistentType.moveSupportingAnnotation(targetIndex, sourceIndex, AttributeOverridesAnnotation.ANNOTATION_NAME);
		fireItemMoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addVirtualAttributeOverride(JavaAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.virtualAttributeOverrides, Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeVirtualAttributeOverride(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.virtualAttributeOverrides, Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}

	public JavaAttributeOverride getAttributeOverrideNamed(String name) {
		return (JavaAttributeOverride) getOverrideNamed(name, attributeOverrides());
	}

	public boolean containsAttributeOverride(String name) {
		return containsOverride(name, attributeOverrides());
	}

	public boolean containsDefaultAttributeOverride(String name) {
		return containsOverride(name, virtualAttributeOverrides());
	}

	public boolean containsSpecifiedAttributeOverride(String name) {
		return containsOverride(name, specifiedAttributeOverrides());
	}

	public JavaAssociationOverride getAssociationOverrideNamed(String name) {
		return (JavaAssociationOverride) getOverrideNamed(name, associationOverrides());
	}

	public boolean containsAssociationOverride(String name) {
		return containsOverride(name, associationOverrides());
	}

	public boolean containsSpecifiedAssociationOverride(String name) {
		return containsOverride(name, specifiedAssociationOverrides());
	}

	public boolean containsDefaultAssociationOverride(String name) {
		return containsOverride(name, virtualAssociationOverrides());
	}

	protected BaseOverride getOverrideNamed(String name, ListIterator<? extends BaseOverride> overrides) {
		for (BaseOverride override : CollectionTools.iterable(overrides)) {
			String overrideName = override.getName();
			if (overrideName == null && name == null) {
				return override;
			}
			if (overrideName != null && overrideName.equals(name)) {
				return override;
			}
		}
		return null;
	}

	protected boolean containsOverride(String name, ListIterator<? extends BaseOverride> overrides) {
		return getOverrideNamed(name, overrides) != null;
	}


	@SuppressWarnings("unchecked")
	public ListIterator<JavaAssociationOverride> associationOverrides() {
		return new CompositeListIterator<JavaAssociationOverride>(specifiedAssociationOverrides(), virtualAssociationOverrides());
	}
	
	public int associationOverridesSize() {
		return this.specifiedAssociationOverridesSize() + this.virtualAssociationOverridesSize();
	}

	public  ListIterator<JavaAssociationOverride> virtualAssociationOverrides() {
		return new CloneListIterator<JavaAssociationOverride>(this.virtualAssociationOverrides);
	}
	
	public int virtualAssociationOverridesSize() {
		return this.virtualAssociationOverrides.size();
	}
	
	public ListIterator<JavaAssociationOverride> specifiedAssociationOverrides() {
		return new CloneListIterator<JavaAssociationOverride>(this.specifiedAssociationOverrides);
	}
	
	public int specifiedAssociationOverridesSize() {
		return this.specifiedAssociationOverrides.size();
	}

	public JavaAssociationOverride addSpecifiedAssociationOverride(int index) {
		JavaAssociationOverride associationOverride = getJpaFactory().buildJavaAssociationOverride(this, createAssociationOverrideOwner());
		this.specifiedAssociationOverrides.add(index, associationOverride);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(index, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverride.initialize(associationOverrideResource);
		this.fireItemAdded(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		return associationOverride;
	}
	
	protected AssociationOverride.Owner createAssociationOverrideOwner() {
		return new AssociationOverrideOwner();
	}
	
	protected void addSpecifiedAssociationOverride(int index, JavaAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected void addSpecifiedAssociationOverride(JavaAssociationOverride associationOverride) {
		this.addSpecifiedAssociationOverride(this.specifiedAssociationOverrides.size(), associationOverride);
	}
	
	protected void removeSpecifiedAssociationOverride_(JavaAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAssociationOverrides, targetIndex, sourceIndex);
		this.javaResourcePersistentType.moveSupportingAnnotation(targetIndex, sourceIndex, AssociationOverridesAnnotation.ANNOTATION_NAME);
		fireItemMoved(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	protected JavaAssociationOverride setAssociationOverrideVirtual(boolean virtual, JavaAssociationOverride associationOverride) {
		// Add a new attribute override
		if (virtual) {
			return setAssociationOverrideVirtual(associationOverride);
		}
		return setAssociationOverrideSpecified(associationOverride);
	}
	
	protected JavaAssociationOverride setAssociationOverrideVirtual(JavaAssociationOverride associationOverride) {
		int index = this.specifiedAssociationOverrides.indexOf(associationOverride);
		this.specifiedAssociationOverrides.remove(index);
		String associationOverrideName = associationOverride.getName();
		//add the virtual attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		JavaAssociationOverride virtualAssociationOverride = null;
		if (associationOverrideName != null) {
			for (PersistentAttribute persistentAttribute : CollectionTools.iterable(allOverridableAssociations())) {
				if (persistentAttribute.getName().equals(associationOverrideName)) {
					//store the virtualAssociationOverride so we can fire change notification later
					virtualAssociationOverride = buildAssociationOverride(new NullAssociationOverride(this.javaResourcePersistentType, associationOverrideName));
					this.virtualAssociationOverrides.add(virtualAssociationOverride);
					break;
				}
			}
		}

		this.javaResourcePersistentType.removeSupportingAnnotation(index, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		
		if (virtualAssociationOverride != null) {
			fireItemAdded(Entity.VIRTUAL_ASSOCIATION_OVERRIDES_LIST, virtualAssociationOverridesSize() - 1, virtualAssociationOverride);
		}
		return virtualAssociationOverride;
	}
	
	protected JavaAssociationOverride setAssociationOverrideSpecified(JavaAssociationOverride oldAssociationOverride) {
		int index = specifiedAssociationOverridesSize();
		JavaAssociationOverride newAssociationOverride = getJpaFactory().buildJavaAssociationOverride(this, createAssociationOverrideOwner());
		this.specifiedAssociationOverrides.add(index, newAssociationOverride);
		
		AssociationOverrideAnnotation attributeOverrideResource = (AssociationOverrideAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(index, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		newAssociationOverride.initialize(attributeOverrideResource);
		
		int virtualIndex = this.virtualAssociationOverrides.indexOf(oldAssociationOverride);
		this.virtualAssociationOverrides.remove(virtualIndex);

		newAssociationOverride.setName(oldAssociationOverride.getName());
		
		this.fireItemRemoved(Entity.VIRTUAL_ASSOCIATION_OVERRIDES_LIST, virtualIndex, oldAssociationOverride);
		this.fireItemAdded(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, newAssociationOverride);		

		return newAssociationOverride;
	}
	
	protected void addVirtualAssociationOverride(JavaAssociationOverride associationOverride) {
		addItemToList(associationOverride, this.virtualAssociationOverrides, Entity.VIRTUAL_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected void removeVirtualAssociationOverride(JavaAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.virtualAssociationOverrides, Entity.VIRTUAL_ASSOCIATION_OVERRIDES_LIST);
	}

	public ListIterator<JavaNamedQuery> namedQueries() {
		return new CloneListIterator<JavaNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public JavaNamedQuery addNamedQuery(int index) {
		JavaNamedQuery namedQuery = getJpaFactory().buildJavaNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		NamedQueryAnnotation namedQueryAnnotation = (NamedQueryAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		namedQuery.initialize(namedQueryAnnotation);
		fireItemAdded(NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, JavaNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	protected void addNamedQuery(JavaNamedQuery namedQuery) {
		this.addNamedQuery(this.namedQueries.size(), namedQuery);
	}
	
	public void removeNamedQuery(NamedQuery namedQuery) {
		removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}
	
	public void removeNamedQuery(int index) {
		JavaNamedQuery removedNamedQuery = this.namedQueries.remove(index);
		this.javaResourcePersistentType.removeSupportingAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(NAMED_QUERIES_LIST, index, removedNamedQuery);
	}	
	
	protected void removeNamedQuery_(JavaNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
		this.javaResourcePersistentType.moveSupportingAnnotation(targetIndex, sourceIndex, NamedQueriesAnnotation.ANNOTATION_NAME);
		fireItemMoved(NAMED_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<JavaNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<JavaNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public JavaNamedNativeQuery addNamedNativeQuery(int index) {
		JavaNamedNativeQuery namedNativeQuery = getJpaFactory().buildJavaNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		NamedNativeQueryAnnotation namedNativeQueryAnnotation = (NamedNativeQueryAnnotation) this.javaResourcePersistentType.addSupportingAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		namedNativeQuery.initialize(namedNativeQueryAnnotation);		
		fireItemAdded(NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, JavaNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
	
	protected void addNamedNativeQuery(JavaNamedNativeQuery namedNativeQuery) {
		this.addNamedNativeQuery(this.namedNativeQueries.size(), namedNativeQuery);
	}
	
	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}
	
	public void removeNamedNativeQuery(int index) {
		JavaNamedNativeQuery removedNamedNativeQuery = this.namedNativeQueries.remove(index);
		this.javaResourcePersistentType.removeSupportingAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(NAMED_NATIVE_QUERIES_LIST, index, removedNamedNativeQuery);
	}	
	
	protected void removeNamedNativeQuery_(JavaNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex);
		this.javaResourcePersistentType.moveSupportingAnnotation(targetIndex, sourceIndex, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		fireItemMoved(NAMED_NATIVE_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<JavaQuery> queries() {
		return new CompositeIterator<JavaQuery>(this.namedNativeQueries(), this.namedQueries());
	}

	public String getIdClass() {
		return this.idClass;
	}
	
	public void setIdClass(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		if (newIdClass != oldIdClass) {
			if (newIdClass != null) {
				if (getResourceIdClass() == null) {
					addResourceIdClass();
				}
				getResourceIdClass().setValue(newIdClass);
			}
			else {
				removeResourceIdClass();
			}
		}
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}

	protected IdClassAnnotation getResourceIdClass() {
		return (IdClassAnnotation) this.javaResourcePersistentType.getSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	protected void addResourceIdClass() {
		this.javaResourcePersistentType.addSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	protected void removeResourceIdClass() {
		this.javaResourcePersistentType.removeSupportingAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}

	public Entity getParentEntity() {
		for (Iterator<PersistentType> stream = getPersistentType().ancestors(); stream.hasNext();) {
			TypeMapping typeMapping = stream.next().getMapping();
			if (typeMapping instanceof Entity) {
				return (Entity) typeMapping;
			}
		}
		return this;
	}

	public Entity getRootEntity() {
		Entity rootEntity = this;
		for (Iterator<PersistentType> stream = getPersistentType().inheritanceHierarchy(); stream.hasNext();) {
			PersistentType persistentType = stream.next();
			if (persistentType.getMapping() instanceof Entity) {
				rootEntity = (Entity) persistentType.getMapping();
			}
		}
		return rootEntity;
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
	 * Return whether the entity is a descendant in (as opposed to the root of)
	 * an inheritance hierarchy.
	 */
	protected boolean isDescendant() {
		return ! this.isRoot();
	}

	/**
	 * Return whether the entity is the top of an inheritance hierarchy.
	 */
	protected boolean isRoot() {
		return this == this.getRootEntity();
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
		return getPrimaryKeyColumnName(getPersistentType().allAttributes());
	}
	
	//copied in GenericOrmEntity to avoid an API change for fixing bug 229423 in RC1
	public String getPrimaryKeyColumnName(Iterator<PersistentAttribute> attributes) {
		String pkColumnName = null;
		for (Iterator<PersistentAttribute> stream = attributes; stream.hasNext();) {
			PersistentAttribute attribute = stream.next();
			String name = attribute.getPrimaryKeyColumnName();
			if (name != null) {
				//if the attribute is a primary key then we need to check if there is an attribute override
				//and use its column name instead (bug 229423)
				AttributeOverride attributeOverride = getAttributeOverrideNamed(attribute.getName());
				if (attributeOverride != null) {
					name = attributeOverride.getColumn().getName();
				}
			}
			if (pkColumnName == null) {
				pkColumnName = name;
			}
			else if (name != null) {
				// if we encounter a composite primary key, return null
				return null;
			}
		}
		// if we encounter only a single primary key column name, return it
		return pkColumnName;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return ! CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
	}
	
	public boolean shouldValidateDbInfo() {
		return !isAbstractTablePerClass();
	}
	
	@Override
	public Iterator<Table> associatedTables() {
		return new CompositeIterator<Table>(this.getTable(), this.secondaryTables());
	}

	@Override
	public Iterator<Table> associatedTablesIncludingInherited() {
		return new CompositeIterator<Table>(new TransformationIterator<TypeMapping, Iterator<Table>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<Table> transform(TypeMapping mapping) {
				return new FilteringIterator<Table, Table>(mapping.associatedTables()) {
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
		return new FilteringIterator<String, String>(this.tableNames(tables)) {
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
	protected Iterator<TypeMapping> inheritanceHierarchy() {
		return new TransformationIterator<PersistentType, TypeMapping>(getPersistentType().inheritanceHierarchy()) {
			@Override
			protected TypeMapping transform(PersistentType type) {
				return type.getMapping();
			}
		};
	}

	@Override
	public Iterator<String> allOverridableAttributeNames() {
		return new CompositeIterator<String>(new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(TypeMapping mapping) {
				return mapping.overridableAttributeNames();
			}
		});
	}


	@Override
	public Iterator<PersistentAttribute> allOverridableAttributes() {
		return new CompositeIterator<PersistentAttribute>(new TransformationIterator<TypeMapping, Iterator<PersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<PersistentAttribute> transform(TypeMapping mapping) {
				return mapping.overridableAttributes();
			}
		});
	}

	@Override
	public Iterator<PersistentAttribute> allOverridableAssociations() {
		return new CompositeIterator<PersistentAttribute>(new TransformationIterator<TypeMapping, Iterator<PersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<PersistentAttribute> transform(TypeMapping mapping) {
				return mapping.overridableAssociations();
			}
		});
	}
	
	@Override
	public Iterator<String> allOverridableAssociationNames() {
		return new CompositeIterator<String>(new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(TypeMapping mapping) {
				return mapping.overridableAssociationNames();
			}
		});
	}
	
	@Override
	public void update(JavaResourcePersistentType resourcePersistentType) {
		super.update(resourcePersistentType);
		
		this.setSpecifiedName_(this.getResourceName());
		this.setDefaultName(this.getResourceDefaultName());
		
		this.updateInheritance(getResourceInheritance());
		this.setDiscriminatorColumnAllowed(buildDiscriminatorColumnIsAllowed());
		this.updateDiscriminatorColumn();
		this.setDiscriminatorValueAllowed(buildDiscriminatorValueIsAllowed());
		this.updateDiscriminatorValue(getResourceDiscriminatorValue());
		this.updateTable();
		this.updateSecondaryTables();
		this.updateTableGenerator();
		this.updateSequenceGenerator();
		this.updateSpecifiedPrimaryKeyJoinColumns();
		this.updateDefaultPrimaryKeyJoinColumn();
		this.updateSpecifiedAttributeOverrides();
		this.updateVirtualAttributeOverrides();
		this.updateSpecifiedAssociationOverrides();
		this.updateVirtualAssociationOverrides();
		this.updateNamedQueries();
		this.updateNamedNativeQueries();
		this.updateIdClass();
	}
		
	protected String getResourceName() {
		return this.getResourceMapping().getName();
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
	
	protected void updateDiscriminatorColumn() {
		getDiscriminatorColumn().update(this.javaResourcePersistentType);
	}
	
	protected void updateDiscriminatorValue(DiscriminatorValueAnnotation discriminatorValueResource) {
		this.setSpecifiedDiscriminatorValue_(discriminatorValueResource.getValue());
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
		if (!isDiscriminatorValueAllowed()) {
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
	
	protected boolean buildDiscriminatorValueIsAllowed() {
		return !isTablePerClass() && !isAbstract();
	}
	
	protected boolean buildDiscriminatorColumnIsAllowed() {
		return !isTablePerClass() && isRoot();
	}
	
	protected void updateSecondaryTables() {
		ListIterator<JavaSecondaryTable> secondaryTables = specifiedSecondaryTables();
		ListIterator<NestableAnnotation> resourceSecondaryTables = this.javaResourcePersistentType.supportingAnnotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTablesAnnotation.ANNOTATION_NAME);
		
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

	protected void updateTableGenerator() {
		TableGeneratorAnnotation tableGeneratorResource = getResourceTableGenerator();
		if (tableGeneratorResource == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(buildTableGenerator(tableGeneratorResource));
			}
			else {
				getTableGenerator().update(tableGeneratorResource);
			}
		}
	}
	
	protected JavaTableGenerator buildTableGenerator(TableGeneratorAnnotation tableGeneratorResource) {
		JavaTableGenerator generator = getJpaFactory().buildJavaTableGenerator(this);
		generator.initialize(tableGeneratorResource);
		return generator;
	}
	
	protected TableGeneratorAnnotation getResourceTableGenerator() {
		return (TableGeneratorAnnotation) this.javaResourcePersistentType.getSupportingAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}

	protected void updateSequenceGenerator() {
		SequenceGeneratorAnnotation sequenceGeneratorResource = getResourceSequenceGenerator();
		if (sequenceGeneratorResource == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(buildSequenceGenerator(sequenceGeneratorResource));
			}
			else {
				getSequenceGenerator().update(sequenceGeneratorResource);
			}
		}
	}
	
	protected JavaSequenceGenerator buildSequenceGenerator(SequenceGeneratorAnnotation sequenceGeneratorResource) {
		JavaSequenceGenerator generator = getJpaFactory().buildJavaSequenceGenerator(this);
		generator.initialize(sequenceGeneratorResource);
		return generator;
	}
	
	protected SequenceGeneratorAnnotation getResourceSequenceGenerator() {
		return (SequenceGeneratorAnnotation) this.javaResourcePersistentType.getSupportingAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}

	
	protected void updateSpecifiedPrimaryKeyJoinColumns() {
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<NestableAnnotation> resourcePrimaryKeyJoinColumns = this.javaResourcePersistentType.supportingAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
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
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		primaryKeyJoinColumn.initialize(primaryKeyJoinColumnResource);
		return primaryKeyJoinColumn;
	}

	protected void updateDefaultPrimaryKeyJoinColumn() {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			setDefaultPrimaryKeyJoinColumn(null);
			return;
		}
		if (getDefaultPrimaryKeyJoinColumn() == null) {
			this.setDefaultPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(this.javaResourcePersistentType)));
		}
		else {
			this.defaultPrimaryKeyJoinColumn.update(new NullPrimaryKeyJoinColumn(this.javaResourcePersistentType));
		}
	}
		
	protected void updateSpecifiedAttributeOverrides() {
		ListIterator<JavaAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<NestableAnnotation> resourceAttributeOverrides = this.javaResourcePersistentType.supportingAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		while (attributeOverrides.hasNext()) {
			JavaAttributeOverride attributeOverride = attributeOverrides.next();
			if (resourceAttributeOverrides.hasNext()) {
				attributeOverride.update((AttributeOverrideAnnotation) resourceAttributeOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride_(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(buildAttributeOverride((AttributeOverrideAnnotation) resourceAttributeOverrides.next()));
		}	
	}
	
	protected JavaAttributeOverride buildAttributeOverride(AttributeOverrideAnnotation attributeOverrideResource) {
		JavaAttributeOverride attributeOverride = getJpaFactory().buildJavaAttributeOverride(this, createAttributeOverrideOwner());
		attributeOverride.initialize(attributeOverrideResource);
		return attributeOverride;
	}
	
	protected JavaAttributeOverride buildVirtualAttributeOverride(PersistentAttribute attribute) {
		return buildAttributeOverride(buildVirtualAttributeOverrideResource(attribute));
	}
	
	protected VirtualAttributeOverride buildVirtualAttributeOverrideResource(PersistentAttribute attribute) {
		ColumnMapping columnMapping = (ColumnMapping) attribute.getMapping();
		return new VirtualAttributeOverride(this.javaResourcePersistentType, attribute.getName(), columnMapping.getColumn());
	}

	protected void updateVirtualAttributeOverrides( ) {
		for (PersistentAttribute persistentAttribute : CollectionTools.iterable(allOverridableAttributes())) {
			JavaAttributeOverride attributeOverride = getAttributeOverrideNamed(persistentAttribute.getName());
			if (attributeOverride == null) {
				addVirtualAttributeOverride(buildVirtualAttributeOverride(persistentAttribute));
			}
			else if (attributeOverride.isVirtual()) {
				attributeOverride.update(buildVirtualAttributeOverrideResource(persistentAttribute));
			}
		}
		
		Collection<String> attributeNames = CollectionTools.collection(allOverridableAttributeNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		for (JavaAttributeOverride attributeOverride : CollectionTools.iterable(virtualAttributeOverrides())) {
			if (!attributeNames.contains(attributeOverride.getName())
				|| containsSpecifiedAttributeOverride(attributeOverride.getName())) {
				removeVirtualAttributeOverride(attributeOverride);
			}
		}
	}

	protected void updateSpecifiedAssociationOverrides() {
		ListIterator<JavaAssociationOverride> associationOverrides = specifiedAssociationOverrides();
		ListIterator<NestableAnnotation> resourceAssociationOverrides = this.javaResourcePersistentType.supportingAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		while (associationOverrides.hasNext()) {
			JavaAssociationOverride associationOverride = associationOverrides.next();
			if (resourceAssociationOverrides.hasNext()) {
				associationOverride.update((AssociationOverrideAnnotation) resourceAssociationOverrides.next());
			}
			else {
				removeSpecifiedAssociationOverride_(associationOverride);
			}
		}
		
		while (resourceAssociationOverrides.hasNext()) {
			addSpecifiedAssociationOverride(buildAssociationOverride((AssociationOverrideAnnotation) resourceAssociationOverrides.next()));
		}	
	}
	
	protected JavaAssociationOverride buildAssociationOverride(AssociationOverrideAnnotation associationOverrideResource) {
		JavaAssociationOverride associationOverride = getJpaFactory().buildJavaAssociationOverride(this, createAssociationOverrideOwner());
		associationOverride.initialize(associationOverrideResource);
		return associationOverride;
	}
	
	protected void updateVirtualAssociationOverrides() {
		for (Iterator<String> i = allOverridableAssociationNames(); i.hasNext(); ) {
			String associationName = i.next();
			JavaAssociationOverride associationOverride = getAssociationOverrideNamed(associationName);
			if (associationOverride == null) {
				associationOverride = buildAssociationOverride(new NullAssociationOverride(this.javaResourcePersistentType, associationName));
				addVirtualAssociationOverride(associationOverride);
			}
			else if (associationOverride.isVirtual()) {
				associationOverride.update(new NullAssociationOverride(this.javaResourcePersistentType, associationName));
			}
		}
		
		Collection<String> associationNames = CollectionTools.collection(allOverridableAssociationNames());
	
		//remove any default mappings that are not included in the associationNames collection
		for (JavaAssociationOverride associationOverride : CollectionTools.iterable(virtualAssociationOverrides())) {
			if (!associationNames.contains(associationOverride.getName())
				|| containsSpecifiedAssociationOverride(associationOverride.getName())) {
				removeVirtualAssociationOverride(associationOverride);
			}
		}
	}

	protected void updateNamedQueries() {
		ListIterator<JavaNamedQuery> queries = namedQueries();
		ListIterator<NestableAnnotation> resourceNamedQueries = this.javaResourcePersistentType.supportingAnnotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		
		while (queries.hasNext()) {
			JavaNamedQuery namedQuery = queries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update((NamedQueryAnnotation) resourceNamedQueries.next());
			}
			else {
				removeNamedQuery_(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(buildNamedQuery((NamedQueryAnnotation) resourceNamedQueries.next()));
		}
	}
	
	protected void updateNamedNativeQueries() {
		ListIterator<JavaNamedNativeQuery> queries = namedNativeQueries();
		ListIterator<NestableAnnotation> resourceNamedNativeQueries = this.javaResourcePersistentType.supportingAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		
		while (queries.hasNext()) {
			JavaNamedNativeQuery namedQuery = queries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update((NamedNativeQueryAnnotation) resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery_(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(buildNamedNativeQuery((NamedNativeQueryAnnotation) resourceNamedNativeQueries.next()));
		}	
	}
	
	
	protected JavaNamedQuery buildNamedQuery(NamedQueryAnnotation namedQueryResource) {
		JavaNamedQuery namedQuery = getJpaFactory().buildJavaNamedQuery(this);
		namedQuery.initialize(namedQueryResource);
		return namedQuery;
	}
	
	protected JavaNamedNativeQuery buildNamedNativeQuery(NamedNativeQueryAnnotation namedNativeQueryResource) {
		JavaNamedNativeQuery namedNativeQuery = getJpaFactory().buildJavaNamedNativeQuery(this);
		namedNativeQuery.initialize(namedNativeQueryResource);
		return namedNativeQuery;
	}

	protected void updateIdClass( ) {
		IdClassAnnotation annotation = getResourceIdClass();
		if (annotation != null) {
			setIdClass_(annotation.getValue());
		}
		else {
			setIdClass_(null);
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
		for (JavaAttributeOverride override : CollectionTools.iterable(this.attributeOverrides())) {
			result = override.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (JavaAssociationOverride override : CollectionTools.iterable(this.associationOverrides())) {
			result = override.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		result = this.getDiscriminatorColumn().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.getTableGenerator() != null) {
			result = this.getTableGenerator().javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		if (this.getSequenceGenerator() != null) {
			result = this.getSequenceGenerator().javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	
	//********** Validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		this.validateTable(messages, reporter, astRoot);
		this.validateId(messages, astRoot);
		this.validateInheritance(messages, reporter, astRoot);
		this.validateGenerators(messages, astRoot);
		this.validateQueries(messages, astRoot);
		
		for (Iterator<JavaSecondaryTable> stream = this.specifiedSecondaryTables(); stream.hasNext();) {
			stream.next().validate(messages, reporter, astRoot);
		}

		for (Iterator<JavaAttributeOverride> stream = this.attributeOverrides(); stream.hasNext();) {
			stream.next().validate(messages, reporter, astRoot);
		}
		
		for (Iterator<JavaAssociationOverride> stream = this.associationOverrides(); stream.hasNext();) {
			stream.next().validate(messages, reporter, astRoot);
		}
		
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
	
	protected void validateId(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.entityHasNoId()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ENTITY_NO_ID,
					new String[] {this.getName()},
					this,
					this.getValidationTextRange(astRoot)
				)
			);
		}
	}
	
	protected void validateInheritance(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		validateDiscriminatorColumn(messages, reporter, astRoot);
		validateDiscriminatorValue(messages, reporter, astRoot);
	}
	
	protected void validateDiscriminatorColumn(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (isDiscriminatorColumnAllowed()) {
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
	
	protected void validateDiscriminatorValue(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (!isDiscriminatorValueAllowed() && getSpecifiedDiscriminatorValue() != null) {
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
	
	protected TextRange getDiscriminatorValueTextRange(CompilationUnit astRoot) {
		return getResourceDiscriminatorValue().getTextRange(astRoot);
	}
	
	protected TextRange getDiscriminatorColumnTextRange(CompilationUnit astRoot) {
		return getDiscriminatorColumn().getValidationTextRange(astRoot);
	}

	protected boolean entityHasNoId() {
		return ! this.entityHasId();
	}

	protected boolean entityHasId() {
		for (Iterator<PersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext(); ) {
			if (stream.next().isIdAttribute()) {
				return true;
			}
		}
		return false;
	}
	
	protected void validateGenerators(List<IMessage> messages, CompilationUnit astRoot) {
		for (Iterator<JavaGenerator> localGenerators = this.generators(); localGenerators.hasNext(); ) {
			JavaGenerator localGenerator = localGenerators.next();
			for (Iterator<Generator> globalGenerators = this.getPersistenceUnit().generators(); globalGenerators.hasNext(); ) {
				if (localGenerator.duplicates(globalGenerators.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {localGenerator.getName()},
							localGenerator,
							localGenerator.getNameTextRange(astRoot)
						)
					);
				}
			}
		}
	}
	
	protected void validateQueries(List<IMessage> messages, CompilationUnit astRoot) {
		for (Iterator<JavaQuery> localQueries = this.queries(); localQueries.hasNext(); ) {
			JavaQuery localQuery = localQueries.next();
			for (Iterator<Query> globalQueries = this.getPersistenceUnit().queries(); globalQueries.hasNext(); ) {
				if (localQuery.duplicates(globalQueries.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.QUERY_DUPLICATE_NAME,
							new String[] {localQuery.getName()},
							localQuery,
							localQuery.getNameTextRange(astRoot)
						)
					);
				}
			}
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
			return AbstractJavaEntity.this.getParentEntity().getPrimaryKeyColumnName();
		}
	}
	

	// ********** attribute override owner **********

	class AttributeOverrideOwner implements AttributeOverride.Owner {

		public ColumnMapping getColumnMapping(String attributeName) {
			if (attributeName == null) {
				return null;
			}
			for (Iterator<PersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
				PersistentAttribute persAttribute = stream.next();
				if (attributeName.equals(persAttribute.getName())) {
					if (persAttribute.getMapping() instanceof ColumnMapping) {
						return (ColumnMapping) persAttribute.getMapping();
					}
				}
			}
			return null;
		}

		public boolean isVirtual(BaseOverride override) {
			return AbstractJavaEntity.this.virtualAttributeOverrides.contains(override);
		}

		public BaseOverride setVirtual(boolean virtual, BaseOverride attributeOverride) {
			return AbstractJavaEntity.this.setAttributeOverrideVirtual(virtual, (JavaAttributeOverride) attributeOverride);
		}

		public TypeMapping getTypeMapping() {
			return AbstractJavaEntity.this;
		}

		public TextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
	}


	// ********** association override owner **********

	class AssociationOverrideOwner implements AssociationOverride.Owner {

		public RelationshipMapping getRelationshipMapping(String attributeName) {
			if (attributeName == null) {
				return null;
			}
			for (Iterator<PersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
				PersistentAttribute persAttribute = stream.next();
				if (attributeName.equals(persAttribute.getName())) {
					if (persAttribute.getMapping() instanceof RelationshipMapping) {
						return (RelationshipMapping) persAttribute.getMapping();
					}
				}
			}
			return null;
		}

		public boolean isVirtual(BaseOverride override) {
			return AbstractJavaEntity.this.virtualAssociationOverrides.contains(override);
		}
		
		public BaseOverride setVirtual(boolean virtual, BaseOverride attributeOverride) {
			return AbstractJavaEntity.this.setAssociationOverrideVirtual(virtual, (JavaAssociationOverride) attributeOverride);
		}

		public TypeMapping getTypeMapping() {
			return AbstractJavaEntity.this;
		}

		public TextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
