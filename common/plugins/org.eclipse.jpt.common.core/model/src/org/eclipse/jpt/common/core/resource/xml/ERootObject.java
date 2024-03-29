/*******************************************************************************
 *  Copyright (c) 2009, 2013  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License 2.0 which 
 *  accompanies this distribution, and is available at 
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.common.core.resource.xml;

import org.eclipse.jpt.common.core.utility.TextRange;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Jpa Root EObject</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getVersion <em>Version</em>}</li>
 *   <li>{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getSchemaLocation <em>Schema Location</em>}</li>
 *   <li>{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getImpliedVersion <em>Implied Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface ERootObject extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Schema Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema Location</em>' attribute.
	 * @see #setSchemaLocation(String)
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject_SchemaLocation()
	 * @model required="true"
	 * @generated
	 */
	String getSchemaLocation();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getSchemaLocation <em>Schema Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema Location</em>' attribute.
	 * @see #getSchemaLocation()
	 * @generated
	 */
	void setSchemaLocation(String value);
	
	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject_Namespace()
	 * @model required="true"
	 * @generated
	 */
	String getNamespace();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(String value);

	/**
	 * Returns the value of the '<em><b>Implied Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implied Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implied Version</em>' attribute.
	 * @see #setImpliedVersion(String)
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject_ImpliedVersion()
	 * @model
	 * @generated
	 */
	String getImpliedVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObject#getImpliedVersion <em>Implied Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implied Version</em>' attribute.
	 * @see #getImpliedVersion()
	 * @generated
	 */
	void setImpliedVersion(String value);

	/**
	 * Return either the value of {@link #getVersion()} (if set) or failing that, the version 
	 * implied by the schema location ({@link #getImpliedVersion()})
	 */
	String getDocumentVersion();
	
	/**
	 * Sets the version and updates the schema location to correspond to the version
	 */
	void setDocumentVersion(String versionString);
	
	TextRange getVersionTextRange();
}
