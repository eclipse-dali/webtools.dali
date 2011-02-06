/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Variable One To One 22</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2#isNonCacheable <em>Non Cacheable</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlVariableOneToOne_2_2()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlVariableOneToOne_2_2 extends XmlPartitioningGroup_2_2
{
	/**
	 * Returns the value of the '<em><b>Non Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Cacheable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Cacheable</em>' attribute.
	 * @see #setNonCacheable(boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlVariableOneToOne_2_2_NonCacheable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isNonCacheable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlVariableOneToOne_2_2#isNonCacheable <em>Non Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Cacheable</em>' attribute.
	 * @see #isNonCacheable()
	 * @generated
	 */
	void setNonCacheable(boolean value);

} // XmlVariableOneToOne_2_2
