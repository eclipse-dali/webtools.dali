/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheIndex;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNoSql;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity 24</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4#getCacheIndex <em>Cache Index</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4#getNoSql <em>No Sql</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEntity_2_4()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlEntity_2_4 extends XmlGeneratorContainer2_4
{
	/**
	 * Returns the value of the '<em><b>Cache Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Index</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Index</em>' containment reference.
	 * @see #setCacheIndex(XmlCacheIndex)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEntity_2_4_CacheIndex()
	 * @model containment="true"
	 * @generated
	 */
	XmlCacheIndex getCacheIndex();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4#getCacheIndex <em>Cache Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Index</em>' containment reference.
	 * @see #getCacheIndex()
	 * @generated
	 */
	void setCacheIndex(XmlCacheIndex value);

	/**
	 * Returns the value of the '<em><b>No Sql</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>No Sql</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>No Sql</em>' containment reference.
	 * @see #setNoSql(XmlNoSql)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlEntity_2_4_NoSql()
	 * @model containment="true"
	 * @generated
	 */
	XmlNoSql getNoSql();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlEntity_2_4#getNoSql <em>No Sql</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>No Sql</em>' containment reference.
	 * @see #getNoSql()
	 * @generated
	 */
	void setNoSql(XmlNoSql value);

} // XmlEntity_2_4
