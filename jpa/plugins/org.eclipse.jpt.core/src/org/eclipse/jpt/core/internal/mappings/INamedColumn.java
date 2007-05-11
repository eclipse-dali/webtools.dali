/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.jpt.core.internal.IJpaSourceObject;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>INamed Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getSpecifiedName <em>Specified Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getDefaultName <em>Default Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getColumnDefinition <em>Column Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedColumn()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface INamedColumn extends IJpaSourceObject
{
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedColumn_Name()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Name</em>' attribute.
	 * @see #setSpecifiedName(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedColumn_SpecifiedName()
	 * @model
	 * @generated
	 */
	String getSpecifiedName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	void setSpecifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedColumn_DefaultName()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultName();

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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINamedColumn_ColumnDefinition()
	 * @model
	 * @generated
	 */
	String getColumnDefinition();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn#getColumnDefinition <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column Definition</em>' attribute.
	 * @see #getColumnDefinition()
	 * @generated
	 */
	void setColumnDefinition(String value);

	/**
	 * Return the wrapper for the datasource column
	 */
	Column dbColumn();

	/**
	 * Return the wrapper for the datasource table
	 */
	Table dbTable();

	/**
	 * Return whether the column is found on the datasource.
	 */
	boolean isResolved();

	/**
	 * Return the (best guess) text location of the column's name.
	 */
	ITextRange nameTextRange();

	/**
	 * Return whether the column's datasource is connected
	 */
	boolean isConnected();

	/**
	 * Return the column's "owner" - the object that contains the column
	 * and provides its context.
	 */
	Owner getOwner();


	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner
	{
		/**
		 * Return the type mapping that contains the column.
		 */
		ITypeMapping getTypeMapping();

		/**
		 * Return the column owner's text range. This can be returned by the
		 * column when its annotation is not present.
		 */
		ITextRange validationTextRange();

		/**
		 * Return the wrapper for the datasource table for the given table name
		 */
		Table dbTable(String tableName);
	}
}
