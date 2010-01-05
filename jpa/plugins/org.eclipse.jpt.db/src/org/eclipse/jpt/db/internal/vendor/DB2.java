/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal.vendor;

class DB2
	extends AbstractVendor
{
	private final String dtpVendorName;

	private static final Vendor UDB = new DB2("DB2 UDB"); //$NON-NLS-1$
	private static final Vendor UDB_I_SERIES = new DB2("DB2 UDB iSeries"); //$NON-NLS-1$
	private static final Vendor UDB_Z_SERIES = new DB2("DB2 UDB zSeries"); //$NON-NLS-1$

	static Vendor udb() {
		return UDB;
	}

	static Vendor udbISeries() {
		return UDB_I_SERIES;
	}

	static Vendor udbZSeries() {
		return UDB_Z_SERIES;
	}

	/**
	 * Ensure only static instances.
	 */
	private DB2(String dtpVendorName) {
		super();
		this.dtpVendorName = dtpVendorName;
	}

	@Override
	public String getDTPVendorName() {
		return this.dtpVendorName;
	}

	@Override
	CatalogStrategy getCatalogStrategy() {
		return UnknownCatalogStrategy.instance();  // not verified yet...
	}

	@Override
	FoldingStrategy getFoldingStrategy() {
		return UpperCaseFoldingStrategy.instance();
	}

}
