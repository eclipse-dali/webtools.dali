/*******************************************************************************
* Copyright (c) 2010, 2012 Oracle. All rights reserved.
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

	public static String JavaProjectWizardPage_project;
	public static String JavaProjectWizardPage_destinationProject;
	
	// ClassesGenerator
	public static String ClassesGeneratorProjectWizardPage_title;
	public static String ClassesGeneratorProjectWizardPage_desc;
	
	public static String ClassesGeneratorWizard_title;
	public static String ClassesGeneratorWizard_errorDialogTitle;
	public static String ClassesGeneratorWizard_couldNotCreate;
	
	public static String Error_openingEditor;
	
	public static String JaxbContent_label;
	
	public static String SchemasPage_description;
	public static String SchemasPage_revert;
	public static String SchemasPage_schemas;
	public static String SchemasPage_namespaceColumnLabel;
	public static String SchemasPage_locationColumnLabel;
	public static String SchemasPage_addButtonLabel;
	public static String SchemasPage_editButtonLabel;
	public static String SchemasPage_removeButtonLabel;
	public static String SchemasPage_addSchemaMessage;
	public static String SchemasPage_editSchemaMessage;
	public static String SchemasPage_addSchemaDialogTitle;
	public static String SchemasPage_editSchemaDialogTitle;
	public static String SchemasPage_addSchemaTitle;
	public static String SchemasPage_editSchemaTitle;
	public static String SchemasPage_locationLabel;
	public static String SchemasPage_browseButtonLabel;
	public static String SchemasPage_namespaceLabel;
	public static String SchemasPage_noNamespaceText;
	public static String SchemasPage_schemaUnresolvedMessage;
	public static String SchemasPage_duplicateNamespaceMessage;
	public static String SchemasPage_noLocationMessage;
	public static String SchemasPage_chooseLocationTitle;
	
	public static String SchemaWizardPage_title;
	public static String SchemaWizardPage_desc;
	
	public static String SchemaWizardPage_xmlCatalogTableTitle; 
	public static String SchemaWizardPage_xmlCatalogKeyColumn; 
	public static String SchemaWizardPage_xmlCatalogUriColumn; 

	public static String SchemaWizardPage_errorUriCannotBeLocated;

	public static String SelectFileOrXMLCatalogIdPanel_fileFromWorkspace;
	public static String SelectFileOrXMLCatalogIdPanel_xmlCatalogEntry;
	public static String SelectFileOrXMLCatalogIdPanel_importButton;

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

	public static String ClassesGeneratorWizardPage_sourceFolderSelectionDialog_title;
	public static String ClassesGeneratorWizardPage_chooseSourceFolderDialog_desc;

	public static String ClassesGeneratorWizardPage_jaxbLibrariesNotAvailable;
	public static String ClassesGeneratorWizardPage_moxyLibrariesNotAvailable;

	public static String ClassesGeneratorOptionsWizardPage_title;
	public static String ClassesGeneratorOptionsWizardPage_desc;

	public static String ClassesGeneratorOptionsWizardPage_proxyGroup;
	public static String ClassesGeneratorOptionsWizardPage_noProxy;
	public static String ClassesGeneratorOptionsWizardPage_proxy;
	public static String ClassesGeneratorOptionsWizardPage_proxyFile;
	public static String ClassesGeneratorOptionsWizardPage_chooseAProxyFile;
	
	public static String ClassesGeneratorOptionsWizardPage_useStrictValidation;
	public static String ClassesGeneratorOptionsWizardPage_makeReadOnly;
	public static String ClassesGeneratorOptionsWizardPage_suppressPackageInfoGen;
	public static String ClassesGeneratorOptionsWizardPage_suppressesHeaderGen;
	public static String ClassesGeneratorOptionsWizardPage_target;
	public static String ClassesGeneratorOptionsWizardPage_verbose;
	public static String ClassesGeneratorOptionsWizardPage_quiet;

	public static String ClassesGeneratorOptionsWizardPage_treatsAsXmlSchema;
	public static String ClassesGeneratorOptionsWizardPage_treatsAsRelaxNg;
	public static String ClassesGeneratorOptionsWizardPage_treatsAsRelaxNgCompact;
	public static String ClassesGeneratorOptionsWizardPage_treatsAsDtd;
	public static String ClassesGeneratorOptionsWizardPage_treatsAsWsdl;
	public static String ClassesGeneratorOptionsWizardPage_showsVersion;
	public static String ClassesGeneratorOptionsWizardPage_showsHelp;
	
	public static String ClassesGeneratorExtensionOptionsWizardPage_title;
	public static String ClassesGeneratorExtensionOptionsWizardPage_desc;
	
	public static String ClassesGeneratorExtensionOptionsWizardPage_allowExtensions;
	public static String ClassesGeneratorExtensionOptionsWizardPage_classpath;
	public static String ClassesGeneratorExtensionOptionsWizardPage_additionalArguments;

	public static String ClassesGeneratorUi_generatingEntities;
	public static String ClassesGeneratorUi_generatingEntitiesTask;

	public static String ClassesGeneratorUi_generatingClassesWarningTitle;
	public static String ClassesGeneratorUi_generatingClassesWarningMessage;

	// SchemaGenerator
	public static String SchemaGeneratorWizard_title;
	public static String SchemaGeneratorWizard_generatingSchema;
	public static String SchemaGeneratorWizard_openSchemaFileJobName;

	public static String SchemaGeneratorProjectWizardPage_title;
	public static String SchemaGeneratorProjectWizardPage_desc;
	
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
	
	public static String NewSchemaFileWizardPage_errorNotJavaProject;
	public static String NewSchemaFileWizardPage_overwriteExistingSchemas;
	
	// JptGenerateJob
	
	public static String GenerateJaxbClassesJob_generatingClasses;
	public static String GenerateJaxbClassesJob_generatingClassesTask;
	
	// new project wizard
	
	public static String JaxbProjectWizard_title;
	
	public static String JaxbProjectWizard_firstPage_title;
	public static String JaxbProjectWizard_firstPage_desc;
	
	
	// facet page
	
	public static String JaxbFacetWizardPage_title;
	public static String JaxbFacetWizardPage_desc;
	public static String JaxbFacetWizardPage_platformLabel;
	public static String JaxbFacetWizardPage_facetsPageLink;
	public static String JaxbFacetWizardPage_jaxbImplementationLabel;
	
	
	public static String JaxbPlatformUi_missingJaxbPlatform;


	private static final String BUNDLE_NAME = "jpt_jaxb_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJaxbUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptJaxbUiMessages() {
		throw new UnsupportedOperationException();
	}

}
