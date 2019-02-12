/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.driver;

import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Fold <em>regular</em> identifiers to lower case.
 */
class LowerCaseFoldingStrategy
	implements FoldingStrategy
{
	// singleton
	private static final FoldingStrategy INSTANCE = new LowerCaseFoldingStrategy();

	/**
	 * Return the singleton.
	 */
	static FoldingStrategy instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private LowerCaseFoldingStrategy() {
		super();
	}

	public String fold(String identifier) {
		return identifier.toLowerCase();
	}

	public boolean nameIsFolded(String name) {
		return StringTools.isLowercase(name);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
