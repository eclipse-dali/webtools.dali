/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Base Discriminator Column</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlBaseDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlBaseDiscriminatorColumn#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlBaseDiscriminatorColumn()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlBaseDiscriminatorColumn extends XmlNamedColumn
{
	/**
	 * Returns the value of the '<em><b>Discriminator Type</b></em>' attribute.
	 * The default value is <code>"STRING"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.DiscriminatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.DiscriminatorType
	 * @see #setDiscriminatorType(DiscriminatorType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlBaseDiscriminatorColumn_DiscriminatorType()
	 * @model default="STRING"
	 * @generated
	 */
	DiscriminatorType getDiscriminatorType();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlBaseDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.DiscriminatorType
	 * @see #getDiscriminatorType()
	 * @generated
	 */
	void setDiscriminatorType(DiscriminatorType value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(Integer)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlBaseDiscriminatorColumn_Length()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	Integer getLength();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlBaseDiscriminatorColumn#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(Integer value);

} // XmlBaseDiscriminatorColumn
