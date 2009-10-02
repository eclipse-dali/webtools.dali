/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlManyToOne_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Many To One</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlManyToOne()
 * @model kind="class"
 * @generated
 */
public class XmlManyToOne extends AbstractXmlSingleRelationshipMapping implements XmlManyToOne_2_0
{

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected Boolean id = ID_EDEFAULT;
	/**
	 * The default value of the '{@link #getMappedById() <em>Mapped By Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedById()
	 * @generated
	 * @ordered
	 */
	protected static final String MAPPED_BY_ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getMappedById() <em>Mapped By Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedById()
	 * @generated
	 * @ordered
	 */
	protected String mappedById = MAPPED_BY_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlManyToOne()
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
		return OrmPackage.Literals.XML_MANY_TO_ONE;
	}

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(Boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlDerivedId_2_0_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getId()
	{
		return id;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToOne#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	public void setId(Boolean newId)
	{
		Boolean oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_ONE__ID, oldId, id));
	}

	/**
	 * Returns the value of the '<em><b>Mapped By Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped By Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped By Id</em>' attribute.
	 * @see #setMappedById(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlManyToOne_2_0_MappedById()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMappedById()
	{
		return mappedById;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlManyToOne#getMappedById <em>Mapped By Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped By Id</em>' attribute.
	 * @see #getMappedById()
	 * @generated
	 */
	public void setMappedById(String newMappedById)
	{
		String oldMappedById = mappedById;
		mappedById = newMappedById;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID, oldMappedById, mappedById));
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
			case OrmPackage.XML_MANY_TO_ONE__ID:
				return getId();
			case OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID:
				return getMappedById();
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
			case OrmPackage.XML_MANY_TO_ONE__ID:
				setId((Boolean)newValue);
				return;
			case OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID:
				setMappedById((String)newValue);
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
			case OrmPackage.XML_MANY_TO_ONE__ID:
				setId(ID_EDEFAULT);
				return;
			case OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID:
				setMappedById(MAPPED_BY_ID_EDEFAULT);
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
			case OrmPackage.XML_MANY_TO_ONE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID:
				return MAPPED_BY_ID_EDEFAULT == null ? mappedById != null : !MAPPED_BY_ID_EDEFAULT.equals(mappedById);
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
		if (baseClass == XmlDerivedId_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_MANY_TO_ONE__ID: return OrmV2_0Package.XML_DERIVED_ID_20__ID;
				default: return -1;
			}
		}
		if (baseClass == XmlDerivedId.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID: return OrmV2_0Package.XML_MANY_TO_ONE_20__MAPPED_BY_ID;
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
		if (baseClass == XmlDerivedId_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_DERIVED_ID_20__ID: return OrmPackage.XML_MANY_TO_ONE__ID;
				default: return -1;
			}
		}
		if (baseClass == XmlDerivedId.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlManyToOne_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_MANY_TO_ONE_20__MAPPED_BY_ID: return OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", mappedById: ");
		result.append(mappedById);
		result.append(')');
		return result.toString();
	}
	
	
	// **************** XmlAttributeMapping impl ******************************

	public String getMappingKey() {
		return MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// **************** validation support ************************************
	
	public TextRange getDerivedIdTextRange() {
		return getAttributeTextRange(JPA2_0.ID);
	}
	
	
	// **************** translators *******************************************
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildTargetEntityTranslator(),
			buildFetchTranslator(),
			buildOptionalTranslator(),
			buildAccessTranslator(),
			buildMappedByIdTranslator(),
			buildIdTranslator(),
			buildJoinColumnTranslator(),
			buildJoinTableTranslator(),
			buildCascadeTranslator()
		};
	}
	
	protected static Translator buildMappedByIdTranslator() {
		return new Translator(JPA2_0.MAPPED_BY_ID, OrmV2_0Package.eINSTANCE.getXmlManyToOne_2_0_MappedById(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildIdTranslator() {
		return new Translator(JPA2_0.ID, OrmV2_0Package.eINSTANCE.getXmlDerivedId_2_0_Id(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildJoinColumnTranslator() {
		return XmlJoinColumn.buildTranslator(JPA.JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlJoinColumnsMapping_JoinColumns());
	}
	
	protected static Translator buildJoinTableTranslator() {
		return XmlJoinTable.buildTranslator(JPA.JOIN_TABLE, OrmPackage.eINSTANCE.getXmlJoinTableMapping_JoinTable());
	}	
}
