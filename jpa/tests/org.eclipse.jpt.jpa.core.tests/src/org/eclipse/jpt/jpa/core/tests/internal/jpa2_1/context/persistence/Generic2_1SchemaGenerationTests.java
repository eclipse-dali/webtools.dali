/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.persistence;

import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationAction2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.SchemaGenerationTarget2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;

/**
 *  Generic2_1SchemaGenerationTests
 */
public class Generic2_1SchemaGenerationTests extends PersistenceUnit2_1TestCase
{
	SchemaGeneration2_1 schemaGeneration;

	public static final String SCHEMAGEN_DATABASE_ACTION_KEY = SchemaGeneration2_1.PERSISTENCE_SCHEMAGEN_DATABASE_ACTION;
	public static final SchemaGenerationAction2_1 SCHEMAGEN_DATABASE_ACTION_TEST_VALUE = SchemaGenerationAction2_1.create;
	public static final SchemaGenerationAction2_1 SCHEMAGEN_DATABASE_ACTION_TEST_VALUE_2 = SchemaGenerationAction2_1.drop_and_create;

	public static final String SCHEMAGEN_SCRIPTS_ACTION_KEY = SchemaGeneration2_1.PERSISTENCE_SCHEMAGEN_SCRIPTS_ACTION;
	public static final SchemaGenerationAction2_1 SCHEMAGEN_SCRIPTS_ACTION_TEST_VALUE = SchemaGenerationAction2_1.drop_and_create;
	public static final SchemaGenerationAction2_1 SCHEMAGEN_SCRIPTS_ACTION_TEST_VALUE_2 = SchemaGenerationAction2_1.drop;

	public static final String SCHEMAGEN_CREATE_SOURCE_KEY = SchemaGeneration2_1.PERSISTENCE_SCHEMAGEN_CREATE_SOURCE;
	public static final SchemaGenerationTarget2_1 SCHEMAGEN_CREATE_SOURCE_TEST_VALUE = SchemaGenerationTarget2_1.metadata;
	public static final SchemaGenerationTarget2_1 SCHEMAGEN_CREATE_SOURCE_TEST_VALUE_2 = SchemaGenerationTarget2_1.metadata_then_script;

	public static final String SCHEMAGEN_DROP_SOURCE_KEY = SchemaGeneration2_1.PERSISTENCE_SCHEMAGEN_DROP_SOURCE;
	public static final SchemaGenerationTarget2_1 SCHEMAGEN_DROP_SOURCE_TEST_VALUE = SchemaGenerationTarget2_1.script_then_metadata;
	public static final SchemaGenerationTarget2_1 SCHEMAGEN_DROP_SOURCE_TEST_VALUE_2 = SchemaGenerationTarget2_1.script;
	
	public static final String CREATE_DATABASE_SCHEMAS_KEY = SchemaGeneration2_1.PERSISTENCE_CREATE_DATABASE_SCHEMAS;
	public static final Boolean CREATE_DATABASE_SCHEMAS_TEST_VALUE = true;
	public static final Boolean CREATE_DATABASE_SCHEMAS_TEST_VALUE_2 = ! CREATE_DATABASE_SCHEMAS_TEST_VALUE;
	
	public static final String SCRIPTS_CREATE_TARGET_KEY = SchemaGeneration2_1.PERSISTENCE_SCRIPTS_CREATE_TARGET;
	public static final String SCRIPTS_CREATE_TARGET_TEST_VALUE = "ScriptsCreateTarget";
	public static final String SCRIPTS_CREATE_TARGET_TEST_VALUE_2 = "ScriptsCreateTarget_2";
	
	public static final String SCRIPTS_DROP_TARGET_KEY = SchemaGeneration2_1.PERSISTENCE_SCRIPTS_DROP_TARGET;
	public static final String SCRIPTS_DROP_TARGET_TEST_VALUE = "ScriptsDropTarget";
	public static final String SCRIPTS_DROP_TARGET_TEST_VALUE_2 = "ScriptsDropTarget_2";
	
	public static final String DATABASE_PRODUCT_NAME_KEY = SchemaGeneration2_1.PERSISTENCE_DATABASE_PRODUCT_NAME;
	public static final String DATABASE_PRODUCT_NAME_TEST_VALUE = "DatabaseProductName";
	public static final String DATABASE_PRODUCT_NAME_TEST_VALUE_2 = "DatabaseProductName_2";
	
