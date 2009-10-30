/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.osgi.util.NLS;

public class JptUiValidationPreferenceMessages {

	public static String PROJECT_LEVEL_CATEGORY;
	public static String NO_JPA_PROJECT;
	public static String PROJECT_NO_CONNECTION;
	public static String PROJECT_INVALID_CONNECTION;
	public static String PROJECT_INACTIVE_CONNECTION;
	public static String PROJECT_NO_PERSISTENCE_XML;
	public static String PROJECT_MULTIPLE_PERSISTENCE_XML;
	public static String PERSISTENCE_NO_PERSISTENCE_UNIT;
	public static String PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS;
	public static String PERSISTENCE_XML_INVALID_CONTENT;

	public static String PERSISTENCE_UNIT_LEVEL_CATEGORY;
	public static String PERSISTENCE_UNIT_UNSPECIFIED_MAPPING_FILE;
	public static String PERSISTENCE_UNIT_UNSUPPORTED_MAPPING_FILE_CONTENT;
	public static String PERSISTENCE_UNIT_NONEXISTENT_MAPPING_FILE;
	public static String PERSISTENCE_UNIT_INVALID_MAPPING_FILE;
	public static String PERSISTENCE_UNIT_DUPLICATE_MAPPING_FILE;
	public static String PERSISTENCE_UNIT_UNSPECIFIED_CLASS;
	public static String PERSISTENCE_UNIT_NONEXISTENT_CLASS;
	public static String PERSISTENCE_UNIT_INVALID_CLASS;
	public static String PERSISTENCE_UNIT_DUPLICATE_CLASS;
	public static String PERSISTENCE_UNIT_REDUNDANT_CLASS;
	public static String PERSISTENCE_UNIT_DUPLICATE_JAR_FILE;
	public static String PERSISTENCE_UNIT_UNSPECIFIED_JAR_FILE;
	public static String PERSISTENCE_UNIT_JAR_FILE_DEPLOYMENT_PATH_WARNING;
	public static String PERSISTENCE_UNIT_NONEXISTENT_JAR_FILE;
	public static String MAPPING_FILE_EXTRANEOUS_PERSISTENCE_UNIT_DEFAULTS;
	public static String PERSISTENT_TYPE_MAPPED_BUT_NOT_INCLUDED_IN_PERSISTENCE_UNIT;
	public static String PERSISTENT_TYPE_ANNOTATED_BUT_NOT_INCLUDED_IN_PERSISTENCE_UNIT;
	public static String PERSISTENT_TYPE_UNSPECIFIED_CLASS;
	public static String PERSISTENT_TYPE_UNRESOLVED_CLASS;

	public static String TYPE_LEVEL_CATEGORY;
	public static String ENTITY_NO_ID;
	public static String ENTITY_SINGLE_TABLE_DESCENDANT_DEFINES_TABLE;
	public static String ENTITY_ABSTRACT_TABLE_PER_CLASS_DEFINES_TABLE;
	public static String ENTITY_ABSTRACT_DISCRIMINATOR_VALUE_DEFINED;
	public static String ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_VALUE_DEFINED;
	public static String ENTITY_NON_ROOT_DISCRIMINATOR_COLUMN_DEFINED;
	public static String ENTITY_TABLE_PER_CLASS_DISCRIMINATOR_COLUMN_DEFINED;
	public static String PERSISTENT_ATTRIBUTE_UNSPECIFIED_NAME;
	public static String PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME;
	public static String PERSISTENT_ATTRIBUTE_INHERITED_ATTRIBUTES_NOT_SUPPORTED;
	public static String PERSISTENT_ATTRIBUTE_INVALID_MAPPING;
	public static String PERSISTENT_ATTRIBUTE_FINAL_FIELD;
	public static String PERSISTENT_ATTRIBUTE_PUBLIC_FIELD;

	public static String ATTRIBUTE_LEVEL_CATEGORY;
	public static String MAPPING_UNRESOLVED_MAPPED_BY;
	public static String MAPPING_INVALID_MAPPED_BY;
	public static String MAPPING_MAPPED_BY_WITH_JOIN_TABLE;
	public static String MAPPING_MAPPED_BY_ON_BOTH_SIDES;
	public static String TARGET_ENTITY_NOT_DEFINED;
	public static String TARGET_ENTITY_IS_NOT_AN_ENTITY;

	public static String PHYSICAL_MAPPING_CATEGORY;
	public static String TABLE_UNRESOLVED_CATALOG;
	public static String TABLE_UNRESOLVED_SCHEMA;
	public static String TABLE_UNRESOLVED_NAME;
	public static String SECONDARY_TABLE_UNRESOLVED_CATALOG;
	public static String SECONDARY_TABLE_UNRESOLVED_SCHEMA;
	public static String SECONDARY_TABLE_UNRESOLVED_NAME;
	public static String JOIN_TABLE_UNRESOLVED_CATALOG;
	public static String JOIN_TABLE_UNRESOLVED_SCHEMA;
	public static String JOIN_TABLE_UNRESOLVED_NAME;
	public static String COLUMN_UNRESOLVED_TABLE;
	public static String COLUMN_UNRESOLVED_NAME;
	public static String JOIN_COLUMN_UNRESOLVED_TABLE;
	public static String JOIN_COLUMN_UNRESOLVED_NAME;
	public static String JOIN_COLUMN_UNRESOLVED_NAME_MULTIPLE_JOIN_COLUMNS;
	public static String JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME;
	public static String JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME_MULTIPLE_JOIN_COLUMNS;
	public static String PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	public static String PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;

	public static String IMPLIED_ATTRIBUTE_CATEGORY;
	public static String VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_CATALOG;
	public static String VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_SCHEMA;
	public static String VIRTUAL_ATTRIBUTE_JOIN_TABLE_UNRESOLVED_NAME;
	public static String VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_TABLE;
	public static String VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_TABLE;
	public static String VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME;
	public static String VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME;
	public static String VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_TABLE;
	public static String VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME;
	public static String VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME_MULTIPLE_JOIN_COLUMNS;
	public static String VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME;
	public static String VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME_MULTIPLE_JOIN_COLUMNS;
	public static String VIRTUAL_ATTRIBUTE_TARGET_ENTITY_IS_NOT_AN_ENTITY;
	public static String VIRTUAL_ATTRIBUTE_TARGET_ENTITY_NOT_DEFINED;

	public static String IMPLIED_ASSOCIATION_CATEGORY;
	public static String VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_TABLE;
	public static String VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME;
	public static String VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME;

	public static String INHERITANCE_CATEGORY;
	public static String DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	public static String ENTITY_TABLE_PER_CLASS_NOT_SUPPORTED_ON_PLATFORM;
	public static String ENTITY_TABLE_PER_CLASS_NOT_PORTABLE_ON_PLATFORM;

	public static String QUERIES_GENERATORS_CATEGORY;
	public static String GENERATOR_DUPLICATE_NAME;
	public static String ID_MAPPING_UNRESOLVED_GENERATOR_NAME;
	public static String GENERATED_VALUE_UNRESOLVED_GENERATOR;
	public static String QUERY_DUPLICATE_NAME;

	private static final String BUNDLE_NAME = "jpt_ui_validation_preferences"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptUiValidationPreferenceMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptUiValidationPreferenceMessages() {
		throw new UnsupportedOperationException();
	}


}
