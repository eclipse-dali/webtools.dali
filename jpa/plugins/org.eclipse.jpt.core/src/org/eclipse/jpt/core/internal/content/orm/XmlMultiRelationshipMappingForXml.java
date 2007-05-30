/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Multi Relationship Mapping For Xml</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml#getJoinTableForXml <em>Join Table For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml#getMapKeyForXml <em>Map Key For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMappingForXml()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlMultiRelationshipMappingForXml extends EObject
{
	/**
	 * Returns the value of the '<em><b>Join Table For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Table For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Table For Xml</em>' reference.
	 * @see #setJoinTableForXml(XmlJoinTable)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMappingForXml_JoinTableForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated
	 */
	XmlJoinTable getJoinTableForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml#getJoinTableForXml <em>Join Table For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Table For Xml</em>' reference.
	 * @see #getJoinTableForXml()
	 * @generated
	 */
	void setJoinTableForXml(XmlJoinTable value);

	/**
	 * Returns the value of the '<em><b>Map Key For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key For Xml</em>' containment reference.
	 * @see #setMapKeyForXml(XmlMapKey)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMappingForXml_MapKeyForXml()
	 * @model containment="true"
	 * @generated
	 */
	XmlMapKey getMapKeyForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml#getMapKeyForXml <em>Map Key For Xml</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key For Xml</em>' containment reference.
	 * @see #getMapKeyForXml()
	 * @generated
	 */
	void setMapKeyForXml(XmlMapKey value);
} // XmlMultiRelationshipMappingForXml