	public static final String DATABASE_MAJOR_VERSION_KEY = SchemaGeneration2_1.PERSISTENCE_DATABASE_MAJOR_VERSION;
	public static final String DATABASE_MAJOR_VERSION_TEST_VALUE = "DatabaseMajorVersion";
	public static final String DATABASE_MAJOR_VERSION_TEST_VALUE_2 = "DatabaseMajorVersion_2";
	
	public static final String DATABASE_MINOR_VERSION_KEY = SchemaGeneration2_1.PERSISTENCE_DATABASE_MINOR_VERSION;
	public static final String DATABASE_MINOR_VERSION_TEST_VALUE = "DatabaseMinorVersion";
	public static final String DATABASE_MINOR_VERSION_TEST_VALUE_2 = "DatabaseMinorVersion_2";
	
	public static final String CREATE_SCRIPT_SOURCE_KEY = SchemaGeneration2_1.PERSISTENCE_CREATE_SCRIPT_SOURCE;
	public static final String CREATE_SCRIPT_SOURCE_TEST_VALUE = "CreateScriptSource";
	public static final String CREATE_SCRIPT_SOURCE_TEST_VALUE_2 = "CreateScriptSource_2";
	
	public static final String DROP_SCRIPT_SOURCE_KEY = SchemaGeneration2_1.PERSISTENCE_DROP_SCRIPT_SOURCE;
	public static final String DROP_SCRIPT_SOURCE_TEST_VALUE = "DropScriptSource";
	public static final String DROP_SCRIPT_SOURCE_TEST_VALUE_2 = "DropScriptSource_2";
	
	public static final String CONNECTION_KEY = SchemaGeneration2_1.PERSISTENCE_CONNECTION;
	public static final String CONNECTION_TEST_VALUE = "Connection";
	public static final String CONNECTION_TEST_VALUE_2 = "Connection_2";
	
	public static final String SQL_LOAD_SCRIPT_SOURCE_KEY = SchemaGeneration2_1.PERSISTENCE_SQL_LOAD_SCRIPT_SOURCE;
	public static final String SQL_LOAD_SCRIPT_SOURCE_TEST_VALUE = "SqlLoadScriptSource";
	public static final String SQL_LOAD_SCRIPT_SOURCE_TEST_VALUE_2 = "SqlLoadScriptSource_2";


	// ********** constructors **********
	public Generic2_1SchemaGenerationTests(String name) {
		super(name);
	}

