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

import org.eclipse.jpt.core.internal.platform.DefaultsContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IColumn</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IColumn#getLength <em>Length</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IColumn#getSpecifiedLength <em>Specified Length</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IColumn#getPrecision <em>Precision</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IColumn#getSpecifiedPrecision <em>Specified Precision</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IColumn#getScale <em>Scale</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IColumn#getSpecifiedScale <em>Specified Scale</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumn()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IColumn extends IAbstractColumn
{
	int DEFAULT_LENGTH = 255;

	int DEFAULT_PRECISION = 0;

	int DEFAULT_SCALE = 0;

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumn_Length()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getLength();

	/**
	 * Returns the value of the '<em><b>Specified Length</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Length</em>' attribute.
	 * @see #setSpecifiedLength(int)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumn_SpecifiedLength()
	 * @model default="-1"
	 * @generated
	 */
	int getSpecifiedLength();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IColumn#getSpecifiedLength <em>Specified Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Length</em>' attribute.
	 * @see #getSpecifiedLength()
	 * @generated
	 */
	void setSpecifiedLength(int value);

	/**
	 * Returns the value of the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Precision</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Precision</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumn_Precision()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getPrecision();

	/**
	 * Returns the value of the '<em><b>Specified Precision</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Precision</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Precision</em>' attribute.
	 * @see #setSpecifiedPrecision(int)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumn_SpecifiedPrecision()
	 * @model default="-1"
	 * @generated
	 */
	int getSpecifiedPrecision();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IColumn#getSpecifiedPrecision <em>Specified Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Precision</em>' attribute.
	 * @see #getSpecifiedPrecision()
	 * @generated
	 */
	void setSpecifiedPrecision(int value);

	/**
	 * Returns the value of the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scale</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumn_Scale()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getScale();

	/**
	 * Returns the value of the '<em><b>Specified Scale</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Scale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Scale</em>' attribute.
	 * @see #setSpecifiedScale(int)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIColumn_SpecifiedScale()
	 * @model default="-1"
	 * @generated
	 */
	int getSpecifiedScale();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IColumn#getSpecifiedScale <em>Specified Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Scale</em>' attribute.
	 * @see #getSpecifiedScale()
	 * @generated
	 */
	void setSpecifiedScale(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	int getDefaultLength();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	int getDefaultPrecision();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	int getDefaultScale();

	void refreshDefaults(DefaultsContext defaultsContext);

	/**
	 * Return whether the column is found on the datasource
	 */
	boolean isResolved();
}
