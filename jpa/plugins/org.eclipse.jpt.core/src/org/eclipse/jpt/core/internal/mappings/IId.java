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

import org.eclipse.jpt.core.internal.IAttributeMapping;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Id</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IId#getColumn <em>Column</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IId#getGeneratedValue <em>Generated Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IId#getTemporal <em>Temporal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IId#getTableGenerator <em>Table Generator</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IId#getSequenceGenerator <em>Sequence Generator</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIId()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IId extends IAttributeMapping, IColumnMapping
{
	/**
	 * Returns the value of the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIId_Column()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	IColumn getColumn();

	/**
	 * Returns the value of the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generated Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generated Value</em>' containment reference.
	 * @see #setGeneratedValue(IGeneratedValue)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIId_GeneratedValue()
	 * @model containment="true"
	 * @generated
	 */
	IGeneratedValue getGeneratedValue();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IId#getGeneratedValue <em>Generated Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generated Value</em>' containment reference.
	 * @see #getGeneratedValue()
	 * @generated
	 */
	void setGeneratedValue(IGeneratedValue value);

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIId_Temporal()
	 * @model
	 * @generated
	 */
	TemporalType getTemporal();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IId#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	void setTemporal(TemporalType value);

	/**
	 * Returns the value of the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generator</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generator</em>' containment reference.
	 * @see #setTableGenerator(ITableGenerator)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIId_TableGenerator()
	 * @model containment="true"
	 * @generated
	 */
	ITableGenerator getTableGenerator();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IId#getTableGenerator <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Generator</em>' containment reference.
	 * @see #getTableGenerator()
	 * @generated
	 */
	void setTableGenerator(ITableGenerator value);

	/**
	 * Returns the value of the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generator</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #setSequenceGenerator(ISequenceGenerator)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIId_SequenceGenerator()
	 * @model containment="true"
	 * @generated
	 */
	ISequenceGenerator getSequenceGenerator();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IId#getSequenceGenerator <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	void setSequenceGenerator(ISequenceGenerator value);

	IGeneratedValue createGeneratedValue();

	ITableGenerator createTableGenerator();

	ISequenceGenerator createSequenceGenerator();
} // Id
