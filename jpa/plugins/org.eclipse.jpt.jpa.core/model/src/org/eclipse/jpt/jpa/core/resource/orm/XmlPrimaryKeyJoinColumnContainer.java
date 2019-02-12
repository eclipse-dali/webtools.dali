/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Primary Key Join Column Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumnContainer#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlPrimaryKeyJoinColumnContainer()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlPrimaryKeyJoinColumnContainer extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlPrimaryKeyJoinColumnContainer_PrimaryKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();

} // XmlPrimaryKeyJoinColumnContainer
