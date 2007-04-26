/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.jpt.core.internal.IXmlEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence Unit Metadata</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata#isXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata#getPersistenceUnitDefaults <em>Persistence Unit Defaults</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadata()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface PersistenceUnitMetadata extends IXmlEObject
{
	/**
	 * Returns the value of the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Mapping Metadata Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mapping Metadata Complete</em>' attribute.
	 * @see #setXmlMappingMetadataComplete(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadata_XmlMappingMetadataComplete()
	 * @model volatile="true"
	 * @generated
	 */
	boolean isXmlMappingMetadataComplete();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata#isXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Mapping Metadata Complete</em>' attribute.
	 * @see #isXmlMappingMetadataComplete()
	 * @generated
	 */
	void setXmlMappingMetadataComplete(boolean value);

	/**
	 * Returns the value of the '<em><b>Persistence Unit Defaults</b></em>' reference.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Unit Defaults</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Defaults</em>' reference.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadata_PersistenceUnitDefaults()
	 * @model resolveProxies="false" changeable="false" volatile="true"
	 * @generated
	 */
	PersistenceUnitDefaults getPersistenceUnitDefaults();
} // PersistenceUnitMetadata
