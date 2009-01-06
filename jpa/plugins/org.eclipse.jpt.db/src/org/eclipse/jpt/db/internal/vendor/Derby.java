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

import java.util.ArrayList;

import org.eclipse.datatools.modelbase.sql.schema.Database;

class Derby
	extends AbstractVendor
{
	// singleton
	private static final Vendor INSTANCE = new Derby();

	/**
	 * Return the singleton.
	 */
	static Vendor instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Derby() {
		super();
	}

	@Override
	public String getDTPVendorName() {
		return "Derby"; //$NON-NLS-1$
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
	void addDefaultSchemaIdentifiersTo(Database database, String userName, ArrayList<String> identifiers) {
		identifiers.add(this.buildDefaultSchemaIdentifier(userName));
	}

	/**
	 * The default user name on Derby is "APP".
	 */
	private String buildDefaultSchemaIdentifier(String userName) {
		return ((userName != null) && (userName.length() != 0)) ? userName : DEFAULT_USER_NAME;
	}
	private static final String DEFAULT_USER_NAME = "APP";  //$NON-NLS-1$

	@Override
	char[] getExtendedNormalNamePartCharacters() {
		return EXTENDED_NORMAL_NAME_PART_CHARACTERS;
	}
	private static final char[] EXTENDED_NORMAL_NAME_PART_CHARACTERS = new char[] { '_' };

}
