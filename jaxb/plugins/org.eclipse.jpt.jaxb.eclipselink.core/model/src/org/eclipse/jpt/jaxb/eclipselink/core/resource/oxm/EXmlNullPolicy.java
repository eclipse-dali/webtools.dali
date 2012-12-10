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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Null Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy#isIsSetPerformedForAbsentNode <em>Is Set Performed For Absent Node</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlNullPolicy()
 * @model kind="class"
 * @generated
 */
public class EXmlNullPolicy extends EAbstractXmlNullPolicy
{
	/**
	 * The default value of the '{@link #isIsSetPerformedForAbsentNode() <em>Is Set Performed For Absent Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSetPerformedForAbsentNode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SET_PERFORMED_FOR_ABSENT_NODE_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isIsSetPerformedForAbsentNode() <em>Is Set Performed For Absent Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSetPerformedForAbsentNode()
	 * @generated
	 * @ordered
	 */
	protected boolean isSetPerformedForAbsentNode = IS_SET_PERFORMED_FOR_ABSENT_NODE_EDEFAULT;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlNullPolicy()
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
		return OxmPackage.Literals.EXML_NULL_POLICY;
	}
	
	
	/**
	 * Returns the value of the '<em><b>Is Set Performed For Absent Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Set Performed For Absent Node</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Set Performed For Absent Node</em>' attribute.
	 * @see #setIsSetPerformedForAbsentNode(boolean)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlNullPolicy_IsSetPerformedForAbsentNode()
	 * @model
	 * @generated
	 */
	public boolean isIsSetPerformedForAbsentNode()
	{
		return isSetPerformedForAbsentNode;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlNullPolicy#isIsSetPerformedForAbsentNode <em>Is Set Performed For Absent Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Set Performed For Absent Node</em>' attribute.
	 * @see #isIsSetPerformedForAbsentNode()
	 * @generated
	 */
	public void setIsSetPerformedForAbsentNode(boolean newIsSetPerformedForAbsentNode)
	{
		boolean oldIsSetPerformedForAbsentNode = isSetPerformedForAbsentNode;
		isSetPerformedForAbsentNode = newIsSetPerformedForAbsentNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_NULL_POLICY__IS_SET_PERFORMED_FOR_ABSENT_NODE, oldIsSetPerformedForAbsentNode, isSetPerformedForAbsentNode));
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
			case OxmPackage.EXML_NULL_POLICY__IS_SET_PERFORMED_FOR_ABSENT_NODE:
				return isIsSetPerformedForAbsentNode();
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
			case OxmPackage.EXML_NULL_POLICY__IS_SET_PERFORMED_FOR_ABSENT_NODE:
				setIsSetPerformedForAbsentNode((Boolean)newValue);
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
			case OxmPackage.EXML_NULL_POLICY__IS_SET_PERFORMED_FOR_ABSENT_NODE:
				setIsSetPerformedForAbsentNode(IS_SET_PERFORMED_FOR_ABSENT_NODE_EDEFAULT);
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
			case OxmPackage.EXML_NULL_POLICY__IS_SET_PERFORMED_FOR_ABSENT_NODE:
				return isSetPerformedForAbsentNode != IS_SET_PERFORMED_FOR_ABSENT_NODE_EDEFAULT;
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
		result.append(" (isSetPerformedForAbsentNode: ");
		result.append(isSetPerformedForAbsentNode);
		result.append(')');
		return result.toString();
	}


	// ***** translators *****
	
	static class XmlNullPolicyTranslator
			extends AbstractAbstractXmlNullPolicyTranslator {
		
		public XmlNullPolicyTranslator() {
			super(Oxm.XML_NULL_POLICY, buildTranslatorChildren());
		}
		
		private static Translator[] buildTranslatorChildren() {
			return new Translator[] {
			};
		}
		
		@Override
		public EObject createEMFObject(String nodeName, String readAheadName) {
			return OxmFactory.eINSTANCE.createEXmlNullPolicy();
		}
	}
}