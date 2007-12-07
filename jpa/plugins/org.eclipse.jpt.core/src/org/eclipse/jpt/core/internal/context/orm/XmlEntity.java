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
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.core.internal.context.java.IJavaEntity;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaSecondaryTable;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.Inheritance;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
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

//	protected EList<IPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
//
//	protected EList<IPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	protected InheritanceType specifiedInheritanceStrategy;
	
	protected InheritanceType defaultInheritanceStrategy;

//	protected String defaultDiscriminatorValue;
//
//	protected String specifiedDiscriminatorValue;
//
//	protected IDiscriminatorColumn discriminatorColumn;
//
//	protected ISequenceGenerator sequenceGenerator;
//
//	protected ITableGenerator tableGenerator;
//
//	protected EList<IAttributeOverride> specifiedAttributeOverrides;
//
//	protected EList<IAttributeOverride> defaultAttributeOverrides;
//
//	protected EList<IAssociationOverride> specifiedAssociationOverrides;
//
//	protected EList<IAssociationOverride> defaultAssociationOverrides;
//
//	protected EList<INamedQuery> namedQueries;
//
//	protected EList<INamedNativeQuery> namedNativeQueries;
//
//	protected String idClass;
//

	public XmlEntity(XmlPersistentType parent) {
		super(parent);
		this.table = new XmlTable(this);
		this.specifiedSecondaryTables = new ArrayList<XmlSecondaryTable>();
		this.virtualSecondaryTables = new ArrayList<XmlSecondaryTable>();
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

//	protected Adapter buildListener() {
//		return new AdapterImpl() {
//			@Override
//			public void notifyChanged(Notification notification) {
//				XmlEntityInternal.this.notifyChanged(notification);
//			}
//		};
//	}
//
//	protected void notifyChanged(Notification notification) {
//		switch (notification.getFeatureID(IEntity.class)) {
//			case JpaCoreMappingsPackage.IENTITY__ID_CLASS :
//				idClassChanged();
//				break;
//			case JpaCoreMappingsPackage.IENTITY__INHERITANCE_STRATEGY :
//				inheritanceStrategyChanged();
//				break;
//			default :
//				break;
//		}
//		switch (notification.getFeatureID(XmlEntityForXml.class)) {
//			case OrmPackage.XML_ENTITY_FOR_XML__ID_CLASS_FOR_XML :
//				xmlIdClassChanged();
//				break;
//			case OrmPackage.XML_ENTITY_FOR_XML__INHERITANCE_FOR_XML :
//				xmlInheritanceChanged();
//				break;
//			default :
//				break;
//		}
//	}
//

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

	public ListIterator<XmlSecondaryTable> secondaryTables() {
		return new CompositeListIterator<XmlSecondaryTable>(specifiedSecondaryTables(), virtualSecondaryTables());
	}

	public int secondaryTablesSize() {
		return virtualSecondaryTablesSize() + specifiedSecondaryTablesSize();
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

	public ListIterator<XmlSecondaryTable> specifiedSecondaryTables() {
		return new CloneListIterator<XmlSecondaryTable>(this.specifiedSecondaryTables);
	}

	public int specifiedSecondaryTablesSize() {
		return this.specifiedSecondaryTables.size();
	}
	
	public XmlSecondaryTable addSpecifiedSecondaryTable(int index) {
		XmlSecondaryTable secondaryTable = new XmlSecondaryTable(this);
		this.specifiedSecondaryTables.add(index, secondaryTable);
		typeMappingResource().getSecondaryTables().add(index, OrmFactory.eINSTANCE.createSecondaryTable());
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
	
	public void moveSpecifiedSecondaryTable(int oldIndex, int newIndex) {
		typeMappingResource().getSecondaryTables().move(newIndex, oldIndex);
		moveItemInList(newIndex, oldIndex, this.specifiedSecondaryTables, IEntity.SPECIFIED_SECONDARY_TABLES_LIST);
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
				this.inheritanceResource().setStrategy(InheritanceType.toXmlResourceModel(newInheritanceType));						
				if (this.inheritanceResource().isAllFeaturesUnset()) {
					removeInheritanceResource();
				}
			}
			else if (newInheritanceType != null) {
				addInheritanceResource();
				inheritanceResource().setStrategy(InheritanceType.toXmlResourceModel(newInheritanceType));
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

//	public IDiscriminatorColumn getDiscriminatorColumn() {
//		return discriminatorColumn;
//	}
//
//	public NotificationChain basicSetDiscriminatorColumn(IDiscriminatorColumn newDiscriminatorColumn, NotificationChain msgs) {
//		IDiscriminatorColumn oldDiscriminatorColumn = discriminatorColumn;
//		discriminatorColumn = newDiscriminatorColumn;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN, oldDiscriminatorColumn, newDiscriminatorColumn);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public ISequenceGenerator getSequenceGenerator() {
//		return sequenceGenerator;
//	}
//
//	/**
//	 * <!-- begin-user-doc -->
//	 * <!-- end-user-doc -->
//	 * @generated
//	 */
//	public NotificationChain basicSetSequenceGenerator(ISequenceGenerator newSequenceGenerator, NotificationChain msgs) {
//		ISequenceGenerator oldSequenceGenerator = sequenceGenerator;
//		sequenceGenerator = newSequenceGenerator;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setSequenceGenerator(ISequenceGenerator newSequenceGenerator) {
//		if (newSequenceGenerator != sequenceGenerator) {
//			NotificationChain msgs = null;
//			if (sequenceGenerator != null)
//				msgs = ((InternalEObject) sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR, null, msgs);
//			if (newSequenceGenerator != null)
//				msgs = ((InternalEObject) newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR, null, msgs);
//			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
//	}
//
//	public ITableGenerator getTableGenerator() {
//		return tableGenerator;
//	}
//
//	public NotificationChain basicSetTableGenerator(ITableGenerator newTableGenerator, NotificationChain msgs) {
//		ITableGenerator oldTableGenerator = tableGenerator;
//		tableGenerator = newTableGenerator;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setTableGenerator(ITableGenerator newTableGenerator) {
//		if (newTableGenerator != tableGenerator) {
//			NotificationChain msgs = null;
//			if (tableGenerator != null)
//				msgs = ((InternalEObject) tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR, null, msgs);
//			if (newTableGenerator != null)
//				msgs = ((InternalEObject) newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR, null, msgs);
//			msgs = basicSetTableGenerator(newTableGenerator, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
//	}
//
//	public String getDefaultDiscriminatorValue() {
//		return defaultDiscriminatorValue;
//	}
//
//	public void setDefaultDiscriminatorValue(String newDefaultDiscriminatorValue) {
//		String oldDefaultDiscriminatorValue = defaultDiscriminatorValue;
//		defaultDiscriminatorValue = newDefaultDiscriminatorValue;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_DISCRIMINATOR_VALUE, oldDefaultDiscriminatorValue, defaultDiscriminatorValue));
//	}
//
//	public String getSpecifiedDiscriminatorValue() {
//		return specifiedDiscriminatorValue;
//	}
//
//	public void setSpecifiedDiscriminatorValue(String newSpecifiedDiscriminatorValue) {
//		String oldSpecifiedDiscriminatorValue = specifiedDiscriminatorValue;
//		specifiedDiscriminatorValue = newSpecifiedDiscriminatorValue;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_DISCRIMINATOR_VALUE, oldSpecifiedDiscriminatorValue, specifiedDiscriminatorValue));
//	}
//
//	public String getDiscriminatorValue() {
//		return (this.getSpecifiedDiscriminatorValue() == null) ? getDefaultDiscriminatorValue() : this.getSpecifiedDiscriminatorValue();
//	}
//
//	public EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
//		return this.getSpecifiedPrimaryKeyJoinColumns().isEmpty() ? this.getDefaultPrimaryKeyJoinColumns() : this.getSpecifiedPrimaryKeyJoinColumns();
//	}
//
//	public EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
//		if (specifiedPrimaryKeyJoinColumns == null) {
//			specifiedPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS);
//		}
//		return specifiedPrimaryKeyJoinColumns;
//	}
//
//	public EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
//		if (defaultPrimaryKeyJoinColumns == null) {
//			defaultPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS);
//		}
//		return defaultPrimaryKeyJoinColumns;
//	}
//
//	public EList<IAttributeOverride> getAttributeOverrides() {
//		EList<IAttributeOverride> list = new EObjectEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__ATTRIBUTE_OVERRIDES);
//		list.addAll(getSpecifiedAttributeOverrides());
//		list.addAll(getDefaultAttributeOverrides());
//		return list;
//	}
//
//	public EList<IAttributeOverride> getSpecifiedAttributeOverrides() {
//		if (specifiedAttributeOverrides == null) {
//			specifiedAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES);
//		}
//		return specifiedAttributeOverrides;
//	}
//
//	public EList<IAttributeOverride> getDefaultAttributeOverrides() {
//		if (defaultAttributeOverrides == null) {
//			defaultAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES);
//		}
//		return defaultAttributeOverrides;
//	}
//
//	public EList<IAssociationOverride> getAssociationOverrides() {
//		EList<IAssociationOverride> list = new EObjectEList<IAssociationOverride>(IAssociationOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__ASSOCIATION_OVERRIDES);
//		list.addAll(getSpecifiedAssociationOverrides());
//		list.addAll(getDefaultAssociationOverrides());
//		return list;
//	}
//
//	public EList<IAssociationOverride> getSpecifiedAssociationOverrides() {
//		if (specifiedAssociationOverrides == null) {
//			specifiedAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES);
//		}
//		return specifiedAssociationOverrides;
//	}
//
//	public EList<IAssociationOverride> getDefaultAssociationOverrides() {
//		if (defaultAssociationOverrides == null) {
//			defaultAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES);
//		}
//		return defaultAssociationOverrides;
//	}
//
//	public EList<INamedQuery> getNamedQueries() {
//		if (namedQueries == null) {
//			namedQueries = new EObjectContainmentEList<INamedQuery>(INamedQuery.class, this, OrmPackage.XML_ENTITY_INTERNAL__NAMED_QUERIES);
//		}
//		return namedQueries;
//	}
//
//	public EList<INamedNativeQuery> getNamedNativeQueries() {
//		if (namedNativeQueries == null) {
//			namedNativeQueries = new EObjectContainmentEList<INamedNativeQuery>(INamedNativeQuery.class, this, OrmPackage.XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES);
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

//	public XmlDiscriminatorColumn getDiscriminatorColumnForXml() {
//		if (getDiscriminatorColumnInternal().isAllFeaturesUnset()) {
//			return null;
//		}
//		return getDiscriminatorColumnInternal();
//	}
//
//	private XmlDiscriminatorColumn getDiscriminatorColumnInternal() {
//		return (XmlDiscriminatorColumn) getDiscriminatorColumn();
//	}
//
//	public void setDiscriminatorColumnForXmlGen(XmlDiscriminatorColumn newDiscriminatorColumnForXml) {
//		XmlDiscriminatorColumn oldValue = newDiscriminatorColumnForXml == null ? (XmlDiscriminatorColumn) getDiscriminatorColumn() : null;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN_FOR_XML, oldValue, newDiscriminatorColumnForXml));
//	}
//
//	public void setDiscriminatorColumnForXml(XmlDiscriminatorColumn newDiscriminatorColumnForXml) {
//		setDiscriminatorColumnForXmlGen(newDiscriminatorColumnForXml);
//		if (newDiscriminatorColumnForXml == null) {
//			getDiscriminatorColumnInternal().unsetAllAttributes();
//		}
//	}
//
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
//	public XmlInheritance getInheritanceForXml() {
//		return inheritanceForXml;
//	}
//
//	public NotificationChain basicSetInheritanceForXml(XmlInheritance newInheritanceForXml, NotificationChain msgs) {
//		XmlInheritance oldInheritanceForXml = inheritanceForXml;
//		inheritanceForXml = newInheritanceForXml;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML, oldInheritanceForXml, newInheritanceForXml);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setInheritanceForXml(XmlInheritance newInheritanceForXml) {
//		if (newInheritanceForXml != inheritanceForXml) {
//			NotificationChain msgs = null;
//			if (inheritanceForXml != null)
//				msgs = ((InternalEObject) inheritanceForXml).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML, null, msgs);
//			if (newInheritanceForXml != null)
//				msgs = ((InternalEObject) newInheritanceForXml).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML, null, msgs);
//			msgs = basicSetInheritanceForXml(newInheritanceForXml, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML, newInheritanceForXml, newInheritanceForXml));
//	}


//	public void makeDiscriminatorColumnForXmlNull() {
//		setDiscriminatorColumnForXmlGen(null);
//	}
//
//	//um, this is an object on XmlInheritance, but a tag on entity in the xml, how to handle???
//	public void makeDiscriminatorColumnForXmlNonNull() {
//		setDiscriminatorColumnForXmlGen(getDiscriminatorColumnForXml());
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
//	public ISecondaryTable createSecondaryTable(int index) {
//		return OrmFactory.eINSTANCE.createXmlSecondaryTable(buildSecondaryTableOwner());
//	}
//
//	private ITable.Owner buildSecondaryTableOwner() {
//		return new ITable.Owner() {
//			public ITextRange validationTextRange() {
//				return XmlEntityInternal.this.validationTextRange();
//			}
//
//			public ITypeMapping getTypeMapping() {
//				return XmlEntityInternal.this;
//			}
//		};
//	}
//
//	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
//		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
//	}
//
//	public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
//		return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn(new IEntity.PrimaryKeyJoinColumnOwner(this));
//	}
//
//	public INamedQuery createNamedQuery(int index) {
//		return OrmFactory.eINSTANCE.createXmlNamedQuery();
//	}
//
//	public INamedNativeQuery createNamedNativeQuery(int index) {
//		return OrmFactory.eINSTANCE.createXmlNamedNativeQuery();
//	}
	
	
	@Override
	public void initialize(Entity entity) {
		super.initialize(entity);
		this.specifiedName = entity.getName();
		this.defaultName = defaultName();
		this.initializeInheritance(this.inheritanceResource());
		this.table.initialize(entity);
		this.initializeSpecifiedSecondaryTables(entity);
		this.initializeVirtualSecondaryTables();
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
		ListIterator<IJavaSecondaryTable> javaSecondaryTables = javaEntity().secondaryTables();
		while(javaSecondaryTables.hasNext()) {
			IJavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
			if (javaSecondaryTable.getName() != null) {
				if (!containsSpecifiedSecondaryTable(javaSecondaryTable.getName())) {
					//TODO calling setters during initialize in createVirtualSecondaryTable
					//I think this is going to be a problem
					this.virtualSecondaryTables.add(createVirtualSecondaryTable(javaSecondaryTable));
				}
			}
		}
	}
	

	@Override
	public void update(Entity entity) {
		super.update(entity);
		this.setSpecifiedName(entity.getName());
		this.setDefaultName(this.defaultName());
		this.updateInheritance(this.inheritanceResource());
		this.table.update(entity);
		this.updateSpecifiedSecondaryTables(entity);
		this.updateVirtualSecondaryTables();
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
	
	protected void updateVirtualSecondaryTables() {
		ListIterator<XmlSecondaryTable> secondaryTables = virtualSecondaryTables();
		ListIterator<IJavaSecondaryTable> javaSecondaryTables = EmptyListIterator.instance();
		
		if (javaEntity() != null && !isMetadataComplete()) {
			javaSecondaryTables = javaEntity().secondaryTables();
		}
		while (secondaryTables.hasNext()) {
			XmlSecondaryTable virtualSecondaryTable = secondaryTables.next();
			if (javaSecondaryTables.hasNext()) {
				IJavaSecondaryTable javaSecondaryTable = javaSecondaryTables.next();
				if (!containsSpecifiedSecondaryTable(javaSecondaryTable.getName())) {
					virtualSecondaryTable.setDefaultName(javaSecondaryTable.getName());
					virtualSecondaryTable.setDefaultCatalog(javaSecondaryTable.getCatalog());
					virtualSecondaryTable.setDefaultSchema(javaSecondaryTable.getSchema());
					//TODO what about pkJoinColumns?
				}
				else {
					removeVirtualSecondaryTable(virtualSecondaryTable);
				}
			}
			else {
				removeVirtualSecondaryTable(virtualSecondaryTable);
			}
		}
		
		while (javaSecondaryTables.hasNext()) {
			addVirtualSecondaryTable(createVirtualSecondaryTable(javaSecondaryTables.next()));
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
	
	protected InheritanceType specifiedInheritanceStrategy(Inheritance inheritanceResource) {
		if (inheritanceResource == null) {
			return null;
		}
		return InheritanceType.fromXmlResourceModel(inheritanceResource.getStrategy());
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

	
	public ISequenceGenerator addSequenceGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public IAttributeOverride addSpecifiedAttributeOverride(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public IPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		// TODO Auto-generated method stub
		return null;
	}


	public ITableGenerator addTableGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends IAttributeOverride> ListIterator<T> attributeOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends IAttributeOverride> ListIterator<T> defaultAttributeOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends IPrimaryKeyJoinColumn> ListIterator<T> defaultPrimaryKeyJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDefaultDiscriminatorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public IDiscriminatorColumn getDiscriminatorColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDiscriminatorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public ISequenceGenerator getSequenceGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSpecifiedDiscriminatorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITableGenerator getTableGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void moveSpecifiedAttributeOverride(int oldIndex, int newIndex) {
		// TODO Auto-generated method stub
		
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int oldIndex, int newIndex) {
		// TODO Auto-generated method stub
		
	}

	public String primaryKeyColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends IPrimaryKeyJoinColumn> ListIterator<T> primaryKeyJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeSequenceGenerator() {
		// TODO Auto-generated method stub
		
	}

	public void removeSpecifiedAttributeOverride(int index) {
		// TODO Auto-generated method stub
		
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		// TODO Auto-generated method stub
		
	}

	public void removeTableGenerator() {
		// TODO Auto-generated method stub
		
	}

	public void setSpecifiedDiscriminatorValue(String value) {
		// TODO Auto-generated method stub
		
	}

	public <T extends IAttributeOverride> ListIterator<T> specifiedAttributeOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

	public int specifiedAttributeOverridesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T extends IPrimaryKeyJoinColumn> ListIterator<T> specifiedPrimaryKeyJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		// TODO Auto-generated method stub
		return 0;
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
		entityMappings.getEntities().add(entity);
		return entity;
	}
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(getName());
	}
}
