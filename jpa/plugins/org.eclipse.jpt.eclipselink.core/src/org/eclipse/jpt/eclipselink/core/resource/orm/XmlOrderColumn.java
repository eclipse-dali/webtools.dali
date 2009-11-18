/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderCorrectionType_2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.XmlOrderColumn_2_0;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Order Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOrderColumn()
 * @model kind="class"
 * @generated
 */
public class XmlOrderColumn extends org.eclipse.jpt.core.resource.orm.XmlOrderColumn implements XmlOrderColumn_2_0
{

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final OrderCorrectionType_2_0 CORRECTION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCorrectionType() <em>Correction Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrectionType()
	 * @generated
	 * @ordered
	 */
	protected OrderCorrectionType_2_0 correctionType = CORRECTION_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlOrderColumn()
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
		return EclipseLinkOrmPackage.Literals.XML_ORDER_COLUMN;
	}

	/**
	 * Returns the value of the '<em><b>Correction Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderCorrectionType_2_0}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Correction Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correction Type</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderCorrectionType_2_0
	 * @see #setCorrectionType(OrderCorrectionType_2_0)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOrderColumn_2_0_CorrectionType()
	 * @model
	 * @generated
	 */
	public OrderCorrectionType_2_0 getCorrectionType()
	{
		return correctionType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOrderColumn#getCorrectionType <em>Correction Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Correction Type</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderCorrectionType_2_0
	 * @see #getCorrectionType()
	 * @generated
	 */
	public void setCorrectionType(OrderCorrectionType_2_0 newCorrectionType)
	{
		OrderCorrectionType_2_0 oldCorrectionType = correctionType;
		correctionType = newCorrectionType == null ? CORRECTION_TYPE_EDEFAULT : newCorrectionType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ORDER_COLUMN__CORRECTION_TYPE, oldCorrectionType, correctionType));
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
			case EclipseLinkOrmPackage.XML_ORDER_COLUMN__CORRECTION_TYPE:
				return getCorrectionType();
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
			case EclipseLinkOrmPackage.XML_ORDER_COLUMN__CORRECTION_TYPE:
				setCorrectionType((OrderCorrectionType_2_0)newValue);
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
			case EclipseLinkOrmPackage.XML_ORDER_COLUMN__CORRECTION_TYPE:
				setCorrectionType(CORRECTION_TYPE_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_ORDER_COLUMN__CORRECTION_TYPE:
				return correctionType != CORRECTION_TYPE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlOrderColumn_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ORDER_COLUMN__CORRECTION_TYPE: return EclipseLinkOrmV2_0Package.XML_ORDER_COLUMN_20__CORRECTION_TYPE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlOrderColumn_2_0.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_0Package.XML_ORDER_COLUMN_20__CORRECTION_TYPE: return EclipseLinkOrmPackage.XML_ORDER_COLUMN__CORRECTION_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (correctionType: ");
		result.append(correctionType);
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
			buildNameTranslator(),
			buildNullableTranslator(),
			buildInsertableTranslator(),
			buildUpdatableTranslator(),
			buildColumnDefinitionTranslator(),
			buildCorrectionTypeTranslator(),
		};
	}
		
	protected static Translator buildCorrectionTypeTranslator() {
		return new Translator(EclipseLink2_0.CORRECTION_TYPE, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlOrderColumn_2_0_CorrectionType(), Translator.DOM_ATTRIBUTE);
	}
}
