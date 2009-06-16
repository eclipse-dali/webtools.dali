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
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.Attributes#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.jpt2_0.core.resource.orm.Attributes#getElementCollections <em>Element Collections</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getAttributes()
 * @model kind="class"
 * @generated
 */
public class Attributes extends org.eclipse.jpt.core.resource.orm.Attributes
{
	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElementCollections() <em>Element Collections</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementCollections()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlElementCollection> elementCollections;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Attributes()
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
		return Orm2_0Package.Literals.ATTRIBUTES;
	}

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getAttributes_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt2_0.core.resource.orm.Attributes#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	public void setDescription(String newDescription)
	{
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Orm2_0Package.ATTRIBUTES__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Element Collections</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt2_0.core.resource.orm.XmlElementCollection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Collections</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Collections</em>' containment reference list.
	 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getAttributes_ElementCollections()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlElementCollection> getElementCollections()
	{
		if (elementCollections == null)
		{
			elementCollections = new EObjectContainmentEList<XmlElementCollection>(XmlElementCollection.class, this, Orm2_0Package.ATTRIBUTES__ELEMENT_COLLECTIONS);
		}
		return elementCollections;
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
			case Orm2_0Package.ATTRIBUTES__ELEMENT_COLLECTIONS:
				return ((InternalEList<?>)getElementCollections()).basicRemove(otherEnd, msgs);
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
			case Orm2_0Package.ATTRIBUTES__DESCRIPTION:
				return getDescription();
			case Orm2_0Package.ATTRIBUTES__ELEMENT_COLLECTIONS:
				return getElementCollections();
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
			case Orm2_0Package.ATTRIBUTES__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case Orm2_0Package.ATTRIBUTES__ELEMENT_COLLECTIONS:
				getElementCollections().clear();
				getElementCollections().addAll((Collection<? extends XmlElementCollection>)newValue);
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
			case Orm2_0Package.ATTRIBUTES__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case Orm2_0Package.ATTRIBUTES__ELEMENT_COLLECTIONS:
				getElementCollections().clear();
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
			case Orm2_0Package.ATTRIBUTES__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case Orm2_0Package.ATTRIBUTES__ELEMENT_COLLECTIONS:
				return elementCollections != null && !elementCollections.isEmpty();
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
		result.append(" (description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			Orm2_0Package.eINSTANCE.getAttributes(), 
			buildTranslatorChildren());
	}
	
	public static Translator buildTranslator() {
		return buildTranslator(
			JPA.ATTRIBUTES, 
			OrmPackage.eINSTANCE.getXmlTypeMapping_Attributes());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			XmlId.buildTranslator(JPA.ID, OrmPackage.eINSTANCE.getAttributes_Ids()),
			XmlEmbeddedId.buildTranslator(JPA.EMBEDDED_ID, OrmPackage.eINSTANCE.getAttributes_EmbeddedIds()),
			XmlBasic.buildTranslator(JPA.BASIC, OrmPackage.eINSTANCE.getAttributes_Basics()),
			XmlVersion.buildTranslator(JPA.VERSION, OrmPackage.eINSTANCE.getAttributes_Versions()),
			XmlManyToOne.buildTranslator(JPA.MANY_TO_ONE, OrmPackage.eINSTANCE.getAttributes_ManyToOnes()),
			XmlOneToMany.buildTranslator(JPA.ONE_TO_MANY, OrmPackage.eINSTANCE.getAttributes_OneToManys()),
			XmlOneToOne.buildTranslator(JPA.ONE_TO_ONE, OrmPackage.eINSTANCE.getAttributes_OneToOnes()),
			XmlManyToMany.buildTranslator(JPA.MANY_TO_MANY, OrmPackage.eINSTANCE.getAttributes_ManyToManys()),
			XmlElementCollection.buildTranslator(JPA.ELEMENT_COLLECTION, Orm2_0Package.eINSTANCE.getAttributes_ElementCollections()),
			XmlEmbedded.buildTranslator(JPA.EMBEDDED, OrmPackage.eINSTANCE.getAttributes_Embeddeds()),
			XmlTransient.buildTranslator(JPA.TRANSIENT, OrmPackage.eINSTANCE.getAttributes_Transients()),
		};
	}

} // Attributes
