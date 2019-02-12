/*******************************************************************************
 * Copyright (c) 2012, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.internal.utility.translators.StringAttributeTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Enum</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getJavaEnum <em>Java Enum</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getXmlEnumValues <em>Xml Enum Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnum()
 * @model kind="class"
 * @generated
 */
public class EXmlEnum extends EAbstractTypeMapping
{
	/**
	 * The default value of the '{@link #getJavaEnum() <em>Java Enum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaEnum()
	 * @generated
	 * @ordered
	 */
	protected static final String JAVA_ENUM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJavaEnum() <em>Java Enum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaEnum()
	 * @generated
	 * @ordered
	 */
	protected String javaEnum = JAVA_ENUM_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getXmlEnumValues() <em>Xml Enum Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlEnumValues()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlEnumValue> xmlEnumValues;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlEnum()
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
		return OxmPackage.Literals.EXML_ENUM;
	}

	/**
	 * Returns the value of the '<em><b>Java Enum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Enum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Enum</em>' attribute.
	 * @see #setJavaEnum(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnum_JavaEnum()
	 * @model
	 * @generated
	 */
	public String getJavaEnum()
	{
		return javaEnum;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getJavaEnum <em>Java Enum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Enum</em>' attribute.
	 * @see #getJavaEnum()
	 * @generated
	 */
	public void setJavaEnum(String newJavaEnum)
	{
		String oldJavaEnum = javaEnum;
		javaEnum = newJavaEnum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ENUM__JAVA_ENUM, oldJavaEnum, javaEnum));
	}

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnum_Value()
	 * @model
	 * @generated
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	public void setValue(String newValue)
	{
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ENUM__VALUE, oldValue, value));
	}

	/**
	 * Returns the value of the '<em><b>Xml Enum Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Enum Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Enum Values</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnum_XmlEnumValues()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlEnumValue> getXmlEnumValues()
	{
		if (xmlEnumValues == null)
		{
			xmlEnumValues = new EObjectContainmentEList<EXmlEnumValue>(EXmlEnumValue.class, this, OxmPackage.EXML_ENUM__XML_ENUM_VALUES);
		}
		return xmlEnumValues;
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
			case OxmPackage.EXML_ENUM__XML_ENUM_VALUES:
				return ((InternalEList<?>)getXmlEnumValues()).basicRemove(otherEnd, msgs);
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
			case OxmPackage.EXML_ENUM__JAVA_ENUM:
				return getJavaEnum();
			case OxmPackage.EXML_ENUM__VALUE:
				return getValue();
			case OxmPackage.EXML_ENUM__XML_ENUM_VALUES:
				return getXmlEnumValues();
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
			case OxmPackage.EXML_ENUM__JAVA_ENUM:
				setJavaEnum((String)newValue);
				return;
			case OxmPackage.EXML_ENUM__VALUE:
				setValue((String)newValue);
				return;
			case OxmPackage.EXML_ENUM__XML_ENUM_VALUES:
				getXmlEnumValues().clear();
				getXmlEnumValues().addAll((Collection<? extends EXmlEnumValue>)newValue);
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
			case OxmPackage.EXML_ENUM__JAVA_ENUM:
				setJavaEnum(JAVA_ENUM_EDEFAULT);
				return;
			case OxmPackage.EXML_ENUM__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case OxmPackage.EXML_ENUM__XML_ENUM_VALUES:
				getXmlEnumValues().clear();
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
			case OxmPackage.EXML_ENUM__JAVA_ENUM:
				return JAVA_ENUM_EDEFAULT == null ? javaEnum != null : !JAVA_ENUM_EDEFAULT.equals(javaEnum);
			case OxmPackage.EXML_ENUM__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case OxmPackage.EXML_ENUM__XML_ENUM_VALUES:
				return xmlEnumValues != null && !xmlEnumValues.isEmpty();
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
		result.append(" (javaEnum: ");
		result.append(javaEnum);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}
	
	
	// ***** text range *****
	
	public TextRange getJavaEnumTextRange() {
		return getAttributeTextRange(Oxm.JAVA_ENUM);
	}
	

	// ***** translators *****
	
	public static Translator buildTranslator() {
		return new SimpleTranslator(Oxm.XML_ENUMS + "/" + Oxm.XML_ENUM, OxmPackage.eINSTANCE.getEXmlBindings_XmlEnums(), buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildJavaEnumTranslator(),
			buildValueTranslator()
		};
	}
	
	protected static Translator buildJavaEnumTranslator() {
		return new StringAttributeTranslator(
			Oxm.JAVA_ENUM,
			OxmPackage.eINSTANCE.getEXmlEnum_JavaEnum());
	}
	
	protected static Translator buildValueTranslator() {
		return new StringAttributeTranslator(
			Oxm.VALUE,
			OxmPackage.eINSTANCE.getEXmlEnum_Value(), 
			Translator.IGNORE_DEFAULT_ATTRIBUTE_VALUE);
	}
}
