/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.resource.xml.EFactoryTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmTable;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmTable;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTable;

/**
 * <code>orm.xml</code> table
 */
public class GenericOrmTable
	extends AbstractOrmTable<OrmEntity, OrmTable.ParentAdapter, XmlTable>
	implements OrmTable
{
	public GenericOrmTable(OrmTable.ParentAdapter parentAdapter) {
		super(parentAdapter);
	}


	// ********** XML table **********

	@Override
	protected XmlTable getXmlTable() {
		return this.getXmlEntity().getTable();
	}

	@Override
	protected XmlTable buildXmlTable() {
		XmlTable xmlTable = EFactoryTools.create(
			this.getResourceModelFactory(), 
			OrmPackage.eINSTANCE.getXmlTable(), 
			XmlTable.class);
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

	protected OrmEntity getEntity() {
		return this.parent;
	}
}
