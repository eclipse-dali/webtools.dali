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
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Persistence Unit Defaults 23</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPersistenceUnitDefaults_2_3#getTenantDiscriminatorColumns <em>Tenant Discriminator Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlPersistenceUnitDefaults_2_3()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlPersistenceUnitDefaults_2_3 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Tenant Discriminator Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tenant Discriminator Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tenant Discriminator Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlPersistenceUnitDefaults_2_3_TenantDiscriminatorColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlTenantDiscriminatorColumn_2_3> getTenantDiscriminatorColumns();

} // XmlPersistenceUnitDefaults_2_3
