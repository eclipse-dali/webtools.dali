/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Is Set Null Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy#getIsSetMethodName <em>Is Set Method Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy#getXmlIsSetParameters <em>Xml Is Set Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlIsSetNullPolicy()
 * @model kind="class"
 * @generated
 */
public class EXmlIsSetNullPolicy extends EAbstractXmlNullPolicy
{
	/**
	 * The default value of the '{@link #getIsSetMethodName() <em>Is Set Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsSetMethodName()
	 * @generated
	 * @ordered
	 */
	protected static final String IS_SET_METHOD_NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getIsSetMethodName() <em>Is Set Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsSetMethodName()
	 * @generated
	 * @ordered
	 */
	protected String isSetMethodName = IS_SET_METHOD_NAME_EDEFAULT;
	/**
	 * The cached value of the '{@link #getXmlIsSetParameters() <em>Xml Is Set Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlIsSetParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<EXmlIsSetParameter> xmlIsSetParameters;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlIsSetNullPolicy()
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
		return OxmPackage.Literals.EXML_IS_SET_NULL_POLICY;
	}
	
	
	/**
	 * Returns the value of the '<em><b>Is Set Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Set Method Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Set Method Name</em>' attribute.
	 * @see #setIsSetMethodName(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlIsSetNullPolicy_IsSetMethodName()
	 * @model
	 * @generated
	 */
	public String getIsSetMethodName()
	{
		return isSetMethodName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetNullPolicy#getIsSetMethodName <em>Is Set Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Set Method Name</em>' attribute.
	 * @see #getIsSetMethodName()
	 * @generated
	 */
	public void setIsSetMethodName(String newIsSetMethodName)
	{
		String oldIsSetMethodName = isSetMethodName;
		isSetMethodName = newIsSetMethodName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_IS_SET_NULL_POLICY__IS_SET_METHOD_NAME, oldIsSetMethodName, isSetMethodName));
	}

	/**
	 * Returns the value of the '<em><b>Xml Is Set Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlIsSetParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Is Set Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Is Set Parameters</em>' containment reference list.
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlIsSetNullPolicy_XmlIsSetParameters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<EXmlIsSetParameter> getXmlIsSetParameters()
	{
		if (xmlIsSetParameters == null)
		{
			xmlIsSetParameters = new EObjectContainmentEList<EXmlIsSetParameter>(EXmlIsSetParameter.class, this, OxmPackage.EXML_IS_SET_NULL_POLICY__XML_IS_SET_PARAMETERS);
		}
		return xmlIsSetParameters;
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
			case OxmPackage.EXML_IS_SET_NULL_POLICY__XML_IS_SET_PARAMETERS:
				return ((InternalEList<?>)getXmlIsSetParameters()).basicRemove(otherEnd, msgs);
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
			case OxmPackage.EXML_IS_SET_NULL_POLICY__IS_SET_METHOD_NAME:
				return getIsSetMethodName();
			case OxmPackage.EXML_IS_SET_NULL_POLICY__XML_IS_SET_PARAMETERS:
				return getXmlIsSetParameters();
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
			case OxmPackage.EXML_IS_SET_NULL_POLICY__IS_SET_METHOD_NAME:
				setIsSetMethodName((String)newValue);
				return;
			case OxmPackage.EXML_IS_SET_NULL_POLICY__XML_IS_SET_PARAMETERS:
				getXmlIsSetParameters().clear();
				getXmlIsSetParameters().addAll((Collection<? extends EXmlIsSetParameter>)newValue);
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
			case OxmPackage.EXML_IS_SET_NULL_POLICY__IS_SET_METHOD_NAME:
				setIsSetMethodName(IS_SET_METHOD_NAME_EDEFAULT);
				return;
			case OxmPackage.EXML_IS_SET_NULL_POLICY__XML_IS_SET_PARAMETERS:
				getXmlIsSetParameters().clear();
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
			case OxmPackage.EXML_IS_SET_NULL_POLICY__IS_SET_METHOD_NAME:
				return IS_SET_METHOD_NAME_EDEFAULT == null ? isSetMethodName != null : !IS_SET_METHOD_NAME_EDEFAULT.equals(isSetMethodName);
			case OxmPackage.EXML_IS_SET_NULL_POLICY__XML_IS_SET_PARAMETERS:
				return xmlIsSetParameters != null && !xmlIsSetParameters.isEmpty();
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
		result.append(" (isSetMethodName: ");
		result.append(isSetMethodName);
		result.append(')');
		return result.toString();
	}


	// ***** translators *****
	
	static class XmlIsSetNullPolicyTranslator
			extends AbstractAbstractXmlNullPolicyTranslator {
		
		public XmlIsSetNullPolicyTranslator() {
			super(EclipseLink.XML_IS_SET_NULL_POLICY, buildTranslatorChildren());
		}
		
		private static Translator[] buildTranslatorChildren() {
			return new Translator[] {
			};
		}
		
		@Override
		public EObject createEMFObject(String nodeName, String readAheadName) {
			return OxmFactory.eINSTANCE.createEXmlIsSetNullPolicy();
		}
	}
}
