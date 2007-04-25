/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Secondary Table</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlSecondaryTable()
 * @model kind="class"
 * @generated
 */
public class XmlSecondaryTable extends AbstractXmlTable
	implements ISecondaryTable
{
	private XmlSecondaryTable() {
		super();
	}

	protected XmlSecondaryTable(Owner owner) {
		super(owner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_SECONDARY_TABLE;
	}

	private XmlEntityInternal entity() {
		return (XmlEntityInternal) eContainer();
	}

	@Override
	protected void makeTableForXmlNonNull() {
	//secondaryTables are part of a collection, the secondary-table element will be removed/added
	//when the XmlSecondaryTable is removed/added to the XmlEntity collection
	}

	@Override
	protected void makeTableForXmlNull() {
	//secondaryTables are part of a collection, the secondary-table element will be removed/added
	//when the XmlSecondaryTable is removed/added to the XmlEntity collection
	}
} // XmlSecondaryTable
