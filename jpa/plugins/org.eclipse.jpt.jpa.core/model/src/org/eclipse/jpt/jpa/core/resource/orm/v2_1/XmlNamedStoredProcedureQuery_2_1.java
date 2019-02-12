/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm.v2_1;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Named Stored Procedure Query 21</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getResultClasses <em>Result Classes</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getResultSetMappings <em>Result Set Mappings</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getProcedureName <em>Procedure Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedStoredProcedureQuery_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlNamedStoredProcedureQuery_2_1 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Result Classes</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Classes</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Classes</em>' attribute list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedStoredProcedureQuery_2_1_ResultClasses()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	EList<String> getResultClasses();

	/**
	 * Returns the value of the '<em><b>Result Set Mappings</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Set Mappings</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Set Mappings</em>' attribute list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedStoredProcedureQuery_2_1_ResultSetMappings()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	EList<String> getResultSetMappings();

	/**
	 * Returns the value of the '<em><b>Procedure Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Procedure Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Procedure Name</em>' attribute.
	 * @see #setProcedureName(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedStoredProcedureQuery_2_1_ProcedureName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getProcedureName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlNamedStoredProcedureQuery_2_1#getProcedureName <em>Procedure Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Procedure Name</em>' attribute.
	 * @see #getProcedureName()
	 * @generated
	 */
	void setProcedureName(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlNamedStoredProcedureQuery_2_1_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlStoredProcedureParameter> getParameters();

} // XmlNamedStoredProcedureQuery_2_1
