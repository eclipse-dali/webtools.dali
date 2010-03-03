/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import org.eclipse.emf.common.util.EList;
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
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.internal.context.PrimaryKeyValidator;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaEntity;
import org.eclipse.jpt.core.internal.jpa1.context.GenericEntityPrimaryKeyValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.Inheritance;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.ClassName;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmEntity
	extends AbstractOrmTypeMapping<XmlEntity>
	implements OrmEntity, OrmCacheableHolder2_0
{
	protected String specifiedName;
	
	protected String defaultName;
	
	protected final OrmIdClassReference idClassReference;
	
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
	
	protected final OrmAttributeOverrideContainer attributeOverrideContainer;
	
	protected final OrmAssociationOverrideContainer associationOverrideContainer;
	
	protected final OrmQueryContainer queryContainer;
	
	protected Entity rootEntity;
	
	protected AbstractOrmEntity(OrmPersistentType parent, XmlEntity resourceMapping) {
		super(parent, resourceMapping);
		this.idClassReference = buildIdClassReference();
		this.table = getXmlContextNodeFactory().buildOrmTable(this);
		this.specifiedSecondaryTables = new ArrayList<OrmSecondaryTable>();
		this.virtualSecondaryTables = new ArrayList<OrmSecondaryTable>();
		this.discriminatorColumn = buildDiscriminatorColumn();
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
		this.associationOverrideContainer = buildAssociationOverrideContainer();
		this.attributeOverrideContainer = buildAttributeOverrideContainer();
		this.queryContainer = this.buildQueryContainer();
		this.generatorContainer = this.buildGeneratorContainer();
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
	}
	
	protected OrmIdClassReference buildIdClassReference() {
		return new GenericOrmIdClassReference(this, getJavaIdClassReferenceForDefaults());
	}
	
	protected OrmDiscriminatorColumn buildDiscriminatorColumn() {
		return getXmlContextNodeFactory().buildOrmDiscriminatorColumn(this, buildDiscriminatorColumnOwner());
	}
	
	protected OrmDiscriminatorColumn.Owner buildDiscriminatorColumnOwner() {
		return new OrmDiscriminatorColumn.Owner(){
			public String getDefaultTableName() {
				return AbstractOrmEntity.this.getPrimaryTableName();
			}

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

			public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
				return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.DISCRIMINATOR_COLUMN_UNRESOLVED_NAME,
					new String[] {column.getName(), column.getDbTable().getName()}, 
					column,
					textRange
				);
			}

			public TextRange getValidationTextRange() {
				return AbstractOrmEntity.this.getValidationTextRange();
			}
		};
	}

	protected OrmAssociationOverrideContainer buildAssociationOverrideContainer() {
		return getXmlContextNodeFactory().buildOrmAssociationOverrideContainer(
			this,
			new AssociationOverrideContainerOwner());
	}
	
	protected OrmAttributeOverrideContainer buildAttributeOverrideContainer() {
		return getXmlContextNodeFactory().buildOrmAttributeOverrideContainer(
			this,
			new AttributeOverrideContainerOwner());
	}

	protected OrmGeneratorContainer buildGeneratorContainer() {
		return getXmlContextNodeFactory().buildOrmGeneratorContainer(this, this.resourceTypeMapping);
	}

	protected OrmQueryContainer buildQueryContainer() {
		return getXmlContextNodeFactory().buildOrmQueryContainer(this, this.resourceTypeMapping);
	}
	

	// ******************* TypeMapping implementation ********************

	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
	
	@Override
	public JavaPersistentType getIdClass() {
		return this.idClassReference.getIdClass();
	}

	@Override
	public String getPrimaryTableName() {
		return this.table.getName();
	}
	
	@Override
	public org.eclipse.jpt.db.Table getPrimaryDbTable() {
		return this.table.getDbTable();
	}

	@Override
	public org.eclipse.jpt.db.Table getDbTable(String tableName) {
		// matching database objects and identifiers is database platform-specific
		return this.getDataSource().selectDatabaseObjectForIdentifier(this.getAssociatedDbTablesIncludingInherited(), tableName);
	}

	private Iterable<org.eclipse.jpt.db.Table> getAssociatedDbTablesIncludingInherited() {
		return new FilteringIterable<org.eclipse.jpt.db.Table>(this.getAssociatedDbTablesIncludingInherited_()) {
			@Override
			protected boolean accept(org.eclipse.jpt.db.Table t) {
				return t != null;
			}
		};
	}

	private Iterable<org.eclipse.jpt.db.Table> getAssociatedDbTablesIncludingInherited_() {
		return new TransformationIterable<Table, org.eclipse.jpt.db.Table>(this.getAssociatedTablesIncludingInherited()) {
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
	
	protected JavaIdClassReference getJavaIdClassReferenceForDefaults() {
		JavaEntity entity = getJavaEntityForDefaults();
		return (entity == null) ? null : entity.getIdClassReference();
	}
	
	
	//****************** OrmAttributeOverrideContainer.Owner implementation *******************
	
	public TypeMapping getOverridableTypeMapping() {
		PersistentType superPersistentType = getPersistentType().getSuperPersistentType();
		return superPersistentType == null ? null : superPersistentType.getMapping();
	}

	public OrmTypeMapping getTypeMapping() {
		return this;
	}
	
	protected JavaAttributeOverride getJavaAttributeOverrideNamed(String attributeName) {
		if (getJavaEntity() != null) {
			return getJavaEntity().getAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
		}
		return null;
	}
	
	@Override
	public RelationshipReference resolveRelationshipReference(String name) {
		if (this.isJpa2_0Compatible()) {
			int dotIndex = name.indexOf('.');
			if (dotIndex != -1) {
				AssociationOverride override = getAssociationOverrideContainer().getAssociationOverrideNamed(name.substring(dotIndex + 1));
				if (override != null && !override.isVirtual()) {
					return override.getRelationshipReference();
				}
			}
		}
		return super.resolveRelationshipReference(name);
	}
	
	protected JavaAssociationOverride getJavaAssociationOverrideNamed(String attributeName) {
		if (getJavaEntity() != null) {
			return getJavaEntity().getAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
		}
		return null;
	}
	
	// **************** name **************************************************
	
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
	
	
	// **************** id class **********************************************
	
	public OrmIdClassReference getIdClassReference() {
		return this.idClassReference;
	}
	
	
	// **************** table *************************************************
	
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
		return new FilteringIterator<String>(this.tableNames(tables)) {
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
		return this.getAssociatedTablesIncludingInherited().iterator();
	}

	public Iterable<Table> getAssociatedTablesIncludingInherited() {
		return new CompositeIterable<Table>(new TransformationIterable<TypeMapping, Iterable<Table>>(CollectionTools.iterable(this.inheritanceHierarchy())) {
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
			fireListChanged(OrmEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST, this.defaultPrimaryKeyJoinColumns);
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

	public OrmAttributeOverrideContainer getAttributeOverrideContainer() {
		return this.attributeOverrideContainer;
	}
	
	public OrmAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.associationOverrideContainer;
	}
	
	public OrmQueryContainer getQueryContainer() {
		return this.queryContainer;
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
	
	public Entity getParentEntity() {
		for (Iterator<PersistentType> stream = getPersistentType().ancestors(); stream.hasNext();) {
			TypeMapping tm = stream.next().getMapping();
			if (tm instanceof Entity) {
				return (Entity) tm;
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
		if (this.isJpa2_0Compatible()) {
			int dotIndex = attributeName.indexOf('.');
			if (dotIndex != -1) {
				AttributeOverride override = getAttributeOverrideContainer().getAttributeOverrideNamed(attributeName.substring(dotIndex + 1));
				if (override != null && !override.isVirtual()) {
					return override.getColumn();
				}
			}
		}
		return super.resolveOverriddenColumn(attributeName);
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
	
	@Override
	public void update() {
		super.update();
		this.setSpecifiedName(this.resourceTypeMapping.getName());
		this.setDefaultName(this.buildDefaultName());
		this.idClassReference.update(getJavaIdClassReferenceForDefaults());
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
		getAttributeOverrideContainer().update();
		getAssociationOverrideContainer().update();
		getQueryContainer().update();
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		this.postUpdateDiscriminatorColumn();
		this.postUpdateDiscriminatorValue();
	}

	protected String buildDefaultName() {
		if (!isMetadataComplete()) {
			JavaEntity javaEntity = getJavaEntity();
			if (javaEntity != null) {
				return javaEntity.getName();
			}
		}
		String className = getClass_();
		return StringTools.stringIsEmpty(className) ? null : ClassName.getSimpleName(className);
	}
	
	protected void updateDiscriminatorColumn() {
		this.setSpecifiedDiscriminatorColumnIsAllowed(this.buildSpecifiedDiscriminatorColumnIsAllowed());
		getDiscriminatorColumn().update(this.resourceTypeMapping);
	}
	
	protected void postUpdateDiscriminatorColumn() {
		this.setDiscriminatorColumnIsUndefined(this.buildDiscriminatorColumnIsUndefined());
		this.getDiscriminatorColumn().postUpdate();
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
		return getXmlContextNodeFactory().buildOrmSecondaryTable(this, xmlSecondaryTable);
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
		return getXmlContextNodeFactory().buildOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner(), resourcePkJoinColumn);
	}
	
	
	// *************************************************************************
	
	public String getPrimaryKeyColumnName() {
		return AbstractJavaEntity.getPrimaryKeyColumnName(this);
	}
	
	public PersistentAttribute getIdAttribute() {
		Iterable<AttributeMapping> idAttributeMappings = getAllAttributeMappings(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		if (CollectionTools.size(idAttributeMappings) != 1) {
			return null;
		}
		return idAttributeMappings.iterator().next().getPersistentAttribute();
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
		
		validatePrimaryKey(messages, reporter);
		validateTable(messages, reporter);	
		for (Iterator<OrmSecondaryTable> stream = this.secondaryTables(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter);
		}
		validateInheritance(messages, reporter);
		this.generatorContainer.validate(messages, reporter);
		this.queryContainer.validate(messages, reporter);
		this.attributeOverrideContainer.validate(messages, reporter);
		this.associationOverrideContainer.validate(messages, reporter);
	}
	
	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter) {
		buildPrimaryKeyValidator().validate(messages, reporter);
	}
	
	protected PrimaryKeyValidator buildPrimaryKeyValidator() {
		return new GenericEntityPrimaryKeyValidator(this, buildTextRangeResolver());
		// TODO - JPA 2.0 validation
	}
	
	protected PrimaryKeyTextRangeResolver buildTextRangeResolver() {
		return new OrmEntityTextRangeResolver(this);
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
		
	class AssociationOverrideContainerOwner implements OrmAssociationOverrideContainer.Owner {
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmEntity.this.getOverridableTypeMapping();
		}
		
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmEntity.this.getTypeMapping();
		}

		public EList<XmlAssociationOverride> getResourceAssociationOverrides() {
			return AbstractOrmEntity.this.resourceTypeMapping.getAssociationOverrides();
		}

		public RelationshipReference resolveRelationshipReference(String associationOverrideName) {
			if (!isMetadataComplete()) {
				JavaPersistentType javaPersistentType = getPersistentType().getJavaPersistentType();
				if (javaPersistentType != null) {
					RelationshipReference relationshipReference = javaPersistentType.getMapping().resolveRelationshipReference(associationOverrideName);
					if (relationshipReference != null) {
						return relationshipReference;
					}
				}
			}
			return MappingTools.resolveRelationshipReference(getOverridableTypeMapping(), associationOverrideName);
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return AbstractOrmEntity.this.tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return AbstractOrmEntity.this.associatedTableNamesIncludingInherited();
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return AbstractOrmEntity.this.getDbTable(tableName);
		}
		
		public String getDefaultTableName() {
			return AbstractOrmEntity.this.getPrimaryTableName();
		}

		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.COLUMN_TABLE_NOT_VALID_FOR_THIS_ENTITY,
					new String[] {column.getTable(), column.getName()}, 
					column, 
					textRange
				);
		}

		protected IMessage buildVirtualOverrideColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID_FOR_THIS_ENTITY,
				new String[] {overrideName, column.getTable(), column.getName()},
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
		
		public TextRange getValidationTextRange() {
			return AbstractOrmEntity.this.getValidationTextRange();
		}
	}
	
	//********** OrmAttributeOverrideContainer.Owner implementation *********	
	
	class AttributeOverrideContainerOwner implements OrmAttributeOverrideContainer.Owner {
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmEntity.this.getOverridableTypeMapping();
		}
		
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmEntity.this.getTypeMapping();
		}

		public EList<XmlAttributeOverride> getResourceAttributeOverrides() {
			return AbstractOrmEntity.this.resourceTypeMapping.getAttributeOverrides();
		}

		public Column resolveOverriddenColumn(String attributeOverrideName) {
			if (!isMetadataComplete()) {
				JavaPersistentType javaPersistentType = getPersistentType().getJavaPersistentType();
				if (javaPersistentType != null) {
					Column column = javaPersistentType.getMapping().resolveOverriddenColumn(attributeOverrideName);
					if (column != null) {
						return column;
					}
				}
			}
			return MappingTools.resolveOverridenColumn(getOverridableTypeMapping(), attributeOverrideName);
		}
		
		public XmlColumn buildVirtualXmlColumn(Column overridableColumn, String attributeName, boolean isMetadataComplete) {
			JavaAttributeOverride javaAttributeOverride = null;
			if (!isMetadataComplete) {
				javaAttributeOverride = getJavaAttributeOverrideNamed(attributeName);
			}
			if (javaAttributeOverride == null) {
				//TODO not the greatest solution here, but things seems to work, so I'm stepping away slowly
				if (overridableColumn instanceof JavaColumn) {
					return new VirtualXmlColumn(AbstractOrmEntity.this, overridableColumn);
				}
				return new VirtualXmlAttributeOverrideColumn(overridableColumn);
			}
			return new VirtualXmlColumn(AbstractOrmEntity.this, javaAttributeOverride.getColumn());
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractOrmEntity.this.tableNameIsInvalid(tableName);
		}
		
		public Iterator<String> candidateTableNames() {
			return AbstractOrmEntity.this.associatedTableNamesIncludingInherited();
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return AbstractOrmEntity.this.getDbTable(tableName);
		}
		
		public String getDefaultTableName() {
			return AbstractOrmEntity.this.getPrimaryTableName();
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

		public IMessage buildVirtualColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
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
				return this.buildVirtualOverrideColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_TABLE_NOT_VALID_FOR_THIS_ENTITY,
				new String[] {column.getTable(), column.getName()}, 
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualOverrideColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID_FOR_THIS_ENTITY,
				new String[] {overrideName, column.getTable(), column.getName()},
				column, 
				textRange
			);
		}
		
		public TextRange getValidationTextRange() {
			return AbstractOrmEntity.this.getValidationTextRange();
		}
	}
	
	class PrimaryKeyJoinColumnOwner implements OrmBaseJoinColumn.Owner
	{
		public TypeMapping getTypeMapping() {
			return AbstractOrmEntity.this;
		}

		public String getDefaultTableName() {
			return AbstractOrmEntity.this.getPrimaryTableName();
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
			Entity parentEntity = AbstractOrmEntity.this.getParentEntity();
			return (parentEntity == null) ? getPrimaryKeyColumnName() : parentEntity.getPrimaryKeyColumnName();
		}
		
		public TextRange getValidationTextRange() {
			return null;
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
