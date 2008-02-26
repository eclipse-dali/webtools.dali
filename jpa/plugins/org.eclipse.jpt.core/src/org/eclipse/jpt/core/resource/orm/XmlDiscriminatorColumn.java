/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Discriminator Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlDiscriminatorColumn()
 * @model kind="class"
 * @generated
 */
public class XmlDiscriminatorColumn extends AbstractJpaEObject implements XmlNamedColumn
{
	/**
	 * The default value of the '{@link #getColumnDefinition() <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnDefinition()
	 * @generated
	 * @ordered
	 */
	protected static final String COLUMN_DEFINITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColumnDefinition() <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnDefinition()
	 * @generated
	 * @ordered
	 */
	protected String columnDefinition = COLUMN_DEFINITION_EDEFAULT;

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
	protected static final DiscriminatorType DISCRIMINATOR_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiscriminatorType() <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorType()
	 * @generated
	 * @ordered
	 */
	protected DiscriminatorType discriminatorType = DISCRIMINATOR_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final Integer LENGTH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected Integer length = LENGTH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlDiscriminatorColumn()
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
		return OrmPackage.Literals.XML_DISCRIMINATOR_COLUMN;
	}

	/**
	 * Returns the value of the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Definition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Definition</em>' attribute.
	 * @see #setColumnDefinition(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlNamedColumn_ColumnDefinition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getColumnDefinition()
	{
		return columnDefinition;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getColumnDefinition <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column Definition</em>' attribute.
	 * @see #getColumnDefinition()
	 * @generated
	 */
	public void setColumnDefinition(String newColumnDefinition)
	{
		String oldColumnDefinition = columnDefinition;
		columnDefinition = newColumnDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION, oldColumnDefinition, columnDefinition));
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Type</b></em>' attribute.
	 * The default value is <code>"STRING"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.DiscriminatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Type</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.DiscriminatorType
	 * @see #setDiscriminatorType(DiscriminatorType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlDiscriminatorColumn_DiscriminatorType()
	 * @model default="STRING"
	 * @generated
	 */
	public DiscriminatorType getDiscriminatorType()
	{
		return discriminatorType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Type</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.DiscriminatorType
	 * @see #getDiscriminatorType()
	 * @generated
	 */
	public void setDiscriminatorType(DiscriminatorType newDiscriminatorType)
	{
		DiscriminatorType oldDiscriminatorType = discriminatorType;
		discriminatorType = newDiscriminatorType == null ? DISCRIMINATOR_TYPE_EDEFAULT : newDiscriminatorType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE, oldDiscriminatorType, discriminatorType));
	}

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(Integer)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlDiscriminatorColumn_Length()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	public Integer getLength()
	{
		return length;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	public void setLength(Integer newLength)
	{
		Integer oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__LENGTH, oldLength, length));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlNamedColumn_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__NAME, oldName, name));
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
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION:
				return getColumnDefinition();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__NAME:
				return getName();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE:
				return getDiscriminatorType();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__LENGTH:
				return getLength();
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
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION:
				setColumnDefinition((String)newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE:
				setDiscriminatorType((DiscriminatorType)newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__LENGTH:
				setLength((Integer)newValue);
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
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION:
				setColumnDefinition(COLUMN_DEFINITION_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE:
				setDiscriminatorType(DISCRIMINATOR_TYPE_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__LENGTH:
				setLength(LENGTH_EDEFAULT);
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
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION:
				return COLUMN_DEFINITION_EDEFAULT == null ? columnDefinition != null : !COLUMN_DEFINITION_EDEFAULT.equals(columnDefinition);
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE:
				return discriminatorType != DISCRIMINATOR_TYPE_EDEFAULT;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__LENGTH:
				return LENGTH_EDEFAULT == null ? length != null : !LENGTH_EDEFAULT.equals(length);
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
		result.append(" (columnDefinition: ");
		result.append(columnDefinition);
		result.append(", name: ");
		result.append(name);
		result.append(", discriminatorType: ");
		result.append(discriminatorType);
		result.append(", length: ");
		result.append(length);
		result.append(')');
		return result.toString();
	}

} // DiscriminatorColumn
