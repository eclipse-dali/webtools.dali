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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Join Table</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlJoinTable#getJoinColumns <em>Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlJoinTable#getInverseJoinColumns <em>Inverse Join Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTable()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlJoinTable extends XmlBaseTable
{
	/**
	 * Returns the value of the '<em><b>Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTable_JoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlJoinColumn> getJoinColumns();

	/**
	 * Returns the value of the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTable_InverseJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlJoinColumn> getInverseJoinColumns();

	boolean isSpecified();
} // JoinTable
