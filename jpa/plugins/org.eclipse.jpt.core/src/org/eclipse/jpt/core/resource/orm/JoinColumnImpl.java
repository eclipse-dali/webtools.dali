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
 * A representation of the model object '<em><b>Join Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getJoinColumnImpl()
 * @model kind="class"
 * @generated
 */
public class JoinColumnImpl extends AbstractJpaEObject implements XmlJoinColumn
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
	 * The default value of the '{@link #getReferencedColumnName() <em>Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReferencedColumnName() <em>Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected String referencedColumnName = REFERENCED_COLUMN_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JoinColumnImpl()
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
		return OrmPackage.Literals.JOIN_COLUMN_IMPL;
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getNamedColumn_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.JoinColumnImpl#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.JOIN_COLUMN_IMPL__NAME, oldName, name));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractColumn_Insertable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getInsertable()
	{
		return insertable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.JoinColumnImpl#getInsertable <em>Insertable</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.JOIN_COLUMN_IMPL__INSERTABLE, oldInsertable, insertable));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractColumn_Nullable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getNullable()
	{
		return nullable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.JoinColumnImpl#getNullable <em>Nullable</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.JOIN_COLUMN_IMPL__NULLABLE, oldNullable, nullable));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getNamedColumn_ColumnDefinition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getColumnDefinition()
	{
		return columnDefinition;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.JoinColumnImpl#getColumnDefinition <em>Column Definition</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.JOIN_COLUMN_IMPL__COLUMN_DEFINITION, oldColumnDefinition, columnDefinition));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractColumn_Table()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTable()
	{
		return table;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.JoinColumnImpl#getTable <em>Table</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.JOIN_COLUMN_IMPL__TABLE, oldTable, table));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractColumn_Unique()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getUnique()
	{
		return unique;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.JoinColumnImpl#getUnique <em>Unique</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.JOIN_COLUMN_IMPL__UNIQUE, oldUnique, unique));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractColumn_Updatable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getUpdatable()
	{
		return updatable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.JoinColumnImpl#getUpdatable <em>Updatable</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.JOIN_COLUMN_IMPL__UPDATABLE, oldUpdatable, updatable));
	}

	/**
	 * Returns the value of the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Column Name</em>' attribute.
	 * @see #setReferencedColumnName(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getJoinColumn_ReferencedColumnName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getReferencedColumnName()
	{
		return referencedColumnName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.JoinColumnImpl#getReferencedColumnName <em>Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenced Column Name</em>' attribute.
	 * @see #getReferencedColumnName()
	 * @generated
	 */
	public void setReferencedColumnName(String newReferencedColumnName)
	{
		String oldReferencedColumnName = referencedColumnName;
		referencedColumnName = newReferencedColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.JOIN_COLUMN_IMPL__REFERENCED_COLUMN_NAME, oldReferencedColumnName, referencedColumnName));
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
			case OrmPackage.JOIN_COLUMN_IMPL__COLUMN_DEFINITION:
				return getColumnDefinition();
			case OrmPackage.JOIN_COLUMN_IMPL__NAME:
				return getName();
			case OrmPackage.JOIN_COLUMN_IMPL__INSERTABLE:
				return getInsertable();
			case OrmPackage.JOIN_COLUMN_IMPL__NULLABLE:
				return getNullable();
			case OrmPackage.JOIN_COLUMN_IMPL__TABLE:
				return getTable();
			case OrmPackage.JOIN_COLUMN_IMPL__UNIQUE:
				return getUnique();
			case OrmPackage.JOIN_COLUMN_IMPL__UPDATABLE:
				return getUpdatable();
			case OrmPackage.JOIN_COLUMN_IMPL__REFERENCED_COLUMN_NAME:
				return getReferencedColumnName();
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
			case OrmPackage.JOIN_COLUMN_IMPL__COLUMN_DEFINITION:
				setColumnDefinition((String)newValue);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__INSERTABLE:
				setInsertable((Boolean)newValue);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__NULLABLE:
				setNullable((Boolean)newValue);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__TABLE:
				setTable((String)newValue);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__UNIQUE:
				setUnique((Boolean)newValue);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__UPDATABLE:
				setUpdatable((Boolean)newValue);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__REFERENCED_COLUMN_NAME:
				setReferencedColumnName((String)newValue);
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
			case OrmPackage.JOIN_COLUMN_IMPL__COLUMN_DEFINITION:
				setColumnDefinition(COLUMN_DEFINITION_EDEFAULT);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__INSERTABLE:
				setInsertable(INSERTABLE_EDEFAULT);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__NULLABLE:
				setNullable(NULLABLE_EDEFAULT);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__TABLE:
				setTable(TABLE_EDEFAULT);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__UNIQUE:
				setUnique(UNIQUE_EDEFAULT);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__UPDATABLE:
				setUpdatable(UPDATABLE_EDEFAULT);
				return;
			case OrmPackage.JOIN_COLUMN_IMPL__REFERENCED_COLUMN_NAME:
				setReferencedColumnName(REFERENCED_COLUMN_NAME_EDEFAULT);
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
			case OrmPackage.JOIN_COLUMN_IMPL__COLUMN_DEFINITION:
				return COLUMN_DEFINITION_EDEFAULT == null ? columnDefinition != null : !COLUMN_DEFINITION_EDEFAULT.equals(columnDefinition);
			case OrmPackage.JOIN_COLUMN_IMPL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.JOIN_COLUMN_IMPL__INSERTABLE:
				return INSERTABLE_EDEFAULT == null ? insertable != null : !INSERTABLE_EDEFAULT.equals(insertable);
			case OrmPackage.JOIN_COLUMN_IMPL__NULLABLE:
				return NULLABLE_EDEFAULT == null ? nullable != null : !NULLABLE_EDEFAULT.equals(nullable);
			case OrmPackage.JOIN_COLUMN_IMPL__TABLE:
				return TABLE_EDEFAULT == null ? table != null : !TABLE_EDEFAULT.equals(table);
			case OrmPackage.JOIN_COLUMN_IMPL__UNIQUE:
				return UNIQUE_EDEFAULT == null ? unique != null : !UNIQUE_EDEFAULT.equals(unique);
			case OrmPackage.JOIN_COLUMN_IMPL__UPDATABLE:
				return UPDATABLE_EDEFAULT == null ? updatable != null : !UPDATABLE_EDEFAULT.equals(updatable);
			case OrmPackage.JOIN_COLUMN_IMPL__REFERENCED_COLUMN_NAME:
				return REFERENCED_COLUMN_NAME_EDEFAULT == null ? referencedColumnName != null : !REFERENCED_COLUMN_NAME_EDEFAULT.equals(referencedColumnName);
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
		result.append(", insertable: ");
		result.append(insertable);
		result.append(", nullable: ");
		result.append(nullable);
		result.append(", table: ");
		result.append(table);
		result.append(", unique: ");
		result.append(unique);
		result.append(", updatable: ");
		result.append(updatable);
		result.append(", referencedColumnName: ");
		result.append(referencedColumnName);
		result.append(')');
		return result.toString();
	}

} // JoinColumn
