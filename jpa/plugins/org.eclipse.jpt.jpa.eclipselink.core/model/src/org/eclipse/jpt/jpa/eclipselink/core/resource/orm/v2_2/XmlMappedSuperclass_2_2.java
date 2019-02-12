/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAdditionalCriteria;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Mapped Superclass 22</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2#getAdditionalCriteria <em>Additional Criteria</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlMappedSuperclass_2_2()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlMappedSuperclass_2_2 extends XmlPartitioningGroup_2_2
{
	/**
	 * Returns the value of the '<em><b>Additional Criteria</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Criteria</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Criteria</em>' containment reference.
	 * @see #setAdditionalCriteria(XmlAdditionalCriteria)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlMappedSuperclass_2_2_AdditionalCriteria()
	 * @model containment="true"
	 * @generated
	 */
	XmlAdditionalCriteria getAdditionalCriteria();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlMappedSuperclass_2_2#getAdditionalCriteria <em>Additional Criteria</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Criteria</em>' containment reference.
	 * @see #getAdditionalCriteria()
	 * @generated
	 */
	void setAdditionalCriteria(XmlAdditionalCriteria value);

} // XmlMappedSuperclass_2_2
