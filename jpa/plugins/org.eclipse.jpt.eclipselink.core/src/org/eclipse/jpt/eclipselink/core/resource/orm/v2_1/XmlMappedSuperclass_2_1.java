/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.eclipselink.core.resource.orm.v2_1;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.resource.orm.SqlResultSetMapping;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer;
import org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.core.resource.orm.XmlQueryContainer;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethodsHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlQueryRedirectors;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Mapped Superclass 21</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getQueryRedirectors <em>Query Redirectors</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getParentClass <em>Parent Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlMappedSuperclass_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlMappedSuperclass_2_1 extends XmlAssociationOverrideContainer, XmlAttributeOverrideContainer, XmlFetchGroupContainer_2_1, XmlGeneratorContainer, XmlQueryContainer, org.eclipse.jpt.eclipselink.core.resource.orm.XmlQueryContainer, XmlAccessMethodsHolder
{
	/**
	 * Returns the value of the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.SqlResultSetMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sql Result Set Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sql Result Set Mappings</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlMappedSuperclass_2_1_SqlResultSetMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<SqlResultSetMapping> getSqlResultSetMappings();

	/**
	 * Returns the value of the '<em><b>Query Redirectors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query Redirectors</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query Redirectors</em>' containment reference.
	 * @see #setQueryRedirectors(XmlQueryRedirectors)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlMappedSuperclass_2_1_QueryRedirectors()
	 * @model containment="true"
	 * @generated
	 */
	XmlQueryRedirectors getQueryRedirectors();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getQueryRedirectors <em>Query Redirectors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query Redirectors</em>' containment reference.
	 * @see #getQueryRedirectors()
	 * @generated
	 */
	void setQueryRedirectors(XmlQueryRedirectors value);

	/**
	 * Returns the value of the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Class</em>' attribute.
	 * @see #setParentClass(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlMappedSuperclass_2_1_ParentClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getParentClass();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlMappedSuperclass_2_1#getParentClass <em>Parent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Class</em>' attribute.
	 * @see #getParentClass()
	 * @generated
	 */
	void setParentClass(String value);

} // XmlMappedSuperclass_2_1
