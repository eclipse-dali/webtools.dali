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

/**
 * 
 */
class UnrecognizedVendor
	extends AbstractVendor
{
	// singleton
	private static final Vendor INSTANCE = new UnrecognizedVendor();

	/**
	 * Return the singleton.
	 */
	static Vendor instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private UnrecognizedVendor() {
		super();
	}

	@Override
	public String getDTPVendorName() {
		return "Unrecognized Vendor"; //$NON-NLS-1$
	}

	/**
	 * Not sure what to do here....
	 * Assume the DTP database is organized into one or more catalogs and
	 * the schemata are contained by those catalogs. This appears to be the
	 * default way DTP builds models these days (i.e. a database with at
	 * least one catalog, instead of the database holding schemata
	 * directly).
	 */
	@Override
	CatalogStrategy getCatalogStrategy() {
		return UnknownCatalogStrategy.instance();
	}

	@Override
	FoldingStrategy getFoldingStrategy() {
		return UpperCaseFoldingStrategy.instance();
	}

}
