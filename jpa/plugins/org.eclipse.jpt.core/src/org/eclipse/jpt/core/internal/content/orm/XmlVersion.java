/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.mappings.IVersion;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.TemporalType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Version</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlVersion()
 * @model kind="class"
 * @generated
 */
public class XmlVersion extends XmlAttributeMapping
	implements IVersion, IXmlColumnMapping
{
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

	protected XmlVersion() {
		super();
		this.column = OrmFactory.eINSTANCE.createXmlColumn(buildOwner());
		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ID__COLUMN, null, null);
	}

	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlVersionMapping(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_VERSION;
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIVersion_Column()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_VERSION__COLUMN, oldColumn, newColumn);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIVersion_Temporal()
	 * @model
	 * @generated
	 */
	public TemporalType getTemporal() {
		return temporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlVersion#getTemporal <em>Temporal</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_VERSION__TEMPORAL, oldTemporal, temporal));
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
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlVersion#getColumnForXml <em>Column For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column For Xml</em>' reference.
	 * @see #getColumnForXml()
	 * @generated NOT
	 */
	public void setColumnForXmlGen(XmlColumn newColumnForXml) {
		XmlColumn oldValue = newColumnForXml == null ? (XmlColumn) getColumn() : null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_VERSION__COLUMN_FOR_XML, oldValue, newColumnForXml));
	}

	public void setColumnForXml(XmlColumn newColumnForXml) {
		setColumnForXmlGen(newColumnForXml);
		if (newColumnForXml == null) {
			((XmlColumn) getColumn()).unsetAllAttributes();
		}
	}

	public void makeColumnForXmlNonNull() {
		setColumnForXmlGen(getColumnForXml());
	}

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
			case OrmPackage.XML_VERSION__COLUMN :
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
			case OrmPackage.XML_VERSION__COLUMN :
				return getColumn();
			case OrmPackage.XML_VERSION__TEMPORAL :
				return getTemporal();
			case OrmPackage.XML_VERSION__COLUMN_FOR_XML :
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
			case OrmPackage.XML_VERSION__TEMPORAL :
				setTemporal((TemporalType) newValue);
				return;
			case OrmPackage.XML_VERSION__COLUMN_FOR_XML :
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
			case OrmPackage.XML_VERSION__TEMPORAL :
				setTemporal(TEMPORAL_EDEFAULT);
				return;
			case OrmPackage.XML_VERSION__COLUMN_FOR_XML :
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
			case OrmPackage.XML_VERSION__COLUMN :
				return column != null;
			case OrmPackage.XML_VERSION__TEMPORAL :
				return temporal != TEMPORAL_EDEFAULT;
			case OrmPackage.XML_VERSION__COLUMN_FOR_XML :
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
		if (baseClass == IVersion.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_VERSION__COLUMN :
					return JpaCoreMappingsPackage.IVERSION__COLUMN;
				case OrmPackage.XML_VERSION__TEMPORAL :
					return JpaCoreMappingsPackage.IVERSION__TEMPORAL;
				default :
					return -1;
			}
		}
		if (baseClass == IXmlColumnMapping.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_VERSION__COLUMN_FOR_XML :
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
		if (baseClass == IVersion.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IVERSION__COLUMN :
					return OrmPackage.XML_VERSION__COLUMN;
				case JpaCoreMappingsPackage.IVERSION__TEMPORAL :
					return OrmPackage.XML_VERSION__TEMPORAL;
				default :
					return -1;
			}
		}
		if (baseClass == IXmlColumnMapping.class) {
			switch (baseFeatureID) {
				case OrmPackage.IXML_COLUMN_MAPPING__COLUMN_FOR_XML :
					return OrmPackage.XML_VERSION__COLUMN_FOR_XML;
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
		result.append(" (temporal: ");
		result.append(temporal);
		result.append(')');
		return result.toString();
	}

	@Override
	public int xmlSequence() {
		return 2;
	}

	public String getKey() {
		return IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
} // XmlVersion
