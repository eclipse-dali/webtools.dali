/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali DBWS UI.
 *
 * @version 2.3
 */
public class JptDbwsUiMessages {
	
	// DbwsGenerator
	public static String DbwsGeneratorWizard_title;
	public static String DbwsGeneratorWizard_generatingDbws;

	public static String WebDynamicProjectWizardPage_title;
	public static String WebDynamicProjectWizardPage_desc;
	public static String WebDynamicProjectWizardPage_destinationProject;

	public static String BuilderXmlWizardPage_title;
	public static String BuilderXmlWizardPage_desc;
	
	public static String BuilderXmlWizardPage_errorUriCannotBeLocated;
	
	public static String JdbcDriverWizardPage_title;
	public static String JdbcDriverWizardPage_desc;

	public static String JdbcDriverWizardPage_driverFiles;
	
	public static String JdbcDriverWizardPage_addButton;
	public static String JdbcDriverWizardPage_removeButton;
	
	public static String JdbcDriverWizardPage_chooseADriverFile;
	
	public static String DbwsGeneratorUi_runningDbwsWarningTitle;
	public static String DbwsGeneratorUi_runningDbwsWarningMessage;
	public static String DbwsGeneratorUi_dbwsNotOnClasspathMessage;
	
	public static String DbwsGeneratorUi_notJavaProject;
	public static String DbwsGeneratorUi_notWebDynamicProject;
	
	public static String BuilderXmlWizardPage_xmlCatalogTableTitle;
	public static String BuilderXmlWizardPage_xmlCatalogKeyColumn;
	public static String BuilderXmlWizardPage_xmlCatalogUriColumn;


	
	
	private static final String BUNDLE_NAME = "jpt_dbws_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptDbwsUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptDbwsUiMessages() {
		throw new UnsupportedOperationException();
	}

}
