/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm.v2_2;

import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity 22</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getAdditionalCriteria <em>Additional Criteria</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntity_2_2()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlEntity_2_2 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade On Delete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade On Delete</em>' attribute.
	 * @see #setCascadeOnDelete(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntity_2_2_CascadeOnDelete()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getCascadeOnDelete();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade On Delete</em>' attribute.
	 * @see #getCascadeOnDelete()
	 * @generated
	 */
	void setCascadeOnDelete(Boolean value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' containment reference.
	 * @see #setIndex(XmlIndex_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntity_2_2_Index()
	 * @model containment="true"
	 * @generated
	 */
	XmlIndex_2_2 getIndex();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getIndex <em>Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' containment reference.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(XmlIndex_2_2 value);

	/**
	 * Returns the value of the '<em><b>Additional Criteria</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Criteria</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Criteria</em>' containment reference.
	 * @see #setAdditionalCriteria(XmlAdditionalCriteria_2_2)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlEntity_2_2_AdditionalCriteria()
	 * @model containment="true"
	 * @generated
	 */
	XmlAdditionalCriteria_2_2 getAdditionalCriteria();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_2.XmlEntity_2_2#getAdditionalCriteria <em>Additional Criteria</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Criteria</em>' containment reference.
	 * @see #getAdditionalCriteria()
	 * @generated
	 */
	void setAdditionalCriteria(XmlAdditionalCriteria_2_2 value);

} // XmlEntity_2_2
