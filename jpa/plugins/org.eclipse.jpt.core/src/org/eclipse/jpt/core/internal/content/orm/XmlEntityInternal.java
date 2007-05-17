/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.INamedNativeQuery;
import org.eclipse.jpt.core.internal.mappings.INamedQuery;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.core.internal.mappings.InheritanceType;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xml Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityInternal()
 * @model kind="class"
 * @generated
 */
public class XmlEntityInternal extends XmlTypeMapping
	implements XmlEntityForXml, XmlEntity
{
	/**
	 * The cached value of the '{@link #getIdClassForXml() <em>Id Class For Xml</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClassForXml()
	 * @generated
	 * @ordered
	 */
	protected XmlIdClass idClassForXml;

	/**
	 * The cached value of the '{@link #getInheritanceForXml() <em>Inheritance For Xml</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInheritanceForXml()
	 * @generated
	 * @ordered
	 */
	protected XmlInheritance inheritanceForXml;

	/**
	 * The default value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedName = SPECIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected String defaultName = DEFAULT_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTable() <em>Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected ITable table;

	/**
	 * The cached value of the '{@link #getSpecifiedSecondaryTables() <em>Specified Secondary Tables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSecondaryTables()
	 * @generated
	 * @ordered
	 */
	protected EList<ISecondaryTable> specifiedSecondaryTables;

	/**
	 * The cached value of the '{@link #getSpecifiedPrimaryKeyJoinColumns() <em>Specified Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;

	/**
	 * The cached value of the '{@link #getDefaultPrimaryKeyJoinColumns() <em>Default Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	/**
	 * The default value of the '{@link #getInheritanceStrategy() <em>Inheritance Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInheritanceStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final InheritanceType INHERITANCE_STRATEGY_EDEFAULT = InheritanceType.DEFAULT;

	/**
	 * The cached value of the '{@link #getInheritanceStrategy() <em>Inheritance Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInheritanceStrategy()
	 * @generated
	 * @ordered
	 */
	protected InheritanceType inheritanceStrategy = INHERITANCE_STRATEGY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultDiscriminatorValue() <em>Default Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultDiscriminatorValue() <em>Default Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultDiscriminatorValue = DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecifiedDiscriminatorValue() <em>Specified Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedDiscriminatorValue() <em>Specified Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected String specifiedDiscriminatorValue = SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDiscriminatorValue() <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DISCRIMINATOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiscriminatorColumn() <em>Discriminator Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorColumn()
	 * @generated
	 * @ordered
	 */
	protected IDiscriminatorColumn discriminatorColumn;

	/**
	 * The cached value of the '{@link #getSequenceGenerator() <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceGenerator()
	 * @generated
	 * @ordered
	 */
	protected ISequenceGenerator sequenceGenerator;

	/**
	 * The cached value of the '{@link #getTableGenerator() <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableGenerator()
	 * @generated
	 * @ordered
	 */
	protected ITableGenerator tableGenerator;

	/**
	 * The cached value of the '{@link #getSpecifiedAttributeOverrides() <em>Specified Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAttributeOverride> specifiedAttributeOverrides;

	/**
	 * The cached value of the '{@link #getDefaultAttributeOverrides() <em>Default Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAttributeOverride> defaultAttributeOverrides;

	/**
	 * The cached value of the '{@link #getSpecifiedAssociationOverrides() <em>Specified Association Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAssociationOverride> specifiedAssociationOverrides;

	/**
	 * The cached value of the '{@link #getDefaultAssociationOverrides() <em>Default Association Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<IAssociationOverride> defaultAssociationOverrides;

	/**
	 * The cached value of the '{@link #getNamedQueries() <em>Named Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<INamedQuery> namedQueries;

	/**
	 * The cached value of the '{@link #getNamedNativeQueries() <em>Named Native Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedNativeQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<INamedNativeQuery> namedNativeQueries;

	/**
	 * The default value of the '{@link #getIdClass() <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClass()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIdClass() <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClass()
	 * @generated
	 * @ordered
	 */
	protected String idClass = ID_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getVirtualSecondaryTables() <em>Virtual Secondary Tables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVirtualSecondaryTables()
	 * @generated
	 * @ordered
	 */
	protected EList<ISecondaryTable> virtualSecondaryTables;

	protected XmlEntityInternal() {
		super();
		this.table = OrmFactory.eINSTANCE.createXmlTable(buildTableOwner());
		((InternalEObject) this.table).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__TABLE, null, null);
		this.discriminatorColumn = OrmFactory.eINSTANCE.createXmlDiscriminatorColumn(new IDiscriminatorColumn.Owner(this));
		((InternalEObject) this.discriminatorColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN, null, null);
		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
		this.eAdapters().add(this.buildListener());
	}

	protected Adapter buildListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				XmlEntityInternal.this.notifyChanged(notification);
			}
		};
	}

	protected void notifyChanged(Notification notification) {
		switch (notification.getFeatureID(IEntity.class)) {
			case JpaCoreMappingsPackage.IENTITY__ID_CLASS :
				idClassChanged();
				break;
			case JpaCoreMappingsPackage.IENTITY__INHERITANCE_STRATEGY :
				inheritanceStrategyChanged();
				break;
			default :
				break;
		}
		switch (notification.getFeatureID(XmlEntityForXml.class)) {
			case OrmPackage.XML_ENTITY_FOR_XML__ID_CLASS_FOR_XML :
				xmlIdClassChanged();
				break;
			case OrmPackage.XML_ENTITY_FOR_XML__INHERITANCE_FOR_XML :
				xmlInheritanceChanged();
				break;
			default :
				break;
		}
	}

	protected void inheritanceStrategyChanged() {
		if (getInheritanceStrategy() == InheritanceType.DEFAULT) {
			setInheritanceForXml(null);
		}
		else {
			if (getInheritanceForXml() == null) {
				setInheritanceForXml(OrmFactory.eINSTANCE.createXmlInheritance());
			}
			getInheritanceForXml().setStrategy(getInheritanceStrategy());
		}
	}

	protected void xmlInheritanceChanged() {
		if (getInheritanceForXml() == null) {
			setInheritanceStrategy(null);
		}
	}

	private ITable.Owner buildTableOwner() {
		return new ITable.Owner() {
			public ITextRange validationTextRange() {
				return XmlEntityInternal.this.validationTextRange();
			}

			public ITypeMapping getTypeMapping() {
				return XmlEntityInternal.this;
			}
		};
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_ENTITY_INTERNAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		return (this.getSpecifiedName() == null) ? getDefaultName() : this.getSpecifiedName();
	}

	/**
	 * Returns the value of the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Name</em>' attribute.
	 * @see #setSpecifiedName(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_SpecifiedName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedName() {
		return specifiedName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = specifiedName;
		specifiedName = newSpecifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_NAME, oldSpecifiedName, specifiedName));
	}

	/**
	 * Returns the value of the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_DefaultName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultName() {
		return defaultName;
	}

	/**
	 * Returns the value of the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_Table()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public ITable getTable() {
		return table;
	}

	private XmlTable getTableInternal() {
		return (XmlTable) getTable();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTable(ITable newTable, NotificationChain msgs) {
		ITable oldTable = table;
		table = newTable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__TABLE, oldTable, newTable);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntity_SecondaryTables()
	 * @model type="org.eclipse.jpt.core.internal.mappings.ISecondaryTable" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public EList<ISecondaryTable> getSecondaryTables() {
		EList<ISecondaryTable> list = new EObjectContainmentEList<ISecondaryTable>(ISecondaryTable.class, this, OrmPackage.XML_ENTITY_INTERNAL__SECONDARY_TABLES);
		list.addAll(getSpecifiedSecondaryTables());
		list.addAll(getVirtualSecondaryTables());
		return list;
	}

	/**
	 * Returns the value of the '<em><b>Virtual Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Virtual Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntity_VirtualSecondaryTables()
	 * @model type="org.eclipse.jpt.core.internal.mappings.ISecondaryTable" containment="true"
	 * @generated
	 */
	public EList<ISecondaryTable> getVirtualSecondaryTables() {
		if (virtualSecondaryTables == null) {
			virtualSecondaryTables = new EObjectContainmentEList<ISecondaryTable>(ISecondaryTable.class, this, OrmPackage.XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES);
		}
		return virtualSecondaryTables;
	}

	protected void xmlIdClassChanged() {
		if (getIdClassForXml() == null) {
			setIdClass(null);
		}
	}

	public boolean containsSecondaryTable(String name) {
		return containsSecondaryTable(name, getSecondaryTables());
	}

	public boolean containsSpecifiedSecondaryTable(String name) {
		return containsSecondaryTable(name, getSpecifiedSecondaryTables());
	}

	private boolean containsSecondaryTable(String name, List<ISecondaryTable> secondaryTables) {
		for (ISecondaryTable secondaryTable : secondaryTables) {
			String secondaryTableName = secondaryTable.getName();
			if (secondaryTableName != null && secondaryTableName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the value of the '<em><b>Specified Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_SpecifiedSecondaryTables()
	 * @model type="org.eclipse.jpt.core.internal.mappings.ISecondaryTable" containment="true"
	 * @generated
	 */
	public EList<ISecondaryTable> getSpecifiedSecondaryTables() {
		if (specifiedSecondaryTables == null) {
			specifiedSecondaryTables = new EObjectContainmentEList<ISecondaryTable>(ISecondaryTable.class, this, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES);
		}
		return specifiedSecondaryTables;
	}

	/**
	 * Returns the value of the '<em><b>Inheritance Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.InheritanceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inheritance Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inheritance Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @see #setInheritanceStrategy(InheritanceType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_InheritanceStrategy()
	 * @model
	 * @generated
	 */
	public InheritanceType getInheritanceStrategy() {
		return inheritanceStrategy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getInheritanceStrategy <em>Inheritance Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inheritance Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.InheritanceType
	 * @see #getInheritanceStrategy()
	 * @generated
	 */
	public void setInheritanceStrategy(InheritanceType newInheritanceStrategy) {
		InheritanceType oldInheritanceStrategy = inheritanceStrategy;
		inheritanceStrategy = newInheritanceStrategy == null ? INHERITANCE_STRATEGY_EDEFAULT : newInheritanceStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_STRATEGY, oldInheritanceStrategy, inheritanceStrategy));
	}

	//	public void setInheritanceStrategy(InheritanceType newInheritanceStrategy) {
	//		setInheritanceStrategyGen(newInheritanceStrategy);
	//		if (newInheritanceStrategy != INHERITANCE_STRATEGY_EDEFAULT) {
	//			//makeInheritanceForXmlNonNull();
	//		}
	//		setInheritanceStrategyForXml(newInheritanceStrategy);
	//		if (isAllFeaturesUnset()) {
	//			//makeInheritanceForXmlNull();
	//		}
	//	}
	/**
	 * Returns the value of the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Column</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_DiscriminatorColumn()
	 * @model containment="true" changeable="false"
	 * @generated
	 */
	public IDiscriminatorColumn getDiscriminatorColumn() {
		return discriminatorColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDiscriminatorColumn(IDiscriminatorColumn newDiscriminatorColumn, NotificationChain msgs) {
		IDiscriminatorColumn oldDiscriminatorColumn = discriminatorColumn;
		discriminatorColumn = newDiscriminatorColumn;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN, oldDiscriminatorColumn, newDiscriminatorColumn);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #setSequenceGenerator(ISequenceGenerator)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_SequenceGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public ISequenceGenerator getSequenceGenerator() {
		return sequenceGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSequenceGenerator(ISequenceGenerator newSequenceGenerator, NotificationChain msgs) {
		ISequenceGenerator oldSequenceGenerator = sequenceGenerator;
		sequenceGenerator = newSequenceGenerator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getSequenceGenerator <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public void setSequenceGenerator(ISequenceGenerator newSequenceGenerator) {
		if (newSequenceGenerator != sequenceGenerator) {
			NotificationChain msgs = null;
			if (sequenceGenerator != null)
				msgs = ((InternalEObject) sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR, null, msgs);
			if (newSequenceGenerator != null)
				msgs = ((InternalEObject) newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR, null, msgs);
			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generator</em>' containment reference.
	 * @see #setTableGenerator(ITableGenerator)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_TableGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public ITableGenerator getTableGenerator() {
		return tableGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTableGenerator(ITableGenerator newTableGenerator, NotificationChain msgs) {
		ITableGenerator oldTableGenerator = tableGenerator;
		tableGenerator = newTableGenerator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getTableGenerator <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Generator</em>' containment reference.
	 * @see #getTableGenerator()
	 * @generated
	 */
	public void setTableGenerator(ITableGenerator newTableGenerator) {
		if (newTableGenerator != tableGenerator) {
			NotificationChain msgs = null;
			if (tableGenerator != null)
				msgs = ((InternalEObject) tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR, null, msgs);
			if (newTableGenerator != null)
				msgs = ((InternalEObject) newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR, null, msgs);
			msgs = basicSetTableGenerator(newTableGenerator, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Default Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Discriminator Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Discriminator Value</em>' attribute.
	 * @see #setDefaultDiscriminatorValue(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_DefaultDiscriminatorValue()
	 * @model
	 * @generated
	 */
	public String getDefaultDiscriminatorValue() {
		return defaultDiscriminatorValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getDefaultDiscriminatorValue <em>Default Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Discriminator Value</em>' attribute.
	 * @see #getDefaultDiscriminatorValue()
	 * @generated
	 */
	public void setDefaultDiscriminatorValue(String newDefaultDiscriminatorValue) {
		String oldDefaultDiscriminatorValue = defaultDiscriminatorValue;
		defaultDiscriminatorValue = newDefaultDiscriminatorValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_DISCRIMINATOR_VALUE, oldDefaultDiscriminatorValue, defaultDiscriminatorValue));
	}

	/**
	 * Returns the value of the '<em><b>Specified Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Discriminator Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Discriminator Value</em>' attribute.
	 * @see #setSpecifiedDiscriminatorValue(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_SpecifiedDiscriminatorValue()
	 * @model
	 * @generated
	 */
	public String getSpecifiedDiscriminatorValue() {
		return specifiedDiscriminatorValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getSpecifiedDiscriminatorValue <em>Specified Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Discriminator Value</em>' attribute.
	 * @see #getSpecifiedDiscriminatorValue()
	 * @generated
	 */
	public void setSpecifiedDiscriminatorValue(String newSpecifiedDiscriminatorValue) {
		String oldSpecifiedDiscriminatorValue = specifiedDiscriminatorValue;
		specifiedDiscriminatorValue = newSpecifiedDiscriminatorValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_DISCRIMINATOR_VALUE, oldSpecifiedDiscriminatorValue, specifiedDiscriminatorValue));
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Value</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_DiscriminatorValue()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getDiscriminatorValue() {
		return (this.getSpecifiedDiscriminatorValue() == null) ? getDefaultDiscriminatorValue() : this.getSpecifiedDiscriminatorValue();
	}

	public EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumns().isEmpty() ? this.getDefaultPrimaryKeyJoinColumns() : this.getSpecifiedPrimaryKeyJoinColumns();
	}

	/**
	 * Returns the value of the '<em><b>Specified Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_SpecifiedPrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		if (specifiedPrimaryKeyJoinColumns == null) {
			specifiedPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS);
		}
		return specifiedPrimaryKeyJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Default Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_DefaultPrimaryKeyJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		if (defaultPrimaryKeyJoinColumns == null) {
			defaultPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS);
		}
		return defaultPrimaryKeyJoinColumns;
	}

	public EList<IAttributeOverride> getAttributeOverrides() {
		EList<IAttributeOverride> list = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__ATTRIBUTE_OVERRIDES);
		list.addAll(getSpecifiedAttributeOverrides());
		list.addAll(getDefaultAttributeOverrides());
		return list;
	}

	/**
	 * Returns the value of the '<em><b>Specified Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Attribute Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_SpecifiedAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	public EList<IAttributeOverride> getSpecifiedAttributeOverrides() {
		if (specifiedAttributeOverrides == null) {
			specifiedAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES);
		}
		return specifiedAttributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Default Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Attribute Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_DefaultAttributeOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAttributeOverride" containment="true"
	 * @generated
	 */
	public EList<IAttributeOverride> getDefaultAttributeOverrides() {
		if (defaultAttributeOverrides == null) {
			defaultAttributeOverrides = new EObjectContainmentEList<IAttributeOverride>(IAttributeOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES);
		}
		return defaultAttributeOverrides;
	}

	public EList<IAssociationOverride> getAssociationOverrides() {
		EList<IAssociationOverride> list = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__ASSOCIATION_OVERRIDES);
		list.addAll(getSpecifiedAssociationOverrides());
		list.addAll(getDefaultAssociationOverrides());
		return list;
	}

	/**
	 * Returns the value of the '<em><b>Specified Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Association Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_SpecifiedAssociationOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAssociationOverride" containment="true"
	 * @generated
	 */
	public EList<IAssociationOverride> getSpecifiedAssociationOverrides() {
		if (specifiedAssociationOverrides == null) {
			specifiedAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES);
		}
		return specifiedAssociationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Default Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Association Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_DefaultAssociationOverrides()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IAssociationOverride" containment="true"
	 * @generated
	 */
	public EList<IAssociationOverride> getDefaultAssociationOverrides() {
		if (defaultAssociationOverrides == null) {
			defaultAssociationOverrides = new EObjectContainmentEList<IAssociationOverride>(IAssociationOverride.class, this, OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES);
		}
		return defaultAssociationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Named Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.INamedQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_NamedQueries()
	 * @model type="org.eclipse.jpt.core.internal.mappings.INamedQuery" containment="true"
	 * @generated
	 */
	public EList<INamedQuery> getNamedQueries() {
		if (namedQueries == null) {
			namedQueries = new EObjectContainmentEList<INamedQuery>(INamedQuery.class, this, OrmPackage.XML_ENTITY_INTERNAL__NAMED_QUERIES);
		}
		return namedQueries;
	}

	/**
	 * Returns the value of the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Native Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Native Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_NamedNativeQueries()
	 * @model type="org.eclipse.jpt.core.internal.mappings.INamedNativeQuery" containment="true"
	 * @generated
	 */
	public EList<INamedNativeQuery> getNamedNativeQueries() {
		if (namedNativeQueries == null) {
			namedNativeQueries = new EObjectContainmentEList<INamedNativeQuery>(INamedNativeQuery.class, this, OrmPackage.XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES);
		}
		return namedNativeQueries;
	}

	/**
	 * Returns the value of the '<em><b>Id Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Class</em>' attribute.
	 * @see #setIdClass(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIEntity_IdClass()
	 * @model
	 * @generated
	 */
	public String getIdClass() {
		return idClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getIdClass <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class</em>' attribute.
	 * @see #getIdClass()
	 * @generated
	 */
	public void setIdClass(String newIdClass) {
		String oldIdClass = idClass;
		idClass = newIdClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS, oldIdClass, idClass));
	}

	protected void idClassChanged() {
		if (getIdClass() == null) {
			setIdClassForXml(null);
		}
		else {
			if (getIdClassForXml() == null) {
				setIdClassForXml(OrmFactory.eINSTANCE.createXmlIdClass());
			}
			getIdClassForXml().setValue(getIdClass());
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public boolean discriminatorValueIsAllowed() {
		Type type = getPersistentType().findType();
		return (type == null) ? false : type.isAbstract();
	}

	public IEntity parentEntity() {
		for (Iterator<IPersistentType> i = getPersistentType().inheritanceHierarchy(); i.hasNext();) {
			ITypeMapping typeMapping = i.next().getMapping();
			if (typeMapping != this && typeMapping instanceof IEntity) {
				return (IEntity) typeMapping;
			}
		}
		return this;
	}

	public IEntity rootEntity() {
		IEntity rootEntity = null;
		for (Iterator<IPersistentType> i = getPersistentType().inheritanceHierarchy(); i.hasNext();) {
			IPersistentType persistentType = i.next();
			if (persistentType.getMapping() instanceof IEntity) {
				rootEntity = (IEntity) persistentType.getMapping();
			}
		}
		return rootEntity;
	}

	/**
	 * Returns the value of the '<em><b>Table For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table For Xml</em>' reference.
	 * @see #setTableForXml(XmlTable)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml_TableForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated NOT
	 */
	public XmlTable getTableForXml() {
		if (getTableInternal().isAllFeaturesUnset()) {
			return null;
		}
		return getTableInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getTableForXml <em>Table For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table For Xml</em>' reference.
	 * @see #getTableForXml()
	 * @generated NOT
	 */
	public void setTableForXmlGen(XmlTable newTableForXml) {
		XmlTable oldValue = newTableForXml == null ? (XmlTable) getTable() : null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__TABLE_FOR_XML, oldValue, newTableForXml));
	}

	public void setTableForXml(XmlTable newTableForXml) {
		setTableForXmlGen(newTableForXml);
		if (newTableForXml == null) {
			getTableInternal().unsetAllAttributes();
		}
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Column For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Column For Xml</em>' reference.
	 * @see #setDiscriminatorColumnForXml(XmlDiscriminatorColumn)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml_DiscriminatorColumnForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated NOT
	 */
	public XmlDiscriminatorColumn getDiscriminatorColumnForXml() {
		if (getDiscriminatorColumnInternal().isAllFeaturesUnset()) {
			return null;
		}
		return getDiscriminatorColumnInternal();
	}

	private XmlDiscriminatorColumn getDiscriminatorColumnInternal() {
		return (XmlDiscriminatorColumn) getDiscriminatorColumn();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getDiscriminatorColumnForXml <em>Discriminator Column For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Column For Xml</em>' reference.
	 * @see #getDiscriminatorColumnForXml()
	 * @generated NOT
	 */
	public void setDiscriminatorColumnForXmlGen(XmlDiscriminatorColumn newDiscriminatorColumnForXml) {
		XmlDiscriminatorColumn oldValue = newDiscriminatorColumnForXml == null ? (XmlDiscriminatorColumn) getDiscriminatorColumn() : null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN_FOR_XML, oldValue, newDiscriminatorColumnForXml));
	}

	public void setDiscriminatorColumnForXml(XmlDiscriminatorColumn newDiscriminatorColumnForXml) {
		setDiscriminatorColumnForXmlGen(newDiscriminatorColumnForXml);
		if (newDiscriminatorColumnForXml == null) {
			getDiscriminatorColumnInternal().unsetAllAttributes();
		}
	}

	/**
	 * Returns the value of the '<em><b>Id Class For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Class For Xml</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Class For Xml</em>' containment reference.
	 * @see #setIdClassForXml(XmlIdClass)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml_IdClassForXml()
	 * @model containment="true"
	 * @generated
	 */
	public XmlIdClass getIdClassForXml() {
		return idClassForXml;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdClassForXml(XmlIdClass newIdClassForXml, NotificationChain msgs) {
		XmlIdClass oldIdClassForXml = idClassForXml;
		idClassForXml = newIdClassForXml;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML, oldIdClassForXml, newIdClassForXml);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getIdClassForXml <em>Id Class For Xml</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class For Xml</em>' containment reference.
	 * @see #getIdClassForXml()
	 * @generated
	 */
	public void setIdClassForXml(XmlIdClass newIdClassForXml) {
		if (newIdClassForXml != idClassForXml) {
			NotificationChain msgs = null;
			if (idClassForXml != null)
				msgs = ((InternalEObject) idClassForXml).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML, null, msgs);
			if (newIdClassForXml != null)
				msgs = ((InternalEObject) newIdClassForXml).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML, null, msgs);
			msgs = basicSetIdClassForXml(newIdClassForXml, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML, newIdClassForXml, newIdClassForXml));
	}

	/**
	 * Returns the value of the '<em><b>Inheritance For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inheritance For Xml</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inheritance For Xml</em>' containment reference.
	 * @see #setInheritanceForXml(XmlInheritance)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml_InheritanceForXml()
	 * @model containment="true"
	 * @generated
	 */
	public XmlInheritance getInheritanceForXml() {
		return inheritanceForXml;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInheritanceForXml(XmlInheritance newInheritanceForXml, NotificationChain msgs) {
		XmlInheritance oldInheritanceForXml = inheritanceForXml;
		inheritanceForXml = newInheritanceForXml;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML, oldInheritanceForXml, newInheritanceForXml);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal#getInheritanceForXml <em>Inheritance For Xml</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inheritance For Xml</em>' containment reference.
	 * @see #getInheritanceForXml()
	 * @generated
	 */
	public void setInheritanceForXml(XmlInheritance newInheritanceForXml) {
		if (newInheritanceForXml != inheritanceForXml) {
			NotificationChain msgs = null;
			if (inheritanceForXml != null)
				msgs = ((InternalEObject) inheritanceForXml).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML, null, msgs);
			if (newInheritanceForXml != null)
				msgs = ((InternalEObject) newInheritanceForXml).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML, null, msgs);
			msgs = basicSetInheritanceForXml(newInheritanceForXml, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML, newInheritanceForXml, newInheritanceForXml));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML :
				return basicSetIdClassForXml(null, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML :
				return basicSetInheritanceForXml(null, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE :
				return basicSetTable(null, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES :
				return ((InternalEList<?>) getSpecifiedSecondaryTables()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__PRIMARY_KEY_JOIN_COLUMNS :
				return ((InternalEList<?>) getPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN :
				return basicSetDiscriminatorColumn(null, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR :
				return basicSetSequenceGenerator(null, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR :
				return basicSetTableGenerator(null, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getSpecifiedAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES :
				return ((InternalEList<?>) getDefaultAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__ASSOCIATION_OVERRIDES :
				return ((InternalEList<?>) getAssociationOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES :
				return ((InternalEList<?>) getSpecifiedAssociationOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES :
				return ((InternalEList<?>) getDefaultAssociationOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_QUERIES :
				return ((InternalEList<?>) getNamedQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES :
				return ((InternalEList<?>) getNamedNativeQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__SECONDARY_TABLES :
				return ((InternalEList<?>) getSecondaryTables()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES :
				return ((InternalEList<?>) getVirtualSecondaryTables()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_NAME, oldDefaultName, defaultName));
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_ENTITY_NAME_KEY));
	}

	public String getKey() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE_FOR_XML :
				return getTableForXml();
			case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN_FOR_XML :
				return getDiscriminatorColumnForXml();
			case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML :
				return getIdClassForXml();
			case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML :
				return getInheritanceForXml();
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_NAME :
				return getSpecifiedName();
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_NAME :
				return getDefaultName();
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE :
				return getTable();
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES :
				return getSpecifiedSecondaryTables();
			case OrmPackage.XML_ENTITY_INTERNAL__PRIMARY_KEY_JOIN_COLUMNS :
				return getPrimaryKeyJoinColumns();
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				return getSpecifiedPrimaryKeyJoinColumns();
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				return getDefaultPrimaryKeyJoinColumns();
			case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_STRATEGY :
				return getInheritanceStrategy();
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_DISCRIMINATOR_VALUE :
				return getDefaultDiscriminatorValue();
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_DISCRIMINATOR_VALUE :
				return getSpecifiedDiscriminatorValue();
			case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_VALUE :
				return getDiscriminatorValue();
			case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN :
				return getDiscriminatorColumn();
			case OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR :
				return getSequenceGenerator();
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR :
				return getTableGenerator();
			case OrmPackage.XML_ENTITY_INTERNAL__ATTRIBUTE_OVERRIDES :
				return getAttributeOverrides();
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return getSpecifiedAttributeOverrides();
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES :
				return getDefaultAttributeOverrides();
			case OrmPackage.XML_ENTITY_INTERNAL__ASSOCIATION_OVERRIDES :
				return getAssociationOverrides();
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES :
				return getSpecifiedAssociationOverrides();
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES :
				return getDefaultAssociationOverrides();
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_QUERIES :
				return getNamedQueries();
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES :
				return getNamedNativeQueries();
			case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS :
				return getIdClass();
			case OrmPackage.XML_ENTITY_INTERNAL__SECONDARY_TABLES :
				return getSecondaryTables();
			case OrmPackage.XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES :
				return getVirtualSecondaryTables();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE_FOR_XML :
				setTableForXml((XmlTable) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN_FOR_XML :
				setDiscriminatorColumnForXml((XmlDiscriminatorColumn) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML :
				setIdClassForXml((XmlIdClass) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML :
				setInheritanceForXml((XmlInheritance) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES :
				getSpecifiedSecondaryTables().clear();
				getSpecifiedSecondaryTables().addAll((Collection<? extends ISecondaryTable>) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				getSpecifiedPrimaryKeyJoinColumns().clear();
				getSpecifiedPrimaryKeyJoinColumns().addAll((Collection<? extends IPrimaryKeyJoinColumn>) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				getDefaultPrimaryKeyJoinColumns().clear();
				getDefaultPrimaryKeyJoinColumns().addAll((Collection<? extends IPrimaryKeyJoinColumn>) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_STRATEGY :
				setInheritanceStrategy((InheritanceType) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_DISCRIMINATOR_VALUE :
				setDefaultDiscriminatorValue((String) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_DISCRIMINATOR_VALUE :
				setSpecifiedDiscriminatorValue((String) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR :
				setSequenceGenerator((ISequenceGenerator) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR :
				setTableGenerator((ITableGenerator) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES :
				getSpecifiedAttributeOverrides().clear();
				getSpecifiedAttributeOverrides().addAll((Collection<? extends IAttributeOverride>) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES :
				getDefaultAttributeOverrides().clear();
				getDefaultAttributeOverrides().addAll((Collection<? extends IAttributeOverride>) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES :
				getSpecifiedAssociationOverrides().clear();
				getSpecifiedAssociationOverrides().addAll((Collection<? extends IAssociationOverride>) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES :
				getDefaultAssociationOverrides().clear();
				getDefaultAssociationOverrides().addAll((Collection<? extends IAssociationOverride>) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_QUERIES :
				getNamedQueries().clear();
				getNamedQueries().addAll((Collection<? extends INamedQuery>) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES :
				getNamedNativeQueries().clear();
				getNamedNativeQueries().addAll((Collection<? extends INamedNativeQuery>) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS :
				setIdClass((String) newValue);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES :
				getVirtualSecondaryTables().clear();
				getVirtualSecondaryTables().addAll((Collection<? extends ISecondaryTable>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE_FOR_XML :
				setTableForXml((XmlTable) null);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN_FOR_XML :
				setDiscriminatorColumnForXml((XmlDiscriminatorColumn) null);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML :
				setIdClassForXml((XmlIdClass) null);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML :
				setInheritanceForXml((XmlInheritance) null);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_NAME :
				setSpecifiedName(SPECIFIED_NAME_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES :
				getSpecifiedSecondaryTables().clear();
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				getSpecifiedPrimaryKeyJoinColumns().clear();
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				getDefaultPrimaryKeyJoinColumns().clear();
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_STRATEGY :
				setInheritanceStrategy(INHERITANCE_STRATEGY_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_DISCRIMINATOR_VALUE :
				setDefaultDiscriminatorValue(DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_DISCRIMINATOR_VALUE :
				setSpecifiedDiscriminatorValue(SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR :
				setSequenceGenerator((ISequenceGenerator) null);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR :
				setTableGenerator((ITableGenerator) null);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES :
				getSpecifiedAttributeOverrides().clear();
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES :
				getDefaultAttributeOverrides().clear();
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES :
				getSpecifiedAssociationOverrides().clear();
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES :
				getDefaultAssociationOverrides().clear();
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_QUERIES :
				getNamedQueries().clear();
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES :
				getNamedNativeQueries().clear();
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS :
				setIdClass(ID_CLASS_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES :
				getVirtualSecondaryTables().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE_FOR_XML :
				return getTableForXml() != null;
			case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN_FOR_XML :
				return getDiscriminatorColumnForXml() != null;
			case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML :
				return idClassForXml != null;
			case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML :
				return inheritanceForXml != null;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_NAME :
				return SPECIFIED_NAME_EDEFAULT == null ? specifiedName != null : !SPECIFIED_NAME_EDEFAULT.equals(specifiedName);
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_NAME :
				return DEFAULT_NAME_EDEFAULT == null ? defaultName != null : !DEFAULT_NAME_EDEFAULT.equals(defaultName);
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE :
				return table != null;
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES :
				return specifiedSecondaryTables != null && !specifiedSecondaryTables.isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__PRIMARY_KEY_JOIN_COLUMNS :
				return !getPrimaryKeyJoinColumns().isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
				return specifiedPrimaryKeyJoinColumns != null && !specifiedPrimaryKeyJoinColumns.isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
				return defaultPrimaryKeyJoinColumns != null && !defaultPrimaryKeyJoinColumns.isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_STRATEGY :
				return inheritanceStrategy != INHERITANCE_STRATEGY_EDEFAULT;
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_DISCRIMINATOR_VALUE :
				return DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT == null ? defaultDiscriminatorValue != null : !DEFAULT_DISCRIMINATOR_VALUE_EDEFAULT.equals(defaultDiscriminatorValue);
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_DISCRIMINATOR_VALUE :
				return SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT == null ? specifiedDiscriminatorValue != null : !SPECIFIED_DISCRIMINATOR_VALUE_EDEFAULT.equals(specifiedDiscriminatorValue);
			case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_VALUE :
				return DISCRIMINATOR_VALUE_EDEFAULT == null ? getDiscriminatorValue() != null : !DISCRIMINATOR_VALUE_EDEFAULT.equals(getDiscriminatorValue());
			case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN :
				return discriminatorColumn != null;
			case OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR :
				return sequenceGenerator != null;
			case OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR :
				return tableGenerator != null;
			case OrmPackage.XML_ENTITY_INTERNAL__ATTRIBUTE_OVERRIDES :
				return !getAttributeOverrides().isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES :
				return specifiedAttributeOverrides != null && !specifiedAttributeOverrides.isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES :
				return defaultAttributeOverrides != null && !defaultAttributeOverrides.isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__ASSOCIATION_OVERRIDES :
				return !getAssociationOverrides().isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES :
				return specifiedAssociationOverrides != null && !specifiedAssociationOverrides.isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES :
				return defaultAssociationOverrides != null && !defaultAssociationOverrides.isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_QUERIES :
				return namedQueries != null && !namedQueries.isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES :
				return namedNativeQueries != null && !namedNativeQueries.isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS :
				return ID_CLASS_EDEFAULT == null ? idClass != null : !ID_CLASS_EDEFAULT.equals(idClass);
			case OrmPackage.XML_ENTITY_INTERNAL__SECONDARY_TABLES :
				return !getSecondaryTables().isEmpty();
			case OrmPackage.XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES :
				return virtualSecondaryTables != null && !virtualSecondaryTables.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == XmlEntityForXml.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_ENTITY_INTERNAL__TABLE_FOR_XML :
					return OrmPackage.XML_ENTITY_FOR_XML__TABLE_FOR_XML;
				case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN_FOR_XML :
					return OrmPackage.XML_ENTITY_FOR_XML__DISCRIMINATOR_COLUMN_FOR_XML;
				case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML :
					return OrmPackage.XML_ENTITY_FOR_XML__ID_CLASS_FOR_XML;
				case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML :
					return OrmPackage.XML_ENTITY_FOR_XML__INHERITANCE_FOR_XML;
				default :
					return -1;
			}
		}
		if (baseClass == IEntity.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_NAME :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_NAME;
				case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_NAME :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_NAME;
				case OrmPackage.XML_ENTITY_INTERNAL__TABLE :
					return JpaCoreMappingsPackage.IENTITY__TABLE;
				case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_SECONDARY_TABLES;
				case OrmPackage.XML_ENTITY_INTERNAL__PRIMARY_KEY_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IENTITY__PRIMARY_KEY_JOIN_COLUMNS;
				case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS;
				case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS;
				case OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_STRATEGY :
					return JpaCoreMappingsPackage.IENTITY__INHERITANCE_STRATEGY;
				case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_DISCRIMINATOR_VALUE :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_DISCRIMINATOR_VALUE;
				case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_DISCRIMINATOR_VALUE :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_DISCRIMINATOR_VALUE;
				case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_VALUE :
					return JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_VALUE;
				case OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN :
					return JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_COLUMN;
				case OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR :
					return JpaCoreMappingsPackage.IENTITY__SEQUENCE_GENERATOR;
				case OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR :
					return JpaCoreMappingsPackage.IENTITY__TABLE_GENERATOR;
				case OrmPackage.XML_ENTITY_INTERNAL__ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__ATTRIBUTE_OVERRIDES;
				case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES;
				case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_ATTRIBUTE_OVERRIDES;
				case OrmPackage.XML_ENTITY_INTERNAL__ASSOCIATION_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__ASSOCIATION_OVERRIDES;
				case OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES;
				case OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES :
					return JpaCoreMappingsPackage.IENTITY__DEFAULT_ASSOCIATION_OVERRIDES;
				case OrmPackage.XML_ENTITY_INTERNAL__NAMED_QUERIES :
					return JpaCoreMappingsPackage.IENTITY__NAMED_QUERIES;
				case OrmPackage.XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES :
					return JpaCoreMappingsPackage.IENTITY__NAMED_NATIVE_QUERIES;
				case OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS :
					return JpaCoreMappingsPackage.IENTITY__ID_CLASS;
				default :
					return -1;
			}
		}
		if (baseClass == XmlEntity.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_ENTITY_INTERNAL__SECONDARY_TABLES :
					return OrmPackage.XML_ENTITY__SECONDARY_TABLES;
				case OrmPackage.XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES :
					return OrmPackage.XML_ENTITY__VIRTUAL_SECONDARY_TABLES;
				default :
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == XmlEntityForXml.class) {
			switch (baseFeatureID) {
				case OrmPackage.XML_ENTITY_FOR_XML__TABLE_FOR_XML :
					return OrmPackage.XML_ENTITY_INTERNAL__TABLE_FOR_XML;
				case OrmPackage.XML_ENTITY_FOR_XML__DISCRIMINATOR_COLUMN_FOR_XML :
					return OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN_FOR_XML;
				case OrmPackage.XML_ENTITY_FOR_XML__ID_CLASS_FOR_XML :
					return OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS_FOR_XML;
				case OrmPackage.XML_ENTITY_FOR_XML__INHERITANCE_FOR_XML :
					return OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_FOR_XML;
				default :
					return -1;
			}
		}
		if (baseClass == IEntity.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_NAME :
					return OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_NAME;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_NAME :
					return OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_NAME;
				case JpaCoreMappingsPackage.IENTITY__TABLE :
					return OrmPackage.XML_ENTITY_INTERNAL__TABLE;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_SECONDARY_TABLES :
					return OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_SECONDARY_TABLES;
				case JpaCoreMappingsPackage.IENTITY__PRIMARY_KEY_JOIN_COLUMNS :
					return OrmPackage.XML_ENTITY_INTERNAL__PRIMARY_KEY_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS :
					return OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS :
					return OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IENTITY__INHERITANCE_STRATEGY :
					return OrmPackage.XML_ENTITY_INTERNAL__INHERITANCE_STRATEGY;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_DISCRIMINATOR_VALUE :
					return OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_DISCRIMINATOR_VALUE;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_DISCRIMINATOR_VALUE :
					return OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_DISCRIMINATOR_VALUE;
				case JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_VALUE :
					return OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_VALUE;
				case JpaCoreMappingsPackage.IENTITY__DISCRIMINATOR_COLUMN :
					return OrmPackage.XML_ENTITY_INTERNAL__DISCRIMINATOR_COLUMN;
				case JpaCoreMappingsPackage.IENTITY__SEQUENCE_GENERATOR :
					return OrmPackage.XML_ENTITY_INTERNAL__SEQUENCE_GENERATOR;
				case JpaCoreMappingsPackage.IENTITY__TABLE_GENERATOR :
					return OrmPackage.XML_ENTITY_INTERNAL__TABLE_GENERATOR;
				case JpaCoreMappingsPackage.IENTITY__ATTRIBUTE_OVERRIDES :
					return OrmPackage.XML_ENTITY_INTERNAL__ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_ATTRIBUTE_OVERRIDES :
					return OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_ATTRIBUTE_OVERRIDES :
					return OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ATTRIBUTE_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__ASSOCIATION_OVERRIDES :
					return OrmPackage.XML_ENTITY_INTERNAL__ASSOCIATION_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__SPECIFIED_ASSOCIATION_OVERRIDES :
					return OrmPackage.XML_ENTITY_INTERNAL__SPECIFIED_ASSOCIATION_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__DEFAULT_ASSOCIATION_OVERRIDES :
					return OrmPackage.XML_ENTITY_INTERNAL__DEFAULT_ASSOCIATION_OVERRIDES;
				case JpaCoreMappingsPackage.IENTITY__NAMED_QUERIES :
					return OrmPackage.XML_ENTITY_INTERNAL__NAMED_QUERIES;
				case JpaCoreMappingsPackage.IENTITY__NAMED_NATIVE_QUERIES :
					return OrmPackage.XML_ENTITY_INTERNAL__NAMED_NATIVE_QUERIES;
				case JpaCoreMappingsPackage.IENTITY__ID_CLASS :
					return OrmPackage.XML_ENTITY_INTERNAL__ID_CLASS;
				default :
					return -1;
			}
		}
		if (baseClass == XmlEntity.class) {
			switch (baseFeatureID) {
				case OrmPackage.XML_ENTITY__SECONDARY_TABLES :
					return OrmPackage.XML_ENTITY_INTERNAL__SECONDARY_TABLES;
				case OrmPackage.XML_ENTITY__VIRTUAL_SECONDARY_TABLES :
					return OrmPackage.XML_ENTITY_INTERNAL__VIRTUAL_SECONDARY_TABLES;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (specifiedName: ");
		result.append(specifiedName);
		result.append(", defaultName: ");
		result.append(defaultName);
		result.append(", inheritanceStrategy: ");
		result.append(inheritanceStrategy);
		result.append(", defaultDiscriminatorValue: ");
		result.append(defaultDiscriminatorValue);
		result.append(", specifiedDiscriminatorValue: ");
		result.append(specifiedDiscriminatorValue);
		result.append(", idClass: ");
		result.append(idClass);
		result.append(')');
		return result.toString();
	}

	@Override
	public String getTableName() {
		return getTable().getName();
	}

	public void makeTableForXmlNull() {
		setTableForXmlGen(null);
	}

	public void makeTableForXmlNonNull() {
		setTableForXmlGen(getTableForXml());
	}

	public void makeDiscriminatorColumnForXmlNull() {
		setDiscriminatorColumnForXmlGen(null);
	}

	//um, this is an object on XmlInheritance, but a tag on entity in the xml, how to handle???
	public void makeDiscriminatorColumnForXmlNonNull() {
		setDiscriminatorColumnForXmlGen(getDiscriminatorColumnForXml());
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

	public String primaryKeyAttributeName() {
		String pkColumnName = null;
		String pkAttributeName = null;
		for (Iterator<IPersistentAttribute> stream = getPersistentType().allAttributes(); stream.hasNext();) {
			IPersistentAttribute attribute = stream.next();
			String name = attribute.primaryKeyColumnName();
			if (pkColumnName == null) {
				pkColumnName = name;
				pkAttributeName = attribute.getName();
			}
			else if (name != null) {
				// if we encounter a composite primary key, return null
				return null;
			}
		}
		// if we encounter only a single primary key column name, return it
		return pkAttributeName;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return !CollectionTools.contains(this.associatedTableNamesIncludingInherited(), tableName);
	}

	private Iterator<String> tableNames(Iterator<ITable> tables) {
		return new TransformationIterator(tables) {
			@Override
			protected Object transform(Object next) {
				return ((ITable) next).getName();
			}
		};
	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return this.nonNullTableNames(this.associatedTablesIncludingInherited());
	}

	private Iterator<String> nonNullTableNames(Iterator<ITable> tables) {
		return new FilteringIterator(this.tableNames(tables)) {
			@Override
			protected boolean accept(Object o) {
				return o != null;
			}
		};
	}

	public Iterator<ITable> associatedTables() {
		return new CompositeIterator(this.getTable(), this.getSecondaryTables().iterator());
	}

	public Iterator<ITable> associatedTablesIncludingInherited() {
		return new CompositeIterator(new TransformationIterator(this.inheritanceHierarchy()) {
			@Override
			protected Object transform(Object next) {
				return new FilteringIterator(((ITypeMapping) next).associatedTables()) {
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
	public int xmlSequence() {
		return 1;
	}

	/**
	 * Return an iterator of Entities, each which inherits from the one before,
	 * and terminates at the root entity (or at the point of cyclicity).
	 */
	private Iterator<ITypeMapping> inheritanceHierarchy() {
		return new TransformationIterator(getPersistentType().inheritanceHierarchy()) {
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

	public Iterator<String> overridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public IAttributeOverride createAttributeOverride(int index) {
		return OrmFactory.eINSTANCE.createXmlAttributeOverride(new IEntity.AttributeOverrideOwner(this));
	}

	public IAssociationOverride createAssociationOverride(int index) {
		return OrmFactory.eINSTANCE.createXmlAssociationOverride(new IEntity.AssociationOverrideOwner(this));
	}

	public boolean containsAttributeOverride(String name) {
		return containsAttributeOverride(name, getAttributeOverrides());
	}

	public boolean containsSpecifiedAttributeOverride(String name) {
		return containsAttributeOverride(name, getSpecifiedAttributeOverrides());
	}

	private boolean containsAttributeOverride(String name, List<IAttributeOverride> attributeOverrides) {
		for (IAttributeOverride attributeOverride : attributeOverrides) {
			String attributeOverrideName = attributeOverride.getName();
			if (attributeOverrideName != null && attributeOverrideName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsAssociationOverride(String name) {
		return containsAssociationOverride(name, getAssociationOverrides());
	}

	public boolean containsSpecifiedAssociationOverride(String name) {
		return containsAssociationOverride(name, getSpecifiedAssociationOverrides());
	}

	private boolean containsAssociationOverride(String name, List<IAssociationOverride> associationOverrides) {
		for (IAssociationOverride associationOverride : associationOverrides) {
			String overrideName = associationOverride.getName();
			if (overrideName != null && overrideName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public ISecondaryTable createSecondaryTable(int index) {
		return OrmFactory.eINSTANCE.createXmlSecondaryTable(buildSecondaryTableOwner());
	}

	private ITable.Owner buildSecondaryTableOwner() {
		return new ITable.Owner() {
			public ITextRange validationTextRange() {
				return XmlEntityInternal.this.validationTextRange();
			}

			public ITypeMapping getTypeMapping() {
				return XmlEntityInternal.this;
			}
		};
	}

	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
	}

	public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
		return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn(new IEntity.PrimaryKeyJoinColumnOwner(this));
	}

	public INamedQuery createNamedQuery(int index) {
		return OrmFactory.eINSTANCE.createXmlNamedQuery();
	}

	public INamedNativeQuery createNamedNativeQuery(int index) {
		return OrmFactory.eINSTANCE.createXmlNamedNativeQuery();
	}
}
