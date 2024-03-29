/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Customizer</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer#getCustomizerClassName <em>Customizer Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizer()
 * @model kind="class"
 * @extends EBaseObject
 * @generated
 */
public class XmlCustomizer extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getCustomizerClassName() <em>Customizer Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomizerClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CUSTOMIZER_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCustomizerClassName() <em>Customizer Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomizerClassName()
	 * @generated
	 * @ordered
	 */
	protected String customizerClassName = CUSTOMIZER_CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlCustomizer()
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
		return EclipseLinkOrmPackage.Literals.XML_CUSTOMIZER;
	}

	/**
	 * Returns the value of the '<em><b>Customizer Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Customizer Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Customizer Class Name</em>' attribute.
	 * @see #setCustomizerClassName(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlCustomizer_CustomizerClassName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getCustomizerClassName()
	{
		return customizerClassName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCustomizer#getCustomizerClassName <em>Customizer Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Customizer Class Name</em>' attribute.
	 * @see #getCustomizerClassName()
	 * @generated
	 */
	public void setCustomizerClassName(String newCustomizerClassName)
	{
		String oldCustomizerClassName = customizerClassName;
		customizerClassName = newCustomizerClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_CUSTOMIZER__CUSTOMIZER_CLASS_NAME, oldCustomizerClassName, customizerClassName));
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
			case EclipseLinkOrmPackage.XML_CUSTOMIZER__CUSTOMIZER_CLASS_NAME:
				return getCustomizerClassName();
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
			case EclipseLinkOrmPackage.XML_CUSTOMIZER__CUSTOMIZER_CLASS_NAME:
				setCustomizerClassName((String)newValue);
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
			case EclipseLinkOrmPackage.XML_CUSTOMIZER__CUSTOMIZER_CLASS_NAME:
				setCustomizerClassName(CUSTOMIZER_CLASS_NAME_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_CUSTOMIZER__CUSTOMIZER_CLASS_NAME:
				return CUSTOMIZER_CLASS_NAME_EDEFAULT == null ? customizerClassName != null : !CUSTOMIZER_CLASS_NAME_EDEFAULT.equals(customizerClassName);
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
		result.append(" (customizerClassName: ");
		result.append(customizerClassName);
		result.append(')');
		return result.toString();
	}

} // XmlCustomizer
