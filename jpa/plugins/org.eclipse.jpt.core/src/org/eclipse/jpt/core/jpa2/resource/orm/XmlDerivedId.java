/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlDerivedId.java,v 1.1 2009/08/31 21:56:19 pfullbright Exp $
 */
package org.eclipse.jpt.core.jpa2.resource.orm;

import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Derived Id</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlDerivedId#getDerivedId <em>Derived Id</em>}</li>
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
	 * Returns the value of the '<em><b>Derived Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Derived Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Derived Id</em>' attribute.
	 * @see #setDerivedId(Boolean)
	 * @see org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package#getXmlDerivedId_DerivedId()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getDerivedId();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.jpa2.resource.orm.XmlDerivedId#getDerivedId <em>Derived Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Derived Id</em>' attribute.
	 * @see #getDerivedId()
	 * @generated
	 */
	void setDerivedId(Boolean value);

} // XmlDerivedId
