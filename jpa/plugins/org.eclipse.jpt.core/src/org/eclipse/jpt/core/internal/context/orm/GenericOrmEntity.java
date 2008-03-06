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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
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
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmAbstractJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEntity;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.IdClass;
import org.eclipse.jpt.core.resource.orm.Inheritance;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
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

public class GenericOrmEntity extends AbstractOrmTypeMapping<XmlEntity> implements OrmEntity
{
	protected String specifiedName;

	protected String defaultName;

	protected String idClass;

	protected final OrmTable table;

	protected final List<OrmSecondaryTable> specifiedSecondaryTables;
	
	protected final List<OrmSecondaryTable> virtualSecondaryTables;
	
	protected final List<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
	
	protected final List<OrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	protected InheritanceType specifiedInheritanceStrategy;
	
	protected InheritanceType defaultInheritanceStrategy;

	protected String defaultDiscriminatorValue;

	protected String specifiedDiscriminatorValue;

	protected boolean discriminatorValueAllowed;

	protected final OrmDiscriminatorColumn discriminatorColumn;

	protected OrmSequenceGenerator sequenceGenerator;

	protected OrmTableGenerator tableGenerator;

	protected final List<OrmAttributeOverride> specifiedAttributeOverrides;
	
	protected final List<OrmAttributeOverride> defaultAttributeOverrides;

	protected final List<OrmAssociationOverride> specifiedAssociationOverrides;

	protected final List<OrmAssociationOverride> defaultAssociationOverrides;

	protected final List<OrmNamedQuery> namedQueries;

	protected final List<OrmNamedNativeQuery> namedNativeQueries;

	public GenericOrmEntity(OrmPersistentType parent) {
		super(parent);
		this.table = jpaFactory().buildOrmTable(this);
		this.specifiedSecondaryTables = new ArrayList<OrmSecondaryTable>();
		this.virtualSecondaryTables = new ArrayList<OrmSecondaryTable>();
		this.discriminatorColumn = buildDiscriminatorColumn();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
		this.specifiedAttributeOverrides = new ArrayList<OrmAttributeOverride>();
		this.defaultAttributeOverrides = new ArrayList<OrmAttributeOverride>();
		this.specifiedAssociationOverrides = new ArrayList<OrmAssociationOverride>();
		this.defaultAssociationOverrides = new ArrayList<OrmAssociationOverride>();
		this.namedQueries = new ArrayList<OrmNamedQuery>();
		this.namedNativeQueries = new ArrayList<OrmNamedNativeQuery>();
	}
	
