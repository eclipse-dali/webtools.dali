/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;

import org.eclipse.jpt.common.utility.Filter;

/**
 * This filter accepts only non-<code>null</code>, non-empty,
 * non-whitespace-only strings.
 */
public class NonEmptyStringFilter
	implements Filter<String>, Serializable
{
	public static final Filter<String> INSTANCE = new NonEmptyStringFilter();

	public static Filter<String> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NonEmptyStringFilter() {
		super();
	}

	// accept only non-null objects
	public boolean accept(String string) {
		return StringTools.stringIsNotEmpty(string);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}
}
