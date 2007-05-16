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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity For Xml</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getTableForXml <em>Table For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getDiscriminatorColumnForXml <em>Discriminator Column For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getIdClassForXml <em>Id Class For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getInheritanceForXml <em>Inheritance For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlEntityForXml extends EObject
{
	/**
	 * Returns the value of the '<em><b>Table For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table For Xml</em>' reference.
	 * @see #setTableForXml(XmlTable)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml_TableForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated
	 */
	XmlTable getTableForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getTableForXml <em>Table For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table For Xml</em>' reference.
	 * @see #getTableForXml()
	 * @generated
	 */
	void setTableForXml(XmlTable value);

	/**
	 * Returns the value of the '<em><b>Discriminator Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Column For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Column For Xml</em>' reference.
	 * @see #setDiscriminatorColumnForXml(XmlDiscriminatorColumn)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml_DiscriminatorColumnForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated
	 */
	XmlDiscriminatorColumn getDiscriminatorColumnForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getDiscriminatorColumnForXml <em>Discriminator Column For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Column For Xml</em>' reference.
	 * @see #getDiscriminatorColumnForXml()
	 * @generated
	 */
	void setDiscriminatorColumnForXml(XmlDiscriminatorColumn value);

	/**
	 * Returns the value of the '<em><b>Id Class For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Class For Xml</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Class For Xml</em>' containment reference.
	 * @see #setIdClassForXml(XmlIdClass)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml_IdClassForXml()
	 * @model containment="true"
	 * @generated
	 */
	XmlIdClass getIdClassForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getIdClassForXml <em>Id Class For Xml</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class For Xml</em>' containment reference.
	 * @see #getIdClassForXml()
	 * @generated
	 */
	void setIdClassForXml(XmlIdClass value);

	/**
	 * Returns the value of the '<em><b>Inheritance For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inheritance For Xml</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inheritance For Xml</em>' containment reference.
	 * @see #setInheritanceForXml(XmlInheritance)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlEntityForXml_InheritanceForXml()
	 * @model containment="true"
	 * @generated
	 */
	XmlInheritance getInheritanceForXml();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml#getInheritanceForXml <em>Inheritance For Xml</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inheritance For Xml</em>' containment reference.
	 * @see #getInheritanceForXml()
	 * @generated
	 */
	void setInheritanceForXml(XmlInheritance value);
} // XmlEntityForXml
