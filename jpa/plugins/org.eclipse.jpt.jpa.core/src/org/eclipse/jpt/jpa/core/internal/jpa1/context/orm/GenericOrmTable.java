/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmTable;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTable;

/**
 * <code>orm.xml</code> table
 */
public class GenericOrmTable
	extends AbstractOrmTable<XmlTable>
{
	public GenericOrmTable(OrmEntity parent, Owner owner) {
		super(parent, owner);
	}


	// ********** XML table **********

	@Override
	protected XmlTable getXmlTable() {
		return this.getXmlEntity().getTable();
	}

	@Override
	protected XmlTable buildXmlTable() {
		XmlTable xmlTable = OrmFactory.eINSTANCE.createXmlTable();
		this.getXmlEntity().setTable(xmlTable);
		return xmlTable;
	}

	@Override
	protected void removeXmlTable() {
		this.getXmlEntity().setTable(null);
	}

	protected XmlEntity getXmlEntity() {
		return this.getEntity().getXmlTypeMapping();
	}


	// ********** defaults **********

	@Override
	protected String buildDefaultName() {
		return this.getEntity().getDefaultTableName();
	}

	@Override
	protected String buildDefaultSchema() {
		return this.getEntity().getDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getEntity().getDefaultCatalog();
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.connectionProfileIsActive();
	}


	// ********** misc **********

	@Override
	public OrmEntity getParent() {
		return (OrmEntity) super.getParent();
	}

	protected OrmEntity getEntity() {
		return this.getParent();
	}
}
