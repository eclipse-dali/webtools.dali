/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlPrivateOwned.java,v 1.1 2008/10/16 21:17:50 pfullbright Exp $
 */
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.jpt.core.resource.common.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Private Owned</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned#isPrivateOwned <em>Private Owned</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrivateOwned()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlPrivateOwned extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Private Owned</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Private Owned</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Private Owned</em>' attribute.
	 * @see #setPrivateOwned(boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlPrivateOwned_PrivateOwned()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isPrivateOwned();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned#isPrivateOwned <em>Private Owned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Private Owned</em>' attribute.
	 * @see #isPrivateOwned()
	 * @generated
	 */
	void setPrivateOwned(boolean value);

} // XmlPrivateOwned
