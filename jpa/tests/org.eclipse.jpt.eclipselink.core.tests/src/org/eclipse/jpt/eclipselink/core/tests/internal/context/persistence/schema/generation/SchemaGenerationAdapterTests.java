/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.schema.generation;

import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.DdlGenerationType;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.OutputMode;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.EclipseLinkPersistenceUnitTestCase;

/**
 * Tests the update of OutputMode model object by the SchemaGeneration adapter
 * when the PersistenceUnit changes.
 */
@SuppressWarnings("nls")
public class SchemaGenerationAdapterTests extends EclipseLinkPersistenceUnitTestCase
{
	private SchemaGeneration schemaGeneration;

	public static final String DDL_GENERATION_TYPE_KEY = SchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE;
	public static final DdlGenerationType DDL_GENERATION_TYPE_TEST_VALUE = DdlGenerationType.drop_and_create_tables;
	public static final DdlGenerationType DDL_GENERATION_TYPE_TEST_VALUE_2 = DdlGenerationType.none;

	public static final String OUTPUT_MODE_KEY = SchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE;
	public static final OutputMode OUTPUT_MODE_TEST_VALUE = OutputMode.sql_script;
	public static final OutputMode OUTPUT_MODE_TEST_VALUE_2 = OutputMode.database;

	private static final String CREATE_FILE_NAME_KEY = SchemaGeneration.ECLIPSELINK_CREATE_FILE_NAME;
	private static final String CREATE_FILE_NAME_TEST_VALUE = "create-file-name.test";
	private static final String CREATE_FILE_NAME_TEST_VALUE_2 = "create-file-name-2.test";

	private static final String DROP_FILE_NAME_KEY = SchemaGeneration.ECLIPSELINK_DROP_FILE_NAME;
	private static final String DROP_FILE_NAME_TEST_VALUE = "drop-file-name.test";
	private static final String DROP_FILE_NAME_TEST_VALUE_2 = "drop-file-name-2.test";

	private static final String APPLICATION_LOCATION_KEY = SchemaGeneration.ECLIPSELINK_APPLICATION_LOCATION;
	private static final String APPLICATION_LOCATION_TEST_VALUE = "C:/temp";
	private static final String APPLICATION_LOCATION_TEST_VALUE_2 = "C:/tmp";

