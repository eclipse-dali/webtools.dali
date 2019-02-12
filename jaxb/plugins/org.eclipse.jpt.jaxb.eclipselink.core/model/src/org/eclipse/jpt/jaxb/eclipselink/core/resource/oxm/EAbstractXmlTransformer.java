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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EAbstract Xml Transformer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer#getMethod <em>Method</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer#getTransformerClass <em>Transformer Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlTransformer()
 * @model kind="class" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public abstract class EAbstractXmlTransformer extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected String method = METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getTransformerClass() <em>Transformer Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformerClass()
	 * @generated
	 * @ordered
	 */
	protected static final String TRANSFORMER_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTransformerClass() <em>Transformer Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformerClass()
	 * @generated
	 * @ordered
	 */
	protected String transformerClass = TRANSFORMER_CLASS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EAbstractXmlTransformer()
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
		return OxmPackage.Literals.EABSTRACT_XML_TRANSFORMER;
	}

	/**
	 * Returns the value of the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' attribute.
	 * @see #setMethod(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlTransformer_Method()
	 * @model
	 * @generated
	 */
	public String getMethod()
	{
		return method;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer#getMethod <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method</em>' attribute.
	 * @see #getMethod()
	 * @generated
	 */
	public void setMethod(String newMethod)
	{
		String oldMethod = method;
		method = newMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_XML_TRANSFORMER__METHOD, oldMethod, method));
	}

	/**
	 * Returns the value of the '<em><b>Transformer Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformer Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformer Class</em>' attribute.
	 * @see #setTransformerClass(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEAbstractXmlTransformer_TransformerClass()
	 * @model
	 * @generated
	 */
	public String getTransformerClass()
	{
		return transformerClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EAbstractXmlTransformer#getTransformerClass <em>Transformer Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transformer Class</em>' attribute.
	 * @see #getTransformerClass()
	 * @generated
	 */
	public void setTransformerClass(String newTransformerClass)
	{
		String oldTransformerClass = transformerClass;
		transformerClass = newTransformerClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS, oldTransformerClass, transformerClass));
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
			case OxmPackage.EABSTRACT_XML_TRANSFORMER__METHOD:
				return getMethod();
			case OxmPackage.EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS:
				return getTransformerClass();
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
			case OxmPackage.EABSTRACT_XML_TRANSFORMER__METHOD:
				setMethod((String)newValue);
				return;
			case OxmPackage.EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS:
				setTransformerClass((String)newValue);
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
			case OxmPackage.EABSTRACT_XML_TRANSFORMER__METHOD:
				setMethod(METHOD_EDEFAULT);
				return;
			case OxmPackage.EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS:
				setTransformerClass(TRANSFORMER_CLASS_EDEFAULT);
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
			case OxmPackage.EABSTRACT_XML_TRANSFORMER__METHOD:
				return METHOD_EDEFAULT == null ? method != null : !METHOD_EDEFAULT.equals(method);
			case OxmPackage.EABSTRACT_XML_TRANSFORMER__TRANSFORMER_CLASS:
				return TRANSFORMER_CLASS_EDEFAULT == null ? transformerClass != null : !TRANSFORMER_CLASS_EDEFAULT.equals(transformerClass);
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
		result.append(" (method: ");
		result.append(method);
		result.append(", transformerClass: ");
		result.append(transformerClass);
		result.append(')');
		return result.toString();
	}

} // EAbstractXmlTransformer
