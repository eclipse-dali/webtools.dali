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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Tenant Discriminator2 3</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getContextProperty <em>Context Property</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getTable <em>Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getPrimaryKey <em>Primary Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlTenantDiscriminatorColumn_2_3()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlTenantDiscriminatorColumn_2_3
{

	/**
	 * Returns the value of the '<em><b>Context Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Property</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Property</em>' attribute.
	 * @see #setContextProperty(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlTenantDiscriminatorColumn_2_3_ContextProperty()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getContextProperty();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getContextProperty <em>Context Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context Property</em>' attribute.
	 * @see #getContextProperty()
	 * @generated
	 */
	void setContextProperty(String value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlTenantDiscriminatorColumn_2_3_Table()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getTable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getTable <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' attribute.
	 * @see #getTable()
	 * @generated
	 */
	void setTable(String value);

	/**
	 * Returns the value of the '<em><b>Primary Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key</em>' attribute.
	 * @see #setPrimaryKey(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlTenantDiscriminatorColumn_2_3_PrimaryKey()
	 * @model
	 * @generated
	 */
	Boolean getPrimaryKey();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3#getPrimaryKey <em>Primary Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key</em>' attribute.
	 * @see #getPrimaryKey()
	 * @generated
	 */
	void setPrimaryKey(Boolean value);
} // XmlTenantDiscriminator2_3
