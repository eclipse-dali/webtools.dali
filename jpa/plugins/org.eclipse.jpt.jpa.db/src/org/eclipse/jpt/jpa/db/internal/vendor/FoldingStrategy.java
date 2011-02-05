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
 * Handle database-specific identifier-folding issues.
 */
interface FoldingStrategy {

	/**
	 * Fold the specified name.
	 */
	String fold(String name);

	/**
	 * Return whether the specified database object name is already folded,
	 * meaning, if it has no special characters, it requires no delimiters.
	 */
	boolean nameIsFolded(String name);

	/**
	 * Return whether the database is case-sensitive when using "regular"
	 * (i.e. non-delimited) identifiers.
	 */
	boolean regularIdentifiersAreCaseSensitive();

}
