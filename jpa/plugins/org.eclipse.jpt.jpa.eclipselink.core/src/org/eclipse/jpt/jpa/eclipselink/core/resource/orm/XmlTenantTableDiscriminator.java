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
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Tenant Table Discriminator</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTenantTableDiscriminator()
 * @model kind="class"
 * @generated
 */
public class XmlTenantTableDiscriminator extends EBaseObjectImpl implements XmlTenantTableDiscriminator_2_4
{
	/**
	 * The default value of the '{@link #getContextProperty() <em>Context Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextProperty()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTEXT_PROPERTY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContextProperty() <em>Context Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextProperty()
	 * @generated
	 * @ordered
	 */
	protected String contextProperty = CONTEXT_PROPERTY_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final TenantTableDiscriminatorType TYPE_EDEFAULT = TenantTableDiscriminatorType.SCHEMA;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected TenantTableDiscriminatorType type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlTenantTableDiscriminator()
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
		return EclipseLinkOrmPackage.Literals.XML_TENANT_TABLE_DISCRIMINATOR;
	}

	/**
	 * Returns the value of the '<em><b>Context Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Property</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Property</em>' attribute.
	 * @see #setContextProperty(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTenantTableDiscriminator_2_4_ContextProperty()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getContextProperty()
	{
		return contextProperty;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantTableDiscriminator#getContextProperty <em>Context Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context Property</em>' attribute.
	 * @see #getContextProperty()
	 * @generated
	 */
	public void setContextProperty(String newContextProperty)
	{
		String oldContextProperty = contextProperty;
		contextProperty = newContextProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__CONTEXT_PROPERTY, oldContextProperty, contextProperty));
	}

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType
	 * @see #setType(TenantTableDiscriminatorType)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTenantTableDiscriminator_2_4_Type()
	 * @model
	 * @generated
	 */
	public TenantTableDiscriminatorType getType()
	{
		return type;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantTableDiscriminator#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.TenantTableDiscriminatorType
	 * @see #getType()
	 * @generated
	 */
	public void setType(TenantTableDiscriminatorType newType)
	{
		TenantTableDiscriminatorType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__TYPE, oldType, type));
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
			case EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__CONTEXT_PROPERTY:
				return getContextProperty();
			case EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__TYPE:
				return getType();
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
			case EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__CONTEXT_PROPERTY:
				setContextProperty((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__TYPE:
				setType((TenantTableDiscriminatorType)newValue);
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
			case EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__CONTEXT_PROPERTY:
				setContextProperty(CONTEXT_PROPERTY_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__TYPE:
				setType(TYPE_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__CONTEXT_PROPERTY:
				return CONTEXT_PROPERTY_EDEFAULT == null ? contextProperty != null : !CONTEXT_PROPERTY_EDEFAULT.equals(contextProperty);
			case EclipseLinkOrmPackage.XML_TENANT_TABLE_DISCRIMINATOR__TYPE:
				return type != TYPE_EDEFAULT;
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
		result.append(" (contextProperty: ");
		result.append(contextProperty);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}


	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLinkOrmPackage.eINSTANCE.getXmlTenantTableDiscriminator(), 
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildContextPropertyTranslator(),
			buildReferencedFieldNameTranslator(),
		};
	}

	protected static Translator buildContextPropertyTranslator() {
		return new Translator(EclipseLink2_4.TENANT_TABLE_DISCRIMINATOR__CONTEXT_PROPERTY, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlTenantTableDiscriminator_2_4_ContextProperty(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildReferencedFieldNameTranslator() {
		return new Translator(EclipseLink2_4.TENANT_TABLE_DISCRIMINATOR__TYPE, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlTenantTableDiscriminator_2_4_Type(), Translator.DOM_ATTRIBUTE);
	}
} // XmlTenantTableDiscriminator
