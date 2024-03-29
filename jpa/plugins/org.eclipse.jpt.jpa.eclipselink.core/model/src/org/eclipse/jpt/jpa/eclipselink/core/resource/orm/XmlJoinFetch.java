/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the model object '<em><b>Xml Join Fetch</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 * 
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch#getJoinFetch <em>Join Fetch</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlJoinFetch extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Join Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Fetch</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see #setJoinFetch(XmlJoinFetchType)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlJoinFetch_JoinFetch()
	 * @model
	 * @generated
	 */
	XmlJoinFetchType getJoinFetch();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch#getJoinFetch <em>Join Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Fetch</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType
	 * @see #getJoinFetch()
	 * @generated
	 */
	void setJoinFetch(XmlJoinFetchType value);
	
	/**
	 * Return the {@link TextRange} for the join-fetch element.
	 */
	TextRange getJoinFetchTextRange();
}