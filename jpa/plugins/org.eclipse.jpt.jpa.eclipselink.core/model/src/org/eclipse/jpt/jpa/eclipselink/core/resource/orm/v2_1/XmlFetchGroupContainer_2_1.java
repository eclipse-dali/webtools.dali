/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License 2.0 which 
 *  accompanies this distribution, and is available at 
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchGroup;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Fetch Group Container 21</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlFetchGroupContainer_2_1#getFetchGroups <em>Fetch Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroupContainer_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlFetchGroupContainer_2_1 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Fetch Groups</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlFetchGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch Groups</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroupContainer_2_1_FetchGroups()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlFetchGroup> getFetchGroups();

} // XmlFetchGroupContainer_2_1
