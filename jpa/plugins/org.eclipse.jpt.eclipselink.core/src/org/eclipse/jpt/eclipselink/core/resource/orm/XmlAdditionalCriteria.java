/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlAdditionalCriteria_2_2;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Additional Criteria</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAdditionalCriteria()
 * @model kind="class"
 * @generated
 */
public class XmlAdditionalCriteria extends AbstractJpaEObject implements XmlAdditionalCriteria_2_2
{
	/**
	 * The default value of the '{@link #getCriteria() <em>Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCriteria()
	 * @generated
	 * @ordered
	 */
	protected static final String CRITERIA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCriteria() <em>Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCriteria()
	 * @generated
	 * @ordered
	 */
	protected String criteria = CRITERIA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlAdditionalCriteria()
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
		return EclipseLinkOrmPackage.Literals.XML_ADDITIONAL_CRITERIA;
	}

	/**
	 * Returns the value of the '<em><b>Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Criteria</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Criteria</em>' attribute.
	 * @see #setCriteria(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAdditionalCriteria_2_2_Criteria()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getCriteria()
	{
		return criteria;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlAdditionalCriteria#getCriteria <em>Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Criteria</em>' attribute.
	 * @see #getCriteria()
	 * @generated
	 */
	public void setCriteria(String newCriteria)
	{
		String oldCriteria = criteria;
		criteria = newCriteria;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ADDITIONAL_CRITERIA__CRITERIA, oldCriteria, criteria));
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
			case EclipseLinkOrmPackage.XML_ADDITIONAL_CRITERIA__CRITERIA:
				return getCriteria();
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
			case EclipseLinkOrmPackage.XML_ADDITIONAL_CRITERIA__CRITERIA:
				setCriteria((String)newValue);
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
			case EclipseLinkOrmPackage.XML_ADDITIONAL_CRITERIA__CRITERIA:
				setCriteria(CRITERIA_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_ADDITIONAL_CRITERIA__CRITERIA:
				return CRITERIA_EDEFAULT == null ? criteria != null : !CRITERIA_EDEFAULT.equals(criteria);
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
		result.append(" (criteria: ");
		result.append(criteria);
		result.append(')');
		return result.toString();
	}


	// ********** translators **********
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLinkOrmV2_2Package.eINSTANCE.getXmlAdditionalCriteria_2_2(), 
			buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildCriteriaTranslator()
		};
	}
	
	protected static Translator buildCriteriaTranslator() {
		return new Translator(EclipseLink2_2.CRITERIA, EclipseLinkOrmV2_2Package.eINSTANCE.getXmlAdditionalCriteria_2_2_Criteria());
	}
	
} // XmlAdditionalCriteria
