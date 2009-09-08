/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity#getCacheable <em>Cacheable</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEntity()
 * @model kind="class"
 * @generated
 */
public class XmlEntity extends org.eclipse.jpt.core.resource.orm.XmlEntity
{
	/**
	 * The default value of the '{@link #getCacheable() <em>Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean CACHEABLE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getCacheable() <em>Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheable()
	 * @generated
	 * @ordered
	 */
	protected Boolean cacheable = CACHEABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEntity()
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
		return Orm2_0Package.Literals.XML_ENTITY;
	}
	/**
	 * Returns the value of the '<em><b>Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cacheable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cacheable</em>' attribute.
	 * @see #setCacheable(Boolean)
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlEntity_Cacheable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getCacheable()
	{
		return cacheable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity#getCacheable <em>Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cacheable</em>' attribute.
	 * @see #getCacheable()
	 * @generated
	 */
	public void setCacheable(Boolean newCacheable)
	{
		Boolean oldCacheable = cacheable;
		cacheable = newCacheable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_ENTITY__CACHEABLE, oldCacheable, cacheable));
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
			case Orm2_0Package.XML_ENTITY__CACHEABLE:
				return getCacheable();
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
			case Orm2_0Package.XML_ENTITY__CACHEABLE:
				setCacheable((Boolean)newValue);
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
			case Orm2_0Package.XML_ENTITY__CACHEABLE:
				setCacheable(CACHEABLE_EDEFAULT);
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
			case Orm2_0Package.XML_ENTITY__CACHEABLE:
				return CACHEABLE_EDEFAULT == null ? cacheable != null : !CACHEABLE_EDEFAULT.equals(cacheable);
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
		result.append(" (cacheable: ");
		result.append(cacheable);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			Orm2_0Package.eINSTANCE.getXmlEntity(), 
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildClassTranslator(),
			buildAccessTranslator(),
			buildCacheableTranslator(),
			buildMetadataCompleteTranslator(),
			buildDescriptionTranslator(),
			buildTableTranslator(),
			buildSecondaryTableTranslator(),
			buildPrimaryKeyJoinColumnTranslator(),
			buildIdClassTranslator(),
			buildInheritanceTranslator(),
			buildDiscriminatorValueTranslator(),
			buildDiscriminatorColumnTranslator(),
			buildSequenceGeneratorTranslator(),
			buildTableGeneratorTranslator(),
			buildNamedQueryTranslator(),
			buildNamedNativeQueryTranslator(),
			buildSqlResultSetMappingTranslator(),
			buildExcludeDefaultListenersTranslator(),
			buildExcludeSuperclassListenersTranslator(),
			buildEntityListenersTranslator(),
			PrePersist.buildTranslator(),
			PostPersist.buildTranslator(),
			PreRemove.buildTranslator(),
			PostRemove.buildTranslator(),
			PreUpdate.buildTranslator(),
			PostUpdate.buildTranslator(),
			PostLoad.buildTranslator(),
			buildAttributeOverrideTranslator(),
			buildAssociationOverrideTranslator(),
			Attributes.buildTranslator()
		};
	}
	
	protected static Translator buildCacheableTranslator() {
		return new Translator(JPA.CACHEABLE, Orm2_0Package.eINSTANCE.getXmlEntity_Cacheable(), Translator.DOM_ATTRIBUTE);
	}
	
	//TODO use these 2.0 version translators once we have context model for XmlNamedQueries in 2.0	
//	protected static Translator buildNamedQueryTranslator() {
//		return XmlNamedQuery.buildTranslator(JPA.NAMED_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedQueries());
//	}
//	
//	protected static Translator buildNamedNativeQueryTranslator() {
//		return XmlNamedNativeQuery.buildTranslator(JPA.NAMED_NATIVE_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedNativeQueries());
//	}
	
	protected static Translator buildSequenceGeneratorTranslator() {
		return XmlSequenceGenerator.buildTranslator(JPA.SEQUENCE_GENERATOR, OrmPackage.eINSTANCE.getXmlGeneratorContainer_SequenceGenerator());
	}
	
	protected static Translator buildTableGeneratorTranslator() {
		return XmlTableGenerator.buildTranslator(JPA.TABLE_GENERATOR, OrmPackage.eINSTANCE.getXmlGeneratorContainer_TableGenerator());
	}
	
	protected static Translator buildAssociationOverrideTranslator() {
		return XmlAssociationOverride.buildTranslator(JPA.ASSOCIATION_OVERRIDE, OrmPackage.eINSTANCE.getXmlAssociationOverrideContainer_AssociationOverrides());
	}

} // XmlEntity
