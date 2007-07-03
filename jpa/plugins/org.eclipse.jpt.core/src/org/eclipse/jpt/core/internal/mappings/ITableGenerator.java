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

import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ITable Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getTable <em>Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedTable <em>Specified Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultTable <em>Default Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedCatalog <em>Specified Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultCatalog <em>Default Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedSchema <em>Specified Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultSchema <em>Default Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getPkColumnName <em>Pk Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedPkColumnName <em>Specified Pk Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultPkColumnName <em>Default Pk Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getValueColumnName <em>Value Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedValueColumnName <em>Specified Value Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultValueColumnName <em>Default Value Column Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getPkColumnValue <em>Pk Column Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedPkColumnValue <em>Specified Pk Column Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getDefaultPkColumnValue <em>Default Pk Column Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getUniqueConstraints <em>Unique Constraints</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface ITableGenerator extends IGenerator
{
	/**
	 * Returns the value of the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_Table()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getTable();

	/**
	 * Returns the value of the '<em><b>Specified Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Table</em>' attribute.
	 * @see #setSpecifiedTable(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_SpecifiedTable()
	 * @model
	 * @generated
	 */
	String getSpecifiedTable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedTable <em>Specified Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Table</em>' attribute.
	 * @see #getSpecifiedTable()
	 * @generated
	 */
	void setSpecifiedTable(String value);

	/**
	 * Returns the value of the '<em><b>Default Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Table</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_DefaultTable()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultTable();

	/**
	 * Returns the value of the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalog</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_Catalog()
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_SpecifiedCatalog()
	 * @model
	 * @generated
	 */
	String getSpecifiedCatalog();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedCatalog <em>Specified Catalog</em>}' attribute.
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_DefaultCatalog()
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_Schema()
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_SpecifiedSchema()
	 * @model
	 * @generated
	 */
	String getSpecifiedSchema();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedSchema <em>Specified Schema</em>}' attribute.
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_DefaultSchema()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultSchema();

	/**
	 * Returns the value of the '<em><b>Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pk Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pk Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_PkColumnName()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getPkColumnName();

	/**
	 * Returns the value of the '<em><b>Specified Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Pk Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Pk Column Name</em>' attribute.
	 * @see #setSpecifiedPkColumnName(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_SpecifiedPkColumnName()
	 * @model
	 * @generated
	 */
	String getSpecifiedPkColumnName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedPkColumnName <em>Specified Pk Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Pk Column Name</em>' attribute.
	 * @see #getSpecifiedPkColumnName()
	 * @generated
	 */
	void setSpecifiedPkColumnName(String value);

	/**
	 * Returns the value of the '<em><b>Default Pk Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Pk Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Pk Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_DefaultPkColumnName()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultPkColumnName();

	/**
	 * Returns the value of the '<em><b>Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_ValueColumnName()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getValueColumnName();

	/**
	 * Returns the value of the '<em><b>Specified Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Value Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Value Column Name</em>' attribute.
	 * @see #setSpecifiedValueColumnName(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_SpecifiedValueColumnName()
	 * @model
	 * @generated
	 */
	String getSpecifiedValueColumnName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedValueColumnName <em>Specified Value Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Value Column Name</em>' attribute.
	 * @see #getSpecifiedValueColumnName()
	 * @generated
	 */
	void setSpecifiedValueColumnName(String value);

	/**
	 * Returns the value of the '<em><b>Default Value Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_DefaultValueColumnName()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultValueColumnName();

	/**
	 * Returns the value of the '<em><b>Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pk Column Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pk Column Value</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_PkColumnValue()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getPkColumnValue();

	/**
	 * Returns the value of the '<em><b>Specified Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Pk Column Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Pk Column Value</em>' attribute.
	 * @see #setSpecifiedPkColumnValue(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_SpecifiedPkColumnValue()
	 * @model
	 * @generated
	 */
	String getSpecifiedPkColumnValue();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator#getSpecifiedPkColumnValue <em>Specified Pk Column Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Pk Column Value</em>' attribute.
	 * @see #getSpecifiedPkColumnValue()
	 * @generated
	 */
	void setSpecifiedPkColumnValue(String value);

	/**
	 * Returns the value of the '<em><b>Default Pk Column Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Pk Column Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Pk Column Value</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_DefaultPkColumnValue()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultPkColumnValue();

	/**
	 * Returns the value of the '<em><b>Unique Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IUniqueConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Constraints</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getITableGenerator_UniqueConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IUniqueConstraint> getUniqueConstraints();

	void refreshDefaults(DefaultsContext defaultsContext);

	IUniqueConstraint createUniqueConstraint(int index);

	Schema dbSchema();

	Table dbTable();


	class UniqueConstraintOwner implements IUniqueConstraint.Owner
	{
		private final ITableGenerator tableGenerator;

		public UniqueConstraintOwner(ITableGenerator tableGenerator) {
			super();
			this.tableGenerator = tableGenerator;
		}

		public Iterator<String> candidateUniqueConstraintColumnNames() {
			return this.tableGenerator.dbTable().columnNames();
		}
	}
}
