/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlEntity#getSecondaryTables <em>Secondary Tables</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlEntity#getSpecifiedSecondaryTables <em>Specified Secondary Tables</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlEntity#getDefaultSecondaryTables <em>Default Secondary Tables</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntity()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlEntity extends IEntity
{
	/**
	 * Returns the value of the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntity_SecondaryTables()
	 * @model type="org.eclipse.jpt.core.internal.mappings.ISecondaryTable" containment="true" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList<ISecondaryTable> getSecondaryTables();

	/**
	 * Returns the value of the '<em><b>Specified Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntity_SpecifiedSecondaryTables()
	 * @model type="org.eclipse.jpt.core.internal.mappings.ISecondaryTable" containment="true"
	 * @generated
	 */
	EList<ISecondaryTable> getSpecifiedSecondaryTables();

	/**
	 * Returns the value of the '<em><b>Default Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntity_DefaultSecondaryTables()
	 * @model type="org.eclipse.jpt.core.internal.mappings.ISecondaryTable" containment="true"
	 * @generated
	 */
	EList<ISecondaryTable> getDefaultSecondaryTables();
} // XmlEntity
