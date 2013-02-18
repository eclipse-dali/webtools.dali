/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMultitenant_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLink2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlTenantTableDiscriminator_2_4;
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
public class XmlMultitenant extends EBaseObjectImpl implements XmlMultitenant_2_3, XmlMultitenant_2_4
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final MultitenantType TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected MultitenantType type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTenantDiscriminatorColumns() <em>Tenant Discriminator Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTenantDiscriminatorColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlTenantDiscriminatorColumn> tenantDiscriminatorColumns;

	/**
	 * The default value of the '{@link #getIncludeCriteria() <em>Include Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludeCriteria()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean INCLUDE_CRITERIA_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getIncludeCriteria() <em>Include Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludeCriteria()
	 * @generated
	 * @ordered
	 */
	protected Boolean includeCriteria = INCLUDE_CRITERIA_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTenantTableDiscriminator() <em>Tenant Table Discriminator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTenantTableDiscriminator()
	 * @generated
	 * @ordered
	 */
	protected XmlTenantTableDiscriminator tenantTableDiscriminator;

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
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType
	 * @see #setType(MultitenantType)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMultitenant_2_3_Type()
	 * @model
	 * @generated
	 */
	public MultitenantType getType()
	{
		return type;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType
	 * @see #getType()
	 * @generated
	 */
	public void setType(MultitenantType newType)
	{
		MultitenantType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MULTITENANT__TYPE, oldType, type));
	}

	/**
	 * Returns the value of the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn}.
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
	public EList<XmlTenantDiscriminatorColumn> getTenantDiscriminatorColumns()
	{
		if (tenantDiscriminatorColumns == null)
		{
			tenantDiscriminatorColumns = new EObjectContainmentEList<XmlTenantDiscriminatorColumn>(XmlTenantDiscriminatorColumn.class, this, EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_DISCRIMINATOR_COLUMNS);
		}
		return tenantDiscriminatorColumns;
	}

	/**
	 * Returns the value of the '<em><b>Include Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include Criteria</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Include Criteria</em>' attribute.
	 * @see #setIncludeCriteria(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMultitenant_2_4_IncludeCriteria()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getIncludeCriteria()
	{
		return includeCriteria;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant#getIncludeCriteria <em>Include Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Include Criteria</em>' attribute.
	 * @see #getIncludeCriteria()
	 * @generated
	 */
	public void setIncludeCriteria(Boolean newIncludeCriteria)
	{
		Boolean oldIncludeCriteria = includeCriteria;
		includeCriteria = newIncludeCriteria;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MULTITENANT__INCLUDE_CRITERIA, oldIncludeCriteria, includeCriteria));
	}

	/**
	 * Returns the value of the '<em><b>Tenant Table Discriminator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tenant Table Discriminator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tenant Table Discriminator</em>' containment reference.
	 * @see #setTenantTableDiscriminator(XmlTenantTableDiscriminator)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlMultitenant_2_4_TenantTableDiscriminator()
	 * @model containment="true"
	 * @generated
	 */
	public XmlTenantTableDiscriminator getTenantTableDiscriminator()
	{
		return tenantTableDiscriminator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTenantTableDiscriminator(XmlTenantTableDiscriminator newTenantTableDiscriminator, NotificationChain msgs)
	{
		XmlTenantTableDiscriminator oldTenantTableDiscriminator = tenantTableDiscriminator;
		tenantTableDiscriminator = newTenantTableDiscriminator;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR, oldTenantTableDiscriminator, newTenantTableDiscriminator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant#getTenantTableDiscriminator <em>Tenant Table Discriminator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tenant Table Discriminator</em>' containment reference.
	 * @see #getTenantTableDiscriminator()
	 * @generated
	 */
	public void setTenantTableDiscriminator(XmlTenantTableDiscriminator newTenantTableDiscriminator)
	{
		if (newTenantTableDiscriminator != tenantTableDiscriminator)
		{
			NotificationChain msgs = null;
			if (tenantTableDiscriminator != null)
				msgs = ((InternalEObject)tenantTableDiscriminator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR, null, msgs);
			if (newTenantTableDiscriminator != null)
				msgs = ((InternalEObject)newTenantTableDiscriminator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR, null, msgs);
			msgs = basicSetTenantTableDiscriminator(newTenantTableDiscriminator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR, newTenantTableDiscriminator, newTenantTableDiscriminator));
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
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR:
				return basicSetTenantTableDiscriminator(null, msgs);
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
			case EclipseLinkOrmPackage.XML_MULTITENANT__INCLUDE_CRITERIA:
				return getIncludeCriteria();
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR:
				return getTenantTableDiscriminator();
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
				setType((MultitenantType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_DISCRIMINATOR_COLUMNS:
				getTenantDiscriminatorColumns().clear();
				getTenantDiscriminatorColumns().addAll((Collection<? extends XmlTenantDiscriminatorColumn>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MULTITENANT__INCLUDE_CRITERIA:
				setIncludeCriteria((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR:
				setTenantTableDiscriminator((XmlTenantTableDiscriminator)newValue);
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
			case EclipseLinkOrmPackage.XML_MULTITENANT__INCLUDE_CRITERIA:
				setIncludeCriteria(INCLUDE_CRITERIA_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR:
				setTenantTableDiscriminator((XmlTenantTableDiscriminator)null);
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
			case EclipseLinkOrmPackage.XML_MULTITENANT__INCLUDE_CRITERIA:
				return INCLUDE_CRITERIA_EDEFAULT == null ? includeCriteria != null : !INCLUDE_CRITERIA_EDEFAULT.equals(includeCriteria);
			case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR:
				return tenantTableDiscriminator != null;
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
		if (baseClass == XmlMultitenant_2_4.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_MULTITENANT__INCLUDE_CRITERIA: return EclipseLinkOrmV2_4Package.XML_MULTITENANT_24__INCLUDE_CRITERIA;
				case EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR: return EclipseLinkOrmV2_4Package.XML_MULTITENANT_24__TENANT_TABLE_DISCRIMINATOR;
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
		if (baseClass == XmlMultitenant_2_4.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_4Package.XML_MULTITENANT_24__INCLUDE_CRITERIA: return EclipseLinkOrmPackage.XML_MULTITENANT__INCLUDE_CRITERIA;
				case EclipseLinkOrmV2_4Package.XML_MULTITENANT_24__TENANT_TABLE_DISCRIMINATOR: return EclipseLinkOrmPackage.XML_MULTITENANT__TENANT_TABLE_DISCRIMINATOR;
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
		result.append(" (type: ");
		result.append(type);
		result.append(", includeCriteria: ");
		result.append(includeCriteria);
		result.append(')');
		return result.toString();
	}

	public TextRange getIncludeCriteriaTextRange() {
		return getAttributeTextRange(EclipseLink2_4.MULTITENANT__INCLUDE_CRITERIA); 
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
			buildIncludeCriteriaTranslator(),
			buildTenantDiscriminatorColumnsTranslator(),
			buildTenantTableDiscriminatorTranslator()
		};
	}

	protected static Translator buildTypeTranslator() {
		return new Translator(EclipseLink2_3.MULTITENANT__TYPE, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlMultitenant_2_3_Type(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildTenantDiscriminatorColumnsTranslator() {
		return XmlTenantDiscriminatorColumn.buildTranslator(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlMultitenant_2_3_TenantDiscriminatorColumns());		
	}
	
	protected static Translator buildIncludeCriteriaTranslator() {
		return new Translator(EclipseLink2_4.MULTITENANT__INCLUDE_CRITERIA, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlMultitenant_2_4_IncludeCriteria(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildTenantTableDiscriminatorTranslator() {
		return XmlTenantTableDiscriminator.buildTranslator(EclipseLink2_4.TENANT_TABLE_DISCRIMINATOR, EclipseLinkOrmV2_4Package.eINSTANCE.getXmlMultitenant_2_4_TenantTableDiscriminator());		
	}

} // XmlMultitenant
