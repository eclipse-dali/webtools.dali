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
import org.eclipse.jpt.core.JpaValidation.Supported;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
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
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmTable;
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
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmEntity
	extends AbstractOrmTypeMapping<XmlEntity>
	implements OrmEntity
{
	protected String specifiedName;

	protected String defaultName;

	protected String idClass;

	protected final OrmTable table;

	protected boolean specifiedTableIsAllowed;
	
	protected boolean tableIsUndefined;

	protected final List<OrmSecondaryTable> specifiedSecondaryTables;
	
	protected final List<OrmSecondaryTable> virtualSecondaryTables;
	
	protected final List<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
	
	protected final List<OrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	protected InheritanceType specifiedInheritanceStrategy;
	
	protected InheritanceType defaultInheritanceStrategy;

	protected String defaultDiscriminatorValue;

	protected String specifiedDiscriminatorValue;

	protected boolean specifiedDiscriminatorValueIsAllowed;

	protected boolean discriminatorValueIsUndefined;

	protected final OrmDiscriminatorColumn discriminatorColumn;

	protected boolean specifiedDiscriminatorColumnIsAllowed;
	
	protected boolean discriminatorColumnIsUndefined;

	protected final OrmGeneratorContainer generatorContainer;

	protected final List<OrmAttributeOverride> specifiedAttributeOverrides;
	
	protected final List<OrmAttributeOverride> virtualAttributeOverrides;

	protected final List<OrmAssociationOverride> specifiedAssociationOverrides;

	protected final List<OrmAssociationOverride> virtualAssociationOverrides;

	protected final OrmQueryContainer queryContainer;
	
	protected Entity rootEntity;

	protected AbstractOrmEntity(OrmPersistentType parent, XmlEntity resourceMapping) {
		super(parent, resourceMapping);
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
		this.queryContainer = getJpaFactory().buildOrmQueryContainer(this, resourceMapping);
		this.generatorContainer = getJpaFactory().buildOrmGeneratorContainer(parent, resourceMapping);
		this.specifiedName = this.resourceTypeMapping.getName();
		this.defaultName = this.buildDefaultName();
		this.rootEntity = this.calculateRootEntity();
		this.initializeInheritance(this.getResourceInheritance());
		this.specifiedDiscriminatorColumnIsAllowed = this.buildSpecifiedDiscriminatorColumnIsAllowed();
		this.discriminatorColumnIsUndefined = this.buildDiscriminatorColumnIsUndefined();
		this.discriminatorColumn.initialize(this.resourceTypeMapping); //TODO pass in to constructor
		this.specifiedDiscriminatorValueIsAllowed = this.buildSpecifiedDiscriminatorValueIsAllowed();
		this.discriminatorValueIsUndefined = this.buildDiscriminatorValueIsUndefined();
		this.specifiedDiscriminatorValue = this.resourceTypeMapping.getDiscriminatorValue();
		this.defaultDiscriminatorValue = this.buildDefaultDiscriminatorValue();
		this.specifiedTableIsAllowed = this.buildSpecifiedTableIsAllowed();
		this.tableIsUndefined = this.buildTableIsUndefined();
		this.table.initialize(this.resourceTypeMapping);//TODO pass in to constructor
		this.initializeSpecifiedSecondaryTables();
		this.initializeVirtualSecondaryTables();
		this.initializeSpecifiedPrimaryKeyJoinColumns();
		this.initializeDefaultPrimaryKeyJoinColumns();
		this.initializeSpecifiedAttributeOverrides();
		this.initializeSpecifiedAssociationOverrides();
		this.initializeIdClass(this.getResourceIdClass());
	}
	
	protected OrmDiscriminatorColumn buildDiscriminatorColumn() {
		return getJpaFactory().buildOrmDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected OrmDiscriminatorColumn.Owner buildDiscriminatorColumnOwner() {
		return new OrmDiscriminatorColumn.Owner(){
			public org.eclipse.jpt.db.Table getDbTable(String tableName) {
				return AbstractOrmEntity.this.getDbTable(tableName);
			}

			public TypeMapping getTypeMapping() {
				return AbstractOrmEntity.this;
			}
			
			public String getDefaultColumnName() {
				if (getResourceTypeMapping().getDiscriminatorColumn() == null) {
					if (!isMetadataComplete()) {
						if (getJavaEntity() != null && getJavaEntity().getDiscriminatorColumn().getSpecifiedName() != null) {
							return getJavaEntity().getDiscriminatorColumn().getSpecifiedName();
						}
					}
				}
				return isDescendant() ?
						getRootEntity().getDiscriminatorColumn().getName()
					:
						isTablePerClass() ? 
							null
						:
							DiscriminatorColumn.DEFAULT_NAME;
			}
			
			public int getDefaultLength() {
				if (getResourceTypeMapping().getDiscriminatorColumn() == null) {
					if (!isMetadataComplete()) {
						if (getJavaEntity() != null && getJavaEntity().getDiscriminatorColumn().getSpecifiedLength() != null) {
							return getJavaEntity().getDiscriminatorColumn().getSpecifiedLength().intValue();
						}
					}
				}
				return isDescendant() ?
					getRootEntity().getDiscriminatorColumn().getLength()
				:
					isTablePerClass() ? 
						0//TODO think i want to return null here
					:
						DiscriminatorColumn.DEFAULT_LENGTH;
			}
			
			public DiscriminatorType getDefaultDiscriminatorType() {
				if (getResourceTypeMapping().getDiscriminatorColumn() == null) {
					if (!isMetadataComplete()) {
						if (getJavaEntity() != null && getJavaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType() != null) {
							return getJavaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType();
						}
					}
				}
				return isDescendant() ?
					getRootEntity().getDiscriminatorColumn().getDiscriminatorType()
				:
					isTablePerClass() ? 
						null
					:
						DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
			}
			
			public TextRange getValidationTextRange() {
				return AbstractOrmEntity.this.getValidationTextRange();
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
		XmlSecondaryTable secondaryTableResource = OrmFactory.eINSTANCE.createXmlSecondaryTable();
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
				XmlSecondaryTable secondaryTableResource = OrmFactory.eINSTANCE.createXmlSecondaryTable();
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
	
	@Override
	public boolean shouldValidateAgainstDatabase() {
		return super.shouldValidateAgainstDatabase() && ! isAbstractTablePerClass();
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
				if (this.getResourceInheritance().isUnset()) {
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

	public OrmGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
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
		firePropertyChanged(Entity.DISCRIMINATOR_VALUE_IS_UNDEFINED_PROPERTY, old, discriminatorValueIsUndefined);
	}
	
	public boolean specifiedDiscriminatorColumnIsAllowed() {
		return this.specifiedDiscriminatorColumnIsAllowed;
	}
	
	protected void setSpecifiedDiscriminatorColumnIsAllowed(boolean specifiedDiscriminatorColumnIsAllowed) {
		boolean old = this.specifiedDiscriminatorColumnIsAllowed;
		this.specifiedDiscriminatorColumnIsAllowed = specifiedDiscriminatorColumnIsAllowed;
		firePropertyChanged(Entity.SPECIFIED_DISCRIMINATOR_COLUMN_IS_ALLOWED_PROPERTY, old, specifiedDiscriminatorColumnIsAllowed);
	}
	
	public boolean discriminatorColumnIsUndefined() {
		return this.discriminatorColumnIsUndefined;
	}
	
	protected void setDiscriminatorColumnIsUndefined(boolean discriminatorColumnIsUndefined) {
		boolean old = this.discriminatorColumnIsUndefined;
		this.discriminatorColumnIsUndefined = discriminatorColumnIsUndefined;
		firePropertyChanged(Entity.DISCRIMINATOR_COLUMN_IS_UNDEFINED_PROPERTY, old, discriminatorColumnIsUndefined);
	}

	
	public boolean specifiedTableIsAllowed() {
		return this.specifiedTableIsAllowed;
	}
	
	protected void setSpecifiedTableIsAllowed(boolean specifiedTableIsAllowed) {
		boolean old = this.specifiedTableIsAllowed;
		this.specifiedTableIsAllowed = specifiedTableIsAllowed;
		firePropertyChanged(Entity.SPECIFIED_TABLE_IS_ALLOWED_PROPERTY, old, specifiedTableIsAllowed);
	}
	
	public boolean tableIsUndefined() {
		return this.tableIsUndefined;
	}
	
	protected void setTableIsUndefined(boolean tableIsUndefined) {
		boolean old = this.tableIsUndefined;
		this.tableIsUndefined = tableIsUndefined;
		firePropertyChanged(Entity.TABLE_IS_UNDEFINED_PROPERTY, old, tableIsUndefined);
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
		XmlPrimaryKeyJoinColumn resourcePkJoinColumn = OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn();
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
		XmlAttributeOverride xmlAttributeOverride = OrmFactory.eINSTANCE.createXmlAttributeOverride();
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

	public OrmQueryContainer getQueryContainer() {
		return this.queryContainer;
	}
	
	public char getIdClassEnclosingTypeSeparator() {
		return '$';
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
				if (this.getResourceIdClass().isUnset()) {
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

	/**
	 * Return the ultimate top of the inheritance hierarchy 
	 * This method should never return null. The root
	 * is defined as the persistent type in the inheritance hierarchy
	 * that has no parent.  The root should be an entity
	 *  
	 * Non-entities in the hierarchy should be ignored, ie skip
	 * over them in the search for the root. 
	 */
	protected Entity getRootEntity() {
		return this.rootEntity;
	}

	public String getDefaultTableName() {
		JavaEntity javaEntity = this.getJavaEntity();
		if (javaEntity != null) {
			JavaTable javaTable = javaEntity.getTable();
			if ( ! this.isMetadataComplete()
					&& ! this.table.isResourceSpecified()
					&& javaTable.getSpecifiedName() != null) {
				return javaTable.getSpecifiedName();
			}
		}
		return this.isSingleTableDescendant() ?
						this.getRootEntity().getTable().getName()
					:
						this.isAbstractTablePerClass() ?
								null
							:
								this.getName();
	}

	public String getDefaultSchema() {
		JavaEntity javaEntity = this.getJavaEntity();
		if (javaEntity != null) {
			JavaTable javaTable = javaEntity.getTable();
			if ( ! this.isMetadataComplete()
					&& ! this.table.isResourceSpecified()
					&& javaTable.getSpecifiedSchema() != null) {
				return javaTable.getSpecifiedSchema();
			}
		}

		return this.isSingleTableDescendant() ?
						this.getRootEntity().getTable().getSchema()
					:
						this.isAbstractTablePerClass() ?
								null
							:
								this.getContextDefaultSchema();
	}

	public String getDefaultCatalog() {
		JavaEntity javaEntity = this.getJavaEntity();
		if (javaEntity != null) {
			JavaTable javaTable = javaEntity.getTable();
			if ( ! this.isMetadataComplete()
					&& ! this.table.isResourceSpecified()
					&& javaTable.getSpecifiedCatalog() != null) {
				return javaTable.getSpecifiedCatalog();
			}
		}

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
	
	/**
	 * Return whether the entity is the top of an inheritance hierarchy
	 * and has no descendants and no specified inheritance strategy has been defined.
	 */
	protected boolean isRootNoDescendantsNoStrategyDefined() {
		return isRoot() && !getPersistenceUnit().isRootWithSubEntities(this.getName()) && getSpecifiedInheritanceStrategy() == null;
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
	 * Return whether the type is abstract, false if no java type exists.
	 */
	protected boolean isAbstract() {
		JavaResourcePersistentType javaResourcePersistentType = getJavaResourcePersistentType();
		return javaResourcePersistentType == null ? false : javaResourcePersistentType.isAbstract();
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
	public Iterator<OrmPersistentAttribute> overridableAttributes() {
		if (!isTablePerClass()) {
			return EmptyIterator.instance();
		}
		return new FilteringIterator<OrmPersistentAttribute, OrmPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(OrmPersistentAttribute o) {
				return o.isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<OrmPersistentAttribute> overridableAssociations() {
		if (!isTablePerClass()) {
			return EmptyIterator.instance();
		}
		return new FilteringIterator<OrmPersistentAttribute, OrmPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(OrmPersistentAttribute o) {
				return o.isOverridableAssociation();
			}
		};
	}
	
	@Override
	public Iterator<PersistentAttribute> allOverridableAttributes() {
		return new CompositeIterator<PersistentAttribute>(new TransformationIterator<TypeMapping, Iterator<PersistentAttribute>>(this.ancestors()) {
			@Override
			protected Iterator<PersistentAttribute> transform(TypeMapping mapping) {
				return mapping.overridableAttributes();
			}
		});
	}
	
	@Override
	public Iterator<PersistentAttribute> allOverridableAssociations() {
		return new CompositeIterator<PersistentAttribute>(new TransformationIterator<TypeMapping, Iterator<PersistentAttribute>>(this.ancestors()) {
			@Override
			protected Iterator<PersistentAttribute> transform(TypeMapping mapping) {
				return mapping.overridableAssociations();
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
	
	
	protected void initializeInheritance(Inheritance inheritanceResource) {
		this.specifiedInheritanceStrategy = this.getResourceInheritanceStrategy(inheritanceResource);
		//no need to initialize defaultInheritanceStrategy, need to get all the persistentTypes in the model first
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
	
	protected void initializeIdClass(XmlIdClass idClassResource) {
		this.idClass = this.getResourceIdClass(idClassResource);	
	}

	protected String getResourceIdClass(XmlIdClass idClassResource) {
		return idClassResource == null ? null : idClassResource.getClassName();
	}

	@Override
	public void update() {
		super.update();
		this.setSpecifiedName(this.resourceTypeMapping.getName());
		this.setDefaultName(this.buildDefaultName());
		this.updateInheritance(this.getResourceInheritance());
		this.updateRootEntity();
		this.updateDiscriminatorColumn();
		this.updateDiscriminatorValue();
		this.setSpecifiedTableIsAllowed(this.buildSpecifiedTableIsAllowed());
		this.setTableIsUndefined(this.buildTableIsUndefined());
		this.table.update(this.resourceTypeMapping);
		this.updateSpecifiedSecondaryTables();
		this.updateVirtualSecondaryTables();
		this.generatorContainer.update();
		this.updateSpecifiedPrimaryKeyJoinColumns();
		this.updateDefaultPrimaryKeyJoinColumns();
		this.updateSpecifiedAttributeOverrides();
		this.updateVirtualAttributeOverrides();
		this.updateSpecifiedAssociationOverrides();
		getQueryContainer().update();
		this.updateIdClass(this.getResourceIdClass());
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		this.setDiscriminatorColumnIsUndefined(this.buildDiscriminatorColumnIsUndefined());
		getDiscriminatorColumn().postUpdate();
		postUpdateDiscriminatorValue();
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
	
	protected void updateDiscriminatorColumn() {
		this.setSpecifiedDiscriminatorColumnIsAllowed(this.buildSpecifiedDiscriminatorColumnIsAllowed());
		getDiscriminatorColumn().update(this.resourceTypeMapping);
	}
	
	protected void postUpdateDiscriminatorColumn() {
		this.setDiscriminatorColumnIsUndefined(this.buildDiscriminatorColumnIsUndefined());
		getDiscriminatorColumn().postUpdate();
	}
	
	protected void updateDiscriminatorValue() {
		this.setSpecifiedDiscriminatorValueIsAllowed(this.buildSpecifiedDiscriminatorValueIsAllowed());
		this.setSpecifiedDiscriminatorValue(this.resourceTypeMapping.getDiscriminatorValue());
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
		if (!isMetadataComplete() && getJavaEntity() != null) {
			return getJavaEntity().getDiscriminatorValue();
		}
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
	
	protected JavaResourcePersistentType getJavaResourcePersistentType() {
		if (getPersistentType().getJavaPersistentType() != null) {
			return getPersistentType().getJavaPersistentType().getResourcePersistentType();
		}
		return null;
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
	

	protected void updateInheritance(Inheritance inheritanceResource) {
		this.setSpecifiedInheritanceStrategy_(this.getResourceInheritanceStrategy(inheritanceResource));
		this.setDefaultInheritanceStrategy(this.buildDefaultInheritanceStrategy());
	}
	
	protected void updateRootEntity() {
		//I am making an assumption here that we don't need property change notification for rootEntity, this might be wrong
		this.rootEntity = calculateRootEntity();
		if (this.rootEntity != this) {
			this.rootEntity.addSubEntity(this);
		}
	}
	
	protected Entity calculateRootEntity() {
		Entity rootEntity = this;
		for (Iterator<PersistentType> stream = getPersistentType().inheritanceHierarchy(); stream.hasNext();) {
			PersistentType persistentType = stream.next();
			if (persistentType.getMapping() instanceof Entity) {
				rootEntity = (Entity) persistentType.getMapping();
			}
		}
		return rootEntity;
	}
	
	public void addSubEntity(Entity subEntity) {
		getPersistenceUnit().addRootWithSubEntities(getName());
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

	protected InheritanceType getResourceInheritanceStrategy(Inheritance inheritanceResource) {
		if (inheritanceResource == null) {
			return null;
		}
		return InheritanceType.fromOrmResourceModel(inheritanceResource.getStrategy());
	}
	
	protected InheritanceType buildDefaultInheritanceStrategy() {
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
	
	protected void updateIdClass(XmlIdClass idClassResource) {
		this.setIdClass_(this.getResourceIdClass(idClassResource));
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
	
	public void addToResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getEntities().add(this.resourceTypeMapping);
	}
	
	public void removeFromResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getEntities().remove(this.resourceTypeMapping);
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		this.validateTable(messages, reporter);	
		this.validateId(messages);
		this.validateInheritance(messages, reporter);
		this.generatorContainer.validate(messages, reporter);
		this.queryContainer.validate(messages, reporter);

		for (Iterator<OrmSecondaryTable> stream = this.secondaryTables(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter);
		}

		for (Iterator<OrmAttributeOverride> stream = this.attributeOverrides(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter);
		}

		for (Iterator<OrmAssociationOverride> stream = this.associationOverrides(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter);
		}
	}
	
	protected void validateTable(List<IMessage> messages, IReporter reporter) {
		if (isAbstractTablePerClass()) {
			if (this.table.isResourceSpecified()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_ABSTRACT_TABLE_PER_CLASS_DEFINES_TABLE,
						new String[] {this.getName()},
						this,
						this.getTable().getValidationTextRange()
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
						this.getTable().getValidationTextRange()
					)
				);
			}
			return;
		}
		this.table.validate(messages, reporter);
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
	
	protected void validateInheritance(List<IMessage> messages, IReporter reporter) {
		validateInheritanceStrategy(messages);
		validateDiscriminatorColumn(messages, reporter);
		validateDiscriminatorValue(messages);
	}
	
	protected void validateDiscriminatorColumn(List<IMessage> messages, IReporter reporter) {
		if (specifiedDiscriminatorColumnIsAllowed() && !discriminatorColumnIsUndefined()) {
			getDiscriminatorColumn().validate(messages, reporter);
		}
		else if (getDiscriminatorColumn().isResourceSpecified()) {
			if (!isRoot()) {
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
			else if (isTablePerClass()) {
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
		if (discriminatorValueIsUndefined() && getSpecifiedDiscriminatorValue() != null) {
			if (isAbstract()) {
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
			else if (isTablePerClass()) {
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
		Supported tablePerConcreteClassInheritanceIsSupported = getJpaValidation().getTablePerConcreteClassInheritanceIsSupported();
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
		return this.resourceTypeMapping.getDiscriminatorValueTextRange();
	}
	
	protected TextRange getDiscriminatorColumnTextRange() {
		return this.resourceTypeMapping.getDiscriminatorColumn().getValidationTextRange();
	}

	protected TextRange getInheritanceStrategyTextRange() {
		return this.resourceTypeMapping.getInheritanceStrategyTextRange();
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

	
	class PrimaryKeyJoinColumnOwner implements OrmBaseJoinColumn.Owner
	{
		public TypeMapping getTypeMapping() {
			return AbstractOrmEntity.this;
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return AbstractOrmEntity.this.getDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			Entity parentEntity = AbstractOrmEntity.this.getParentEntity();
			return (parentEntity == null) ? null : parentEntity.getPrimaryDbTable();
		}

		public int joinColumnsSize() {
			return AbstractOrmEntity.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return AbstractOrmEntity.this.defaultPrimaryKeyJoinColumns.contains(joinColumn);
		}
		
		public String getDefaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return AbstractOrmEntity.this.getParentEntity().getPrimaryKeyColumnName();
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
			return AbstractOrmEntity.this.virtualAttributeOverrides.contains(override);
		}

		public BaseOverride setVirtual(boolean virtual, BaseOverride override) {
			return AbstractOrmEntity.this.setAttributeOverrideVirtual(virtual, (OrmAttributeOverride) override);
		}

		public TypeMapping getTypeMapping() {
			return AbstractOrmEntity.this;
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
			return AbstractOrmEntity.this.virtualAssociationOverrides.contains(override);
		}

		public BaseOverride setVirtual(boolean virtual, BaseOverride override) {
			// TODO Auto-generated method stub
			return null;
		}

		public TypeMapping getTypeMapping() {
			return AbstractOrmEntity.this;
		}
	}
}
