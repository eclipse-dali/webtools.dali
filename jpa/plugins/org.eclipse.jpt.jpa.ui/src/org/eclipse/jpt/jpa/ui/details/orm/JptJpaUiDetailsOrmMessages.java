/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details.orm;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA UI ORM details view.
 */
public class JptJpaUiDetailsOrmMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_ui_details_orm"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiDetailsOrmMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String ENTITY_MAPPINGS_SECTION_TITLE;
	public static String ENTITY_MAPPINGS_DETAILS_PAGE_CATALOG;
	public static String ENTITY_MAPPINGS_DETAILS_PAGE_PACKAGE;
	public static String ENTITY_MAPPINGS_DETAILS_PAGE_SCHEMA;
	public static String ENTITY_MAPPINGS_PAGE_CATALOG_DEFAULT;
	public static String ENTITY_MAPPINGS_PAGE_CATALOG_NO_DEFAULT_SPECIFIED;
	public static String ENTITY_MAPPINGS_PAGE_SCHEMA_DEFAULT;
	public static String ENTITY_MAPPINGS_PAGE_SCHEMA_NO_DEFAULT_SPECIFIED;
	public static String METADATA_COMPLETE_COMPOSITE_METADATA_COMPLETE;
	public static String METADATA_COMPLETE_COMPOSITE_METADATA_COMPLETE_WITH_DEFAULT;
	public static String ORM_GENERATORS_COMPOSITE_DISPLAY_STRING;
	public static String ORM_GENERATORS_COMPOSITE_GROUP_BOX;
	public static String ORM_MAPPING_NAME_CHOOSER_NAME;
	public static String ORM_JAVA_CLASS_CHOOSER_JAVA_CLASS;
	public static String ORM_QUERIES_COMPOSITE_GROUP_BOX;
	public static String PERSISTENCE_UNIT_METADATA_COMPOSITE_DELIMITED_IDENTIFIERS_CHECK_BOX;
	public static String PERSISTENCE_UNIT_METADATA_COMPOSITE_CASCADE_PERSIST_CHECK_BOX;
	public static String PERSISTENCE_UNIT_METADATA_COMPOSITE_CATALOG;
	public static String PERSISTENCE_UNIT_METADATA_COMPOSITE_PERSISTENCE_UNIT_SECTION;
	public static String PERSISTENCE_UNIT_METADATA_COMPOSITE_SCHEMA;
	public static String PERSISTENCE_UNIT_METADATA_COMPOSITE_XML_MAPPING_METADATA_COMPLETE_CHECK_BOX;
	public static String PERSISTENCE_UNIT_METADATA_SECTION_CATALOG_DEFAULT;
	public static String PERSISTENCE_UNIT_METADATA_SECTION_SCHEMA_DEFAULT;

	public static String ADD_GENERATOR_DIALOG_NAME;
	public static String ADD_GENERATOR_DIALOG_GENERATOR_TYPE;
 	public static String ADD_GENERATOR_DIALOG_TITLE;
	public static String ADD_GENERATOR_DIALOG_DESCRIPTION_TITLE;
	public static String ADD_GENERATOR_DIALOG_DESCRIPTION;
	public static String ADD_GENERATOR_DIALOG_TABLE_GENERATOR;
	public static String ADD_GENERATOR_DIALOG_SEQUENCE_GENERATOR;
	public static String GENERATOR_STATE_OBJECT_NAME_EXISTS;
	public static String GENERATOR_STATE_OBJECT_NAME_MUST_BE_SPECIFIED;
	public static String GENERATOR_STATE_OBJECT_TYPE_MUST_BE_SPECIFIED;
	
	public static String UNSUPPORTED_ORM_MAPPING_UI_PROVIDER_LABEL;
	public static String UNSUPPORTED_ORM_MAPPING_UI_PROVIDER_LINK_LABEL;

	private JptJpaUiDetailsOrmMessages() {
		throw new UnsupportedOperationException();
	}
}
