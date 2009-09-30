/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlEntity_1_1.java,v 1.1 2009/09/30 23:17:54 pfullbright Exp $
 */
package org.eclipse.jpt.eclipselink.core.resource.orm.v1_1;

import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity 11</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1#getPrimaryKey <em>Primary Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlEntity_1_1()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlEntity_1_1 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Primary Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key</em>' containment reference.
	 * @see #setPrimaryKey(XmlPrimaryKey_1_1)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlEntity_1_1_PrimaryKey()
	 * @model containment="true"
	 * @generated
	 */
	XmlPrimaryKey_1_1 getPrimaryKey();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.XmlEntity_1_1#getPrimaryKey <em>Primary Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key</em>' containment reference.
	 * @see #getPrimaryKey()
	 * @generated
	 */
	void setPrimaryKey(XmlPrimaryKey_1_1 value);

} // XmlEntity_1_1
