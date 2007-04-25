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

import org.eclipse.jpt.core.internal.IXmlEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence Unit Metadata For Xml</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml#isXmlMappingMetadataCompleteForXml <em>Xml Mapping Metadata Complete For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml#getPersistenceUnitDefaultsForXml <em>Persistence Unit Defaults For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataForXml()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface PersistenceUnitMetadataForXml extends IXmlEObject
{
	/**
	 * Returns the value of the '<em><b>Xml Mapping Metadata Complete For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Mapping Metadata Complete For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mapping Metadata Complete For Xml</em>' attribute.
	 * @see #setXmlMappingMetadataCompleteForXml(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataForXml_XmlMappingMetadataCompleteForXml()
	 * @model volatile="true"
	 * @generated
	 */
	boolean isXmlMappingMetadataCompleteForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml#isXmlMappingMetadataCompleteForXml <em>Xml Mapping Metadata Complete For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Mapping Metadata Complete For Xml</em>' attribute.
	 * @see #isXmlMappingMetadataCompleteForXml()
	 * @generated
	 */
	void setXmlMappingMetadataCompleteForXml(boolean value);

	/**
	 * Returns the value of the '<em><b>Persistence Unit Defaults For Xml</b></em>' reference.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Unit Defaults For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Defaults For Xml</em>' reference.
	 * @see #setPersistenceUnitDefaultsForXml(PersistenceUnitDefaultsForXml)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataForXml_PersistenceUnitDefaultsForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated
	 */
	PersistenceUnitDefaultsForXml getPersistenceUnitDefaultsForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml#getPersistenceUnitDefaultsForXml <em>Persistence Unit Defaults For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Unit Defaults For Xml</em>' reference.
	 * @see #getPersistenceUnitDefaultsForXml()
	 * @generated
	 */
	void setPersistenceUnitDefaultsForXml(PersistenceUnitDefaultsForXml value);
} // PersistenceUnitMetadataForXml
