/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEntity;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.IdClass;
import org.eclipse.jpt.core.resource.orm.Inheritance;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericOrmEntity extends AbstractOrmTypeMapping<XmlEntity> implements Entity
{
	protected String specifiedName;

	protected String defaultName;

	protected String idClass;

	protected final GenericOrmTable table;

	protected final List<GenericOrmSecondaryTable> specifiedSecondaryTables;
	
	protected final List<GenericOrmSecondaryTable> virtualSecondaryTables;
	//TODO this might need to move to IEntity, for the UI
		public static final String VIRTUAL_SECONDARY_TABLES_LIST = "virtualSecondaryTablesList";
	
	protected final List<GenericOrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
	
	protected final List<GenericOrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	protected InheritanceType specifiedInheritanceStrategy;
	
	protected InheritanceType defaultInheritanceStrategy;

	protected String defaultDiscriminatorValue;

	protected String specifiedDiscriminatorValue;

	protected boolean discriminatorValueAllowed;

	protected final GenericOrmDiscriminatorColumn discriminatorColumn;

	protected GenericOrmSequenceGenerator sequenceGenerator;

	protected GenericOrmTableGenerator tableGenerator;

	protected final List<GenericOrmAttributeOverride> specifiedAttributeOverrides;
	
	protected final List<GenericOrmAttributeOverride> defaultAttributeOverrides;

	protected final List<GenericOrmAssociationOverride> specifiedAssociationOverrides;

	protected final List<GenericOrmAssociationOverride> defaultAssociationOverrides;

	protected final List<GenericOrmNamedQuery> namedQueries;

	protected final List<GenericOrmNamedNativeQuery> namedNativeQueries;

	public GenericOrmEntity(OrmPersistentType parent) {
		super(parent);
		this.table = new GenericOrmTable(this);
		this.specifiedSecondaryTables = new ArrayList<GenericOrmSecondaryTable>();
		this.virtualSecondaryTables = new ArrayList<GenericOrmSecondaryTable>();
		this.discriminatorColumn = createXmlDiscriminatorColumn();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<GenericOrmPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumns = new ArrayList<GenericOrmPrimaryKeyJoinColumn>();
		this.specifiedAttributeOverrides = new ArrayList<GenericOrmAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<GenericOrmAttributeOverride>();
		this.specifiedAssociationOverrides = new ArrayList<GenericOrmAssociationOverride>();
		this.defaultAssociationOverrides = new ArrayList<GenericOrmAssociationOverride>();
		this.namedQueries = new ArrayList<GenericOrmNamedQuery>();
		this.namedNativeQueries = new ArrayList<GenericOrmNamedNativeQuery>();
	}
	
	protected GenericOrmDiscriminatorColumn createXmlDiscriminatorColumn() {
		return new GenericOrmDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected NamedColumn.Owner buildDiscriminatorColumnOwner() {
		return new NamedColumn.Owner(){
			public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
				return GenericOrmEntity.this.dbTable(tableName);
			}

			public TextRange validationTextRange(CompilationUnit astRoot) {
				return GenericOrmEntity.this.validationTextRange(astRoot);
			}

			public TypeMapping typeMapping() {
				return GenericOrmEntity.this;
			}
			
			public String defaultColumnName() {
				//TODO default column name from java here or in XmlDiscriminatorColumn?
				return DiscriminatorColumn.DEFAULT_NAME;
			}
		};
	}

	// ******************* ITypeMapping implementation ********************

	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
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
	
	public JavaEntity javaEntity() {
		JavaPersistentType javaPersistentType = getJavaPersistentType();
		if (javaPersistentType != null && javaPersistentType.mappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return (JavaEntity) javaPersistentType.getMapping();
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

	public GenericOrmTable getTable() {
		return this.table;
	}

	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmSecondaryTable> secondaryTables() {
		if (specifiedSecondaryTablesSize() > 0) {
			return specifiedSecondaryTables();
		}
		return virtualSecondaryTables();
	}

	public int secondaryTablesSize() {
		if (specifiedSecondaryTablesSize() > 0) {
			return specifiedSecondaryTablesSize();
		}
		return virtualSecondaryTablesSize();
	}
	
	public ListIterator<GenericOrmSecondaryTable> virtualSecondaryTables() {
		return new CloneListIterator<GenericOrmSecondaryTable>(this.virtualSecondaryTables);
	}

	public int virtualSecondaryTablesSize() {
		return this.virtualSecondaryTables.size();
	}
	
	protected void addVirtualSecondaryTable(GenericOrmSecondaryTable secondaryTable) {
		addItemToList(secondaryTable, this.virtualSecondaryTables, GenericOrmEntity.VIRTUAL_SECONDARY_TABLES_LIST);
	}
	
	protected void removeVirtualSecondaryTable(GenericOrmSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.virtualSecondaryTables, GenericOrmEntity.VIRTUAL_SECONDARY_TABLES_LIST);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmSecondaryTable> specifiedSecondaryTables() {
		return new CloneListIterator<GenericOrmSecondaryTable>(this.specifiedSecondaryTables);
	}

	public int specifiedSecondaryTablesSize() {
		return this.specifiedSecondaryTables.size();
	}
	
	public GenericOrmSecondaryTable addSpecifiedSecondaryTable(int index) {
		GenericOrmSecondaryTable secondaryTable = new GenericOrmSecondaryTable(this);
		this.specifiedSecondaryTables.add(index, secondaryTable);
		XmlSecondaryTable secondaryTableResource = OrmFactory.eINSTANCE.createSecondaryTable();
		secondaryTable.initialize(secondaryTableResource);
		typeMappingResource().getSecondaryTables().add(index, secondaryTableResource);
		fireItemAdded(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, secondaryTable);
		return secondaryTable;
	}
	
	protected void addSpecifiedSecondaryTable(int index, GenericOrmSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.specifiedSecondaryTables, Entity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}
	
	public void removeSpecifiedSecondaryTable(int index) {
		GenericOrmSecondaryTable removedSecondaryTable = this.specifiedSecondaryTables.remove(index);
		typeMappingResource().getSecondaryTables().remove(index);
		fireItemRemoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, removedSecondaryTable);
	}
	
	protected void removeSpecifiedSecondaryTable_(GenericOrmSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.specifiedSecondaryTables, Entity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedSecondaryTables, targetIndex, sourceIndex);
		this.typeMappingResource().getSecondaryTables().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, targetIndex, sourceIndex);		
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
	
	public boolean containsVirtualSecondaryTable(GenericOrmSecondaryTable secondaryTable) {
		return this.virtualSecondaryTables.contains(secondaryTable);
	}

	protected boolean containsSecondaryTable(String name, ListIterator<GenericOrmSecondaryTable> secondaryTables) {
		for (GenericOrmSecondaryTable secondaryTable : CollectionTools.iterable(secondaryTables)) {
			String secondaryTableName = secondaryTable.getName();
			if (secondaryTableName != null && secondaryTableName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	protected Iterator<String> tableNames(Iterator<Table> tables) {
		return new TransformationIterator<Table, String>(tables) {
			@Override
			protected String transform(Table t) {
				return t.getName();
			}
		};
	}

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

	public Iterator<Table> associatedTables() {
		return new SingleElementIterator<Table>(getTable());
		//TODO return new CompositeIterator(this.getTable(), this.getSecondaryTables().iterator());
	}

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

	public boolean tableNameIsInvalid(String tableName) {
		return !CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
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

	public GenericOrmDiscriminatorColumn getDiscriminatorColumn() {
		return this.discriminatorColumn;
	}

	public GenericOrmSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		this.sequenceGenerator = new GenericOrmSequenceGenerator(this);
		typeMappingResource().setSequenceGenerator(OrmFactory.eINSTANCE.createSequenceGeneratorImpl());
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		GenericOrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.typeMappingResource().setSequenceGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, null);
	}
	
	public GenericOrmSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(GenericOrmSequenceGenerator newSequenceGenerator) {
		GenericOrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	public GenericOrmTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		this.tableGenerator = new GenericOrmTableGenerator(this);
		typeMappingResource().setTableGenerator(OrmFactory.eINSTANCE.createTableGeneratorImpl());
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		GenericOrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.typeMappingResource().setTableGenerator(null);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);
	}
	
	public GenericOrmTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}

	protected void setTableGenerator(GenericOrmTableGenerator newTableGenerator) {
		GenericOrmTableGenerator oldTableGenerator = this.tableGenerator;
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
	
	public boolean isDiscriminatorValueAllowed() {
		return this.discriminatorValueAllowed;
	}
	
	protected void setDiscriminatorValueAllowed(boolean newDiscriminatorValueAllowed) {
		boolean oldDiscriminatorValueAllowed = this.discriminatorValueAllowed;
		this.discriminatorValueAllowed = newDiscriminatorValueAllowed;
		firePropertyChanged(Entity.DISCRIMINATOR_VALUE_ALLOWED_PROPERTY, oldDiscriminatorValueAllowed, newDiscriminatorValueAllowed);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		return new CloneListIterator<GenericOrmPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumns);
	}
	
	public int defaultPrimaryKeyJoinColumnsSize() {
		return this.defaultPrimaryKeyJoinColumns.size();
	}

	public PrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumns() : this.specifiedPrimaryKeyJoinColumns();
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumnsSize() : this.specifiedPrimaryKeyJoinColumnsSize();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<GenericOrmPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	public GenericOrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		GenericOrmPrimaryKeyJoinColumn primaryKeyJoinColumn = new GenericOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		this.typeMappingResource().getPrimaryKeyJoinColumns().add(index, OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		this.fireItemAdded(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		return primaryKeyJoinColumn;
	}
	
	protected AbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, GenericOrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(primaryKeyJoinColumn));
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		GenericOrmPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		this.typeMappingResource().getPrimaryKeyJoinColumns().remove(index);
		fireItemRemoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(GenericOrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedPrimaryKeyJoinColumns, targetIndex, sourceIndex);
		this.typeMappingResource().getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<GenericOrmAttributeOverride>(specifiedAttributeOverrides(), defaultAttributeOverrides());
	}

	public int attributeOverridesSize() {
		return this.specifiedAttributeOverridesSize() + this.defaultAttributeOverridesSize();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<GenericOrmAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	public int defaultAttributeOverridesSize() {
		return this.defaultAttributeOverrides.size();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<GenericOrmAttributeOverride>(this.specifiedAttributeOverrides);
	}

	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public GenericOrmAttributeOverride addSpecifiedAttributeOverride(int index) {
		GenericOrmAttributeOverride attributeOverride = new GenericOrmAttributeOverride(this, createAttributeOverrideOwner());
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		this.typeMappingResource().getAttributeOverrides().add(index, OrmFactory.eINSTANCE.createAttributeOverrideImpl());
		this.fireItemAdded(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}

	protected void addSpecifiedAttributeOverride(int index, GenericOrmAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(AttributeOverride attributeOverride) {
		removeSpecifiedAttributeOverride(this.specifiedAttributeOverrides.indexOf(attributeOverride));
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		GenericOrmAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);
		this.typeMappingResource().getAttributeOverrides().remove(index);
		fireItemRemoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride_(GenericOrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.typeMappingResource().getAttributeOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmAssociationOverride> associationOverrides() {
		return new CompositeListIterator<GenericOrmAssociationOverride>(specifiedAssociationOverrides(), defaultAssociationOverrides());
	}

	public int associationOverridesSize() {
		return this.specifiedAssociationOverridesSize() + this.defaultAssociationOverridesSize();
	}

	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmAssociationOverride> defaultAssociationOverrides() {
		return new CloneListIterator<GenericOrmAssociationOverride>(this.defaultAssociationOverrides);
	}
	
	public int defaultAssociationOverridesSize() {
		return this.defaultAssociationOverrides.size();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmAssociationOverride> specifiedAssociationOverrides() {
		return new CloneListIterator<GenericOrmAssociationOverride>(this.specifiedAssociationOverrides);
	}

	public int specifiedAssociationOverridesSize() {
		return this.specifiedAssociationOverrides.size();
	}

	public GenericOrmAssociationOverride addSpecifiedAssociationOverride(int index) {
		GenericOrmAssociationOverride associationOverride = new GenericOrmAssociationOverride(this, createAssociationOverrideOwner());
		this.specifiedAssociationOverrides.add(index, associationOverride);
		this.typeMappingResource().getAssociationOverrides().add(index, OrmFactory.eINSTANCE.createAssociationOverride());
		this.fireItemAdded(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		return associationOverride;
	}

	protected void addSpecifiedAssociationOverride(int index, GenericOrmAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAssociationOverride(AssociationOverride associationOverride) {
		removeSpecifiedAssociationOverride(this.specifiedAssociationOverrides.indexOf(associationOverride));
	}
	
	public void removeSpecifiedAssociationOverride(int index) {
		GenericOrmAssociationOverride removedAssociationOverride = this.specifiedAssociationOverrides.remove(index);
		this.typeMappingResource().getAssociationOverrides().remove(index);
		fireItemRemoved(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, removedAssociationOverride);
	}
	
	protected void removeSpecifiedAssociationOverride_(GenericOrmAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAssociationOverrides, targetIndex, sourceIndex);
		this.typeMappingResource().getAssociationOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmNamedQuery> namedQueries() {
		return new CloneListIterator<GenericOrmNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public GenericOrmNamedQuery addNamedQuery(int index) {
		GenericOrmNamedQuery namedQuery = new GenericOrmNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		this.typeMappingResource().getNamedQueries().add(index, OrmFactory.eINSTANCE.createNamedQuery());
		this.fireItemAdded(Entity.NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, GenericOrmNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, Entity.NAMED_QUERIES_LIST);
	}
		
	public void removeNamedQuery(NamedQuery namedQuery) {
		removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}
	
	public void removeNamedQuery(int index) {
		GenericOrmNamedQuery namedQuery = this.namedQueries.remove(index);
		this.typeMappingResource().getNamedQueries().remove(index);
		fireItemRemoved(Entity.NAMED_QUERIES_LIST, index, namedQuery);
	}
	
	protected void removeNamedQuery_(GenericOrmNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, Entity.NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
		this.typeMappingResource().getNamedQueries().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.NAMED_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<GenericOrmNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public GenericOrmNamedNativeQuery addNamedNativeQuery(int index) {
		GenericOrmNamedNativeQuery namedNativeQuery = new GenericOrmNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.typeMappingResource().getNamedNativeQueries().add(index, OrmFactory.eINSTANCE.createNamedNativeQuery());
		this.fireItemAdded(Entity.NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, GenericOrmNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, Entity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}
	
	public void removeNamedNativeQuery(int index) {
		GenericOrmNamedNativeQuery namedNativeQuery = this.namedNativeQueries.remove(index);
		this.typeMappingResource().getNamedNativeQueries().remove(index);
		fireItemRemoved(Entity.NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
	}

	protected void removeNamedNativeQuery_(GenericOrmNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, Entity.NAMED_NATIVE_QUERIES_LIST);
	}
		
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex);
		this.typeMappingResource().getNamedNativeQueries().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.NAMED_NATIVE_QUERIES_LIST, targetIndex, sourceIndex);		
	}

	public String getIdClass() {
		return this.idClass;
	}
	
	public void setIdClass(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		if (oldIdClass != newIdClass) {
			if (this.idClassResource() != null) {
				this.idClassResource().setClassName(newIdClass);						
				if (this.idClassResource().isAllFeaturesUnset()) {
					removeIdClassResource();
				}
			}
			else if (newIdClass != null) {
				addIdClassResource();
				idClassResource().setClassName(newIdClass);
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
		return typeMappingResource().getIdClass();
	}
	
	protected void addIdClassResource() {
		typeMappingResource().setIdClass(OrmFactory.eINSTANCE.createIdClass());		
	}
	
	protected void removeIdClassResource() {
		typeMappingResource().setIdClass(null);
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
		Entity rootEntity = null;
		for (Iterator<PersistentType> i = persistentType().inheritanceHierarchy(); i.hasNext();) {
			PersistentType persistentType = i.next();
			if (persistentType.getMapping() instanceof Entity) {
				rootEntity = (Entity) persistentType.getMapping();
			}
		}
		return rootEntity;
	}

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


	public int xmlSequence() {
		return 1;
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
	public void initialize(XmlEntity entity) {
		super.initialize(entity);
		this.specifiedName = entity.getName();
		this.defaultName = this.defaultName();
		this.initializeInheritance(this.inheritanceResource());
		this.discriminatorColumn.initialize(entity);
		this.specifiedDiscriminatorValue = entity.getDiscriminatorValue();
		this.defaultDiscriminatorValue = this.defaultDiscriminatorValue();
		this.discriminatorValueAllowed = this.discriminatorValueIsAllowed();
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
		this.initializeIdClass(this.idClassResource());
	}
	
	protected void initializeInheritance(Inheritance inheritanceResource) {
		this.specifiedInheritanceStrategy = this.specifiedInheritanceStrategy(inheritanceResource);
		this.defaultInheritanceStrategy = this.defaultInheritanceStrategy();
	}

	protected void initializeSpecifiedSecondaryTables(XmlEntity entity) {
		for (XmlSecondaryTable secondaryTable : entity.getSecondaryTables()) {
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
		ListIterator<JavaSecondaryTable> javaSecondaryTables = javaEntity().secondaryTables();
		while(javaSecondaryTables.hasNext()) {
			JavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
			if (javaSecondaryTable.getName() != null) {
				//TODO calling setters during initialize in createVirtualSecondaryTable
				//I think this is going to be a problem
				this.virtualSecondaryTables.add(createVirtualSecondaryTable(javaSecondaryTable));
			}
		}
	}	
	
	protected void initializeTableGenerator(XmlEntity entity) {
		if (entity.getTableGenerator() != null) {
			this.tableGenerator = new GenericOrmTableGenerator(this);
			this.tableGenerator.initialize(entity.getTableGenerator());
		}
	}
	
	protected void initializeSequenceGenerator(XmlEntity entity) {
		if (entity.getSequenceGenerator() != null) {
			this.sequenceGenerator = new GenericOrmSequenceGenerator(this);
			this.sequenceGenerator.initialize(entity.getSequenceGenerator());
		}
	}
	
	protected void initializeSpecifiedPrimaryKeyJoinColumns(XmlEntity entity) {
		for (XmlPrimaryKeyJoinColumn primaryKeyJoinColumn : entity.getPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(createPrimaryKeyJoinColumn(primaryKeyJoinColumn));
		}
	}
	
	protected void initializeSpecifiedAttributeOverrides(XmlEntity entity) {
		for (XmlAttributeOverride attributeOverride : entity.getAttributeOverrides()) {
			this.specifiedAttributeOverrides.add(createAttributeOverride(attributeOverride));
		}
	}
	
	protected void initializeSpecifiedAssociationOverrides(XmlEntity entity) {
		for (XmlAssociationOverride associationOverride : entity.getAssociationOverrides()) {
			this.specifiedAssociationOverrides.add(createAssociationOverride(associationOverride));
		}
	}
	
	protected void initializeNamedQueries(XmlEntity entity) {
		for (XmlNamedQuery namedQuery : entity.getNamedQueries()) {
			this.namedQueries.add(createNamedQuery(namedQuery));
		}
	}
	
	protected void initializeNamedNativeQueries(XmlEntity entity) {
		for (XmlNamedNativeQuery namedNativeQuery : entity.getNamedNativeQueries()) {
			this.namedNativeQueries.add(createNamedNativeQuery(namedNativeQuery));
		}
	}
	
	protected void initializeIdClass(IdClass idClassResource) {
		this.idClass = this.idClass(idClassResource);	
	}

	protected String idClass(IdClass idClassResource) {
		return idClassResource == null ? null : idClassResource.getClassName();
	}

	@Override
	public void update(XmlEntity entity) {
		super.update(entity);
		this.setSpecifiedName(entity.getName());
		this.setDefaultName(this.defaultName());
		this.updateInheritance(this.inheritanceResource());
		this.discriminatorColumn.update(entity);
		this.setSpecifiedDiscriminatorValue(entity.getDiscriminatorValue());
		this.setDefaultDiscriminatorValue(defaultDiscriminatorValue());
		this.setDiscriminatorValueAllowed(this.discriminatorValueIsAllowed());
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
		this.updateIdClass(this.idClassResource());
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
	
	protected boolean discriminatorValueIsAllowed() {
		return javaEntity() == null ? false : javaEntity().isDiscriminatorValueAllowed();
	}

	protected void updateInheritance(Inheritance inheritanceResource) {
		this.setSpecifiedInheritanceStrategy_(this.specifiedInheritanceStrategy(inheritanceResource));
		this.setDefaultInheritanceStrategy(this.defaultInheritanceStrategy());
	}
	
	protected void updateSpecifiedSecondaryTables(XmlEntity entity) {
		ListIterator<GenericOrmSecondaryTable> secondaryTables = specifiedSecondaryTables();
		ListIterator<XmlSecondaryTable> resourceSecondaryTables = entity.getSecondaryTables().listIterator();
		
		while (secondaryTables.hasNext()) {
			GenericOrmSecondaryTable secondaryTable = secondaryTables.next();
			if (resourceSecondaryTables.hasNext()) {
				secondaryTable.update(resourceSecondaryTables.next());
			}
			else {
				removeSpecifiedSecondaryTable_(secondaryTable);
			}
		}
		
		while (resourceSecondaryTables.hasNext()) {
			addSpecifiedSecondaryTable(specifiedSecondaryTablesSize(), createSecondaryTable(resourceSecondaryTables.next()));
		}
	}
	
	//if any secondary-tables are specified in the xml file, then all of the java secondaryTables are overriden
	protected void updateVirtualSecondaryTables() {
		ListIterator<GenericOrmSecondaryTable> secondaryTables = virtualSecondaryTables();
		ListIterator<JavaSecondaryTable> javaSecondaryTables = EmptyListIterator.instance();
		
		if (javaEntity() != null && !isMetadataComplete() && specifiedSecondaryTablesSize() == 0) {
			javaSecondaryTables = javaEntity().secondaryTables();
		}
		while (secondaryTables.hasNext()) {
			GenericOrmSecondaryTable virtualSecondaryTable = secondaryTables.next();
			if (javaSecondaryTables.hasNext()) {
				JavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
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
			JavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
			addVirtualSecondaryTable(createVirtualSecondaryTable(javaSecondaryTable));
		}
	}

	protected GenericOrmSecondaryTable createSecondaryTable(XmlSecondaryTable secondaryTable) {
		GenericOrmSecondaryTable xmlSecondaryTable = new GenericOrmSecondaryTable(this);
		xmlSecondaryTable.initialize(secondaryTable);
		return xmlSecondaryTable;
	}
	
	protected GenericOrmSecondaryTable createVirtualSecondaryTable(JavaSecondaryTable javaSecondaryTable) {
		GenericOrmSecondaryTable virutalSecondaryTable = new GenericOrmSecondaryTable(this);
		virutalSecondaryTable.setDefaultName(javaSecondaryTable.getName());
		virutalSecondaryTable.setDefaultCatalog(javaSecondaryTable.getCatalog());
		virutalSecondaryTable.setDefaultSchema(javaSecondaryTable.getSchema());		
		//TODO what about primaryKeyJoinColumns, would you want to see those in the orm.xml ui??
		return virutalSecondaryTable;
	}
	
	protected void updateTableGenerator(XmlEntity entity) {
		if (entity.getTableGenerator() == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(new GenericOrmTableGenerator(this));
				getTableGenerator().initialize(entity.getTableGenerator());
			}
			else {
				getTableGenerator().update(entity.getTableGenerator());
			}
		}
	}
	
	protected void updateSequenceGenerator(XmlEntity entity) {
		if (entity.getSequenceGenerator() == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(new GenericOrmSequenceGenerator(this));
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
	
	protected void updateSpecifiedPrimaryKeyJoinColumns(XmlEntity entity) {
		ListIterator<GenericOrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<XmlPrimaryKeyJoinColumn> resourcePrimaryKeyJoinColumns = entity.getPrimaryKeyJoinColumns().listIterator();
		
		while (primaryKeyJoinColumns.hasNext()) {
			GenericOrmPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
			if (resourcePrimaryKeyJoinColumns.hasNext()) {
				primaryKeyJoinColumn.update(resourcePrimaryKeyJoinColumns.next());
			}
			else {
				removeSpecifiedPrimaryKeyJoinColumn_(primaryKeyJoinColumn);
			}
		}
		
		while (resourcePrimaryKeyJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(specifiedPrimaryKeyJoinColumnsSize(), createPrimaryKeyJoinColumn(resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected GenericOrmPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		GenericOrmPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = new GenericOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		xmlPrimaryKeyJoinColumn.initialize(primaryKeyJoinColumn);
		return xmlPrimaryKeyJoinColumn;
	}

	protected void updateSpecifiedAttributeOverrides(XmlEntity entity) {
		ListIterator<GenericOrmAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<XmlAttributeOverride> resourceAttributeOverrides = entity.getAttributeOverrides().listIterator();
		
		while (attributeOverrides.hasNext()) {
			GenericOrmAttributeOverride attributeOverride = attributeOverrides.next();
			if (resourceAttributeOverrides.hasNext()) {
				attributeOverride.update(resourceAttributeOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride_(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), createAttributeOverride(resourceAttributeOverrides.next()));
		}
	}
	
	protected GenericOrmAttributeOverride createAttributeOverride(XmlAttributeOverride attributeOverride) {
		GenericOrmAttributeOverride xmlAttributeOverride = new GenericOrmAttributeOverride(this, createAttributeOverrideOwner());
		xmlAttributeOverride.initialize(attributeOverride);
		return xmlAttributeOverride;
	}

	protected AttributeOverride.Owner createAttributeOverrideOwner() {
		return new AttributeOverrideOwner();
	}

	protected void updateSpecifiedAssociationOverrides(XmlEntity entity) {
		ListIterator<GenericOrmAssociationOverride> associationOverrides = specifiedAssociationOverrides();
		ListIterator<XmlAssociationOverride> resourceAssociationOverrides = entity.getAssociationOverrides().listIterator();
		
		while (associationOverrides.hasNext()) {
			GenericOrmAssociationOverride associationOverride = associationOverrides.next();
			if (resourceAssociationOverrides.hasNext()) {
				associationOverride.update(resourceAssociationOverrides.next());
			}
			else {
				removeSpecifiedAssociationOverride_(associationOverride);
			}
		}
		
		while (resourceAssociationOverrides.hasNext()) {
			addSpecifiedAssociationOverride(specifiedAssociationOverridesSize(), createAssociationOverride(resourceAssociationOverrides.next()));
		}
	}
	
	protected GenericOrmAssociationOverride createAssociationOverride(XmlAssociationOverride associationOverride) {
		GenericOrmAssociationOverride xmlAssociationOverride = new GenericOrmAssociationOverride(this, createAssociationOverrideOwner());
		xmlAssociationOverride.initialize(associationOverride);
		return xmlAssociationOverride;
	}

	protected AssociationOverride.Owner createAssociationOverrideOwner() {
		return new AssociationOverrideOwner();
	}
	
	protected void updateNamedQueries(XmlEntity entity) {
		ListIterator<GenericOrmNamedQuery> namedQueries = namedQueries();
		ListIterator<XmlNamedQuery> resourceNamedQueries = entity.getNamedQueries().listIterator();
		
		while (namedQueries.hasNext()) {
			GenericOrmNamedQuery namedQuery = namedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update(resourceNamedQueries.next());
			}
			else {
				removeNamedQuery_(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(namedQueriesSize(), createNamedQuery(resourceNamedQueries.next()));
		}
	}

	protected GenericOrmNamedQuery createNamedQuery(XmlNamedQuery namedQuery) {
		GenericOrmNamedQuery xmlNamedQuery = new GenericOrmNamedQuery(this);
		xmlNamedQuery.initialize(namedQuery);
		return xmlNamedQuery;
	}

	protected void updateNamedNativeQueries(XmlEntity entity) {
		ListIterator<GenericOrmNamedNativeQuery> namedNativeQueries = namedNativeQueries();
		ListIterator<XmlNamedNativeQuery> resourceNamedNativeQueries = entity.getNamedNativeQueries().listIterator();
		
		while (namedNativeQueries.hasNext()) {
			GenericOrmNamedNativeQuery namedQuery = namedNativeQueries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update(resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery_(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(namedQueriesSize(), createNamedNativeQuery(resourceNamedNativeQueries.next()));
		}
	}

	protected GenericOrmNamedNativeQuery createNamedNativeQuery(XmlNamedNativeQuery namedQuery) {
		GenericOrmNamedNativeQuery xmlNamedNativeQuery = new GenericOrmNamedNativeQuery(this);
		xmlNamedNativeQuery.initialize(namedQuery);
		return xmlNamedNativeQuery;
	}
	
	protected void updateIdClass(IdClass idClassResource) {
		this.setIdClass_(this.idClass(idClassResource));
	}

	public String primaryKeyColumnName() {
		return GenericJavaEntity.primaryKeyColumnName(persistentType().allAttributes());
	}

		
	public ColumnMapping columnMapping(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isVirtual(BaseOverride override) {
		// TODO Auto-generated method stub
		return false;
	}

	//**********  Validation **************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		table.addToMessages(messages, astRoot);	
		addIdMessages(messages, astRoot);
		
		
		for (GenericOrmSecondaryTable context : specifiedSecondaryTables) {
			context.addToMessages(messages, astRoot);
		}

		for (Iterator<GenericOrmAttributeOverride> stream = this.attributeOverrides(); stream.hasNext();) {
			stream.next().addToMessages(messages, astRoot);
		}
		
		for (Iterator<GenericOrmAssociationOverride> stream = this.associationOverrides(); stream.hasNext();) {
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
		for (Iterator<PersistentAttribute> stream = this.persistentType().allAttributes(); stream.hasNext(); ) {
			if (stream.next().isIdAttribute()) {
				return true;
			}
		}
		return false;
	}
	
	
	public TextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TypeMapping typeMapping() {
		return this;
	}
	
	public void removeFromResourceModel(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		entityMappings.getEntities().remove(this.typeMappingResource());
	}
	
	public XmlEntity addToResourceModel(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings) {
		XmlEntity entity = OrmFactory.eINSTANCE.createEntity();
		persistentType().initialize(entity);
		entityMappings.getEntities().add(entity);
		return entity;
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
	
	class PrimaryKeyJoinColumnOwner implements AbstractJoinColumn.Owner
	{
		public TextRange validationTextRange(CompilationUnit astRoot) {
			return GenericOrmEntity.this.validationTextRange(astRoot);
		}

		public TypeMapping typeMapping() {
			return GenericOrmEntity.this;
		}

		public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
			return GenericOrmEntity.this.dbTable(tableName);
		}

		public org.eclipse.jpt.db.internal.Table dbReferencedColumnTable() {
			Entity parentEntity = GenericOrmEntity.this.parentEntity();
			return (parentEntity == null) ? null : parentEntity.primaryDbTable();
		}

		public int joinColumnsSize() {
			return GenericOrmEntity.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return GenericOrmEntity.this.defaultPrimaryKeyJoinColumns.contains(joinColumn);
		}
		
		public String defaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return GenericOrmEntity.this.parentEntity().primaryKeyColumnName();
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
			return GenericOrmEntity.this.defaultAttributeOverrides.contains(override);
		}

		public TypeMapping typeMapping() {
			return GenericOrmEntity.this;
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
			return GenericOrmEntity.this.defaultAssociationOverrides.contains(override);
		}

		public TypeMapping typeMapping() {
			return GenericOrmEntity.this;
		}

		public TextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
