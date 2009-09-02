/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlDerivedId.java,v 1.2 2009/09/02 06:25:50 pfullbright Exp $
 */
package org.eclipse.jpt.core.jpa2.resource.orm;

import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Derived Id</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlDerivedId#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlDerivedId()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlDerivedId extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(Boolean)
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlDerivedId_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getId();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlDerivedId#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(Boolean value);

	/**
	 * Return the text range of the derived id part of the XML document
	 */
	TextRange getDerivedIdTextRange();
	
} // XmlDerivedId
