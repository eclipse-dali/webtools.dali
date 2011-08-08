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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Tenant Discriminator</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTenantDiscriminatorColumn()
 * @model kind="class"
 * @generated
 */
public class XmlTenantDiscriminatorColumn extends AbstractXmlDiscriminatorColumn implements XmlTenantDiscriminatorColumn_2_3
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
	 * The default value of the '{@link #getTable() <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected static final String TABLE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getTable() <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected String table = TABLE_EDEFAULT;
	/**
	 * The default value of the '{@link #getPrimaryKey() <em>Primary Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKey()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean PRIMARY_KEY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPrimaryKey() <em>Primary Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKey()
	 * @generated
	 * @ordered
	 */
	protected Boolean primaryKey = PRIMARY_KEY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlTenantDiscriminatorColumn()
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
		return EclipseLinkOrmPackage.Literals.XML_TENANT_DISCRIMINATOR_COLUMN;
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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTenantDiscriminatorColumn_2_3_ContextProperty()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getContextProperty()
	{
		return contextProperty;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn#getContextProperty <em>Context Property</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY, oldContextProperty, contextProperty));
	}

	/**
	 * Returns the value of the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' attribute.
	 * @see #setTable(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTenantDiscriminatorColumn_2_3_Table()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTable()
	{
		return table;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn#getTable <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' attribute.
	 * @see #getTable()
	 * @generated
	 */
	public void setTable(String newTable)
	{
		String oldTable = table;
		table = newTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__TABLE, oldTable, table));
	}

	/**
	 * Returns the value of the '<em><b>Primary Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key</em>' attribute.
	 * @see #setPrimaryKey(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTenantDiscriminatorColumn_2_3_PrimaryKey()
	 * @model
	 * @generated
	 */
	public Boolean getPrimaryKey()
	{
		return primaryKey;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn#getPrimaryKey <em>Primary Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key</em>' attribute.
	 * @see #getPrimaryKey()
	 * @generated
	 */
	public void setPrimaryKey(Boolean newPrimaryKey)
	{
		Boolean oldPrimaryKey = primaryKey;
		primaryKey = newPrimaryKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY, oldPrimaryKey, primaryKey));
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
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY:
				return getContextProperty();
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__TABLE:
				return getTable();
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY:
				return getPrimaryKey();
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
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY:
				setContextProperty((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__TABLE:
				setTable((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY:
				setPrimaryKey((Boolean)newValue);
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
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY:
				setContextProperty(CONTEXT_PROPERTY_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__TABLE:
				setTable(TABLE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY:
				setPrimaryKey(PRIMARY_KEY_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY:
				return CONTEXT_PROPERTY_EDEFAULT == null ? contextProperty != null : !CONTEXT_PROPERTY_EDEFAULT.equals(contextProperty);
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__TABLE:
				return TABLE_EDEFAULT == null ? table != null : !TABLE_EDEFAULT.equals(table);
			case EclipseLinkOrmPackage.XML_TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY:
				return PRIMARY_KEY_EDEFAULT == null ? primaryKey != null : !PRIMARY_KEY_EDEFAULT.equals(primaryKey);
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
		result.append(", table: ");
		result.append(table);
		result.append(", primaryKey: ");
		result.append(primaryKey);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature,
			EclipseLinkOrmPackage.eINSTANCE.getXmlTenantDiscriminatorColumn(),
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildContextPropertyTranslator(),
			buildDiscrminiatorTypeTranslator(),
			buildColumnDefinitionTranslator(),
			buildLengthTranslator(),
			buildTableTranslator(),
			buildPrimaryKeyTranslator()
		};
	}

	protected static Translator buildContextPropertyTranslator() {
		return new Translator(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlTenantDiscriminatorColumn_2_3_ContextProperty(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildTableTranslator() {
		return new Translator(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__TABLE, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlTenantDiscriminatorColumn_2_3_Table(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildPrimaryKeyTranslator() {
		return new Translator(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlTenantDiscriminatorColumn_2_3_PrimaryKey(), Translator.DOM_ATTRIBUTE);
	}
} // XmlTenantDiscriminator
