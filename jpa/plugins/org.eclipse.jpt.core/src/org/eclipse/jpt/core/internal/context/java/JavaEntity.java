/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValue;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.Inheritance;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.NullAttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.NullColumn;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumns;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTables;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;


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

	protected String specifiedDiscriminatorValue;
	
	protected IJavaDiscriminatorColumn discriminatorColumn;

	protected IJavaSequenceGenerator sequenceGenerator;

	protected IJavaTableGenerator tableGenerator;

	protected final List<IJavaAttributeOverride> specifiedAttributeOverrides;

	protected final List<IJavaAttributeOverride> defaultAttributeOverrides;

//	protected List<IAssociationOverride> specifiedAssociationOverrides;
//
//	protected List<IAssociationOverride> defaultAssociationOverrides;
//
//	protected List<INamedQuery> namedQueries;
//
//	protected List<INamedNativeQuery> namedNativeQueries;
//
//	protected String idClass;

	
	public JavaEntity(IJavaPersistentType parent) {
		super(parent);
		this.table = jpaFactory().createJavaTable(this);
		this.discriminatorColumn = createJavaDiscriminatorColumn();
		this.specifiedSecondaryTables = new ArrayList<IJavaSecondaryTable>();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<IJavaPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumn = this.jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedAttributeOverrides = new ArrayList<IJavaAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<IJavaAttributeOverride>();
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
		this.discriminatorColumn.initializeFromResource(persistentTypeResource);
		this.initializeSecondaryTables(persistentTypeResource);
		this.initializeTableGenerator(persistentTypeResource);
		this.initializeSequenceGenerator(persistentTypeResource);
		this.initializePrimaryKeyJoinColumns(persistentTypeResource);
		this.initializeAttributeOverrides(persistentTypeResource);
		this.initializeDefaultAttributeOverrides(persistentTypeResource);
	}
	
	protected void initializeSecondaryTables(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			IJavaSecondaryTable secondaryTable = jpaFactory().createJavaSecondaryTable(this);
			secondaryTable.initializeFromResource((SecondaryTable) annotations.next());
			this.specifiedSecondaryTables.add(secondaryTable);
		}
	}
	
	protected void initializeTableGenerator(JavaPersistentTypeResource persistentTypeResource) {
		TableGenerator tableGeneratorResource = tableGenerator(persistentTypeResource);
		if (tableGeneratorResource != null) {
			this.tableGenerator = jpaFactory().createJavaTableGenerator(this);
			this.tableGenerator.initializeFromResource(tableGeneratorResource);
		}
	}
	
	protected void initializeSequenceGenerator(JavaPersistentTypeResource persistentTypeResource) {
		SequenceGenerator sequenceGeneratorResource = sequenceGenerator(persistentTypeResource);
		if (sequenceGeneratorResource != null) {
			this.sequenceGenerator = jpaFactory().createJavaSequenceGenerator(this);
			this.sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
		}
	}
	
	protected void initializePrimaryKeyJoinColumns(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
			primaryKeyJoinColumn.initializeFromResource((PrimaryKeyJoinColumn) annotations.next());
			this.specifiedPrimaryKeyJoinColumns.add(primaryKeyJoinColumn);
		}
	}
	
	protected void initializeAttributeOverrides(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			IJavaAttributeOverride attributeOverride = jpaFactory().createJavaAttributeOverride(this, this);
			attributeOverride.initializeFromResource((AttributeOverride) annotations.next());
			this.specifiedAttributeOverrides.add(attributeOverride);
		}
	}
	
	protected void initializeDefaultAttributeOverrides(JavaPersistentTypeResource persistentTypeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			IJavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				attributeOverride = createAttributeOverride(new NullAttributeOverride(persistentTypeResource));
				attributeOverride.setName(attributeName);
				this.defaultAttributeOverrides.add(attributeOverride);
			}
		}
	}

	//query for the table resource every time on setters.
	//call one setter and the tableResource could change. 
	//You could call more than one setter before this object has received any notification
	//from the java resource model
	protected Inheritance inheritanceResource() {
		//TODO get the NullInheritance from the resource model or build it here in the context model??
		return (Inheritance) this.persistentTypeResource.nonNullAnnotation(Inheritance.ANNOTATION_NAME);
	}
	
	protected DiscriminatorValue discriminatorValueResource() {
		return (DiscriminatorValue) this.persistentTypeResource.nonNullAnnotation(DiscriminatorValue.ANNOTATION_NAME);
	}

	
	//****************** IOverride.Owner implemenation *******************
	public IColumnMapping columnMapping(String attributeName) {
		if (attributeName == null) {
			return null;
		}
		for (Iterator<IPersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
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
		return this.defaultAttributeOverrides.contains(override);
	}

	//****************** IJavaTypeMapping implemenation *******************

	public String annotationName() {
		return Entity.ANNOTATION_NAME;
	}
	
	public String getKey() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public boolean isMapped() {
		return true;
	}
	
	@Override
	public String getName() {
		return (this.getSpecifiedName() == null) ? this.getDefaultName() : this.getSpecifiedName();
	}

	//****************** IEntity implemenation *******************

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
	
	public ISecondaryTable addSpecifiedSecondaryTable(int index) {
		IJavaSecondaryTable secondaryTable = jpaFactory().createJavaSecondaryTable(this);
		this.specifiedSecondaryTables.add(index, secondaryTable);
		this.persistentTypeResource.addAnnotation(index, SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		fireItemAdded(IEntity.SPECIFIED_SECONDARY_TABLES_LIST, index, secondaryTable);
		return secondaryTable;
	}
	
	protected void addSpecifiedSecondaryTable(int index, IJavaSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.specifiedSecondaryTables, IEntity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void removeSpecifiedSecondaryTable(int index) {
		IJavaSecondaryTable removedSecondaryTable = this.specifiedSecondaryTables.remove(index);
		this.persistentTypeResource.removeAnnotation(index, SecondaryTable.ANNOTATION_NAME, SecondaryTables.ANNOTATION_NAME);
		fireItemRemoved(IEntity.SPECIFIED_SECONDARY_TABLES_LIST, index, removedSecondaryTable);
	}
	
	protected void removeSpecifiedSecondaryTable(IJavaSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.specifiedSecondaryTables, IEntity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void moveSpecifiedSecondaryTable(int oldIndex, int newIndex) {
		this.persistentTypeResource.move(oldIndex, newIndex, SecondaryTables.ANNOTATION_NAME);
		moveItemInList(newIndex, oldIndex, this.specifiedSecondaryTables, IEntity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public ListIterator<IJavaSecondaryTable> secondaryTables() {
		return specifiedSecondaryTables();
	}

	public int secondaryTablesSize() {
		return specifiedSecondaryTablesSize();
	}
	
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

	public String getDiscriminatorValue() {
		return (this.getSpecifiedDiscriminatorValue() == null) ? getDefaultDiscriminatorValue() : this.getSpecifiedDiscriminatorValue();
	}

	public IJavaTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		IJavaTableGenerator tableGenerator = jpaFactory().createJavaTableGenerator(this);
		setTableGenerator(tableGenerator);
		return tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		setTableGenerator(null);
	}
	
	public IJavaTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}
	
	protected void setTableGenerator(IJavaTableGenerator newTableGenerator) {
		IJavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		if (newTableGenerator != null) {
			this.persistentTypeResource.addAnnotation(TableGenerator.ANNOTATION_NAME);
		}
		else {
			this.persistentTypeResource.removeAnnotation(TableGenerator.ANNOTATION_NAME);
		}
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}

	public IJavaSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		IJavaSequenceGenerator sequenceGenerator = jpaFactory().createJavaSequenceGenerator(this);
		setSequenceGenerator(sequenceGenerator);
		return sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		this.setSequenceGenerator(null);
	}
	
	public IJavaSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(IJavaSequenceGenerator newSequenceGenerator) {
		IJavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		if (newSequenceGenerator != null) {
			this.persistentTypeResource.addAnnotation(SequenceGenerator.ANNOTATION_NAME);
		}
		else {
			this.persistentTypeResource.removeAnnotation(SequenceGenerator.ANNOTATION_NAME);
		}
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	public ListIterator<IJavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumns() : this.specifiedPrimaryKeyJoinColumns();
	}
	
	public ListIterator<IJavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<IJavaPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}
	
	public ListIterator<IJavaPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		return new SingleElementListIterator<IJavaPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn);
	}

	public IPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		this.persistentTypeResource.addAnnotation(index, PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		this.firePropertyChanged(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		return primaryKeyJoinColumn;
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		IJavaPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		this.persistentTypeResource.removeAnnotation(index, PrimaryKeyJoinColumn.ANNOTATION_NAME, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		fireItemRemoved(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn(IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int oldIndex, int newIndex) {
		this.persistentTypeResource.move(oldIndex, newIndex, PrimaryKeyJoinColumns.ANNOTATION_NAME);
		moveItemInList(newIndex, oldIndex, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);		
	}
	
	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	
	public ListIterator<IJavaAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<IJavaAttributeOverride>(specifiedAttributeOverrides(), defaultAttributeOverrides());
	}
	
	public ListIterator<IJavaAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<IJavaAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	public ListIterator<IJavaAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<IJavaAttributeOverride>(this.specifiedAttributeOverrides);
	}
	
	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public IAttributeOverride addSpecifiedAttributeOverride(int index) {
		IJavaAttributeOverride attributeOverride = jpaFactory().createJavaAttributeOverride(this, this);
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		this.persistentTypeResource.addAnnotation(index, AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		this.firePropertyChanged(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}
	
	protected void addSpecifiedAttributeOverride(int index, IJavaAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		IJavaAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);
		this.persistentTypeResource.removeAnnotation(index, AttributeOverride.ANNOTATION_NAME, AttributeOverrides.ANNOTATION_NAME);
		fireItemRemoved(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride(IJavaAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}

	public void moveSpecifiedAttributeOverride(int oldIndex, int newIndex) {
		this.persistentTypeResource.move(oldIndex, newIndex, AttributeOverrides.ANNOTATION_NAME);
		moveItemInList(newIndex, oldIndex, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
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
//
//	public boolean containsAssociationOverride(String name) {
//		return containsOverride(name, getAssociationOverrides());
//	}
//
//	public boolean containsSpecifiedAssociationOverride(String name) {
//		return containsOverride(name, getSpecifiedAssociationOverrides());
//	}
//
//	public boolean containsDefaultAssociationOverride(String name) {
//		return containsOverride(name, getDefaultAssociationOverrides());
//	}
//
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

//	public EList<IAttributeOverride> getSpecifiedAttributeOverrides() {
//		if (specifiedAttributeOverrides == null) {
//			specifiedAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES);
//		}
//		return specifiedAttributeOverrides;
//	}
//	
//	public EList<IAttributeOverride> getDefaultAttributeOverrides() {
//		if (defaultAttributeOverrides == null) {
//			defaultAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ATTRIBUTE_OVERRIDES);
//		}
//		return defaultAttributeOverrides;
//	}
//
//	public EList<IAssociationOverride> getAssociationOverrides() {
//		EList<IAssociationOverride> list = new EObjectEList<IAssociationOverride>(IAssociationOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__ASSOCIATION_OVERRIDES);
//		list.addAll(getSpecifiedAssociationOverrides());
//		list.addAll(getDefaultAssociationOverrides());
//		return list;
//	}
//
//	public EList<IAssociationOverride> getSpecifiedAssociationOverrides() {
//		if (specifiedAssociationOverrides == null) {
//			specifiedAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__SPECIFIED_ASSOCIATION_OVERRIDES);
//		}
//		return specifiedAssociationOverrides;
//	}
//
//	public EList<IAssociationOverride> getDefaultAssociationOverrides() {
//		if (defaultAssociationOverrides == null) {
//			defaultAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__DEFAULT_ASSOCIATION_OVERRIDES);
//		}
//		return defaultAssociationOverrides;
//	}
//
//	public EList<INamedQuery> getNamedQueries() {
//		if (namedQueries == null) {
//			namedQueries = new EObjectContainmentEList<INamedQuery>(INamedQuery.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_QUERIES);
//		}
//		return namedQueries;
//	}
//
//	public EList<INamedNativeQuery> getNamedNativeQueries() {
//		if (namedNativeQueries == null) {
//			namedNativeQueries = new EObjectContainmentEList<INamedNativeQuery>(INamedNativeQuery.class, this, JpaJavaMappingsPackage.JAVA_ENTITY__NAMED_NATIVE_QUERIES);
//		}
//		return namedNativeQueries;
//	}
//
//	public String getIdClass() {
//		return idClass;
//	}
//
//	public void setIdClass(String newIdClass) {
//		String oldIdClass = idClass;
//		idClass = newIdClass;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ENTITY__ID_CLASS, oldIdClass, idClass));
//	}
//
//	public boolean discriminatorValueIsAllowed() {
//		return !getType().isAbstract();
//	}
//
	public IEntity parentEntity() {
		for (Iterator<IPersistentType> i = getPersistentType().inheritanceHierarchy(); i.hasNext();) {
			ITypeMapping typeMapping = i.next().getMapping();
			if (typeMapping != this && typeMapping instanceof IEntity) {
				return (IEntity) typeMapping;
			}
		}
		return this;
	}

	public ITypeMapping typeMapping() {
		return this;
	}

	public IEntity rootEntity() {
		IEntity rootEntity = this;
		for (Iterator<IPersistentType> i = getPersistentType().inheritanceHierarchy(); i.hasNext();) {
			IPersistentType persistentType = i.next();
			if (persistentType.getMapping() instanceof IEntity) {
				rootEntity = (IEntity) persistentType.getMapping();
			}
		}
		return rootEntity;
	}

	@Override
	public String getTableName() {
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
		this.updateSecondaryTables(persistentTypeResource);
		this.updateTableGenerator(persistentTypeResource);
		this.updateSequenceGenerator(persistentTypeResource);
		this.updateSpecifiedPrimaryKeyJoinColumns(persistentTypeResource);
		this.updateDefaultPrimaryKeyJoinColumns(persistentTypeResource);
		this.updateSpecifiedAttributeOverrides(persistentTypeResource);
		this.updateDefaultAttributeOverrides(persistentTypeResource);
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
		this.setSpecifiedInheritanceStrategy(this.specifiedInheritanceStrategy(inheritanceResource));
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
		this.setSpecifiedDiscriminatorValue(discriminatorValueResource.getValue());
		this.setDefaultDiscriminatorValue(this.javaDefaultDiscriminatorValue());
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
				removeSpecifiedSecondaryTable(secondaryTable);
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
				IJavaTableGenerator tableGenerator = addTableGenerator();
				tableGenerator.initializeFromResource(tableGeneratorResource);
			}
			else {
				getTableGenerator().update(tableGeneratorResource);
			}
		}
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
				IJavaSequenceGenerator sequenceGenerator = addSequenceGenerator();
				sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
			}
			else {
				getSequenceGenerator().update(sequenceGeneratorResource);
			}
		}
	}
		
	protected TableGenerator tableGenerator(JavaPersistentTypeResource persistentTypeResource) {
		return (TableGenerator) persistentTypeResource.annotation(TableGenerator.ANNOTATION_NAME);
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
				removeSpecifiedPrimaryKeyJoinColumn(primaryKeyJoinColumn);
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

	protected void updateDefaultPrimaryKeyJoinColumns(JavaPersistentTypeResource persistentTypeResource) {
		this.defaultPrimaryKeyJoinColumn.update(new NullPrimaryKeyJoinColumn(persistentTypeResource));
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
				removeSpecifiedAttributeOverride(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), createAttributeOverride((AttributeOverride) resourceAttributeOverrides.next()));
		}	
	}
	
	protected IJavaAttributeOverride createAttributeOverride(AttributeOverride attributeOverrideResource) {
		IJavaAttributeOverride attributeOverride = jpaFactory().createJavaAttributeOverride(this, this);
		attributeOverride.initializeFromResource(attributeOverrideResource);
		return attributeOverride;
	}
	
	protected void updateDefaultAttributeOverrides(JavaPersistentTypeResource persistentTypeResource) {
		for (Iterator<String> i = allOverridableAttributeNames(); i.hasNext(); ) {
			String attributeName = i.next();
			IJavaAttributeOverride attributeOverride = attributeOverrideNamed(attributeName);
			if (attributeOverride == null) {
				attributeOverride = createAttributeOverride(new NullAttributeOverride(persistentTypeResource));
				addDefaultAttributeOverride(attributeOverride);
				attributeOverride.setName(attributeName);
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

	
//	@Override
//	public void updateFromJava(CompilationUnit astRoot) {
//		this.setSpecifiedName(this.getType().annotationElementValue(NAME_ADAPTER, astRoot));
//		this.setDefaultName(this.getType().getName());
//		this.getJavaTable().updateFromJava(astRoot);
//		this.updateSecondaryTablesFromJava(astRoot);
//		this.updateNamedQueriesFromJava(astRoot);
//		this.updateNamedNativeQueriesFromJava(astRoot);
//		this.updateSpecifiedPrimaryKeyJoinColumnsFromJava(astRoot);
//		this.updateAttributeOverridesFromJava(astRoot);
//		this.updateAssociationOverridesFromJava(astRoot);
//		this.setInheritanceStrategy(InheritanceType.fromJavaAnnotationValue(this.inheritanceStrategyAdapter.getValue(astRoot)));
//		this.getJavaDiscriminatorColumn().updateFromJava(astRoot);
//		this.setSpecifiedDiscriminatorValue(this.discriminatorValueAdapter.getValue(astRoot));
//		this.setDefaultDiscriminatorValue(this.javaDefaultDiscriminatorValue());
//		this.updateTableGeneratorFromJava(astRoot);
//		this.updateSequenceGeneratorFromJava(astRoot);
//		this.updateIdClassFromJava(astRoot);
//	}
//
//	private void updateIdClassFromJava(CompilationUnit astRoot) {
//		if (this.idClassAnnotationAdapter.getAnnotation(astRoot) == null) {
//			this.setIdClass(null);
//		}
//		else {
//			this.setIdClass(this.idClassValueAdapter.getValue(astRoot));
//		}
//	}
//

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

	public String primaryKeyColumnName() {
		String pkColumnName = null;
		for (Iterator<IPersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
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
				return new FilteringIterator<ITable>(mapping.associatedTables()) {
					@Override
					protected boolean accept(Object o) {
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
		return new FilteringIterator<String>(this.tableNames(tables)) {
			@Override
			protected boolean accept(Object o) {
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
		return new TransformationIterator<IPersistentType, ITypeMapping>(getPersistentType().inheritanceHierarchy()) {
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

//	public IAttributeOverride createAttributeOverride(int index) {
//		return createJavaAttributeOverride(index);
//	}
//
//	private JavaAttributeOverride createJavaAttributeOverride(int index) {
//		return JavaAttributeOverride.createAttributeOverride(new AttributeOverrideOwner(this), this.getType(), index);
//	}
//
//	public IAssociationOverride createAssociationOverride(int index) {
//		return createJavaAssociationOverride(index);
//	}
//
//	private JavaAssociationOverride createJavaAssociationOverride(int index) {
//		return JavaAssociationOverride.createAssociationOverride(new AssociationOverrideOwner(this), this.getType(), index);
//	}
//
//	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
//		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
//	}
//
//	public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
//		return this.createJavaPrimaryKeyJoinColumn(index);
//	}
//
//	private JavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn(int index) {
//		return JavaPrimaryKeyJoinColumn.createEntityPrimaryKeyJoinColumn(buildPkJoinColumnOwner(), this.getType(), index);
//	}
//
//	protected IAbstractJoinColumn.Owner buildPkJoinColumnOwner() {
//		return new IEntity.PrimaryKeyJoinColumnOwner(this);
//	}
//
//	public JavaNamedQuery createNamedQuery(int index) {
//		return createJavaNamedQuery(index);
//	}
//
//	private JavaNamedQuery createJavaNamedQuery(int index) {
//		return JavaNamedQuery.createJavaNamedQuery(this.getType(), index);
//	}
//
//	public JavaNamedNativeQuery createNamedNativeQuery(int index) {
//		return createJavaNamedNativeQuery(index);
//	}
//
//	private JavaNamedNativeQuery createJavaNamedNativeQuery(int index) {
//		return JavaNamedNativeQuery.createJavaNamedNativeQuery(this.getType(), index);
//	}
//
//	public ISequenceGenerator createSequenceGenerator() {
//		return JpaJavaMappingsFactory.eINSTANCE.createJavaSequenceGenerator(getType());
//	}
//
//	public ITableGenerator createTableGenerator() {
//		return JpaJavaMappingsFactory.eINSTANCE.createJavaTableGenerator(getType());
//	}

	// ********** misc **********
//	private static void attributeChanged(Object value, AnnotationAdapter annotationAdapter) {
//		Annotation annotation = annotationAdapter.getAnnotation();
//		if (value == null) {
//			if (annotation != null) {
//				annotationAdapter.removeAnnotation();
//			}
//		}
//		else {
//			if (annotation == null) {
//				annotationAdapter.newMarkerAnnotation();
//			}
//		}
//	}

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
//		for (IAttributeOverride override : this.getAttributeOverrides()) {
//			result = ((JavaAttributeOverride) override).candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
//		for (IAssociationOverride override : this.getAssociationOverrides()) {
//			result = ((JavaAssociationOverride) override).candidateValuesFor(pos, filter, astRoot);
//			if (result != null) {
//				return result;
//			}
//		}
		result = this.getDiscriminatorColumn().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		IJavaTableGenerator jtg = this.getTableGenerator();
		if (jtg != null) {
			result = jtg.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		IJavaSequenceGenerator jsg = this.getSequenceGenerator();
		if (jsg != null) {
			result = jsg.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
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
			return CollectionTools.size(JavaEntity.this.primaryKeyJoinColumns());
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return JavaEntity.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}
		
		public int indexOf(IAbstractJoinColumn joinColumn) {
			return CollectionTools.indexOf(JavaEntity.this.primaryKeyJoinColumns(), joinColumn);
		}
		
		public String defaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return JavaEntity.this.parentEntity().primaryKeyColumnName();
		}
	}
}
