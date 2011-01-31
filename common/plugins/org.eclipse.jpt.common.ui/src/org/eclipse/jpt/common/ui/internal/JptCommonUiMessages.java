/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali UI.
 *
 * @version 3.0
 * @since 1.0
 */
public class JptCommonUiMessages {


	public static String Boolean_True;
	public static String Boolean_False;
	
	public static String DefaultEmpty;
	public static String DefaultWithOneParam;
	public static String NoneSelected;

	public static String AddRemovePane_AddButtonText;
	public static String AddRemovePane_RemoveButtonText;

	public static String ChooserPane_browseButton;
	public static String ClassChooserPane_dialogMessage;
	public static String ClassChooserPane_dialogTitle;
	public static String PackageChooserPane_dialogMessage;
	public static String PackageChooserPane_dialogTitle;
	public static String EnumComboViewer_default;
	public static String EnumComboViewer_defaultWithDefault;
	public static String NewNameStateObject_nameAlreadyExists;
	public static String NewNameStateObject_nameMustBeSpecified;

	private static final String BUNDLE_NAME = "jpt_common_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptCommonUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptCommonUiMessages() {
		throw new UnsupportedOperationException();
	}

}
