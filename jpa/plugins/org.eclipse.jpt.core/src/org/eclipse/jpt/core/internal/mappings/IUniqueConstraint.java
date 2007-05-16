/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.IJpaSourceObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IUnique Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IUniqueConstraint#getColumnNames <em>Column Names</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIUniqueConstraint()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IUniqueConstraint extends IJpaSourceObject
{
	/**
	 * Returns the value of the '<em><b>Column Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Names</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Names</em>' attribute list.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIUniqueConstraint_ColumnNames()
	 * @model type="java.lang.String"
	 * @generated
	 */
	EList<String> getColumnNames();


	/**
	 * All containers must implement this interface.
	 */
	interface Owner
	{
		Iterator<String> candidateUniqueConstraintColumnNames();
	}
}
