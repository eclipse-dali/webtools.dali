/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Basic Collection Impl</b></em>'.
 * <!-- end-user-doc -->
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves. *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBasicCollectionImpl()
 * @model kind="class"
 * @generated
 */
public class XmlBasicCollectionImpl extends AbstractXmlAttributeMapping implements XmlBasicCollection
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlBasicCollectionImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return EclipseLinkOrmPackage.Literals.XML_BASIC_COLLECTION_IMPL;
	}
	
	public String getMappingKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
} // XmlBasicCollectionImpl
