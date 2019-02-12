/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.jpa2_1.persistence;

import org.eclipse.osgi.util.NLS;

public class JptJpaUiPersistenceMessages2_1 {

	private static final String BUNDLE_NAME = "jpt_jpa_ui_persistence2_1"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiPersistenceMessages2_1.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	// Schema Generation tab
	public static String SCHEMA_GENERATION_COMPOSITE_SCHEMA_GENERATION_TAB_TITLE;
	
	public static String PERSISTENCE_UNIT_SCHEMA_GENERATION2_1_EDITOR_PAGE_TITLE;
	public static String PERSISTENCE_UNIT_SCHEMA_GENERATION2_1_EDITOR_PAGE_DESCRIPTION;

	// Schema Generation properties
	public static String SCHEMA_GENERATION_COMPOSITE_SCHEMA_GENERATION_GROUP_TITLE;
	
	public static String SCHEMA_GENERATION_COMPOSITE_DATABASE_ACTION;
	public static String SCHEMA_GENERATION_COMPOSITE_SCRIPTS_GENERATION;
	public static String SCHEMA_GENERATION_COMPOSITE_METADATA_AND_SCRIPT_CREATION;
	public static String SCHEMA_GENERATION_COMPOSITE_METADATA_AND_SCRIPT_DROPPING;
	
	public static String SCHEMA_GENERATION_COMPOSITE_SCRIPTS_CREATE_TARGET;
	public static String SCHEMA_GENERATION_COMPOSITE_SCRIPTS_DROP_TARGET;
	public static String SCHEMA_GENERATION_COMPOSITE_DATABASE_PRODUCT_NAME;
	public static String SCHEMA_GENERATION_COMPOSITE_DATABASE_MAJOR_VERSION;
	public static String SCHEMA_GENERATION_COMPOSITE_DATABASE_MINOR_VERSION;
	public static String SCHEMA_GENERATION_COMPOSITE_CREATE_SCRIPT_SOURCE;
	public static String SCHEMA_GENERATION_COMPOSITE_DROP_SCRIPT_SOURCE;
	public static String SCHEMA_GENERATION_COMPOSITE_CONNECTION;
	
	public static String SCHEMA_GENERATION_ACTION_NONE;
	public static String SCHEMA_GENERATION_ACTION_CREATE;
	public static String SCHEMA_GENERATION_ACTION_DROP_AND_CREATE;
	public static String SCHEMA_GENERATION_ACTION_DROP;
	
	public static String SCHEMA_GENERATION_TARGET_METADATA;
	public static String SCHEMA_GENERATION_TARGET_SCRIPT;
	public static String SCHEMA_GENERATION_TARGET_METADATA_THEN_SCRIPT;
	public static String SCHEMA_GENERATION_TARGET_SCRIPT_THEN_METADATA;
	
	public static String SCHEMA_GENERATION_COMPOSITE_CREATE_DATABASE_SCHEMAS_LABEL;
	public static String SCHEMA_GENERATION_COMPOSITE_DEFAULT_CREATE_DATABASE_SCHEMAS_LABEL;
	

	// Data Loading
	public static String SCHEMA_GENERATION_COMPOSITE_DATA_LOADING_GROUP_TITLE;
	
	public static String SCHEMA_GENERATION_COMPOSITE_SQL_LOAD_SCRIPT_SOURCE_LABEL;


	private JptJpaUiPersistenceMessages2_1() {
		throw new UnsupportedOperationException();
	}
}
