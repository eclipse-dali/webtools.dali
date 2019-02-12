/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.common.core.internal.utility.translators.BooleanTranslator;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Converter</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlConverter()
 * @model kind="class"
 * @generated
 */
public class XmlConverter extends AbstractXmlManagedType implements XmlConverter_2_1
{
	/**
	 * The default value of the '{@link #getAutoApply() <em>Auto Apply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutoApply()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean AUTO_APPLY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAutoApply() <em>Auto Apply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutoApply()
	 * @generated
	 * @ordered
	 */
	protected Boolean autoApply = AUTO_APPLY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlConverter()
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
		return OrmPackage.Literals.XML_CONVERTER;
	}

	/**
	 * Returns the value of the '<em><b>Auto Apply</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Apply</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Apply</em>' attribute.
	 * @see #setAutoApply(Boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlConverter_2_1_AutoApply()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getAutoApply()
	{
		return autoApply;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlConverter#getAutoApply <em>Auto Apply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Apply</em>' attribute.
	 * @see #getAutoApply()
	 * @generated
	 */
	public void setAutoApply(Boolean newAutoApply)
	{
		Boolean oldAutoApply = autoApply;
		autoApply = newAutoApply;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_CONVERTER__AUTO_APPLY, oldAutoApply, autoApply));
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
			case OrmPackage.XML_CONVERTER__AUTO_APPLY:
				return getAutoApply();
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
			case OrmPackage.XML_CONVERTER__AUTO_APPLY:
				setAutoApply((Boolean)newValue);
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
			case OrmPackage.XML_CONVERTER__AUTO_APPLY:
				setAutoApply(AUTO_APPLY_EDEFAULT);
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
			case OrmPackage.XML_CONVERTER__AUTO_APPLY:
				return AUTO_APPLY_EDEFAULT == null ? autoApply != null : !AUTO_APPLY_EDEFAULT.equals(autoApply);
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
		if (baseClass == XmlConverter_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_CONVERTER__AUTO_APPLY: return OrmV2_1Package.XML_CONVERTER_21__AUTO_APPLY;
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
		if (baseClass == XmlConverter_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_CONVERTER_21__AUTO_APPLY: return OrmPackage.XML_CONVERTER__AUTO_APPLY;
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
		result.append(" (autoApply: ");
		result.append(autoApply);
		result.append(')');
		return result.toString();
	}


	public Class<XmlConverter> getManagedTypeType() {
		return XmlConverter.class;
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			Translator.END_TAG_NO_INDENT,
			OrmPackage.eINSTANCE.getXmlConverter(),
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildClassTranslator(),
			buildAutoApplyTranslator(),
			buildDescriptionTranslator(),
		};
	}
	
	protected static Translator buildAutoApplyTranslator() {
		return new BooleanTranslator(JPA2_1.AUTO_APPLY, OrmV2_1Package.eINSTANCE.getXmlConverter_2_1_AutoApply(), Translator.DOM_ATTRIBUTE);
	}

} // XmlConverter
