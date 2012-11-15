/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLink2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Join Field</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinField()
 * @model kind="class"
 * @generated
 */
public class XmlJoinField extends EBaseObjectImpl implements XmlJoinField_2_4
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
	 * The default value of the '{@link #getReferencedFieldName() <em>Referenced Field Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedFieldName()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCED_FIELD_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReferencedFieldName() <em>Referenced Field Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedFieldName()
	 * @generated
	 * @ordered
	 */
	protected String referencedFieldName = REFERENCED_FIELD_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlJoinField()
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
		return EclipseLinkOrmPackage.Literals.XML_JOIN_FIELD;
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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinField_2_4_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinField#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_JOIN_FIELD__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Referenced Field Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Field Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Field Name</em>' attribute.
	 * @see #setReferencedFieldName(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinField_2_4_ReferencedFieldName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getReferencedFieldName()
	{
		return referencedFieldName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinField#getReferencedFieldName <em>Referenced Field Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenced Field Name</em>' attribute.
	 * @see #getReferencedFieldName()
	 * @generated
	 */
	public void setReferencedFieldName(String newReferencedFieldName)
	{
		String oldReferencedFieldName = referencedFieldName;
		referencedFieldName = newReferencedFieldName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_JOIN_FIELD__REFERENCED_FIELD_NAME, oldReferencedFieldName, referencedFieldName));
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
			case EclipseLinkOrmPackage.XML_JOIN_FIELD__NAME:
				return getName();
			case EclipseLinkOrmPackage.XML_JOIN_FIELD__REFERENCED_FIELD_NAME:
				return getReferencedFieldName();
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
			case EclipseLinkOrmPackage.XML_JOIN_FIELD__NAME:
				setName((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_JOIN_FIELD__REFERENCED_FIELD_NAME:
				setReferencedFieldName((String)newValue);
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
			case EclipseLinkOrmPackage.XML_JOIN_FIELD__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_JOIN_FIELD__REFERENCED_FIELD_NAME:
				setReferencedFieldName(REFERENCED_FIELD_NAME_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_JOIN_FIELD__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EclipseLinkOrmPackage.XML_JOIN_FIELD__REFERENCED_FIELD_NAME:
				return REFERENCED_FIELD_NAME_EDEFAULT == null ? referencedFieldName != null : !REFERENCED_FIELD_NAME_EDEFAULT.equals(referencedFieldName);
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
		result.append(", referencedFieldName: ");
		result.append(referencedFieldName);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLinkOrmPackage.eINSTANCE.getXmlJoinField(), 
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildReferencedFieldNameTranslator(),
		};
	}

	protected static Translator buildNameTranslator() {
		return new Translator(EclipseLink2_4.JOIN_FIELD__NAME, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlJoinField_2_4_Name(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildReferencedFieldNameTranslator() {
		return new Translator(EclipseLink2_4.JOIN_FIELD__REFERENCED_FIELD_NAME, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlJoinField_2_4_ReferencedFieldName(), Translator.DOM_ATTRIBUTE);
	}
} // XmlJoinField
