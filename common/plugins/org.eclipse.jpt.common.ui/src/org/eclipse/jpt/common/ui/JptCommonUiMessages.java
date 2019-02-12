/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali common UI.
 */
public class JptCommonUiMessages {

	private static final String BUNDLE_NAME = "jpt_common_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptCommonUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String BOOLEAN_TRUE;
	public static String BOOLEAN_FALSE;

	public static String DEFAULT_EMPTY;
	public static String DEFAULT_WITH_ONE_PARAM;
	public static String NONE_SELECTED;

	public static String ADD_REMOVE_PANE__ADD_BUTTON_TEXT;
	public static String ADD_REMOVE_PANE__REMOVE_BUTTON_TEXT;

	public static String CHOOSER_PANE__BROWSE_BUTTON;
	public static String CLASS_CHOOSER_PANE__DIALOG_MESSAGE;
	public static String CLASS_CHOOSER_PANE__DIALOG_TITLE;
	public static String PACKAGE_CHOOSER_PANE__DIALOG_MESSAGE;
	public static String PACKAGE_CHOOSER_PANE__DIALOG_TITLE;
	public static String ENUM_COMBO_VIEWER__DEFAULT;
	public static String ENUM_COMBO_VIEWER__DEFAULT_WITH_DEFAULT;
	public static String NEW_NAME_STATE_OBJECT__NAME_ALREADY_EXISTS;
	public static String NEW_NAME_STATE_OBJECT__NAME_MUST_BE_SPECIFIED;

	public static String OPTIONAL_MESSAGE_DIALOG__DO_NOT_SHOW_WARNING;

	public static String PROBLEM_SEVERITIES_PAGE__ERROR;
	public static String PROBLEM_SEVERITIES_PAGE__WARNING;
	public static String PROBLEM_SEVERITIES_PAGE__INFO;
	public static String PROBLEM_SEVERITIES_PAGE__IGNORE;

	public static String ABSTRACT_JPT_GENERATE_JOB__GENERATION_FAILED;
	public static String ABSTRACT_JPT_GENERATE_JOB__ERROR;

	private JptCommonUiMessages() {
		throw new UnsupportedOperationException();
	}
}
