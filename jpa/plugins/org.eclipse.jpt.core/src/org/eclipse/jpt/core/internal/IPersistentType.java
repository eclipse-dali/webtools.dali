/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IPersistent Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.IPersistentType#getMappingKey <em>Mapping Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIPersistentType()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IPersistentType extends IJpaContentNode
{
	/**
	 * Returns the value of the '<em><b>Mapping Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Key</em>' attribute.
	 * @see #setMappingKey(String)
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIPersistentType_MappingKey()
	 * @model required="true"
	 * @generated
	 */
	String getMappingKey();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.IPersistentType#getMappingKey <em>Mapping Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping Key</em>' attribute.
	 * @see #getMappingKey()
	 * @generated
	 */
	void setMappingKey(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	ITypeMapping getMapping();

	/**
	 * Return the parent IPersistentType from the inheritance hierarchy.
	 * If the java inheritance parent is not a IPersistentType then continue
	 * up the hierarchy.  Return null if this persistentType is the root
	 * persistent type. 
	 * @model
	 * @generated
	 */
	IPersistentType parentPersistentType();

	/**
	 * Return a read-only iterator of the contained IPersistentAttributes
	 */
	Iterator<? extends IPersistentAttribute> attributes();

	Iterator<String> attributeNames();

	/**
	 * Return a read-only iterator of the all the IPersistentAttributes
	 * in the hierarchy
	 */
	Iterator<IPersistentAttribute> allAttributes();

	Iterator<String> allAttributeNames();

	/**
	 * Return the attribute named <code>attributeName</code> if
	 * it exists locally on this type
	 */
	IPersistentAttribute attributeNamed(String attributeName);

	/**
	 * Resolve and return the attribute named <code>attributeName</code> if it
	 * is distinct and exists within the context of this type
	 */
	IPersistentAttribute resolveAttribute(String attributeName);

	Iterator<IPersistentType> inheritanceHierarchy();

	/**
	 * Return the corresponding JDT IType, if it resolves to a single IType
	 */
	IType findJdtType();
}
