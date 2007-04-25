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
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.mappings.IGenerator;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlGenerator()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class XmlGenerator extends XmlEObject implements IGenerator
{
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitialValue() <em>Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialValue()
	 * @generated
	 * @ordered
	 */
	protected static final int INITIAL_VALUE_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getSpecifiedInitialValue() <em>Specified Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedInitialValue()
	 * @generated
	 * @ordered
	 */
	protected static final int SPECIFIED_INITIAL_VALUE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getSpecifiedInitialValue() <em>Specified Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedInitialValue()
	 * @generated
	 * @ordered
	 */
	protected int specifiedInitialValue = SPECIFIED_INITIAL_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultInitialValue() <em>Default Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultInitialValue()
	 * @generated
	 * @ordered
	 */
	protected static final int DEFAULT_INITIAL_VALUE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDefaultInitialValue() <em>Default Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultInitialValue()
	 * @generated
	 * @ordered
	 */
	protected int defaultInitialValue = DEFAULT_INITIAL_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAllocationSize() <em>Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllocationSize()
	 * @generated
	 * @ordered
	 */
	protected static final int ALLOCATION_SIZE_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getSpecifiedAllocationSize() <em>Specified Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAllocationSize()
	 * @generated
	 * @ordered
	 */
	protected static final int SPECIFIED_ALLOCATION_SIZE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getSpecifiedAllocationSize() <em>Specified Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAllocationSize()
	 * @generated
	 * @ordered
	 */
	protected int specifiedAllocationSize = SPECIFIED_ALLOCATION_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultAllocationSize() <em>Default Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAllocationSize()
	 * @generated
	 * @ordered
	 */
	protected static final int DEFAULT_ALLOCATION_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDefaultAllocationSize() <em>Default Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAllocationSize()
	 * @generated
	 * @ordered
	 */
	protected int defaultAllocationSize = DEFAULT_ALLOCATION_SIZE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlGenerator() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_GENERATOR;
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIGenerator_Name()
	 * @model
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlGenerator#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_GENERATOR__NAME, oldName, name));
	}

	public int getInitialValue() {
		return (this.getSpecifiedInitialValue() == -1) ? this.getDefaultInitialValue() : this.getSpecifiedInitialValue();
	}

	/**
	 * Returns the value of the '<em><b>Specified Initial Value</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Initial Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Initial Value</em>' attribute.
	 * @see #setSpecifiedInitialValue(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIGenerator_SpecifiedInitialValue()
	 * @model default="-1"
	 * @generated
	 */
	public int getSpecifiedInitialValue() {
		return specifiedInitialValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlGenerator#getSpecifiedInitialValue <em>Specified Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Initial Value</em>' attribute.
	 * @see #getSpecifiedInitialValue()
	 * @generated
	 */
	public void setSpecifiedInitialValue(int newSpecifiedInitialValue) {
		int oldSpecifiedInitialValue = specifiedInitialValue;
		specifiedInitialValue = newSpecifiedInitialValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_GENERATOR__SPECIFIED_INITIAL_VALUE, oldSpecifiedInitialValue, specifiedInitialValue));
	}

	/**
	 * Returns the value of the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Initial Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Initial Value</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIGenerator_DefaultInitialValue()
	 * @model changeable="false"
	 * @generated
	 */
	public int getDefaultInitialValue() {
		return defaultInitialValue;
	}

	public int getAllocationSize() {
		return (this.getSpecifiedAllocationSize() == -1) ? this.getDefaultAllocationSize() : this.getSpecifiedAllocationSize();
	}

	/**
	 * Returns the value of the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Allocation Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Allocation Size</em>' attribute.
	 * @see #setSpecifiedAllocationSize(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIGenerator_SpecifiedAllocationSize()
	 * @model default="-1"
	 * @generated
	 */
	public int getSpecifiedAllocationSize() {
		return specifiedAllocationSize;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlGenerator#getSpecifiedAllocationSize <em>Specified Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Allocation Size</em>' attribute.
	 * @see #getSpecifiedAllocationSize()
	 * @generated
	 */
	public void setSpecifiedAllocationSize(int newSpecifiedAllocationSize) {
		int oldSpecifiedAllocationSize = specifiedAllocationSize;
		specifiedAllocationSize = newSpecifiedAllocationSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE, oldSpecifiedAllocationSize, specifiedAllocationSize));
	}

	/**
	 * Returns the value of the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Allocation Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Allocation Size</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIGenerator_DefaultAllocationSize()
	 * @model changeable="false"
	 * @generated
	 */
	public int getDefaultAllocationSize() {
		return defaultAllocationSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_GENERATOR__NAME :
				return getName();
			case OrmPackage.XML_GENERATOR__INITIAL_VALUE :
				return new Integer(getInitialValue());
			case OrmPackage.XML_GENERATOR__SPECIFIED_INITIAL_VALUE :
				return new Integer(getSpecifiedInitialValue());
			case OrmPackage.XML_GENERATOR__DEFAULT_INITIAL_VALUE :
				return new Integer(getDefaultInitialValue());
			case OrmPackage.XML_GENERATOR__ALLOCATION_SIZE :
				return new Integer(getAllocationSize());
			case OrmPackage.XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE :
				return new Integer(getSpecifiedAllocationSize());
			case OrmPackage.XML_GENERATOR__DEFAULT_ALLOCATION_SIZE :
				return new Integer(getDefaultAllocationSize());
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
			case OrmPackage.XML_GENERATOR__NAME :
				setName((String) newValue);
				return;
			case OrmPackage.XML_GENERATOR__SPECIFIED_INITIAL_VALUE :
				setSpecifiedInitialValue(((Integer) newValue).intValue());
				return;
			case OrmPackage.XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE :
				setSpecifiedAllocationSize(((Integer) newValue).intValue());
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
			case OrmPackage.XML_GENERATOR__NAME :
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.XML_GENERATOR__SPECIFIED_INITIAL_VALUE :
				setSpecifiedInitialValue(SPECIFIED_INITIAL_VALUE_EDEFAULT);
				return;
			case OrmPackage.XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE :
				setSpecifiedAllocationSize(SPECIFIED_ALLOCATION_SIZE_EDEFAULT);
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
			case OrmPackage.XML_GENERATOR__NAME :
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.XML_GENERATOR__INITIAL_VALUE :
				return getInitialValue() != INITIAL_VALUE_EDEFAULT;
			case OrmPackage.XML_GENERATOR__SPECIFIED_INITIAL_VALUE :
				return specifiedInitialValue != SPECIFIED_INITIAL_VALUE_EDEFAULT;
			case OrmPackage.XML_GENERATOR__DEFAULT_INITIAL_VALUE :
				return defaultInitialValue != DEFAULT_INITIAL_VALUE_EDEFAULT;
			case OrmPackage.XML_GENERATOR__ALLOCATION_SIZE :
				return getAllocationSize() != ALLOCATION_SIZE_EDEFAULT;
			case OrmPackage.XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE :
				return specifiedAllocationSize != SPECIFIED_ALLOCATION_SIZE_EDEFAULT;
			case OrmPackage.XML_GENERATOR__DEFAULT_ALLOCATION_SIZE :
				return defaultAllocationSize != DEFAULT_ALLOCATION_SIZE_EDEFAULT;
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
		if (baseClass == IGenerator.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_GENERATOR__NAME :
					return JpaCoreMappingsPackage.IGENERATOR__NAME;
				case OrmPackage.XML_GENERATOR__INITIAL_VALUE :
					return JpaCoreMappingsPackage.IGENERATOR__INITIAL_VALUE;
				case OrmPackage.XML_GENERATOR__SPECIFIED_INITIAL_VALUE :
					return JpaCoreMappingsPackage.IGENERATOR__SPECIFIED_INITIAL_VALUE;
				case OrmPackage.XML_GENERATOR__DEFAULT_INITIAL_VALUE :
					return JpaCoreMappingsPackage.IGENERATOR__DEFAULT_INITIAL_VALUE;
				case OrmPackage.XML_GENERATOR__ALLOCATION_SIZE :
					return JpaCoreMappingsPackage.IGENERATOR__ALLOCATION_SIZE;
				case OrmPackage.XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE :
					return JpaCoreMappingsPackage.IGENERATOR__SPECIFIED_ALLOCATION_SIZE;
				case OrmPackage.XML_GENERATOR__DEFAULT_ALLOCATION_SIZE :
					return JpaCoreMappingsPackage.IGENERATOR__DEFAULT_ALLOCATION_SIZE;
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
		if (baseClass == IGenerator.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IGENERATOR__NAME :
					return OrmPackage.XML_GENERATOR__NAME;
				case JpaCoreMappingsPackage.IGENERATOR__INITIAL_VALUE :
					return OrmPackage.XML_GENERATOR__INITIAL_VALUE;
				case JpaCoreMappingsPackage.IGENERATOR__SPECIFIED_INITIAL_VALUE :
					return OrmPackage.XML_GENERATOR__SPECIFIED_INITIAL_VALUE;
				case JpaCoreMappingsPackage.IGENERATOR__DEFAULT_INITIAL_VALUE :
					return OrmPackage.XML_GENERATOR__DEFAULT_INITIAL_VALUE;
				case JpaCoreMappingsPackage.IGENERATOR__ALLOCATION_SIZE :
					return OrmPackage.XML_GENERATOR__ALLOCATION_SIZE;
				case JpaCoreMappingsPackage.IGENERATOR__SPECIFIED_ALLOCATION_SIZE :
					return OrmPackage.XML_GENERATOR__SPECIFIED_ALLOCATION_SIZE;
				case JpaCoreMappingsPackage.IGENERATOR__DEFAULT_ALLOCATION_SIZE :
					return OrmPackage.XML_GENERATOR__DEFAULT_ALLOCATION_SIZE;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", specifiedInitialValue: ");
		result.append(specifiedInitialValue);
		result.append(", defaultInitialValue: ");
		result.append(defaultInitialValue);
		result.append(", specifiedAllocationSize: ");
		result.append(specifiedAllocationSize);
		result.append(", defaultAllocationSize: ");
		result.append(defaultAllocationSize);
		result.append(')');
		return result.toString();
	}
} // XmlGenerator
