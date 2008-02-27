/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAbstractJoinColumn;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaNamedColumn;
import org.eclipse.jpt.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.internal.resource.java.NullAssociationOverride;
import org.eclipse.jpt.core.internal.resource.java.NullAttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.NullColumn;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverrides;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.resource.java.DiscriminatorValue;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.core.resource.java.IdClass;
import org.eclipse.jpt.core.resource.java.Inheritance;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NamedNativeQueries;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueries;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumns;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTables;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaEntity extends AbstractJavaTypeMapping implements JavaEntity
{
	protected EntityAnnotation entityResource;
	
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

	protected JavaSequenceGenerator sequenceGenerator;

	protected JavaTableGenerator tableGenerator;

	protected final List<JavaAttributeOverride> specifiedAttributeOverrides;

	protected final List<JavaAttributeOverride> defaultAttributeOverrides;

	protected final List<JavaAssociationOverride> specifiedAssociationOverrides;

	protected final List<JavaAssociationOverride> defaultAssociationOverrides;

	protected final List<JavaNamedQuery> namedQueries;

	protected final List<JavaNamedNativeQuery> namedNativeQueries;

	protected String idClass;

	
	public GenericJavaEntity(JavaPersistentType parent) {
		super(parent);
		this.table = jpaFactory().buildJavaTable(this);
		this.discriminatorColumn = createJavaDiscriminatorColumn();
		this.specifiedSecondaryTables = new ArrayList<JavaSecondaryTable>();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<JavaPrimaryKeyJoinColumn>();
		this.specifiedAttributeOverrides = new ArrayList<JavaAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<JavaAttributeOverride>();
		this.namedQueries = new ArrayList<JavaNamedQuery>();
		this.namedNativeQueries = new ArrayList<JavaNamedNativeQuery>();
		this.specifiedAssociationOverrides = new ArrayList<JavaAssociationOverride>();
		this.defaultAssociationOverrides = new ArrayList<JavaAssociationOverride>();
	}
	
	protected JavaAbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}
	
	protected JavaDiscriminatorColumn createJavaDiscriminatorColumn() {
		return jpaFactory().buildJavaDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected JavaNamedColumn.Owner buildDiscriminatorColumnOwner() {
		return new JavaNamedColumn.Owner(){
			public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
				return GenericJavaEntity.this.dbTable(tableName);
			}

			public TextRange validationTextRange(CompilationUnit astRoot) {
				return GenericJavaEntity.this.validationTextRange(astRoot);
			}

			public TypeMapping typeMapping() {
				return GenericJavaEntity.this;
			}
			
			public String defaultColumnName() {
				return DiscriminatorColumn.DEFAULT_NAME;
			}
		};
	}

	@Override
	public void initializeFromResource(JavaResourcePersistentType persistentTypeResource) {
		super.initializeFromResource(persistentTypeResource);
		this.entityResource = (EntityAnnotation) persistentTypeResource.mappingAnnotation(EntityAnnotation.ANNOTATION_NAME);
		
		this.specifiedName = this.specifiedName(this.entityResource);
		this.defaultName = this.defaultName(persistentTypeResource);
		this.table.initializeFromResource(persistentTypeResource);
		this.defaultInheritanceStrategy = this.defaultInheritanceStrategy();
		this.specifiedInheritanceStrategy = this.specifiedInheritanceStrategy(inheritanceResource());
		this.specifiedDiscriminatorValue = this.discriminatorValueResource().getValue();
		this.defaultDiscriminatorValue = this.javaDefaultDiscriminatorValue();
		this.discriminatorValueAllowed = this.discriminatorValueIsAllowed(persistentTypeResource);
		this.discriminatorColumn.initializeFromResource(persistentTypeResource);
		this.initializeSecondaryTables(persistentTypeResource);
		this.initializeTableGenerator(persistentTypeResource);
		this.initializeSequenceGenerator(persistentTypeResource);
		this.initializePrimaryKeyJoinColumns(persistentTypeResource);
		this.initializeDefaultPrimaryKeyJoinColumn(persistentTypeResource);
		this.initializeSpecifiedAttributeOverrides(persistentTypeResource);
		this.initializeDefaultAttributeOverrides(persistentTypeResource);
		this.initializeSpecifiedAssociationOverrides(persistentTypeResource);
		this.initializeDefaultAssociationOverrides(persistentTypeResource);
		this.initializeNamedQueries(persistentTypeResource);
		this.initializeNamedNativeQueries(persistentTypeResource);
		this.initializeIdClass(persistentTypeResource);
	}
	
	protected void initializeSecondaryTables(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaResourceNode> annotations = persistentTypeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedSecondaryTables.add(createSecondaryTable((SecondaryTableAnnotation) annotations.next()));
		}
	}
	
	protected void initializeTableGenerator(JavaResourcePersistentType persistentTypeResource) {
		TableGeneratorAnnotation tableGeneratorResource = tableGenerator(persistentTypeResource);
		if (tableGeneratorResource != null) {
			this.tableGenerator = createTableGenerator(tableGeneratorResource);
		}
	}
	
	protected void initializeSequenceGenerator(JavaResourcePersistentType persistentTypeResource) {
		SequenceGeneratorAnnotation sequenceGeneratorResource = sequenceGenerator(persistentTypeResource);
		if (sequenceGeneratorResource != null) {
			this.sequenceGenerator = createSequenceGenerator(sequenceGeneratorResource);
		}
	}
	
	protected void initializePrimaryKeyJoinColumns(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaResourceNode> annotations = persistentTypeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedPrimaryKeyJoinColumns.add(createPrimaryKeyJoinColumn((PrimaryKeyJoinColumnAnnotation) annotations.next()));
		}
	}
	
	protected boolean shouldBuildDefaultPrimaryKeyJoinColumn() {
		return !containsSpecifiedPrimaryKeyJoinColumns();
	}

	protected void initializeDefaultPrimaryKeyJoinColumn(JavaResourcePersistentType persistentTypeResource) {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			return;
		}
		this.defaultPrimaryKeyJoinColumn = this.jpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.defaultPrimaryKeyJoinColumn.initializeFromResource(new NullPrimaryKeyJoinColumn(persistentTypeResource));
	}	

	protected void initializeSpecifiedAttributeOverrides(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaResourceNode> annotations = persistentTypeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedAttributeOverrides.add(createAttributeOverride((AttributeOverrideAnnotation) annotations.next()));
		}
	}
	
	protected void initializeDefaultAttributeOverrides(JavaResourcePersistentType persistentTypeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			JavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				this.defaultAttributeOverrides.add(createAttributeOverride(new NullAttributeOverride(persistentTypeResource, attributeName)));
			}
		}
	}
	
	protected void initializeSpecifiedAssociationOverrides(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaResourceNode> annotations = persistentTypeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedAssociationOverrides.add(createAssociationOverride((AssociationOverrideAnnotation) annotations.next()));
		}
	}
	
	protected void initializeDefaultAssociationOverrides(JavaResourcePersistentType persistentTypeResource) {
		for (Iterator<String> i = allOverridableAssociationNames(); i.hasNext(); ) {
			String associationName = i.next();
			JavaAssociationOverride associationOverride = associationOverrideNamed(associationName);
			if (associationOverride == null) {
				associationOverride = createAssociationOverride(new NullAssociationOverride(persistentTypeResource, associationName));
				this.defaultAssociationOverrides.add(associationOverride);
			}
		}
	}

	protected void initializeNamedQueries(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaResourceNode> annotations = persistentTypeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.namedQueries.add(createNamedQuery((NamedQueryAnnotation) annotations.next()));
		}
	}
	
	protected void initializeNamedNativeQueries(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaResourceNode> annotations = persistentTypeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.namedNativeQueries.add(createNamedNativeQuery((NamedNativeQueryAnnotation) annotations.next()));
		}
	}

	//query for the inheritance resource every time on setters.
	//call one setter and the inheritanceResource could change. 
	//You could call more than one setter before this object has received any notification
	//from the java resource model
	protected Inheritance inheritanceResource() {
		return (Inheritance) this.persistentTypeResource.nonNullAnnotation(Inheritance.ANNOTATION_NAME);
	}
	
	protected DiscriminatorValue discriminatorValueResource() {
		return (DiscriminatorValue) this.persistentTypeResource.nonNullAnnotation(DiscriminatorValue.ANNOTATION_NAME);
	}

	protected void initializeIdClass(JavaResourcePersistentType typeResource) {
		IdClass idClassResource = (IdClass) typeResource.annotation(IdClass.ANNOTATION_NAME);
		if (idClassResource != null) {
			this.idClass = idClassResource.getValue();
		}
	}
	
	//****************** ITypeMapping implemenation *******************
	
	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public boolean isMapped() {
		return true;
	}

	@Override
	public String tableName() {
		return getTable().getName();
	}

	@Override
	public org.eclipse.jpt.db.internal.Table primaryDbTable() {
		return getTable().dbTable();
	}

	@Override
	public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
		for (Iterator<Table> stream = this.associatedTablesIncludingInherited(); stream.hasNext();) {
			org.eclipse.jpt.db.internal.Table dbTable = stream.next().dbTable();
			if (dbTable != null && dbTable.matchesShortJavaClassName(tableName)) {
				return dbTable;
			}
		}
		return null;
	}

	@Override
	public Schema dbSchema() {
		return getTable().dbSchema();
	}


	//****************** IJavaTypeMapping implemenation *******************

	public String annotationName() {
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
		this.entityResource.setName(newSpecifiedName);
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
		JavaSecondaryTable secondaryTable = jpaFactory().buildJavaSecondaryTable(this);
		this.specifiedSecondaryTables.add(index, secondaryTable);
		SecondaryTableAnnotation secondaryTableResource = (SecondaryTableAnnotation) this.persistentTypeResource.addAnnotation(index, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		secondaryTable.initializeFromResource(secondaryTableResource);
		fireItemAdded(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, secondaryTable);
		return secondaryTable;
	}
	
	protected void addSpecifiedSecondaryTable(int index, JavaSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.specifiedSecondaryTables, Entity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}
	
	public void removeSpecifiedSecondaryTable(int index) {
		JavaSecondaryTable removedSecondaryTable = this.specifiedSecondaryTables.remove(index);
		this.persistentTypeResource.removeAnnotation(index, SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		fireItemRemoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, removedSecondaryTable);
	}
	
	protected void removeSpecifiedSecondaryTable_(JavaSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.specifiedSecondaryTables, Entity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedSecondaryTables, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, SecondaryTables.ANNOTATION_NAME);
		fireItemMoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<JavaSecondaryTable> secondaryTables() {
		return specifiedSecondaryTables();
	}

	public int secondaryTablesSize() {
		return specifiedSecondaryTablesSize();
	}
//TODO	
//	public boolean containsSecondaryTable(String name) {
//		return containsSecondaryTable(name, getSecondaryTables());
//	}
//
//	public boolean containsSpecifiedSecondaryTable(String name) {
//		return containsSecondaryTable(name, getSpecifiedSecondaryTables());
//	}
//
//	private boolean containsSecondaryTable(String name, List<ISecondaryTable> secondaryTables) {
//		for (ISecondaryTable secondaryTable : secondaryTables) {
//			String secondaryTableName = secondaryTable.getName();
//			if (secondaryTableName != null && secondaryTableName.equals(name)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
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
		inheritanceResource().setStrategy(InheritanceType.toJavaResourceModel(newInheritanceType));
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
		discriminatorValueResource().setValue(newSpecifiedDiscriminatorValue);
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
	
	public JavaTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		this.tableGenerator = jpaFactory().buildJavaTableGenerator(this);
		TableGeneratorAnnotation tableGeneratorResource = (TableGeneratorAnnotation) this.persistentTypeResource.addAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		this.tableGenerator.initializeFromResource(tableGeneratorResource);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		JavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.persistentTypeResource.removeAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
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
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		this.sequenceGenerator = jpaFactory().buildJavaSequenceGenerator(this);
		SequenceGeneratorAnnotation sequenceGeneratorResource = (SequenceGeneratorAnnotation) this.persistentTypeResource.addAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		this.sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		JavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.persistentTypeResource.removeAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
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
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnResource = (PrimaryKeyJoinColumnAnnotation) this.persistentTypeResource.addAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		primaryKeyJoinColumn.initializeFromResource(pkJoinColumnResource);
		this.fireItemAdded(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		if (oldDefaultPkJoinColumn != null) {
			this.firePropertyChanged(Entity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldDefaultPkJoinColumn, null);
		}
		return primaryKeyJoinColumn;
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
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
			this.defaultPrimaryKeyJoinColumn = createPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(this.persistentTypeResource));
		}
		this.persistentTypeResource.removeAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
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
		this.persistentTypeResource.move(targetIndex, sourceIndex, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		moveItemInList(targetIndex, sourceIndex, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);		
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<JavaAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<JavaAttributeOverride>(specifiedAttributeOverrides(), defaultAttributeOverrides());
	}
	
	public int attributeOverridesSize() {
		return this.specifiedAttributeOverridesSize() + this.defaultAttributeOverridesSize();
	}
	
	public ListIterator<JavaAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<JavaAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	public int defaultAttributeOverridesSize() {
		return this.defaultAttributeOverrides.size();
	}
	
	public ListIterator<JavaAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<JavaAttributeOverride>(this.specifiedAttributeOverrides);
	}
	
	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public JavaAttributeOverride addSpecifiedAttributeOverride(int index) {
		JavaAttributeOverride attributeOverride = jpaFactory().buildJavaAttributeOverride(this, createAttributeOverrideOwner());
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) this.persistentTypeResource.addAnnotation(index, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		attributeOverride.initializeFromResource(attributeOverrideResource);
		this.fireItemAdded(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}
	
	protected AttributeOverride.Owner createAttributeOverrideOwner() {
		return new AttributeOverrideOwner();
	}
	
	protected void addSpecifiedAttributeOverride(int index, JavaAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(AttributeOverride attributeOverride) {
		removeSpecifiedAttributeOverride(this.specifiedAttributeOverrides.indexOf(attributeOverride));
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		JavaAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);
		
		//add the default attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		JavaAttributeOverride defaultAttributeOverride = null;
		if (removedAttributeOverride.getName() != null) {
			if (CollectionTools.contains(allOverridableAttributeNames(), removedAttributeOverride.getName())) {
				defaultAttributeOverride = createAttributeOverride(new NullAttributeOverride(this.persistentTypeResource, removedAttributeOverride.getName()));
				this.defaultAttributeOverrides.add(defaultAttributeOverride);
			}
		}

		this.persistentTypeResource.removeAnnotation(index, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		fireItemRemoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
		
		if (defaultAttributeOverride != null) {
			fireItemAdded(Entity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST, defaultAttributeOverridesSize() - 1, defaultAttributeOverride);
		}
	}
	
	protected void removeSpecifiedAttributeOverride_(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, AttributeOverrides.ANNOTATION_NAME);
		fireItemMoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addDefaultAttributeOverride(JavaAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.defaultAttributeOverrides, Entity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeDefaultAttributeOverride(JavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.defaultAttributeOverrides, Entity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
	}

	public JavaAttributeOverride attributeOverrideNamed(String name) {
		return (JavaAttributeOverride) overrideNamed(name, attributeOverrides());
	}

	public boolean containsAttributeOverride(String name) {
		return containsOverride(name, attributeOverrides());
	}

	public boolean containsDefaultAttributeOverride(String name) {
		return containsOverride(name, defaultAttributeOverrides());
	}

	public boolean containsSpecifiedAttributeOverride(String name) {
		return containsOverride(name, specifiedAttributeOverrides());
	}

	public JavaAssociationOverride associationOverrideNamed(String name) {
		return (JavaAssociationOverride) overrideNamed(name, associationOverrides());
	}

	public boolean containsAssociationOverride(String name) {
		return containsOverride(name, associationOverrides());
	}

	public boolean containsSpecifiedAssociationOverride(String name) {
		return containsOverride(name, specifiedAssociationOverrides());
	}

	public boolean containsDefaultAssociationOverride(String name) {
		return containsOverride(name, defaultAssociationOverrides());
	}

	private BaseOverride overrideNamed(String name, ListIterator<? extends BaseOverride> overrides) {
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

	private boolean containsOverride(String name, ListIterator<? extends BaseOverride> overrides) {
		return overrideNamed(name, overrides) != null;
	}


	@SuppressWarnings("unchecked")
	public ListIterator<JavaAssociationOverride> associationOverrides() {
		return new CompositeListIterator<JavaAssociationOverride>(specifiedAssociationOverrides(), defaultAssociationOverrides());
	}
	
	public int associationOverridesSize() {
		return this.specifiedAssociationOverridesSize() + this.defaultAssociationOverridesSize();
	}

	public  ListIterator<JavaAssociationOverride> defaultAssociationOverrides() {
		return new CloneListIterator<JavaAssociationOverride>(this.defaultAssociationOverrides);
	}
	
	public int defaultAssociationOverridesSize() {
		return this.defaultAssociationOverrides.size();
	}
	
	public ListIterator<JavaAssociationOverride> specifiedAssociationOverrides() {
		return new CloneListIterator<JavaAssociationOverride>(this.specifiedAssociationOverrides);
	}
	
	public int specifiedAssociationOverridesSize() {
		return this.specifiedAssociationOverrides.size();
	}

	public JavaAssociationOverride addSpecifiedAssociationOverride(int index) {
		JavaAssociationOverride associationOverride = jpaFactory().buildJavaAssociationOverride(this, createAssociationOverrideOwner());
		this.specifiedAssociationOverrides.add(index, associationOverride);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) this.persistentTypeResource.addAnnotation(index, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		associationOverride.initializeFromResource(associationOverrideResource);
		this.fireItemAdded(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		return associationOverride;
	}
	
	protected AssociationOverride.Owner createAssociationOverrideOwner() {
		return new AssociationOverrideOwner();
	}
	
	protected void addSpecifiedAssociationOverride(int index, JavaAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAssociationOverride(AssociationOverride associationOverride) {
		removeSpecifiedAssociationOverride(this.specifiedAssociationOverrides.indexOf(associationOverride));
	}
	
	public void removeSpecifiedAssociationOverride(int index) {
		JavaAssociationOverride removedAssociationOverride = this.specifiedAssociationOverrides.remove(index);
		JavaAssociationOverride defaultAssociationOverride = null;

		//add the default association override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the association override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		if (removedAssociationOverride.getName() != null) {
			if (CollectionTools.contains(allOverridableAssociationNames(), removedAssociationOverride.getName())) {
				defaultAssociationOverride = createAssociationOverride(new NullAssociationOverride(this.persistentTypeResource, removedAssociationOverride.getName()));
				this.defaultAssociationOverrides.add(defaultAssociationOverride);
			}
		}
		this.persistentTypeResource.removeAnnotation(index, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		fireItemRemoved(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, removedAssociationOverride);

		if (defaultAssociationOverride != null) {
			fireItemAdded(Entity.DEFAULT_ASSOCIATION_OVERRIDES_LIST, defaultAssociationOverridesSize() - 1, defaultAssociationOverride);
		}
	}
	
	protected void removeSpecifiedAssociationOverride_(JavaAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAssociationOverrides, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, AssociationOverrides.ANNOTATION_NAME);
		fireItemMoved(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addDefaultAssociationOverride(JavaAssociationOverride associationOverride) {
		addItemToList(associationOverride, this.defaultAssociationOverrides, Entity.DEFAULT_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected void removeDefaultAssociationOverride(JavaAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.defaultAssociationOverrides, Entity.DEFAULT_ASSOCIATION_OVERRIDES_LIST);
	}

	public ListIterator<JavaNamedQuery> namedQueries() {
		return new CloneListIterator<JavaNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public JavaNamedQuery addNamedQuery(int index) {
		JavaNamedQuery namedQuery = jpaFactory().buildJavaNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		this.persistentTypeResource.addAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		fireItemAdded(Entity.NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, JavaNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, Entity.NAMED_QUERIES_LIST);
	}
	
	public void removeNamedQuery(NamedQuery namedQuery) {
		removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}
	
	public void removeNamedQuery(int index) {
		JavaNamedQuery removedNamedQuery = this.namedQueries.remove(index);
		this.persistentTypeResource.removeAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		fireItemRemoved(Entity.NAMED_QUERIES_LIST, index, removedNamedQuery);
	}	
	
	protected void removeNamedQuery_(JavaNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, Entity.NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, NamedQueries.ANNOTATION_NAME);
		fireItemMoved(Entity.NAMED_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<JavaNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<JavaNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public JavaNamedNativeQuery addNamedNativeQuery(int index) {
		JavaNamedNativeQuery namedNativeQuery = jpaFactory().buildJavaNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.persistentTypeResource.addAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		fireItemAdded(Entity.NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, JavaNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, Entity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}
	
	public void removeNamedNativeQuery(int index) {
		JavaNamedNativeQuery removedNamedNativeQuery = this.namedNativeQueries.remove(index);
		this.persistentTypeResource.removeAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		fireItemRemoved(Entity.NAMED_NATIVE_QUERIES_LIST, index, removedNamedNativeQuery);
	}	
	
	protected void removeNamedNativeQuery_(JavaNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, Entity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, NamedNativeQueries.ANNOTATION_NAME);
		fireItemMoved(Entity.NAMED_NATIVE_QUERIES_LIST, targetIndex, sourceIndex);		
	}

	public String getIdClass() {
		return this.idClass;
	}
	
	public void setIdClass(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		if (newIdClass != oldIdClass) {
			if (newIdClass != null) {
				if (idClassResource() == null) {
					addIdClassResource();
				}
				idClassResource().setValue(newIdClass);
			}
			else {
				removeIdClassResource();
			}
		}
		firePropertyChanged(Entity.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(Entity.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}

	protected IdClass idClassResource() {
		return (IdClass) this.persistentTypeResource.annotation(IdClass.ANNOTATION_NAME);
	}
	
	protected void addIdClassResource() {
		this.persistentTypeResource.addAnnotation(IdClass.ANNOTATION_NAME);
	}
	
	protected void removeIdClassResource() {
		this.persistentTypeResource.removeAnnotation(IdClass.ANNOTATION_NAME);
	}

	public Entity parentEntity() {
		for (Iterator<PersistentType> i = persistentType().inheritanceHierarchy(); i.hasNext();) {
			TypeMapping typeMapping = i.next().getMapping();
			if (typeMapping != this && typeMapping instanceof Entity) {
				return (Entity) typeMapping;
			}
		}
		return this;
	}

	public Entity rootEntity() {
		Entity rootEntity = this;
		for (Iterator<PersistentType> i = persistentType().inheritanceHierarchy(); i.hasNext();) {
			PersistentType persistentType = i.next();
			if (persistentType.getMapping() instanceof Entity) {
				rootEntity = (Entity) persistentType.getMapping();
			}
		}
		return rootEntity;
	}

	public String primaryKeyColumnName() {
		return primaryKeyColumnName(persistentType().allAttributes());
	}
	
	public static String primaryKeyColumnName(Iterator<PersistentAttribute> attributes) {
		String pkColumnName = null;
		for (Iterator<PersistentAttribute> stream = attributes; stream.hasNext();) {
			PersistentAttribute attribute = stream.next();
			String name = attribute.primaryKeyColumnName();
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
//TODO
//	public String primaryKeyAttributeName() {
//		String pkColumnName = null;
//		String pkAttributeName = null;
//		for (Iterator<IPersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
//			IPersistentAttribute attribute = stream.next();
//			String name = attribute.primaryKeyColumnName();
//			if (pkColumnName == null) {
//				pkColumnName = name;
//				pkAttributeName = attribute.getName();
//			}
//			else if (name != null) {
//				// if we encounter a composite primary key, return null
//				return null;
//			}
//		}
//		// if we encounter only a single primary key column name, return it
//		return pkAttributeName;
//	}

	@Override
	public boolean tableNameIsInvalid(String tableName) {
		return !CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
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
		return new TransformationIterator<PersistentType, TypeMapping>(persistentType().inheritanceHierarchy()) {
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
	public Iterator<String> allOverridableAssociationNames() {
		return new CompositeIterator<String>(new TransformationIterator<TypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(TypeMapping mapping) {
				return mapping.overridableAssociationNames();
			}
		});
	}
	
	@Override
	public void update(JavaResourcePersistentType persistentTypeResource) {
		super.update(persistentTypeResource);
		this.entityResource = (EntityAnnotation) persistentTypeResource.mappingAnnotation(EntityAnnotation.ANNOTATION_NAME);
		
		this.setSpecifiedName(this.specifiedName(this.entityResource));
		this.setDefaultName(this.defaultName(persistentTypeResource));
		
		this.updateTable(persistentTypeResource);
		this.updateInheritance(inheritanceResource());
		this.updateDiscriminatorColumn(persistentTypeResource);
		this.updateDiscriminatorValue(discriminatorValueResource());
		this.setDiscriminatorValueAllowed(discriminatorValueIsAllowed(persistentTypeResource));
		this.updateSecondaryTables(persistentTypeResource);
		this.updateTableGenerator(persistentTypeResource);
		this.updateSequenceGenerator(persistentTypeResource);
		this.updateSpecifiedPrimaryKeyJoinColumns(persistentTypeResource);
		this.updateDefaultPrimaryKeyJoinColumn(persistentTypeResource);
		this.updateSpecifiedAttributeOverrides(persistentTypeResource);
		this.updateDefaultAttributeOverrides(persistentTypeResource);
		this.updateSpecifiedAssociationOverrides(persistentTypeResource);
		this.updateDefaultAssociationOverrides(persistentTypeResource);
		this.updateNamedQueries(persistentTypeResource);
		this.updateNamedNativeQueries(persistentTypeResource);
		this.updateIdClass(persistentTypeResource);
	}
		
	protected String specifiedName(EntityAnnotation entityResource) {
		return entityResource.getName();
	}
	
	protected String defaultName(JavaResourcePersistentType persistentTypeResource) {
		return persistentTypeResource.getName();
	}

	protected void updateTable(JavaResourcePersistentType persistentTypeResource) {
		getTable().update(persistentTypeResource);
	}
	
	protected void updateInheritance(Inheritance inheritanceResource) {
		this.setSpecifiedInheritanceStrategy_(this.specifiedInheritanceStrategy(inheritanceResource));
		this.setDefaultInheritanceStrategy(this.defaultInheritanceStrategy());
	}
	
	protected InheritanceType specifiedInheritanceStrategy(Inheritance inheritanceResource) {
		return InheritanceType.fromJavaResourceModel(inheritanceResource.getStrategy());
	}
	
	protected InheritanceType defaultInheritanceStrategy() {
		if (rootEntity() == this) {
			return InheritanceType.SINGLE_TABLE;
		}
		return rootEntity().getInheritanceStrategy();
	}
	
	protected void updateDiscriminatorColumn(JavaResourcePersistentType persistentTypeResource) {
		getDiscriminatorColumn().update(persistentTypeResource);
	}
	
	protected void updateDiscriminatorValue(DiscriminatorValue discriminatorValueResource) {
		this.setSpecifiedDiscriminatorValue_(discriminatorValueResource.getValue());
		this.setDefaultDiscriminatorValue(this.javaDefaultDiscriminatorValue());
	}
	
	protected boolean discriminatorValueIsAllowed(JavaResourcePersistentType persistentTypeResource) {
		return !persistentTypeResource.isAbstract();
	}
	
	protected void updateSecondaryTables(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaSecondaryTable> secondaryTables = specifiedSecondaryTables();
		ListIterator<JavaResourceNode> resourceSecondaryTables = persistentTypeResource.annotations(SecondaryTableAnnotation.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		
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
			addSpecifiedSecondaryTable(specifiedSecondaryTablesSize(), createSecondaryTable((SecondaryTableAnnotation) resourceSecondaryTables.next()));
		}
	}

	protected JavaSecondaryTable createSecondaryTable(SecondaryTableAnnotation secondaryTableResource) {
		JavaSecondaryTable secondaryTable = jpaFactory().buildJavaSecondaryTable(this);
		secondaryTable.initializeFromResource(secondaryTableResource);
		return secondaryTable;
	}

	protected void updateTableGenerator(JavaResourcePersistentType persistentTypeResource) {
		TableGeneratorAnnotation tableGeneratorResource = tableGenerator(persistentTypeResource);
		if (tableGeneratorResource == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(createTableGenerator(tableGeneratorResource));
			}
			else {
				getTableGenerator().update(tableGeneratorResource);
			}
		}
	}
	
	protected JavaTableGenerator createTableGenerator(TableGeneratorAnnotation tableGeneratorResource) {
		JavaTableGenerator tableGenerator = jpaFactory().buildJavaTableGenerator(this);
		tableGenerator.initializeFromResource(tableGeneratorResource);
		return tableGenerator;
	}
	
	protected TableGeneratorAnnotation tableGenerator(JavaResourcePersistentType persistentTypeResource) {
		return (TableGeneratorAnnotation) persistentTypeResource.annotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}

	protected void updateSequenceGenerator(JavaResourcePersistentType persistentTypeResource) {
		SequenceGeneratorAnnotation sequenceGeneratorResource = sequenceGenerator(persistentTypeResource);
		if (sequenceGeneratorResource == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(createSequenceGenerator(sequenceGeneratorResource));
			}
			else {
				getSequenceGenerator().update(sequenceGeneratorResource);
			}
		}
	}
	
	protected JavaSequenceGenerator createSequenceGenerator(SequenceGeneratorAnnotation sequenceGeneratorResource) {
		JavaSequenceGenerator sequenceGenerator = jpaFactory().buildJavaSequenceGenerator(this);
		sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
		return sequenceGenerator;
	}
	
	protected SequenceGeneratorAnnotation sequenceGenerator(JavaResourcePersistentType persistentTypeResource) {
		return (SequenceGeneratorAnnotation) persistentTypeResource.annotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}

	
	protected void updateSpecifiedPrimaryKeyJoinColumns(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<JavaResourceNode> resourcePrimaryKeyJoinColumns = persistentTypeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		
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
			addSpecifiedPrimaryKeyJoinColumn(specifiedPrimaryKeyJoinColumnsSize(), createPrimaryKeyJoinColumn((PrimaryKeyJoinColumnAnnotation) resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected JavaPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation primaryKeyJoinColumnResource) {
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		primaryKeyJoinColumn.initializeFromResource(primaryKeyJoinColumnResource);
		return primaryKeyJoinColumn;
	}

	protected void updateDefaultPrimaryKeyJoinColumn(JavaResourcePersistentType persistentTypeResource) {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			setDefaultPrimaryKeyJoinColumn(null);
			return;
		}
		if (getDefaultPrimaryKeyJoinColumn() == null) {
			this.setDefaultPrimaryKeyJoinColumn(createPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(this.persistentTypeResource)));
		}
		else {
			this.defaultPrimaryKeyJoinColumn.update(new NullPrimaryKeyJoinColumn(persistentTypeResource));
		}
	}
		
	protected void updateSpecifiedAttributeOverrides(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<JavaResourceNode> resourceAttributeOverrides = persistentTypeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
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
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), createAttributeOverride((AttributeOverrideAnnotation) resourceAttributeOverrides.next()));
		}	
	}
	
	protected JavaAttributeOverride createAttributeOverride(AttributeOverrideAnnotation attributeOverrideResource) {
		JavaAttributeOverride attributeOverride = jpaFactory().buildJavaAttributeOverride(this, createAttributeOverrideOwner());
		attributeOverride.initializeFromResource(attributeOverrideResource);
		return attributeOverride;
	}
	
	protected void updateDefaultAttributeOverrides(JavaResourcePersistentType persistentTypeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			JavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				attributeOverride = createAttributeOverride(new NullAttributeOverride(persistentTypeResource, attributeName));
				addDefaultAttributeOverride(attributeOverride);
			}
			else if (attributeOverride.isVirtual()) {
				attributeOverride.getColumn().update(new NullColumn(persistentTypeResource));
			}
		}
		
		Collection<String> attributeNames = CollectionTools.collection(allOverridableAttributeNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		for (JavaAttributeOverride attributeOverride : CollectionTools.iterable(defaultAttributeOverrides())) {
			if (!attributeNames.contains(attributeOverride.getName())
				|| containsSpecifiedAttributeOverride(attributeOverride.getName())) {
				removeDefaultAttributeOverride(attributeOverride);
			}
		}
	}

	protected void updateSpecifiedAssociationOverrides(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaAssociationOverride> associationOverrides = specifiedAssociationOverrides();
		ListIterator<JavaResourceNode> resourceAssociationOverrides = persistentTypeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		
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
			addSpecifiedAssociationOverride(specifiedAssociationOverridesSize(), createAssociationOverride((AssociationOverrideAnnotation) resourceAssociationOverrides.next()));
		}	
	}
	
	protected JavaAssociationOverride createAssociationOverride(AssociationOverrideAnnotation associationOverrideResource) {
		JavaAssociationOverride associationOverride = jpaFactory().buildJavaAssociationOverride(this, createAssociationOverrideOwner());
		associationOverride.initializeFromResource(associationOverrideResource);
		return associationOverride;
	}
	
	protected void updateDefaultAssociationOverrides(JavaResourcePersistentType persistentTypeResource) {
		for (Iterator<String> i = allOverridableAssociationNames(); i.hasNext(); ) {
			String associationName = i.next();
			JavaAssociationOverride associationOverride = associationOverrideNamed(associationName);
			if (associationOverride == null) {
				associationOverride = createAssociationOverride(new NullAssociationOverride(persistentTypeResource, associationName));
				addDefaultAssociationOverride(associationOverride);
			}
			else if (associationOverride.isVirtual()) {
				//TODO what is this about for attributeOverrides???
				//associationOverride.getColumn().update(new NullColumn(persistentTypeResource));
			}
		}
		
		Collection<String> associationNames = CollectionTools.collection(allOverridableAssociationNames());
	
		//remove any default mappings that are not included in the associationNames collection
		for (JavaAssociationOverride associationOverride : CollectionTools.iterable(defaultAssociationOverrides())) {
			if (!associationNames.contains(associationOverride.getName())
				|| containsSpecifiedAssociationOverride(associationOverride.getName())) {
				removeDefaultAssociationOverride(associationOverride);
			}
		}
	}

	protected void updateNamedQueries(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaNamedQuery> namedQueries = namedQueries();
		ListIterator<JavaResourceNode> resourceNamedQueries = persistentTypeResource.annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		
		while (namedQueries.hasNext()) {
			JavaNamedQuery namedQuery = namedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update((NamedQueryAnnotation) resourceNamedQueries.next());
			}
			else {
				removeNamedQuery_(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(namedQueriesSize(), createNamedQuery((NamedQueryAnnotation) resourceNamedQueries.next()));
		}	
	}
	
	protected void updateNamedNativeQueries(JavaResourcePersistentType persistentTypeResource) {
		ListIterator<JavaNamedNativeQuery> namedNativeQueries = namedNativeQueries();
		ListIterator<JavaResourceNode> resourceNamedNativeQueries = persistentTypeResource.annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		
		while (namedNativeQueries.hasNext()) {
			JavaNamedNativeQuery namedQuery = namedNativeQueries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update((NamedNativeQueryAnnotation) resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery_(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(namedQueriesSize(), createNamedNativeQuery((NamedNativeQueryAnnotation) resourceNamedNativeQueries.next()));
		}	
	}
	
	
	protected JavaNamedQuery createNamedQuery(NamedQueryAnnotation namedQueryResource) {
		JavaNamedQuery namedQuery = jpaFactory().buildJavaNamedQuery(this);
		namedQuery.initializeFromResource(namedQueryResource);
		return namedQuery;
	}
	
	protected JavaNamedNativeQuery createNamedNativeQuery(NamedNativeQueryAnnotation namedNativeQueryResource) {
		JavaNamedNativeQuery namedNativeQuery = jpaFactory().buildJavaNamedNativeQuery(this);
		namedNativeQuery.initializeFromResource(namedNativeQueryResource);
		return namedNativeQuery;
	}

	protected void updateIdClass(JavaResourcePersistentType typeResource) {
		IdClass idClass = (IdClass) typeResource.annotation(IdClass.ANNOTATION_NAME);
		if (idClass != null) {
			setIdClass_(idClass.getValue());
		}
		else {
			setIdClass_(null);
		}
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
	protected String javaDefaultDiscriminatorValue() {
		if (this.persistentTypeResource.isAbstract()) {
			return null;
		}
		if (this.discriminatorType() != DiscriminatorType.STRING) {
			return null;
		}
		return this.getName();
	}

	protected DiscriminatorType discriminatorType() {
		return this.getDiscriminatorColumn().getDiscriminatorType();
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
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		getTable().addToMessages(messages, astRoot);
		addIdMessages(messages, astRoot);
		
		for (Iterator<JavaSecondaryTable> stream = this.specifiedSecondaryTables(); stream.hasNext();) {
			stream.next().addToMessages(messages, astRoot);
		}

		for (Iterator<JavaAttributeOverride> stream = this.attributeOverrides(); stream.hasNext();) {
			stream.next().addToMessages(messages, astRoot);
		}
		
		for (Iterator<JavaAssociationOverride> stream = this.associationOverrides(); stream.hasNext();) {
			stream.next().addToMessages(messages, astRoot);
		}
		
	}
	
	protected void addIdMessages(List<IMessage> messages, CompilationUnit astRoot) {
		addNoIdMessage(messages, astRoot);
	}
	
	protected void addNoIdMessage(List<IMessage> messages, CompilationUnit astRoot) {
		if (entityHasNoId()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ENTITY_NO_ID,
					new String[] {this.getName()},
					this, this.validationTextRange(astRoot))
			);
		}
	}
	
	private boolean entityHasNoId() {
		return ! this.entityHasId();
	}

	private boolean entityHasId() {
		for (Iterator<PersistentAttribute> stream = persistentType().allAttributes(); stream.hasNext(); ) {
			if (stream.next().isIdAttribute()) {
				return true;
			}
		}
		return false;
	}
	
	class PrimaryKeyJoinColumnOwner implements JavaAbstractJoinColumn.Owner
	{
		public TextRange validationTextRange(CompilationUnit astRoot) {
			return GenericJavaEntity.this.validationTextRange(astRoot);
		}

		public TypeMapping typeMapping() {
			return GenericJavaEntity.this;
		}

		public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
			return GenericJavaEntity.this.dbTable(tableName);
		}

		public org.eclipse.jpt.db.internal.Table dbReferencedColumnTable() {
			Entity parentEntity = GenericJavaEntity.this.parentEntity();
			return (parentEntity == null) ? null : parentEntity.primaryDbTable();
		}

		public int joinColumnsSize() {
			return GenericJavaEntity.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return GenericJavaEntity.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}
		
		public String defaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return GenericJavaEntity.this.parentEntity().primaryKeyColumnName();
		}
	}
	
	class AttributeOverrideOwner implements AttributeOverride.Owner {

		public ColumnMapping columnMapping(String attributeName) {
			if (attributeName == null) {
				return null;
			}
			for (Iterator<PersistentAttribute> stream = persistentType().allAttributes(); stream.hasNext();) {
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
			return GenericJavaEntity.this.defaultAttributeOverrides.contains(override);
		}

		public TypeMapping typeMapping() {
			return GenericJavaEntity.this;
		}

		public TextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	class AssociationOverrideOwner implements AssociationOverride.Owner {

		public RelationshipMapping relationshipMapping(String attributeName) {
			if (attributeName == null) {
				return null;
			}
			for (Iterator<PersistentAttribute> stream = persistentType().allAttributes(); stream.hasNext();) {
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
			return GenericJavaEntity.this.defaultAssociationOverrides.contains(override);
		}

		public TypeMapping typeMapping() {
			return GenericJavaEntity.this;
		}

		public TextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
