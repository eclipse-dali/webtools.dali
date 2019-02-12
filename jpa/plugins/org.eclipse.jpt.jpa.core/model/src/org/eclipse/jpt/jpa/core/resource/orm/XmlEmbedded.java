/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlEmbedded_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEmbedded_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Embedded</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEmbedded()
 * @model kind="class"
 * @generated
 */
public class XmlEmbedded extends AbstractXmlEmbedded implements XmlEmbedded_2_0, XmlEmbedded_2_1
{
	/**
	 * The cached value of the '{@link #getAssociationOverrides() <em>Association Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAssociationOverride> associationOverrides;

	/**
	 * The cached value of the '{@link #getConverts() <em>Converts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverts()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlConvert> converts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEmbedded()
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
		return OrmPackage.Literals.XML_EMBEDDED;
	}

	/**
	 * Returns the value of the '<em><b>Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Association Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlAssociationOverrideContainer_AssociationOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAssociationOverride> getAssociationOverrides()
	{
		if (associationOverrides == null)
		{
			associationOverrides = new EObjectContainmentEList<XmlAssociationOverride>(XmlAssociationOverride.class, this, OrmPackage.XML_EMBEDDED__ASSOCIATION_OVERRIDES);
		}
		return associationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Converts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlConvert}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Converts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Converts</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEmbedded_2_1_Converts()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConvert> getConverts()
	{
		if (converts == null)
		{
			converts = new EObjectContainmentEList<XmlConvert>(XmlConvert.class, this, OrmPackage.XML_EMBEDDED__CONVERTS);
		}
		return converts;
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
			case OrmPackage.XML_EMBEDDED__ASSOCIATION_OVERRIDES:
				return ((InternalEList<?>)getAssociationOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_EMBEDDED__CONVERTS:
				return ((InternalEList<?>)getConverts()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_EMBEDDED__ASSOCIATION_OVERRIDES:
				return getAssociationOverrides();
			case OrmPackage.XML_EMBEDDED__CONVERTS:
				return getConverts();
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
			case OrmPackage.XML_EMBEDDED__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				getAssociationOverrides().addAll((Collection<? extends XmlAssociationOverride>)newValue);
				return;
			case OrmPackage.XML_EMBEDDED__CONVERTS:
				getConverts().clear();
				getConverts().addAll((Collection<? extends XmlConvert>)newValue);
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
			case OrmPackage.XML_EMBEDDED__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				return;
			case OrmPackage.XML_EMBEDDED__CONVERTS:
				getConverts().clear();
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
			case OrmPackage.XML_EMBEDDED__ASSOCIATION_OVERRIDES:
				return associationOverrides != null && !associationOverrides.isEmpty();
			case OrmPackage.XML_EMBEDDED__CONVERTS:
				return converts != null && !converts.isEmpty();
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
		if (baseClass == XmlAssociationOverrideContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_EMBEDDED__ASSOCIATION_OVERRIDES: return OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlEmbedded_2_0.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlEmbedded_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_EMBEDDED__CONVERTS: return OrmV2_1Package.XML_EMBEDDED_21__CONVERTS;
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
		if (baseClass == XmlAssociationOverrideContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES: return OrmPackage.XML_EMBEDDED__ASSOCIATION_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlEmbedded_2_0.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlEmbedded_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_EMBEDDED_21__CONVERTS: return OrmPackage.XML_EMBEDDED__CONVERTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public String getMappingKey() {
		return MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// ********** translators **********
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildAccessTranslator(),
			buildAttributeOverrideTranslator(),
			buildAssociationOverrideTranslator(),
			buildConvertTranslator()
		};
	}
	
	protected static Translator buildAssociationOverrideTranslator() {
		return XmlAssociationOverride.buildTranslator(JPA.ASSOCIATION_OVERRIDE, OrmPackage.eINSTANCE.getXmlAssociationOverrideContainer_AssociationOverrides());
	}
	
	protected static Translator buildConvertTranslator() {
		return XmlConvert.buildTranslator(JPA2_1.CONVERT, OrmV2_1Package.eINSTANCE.getXmlEmbedded_2_1_Converts());
	}

}
