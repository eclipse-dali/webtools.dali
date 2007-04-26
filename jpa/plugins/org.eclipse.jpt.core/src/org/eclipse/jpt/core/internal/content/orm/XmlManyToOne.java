/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.mappings.IManyToOne;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Many To One</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlManyToOne()
 * @model kind="class"
 * @generated
 */
public class XmlManyToOne extends XmlSingleRelationshipMapping
	implements IManyToOne
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlManyToOne() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_MANY_TO_ONE;
	}

	@Override
	public int xmlSequence() {
		return 3;
	}

	public String getKey() {
		return IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlManyToOneMapping(this);
	}
}
