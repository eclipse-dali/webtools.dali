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

import java.util.ArrayList;
import org.eclipse.jpt.jpa.db.Database;

class HSQLDB
	extends AbstractDTPDriverAdapter
{
	HSQLDB(Database database) {
		super(database);
	}

	@Override
	CatalogStrategy buildCatalogStrategy() {
		// unverified...
		return new UnknownCatalogStrategy(this.database.getDTPDatabase());
	}

	@Override
	FoldingStrategy buildFoldingStrategy() {
		return UpperCaseFoldingStrategy.instance();
	}

	@Override
	void addDefaultSchemaNamesTo(ArrayList<String> names) {
		names.add(PUBLIC_SCHEMA_NAME);
	}
	private static final String PUBLIC_SCHEMA_NAME = "PUBLIC";  //$NON-NLS-1$


	// ********** factory **********

	static class Factory implements DTPDriverAdapterFactory {
		private static final String[] VENDORS = {
				"HSQLDB" //$NON-NLS-1$
			};
		public String[] getSupportedVendors() {
			return VENDORS;
		}
		public DTPDriverAdapter buildAdapter(Database database) {
			return new HSQLDB(database);
		}
	}
}
