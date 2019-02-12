/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.prefs.validation;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA UI validation preferences.
 */
public class JptJpaUiPreferencesValidationMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_ui_preferences_validation"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiPreferencesValidationMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String PROJECT_LEVEL_CATEGORY;
	public static String PERSISTENCE_UNIT_LEVEL_CATEGORY;
	public static String TYPE_LEVEL_CATEGORY;
	public static String ATTRIBUTE_LEVEL_CATEGORY;
	public static String DATABASE_CATEGORY;
	public static String IMPLIED_ATTRIBUTE_LEVEL_CATEGORY;
	public static String TABLE_CATEGORY;
	public static String COLUMN_CATEGORY;
	public static String OVERRIDES_CATEGORY;
	public static String INHERITANCE_CATEGORY;
	public static String QUERIES_GENERATORS_CATEGORY;

	private JptJpaUiPreferencesValidationMessages() {
		throw new UnsupportedOperationException();
	}
}
