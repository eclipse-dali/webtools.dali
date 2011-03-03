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

import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Fold <em>regular</em> identifiers to upper case.
 */
class UpperCaseFoldingStrategy
	implements FoldingStrategy
{
	// singleton
	private static final FoldingStrategy INSTANCE = new UpperCaseFoldingStrategy();

	/**
	 * Return the singleton.
	 */
	static FoldingStrategy instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private UpperCaseFoldingStrategy() {
		super();
	}

	public String fold(String identifier) {
		return identifier.toUpperCase();
	}

	public boolean nameIsFolded(String name) {
		return StringTools.stringIsUppercase(name);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
