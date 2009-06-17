/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.resource.orm;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.jpt.core.internal.resource.xml.translators.BooleanTranslator;
import org.eclipse.jpt.core.internal.resource.xml.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlAccessHolder;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt2_0.core.MappingKeys2_0;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Element Collection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getTargetClass <em>Target Class</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getFetch <em>Fetch</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getOrderBy <em>Order By</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getOrderColumn <em>Order Column</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKey <em>Map Key</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyClass <em>Map Key Class</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyTemporal <em>Map Key Temporal</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyEnumerated <em>Map Key Enumerated</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyAttributeOverrides <em>Map Key Attribute Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyColumn <em>Map Key Column</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyJoinColumns <em>Map Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getColumn <em>Column</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getTemporal <em>Temporal</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getEnumerated <em>Enumerated</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#isLob <em>Lob</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getAttributeOverrides <em>Attribute Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getAssociationOverrides <em>Association Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getCollectionTable <em>Collection Table</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection()
 * @model kind="class"
 * @generated
 */
public class XmlElementCollection extends AbstractXmlAttributeMapping implements XmlAttributeMapping
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final AccessType ACCESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected AccessType access = ACCESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetClass() <em>Target Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetClass()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetClass() <em>Target Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetClass()
	 * @generated
	 * @ordered
	 */
	protected String targetClass = TARGET_CLASS_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final FetchType FETCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected FetchType fetch = FETCH_EDEFAULT;

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
	 * The cached value of the '{@link #getOrderColumn() <em>Order Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderColumn()
	 * @generated
	 * @ordered
	 */
	protected XmlOrderColumn orderColumn;

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
	 * The cached value of the '{@link #getMapKeyAttributeOverrides() <em>Map Key Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAttributeOverride> mapKeyAttributeOverrides;

	/**
	 * The cached value of the '{@link #getMapKeyColumn() <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyColumn()
	 * @generated
	 * @ordered
	 */
	protected XmlMapKeyColumn mapKeyColumn;

	/**
	 * The cached value of the '{@link #getMapKeyJoinColumns() <em>Map Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlMapKeyJoinColumn> mapKeyJoinColumns;

	/**
	 * The cached value of the '{@link #getColumn() <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected XmlColumn column;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final TemporalType TEMPORAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemporal() <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporal()
	 * @generated
	 * @ordered
	 */
	protected TemporalType temporal = TEMPORAL_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final EnumType ENUMERATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEnumerated() <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumerated()
	 * @generated
	 * @ordered
	 */
	protected EnumType enumerated = ENUMERATED_EDEFAULT;

	/**
	 * The default value of the '{@link #isLob() <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLob()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOB_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLob() <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLob()
	 * @generated
	 * @ordered
	 */
	protected boolean lob = LOB_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttributeOverrides() <em>Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAttributeOverride> attributeOverrides;

	/**
	 * The cached value of the '{@link #getAssociationOverrides() <em>Association Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAssociationOverride> associationOverrides;

	/**
	 * The cached value of the '{@link #getCollectionTable() <em>Collection Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollectionTable()
	 * @generated
	 * @ordered
	 */
	protected XmlCollectionTable collectionTable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlElementCollection()
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
		return Orm2_0Package.Literals.XML_ELEMENT_COLLECTION;
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlAccessHolder_Access()
	 * @model
	 * @generated
	 */
	public AccessType getAccess()
	{
		return access;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #getAccess()
	 * @generated
	 */
	public void setAccess(AccessType newAccess)
	{
		AccessType oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__ACCESS, oldAccess, access));
	}

	/**
	 * Returns the value of the '<em><b>Target Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Class</em>' attribute.
	 * @see #setTargetClass(String)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_TargetClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTargetClass()
	{
		return targetClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getTargetClass <em>Target Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Class</em>' attribute.
	 * @see #getTargetClass()
	 * @generated
	 */
	public void setTargetClass(String newTargetClass)
	{
		String oldTargetClass = targetClass;
		targetClass = newTargetClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__TARGET_CLASS, oldTargetClass, targetClass));
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.FetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.FetchType
	 * @see #setFetch(FetchType)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_Fetch()
	 * @model
	 * @generated
	 */
	public FetchType getFetch()
	{
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.FetchType
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(FetchType newFetch)
	{
		FetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__FETCH, oldFetch, fetch));
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
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_OrderBy()
	 * @model dataType="org.eclipse.jpt.core.resource.orm.OrderBy"
	 * @generated
	 */
	public String getOrderBy()
	{
		return orderBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getOrderBy <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order By</em>' attribute.
	 * @see #getOrderBy()
	 * @generated
	 */
	public void setOrderBy(String newOrderBy)
	{
		String oldOrderBy = orderBy;
		orderBy = newOrderBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_BY, oldOrderBy, orderBy));
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
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_OrderColumn()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_COLUMN, oldOrderColumn, newOrderColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getOrderColumn <em>Order Column</em>}' containment reference.
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
				msgs = ((InternalEObject)orderColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_COLUMN, null, msgs);
			if (newOrderColumn != null)
				msgs = ((InternalEObject)newOrderColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_COLUMN, null, msgs);
			msgs = basicSetOrderColumn(newOrderColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_COLUMN, newOrderColumn, newOrderColumn));
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
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_MapKey()
	 * @model containment="true"
	 * @generated
	 */
	public MapKey getMapKey()
	{
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY, oldMapKey, newMapKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKey <em>Map Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key</em>' containment reference.
	 * @see #getMapKey()
	 * @generated
	 */
	public void setMapKey(MapKey newMapKey)
	{
		if (newMapKey != mapKey)
		{
			NotificationChain msgs = null;
			if (mapKey != null)
				msgs = ((InternalEObject)mapKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY, null, msgs);
			if (newMapKey != null)
				msgs = ((InternalEObject)newMapKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY, null, msgs);
			msgs = basicSetMapKey(newMapKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY, newMapKey, newMapKey));
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
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_MapKeyClass()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS, oldMapKeyClass, newMapKeyClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyClass <em>Map Key Class</em>}' containment reference.
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
				msgs = ((InternalEObject)mapKeyClass).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS, null, msgs);
			if (newMapKeyClass != null)
				msgs = ((InternalEObject)newMapKeyClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS, null, msgs);
			msgs = basicSetMapKeyClass(newMapKeyClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS, newMapKeyClass, newMapKeyClass));
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
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_MapKeyTemporal()
	 * @model
	 * @generated
	 */
	public TemporalType getMapKeyTemporal()
	{
		return mapKeyTemporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyTemporal <em>Map Key Temporal</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL, oldMapKeyTemporal, mapKeyTemporal));
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
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_MapKeyEnumerated()
	 * @model
	 * @generated
	 */
	public EnumType getMapKeyEnumerated()
	{
		return mapKeyEnumerated;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyEnumerated <em>Map Key Enumerated</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED, oldMapKeyEnumerated, mapKeyEnumerated));
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
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_MapKeyAttributeOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAttributeOverride> getMapKeyAttributeOverrides()
	{
		if (mapKeyAttributeOverrides == null)
		{
			mapKeyAttributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES);
		}
		return mapKeyAttributeOverrides;
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
	 * @see #setMapKeyColumn(XmlMapKeyColumn)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_MapKeyColumn()
	 * @model containment="true"
	 * @generated
	 */
	public XmlMapKeyColumn getMapKeyColumn()
	{
		return mapKeyColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyColumn(XmlMapKeyColumn newMapKeyColumn, NotificationChain msgs)
	{
		XmlMapKeyColumn oldMapKeyColumn = mapKeyColumn;
		mapKeyColumn = newMapKeyColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN, oldMapKeyColumn, newMapKeyColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getMapKeyColumn <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Column</em>' containment reference.
	 * @see #getMapKeyColumn()
	 * @generated
	 */
	public void setMapKeyColumn(XmlMapKeyColumn newMapKeyColumn)
	{
		if (newMapKeyColumn != mapKeyColumn)
		{
			NotificationChain msgs = null;
			if (mapKeyColumn != null)
				msgs = ((InternalEObject)mapKeyColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN, null, msgs);
			if (newMapKeyColumn != null)
				msgs = ((InternalEObject)newMapKeyColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN, null, msgs);
			msgs = basicSetMapKeyColumn(newMapKeyColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN, newMapKeyColumn, newMapKeyColumn));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt2_0.core.resource.orm.XmlMapKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_MapKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlMapKeyJoinColumn> getMapKeyJoinColumns()
	{
		if (mapKeyJoinColumns == null)
		{
			mapKeyJoinColumns = new EObjectContainmentEList<XmlMapKeyJoinColumn>(XmlMapKeyJoinColumn.class, this, Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS);
		}
		return mapKeyJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column</em>' containment reference.
	 * @see #setColumn(XmlColumn)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_Column()
	 * @model containment="true"
	 * @generated
	 */
	public XmlColumn getColumn()
	{
		return column;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColumn(XmlColumn newColumn, NotificationChain msgs)
	{
		XmlColumn oldColumn = column;
		column = newColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__COLUMN, oldColumn, newColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getColumn <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column</em>' containment reference.
	 * @see #getColumn()
	 * @generated
	 */
	public void setColumn(XmlColumn newColumn)
	{
		if (newColumn != column)
		{
			NotificationChain msgs = null;
			if (column != null)
				msgs = ((InternalEObject)column).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__COLUMN, null, msgs);
			if (newColumn != null)
				msgs = ((InternalEObject)newColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__COLUMN, null, msgs);
			msgs = basicSetColumn(newColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__COLUMN, newColumn, newColumn));
	}

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_Temporal()
	 * @model
	 * @generated
	 */
	public TemporalType getTemporal()
	{
		return temporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	public void setTemporal(TemporalType newTemporal)
	{
		TemporalType oldTemporal = temporal;
		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__TEMPORAL, oldTemporal, temporal));
	}

	/**
	 * Returns the value of the '<em><b>Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #setEnumerated(EnumType)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_Enumerated()
	 * @model
	 * @generated
	 */
	public EnumType getEnumerated()
	{
		return enumerated;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getEnumerated <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #getEnumerated()
	 * @generated
	 */
	public void setEnumerated(EnumType newEnumerated)
	{
		EnumType oldEnumerated = enumerated;
		enumerated = newEnumerated == null ? ENUMERATED_EDEFAULT : newEnumerated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__ENUMERATED, oldEnumerated, enumerated));
	}

	/**
	 * Returns the value of the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lob</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lob</em>' attribute.
	 * @see #setLob(boolean)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_Lob()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isLob()
	{
		return lob;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#isLob <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lob</em>' attribute.
	 * @see #isLob()
	 * @generated
	 */
	public void setLob(boolean newLob)
	{
		boolean oldLob = lob;
		lob = newLob;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__LOB, oldLob, lob));
	}

	/**
	 * Returns the value of the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_AttributeOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAttributeOverride> getAttributeOverrides()
	{
		if (attributeOverrides == null)
		{
			attributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, Orm2_0Package.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES);
		}
		return attributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Association Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_AssociationOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAssociationOverride> getAssociationOverrides()
	{
		if (associationOverrides == null)
		{
			associationOverrides = new EObjectContainmentEList<XmlAssociationOverride>(XmlAssociationOverride.class, this, Orm2_0Package.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES);
		}
		return associationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Collection Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Collection Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Collection Table</em>' containment reference.
	 * @see #setCollectionTable(XmlCollectionTable)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlElementCollection_CollectionTable()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCollectionTable getCollectionTable()
	{
		return collectionTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCollectionTable(XmlCollectionTable newCollectionTable, NotificationChain msgs)
	{
		XmlCollectionTable oldCollectionTable = collectionTable;
		collectionTable = newCollectionTable;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__COLLECTION_TABLE, oldCollectionTable, newCollectionTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection#getCollectionTable <em>Collection Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Collection Table</em>' containment reference.
	 * @see #getCollectionTable()
	 * @generated
	 */
	public void setCollectionTable(XmlCollectionTable newCollectionTable)
	{
		if (newCollectionTable != collectionTable)
		{
			NotificationChain msgs = null;
			if (collectionTable != null)
				msgs = ((InternalEObject)collectionTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__COLLECTION_TABLE, null, msgs);
			if (newCollectionTable != null)
				msgs = ((InternalEObject)newCollectionTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Orm2_0Package.XML_ELEMENT_COLLECTION__COLLECTION_TABLE, null, msgs);
			msgs = basicSetCollectionTable(newCollectionTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ELEMENT_COLLECTION__COLLECTION_TABLE, newCollectionTable, newCollectionTable));
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
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				return basicSetOrderColumn(null, msgs);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY:
				return basicSetMapKey(null, msgs);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				return basicSetMapKeyClass(null, msgs);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return ((InternalEList<?>)getMapKeyAttributeOverrides()).basicRemove(otherEnd, msgs);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				return basicSetMapKeyColumn(null, msgs);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getMapKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLUMN:
				return basicSetColumn(null, msgs);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				return ((InternalEList<?>)getAttributeOverrides()).basicRemove(otherEnd, msgs);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				return ((InternalEList<?>)getAssociationOverrides()).basicRemove(otherEnd, msgs);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				return basicSetCollectionTable(null, msgs);
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
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ACCESS:
				return getAccess();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__TARGET_CLASS:
				return getTargetClass();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__FETCH:
				return getFetch();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_BY:
				return getOrderBy();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				return getOrderColumn();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY:
				return getMapKey();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				return getMapKeyClass();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL:
				return getMapKeyTemporal();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED:
				return getMapKeyEnumerated();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return getMapKeyAttributeOverrides();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				return getMapKeyColumn();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				return getMapKeyJoinColumns();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLUMN:
				return getColumn();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__TEMPORAL:
				return getTemporal();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ENUMERATED:
				return getEnumerated();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__LOB:
				return isLob();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				return getAttributeOverrides();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				return getAssociationOverrides();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				return getCollectionTable();
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
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ACCESS:
				setAccess((AccessType)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__TARGET_CLASS:
				setTargetClass((String)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__FETCH:
				setFetch((FetchType)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_BY:
				setOrderBy((String)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				setOrderColumn((XmlOrderColumn)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY:
				setMapKey((MapKey)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				setMapKeyClass((XmlMapKeyClass)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL:
				setMapKeyTemporal((TemporalType)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED:
				setMapKeyEnumerated((EnumType)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				getMapKeyAttributeOverrides().clear();
				getMapKeyAttributeOverrides().addAll((Collection<? extends XmlAttributeOverride>)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				setMapKeyColumn((XmlMapKeyColumn)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				getMapKeyJoinColumns().clear();
				getMapKeyJoinColumns().addAll((Collection<? extends XmlMapKeyJoinColumn>)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLUMN:
				setColumn((XmlColumn)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__TEMPORAL:
				setTemporal((TemporalType)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ENUMERATED:
				setEnumerated((EnumType)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__LOB:
				setLob((Boolean)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				getAttributeOverrides().addAll((Collection<? extends XmlAttributeOverride>)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				getAssociationOverrides().addAll((Collection<? extends XmlAssociationOverride>)newValue);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				setCollectionTable((XmlCollectionTable)newValue);
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
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ACCESS:
				setAccess(ACCESS_EDEFAULT);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__TARGET_CLASS:
				setTargetClass(TARGET_CLASS_EDEFAULT);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__FETCH:
				setFetch(FETCH_EDEFAULT);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_BY:
				setOrderBy(ORDER_BY_EDEFAULT);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				setOrderColumn((XmlOrderColumn)null);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY:
				setMapKey((MapKey)null);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				setMapKeyClass((XmlMapKeyClass)null);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL:
				setMapKeyTemporal(MAP_KEY_TEMPORAL_EDEFAULT);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED:
				setMapKeyEnumerated(MAP_KEY_ENUMERATED_EDEFAULT);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				getMapKeyAttributeOverrides().clear();
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				setMapKeyColumn((XmlMapKeyColumn)null);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				getMapKeyJoinColumns().clear();
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLUMN:
				setColumn((XmlColumn)null);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__TEMPORAL:
				setTemporal(TEMPORAL_EDEFAULT);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ENUMERATED:
				setEnumerated(ENUMERATED_EDEFAULT);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__LOB:
				setLob(LOB_EDEFAULT);
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				return;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				setCollectionTable((XmlCollectionTable)null);
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
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ACCESS:
				return access != ACCESS_EDEFAULT;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__TARGET_CLASS:
				return TARGET_CLASS_EDEFAULT == null ? targetClass != null : !TARGET_CLASS_EDEFAULT.equals(targetClass);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__FETCH:
				return fetch != FETCH_EDEFAULT;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_BY:
				return ORDER_BY_EDEFAULT == null ? orderBy != null : !ORDER_BY_EDEFAULT.equals(orderBy);
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				return orderColumn != null;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY:
				return mapKey != null;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				return mapKeyClass != null;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL:
				return mapKeyTemporal != MAP_KEY_TEMPORAL_EDEFAULT;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED:
				return mapKeyEnumerated != MAP_KEY_ENUMERATED_EDEFAULT;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return mapKeyAttributeOverrides != null && !mapKeyAttributeOverrides.isEmpty();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				return mapKeyColumn != null;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				return mapKeyJoinColumns != null && !mapKeyJoinColumns.isEmpty();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLUMN:
				return column != null;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__TEMPORAL:
				return temporal != TEMPORAL_EDEFAULT;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ENUMERATED:
				return enumerated != ENUMERATED_EDEFAULT;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__LOB:
				return lob != LOB_EDEFAULT;
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				return attributeOverrides != null && !attributeOverrides.isEmpty();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				return associationOverrides != null && !associationOverrides.isEmpty();
			case Orm2_0Package.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				return collectionTable != null;
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
		if (baseClass == XmlAccessHolder.class)
		{
			switch (derivedFeatureID)
			{
				case Orm2_0Package.XML_ELEMENT_COLLECTION__ACCESS: return OrmPackage.XML_ACCESS_HOLDER__ACCESS;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (derivedFeatureID)
			{
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
		if (baseClass == XmlAccessHolder.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ACCESS_HOLDER__ACCESS: return Orm2_0Package.XML_ELEMENT_COLLECTION__ACCESS;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (baseFeatureID)
			{
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
		result.append(" (access: ");
		result.append(access);
		result.append(", targetClass: ");
		result.append(targetClass);
		result.append(", fetch: ");
		result.append(fetch);
		result.append(", orderBy: ");
		result.append(orderBy);
		result.append(", mapKeyTemporal: ");
		result.append(mapKeyTemporal);
		result.append(", mapKeyEnumerated: ");
		result.append(mapKeyEnumerated);
		result.append(", temporal: ");
		result.append(temporal);
		result.append(", enumerated: ");
		result.append(enumerated);
		result.append(", lob: ");
		result.append(lob);
		result.append(')');
		return result.toString();
	}

	public String getMappingKey() {
		return MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildTargetClassTranslator(),
			buildFetchTranslator(),
			buildAccessTranslator(),
			buildOrderByTranslator(),
			XmlOrderColumn.buildTranslator(JPA.ORDER_COLUMN, Orm2_0Package.eINSTANCE.getXmlElementCollection_OrderColumn()),		
			buildMapKeyTranslator(),
			XmlMapKeyClass.buildTranslator(JPA.MAP_KEY_CLASS, Orm2_0Package.eINSTANCE.getXmlElementCollection_MapKeyClass()),		
			buildMapKeyTemporalTranslator(),
			buildMapKeyEnumeratedTranslator(),
			org.eclipse.jpt2_0.core.resource.orm.XmlAttributeOverride.buildTranslator(JPA.MAP_KEY_ATTRIBUTE_OVERRIDE, Orm2_0Package.eINSTANCE.getXmlElementCollection_MapKeyAttributeOverrides()),		
			XmlColumn.buildTranslator(JPA.MAP_KEY_COLUMN, Orm2_0Package.eINSTANCE.getXmlElementCollection_MapKeyColumn()),		
			XmlJoinColumn.buildTranslator(JPA.MAP_KEY_JOIN_COLUMN, Orm2_0Package.eINSTANCE.getXmlElementCollection_MapKeyJoinColumns()),		
			XmlColumn.buildTranslator(JPA.COLUMN, Orm2_0Package.eINSTANCE.getXmlElementCollection_Column()),		
			buildTemporalTranslator(),
			buildEnumeratedTranslator(),
			buildLobTranslator(),
			org.eclipse.jpt2_0.core.resource.orm.XmlAttributeOverride.buildTranslator(JPA.ATTRIBUTE_OVERRIDE, Orm2_0Package.eINSTANCE.getXmlElementCollection_AttributeOverrides()),		
			org.eclipse.jpt2_0.core.resource.orm.XmlAssociationOverride.buildTranslator(JPA.ASSOCIATION_OVERRIDE, Orm2_0Package.eINSTANCE.getXmlElementCollection_AssociationOverrides()),		
			XmlCollectionTable.buildTranslator(JPA.COLLECTION_TABLE, Orm2_0Package.eINSTANCE.getXmlElementCollection_CollectionTable())			
		};
	}
	
	protected static Translator buildTargetClassTranslator() {
		return new Translator(JPA.TARGET_CLASS, Orm2_0Package.eINSTANCE.getXmlElementCollection_TargetClass(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildFetchTranslator() {
		return new Translator(JPA.FETCH, Orm2_0Package.eINSTANCE.getXmlElementCollection_Fetch(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildAccessTranslator() {
		return new Translator(JPA.ACCESS, OrmPackage.eINSTANCE.getXmlAccessHolder_Access(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildOrderByTranslator() {
		return new BooleanTranslator(JPA.ORDER_BY, Orm2_0Package.eINSTANCE.getXmlElementCollection_OrderBy());
	}
	
	protected static Translator buildColumnTranslator() {
		return XmlColumn.buildTranslator(JPA.COLUMN, Orm2_0Package.eINSTANCE.getXmlElementCollection_Column());
	}
	
	protected static Translator buildLobTranslator() {
		return new EmptyTagBooleanTranslator(JPA.LOB, Orm2_0Package.eINSTANCE.getXmlElementCollection_Lob());
	}
	
	protected static Translator buildTemporalTranslator() {
		return new Translator(JPA.TEMPORAL, Orm2_0Package.eINSTANCE.getXmlElementCollection_Temporal());
	}
	
	protected static Translator buildEnumeratedTranslator() {
		return new Translator(JPA.ENUMERATED, Orm2_0Package.eINSTANCE.getXmlElementCollection_Enumerated());
	}
	
	protected static Translator buildMapKeyTranslator() {
		return new EmptyTagBooleanTranslator(JPA.MAP_KEY, Orm2_0Package.eINSTANCE.getXmlElementCollection_MapKey());
	}
	
	protected static Translator buildMapKeyTemporalTranslator() {
		return new Translator(JPA.TEMPORAL, Orm2_0Package.eINSTANCE.getXmlElementCollection_MapKeyTemporal());
	}
	
	protected static Translator buildMapKeyEnumeratedTranslator() {
		return new Translator(JPA.ENUMERATED, Orm2_0Package.eINSTANCE.getXmlElementCollection_MapKeyEnumerated());
	}

} // XmlElementCollection
