/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.v2_0.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.jpa2.resource.orm.JPA;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlDerivedId;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Many To One</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToOne()
 * @model kind="class"
 * @generated
 */
public class XmlManyToOne extends org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlManyToOne
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
		return EclipseLink2_0OrmPackage.Literals.XML_MANY_TO_ONE;
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
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlDerivedId_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getId()
	{
		return id;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToOne#getId <em>Id</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__ID, oldId, id));
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
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToOne_MappedById()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMappedById()
	{
		return mappedById;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToOne#getMappedById <em>Mapped By Id</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID, oldMappedById, mappedById));
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
			case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__ID:
				return getId();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID:
				return getMappedById();
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
			case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__ID:
				setId((Boolean)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID:
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
			case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__ID:
				setId(ID_EDEFAULT);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID:
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
			case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID:
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
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlDerivedId.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__ID: return Orm2_0Package.XML_DERIVED_ID__ID;
				default: return -1;
			}
		}
		if (baseClass == org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID: return Orm2_0Package.XML_MANY_TO_ONE__MAPPED_BY_ID;
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
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlDerivedId.class)
		{
			switch (baseFeatureID)
			{
				case Orm2_0Package.XML_DERIVED_ID__ID: return EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__ID;
				default: return -1;
			}
		}
		if (baseClass == org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne.class)
		{
			switch (baseFeatureID)
			{
				case Orm2_0Package.XML_MANY_TO_ONE__MAPPED_BY_ID: return EclipseLink2_0OrmPackage.XML_MANY_TO_ONE__MAPPED_BY_ID;
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
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLink2_0OrmPackage.eINSTANCE.getXmlManyToOne(), 
			buildTranslatorChildren());
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
			buildCascadeTranslator(),
			buildJoinFetchTranslator(),
			buildPropertyTranslator(),
			buildAccessMethodsTranslator()
		};
	}
	
	protected static Translator buildMappedByIdTranslator() {
		return new Translator(JPA.MAPPED_BY_ID, Orm2_0Package.eINSTANCE.getXmlManyToOne_MappedById(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildIdTranslator() {
		return new Translator(JPA.ID, Orm2_0Package.eINSTANCE.getXmlDerivedId_Id(), Translator.DOM_ATTRIBUTE);
	}

} // XmlManyToOne
