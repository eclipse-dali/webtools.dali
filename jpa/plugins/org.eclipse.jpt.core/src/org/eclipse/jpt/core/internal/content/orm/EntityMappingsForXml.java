/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Mappings For Xml</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml#getPersistenceUnitMetadataForXml <em>Persistence Unit Metadata For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml#getPackageForXml <em>Package For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsForXml()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface EntityMappingsForXml extends EObject
{
	/**
	 * Returns the value of the '<em><b>Persistence Unit Metadata For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Unit Metadata For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Metadata For Xml</em>' reference.
	 * @see #setPersistenceUnitMetadataForXml(PersistenceUnitMetadataForXml)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsForXml_PersistenceUnitMetadataForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated
	 */
	PersistenceUnitMetadataForXml getPersistenceUnitMetadataForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml#getPersistenceUnitMetadataForXml <em>Persistence Unit Metadata For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Unit Metadata For Xml</em>' reference.
	 * @see #getPersistenceUnitMetadataForXml()
	 * @generated
	 */
	void setPersistenceUnitMetadataForXml(PersistenceUnitMetadataForXml value);

	/**
	 * Returns the value of the '<em><b>Package For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package For Xml</em>' attribute.
	 * @see #setPackageForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsForXml_PackageForXml()
	 * @model volatile="true"
	 * @generated
	 */
	String getPackageForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml#getPackageForXml <em>Package For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package For Xml</em>' attribute.
	 * @see #getPackageForXml()
	 * @generated
	 */
	void setPackageForXml(String value);
} // EntityMappingsForXml
