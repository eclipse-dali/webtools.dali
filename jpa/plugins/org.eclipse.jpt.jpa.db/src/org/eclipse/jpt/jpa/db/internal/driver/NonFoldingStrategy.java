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
 * Do not fold <em>regular</em> identifiers.
 */
class NonFoldingStrategy
	implements FoldingStrategy
{
	// singleton
	private static final FoldingStrategy INSTANCE = new NonFoldingStrategy();

	/**
	 * Return the singleton.
	 */
	static FoldingStrategy instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NonFoldingStrategy() {
		super();
	}

	/**
	 * Since identifiers are not folded to upper- or lower-case, the name is
	 * already <em>folded</em>.
	 */
	public String fold(String identifier) {
		return identifier;
	}

	/**
	 * Since identifiers are not folded to upper- or lower-case, the name is
	 * already <em>folded</em>.
	 * (Non-folding databases do not require delimiters around mixed-case
	 * <em>regular</em> identifiers.)
	 */
	public boolean nameIsFolded(String name) {
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
