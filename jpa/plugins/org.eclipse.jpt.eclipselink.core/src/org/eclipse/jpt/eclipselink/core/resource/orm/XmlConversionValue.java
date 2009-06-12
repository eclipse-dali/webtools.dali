/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Conversion Value</b></em>'.
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
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getDataValue <em>Data Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getObjectValue <em>Object Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class XmlConversionValue extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * The default value of the '{@link #getDataValue() <em>Data Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DATA_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDataValue() <em>Data Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataValue()
	 * @generated
	 * @ordered
	 */
	protected String dataValue = DATA_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getObjectValue() <em>Object Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectValue()
	 * @generated
	 * @ordered
	 */
	protected static final String OBJECT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObjectValue() <em>Object Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectValue()
	 * @generated
	 * @ordered
	 */
	protected String objectValue = OBJECT_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlConversionValue()
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
		return EclipseLinkOrmPackage.Literals.XML_CONVERSION_VALUE;
	}

	/**
	 * Returns the value of the '<em><b>Data Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Value</em>' attribute.
	 * @see #setDataValue(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue_DataValue()
	 * @model
	 * @generated
	 */
	public String getDataValue()
	{
		return dataValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getDataValue <em>Data Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Value</em>' attribute.
	 * @see #getDataValue()
	 * @generated
	 */
	public void setDataValue(String newDataValue)
	{
		String oldDataValue = dataValue;
		dataValue = newDataValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CONVERSION_VALUE__DATA_VALUE, oldDataValue, dataValue));
	}

	/**
	 * Returns the value of the '<em><b>Object Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Value</em>' attribute.
	 * @see #setObjectValue(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue_ObjectValue()
	 * @model
	 * @generated
	 */
	public String getObjectValue()
	{
		return objectValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getObjectValue <em>Object Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Value</em>' attribute.
	 * @see #getObjectValue()
	 * @generated
	 */
	public void setObjectValue(String newObjectValue)
	{
		String oldObjectValue = objectValue;
		objectValue = newObjectValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CONVERSION_VALUE__OBJECT_VALUE, oldObjectValue, objectValue));
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
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE__DATA_VALUE:
				return getDataValue();
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE__OBJECT_VALUE:
				return getObjectValue();
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
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE__DATA_VALUE:
				setDataValue((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE__OBJECT_VALUE:
				setObjectValue((String)newValue);
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
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE__DATA_VALUE:
				setDataValue(DATA_VALUE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE__OBJECT_VALUE:
				setObjectValue(OBJECT_VALUE_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE__DATA_VALUE:
				return DATA_VALUE_EDEFAULT == null ? dataValue != null : !DATA_VALUE_EDEFAULT.equals(dataValue);
			case EclipseLinkOrmPackage.XML_CONVERSION_VALUE__OBJECT_VALUE:
				return OBJECT_VALUE_EDEFAULT == null ? objectValue != null : !OBJECT_VALUE_EDEFAULT.equals(objectValue);
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
		result.append(" (dataValue: ");
		result.append(dataValue);
		result.append(", objectValue: ");
		result.append(objectValue);
		result.append(')');
		return result.toString();
	}
	
	public TextRange getDataValueTextRange() {
		return getAttributeTextRange(JPA.CONVERSION_VALUE__DATA_VALUE);
	}
	
	public TextRange getObjectValueTextRange() {
		return getAttributeTextRange(JPA.CONVERSION_VALUE__OBJECT_VALUE);
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildDataValueTranslator(),
			buildObjectValueTranslator()
		};
	}
	
	protected static Translator buildDataValueTranslator() {
		return new Translator(JPA.CONVERSION_VALUE__DATA_VALUE, EclipseLinkOrmPackage.eINSTANCE.getXmlConversionValue_DataValue(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildObjectValueTranslator() {
		return new Translator(JPA.CONVERSION_VALUE__OBJECT_VALUE, EclipseLinkOrmPackage.eINSTANCE.getXmlConversionValue_ObjectValue(), Translator.DOM_ATTRIBUTE);
	}
	
} // XmlConversionValue
