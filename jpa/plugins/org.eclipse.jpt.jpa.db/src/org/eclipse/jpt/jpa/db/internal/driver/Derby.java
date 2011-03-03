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

class Derby
	extends AbstractDTPDriverAdapter
{
	Derby(Database database) {
		super(database);
	}

	@Override
	CatalogStrategy buildCatalogStrategy() {
		return new FauxCatalogStrategy(this.database.getDTPDatabase());
	}

	@Override
	FoldingStrategy buildFoldingStrategy() {
		return UpperCaseFoldingStrategy.instance();
	}

	@Override
	void addDefaultSchemaNamesTo(ArrayList<String> names) {
		names.add(this.getDefaultSchemaName());
	}

	/**
	 * The default user name on Derby is <code>"APP"</code> when the user
	 * connects without a user name.
	 */
	private String getDefaultSchemaName() {
		String userName = this.getUserName();
		return ((userName != null) && (userName.length() != 0)) ?
				userName :
				DEFAULT_USER_NAME;
	}
	private static final String DEFAULT_USER_NAME = "APP";  //$NON-NLS-1$


	// ********** factory **********

	static class Factory implements DTPDriverAdapterFactory {
		private static final String[] VENDORS = {
				"Derby" //$NON-NLS-1$
			};
		public String[] getSupportedVendors() {
			return VENDORS;
		}
		public DTPDriverAdapter buildAdapter(Database database) {
			return new Derby(database);
		}
	}
}
