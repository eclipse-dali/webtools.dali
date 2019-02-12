/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;

import org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Foreign Key</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlForeignKey()
 * @model kind="class"
 * @generated
 */
public class XmlForeignKey extends EBaseObjectImpl implements XmlForeignKey_2_1
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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final ConstraintMode_2_1 CONSTRAINT_MODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConstraintMode() <em>Constraint Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintMode()
	 * @generated
	 * @ordered
	 */
	protected ConstraintMode_2_1 constraintMode = CONSTRAINT_MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getForeignKeyDefinition() <em>Foreign Key Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForeignKeyDefinition()
	 * @generated
	 * @ordered
	 */
	protected static final String FOREIGN_KEY_DEFINITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getForeignKeyDefinition() <em>Foreign Key Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForeignKeyDefinition()
	 * @generated
	 * @ordered
	 */
	protected String foreignKeyDefinition = FOREIGN_KEY_DEFINITION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlForeignKey()
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
		return OrmPackage.Literals.XML_FOREIGN_KEY;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlForeignKey_2_1_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlForeignKey#getDescription <em>Description</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_FOREIGN_KEY__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlForeignKey_2_1_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlForeignKey#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_FOREIGN_KEY__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Constraint Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint Mode</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1
	 * @see #setConstraintMode(ConstraintMode_2_1)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlForeignKey_2_1_ConstraintMode()
	 * @model
	 * @generated
	 */
	public ConstraintMode_2_1 getConstraintMode()
	{
		return constraintMode;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlForeignKey#getConstraintMode <em>Constraint Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraint Mode</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1
	 * @see #getConstraintMode()
	 * @generated
	 */
	public void setConstraintMode(ConstraintMode_2_1 newConstraintMode)
	{
		ConstraintMode_2_1 oldConstraintMode = constraintMode;
		constraintMode = newConstraintMode == null ? CONSTRAINT_MODE_EDEFAULT : newConstraintMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_FOREIGN_KEY__CONSTRAINT_MODE, oldConstraintMode, constraintMode));
	}

	/**
	 * Returns the value of the '<em><b>Foreign Key Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Foreign Key Definition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Foreign Key Definition</em>' attribute.
	 * @see #setForeignKeyDefinition(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlForeignKey_2_1_ForeignKeyDefinition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getForeignKeyDefinition()
	{
		return foreignKeyDefinition;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlForeignKey#getForeignKeyDefinition <em>Foreign Key Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Foreign Key Definition</em>' attribute.
	 * @see #getForeignKeyDefinition()
	 * @generated
	 */
	public void setForeignKeyDefinition(String newForeignKeyDefinition)
	{
		String oldForeignKeyDefinition = foreignKeyDefinition;
		foreignKeyDefinition = newForeignKeyDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_FOREIGN_KEY__FOREIGN_KEY_DEFINITION, oldForeignKeyDefinition, foreignKeyDefinition));
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
			case OrmPackage.XML_FOREIGN_KEY__DESCRIPTION:
				return getDescription();
			case OrmPackage.XML_FOREIGN_KEY__NAME:
				return getName();
			case OrmPackage.XML_FOREIGN_KEY__CONSTRAINT_MODE:
				return getConstraintMode();
			case OrmPackage.XML_FOREIGN_KEY__FOREIGN_KEY_DEFINITION:
				return getForeignKeyDefinition();
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
			case OrmPackage.XML_FOREIGN_KEY__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case OrmPackage.XML_FOREIGN_KEY__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.XML_FOREIGN_KEY__CONSTRAINT_MODE:
				setConstraintMode((ConstraintMode_2_1)newValue);
				return;
			case OrmPackage.XML_FOREIGN_KEY__FOREIGN_KEY_DEFINITION:
				setForeignKeyDefinition((String)newValue);
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
			case OrmPackage.XML_FOREIGN_KEY__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OrmPackage.XML_FOREIGN_KEY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.XML_FOREIGN_KEY__CONSTRAINT_MODE:
				setConstraintMode(CONSTRAINT_MODE_EDEFAULT);
				return;
			case OrmPackage.XML_FOREIGN_KEY__FOREIGN_KEY_DEFINITION:
				setForeignKeyDefinition(FOREIGN_KEY_DEFINITION_EDEFAULT);
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
			case OrmPackage.XML_FOREIGN_KEY__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OrmPackage.XML_FOREIGN_KEY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.XML_FOREIGN_KEY__CONSTRAINT_MODE:
				return constraintMode != CONSTRAINT_MODE_EDEFAULT;
			case OrmPackage.XML_FOREIGN_KEY__FOREIGN_KEY_DEFINITION:
				return FOREIGN_KEY_DEFINITION_EDEFAULT == null ? foreignKeyDefinition != null : !FOREIGN_KEY_DEFINITION_EDEFAULT.equals(foreignKeyDefinition);
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
		result.append(", name: ");
		result.append(name);
		result.append(", constraintMode: ");
		result.append(constraintMode);
		result.append(", foreignKeyDefinition: ");
		result.append(foreignKeyDefinition);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			OrmPackage.eINSTANCE.getXmlForeignKey(),
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildConstraintModeTranslator(),
			buildForeignKeyDefinitionTranslator(),
			buildDescriptionTranslator(),
		};
	}

	protected static Translator buildNameTranslator() {
		return new Translator(JPA.NAME, OrmV2_1Package.eINSTANCE.getXmlForeignKey_2_1_Name(), Translator.DOM_ATTRIBUTE);
	}	

	protected static Translator buildForeignKeyDefinitionTranslator() {
		return new Translator(JPA2_1.FOREIGN_KEY_DEFINITION, OrmV2_1Package.eINSTANCE.getXmlForeignKey_2_1_ForeignKeyDefinition(), Translator.DOM_ATTRIBUTE);
	}	

	protected static Translator buildConstraintModeTranslator() {
		return new Translator(JPA2_1.CONSTRAINT_MODE, OrmV2_1Package.eINSTANCE.getXmlForeignKey_2_1_ConstraintMode(), Translator.DOM_ATTRIBUTE);
	}	

	protected static Translator buildDescriptionTranslator() {
		return new Translator(JPA.DESCRIPTION, OrmV2_1Package.eINSTANCE.getXmlForeignKey_2_1_Description());
	}	

} // XmlForeignKey
