/**
 * <copyright>
 * </copyright>
 *
 * $Id: UniqueConstraint.java,v 1.1.2.2 2007/10/16 17:01:41 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.jpt.core.internal.resource.common.IJptEObject;
import org.eclipse.jpt.core.internal.resource.common.JptEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unique Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint#getColumnNames <em>Column Names</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getUniqueConstraint()
 * @model kind="class"
 * @extends IJptEObject
 * @generated
 */
public class UniqueConstraint extends JptEObject implements IJptEObject
{
	/**
	 * The cached value of the '{@link #getColumnNames() <em>Column Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> columnNames;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UniqueConstraint()
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
		return OrmPackage.Literals.UNIQUE_CONSTRAINT;
	}

	/**
	 * Returns the value of the '<em><b>Column Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Names</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Names</em>' attribute list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getUniqueConstraint_ColumnNames()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public EList<String> getColumnNames()
	{
		if (columnNames == null)
		{
			columnNames = new EDataTypeEList<String>(String.class, this, OrmPackage.UNIQUE_CONSTRAINT__COLUMN_NAMES);
		}
		return columnNames;
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
			case OrmPackage.UNIQUE_CONSTRAINT__COLUMN_NAMES:
				return getColumnNames();
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
			case OrmPackage.UNIQUE_CONSTRAINT__COLUMN_NAMES:
				getColumnNames().clear();
				getColumnNames().addAll((Collection<? extends String>)newValue);
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
			case OrmPackage.UNIQUE_CONSTRAINT__COLUMN_NAMES:
				getColumnNames().clear();
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
			case OrmPackage.UNIQUE_CONSTRAINT__COLUMN_NAMES:
				return columnNames != null && !columnNames.isEmpty();
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
		result.append(" (columnNames: ");
		result.append(columnNames);
		result.append(')');
		return result.toString();
	}

} // UniqueConstraint
