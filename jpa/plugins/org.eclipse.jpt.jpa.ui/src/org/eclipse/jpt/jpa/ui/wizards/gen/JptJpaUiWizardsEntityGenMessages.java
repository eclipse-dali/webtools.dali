/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.wizards.gen;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA UI Entity Generation Wizard.
 */
public class JptJpaUiWizardsEntityGenMessages {
	private static final String BUNDLE_NAME = "jpt_jpa_ui_wizards_entity_gen"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiWizardsEntityGenMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_RESTORE_DEFAULTS;
	public static String GENERATE_ENTITIES_WIZARD_GENERATE_ENTITIES;
	public static String GENERATE_ENTITIES_WIZARD_SELECT_JPA_PROJECT;
	public static String GENERATE_ENTITIES_WIZARD_SELECT_JPA_PROJECT_MSG;
	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_SELECT_TABLE;
	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_CHOOSE_ENTITY_TABLE;
	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_UPDATE_PERSISTENCE_XML;
	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_TABLES;
	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_TABLE_COLUMN;
	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_TABLE_SEARCH;
	
	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_GET_TABLES_JOB_NAME;
	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_GET_TABLES_TASK_NAME;
	public static String GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_GET_TABLES_SUB_TASK_NAME;

	//Database connection group
	public static String CONNECTION;
	public static String ADD_CONNECTION_LINK;
	public static String CONNECT_LINK;
	public static String SCHEMA_INFO;
	public static String SCHEMA;
	public static String CONNECTING_TO_DATABASE;
	
	//Default table gen properties
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_TITLE;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_DESC;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_DOMAIN_JAVA_CLASS;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_TABLE_MAPPING;
	public static String GENERATE_ENTITIES_WIZARD_TABLE_PANEL_CLASS_NAME;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_FETCH;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_FETCH_DEFAULT;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_COLLECTION_TYPE;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_SEQUENCE;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_SEQUENCE_NOTE;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_ACCESS;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_KEY_GEN;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_GEN_OPTIONAL_ANNOTATIONS;
	public static String GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_GEN_OPTIONAL_ANNOTATIONS_DESC;
	
	//Asso figure
	public static String MANY_TO_ONE_DESC;
	public static String ONE_TO_ONE_DESC;
	public static String MANY_TO_MANY_DESC;
	//table association wizard page
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_PAGE_TITLE;
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_PAGE_DESC;
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_PAGE_LABEL;
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_PAGE_NEW_ASSOC;
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_PAGE_DEL_ASSOC;
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_EDITOR_GEN_ASSOC;
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_EDITOR_ENTITY_REF;
	public static String PROPERTY;
	public static String CASCADE;
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_EDITOR_SET_REF;
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_EDITOR_JOINED_WHEN;
	public static String GENERATE_ENTITIES_WIZARD_ASSOC_EDITOR_TABLE_JOIN;
	public static String CARDINALITY;
	public static String SELECT_CASCADE_DLG_TITLE;
	//new association wizard
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TITLE;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_TITLE;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_DESC;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_ASSOC_KIND;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_SIMPLE_ASSOC;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_M2M_ASSOC;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_ASSOC_TABLES;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_TABLE1;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_TABLE2;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_INTERMEDIATE_TABLE;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_NONEXSISTENT_TABLE;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_TABLES_PAGE_NONEXSISTENT_JOIN_TABLE;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_COLS_PAGE_TITLE;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_COLS_PAGE_DESC;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_COLS_PAGE_LABEL;
	public static String ADD;
	public static String REMOVE;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_CARDINALITY_PAGE_TITLE;
	public static String GENERATE_ENTITIES_WIZARD_NEW_ASSOC_CARDINALITY_PAGE_DESC;
	public static String MANY_TO_ONE;
	public static String ONE_TO_MANY;
	public static String ONE_TO_ONE;
	public static String MANY_TO_MANY;
	//select table dialog
	public static String SELECT_TABLE_DLG_TITLE;
	public static String SELECT_TABLE_DLG_DESC;
	//individual table and column gen properties
	public static String GENERATE_ENTITIES_WIZARD_TABLES_AND_COLUMNS_PAGE_TITLE;
	public static String GENERATE_ENTITIES_WIZARD_TABLES_AND_COLUMNS_PAGE_DESC;
	public static String GENERATE_ENTITIES_WIZARD_TABLES_AND_COLUMNS_PAGE_LABEL_TABLE_AND_COLUMNS;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_GEN_PROP;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_COL_MAPPING;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_PROP_NAME;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_PROP_TYPE;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_MAP_KIND;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_COL_UPDATEABLE;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_COL_INSERTABLE;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_BEAN_PROP;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_GETTER_SCOPE;
	public static String GENERATE_ENTITIES_WIZARD_COL_PANEL_SETTER_SCOPE;

	private JptJpaUiWizardsEntityGenMessages() {
		throw new UnsupportedOperationException();
	}
}
