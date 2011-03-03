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

/**
 * Adapt database-specific identifier-folding etc.
 */
interface FoldingStrategy {

	/**
	 * Fold the specified identifier.
	 */
	String fold(String identifier);

	/**
	 * Return whether the specified database object name is already folded,
	 * meaning, typically, if it has no special characters, it requires no
	 * delimiters.
	 */
	boolean nameIsFolded(String name);
}
