/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Clone Copy Policy</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 *
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCloneCopyPolicy#getMethod <em>Method</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCloneCopyPolicy#getWorkingCopyMethod <em>Working Copy Method</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCloneCopyPolicy()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class XmlCloneCopyPolicy extends AbstractJpaEObject implements JpaEObject
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
	 * The default value of the '{@link #getWorkingCopyMethod() <em>Working Copy Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkingCopyMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String WORKING_COPY_METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWorkingCopyMethod() <em>Working Copy Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkingCopyMethod()
	 * @generated
	 * @ordered
	 */
	protected String workingCopyMethod = WORKING_COPY_METHOD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlCloneCopyPolicy()
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
		return EclipseLinkOrmPackage.Literals.XML_CLONE_COPY_POLICY;
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCloneCopyPolicy_Method()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMethod()
	{
		return method;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCloneCopyPolicy#getMethod <em>Method</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__METHOD, oldMethod, method));
	}

	/**
	 * Returns the value of the '<em><b>Working Copy Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Working Copy Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Working Copy Method</em>' attribute.
	 * @see #setWorkingCopyMethod(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCloneCopyPolicy_WorkingCopyMethod()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getWorkingCopyMethod()
	{
		return workingCopyMethod;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlCloneCopyPolicy#getWorkingCopyMethod <em>Working Copy Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Working Copy Method</em>' attribute.
	 * @see #getWorkingCopyMethod()
	 * @generated
	 */
	public void setWorkingCopyMethod(String newWorkingCopyMethod)
	{
		String oldWorkingCopyMethod = workingCopyMethod;
		workingCopyMethod = newWorkingCopyMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__WORKING_COPY_METHOD, oldWorkingCopyMethod, workingCopyMethod));
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
			case EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__METHOD:
				return getMethod();
			case EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__WORKING_COPY_METHOD:
				return getWorkingCopyMethod();
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
			case EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__METHOD:
				setMethod((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__WORKING_COPY_METHOD:
				setWorkingCopyMethod((String)newValue);
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
			case EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__METHOD:
				setMethod(METHOD_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__WORKING_COPY_METHOD:
				setWorkingCopyMethod(WORKING_COPY_METHOD_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__METHOD:
				return METHOD_EDEFAULT == null ? method != null : !METHOD_EDEFAULT.equals(method);
			case EclipseLinkOrmPackage.XML_CLONE_COPY_POLICY__WORKING_COPY_METHOD:
				return WORKING_COPY_METHOD_EDEFAULT == null ? workingCopyMethod != null : !WORKING_COPY_METHOD_EDEFAULT.equals(workingCopyMethod);
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
		result.append(", workingCopyMethod: ");
		result.append(workingCopyMethod);
		result.append(')');
		return result.toString();
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildMethodTranslator(),
			buildWorkingCopyMethodTranslator(),
		};
	}
	
	protected static Translator buildMethodTranslator() {
		return new Translator(EclipseLink.CLONE_COPY_POLICY__METHOD, EclipseLinkOrmPackage.eINSTANCE.getXmlCloneCopyPolicy_Method(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildWorkingCopyMethodTranslator() {
		return new Translator(EclipseLink.CLONE_COPY_POLICY__WORKING_COPY_METHOD, EclipseLinkOrmPackage.eINSTANCE.getXmlCloneCopyPolicy_WorkingCopyMethod(), Translator.DOM_ATTRIBUTE);
	}
	
} // XmlCloneCopyPolicy
