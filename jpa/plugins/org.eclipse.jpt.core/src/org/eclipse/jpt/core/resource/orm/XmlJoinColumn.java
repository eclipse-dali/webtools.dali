/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Join Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn#getReferencedColumnName <em>Referenced Column Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getJoinColumn()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlJoinColumn extends XmlAbstractColumn
{
	/**
	 * Returns the value of the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Column Name</em>' attribute.
	 * @see #setReferencedColumnName(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getJoinColumn_ReferencedColumnName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getReferencedColumnName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn#getReferencedColumnName <em>Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenced Column Name</em>' attribute.
	 * @see #getReferencedColumnName()
	 * @generated
	 */
	void setReferencedColumnName(String value);

} // JoinColumn
