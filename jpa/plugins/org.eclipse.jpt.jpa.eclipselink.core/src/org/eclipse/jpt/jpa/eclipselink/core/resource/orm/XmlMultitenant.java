/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Multitenant</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMultitenant()
 * @model kind="class"
 * @generated
 */
public class XmlMultitenant extends AbstractJpaEObject implements XmlMultitenant_2_3
{
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final XmlMultitenantType TYPE_EDEFAULT = XmlMultitenantType.SINGLE_TABLE;
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected XmlMultitenantType type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTenantDiscriminatorColumns() <em>Tenant Discriminator Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTenantDiscriminatorColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlTenantDiscriminatorColumn_2_3> tenantDiscriminatorColumns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlMultitenant()
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
		return EclipseLinkOrmPackage.Literals.XML_MULTITENANT;
	}
	
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType
	 * @see #setType(XmlMultitenantType)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMultitenant_2_3_Type()
	 * @model
	 * @generated
	 */
	public XmlMultitenantType getType()
	{
		return type;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenantType
	 * @see #getType()
	 * @generated
	 */
	public void setType(XmlMultitenantType newType)
	{
		XmlMultitenantType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MULTITENANT__TYPE, oldType, type));
	}

	/**
	 * Returns the value of the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tenant Discriminator Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tenant Discriminator Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMultitenant_2_3_TenantDiscriminatorColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlTenantDiscriminatorColumn_2_3> getTenantDiscriminatorColumns()
	{
		if (tenantDiscriminatorColumns == null)
		{
			tenantDiscriminatorColumns = new EObjectContainmentEList<XmlTenantDiscriminatorColumn_2_3>(XmlTenantDiscriminatorColumn_2_3.class, this, EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_DISCRIMINATOR_COLUMNS);
		}
		return tenantDiscriminatorColumns;
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
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_DISCRIMINATOR_COLUMNS:
				return ((InternalEList<?>)getTenantDiscriminatorColumns()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_MULTITENANT__TYPE:
				return getType();
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_DISCRIMINATOR_COLUMNS:
				return getTenantDiscriminatorColumns();
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
			case EclipseLinkOrmPackage.XML_MULTITENANT__TYPE:
				setType((XmlMultitenantType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_DISCRIMINATOR_COLUMNS:
				getTenantDiscriminatorColumns().clear();
				getTenantDiscriminatorColumns().addAll((Collection<? extends XmlTenantDiscriminatorColumn_2_3>)newValue);
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
			case EclipseLinkOrmPackage.XML_MULTITENANT__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_DISCRIMINATOR_COLUMNS:
				getTenantDiscriminatorColumns().clear();
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
			case EclipseLinkOrmPackage.XML_MULTITENANT__TYPE:
				return type != TYPE_EDEFAULT;
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_DISCRIMINATOR_COLUMNS:
				return tenantDiscriminatorColumns != null && !tenantDiscriminatorColumns.isEmpty();
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
		result.append(" (type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature,
			EclipseLinkOrmPackage.eINSTANCE.getXmlMultitenant(),
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildTypeTranslator(),
			buildTenantDiscriminatorColumnsTranslator()
		};
	}

	protected static Translator buildTypeTranslator() {
		return new Translator(EclipseLink2_3.MULTITENANT__TYPE, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlMultitenant_2_3_Type());
	}

	protected static Translator buildTenantDiscriminatorColumnsTranslator() {
		return XmlTenantDiscriminatorColumn.buildTranslator(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlMultitenant_2_3_TenantDiscriminatorColumns());		
	}

} // XmlMultitenant
