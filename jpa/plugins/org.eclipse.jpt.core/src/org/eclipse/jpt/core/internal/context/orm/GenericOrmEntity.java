/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
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
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.Inheritance;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlIdClass;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericOrmEntity
	extends AbstractOrmTypeMapping<XmlEntity>
	implements OrmEntity
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
	
	protected final List<OrmAttributeOverride> virtualAttributeOverrides;

	protected final List<OrmAssociationOverride> specifiedAssociationOverrides;

	protected final List<OrmAssociationOverride> virtualAssociationOverrides;

	protected final List<OrmNamedQuery> namedQueries;

	protected final List<OrmNamedNativeQuery> namedNativeQueries;

	public GenericOrmEntity(OrmPersistentType parent) {
		super(parent);
		this.table = getJpaFactory().buildOrmTable(this);
		this.specifiedSecondaryTables = new ArrayList<OrmSecondaryTable>();
		this.virtualSecondaryTables = new ArrayList<OrmSecondaryTable>();
		this.discriminatorColumn = buildDiscriminatorColumn();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
		this.specifiedAttributeOverrides = new ArrayList<OrmAttributeOverride>();
		this.virtualAttributeOverrides = new ArrayList<OrmAttributeOverride>();
		this.specifiedAssociationOverrides = new ArrayList<OrmAssociationOverride>();
		this.virtualAssociationOverrides = new ArrayList<OrmAssociationOverride>();
		this.namedQueries = new ArrayList<OrmNamedQuery>();
		this.namedNativeQueries = new ArrayList<OrmNamedNativeQuery>();
	}
	
	protected OrmDiscriminatorColumn buildDiscriminatorColumn() {
		return getJpaFactory().buildOrmDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected OrmNamedColumn.Owner buildDiscriminatorColumnOwner() {
		return new OrmNamedColumn.Owner(){
			public org.eclipse.jpt.db.Table getDbTable(String tableName) {
				return GenericOrmEntity.this.getDbTable(tableName);
			}

			public TypeMapping getTypeMapping() {
				return GenericOrmEntity.this;
			}
			
			public String getDefaultColumnName() {
				//TODO default column name from java here or in XmlDiscriminatorColumn?
				return DiscriminatorColumn.DEFAULT_NAME;
			}
			
			public TextRange getValidationTextRange() {
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
	public String getPrimaryTableName() {
		return this.table.getName();
	}
	
	@Override
	public org.eclipse.jpt.db.Table getPrimaryDbTable() {
		return this.table.getDbTable();
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

	private Iterator<org.eclipse.jpt.db.Table> associatedDbTablesIncludingInherited() {
		return new FilteringIterator<org.eclipse.jpt.db.Table, org.eclipse.jpt.db.Table>(this.associatedDbTablesIncludingInherited_()) {
			@Override
			protected boolean accept(org.eclipse.jpt.db.Table t) {
				return t != null;
			}
		};
	}

	private Iterator<org.eclipse.jpt.db.Table> associatedDbTablesIncludingInherited_() {
		return new TransformationIterator<Table, org.eclipse.jpt.db.Table>(this.associatedTablesIncludingInherited()) {
			@Override
			protected org.eclipse.jpt.db.Table transform(Table t) {
				return t.getDbTable();
			}
		};
	}
	
	@Override
	public Schema getDbSchema() {
		return this.table.getDbSchema();
	}
	
	public JavaEntity getJavaEntity() {
		JavaPersistentType javaPersistentType = this.getJavaPersistentType();
		if (javaPersistentType != null && javaPersistentType.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
			return (JavaEntity) javaPersistentType.getMapping();
		}
		return null;
	}

	/**
	 * This checks metaDataComplete before returning the JavaEntity.
	 * As far as defaults are concerned, if metadataComplete is true, the JavaEntity is ignored.
	 */
	protected JavaEntity getJavaEntityForDefaults() {
		if (isMetadataComplete()) {
			return null;
		}
		return getJavaEntity();
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
		this.resourceTypeMapping.setName(newSpecifiedName);
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
			throw new IllegalStateException("Virtual secondary tables exist, must first call setSecondaryTablesDefinedInXml(true)"); //$NON-NLS-1$
		}
		XmlSecondaryTable secondaryTableResource = OrmFactory.eINSTANCE.createXmlSecondaryTableImpl();
		OrmSecondaryTable secondaryTable =  buildSecondaryTable(secondaryTableResource);
		this.specifiedSecondaryTables.add(index, secondaryTable);
		this.resourceTypeMapping.getSecondaryTables().add(index, secondaryTableResource);
		fireItemAdded(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, secondaryTable);
		return secondaryTable;
	}

	public OrmSecondaryTable addSpecifiedSecondaryTable() {
		return this.addSpecifiedSecondaryTable(this.specifiedSecondaryTables.size());
	}
	
	protected void addSpecifiedSecondaryTable(int index, OrmSecondaryTable secondaryTable) {
		addItemToList(index, secondaryTable, this.specifiedSecondaryTables, Entity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	protected void addSpecifiedSecondaryTable(OrmSecondaryTable secondaryTable) {
		this.addSpecifiedSecondaryTable(this.specifiedSecondaryTables.size(), secondaryTable);
	}
	
	public void removeSpecifiedSecondaryTable(SecondaryTable secondaryTable) {
		this.removeSpecifiedSecondaryTable(this.specifiedSecondaryTables.indexOf(secondaryTable));
	}
	
	public void removeSpecifiedSecondaryTable(int index) {
		OrmSecondaryTable removedSecondaryTable = this.specifiedSecondaryTables.remove(index);
		this.resourceTypeMapping.getSecondaryTables().remove(index);
		fireItemRemoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, index, removedSecondaryTable);
	}
	
	protected void removeSpecifiedSecondaryTable_(OrmSecondaryTable secondaryTable) {
		removeItemFromList(secondaryTable, this.specifiedSecondaryTables, Entity.SPECIFIED_SECONDARY_TABLES_LIST);
	}
	
	public void moveSpecifiedSecondaryTable(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedSecondaryTables, targetIndex, sourceIndex);
		this.resourceTypeMapping.getSecondaryTables().move(targetIndex, sourceIndex);
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
			List<OrmSecondaryTable> virtualSecondaryTables1 = CollectionTools.list(this.virtualSecondaryTables());
			List<OrmSecondaryTable> virtualSecondaryTables2 = CollectionTools.list(this.virtualSecondaryTables());
			//remove all the virtual secondary tables without firing change notification.
			for (OrmSecondaryTable virtualSecondaryTable : CollectionTools.iterable(virtualSecondaryTables())) {
				this.virtualSecondaryTables.remove(virtualSecondaryTable);				
			}
			//add specified secondary tables for each virtual secondary table. If the virtual secondary tables
			//are not removed first, they will be removed as a side effect of adding the first specified secondary table.
			//This screws up the change notification to the UI, since that change notification is in a different thread
			for (OrmSecondaryTable virtualSecondaryTable : virtualSecondaryTables2) {
				XmlSecondaryTable secondaryTableResource = OrmFactory.eINSTANCE.createXmlSecondaryTableImpl();
				OrmSecondaryTable specifiedSecondaryTable =  buildSecondaryTable(secondaryTableResource);
				this.specifiedSecondaryTables.add(specifiedSecondaryTable);
				this.resourceTypeMapping.getSecondaryTables().add(secondaryTableResource);
				specifiedSecondaryTable.initializeFrom(virtualSecondaryTable);
			}
			//fire change notification at the end
			fireItemsRemoved(OrmEntity.VIRTUAL_SECONDARY_TABLES_LIST, 0, virtualSecondaryTables1);
			fireItemsAdded(Entity.SPECIFIED_SECONDARY_TABLES_LIST, 0, this.specifiedSecondaryTables);		
		}
	}
	
	protected void removeSecondaryTablesFromXml() {
		if (specifiedSecondaryTablesSize() != 0) {
			List<OrmSecondaryTable> specifiedSecondaryTablesCopy = CollectionTools.list(this.specifiedSecondaryTables());
			for (OrmSecondaryTable specifiedSecondaryTable : CollectionTools.iterable(specifiedSecondaryTables())) {
				int index = this.specifiedSecondaryTables.indexOf(specifiedSecondaryTable);
				this.specifiedSecondaryTables.remove(specifiedSecondaryTable);
				if (this.specifiedSecondaryTables.size() == 0) {
					initializeVirtualSecondaryTables();
				}
				this.resourceTypeMapping.getSecondaryTables().remove(index);
			}
			fireItemsRemoved(Entity.SPECIFIED_SECONDARY_TABLES_LIST, 0, specifiedSecondaryTablesCopy);
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
		return new CompositeIterator<Table>(this.table, this.secondaryTables());
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
		return ! CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
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
			if (this.getResourceInheritance() != null) {
				this.getResourceInheritance().setStrategy(InheritanceType.toOrmResourceModel(newInheritanceType));						
				if (this.getResourceInheritance().isAllFeaturesUnset()) {
					removeResourceInheritance();
				}
			}
			else if (newInheritanceType != null) {
				addResourceInheritance();
				getResourceInheritance().setStrategy(InheritanceType.toOrmResourceModel(newInheritanceType));
			}
		}
		firePropertyChanged(SPECIFIED_INHERITANCE_STRATEGY_PROPERTY, oldInheritanceType, newInheritanceType);
	}
	
	protected void setSpecifiedInheritanceStrategy_(InheritanceType newInheritanceType) {
		InheritanceType oldInheritanceType = this.specifiedInheritanceStrategy;
		this.specifiedInheritanceStrategy = newInheritanceType;
		firePropertyChanged(SPECIFIED_INHERITANCE_STRATEGY_PROPERTY, oldInheritanceType, newInheritanceType);
	}

	protected Inheritance getResourceInheritance() {
		return this.resourceTypeMapping.getInheritance();
	}
	
	protected void addResourceInheritance() {
		this.resourceTypeMapping.setInheritance(OrmFactory.eINSTANCE.createInheritance());		
	}
	
	protected void removeResourceInheritance() {
		this.resourceTypeMapping.setInheritance(null);
	}

	public OrmDiscriminatorColumn getDiscriminatorColumn() {
		return this.discriminatorColumn;
	}

	public OrmSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists"); //$NON-NLS-1$
		}
		XmlSequenceGenerator resourceSequenceGenerator = OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl();
		this.sequenceGenerator = buildSequenceGenerator(resourceSequenceGenerator);
		this.resourceTypeMapping.setSequenceGenerator(resourceSequenceGenerator);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed"); //$NON-NLS-1$
		}
		OrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.resourceTypeMapping.setSequenceGenerator(null);
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
			throw new IllegalStateException("tableGenerator already exists"); //$NON-NLS-1$
		}
		XmlTableGenerator resourceTableGenerator = OrmFactory.eINSTANCE.createXmlTableGeneratorImpl();
		this.tableGenerator = buildTableGenerator(resourceTableGenerator);
		this.resourceTypeMapping.setTableGenerator(resourceTableGenerator);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed"); //$NON-NLS-1$
		}
		OrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.resourceTypeMapping.setTableGenerator(null);
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
	
	protected Iterator<OrmGenerator> generators() {
		ArrayList<OrmGenerator> generators = new ArrayList<OrmGenerator>();
		this.addGeneratorsTo(generators);
		return generators.iterator();
	}

	protected void addGeneratorsTo(ArrayList<OrmGenerator> generators) {
		if (this.sequenceGenerator != null) {
			generators.add(this.sequenceGenerator);
		}
		if (this.tableGenerator != null) {
			generators.add(this.tableGenerator);
		}
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
		this.resourceTypeMapping.setDiscriminatorValue(newSpecifiedDiscriminatorValue);
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
		throw new UnsupportedOperationException("use defaultPrimaryKeyJoinColumns() instead"); //$NON-NLS-1$
	}

	protected void addDefaultPrimaryKeyJoinColumn(OrmPrimaryKeyJoinColumn defaultPkJoinColumn) {
		addItemToList(defaultPkJoinColumn, this.defaultPrimaryKeyJoinColumns, OrmEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	protected void removeDefaultPrimaryKeyJoinColumn(PrimaryKeyJoinColumn defaultPkJoinColumn) {
		removeItemFromList(defaultPkJoinColumn, this.defaultPrimaryKeyJoinColumns, OrmEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST);
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
	
	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.specifiedPrimaryKeyJoinColumns.isEmpty();
	}	

	public OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		if (!this.defaultPrimaryKeyJoinColumns.isEmpty()) {
			this.defaultPrimaryKeyJoinColumns.clear();
		}
		XmlPrimaryKeyJoinColumn resourcePkJoinColumn = OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl();
		OrmPrimaryKeyJoinColumn contextPkJoinColumn = buildPrimaryKeyJoinColumn(resourcePkJoinColumn);
		this.specifiedPrimaryKeyJoinColumns.add(index, contextPkJoinColumn);
		this.resourceTypeMapping.getPrimaryKeyJoinColumns().add(index, resourcePkJoinColumn);

		this.fireItemAdded(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, contextPkJoinColumn);
		this.fireListCleared(OrmEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST);
		return contextPkJoinColumn;
	}
	
	protected OrmBaseJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, OrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	protected void addSpecifiedPrimaryKeyJoinColumn(OrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.addSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.size(), primaryKeyJoinColumn);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(primaryKeyJoinColumn));
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		OrmPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		if (!containsSpecifiedPrimaryKeyJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			initializeDefaultPrimaryKeyJoinColumns();
		}
		this.resourceTypeMapping.getPrimaryKeyJoinColumns().remove(index);
		fireItemRemoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
		if (!this.defaultPrimaryKeyJoinColumns.isEmpty()) {
			fireListChanged(OrmEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST);
		}
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(OrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedPrimaryKeyJoinColumns, targetIndex, sourceIndex);
		this.resourceTypeMapping.getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<OrmAttributeOverride> attributeOverrides() {
		return new CompositeListIterator<OrmAttributeOverride>(specifiedAttributeOverrides(), virtualAttributeOverrides());
	}

	public int attributeOverridesSize() {
		return this.specifiedAttributeOverridesSize() + this.virtualAttributeOverridesSize();
	}
	
	public ListIterator<OrmAttributeOverride> virtualAttributeOverrides() {
		return new CloneListIterator<OrmAttributeOverride>(this.virtualAttributeOverrides);
	}
	
	public int virtualAttributeOverridesSize() {
		return this.virtualAttributeOverrides.size();
	}
	
	protected void addVirtualAttributeOverride(OrmAttributeOverride attributeOverride) {
		addItemToList(attributeOverride, this.virtualAttributeOverrides, Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void removeVirtualAttributeOverride(OrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.virtualAttributeOverrides, Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected OrmAttributeOverride setAttributeOverrideVirtual(boolean virtual, OrmAttributeOverride attributeOverride) {
		if (virtual) {
			return setAttributeOverrideVirtual(attributeOverride);
		}
		return setAttributeOverrideSpecified(attributeOverride);
	}
	
	protected OrmAttributeOverride setAttributeOverrideVirtual(OrmAttributeOverride attributeOverride) {
		int index = this.specifiedAttributeOverrides.indexOf(attributeOverride);
		this.specifiedAttributeOverrides.remove(index);
		String attributeOverrideName = attributeOverride.getName();
		//add the virtual attribute override so that I can control the order that change notification is sent.
		//otherwise when we remove the annotation from java we will get an update and add the attribute override
		//during the udpate.  This causes the UI to be flaky, since change notification might not occur in the correct order
		OrmAttributeOverride virtualAttributeOverride = null;
		if (attributeOverrideName != null) {
			for (PersistentAttribute persistentAttribute : CollectionTools.iterable(allOverridableAttributes())) {
				if (persistentAttribute.getName().equals(attributeOverrideName)) {
					JavaAttributeOverride javaAttributeOverride = null;
					if (getJavaEntity() != null) {
						javaAttributeOverride = getJavaEntity().getAttributeOverrideNamed(attributeOverrideName);
					}
					//store the virtualAttributeOverride so we can fire change notification later
					virtualAttributeOverride = buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride);
					this.virtualAttributeOverrides.add(virtualAttributeOverride);
				}
			}
		}

		this.resourceTypeMapping.getAttributeOverrides().remove(index);
		fireItemRemoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, attributeOverride);
		
		if (virtualAttributeOverride != null) {
			fireItemAdded(Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, virtualAttributeOverridesSize() - 1, virtualAttributeOverride);
		}
		return virtualAttributeOverride;
	}
	
	protected OrmAttributeOverride setAttributeOverrideSpecified(OrmAttributeOverride oldAttributeOverride) {
		int index = specifiedAttributeOverridesSize();
		XmlAttributeOverride xmlAttributeOverride = OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl();
		OrmAttributeOverride newAttributeOverride = getJpaFactory().buildOrmAttributeOverride(this, createAttributeOverrideOwner(), xmlAttributeOverride);
		this.specifiedAttributeOverrides.add(index, newAttributeOverride);
		
		this.resourceTypeMapping.getAttributeOverrides().add(xmlAttributeOverride);
		
		int defaultIndex = this.virtualAttributeOverrides.indexOf(oldAttributeOverride);
		this.virtualAttributeOverrides.remove(defaultIndex);

		newAttributeOverride.setName(oldAttributeOverride.getName());
		newAttributeOverride.getColumn().setSpecifiedName(oldAttributeOverride.getColumn().getName());
		
		this.fireItemRemoved(Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST, defaultIndex, oldAttributeOverride);
		this.fireItemAdded(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, index, newAttributeOverride);		

		return newAttributeOverride;
	}

	public ListIterator<OrmAttributeOverride> specifiedAttributeOverrides() {
		return new CloneListIterator<OrmAttributeOverride>(this.specifiedAttributeOverrides);
	}

	public int specifiedAttributeOverridesSize() {
		return this.specifiedAttributeOverrides.size();
	}

	protected void addSpecifiedAttributeOverride(int index, OrmAttributeOverride attributeOverride) {
		addItemToList(index, attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	protected void addSpecifiedAttributeOverride(OrmAttributeOverride attributeOverride) {
		this.addSpecifiedAttributeOverride(this.specifiedAttributeOverrides.size(), attributeOverride);
	}
	
	protected void removeSpecifiedAttributeOverride_(OrmAttributeOverride attributeOverride) {
		removeItemFromList(attributeOverride, this.specifiedAttributeOverrides, Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAttributeOverrides, targetIndex, sourceIndex);
		this.resourceTypeMapping.getAttributeOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	@SuppressWarnings("unchecked")
	public ListIterator<OrmAssociationOverride> associationOverrides() {
		return new CompositeListIterator<OrmAssociationOverride>(specifiedAssociationOverrides(), virtualAssociationOverrides());
	}

	public int associationOverridesSize() {
		return this.specifiedAssociationOverridesSize() + this.virtualAssociationOverridesSize();
	}

	public ListIterator<OrmAssociationOverride> virtualAssociationOverrides() {
		return new CloneListIterator<OrmAssociationOverride>(this.virtualAssociationOverrides);
	}
	
	public int virtualAssociationOverridesSize() {
		return this.virtualAssociationOverrides.size();
	}
	
	public ListIterator<OrmAssociationOverride> specifiedAssociationOverrides() {
		return new CloneListIterator<OrmAssociationOverride>(this.specifiedAssociationOverrides);
	}

	public int specifiedAssociationOverridesSize() {
		return this.specifiedAssociationOverrides.size();
	}

	protected void addSpecifiedAssociationOverride(int index, OrmAssociationOverride associationOverride) {
		addItemToList(index, associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	protected void addSpecifiedAssociationOverride(OrmAssociationOverride associationOverride) {
		this.addSpecifiedAssociationOverride(this.specifiedAssociationOverrides.size(), associationOverride);
	}
	
	protected void removeSpecifiedAssociationOverride_(OrmAssociationOverride associationOverride) {
		removeItemFromList(associationOverride, this.specifiedAssociationOverrides, Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST);
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedAssociationOverrides, targetIndex, sourceIndex);
		this.resourceTypeMapping.getAssociationOverrides().move(targetIndex, sourceIndex);
		fireItemMoved(Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST, targetIndex, sourceIndex);		
	}

	public OrmAttributeOverride getAttributeOverrideNamed(String name) {
		return (OrmAttributeOverride) getOverrideNamed(name, attributeOverrides());
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

	public OrmAssociationOverride getAssociationOverrideNamed(String name) {
		return (OrmAssociationOverride) getOverrideNamed(name, associationOverrides());
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

	private BaseOverride getOverrideNamed(String name, ListIterator<? extends BaseOverride> overrides) {
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
		return getOverrideNamed(name, overrides) != null;
	}

	
	public ListIterator<OrmNamedQuery> namedQueries() {
		return new CloneListIterator<OrmNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public OrmNamedQuery addNamedQuery(int index) {
		XmlNamedQuery resourceNamedQuery = OrmFactory.eINSTANCE.createXmlNamedQuery();
		OrmNamedQuery contextNamedQuery = buildNamedQuery(resourceNamedQuery);
		this.namedQueries.add(index, contextNamedQuery);
		this.resourceTypeMapping.getNamedQueries().add(index, resourceNamedQuery);
		this.fireItemAdded(NAMED_QUERIES_LIST, index, contextNamedQuery);
		return contextNamedQuery;
	}
	
	protected void addNamedQuery(int index, OrmNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
		
	protected void addNamedQuery(OrmNamedQuery namedQuery) {
		this.addNamedQuery(this.namedQueries.size(), namedQuery);
	}
		
	public void removeNamedQuery(NamedQuery namedQuery) {
		removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}
	
	public void removeNamedQuery(int index) {
		OrmNamedQuery namedQuery = this.namedQueries.remove(index);
		this.resourceTypeMapping.getNamedQueries().remove(index);
		fireItemRemoved(NAMED_QUERIES_LIST, index, namedQuery);
	}
	
	protected void removeNamedQuery_(OrmNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
		this.resourceTypeMapping.getNamedQueries().move(targetIndex, sourceIndex);
		fireItemMoved(NAMED_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<OrmNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<OrmNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public OrmNamedNativeQuery addNamedNativeQuery(int index) {
		XmlNamedNativeQuery resourceNamedNativeQuery = OrmFactory.eINSTANCE.createXmlNamedNativeQuery();
		OrmNamedNativeQuery contextNamedNativeQuery = buildNamedNativeQuery(resourceNamedNativeQuery);
		this.namedNativeQueries.add(index, contextNamedNativeQuery);
		this.resourceTypeMapping.getNamedNativeQueries().add(index, resourceNamedNativeQuery);
		this.fireItemAdded(NAMED_NATIVE_QUERIES_LIST, index, contextNamedNativeQuery);
		return contextNamedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, OrmNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
	
	protected void addNamedNativeQuery(OrmNamedNativeQuery namedNativeQuery) {
		this.addNamedNativeQuery(this.namedNativeQueries.size(), namedNativeQuery);
	}
	
	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}
	
	public void removeNamedNativeQuery(int index) {
		OrmNamedNativeQuery namedNativeQuery = this.namedNativeQueries.remove(index);
		this.resourceTypeMapping.getNamedNativeQueries().remove(index);
		fireItemRemoved(NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
	}

	protected void removeNamedNativeQuery_(OrmNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
		
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex);
		this.resourceTypeMapping.getNamedNativeQueries().move(targetIndex, sourceIndex);
		fireItemMoved(NAMED_NATIVE_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<OrmQuery> queries() {
		return new CompositeIterator<OrmQuery>(this.namedQueries(), this.namedNativeQueries());
	}

	public String getIdClass() {
		return this.idClass;
	}
	
	public void setIdClass(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		if (oldIdClass != newIdClass) {
			if (this.getResourceIdClass() != null) {
				this.getResourceIdClass().setClassName(newIdClass);						
				if (this.getResourceIdClass().isAllFeaturesUnset()) {
					removeResourceIdClass();
				}
			}
			else if (newIdClass != null) {
				addResourceIdClass();
				getResourceIdClass().setClassName(newIdClass);
			}
		}
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}

	protected XmlIdClass getResourceIdClass() {
		return this.resourceTypeMapping.getIdClass();
	}
	
	protected void addResourceIdClass() {
		this.resourceTypeMapping.setIdClass(OrmFactory.eINSTANCE.createXmlIdClass());		
	}
	
	protected void removeResourceIdClass() {
		this.resourceTypeMapping.setIdClass(null);
	}

	public Entity getParentEntity() {
		for (Iterator<PersistentType> stream = getPersistentType().ancestors(); stream.hasNext();) {
			TypeMapping tm = stream.next().getMapping();
			if (tm instanceof Entity) {
				return (Entity) tm;
			}
		}
		return this;
	}

	public Entity getRootEntity() {
		Entity rootEntity = null;
		for (Iterator<PersistentType> stream = getPersistentType().inheritanceHierarchy(); stream.hasNext();) {
			PersistentType persistentType = stream.next();
			if (persistentType.getMapping() instanceof Entity) {
				rootEntity = (Entity) persistentType.getMapping();
			}
		}
		return rootEntity;
	}

	public String getDefaultTableName() {
		JavaEntity javaEntity = this.getJavaEntity();
		if (javaEntity != null) {
			JavaTable javaTable = javaEntity.getTable();
			if ( ! this.isMetadataComplete()
					&& ! this.table.hasSpecifiedResourceTable()
					&& javaTable.getSpecifiedName() != null) {
				return javaTable.getSpecifiedName();
			}
		}

		return this.isSingleTableDescendant() ?
						this.getRootEntity().getTable().getName()
					:
						this.getName();
	}

	public String getDefaultSchema() {
		JavaEntity javaEntity = this.getJavaEntity();
		if (javaEntity != null) {
			if (this.isMetadataComplete() || this.table.hasSpecifiedResourceTable()) {
				return javaEntity.getTable().getDefaultSchema();
			}
			return javaEntity.getTable().getSchema();
		}

		return this.isSingleTableDescendant() ?
						this.getRootEntity().getTable().getSchema()
					:
						this.getContextDefaultSchema();
	}

	public String getDefaultCatalog() {
		JavaEntity javaEntity = this.getJavaEntity();
		if (javaEntity != null) {
			if (this.isMetadataComplete() || this.table.hasSpecifiedResourceTable()) {
				return javaEntity.getTable().getDefaultCatalog();
			}
			return javaEntity.getTable().getCatalog();
		}

		return this.isSingleTableDescendant() ?
						this.getRootEntity().getTable().getCatalog()
					:
						this.getContextDefaultCatalog();
	}

	/**
	 * Return whether the entity is a descendant of the root entity
	 * of a "single table" inheritance hierarchy.
	 */
	protected boolean isSingleTableDescendant() {
		return this.isDescendant() && (this.getInheritanceStrategy() == InheritanceType.SINGLE_TABLE);
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


	public int getXmlSequence() {
		return 1;
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
	public Iterator<PersistentAttribute> allOverridableAttributes() {
		return new CompositeIterator<PersistentAttribute>(new TransformationIterator<TypeMapping, Iterator<PersistentAttribute>>(this.inheritanceHierarchy()) {
			@Override
			protected Iterator<PersistentAttribute> transform(TypeMapping mapping) {
				return mapping.overridableAttributes();
			}
		});
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
	public void initialize() {
		super.initialize();
		this.specifiedName = this.resourceTypeMapping.getName();
		this.defaultName = this.buildDefaultName();
		this.initializeInheritance(this.getResourceInheritance());
		this.discriminatorColumn.initialize(this.resourceTypeMapping);
		this.specifiedDiscriminatorValue = this.resourceTypeMapping.getDiscriminatorValue();
		this.defaultDiscriminatorValue = this.defaultDiscriminatorValue();
		this.discriminatorValueAllowed = this.discriminatorValueIsAllowed();
		this.table.initialize(this.resourceTypeMapping);
		this.initializeSpecifiedSecondaryTables();
		this.initializeVirtualSecondaryTables();
		this.initializeSequenceGenerator();
		this.initializeTableGenerator();
		this.initializeSpecifiedPrimaryKeyJoinColumns();
		this.initializeDefaultPrimaryKeyJoinColumns();
		this.initializeSpecifiedAttributeOverrides();
		this.initializeVirtualAttributeOverrides();
		this.initializeSpecifiedAssociationOverrides();
		this.initializeNamedQueries();
		this.initializeNamedNativeQueries();
		this.initializeIdClass(this.getResourceIdClass());
	}
	
	protected void initializeInheritance(Inheritance inheritanceResource) {
		this.specifiedInheritanceStrategy = this.specifiedInheritanceStrategy(inheritanceResource);
		this.defaultInheritanceStrategy = this.defaultInheritanceStrategy();
	}

	protected void initializeSpecifiedSecondaryTables() {
		for (XmlSecondaryTable secondaryTable : this.resourceTypeMapping.getSecondaryTables()) {
			this.specifiedSecondaryTables.add(buildSecondaryTable(secondaryTable));
		}
	}
	
	protected void initializeVirtualSecondaryTables() {
		if (isMetadataComplete()) {
			return;
		}
		if (getJavaEntity() == null) {
			return;
		}
		if (specifiedSecondaryTablesSize() > 0) {
			return;
		}
		ListIterator<JavaSecondaryTable> javaSecondaryTables = getJavaEntity().secondaryTables();
		while(javaSecondaryTables.hasNext()) {
			JavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
			if (javaSecondaryTable.getName() != null) {
				this.virtualSecondaryTables.add(buildVirtualSecondaryTable(javaSecondaryTable));
			}
		}
	}	
	
	protected void initializeDefaultPrimaryKeyJoinColumns() {
		if (isMetadataComplete()) {
			return;
		}
		if (getJavaEntity() == null) {
			this.defaultPrimaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn(null));
			return;
		}
		if (specifiedPrimaryKeyJoinColumnsSize() > 0) {
			return;
		}
		ListIterator<JavaPrimaryKeyJoinColumn> javaPkJoinColumns = getJavaEntity().primaryKeyJoinColumns();
		while(javaPkJoinColumns.hasNext()) {
			JavaPrimaryKeyJoinColumn javaPkJoinColumn = javaPkJoinColumns.next();
			if (javaPkJoinColumn.getName() != null) {
				this.defaultPrimaryKeyJoinColumns.add(buildVirtualPrimaryKeyJoinColumn(javaPkJoinColumn));
			}
		}
	}

	protected void initializeTableGenerator() {
		if (this.resourceTypeMapping.getTableGenerator() != null) {
			this.tableGenerator = buildTableGenerator(this.resourceTypeMapping.getTableGenerator());
		}
	}
	
	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator resourceTableGenerator) {
		return getJpaFactory().buildOrmTableGenerator(this, resourceTableGenerator);
	}

	protected void initializeSequenceGenerator() {
		if (this.resourceTypeMapping.getSequenceGenerator() != null) {
			this.sequenceGenerator = buildSequenceGenerator(this.resourceTypeMapping.getSequenceGenerator());
		}
	}
	
	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator resourceSequenceGenerator) {
		return getJpaFactory().buildOrmSequenceGenerator(this, resourceSequenceGenerator);
	}

	protected void initializeSpecifiedPrimaryKeyJoinColumns() {
		for (XmlPrimaryKeyJoinColumn resourcePkJoinColumn : this.resourceTypeMapping.getPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn(resourcePkJoinColumn));
		}
	}
	
	protected void initializeSpecifiedAttributeOverrides() {
		for (XmlAttributeOverride attributeOverride : this.resourceTypeMapping.getAttributeOverrides()) {
			this.specifiedAttributeOverrides.add(buildAttributeOverride(attributeOverride));
		}
	}
	
	protected void initializeVirtualAttributeOverrides() {
		for (PersistentAttribute persistentAttribute : CollectionTools.iterable(allOverridableAttributes())) {
			OrmAttributeOverride attributeOverride = getAttributeOverrideNamed(persistentAttribute.getName());
			if (attributeOverride == null) {
				JavaAttributeOverride javaAttributeOverride = null;
				if (getJavaEntity() != null) {
					javaAttributeOverride = getJavaEntity().getAttributeOverrideNamed(persistentAttribute.getName());
				}
				this.virtualAttributeOverrides.add(buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride));
			}
		}
	}

	protected OrmAttributeOverride buildVirtualAttributeOverride(PersistentAttribute persistentAttribute, JavaAttributeOverride javaAttributeOverride) {
		return buildAttributeOverride(buildVirtualXmlAttributeOverride(persistentAttribute, javaAttributeOverride));
	}
	
	protected XmlAttributeOverride buildVirtualXmlAttributeOverride(PersistentAttribute persistentAttribute, JavaAttributeOverride javaAttributeOverride) {
		XmlColumn xmlColumn;
		if (javaAttributeOverride == null) {
			ColumnMapping columnMapping = (ColumnMapping) persistentAttribute.getMapping();
			xmlColumn = new VirtualXmlColumn(this, columnMapping.getColumn());		
		}
		else {
			xmlColumn = new VirtualXmlColumn(this, javaAttributeOverride.getColumn());
		}
		return new VirtualXmlAttributeOverride(persistentAttribute.getName(), xmlColumn);
	}
	
	protected void initializeSpecifiedAssociationOverrides() {
		for (XmlAssociationOverride associationOverride : this.resourceTypeMapping.getAssociationOverrides()) {
			this.specifiedAssociationOverrides.add(buildAssociationOverride(associationOverride));
		}
	}
	
	protected void initializeNamedQueries() {
		for (XmlNamedQuery namedQuery : this.resourceTypeMapping.getNamedQueries()) {
			this.namedQueries.add(buildNamedQuery(namedQuery));
		}
	}
	
	protected void initializeNamedNativeQueries() {
		for (XmlNamedNativeQuery namedNativeQuery : this.resourceTypeMapping.getNamedNativeQueries()) {
			this.namedNativeQueries.add(buildNamedNativeQuery(namedNativeQuery));
		}
	}
	
	protected void initializeIdClass(XmlIdClass idClassResource) {
		this.idClass = this.idClass(idClassResource);	
	}

	protected String idClass(XmlIdClass idClassResource) {
		return idClassResource == null ? null : idClassResource.getClassName();
	}

	@Override
	public void update() {
		super.update();
		this.setSpecifiedName(this.resourceTypeMapping.getName());
		this.setDefaultName(this.buildDefaultName());
		this.updateInheritance(this.getResourceInheritance());
		this.discriminatorColumn.update(this.resourceTypeMapping);
		this.setSpecifiedDiscriminatorValue(this.resourceTypeMapping.getDiscriminatorValue());
		this.setDefaultDiscriminatorValue(defaultDiscriminatorValue());
		this.setDiscriminatorValueAllowed(this.discriminatorValueIsAllowed());
		this.table.update(this.resourceTypeMapping);
		this.updateSpecifiedSecondaryTables();
		this.updateVirtualSecondaryTables();
		this.updateSequenceGenerator();
		this.updateTableGenerator();
		this.updateSpecifiedPrimaryKeyJoinColumns();
		this.updateDefaultPrimaryKeyJoinColumns();
		this.updateSpecifiedAttributeOverrides();
		this.updateVirtualAttributeOverrides();
		this.updateSpecifiedAssociationOverrides();
		this.updateNamedQueries();
		this.updateNamedNativeQueries();
		this.updateIdClass(this.getResourceIdClass());
	}

	protected String buildDefaultName() {
		if (!isMetadataComplete()) {
			JavaEntity javaEntity = getJavaEntity();
			if (javaEntity != null) {
				return javaEntity.getName();
			}
		}
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
	
	protected JavaResourcePersistentType getJavaResourcePersistentType() {
		if (getPersistentType().getJavaPersistentType() != null) {
			return getPersistentType().getJavaPersistentType().getResourcePersistentType();
		}
		return null;
	}
	
	protected boolean discriminatorValueIsAllowed() {
		JavaResourcePersistentType javaResourcePersistentType = getJavaResourcePersistentType();
		return javaResourcePersistentType == null ? false : !javaResourcePersistentType.isAbstract();
	}

	protected void updateInheritance(Inheritance inheritanceResource) {
		this.setSpecifiedInheritanceStrategy_(this.specifiedInheritanceStrategy(inheritanceResource));
		this.setDefaultInheritanceStrategy(this.defaultInheritanceStrategy());
	}
	
	protected void updateSpecifiedSecondaryTables() {
		// make a copy of the XML tables (to prevent ConcurrentModificationException)
		Iterator<XmlSecondaryTable> xmlTables = new CloneIterator<XmlSecondaryTable>(this.resourceTypeMapping.getSecondaryTables());
		
		for (Iterator<OrmSecondaryTable> contextTables = this.specifiedSecondaryTables(); contextTables.hasNext(); ) {
			OrmSecondaryTable contextTable = contextTables.next();
			if (xmlTables.hasNext()) {
				contextTable.update(xmlTables.next());
			}
			else {
				removeSpecifiedSecondaryTable_(contextTable);
			}
		}
		
		while (xmlTables.hasNext()) {
			addSpecifiedSecondaryTable(buildSecondaryTable(xmlTables.next()));
		}
	}
	
	//if any secondary-tables are specified in the xml file, then all of the java secondaryTables are overriden
	protected void updateVirtualSecondaryTables() {
		ListIterator<OrmSecondaryTable> secondaryTables = virtualSecondaryTables();
		ListIterator<JavaSecondaryTable> javaSecondaryTables = EmptyListIterator.instance();
		
		if (getJavaEntity() != null && !isMetadataComplete() && specifiedSecondaryTablesSize() == 0) {
			javaSecondaryTables = getJavaEntity().secondaryTables();
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
		return getJpaFactory().buildOrmSecondaryTable(this, xmlSecondaryTable);
	}
	
	protected OrmSecondaryTable buildVirtualSecondaryTable(JavaSecondaryTable javaSecondaryTable) {
		return buildSecondaryTable(new VirtualXmlSecondaryTable(javaSecondaryTable));
	}
	
	protected void updateTableGenerator() {
		if (this.resourceTypeMapping.getTableGenerator() == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(buildTableGenerator(this.resourceTypeMapping.getTableGenerator()));
			}
			else {
				getTableGenerator().update(this.resourceTypeMapping.getTableGenerator());
			}
		}
	}
	
	protected void updateSequenceGenerator() {
		if (this.resourceTypeMapping.getSequenceGenerator() == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(buildSequenceGenerator(this.resourceTypeMapping.getSequenceGenerator()));
			}
			else {
				getSequenceGenerator().update(this.resourceTypeMapping.getSequenceGenerator());
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
		if ((this.getResourceInheritance() == null)
				&& ! this.isMetadataComplete()
				&& (this.getJavaEntity() != null)) {
			return this.getJavaEntity().getInheritanceStrategy();
		}
		return this.isRoot() ? InheritanceType.SINGLE_TABLE : this.getRootEntity().getInheritanceStrategy();
	}
	
	protected void updateSpecifiedPrimaryKeyJoinColumns() {
		// make a copy of the XML join columns (to prevent ConcurrentModificationException)
		Iterator<XmlPrimaryKeyJoinColumn> xmlPkJoinColumns = new CloneIterator<XmlPrimaryKeyJoinColumn>(this.resourceTypeMapping.getPrimaryKeyJoinColumns());
		
		for (Iterator<OrmPrimaryKeyJoinColumn> contextPkJoinColumns = this.specifiedPrimaryKeyJoinColumns(); contextPkJoinColumns.hasNext(); ) {
			OrmPrimaryKeyJoinColumn contextPkJoinColumn = contextPkJoinColumns.next();
			if (xmlPkJoinColumns.hasNext()) {
				contextPkJoinColumn.update(xmlPkJoinColumns.next());
			}
			else {
				removeSpecifiedPrimaryKeyJoinColumn_(contextPkJoinColumn);
			}
		}
		
		while (xmlPkJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(xmlPkJoinColumns.next()));
		}
	}
	
	//if there are any specified pkJoinColumns, then no default pkJoinColumns
	//if the java has specified pkJoinColumns, then those are the default pkJoinColumns
	//otherwise, just 1 pkJoinColumn, defaults being null if multiple primaryKey columns
	protected void updateDefaultPrimaryKeyJoinColumns() {
		ListIterator<OrmPrimaryKeyJoinColumn> defaultPkJoinColumns = defaultPrimaryKeyJoinColumns();
		ListIterator<JavaPrimaryKeyJoinColumn> javaPkJoinColumns = EmptyListIterator.instance();
		
		if (getJavaEntity() != null && !isMetadataComplete() && specifiedPrimaryKeyJoinColumnsSize() == 0) {
			javaPkJoinColumns = getJavaEntity().primaryKeyJoinColumns();
		}
		while (defaultPkJoinColumns.hasNext()) {
			OrmPrimaryKeyJoinColumn defaultPkJoinColumn = defaultPkJoinColumns.next();
			if (javaPkJoinColumns.hasNext()) {
				JavaPrimaryKeyJoinColumn javaPkJoinColumn = javaPkJoinColumns.next();
				defaultPkJoinColumn.update(new VirtualXmlPrimaryKeyJoinColumn(javaPkJoinColumn));
			}
			else {
				if (defaultPrimaryKeyJoinColumnsSize() == 1) {
					defaultPkJoinColumn.update(null);
				}
				else {
					removeDefaultPrimaryKeyJoinColumn(defaultPkJoinColumn);
				}
			}
		}
		
		while (javaPkJoinColumns.hasNext()) {
			JavaPrimaryKeyJoinColumn javaPkJoinColumn = javaPkJoinColumns.next();
			addDefaultPrimaryKeyJoinColumn(buildVirtualPrimaryKeyJoinColumn(javaPkJoinColumn));
		}
		
		if (defaultPrimaryKeyJoinColumnsSize() == 0 && specifiedPrimaryKeyJoinColumnsSize() == 0) {
			addDefaultPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(null));
		}
	}
	
	protected OrmPrimaryKeyJoinColumn buildVirtualPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn javaSecondaryTable) {
		return buildPrimaryKeyJoinColumn(new VirtualXmlPrimaryKeyJoinColumn(javaSecondaryTable));
	}
	
	protected OrmPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn resourcePkJoinColumn) {
		return getJpaFactory().buildOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner(), resourcePkJoinColumn);
	}

	protected void updateSpecifiedAttributeOverrides() {
		// make a copy of the XML overrides (to prevent ConcurrentModificationException)
		Iterator<XmlAttributeOverride> xmlOverrides = new CloneIterator<XmlAttributeOverride>(this.resourceTypeMapping.getAttributeOverrides());
		
		for (Iterator<OrmAttributeOverride> contextOverrides = this.specifiedAttributeOverrides(); contextOverrides.hasNext(); ) {
			OrmAttributeOverride contextOverride = contextOverrides.next();
			if (xmlOverrides.hasNext()) {
				contextOverride.update(xmlOverrides.next());
			}
			else {
				removeSpecifiedAttributeOverride_(contextOverride);
			}
		}
		
		while (xmlOverrides.hasNext()) {
			addSpecifiedAttributeOverride(buildAttributeOverride(xmlOverrides.next()));
		}
	}
	
	protected void updateVirtualAttributeOverrides() {
		Iterator<PersistentAttribute> overridableAttributes = allOverridableAttributes();
		ListIterator<OrmAttributeOverride> virtualAttributeOverridesCopy = virtualAttributeOverrides();
		
		for (PersistentAttribute persistentAttribute : CollectionTools.iterable(overridableAttributes)) {
			OrmAttributeOverride ormAttributeOverride = getAttributeOverrideNamed(persistentAttribute.getName());
			if (ormAttributeOverride != null && !ormAttributeOverride.isVirtual()) {
				continue;
			}
			JavaAttributeOverride javaAttributeOverride = null;
			if (getJavaEntity() != null) {
				javaAttributeOverride = getJavaEntity().getAttributeOverrideNamed(persistentAttribute.getName());
			}
			if (ormAttributeOverride != null) {
				if (virtualAttributeOverridesCopy.hasNext()) {
					OrmAttributeOverride virtualAttributeOverride = virtualAttributeOverridesCopy.next();
					virtualAttributeOverride.update(buildVirtualXmlAttributeOverride(persistentAttribute, javaAttributeOverride));
				}
				else {
					addVirtualAttributeOverride(buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride));
				}
			}
			else {
				addVirtualAttributeOverride(buildVirtualAttributeOverride(persistentAttribute, javaAttributeOverride));
			}
		}
		for (OrmAttributeOverride virtualAttributeOverride : CollectionTools.iterable(virtualAttributeOverridesCopy)) {
			removeVirtualAttributeOverride(virtualAttributeOverride);
		}
	}
	
	protected OrmAttributeOverride buildAttributeOverride(XmlAttributeOverride attributeOverride) {
		return getJpaFactory().buildOrmAttributeOverride(this, createAttributeOverrideOwner(), attributeOverride);
	}

	protected AttributeOverride.Owner createAttributeOverrideOwner() {
		return new AttributeOverrideOwner();
	}

	protected void updateSpecifiedAssociationOverrides() {
		// make a copy of the XML overrides (to prevent ConcurrentModificationException)
		Iterator<XmlAssociationOverride> xmlOverrides = new CloneIterator<XmlAssociationOverride>(this.resourceTypeMapping.getAssociationOverrides());
		
		for (Iterator<OrmAssociationOverride> contextOverrides = this.specifiedAssociationOverrides(); contextOverrides.hasNext(); ) {
			OrmAssociationOverride contextOverride = contextOverrides.next();
			if (xmlOverrides.hasNext()) {
				contextOverride.update(xmlOverrides.next());
			}
			else {
				removeSpecifiedAssociationOverride_(contextOverride);
			}
		}
		
		while (xmlOverrides.hasNext()) {
			addSpecifiedAssociationOverride(buildAssociationOverride(xmlOverrides.next()));
		}
	}
	
	protected OrmAssociationOverride buildAssociationOverride(XmlAssociationOverride associationOverride) {
		return getJpaFactory().buildOrmAssociationOverride(this, createAssociationOverrideOwner(), associationOverride);
	}

	protected AssociationOverride.Owner createAssociationOverrideOwner() {
		return new AssociationOverrideOwner();
	}
	
	protected void updateNamedQueries() {
		// make a copy of the XML queries (to prevent ConcurrentModificationException)
		Iterator<XmlNamedQuery> xmlQueries = new CloneIterator<XmlNamedQuery>(this.resourceTypeMapping.getNamedQueries());
		
		for (Iterator<OrmNamedQuery> contextQueries = this.namedQueries(); contextQueries.hasNext(); ) {
			OrmNamedQuery contextQuery = contextQueries.next();
			if (xmlQueries.hasNext()) {
				contextQuery.update(xmlQueries.next());
			}
			else {
				removeNamedQuery_(contextQuery);
			}
		}
		
		while (xmlQueries.hasNext()) {
			addNamedQuery(buildNamedQuery(xmlQueries.next()));
		}
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery resourceNamedQuery) {
		return getJpaFactory().buildOrmNamedQuery(this, resourceNamedQuery);
	}

	protected void updateNamedNativeQueries() {
		// make a copy of the XML queries (to prevent ConcurrentModificationException)
		Iterator<XmlNamedNativeQuery> xmlQueries = new CloneIterator<XmlNamedNativeQuery>(this.resourceTypeMapping.getNamedNativeQueries());
		
		for (Iterator<OrmNamedNativeQuery> contextQueries = this.namedNativeQueries(); contextQueries.hasNext(); ) {
			OrmNamedNativeQuery contextQuery = contextQueries.next();
			if (xmlQueries.hasNext()) {
				contextQuery.update(xmlQueries.next());
			}
			else {
				removeNamedNativeQuery_(contextQuery);
			}
		}
		
		while (xmlQueries.hasNext()) {
			addNamedNativeQuery(buildNamedNativeQuery(xmlQueries.next()));
		}
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery resourceNamedNativeQuery) {
		return getJpaFactory().buildOrmNamedNativeQuery(this, resourceNamedNativeQuery);
	}
	
	protected void updateIdClass(XmlIdClass idClassResource) {
		this.setIdClass_(this.idClass(idClassResource));
	}
	
	
	// *************************************************************************
	
	public String getPrimaryKeyColumnName() {
		return getPrimaryKeyColumnName(getPersistentType().allAttributes());
	}
	
	//copied in GenericJavaEntity to avoid an API change for fixing bug 229423 in RC1
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
	
	public void removeFromResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getEntities().remove(this.resourceTypeMapping);
	}
	
	public XmlEntity addToResourceModel(XmlEntityMappings entityMappings) {
		XmlEntity entity = OrmFactory.eINSTANCE.createXmlEntity();
		getPersistentType().initialize(entity);
		entityMappings.getEntities().add(entity);
		return entity;
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);

		this.table.validate(messages);	
		this.validateId(messages);
		this.validateGenerators(messages);
		this.validateQueries(messages);

		for (Iterator<OrmSecondaryTable> stream = this.secondaryTables(); stream.hasNext(); ) {
			stream.next().validate(messages);
		}

		for (Iterator<OrmAttributeOverride> stream = this.attributeOverrides(); stream.hasNext(); ) {
			stream.next().validate(messages);
		}

		for (Iterator<OrmAssociationOverride> stream = this.associationOverrides(); stream.hasNext(); ) {
			stream.next().validate(messages);
		}
	}
	
	protected void validateId(List<IMessage> messages) {
		if (this.entityHasNoId()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ENTITY_NO_ID,
					new String[] {this.getName()},
					this, 
					this.getValidationTextRange()
				)
			);
		}
	}
	
	private boolean entityHasNoId() {
		return ! this.entityHasId();
	}
	
	private boolean entityHasId() {
		for (Iterator<PersistentAttribute> stream = this.getPersistentType().allAttributes(); stream.hasNext(); ) {
			if (stream.next().isIdAttribute()) {
				return true;
			}
		}
		return false;
	}
	
	protected void validateGenerators(List<IMessage> messages) {
		for (Iterator<OrmGenerator> localGenerators = this.generators(); localGenerators.hasNext(); ) {
			OrmGenerator localGenerator = localGenerators.next();
			for (Iterator<Generator> globalGenerators = this.getPersistenceUnit().generators(); globalGenerators.hasNext(); ) {
				if (localGenerator.duplicates(globalGenerators.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {localGenerator.getName()},
							localGenerator,
							localGenerator.getNameTextRange()
						)
					);
				}
			}
		}
	}
	
	protected void validateQueries(List<IMessage> messages) {
		for (Iterator<OrmQuery> localQueries = this.queries(); localQueries.hasNext(); ) {
			OrmQuery localQuery = localQueries.next();
			for (Iterator<Query> globalQueries = this.getPersistenceUnit().queries(); globalQueries.hasNext(); ) {
				if (localQuery.duplicates(globalQueries.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.QUERY_DUPLICATE_NAME,
							new String[] {localQuery.getName()},
							localQuery,
							localQuery.getNameTextRange())
					);
				}
			}
		}
	}
	
	
	class PrimaryKeyJoinColumnOwner implements OrmBaseJoinColumn.Owner
	{
		public TypeMapping getTypeMapping() {
			return GenericOrmEntity.this;
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return GenericOrmEntity.this.getDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			Entity parentEntity = GenericOrmEntity.this.getParentEntity();
			return (parentEntity == null) ? null : parentEntity.getPrimaryDbTable();
		}

		public int joinColumnsSize() {
			return GenericOrmEntity.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericOrmEntity.this.defaultPrimaryKeyJoinColumns.contains(joinColumn);
		}
		
		public String getDefaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return GenericOrmEntity.this.getParentEntity().getPrimaryKeyColumnName();
		}
		
		public TextRange getValidationTextRange() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
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
			return GenericOrmEntity.this.virtualAttributeOverrides.contains(override);
		}

		public BaseOverride setVirtual(boolean virtual, BaseOverride override) {
			return GenericOrmEntity.this.setAttributeOverrideVirtual(virtual, (OrmAttributeOverride) override);
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmEntity.this;
		}
		
	}

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
			return GenericOrmEntity.this.virtualAssociationOverrides.contains(override);
		}

		public BaseOverride setVirtual(boolean virtual, BaseOverride override) {
			// TODO Auto-generated method stub
			return null;
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmEntity.this;
		}
	}
}
