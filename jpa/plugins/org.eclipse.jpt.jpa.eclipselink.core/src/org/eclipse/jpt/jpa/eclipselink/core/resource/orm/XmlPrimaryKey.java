/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;
import org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlPrimaryKey_1_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Primary Key</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrimaryKey()
 * @model kind="class"
 * @generated
 */
public class XmlPrimaryKey extends AbstractJpaEObject implements XmlPrimaryKey_1_1, XmlPrimaryKey_2_1
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final IdValidationType_1_1 VALIDATION_EDEFAULT = null;
	
	/**
	 * The cached value of the '{@link #getValidation() <em>Validation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidation()
	 * @generated
	 * @ordered
	 */
	protected IdValidationType_1_1 validation = VALIDATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getColumns() <em>Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlColumn> columns;

	/**
	 * The default value of the '{@link #getCacheKeyType() <em>Cache Key Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheKeyType()
	 * @generated
	 * @ordered
	 */
	protected static final CacheKeyType_2_1 CACHE_KEY_TYPE_EDEFAULT = CacheKeyType_2_1.ID_VALUE;

	/**
	 * The cached value of the '{@link #getCacheKeyType() <em>Cache Key Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheKeyType()
	 * @generated
	 * @ordered
	 */
	protected CacheKeyType_2_1 cacheKeyType = CACHE_KEY_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlPrimaryKey()
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
		return EclipseLinkOrmPackage.Literals.XML_PRIMARY_KEY;
	}

	/**
	 * Returns the value of the '<em><b>Validation</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1
	 * @see #setValidation(IdValidationType_1_1)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrimaryKey_1_1_Validation()
	 * @model
	 * @generated
	 */
	public IdValidationType_1_1 getValidation()
	{
		return validation;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrimaryKey#getValidation <em>Validation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validation</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.IdValidationType_1_1
	 * @see #getValidation()
	 * @generated
	 */
	public void setValidation(IdValidationType_1_1 newValidation)
	{
		IdValidationType_1_1 oldValidation = validation;
		validation = newValidation == null ? VALIDATION_EDEFAULT : newValidation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PRIMARY_KEY__VALIDATION, oldValidation, validation));
	}

	/**
	 * Returns the value of the '<em><b>Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrimaryKey_1_1_Columns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlColumn> getColumns()
	{
		if (columns == null)
		{
			columns = new EObjectContainmentEList<XmlColumn>(XmlColumn.class, this, EclipseLinkOrmPackage.XML_PRIMARY_KEY__COLUMNS);
		}
		return columns;
	}

	/**
	 * Returns the value of the '<em><b>Cache Key Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Key Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Key Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1
	 * @see #setCacheKeyType(CacheKeyType_2_1)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrimaryKey_2_1_CacheKeyType()
	 * @model
	 * @generated
	 */
	public CacheKeyType_2_1 getCacheKeyType()
	{
		return cacheKeyType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrimaryKey#getCacheKeyType <em>Cache Key Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Key Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1
	 * @see #getCacheKeyType()
	 * @generated
	 */
	public void setCacheKeyType(CacheKeyType_2_1 newCacheKeyType)
	{
		CacheKeyType_2_1 oldCacheKeyType = cacheKeyType;
		cacheKeyType = newCacheKeyType == null ? CACHE_KEY_TYPE_EDEFAULT : newCacheKeyType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PRIMARY_KEY__CACHE_KEY_TYPE, oldCacheKeyType, cacheKeyType));
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
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__COLUMNS:
				return ((InternalEList<?>)getColumns()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__VALIDATION:
				return getValidation();
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__COLUMNS:
				return getColumns();
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__CACHE_KEY_TYPE:
				return getCacheKeyType();
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
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__VALIDATION:
				setValidation((IdValidationType_1_1)newValue);
				return;
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__COLUMNS:
				getColumns().clear();
				getColumns().addAll((Collection<? extends XmlColumn>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__CACHE_KEY_TYPE:
				setCacheKeyType((CacheKeyType_2_1)newValue);
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
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__VALIDATION:
				setValidation(VALIDATION_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__COLUMNS:
				getColumns().clear();
				return;
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__CACHE_KEY_TYPE:
				setCacheKeyType(CACHE_KEY_TYPE_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__VALIDATION:
				return validation != VALIDATION_EDEFAULT;
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__COLUMNS:
				return columns != null && !columns.isEmpty();
			case EclipseLinkOrmPackage.XML_PRIMARY_KEY__CACHE_KEY_TYPE:
				return cacheKeyType != CACHE_KEY_TYPE_EDEFAULT;
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
		if (baseClass == XmlPrimaryKey_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_PRIMARY_KEY__CACHE_KEY_TYPE: return EclipseLinkOrmV2_1Package.XML_PRIMARY_KEY_21__CACHE_KEY_TYPE;
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
		if (baseClass == XmlPrimaryKey_2_1.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_1Package.XML_PRIMARY_KEY_21__CACHE_KEY_TYPE: return EclipseLinkOrmPackage.XML_PRIMARY_KEY__CACHE_KEY_TYPE;
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
		result.append(" (validation: ");
		result.append(validation);
		result.append(", cacheKeyType: ");
		result.append(cacheKeyType);
		result.append(')');
		return result.toString();
	}
	
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature,
			EclipseLinkOrmPackage.eINSTANCE.getXmlPrimaryKey(),
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildValidationTranslator(),
			buildCacheKeyTypeTranslator(),
			buildColumnTranslator()
		};
	}
	
	protected static Translator buildValidationTranslator() {
		return new Translator(EclipseLink1_1.PRIMARY_KEY__VALIDATION, EclipseLinkOrmV1_1Package.eINSTANCE.getXmlPrimaryKey_1_1_Validation(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildCacheKeyTypeTranslator() {
		return new Translator(EclipseLink2_1.CACHE_KEY_TYPE, EclipseLinkOrmV2_1Package.eINSTANCE.getXmlPrimaryKey_2_1_CacheKeyType(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildColumnTranslator() {
		return XmlColumn.buildTranslator(EclipseLink1_1.PRIMARY_KEY__COLUMN, EclipseLinkOrmV1_1Package.eINSTANCE.getXmlPrimaryKey_1_1_Columns());
	}
}
