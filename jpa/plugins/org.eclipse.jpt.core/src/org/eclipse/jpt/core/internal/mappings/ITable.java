/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.jpt.core.internal.IJpaSourceObject;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ITable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITable#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedName <em>Specified Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITable#getDefaultName <em>Default Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITable#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedCatalog <em>Specified Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITable#getDefaultCatalog <em>Default Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITable#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedSchema <em>Specified Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITable#getDefaultSchema <em>Default Schema</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface ITable extends IJpaSourceObject
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable_Name()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable_DefaultName()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultName();

	/**
	 * Returns the value of the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalog</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable_Catalog()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getCatalog();

	/**
	 * Returns the value of the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Catalog</em>' attribute.
	 * @see #setSpecifiedCatalog(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable_SpecifiedCatalog()
	 * @model
	 * @generated
	 */
	String getSpecifiedCatalog();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedCatalog <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Catalog</em>' attribute.
	 * @see #getSpecifiedCatalog()
	 * @generated
	 */
	void setSpecifiedCatalog(String value);

	/**
	 * Returns the value of the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Catalog</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable_DefaultCatalog()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultCatalog();

	/**
	 * Returns the value of the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable_Schema()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getSchema();

	/**
	 * Returns the value of the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Schema</em>' attribute.
	 * @see #setSpecifiedSchema(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable_SpecifiedSchema()
	 * @model
	 * @generated
	 */
	String getSpecifiedSchema();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedSchema <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Schema</em>' attribute.
	 * @see #getSpecifiedSchema()
	 * @generated
	 */
	void setSpecifiedSchema(String value);

	/**
	 * Returns the value of the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Schema</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable_DefaultSchema()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultSchema();

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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITable_SpecifiedName()
	 * @model
	 * @generated
	 */
	String getSpecifiedName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ITable#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	void setSpecifiedName(String value);

	void refreshDefaults(DefaultsContext defaultsContext);

	Table dbTable();

	/**
	 * Return true if this table is connected to a datasource
	 */
	boolean isConnected();

	/** 
	 * Return true if this table's schema can be resolved to a schema on the active connection
	 */
	boolean hasResolvedSchema();

	/** 
	 * Return true if this can be resolved to a table on the active connection
	 */
	boolean isResolved();

	ITextRange getNameTextRange();

	ITextRange getSchemaTextRange();

	Owner getOwner();
	/**
	 * interface allowing tables to be owned by various objects
	 */
	interface Owner
	{
		ITextRange getTextRange();

		ITypeMapping getTypeMapping();
	}
}