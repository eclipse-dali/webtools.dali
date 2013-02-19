/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm.v2_1;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Foreign Key 21</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getConstraintMode <em>Constraint Mode</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getForeignKeyDefinition <em>Foreign Key Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlForeignKey_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlForeignKey_2_1 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlForeignKey_2_1_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlForeignKey_2_1_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Constraint Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint Mode</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1
	 * @see #setConstraintMode(ConstraintMode_2_1)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlForeignKey_2_1_ConstraintMode()
	 * @model
	 * @generated
	 */
	ConstraintMode_2_1 getConstraintMode();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getConstraintMode <em>Constraint Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraint Mode</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.ConstraintMode_2_1
	 * @see #getConstraintMode()
	 * @generated
	 */
	void setConstraintMode(ConstraintMode_2_1 value);

	/**
	 * Returns the value of the '<em><b>Foreign Key Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Foreign Key Definition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Foreign Key Definition</em>' attribute.
	 * @see #setForeignKeyDefinition(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getXmlForeignKey_2_1_ForeignKeyDefinition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getForeignKeyDefinition();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1#getForeignKeyDefinition <em>Foreign Key Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Foreign Key Definition</em>' attribute.
	 * @see #getForeignKeyDefinition()
	 * @generated
	 */
	void setForeignKeyDefinition(String value);

} // XmlForeignKey_2_1
