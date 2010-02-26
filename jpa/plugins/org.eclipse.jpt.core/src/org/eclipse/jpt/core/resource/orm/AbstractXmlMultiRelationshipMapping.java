/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMapKeyAttributeOverrideContainer_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderable_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.Translator;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Relationship Mapping</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getMapKey <em>Map Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlMultiRelationshipMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractXmlMultiRelationshipMapping extends AbstractXmlRelationshipMapping implements XmlMappedByMapping, XmlJoinTableMapping, XmlOrderable, XmlMultiRelationshipMapping_2_0
{
	/**
	 * The default value of the '{@link #getMappedBy() <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String MAPPED_BY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getMappedBy() <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedBy()
	 * @generated
	 * @ordered
	 */
	protected String mappedBy = MAPPED_BY_EDEFAULT;
	/**
	 * The cached value of the '{@link #getJoinTable() <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinTable()
	 * @generated
	 * @ordered
	 */
	protected XmlJoinTable joinTable;
	/**
	 * The cached value of the '{@link #getOrderColumn() <em>Order Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderColumn()
	 * @generated
	 * @ordered
	 */
	protected XmlOrderColumn orderColumn;
	/**
	 * The default value of the '{@link #getOrderBy() <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderBy()
	 * @generated
	 * @ordered
	 */
	protected static final String ORDER_BY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOrderBy() <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderBy()
	 * @generated
	 * @ordered
	 */
	protected String orderBy = ORDER_BY_EDEFAULT;
	/**
	 * The cached value of the '{@link #getMapKeyAttributeOverrides() <em>Map Key Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAttributeOverride> mapKeyAttributeOverrides;
	/**
	 * The cached value of the '{@link #getMapKeyClass() <em>Map Key Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyClass()
	 * @generated
	 * @ordered
	 */
	protected XmlMapKeyClass mapKeyClass;
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final TemporalType MAP_KEY_TEMPORAL_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getMapKeyTemporal() <em>Map Key Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyTemporal()
	 * @generated
	 * @ordered
	 */
	protected TemporalType mapKeyTemporal = MAP_KEY_TEMPORAL_EDEFAULT;
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final EnumType MAP_KEY_ENUMERATED_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getMapKeyEnumerated() <em>Map Key Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyEnumerated()
	 * @generated
	 * @ordered
	 */
	protected EnumType mapKeyEnumerated = MAP_KEY_ENUMERATED_EDEFAULT;
	/**
	 * The cached value of the '{@link #getMapKeyColumn() <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyColumn()
	 * @generated
	 * @ordered
	 */
	protected XmlColumn mapKeyColumn;
	/**
	 * The cached value of the '{@link #getMapKeyJoinColumns() <em>Map Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinColumn> mapKeyJoinColumns;
	/**
	 * The cached value of the '{@link #getMapKey() <em>Map Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKey()
	 * @generated
	 * @ordered
	 */
	protected MapKey mapKey;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractXmlMultiRelationshipMapping()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return OrmPackage.Literals.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING;
	}

	/**
	 * Returns the value of the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped By</em>' attribute.
	 * @see #setMappedBy(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedByMapping_MappedBy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMappedBy()
	{
		return mappedBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getMappedBy <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped By</em>' attribute.
	 * @see #getMappedBy()
	 * @generated
	 */
	public void setMappedBy(String newMappedBy)
	{
		String oldMappedBy = mappedBy;
		mappedBy = newMappedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY, oldMappedBy, mappedBy));
	}

	/**
	 * Returns the value of the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Table</em>' containment reference.
	 * @see #setJoinTable(XmlJoinTable)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTableMapping_JoinTable()
	 * @model containment="true"
	 * @generated
	 */
	public XmlJoinTable getJoinTable()
	{
		return joinTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinTable(XmlJoinTable newJoinTable, NotificationChain msgs)
	{
		XmlJoinTable oldJoinTable = joinTable;
		joinTable = newJoinTable;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE, oldJoinTable, newJoinTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getJoinTable <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Table</em>' containment reference.
	 * @see #getJoinTable()
	 * @generated
	 */
	public void setJoinTable(XmlJoinTable newJoinTable)
	{
		if (newJoinTable != joinTable)
		{
			NotificationChain msgs = null;
			if (joinTable != null)
				msgs = ((InternalEObject)joinTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE, null, msgs);
			if (newJoinTable != null)
				msgs = ((InternalEObject)newJoinTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE, null, msgs);
			msgs = basicSetJoinTable(newJoinTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE, newJoinTable, newJoinTable));
	}

	/**
	 * Returns the value of the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order Column</em>' containment reference.
	 * @see #setOrderColumn(XmlOrderColumn)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderable_2_0_OrderColumn()
	 * @model containment="true"
	 * @generated
	 */
	public XmlOrderColumn getOrderColumn()
	{
		return orderColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrderColumn(XmlOrderColumn newOrderColumn, NotificationChain msgs)
	{
		XmlOrderColumn oldOrderColumn = orderColumn;
		orderColumn = newOrderColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN, oldOrderColumn, newOrderColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getOrderColumn <em>Order Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order Column</em>' containment reference.
	 * @see #getOrderColumn()
	 * @generated
	 */
	public void setOrderColumn(XmlOrderColumn newOrderColumn)
	{
		if (newOrderColumn != orderColumn)
		{
			NotificationChain msgs = null;
			if (orderColumn != null)
				msgs = ((InternalEObject)orderColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN, null, msgs);
			if (newOrderColumn != null)
				msgs = ((InternalEObject)newOrderColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN, null, msgs);
			msgs = basicSetOrderColumn(newOrderColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN, newOrderColumn, newOrderColumn));
	}

	/**
	 * Returns the value of the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order By</em>' attribute.
	 * @see #setOrderBy(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderable_OrderBy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getOrderBy <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order By</em>' attribute.
	 * @see #getOrderBy()
	 * @generated
	 */
	public void setOrderBy(String newOrderBy) {
		String oldOrderBy = orderBy;
		orderBy = newOrderBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY, oldOrderBy, orderBy));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMapKeyAttributeOverrideContainer_2_0_MapKeyAttributeOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAttributeOverride> getMapKeyAttributeOverrides()
	{
		if (mapKeyAttributeOverrides == null)
		{
			mapKeyAttributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES);
		}
		return mapKeyAttributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Class</em>' containment reference.
	 * @see #setMapKeyClass(XmlMapKeyClass)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_2_0_MapKeyClass()
	 * @model containment="true"
	 * @generated
	 */
	public XmlMapKeyClass getMapKeyClass()
	{
		return mapKeyClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyClass(XmlMapKeyClass newMapKeyClass, NotificationChain msgs)
	{
		XmlMapKeyClass oldMapKeyClass = mapKeyClass;
		mapKeyClass = newMapKeyClass;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS, oldMapKeyClass, newMapKeyClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getMapKeyClass <em>Map Key Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Class</em>' containment reference.
	 * @see #getMapKeyClass()
	 * @generated
	 */
	public void setMapKeyClass(XmlMapKeyClass newMapKeyClass)
	{
		if (newMapKeyClass != mapKeyClass)
		{
			NotificationChain msgs = null;
			if (mapKeyClass != null)
				msgs = ((InternalEObject)mapKeyClass).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS, null, msgs);
			if (newMapKeyClass != null)
				msgs = ((InternalEObject)newMapKeyClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS, null, msgs);
			msgs = basicSetMapKeyClass(newMapKeyClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS, newMapKeyClass, newMapKeyClass));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #setMapKeyTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_2_0_MapKeyTemporal()
	 * @model
	 * @generated
	 */
	public TemporalType getMapKeyTemporal()
	{
		return mapKeyTemporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getMapKeyTemporal <em>Map Key Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #getMapKeyTemporal()
	 * @generated
	 */
	public void setMapKeyTemporal(TemporalType newMapKeyTemporal)
	{
		TemporalType oldMapKeyTemporal = mapKeyTemporal;
		mapKeyTemporal = newMapKeyTemporal == null ? MAP_KEY_TEMPORAL_EDEFAULT : newMapKeyTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL, oldMapKeyTemporal, mapKeyTemporal));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #setMapKeyEnumerated(EnumType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_2_0_MapKeyEnumerated()
	 * @model
	 * @generated
	 */
	public EnumType getMapKeyEnumerated()
	{
		return mapKeyEnumerated;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getMapKeyEnumerated <em>Map Key Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #getMapKeyEnumerated()
	 * @generated
	 */
	public void setMapKeyEnumerated(EnumType newMapKeyEnumerated)
	{
		EnumType oldMapKeyEnumerated = mapKeyEnumerated;
		mapKeyEnumerated = newMapKeyEnumerated == null ? MAP_KEY_ENUMERATED_EDEFAULT : newMapKeyEnumerated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED, oldMapKeyEnumerated, mapKeyEnumerated));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Column</em>' containment reference.
	 * @see #setMapKeyColumn(XmlColumn)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_2_0_MapKeyColumn()
	 * @model containment="true"
	 * @generated
	 */
	public XmlColumn getMapKeyColumn()
	{
		return mapKeyColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyColumn(XmlColumn newMapKeyColumn, NotificationChain msgs)
	{
		XmlColumn oldMapKeyColumn = mapKeyColumn;
		mapKeyColumn = newMapKeyColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN, oldMapKeyColumn, newMapKeyColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getMapKeyColumn <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Column</em>' containment reference.
	 * @see #getMapKeyColumn()
	 * @generated
	 */
	public void setMapKeyColumn(XmlColumn newMapKeyColumn)
	{
		if (newMapKeyColumn != mapKeyColumn)
		{
			NotificationChain msgs = null;
			if (mapKeyColumn != null)
				msgs = ((InternalEObject)mapKeyColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN, null, msgs);
			if (newMapKeyColumn != null)
				msgs = ((InternalEObject)newMapKeyColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN, null, msgs);
			msgs = basicSetMapKeyColumn(newMapKeyColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN, newMapKeyColumn, newMapKeyColumn));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_2_0_MapKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinColumn> getMapKeyJoinColumns()
	{
		if (mapKeyJoinColumns == null)
		{
			mapKeyJoinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS);
		}
		return mapKeyJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key</em>' containment reference.
	 * @see #setMapKey(MapKey)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlMultiRelationshipMapping_MapKey()
	 * @model containment="true"
	 * @generated
	 */
	public MapKey getMapKey() {
		return mapKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKey(MapKey newMapKey, NotificationChain msgs)
	{
		MapKey oldMapKey = mapKey;
		mapKey = newMapKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY, oldMapKey, newMapKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping#getMapKey <em>Map Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key</em>' containment reference.
	 * @see #getMapKey()
	 * @generated
	 */
	public void setMapKey(MapKey newMapKey) {
		if (newMapKey != mapKey)
		{
			NotificationChain msgs = null;
			if (mapKey != null)
				msgs = ((InternalEObject)mapKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY, null, msgs);
			if (newMapKey != null)
				msgs = ((InternalEObject)newMapKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY, null, msgs);
			msgs = basicSetMapKey(newMapKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY, newMapKey, newMapKey));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE:
				return basicSetJoinTable(null, msgs);
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN:
				return basicSetOrderColumn(null, msgs);
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return ((InternalEList<?>)getMapKeyAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS:
				return basicSetMapKeyClass(null, msgs);
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN:
				return basicSetMapKeyColumn(null, msgs);
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getMapKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY:
				return basicSetMapKey(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY:
				return getMappedBy();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE:
				return getJoinTable();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN:
				return getOrderColumn();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY:
				return getOrderBy();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return getMapKeyAttributeOverrides();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS:
				return getMapKeyClass();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL:
				return getMapKeyTemporal();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED:
				return getMapKeyEnumerated();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN:
				return getMapKeyColumn();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS:
				return getMapKeyJoinColumns();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY:
				return getMapKey();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY:
				setMappedBy((String)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE:
				setJoinTable((XmlJoinTable)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN:
				setOrderColumn((XmlOrderColumn)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY:
				setOrderBy((String)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES:
				getMapKeyAttributeOverrides().clear();
				getMapKeyAttributeOverrides().addAll((Collection<? extends XmlAttributeOverride>)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS:
				setMapKeyClass((XmlMapKeyClass)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL:
				setMapKeyTemporal((TemporalType)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED:
				setMapKeyEnumerated((EnumType)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN:
				setMapKeyColumn((XmlColumn)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS:
				getMapKeyJoinColumns().clear();
				getMapKeyJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY:
				setMapKey((MapKey)newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY:
				setMappedBy(MAPPED_BY_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE:
				setJoinTable((XmlJoinTable)null);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN:
				setOrderColumn((XmlOrderColumn)null);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY:
				setOrderBy(ORDER_BY_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES:
				getMapKeyAttributeOverrides().clear();
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS:
				setMapKeyClass((XmlMapKeyClass)null);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL:
				setMapKeyTemporal(MAP_KEY_TEMPORAL_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED:
				setMapKeyEnumerated(MAP_KEY_ENUMERATED_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN:
				setMapKeyColumn((XmlColumn)null);
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS:
				getMapKeyJoinColumns().clear();
				return;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY:
				setMapKey((MapKey)null);
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY:
				return MAPPED_BY_EDEFAULT == null ? mappedBy != null : !MAPPED_BY_EDEFAULT.equals(mappedBy);
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE:
				return joinTable != null;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN:
				return orderColumn != null;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY:
				return ORDER_BY_EDEFAULT == null ? orderBy != null : !ORDER_BY_EDEFAULT.equals(orderBy);
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return mapKeyAttributeOverrides != null && !mapKeyAttributeOverrides.isEmpty();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS:
				return mapKeyClass != null;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL:
				return mapKeyTemporal != MAP_KEY_TEMPORAL_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED:
				return mapKeyEnumerated != MAP_KEY_ENUMERATED_EDEFAULT;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN:
				return mapKeyColumn != null;
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS:
				return mapKeyJoinColumns != null && !mapKeyJoinColumns.isEmpty();
			case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY:
				return mapKey != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlMappedByMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY: return OrmPackage.XML_MAPPED_BY_MAPPING__MAPPED_BY;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinTableMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE: return OrmPackage.XML_JOIN_TABLE_MAPPING__JOIN_TABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlOrderable_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN: return OrmV2_0Package.XML_ORDERABLE_20__ORDER_COLUMN;
				default: return -1;
			}
		}
		if (baseClass == XmlOrderable.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY: return OrmPackage.XML_ORDERABLE__ORDER_BY;
				default: return -1;
			}
		}
		if (baseClass == XmlMapKeyAttributeOverrideContainer_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES: return OrmV2_0Package.XML_MAP_KEY_ATTRIBUTE_OVERRIDE_CONTAINER_20__MAP_KEY_ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlMultiRelationshipMapping_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS: return OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_CLASS;
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL: return OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_TEMPORAL;
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED: return OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_ENUMERATED;
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN: return OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_COLUMN;
				case OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS: return OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_JOIN_COLUMNS;
				default: return -1;
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlMappedByMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_MAPPED_BY_MAPPING__MAPPED_BY: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinTableMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_JOIN_TABLE_MAPPING__JOIN_TABLE: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlOrderable_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_ORDERABLE_20__ORDER_COLUMN: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_COLUMN;
				default: return -1;
			}
		}
		if (baseClass == XmlOrderable.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ORDERABLE__ORDER_BY: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY;
				default: return -1;
			}
		}
		if (baseClass == XmlMapKeyAttributeOverrideContainer_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_MAP_KEY_ATTRIBUTE_OVERRIDE_CONTAINER_20__MAP_KEY_ATTRIBUTE_OVERRIDES: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlMultiRelationshipMapping_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_CLASS: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_CLASS;
				case OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_TEMPORAL: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_TEMPORAL;
				case OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_ENUMERATED: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_ENUMERATED;
				case OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_COLUMN: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_COLUMN;
				case OrmV2_0Package.XML_MULTI_RELATIONSHIP_MAPPING_20__MAP_KEY_JOIN_COLUMNS: return OrmPackage.ABSTRACT_XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY_JOIN_COLUMNS;
				default: return -1;
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
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mappedBy: ");
		result.append(mappedBy);
		result.append(", orderBy: ");
		result.append(orderBy);
		result.append(", mapKeyTemporal: ");
		result.append(mapKeyTemporal);
		result.append(", mapKeyEnumerated: ");
		result.append(mapKeyEnumerated);
		result.append(')');
		return result.toString();
	}
	
	public TextRange getMappedByTextRange() {
		return getAttributeTextRange(JPA.MAPPED_BY);
	}
	
	// ********** translators **********
	
	protected static Translator buildOrderByTranslator() {
		return new Translator(JPA.ORDER_BY, OrmPackage.eINSTANCE.getXmlOrderable_OrderBy());
	}
	
	protected static Translator buildMapKeyTranslator() {
		return MapKey.buildTranslator(JPA.MAP_KEY, OrmPackage.eINSTANCE.getAbstractXmlMultiRelationshipMapping_MapKey());
	}

}
