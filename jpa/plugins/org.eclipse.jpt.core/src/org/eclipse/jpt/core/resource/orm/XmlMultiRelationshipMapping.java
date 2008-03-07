/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.jpt.core.TextRange;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Relationship Mapping</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping#getMappedBy <em>Mapped By</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping#getOrderBy <em>Order By</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping#getMapKey <em>Map Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlMultiRelationshipMapping extends XmlRelationshipMapping
{
	/**
	 * Returns the value of the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order By</em>' attribute.
	 * @see #setOrderBy(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_OrderBy()
	 * @model dataType="org.eclipse.jpt.core.resource.orm.OrderBy"
	 * @generated
	 */
	String getOrderBy();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping#getOrderBy <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order By</em>' attribute.
	 * @see #getOrderBy()
	 * @generated
	 */
	void setOrderBy(String value);

	/**
	 * Returns the value of the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key</em>' containment reference.
	 * @see #setMapKey(MapKey)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_MapKey()
	 * @model containment="true"
	 * @generated
	 */
	MapKey getMapKey();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping#getMapKey <em>Map Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key</em>' containment reference.
	 * @see #getMapKey()
	 * @generated
	 */
	void setMapKey(MapKey value);

	/**
	 * Returns the value of the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped By</em>' attribute.
	 * @see #setMappedBy(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMultiRelationshipMapping_MappedBy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getMappedBy();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping#getMappedBy <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped By</em>' attribute.
	 * @see #getMappedBy()
	 * @generated
	 */
	void setMappedBy(String value);

	TextRange mappedByTextRange();
} // MultiRelationshipMapping
