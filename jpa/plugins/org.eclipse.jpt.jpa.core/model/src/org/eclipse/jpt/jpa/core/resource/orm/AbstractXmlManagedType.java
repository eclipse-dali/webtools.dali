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

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Xml Managed Type</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getAbstractXmlManagedType()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractXmlManagedType extends EBaseObjectImpl implements XmlManagedType
{
	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractXmlManagedType()
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
		return OrmPackage.Literals.ABSTRACT_XML_MANAGED_TYPE;
	}

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlManagedType_ClassName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlManagedType#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	public void setClassName(String newClassName)
	{
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MANAGED_TYPE__CLASS_NAME, oldClassName, className));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlManagedType_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlManagedType#getDescription <em>Description</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_MANAGED_TYPE__DESCRIPTION, oldDescription, description));
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
			case OrmPackage.ABSTRACT_XML_MANAGED_TYPE__CLASS_NAME:
				return getClassName();
			case OrmPackage.ABSTRACT_XML_MANAGED_TYPE__DESCRIPTION:
				return getDescription();
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
			case OrmPackage.ABSTRACT_XML_MANAGED_TYPE__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_MANAGED_TYPE__DESCRIPTION:
				setDescription((String)newValue);
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
			case OrmPackage.ABSTRACT_XML_MANAGED_TYPE__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_MANAGED_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
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
			case OrmPackage.ABSTRACT_XML_MANAGED_TYPE__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case OrmPackage.ABSTRACT_XML_MANAGED_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
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
		result.append(" (className: ");
		result.append(className);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}


	public TextRange getClassTextRange() {
		return getAttributeTextRange(JPA.CLASS);
	}


	// ********** translators **********

	protected static Translator buildDescriptionTranslator() {
		return new Translator(JPA.DESCRIPTION, OrmPackage.eINSTANCE.getXmlManagedType_Description());
	}
	
	protected static Translator buildClassTranslator() {
		return new Translator(JPA.CLASS, OrmPackage.eINSTANCE.getXmlManagedType_ClassName(), Translator.DOM_ATTRIBUTE);
	}


	// ********** refactoring **********

	public ReplaceEdit createRenameTypeEdit(IType originalType, String newName) {
		String originalName = originalType.getTypeQualifiedName();
		int nameIndex = this.className.lastIndexOf(originalName);
		int offset = getAttributeNode(JPA.CLASS).getValueRegionStartOffset() + 1; // +1 = opening double quote
		return new ReplaceEdit(offset + nameIndex, originalName.length(), newName);
	}

	public ReplaceEdit createRenamePackageEdit(String newPackageName) {
		int packageLength = this.className.lastIndexOf('.');
		if (newPackageName == "") {//$NON-NLS-1$
			//moving to the default package, remove the '.'
			packageLength++;
		}
		if (packageLength == -1) {
			//moving from the default package or unspecified package
			packageLength = 0;
			newPackageName = newPackageName + '.';
		}
		int offset = getAttributeNode(JPA.CLASS).getValueRegionStartOffset() + 1; // +1 = opening double quote
		return new ReplaceEdit(offset, packageLength, newPackageName);
	}

	// *********** content assist ************
	
	protected TextRange getClassNameCodeAssistTextRange() {
		return getAttributeCodeAssistTextRange(JPA.CLASS);
	}

	public boolean classNameTouches(int pos) {
		TextRange textRange = this.getClassNameCodeAssistTextRange();
		return (textRange != null) && (textRange.touches(pos));
	}
} // AbstractXmlManagedType
