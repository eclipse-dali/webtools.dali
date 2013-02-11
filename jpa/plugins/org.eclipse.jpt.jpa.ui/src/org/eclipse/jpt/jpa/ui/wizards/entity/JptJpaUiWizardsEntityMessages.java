/*******************************************************************************
 * Copyright (c) 2008, 2013 SAP AG, Walldorf. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     SAP AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.wizards.entity;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA UI Entity Wizard.
 */
public class JptJpaUiWizardsEntityMessages {
	
	private static final String BUNDLE_NAME = "jpt_jpa_ui_wizards_entity";//$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiWizardsEntityMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String ENTITY_WIZARD_TITLE;
	public static String ENTITY_WIZARD_PAGE_TITLE;
	public static String ENTITY_WIZARD_PAGE_DESCRIPTION;
	public static String DEFAULT_PACKAGE_WARNING;
	public static String ENTITY_PROPERTIES_TITLE;
	public static String ENTITY_PROPERTIES_DESCRIPTION;
	public static String ENTITY;
	public static String MAPPED_AS_SUPERCLASS;
	public static String INHERITANCE_GROUP;
	public static String INHERITANCE_CHECK_BOX;
	public static String XML_STORAGE_GROUP;
	public static String XML_SUPPORT;
	public static String CHOOSE_XML;
	public static String MAPPING_XML_TITLE;
	public static String XML_NAME_TITLE;
	public static String INVALID_XML_NAME;
	public static String MAPPING_FILE_NOT_LISTED_ERROR;
	public static String CHOOSE_MAPPING_XML_MESSAGE;
	public static String TYPE_DIALOG_TITLE;
	public static String TYPE_DIALOG_DESCRIPTION;
	public static String ENTITY_NAME;
	public static String TABLE_NAME;
	public static String TABLE_NAME_GROUP;
	public static String USE_DEFAULT;
	public static String ENTITY_FIELDS_DIALOG_TITLE;
	public static String ENTITY_FIELDS_GROUP;
	public static String KEY;
	public static String NAME_COLUMN;
	public static String TYPE_COLUMN;
	public static String NAME_TEXT_FIELD;
	public static String TYPE_TEXT_FIELD;
	public static String BROWSE_BUTTON_LABEL;
	public static String ADD_BUTTON_LABEL;
	public static String EDIT_BUTTON_LABEL;
	public static String ENTITY_DATA_MODEL_PROVIDER_TYPE_NOT_IN_PROJECT_CLASSPATH;
	public static String ENTITY_DATA_MODEL_PROVIDER_INVALID_PK_TYPE;
	public static String ENTITY_DATA_MODEL_PROVIDER_INVALID_ARGUMENT;
	public static String REMOVE_BUTTON_LABEL;
	public static String DUPLICATED_ENTITY_NAMES_MESSAGE;
	public static String ACCESS_TYPE;
	public static String FIELD_BASED;
	public static String PROPERTY_BASED;
	public static String NO_JPA_PROJECTS;
	public static String APPLY_CHANGES_TO_PERSISTENCE_XML;
	public static String ADD_MAPPED_SUPERCLASS_TO_XML;
	public static String ADD_ENTITY_TO_XML;

	private JptJpaUiWizardsEntityMessages() {
		throw new UnsupportedOperationException();
	}
}
