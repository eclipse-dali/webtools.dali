/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.jpt.core.internal.IJpaSourceObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IGenerator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getInitialValue <em>Initial Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getSpecifiedInitialValue <em>Specified Initial Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getDefaultInitialValue <em>Default Initial Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getAllocationSize <em>Allocation Size</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getSpecifiedAllocationSize <em>Specified Allocation Size</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getDefaultAllocationSize <em>Default Allocation Size</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IGenerator extends IJpaSourceObject
{
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
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Value</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator_InitialValue()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getInitialValue();

	/**
	 * Returns the value of the '<em><b>Specified Initial Value</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Initial Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Initial Value</em>' attribute.
	 * @see #setSpecifiedInitialValue(int)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator_SpecifiedInitialValue()
	 * @model default="-1"
	 * @generated
	 */
	int getSpecifiedInitialValue();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getSpecifiedInitialValue <em>Specified Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Initial Value</em>' attribute.
	 * @see #getSpecifiedInitialValue()
	 * @generated
	 */
	void setSpecifiedInitialValue(int value);

	/**
	 * Returns the value of the '<em><b>Default Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Initial Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Initial Value</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator_DefaultInitialValue()
	 * @model changeable="false"
	 * @generated
	 */
	int getDefaultInitialValue();

	/**
	 * Returns the value of the '<em><b>Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allocation Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allocation Size</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator_AllocationSize()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getAllocationSize();

	/**
	 * Returns the value of the '<em><b>Specified Allocation Size</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Allocation Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Allocation Size</em>' attribute.
	 * @see #setSpecifiedAllocationSize(int)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator_SpecifiedAllocationSize()
	 * @model default="-1"
	 * @generated
	 */
	int getSpecifiedAllocationSize();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IGenerator#getSpecifiedAllocationSize <em>Specified Allocation Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Allocation Size</em>' attribute.
	 * @see #getSpecifiedAllocationSize()
	 * @generated
	 */
	void setSpecifiedAllocationSize(int value);

	/**
	 * Returns the value of the '<em><b>Default Allocation Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Allocation Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Allocation Size</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIGenerator_DefaultAllocationSize()
	 * @model changeable="false"
	 * @generated
	 */
	int getDefaultAllocationSize();
} // IGenerator
