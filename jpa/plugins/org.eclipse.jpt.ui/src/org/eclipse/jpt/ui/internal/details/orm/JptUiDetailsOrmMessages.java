/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali ORM widgets.
 *
 * @version 2.2
 * @since 1.0
 */
public class JptUiDetailsOrmMessages {

	public static String EntityMappingsSection_title;
	public static String EntityMappingsDetailsPage_access;
	public static String EntityMappingsDetailsPage_catalog;
	public static String EntityMappingsDetailsPage_field;
	public static String EntityMappingsDetailsPage_package;
	public static String EntityMappingsDetailsPage_property;
	public static String EntityMappingsDetailsPage_schema;
	public static String EntityMappingsPage_catalogDefault;
	public static String EntityMappingsPage_catalogNoDefaultSpecified;
	public static String EntityMappingsPage_schemaDefault;
	public static String EntityMappingsPage_schemaNoDefaultSpecified;
	public static String MetadataCompleteComposite_metadataComplete;
	public static String MetadataCompleteComposite_metadataCompleteWithDefault;
	public static String OrmGeneratorsComposite_displayString;
	public static String OrmGeneratorsComposite_groupBox;
	public static String OrmMappingNameChooser_name;
	public static String OrmJavaClassChooser_javaClass;
	public static String OrmQueriesComposite_groupBox;
	public static String PersistenceUnitMetadataComposite_access;
	public static String PersistenceUnitMetadataComposite_delimitedIdentifiersCheckBox;
	public static String PersistenceUnitMetadataComposite_cascadePersistCheckBox;
	public static String PersistenceUnitMetadataComposite_catalog;
	public static String PersistenceUnitMetadataComposite_field;
	public static String PersistenceUnitMetadataComposite_persistenceUnitSection;
	public static String PersistenceUnitMetadataComposite_property;
	public static String PersistenceUnitMetadataComposite_schema;
	public static String PersistenceUnitMetadataComposite_xmlMappingMetadataCompleteCheckBox;
	public static String PersistenceUnitMetadataSection_catalogDefault;
	public static String PersistenceUnitMetadataSection_schemaDefault;

	public static String AddGeneratorDialog_name;
	public static String AddGeneratorDialog_generatorType;
 	public static String AddGeneratorDialog_title;
	public static String AddGeneratorDialog_descriptionTitle;
	public static String AddGeneratorDialog_description;
	public static String AddGeneratorDialog_tableGenerator;
	public static String AddGeneratorDialog_sequenceGenerator;
	public static String GeneratorStateObject_nameExists;
	public static String GeneratorStateObject_nameMustBeSpecified;
	public static String GeneratorStateObject_typeMustBeSpecified;
	
	public static String UnsupportedOrmMappingUiProvider_label;
	public static String UnsupportedOrmMappingUiProvider_linkLabel;

	private static final String BUNDLE_NAME = "jpt_ui_details_orm"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptUiDetailsOrmMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptUiDetailsOrmMessages() {
		throw new UnsupportedOperationException();
	}

}
