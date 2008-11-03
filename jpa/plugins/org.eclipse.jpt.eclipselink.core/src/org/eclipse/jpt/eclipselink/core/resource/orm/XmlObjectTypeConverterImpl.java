/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Object Type CustomConverter</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 * 
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverterImpl()
 * @model kind="class"
 * @generated
 */
public class XmlObjectTypeConverterImpl extends AbstractJpaEObject implements XmlObjectTypeConverter
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
	 * The default value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected static final String DATA_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected String dataType = DATA_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getObjectType() <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectType()
	 * @generated
	 * @ordered
	 */
	protected static final String OBJECT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObjectType() <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectType()
	 * @generated
	 * @ordered
	 */
	protected String objectType = OBJECT_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConversionValues() <em>Conversion Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConversionValues()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlConversionValue> conversionValues;

	/**
	 * The default value of the '{@link #getDefaultObjectValue() <em>Default Object Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultObjectValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_OBJECT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultObjectValue() <em>Default Object Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultObjectValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultObjectValue = DEFAULT_OBJECT_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlObjectTypeConverterImpl()
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
		return EclipseLinkOrmPackage.Literals.XML_OBJECT_TYPE_CONVERTER_IMPL;
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedConverter_Name()
	 * @model
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see #setDataType(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverter_DataType()
	 * @model
	 * @generated
	 */
	public String getDataType()
	{
		return dataType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see #getDataType()
	 * @generated
	 */
	public void setDataType(String newDataType)
	{
		String oldDataType = dataType;
		dataType = newDataType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DATA_TYPE, oldDataType, dataType));
	}

	/**
	 * Returns the value of the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Type</em>' attribute.
	 * @see #setObjectType(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverter_ObjectType()
	 * @model
	 * @generated
	 */
	public String getObjectType()
	{
		return objectType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl#getObjectType <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Type</em>' attribute.
	 * @see #getObjectType()
	 * @generated
	 */
	public void setObjectType(String newObjectType)
	{
		String oldObjectType = objectType;
		objectType = newObjectType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__OBJECT_TYPE, oldObjectType, objectType));
	}

	/**
	 * Returns the value of the '<em><b>Conversion Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conversion Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conversion Values</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverter_ConversionValues()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConversionValue> getConversionValues()
	{
		if (conversionValues == null)
		{
			conversionValues = new EObjectContainmentEList<XmlConversionValue>(XmlConversionValue.class, this, EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__CONVERSION_VALUES);
		}
		return conversionValues;
	}

	/**
	 * Returns the value of the '<em><b>Default Object Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Object Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Object Value</em>' attribute.
	 * @see #setDefaultObjectValue(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlObjectTypeConverter_DefaultObjectValue()
	 * @model
	 * @generated
	 */
	public String getDefaultObjectValue()
	{
		return defaultObjectValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverterImpl#getDefaultObjectValue <em>Default Object Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Object Value</em>' attribute.
	 * @see #getDefaultObjectValue()
	 * @generated
	 */
	public void setDefaultObjectValue(String newDefaultObjectValue)
	{
		String oldDefaultObjectValue = defaultObjectValue;
		defaultObjectValue = newDefaultObjectValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DEFAULT_OBJECT_VALUE, oldDefaultObjectValue, defaultObjectValue));
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
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__CONVERSION_VALUES:
				return ((InternalEList<?>)getConversionValues()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__NAME:
				return getName();
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DATA_TYPE:
				return getDataType();
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__OBJECT_TYPE:
				return getObjectType();
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__CONVERSION_VALUES:
				return getConversionValues();
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DEFAULT_OBJECT_VALUE:
				return getDefaultObjectValue();
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
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__NAME:
				setName((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DATA_TYPE:
				setDataType((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__OBJECT_TYPE:
				setObjectType((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__CONVERSION_VALUES:
				getConversionValues().clear();
				getConversionValues().addAll((Collection<? extends XmlConversionValue>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DEFAULT_OBJECT_VALUE:
				setDefaultObjectValue((String)newValue);
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
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DATA_TYPE:
				setDataType(DATA_TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__OBJECT_TYPE:
				setObjectType(OBJECT_TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__CONVERSION_VALUES:
				getConversionValues().clear();
				return;
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DEFAULT_OBJECT_VALUE:
				setDefaultObjectValue(DEFAULT_OBJECT_VALUE_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DATA_TYPE:
				return DATA_TYPE_EDEFAULT == null ? dataType != null : !DATA_TYPE_EDEFAULT.equals(dataType);
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__OBJECT_TYPE:
				return OBJECT_TYPE_EDEFAULT == null ? objectType != null : !OBJECT_TYPE_EDEFAULT.equals(objectType);
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__CONVERSION_VALUES:
				return conversionValues != null && !conversionValues.isEmpty();
			case EclipseLinkOrmPackage.XML_OBJECT_TYPE_CONVERTER_IMPL__DEFAULT_OBJECT_VALUE:
				return DEFAULT_OBJECT_VALUE_EDEFAULT == null ? defaultObjectValue != null : !DEFAULT_OBJECT_VALUE_EDEFAULT.equals(defaultObjectValue);
		}
		return super.eIsSet(featureID);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", dataType: ");
		result.append(dataType);
		result.append(", objectType: ");
		result.append(objectType);
		result.append(", defaultObjectValue: ");
		result.append(defaultObjectValue);
		result.append(')');
		return result.toString();
	}

} // XmlObjectTypeConverter
