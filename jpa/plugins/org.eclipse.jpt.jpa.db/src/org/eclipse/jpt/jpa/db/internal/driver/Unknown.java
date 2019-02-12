/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.driver;

import org.eclipse.jpt.jpa.db.Database;

class Unknown
	extends AbstractDTPDriverAdapter
{
	Unknown(Database database) {
		super(database);
	}

	@Override
	CatalogStrategy buildCatalogStrategy() {
		// infer from existing catalogs and schemas
		return new UnknownCatalogStrategy(this.database.getDTPDatabase());
	}

	@Override
	FoldingStrategy buildFoldingStrategy() {
		// SQL standard
		return UpperCaseFoldingStrategy.instance();
	}


	// ********** factory **********

	static class Factory implements DTPDriverAdapterFactory {
		public String[] getSupportedVendors() {
			throw new UnsupportedOperationException();
		}
		public DTPDriverAdapter buildAdapter(Database database) {
			return new Unknown(database);
		}
	}
}
