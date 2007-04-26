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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.EnumType;
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.TemporalType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Basic</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlBasic()
 * @model kind="class"
 * @generated
 */
public class XmlBasic extends XmlAttributeMapping
	implements IBasic, IXmlColumnMapping
{
	/**
	 * The default value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultEagerFetchType FETCH_EDEFAULT = DefaultEagerFetchType.DEFAULT;

	/**
	 * The cached value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected DefaultEagerFetchType fetch = FETCH_EDEFAULT;

	/**
	 * The default value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean OPTIONAL_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The cached value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected DefaultTrueBoolean optional = OPTIONAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getColumn() <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected IColumn column;

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
	protected static final TemporalType TEMPORAL_EDEFAULT = TemporalType.NULL;

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
	protected static final EnumType ENUMERATED_EDEFAULT = EnumType.DEFAULT;

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
	 * @generated NOT
	 */
	protected XmlBasic() {
		super();
		this.column = OrmFactory.eINSTANCE.createXmlColumn(buildOwner());
		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_BASIC__COLUMN, null, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_BASIC;
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIBasic_Column()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public IColumn getColumn() {
		return column;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColumn(IColumn newColumn, NotificationChain msgs) {
		IColumn oldColumn = column;
		column = newColumn;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__COLUMN, oldColumn, newColumn);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #setFetch(DefaultEagerFetchType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIBasic_Fetch()
	 * @model
	 * @generated
	 */
	public DefaultEagerFetchType getFetch() {
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(DefaultEagerFetchType newFetch) {
		DefaultEagerFetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__FETCH, oldFetch, fetch));
	}

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setOptional(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIBasic_Optional()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getOptional() {
		return optional;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic#getOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getOptional()
	 * @generated
	 */
	public void setOptional(DefaultTrueBoolean newOptional) {
		DefaultTrueBoolean oldOptional = optional;
		optional = newOptional == null ? OPTIONAL_EDEFAULT : newOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__OPTIONAL, oldOptional, optional));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIBasic_Lob()
	 * @model
	 * @generated
	 */
	public boolean isLob() {
		return lob;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic#isLob <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lob</em>' attribute.
	 * @see #isLob()
	 * @generated
	 */
	public void setLob(boolean newLob) {
		boolean oldLob = lob;
		lob = newLob;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__LOB, oldLob, lob));
	}

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIBasic_Temporal()
	 * @model
	 * @generated
	 */
	public TemporalType getTemporal() {
		return temporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = temporal;
		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__TEMPORAL, oldTemporal, temporal));
	}

	/**
	 * Returns the value of the '<em><b>Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.EnumType
	 * @see #setEnumerated(EnumType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIBasic_Enumerated()
	 * @model
	 * @generated
	 */
	public EnumType getEnumerated() {
		return enumerated;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic#getEnumerated <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.EnumType
	 * @see #getEnumerated()
	 * @generated
	 */
	public void setEnumerated(EnumType newEnumerated) {
		EnumType oldEnumerated = enumerated;
		enumerated = newEnumerated == null ? ENUMERATED_EDEFAULT : newEnumerated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__ENUMERATED, oldEnumerated, enumerated));
	}

	/**
	 * Returns the value of the '<em><b>Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column For Xml</em>' reference.
	 * @see #setColumnForXml(XmlColumn)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIXmlColumnMapping_ColumnForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated NOT
	 */
	public XmlColumn getColumnForXml() {
		if (((XmlColumn) getColumn()).isAllFeaturesUnset()) {
			return null;
		}
		return (XmlColumn) getColumn();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic#getColumnForXml <em>Column For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column For Xml</em>' reference.
	 * @see #getColumnForXml()
	 * @generated NOT
	 */
	public void setColumnForXmlGen(XmlColumn newColumnForXml) {
		XmlColumn oldValue = newColumnForXml == null ? (XmlColumn) getColumn() : null;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_BASIC__COLUMN_FOR_XML, oldValue, newColumnForXml));
		}
	}

	public void setColumnForXml(XmlColumn newColumnForXml) {
		setColumnForXmlGen(newColumnForXml);
		if (newColumnForXml == null) {
			((XmlColumn) getColumn()).unsetAllAttributes();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public void makeColumnForXmlNonNull() {
		setColumnForXmlGen(getColumnForXml());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public void makeColumnForXmlNull() {
		setColumnForXmlGen(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_BASIC__COLUMN :
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_BASIC__FETCH :
				return getFetch();
			case OrmPackage.XML_BASIC__OPTIONAL :
				return getOptional();
			case OrmPackage.XML_BASIC__COLUMN :
				return getColumn();
			case OrmPackage.XML_BASIC__LOB :
				return isLob() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.XML_BASIC__TEMPORAL :
				return getTemporal();
			case OrmPackage.XML_BASIC__ENUMERATED :
				return getEnumerated();
			case OrmPackage.XML_BASIC__COLUMN_FOR_XML :
				return getColumnForXml();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.XML_BASIC__FETCH :
				setFetch((DefaultEagerFetchType) newValue);
				return;
			case OrmPackage.XML_BASIC__OPTIONAL :
				setOptional((DefaultTrueBoolean) newValue);
				return;
			case OrmPackage.XML_BASIC__LOB :
				setLob(((Boolean) newValue).booleanValue());
				return;
			case OrmPackage.XML_BASIC__TEMPORAL :
				setTemporal((TemporalType) newValue);
				return;
			case OrmPackage.XML_BASIC__ENUMERATED :
				setEnumerated((EnumType) newValue);
				return;
			case OrmPackage.XML_BASIC__COLUMN_FOR_XML :
				setColumnForXml((XmlColumn) newValue);
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
			case OrmPackage.XML_BASIC__FETCH :
				setFetch(FETCH_EDEFAULT);
				return;
			case OrmPackage.XML_BASIC__OPTIONAL :
				setOptional(OPTIONAL_EDEFAULT);
				return;
			case OrmPackage.XML_BASIC__LOB :
				setLob(LOB_EDEFAULT);
				return;
			case OrmPackage.XML_BASIC__TEMPORAL :
				setTemporal(TEMPORAL_EDEFAULT);
				return;
			case OrmPackage.XML_BASIC__ENUMERATED :
				setEnumerated(ENUMERATED_EDEFAULT);
				return;
			case OrmPackage.XML_BASIC__COLUMN_FOR_XML :
				setColumnForXml((XmlColumn) null);
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
			case OrmPackage.XML_BASIC__FETCH :
				return fetch != FETCH_EDEFAULT;
			case OrmPackage.XML_BASIC__OPTIONAL :
				return optional != OPTIONAL_EDEFAULT;
			case OrmPackage.XML_BASIC__COLUMN :
				return column != null;
			case OrmPackage.XML_BASIC__LOB :
				return lob != LOB_EDEFAULT;
			case OrmPackage.XML_BASIC__TEMPORAL :
				return temporal != TEMPORAL_EDEFAULT;
			case OrmPackage.XML_BASIC__ENUMERATED :
				return enumerated != ENUMERATED_EDEFAULT;
			case OrmPackage.XML_BASIC__COLUMN_FOR_XML :
				return getColumnForXml() != null;
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
		if (baseClass == IColumnMapping.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IBasic.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_BASIC__FETCH :
					return JpaCoreMappingsPackage.IBASIC__FETCH;
				case OrmPackage.XML_BASIC__OPTIONAL :
					return JpaCoreMappingsPackage.IBASIC__OPTIONAL;
				case OrmPackage.XML_BASIC__COLUMN :
					return JpaCoreMappingsPackage.IBASIC__COLUMN;
				case OrmPackage.XML_BASIC__LOB :
					return JpaCoreMappingsPackage.IBASIC__LOB;
				case OrmPackage.XML_BASIC__TEMPORAL :
					return JpaCoreMappingsPackage.IBASIC__TEMPORAL;
				case OrmPackage.XML_BASIC__ENUMERATED :
					return JpaCoreMappingsPackage.IBASIC__ENUMERATED;
				default :
					return -1;
			}
		}
		if (baseClass == IXmlColumnMapping.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_BASIC__COLUMN_FOR_XML :
					return OrmPackage.IXML_COLUMN_MAPPING__COLUMN_FOR_XML;
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
		if (baseClass == IColumnMapping.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IBasic.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IBASIC__FETCH :
					return OrmPackage.XML_BASIC__FETCH;
				case JpaCoreMappingsPackage.IBASIC__OPTIONAL :
					return OrmPackage.XML_BASIC__OPTIONAL;
				case JpaCoreMappingsPackage.IBASIC__COLUMN :
					return OrmPackage.XML_BASIC__COLUMN;
				case JpaCoreMappingsPackage.IBASIC__LOB :
					return OrmPackage.XML_BASIC__LOB;
				case JpaCoreMappingsPackage.IBASIC__TEMPORAL :
					return OrmPackage.XML_BASIC__TEMPORAL;
				case JpaCoreMappingsPackage.IBASIC__ENUMERATED :
					return OrmPackage.XML_BASIC__ENUMERATED;
				default :
					return -1;
			}
		}
		if (baseClass == IXmlColumnMapping.class) {
			switch (baseFeatureID) {
				case OrmPackage.IXML_COLUMN_MAPPING__COLUMN_FOR_XML :
					return OrmPackage.XML_BASIC__COLUMN_FOR_XML;
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

	public String getKey() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlBasicMapping(this);
	}

	@Override
	public void initializeFromXmlIdMapping(XmlId oldMapping) {
		super.initializeFromXmlIdMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
	}

	@Override
	public int xmlSequence() {
		return 1;
	}
}
