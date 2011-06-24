/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
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
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlPersistenceUnitDefaults_2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminator_2_3;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Persistence Unit Defaults</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults()
 * @model kind="class"
 * @generated
 */
public class XmlPersistenceUnitDefaults extends org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitDefaults implements XmlPersistenceUnitDefaults_2_1, XmlPersistenceUnitDefaults_2_3
{
	/**
	 * The cached value of the '{@link #getAccessMethods() <em>Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessMethods()
	 * @generated
	 * @ordered
	 */
	protected XmlAccessMethods accessMethods;

	/**
	 * The cached value of the '{@link #getTenantDiscriminators() <em>Tenant Discriminators</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTenantDiscriminators()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlTenantDiscriminator_2_3> tenantDiscriminators;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlPersistenceUnitDefaults()
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
		return EclipseLinkOrmPackage.Literals.XML_PERSISTENCE_UNIT_DEFAULTS;
	}

	/**
	 * Returns the value of the '<em><b>Access Methods</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access Methods</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access Methods</em>' containment reference.
	 * @see #setAccessMethods(XmlAccessMethods)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAccessMethodsHolder_AccessMethods()
	 * @model containment="true"
	 * @generated
	 */
	public XmlAccessMethods getAccessMethods()
	{
		return accessMethods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAccessMethods(XmlAccessMethods newAccessMethods, NotificationChain msgs)
	{
		XmlAccessMethods oldAccessMethods = accessMethods;
		accessMethods = newAccessMethods;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS, oldAccessMethods, newAccessMethods);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults#getAccessMethods <em>Access Methods</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access Methods</em>' containment reference.
	 * @see #getAccessMethods()
	 * @generated
	 */
	public void setAccessMethods(XmlAccessMethods newAccessMethods)
	{
		if (newAccessMethods != accessMethods)
		{
			NotificationChain msgs = null;
			if (accessMethods != null)
				msgs = ((InternalEObject)accessMethods).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS, null, msgs);
			if (newAccessMethods != null)
				msgs = ((InternalEObject)newAccessMethods).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS, null, msgs);
			msgs = basicSetAccessMethods(newAccessMethods, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS, newAccessMethods, newAccessMethods));
	}

	/**
	 * Returns the value of the '<em><b>Tenant Discriminators</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminator_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tenant Discriminators</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tenant Discriminators</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults_2_3_TenantDiscriminators()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlTenantDiscriminator_2_3> getTenantDiscriminators()
	{
		if (tenantDiscriminators == null)
		{
			tenantDiscriminators = new EObjectContainmentEList<XmlTenantDiscriminator_2_3>(XmlTenantDiscriminator_2_3.class, this, EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__TENANT_DISCRIMINATORS);
		}
		return tenantDiscriminators;
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS:
				return basicSetAccessMethods(null, msgs);
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__TENANT_DISCRIMINATORS:
				return ((InternalEList<?>)getTenantDiscriminators()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS:
				return getAccessMethods();
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__TENANT_DISCRIMINATORS:
				return getTenantDiscriminators();
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)newValue);
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__TENANT_DISCRIMINATORS:
				getTenantDiscriminators().clear();
				getTenantDiscriminators().addAll((Collection<? extends XmlTenantDiscriminator_2_3>)newValue);
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS:
				setAccessMethods((XmlAccessMethods)null);
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__TENANT_DISCRIMINATORS:
				getTenantDiscriminators().clear();
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS:
				return accessMethods != null;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__TENANT_DISCRIMINATORS:
				return tenantDiscriminators != null && !tenantDiscriminators.isEmpty();
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
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlPersistenceUnitDefaults_2_1.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlPersistenceUnitDefaults_2_3.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__TENANT_DISCRIMINATORS: return EclipseLinkOrmV2_3Package.XML_PERSISTENCE_UNIT_DEFAULTS_23__TENANT_DISCRIMINATORS;
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
		if (baseClass == XmlAccessMethodsHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ACCESS_METHODS_HOLDER__ACCESS_METHODS: return EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS_METHODS;
				default: return -1;
			}
		}
		if (baseClass == XmlPersistenceUnitDefaults_2_1.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlPersistenceUnitDefaults_2_3.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmV2_3Package.XML_PERSISTENCE_UNIT_DEFAULTS_23__TENANT_DISCRIMINATORS: return EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__TENANT_DISCRIMINATORS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}


	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildDescriptionTranslator(),
			buildSchemaTranslator(),
			buildCatalogTranslator(),
			buildDelimitedIdentifiersTranslator(),
			buildAccessTranslator(),
			buildAccessMethodsTranslator(),
			buildCascadePersistTranslator(),
			XmlTenantDiscriminator.buildTranslator(EclipseLink2_3.TENANT_DISCRIMINATOR, EclipseLinkOrmV2_3Package.eINSTANCE.getXmlPersistenceUnitDefaults_2_3_TenantDiscriminators()),
			buildEntityListenersTranslator()
		};
	}
	
	protected static Translator buildAccessMethodsTranslator() {
		return XmlAccessMethods.buildTranslator(EclipseLink.ACCESS_METHODS, EclipseLinkOrmPackage.eINSTANCE.getXmlAccessMethodsHolder_AccessMethods());
	}

} // XmlPersistenceUnitDefaults
