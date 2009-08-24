/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.schema.generation;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.DdlGenerationType;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.OutputMode;
import org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.EclipseLinkPersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * SchemaGenerationValueModelTests
 * 
 * Tests the PropertyValueModel of SchemaGeneration model and the update of the
 * PersistenceUnit.
 */
public class SchemaGenerationValueModelTests extends EclipseLinkPersistenceUnitTestCase
{
	private SchemaGeneration schemaGen;
	private PropertyValueModel<SchemaGeneration> schemaGenHolder;

	private WritablePropertyValueModel<DdlGenerationType> ddlGenerationTypeHolder;
	private PropertyChangeEvent ddlGenerationTypeEvent;

	private WritablePropertyValueModel<OutputMode> outputModeHolder;
	private PropertyChangeEvent outputModeEvent;

	public static final DdlGenerationType DDL_GENERATION_TYPE_TEST_VALUE = DdlGenerationType.drop_and_create_tables;
	public static final OutputMode OUTPUT_MODE_TEST_VALUE = OutputMode.sql_script;

	public SchemaGenerationValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.schemaGen = this.subject.getSchemaGeneration(); // Subject
		this.schemaGenHolder = new SimplePropertyValueModel<SchemaGeneration>(this.schemaGen);
		
		this.ddlGenerationTypeHolder = this.buildDdlGenerationTypeAA(this.schemaGenHolder);
		PropertyChangeListener ddlGenerationTypeListener = this.buildDdlGenerationTypeChangeListener();
		this.ddlGenerationTypeHolder.addPropertyChangeListener(PropertyValueModel.VALUE, ddlGenerationTypeListener);
		this.ddlGenerationTypeEvent = null;
		
		this.outputModeHolder = this.buildOutputModeAA(this.schemaGenHolder);
		PropertyChangeListener outputModeListener = this.buildOutputModeChangeListener();
		this.outputModeHolder.addPropertyChangeListener(PropertyValueModel.VALUE, outputModeListener);
		this.outputModeEvent = null;
	}

	/**
	 * Initializes directly the PU properties before testing. Cannot use
	 * Property Holder to initialize because it is not created yet
	 */
	@Override
	protected void populatePu() {
		this.persistenceUnitSetProperty(
			SchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE,
			DDL_GENERATION_TYPE_TEST_VALUE);
		this.persistenceUnitSetProperty(
			SchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE,
			OUTPUT_MODE_TEST_VALUE);
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.schemaGen;
	}

	/** ****** DdlGenerationType ******* */
	private WritablePropertyValueModel<DdlGenerationType> buildDdlGenerationTypeAA(PropertyValueModel<SchemaGeneration> subjectHolder) {
		return new PropertyAspectAdapter<SchemaGeneration, DdlGenerationType>(subjectHolder, SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY) {
			@Override
			protected DdlGenerationType buildValue_() {
				return this.subject.getDdlGenerationType();
			}

			@Override
			protected void setValue_(DdlGenerationType enumValue) {
				this.subject.setDdlGenerationType(enumValue);
			}
		};
	}

	private PropertyChangeListener buildDdlGenerationTypeChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				SchemaGenerationValueModelTests.this.ddlGenerationTypeEvent = e;
			}
		};
	}

	/** ****** OutputMode ******* */
	private WritablePropertyValueModel<OutputMode> buildOutputModeAA(PropertyValueModel<SchemaGeneration> subjectHolder) {
		return new PropertyAspectAdapter<SchemaGeneration, OutputMode>(subjectHolder, SchemaGeneration.OUTPUT_MODE_PROPERTY) {
			@Override
			protected OutputMode buildValue_() {
				return this.subject.getOutputMode();
			}

			@Override
			protected void setValue_(OutputMode enumValue) {
				this.subject.setOutputMode(enumValue);
			}
		};
	}

	private PropertyChangeListener buildOutputModeChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				SchemaGenerationValueModelTests.this.outputModeEvent = e;
			}
		};
	}

	public void testValue() {
		/** ****** DdlGenerationType ******* */
		this.verifyDdlGenerationTypeAAValue(DDL_GENERATION_TYPE_TEST_VALUE);
		assertEquals(
			SchemaGeneration.DEFAULT_SCHEMA_GENERATION_DDL_GENERATION_TYPE,
			this.schemaGen.getDefaultDdlGenerationType());
		/** ****** OutputMode ******* */
		this.verifyOutputModeAAValue(OUTPUT_MODE_TEST_VALUE);
		assertEquals(
			SchemaGeneration.DEFAULT_SCHEMA_GENERATION_OUTPUT_MODE,
			this.schemaGen.getDefaultOutputMode());
	}

	public void testSetValue() throws Exception {
		/** ****** DdlGenerationType ******* */
		this.ddlGenerationTypeEvent = null;
		this.verifyHasListeners(this.ddlGenerationTypeHolder, PropertyValueModel.VALUE);
		DdlGenerationType newDdlGenerationType = DdlGenerationType.create_tables;
		// Modify the persistenceUnit directly
		this.subject.setProperty(
			SchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE,
			this.getPropertyStringValueOf(newDdlGenerationType),
			false);
		this.verifyDdlGenerationTypeAAValue(newDdlGenerationType);
		assertNotNull(this.ddlGenerationTypeEvent);
		
		/** ****** OutputMode ******* */
		this.outputModeEvent = null;
		this.verifyHasListeners(this.outputModeHolder, PropertyValueModel.VALUE);
		OutputMode newOutputMode = OutputMode.database;
		// Modify the property holder
		this.outputModeHolder.setValue(newOutputMode);
		this.verifyOutputModeAAValue(newOutputMode);
		assertNotNull(this.outputModeEvent);
	}

	public void testSetNullValue() {
		/** ****** DdlGenerationType ******* */
		this.ddlGenerationTypeEvent = null;
		// Setting the persistenceUnit directly
		this.subject.setProperty(SchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE, null, false);
		this.ddlGenerationTypeHolder.setValue(null);
		// testing Holder
		this.verifyDdlGenerationTypeAAValue(null);
		assertNotNull(this.ddlGenerationTypeEvent);
		// testing PU properties
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(SchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE);
		assertNull(property);
		
		/** ****** OutputMode ******* */
		this.outputModeEvent = null;
		// Setting the property holder
		this.outputModeHolder.setValue(null);
		// testing Holder
		this.verifyOutputModeAAValue(null);
		assertNotNull(this.outputModeEvent);
		// testing PU properties
		property = this.getPersistenceUnit().getProperty(SchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE);
		assertNull(property);
	}

	/** ****** convenience methods ******* */
	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyDdlGenerationTypeAAValue(DdlGenerationType testValue) {
		this.verifyAAValue(
			testValue,
			this.schemaGen.getDdlGenerationType(),
			this.ddlGenerationTypeHolder,
			SchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE);
	}

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyOutputModeAAValue(OutputMode testValue) {
		this.verifyAAValue(
			testValue,
			this.schemaGen.getOutputMode(),
			this.outputModeHolder,
			SchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE);
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		throw new UnsupportedOperationException();
	}
}
