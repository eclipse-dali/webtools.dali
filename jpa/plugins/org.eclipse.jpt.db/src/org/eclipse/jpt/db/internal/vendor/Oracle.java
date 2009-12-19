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

class Oracle
	extends AbstractVendor
{
	// singleton
	private static final Vendor INSTANCE = new Oracle();

	/**
	 * Return the singleton.
	 */
	static Vendor instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Oracle() {
		super();
	}

	@Override
	public String getDTPVendorName() {
		return "Oracle"; //$NON-NLS-1$
	}

	@Override
	CatalogStrategy getCatalogStrategy() {
		return FauxCatalogStrategy.instance();
	}

	@Override
	FoldingStrategy getFoldingStrategy() {
		return UpperCaseFoldingStrategy.instance();
	}

	@Override
	char[] getExtendedRegularNamePartCharacters() {
		return EXTENDED_REGULAR_NAME_PART_CHARACTERS;
	}
	private static final char[] EXTENDED_REGULAR_NAME_PART_CHARACTERS = new char[] { '_', '$', '#' };

}
