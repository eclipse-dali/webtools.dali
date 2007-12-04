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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class XmlEntity extends XmlTypeMapping implements IEntity
{
	protected Entity entity;
	
	protected String specifiedName;

	protected String defaultName;

//	protected XmlIdClass idClassForXml;
//
//	protected XmlInheritance inheritanceForXml;
//
//	protected ITable table;
//
//	protected EList<ISecondaryTable> specifiedSecondaryTables;
//
//	protected EList<IPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
//
//	protected EList<IPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;
//
//	protected static final InheritanceType INHERITANCE_STRATEGY_EDEFAULT = InheritanceType.DEFAULT;
//
//	protected InheritanceType inheritanceStrategy = INHERITANCE_STRATEGY_EDEFAULT;
//
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
//	protected EList<ISecondaryTable> virtualSecondaryTables;

	public XmlEntity(XmlPersistentType parent) {
		super(parent);
//		this.table = OrmFactory.eINSTANCE.createXmlTable(buildTableOwner());
//		((InternalEObject) this.table).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__TABLE, null, null);
//		this.discriminatorColumn = OrmFactory.eINSTANCE.createXmlDiscriminatorColumn(new IDiscriminatorColumn.Owner(this));
//		((InternalEObject) this.discriminatorColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN, null, null);
//		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
//		this.eAdapters().add(this.buildListener());
	}

	public String getKey() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
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
//	protected void inheritanceStrategyChanged() {
//		if (getInheritanceStrategy() == InheritanceType.DEFAULT) {
//			setInheritanceForXml(null);
//		}
//		else {
//			if (getInheritanceForXml() == null) {
//				setInheritanceForXml(OrmFactory.eINSTANCE.createXmlInheritance());
//			}
//			getInheritanceForXml().setStrategy(getInheritanceStrategy());
//		}
//	}
//
//	protected void xmlInheritanceChanged() {
//		if (getInheritanceForXml() == null) {
//			setInheritanceStrategy(null);
//		}
//	}
//
//	@Override
//	protected void addInsignificantFeatureIdsTo(Set<Integer> insignificantFeatureIds) {
//		super.addInsignificantFeatureIdsTo(insignificantFeatureIds);
//		insignificantFeatureIds.add(OrmPackage.XML_ENTITY_INTERNAL__SECONDARY_TABLES);
//		insignificantFeatureIds.add(OrmPackage.XML_ENTITY_INTERNAL__ATTRIBUTE_OVERRIDES);
//		insignificantFeatureIds.add(OrmPackage.XML_ENTITY_INTERNAL__ASSOCIATION_OVERRIDES);
//	}
//
//	private ITable.Owner buildTableOwner() {
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

	public String getName() {
		return (this.getSpecifiedName() == null) ? getDefaultName() : this.getSpecifiedName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		this.entity.setName(newSpecifiedName);
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

//	public ITable getTable() {
//		return table;
//	}
//
//	private XmlTable getTableInternal() {
//		return (XmlTable) getTable();
//	}
//
//	public EList<ISecondaryTable> getSecondaryTables() {
//		EList<ISecondaryTable> list = new EObjectEList<ISecondaryTable>(ISecondaryTable.class, this, OrmPackage.XML_ENTITY_INTERNAL__SECONDARY_TABLES);
//		list.addAll(getSpecifiedSecondaryTables());
//		list.addAll(getVirtualSecondaryTables());
//		return list;
//	}
//
//	public EList<ISecondaryTable> getVirtualSecondaryTables() {
//		if (virtualSecondaryTables == null) {
//			virtualSecondaryTables = new EObjectContainmentEList<ISecondaryTable>(ISecondaryTable.class, this, OrmPackage.XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES);
//		}
//		return virtualSecondaryTables;
//	}
//
//	protected void xmlIdClassChanged() {
//		if (getIdClassForXml() == null) {
//			setIdClass(null);
//		}
//	}
//
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
//	public EList<ISecondaryTable> getSpecifiedSecondaryTables() {
//		if (specifiedSecondaryTables == null) {
//			specifiedSecondaryTables = new EObjectContainmentEList<ISecondaryTable>(ISecondaryTable.class, this, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES);
//		}
//		return specifiedSecondaryTables;
//	}
//
//	public InheritanceType getInheritanceStrategy() {
//		return inheritanceStrategy;
//	}
//
//	public void setInheritanceStrategy(InheritanceType newInheritanceStrategy) {
//		InheritanceType oldInheritanceStrategy = inheritanceStrategy;
//		inheritanceStrategy = newInheritanceStrategy == null ? INHERITANCE_STRATEGY_EDEFAULT : newInheritanceStrategy;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_STRATEGY, oldInheritanceStrategy, inheritanceStrategy));
//	}
//
//	//	public void setInheritanceStrategy(InheritanceType newInheritanceStrategy) {
//	//		setInheritanceStrategyGen(newInheritanceStrategy);
//	//		if (newInheritanceStrategy != INHERITANCE_STRATEGY_EDEFAULT) {
//	//			//makeInheritanceForXmlNonNull();
//	//		}
//	//		setInheritanceStrategyForXml(newInheritanceStrategy);
//	//		if (isAllFeaturesUnset()) {
//	//			//makeInheritanceForXmlNull();
//	//		}
//	//	}
//
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
//
//	public XmlTable getTableForXml() {
//		if (getTableInternal().isAllFeaturesUnset()) {
//			return null;
//		}
//		return getTableInternal();
//	}
//	public void setTableForXmlGen(XmlTable newTableForXml) {
//		XmlTable oldValue = newTableForXml == null ? (XmlTable) getTable() : null;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__TABLE_FOR_XML, oldValue, newTableForXml));
//	}
//
//	public void setTableForXml(XmlTable newTableForXml) {
//		setTableForXmlGen(newTableForXml);
//		if (newTableForXml == null) {
//			getTableInternal().unsetAllAttributes();
//		}
//	}
//
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
//
//	@Override
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		super.refreshDefaults(defaultsContext);
//		setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_ENTITY_NAME_KEY));
//	}
//
//	@Override
//	public String getTableName() {
//		return getTable().getName();
//	}
//
//	public void makeTableForXmlNull() {
//		setTableForXmlGen(null);
//	}
//
//	public void makeTableForXmlNonNull() {
//		setTableForXmlGen(getTableForXml());
//	}
//
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
//	public boolean tableNameIsInvalid(String tableName) {
//		return !CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
//	}
//
//	private Iterator<String> tableNames(Iterator<ITable> tables) {
//		return new TransformationIterator(tables) {
//			@Override
//			protected Object transform(Object next) {
//				return ((ITable) next).getName();
//			}
//		};
//	}
//
//	public Iterator<String> associatedTableNamesIncludingInherited() {
//		return this.nonNullTableNames(this.associatedTablesIncludingInherited());
//	}
//
//	private Iterator<String> nonNullTableNames(Iterator<ITable> tables) {
//		return new FilteringIterator(this.tableNames(tables)) {
//			@Override
//			protected boolean accept(Object o) {
//				return o != null;
//			}
//		};
//	}
//
//	public Iterator<ITable> associatedTables() {
//		return new CompositeIterator(this.getTable(), this.getSecondaryTables().iterator());
//	}
//
//	public Iterator<ITable> associatedTablesIncludingInherited() {
//		return new CompositeIterator(new TransformationIterator(this.inheritanceHierarchy()) {
//			@Override
//			protected Object transform(Object next) {
//				return new FilteringIterator(((ITypeMapping) next).associatedTables()) {
//					@Override
//					protected boolean accept(Object o) {
//						return true;
//						//TODO
//						//filtering these out so as to avoid the duplicate table, root and children share the same table
//						//return !(o instanceof SingleTableInheritanceChildTableImpl);
//					}
//				};
//			}
//		});
//	}
//
//	@Override
//	public Table primaryDbTable() {
//		return getTable().dbTable();
//	}
//
//	@Override
//	public Table dbTable(String tableName) {
//		for (Iterator<ITable> stream = this.associatedTablesIncludingInherited(); stream.hasNext();) {
//			Table dbTable = stream.next().dbTable();
//			if (dbTable != null && dbTable.matchesShortJavaClassName(tableName)) {
//				return dbTable;
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public Schema dbSchema() {
//		return getTable().dbSchema();
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
	private Iterator<ITypeMapping> inheritanceHierarchy() {
		return new TransformationIterator(persistentType().inheritanceHierarchy()) {
			@Override
			protected Object transform(Object next) {
				return ((IPersistentType) next).getMapping();
			}
		};
		//TODO once we support inheritance, which of these should we use??
		//return this.getInheritance().typeMappingLineage();
	}

	public Iterator<String> allOverridableAttributeNames() {
		return new CompositeIterator(new TransformationIterator(this.inheritanceHierarchy()) {
			protected Object transform(Object next) {
				return ((ITypeMapping) next).overridableAttributeNames();
			}
		});
	}

	public Iterator<String> allOverridableAssociationNames() {
		return new CompositeIterator(new TransformationIterator(this.inheritanceHierarchy()) {
			protected Object transform(Object next) {
				return ((ITypeMapping) next).overridableAssociationNames();
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
	protected void setAccessOnResource(AccessType newAccess) {
		this.entity.setAccess(AccessType.toXmlResourceModel(newAccess));
	}
	
	@Override
	protected void setClassOnResource(String newClass) {
		this.entity.setClassName(newClass);
	}
	
	@Override
	protected void setMetadataCompleteOnResource(Boolean newMetadataComplete) {
		this.entity.setMetadataComplete(newMetadataComplete);
	}

	
	public void initialize(Entity entity) {
		this.entity = entity;
		this.specifiedName = entity.getName();
		this.defaultName = defaultName();
		this.class_ = entity.getClassName();
		this.specifiedMetadataComplete = this.metadataComplete(entity);
		this.defaultMetadataComplete = this.defaultMetadataComplete();
		this.specifiedAccess = AccessType.fromXmlResourceModel(entity.getAccess());
		this.defaultAccess = this.defaultAccess();
	}
	
	public void update(Entity entity) {
		this.entity = entity;
		this.setSpecifiedName(entity.getName());
		this.setDefaultName(this.defaultName());
		this.setClass(entity.getClassName());
		this.setSpecifiedMetadataComplete(this.metadataComplete(entity));
		this.setDefaultMetadataComplete(this.defaultMetadataComplete());
		this.setSpecifiedAccess(AccessType.fromXmlResourceModel(entity.getAccess()));
		this.setDefaultAccess(this.defaultAccess());
	}

	protected String defaultName() {
		String className = getClass_();
		if (className != null) {
			return ClassTools.shortNameForClassNamed(className);
		}
		return null;
	}
	
	protected Boolean metadataComplete(Entity entity) {
		return entity.getMetadataComplete();
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

	public ISecondaryTable addSpecifiedSecondaryTable(int index) {
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

	public InheritanceType getDefaultInheritanceStrategy() {
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

	public InheritanceType getInheritanceStrategy() {
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

	public InheritanceType getSpecifiedInheritanceStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITable getTable() {
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

	public void moveSpecifiedSecondaryTable(int oldIndex, int newIndex) {
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

	public void removeSpecifiedSecondaryTable(int index) {
		// TODO Auto-generated method stub
		
	}

	public void removeTableGenerator() {
		// TODO Auto-generated method stub
		
	}

	public <T extends ISecondaryTable> ListIterator<T> secondaryTables() {
		// TODO Auto-generated method stub
		return null;
	}

	public int secondaryTablesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setSpecifiedDiscriminatorValue(String value) {
		// TODO Auto-generated method stub
		
	}

	public void setSpecifiedInheritanceStrategy(InheritanceType newInheritanceType) {
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

	public <T extends ISecondaryTable> ListIterator<T> specifiedSecondaryTables() {
		// TODO Auto-generated method stub
		return null;
	}

	public int specifiedSecondaryTablesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<ITable> associatedTables() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<ITable> associatedTablesIncludingInherited() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean tableNameIsInvalid(String tableName) {
		// TODO Auto-generated method stub
		return false;
	}

	public IColumnMapping columnMapping(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isVirtual(IOverride override) {
		// TODO Auto-generated method stub
		return false;
	}

	public ITypeMapping typeMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void removeFromResourceModel() {
		this.entity.entityMappings().getEntities().remove(this.entity);
	}
	
	@Override
	public void addToResourceModel(org.eclipse.jpt.core.internal.resource.orm.EntityMappings entityMappings) {
		this.entity = OrmFactory.eINSTANCE.createEntity();
		entityMappings.getEntities().add(this.entity);
	}
}
