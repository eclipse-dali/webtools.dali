/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationAction2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationTarget2_1;

/**
 * JPA 2.1 schema generation
 */
public interface SchemaGeneration2_1
	extends PersistenceUnitProperties
{
	// Schema Generation 
	SchemaGenerationAction2_1 getSchemaGenDatabaseAction();
	void setSchemaGenDatabaseAction(SchemaGenerationAction2_1 newSchemaGenAction);
		String SCHEMAGEN_DATABASE_ACTION_PROPERTY = "schemaGenDatabaseAction"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_SCHEMAGEN_DATABASE_ACTION = "javax.persistence.schema-generation.database.action"; //$NON-NLS-1$
	SchemaGenerationAction2_1 getDefaultSchemaGenDatabaseAction();
		SchemaGenerationAction2_1 DEFAULT_SCHEMAGEN_DATABASE_ACTION = SchemaGenerationAction2_1.none;

	SchemaGenerationAction2_1 getSchemaGenScriptsAction();
	void setSchemaGenScriptsAction(SchemaGenerationAction2_1 newSchemaGenAction);
		String SCHEMAGEN_SCRIPTS_ACTION_PROPERTY = "schemaGenScriptsAction"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_SCHEMAGEN_SCRIPTS_ACTION = "javax.persistence.schema-generation.scripts.action"; //$NON-NLS-1$
	SchemaGenerationAction2_1 getDefaultSchemaGenScriptsAction();
		SchemaGenerationAction2_1 DEFAULT_SCHEMAGEN_SCRIPTS_ACTION = SchemaGenerationAction2_1.none;

	SchemaGenerationTarget2_1 getSchemaGenCreateSource();
	void setSchemaGenCreateSource(SchemaGenerationTarget2_1 newSchemaGenSource);
		String SCHEMAGEN_CREATE_SOURCE_PROPERTY = "schemaGenCreateSource"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_SCHEMAGEN_CREATE_SOURCE = "javax.persistence.schema-generation.create-source"; //$NON-NLS-1$
	SchemaGenerationTarget2_1 getDefaultSchemaGenCreateSource();
		SchemaGenerationTarget2_1 DEFAULT_SCHEMAGEN_CREATE_SOURCE = null;

	SchemaGenerationTarget2_1 getSchemaGenDropSource();
	void setSchemaGenDropSource(SchemaGenerationTarget2_1 newSchemaGenSource);
		String SCHEMAGEN_DROP_SOURCE_PROPERTY = "schemaGenDropSource"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_SCHEMAGEN_DROP_SOURCE = "javax.persistence.schema-generation.drop-source"; //$NON-NLS-1$
	SchemaGenerationTarget2_1 getDefaultSchemaGenDropSource();
		SchemaGenerationTarget2_1 DEFAULT_SCHEMAGEN_DROP_SOURCE = null;

	Boolean getCreateDatabaseSchemas();
	void setCreateDatabaseSchemas(Boolean newCreateDatabaseSchemas);
		String CREATE_DATABASE_SCHEMAS_PROPERTY = "createDatabaseSchemas"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_CREATE_DATABASE_SCHEMAS = "javax.persistence.schema-generation.create-database-schemas"; //$NON-NLS-1$
	Boolean getDefaultCreateDatabaseSchemas();
		String DEFAULT_CREATE_DATABASE_SCHEMAS_PROPERTY = "defaultCreateDatabaseSchemas"; //$NON-NLS-1$
		Boolean DEFAULT_CREATE_DATABASE_SCHEMAS = Boolean.FALSE;

	String getScriptsCreateTarget();
	void setScriptsCreateTarget(String newScriptsCreateTarget);
		String SCRIPTS_CREATE_TARGET_PROPERTY = "scriptsCreateTarget"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_SCRIPTS_CREATE_TARGET = "javax.persistence.schema-generation.scripts.create-target"; //$NON-NLS-1$
	String getDefaultScriptsCreateTarget();
		String DEFAULT_SCRIPTS_CREATE_TARGET = ""; //$NON-NLS-1$

	String getScriptsDropTarget();
	void setScriptsDropTarget(String newScriptsDropTarget);
		String SCRIPTS_DROP_TARGET_PROPERTY = "scriptsDropTarget"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_SCRIPTS_DROP_TARGET = "javax.persistence.schema-generation.scripts.drop-target"; //$NON-NLS-1$
	String getDefaultScriptsDropTarget();
		String DEFAULT_SCRIPTS_DROP_TARGET = ""; //$NON-NLS-1$

	String getDatabaseProductName();
	void setDatabaseProductName(String newDatabaseProductName);
		String DATABASE_PRODUCT_NAME_PROPERTY = "databaseProductName"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_DATABASE_PRODUCT_NAME = "javax.persistence.database-product-name"; //$NON-NLS-1$
	String getDefaultDatabaseProductName();
		String DEFAULT_DATABASE_PRODUCT_NAME = ""; //$NON-NLS-1$

	String getDatabaseMajorVersion();
	void setDatabaseMajorVersion(String newDatabaseMajorVersion);
		String DATABASE_MAJOR_VERSION_PROPERTY = "databaseMajorVersion"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_DATABASE_MAJOR_VERSION = "javax.persistence.database-major-version"; //$NON-NLS-1$
	String getDefaultDatabaseMajorVersion();
		String DEFAULT_DATABASE_MAJOR_VERSION = ""; //$NON-NLS-1$

	String getDatabaseMinorVersion();
	void setDatabaseMinorVersion(String newDatabaseMinorVersion);
		String DATABASE_MINOR_VERSION_PROPERTY = "databaseMinorVersion"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_DATABASE_MINOR_VERSION = "javax.persistence.database-minor-version"; //$NON-NLS-1$
	String getDefaultDatabaseMinorVersion();
		String DEFAULT_DATABASE_MINOR_VERSION = ""; //$NON-NLS-1$

	String getCreateScriptSource();
	void setCreateScriptSource(String newCreateScriptSource);
		String CREATE_SCRIPT_SOURCE_PROPERTY = "createScriptSource"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_CREATE_SCRIPT_SOURCE = "javax.persistence.schema-generation.create-script-source"; //$NON-NLS-1$
	String getDefaultCreateScriptSource();
		String DEFAULT_CREATE_SCRIPT_SOURCE = ""; //$NON-NLS-1$

	String getDropScriptSource();
	void setDropScriptSource(String newDropScriptSource);
		String DROP_SCRIPT_SOURCE_PROPERTY = "dropScriptSource"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_DROP_SCRIPT_SOURCE = "javax.persistence.schema-generation.drop-script-source"; //$NON-NLS-1$
	String getDefaultDropScriptSource();
		String DEFAULT_DROP_SCRIPT_SOURCE = ""; //$NON-NLS-1$

	String getConnection();
	void setConnection(String newConnection);
		String CONNECTION_PROPERTY = "connection"; //$NON-NLS-1$
		// Property key string
		String PERSISTENCE_CONNECTION = "javax.persistence.schema-generation.connection"; //$NON-NLS-1$
	String getDefaultConnection();
		String DEFAULT_CONNECTION = ""; //$NON-NLS-1$

	// Data Loading 
	String getSqlLoadScriptSource();
	void setSqlLoadScriptSource(String newSqlLoadScriptSource);
		String SQL_LOAD_SCRIPT_SOURCE_PROPERTY = "sqlLoadScriptSource"; //$NON-NLS-1$
		// Property key
		String PERSISTENCE_SQL_LOAD_SCRIPT_SOURCE = "javax.persistence.sql-load-script-source"; //$NON-NLS-1$
	String getDefaultSqlLoadScriptSource();
		String DEFAULT_SQL_LOAD_SCRIPT_SOURCE = ""; //$NON-NLS-1$
}
