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

import org.eclipse.jpt.core.internal.AccessType;
import org.eclipse.jpt.core.internal.IXmlEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence Unit Defaults For Xml</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getSchemaForXml <em>Schema For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getCatalogForXml <em>Catalog For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getAccessForXml <em>Access For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#isCascadePersistForXml <em>Cascade Persist For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface PersistenceUnitDefaultsForXml extends IXmlEObject
{
	/**
	 * Returns the value of the '<em><b>Schema For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema For Xml</em>' attribute.
	 * @see #setSchemaForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml_SchemaForXml()
	 * @model volatile="true"
	 * @generated
	 */
	String getSchemaForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getSchemaForXml <em>Schema For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema For Xml</em>' attribute.
	 * @see #getSchemaForXml()
	 * @generated
	 */
	void setSchemaForXml(String value);

	/**
	 * Returns the value of the '<em><b>Catalog For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalog For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalog For Xml</em>' attribute.
	 * @see #setCatalogForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml_CatalogForXml()
	 * @model volatile="true"
	 * @generated
	 */
	String getCatalogForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getCatalogForXml <em>Catalog For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catalog For Xml</em>' attribute.
	 * @see #getCatalogForXml()
	 * @generated
	 */
	void setCatalogForXml(String value);

	/**
	 * Returns the value of the '<em><b>Access For Xml</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @see #setAccessForXml(AccessType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml_AccessForXml()
	 * @model volatile="true"
	 * @generated
	 */
	AccessType getAccessForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#getAccessForXml <em>Access For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @see #getAccessForXml()
	 * @generated
	 */
	void setAccessForXml(AccessType value);

	/**
	 * Returns the value of the '<em><b>Cascade Persist For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Persist For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Persist For Xml</em>' attribute.
	 * @see #setCascadePersistForXml(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml_CascadePersistForXml()
	 * @model volatile="true"
	 * @generated
	 */
	boolean isCascadePersistForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml#isCascadePersistForXml <em>Cascade Persist For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist For Xml</em>' attribute.
	 * @see #isCascadePersistForXml()
	 * @generated
	 */
	void setCascadePersistForXml(boolean value);
} // PersistenceUnitDefaultsForXml
