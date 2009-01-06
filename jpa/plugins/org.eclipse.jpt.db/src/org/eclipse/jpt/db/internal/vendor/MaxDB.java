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

class MaxDB
	extends AbstractVendor
{
	// singleton
	private static final Vendor INSTANCE = new MaxDB();

	/**
	 * Return the singleton.
	 */
	static Vendor instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private MaxDB() {
		super();
	}

	@Override
	public String getDTPVendorName() {
		return "MaxDB"; //$NON-NLS-1$
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
	char[] getExtendedNormalNameStartCharacters() {
		return EXTENDED_NORMAL_NAME_START_CHARACTERS;
	}
	private static final char[] EXTENDED_NORMAL_NAME_START_CHARACTERS = new char[] { '#', '@', '$' };

	@Override
	char[] getExtendedNormalNamePartCharacters() {
		return EXTENDED_NORMAL_NAME_PART_CHARACTERS;
	}
	private static final char[] EXTENDED_NORMAL_NAME_PART_CHARACTERS = new char[] { '_' };

}
