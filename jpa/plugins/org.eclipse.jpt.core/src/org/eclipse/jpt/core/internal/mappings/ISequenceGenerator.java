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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ISequence Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getSequenceName <em>Sequence Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getSpecifiedSequenceName <em>Specified Sequence Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getDefaultSequenceName <em>Default Sequence Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISequenceGenerator()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface ISequenceGenerator extends IGenerator
{
	/**
	 * Returns the value of the '<em><b>Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISequenceGenerator_SequenceName()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getSequenceName();

	/**
	 * Returns the value of the '<em><b>Specified Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Sequence Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Sequence Name</em>' attribute.
	 * @see #setSpecifiedSequenceName(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISequenceGenerator_SpecifiedSequenceName()
	 * @model
	 * @generated
	 */
	String getSpecifiedSequenceName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator#getSpecifiedSequenceName <em>Specified Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Sequence Name</em>' attribute.
	 * @see #getSpecifiedSequenceName()
	 * @generated
	 */
	void setSpecifiedSequenceName(String value);

	/**
	 * Returns the value of the '<em><b>Default Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Sequence Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Sequence Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getISequenceGenerator_DefaultSequenceName()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultSequenceName();
} // ISequenceGenerator
