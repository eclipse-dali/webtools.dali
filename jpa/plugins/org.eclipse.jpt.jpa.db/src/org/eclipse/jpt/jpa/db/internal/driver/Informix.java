/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.driver;

import org.eclipse.jpt.jpa.db.Database;

class Informix
	extends AbstractDTPDriverAdapter
{
	Informix(Database database) {
		super(database);
	}

	@Override
	CatalogStrategy buildCatalogStrategy() {
		// unverified...
		return new UnknownCatalogStrategy(this.database.getDTPDatabase());
	}

	@Override
	FoldingStrategy buildFoldingStrategy() {
		return LowerCaseFoldingStrategy.instance();
	}

	@Override
	char[] getExtendedRegularNameStartCharacters() {
		return EXTENDED_REGULAR_NAME_START_CHARACTERS;
	}
	private static final char[] EXTENDED_REGULAR_NAME_START_CHARACTERS = new char[] { '_' };

	@Override
	char[] getExtendedRegularNamePartCharacters() {
		return EXTENDED_REGULAR_NAME_PART_CHARACTERS;
	}
	private static final char[] EXTENDED_REGULAR_NAME_PART_CHARACTERS = new char[] { '$' };


	// ********** factory **********

	static class Factory implements DTPDriverAdapterFactory {
		private static final String[] VENDORS = {
				"Informix" //$NON-NLS-1$
			};
		public String[] getSupportedVendors() {
			return VENDORS;
		}
		public DTPDriverAdapter buildAdapter(Database database) {
			return new Informix(database);
		}
	}
}
