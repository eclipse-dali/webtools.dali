/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali mapping panes.
 *
 * @version 3.0
 * @since 3.0
 */
public class JptUiDetailsMessages2_0 {

	public static String DerivedIdPane_derivedIdCheckboxLabel;
	public static String ElementCollectionMapping2_0_label;
	public static String ElementCollectionMapping2_0_linkLabel;

	private static final String BUNDLE_NAME = "jpt_ui_details2_0"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptUiDetailsMessages2_0.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptUiDetailsMessages2_0() {
		throw new UnsupportedOperationException();
	}

}
