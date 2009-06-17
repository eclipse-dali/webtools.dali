/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlAccessHolder;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Many To One</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlManyToOne#getMappedById <em>Mapped By Id</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.XmlManyToOne#isId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlManyToOne()
 * @model kind="class"
 * @generated
 */
public class XmlManyToOne extends org.eclipse.jpt.core.resource.orm.XmlManyToOne implements XmlAttributeMapping
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final AccessType ACCESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected AccessType access = ACCESS_EDEFAULT;

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
	 * The default value of the '{@link #isId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isId()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ID_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isId()
	 * @generated
	 * @ordered
	 */
	protected boolean id = ID_EDEFAULT;

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
		return Orm2_0Package.Literals.XML_MANY_TO_ONE;
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlAccessHolder_Access()
	 * @model
	 * @generated
	 */
	public AccessType getAccess()
	{
		return access;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlManyToOne#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #getAccess()
	 * @generated
	 */
	public void setAccess(AccessType newAccess)
	{
		AccessType oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_MANY_TO_ONE__ACCESS, oldAccess, access));
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
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlManyToOne_MappedById()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMappedById()
	{
		return mappedById;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlManyToOne#getMappedById <em>Mapped By Id</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_MANY_TO_ONE__MAPPED_BY_ID, oldMappedById, mappedById));
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
	 * @see #setId(boolean)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlManyToOne_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isId()
	{
		return id;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.XmlManyToOne#isId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #isId()
	 * @generated
	 */
	public void setId(boolean newId)
	{
		boolean oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.XML_MANY_TO_ONE__ID, oldId, id));
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
			case Orm2_0Package.XML_MANY_TO_ONE__ACCESS:
				return getAccess();
			case Orm2_0Package.XML_MANY_TO_ONE__MAPPED_BY_ID:
				return getMappedById();
			case Orm2_0Package.XML_MANY_TO_ONE__ID:
				return isId();
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
			case Orm2_0Package.XML_MANY_TO_ONE__ACCESS:
				setAccess((AccessType)newValue);
				return;
			case Orm2_0Package.XML_MANY_TO_ONE__MAPPED_BY_ID:
				setMappedById((String)newValue);
				return;
			case Orm2_0Package.XML_MANY_TO_ONE__ID:
				setId((Boolean)newValue);
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
			case Orm2_0Package.XML_MANY_TO_ONE__ACCESS:
				setAccess(ACCESS_EDEFAULT);
				return;
			case Orm2_0Package.XML_MANY_TO_ONE__MAPPED_BY_ID:
				setMappedById(MAPPED_BY_ID_EDEFAULT);
				return;
			case Orm2_0Package.XML_MANY_TO_ONE__ID:
				setId(ID_EDEFAULT);
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
			case Orm2_0Package.XML_MANY_TO_ONE__ACCESS:
				return access != ACCESS_EDEFAULT;
			case Orm2_0Package.XML_MANY_TO_ONE__MAPPED_BY_ID:
				return MAPPED_BY_ID_EDEFAULT == null ? mappedById != null : !MAPPED_BY_ID_EDEFAULT.equals(mappedById);
			case Orm2_0Package.XML_MANY_TO_ONE__ID:
				return id != ID_EDEFAULT;
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
		if (baseClass == XmlAccessHolder.class)
		{
			switch (derivedFeatureID)
			{
				case Orm2_0Package.XML_MANY_TO_ONE__ACCESS: return OrmPackage.XML_ACCESS_HOLDER__ACCESS;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (derivedFeatureID)
			{
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
		if (baseClass == XmlAccessHolder.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ACCESS_HOLDER__ACCESS: return Orm2_0Package.XML_MANY_TO_ONE__ACCESS;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (baseFeatureID)
			{
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
		result.append(" (access: ");
		result.append(access);
		result.append(", mappedById: ");
		result.append(mappedById);
		result.append(", id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			Orm2_0Package.eINSTANCE.getXmlManyToOne(), 
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
			buildCascadeTranslator()
		};
	}
	
	protected static Translator buildAccessTranslator() {
		return new Translator(JPA.ACCESS, OrmPackage.eINSTANCE.getXmlAccessHolder_Access(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildMappedByIdTranslator() {
		return new Translator(JPA.MAPPED_BY_ID, Orm2_0Package.eINSTANCE.getXmlManyToOne_MappedById(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildIdTranslator() {
		return new Translator(JPA.ID, Orm2_0Package.eINSTANCE.getXmlManyToOne_Id(), Translator.DOM_ATTRIBUTE);
	}

} // XmlManyToOne
