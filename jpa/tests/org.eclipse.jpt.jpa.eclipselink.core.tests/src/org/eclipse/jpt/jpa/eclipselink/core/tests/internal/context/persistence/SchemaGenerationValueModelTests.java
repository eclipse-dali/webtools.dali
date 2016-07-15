/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkDdlGenerationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOutputMode;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkSchemaGeneration;

/**
 * SchemaGenerationValueModelTests
 * 
 * Tests the PropertyValueModel of SchemaGeneration model and the update of the
 * PersistenceUnit.
 */
public class SchemaGenerationValueModelTests extends EclipseLinkPersistenceUnitTestCase
{
	private EclipseLinkSchemaGeneration schemaGen;
	private PropertyValueModel<EclipseLinkSchemaGeneration> schemaGenHolder;

	private ModifiablePropertyValueModel<EclipseLinkDdlGenerationType> ddlGenerationTypeHolder;
	private PropertyChangeEvent ddlGenerationTypeEvent;

	private ModifiablePropertyValueModel<EclipseLinkOutputMode> outputModeHolder;
	private PropertyChangeEvent outputModeEvent;

	public static final EclipseLinkDdlGenerationType DDL_GENERATION_TYPE_TEST_VALUE = EclipseLinkDdlGenerationType.drop_and_create_tables;
	public static final EclipseLinkOutputMode OUTPUT_MODE_TEST_VALUE = EclipseLinkOutputMode.sql_script;

	public SchemaGenerationValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.schemaGen = this.subject.getEclipseLinkSchemaGeneration(); // Subject
		this.schemaGenHolder = new SimplePropertyValueModel<EclipseLinkSchemaGeneration>(this.schemaGen);
		
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
			EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE,
			DDL_GENERATION_TYPE_TEST_VALUE);
		this.persistenceUnitSetProperty(
			EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE,
			OUTPUT_MODE_TEST_VALUE);
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.schemaGen;
	}

	/** ****** DdlGenerationType ******* */
	private ModifiablePropertyValueModel<EclipseLinkDdlGenerationType> buildDdlGenerationTypeAA(PropertyValueModel<EclipseLinkSchemaGeneration> subjectHolder) {
		return new PropertyAspectAdapterXXXX<EclipseLinkSchemaGeneration, EclipseLinkDdlGenerationType>(subjectHolder, EclipseLinkSchemaGeneration.DDL_GENERATION_TYPE_PROPERTY) {
			@Override
			protected EclipseLinkDdlGenerationType buildValue_() {
				return this.subject.getDdlGenerationType();
			}

			@Override
			protected void setValue_(EclipseLinkDdlGenerationType enumValue) {
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
	private ModifiablePropertyValueModel<EclipseLinkOutputMode> buildOutputModeAA(PropertyValueModel<EclipseLinkSchemaGeneration> subjectHolder) {
		return new PropertyAspectAdapterXXXX<EclipseLinkSchemaGeneration, EclipseLinkOutputMode>(subjectHolder, EclipseLinkSchemaGeneration.OUTPUT_MODE_PROPERTY) {
			@Override
			protected EclipseLinkOutputMode buildValue_() {
				return this.subject.getOutputMode();
			}

			@Override
			protected void setValue_(EclipseLinkOutputMode enumValue) {
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
			EclipseLinkSchemaGeneration.DEFAULT_SCHEMA_GENERATION_DDL_GENERATION_TYPE,
			this.schemaGen.getDefaultDdlGenerationType());
		/** ****** OutputMode ******* */
		this.verifyOutputModeAAValue(OUTPUT_MODE_TEST_VALUE);
		assertEquals(
			EclipseLinkSchemaGeneration.DEFAULT_SCHEMA_GENERATION_OUTPUT_MODE,
			this.schemaGen.getDefaultOutputMode());
	}

	public void testSetValue() throws Exception {
		/** ****** DdlGenerationType ******* */
		this.ddlGenerationTypeEvent = null;
		this.verifyHasListeners(this.ddlGenerationTypeHolder, PropertyValueModel.VALUE);
		EclipseLinkDdlGenerationType newDdlGenerationType = EclipseLinkDdlGenerationType.create_tables;
		// Modify the persistenceUnit directly
		this.subject.setProperty(
			EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE,
			this.getPropertyStringValueOf(newDdlGenerationType),
			false);
		this.verifyDdlGenerationTypeAAValue(newDdlGenerationType);
		assertNotNull(this.ddlGenerationTypeEvent);
		
		/** ****** OutputMode ******* */
		this.outputModeEvent = null;
		this.verifyHasListeners(this.outputModeHolder, PropertyValueModel.VALUE);
		EclipseLinkOutputMode newOutputMode = EclipseLinkOutputMode.database;
		// Modify the property holder
		this.outputModeHolder.setValue(newOutputMode);
		this.verifyOutputModeAAValue(newOutputMode);
		assertNotNull(this.outputModeEvent);
	}

	public void testSetNullValue() {
		/** ****** DdlGenerationType ******* */
		this.ddlGenerationTypeEvent = null;
		// Setting the persistenceUnit directly
		this.subject.setProperty(EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE, null, false);
		this.ddlGenerationTypeHolder.setValue(null);
		// testing Holder
		this.verifyDdlGenerationTypeAAValue(null);
		assertNotNull(this.ddlGenerationTypeEvent);
		// testing PU properties
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE);
		assertNull(property);
		
		/** ****** OutputMode ******* */
		this.outputModeEvent = null;
		// Setting the property holder
		this.outputModeHolder.setValue(null);
		// testing Holder
		this.verifyOutputModeAAValue(null);
		assertNotNull(this.outputModeEvent);
		// testing PU properties
		property = this.getPersistenceUnit().getProperty(EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE);
		assertNull(property);
	}

	/** ****** convenience methods ******* */
	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyDdlGenerationTypeAAValue(EclipseLinkDdlGenerationType testValue) {
		this.verifyAAValue(
			testValue,
			this.schemaGen.getDdlGenerationType(),
			this.ddlGenerationTypeHolder,
			EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE);
	}

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyOutputModeAAValue(EclipseLinkOutputMode testValue) {
		this.verifyAAValue(
			testValue,
			this.schemaGen.getOutputMode(),
			this.outputModeHolder,
			EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE);
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
