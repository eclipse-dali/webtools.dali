/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.prefs;

import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * An implementation of the JDK {@link Preferences} that does nothing,
 * in a reasonable fashion.
 */
public class NullPreferences
	extends AbstractPreferences
{
	// singleton
	private static final Preferences INSTANCE = new NullPreferences();

	/**
	 * Return the singleton.
	 */
	public static Preferences instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullPreferences() {
		super(null, StringTools.EMPTY_STRING);
	}

	@Override
	protected void putSpi(String key, String value) {
		// NOP
	}

	@Override
	protected String getSpi(String key) {
		return null;
	}

	@Override
	protected void removeSpi(String key) {
		// NOP
	}

	@Override
	protected void removeNodeSpi() throws BackingStoreException {
		// NOP
	}

	@Override
	protected String[] keysSpi() throws BackingStoreException {
		return StringTools.EMPTY_STRING_ARRAY;
	}

	@Override
	protected String[] childrenNamesSpi() throws BackingStoreException {
		return StringTools.EMPTY_STRING_ARRAY;
	}

	@Override
	protected AbstractPreferences childSpi(String name) {
		return this;
	}

	@Override
	protected void syncSpi() throws BackingStoreException {
		// NOP
	}

	@Override
	protected void flushSpi() throws BackingStoreException {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
