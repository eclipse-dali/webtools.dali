/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.schema.generation;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.persistence.GenericProperty;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkJpaProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitPropertyListListener;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.DdlGenerationType;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.EclipseLinkSchemaGeneration;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.OutputMode;
import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.core.tests.internal.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Tests the update of OutputMode model object by the SchemaGeneration adapter
 * when the PersistenceUnit changes.
 */
public class SchemaGenerationAdapterTests extends PersistenceUnitTestCase
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
		this.schemaGeneration = this.persistenceUnitProperties.getSchemaGeneration();
		
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
		
		this.persistenceUnitPut("property.0", "value.0");
		this.persistenceUnitPut(OUTPUT_MODE_KEY, this.getEclipseLinkStringValueOf(OUTPUT_MODE_TEST_VALUE));
		this.persistenceUnitPut("property.2", "value.2");
		this.persistenceUnitPut("property.3", "value.3");
		this.persistenceUnitPut("property.4", "value.4");
		this.persistenceUnitPut(DDL_GENERATION_TYPE_KEY, this.getEclipseLinkStringValueOf(DDL_GENERATION_TYPE_TEST_VALUE));
		this.persistenceUnitPut(CREATE_FILE_NAME_KEY, CREATE_FILE_NAME_TEST_VALUE);
		this.persistenceUnitPut(DROP_FILE_NAME_KEY, DROP_FILE_NAME_TEST_VALUE);
		this.persistenceUnitPut(APPLICATION_LOCATION_KEY, APPLICATION_LOCATION_TEST_VALUE);
		return;
	}

	public void testHasListeners() throws Exception {
		ListAspectAdapter<PersistenceUnit, Property> propertiesAdapter = (ListAspectAdapter<PersistenceUnit, Property>) ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertiesAdapter();
		GenericProperty outputModeProperty = (GenericProperty) this.persistenceUnit().getProperty(OUTPUT_MODE_KEY);
		GenericProperty ddlGenTypeProperty = (GenericProperty) this.persistenceUnit().getProperty(DDL_GENERATION_TYPE_KEY);
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();

		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(outputModeProperty.hasAnyPropertyChangeListeners(Property.VALUE_PROPERTY));
		assertTrue(ddlGenTypeProperty.hasAnyPropertyChangeListeners(Property.VALUE_PROPERTY));
		this.verifyHasListeners(this.schemaGeneration, SchemaGeneration.OUTPUT_MODE_PROPERTY);
		this.verifyHasListeners(this.schemaGeneration, SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY);
		this.verifyHasListeners(propertyListAdapter);

		EclipseLinkSchemaGeneration schemaGen = (EclipseLinkSchemaGeneration) this.schemaGeneration;
		PersistenceUnitPropertyListListener propertyListListener = schemaGen.propertyListListener();
		propertyListAdapter.removeListChangeListener(ListValueModel.LIST_VALUES, propertyListListener);
		this.verifyHasListeners(this.schemaGeneration, SchemaGeneration.OUTPUT_MODE_PROPERTY);
		this.verifyHasListeners(this.schemaGeneration, SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY);
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

	protected PersistenceUnitProperties model() {
		return this.schemaGeneration;
	}
}
