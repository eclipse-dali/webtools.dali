/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.vendor;

import java.util.ArrayList;

import org.eclipse.datatools.modelbase.sql.schema.Database;

class HSQLDB
	extends AbstractVendor
{
	// singleton
	private static final Vendor INSTANCE = new HSQLDB();

	/**
	 * Return the singleton.
	 */
	static Vendor instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private HSQLDB() {
		super();
	}

	@Override
	public String getDTPVendorName() {
		return "HSQLDB"; //$NON-NLS-1$
	}

	@Override
	CatalogStrategy getCatalogStrategy() {
		return UnknownCatalogStrategy.instance();  // not verified yet...
	}

	@Override
	FoldingStrategy getFoldingStrategy() {
		return UpperCaseFoldingStrategy.instance();
	}

	@Override
	void addDefaultSchemaNamesTo(Database database, String userName, ArrayList<String> names) {
		names.add(PUBLIC_SCHEMA_NAME);
	}
	private static final String PUBLIC_SCHEMA_NAME = "PUBLIC";  //$NON-NLS-1$

}
