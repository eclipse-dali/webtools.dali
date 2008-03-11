/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.util.Iterator;

import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 *  "null" database
 *  This is used when the connection profile is inactive (i.e. it is neither
 *  connected to the database nor working off-line).
 */
final class NullDatabase
	implements InternalDatabase
{
	private static final String EMPTY_STRING = "";  //$NON-NLS-1$


	// ********** singleton **********

	private static final NullDatabase INSTANCE = new NullDatabase();

	static synchronized InternalDatabase instance() {
		return INSTANCE;
	}

	/**
	 * 'private' to ensure singleton
	 */
	private NullDatabase() {
		super();
	}


	// ********** Database implementation **********

	public String name() {
		return ClassTools.shortClassNameForObject(this);
	}

	public String vendor() {
		return EMPTY_STRING;
	}

	public String version() {
		return EMPTY_STRING;
	}

	public boolean isCaseSensitive() {
		return false;
	}

	public DatabaseDefinition dtpDefinition() {
		return null;
	}

	// ***** catalogs

	public boolean supportsCatalogs() {
		return false;
	}

	public Iterator<Catalog> catalogs() {
		return EmptyIterator.<Catalog>instance();
	}

	public int catalogsSize() {
		return 0;
	}

	public Iterator<String> catalogNames() {
		return EmptyIterator.<String>instance();
	}

	public boolean containsCatalogNamed(String name) {
		return false;
	}

	public Catalog catalogNamed(String name) {
		return null;
	}

	public String defaultCatalogName() {
		return EMPTY_STRING;
	}

	public DTPCatalogWrapper defaultCatalog() {
		return null;
	}

	// ***** schemata

	public Iterator<Schema> schemata() {
		return EmptyIterator.<Schema>instance();
	}

	public int schemataSize() {
		return 0;
	}

	public Iterator<String> schemaNames() {
		return EmptyIterator.<String>instance();
	}

	public boolean containsSchemaNamed(String name) {
		return false;
	}

	public Schema schemaNamed(String name) {
		return null;
	}


	// ********** InternalDatabase implementation **********

	public void dispose() {
		// do nothing
	}


	// ********** Comparable implementation **********

	public int compareTo(Database o) {
		throw new UnsupportedOperationException("the \"null\" database should not be in a sorted list");  //$NON-NLS-1$
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return ClassTools.toStringClassNameForObject(this);
	}

}
