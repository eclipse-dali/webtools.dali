/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Access Methods</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods#getGetMethod <em>Get Method</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods#getSetMethod <em>Set Method</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethods()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class XmlAccessMethods extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * The default value of the '{@link #getGetMethod() <em>Get Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String GET_METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGetMethod() <em>Get Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetMethod()
	 * @generated
	 * @ordered
	 */
	protected String getMethod = GET_METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getSetMethod() <em>Set Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String SET_METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSetMethod() <em>Set Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetMethod()
	 * @generated
	 * @ordered
	 */
	protected String setMethod = SET_METHOD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlAccessMethods()
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
		return EclipseLinkOrmPackage.Literals.XML_ACCESS_METHODS;
	}

	/**
	 * Returns the value of the '<em><b>Get Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Get Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Get Method</em>' attribute.
	 * @see #setGetMethod(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethods_GetMethod()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getGetMethod()
	{
		return getMethod;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods#getGetMethod <em>Get Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Get Method</em>' attribute.
	 * @see #getGetMethod()
	 * @generated
	 */
	public void setGetMethod(String newGetMethod)
	{
		String oldGetMethod = getMethod;
		getMethod = newGetMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ACCESS_METHODS__GET_METHOD, oldGetMethod, getMethod));
	}

	/**
	 * Returns the value of the '<em><b>Set Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Set Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Set Method</em>' attribute.
	 * @see #setSetMethod(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethods_SetMethod()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getSetMethod()
	{
		return setMethod;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods#getSetMethod <em>Set Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Set Method</em>' attribute.
	 * @see #getSetMethod()
	 * @generated
	 */
	public void setSetMethod(String newSetMethod)
	{
		String oldSetMethod = setMethod;
		setMethod = newSetMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ACCESS_METHODS__SET_METHOD, oldSetMethod, setMethod));
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
			case EclipseLinkOrmPackage.XML_ACCESS_METHODS__GET_METHOD:
				return getGetMethod();
			case EclipseLinkOrmPackage.XML_ACCESS_METHODS__SET_METHOD:
				return getSetMethod();
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
			case EclipseLinkOrmPackage.XML_ACCESS_METHODS__GET_METHOD:
				setGetMethod((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_ACCESS_METHODS__SET_METHOD:
				setSetMethod((String)newValue);
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
			case EclipseLinkOrmPackage.XML_ACCESS_METHODS__GET_METHOD:
				setGetMethod(GET_METHOD_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_ACCESS_METHODS__SET_METHOD:
				setSetMethod(SET_METHOD_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_ACCESS_METHODS__GET_METHOD:
				return GET_METHOD_EDEFAULT == null ? getMethod != null : !GET_METHOD_EDEFAULT.equals(getMethod);
			case EclipseLinkOrmPackage.XML_ACCESS_METHODS__SET_METHOD:
				return SET_METHOD_EDEFAULT == null ? setMethod != null : !SET_METHOD_EDEFAULT.equals(setMethod);
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
		result.append(" (getMethod: ");
		result.append(getMethod);
		result.append(", setMethod: ");
		result.append(setMethod);
		result.append(')');
		return result.toString();
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			Translator.END_TAG_NO_INDENT,
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildGetMethodTranslator(),
			buildSetMethodTranslator(),
		};
	}
	
	protected static Translator buildGetMethodTranslator() {
		return new Translator(EclipseLink.ACCESS_METHODS__GET_METHOD, EclipseLinkOrmPackage.eINSTANCE.getXmlAccessMethods_GetMethod(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildSetMethodTranslator() {
		return new Translator(EclipseLink.ACCESS_METHODS__SET_METHOD, EclipseLinkOrmPackage.eINSTANCE.getXmlAccessMethods_SetMethod(), Translator.DOM_ATTRIBUTE);
	}
	
} // XmlAccessMethods
