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
	
	// ClassesGenerator
	public static String ClassesGeneratorWizard_title;
	public static String ClassesGeneratorWizard_errorDialogTitle;
	public static String ClassesGeneratorWizard_couldNotCreate;

	public static String ClassesGeneratorWizardPage_title;
	public static String ClassesGeneratorWizardPage_desc;
	
	public static String ClassesGeneratorWizardPage_usesMoxyImplementation;
	
	public static String ClassesGeneratorWizardPage_catalog;
	public static String ClassesGeneratorWizardPage_bindingsFiles;
	public static String ClassesGeneratorWizardPage_browseButton;
	public static String ClassesGeneratorWizardPage_addButton;
	public static String ClassesGeneratorWizardPage_removeButton;
	public static String ClassesGeneratorWizardPage_chooseABindingsFile;
	public static String ClassesGeneratorWizardPage_chooseACatalog;
	
	public static String ClassesGeneratorWizardPage_jaxbLibrariesNotAvailable;
	
	public static String ClassesGeneratorWizardPage_moxyLibrariesNotAvailable;

	public static String ClassesGeneratorUi_generatingEntities;
	public static String ClassesGeneratorUi_generatingEntitiesTask;

	public static String ClassesGeneratorUi_generatingClassesWarningTitle;
	public static String ClassesGeneratorUi_generatingClassesWarningMessage;

	// SchemaGenerator
	public static String SchemaGeneratorWizard_title;
	public static String SchemaGeneratorWizard_generatingSchema;

	public static String ProjectWizardPage_desc;
	public static String ProjectWizardPage_project;

	public static String SchemaGeneratorWizardPage_title;
	public static String SchemaGeneratorWizardPage_desc;
	
	public static String SchemaGeneratorWizardPage_shemaLocation;
	public static String SchemaGeneratorWizardPage_shema;
	public static String SchemaGeneratorWizardPage_packages;
	public static String SchemaGeneratorWizardPage_browse;
	
	public static String SchemaGeneratorWizardPage_chooseSchemaDialogTitle;
	
	public static String SchemaGeneratorWizardPage_errorNoSchema;
	public static String SchemaGeneratorWizardPage_errorNoPackage;
	
	public static String SchemaGeneratorWizardPage_jaxbLibrariesNotAvailable;

	public static String SchemaGeneratorWizardPage_moxyLibrariesNotAvailable;
	
	public static String SchemaGeneratorWizard_generateSchemaTask;
	
	private static final String BUNDLE_NAME = "jpt_jaxb_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptJaxbUiMessages() {
		throw new UnsupportedOperationException();
	}

}
