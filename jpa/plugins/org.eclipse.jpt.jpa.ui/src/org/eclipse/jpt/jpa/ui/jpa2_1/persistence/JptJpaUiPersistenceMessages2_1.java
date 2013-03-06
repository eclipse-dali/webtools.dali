/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.jpa2_1.persistence;

import org.eclipse.osgi.util.NLS;

/**
 *  JptJpaUiPersistenceMessages2_1
 */
public class JptJpaUiPersistenceMessages2_1 {

	private static final String BUNDLE_NAME = "jpt_jpa_ui_persistence2_1"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiPersistenceMessages2_1.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	// Schema Generation tab
	public static String SchemaGenerationComposite_schemaGenerationTabTitle;
	
	public static String PersistenceUnitSchemaGeneration2_1EditorPage_title;
	public static String PersistenceUnitSchemaGeneration2_1EditorPage_description;

	// Schema Generation properties
	public static String SchemaGenerationComposite_schemaGenerationGroupTitle;
	
	public static String SchemaGenerationComposite_databaseAction;
	public static String SchemaGenerationComposite_scriptsGeneration;
	public static String SchemaGenerationComposite_metadataAndScriptCreation;
	public static String SchemaGenerationComposite_metadataAndScriptDropping;
	
	public static String SchemaGenerationComposite_scriptsCreateTarget;
	public static String SchemaGenerationComposite_scriptsDropTarget;
	public static String SchemaGenerationComposite_databaseProductName;
	public static String SchemaGenerationComposite_databaseMajorVersion;
	public static String SchemaGenerationComposite_databaseMinorVersion;
	public static String SchemaGenerationComposite_createScriptSource;
	public static String SchemaGenerationComposite_dropScriptSource;
	public static String SchemaGenerationComposite_connection;
	
	public static String SchemaGenerationAction_none;
	public static String SchemaGenerationAction_create;
	public static String SchemaGenerationAction_drop_and_create;
	public static String SchemaGenerationAction_drop;
	
	public static String SchemaGenerationTarget_metadata;
	public static String SchemaGenerationTarget_script;
	public static String SchemaGenerationTarget_metadata_then_script;
	public static String SchemaGenerationTarget_script_then_metadata;
	
	public static String SchemaGenerationComposite_createDatabaseSchemasLabel;
	public static String SchemaGenerationComposite_defaultCreateDatabaseSchemasLabel;
	

	// Data Loading
	public static String SchemaGenerationComposite_dataLoadingGroupTitle;
	
	public static String SchemaGenerationComposite_sqlLoadScriptSourceLabel;


	private JptJpaUiPersistenceMessages2_1() {
		throw new UnsupportedOperationException();
	}
}
