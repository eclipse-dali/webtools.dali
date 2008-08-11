/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.elorm;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Many To One Impl</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlManyToOneImpl()
 * @model kind="class"
 * @generated
 */
public class XmlManyToOneImpl extends AbstractXmlAttributeMapping implements XmlManyToOne
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
	 * The default value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected static final FetchType FETCH_EDEFAULT = FetchType.LAZY;

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
	 * The default value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean OPTIONAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected Boolean optional = OPTIONAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getJoinColumns() <em>Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinColumn> joinColumns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlManyToOneImpl()
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
		return EclipseLinkOrmPackage.Literals.XML_MANY_TO_ONE_IMPL;
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlRelationshipMapping_TargetEntity()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTargetEntity()
	{
		return targetEntity;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlManyToOneImpl#getTargetEntity <em>Target Entity</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__TARGET_ENTITY, oldTargetEntity, targetEntity));
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The default value is <code>"LAZY"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.elorm.FetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.FetchType
	 * @see #setFetch(FetchType)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlRelationshipMapping_Fetch()
	 * @model default="LAZY"
	 * @generated
	 */
	public FetchType getFetch()
	{
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlManyToOneImpl#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.FetchType
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(FetchType newFetch)
	{
		FetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__FETCH, oldFetch, fetch));
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlRelationshipMapping_JoinTable()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE, oldJoinTable, newJoinTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlManyToOneImpl#getJoinTable <em>Join Table</em>}' containment reference.
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
				msgs = ((InternalEObject)joinTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE, null, msgs);
			if (newJoinTable != null)
				msgs = ((InternalEObject)newJoinTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE, null, msgs);
			msgs = basicSetJoinTable(newJoinTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE, newJoinTable, newJoinTable));
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlRelationshipMapping_Cascade()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE, oldCascade, newCascade);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlManyToOneImpl#getCascade <em>Cascade</em>}' containment reference.
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
				msgs = ((InternalEObject)cascade).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE, null, msgs);
			if (newCascade != null)
				msgs = ((InternalEObject)newCascade).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE, null, msgs);
			msgs = basicSetCascade(newCascade, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE, newCascade, newCascade));
	}

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlSingleRelationshipMapping_Optional()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getOptional()
	{
		return optional;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlManyToOneImpl#getOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #getOptional()
	 * @generated
	 */
	public void setOptional(Boolean newOptional)
	{
		Boolean oldOptional = optional;
		optional = newOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__OPTIONAL, oldOptional, optional));
	}

	/**
	 * Returns the value of the '<em><b>Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlSingleRelationshipMapping_JoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinColumn> getJoinColumns()
	{
		if (joinColumns == null)
		{
			joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS);
		}
		return joinColumns;
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE:
				return basicSetJoinTable(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE:
				return basicSetCascade(null, msgs);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS:
				return ((InternalEList<?>)getJoinColumns()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__TARGET_ENTITY:
				return getTargetEntity();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__FETCH:
				return getFetch();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE:
				return getJoinTable();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE:
				return getCascade();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__OPTIONAL:
				return getOptional();
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS:
				return getJoinColumns();
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__TARGET_ENTITY:
				setTargetEntity((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__FETCH:
				setFetch((FetchType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE:
				setJoinTable((XmlJoinTable)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE:
				setCascade((CascadeType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__OPTIONAL:
				setOptional((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS:
				getJoinColumns().clear();
				getJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__TARGET_ENTITY:
				setTargetEntity(TARGET_ENTITY_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__FETCH:
				setFetch(FETCH_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE:
				setJoinTable((XmlJoinTable)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE:
				setCascade((CascadeType)null);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__OPTIONAL:
				setOptional(OPTIONAL_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS:
				getJoinColumns().clear();
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
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__TARGET_ENTITY:
				return TARGET_ENTITY_EDEFAULT == null ? targetEntity != null : !TARGET_ENTITY_EDEFAULT.equals(targetEntity);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__FETCH:
				return fetch != FETCH_EDEFAULT;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE:
				return joinTable != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE:
				return cascade != null;
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__OPTIONAL:
				return OPTIONAL_EDEFAULT == null ? optional != null : !OPTIONAL_EDEFAULT.equals(optional);
			case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS:
				return joinColumns != null && !joinColumns.isEmpty();
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
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__TARGET_ENTITY: return EclipseLinkOrmPackage.XML_RELATIONSHIP_MAPPING__TARGET_ENTITY;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__FETCH: return EclipseLinkOrmPackage.XML_RELATIONSHIP_MAPPING__FETCH;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE: return EclipseLinkOrmPackage.XML_RELATIONSHIP_MAPPING__JOIN_TABLE;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE: return EclipseLinkOrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE;
				default: return -1;
			}
		}
		if (baseClass == XmlSingleRelationshipMapping.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__OPTIONAL: return EclipseLinkOrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;
				case EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS: return EclipseLinkOrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne.class)
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
				case EclipseLinkOrmPackage.XML_RELATIONSHIP_MAPPING__TARGET_ENTITY: return EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__TARGET_ENTITY;
				case EclipseLinkOrmPackage.XML_RELATIONSHIP_MAPPING__FETCH: return EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__FETCH;
				case EclipseLinkOrmPackage.XML_RELATIONSHIP_MAPPING__JOIN_TABLE: return EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_TABLE;
				case EclipseLinkOrmPackage.XML_RELATIONSHIP_MAPPING__CASCADE: return EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__CASCADE;
				default: return -1;
			}
		}
		if (baseClass == XmlSingleRelationshipMapping.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL: return EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__OPTIONAL;
				case EclipseLinkOrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS: return EclipseLinkOrmPackage.XML_MANY_TO_ONE_IMPL__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne.class)
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
		result.append(", optional: ");
		result.append(optional);
		result.append(')');
		return result.toString();
	}

} // XmlManyToOneImpl