	protected OrmDiscriminatorColumn buildDiscriminatorColumn() {
		return jpaFactory().buildOrmDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected OrmNamedColumn.Owner buildDiscriminatorColumnOwner() {
		return new OrmNamedColumn.Owner(){
			public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
				return GenericOrmEntity.this.dbTable(tableName);
			}

			public TypeMapping typeMapping() {
				return GenericOrmEntity.this;
			}
			
			public String defaultColumnName() {
				//TODO default column name from java here or in XmlDiscriminatorColumn?
				return DiscriminatorColumn.DEFAULT_NAME;
			}
			
			public TextRange validationTextRange() {
				// TODO Auto-generated method stub
				return null;
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

	public OrmTable getTable() {
		return this.table;
	}

	public ListIterator<OrmSecondaryTable> secondaryTables() {
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
	
	public ListIterator<OrmSecondaryTable> virtualSecondaryTables() {
		return new CloneListIterator<OrmSecondaryTable>(this.virtualSecondaryTables);
	}

	public int virtualSecondaryTablesSize() {
		return this.virtualSecondaryTables.size();
	}
	
	protected void addVirtualSecondaryTable(OrmSecondaryTable secondaryTable) {
		addItemToList(secondaryTable, this.virtualSecondaryTables, OrmEntity.VIRTUAL_SECONDARY_TABLES_LIST);
	}
	
	protected void removeVirtualSecondaryTable(OrmSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.virtualSecondaryTables, OrmEntity.VIRTUAL_SECONDARY_TABLES_LIST);
	}

	public ListIterator<OrmSecondaryTable> specifiedSecondaryTables() {
		return new CloneListIterator<OrmSecondaryTable>(this.specifiedSecondaryTables);
	}

	public int specifiedSecondaryTablesSize() {
		return this.specifiedSecondaryTables.size();
	}
	
	public OrmSecondaryTable addSpecifiedSecondaryTable(int index) {
		if (!secondaryTablesDefinedInXml()) {
			throw new IllegalStateException("Virtual secondary tables exist, must first call setSecondaryTablesDefinedInXml(true)");
		}
		XmlSecondaryTable secondaryTableResource = OrmFactory.eINSTANCE.createXmlSecondaryTableImpl();
		OrmSecondaryTable secondaryTable =  buildSecondaryTable(secondaryTableResource);
		this.specifiedSecondaryTables.add(index, secondaryTable);
		typeMappingResource().getSecondaryTables().add(index, secondaryTableResource);
		fireItemAdded(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, secondaryTable);
		return secondaryTable;
	}
	
	protected void addSpecifiedSecondaryTable(int index, OrmSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.specifiedSecondaryTables, Entity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}
	
	public void removeSpecifiedSecondaryTable(int index) {
		OrmSecondaryTable removedSecondaryTable = this.specifiedSecondaryTables.remove(index);
		typeMappingResource().getSecondaryTables().remove(index);
		fireItemRemoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, removedSecondaryTable);
	}
	
	protected void removeSpecifiedSecondaryTable_(OrmSecondaryTable secondaryTable) {
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
	
	public boolean containsVirtualSecondaryTable(OrmSecondaryTable secondaryTable) {
		return this.virtualSecondaryTables.contains(secondaryTable);
	}

	protected boolean containsSecondaryTable(String name, ListIterator<OrmSecondaryTable> secondaryTables) {
		for (OrmSecondaryTable secondaryTable : CollectionTools.iterable(secondaryTables)) {
			String secondaryTableName = secondaryTable.getName();
			if (secondaryTableName != null && secondaryTableName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean secondaryTablesDefinedInXml() {
		return virtualSecondaryTablesSize() == 0;
	}
	
	public void setSecondaryTablesDefinedInXml(boolean defineInXml) {
		if (defineInXml == secondaryTablesDefinedInXml()) {
			return;
		}
		if (defineInXml) {
			specifySecondaryTablesInXml();
		}
		else {
			removeSecondaryTablesFromXml();
		}
	}
	
	/**
	 * This is used to take all the java secondary tables and specify them in the xml.  You must
	 * use setSecondaryTablesDefinedInXml(boolean) before calling addSpecifiedSecondaryTable().
	 * 
	 * Yes this code looks odd, but be careful making changes to it
	 */
	protected void specifySecondaryTablesInXml() {
		if (virtualSecondaryTablesSize() != 0) {
			List<OrmSecondaryTable> virtualSecondaryTables = CollectionTools.list(this.virtualSecondaryTables());
			List<OrmSecondaryTable> virtualSecondaryTable2 = CollectionTools.list(this.virtualSecondaryTables());
			//remove all the virtual secondary tables without firing change notification.
			for (OrmSecondaryTable virtualSecondaryTable : CollectionTools.iterable(virtualSecondaryTables())) {
				this.virtualSecondaryTables.remove(virtualSecondaryTable);				
			}
			//add specified secondary tables for each virtual secondary table. If the virtual secondary tables
			//are not removed first, they will be removed as a side effect of adding the first specified secondary table.
			//This screws up the change notification to the UI, since that change notification is in a different thread
			for (OrmSecondaryTable virtualSecondaryTable : virtualSecondaryTable2) {
				XmlSecondaryTable secondaryTableResource = OrmFactory.eINSTANCE.createXmlSecondaryTableImpl();
				OrmSecondaryTable specifiedSecondaryTable =  buildSecondaryTable(secondaryTableResource);
				this.specifiedSecondaryTables.add(specifiedSecondaryTable);
				typeMappingResource().getSecondaryTables().add(secondaryTableResource);
				specifiedSecondaryTable.initializeFrom(virtualSecondaryTable);
			}
			//fire change notification at the end
			fireItemsRemoved(OrmEntity.VIRTUAL_SECONDARY_TABLES_LIST, 0, virtualSecondaryTables);
			fireItemsAdded(Entity.SPECIFIED_SECONDARY_TABLES_LIST, 0, this.specifiedSecondaryTables);		
		}
	}
	
	protected void removeSecondaryTablesFromXml() {
		if (specifiedSecondaryTablesSize() != 0) {
			List<OrmSecondaryTable> specifiedSecondaryTables = CollectionTools.list(this.specifiedSecondaryTables());
			for (OrmSecondaryTable specifiedSecondaryTable : CollectionTools.iterable(specifiedSecondaryTables())) {
				int index = this.specifiedSecondaryTables.indexOf(specifiedSecondaryTable);
				this.specifiedSecondaryTables.remove(specifiedSecondaryTable);
				if (this.specifiedSecondaryTables.size() == 0) {
					initializeVirtualSecondaryTables();
				}
				typeMappingResource().getSecondaryTables().remove(index);
			}
			fireItemsRemoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, 0, specifiedSecondaryTables);
			if (this.virtualSecondaryTables.size() != 0) {
				fireItemsAdded(OrmEntity.VIRTUAL_SECONDARY_TABLES_LIST, 0, this.virtualSecondaryTables);
			}
		}
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

	public OrmDiscriminatorColumn getDiscriminatorColumn() {
		return this.discriminatorColumn;
	}

	public OrmSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		this.sequenceGenerator = jpaFactory().buildOrmSequenceGenerator(this);
		typeMappingResource().setSequenceGenerator(OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl());
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		OrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.typeMappingResource().setSequenceGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, null);
	}
	
	public OrmSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(OrmSequenceGenerator newSequenceGenerator) {
		OrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	public OrmTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		this.tableGenerator = jpaFactory().buildOrmTableGenerator(this);
		typeMappingResource().setTableGenerator(OrmFactory.eINSTANCE.createXmlTableGeneratorImpl());
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		OrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.typeMappingResource().setTableGenerator(null);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);
	}
	
	public OrmTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}

	protected void setTableGenerator(OrmTableGenerator newTableGenerator) {
		OrmTableGenerator oldTableGenerator = this.tableGenerator;
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

	public ListIterator<OrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		return new CloneListIterator<OrmPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumns);
	}
	
	public int defaultPrimaryKeyJoinColumnsSize() {
		return this.defaultPrimaryKeyJoinColumns.size();
	}

	public OrmPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumns() : this.specifiedPrimaryKeyJoinColumns();
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumnsSize() : this.specifiedPrimaryKeyJoinColumnsSize();
	}
	
