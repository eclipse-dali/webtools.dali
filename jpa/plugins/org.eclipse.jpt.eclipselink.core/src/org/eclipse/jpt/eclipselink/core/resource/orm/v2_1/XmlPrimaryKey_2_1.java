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

import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Primary Key 21</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1#getCacheKeyType <em>Cache Key Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlPrimaryKey_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlPrimaryKey_2_1 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Cache Key Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Key Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Key Type</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1
	 * @see #setCacheKeyType(CacheKeyType_2_1)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlPrimaryKey_2_1_CacheKeyType()
	 * @model
	 * @generated
	 */
	CacheKeyType_2_1 getCacheKeyType();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlPrimaryKey_2_1#getCacheKeyType <em>Cache Key Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Key Type</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.CacheKeyType_2_1
	 * @see #getCacheKeyType()
	 * @generated
	 */
	void setCacheKeyType(CacheKeyType_2_1 value);

} // XmlPrimaryKey_2_1
