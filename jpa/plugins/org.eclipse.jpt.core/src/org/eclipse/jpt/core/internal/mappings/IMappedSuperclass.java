/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.jpt.core.internal.ITypeMapping;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapped Superclass</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IMappedSuperclass#getIdClass <em>Id Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMappedSuperclass()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IMappedSuperclass extends ITypeMapping
{
	/**
	 * Returns the value of the '<em><b>Id Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Class</em>' attribute.
	 * @see #setIdClass(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMappedSuperclass_IdClass()
	 * @model
	 * @generated
	 */
	String getIdClass();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IMappedSuperclass#getIdClass <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class</em>' attribute.
	 * @see #getIdClass()
	 * @generated
	 */
	void setIdClass(String value);
} // MappedSuperclass
