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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Basic Impl</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlBasicImpl()
 * @model kind="class"
 * @generated
 */
public class XmlBasicImpl extends AbstractXmlAttributeMapping implements XmlBasic
{
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
	 * The default value of the '{@link #getTemporal() <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporal()
	 * @generated
	 * @ordered
	 */
	protected static final TemporalType TEMPORAL_EDEFAULT = TemporalType.DATE;

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
	 * The default value of the '{@link #getEnumerated() <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumerated()
	 * @generated
	 * @ordered
	 */
	protected static final EnumType ENUMERATED_EDEFAULT = EnumType.ORDINAL;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlBasicImpl()
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
		return EclipseLinkOrmPackage.Literals.XML_BASIC_IMPL;
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getColumnMapping_Column()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN, oldColumn, newColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlBasicImpl#getColumn <em>Column</em>}' containment reference.
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
				msgs = ((InternalEObject)column).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN, null, msgs);
			if (newColumn != null)
				msgs = ((InternalEObject)newColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN, null, msgs);
			msgs = basicSetColumn(newColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN, newColumn, newColumn));
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlBasic_Fetch()
	 * @model default="LAZY"
	 * @generated
	 */
	public FetchType getFetch()
	{
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlBasicImpl#getFetch <em>Fetch</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_BASIC_IMPL__FETCH, oldFetch, fetch));
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlBasic_Optional()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getOptional()
	{
		return optional;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlBasicImpl#getOptional <em>Optional</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_BASIC_IMPL__OPTIONAL, oldOptional, optional));
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlBasic_Lob()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isLob()
	{
		return lob;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlBasicImpl#isLob <em>Lob</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_BASIC_IMPL__LOB, oldLob, lob));
	}

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.elorm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlBasic_Temporal()
	 * @model
	 * @generated
	 */
	public TemporalType getTemporal()
	{
		return temporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlBasicImpl#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	public void setTemporal(TemporalType newTemporal)
	{
		TemporalType oldTemporal = temporal;
		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_BASIC_IMPL__TEMPORAL, oldTemporal, temporal));
	}

	/**
	 * Returns the value of the '<em><b>Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.elorm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EnumType
	 * @see #setEnumerated(EnumType)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlBasic_Enumerated()
	 * @model
	 * @generated
	 */
	public EnumType getEnumerated()
	{
		return enumerated;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlBasicImpl#getEnumerated <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EnumType
	 * @see #getEnumerated()
	 * @generated
	 */
	public void setEnumerated(EnumType newEnumerated)
	{
		EnumType oldEnumerated = enumerated;
		enumerated = newEnumerated == null ? ENUMERATED_EDEFAULT : newEnumerated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_BASIC_IMPL__ENUMERATED, oldEnumerated, enumerated));
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
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN:
				return basicSetColumn(null, msgs);
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
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN:
				return getColumn();
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__FETCH:
				return getFetch();
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__OPTIONAL:
				return getOptional();
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__LOB:
				return isLob() ? Boolean.TRUE : Boolean.FALSE;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__TEMPORAL:
				return getTemporal();
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__ENUMERATED:
				return getEnumerated();
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
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN:
				setColumn((XmlColumn)newValue);
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__FETCH:
				setFetch((FetchType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__OPTIONAL:
				setOptional((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__LOB:
				setLob(((Boolean)newValue).booleanValue());
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__TEMPORAL:
				setTemporal((TemporalType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__ENUMERATED:
				setEnumerated((EnumType)newValue);
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
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN:
				setColumn((XmlColumn)null);
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__FETCH:
				setFetch(FETCH_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__OPTIONAL:
				setOptional(OPTIONAL_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__LOB:
				setLob(LOB_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__TEMPORAL:
				setTemporal(TEMPORAL_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__ENUMERATED:
				setEnumerated(ENUMERATED_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN:
				return column != null;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__FETCH:
				return fetch != FETCH_EDEFAULT;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__OPTIONAL:
				return OPTIONAL_EDEFAULT == null ? optional != null : !OPTIONAL_EDEFAULT.equals(optional);
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__LOB:
				return lob != LOB_EDEFAULT;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__TEMPORAL:
				return temporal != TEMPORAL_EDEFAULT;
			case EclipseLinkOrmPackage.XML_BASIC_IMPL__ENUMERATED:
				return enumerated != ENUMERATED_EDEFAULT;
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
		if (baseClass == ColumnMapping.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN: return EclipseLinkOrmPackage.COLUMN_MAPPING__COLUMN;
				default: return -1;
			}
		}
		if (baseClass == XmlBasic.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_BASIC_IMPL__FETCH: return EclipseLinkOrmPackage.XML_BASIC__FETCH;
				case EclipseLinkOrmPackage.XML_BASIC_IMPL__OPTIONAL: return EclipseLinkOrmPackage.XML_BASIC__OPTIONAL;
				case EclipseLinkOrmPackage.XML_BASIC_IMPL__LOB: return EclipseLinkOrmPackage.XML_BASIC__LOB;
				case EclipseLinkOrmPackage.XML_BASIC_IMPL__TEMPORAL: return EclipseLinkOrmPackage.XML_BASIC__TEMPORAL;
				case EclipseLinkOrmPackage.XML_BASIC_IMPL__ENUMERATED: return EclipseLinkOrmPackage.XML_BASIC__ENUMERATED;
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
		if (baseClass == ColumnMapping.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.COLUMN_MAPPING__COLUMN: return EclipseLinkOrmPackage.XML_BASIC_IMPL__COLUMN;
				default: return -1;
			}
		}
		if (baseClass == XmlBasic.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_BASIC__FETCH: return EclipseLinkOrmPackage.XML_BASIC_IMPL__FETCH;
				case EclipseLinkOrmPackage.XML_BASIC__OPTIONAL: return EclipseLinkOrmPackage.XML_BASIC_IMPL__OPTIONAL;
				case EclipseLinkOrmPackage.XML_BASIC__LOB: return EclipseLinkOrmPackage.XML_BASIC_IMPL__LOB;
				case EclipseLinkOrmPackage.XML_BASIC__TEMPORAL: return EclipseLinkOrmPackage.XML_BASIC_IMPL__TEMPORAL;
				case EclipseLinkOrmPackage.XML_BASIC__ENUMERATED: return EclipseLinkOrmPackage.XML_BASIC_IMPL__ENUMERATED;
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
		result.append(" (fetch: ");
		result.append(fetch);
		result.append(", optional: ");
		result.append(optional);
		result.append(", lob: ");
		result.append(lob);
		result.append(", temporal: ");
		result.append(temporal);
		result.append(", enumerated: ");
		result.append(enumerated);
		result.append(')');
		return result.toString();
	}

} // XmlBasicImpl
