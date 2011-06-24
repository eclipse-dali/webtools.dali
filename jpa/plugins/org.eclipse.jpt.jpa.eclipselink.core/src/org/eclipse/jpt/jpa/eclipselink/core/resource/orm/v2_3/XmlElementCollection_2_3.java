/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlElementCollection_2_3.java,v 1.1 2011/06/24 15:49:56 kmoore Exp $
 */
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3;

import org.eclipse.jpt.jpa.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Element Collection 23</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3#getCompositeMember <em>Composite Member</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlElementCollection_2_3()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlElementCollection_2_3 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Composite Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Member</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Member</em>' attribute.
	 * @see #setCompositeMember(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlElementCollection_2_3_CompositeMember()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getCompositeMember();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlElementCollection_2_3#getCompositeMember <em>Composite Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite Member</em>' attribute.
	 * @see #getCompositeMember()
	 * @generated
	 */
	void setCompositeMember(String value);

} // XmlElementCollection_2_3
