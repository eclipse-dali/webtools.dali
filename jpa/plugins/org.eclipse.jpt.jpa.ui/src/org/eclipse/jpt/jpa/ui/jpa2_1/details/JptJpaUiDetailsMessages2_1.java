/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.jpa2_1.details;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA 2.1 UI details views.
 */
public class JptJpaUiDetailsMessages2_1 {

	private static final String BUNDLE_NAME = "jpt_jpa_ui_details2_1"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiDetailsMessages2_1.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String ADD_QUERY_DIALOG__NAMED_STORED_PROCEDURE_QUERY;


	private JptJpaUiDetailsMessages2_1() {
		throw new UnsupportedOperationException();
	}
}