	// ********** behavior **********
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.schemaGeneration = (SchemaGeneration2_1) this.subject.getSchemaGeneration();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();

		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.SCHEMAGEN_DATABASE_ACTION_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.SCHEMAGEN_SCRIPTS_ACTION_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.SCHEMAGEN_CREATE_SOURCE_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.SCHEMAGEN_DROP_SOURCE_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.CREATE_DATABASE_SCHEMAS_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.SCRIPTS_CREATE_TARGET_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.SCRIPTS_DROP_TARGET_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.DATABASE_PRODUCT_NAME_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.DATABASE_MAJOR_VERSION_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.DATABASE_MINOR_VERSION_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.CREATE_SCRIPT_SOURCE_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.DROP_SCRIPT_SOURCE_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.CONNECTION_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration2_1.SQL_LOAD_SCRIPT_SOURCE_PROPERTY, propertyChangeListener);

		this.clearEvent();
	}

	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 14;
		this.propertiesTotal = this.modelPropertiesSizeOriginal; 
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;

		this.persistenceUnitSetProperty(SCHEMAGEN_DATABASE_ACTION_KEY, SCHEMAGEN_DATABASE_ACTION_TEST_VALUE);
		this.persistenceUnitSetProperty(SCHEMAGEN_SCRIPTS_ACTION_KEY, SCHEMAGEN_SCRIPTS_ACTION_TEST_VALUE);
		this.persistenceUnitSetProperty(SCHEMAGEN_CREATE_SOURCE_KEY, SCHEMAGEN_CREATE_SOURCE_TEST_VALUE);
		this.persistenceUnitSetProperty(SCHEMAGEN_DROP_SOURCE_KEY, SCHEMAGEN_DROP_SOURCE_TEST_VALUE);
		this.persistenceUnitSetProperty(CREATE_DATABASE_SCHEMAS_KEY, CREATE_DATABASE_SCHEMAS_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(SCRIPTS_CREATE_TARGET_KEY, SCRIPTS_CREATE_TARGET_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(SCRIPTS_DROP_TARGET_KEY, SCRIPTS_DROP_TARGET_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(DATABASE_PRODUCT_NAME_KEY, DATABASE_PRODUCT_NAME_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(DATABASE_MAJOR_VERSION_KEY, DATABASE_MAJOR_VERSION_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(DATABASE_MINOR_VERSION_KEY, DATABASE_MINOR_VERSION_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(CREATE_SCRIPT_SOURCE_KEY, CREATE_SCRIPT_SOURCE_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(DROP_SCRIPT_SOURCE_KEY, DROP_SCRIPT_SOURCE_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(CONNECTION_KEY, CONNECTION_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(SQL_LOAD_SCRIPT_SOURCE_KEY, SQL_LOAD_SCRIPT_SOURCE_TEST_VALUE.toString());
	}
	
	// ********** SchemaGenDatabaseAction tests **********
	public void testSetSchemaGenDatabaseAction() throws Exception {
		this.verifyModelInitialized(
			SCHEMAGEN_DATABASE_ACTION_KEY,
			SCHEMAGEN_DATABASE_ACTION_TEST_VALUE);
		this.verifySetProperty(
			SCHEMAGEN_DATABASE_ACTION_KEY,
			SCHEMAGEN_DATABASE_ACTION_TEST_VALUE,
			SCHEMAGEN_DATABASE_ACTION_TEST_VALUE_2);
	}

	public void testAddRemoveSchemaGenDatabaseAction() throws Exception {
		this.verifyAddRemoveProperty(
			SCHEMAGEN_DATABASE_ACTION_KEY,
			SCHEMAGEN_DATABASE_ACTION_TEST_VALUE,
			SCHEMAGEN_DATABASE_ACTION_TEST_VALUE_2);
	}
	
	// ********** SchemaGenScriptsAction tests **********
	public void testSetSchemaGenScriptsAction() throws Exception {
		this.verifyModelInitialized(
			SCHEMAGEN_SCRIPTS_ACTION_KEY,
			SCHEMAGEN_SCRIPTS_ACTION_TEST_VALUE);
		this.verifySetProperty(
			SCHEMAGEN_SCRIPTS_ACTION_KEY,
			SCHEMAGEN_SCRIPTS_ACTION_TEST_VALUE,
			SCHEMAGEN_SCRIPTS_ACTION_TEST_VALUE_2);
	}

	public void testAddRemoveSchemaGenScriptsAction() throws Exception {
		this.verifyAddRemoveProperty(
			SCHEMAGEN_SCRIPTS_ACTION_KEY,
			SCHEMAGEN_SCRIPTS_ACTION_TEST_VALUE,
			SCHEMAGEN_SCRIPTS_ACTION_TEST_VALUE_2);
	}
	
	// ********** SchemaGenCreateSource tests **********
	public void testSetSchemaGenCreateSource() throws Exception {
		this.verifyModelInitialized(
			SCHEMAGEN_CREATE_SOURCE_KEY,
			SCHEMAGEN_CREATE_SOURCE_TEST_VALUE);
		this.verifySetProperty(
			SCHEMAGEN_CREATE_SOURCE_KEY,
			SCHEMAGEN_CREATE_SOURCE_TEST_VALUE,
			SCHEMAGEN_CREATE_SOURCE_TEST_VALUE_2);
	}

	public void testAddRemoveSchemaGenCreateSource() throws Exception {
		this.verifyAddRemoveProperty(
			SCHEMAGEN_CREATE_SOURCE_KEY,
			SCHEMAGEN_CREATE_SOURCE_TEST_VALUE,
			SCHEMAGEN_CREATE_SOURCE_TEST_VALUE_2);
	}
	
	// ********** SchemaGenDropSource tests **********
	public void testSetSchemaGenDropSource() throws Exception {
		this.verifyModelInitialized(
			SCHEMAGEN_DROP_SOURCE_KEY,
			SCHEMAGEN_DROP_SOURCE_TEST_VALUE);
		this.verifySetProperty(
			SCHEMAGEN_DROP_SOURCE_KEY,
			SCHEMAGEN_DROP_SOURCE_TEST_VALUE,
			SCHEMAGEN_DROP_SOURCE_TEST_VALUE_2);
	}

	public void testAddRemoveSchemaGenDropSource() throws Exception {
		this.verifyAddRemoveProperty(
			SCHEMAGEN_DROP_SOURCE_KEY,
			SCHEMAGEN_DROP_SOURCE_TEST_VALUE,
			SCHEMAGEN_DROP_SOURCE_TEST_VALUE_2);
	}


	// ********** CreateDatabaseSchemas tests **********
	public void testSetCreateDatabaseSchemas() throws Exception {
		this.verifyModelInitialized(
			CREATE_DATABASE_SCHEMAS_KEY,
			CREATE_DATABASE_SCHEMAS_TEST_VALUE);
		this.verifySetProperty(
			CREATE_DATABASE_SCHEMAS_KEY,
			CREATE_DATABASE_SCHEMAS_TEST_VALUE,
			CREATE_DATABASE_SCHEMAS_TEST_VALUE_2);
	}

	public void testAddRemoveCreateDatabaseSchemas() throws Exception {
		this.verifyAddRemoveProperty(
			CREATE_DATABASE_SCHEMAS_KEY,
			CREATE_DATABASE_SCHEMAS_TEST_VALUE,
			CREATE_DATABASE_SCHEMAS_TEST_VALUE_2);
	}

	// ********** ScriptsCreateTarget tests **********
	public void testSetScriptsCreateTarget() throws Exception {
		this.verifyModelInitialized(
			SCRIPTS_CREATE_TARGET_KEY,
			SCRIPTS_CREATE_TARGET_TEST_VALUE);
		this.verifySetProperty(
			SCRIPTS_CREATE_TARGET_KEY,
			SCRIPTS_CREATE_TARGET_TEST_VALUE,
			SCRIPTS_CREATE_TARGET_TEST_VALUE_2);
	}

	public void testAddRemoveScriptsCreateTarget() throws Exception {
		this.verifyAddRemoveProperty(
			SCRIPTS_CREATE_TARGET_KEY,
			SCRIPTS_CREATE_TARGET_TEST_VALUE,
			SCRIPTS_CREATE_TARGET_TEST_VALUE_2);
	}

	// ********** ScriptsDropTarget tests **********
	public void testSetScriptsDropTarget() throws Exception {
		this.verifyModelInitialized(
			SCRIPTS_DROP_TARGET_KEY,
			SCRIPTS_DROP_TARGET_TEST_VALUE);
		this.verifySetProperty(
			SCRIPTS_DROP_TARGET_KEY,
			SCRIPTS_DROP_TARGET_TEST_VALUE,
			SCRIPTS_DROP_TARGET_TEST_VALUE_2);
	}

	public void testAddRemoveScriptsDropTarget() throws Exception {
		this.verifyAddRemoveProperty(
			SCRIPTS_DROP_TARGET_KEY,
			SCRIPTS_DROP_TARGET_TEST_VALUE,
			SCRIPTS_DROP_TARGET_TEST_VALUE_2);
	}

	// ********** DatabaseProductName tests **********
	public void testSetDatabaseProductName() throws Exception {
		this.verifyModelInitialized(
			DATABASE_PRODUCT_NAME_KEY,
			DATABASE_PRODUCT_NAME_TEST_VALUE);
		this.verifySetProperty(
			DATABASE_PRODUCT_NAME_KEY,
			DATABASE_PRODUCT_NAME_TEST_VALUE,
			DATABASE_PRODUCT_NAME_TEST_VALUE_2);
	}

	public void testAddRemoveDatabaseProductName() throws Exception {
		this.verifyAddRemoveProperty(
			DATABASE_PRODUCT_NAME_KEY,
			DATABASE_PRODUCT_NAME_TEST_VALUE,
			DATABASE_PRODUCT_NAME_TEST_VALUE_2);
	}

	// ********** DatabaseMajorVersion tests **********
	public void testSetDatabaseMajorVersion() throws Exception {
		this.verifyModelInitialized(
			DATABASE_MAJOR_VERSION_KEY,
			DATABASE_MAJOR_VERSION_TEST_VALUE);
		this.verifySetProperty(
			DATABASE_MAJOR_VERSION_KEY,
			DATABASE_MAJOR_VERSION_TEST_VALUE,
			DATABASE_MAJOR_VERSION_TEST_VALUE_2);
	}

	public void testAddRemoveDatabaseMajorVersion() throws Exception {
		this.verifyAddRemoveProperty(
			DATABASE_MAJOR_VERSION_KEY,
			DATABASE_MAJOR_VERSION_TEST_VALUE,
			DATABASE_MAJOR_VERSION_TEST_VALUE_2);
	}

	// ********** DatabaseMinorVersion tests **********
	public void testSetDatabaseMinorVersion() throws Exception {
		this.verifyModelInitialized(
			DATABASE_MINOR_VERSION_KEY,
			DATABASE_MINOR_VERSION_TEST_VALUE);
		this.verifySetProperty(
			DATABASE_MINOR_VERSION_KEY,
			DATABASE_MINOR_VERSION_TEST_VALUE,
			DATABASE_MINOR_VERSION_TEST_VALUE_2);
	}

	public void testAddRemoveDatabaseMinorVersion() throws Exception {
		this.verifyAddRemoveProperty(
			DATABASE_MINOR_VERSION_KEY,
			DATABASE_MINOR_VERSION_TEST_VALUE,
			DATABASE_MINOR_VERSION_TEST_VALUE_2);
	}

	// ********** CreateScriptSource tests **********
	public void testSetCreateScriptSource() throws Exception {
		this.verifyModelInitialized(
			CREATE_SCRIPT_SOURCE_KEY,
			CREATE_SCRIPT_SOURCE_TEST_VALUE);
		this.verifySetProperty(
			CREATE_SCRIPT_SOURCE_KEY,
			CREATE_SCRIPT_SOURCE_TEST_VALUE,
			CREATE_SCRIPT_SOURCE_TEST_VALUE_2);
	}

	public void testAddRemoveCreateScriptSource() throws Exception {
		this.verifyAddRemoveProperty(
			CREATE_SCRIPT_SOURCE_KEY,
			CREATE_SCRIPT_SOURCE_TEST_VALUE,
			CREATE_SCRIPT_SOURCE_TEST_VALUE_2);
	}

	// ********** DropScriptSource tests **********
	public void testSetDropScriptSource() throws Exception {
		this.verifyModelInitialized(
			DROP_SCRIPT_SOURCE_KEY,
			DROP_SCRIPT_SOURCE_TEST_VALUE);
		this.verifySetProperty(
			DROP_SCRIPT_SOURCE_KEY,
			DROP_SCRIPT_SOURCE_TEST_VALUE,
			DROP_SCRIPT_SOURCE_TEST_VALUE_2);
	}

	public void testAddRemoveDropScriptSource() throws Exception {
		this.verifyAddRemoveProperty(
			DROP_SCRIPT_SOURCE_KEY,
			DROP_SCRIPT_SOURCE_TEST_VALUE,
			DROP_SCRIPT_SOURCE_TEST_VALUE_2);
	}

	// ********** Connection tests **********
	public void testSetConnection() throws Exception {
		this.verifyModelInitialized(
			CONNECTION_KEY,
			CONNECTION_TEST_VALUE);
		this.verifySetProperty(
			CONNECTION_KEY,
			CONNECTION_TEST_VALUE,
			CONNECTION_TEST_VALUE_2);
	}

	public void testAddRemoveConnection() throws Exception {
		this.verifyAddRemoveProperty(
			CONNECTION_KEY,
			CONNECTION_TEST_VALUE,
			CONNECTION_TEST_VALUE_2);
	}

	// ********** SqlLoadScriptSource tests **********
	public void testSetSqlLoadScriptSource() throws Exception {
		this.verifyModelInitialized(
			SQL_LOAD_SCRIPT_SOURCE_KEY,
			SQL_LOAD_SCRIPT_SOURCE_TEST_VALUE);
		this.verifySetProperty(
			SQL_LOAD_SCRIPT_SOURCE_KEY,
			SQL_LOAD_SCRIPT_SOURCE_TEST_VALUE,
			SQL_LOAD_SCRIPT_SOURCE_TEST_VALUE_2);
	}

	public void testAddRemoveSqlLoadScriptSource() throws Exception {
		this.verifyAddRemoveProperty(
			SQL_LOAD_SCRIPT_SOURCE_KEY,
			SQL_LOAD_SCRIPT_SOURCE_TEST_VALUE,
			SQL_LOAD_SCRIPT_SOURCE_TEST_VALUE_2);
	}


	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(SchemaGeneration2_1.SCHEMAGEN_DATABASE_ACTION_PROPERTY))
			this.schemaGeneration.setSchemaGenDatabaseAction((SchemaGenerationAction2_1) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.SCHEMAGEN_SCRIPTS_ACTION_PROPERTY))
			this.schemaGeneration.setSchemaGenScriptsAction((SchemaGenerationAction2_1) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.SCHEMAGEN_CREATE_SOURCE_PROPERTY))
			this.schemaGeneration.setSchemaGenCreateSource((SchemaGenerationTarget2_1) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.SCHEMAGEN_DROP_SOURCE_PROPERTY))
			this.schemaGeneration.setSchemaGenDropSource((SchemaGenerationTarget2_1) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.CREATE_DATABASE_SCHEMAS_PROPERTY))
				this.schemaGeneration.setCreateDatabaseSchemas((Boolean) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.SCRIPTS_CREATE_TARGET_PROPERTY))
			this.schemaGeneration.setScriptsCreateTarget((String) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.SCRIPTS_DROP_TARGET_PROPERTY))
			this.schemaGeneration.setScriptsDropTarget((String) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.DATABASE_PRODUCT_NAME_PROPERTY))
			this.schemaGeneration.setDatabaseProductName((String) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.DATABASE_MAJOR_VERSION_PROPERTY))
			this.schemaGeneration.setDatabaseMajorVersion((String) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.DATABASE_MINOR_VERSION_PROPERTY))
			this.schemaGeneration.setDatabaseMinorVersion((String) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.CREATE_SCRIPT_SOURCE_PROPERTY))
			this.schemaGeneration.setCreateScriptSource((String) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.DROP_SCRIPT_SOURCE_PROPERTY))
			this.schemaGeneration.setDropScriptSource((String) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.CONNECTION_PROPERTY))
			this.schemaGeneration.setConnection((String) newValue);
		else if (propertyName.equals(SchemaGeneration2_1.SQL_LOAD_SCRIPT_SOURCE_PROPERTY))
			this.schemaGeneration.setSqlLoadScriptSource((String) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
		 
	}
	
	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(SchemaGeneration2_1.SCHEMAGEN_DATABASE_ACTION_PROPERTY))
			modelValue = this.schemaGeneration.getSchemaGenDatabaseAction();
		else if (propertyName.equals(SchemaGeneration2_1.SCHEMAGEN_SCRIPTS_ACTION_PROPERTY))
			modelValue = this.schemaGeneration.getSchemaGenScriptsAction();
		else if (propertyName.equals(SchemaGeneration2_1.SCHEMAGEN_CREATE_SOURCE_PROPERTY))
			modelValue = this.schemaGeneration.getSchemaGenCreateSource();
		else if (propertyName.equals(SchemaGeneration2_1.SCHEMAGEN_DROP_SOURCE_PROPERTY))
			modelValue = this.schemaGeneration.getSchemaGenDropSource();
		else if (propertyName.equals(SchemaGeneration2_1.CREATE_DATABASE_SCHEMAS_PROPERTY))
			modelValue = this.schemaGeneration.getCreateDatabaseSchemas();
		else if (propertyName.equals(SchemaGeneration2_1.SCRIPTS_CREATE_TARGET_PROPERTY))
			modelValue = this.schemaGeneration.getScriptsCreateTarget();
		else if (propertyName.equals(SchemaGeneration2_1.SCRIPTS_DROP_TARGET_PROPERTY))
			modelValue = this.schemaGeneration.getScriptsDropTarget();
		else if (propertyName.equals(SchemaGeneration2_1.DATABASE_PRODUCT_NAME_PROPERTY))
			modelValue = this.schemaGeneration.getDatabaseProductName();
		else if (propertyName.equals(SchemaGeneration2_1.DATABASE_MAJOR_VERSION_PROPERTY))
			modelValue = this.schemaGeneration.getDatabaseMajorVersion();
		else if (propertyName.equals(SchemaGeneration2_1.DATABASE_MINOR_VERSION_PROPERTY))
			modelValue = this.schemaGeneration.getDatabaseMinorVersion();
		else if (propertyName.equals(SchemaGeneration2_1.CREATE_SCRIPT_SOURCE_PROPERTY))
			modelValue = this.schemaGeneration.getCreateScriptSource();
		else if (propertyName.equals(SchemaGeneration2_1.DROP_SCRIPT_SOURCE_PROPERTY))
			modelValue = this.schemaGeneration.getDropScriptSource();
		else if (propertyName.equals(SchemaGeneration2_1.CONNECTION_PROPERTY))
			modelValue = this.schemaGeneration.getConnection();
		else if (propertyName.equals(SchemaGeneration2_1.SQL_LOAD_SCRIPT_SOURCE_PROPERTY))
			modelValue = this.schemaGeneration.getSqlLoadScriptSource();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
	
	@Override
	protected PersistenceUnitProperties getModel() {
		return this.schemaGeneration;
	}
}

