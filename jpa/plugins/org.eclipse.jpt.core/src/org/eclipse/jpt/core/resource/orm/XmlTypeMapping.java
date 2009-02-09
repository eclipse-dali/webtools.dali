/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Type Mapping</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getMetadataComplete <em>Metadata Complete</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTypeMapping()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlTypeMapping extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTypeMapping_ClassName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The default value is <code>"PROPERTY"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTypeMapping_Access()
	 * @model default="PROPERTY"
	 * @generated
	 */
	AccessType getAccess();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #getAccess()
	 * @generated
	 */
	void setAccess(AccessType value);

	/**
	 * Returns the value of the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metadata Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata Complete</em>' attribute.
	 * @see #setMetadataComplete(Boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTypeMapping_MetadataComplete()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getMetadataComplete();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getMetadataComplete <em>Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metadata Complete</em>' attribute.
	 * @see #getMetadataComplete()
	 * @generated
	 */
	void setMetadataComplete(Boolean value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTypeMapping_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference.
	 * @see #setAttributes(Attributes)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlTypeMapping_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	Attributes getAttributes();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlTypeMapping#getAttributes <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attributes</em>' containment reference.
	 * @see #getAttributes()
	 * @generated
	 */
	void setAttributes(Attributes value);
	
	TextRange getClassTextRange();

	TextRange getAttributesTextRange();

	//TODO not happy with this, or the corresponding one in XmlAttributeMapping
	String getMappingKey();
} // XmlTypeMapping
