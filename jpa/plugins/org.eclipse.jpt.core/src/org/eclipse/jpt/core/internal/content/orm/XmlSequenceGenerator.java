/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Sequence Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSequenceGenerator()
 * @model kind="class"
 * @generated
 */
public class XmlSequenceGenerator extends XmlGenerator
	implements ISequenceGenerator
{
	/**
	 * The default value of the '{@link #getSequenceName() <em>Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceName()
	 * @generated
	 * @ordered
	 */
	protected static final String SEQUENCE_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedSequenceName() <em>Specified Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSequenceName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_SEQUENCE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedSequenceName() <em>Specified Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSequenceName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedSequenceName = SPECIFIED_SEQUENCE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultSequenceName() <em>Default Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSequenceName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_SEQUENCE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultSequenceName() <em>Default Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSequenceName()
	 * @generated
	 * @ordered
	 */
	protected String defaultSequenceName = DEFAULT_SEQUENCE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlSequenceGenerator() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_SEQUENCE_GENERATOR;
	}

	public String getSequenceName() {
		return (this.getSpecifiedSequenceName() == null) ? getDefaultSequenceName() : this.getSpecifiedSequenceName();
	}

	/**
	 * Returns the value of the '<em><b>Specified Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Sequence Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Sequence Name</em>' attribute.
	 * @see #setSpecifiedSequenceName(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getISequenceGenerator_SpecifiedSequenceName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedSequenceName() {
		return specifiedSequenceName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator#getSpecifiedSequenceName <em>Specified Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Sequence Name</em>' attribute.
	 * @see #getSpecifiedSequenceName()
	 * @generated
	 */
	public void setSpecifiedSequenceName(String newSpecifiedSequenceName) {
		String oldSpecifiedSequenceName = specifiedSequenceName;
		specifiedSequenceName = newSpecifiedSequenceName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME, oldSpecifiedSequenceName, specifiedSequenceName));
	}

	/**
	 * Returns the value of the '<em><b>Default Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Sequence Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Sequence Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getISequenceGenerator_DefaultSequenceName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultSequenceName() {
		return defaultSequenceName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_SEQUENCE_GENERATOR__SEQUENCE_NAME :
				return getSequenceName();
			case OrmPackage.XML_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
				return getSpecifiedSequenceName();
			case OrmPackage.XML_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME :
				return getDefaultSequenceName();
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
			case OrmPackage.XML_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
				setSpecifiedSequenceName((String) newValue);
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
			case OrmPackage.XML_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
				setSpecifiedSequenceName(SPECIFIED_SEQUENCE_NAME_EDEFAULT);
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
			case OrmPackage.XML_SEQUENCE_GENERATOR__SEQUENCE_NAME :
				return SEQUENCE_NAME_EDEFAULT == null ? getSequenceName() != null : !SEQUENCE_NAME_EDEFAULT.equals(getSequenceName());
			case OrmPackage.XML_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
				return SPECIFIED_SEQUENCE_NAME_EDEFAULT == null ? specifiedSequenceName != null : !SPECIFIED_SEQUENCE_NAME_EDEFAULT.equals(specifiedSequenceName);
			case OrmPackage.XML_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME :
				return DEFAULT_SEQUENCE_NAME_EDEFAULT == null ? defaultSequenceName != null : !DEFAULT_SEQUENCE_NAME_EDEFAULT.equals(defaultSequenceName);
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
		if (baseClass == ISequenceGenerator.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_SEQUENCE_GENERATOR__SEQUENCE_NAME :
					return JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__SEQUENCE_NAME;
				case OrmPackage.XML_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
					return JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME;
				case OrmPackage.XML_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME :
					return JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME;
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
		if (baseClass == ISequenceGenerator.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__SEQUENCE_NAME :
					return OrmPackage.XML_SEQUENCE_GENERATOR__SEQUENCE_NAME;
				case JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
					return OrmPackage.XML_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME;
				case JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME :
					return OrmPackage.XML_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME;
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
		result.append(" (specifiedSequenceName: ");
		result.append(specifiedSequenceName);
		result.append(", defaultSequenceName: ");
		result.append(defaultSequenceName);
		result.append(')');
		return result.toString();
	}
} // XmlSequenceGenerator
