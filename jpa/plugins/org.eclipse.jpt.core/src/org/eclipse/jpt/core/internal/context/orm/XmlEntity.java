/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.core.internal.context.java.IJavaEntity;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaSecondaryTable;
import org.eclipse.jpt.core.internal.resource.orm.AssociationOverride;
import org.eclipse.jpt.core.internal.resource.orm.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.Inheritance;
import org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery;
import org.eclipse.jpt.core.internal.resource.orm.NamedQuery;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.orm.SecondaryTable;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class XmlEntity extends XmlTypeMapping<Entity> implements IEntity
{
	protected String specifiedName;

	protected String defaultName;

//	protected XmlIdClass idClassForXml;
//
//	protected XmlInheritance inheritanceForXml;
//
	protected final XmlTable table;

	protected final List<XmlSecondaryTable> specifiedSecondaryTables;
	
	protected final List<XmlSecondaryTable> virtualSecondaryTables;
		public static final String VIRTUAL_SECONDARY_TABLES_LIST = "virtualSecondaryTablesList";
	
	protected final List<XmlPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
	
	protected final List<XmlPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	protected InheritanceType specifiedInheritanceStrategy;
	
	protected InheritanceType defaultInheritanceStrategy;

	protected String defaultDiscriminatorValue;

	protected String specifiedDiscriminatorValue;

	protected final XmlDiscriminatorColumn discriminatorColumn;

	protected XmlSequenceGenerator sequenceGenerator;

	protected XmlTableGenerator tableGenerator;

	protected final List<XmlAttributeOverride> specifiedAttributeOverrides;
	
	protected final List<XmlAttributeOverride> defaultAttributeOverrides;

	protected final List<XmlAssociationOverride> specifiedAssociationOverrides;

	protected final List<XmlAssociationOverride> defaultAssociationOverrides;

	protected final List<XmlNamedQuery> namedQueries;

	protected final List<XmlNamedNativeQuery> namedNativeQueries;

//	protected String idClass;
//

	public XmlEntity(XmlPersistentType parent) {
		super(parent);
		this.table = new XmlTable(this);
		this.specifiedSecondaryTables = new ArrayList<XmlSecondaryTable>();
		this.virtualSecondaryTables = new ArrayList<XmlSecondaryTable>();
		this.discriminatorColumn = createXmlDiscriminatorColumn();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<XmlPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumns = new ArrayList<XmlPrimaryKeyJoinColumn>();
		this.specifiedAttributeOverrides = new ArrayList<XmlAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<XmlAttributeOverride>();
		this.specifiedAssociationOverrides = new ArrayList<XmlAssociationOverride>();
		this.defaultAssociationOverrides = new ArrayList<XmlAssociationOverride>();
		this.namedQueries = new ArrayList<XmlNamedQuery>();
		this.namedNativeQueries = new ArrayList<XmlNamedNativeQuery>();
	}
	
	protected XmlDiscriminatorColumn createXmlDiscriminatorColumn() {
		return new XmlDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected INamedColumn.Owner buildDiscriminatorColumnOwner() {
		return new INamedColumn.Owner(){
			public Table dbTable(String tableName) {
				return XmlEntity.this.dbTable(tableName);
			}

			public ITextRange validationTextRange(CompilationUnit astRoot) {
				return XmlEntity.this.validationTextRange(astRoot);
			}

			public ITypeMapping typeMapping() {
				return XmlEntity.this;
			}
			
			public String defaultColumnName() {
				//TODO default column name from java here or in XmlDiscriminatorColumn?
				return IDiscriminatorColumn.DEFAULT_NAME;
			}
		};
	}

	// ******************* ITypeMapping implementation ********************

	public String getKey() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
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
	
	public IJavaEntity javaEntity() {
		IJavaPersistentType javaPersistentType = getJavaPersistentType();
		if (javaPersistentType != null && javaPersistentType.mappingKey() == IMappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return (IJavaEntity) javaPersistentType.getMapping();
		}
		return null;
	}

	public String getName() {
		return (this.getSpecifiedName() == null) ? getDefaultName() : this.getSpecifiedName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		this.typeMappingResource().setName(newSpecifiedName);
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}

	public XmlTable getTable() {
		return this.table;
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlSecondaryTable> secondaryTables() {
		if (specifiedSecondaryTablesSize() > 0) {
			return specifiedSecondaryTables();
		}
		return virtualSecondaryTables();
	}

	public int secondaryTablesSize() {
		return CollectionTools.size(secondaryTables());
	}
	
	public ListIterator<XmlSecondaryTable> virtualSecondaryTables() {
		return new CloneListIterator<XmlSecondaryTable>(this.virtualSecondaryTables);
	}

	public int virtualSecondaryTablesSize() {
		return this.virtualSecondaryTables.size();
	}
	
	protected void addVirtualSecondaryTable(XmlSecondaryTable secondaryTable) {
		addItemToList(secondaryTable, this.virtualSecondaryTables, XmlEntity.VIRTUAL_SECONDARY_TABLES_LIST);
	}
	
	protected void removeVirtualSecondaryTable(XmlSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.virtualSecondaryTables, XmlEntity.VIRTUAL_SECONDARY_TABLES_LIST);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlSecondaryTable> specifiedSecondaryTables() {
		return new CloneListIterator<XmlSecondaryTable>(this.specifiedSecondaryTables);
	}

	public int specifiedSecondaryTablesSize() {
		return this.specifiedSecondaryTables.size();
	}
	
	public XmlSecondaryTable addSpecifiedSecondaryTable(int index) {
		XmlSecondaryTable secondaryTable = new XmlSecondaryTable(this);
		this.specifiedSecondaryTables.add(index, secondaryTable);
		SecondaryTable secondaryTableResource = OrmFactory.eINSTANCE.createSecondaryTable();
		secondaryTable.initialize(secondaryTableResource);
		typeMappingResource().getSecondaryTables().add(index, secondaryTableResource);
		fireItemAdded(IEntity.SPECIFIED_SECONDARY_TABLES_LIST, index, secondaryTable);
		return secondaryTable;
	}
	
	protected void addSpecifiedSecondaryTable(int index, XmlSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.specifiedSecondaryTables, IEntity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void removeSpecifiedSecondaryTable(int index) {
		XmlSecondaryTable removedSecondaryTable = this.specifiedSecondaryTables.remove(index);
		typeMappingResource().getSecondaryTables().remove(index);
		fireItemRemoved(IEntity.SPECIFIED_SECONDARY_TABLES_LIST, index, removedSecondaryTable);
	}
	
	protected void removeSpecifiedSecondaryTable(XmlSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.specifiedSecondaryTables, IEntity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedSecondaryTables, targetIndex, sourceIndex);
		this.typeMappingResource().getSecondaryTables().move(targetIndex, sourceIndex);
		fireItemMoved(IEntity.SPECIFIED_SECONDARY_TABLES_LIST, targetIndex, sourceIndex);		
	}
	
	public boolean containsSecondaryTable(String name) {
		return containsSecondaryTable(name, secondaryTables());
	}
	
	public boolean containsSpecifiedSecondaryTable(String name) {
		return containsSecondaryTable(name, specifiedSecondaryTables());
	}
	
	public boolean containsVirtualSecondaryTable(String name) {
		return containsSecondaryTable(name, virtualSecondaryTables());
	}
	
	public boolean containsVirtualSecondaryTable(XmlSecondaryTable secondaryTable) {
		return this.virtualSecondaryTables.contains(secondaryTable);
	}

	protected boolean containsSecondaryTable(String name, ListIterator<XmlSecondaryTable> secondaryTables) {
		for (XmlSecondaryTable secondaryTable : CollectionTools.iterable(secondaryTables)) {
			String secondaryTableName = secondaryTable.getName();
			if (secondaryTableName != null && secondaryTableName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	protected Iterator<String> tableNames(Iterator<ITable> tables) {
		return new TransformationIterator<ITable, String>(tables) {
			@Override
			protected String transform(ITable t) {
				return t.getName();
			}
		};
	}

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

	public Iterator<ITable> associatedTables() {
		return new SingleElementIterator<ITable>(getTable());
		//TODO return new CompositeIterator(this.getTable(), this.getSecondaryTables().iterator());
	}

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

	public boolean tableNameIsInvalid(String tableName) {
		return !CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
	}
	
//	protected void xmlIdClassChanged() {
//		if (getIdClassForXml() == null) {
//			setIdClass(null);
//		}
//	}

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
		if (oldInheritanceType != newInheritanceType) {
			if (this.inheritanceResource() != null) {
				this.inheritanceResource().setStrategy(InheritanceType.toOrmResourceModel(newInheritanceType));						
				if (this.inheritanceResource().isAllFeaturesUnset()) {
					removeInheritanceResource();
				}
			}
			else if (newInheritanceType != null) {
				addInheritanceResource();
				inheritanceResource().setStrategy(InheritanceType.toOrmResourceModel(newInheritanceType));
			}
		}
		firePropertyChanged(SPECIFIED_INHERITANCE_STRATEGY_PROPERTY, oldInheritanceType, newInheritanceType);
	}
	
	protected void setSpecifiedInheritanceStrategy_(InheritanceType newInheritanceType) {
		InheritanceType oldInheritanceType = this.specifiedInheritanceStrategy;
		this.specifiedInheritanceStrategy = newInheritanceType;
		firePropertyChanged(SPECIFIED_INHERITANCE_STRATEGY_PROPERTY, oldInheritanceType, newInheritanceType);
	}

	protected Inheritance inheritanceResource() {
		return typeMappingResource().getInheritance();
	}
	
	protected void addInheritanceResource() {
		typeMappingResource().setInheritance(OrmFactory.eINSTANCE.createInheritance());		
	}
	
	protected void removeInheritanceResource() {
		typeMappingResource().setInheritance(null);
	}

	public XmlDiscriminatorColumn getDiscriminatorColumn() {
		return this.discriminatorColumn;
	}

	public XmlSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		this.sequenceGenerator = new XmlSequenceGenerator(this);
		typeMappingResource().setSequenceGenerator(OrmFactory.eINSTANCE.createSequenceGenerator());
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		XmlSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.typeMappingResource().setSequenceGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, null);
	}
	
	public XmlSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(XmlSequenceGenerator newSequenceGenerator) {
		XmlSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	public XmlTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		this.tableGenerator = new XmlTableGenerator(this);
		typeMappingResource().setTableGenerator(OrmFactory.eINSTANCE.createTableGenerator());
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		XmlTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.typeMappingResource().setTableGenerator(null);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);
	}
	
	public XmlTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}

	protected void setTableGenerator(XmlTableGenerator newTableGenerator) {
		XmlTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
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
		typeMappingResource().setDiscriminatorValue(newSpecifiedDiscriminatorValue);
		firePropertyChanged(SPECIFIED_DISCRIMINATOR_VALUE_PROPERTY, oldSpecifiedDiscriminatorValue, newSpecifiedDiscriminatorValue);
	}

	public String getDiscriminatorValue() {
		return (this.getSpecifiedDiscriminatorValue() == null) ? getDefaultDiscriminatorValue() : this.getSpecifiedDiscriminatorValue();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<XmlPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		return new CloneListIterator<XmlPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumns);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumns() : this.specifiedPrimaryKeyJoinColumns();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<XmlPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<XmlPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	public XmlPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumn = new XmlPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		this.typeMappingResource().getPrimaryKeyJoinColumns().add(index, OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		this.fireItemAdded(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		return primaryKeyJoinColumn;
	}
	
	protected IAbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, XmlPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		this.typeMappingResource().getPrimaryKeyJoinColumns().remove(index);
		fireItemRemoved(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedPrimaryKeyJoinColumns, targetIndex, sourceIndex);
		this.typeMappingResource().getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<XmlAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<XmlAttributeOverride>(specifiedAttributeOverrides(), defaultAttributeOverrides());
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<XmlAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<XmlAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<XmlAttributeOverride>(this.specifiedAttributeOverrides);
	}

	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public XmlAttributeOverride addSpecifiedAttributeOverride(int index) {
		XmlAttributeOverride attributeOverride = new XmlAttributeOverride(this, createAttributeOverrideOwner());
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		this.typeMappingResource().getAttributeOverrides().add(index, OrmFactory.eINSTANCE.createAttributeOverride());
		this.fireItemAdded(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}

	protected void addSpecifiedAttributeOverride(int index, XmlAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		XmlAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);
		this.typeMappingResource().getAttributeOverrides().remove(index);
		fireItemRemoved(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
	}

	protected void removeSpecifiedAttributeOverride(XmlAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.typeMappingResource().getAttributeOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlAssociationOverride> associationOverrides() {
		return new CompositeListIterator<XmlAssociationOverride>(specifiedAssociationOverrides(), defaultAssociationOverrides());
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlAssociationOverride> defaultAssociationOverrides() {
		return new CloneListIterator<XmlAssociationOverride>(this.defaultAssociationOverrides);
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<XmlAssociationOverride> specifiedAssociationOverrides() {
		return new CloneListIterator<XmlAssociationOverride>(this.specifiedAssociationOverrides);
	}

	public int specifiedAssociationOverridesSize() {
		return this.specifiedAssociationOverrides.size();
	}

	public XmlAssociationOverride addSpecifiedAssociationOverride(int index) {
		XmlAssociationOverride associationOverride = new XmlAssociationOverride(this, createAssociationOverrideOwner());
		this.specifiedAssociationOverrides.add(index, associationOverride);
		this.typeMappingResource().getAssociationOverrides().add(index, OrmFactory.eINSTANCE.createAssociationOverride());
		this.fireItemAdded(IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		return associationOverride;
	}

	protected void addSpecifiedAssociationOverride(int index, XmlAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.specifiedAssociationOverrides, IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAssociationOverride(int index) {
		XmlAssociationOverride removedAssociationOverride = this.specifiedAssociationOverrides.remove(index);
		this.typeMappingResource().getAssociationOverrides().remove(index);
		fireItemRemoved(IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, removedAssociationOverride);
	}

	protected void removeSpecifiedAssociationOverride(XmlAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.specifiedAssociationOverrides, IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAssociationOverrides, targetIndex, sourceIndex);
		this.typeMappingResource().getAssociationOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}


//	public String getIdClass() {
//		return idClass;
//	}
//
//	public void setIdClass(String newIdClass) {
//		String oldIdClass = idClass;
//		idClass = newIdClass;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS, oldIdClass, idClass));
//	}
//
//	protected void idClassChanged() {
//		if (getIdClass() == null) {
//			setIdClassForXml(null);
//		}
//		else {
//			if (getIdClassForXml() == null) {
//				setIdClassForXml(OrmFactory.eINSTANCE.createXmlIdClass());
//			}
//			getIdClassForXml().setValue(getIdClass());
//		}
//	}
//
//	public boolean discriminatorValueIsAllowed() {
//		Type type = persistentType().findType();
//		return (type == null) ? false : !type.isAbstract();
//	}

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
		IEntity rootEntity = null;
		for (Iterator<IPersistentType> i = persistentType().inheritanceHierarchy(); i.hasNext();) {
			IPersistentType persistentType = i.next();
			if (persistentType.getMapping() instanceof IEntity) {
				rootEntity = (IEntity) persistentType.getMapping();
			}
		}
		return rootEntity;
	}

//	public XmlIdClass getIdClassForXml() {
//		return idClassForXml;
//	}
//
//	public NotificationChain basicSetIdClassForXml(XmlIdClass newIdClassForXml, NotificationChain msgs) {
//		XmlIdClass oldIdClassForXml = idClassForXml;
//		idClassForXml = newIdClassForXml;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML, oldIdClassForXml, newIdClassForXml);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setIdClassForXml(XmlIdClass newIdClassForXml) {
//		if (newIdClassForXml != idClassForXml) {
//			NotificationChain msgs = null;
//			if (idClassForXml != null)
//				msgs = ((InternalEObject) idClassForXml).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML, null, msgs);
//			if (newIdClassForXml != null)
//				msgs = ((InternalEObject) newIdClassForXml).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML, null, msgs);
//			msgs = basicSetIdClassForXml(newIdClassForXml, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML, newIdClassForXml, newIdClassForXml));
//	}
//
//	public String primaryKeyColumnName() {
//		String pkColumnName = null;
//		for (Iterator<IPersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
//			IPersistentAttribute attribute = stream.next();
//			String name = attribute.primaryKeyColumnName();
//			if (pkColumnName == null) {
//				pkColumnName = name;
//			}
//			else if (name != null) {
//				// if we encounter a composite primary key, return null
//				return null;
//			}
//		}
//		// if we encounter only a single primary key column name, return it
//		return pkColumnName;
//	}
//
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
//


	@Override
	public int xmlSequence() {
		return 1;
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

//
//	public IAttributeOverride createAttributeOverride(int index) {
//		return OrmFactory.eINSTANCE.createXmlAttributeOverride(new IEntity.AttributeOverrideOwner(this));
//	}
//
//	public IAssociationOverride createAssociationOverride(int index) {
//		return OrmFactory.eINSTANCE.createXmlAssociationOverride(new IEntity.AssociationOverrideOwner(this));
//	}
//
//	public IAttributeOverride attributeOverrideNamed(String name) {
//		return (IAttributeOverride) overrideNamed(name, getAttributeOverrides());
//	}
//
//	public boolean containsAttributeOverride(String name) {
//		return containsOverride(name, getAttributeOverrides());
//	}
//
//	public boolean containsSpecifiedAttributeOverride(String name) {
//		return containsOverride(name, getSpecifiedAttributeOverrides());
//	}
//
//	public boolean containsAssociationOverride(String name) {
//		return containsOverride(name, getAssociationOverrides());
//	}
//
//	public boolean containsSpecifiedAssociationOverride(String name) {
//		return containsOverride(name, getSpecifiedAssociationOverrides());
//	}
//
//	private IOverride overrideNamed(String name, List<? extends IOverride> overrides) {
//		for (IOverride override : overrides) {
//			String overrideName = override.getName();
//			if (overrideName == null && name == null) {
//				return override;
//			}
//			if (overrideName != null && overrideName.equals(name)) {
//				return override;
//			}
//		}
//		return null;
//	}
//
//	private boolean containsOverride(String name, List<? extends IOverride> overrides) {
//		return overrideNamed(name, overrides) != null;
//	}
//
//	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
//		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
//	}
	
	
	@Override
	public void initialize(Entity entity) {
		super.initialize(entity);
		this.specifiedName = entity.getName();
		this.defaultName = this.defaultName();
		this.initializeInheritance(this.inheritanceResource());
		this.discriminatorColumn.initialize(entity);
		this.specifiedDiscriminatorValue = entity.getDiscriminatorValue();
		this.defaultDiscriminatorValue = this.defaultDiscriminatorValue();
		this.table.initialize(entity);
		this.initializeSpecifiedSecondaryTables(entity);
		this.initializeVirtualSecondaryTables();
		this.initializeSequenceGenerator(entity);
		this.initializeTableGenerator(entity);
		this.initializeSpecifiedPrimaryKeyJoinColumns(entity);
		this.initializeSpecifiedAttributeOverrides(entity);
		this.initializeSpecifiedAssociationOverrides(entity);
		this.initializeNamedQueries(entity);
		this.initializeNamedNativeQueries(entity);
	}
	
	protected void initializeInheritance(Inheritance inheritanceResource) {
		this.specifiedInheritanceStrategy = this.specifiedInheritanceStrategy(inheritanceResource);
		this.defaultInheritanceStrategy = this.defaultInheritanceStrategy();
	}
	
	protected void initializeSpecifiedSecondaryTables(Entity entity) {
		for (SecondaryTable secondaryTable : entity.getSecondaryTables()) {
			this.specifiedSecondaryTables.add(createSecondaryTable(secondaryTable));
		}
	}
	
	protected void initializeVirtualSecondaryTables() {
		if (isMetadataComplete()) {
			return;
		}
		if (javaEntity() == null) {
			return;
		}
		if (specifiedSecondaryTablesSize() > 0) {
			return;
		}
		ListIterator<IJavaSecondaryTable> javaSecondaryTables = javaEntity().secondaryTables();
		while(javaSecondaryTables.hasNext()) {
			IJavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
			if (javaSecondaryTable.getName() != null) {
				//TODO calling setters during initialize in createVirtualSecondaryTable
				//I think this is going to be a problem
				this.virtualSecondaryTables.add(createVirtualSecondaryTable(javaSecondaryTable));
			}
		}
	}	
	
	protected void initializeTableGenerator(Entity entity) {
		if (entity.getTableGenerator() != null) {
			this.tableGenerator = new XmlTableGenerator(this);
			this.tableGenerator.initialize(entity.getTableGenerator());
		}
	}
	
	protected void initializeSequenceGenerator(Entity entity) {
		if (entity.getSequenceGenerator() != null) {
			this.sequenceGenerator = new XmlSequenceGenerator(this);
			this.sequenceGenerator.initialize(entity.getSequenceGenerator());
		}
	}
	
	protected void initializeSpecifiedPrimaryKeyJoinColumns(Entity entity) {
		for (PrimaryKeyJoinColumn primaryKeyJoinColumn : entity.getPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(createPrimaryKeyJoinColumn(primaryKeyJoinColumn));
		}
	}
	
	protected void initializeSpecifiedAttributeOverrides(Entity entity) {
		for (AttributeOverride attributeOverride : entity.getAttributeOverrides()) {
			this.specifiedAttributeOverrides.add(createAttributeOverride(attributeOverride));
		}
	}
	
	protected void initializeSpecifiedAssociationOverrides(Entity entity) {
		for (AssociationOverride associationOverride : entity.getAssociationOverrides()) {
			this.specifiedAssociationOverrides.add(createAssociationOverride(associationOverride));
		}
	}
	
	protected void initializeNamedQueries(Entity entity) {
		for (NamedQuery namedQuery : entity.getNamedQueries()) {
			this.namedQueries.add(createNamedQuery(namedQuery));
		}
	}
	
	protected void initializeNamedNativeQueries(Entity entity) {
		for (NamedNativeQuery namedNativeQuery : entity.getNamedNativeQueries()) {
			this.namedNativeQueries.add(createNamedNativeQuery(namedNativeQuery));
		}
	}

	@Override
	public void update(Entity entity) {
		super.update(entity);
		this.setSpecifiedName(entity.getName());
		this.setDefaultName(this.defaultName());
		this.updateInheritance(this.inheritanceResource());
		this.discriminatorColumn.update(entity);
		this.setSpecifiedDiscriminatorValue(entity.getDiscriminatorValue());
		this.setDefaultDiscriminatorValue(defaultDiscriminatorValue());
		this.table.update(entity);
		this.updateSpecifiedSecondaryTables(entity);
		this.updateVirtualSecondaryTables();
		this.updateSequenceGenerator(entity);
		this.updateTableGenerator(entity);
		this.updateSpecifiedPrimaryKeyJoinColumns(entity);
		this.updateSpecifiedAttributeOverrides(entity);
		this.updateSpecifiedAssociationOverrides(entity);
		this.updateNamedQueries(entity);
		this.updateNamedNativeQueries(entity);
	}

	protected String defaultName() {
		//TODO add a test where the underyling java has a name set @Entity(name="foo")
		//just by having the entity specified in xml we are overriding that name setting
		String className = getClass_();
		if (className != null) {
			return ClassTools.shortNameForClassNamed(className);
		}
		return null;
	}
	
	protected String defaultDiscriminatorValue() {
		//TODO default discriminator value
		return null;
	}
	
	protected void updateInheritance(Inheritance inheritanceResource) {
		this.setSpecifiedInheritanceStrategy_(this.specifiedInheritanceStrategy(inheritanceResource));
		this.setDefaultInheritanceStrategy(this.defaultInheritanceStrategy());
	}
	
	protected void updateSpecifiedSecondaryTables(Entity entity) {
		ListIterator<XmlSecondaryTable> secondaryTables = specifiedSecondaryTables();
		ListIterator<SecondaryTable> resourceSecondaryTables = entity.getSecondaryTables().listIterator();
		
		while (secondaryTables.hasNext()) {
			XmlSecondaryTable secondaryTable = secondaryTables.next();
			if (resourceSecondaryTables.hasNext()) {
				secondaryTable.update(resourceSecondaryTables.next());
			}
			else {
				removeSpecifiedSecondaryTable(secondaryTable);
			}
		}
		
		while (resourceSecondaryTables.hasNext()) {
			addSpecifiedSecondaryTable(specifiedSecondaryTablesSize(), createSecondaryTable(resourceSecondaryTables.next()));
		}
	}
	
	//if any secondary-tables are specified in the xml file, then all of the java secondaryTables are overriden
	protected void updateVirtualSecondaryTables() {
		ListIterator<XmlSecondaryTable> secondaryTables = virtualSecondaryTables();
		ListIterator<IJavaSecondaryTable> javaSecondaryTables = EmptyListIterator.instance();
		
		if (javaEntity() != null && !isMetadataComplete() && specifiedSecondaryTablesSize() == 0) {
			javaSecondaryTables = javaEntity().secondaryTables();
		}
		while (secondaryTables.hasNext()) {
			XmlSecondaryTable virtualSecondaryTable = secondaryTables.next();
			if (javaSecondaryTables.hasNext()) {
				IJavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
				virtualSecondaryTable.setDefaultName(javaSecondaryTable.getName());
				virtualSecondaryTable.setDefaultCatalog(javaSecondaryTable.getCatalog());
				virtualSecondaryTable.setDefaultSchema(javaSecondaryTable.getSchema());
				//TODO what about pkJoinColumns?
			}
			else {
				removeVirtualSecondaryTable(virtualSecondaryTable);
			}
		}
		
		while (javaSecondaryTables.hasNext()) {
			IJavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
			addVirtualSecondaryTable(createVirtualSecondaryTable(javaSecondaryTable));
		}
	}

	protected XmlSecondaryTable createSecondaryTable(SecondaryTable secondaryTable) {
		XmlSecondaryTable xmlSecondaryTable = new XmlSecondaryTable(this);
		xmlSecondaryTable.initialize(secondaryTable);
		return xmlSecondaryTable;
	}
	
	protected XmlSecondaryTable createVirtualSecondaryTable(IJavaSecondaryTable javaSecondaryTable) {
		XmlSecondaryTable virutalSecondaryTable = new XmlSecondaryTable(this);
		virutalSecondaryTable.setDefaultName(javaSecondaryTable.getName());
		virutalSecondaryTable.setDefaultCatalog(javaSecondaryTable.getCatalog());
		virutalSecondaryTable.setDefaultSchema(javaSecondaryTable.getSchema());		
		//TODO what about primaryKeyJoinColumns, would you want to see those in the orm.xml ui??
		return virutalSecondaryTable;
	}
	
	protected void updateTableGenerator(Entity entity) {
		if (entity.getTableGenerator() == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(new XmlTableGenerator(this));
				getTableGenerator().initialize(entity.getTableGenerator());
			}
			else {
				getTableGenerator().update(entity.getTableGenerator());
			}
		}
	}
	
	protected void updateSequenceGenerator(Entity entity) {
		if (entity.getSequenceGenerator() == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(new XmlSequenceGenerator(this));
				getSequenceGenerator().initialize(entity.getSequenceGenerator());
			}
			else {
				getSequenceGenerator().update(entity.getSequenceGenerator());
			}
		}
	}

	protected InheritanceType specifiedInheritanceStrategy(Inheritance inheritanceResource) {
		if (inheritanceResource == null) {
			return null;
		}
		return InheritanceType.fromOrmResourceModel(inheritanceResource.getStrategy());
	}
	
	protected InheritanceType defaultInheritanceStrategy() {
		if (inheritanceResource() == null && !isMetadataComplete()) {
			if (javaEntity() != null) {
				return javaEntity().getInheritanceStrategy();
			}
		}
		if (rootEntity() == this) {
			return InheritanceType.SINGLE_TABLE;
		}
		return rootEntity().getInheritanceStrategy();
	}
	
	protected void updateSpecifiedPrimaryKeyJoinColumns(Entity entity) {
		ListIterator<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<PrimaryKeyJoinColumn> resourcePrimaryKeyJoinColumns = entity.getPrimaryKeyJoinColumns().listIterator();
		
		while (primaryKeyJoinColumns.hasNext()) {
			XmlPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
			if (resourcePrimaryKeyJoinColumns.hasNext()) {
				primaryKeyJoinColumn.update(resourcePrimaryKeyJoinColumns.next());
			}
			else {
				removeSpecifiedPrimaryKeyJoinColumn(primaryKeyJoinColumn);
			}
		}
		
		while (resourcePrimaryKeyJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(specifiedPrimaryKeyJoinColumnsSize(), createPrimaryKeyJoinColumn(resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected XmlPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = new XmlPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		xmlPrimaryKeyJoinColumn.initialize(primaryKeyJoinColumn);
		return xmlPrimaryKeyJoinColumn;
	}

	protected void updateSpecifiedAttributeOverrides(Entity entity) {
		ListIterator<XmlAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<AttributeOverride> resourceAttributeOverrides = entity.getAttributeOverrides().listIterator();
		
		while (attributeOverrides.hasNext()) {
			XmlAttributeOverride attributeOverride = attributeOverrides.next();
			if (resourceAttributeOverrides.hasNext()) {
				attributeOverride.update(resourceAttributeOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), createAttributeOverride(resourceAttributeOverrides.next()));
		}
	}
	
	protected XmlAttributeOverride createAttributeOverride(AttributeOverride attributeOverride) {
		XmlAttributeOverride xmlAttributeOverride = new XmlAttributeOverride(this, createAttributeOverrideOwner());
		xmlAttributeOverride.initialize(attributeOverride);
		return xmlAttributeOverride;
	}

	protected IOverride.Owner createAttributeOverrideOwner() {
		return new AttributeOverrideOwner();
	}

	protected void updateSpecifiedAssociationOverrides(Entity entity) {
		ListIterator<XmlAssociationOverride> associationOverrides = specifiedAssociationOverrides();
		ListIterator<AssociationOverride> resourceAssociationOverrides = entity.getAssociationOverrides().listIterator();
		
		while (associationOverrides.hasNext()) {
			XmlAssociationOverride associationOverride = associationOverrides.next();
			if (resourceAssociationOverrides.hasNext()) {
				associationOverride.update(resourceAssociationOverrides.next());
			}
			else {
				removeSpecifiedAssociationOverride(associationOverride);
			}
		}
		
		while (resourceAssociationOverrides.hasNext()) {
			addSpecifiedAssociationOverride(specifiedAssociationOverridesSize(), createAssociationOverride(resourceAssociationOverrides.next()));
		}
	}
	
	protected XmlAssociationOverride createAssociationOverride(AssociationOverride associationOverride) {
		XmlAssociationOverride xmlAssociationOverride = new XmlAssociationOverride(this, createAssociationOverrideOwner());
		xmlAssociationOverride.initialize(associationOverride);
		return xmlAssociationOverride;
	}

	protected IOverride.Owner createAssociationOverrideOwner() {
		return new AssociationOverrideOwner();
	}
	
	protected void updateNamedQueries(Entity entity) {
		ListIterator<XmlNamedQuery> namedQueries = namedQueries();
		ListIterator<NamedQuery> resourceNamedQueries = entity.getNamedQueries().listIterator();
		
		while (namedQueries.hasNext()) {
			XmlNamedQuery namedQuery = namedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update(resourceNamedQueries.next());
			}
			else {
				removeNamedQuery(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(namedQueriesSize(), createNamedQuery(resourceNamedQueries.next()));
		}
	}

	protected XmlNamedQuery createNamedQuery(NamedQuery namedQuery) {
		XmlNamedQuery xmlNamedQuery = new XmlNamedQuery(this);
		xmlNamedQuery.initialize(namedQuery);
		return xmlNamedQuery;
	}

	protected void updateNamedNativeQueries(Entity entity) {
		ListIterator<XmlNamedNativeQuery> namedNativeQueries = namedNativeQueries();
		ListIterator<NamedNativeQuery> resourceNamedNativeQueries = entity.getNamedNativeQueries().listIterator();
		
		while (namedNativeQueries.hasNext()) {
			XmlNamedNativeQuery namedQuery = namedNativeQueries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update(resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(namedQueriesSize(), createNamedNativeQuery(resourceNamedNativeQueries.next()));
		}
	}

	protected XmlNamedNativeQuery createNamedNativeQuery(NamedNativeQuery namedQuery) {
		XmlNamedNativeQuery xmlNamedNativeQuery = new XmlNamedNativeQuery(this);
		xmlNamedNativeQuery.initialize(namedQuery);
		return xmlNamedNativeQuery;
	}

	public String primaryKeyColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlNamedQuery> namedQueries() {
		return new CloneListIterator<XmlNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public XmlNamedQuery addNamedQuery(int index) {
		XmlNamedQuery namedQuery = new XmlNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		this.typeMappingResource().getNamedQueries().add(index, OrmFactory.eINSTANCE.createNamedQuery());
		this.fireItemAdded(IEntity.NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, XmlNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, IEntity.NAMED_QUERIES_LIST);
	}
	
	public void removeNamedQuery(int index) {
		XmlNamedQuery namedQuery = this.namedQueries.remove(index);
		this.typeMappingResource().getNamedQueries().remove(index);
		fireItemRemoved(IEntity.NAMED_QUERIES_LIST, index, namedQuery);
	}

	protected void removeNamedQuery(XmlNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, IEntity.NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
		this.typeMappingResource().getNamedQueries().move(targetIndex, sourceIndex);
		fireItemMoved(IEntity.NAMED_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<XmlNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<XmlNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public XmlNamedNativeQuery addNamedNativeQuery(int index) {
		XmlNamedNativeQuery namedNativeQuery = new XmlNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.typeMappingResource().getNamedNativeQueries().add(index, OrmFactory.eINSTANCE.createNamedNativeQuery());
		this.fireItemAdded(IEntity.NAMED_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, XmlNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, IEntity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void removeNamedNativeQuery(int index) {
		XmlNamedNativeQuery namedNativeQuery = this.namedNativeQueries.remove(index);
		this.typeMappingResource().getNamedNativeQueries().remove(index);
		fireItemRemoved(IEntity.NAMED_QUERIES_LIST, index, namedNativeQuery);
	}

	protected void removeNamedNativeQuery(XmlNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, IEntity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex);
		this.typeMappingResource().getNamedNativeQueries().move(targetIndex, sourceIndex);
		fireItemMoved(IEntity.NAMED_NATIVE_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	public String getIdClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setIdClass(String value) {
		// TODO Auto-generated method stub
		
	}
	
	public IColumnMapping columnMapping(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isVirtual(IOverride override) {
		// TODO Auto-generated method stub
		return false;
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ITypeMapping typeMapping() {
		return this;
	}
	
	@Override
	public void removeFromResourceModel(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		entityMappings.getEntities().remove(this.typeMappingResource());
	}
	
	@Override
	public Entity addToResourceModel(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		Entity entity = OrmFactory.eINSTANCE.createEntity();
		persistentType().initialize(entity);
		entityMappings.getEntities().add(entity);
		return entity;
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
	
	class PrimaryKeyJoinColumnOwner implements IAbstractJoinColumn.Owner
	{
		public ITextRange validationTextRange(CompilationUnit astRoot) {
			return XmlEntity.this.validationTextRange(astRoot);
		}

		public ITypeMapping typeMapping() {
			return XmlEntity.this;
		}

		public Table dbTable(String tableName) {
			return XmlEntity.this.dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity parentEntity = XmlEntity.this.parentEntity();
			return (parentEntity == null) ? null : parentEntity.primaryDbTable();
		}

		public int joinColumnsSize() {
			return CollectionTools.size(XmlEntity.this.primaryKeyJoinColumns());
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return XmlEntity.this.defaultPrimaryKeyJoinColumns.contains(joinColumn);
		}
		
		public String defaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return XmlEntity.this.parentEntity().primaryKeyColumnName();
		}
	}
	
	class AttributeOverrideOwner implements IOverride.Owner {

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
			return XmlEntity.this.defaultAttributeOverrides.contains(override);
		}

		public ITypeMapping typeMapping() {
			return XmlEntity.this;
		}

		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	class AssociationOverrideOwner implements IOverride.Owner {

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
			return XmlEntity.this.defaultAssociationOverrides.contains(override);
		}

		public ITypeMapping typeMapping() {
			return XmlEntity.this;
		}

		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
