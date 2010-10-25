/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm.v2_2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Index 22</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getTable <em>Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getUnique <em>Unique</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getColumnNames <em>Column Names</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlIndex_2_2 extends JpaEObject
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
	 * @see #setName(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema</em>' attribute.
	 * @see #setSchema(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2_Schema()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getSchema();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getSchema <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema</em>' attribute.
	 * @see #getSchema()
	 * @generated
	 */
	void setSchema(String value);

	/**
	 * Returns the value of the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalog</em>' attribute.
	 * @see #setCatalog(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2_Catalog()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getCatalog();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getCatalog <em>Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catalog</em>' attribute.
	 * @see #getCatalog()
	 * @generated
	 */
	void setCatalog(String value);

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2_Table()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getTable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getTable <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' attribute.
	 * @see #getTable()
	 * @generated
	 */
	void setTable(String value);

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2_Unique()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getUnique();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlIndex_2_2#getUnique <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique</em>' attribute.
	 * @see #getUnique()
	 * @generated
	 */
	void setUnique(Boolean value);

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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlIndex_2_2_ColumnNames()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	EList<String> getColumnNames();

} // XmlIndex_2_2
