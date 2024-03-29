/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlQueryRedirectors;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity2 0</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.XmlEntity_2_0#getCacheInterceptor <em>Cache Interceptor</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.XmlEntity_2_0#getQueryRedirectors <em>Query Redirectors</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlEntity_2_0()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlEntity_2_0 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Cache Interceptor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Interceptor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Interceptor</em>' containment reference.
	 * @see #setCacheInterceptor(XmlClassReference)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlEntity_2_0_CacheInterceptor()
	 * @model containment="true"
	 * @generated
	 */
	XmlClassReference getCacheInterceptor();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.XmlEntity_2_0#getCacheInterceptor <em>Cache Interceptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Interceptor</em>' containment reference.
	 * @see #getCacheInterceptor()
	 * @generated
	 */
	void setCacheInterceptor(XmlClassReference value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlEntity_2_0_QueryRedirectors()
	 * @model containment="true"
	 * @generated
	 */
	XmlQueryRedirectors getQueryRedirectors();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.XmlEntity_2_0#getQueryRedirectors <em>Query Redirectors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query Redirectors</em>' containment reference.
	 * @see #getQueryRedirectors()
	 * @generated
	 */
	void setQueryRedirectors(XmlQueryRedirectors value);

} // XmlEntity2_0
