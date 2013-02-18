/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredFunctionQuery;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredProcedureQuery;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredFunctionQuery;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlRecord;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity Mappings 23</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getTenantDiscriminatorColumns <em>Tenant Discriminator Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getNamedStoredFunctionQueries <em>Named Stored Function Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getNamedPlsqlStoredFunctionQueries <em>Named Plsql Stored Function Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getNamedPlsqlStoredProcedureQueries <em>Named Plsql Stored Procedure Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getPlsqlRecords <em>Plsql Records</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEntityMappings_2_3#getPlsqlTables <em>Plsql Tables</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntityMappings_2_3()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlEntityMappings_2_3 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tenant Discriminator Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tenant Discriminator Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntityMappings_2_3_TenantDiscriminatorColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlTenantDiscriminatorColumn> getTenantDiscriminatorColumns();

	/**
	 * Returns the value of the '<em><b>Named Stored Function Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredFunctionQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Stored Function Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Stored Function Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntityMappings_2_3_NamedStoredFunctionQueries()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlNamedStoredFunctionQuery> getNamedStoredFunctionQueries();

	/**
	 * Returns the value of the '<em><b>Named Plsql Stored Function Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredFunctionQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Plsql Stored Function Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Plsql Stored Function Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntityMappings_2_3_NamedPlsqlStoredFunctionQueries()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlNamedPlsqlStoredFunctionQuery> getNamedPlsqlStoredFunctionQueries();

	/**
	 * Returns the value of the '<em><b>Named Plsql Stored Procedure Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedPlsqlStoredProcedureQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Plsql Stored Procedure Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Plsql Stored Procedure Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntityMappings_2_3_NamedPlsqlStoredProcedureQueries()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlNamedPlsqlStoredProcedureQuery> getNamedPlsqlStoredProcedureQueries();

	/**
	 * Returns the value of the '<em><b>Plsql Records</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlRecord}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plsql Records</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plsql Records</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntityMappings_2_3_PlsqlRecords()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlPlsqlRecord> getPlsqlRecords();

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEntityMappings_2_3_PlsqlTables()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlPlsqlTable> getPlsqlTables();

} // XmlEntityMappings_2_3
