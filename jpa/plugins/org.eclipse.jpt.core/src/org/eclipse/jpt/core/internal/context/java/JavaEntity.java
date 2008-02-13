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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.DiscriminatorType;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.context.base.INamedNativeQuery;
import org.eclipse.jpt.core.internal.context.base.INamedQuery;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverride;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverrides;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValue;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.IdClass;
import org.eclipse.jpt.core.internal.resource.java.Inheritance;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueries;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQuery;
import org.eclipse.jpt.core.internal.resource.java.NamedQueries;
import org.eclipse.jpt.core.internal.resource.java.NamedQuery;
import org.eclipse.jpt.core.internal.resource.java.NullAssociationOverride;
import org.eclipse.jpt.core.internal.resource.java.NullAttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.NullColumn;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumns;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTables;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
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


public class JavaEntity extends JavaTypeMapping implements IJavaEntity
{
	protected Entity entityResource;
	
	protected String specifiedName;

	protected String defaultName;

	protected final IJavaTable table;

	protected final List<IJavaSecondaryTable> specifiedSecondaryTables;

	protected final List<IJavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;

	protected IJavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	protected InheritanceType specifiedInheritanceStrategy;
	
	protected InheritanceType defaultInheritanceStrategy;

	protected String defaultDiscriminatorValue;

	protected boolean discriminatorValueAllowed;
	
	protected String specifiedDiscriminatorValue;
	
	protected final IJavaDiscriminatorColumn discriminatorColumn;

	protected IJavaSequenceGenerator sequenceGenerator;

	protected IJavaTableGenerator tableGenerator;

	protected final List<IJavaAttributeOverride> specifiedAttributeOverrides;

	protected final List<IJavaAttributeOverride> defaultAttributeOverrides;

	protected final List<IJavaAssociationOverride> specifiedAssociationOverrides;

	protected final List<IJavaAssociationOverride> defaultAssociationOverrides;

	protected final List<IJavaNamedQuery> namedQueries;

	protected final List<IJavaNamedNativeQuery> namedNativeQueries;

	protected String idClass;

	
	public JavaEntity(IJavaPersistentType parent) {
		super(parent);
		this.table = jpaFactory().createJavaTable(this);
		this.discriminatorColumn = createJavaDiscriminatorColumn();
		this.specifiedSecondaryTables = new ArrayList<IJavaSecondaryTable>();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<IJavaPrimaryKeyJoinColumn>();
		this.specifiedAttributeOverrides = new ArrayList<IJavaAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<IJavaAttributeOverride>();
		this.namedQueries = new ArrayList<IJavaNamedQuery>();
		this.namedNativeQueries = new ArrayList<IJavaNamedNativeQuery>();
		this.specifiedAssociationOverrides = new ArrayList<IJavaAssociationOverride>();
		this.defaultAssociationOverrides = new ArrayList<IJavaAssociationOverride>();
	}
	