	public ListIterator<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<OrmPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	public OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().buildOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		this.typeMappingResource().getPrimaryKeyJoinColumns().add(index, OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl());
		this.fireItemAdded(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		return primaryKeyJoinColumn;
	}
	
	protected OrmAbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, OrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(primaryKeyJoinColumn));
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		OrmPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		this.typeMappingResource().getPrimaryKeyJoinColumns().remove(index);
		fireItemRemoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(OrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedPrimaryKeyJoinColumns, targetIndex, sourceIndex);
		this.typeMappingResource().getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<OrmAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<OrmAttributeOverride>(specifiedAttributeOverrides(), defaultAttributeOverrides());
	}

	public int attributeOverridesSize() {
		return this.specifiedAttributeOverridesSize() + this.defaultAttributeOverridesSize();
	}
	
	public ListIterator<OrmAttributeOverride> defaultAttributeOverrides() {
		return new CloneListIterator<OrmAttributeOverride>(this.defaultAttributeOverrides);
	}
	
	public int defaultAttributeOverridesSize() {
		return this.defaultAttributeOverrides.size();
	}
	
	public ListIterator<OrmAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<OrmAttributeOverride>(this.specifiedAttributeOverrides);
	}

	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	public OrmAttributeOverride addSpecifiedAttributeOverride(int index) {
		XmlAttributeOverride xmlAttributeOverride = OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl();
		OrmAttributeOverride attributeOverride = buildAttributeOverride(xmlAttributeOverride);
		this.specifiedAttributeOverrides.add(index, attributeOverride);
		this.typeMappingResource().getAttributeOverrides().add(index, xmlAttributeOverride);
		this.fireItemAdded(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		return attributeOverride;
	}

	protected void addSpecifiedAttributeOverride(int index, OrmAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAttributeOverride(AttributeOverride attributeOverride) {
		removeSpecifiedAttributeOverride(this.specifiedAttributeOverrides.indexOf(attributeOverride));
	}
	
	public void removeSpecifiedAttributeOverride(int index) {
		OrmAttributeOverride removedAttributeOverride = this.specifiedAttributeOverrides.remove(index);
		this.typeMappingResource().getAttributeOverrides().remove(index);
		fireItemRemoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, removedAttributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride_(OrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.typeMappingResource().getAttributeOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	@SuppressWarnings("unchecked")
	public ListIterator<OrmAssociationOverride> associationOverrides() {
		return new CompositeListIterator<OrmAssociationOverride>(specifiedAssociationOverrides(), defaultAssociationOverrides());
	}

	public int associationOverridesSize() {
		return this.specifiedAssociationOverridesSize() + this.defaultAssociationOverridesSize();
	}

	public ListIterator<OrmAssociationOverride> defaultAssociationOverrides() {
		return new CloneListIterator<OrmAssociationOverride>(this.defaultAssociationOverrides);
	}
	
	public int defaultAssociationOverridesSize() {
		return this.defaultAssociationOverrides.size();
	}
	
	public ListIterator<OrmAssociationOverride> specifiedAssociationOverrides() {
		return new CloneListIterator<OrmAssociationOverride>(this.specifiedAssociationOverrides);
	}

	public int specifiedAssociationOverridesSize() {
		return this.specifiedAssociationOverrides.size();
	}

	public OrmAssociationOverride addSpecifiedAssociationOverride(int index) {
		XmlAssociationOverride xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		OrmAssociationOverride associationOverride = buildAssociationOverride(xmlAssociationOverride);
		this.specifiedAssociationOverrides.add(index, associationOverride);
		this.typeMappingResource().getAssociationOverrides().add(index, xmlAssociationOverride);
		this.fireItemAdded(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, associationOverride);
		return associationOverride;
	}

	protected void addSpecifiedAssociationOverride(int index, OrmAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void removeSpecifiedAssociationOverride(AssociationOverride associationOverride) {
		removeSpecifiedAssociationOverride(this.specifiedAssociationOverrides.indexOf(associationOverride));
	}
	
	public void removeSpecifiedAssociationOverride(int index) {
		OrmAssociationOverride removedAssociationOverride = this.specifiedAssociationOverrides.remove(index);
		this.typeMappingResource().getAssociationOverrides().remove(index);
		fireItemRemoved(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, index, removedAssociationOverride);
	}
	
	protected void removeSpecifiedAssociationOverride_(OrmAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAssociationOverrides, targetIndex, sourceIndex);
		this.typeMappingResource().getAssociationOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	public ListIterator<OrmNamedQuery> namedQueries() {
		return new CloneListIterator<OrmNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public OrmNamedQuery addNamedQuery(int index) {
		OrmNamedQuery namedQuery = jpaFactory().buildOrmNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		this.typeMappingResource().getNamedQueries().add(index, OrmFactory.eINSTANCE.createXmlNamedQuery());
		this.fireItemAdded(Entity.NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, OrmNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, Entity.NAMED_QUERIES_LIST);
	}
		
	public void removeNamedQuery(NamedQuery namedQuery) {
		removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}
	
	public void removeNamedQuery(int index) {
		OrmNamedQuery namedQuery = this.namedQueries.remove(index);
		this.typeMappingResource().getNamedQueries().remove(index);
		fireItemRemoved(Entity.NAMED_QUERIES_LIST, index, namedQuery);
	}
	
	protected void removeNamedQuery_(OrmNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, Entity.NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
		this.typeMappingResource().getNamedQueries().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.NAMED_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<OrmNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<OrmNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public OrmNamedNativeQuery addNamedNativeQuery(int index) {
		OrmNamedNativeQuery namedNativeQuery = jpaFactory().buildOrmNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.typeMappingResource().getNamedNativeQueries().add(index, OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		this.fireItemAdded(Entity.NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, OrmNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, Entity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}
	
	public void removeNamedNativeQuery(int index) {
		OrmNamedNativeQuery namedNativeQuery = this.namedNativeQueries.remove(index);
		this.typeMappingResource().getNamedNativeQueries().remove(index);
		fireItemRemoved(Entity.NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
	}

	protected void removeNamedNativeQuery_(OrmNamedNativeQuery namedNativeQuery) {
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
			this.specifiedSecondaryTables.add(buildSecondaryTable(secondaryTable));
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
				this.virtualSecondaryTables.add(buildVirtualSecondaryTable(javaSecondaryTable));
			}
		}
	}	
	
	protected void initializeTableGenerator(XmlEntity entity) {
		if (entity.getTableGenerator() != null) {
			this.tableGenerator = buildTableGenerator(entity.getTableGenerator());
		}
	}
	
	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator tableGeneratorResource) {
		OrmTableGenerator tableGenerator = jpaFactory().buildOrmTableGenerator(this);
		tableGenerator.initialize(tableGeneratorResource);
		return tableGenerator;
	}

	protected void initializeSequenceGenerator(XmlEntity entity) {
		if (entity.getSequenceGenerator() != null) {
			this.sequenceGenerator = buildSequenceGenerator(entity.getSequenceGenerator());
		}
	}
	
	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator xmlSequenceGenerator) {
		OrmSequenceGenerator sequenceGenerator = jpaFactory().buildOrmSequenceGenerator(this);
		sequenceGenerator.initialize(xmlSequenceGenerator);
		return sequenceGenerator;
	}

	protected void initializeSpecifiedPrimaryKeyJoinColumns(XmlEntity entity) {
		for (XmlPrimaryKeyJoinColumn primaryKeyJoinColumn : entity.getPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(createPrimaryKeyJoinColumn(primaryKeyJoinColumn));
		}
	}
	
	protected void initializeSpecifiedAttributeOverrides(XmlEntity entity) {
		for (XmlAttributeOverride attributeOverride : entity.getAttributeOverrides()) {
			this.specifiedAttributeOverrides.add(buildAttributeOverride(attributeOverride));
		}
	}
	
	protected void initializeSpecifiedAssociationOverrides(XmlEntity entity) {
		for (XmlAssociationOverride associationOverride : entity.getAssociationOverrides()) {
			this.specifiedAssociationOverrides.add(buildAssociationOverride(associationOverride));
		}
	}
	
	protected void initializeNamedQueries(XmlEntity entity) {
		for (XmlNamedQuery namedQuery : entity.getNamedQueries()) {
			this.namedQueries.add(buildNamedQuery(namedQuery));
		}
	}
	
	protected void initializeNamedNativeQueries(XmlEntity entity) {
		for (XmlNamedNativeQuery namedNativeQuery : entity.getNamedNativeQueries()) {
			this.namedNativeQueries.add(buildNamedNativeQuery(namedNativeQuery));
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
		ListIterator<OrmSecondaryTable> secondaryTables = specifiedSecondaryTables();
		ListIterator<XmlSecondaryTable> resourceSecondaryTables = entity.getSecondaryTables().listIterator();
		
		while (secondaryTables.hasNext()) {
			OrmSecondaryTable secondaryTable = secondaryTables.next();
			if (resourceSecondaryTables.hasNext()) {
				secondaryTable.update(resourceSecondaryTables.next());
			}
			else {
				removeSpecifiedSecondaryTable_(secondaryTable);
			}
		}
		
		while (resourceSecondaryTables.hasNext()) {
			addSpecifiedSecondaryTable(specifiedSecondaryTablesSize(), buildSecondaryTable(resourceSecondaryTables.next()));
		}
	}
	
	//if any secondary-tables are specified in the xml file, then all of the java secondaryTables are overriden
	protected void updateVirtualSecondaryTables() {
		ListIterator<OrmSecondaryTable> secondaryTables = virtualSecondaryTables();
		ListIterator<JavaSecondaryTable> javaSecondaryTables = EmptyListIterator.instance();
		
		if (javaEntity() != null && !isMetadataComplete() && specifiedSecondaryTablesSize() == 0) {
			javaSecondaryTables = javaEntity().secondaryTables();
		}
		while (secondaryTables.hasNext()) {
			OrmSecondaryTable virtualSecondaryTable = secondaryTables.next();
			if (javaSecondaryTables.hasNext()) {
				JavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
				virtualSecondaryTable.update(new VirtualXmlSecondaryTable(javaSecondaryTable));
			}
			else {
				removeVirtualSecondaryTable(virtualSecondaryTable);
			}
		}
		
		while (javaSecondaryTables.hasNext()) {
			JavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
			addVirtualSecondaryTable(buildVirtualSecondaryTable(javaSecondaryTable));
		}
	}

	protected OrmSecondaryTable buildSecondaryTable(XmlSecondaryTable xmlSecondaryTable) {
		return jpaFactory().buildOrmSecondaryTable(this, xmlSecondaryTable);
	}
	
	protected OrmSecondaryTable buildVirtualSecondaryTable(JavaSecondaryTable javaSecondaryTable) {
		return buildSecondaryTable(new VirtualXmlSecondaryTable(javaSecondaryTable));
	}
	
	protected void updateTableGenerator(XmlEntity entity) {
		if (entity.getTableGenerator() == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(buildTableGenerator(entity.getTableGenerator()));
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
				setSequenceGenerator(buildSequenceGenerator(entity.getSequenceGenerator()));
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
		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<XmlPrimaryKeyJoinColumn> resourcePrimaryKeyJoinColumns = entity.getPrimaryKeyJoinColumns().listIterator();
		
		while (primaryKeyJoinColumns.hasNext()) {
			OrmPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
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
	
	protected OrmPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		OrmPrimaryKeyJoinColumn ormPrimaryKeyJoinColumn = jpaFactory().buildOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		ormPrimaryKeyJoinColumn.initialize(primaryKeyJoinColumn);
		return ormPrimaryKeyJoinColumn;
	}

	protected void updateSpecifiedAttributeOverrides(XmlEntity entity) {
		ListIterator<OrmAttributeOverride> attributeOverrides = specifiedAttributeOverrides();
		ListIterator<XmlAttributeOverride> resourceAttributeOverrides = entity.getAttributeOverrides().listIterator();
		
		while (attributeOverrides.hasNext()) {
			OrmAttributeOverride attributeOverride = attributeOverrides.next();
			if (resourceAttributeOverrides.hasNext()) {
				attributeOverride.update(resourceAttributeOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride_(attributeOverride);
			}
		}
		
		while (resourceAttributeOverrides.hasNext()) {
			addSpecifiedAttributeOverride(specifiedAttributeOverridesSize(), buildAttributeOverride(resourceAttributeOverrides.next()));
		}
	}
	
	protected OrmAttributeOverride buildAttributeOverride(XmlAttributeOverride attributeOverride) {
		return jpaFactory().buildOrmAttributeOverride(this, createAttributeOverrideOwner(), attributeOverride);
	}

	protected AttributeOverride.Owner createAttributeOverrideOwner() {
		return new AttributeOverrideOwner();
	}

	protected void updateSpecifiedAssociationOverrides(XmlEntity entity) {
		ListIterator<OrmAssociationOverride> associationOverrides = specifiedAssociationOverrides();
		ListIterator<XmlAssociationOverride> resourceAssociationOverrides = entity.getAssociationOverrides().listIterator();
		
		while (associationOverrides.hasNext()) {
			OrmAssociationOverride associationOverride = associationOverrides.next();
			if (resourceAssociationOverrides.hasNext()) {
				associationOverride.update(resourceAssociationOverrides.next());
			}
			else {
				removeSpecifiedAssociationOverride_(associationOverride);
			}
		}
		
		while (resourceAssociationOverrides.hasNext()) {
			addSpecifiedAssociationOverride(specifiedAssociationOverridesSize(), buildAssociationOverride(resourceAssociationOverrides.next()));
		}
	}
	
	protected OrmAssociationOverride buildAssociationOverride(XmlAssociationOverride associationOverride) {
		return jpaFactory().buildOrmAssociationOverride(this, createAssociationOverrideOwner(), associationOverride);
	}

	protected AssociationOverride.Owner createAssociationOverrideOwner() {
		return new AssociationOverrideOwner();
	}
	
	protected void updateNamedQueries(XmlEntity entity) {
		ListIterator<OrmNamedQuery> namedQueries = namedQueries();
		ListIterator<XmlNamedQuery> resourceNamedQueries = entity.getNamedQueries().listIterator();
		
		while (namedQueries.hasNext()) {
			OrmNamedQuery namedQuery = namedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update(resourceNamedQueries.next());
			}
			else {
				removeNamedQuery_(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(namedQueriesSize(), buildNamedQuery(resourceNamedQueries.next()));
		}
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery namedQuery) {
		OrmNamedQuery ormNamedQuery = jpaFactory().buildOrmNamedQuery(this);
		ormNamedQuery.initialize(namedQuery);
		return ormNamedQuery;
	}

	protected void updateNamedNativeQueries(XmlEntity entity) {
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = namedNativeQueries();
		ListIterator<XmlNamedNativeQuery> resourceNamedNativeQueries = entity.getNamedNativeQueries().listIterator();
		
		while (namedNativeQueries.hasNext()) {
			OrmNamedNativeQuery namedQuery = namedNativeQueries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update(resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery_(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(namedQueriesSize(), buildNamedNativeQuery(resourceNamedNativeQueries.next()));
		}
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery namedQuery) {
		OrmNamedNativeQuery ormNamedNativeQuery = jpaFactory().buildOrmNamedNativeQuery(this);
		ormNamedNativeQuery.initialize(namedQuery);
		return ormNamedNativeQuery;
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
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		getTable().addToMessages(messages);	
		addIdMessages(messages);
		for (OrmSecondaryTable secondaryTable : CollectionTools.iterable(secondaryTables())) {
			secondaryTable.addToMessages(messages);
		}

		for (OrmAttributeOverride attributeOverride : CollectionTools.iterable(attributeOverrides())) {
			attributeOverride.addToMessages(messages);
		}

		for (OrmAssociationOverride associationOverride : CollectionTools.iterable(associationOverrides())) {
			associationOverride.addToMessages(messages);
		}
	}
	
	protected void addIdMessages(List<IMessage> messages) {
		addNoIdMessage(messages);
	}
	
	protected void addNoIdMessage(List<IMessage> messages) {
		if (entityHasNoId()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ENTITY_NO_ID,
					new String[] {this.getName()},
					this, 
					this.validationTextRange())
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
	
	public TypeMapping typeMapping() {
		return this;
	}
	
	public void removeFromResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getEntities().remove(this.typeMappingResource());
	}
	
	public XmlEntity addToResourceModel(XmlEntityMappings entityMappings) {
		XmlEntity entity = OrmFactory.eINSTANCE.createXmlEntity();
		persistentType().initialize(entity);
		entityMappings.getEntities().add(entity);
		return entity;
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
	
	class PrimaryKeyJoinColumnOwner implements OrmAbstractJoinColumn.Owner
	{
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
		
		public TextRange validationTextRange() {
			// TODO Auto-generated method stub
			return null;
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
	}
}
