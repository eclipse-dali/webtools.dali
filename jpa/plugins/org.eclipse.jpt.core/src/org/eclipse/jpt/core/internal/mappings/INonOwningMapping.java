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

import java.util.Iterator;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.ITextRange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>INon Owning Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.INonOwningMapping#getMappedBy <em>Mapped By</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINonOwningMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface INonOwningMapping extends IRelationshipMapping
{
	/**
	 * Returns the value of the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped By</em>' attribute.
	 * @see #setMappedBy(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getINonOwningMapping_MappedBy()
	 * @model
	 * @generated
	 */
	String getMappedBy();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.INonOwningMapping#getMappedBy <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped By</em>' attribute.
	 * @see #getMappedBy()
	 * @generated
	 */
	void setMappedBy(String value);

	Iterator<String> candidateMappedByAttributeNames();
	
	boolean mappedByIsValid(IAttributeMapping mappedByMapping);
	
	ITextRange getMappedByTextRange();
}