	protected IAbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}
	
	protected IJavaDiscriminatorColumn createJavaDiscriminatorColumn() {
		return jpaFactory().createJavaDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected INamedColumn.Owner buildDiscriminatorColumnOwner() {
		return new INamedColumn.Owner(){
			public Table dbTable(String tableName) {
				return JavaEntity.this.dbTable(tableName);
			}

			public ITextRange validationTextRange(CompilationUnit astRoot) {
				return JavaEntity.this.validationTextRange(astRoot);
			}

			public ITypeMapping typeMapping() {
				return JavaEntity.this;
			}
			
			public String defaultColumnName() {
				return IDiscriminatorColumn.DEFAULT_NAME;
			}
		};
	}

	@Override
	public void initializeFromResource(JavaPersistentTypeResource persistentTypeResource) {
		super.initializeFromResource(persistentTypeResource);
		this.entityResource = (Entity) persistentTypeResource.mappingAnnotation(Entity.ANNOTATION_NAME);
		
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
	
	protected void initializeSecondaryTables(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedSecondaryTables.add(createSecondaryTable((SecondaryTable) annotations.next()));
		}
	}
	
	protected void initializeTableGenerator(JavaPersistentTypeResource persistentTypeResource) {
		TableGenerator tableGeneratorResource = tableGenerator(persistentTypeResource);
		if (tableGeneratorResource != null) {
			this.tableGenerator = createTableGenerator(tableGeneratorResource);
		}
	}
	
	protected void initializeSequenceGenerator(JavaPersistentTypeResource persistentTypeResource) {
		SequenceGenerator sequenceGeneratorResource = sequenceGenerator(persistentTypeResource);
		if (sequenceGeneratorResource != null) {
			this.sequenceGenerator = createSequenceGenerator(sequenceGeneratorResource);
		}
	}
	
	protected void initializePrimaryKeyJoinColumns(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedPrimaryKeyJoinColumns.add(createPrimaryKeyJoinColumn((PrimaryKeyJoinColumn) annotations.next()));
		}
	}
	
	protected boolean shouldBuildDefaultPrimaryKeyJoinColumn() {
		return !containsSpecifiedPrimaryKeyJoinColumns();
	}

	protected void initializeDefaultPrimaryKeyJoinColumn(JavaPersistentTypeResource persistentTypeResource) {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			return;
		}
		this.defaultPrimaryKeyJoinColumn = this.jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.defaultPrimaryKeyJoinColumn.initializeFromResource(new NullPrimaryKeyJoinColumn(persistentTypeResource));
	}	

	protected void initializeSpecifiedAttributeOverrides(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedAttributeOverrides.add(createAttributeOverride((AttributeOverride) annotations.next()));
		}
	}
	
	protected void initializeDefaultAttributeOverrides(JavaPersistentTypeResource persistentTypeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			IJavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				this.defaultAttributeOverrides.add(createAttributeOverride(new NullAttributeOverride(persistentTypeResource, attributeName)));
			}
		}
	}
	
	protected void initializeSpecifiedAssociationOverrides(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedAssociationOverrides.add(createAssociationOverride((AssociationOverride) annotations.next()));
		}
	}
	
	protected void initializeDefaultAssociationOverrides(JavaPersistentTypeResource persistentTypeResource) {
		for (Iterator<String> i = allOverridableAssociationNames(); i.hasNext(); ) {
			String associationName = i.next();
			IJavaAssociationOverride associationOverride = associationOverrideNamed(associationName);
			if (associationOverride == null) {
				associationOverride = createAssociationOverride(new NullAssociationOverride(persistentTypeResource, associationName));
				this.defaultAssociationOverrides.add(associationOverride);
			}
		}
	}

	protected void initializeNamedQueries(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.namedQueries.add(createNamedQuery((NamedQuery) annotations.next()));
		}
	}
	
	protected void initializeNamedNativeQueries(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.namedNativeQueries.add(createNamedNativeQuery((NamedNativeQuery) annotations.next()));
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

	protected void initializeIdClass(JavaPersistentTypeResource typeResource) {
		IdClass idClassResource = (IdClass) typeResource.annotation(IdClass.ANNOTATION_NAME);
		if (idClassResource != null) {
			this.idClass = idClassResource.getValue();
		}
	}
	
	//****************** ITypeMapping implemenation *******************
	
	public String getKey() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public boolean isMapped() {
		return true;
	}

	@Override
	public String tableName() {
		return getTable().getName();
	}

	@Override
	public Table primaryDbTable() {
		return getTable().dbTable();
	}

	@Override
	public Table dbTable(String tableName) {
		for (Iterator<ITable> stream = this.associatedTablesIncludingInherited(); stream.hasNext();) {
			Table dbTable = stream.next().dbTable();
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
		return Entity.ANNOTATION_NAME;
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
		firePropertyChanged(IEntity.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}

	public String getDefaultName() {
		return this.defaultName;
	}
	
	protected/*private-protected*/ void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(IEntity.DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}

	public IJavaTable getTable() {
		return this.table;
	}

	public ListIterator<IJavaSecondaryTable> specifiedSecondaryTables() {
		return new CloneListIterator<IJavaSecondaryTable>(this.specifiedSecondaryTables);
	}
	
	public int specifiedSecondaryTablesSize() {
		return this.specifiedSecondaryTables.size();
	}
	
	public IJavaSecondaryTable addSpecifiedSecondaryTable(int index) {
		IJavaSecondaryTable secondaryTable = jpaFactory().createJavaSecondaryTable(this);
		this.specifiedSecondaryTables.add(index, secondaryTable);
		SecondaryTable secondaryTableResource = (SecondaryTable) this.persistentTypeResource.addAnnotation(index, SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		secondaryTable.initializeFromResource(secondaryTableResource);
		fireItemAdded(IEntity.SPECIFIED_SECONDARY_TABLES_LIST, index, secondaryTable);
		return secondaryTable;
	}
	
	protected void addSpecifiedSecondaryTable(int index, IJavaSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.specifiedSecondaryTables, IEntity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void removeSpecifiedSecondaryTable(ISecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}
	
	public void removeSpecifiedSecondaryTable(int index) {
		IJavaSecondaryTable removedSecondaryTable = this.specifiedSecondaryTables.remove(index);
		this.persistentTypeResource.removeAnnotation(index, SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		fireItemRemoved(IEntity.SPECIFIED_SECONDARY_TABLES_LIST, index, removedSecondaryTable);
	}
	
	protected void removeSpecifiedSecondaryTable_(IJavaSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.specifiedSecondaryTables, IEntity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedSecondaryTables, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, SecondaryTables.ANNOTATION_NAME);
		fireItemMoved(IEntity.SPECIFIED_SECONDARY_TABLES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<IJavaSecondaryTable> secondaryTables() {
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

	public IJavaDiscriminatorColumn getDiscriminatorColumn() {
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
		firePropertyChanged(IEntity.DISCRIMINATOR_VALUE_ALLOWED_PROPERTY, oldDiscriminatorValueAllowed, newDiscriminatorValueAllowed);
	}
	
	public IJavaTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		this.tableGenerator = jpaFactory().createJavaTableGenerator(this);
		TableGenerator tableGeneratorResource = (TableGenerator) this.persistentTypeResource.addAnnotation(TableGenerator.ANNOTATION_NAME);
		this.tableGenerator.initializeFromResource(tableGeneratorResource);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		IJavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.persistentTypeResource.removeAnnotation(TableGenerator.ANNOTATION_NAME);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);
	}
	
	public IJavaTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}
	
	protected void setTableGenerator(IJavaTableGenerator newTableGenerator) {
		IJavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}

	public IJavaSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		this.sequenceGenerator = jpaFactory().createJavaSequenceGenerator(this);
		SequenceGenerator sequenceGeneratorResource = (SequenceGenerator) this.persistentTypeResource.addAnnotation(SequenceGenerator.ANNOTATION_NAME);
		this.sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		IJavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.persistentTypeResource.removeAnnotation(SequenceGenerator.ANNOTATION_NAME);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator,null);
	}
	
	public IJavaSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(IJavaSequenceGenerator newSequenceGenerator) {
		IJavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	public ListIterator<IJavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumns() : this.defaultPrimaryKeyJoinColumns();
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.defaultPrimaryKeyJoinColumnsSize();
	}
	
	public ListIterator<IJavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<IJavaPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}
	
	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}
	
	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.specifiedPrimaryKeyJoinColumns.isEmpty();
	}	
	
	public IJavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}
	
	protected void setDefaultPrimaryKeyJoinColumn(IJavaPrimaryKeyJoinColumn newPkJoinColumn) {
		IJavaPrimaryKeyJoinColumn oldPkJoinColumn = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = newPkJoinColumn;
		firePropertyChanged(IEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldPkJoinColumn, newPkJoinColumn);
	}

	protected ListIterator<IJavaPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		if (this.defaultPrimaryKeyJoinColumn != null) {
			return new SingleElementListIterator<IJavaPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn);
		}
		return EmptyListIterator.instance();
	}
	
	protected int defaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	public IPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		IJavaPrimaryKeyJoinColumn oldDefaultPkJoinColumn = this.getDefaultPrimaryKeyJoinColumn();
		if (oldDefaultPkJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultPrimaryKeyJoinColumn = null;
		}
		IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		PrimaryKeyJoinColumn pkJoinColumnResource = (PrimaryKeyJoinColumn) this.persistentTypeResource.addAnnotation(index, PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		primaryKeyJoinColumn.initializeFromResource(pkJoinColumnResource);
		this.fireItemAdded(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		if (oldDefaultPkJoinColumn != null) {
			this.firePropertyChanged(IEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldDefaultPkJoinColumn, null);
		}
		return primaryKeyJoinColumn;
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
		
	public void removeSpecifiedPrimaryKeyJoinColumn(IPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(primaryKeyJoinColumn));
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		IJavaPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		if (!containsSpecifiedPrimaryKeyJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultPrimaryKeyJoinColumn = createPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(this.persistentTypeResource));
		}
		this.persistentTypeResource.removeAnnotation(index, PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		fireItemRemoved(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
		if (this.defaultPrimaryKeyJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(IEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, null, this.defaultPrimaryKeyJoinColumn);
		}
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.persistentTypeResource.move(targetIndex, sourceIndex, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		moveItemInList(targetIndex, sourceIndex, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);		
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<IJavaAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<IJavaAttributeOverride>(specifiedAttributeOverrides(), defaultAttributeOverrides());
	}
	
	public int attributeOverridesSize() {
		return this.specifiedAttributeOverridesSize() + this.defaultAttributeOverridesSize();
	}
	
	public ListIterator<IJavaAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<IJavaAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	public int defaultAttributeOverridesSize() {
		return this.defaultAttributeOverrides.size();
	}
	
	public ListIterator<IJavaAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<IJavaAttributeOverride>(this.specifiedAttributeOverrides);
	}
	
	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public IJavaAttributeOverride addSpecifiedAttributeOverride(int index) {
		IJavaAttributeOverride attributeOverride = jpaFactory().createJavaAttributeOverride(this, createAttributeOverrideOwner());
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		AttributeOverride attributeOverrideResource = (AttributeOverride) this.persistentTypeResource.addAnnotation(index, AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		attributeOverride.initializeFromResource(attributeOverrideResource);
		this.fireItemAdded(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}
	
	protected IAttributeOverride.Owner createAttributeOverrideOwner() {
		return new AttributeOverrideOwner();
	}
	
	protected void addSpecifiedAttributeOverride(int index, IJavaAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(IAttributeOverride attributeOverride) {
		removeSpecifiedAttributeOverride(this.specifiedAttributeOverrides.indexOf(attributeOverride));
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		IJavaAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);
		
		//add the default attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		IJavaAttributeOverride defaultAttributeOverride = null;
		if (removedAttributeOverride.getName() != null) {
			if (CollectionTools.contains(allOverridableAttributeNames(), removedAttributeOverride.getName())) {
				defaultAttributeOverride = createAttributeOverride(new NullAttributeOverride(this.persistentTypeResource, removedAttributeOverride.getName()));
				this.defaultAttributeOverrides.add(defaultAttributeOverride);
			}
		}

		this.persistentTypeResource.removeAnnotation(index, AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		fireItemRemoved(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
		
		if (defaultAttributeOverride != null) {
			fireItemAdded(IEntity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST, defaultAttributeOverridesSize() - 1, defaultAttributeOverride);
		}
	}
	
	protected void removeSpecifiedAttributeOverride_(IJavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, AttributeOverrides.ANNOTATION_NAME);
		fireItemMoved(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addDefaultAttributeOverride(IJavaAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.defaultAttributeOverrides, IEntity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeDefaultAttributeOverride(IJavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.defaultAttributeOverrides, IEntity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST);
	}

	public IJavaAttributeOverride attributeOverrideNamed(String name) {
		return (IJavaAttributeOverride) overrideNamed(name, attributeOverrides());
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

	public IJavaAssociationOverride associationOverrideNamed(String name) {
		return (IJavaAssociationOverride) overrideNamed(name, associationOverrides());
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

	private IOverride overrideNamed(String name, ListIterator<? extends IOverride> overrides) {
		for (IOverride override : CollectionTools.iterable(overrides)) {
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

	private boolean containsOverride(String name, ListIterator<? extends IOverride> overrides) {
		return overrideNamed(name, overrides) != null;
	}


	@SuppressWarnings("unchecked")
	public ListIterator<IJavaAssociationOverride> associationOverrides() {
		return new CompositeListIterator<IJavaAssociationOverride>(specifiedAssociationOverrides(), defaultAssociationOverrides());
	}
	
	public int associationOverridesSize() {
		return this.specifiedAssociationOverridesSize() + this.defaultAssociationOverridesSize();
	}

	public  ListIterator<IJavaAssociationOverride> defaultAssociationOverrides() {
		return new CloneListIterator<IJavaAssociationOverride>(this.defaultAssociationOverrides);
	}
	
	public int defaultAssociationOverridesSize() {
		return this.defaultAssociationOverrides.size();
	}
	
	public ListIterator<IJavaAssociationOverride> specifiedAssociationOverrides() {
		return new CloneListIterator<IJavaAssociationOverride>(this.specifiedAssociationOverrides);
	}
	
	public int specifiedAssociationOverridesSize() {
		return this.specifiedAssociationOverrides.size();
	}

	public IJavaAssociationOverride addSpecifiedAssociationOverride(int index) {
		IJavaAssociationOverride associationOverride = jpaFactory().createJavaAssociationOverride(this, createAssociationOverrideOwner());
		this.specifiedAssociationOverrides.add(index, associationOverride);
		AssociationOverride associationOverrideResource = (AssociationOverride) this.persistentTypeResource.addAnnotation(index, AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		associationOverride.initializeFromResource(associationOverrideResource);
		this.fireItemAdded(IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		return associationOverride;
	}
	
	protected IAssociationOverride.Owner createAssociationOverrideOwner() {
		return new AssociationOverrideOwner();
	}
	
	protected void addSpecifiedAssociationOverride(int index, IJavaAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.specifiedAssociationOverrides, IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAssociationOverride(IAssociationOverride associationOverride) {
		removeSpecifiedAssociationOverride(this.specifiedAssociationOverrides.indexOf(associationOverride));
	}
	
	public void removeSpecifiedAssociationOverride(int index) {
		IJavaAssociationOverride removedAssociationOverride = this.specifiedAssociationOverrides.remove(index);
		IJavaAssociationOverride defaultAssociationOverride = null;

		//add the default association override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the association override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		if (removedAssociationOverride.getName() != null) {
			if (CollectionTools.contains(allOverridableAttributeNames(), removedAssociationOverride.getName())) {
				defaultAssociationOverride = createAssociationOverride(new NullAssociationOverride(this.persistentTypeResource, removedAssociationOverride.getName()));
				this.defaultAssociationOverrides.add(defaultAssociationOverride);
			}
		}
		this.persistentTypeResource.removeAnnotation(index, AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		fireItemRemoved(IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, removedAssociationOverride);

		if (defaultAssociationOverride != null) {
			fireItemAdded(IEntity.DEFAULT_ASSOCIATION_OVERRIDES_LIST, defaultAssociationOverridesSize() - 1, defaultAssociationOverride);
		}
	}
	
	protected void removeSpecifiedAssociationOverride_(IJavaAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.specifiedAssociationOverrides, IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAssociationOverrides, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, AssociationOverrides.ANNOTATION_NAME);
		fireItemMoved(IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addDefaultAssociationOverride(IJavaAssociationOverride associationOverride) {
		addItemToList(associationOverride, this.defaultAssociationOverrides, IEntity.DEFAULT_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected void removeDefaultAssociationOverride(IJavaAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.defaultAssociationOverrides, IEntity.DEFAULT_ASSOCIATION_OVERRIDES_LIST);
	}

	public ListIterator<IJavaNamedQuery> namedQueries() {
		return new CloneListIterator<IJavaNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public IJavaNamedQuery addNamedQuery(int index) {
		IJavaNamedQuery namedQuery = jpaFactory().createJavaNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		this.persistentTypeResource.addAnnotation(index, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		fireItemAdded(IEntity.NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, IJavaNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, IEntity.NAMED_QUERIES_LIST);
	}
	
	public void removeNamedQuery(INamedQuery namedQuery) {
		removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}
	
	public void removeNamedQuery(int index) {
		IJavaNamedQuery removedNamedQuery = this.namedQueries.remove(index);
		this.persistentTypeResource.removeAnnotation(index, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		fireItemRemoved(IEntity.NAMED_QUERIES_LIST, index, removedNamedQuery);
	}	
	
	protected void removeNamedQuery_(IJavaNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, IEntity.NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, NamedQueries.ANNOTATION_NAME);
		fireItemMoved(IEntity.NAMED_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<IJavaNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<IJavaNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public IJavaNamedNativeQuery addNamedNativeQuery(int index) {
		IJavaNamedNativeQuery namedNativeQuery = jpaFactory().createJavaNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.persistentTypeResource.addAnnotation(index, NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		fireItemAdded(IEntity.NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, IJavaNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, IEntity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void removeNamedNativeQuery(INamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}
	
	public void removeNamedNativeQuery(int index) {
		IJavaNamedNativeQuery removedNamedNativeQuery = this.namedNativeQueries.remove(index);
		this.persistentTypeResource.removeAnnotation(index, NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		fireItemRemoved(IEntity.NAMED_NATIVE_QUERIES_LIST, index, removedNamedNativeQuery);
	}	
	
	protected void removeNamedNativeQuery_(IJavaNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, IEntity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex);
		this.persistentTypeResource.move(targetIndex, sourceIndex, NamedNativeQueries.ANNOTATION_NAME);
		fireItemMoved(IEntity.NAMED_NATIVE_QUERIES_LIST, targetIndex, sourceIndex);		
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
		firePropertyChanged(IEntity.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(IEntity.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
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

	public IEntity parentEntity() {
		for (Iterator<IPersistentType> i = persistentType().inheritanceHierarchy(); i.hasNext();) {
			ITypeMapping typeMapping = i.next().getMapping();
			if (typeMapping != this && typeMapping instanceof IEntity) {
				return (IEntity) typeMapping;
			}
		}
		return this;
	}

	public IEntity rootEntity() {
		IEntity rootEntity = this;
		for (Iterator<IPersistentType> i = persistentType().inheritanceHierarchy(); i.hasNext();) {
			IPersistentType persistentType = i.next();
			if (persistentType.getMapping() instanceof IEntity) {
				rootEntity = (IEntity) persistentType.getMapping();
			}
		}
		return rootEntity;
	}

	public String primaryKeyColumnName() {
		return primaryKeyColumnName(persistentType().allAttributes());
	}
	
	public static String primaryKeyColumnName(Iterator<IPersistentAttribute> attributes) {
		String pkColumnName = null;
		for (Iterator<IPersistentAttribute> stream = attributes; stream.hasNext();) {
			IPersistentAttribute attribute = stream.next();
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
	public Iterator<ITable> associatedTables() {
		return new CompositeIterator<ITable>(this.getTable(), this.secondaryTables());
	}

	@Override
	public Iterator<ITable> associatedTablesIncludingInherited() {
		return new CompositeIterator<ITable>(new TransformationIterator<ITypeMapping, Iterator<ITable>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<ITable> transform(ITypeMapping mapping) {
				return new FilteringIterator<ITable, ITable>(mapping.associatedTables()) {
					@Override
					protected boolean accept(ITable o) {
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

	protected Iterator<String> nonNullTableNames(Iterator<ITable> tables) {
		return new FilteringIterator<String, String>(this.tableNames(tables)) {
			@Override
			protected boolean accept(String o) {
				return o != null;
			}
		};
	}

	protected Iterator<String> tableNames(Iterator<ITable> tables) {
		return new TransformationIterator<ITable, String>(tables) {
			@Override
			protected String transform(ITable t) {
				return t.getName();
			}
		};
	}

	/**
	 * Return an iterator of Entities, each which inherits from the one before,
	 * and terminates at the root entity (or at the point of cyclicity).
	 */
	protected Iterator<ITypeMapping> inheritanceHierarchy() {
		return new TransformationIterator<IPersistentType, ITypeMapping>(persistentType().inheritanceHierarchy()) {
			@Override
			protected ITypeMapping transform(IPersistentType type) {
				return type.getMapping();
			}
		};
	}

	@Override
	public Iterator<String> allOverridableAttributeNames() {
		return new CompositeIterator<String>(new TransformationIterator<ITypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(ITypeMapping mapping) {
				return mapping.overridableAttributeNames();
			}
		});
	}

	@Override
	public Iterator<String> allOverridableAssociationNames() {
		return new CompositeIterator<String>(new TransformationIterator<ITypeMapping, Iterator<String>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<String> transform(ITypeMapping mapping) {
				return mapping.overridableAssociationNames();
			}
		});
	}
	
	@Override
	public void update(JavaPersistentTypeResource persistentTypeResource) {
		super.update(persistentTypeResource);
		this.entityResource = (Entity) persistentTypeResource.mappingAnnotation(Entity.ANNOTATION_NAME);
		
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
		
	protected String specifiedName(Entity entityResource) {
		return entityResource.getName();
	}
	
	protected String defaultName(JavaPersistentTypeResource persistentTypeResource) {
		return persistentTypeResource.getName();
	}

	protected void updateTable(JavaPersistentTypeResource persistentTypeResource) {
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
	
	protected void updateDiscriminatorColumn(JavaPersistentTypeResource persistentTypeResource) {
		getDiscriminatorColumn().update(persistentTypeResource);
	}
	
	protected void updateDiscriminatorValue(DiscriminatorValue discriminatorValueResource) {
		this.setSpecifiedDiscriminatorValue_(discriminatorValueResource.getValue());
		this.setDefaultDiscriminatorValue(this.javaDefaultDiscriminatorValue());
	}
	
	protected boolean discriminatorValueIsAllowed(JavaPersistentTypeResource persistentTypeResource) {
		return !persistentTypeResource.isAbstract();
	}
	
	protected void updateSecondaryTables(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaSecondaryTable> secondaryTables = specifiedSecondaryTables();
		ListIterator<JavaResource> resourceSecondaryTables = persistentTypeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		
		while (secondaryTables.hasNext()) {
			IJavaSecondaryTable secondaryTable = secondaryTables.next();
			if (resourceSecondaryTables.hasNext()) {
				secondaryTable.update((SecondaryTable) resourceSecondaryTables.next());
			}
			else {
				removeSpecifiedSecondaryTable_(secondaryTable);
			}
		}
		
		while (resourceSecondaryTables.hasNext()) {
			addSpecifiedSecondaryTable(specifiedSecondaryTablesSize(), createSecondaryTable((SecondaryTable) resourceSecondaryTables.next()));
		}
	}

	protected IJavaSecondaryTable createSecondaryTable(SecondaryTable secondaryTableResource) {
		IJavaSecondaryTable secondaryTable = jpaFactory().createJavaSecondaryTable(this);
		secondaryTable.initializeFromResource(secondaryTableResource);
		return secondaryTable;
	}

	protected void updateTableGenerator(JavaPersistentTypeResource persistentTypeResource) {
		TableGenerator tableGeneratorResource = tableGenerator(persistentTypeResource);
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
	
	protected IJavaTableGenerator createTableGenerator(TableGenerator tableGeneratorResource) {
		IJavaTableGenerator tableGenerator = jpaFactory().createJavaTableGenerator(this);
		tableGenerator.initializeFromResource(tableGeneratorResource);
		return tableGenerator;
	}
	
	protected TableGenerator tableGenerator(JavaPersistentTypeResource persistentTypeResource) {
		return (TableGenerator) persistentTypeResource.annotation(TableGenerator.ANNOTATION_NAME);
	}

	protected void updateSequenceGenerator(JavaPersistentTypeResource persistentTypeResource) {
		SequenceGenerator sequenceGeneratorResource = sequenceGenerator(persistentTypeResource);
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
	
	protected IJavaSequenceGenerator createSequenceGenerator(SequenceGenerator sequenceGeneratorResource) {
		IJavaSequenceGenerator sequenceGenerator = jpaFactory().createJavaSequenceGenerator(this);
		sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
		return sequenceGenerator;
	}
	
	protected SequenceGenerator sequenceGenerator(JavaPersistentTypeResource persistentTypeResource) {
		return (SequenceGenerator) persistentTypeResource.annotation(SequenceGenerator.ANNOTATION_NAME);
	}

	
	protected void updateSpecifiedPrimaryKeyJoinColumns(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<JavaResource> resourcePrimaryKeyJoinColumns = persistentTypeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		
		while (primaryKeyJoinColumns.hasNext()) {
			IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
			if (resourcePrimaryKeyJoinColumns.hasNext()) {
				primaryKeyJoinColumn.update((PrimaryKeyJoinColumn) resourcePrimaryKeyJoinColumns.next());
			}
			else {
				removeSpecifiedPrimaryKeyJoinColumn_(primaryKeyJoinColumn);
			}
		}
		
		while (resourcePrimaryKeyJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(specifiedPrimaryKeyJoinColumnsSize(), createPrimaryKeyJoinColumn((PrimaryKeyJoinColumn) resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected IJavaPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumnResource) {
		IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		primaryKeyJoinColumn.initializeFromResource(primaryKeyJoinColumnResource);
		return primaryKeyJoinColumn;
	}

	protected void updateDefaultPrimaryKeyJoinColumn(JavaPersistentTypeResource persistentTypeResource) {
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
		
	protected void updateSpecifiedAttributeOverrides(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<JavaResource> resourceAttributeOverrides = persistentTypeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		while (attributeOverrides.hasNext()) {
			IJavaAttributeOverride attributeOverride = attributeOverrides.next();
			if (resourceAttributeOverrides.hasNext()) {
				attributeOverride.update((AttributeOverride) resourceAttributeOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride_(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), createAttributeOverride((AttributeOverride) resourceAttributeOverrides.next()));
		}	
	}
	
	protected IJavaAttributeOverride createAttributeOverride(AttributeOverride attributeOverrideResource) {
		IJavaAttributeOverride attributeOverride = jpaFactory().createJavaAttributeOverride(this, createAttributeOverrideOwner());
		attributeOverride.initializeFromResource(attributeOverrideResource);
		return attributeOverride;
	}
	
	protected void updateDefaultAttributeOverrides(JavaPersistentTypeResource persistentTypeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			IJavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
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
		for (IJavaAttributeOverride attributeOverride : CollectionTools.iterable(defaultAttributeOverrides())) {
			if (!attributeNames.contains(attributeOverride.getName())
				|| containsSpecifiedAttributeOverride(attributeOverride.getName())) {
				removeDefaultAttributeOverride(attributeOverride);
			}
		}
	}

	protected void updateSpecifiedAssociationOverrides(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaAssociationOverride> associationOverrides = specifiedAssociationOverrides();
		ListIterator<JavaResource> resourceAssociationOverrides = persistentTypeResource.annotations(AssociationOverride.ANNOTATION_NAME, AssociationOverrides.ANNOTATION_NAME);
		
		while (associationOverrides.hasNext()) {
			IJavaAssociationOverride associationOverride = associationOverrides.next();
			if (resourceAssociationOverrides.hasNext()) {
				associationOverride.update((AssociationOverride) resourceAssociationOverrides.next());
			}
			else {
				removeSpecifiedAssociationOverride_(associationOverride);
			}
		}
		
		while (resourceAssociationOverrides.hasNext()) {
			addSpecifiedAssociationOverride(specifiedAssociationOverridesSize(), createAssociationOverride((AssociationOverride) resourceAssociationOverrides.next()));
		}	
	}
	
	protected IJavaAssociationOverride createAssociationOverride(AssociationOverride associationOverrideResource) {
		IJavaAssociationOverride associationOverride = jpaFactory().createJavaAssociationOverride(this, createAssociationOverrideOwner());
		associationOverride.initializeFromResource(associationOverrideResource);
		return associationOverride;
	}
	
	protected void updateDefaultAssociationOverrides(JavaPersistentTypeResource persistentTypeResource) {
		for (Iterator<String> i = allOverridableAssociationNames(); i.hasNext(); ) {
			String associationName = i.next();
			IJavaAssociationOverride associationOverride = associationOverrideNamed(associationName);
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
		for (IJavaAssociationOverride associationOverride : CollectionTools.iterable(defaultAssociationOverrides())) {
			if (!associationNames.contains(associationOverride.getName())
				|| containsSpecifiedAssociationOverride(associationOverride.getName())) {
				removeDefaultAssociationOverride(associationOverride);
			}
		}
	}

	protected void updateNamedQueries(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaNamedQuery> namedQueries = namedQueries();
		ListIterator<JavaResource> resourceNamedQueries = persistentTypeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		
		while (namedQueries.hasNext()) {
			IJavaNamedQuery namedQuery = namedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update((NamedQuery) resourceNamedQueries.next());
			}
			else {
				removeNamedQuery_(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(namedQueriesSize(), createNamedQuery((NamedQuery) resourceNamedQueries.next()));
		}	
	}
	
	protected void updateNamedNativeQueries(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaNamedNativeQuery> namedNativeQueries = namedNativeQueries();
		ListIterator<JavaResource> resourceNamedNativeQueries = persistentTypeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		
		while (namedNativeQueries.hasNext()) {
			IJavaNamedNativeQuery namedQuery = namedNativeQueries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update((NamedNativeQuery) resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery_(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(namedQueriesSize(), createNamedNativeQuery((NamedNativeQuery) resourceNamedNativeQueries.next()));
		}	
	}
	
	
	protected IJavaNamedQuery createNamedQuery(NamedQuery namedQueryResource) {
		IJavaNamedQuery namedQuery = jpaFactory().createJavaNamedQuery(this);
		namedQuery.initializeFromResource(namedQueryResource);
		return namedQuery;
	}
	
	protected IJavaNamedNativeQuery createNamedNativeQuery(NamedNativeQuery namedNativeQueryResource) {
		IJavaNamedNativeQuery namedNativeQuery = jpaFactory().createJavaNamedNativeQuery(this);
		namedNativeQuery.initializeFromResource(namedNativeQueryResource);
		return namedNativeQuery;
	}

	protected void updateIdClass(JavaPersistentTypeResource typeResource) {
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
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getTable().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (IJavaSecondaryTable sTable : CollectionTools.iterable(this.secondaryTables())) {
			result = sTable.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (IJavaPrimaryKeyJoinColumn column : CollectionTools.iterable(this.primaryKeyJoinColumns())) {
			result = column.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (IJavaAttributeOverride override : CollectionTools.iterable(this.attributeOverrides())) {
			result = override.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (IJavaAssociationOverride override : CollectionTools.iterable(this.associationOverrides())) {
			result = override.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		result = this.getDiscriminatorColumn().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.getTableGenerator() != null) {
			result = this.getTableGenerator().candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		if (this.getSequenceGenerator() != null) {
			result = this.getSequenceGenerator().candidateValuesFor(pos, filter, astRoot);
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
		
		addTableMessages(messages, astRoot);
		addIdMessages(messages, astRoot);
		
		for (IJavaSecondaryTable context : specifiedSecondaryTables) {
			context.addToMessages(messages, astRoot);
		}

		for (Iterator<IJavaAttributeOverride> stream = this.attributeOverrides(); stream.hasNext();) {
			stream.next().addToMessages(messages, astRoot);
		}
		
		for (Iterator<IJavaAssociationOverride> stream = this.associationOverrides(); stream.hasNext();) {
			stream.next().addToMessages(messages, astRoot);
		}
		
	}
	
	protected void addTableMessages(List<IMessage> messages, CompilationUnit astRoot) {
		boolean doContinue = table.isConnected();
		String schema = table.getSchema();
		
		if (doContinue && ! table.hasResolvedSchema()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, table.getName()}, 
						table, table.schemaTextRange(astRoot))
				);
			doContinue = false;
		}
		
		if (doContinue && ! table.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.TABLE_UNRESOLVED_NAME,
						new String[] {table.getName()}, 
						table, table.nameTextRange(astRoot))
				);
		}
	}
	
	
	protected void addIdMessages(List<IMessage> messages, CompilationUnit astRoot) {
		addNoIdMessage(messages, astRoot);
		
	}
	
	protected void addNoIdMessage(List<IMessage> messages, CompilationUnit astRoot) {
		if (entityHasNoId()) {
			messages.add(
				JpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					IJpaValidationMessages.ENTITY_NO_ID,
					new String[] {this.getName()},
					this, this.validationTextRange(astRoot))
			);
		}
	}
	
	private boolean entityHasNoId() {
		return ! this.entityHasId();
	}

	private boolean entityHasId() {
		for (Iterator<IPersistentAttribute> stream = persistentType().allAttributes(); stream.hasNext(); ) {
			if (stream.next().isIdAttribute()) {
				return true;
			}
		}
		return false;
	}
	
	class PrimaryKeyJoinColumnOwner implements IAbstractJoinColumn.Owner
	{
		public ITextRange validationTextRange(CompilationUnit astRoot) {
			return JavaEntity.this.validationTextRange(astRoot);
		}

		public ITypeMapping typeMapping() {
			return JavaEntity.this;
		}

		public Table dbTable(String tableName) {
			return JavaEntity.this.dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity parentEntity = JavaEntity.this.parentEntity();
			return (parentEntity == null) ? null : parentEntity.primaryDbTable();
		}

		public int joinColumnsSize() {
			return JavaEntity.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return JavaEntity.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}
		
		public String defaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return JavaEntity.this.parentEntity().primaryKeyColumnName();
		}
	}
	
	class AttributeOverrideOwner implements IAttributeOverride.Owner {

		public IColumnMapping columnMapping(String attributeName) {
			if (attributeName == null) {
				return null;
			}
			for (Iterator<IPersistentAttribute> stream = persistentType().allAttributes(); stream.hasNext();) {
				IPersistentAttribute persAttribute = stream.next();
				if (attributeName.equals(persAttribute.getName())) {
					if (persAttribute.getMapping() instanceof IColumnMapping) {
						return (IColumnMapping) persAttribute.getMapping();
					}
				}
			}
			return null;
		}

		public boolean isVirtual(IOverride override) {
			return JavaEntity.this.defaultAttributeOverrides.contains(override);
		}

		public ITypeMapping typeMapping() {
			return JavaEntity.this;
		}

		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	class AssociationOverrideOwner implements IAssociationOverride.Owner {

		public IRelationshipMapping relationshipMapping(String attributeName) {
			if (attributeName == null) {
				return null;
			}
			for (Iterator<IPersistentAttribute> stream = persistentType().allAttributes(); stream.hasNext();) {
				IPersistentAttribute persAttribute = stream.next();
				if (attributeName.equals(persAttribute.getName())) {
					if (persAttribute.getMapping() instanceof IRelationshipMapping) {
						return (IRelationshipMapping) persAttribute.getMapping();
					}
				}
			}
			return null;
		}

		public boolean isVirtual(IOverride override) {
			return JavaEntity.this.defaultAssociationOverrides.contains(override);
		}

		public ITypeMapping typeMapping() {
			return JavaEntity.this;
		}

		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
