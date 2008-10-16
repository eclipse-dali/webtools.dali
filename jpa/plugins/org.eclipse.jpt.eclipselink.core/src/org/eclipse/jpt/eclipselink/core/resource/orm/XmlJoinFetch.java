/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlJoinFetch.java,v 1.1 2008/10/16 21:17:50 pfullbright Exp $
 */
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.jpt.core.resource.common.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Join Fetch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch#getJoinFetch <em>Join Fetch</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlJoinFetch extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Join Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Fetch</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see #setJoinFetch(XmlJoinFetchType)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch_JoinFetch()
	 * @model
	 * @generated
	 */
	XmlJoinFetchType getJoinFetch();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch#getJoinFetch <em>Join Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Fetch</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see #getJoinFetch()
	 * @generated
	 */
	void setJoinFetch(XmlJoinFetchType value);

} // XmlJoinFetch
