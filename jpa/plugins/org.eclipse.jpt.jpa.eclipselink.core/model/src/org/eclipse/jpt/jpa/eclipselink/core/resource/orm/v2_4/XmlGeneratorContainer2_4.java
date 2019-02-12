/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4;

import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUuidGenerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Generator Container2 4</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4#getUuidGenerator <em>Uuid Generator</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlGeneratorContainer2_4()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlGeneratorContainer2_4 extends XmlGeneratorContainer
{
	/**
	 * Returns the value of the '<em><b>Uuid Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uuid Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uuid Generator</em>' containment reference.
	 * @see #setUuidGenerator(XmlUuidGenerator)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlGeneratorContainer2_4_UuidGenerator()
	 * @model containment="true"
	 * @generated
	 */
	XmlUuidGenerator getUuidGenerator();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4#getUuidGenerator <em>Uuid Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uuid Generator</em>' containment reference.
	 * @see #getUuidGenerator()
	 * @generated
	 */
	void setUuidGenerator(XmlUuidGenerator value);

} // XmlGeneratorContainer2_4
