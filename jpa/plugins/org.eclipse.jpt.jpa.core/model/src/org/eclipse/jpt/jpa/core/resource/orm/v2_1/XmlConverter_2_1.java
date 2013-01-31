/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm.v2_1;

import org.eclipse.jpt.jpa.core.resource.orm.XmlManagedType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Converter 21</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getAutoApply <em>Auto Apply</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverter_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlConverter_2_1 extends XmlManagedType
{
	/**
	 * Returns the value of the '<em><b>Auto Apply</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Apply</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Apply</em>' attribute.
	 * @see #setAutoApply(Boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlConverter_2_1_AutoApply()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getAutoApply();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1#getAutoApply <em>Auto Apply</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Apply</em>' attribute.
	 * @see #getAutoApply()
	 * @generated
	 */
	void setAutoApply(Boolean value);

} // XmlConverter_2_1
