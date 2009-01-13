/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali ORM widgets.
 *
 * @version 2.2
 * @since 1.0
 */
public class JptUiOrmMessages {

	public static String Boolean_False;
	public static String Boolean_True;
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
	public static String OrmGeneratorsComposite_addSequenceGenerator;
	public static String OrmGeneratorsComposite_addSequenceGeneratorDescription;
	public static String OrmGeneratorsComposite_addSequenceGeneratorDescriptionTitle;
	public static String OrmGeneratorsComposite_addSequenceGeneratorTitle;
	public static String OrmGeneratorsComposite_addTableGenerator;
	public static String OrmGeneratorsComposite_addTableGeneratorDescription;
	public static String OrmGeneratorsComposite_addTableGeneratorDescriptionTitle;
	public static String OrmGeneratorsComposite_addTableGeneratorTitle;
	public static String OrmGeneratorsComposite_displayString;
	public static String OrmGeneratorsComposite_edit;
	public static String OrmGeneratorsComposite_editSequenceGeneratorDescription;
	public static String OrmGeneratorsComposite_editSequenceGeneratorDescriptionTitle;
	public static String OrmGeneratorsComposite_editSequenceGeneratorTitle;
	public static String OrmGeneratorsComposite_editTableGeneratorDescription;
	public static String OrmGeneratorsComposite_editTableGeneratorDescriptionTitle;
	public static String OrmGeneratorsComposite_editTableGeneratorTitle;
	public static String OrmGeneratorsComposite_groupBox;
	public static String OrmGeneratorsComposite_label;
	public static String OrmJavaAttributeChooser_javaAttribute;
	public static String OrmJavaClassChooser_javaClass;
	public static String OrmQueriesComposite_groupBox;
	public static String OrmSequenceGeneratorComposite_name;
	public static String OrmSequenceGeneratorComposite_sequence;
	public static String OrmTableGeneratorComposite_default;
	public static String OrmTableGeneratorComposite_name;
	public static String OrmTableGeneratorComposite_pkColumn;
	public static String OrmTableGeneratorComposite_pkColumnValue;
	public static String OrmTableGeneratorComposite_table;
	public static String OrmTableGeneratorComposite_tableGenerator;
	public static String OrmTableGeneratorComposite_valueColumn;
	public static String PersistenceUnitMetadataComposite_access;
	public static String PersistenceUnitMetadataComposite_cascadePersistCheckBox;
	public static String PersistenceUnitMetadataComposite_catalog;
	public static String PersistenceUnitMetadataComposite_field;
	public static String PersistenceUnitMetadataComposite_persistenceUnitSection;
	public static String PersistenceUnitMetadataComposite_property;
	public static String PersistenceUnitMetadataComposite_schema;
	public static String PersistenceUnitMetadataComposite_xmlMappingMetadataCompleteCheckBox;
	public static String PersistenceUnitMetadataSection_catalogDefault;
	public static String PersistenceUnitMetadataSection_schemaDefault;

	private static final String BUNDLE_NAME = "jpt_ui_orm"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptUiOrmMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptUiOrmMessages() {
		throw new UnsupportedOperationException();
	}

}
