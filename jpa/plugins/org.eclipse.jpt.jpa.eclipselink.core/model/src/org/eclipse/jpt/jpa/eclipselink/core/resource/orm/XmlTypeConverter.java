/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Type CustomConverter</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 * 
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter#getDataType <em>Data Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter#getObjectType <em>Object Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTypeConverter()
 * @model kind="class"
 * @generated
 */
public class XmlTypeConverter extends EBaseObjectImpl implements XmlNamedConverter
{
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
	 * The default value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected static final String DATA_TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected String dataType = DATA_TYPE_EDEFAULT;
	/**
	 * The default value of the '{@link #getObjectType() <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectType()
	 * @generated
	 * @ordered
	 */
	protected static final String OBJECT_TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getObjectType() <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectType()
	 * @generated
	 * @ordered
	 */
	protected String objectType = OBJECT_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlTypeConverter()
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
		return EclipseLinkOrmPackage.Literals.XML_TYPE_CONVERTER;
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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedConverter_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TYPE_CONVERTER__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see #setDataType(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTypeConverter_DataType()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDataType()
	{
		return dataType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see #getDataType()
	 * @generated
	 */
	public void setDataType(String newDataType)
	{
		String oldDataType = dataType;
		dataType = newDataType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TYPE_CONVERTER__DATA_TYPE, oldDataType, dataType));
	}

	/**
	 * Returns the value of the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Type</em>' attribute.
	 * @see #setObjectType(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlTypeConverter_ObjectType()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getObjectType()
	{
		return objectType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter#getObjectType <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Type</em>' attribute.
	 * @see #getObjectType()
	 * @generated
	 */
	public void setObjectType(String newObjectType)
	{
		String oldObjectType = objectType;
		objectType = newObjectType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_TYPE_CONVERTER__OBJECT_TYPE, oldObjectType, objectType));
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
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__NAME:
				return getName();
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__DATA_TYPE:
				return getDataType();
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__OBJECT_TYPE:
				return getObjectType();
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
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__NAME:
				setName((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__DATA_TYPE:
				setDataType((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__OBJECT_TYPE:
				setObjectType((String)newValue);
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
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__DATA_TYPE:
				setDataType(DATA_TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__OBJECT_TYPE:
				setObjectType(OBJECT_TYPE_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__DATA_TYPE:
				return DATA_TYPE_EDEFAULT == null ? dataType != null : !DATA_TYPE_EDEFAULT.equals(dataType);
			case EclipseLinkOrmPackage.XML_TYPE_CONVERTER__OBJECT_TYPE:
				return OBJECT_TYPE_EDEFAULT == null ? objectType != null : !OBJECT_TYPE_EDEFAULT.equals(objectType);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", dataType: ");
		result.append(dataType);
		result.append(", objectType: ");
		result.append(objectType);
		result.append(')');
		return result.toString();
	}

	public TextRange getNameTextRange() {
		return getAttributeTextRange(JPA.NAME);
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			Translator.END_TAG_NO_INDENT,
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildDataTypeTranslator(),
			buildObjectTypeTranslator(),
		};
	}
	
	protected static Translator buildNameTranslator() {
		return new Translator(EclipseLink.CONVERTER__NAME, EclipseLinkOrmPackage.eINSTANCE.getXmlNamedConverter_Name(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildDataTypeTranslator() {
		return new Translator(EclipseLink.TYPE_CONVERTER__DATA_TYPE, EclipseLinkOrmPackage.eINSTANCE.getXmlTypeConverter_DataType(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildObjectTypeTranslator() {
		return new Translator(EclipseLink.TYPE_CONVERTER__OBJECT_TYPE, EclipseLinkOrmPackage.eINSTANCE.getXmlTypeConverter_ObjectType(), Translator.DOM_ATTRIBUTE);
	}


	// ********** refactoring **********

	public ReplaceEdit createRenameDataTypeEdit(IType originalType, String newName) {
		String originalName = originalType.getTypeQualifiedName();
		int nameIndex = this.dataType.lastIndexOf(originalName);
		int offset = getAttributeNode(EclipseLink.TYPE_CONVERTER__DATA_TYPE).getValueRegionStartOffset() + 1; // +1 = opening double quote
		return new ReplaceEdit(offset + nameIndex, originalName.length(), newName);
	}

	public ReplaceEdit createRenameDataTypePackageEdit(String newPackageName) {
		int packageLength = this.dataType.lastIndexOf('.');
		if (newPackageName == "") {//$NON-NLS-1$
			//moving to the default package, remove the '.'
			packageLength++;
		}
		if (packageLength == -1) {
			//moving from the default package or unspecified package
			packageLength = 0;
			newPackageName = newPackageName + '.';
		}
		int offset = getAttributeNode(EclipseLink.TYPE_CONVERTER__DATA_TYPE).getValueRegionStartOffset() + 1; // +1 = opening double quote
		return new ReplaceEdit(offset, packageLength, newPackageName);
	}

	public ReplaceEdit createRenameObjectTypeEdit(IType originalType, String newName) {
		String originalName = originalType.getTypeQualifiedName();
		int nameIndex = this.objectType.lastIndexOf(originalName);
		int offset = getAttributeNode(EclipseLink.TYPE_CONVERTER__OBJECT_TYPE).getValueRegionStartOffset() + 1; // +1 = opening double quote
		return new ReplaceEdit(offset + nameIndex, originalName.length(), newName);
	}

	public ReplaceEdit createRenameObjectTypePackageEdit(String newPackageName) {
		int packageLength = this.objectType.lastIndexOf('.');
		if (newPackageName == "") {//$NON-NLS-1$
			//moving to the default package, remove the '.'
			packageLength++;
		}
		if (packageLength == -1) {
			//moving from the default package or unspecified package
			packageLength = 0;
			newPackageName = newPackageName + '.';
		}
		int offset = getAttributeNode(EclipseLink.TYPE_CONVERTER__OBJECT_TYPE).getValueRegionStartOffset() + 1; // +1 = opening double quote
		return new ReplaceEdit(offset, packageLength, newPackageName);
	}

	// ********** content assist ***************
	
	public TextRange getObjectTypeCodeAssistTextRange() {
		return getAttributeCodeAssistTextRange(EclipseLink.TYPE_CONVERTER__OBJECT_TYPE);
	}
	
	public boolean objectTypeTouches(int pos) {
		TextRange textRange = this.getObjectTypeCodeAssistTextRange();
		return (textRange != null) && textRange.touches(pos);
	}
	
	public TextRange getDataTypeCodeAssistTextRange() {
		return getAttributeCodeAssistTextRange(EclipseLink.TYPE_CONVERTER__DATA_TYPE);
	}
	
	public boolean dataTypeTouches(int pos) {
		TextRange textRange = this.getDataTypeCodeAssistTextRange();
		return (textRange != null) && textRange.touches(pos);
	}
} // XmlTypeConverter
