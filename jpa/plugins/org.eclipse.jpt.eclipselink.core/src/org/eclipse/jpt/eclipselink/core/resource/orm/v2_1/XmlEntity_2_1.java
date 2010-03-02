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

import org.eclipse.jpt.core.resource.orm.XmlClassReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity 21</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1#getClassExtractor <em>Class Extractor</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEntity_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlEntity_2_1 extends XmlFetchGroupContainer_2_1
{
	/**
	 * Returns the value of the '<em><b>Class Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Extractor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Extractor</em>' containment reference.
	 * @see #setClassExtractor(XmlClassReference)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlEntity_2_1_ClassExtractor()
	 * @model containment="true"
	 * @generated
	 */
	XmlClassReference getClassExtractor();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlEntity_2_1#getClassExtractor <em>Class Extractor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Extractor</em>' containment reference.
	 * @see #getClassExtractor()
	 * @generated
	 */
	void setClassExtractor(XmlClassReference value);

} // XmlEntity_2_1
