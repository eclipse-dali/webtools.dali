/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.elorm;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Xml Abstract Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getAbstractXmlAbstractColumn()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractXmlAbstractColumn extends AbstractXmlNamedColumn implements XmlAbstractColumn
{
	/**
	 * The default value of the '{@link #getInsertable() <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean INSERTABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInsertable() <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertable()
	 * @generated
	 * @ordered
	 */
	protected Boolean insertable = INSERTABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNullable() <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNullable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean NULLABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNullable() <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNullable()
	 * @generated
	 * @ordered
	 */
	protected Boolean nullable = NULLABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTable() <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected static final String TABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTable() <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected String table = TABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnique()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean UNIQUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnique()
	 * @generated
	 * @ordered
	 */
	protected Boolean unique = UNIQUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpdatable() <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdatable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean UPDATABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpdatable() <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdatable()
	 * @generated
	 * @ordered
	 */
	protected Boolean updatable = UPDATABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractXmlAbstractColumn()
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
		return EclipseLinkOrmPackage.Literals.ABSTRACT_XML_ABSTRACT_COLUMN;
	}

	/**
	 * Returns the value of the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insertable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Insertable</em>' attribute.
	 * @see #setInsertable(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlAbstractColumn_Insertable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getInsertable()
	{
		return insertable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlAbstractColumn#getInsertable <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insertable</em>' attribute.
	 * @see #getInsertable()
	 * @generated
	 */
	public void setInsertable(Boolean newInsertable)
	{
		Boolean oldInsertable = insertable;
		insertable = newInsertable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__INSERTABLE, oldInsertable, insertable));
	}

	/**
	 * Returns the value of the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nullable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nullable</em>' attribute.
	 * @see #setNullable(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlAbstractColumn_Nullable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getNullable()
	{
		return nullable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlAbstractColumn#getNullable <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nullable</em>' attribute.
	 * @see #getNullable()
	 * @generated
	 */
	public void setNullable(Boolean newNullable)
	{
		Boolean oldNullable = nullable;
		nullable = newNullable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__NULLABLE, oldNullable, nullable));
	}

	/**
	 * Returns the value of the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' attribute.
	 * @see #setTable(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlAbstractColumn_Table()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTable()
	{
		return table;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlAbstractColumn#getTable <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' attribute.
	 * @see #getTable()
	 * @generated
	 */
	public void setTable(String newTable)
	{
		String oldTable = table;
		table = newTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__TABLE, oldTable, table));
	}

	/**
	 * Returns the value of the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique</em>' attribute.
	 * @see #setUnique(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlAbstractColumn_Unique()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getUnique()
	{
		return unique;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlAbstractColumn#getUnique <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique</em>' attribute.
	 * @see #getUnique()
	 * @generated
	 */
	public void setUnique(Boolean newUnique)
	{
		Boolean oldUnique = unique;
		unique = newUnique;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UNIQUE, oldUnique, unique));
	}

	/**
	 * Returns the value of the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Updatable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Updatable</em>' attribute.
	 * @see #setUpdatable(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlAbstractColumn_Updatable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getUpdatable()
	{
		return updatable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlAbstractColumn#getUpdatable <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updatable</em>' attribute.
	 * @see #getUpdatable()
	 * @generated
	 */
	public void setUpdatable(Boolean newUpdatable)
	{
		Boolean oldUpdatable = updatable;
		updatable = newUpdatable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UPDATABLE, oldUpdatable, updatable));
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
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__INSERTABLE:
				return getInsertable();
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__NULLABLE:
				return getNullable();
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__TABLE:
				return getTable();
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UNIQUE:
				return getUnique();
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UPDATABLE:
				return getUpdatable();
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
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__INSERTABLE:
				setInsertable((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__NULLABLE:
				setNullable((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__TABLE:
				setTable((String)newValue);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UNIQUE:
				setUnique((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UPDATABLE:
				setUpdatable((Boolean)newValue);
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
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__INSERTABLE:
				setInsertable(INSERTABLE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__NULLABLE:
				setNullable(NULLABLE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__TABLE:
				setTable(TABLE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UNIQUE:
				setUnique(UNIQUE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UPDATABLE:
				setUpdatable(UPDATABLE_EDEFAULT);
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
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__INSERTABLE:
				return INSERTABLE_EDEFAULT == null ? insertable != null : !INSERTABLE_EDEFAULT.equals(insertable);
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__NULLABLE:
				return NULLABLE_EDEFAULT == null ? nullable != null : !NULLABLE_EDEFAULT.equals(nullable);
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__TABLE:
				return TABLE_EDEFAULT == null ? table != null : !TABLE_EDEFAULT.equals(table);
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UNIQUE:
				return UNIQUE_EDEFAULT == null ? unique != null : !UNIQUE_EDEFAULT.equals(unique);
			case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UPDATABLE:
				return UPDATABLE_EDEFAULT == null ? updatable != null : !UPDATABLE_EDEFAULT.equals(updatable);
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
		if (baseClass == XmlAbstractColumn.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__INSERTABLE: return EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__INSERTABLE;
				case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__NULLABLE: return EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__NULLABLE;
				case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__TABLE: return EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__TABLE;
				case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UNIQUE: return EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__UNIQUE;
				case EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UPDATABLE: return EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__UPDATABLE;
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
		if (baseClass == XmlAbstractColumn.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__INSERTABLE: return EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__INSERTABLE;
				case EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__NULLABLE: return EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__NULLABLE;
				case EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__TABLE: return EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__TABLE;
				case EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__UNIQUE: return EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UNIQUE;
				case EclipseLinkOrmPackage.XML_ABSTRACT_COLUMN__UPDATABLE: return EclipseLinkOrmPackage.ABSTRACT_XML_ABSTRACT_COLUMN__UPDATABLE;
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
		result.append(" (insertable: ");
		result.append(insertable);
		result.append(", nullable: ");
		result.append(nullable);
		result.append(", table: ");
		result.append(table);
		result.append(", unique: ");
		result.append(unique);
		result.append(", updatable: ");
		result.append(updatable);
		result.append(')');
		return result.toString();
	}

} // AbstractXmlAbstractColumn
