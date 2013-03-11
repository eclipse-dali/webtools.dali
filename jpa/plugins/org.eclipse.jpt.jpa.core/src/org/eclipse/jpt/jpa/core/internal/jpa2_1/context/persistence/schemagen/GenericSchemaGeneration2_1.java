/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.persistence.schemagen;

import java.util.Map;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.PersistenceUnit2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationAction2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationTarget;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;

public class GenericSchemaGeneration2_1
	extends AbstractPersistenceUnitProperties
	implements SchemaGeneration2_1
{
	// ********** GenericSchemaGeneration2_1 properties **********
	private SchemaGenerationAction2_1 schemaGenDatabaseAction;
	private SchemaGenerationAction2_1 schemaGenScriptsAction;
	private SchemaGenerationTarget schemaGenCreateSource;
	private SchemaGenerationTarget schemaGenDropSource;
	private Boolean createDatabaseSchemas;
	private String scriptsCreateTarget;
	private String scriptsDropTarget;
	private String databaseProductName;
	private String databaseMajorVersion;
	private String databaseMinorVersion;
	private String createScriptSource;
	private String dropScriptSource;
	private String connection;
	private String sqlLoadScriptSource;

	// ********** constructors **********
	public GenericSchemaGeneration2_1(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		this.schemaGenDatabaseAction = 
			this.getEnumValue(PERSISTENCE_SCHEMAGEN_DATABASE_ACTION, SchemaGenerationAction2_1.values());
		this.schemaGenScriptsAction = 
			this.getEnumValue(PERSISTENCE_SCHEMAGEN_SCRIPTS_ACTION, SchemaGenerationAction2_1.values());
		this.schemaGenCreateSource = 
			this.getEnumValue(PERSISTENCE_SCHEMAGEN_CREATE_SOURCE, SchemaGenerationTarget.values());
		this.schemaGenDropSource = 
			this.getEnumValue(PERSISTENCE_SCHEMAGEN_DROP_SOURCE, SchemaGenerationTarget.values());
		this.createDatabaseSchemas = 
			this.getBooleanValue(PERSISTENCE_CREATE_DATABASE_SCHEMAS);
		this.scriptsCreateTarget = 
			this.getStringValue(PERSISTENCE_SCRIPTS_CREATE_TARGET);
		this.scriptsDropTarget = 
			this.getStringValue(PERSISTENCE_SCRIPTS_DROP_TARGET);
		this.databaseProductName = 
			this.getStringValue(PERSISTENCE_DATABASE_PRODUCT_NAME);
		this.databaseMajorVersion = 
			this.getStringValue(PERSISTENCE_DATABASE_MAJOR_VERSION);
		this.databaseMinorVersion = 
			this.getStringValue(PERSISTENCE_DATABASE_MINOR_VERSION);
		this.createScriptSource = 
			this.getStringValue(PERSISTENCE_CREATE_SCRIPT_SOURCE);
		this.dropScriptSource = 
			this.getStringValue(PERSISTENCE_DROP_SCRIPT_SOURCE);
		this.connection = 
			this.getStringValue(PERSISTENCE_CONNECTION);
		this.sqlLoadScriptSource = 
			this.getStringValue(PERSISTENCE_SQL_LOAD_SCRIPT_SOURCE);
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		if (propertyName.equals(PERSISTENCE_SCHEMAGEN_DATABASE_ACTION)) {
			this.schemaGenDatabaseActionChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_SCHEMAGEN_SCRIPTS_ACTION)) {
			this.schemaGenScriptsActionChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_SCHEMAGEN_CREATE_SOURCE)) {
			this.schemaGenCreateSourceChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_SCHEMAGEN_DROP_SOURCE)) {
			this.schemaGenDropSourceChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_CREATE_DATABASE_SCHEMAS)) {
			this.createDatabaseSchemasChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_SCRIPTS_CREATE_TARGET)) {
			this.scriptsCreateTargetChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_SCRIPTS_DROP_TARGET)) {
			this.scriptsDropTargetChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_DATABASE_PRODUCT_NAME)) {
			this.databaseProductNameChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_DATABASE_MAJOR_VERSION)) {
			this.databaseMajorVersionChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_DATABASE_MINOR_VERSION)) {
			this.databaseMinorVersionChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_CREATE_SCRIPT_SOURCE)) {
			this.createScriptSourceChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_DROP_SCRIPT_SOURCE)) {
			this.dropScriptSourceChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_CONNECTION)) {
			this.connectionChanged(newValue);
		}
		else if (propertyName.equals(PERSISTENCE_SQL_LOAD_SCRIPT_SOURCE)) {
			this.sqlLoadScriptSourceChanged(newValue);
		}
	}
	
	public void propertyRemoved(String propertyName) {
		if (propertyName.equals(PERSISTENCE_SCHEMAGEN_DATABASE_ACTION)) {
			this.schemaGenDatabaseActionChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_SCHEMAGEN_SCRIPTS_ACTION)) {
			this.schemaGenScriptsActionChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_SCHEMAGEN_CREATE_SOURCE)) {
			this.schemaGenCreateSourceChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_SCHEMAGEN_DROP_SOURCE)) {
			this.schemaGenDropSourceChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_CREATE_DATABASE_SCHEMAS)) {
			this.createDatabaseSchemasChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_SCRIPTS_CREATE_TARGET)) {
			this.scriptsCreateTargetChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_SCRIPTS_DROP_TARGET)) {
			this.scriptsDropTargetChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_DATABASE_PRODUCT_NAME)) {
			this.databaseProductNameChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_DATABASE_MAJOR_VERSION)) {
			this.databaseMajorVersionChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_DATABASE_MINOR_VERSION)) {
			this.databaseMinorVersionChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_CREATE_SCRIPT_SOURCE)) {
			this.createScriptSourceChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_DROP_SCRIPT_SOURCE)) {
			this.dropScriptSourceChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_CONNECTION)) {
			this.connectionChanged(null);
		}
		else if (propertyName.equals(PERSISTENCE_SQL_LOAD_SCRIPT_SOURCE)) {
			this.sqlLoadScriptSourceChanged(null);
		}
	}

	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = PU property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			PERSISTENCE_SCHEMAGEN_DATABASE_ACTION,
			SCHEMAGEN_DATABASE_ACTION_PROPERTY);
		propertyNames.put(
			PERSISTENCE_SCHEMAGEN_SCRIPTS_ACTION,
			SCHEMAGEN_SCRIPTS_ACTION_PROPERTY);
		propertyNames.put(
			PERSISTENCE_SCHEMAGEN_CREATE_SOURCE,
			SCHEMAGEN_CREATE_SOURCE_PROPERTY);
		propertyNames.put(
			PERSISTENCE_SCHEMAGEN_DROP_SOURCE,
			SCHEMAGEN_DROP_SOURCE_PROPERTY);
		propertyNames.put(
			PERSISTENCE_CREATE_DATABASE_SCHEMAS,
			CREATE_DATABASE_SCHEMAS_PROPERTY);
		propertyNames.put(
			PERSISTENCE_SCRIPTS_CREATE_TARGET,
			SCRIPTS_CREATE_TARGET_PROPERTY);
		propertyNames.put(
			PERSISTENCE_SCRIPTS_DROP_TARGET,
			SCRIPTS_DROP_TARGET_PROPERTY);
		propertyNames.put(
			PERSISTENCE_DATABASE_PRODUCT_NAME,
			DATABASE_PRODUCT_NAME_PROPERTY);
		propertyNames.put(
			PERSISTENCE_DATABASE_MAJOR_VERSION,
			DATABASE_MAJOR_VERSION_PROPERTY);
		propertyNames.put(
			PERSISTENCE_DATABASE_MINOR_VERSION,
			DATABASE_MINOR_VERSION_PROPERTY);
		propertyNames.put(
			PERSISTENCE_CREATE_SCRIPT_SOURCE,
			CREATE_SCRIPT_SOURCE_PROPERTY);
		propertyNames.put(
			PERSISTENCE_DROP_SCRIPT_SOURCE,
			DROP_SCRIPT_SOURCE_PROPERTY);
		propertyNames.put(
			PERSISTENCE_CONNECTION,
			CONNECTION_PROPERTY);
		propertyNames.put(
			PERSISTENCE_SQL_LOAD_SCRIPT_SOURCE,
			SQL_LOAD_SCRIPT_SOURCE_PROPERTY);
	}

	@Override
	public PersistenceUnit2_1 getPersistenceUnit() {
		return (PersistenceUnit2_1) super.getPersistenceUnit();
	}

	// ********** SchemaGenDatabaseAction **********
	
	public SchemaGenerationAction2_1 getSchemaGenDatabaseAction() {
		return this.schemaGenDatabaseAction;
	}
	
	public void setSchemaGenDatabaseAction(SchemaGenerationAction2_1 newSchemaGenDatabaseAction) {
		SchemaGenerationAction2_1 old = this.schemaGenDatabaseAction;
		this.schemaGenDatabaseAction = newSchemaGenDatabaseAction;
		this.putProperty(SCHEMAGEN_DATABASE_ACTION_PROPERTY, newSchemaGenDatabaseAction);
		this.firePropertyChanged(SCHEMAGEN_DATABASE_ACTION_PROPERTY, old, newSchemaGenDatabaseAction);
	}

	private void schemaGenDatabaseActionChanged(String stringValue) {
		SchemaGenerationAction2_1 newValue = this.getEnumValueOf(stringValue, SchemaGenerationAction2_1.values());
		SchemaGenerationAction2_1 old = this.schemaGenDatabaseAction;
		this.schemaGenDatabaseAction = newValue;
		this.firePropertyChanged(SCHEMAGEN_DATABASE_ACTION_PROPERTY, old, newValue);
	}
	
	public SchemaGenerationAction2_1 getDefaultSchemaGenDatabaseAction() {
		return DEFAULT_SCHEMAGEN_DATABASE_ACTION;
	}

	// ********** SchemaGenScriptsAction **********
	
	public SchemaGenerationAction2_1 getSchemaGenScriptsAction() {
		return this.schemaGenScriptsAction;
	}
	
	public void setSchemaGenScriptsAction(SchemaGenerationAction2_1 newSchemaGenScriptsAction) {
		SchemaGenerationAction2_1 old = this.schemaGenScriptsAction;
		this.schemaGenScriptsAction = newSchemaGenScriptsAction;
		this.putProperty(SCHEMAGEN_SCRIPTS_ACTION_PROPERTY, newSchemaGenScriptsAction);
		this.firePropertyChanged(SCHEMAGEN_SCRIPTS_ACTION_PROPERTY, old, newSchemaGenScriptsAction);
	}

	private void schemaGenScriptsActionChanged(String stringValue) {
		SchemaGenerationAction2_1 newValue = this.getEnumValueOf(stringValue, SchemaGenerationAction2_1.values());
		SchemaGenerationAction2_1 old = this.schemaGenScriptsAction;
		this.schemaGenScriptsAction = newValue;
		this.firePropertyChanged(SCHEMAGEN_SCRIPTS_ACTION_PROPERTY, old, newValue);
	}
	
	public SchemaGenerationAction2_1 getDefaultSchemaGenScriptsAction() {
		return DEFAULT_SCHEMAGEN_SCRIPTS_ACTION;
	}

	// ********** SchemaGenCreateSource **********
	
	public SchemaGenerationTarget getSchemaGenCreateSource() {
		return this.schemaGenCreateSource;
	}
	
	public void setSchemaGenCreateSource(SchemaGenerationTarget newSchemaGenCreateSource) {
		SchemaGenerationTarget old = this.schemaGenCreateSource;
		this.schemaGenCreateSource = newSchemaGenCreateSource;
		this.putProperty(SCHEMAGEN_CREATE_SOURCE_PROPERTY, newSchemaGenCreateSource);
		this.firePropertyChanged(SCHEMAGEN_CREATE_SOURCE_PROPERTY, old, newSchemaGenCreateSource);
	}

	private void schemaGenCreateSourceChanged(String stringValue) {
		SchemaGenerationTarget newValue = this.getEnumValueOf(stringValue, SchemaGenerationTarget.values());
		SchemaGenerationTarget old = this.schemaGenCreateSource;
		this.schemaGenCreateSource = newValue;
		this.firePropertyChanged(SCHEMAGEN_CREATE_SOURCE_PROPERTY, old, newValue);
	}
	
	public SchemaGenerationTarget getDefaultSchemaGenCreateSource() {
		return DEFAULT_SCHEMAGEN_CREATE_SOURCE;
	}

	// ********** SchemaGenDropSource **********
	
	public SchemaGenerationTarget getSchemaGenDropSource() {
		return this.schemaGenDropSource;
	}
	
	public void setSchemaGenDropSource(SchemaGenerationTarget newSchemaGenDropSource) {
		SchemaGenerationTarget old = this.schemaGenDropSource;
		this.schemaGenDropSource = newSchemaGenDropSource;
		this.putProperty(SCHEMAGEN_DROP_SOURCE_PROPERTY, newSchemaGenDropSource);
		this.firePropertyChanged(SCHEMAGEN_DROP_SOURCE_PROPERTY, old, newSchemaGenDropSource);
	}

	private void schemaGenDropSourceChanged(String stringValue) {
		SchemaGenerationTarget newValue = this.getEnumValueOf(stringValue, SchemaGenerationTarget.values());
		SchemaGenerationTarget old = this.schemaGenDropSource;
		this.schemaGenDropSource = newValue;
		this.firePropertyChanged(SCHEMAGEN_DROP_SOURCE_PROPERTY, old, newValue);
	}
	
	public SchemaGenerationTarget getDefaultSchemaGenDropSource() {
		return DEFAULT_SCHEMAGEN_DROP_SOURCE;
	}

	// ********** CreateDatabaseSchemas **********
	public Boolean getCreateDatabaseSchemas() {
		return this.createDatabaseSchemas;
	}

	public void setCreateDatabaseSchemas(Boolean newCreateDatabaseSchemas) {
		Boolean old = this.createDatabaseSchemas;
		this.createDatabaseSchemas = newCreateDatabaseSchemas;
		this.putProperty(CREATE_DATABASE_SCHEMAS_PROPERTY, newCreateDatabaseSchemas);
		this.firePropertyChanged(CREATE_DATABASE_SCHEMAS_PROPERTY, old, newCreateDatabaseSchemas);
	}
	
	private void createDatabaseSchemasChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.createDatabaseSchemas;
		this.createDatabaseSchemas = newValue;
		this.firePropertyChanged(CREATE_DATABASE_SCHEMAS_PROPERTY, old, newValue);
	}

	public Boolean getDefaultCreateDatabaseSchemas() {
		return DEFAULT_CREATE_DATABASE_SCHEMAS;
	}

	// ********** ScriptsCreateTarget **********
	public String getScriptsCreateTarget() {
		return this.scriptsCreateTarget;
	}

	public void setScriptsCreateTarget(String newScriptsCreateTarget) {
		String old = this.scriptsCreateTarget;
		this.scriptsCreateTarget = newScriptsCreateTarget;
		this.putProperty(SCRIPTS_CREATE_TARGET_PROPERTY, newScriptsCreateTarget);
		this.firePropertyChanged(SCRIPTS_CREATE_TARGET_PROPERTY, old, newScriptsCreateTarget);
	}

	private void scriptsCreateTargetChanged(String newValue) {
		String old = this.scriptsCreateTarget;
		this.scriptsCreateTarget = newValue;
		this.firePropertyChanged(SCRIPTS_CREATE_TARGET_PROPERTY, old, newValue);
	}

	public String getDefaultScriptsCreateTarget() {
		return DEFAULT_SCRIPTS_CREATE_TARGET;
	}

	// ********** ScriptsDropTarget **********
	public String getScriptsDropTarget() {
		return this.scriptsDropTarget;
	}

	public void setScriptsDropTarget(String newScriptsDropTarget) {
		String old = this.scriptsDropTarget;
		this.scriptsDropTarget = newScriptsDropTarget;
		this.putProperty(SCRIPTS_DROP_TARGET_PROPERTY, newScriptsDropTarget);
		this.firePropertyChanged(SCRIPTS_DROP_TARGET_PROPERTY, old, newScriptsDropTarget);
	}

	private void scriptsDropTargetChanged(String newValue) {
		String old = this.scriptsDropTarget;
		this.scriptsDropTarget = newValue;
		this.firePropertyChanged(SCRIPTS_DROP_TARGET_PROPERTY, old, newValue);
	}

	public String getDefaultScriptsDropTarget() {
		return DEFAULT_SCRIPTS_DROP_TARGET;
	}

	// ********** DatabaseProductName **********
	public String getDatabaseProductName() {
		return this.databaseProductName;
	}

	public void setDatabaseProductName(String newDatabaseProductName) {
		String old = this.databaseProductName;
		this.databaseProductName = newDatabaseProductName;
		this.putProperty(DATABASE_PRODUCT_NAME_PROPERTY, newDatabaseProductName);
		this.firePropertyChanged(DATABASE_PRODUCT_NAME_PROPERTY, old, newDatabaseProductName);
	}

	private void databaseProductNameChanged(String newValue) {
		String old = this.databaseProductName;
		this.databaseProductName = newValue;
		this.firePropertyChanged(DATABASE_PRODUCT_NAME_PROPERTY, old, newValue);
	}

	public String getDefaultDatabaseProductName() {
		return DEFAULT_DATABASE_PRODUCT_NAME;
	}

	// ********** DatabaseMajorVersion **********
	public String getDatabaseMajorVersion() {
		return this.databaseMajorVersion;
	}

	public void setDatabaseMajorVersion(String newDatabaseMajorVersion) {
		String old = this.databaseMajorVersion;
		this.databaseMajorVersion = newDatabaseMajorVersion;
		this.putProperty(DATABASE_MAJOR_VERSION_PROPERTY, newDatabaseMajorVersion);
		this.firePropertyChanged(DATABASE_MAJOR_VERSION_PROPERTY, old, newDatabaseMajorVersion);
	}

	private void databaseMajorVersionChanged(String newValue) {
		String old = this.databaseMajorVersion;
		this.databaseMajorVersion = newValue;
		this.firePropertyChanged(DATABASE_MAJOR_VERSION_PROPERTY, old, newValue);
	}

	public String getDefaultDatabaseMajorVersion() {
		return DEFAULT_DATABASE_MAJOR_VERSION;
	}

	// ********** DatabaseMinorVersion **********
	public String getDatabaseMinorVersion() {
		return this.databaseMinorVersion;
	}

	public void setDatabaseMinorVersion(String newDatabaseMinorVersion) {
		String old = this.databaseMinorVersion;
		this.databaseMinorVersion = newDatabaseMinorVersion;
		this.putProperty(DATABASE_MINOR_VERSION_PROPERTY, newDatabaseMinorVersion);
		this.firePropertyChanged(DATABASE_MINOR_VERSION_PROPERTY, old, newDatabaseMinorVersion);
	}

	private void databaseMinorVersionChanged(String newValue) {
		String old = this.databaseMinorVersion;
		this.databaseMinorVersion = newValue;
		this.firePropertyChanged(DATABASE_MINOR_VERSION_PROPERTY, old, newValue);
	}

	public String getDefaultDatabaseMinorVersion() {
		return DEFAULT_DATABASE_MINOR_VERSION;
	}

	// ********** CreateScriptSource **********
	public String getCreateScriptSource() {
		return this.createScriptSource;
	}

	public void setCreateScriptSource(String newCreateScriptSource) {
		String old = this.createScriptSource;
		this.createScriptSource = newCreateScriptSource;
		this.putProperty(CREATE_SCRIPT_SOURCE_PROPERTY, newCreateScriptSource);
		this.firePropertyChanged(CREATE_SCRIPT_SOURCE_PROPERTY, old, newCreateScriptSource);
	}

	private void createScriptSourceChanged(String newValue) {
		String old = this.createScriptSource;
		this.createScriptSource = newValue;
		this.firePropertyChanged(CREATE_SCRIPT_SOURCE_PROPERTY, old, newValue);
	}

	public String getDefaultCreateScriptSource() {
		return DEFAULT_CREATE_SCRIPT_SOURCE;
	}

	// ********** DropScriptSource **********
	public String getDropScriptSource() {
		return this.dropScriptSource;
	}

	public void setDropScriptSource(String newDropScriptSource) {
		String old = this.dropScriptSource;
		this.dropScriptSource = newDropScriptSource;
		this.putProperty(DROP_SCRIPT_SOURCE_PROPERTY, newDropScriptSource);
		this.firePropertyChanged(DROP_SCRIPT_SOURCE_PROPERTY, old, newDropScriptSource);
	}

	private void dropScriptSourceChanged(String newValue) {
		String old = this.dropScriptSource;
		this.dropScriptSource = newValue;
		this.firePropertyChanged(DROP_SCRIPT_SOURCE_PROPERTY, old, newValue);
	}

	public String getDefaultDropScriptSource() {
		return DEFAULT_DROP_SCRIPT_SOURCE;
	}

	// ********** Connection **********
	public String getConnection() {
		return this.connection;
	}

	public void setConnection(String newConnection) {
		String old = this.connection;
		this.connection = newConnection;
		this.putProperty(CONNECTION_PROPERTY, newConnection);
		this.firePropertyChanged(CONNECTION_PROPERTY, old, newConnection);
	}

	private void connectionChanged(String newValue) {
		String old = this.connection;
		this.connection = newValue;
		this.firePropertyChanged(CONNECTION_PROPERTY, old, newValue);
	}

	public String getDefaultConnection() {
		return DEFAULT_CONNECTION;
	}

	// ********** SqlLoadScriptSource **********
	public String getSqlLoadScriptSource() {
		return this.sqlLoadScriptSource;
	}

	public void setSqlLoadScriptSource(String newSqlLoadScriptSource) {
		String old = this.sqlLoadScriptSource;
		this.sqlLoadScriptSource = newSqlLoadScriptSource;
		this.putProperty(SQL_LOAD_SCRIPT_SOURCE_PROPERTY, newSqlLoadScriptSource);
		this.firePropertyChanged(SQL_LOAD_SCRIPT_SOURCE_PROPERTY, old, newSqlLoadScriptSource);
	}

	private void sqlLoadScriptSourceChanged(String newValue) {
		String old = this.sqlLoadScriptSource;
		this.sqlLoadScriptSource = newValue;
		this.firePropertyChanged(SQL_LOAD_SCRIPT_SOURCE_PROPERTY, old, newValue);
	}

	public String getDefaultSqlLoadScriptSource() {
		return DEFAULT_SQL_LOAD_SCRIPT_SOURCE;
	}
}
