/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JAXB UI.
 *
 * @version 2.3
 */
public class JptJaxbUiMessages {

	public static String ClassesGeneratorWizard_title;
	public static String ClassesGeneratorWizard_errorDialogTitle;
	public static String ClassesGeneratorWizard_couldNotCreate;

	public static String ClassesGeneratorWizardPage_title;
	public static String ClassesGeneratorWizardPage_desc;
	
	public static String ClassesGeneratorWizardPage_usesMoxyImplementation;
	
	public static String ClassesGeneratorWizardPage_settingsGroupTitle;
	public static String ClassesGeneratorWizardPage_targetFolder;
	public static String ClassesGeneratorWizardPage_targetPackage;
	public static String ClassesGeneratorWizardPage_catalog;
	public static String ClassesGeneratorWizardPage_bindingsFiles;
	public static String ClassesGeneratorWizardPage_addButton;
	public static String ClassesGeneratorWizardPage_removeButton;
	public static String ClassesGeneratorWizardPage_chooseABindingsFile;
	
	public static String ClassesGeneratorWizardPage_targetFolderCannotBeEmpty;
	
	public static String ClassesGeneratorWizardPage_jaxbLibrariesNotAvailable;
	
	public static String ClassesGeneratorWizardPage_moxyLibrariesNotAvailable;

	private static final String BUNDLE_NAME = "jpt_jaxb_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptJaxbUiMessages() {
		throw new UnsupportedOperationException();
	}

}
