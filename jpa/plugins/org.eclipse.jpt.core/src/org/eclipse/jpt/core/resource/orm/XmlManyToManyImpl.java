/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.emfutility.DOMUtilities;
import org.eclipse.jpt.core.internal.resource.orm.translators.OrmXmlMapper;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Many To Many</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlManyToManyImpl()
 * @model kind="class"
 * @generated
 */
public class XmlManyToManyImpl extends AbstractXmlAttributeMapping implements XmlManyToMany
{
	/**
	 * The default value of the '{@link #getTargetEntity() <em>Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_ENTITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetEntity() <em>Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected String targetEntity = TARGET_ENTITY_EDEFAULT;

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
	 * The cached value of the '{@link #getJoinTable() <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinTable()
	 * @generated
	 * @ordered
	 */
	protected XmlJoinTable joinTable;

	/**
	 * The cached value of the '{@link #getCascade() <em>Cascade</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascade()
	 * @generated
	 * @ordered
	 */
	protected CascadeType cascade;

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
	protected XmlManyToManyImpl()
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
		return OrmPackage.Literals.XML_MANY_TO_MANY_IMPL;
	}

	/**
	 * Returns the value of the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Entity</em>' attribute.
	 * @see #setTargetEntity(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlRelationshipMapping_TargetEntity()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTargetEntity()
	{
		return targetEntity;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl#getTargetEntity <em>Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Entity</em>' attribute.
	 * @see #getTargetEntity()
	 * @generated
	 */
	public void setTargetEntity(String newTargetEntity)
	{
		String oldTargetEntity = targetEntity;
		targetEntity = newTargetEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__TARGET_ENTITY, oldTargetEntity, targetEntity));
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The default value is <code>"LAZY"</code>.
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlRelationshipMapping_Fetch()
	 * @model default="LAZY"
	 * @generated
	 */
	public FetchType getFetch()
	{
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl#getFetch <em>Fetch</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__FETCH, oldFetch, fetch));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_MappedBy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMappedBy()
	{
		return mappedBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl#getMappedBy <em>Mapped By</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__MAPPED_BY, oldMappedBy, mappedBy));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_OrderBy()
	 * @model dataType="org.eclipse.jpt.core.resource.orm.OrderBy"
	 * @generated
	 */
	public String getOrderBy()
	{
		return orderBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl#getOrderBy <em>Order By</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__ORDER_BY, oldOrderBy, orderBy));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_MapKey()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY, oldMapKey, newMapKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl#getMapKey <em>Map Key</em>}' containment reference.
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
				msgs = ((InternalEObject)mapKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY, null, msgs);
			if (newMapKey != null)
				msgs = ((InternalEObject)newMapKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY, null, msgs);
			msgs = basicSetMapKey(newMapKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY, newMapKey, newMapKey));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlRelationshipMapping_JoinTable()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE, oldJoinTable, newJoinTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl#getJoinTable <em>Join Table</em>}' containment reference.
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
				msgs = ((InternalEObject)joinTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE, null, msgs);
			if (newJoinTable != null)
				msgs = ((InternalEObject)newJoinTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE, null, msgs);
			msgs = basicSetJoinTable(newJoinTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE, newJoinTable, newJoinTable));
	}

	/**
	 * Returns the value of the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade</em>' containment reference.
	 * @see #setCascade(CascadeType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlRelationshipMapping_Cascade()
	 * @model containment="true"
	 * @generated
	 */
	public CascadeType getCascade()
	{
		return cascade;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCascade(CascadeType newCascade, NotificationChain msgs)
	{
		CascadeType oldCascade = cascade;
		cascade = newCascade;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE, oldCascade, newCascade);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl#getCascade <em>Cascade</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade</em>' containment reference.
	 * @see #getCascade()
	 * @generated
	 */
	public void setCascade(CascadeType newCascade)
	{
		if (newCascade != cascade)
		{
			NotificationChain msgs = null;
			if (cascade != null)
				msgs = ((InternalEObject)cascade).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE, null, msgs);
			if (newCascade != null)
				msgs = ((InternalEObject)newCascade).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE, null, msgs);
			msgs = basicSetCascade(newCascade, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE, newCascade, newCascade));
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
			case OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE:
				return basicSetJoinTable(null, msgs);
			case OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE:
				return basicSetCascade(null, msgs);
			case OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY:
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
			case OrmPackage.XML_MANY_TO_MANY_IMPL__TARGET_ENTITY:
				return getTargetEntity();
			case OrmPackage.XML_MANY_TO_MANY_IMPL__FETCH:
				return getFetch();
			case OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE:
				return getJoinTable();
			case OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE:
				return getCascade();
			case OrmPackage.XML_MANY_TO_MANY_IMPL__MAPPED_BY:
				return getMappedBy();
			case OrmPackage.XML_MANY_TO_MANY_IMPL__ORDER_BY:
				return getOrderBy();
			case OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY:
				return getMapKey();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.XML_MANY_TO_MANY_IMPL__TARGET_ENTITY:
				setTargetEntity((String)newValue);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__FETCH:
				setFetch((FetchType)newValue);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE:
				setJoinTable((XmlJoinTable)newValue);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE:
				setCascade((CascadeType)newValue);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__MAPPED_BY:
				setMappedBy((String)newValue);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__ORDER_BY:
				setOrderBy((String)newValue);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY:
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
			case OrmPackage.XML_MANY_TO_MANY_IMPL__TARGET_ENTITY:
				setTargetEntity(TARGET_ENTITY_EDEFAULT);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__FETCH:
				setFetch(FETCH_EDEFAULT);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE:
				setJoinTable((XmlJoinTable)null);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE:
				setCascade((CascadeType)null);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__MAPPED_BY:
				setMappedBy(MAPPED_BY_EDEFAULT);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__ORDER_BY:
				setOrderBy(ORDER_BY_EDEFAULT);
				return;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY:
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
			case OrmPackage.XML_MANY_TO_MANY_IMPL__TARGET_ENTITY:
				return TARGET_ENTITY_EDEFAULT == null ? targetEntity != null : !TARGET_ENTITY_EDEFAULT.equals(targetEntity);
			case OrmPackage.XML_MANY_TO_MANY_IMPL__FETCH:
				return fetch != FETCH_EDEFAULT;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE:
				return joinTable != null;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE:
				return cascade != null;
			case OrmPackage.XML_MANY_TO_MANY_IMPL__MAPPED_BY:
				return MAPPED_BY_EDEFAULT == null ? mappedBy != null : !MAPPED_BY_EDEFAULT.equals(mappedBy);
			case OrmPackage.XML_MANY_TO_MANY_IMPL__ORDER_BY:
				return ORDER_BY_EDEFAULT == null ? orderBy != null : !ORDER_BY_EDEFAULT.equals(orderBy);
			case OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY:
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
		if (baseClass == XmlRelationshipMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_MANY_TO_MANY_IMPL__TARGET_ENTITY: return OrmPackage.XML_RELATIONSHIP_MAPPING__TARGET_ENTITY;
				case OrmPackage.XML_MANY_TO_MANY_IMPL__FETCH: return OrmPackage.XML_RELATIONSHIP_MAPPING__FETCH;
				case OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE: return OrmPackage.XML_RELATIONSHIP_MAPPING__JOIN_TABLE;
				case OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE: return OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE;
				default: return -1;
			}
		}
		if (baseClass == XmlMultiRelationshipMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_MANY_TO_MANY_IMPL__MAPPED_BY: return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY;
				case OrmPackage.XML_MANY_TO_MANY_IMPL__ORDER_BY: return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY;
				case OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY: return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToMany.class)
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
		if (baseClass == XmlRelationshipMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_RELATIONSHIP_MAPPING__TARGET_ENTITY: return OrmPackage.XML_MANY_TO_MANY_IMPL__TARGET_ENTITY;
				case OrmPackage.XML_RELATIONSHIP_MAPPING__FETCH: return OrmPackage.XML_MANY_TO_MANY_IMPL__FETCH;
				case OrmPackage.XML_RELATIONSHIP_MAPPING__JOIN_TABLE: return OrmPackage.XML_MANY_TO_MANY_IMPL__JOIN_TABLE;
				case OrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE: return OrmPackage.XML_MANY_TO_MANY_IMPL__CASCADE;
				default: return -1;
			}
		}
		if (baseClass == XmlMultiRelationshipMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING__MAPPED_BY: return OrmPackage.XML_MANY_TO_MANY_IMPL__MAPPED_BY;
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING__ORDER_BY: return OrmPackage.XML_MANY_TO_MANY_IMPL__ORDER_BY;
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING__MAP_KEY: return OrmPackage.XML_MANY_TO_MANY_IMPL__MAP_KEY;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToMany.class)
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
		result.append(" (targetEntity: ");
		result.append(targetEntity);
		result.append(", fetch: ");
		result.append(fetch);
		result.append(", mappedBy: ");
		result.append(mappedBy);
		result.append(", orderBy: ");
		result.append(orderBy);
		result.append(')');
		return result.toString();
	}

	public TextRange mappedByTextRange() {
		IDOMNode mappedByNode = (IDOMNode) DOMUtilities.getChildAttributeNode(getNode(), OrmXmlMapper.MAPPED_BY);
		return (mappedByNode == null) ? validationTextRange() : buildTextRange(mappedByNode);
	}
}