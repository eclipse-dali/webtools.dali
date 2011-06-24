/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.jpa.core.resource.xml.JpaEObject;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Mapped Superclass 23</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getMultitenant <em>Multitenant</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getNamedStoredFunctionQueries <em>Named Stored Function Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getNamedPlsqlStoredFunctionQueries <em>Named Plsql Stored Function Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getNamedPlsqlStoredProcedureQueries <em>Named Plsql Stored Procedure Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getPlsqlRecords <em>Plsql Records</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getPlsqlTables <em>Plsql Tables</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMappedSuperclass_2_3()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlMappedSuperclass_2_3 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Multitenant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multitenant</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multitenant</em>' containment reference.
	 * @see #setMultitenant(XmlMultitenant_2_3)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMappedSuperclass_2_3_Multitenant()
	 * @model containment="true"
	 * @generated
	 */
	XmlMultitenant_2_3 getMultitenant();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlMappedSuperclass_2_3#getMultitenant <em>Multitenant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multitenant</em>' containment reference.
	 * @see #getMultitenant()
	 * @generated
	 */
	void setMultitenant(XmlMultitenant_2_3 value);

	/**
	 * Returns the value of the '<em><b>Named Stored Function Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedStoredFunctionQuery_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Stored Function Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Stored Function Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMappedSuperclass_2_3_NamedStoredFunctionQueries()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlNamedStoredFunctionQuery_2_3> getNamedStoredFunctionQueries();

	/**
	 * Returns the value of the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredFunctionQuery_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Plsql Stored Function Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Plsql Stored Function Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMappedSuperclass_2_3_NamedPlsqlStoredFunctionQueries()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlNamedPlsqlStoredFunctionQuery_2_3> getNamedPlsqlStoredFunctionQueries();

	/**
	 * Returns the value of the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlNamedPlsqlStoredProcedureQuery_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Plsql Stored Procedure Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Plsql Stored Procedure Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMappedSuperclass_2_3_NamedPlsqlStoredProcedureQueries()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlNamedPlsqlStoredProcedureQuery_2_3> getNamedPlsqlStoredProcedureQueries();

	/**
	 * Returns the value of the '<em><b>Plsql Records</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plsql Records</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plsql Records</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMappedSuperclass_2_3_PlsqlRecords()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlPlsqlRecord_2_3> getPlsqlRecords();

	/**
	 * Returns the value of the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plsql Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plsql Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMappedSuperclass_2_3_PlsqlTables()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlPlsqlTable> getPlsqlTables();

} // XmlMappedSuperclass_2_3
