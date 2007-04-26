/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Table</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTable()
 * @model kind="class"
 * @generated
 */
public class XmlTable extends AbstractXmlTable
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlTable() {
		super();
	}

	protected XmlTable(Owner owner) {
		super(owner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_TABLE;
	}

	private XmlEntityInternal entity() {
		return (XmlEntityInternal) eContainer();
	}

	@Override
	protected void makeTableForXmlNonNull() {
		entity().makeTableForXmlNonNull();
	}

	@Override
	protected void makeTableForXmlNull() {
		entity().makeTableForXmlNull();
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_NAME_KEY));
	}
}
