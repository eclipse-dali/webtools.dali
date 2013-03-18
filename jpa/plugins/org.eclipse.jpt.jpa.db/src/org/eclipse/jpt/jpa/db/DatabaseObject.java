/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import com.ibm.icu.text.Collator;

/**
 * Behavior common to all database objects.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface DatabaseObject {

	/**
	 * Return the database object's name.
	 */
	String getName();

	Transformer<DatabaseObject, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<DatabaseObject, String>
	{
		@Override
		public String transform(DatabaseObject dbObject) {
			return dbObject.getName();
		}
	}

	/**
	 * Return the database object's <em>identifier</em>, which is the object's name
	 * modified so it can be used in an SQL statement (e.g. if the name contains
	 * special characters or is mixed case, it will be delimited, typically by
	 * double-quotes).
	 */
	String getIdentifier();

	/**
	 * Return the database object's <em>identifier</em>, which is the object's name
	 * modified so it can be used in an SQL statement (e.g. if the name contains
	 * special characters or is mixed case, it will be delimited, typically by
	 * double-quotes).
	 * Return <code>null</code> if the database object's identifier matches the
	 * specified default name.
	 * <p>
	 * This is used by the Dali entity generation code to determine whether
	 * a generated annotation must explicitly identify a database object
	 * (e.g. a table) or the default (as specified here) adequately identifies
	 * the database object (taking into consideration case-sensitivity,
	 * special characters, etc.).
	 */
	String getIdentifier(String defaultName);

	Transformer<DatabaseObject, String> IDENTIFIER_TRANSFORMER = new IdentifierTransformer();
	class IdentifierTransformer
		extends TransformerAdapter<DatabaseObject, String>
	{
		@Override
		public String transform(DatabaseObject dbObject) {
			return dbObject.getIdentifier();
		}
	}

	/**
	 * Return the database object's database.
	 */
	Database getDatabase();

	/**
	 * Return the database object's connection profile.
	 */
	ConnectionProfile getConnectionProfile();

	/**
	 * Refresh the database object's state from the underlying database server,
	 * if appropriate.
	 */
	void refresh();


	/**
	 * Sort by name.
	 */
	Comparator<DatabaseObject> DEFAULT_COMPARATOR = new DefaultComparator();
	class DefaultComparator
		implements Comparator<DatabaseObject>
	{
		public int compare(DatabaseObject dbObject1, DatabaseObject dbObject2) {
			return Collator.getInstance().compare(dbObject1.getName(), dbObject2.getName());
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}
