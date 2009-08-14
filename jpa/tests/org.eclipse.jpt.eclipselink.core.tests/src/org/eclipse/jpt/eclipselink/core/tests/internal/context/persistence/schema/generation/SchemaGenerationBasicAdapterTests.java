/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.schema.generation;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.DdlGenerationType;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.OutputMode;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.EclipseLinkPersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * Tests the update of OutputMode model object by the SchemaGeneration adapter
 * when the PersistenceUnit changes.
 */
public class SchemaGenerationBasicAdapterTests extends EclipseLinkPersistenceUnitTestCase
{
	private SchemaGeneration schemaGeneration;

	public static final String outputModeKey = SchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE;
	public static final String ddlGenTypeKey = SchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE;

	public static final OutputMode OUTPUT_MODE_TEST_VALUE = OutputMode.sql_script;
	public static final OutputMode OUTPUT_MODE_TEST_VALUE_2 = OutputMode.database;
	
	public static final DdlGenerationType DDL_GENERATION_TYPE_TEST_VALUE = DdlGenerationType.drop_and_create_tables;
	public static final DdlGenerationType DDL_GENERATION_TYPE_TEST_VALUE_2 = DdlGenerationType.none;

	public SchemaGenerationBasicAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		this.schemaGeneration = this.subject.getSchemaGeneration();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration.OUTPUT_MODE_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(SchemaGeneration.DDL_GENERATION_TYPE_PROPERTY, propertyChangeListener);
		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing. Cannot use
	 * Property Holder to initialize because it is not created yet
	 */
	@Override
	protected void populatePu() {
		this.propertiesTotal = 6;
		this.modelPropertiesSizeOriginal = 2;
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		this.persistenceUnitSetProperty("property.0", "value.0");
		this.persistenceUnitSetProperty(outputModeKey, this.getPropertyStringValueOf(OUTPUT_MODE_TEST_VALUE));
		this.persistenceUnitSetProperty("property.2", "value.2");
		this.persistenceUnitSetProperty("property.3", "value.3");
		this.persistenceUnitSetProperty("property.4", "value.4");
		this.persistenceUnitSetProperty(ddlGenTypeKey, this.getPropertyStringValueOf(DDL_GENERATION_TYPE_TEST_VALUE));
		return;
	}

	/** ****** test methods ******* */

	/**
	 * Tests the update of OutputMode property by the SchemaGeneration adapter
	 * when the PU changes.
	 */
	public void testOutputModeUpdate() throws Exception {
		ListValueModel<PersistenceUnit.Property> propertiesAdapter = this.buildPropertiesAdapter(this.subjectHolder);
		ListValueModel<PersistenceUnit.Property> propertyListAdapter = new ItemPropertyListValueModelAdapter<PersistenceUnit.Property>(propertiesAdapter, PersistenceUnit.Property.VALUE_PROPERTY);
		
		this.verifyHasListeners(this.schemaGeneration, SchemaGeneration.OUTPUT_MODE_PROPERTY);
		
		// Basic
		assertTrue(schemaGeneration.itemIsProperty(this.getPersistenceUnit().getProperty(outputModeKey)));
		assertEquals(OUTPUT_MODE_TEST_VALUE, this.schemaGeneration.getOutputMode());
		
		// Replace
		this.persistenceUnitSetProperty(outputModeKey, OUTPUT_MODE_TEST_VALUE_2);
		this.verifyPutEvent(SchemaGeneration.OUTPUT_MODE_PROPERTY, OUTPUT_MODE_TEST_VALUE_2, this.schemaGeneration.getOutputMode());
		
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.getPersistenceUnit().removeProperty(outputModeKey);
		assertNull(this.getPersistenceUnit().getProperty(outputModeKey));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		assertNotNull(this.propertyChangedEvent);
		assertNull(this.propertyChangedEvent.getNewValue());
		
		// Add original OutputMode
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitSetProperty(outputModeKey, OUTPUT_MODE_TEST_VALUE);
		this.verifyPutEvent(SchemaGeneration.OUTPUT_MODE_PROPERTY, OUTPUT_MODE_TEST_VALUE, this.schemaGeneration.getOutputMode());
		
		// Replace again
		this.persistenceUnitSetProperty(outputModeKey, OUTPUT_MODE_TEST_VALUE_2);
		this.verifyPutEvent(SchemaGeneration.OUTPUT_MODE_PROPERTY, OUTPUT_MODE_TEST_VALUE_2, this.schemaGeneration.getOutputMode());
		
		// Replace by setting model object
		this.clearEvent();
		this.schemaGeneration.setOutputMode(OUTPUT_MODE_TEST_VALUE);
		this.verifyPutEvent(SchemaGeneration.OUTPUT_MODE_PROPERTY, OUTPUT_MODE_TEST_VALUE, this.schemaGeneration.getOutputMode());
	}

	// ****** convenience methods *******
	@Override
	protected PersistenceUnitProperties getModel() {
		return this.schemaGeneration;
	}

	private ListValueModel<PersistenceUnit.Property> buildPropertiesAdapter(PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new ListAspectAdapter<EclipseLinkPersistenceUnit, PersistenceUnit.Property>(subjectHolder, PersistenceUnit.PROPERTIES_LIST) {
			@Override
			protected ListIterator<PersistenceUnit.Property> listIterator_() {
				return this.subject.properties();
			}

			@Override
			protected int size_() {
				return this.subject.propertiesSize();
			}
		};
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
