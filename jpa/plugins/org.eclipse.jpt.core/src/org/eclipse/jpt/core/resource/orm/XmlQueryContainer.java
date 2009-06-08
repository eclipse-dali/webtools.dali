/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.emf.common.util.EList;

import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Queries Holder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlQueryContainer#getNamedQueries <em>Named Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlQueryContainer#getNamedNativeQueries <em>Named Native Queries</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlQueryContainer()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlQueryContainer extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Named Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlNamedQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlQueryContainer_NamedQueries()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlNamedQuery> getNamedQueries();

	/**
	 * Returns the value of the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Native Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Native Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlQueryContainer_NamedNativeQueries()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlNamedNativeQuery> getNamedNativeQueries();

} // XmlQueriesHolder
