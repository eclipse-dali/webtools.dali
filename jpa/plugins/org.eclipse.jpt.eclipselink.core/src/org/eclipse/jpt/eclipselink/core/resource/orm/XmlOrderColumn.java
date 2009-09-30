/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlOrderColumn.java,v 1.1 2009/09/30 23:17:52 pfullbright Exp $
 */
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderColumnValidationModeType_2_0;
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
	protected static final OrderColumnValidationModeType_2_0 VALIDATION_MODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValidationMode() <em>Validation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidationMode()
	 * @generated
	 * @ordered
	 */
	protected OrderColumnValidationModeType_2_0 validationMode = VALIDATION_MODE_EDEFAULT;

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
	 * Returns the value of the '<em><b>Validation Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderColumnValidationModeType_2_0}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderColumnValidationModeType_2_0
	 * @see #setValidationMode(OrderColumnValidationModeType_2_0)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOrderColumn_2_0_ValidationMode()
	 * @model
	 * @generated
	 */
	public OrderColumnValidationModeType_2_0 getValidationMode()
	{
		return validationMode;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOrderColumn#getValidationMode <em>Validation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderColumnValidationModeType_2_0
	 * @see #getValidationMode()
	 * @generated
	 */
	public void setValidationMode(OrderColumnValidationModeType_2_0 newValidationMode)
	{
		OrderColumnValidationModeType_2_0 oldValidationMode = validationMode;
		validationMode = newValidationMode == null ? VALIDATION_MODE_EDEFAULT : newValidationMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_ORDER_COLUMN__VALIDATION_MODE, oldValidationMode, validationMode));
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
			case EclipseLinkOrmPackage.XML_ORDER_COLUMN__VALIDATION_MODE:
				return getValidationMode();
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
			case EclipseLinkOrmPackage.XML_ORDER_COLUMN__VALIDATION_MODE:
				setValidationMode((OrderColumnValidationModeType_2_0)newValue);
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
			case EclipseLinkOrmPackage.XML_ORDER_COLUMN__VALIDATION_MODE:
				setValidationMode(VALIDATION_MODE_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_ORDER_COLUMN__VALIDATION_MODE:
				return validationMode != VALIDATION_MODE_EDEFAULT;
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
				case EclipseLinkOrmPackage.XML_ORDER_COLUMN__VALIDATION_MODE: return EclipseLinkOrmV2_0Package.XML_ORDER_COLUMN_20__VALIDATION_MODE;
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
				case EclipseLinkOrmV2_0Package.XML_ORDER_COLUMN_20__VALIDATION_MODE: return EclipseLinkOrmPackage.XML_ORDER_COLUMN__VALIDATION_MODE;
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
		result.append(" (validationMode: ");
		result.append(validationMode);
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
			buildValidationModeTranslator(),
		};
	}
		
	protected static Translator buildValidationModeTranslator() {
		return new Translator(EclipseLink2_0.VALIDATION_MODE, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlOrderColumn_2_0_ValidationMode(), Translator.DOM_ATTRIBUTE);
	}
}
