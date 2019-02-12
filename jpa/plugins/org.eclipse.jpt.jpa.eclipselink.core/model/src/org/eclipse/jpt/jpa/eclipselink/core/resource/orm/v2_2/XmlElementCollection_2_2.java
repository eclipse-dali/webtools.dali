/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Element Collection 22</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#isNoncacheable <em>Noncacheable</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlElementCollection_2_2()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlElementCollection_2_2 extends XmlPartitioningGroup_2_2
{
	/**
	 * Returns the value of the '<em><b>Cascade On Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade On Delete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade On Delete</em>' attribute.
	 * @see #setCascadeOnDelete(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlElementCollection_2_2_CascadeOnDelete()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getCascadeOnDelete();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#getCascadeOnDelete <em>Cascade On Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade On Delete</em>' attribute.
	 * @see #getCascadeOnDelete()
	 * @generated
	 */
	void setCascadeOnDelete(Boolean value);

	/**
	 * Returns the value of the '<em><b>Noncacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Noncacheable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Noncacheable</em>' attribute.
	 * @see #setNoncacheable(boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlElementCollection_2_2_Noncacheable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isNoncacheable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlElementCollection_2_2#isNoncacheable <em>Noncacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Noncacheable</em>' attribute.
	 * @see #isNoncacheable()
	 * @generated
	 */
	void setNoncacheable(boolean value);

} // XmlElementCollection_2_2
