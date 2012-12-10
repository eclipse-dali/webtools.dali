/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Enum Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue#getJavaEnumValue <em>Java Enum Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnumValue()
 * @model kind="class"
 * @extends EBaseObject
 * @generated
 */
public class EXmlEnumValue extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getJavaEnumValue() <em>Java Enum Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaEnumValue()
	 * @generated
	 * @ordered
	 */
	protected static final String JAVA_ENUM_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJavaEnumValue() <em>Java Enum Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaEnumValue()
	 * @generated
	 * @ordered
	 */
	protected String javaEnumValue = JAVA_ENUM_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlEnumValue()
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
		return OxmPackage.Literals.EXML_ENUM_VALUE;
	}

	/**
	 * Returns the value of the '<em><b>Java Enum Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Enum Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Enum Value</em>' attribute.
	 * @see #setJavaEnumValue(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlEnumValue_JavaEnumValue()
	 * @model
	 * @generated
	 */
	public String getJavaEnumValue()
	{
		return javaEnumValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnumValue#getJavaEnumValue <em>Java Enum Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Enum Value</em>' attribute.
	 * @see #getJavaEnumValue()
	 * @generated
	 */
	public void setJavaEnumValue(String newJavaEnumValue)
	{
		String oldJavaEnumValue = javaEnumValue;
		javaEnumValue = newJavaEnumValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ENUM_VALUE__JAVA_ENUM_VALUE, oldJavaEnumValue, javaEnumValue));
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
			case OxmPackage.EXML_ENUM_VALUE__JAVA_ENUM_VALUE:
				return getJavaEnumValue();
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
			case OxmPackage.EXML_ENUM_VALUE__JAVA_ENUM_VALUE:
				setJavaEnumValue((String)newValue);
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
			case OxmPackage.EXML_ENUM_VALUE__JAVA_ENUM_VALUE:
				setJavaEnumValue(JAVA_ENUM_VALUE_EDEFAULT);
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
			case OxmPackage.EXML_ENUM_VALUE__JAVA_ENUM_VALUE:
				return JAVA_ENUM_VALUE_EDEFAULT == null ? javaEnumValue != null : !JAVA_ENUM_VALUE_EDEFAULT.equals(javaEnumValue);
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
		result.append(" (javaEnumValue: ");
		result.append(javaEnumValue);
		result.append(')');
		return result.toString();
	}

} // EXmlEnumValue
