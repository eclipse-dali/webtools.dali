/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Basic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getFetch <em>Fetch</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getOptional <em>Optional</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Basic#isLob <em>Lob</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getTemporal <em>Temporal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getEnumerated <em>Enumerated</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasic()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface Basic extends AttributeMapping, ColumnMapping
{
	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The default value is <code>"LAZY"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.resource.orm.FetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.FetchType
	 * @see #setFetch(FetchType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasic_Fetch()
	 * @model default="LAZY"
	 * @generated
	 */
	FetchType getFetch();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.FetchType
	 * @see #getFetch()
	 * @generated
	 */
	void setFetch(FetchType value);

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(Boolean)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasic_Optional()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #getOptional()
	 * @generated
	 */
	void setOptional(Boolean value);

	/**
	 * Returns the value of the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lob</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lob</em>' attribute.
	 * @see #setLob(boolean)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasic_Lob()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isLob();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#isLob <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lob</em>' attribute.
	 * @see #isLob()
	 * @generated
	 */
	void setLob(boolean value);

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasic_Temporal()
	 * @model
	 * @generated
	 */
	TemporalType getTemporal();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	void setTemporal(TemporalType value);

	/**
	 * Returns the value of the '<em><b>Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EnumType
	 * @see #setEnumerated(EnumType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getBasic_Enumerated()
	 * @model
	 * @generated
	 */
	EnumType getEnumerated();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Basic#getEnumerated <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EnumType
	 * @see #getEnumerated()
	 * @generated
	 */
	void setEnumerated(EnumType value);

} // Basic