	public SchemaGenerationAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.schemaGeneration = this.subject.getSchemaGeneration();
		
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration.OUTPUT_MODE_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration.CREATE_FILE_NAME_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration.DROP_FILE_NAME_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration.APPLICATION_LOCATION_PROPERTY, propertyChangeListener);
		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing. Cannot use
	 * Property Holder to initialize because it is not created yet
	 */
	@Override
	protected void populatePu() {
		this.propertiesTotal = 9;
		this.modelPropertiesSizeOriginal = 5;
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		this.persistenceUnitSetProperty("property.0", "value.0");
		this.persistenceUnitSetProperty(OUTPUT_MODE_KEY, this.getPropertyStringValueOf(OUTPUT_MODE_TEST_VALUE));
		this.persistenceUnitSetProperty("property.2", "value.2");
		this.persistenceUnitSetProperty("property.3", "value.3");
		this.persistenceUnitSetProperty("property.4", "value.4");
		this.persistenceUnitSetProperty(DDL_GENERATION_TYPE_KEY, this.getPropertyStringValueOf(DDL_GENERATION_TYPE_TEST_VALUE));
		this.persistenceUnitSetProperty(CREATE_FILE_NAME_KEY, CREATE_FILE_NAME_TEST_VALUE);
		this.persistenceUnitSetProperty(DROP_FILE_NAME_KEY, DROP_FILE_NAME_TEST_VALUE);
		this.persistenceUnitSetProperty(APPLICATION_LOCATION_KEY, APPLICATION_LOCATION_TEST_VALUE);
		return;
	}

	// ********** CreateFileName **********
	/**
	 * Tests the update of CreateFileName property by the SchemaGeneration
	 * adapter when the PU or the model changes.
	 */
	public void testSetCreateFileName() throws Exception {
		this.verifyModelInitialized(
			CREATE_FILE_NAME_KEY,
			CREATE_FILE_NAME_TEST_VALUE);
		this.verifySetProperty(
			CREATE_FILE_NAME_KEY,
			CREATE_FILE_NAME_TEST_VALUE,
			CREATE_FILE_NAME_TEST_VALUE_2);
	}

	public void testAddRemoveCreateFileName() throws Exception {
		this.verifyAddRemoveProperty(
			CREATE_FILE_NAME_KEY,
			CREATE_FILE_NAME_TEST_VALUE,
			CREATE_FILE_NAME_TEST_VALUE_2);
	}
	
	// ********** DropFileName **********
	/**
	 * Tests the update of DropFileName property by the SchemaGeneration adapter
	 * when the PU or the model changes.
	 */
	public void testSetDropFileName() throws Exception {
		this.verifyModelInitialized(
			DROP_FILE_NAME_KEY,
			DROP_FILE_NAME_TEST_VALUE);
		this.verifySetProperty(
			DROP_FILE_NAME_KEY,
			DROP_FILE_NAME_TEST_VALUE,
			DROP_FILE_NAME_TEST_VALUE_2);
	}

	public void testAddRemoveDropFileName() throws Exception {
		this.verifyAddRemoveProperty(
			DROP_FILE_NAME_KEY, 
			DROP_FILE_NAME_TEST_VALUE, 
			DROP_FILE_NAME_TEST_VALUE_2);
	}

	// ********** ApplicationLocation **********
	/**
	 * Tests the update of ApplicationLocation property by the SchemaGeneration
	 * adapter when the PU or the model changes.
	 */
	public void testSetApplicationLocation() throws Exception {
		this.verifyModelInitialized(
			APPLICATION_LOCATION_KEY,
			APPLICATION_LOCATION_TEST_VALUE);
		this.verifySetProperty(
			APPLICATION_LOCATION_KEY,
			APPLICATION_LOCATION_TEST_VALUE,
			APPLICATION_LOCATION_TEST_VALUE_2);
	}

	public void testAddRemoveApplicationLocation() throws Exception {
		this.verifyAddRemoveProperty(
			APPLICATION_LOCATION_KEY,
			APPLICATION_LOCATION_TEST_VALUE,
			APPLICATION_LOCATION_TEST_VALUE_2);
	}

	public void testSetEmptyApplicationLocation() throws Exception {
		String puKey = APPLICATION_LOCATION_KEY;
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(puKey);
		String propertyName = this.getModel().propertyIdOf(property);

		// Set ApplicationLocation to "" & verify that the property is deleted
		this.verifyPuHasProperty(puKey,  "persistenceUnit.properties doesn't contains: ");
		this.setProperty(propertyName, "");

		this.verifyPuHasNotProperty(puKey,  "Property was not deleted");
		this.verifyPutProperty(propertyName, null);
		assertNull(this.getPersistenceUnit().getProperty(puKey));
	}

	// ********** OutputMode **********
	/**
	 * Tests the update of OutputMode property by the SchemaGeneration adapter
	 * when the PU or the model changes.
	 */
	public void testSetOutputMode() throws Exception {
		this.verifyModelInitialized(
			OUTPUT_MODE_KEY,
			OUTPUT_MODE_TEST_VALUE);
		this.verifySetProperty(
			OUTPUT_MODE_KEY,
			OUTPUT_MODE_TEST_VALUE,
			OUTPUT_MODE_TEST_VALUE_2);
	}

	public void testAddRemoveOutputMode() throws Exception {
		this.verifyAddRemoveProperty(
			OUTPUT_MODE_KEY, 
			OUTPUT_MODE_TEST_VALUE, 
			OUTPUT_MODE_TEST_VALUE_2);
	}

	// ********** DdlGenerationType **********
	/**
	 * Tests the update of DdlGenerationType property by the SchemaGeneration
	 * adapter when the PU or the model changes.
	 */
	public void testSetDdlGenerationType() throws Exception {
		this.verifyModelInitialized(
			DDL_GENERATION_TYPE_KEY,
			DDL_GENERATION_TYPE_TEST_VALUE);
		this.verifySetProperty(
			DDL_GENERATION_TYPE_KEY,
			DDL_GENERATION_TYPE_TEST_VALUE,
			DDL_GENERATION_TYPE_TEST_VALUE_2);
	}

	public void testAddRemoveDdlGenerationType() throws Exception {
		this.verifyAddRemoveProperty(
			DDL_GENERATION_TYPE_KEY, 
			DDL_GENERATION_TYPE_TEST_VALUE, 
			DDL_GENERATION_TYPE_TEST_VALUE_2);
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws NoSuchFieldException {
		if (propertyName.equals(SchemaGeneration.OUTPUT_MODE_PROPERTY))
			this.schemaGeneration.setOutputMode((OutputMode) newValue);
		// else if( propertyName.equals( Caching.CACHE_SIZE_PROPERTY))
		// TODO
		else if (propertyName.equals(SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY))
			this.schemaGeneration.setDdlGenerationType((DdlGenerationType) newValue);
		else if (propertyName.equals(SchemaGeneration.APPLICATION_LOCATION_PROPERTY))
			this.schemaGeneration.setApplicationLocation((String) newValue);
		else if (propertyName.equals(SchemaGeneration.CREATE_FILE_NAME_PROPERTY))
			this.schemaGeneration.setCreateFileName((String) newValue);
		else if (propertyName.equals(SchemaGeneration.DROP_FILE_NAME_PROPERTY))
			this.schemaGeneration.setDropFileName((String) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(SchemaGeneration.OUTPUT_MODE_PROPERTY))
			modelValue = this.schemaGeneration.getOutputMode();
		else if (propertyName.equals(SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY))
			modelValue = this.schemaGeneration.getDdlGenerationType();
		else if (propertyName.equals(SchemaGeneration.APPLICATION_LOCATION_PROPERTY))
			modelValue = this.schemaGeneration.getApplicationLocation();
		else if (propertyName.equals(SchemaGeneration.CREATE_FILE_NAME_PROPERTY))
			modelValue = this.schemaGeneration.getCreateFileName();
		else if (propertyName.equals(SchemaGeneration.DROP_FILE_NAME_PROPERTY))
			modelValue = this.schemaGeneration.getDropFileName();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.schemaGeneration;
	}
}
