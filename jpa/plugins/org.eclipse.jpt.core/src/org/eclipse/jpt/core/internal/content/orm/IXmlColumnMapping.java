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

import org.eclipse.jpt.core.internal.mappings.IColumnMapping;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IXml Column Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping#getColumnForXml <em>Column For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIXmlColumnMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IXmlColumnMapping extends IColumnMapping
{
	/**
	 * Returns the value of the '<em><b>Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column For Xml</em>' reference.
	 * @see #setColumnForXml(XmlColumn)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIXmlColumnMapping_ColumnForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated
	 */
	XmlColumn getColumnForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping#getColumnForXml <em>Column For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column For Xml</em>' reference.
	 * @see #getColumnForXml()
	 * @generated
	 */
	void setColumnForXml(XmlColumn value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void makeColumnForXmlNonNull();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void makeColumnForXmlNull();
}
