/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;

/**
 * The initializer of the JPA UI preferences, which sets the default values.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
public class JpaUiPreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * Creates a new <code>JpaUiPreferenceInitializer</code>.
	 */
	public JpaUiPreferenceInitializer() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initializeDefaultPreferences() {

		IPreferenceStore preferences = JptJpaUiPlugin.instance().getPreferenceStore();
		preferences.setDefault(JptJpaUiPlugin.JPQL_IDENTIFIER_CASE_PREF_KEY, JptJpaUiPlugin.JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE);
		preferences.setDefault(JptJpaUiPlugin.JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY, true);
	}
}