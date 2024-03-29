/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkDdlGenerationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOutputMode;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkSchemaGeneration;

/**
 * Tests the update of OutputMode model object by the SchemaGeneration adapter
 * when the PersistenceUnit changes.
 */
@SuppressWarnings("nls")
public class SchemaGenerationBasicAdapterTests extends EclipseLinkPersistenceUnitTestCase
{
	private EclipseLinkSchemaGeneration schemaGeneration;

	public static final String outputModeKey = EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE;
	public static final String ddlGenTypeKey = EclipseLinkSchemaGeneration.ECLIPSELINK_DDL_GENERATION_TYPE;

	public static final EclipseLinkOutputMode OUTPUT_MODE_TEST_VALUE = EclipseLinkOutputMode.sql_script;
	public static final EclipseLinkOutputMode OUTPUT_MODE_TEST_VALUE_2 = EclipseLinkOutputMode.database;
	
	public static final EclipseLinkDdlGenerationType DDL_GENERATION_TYPE_TEST_VALUE = EclipseLinkDdlGenerationType.drop_and_create_tables;
	public static final EclipseLinkDdlGenerationType DDL_GENERATION_TYPE_TEST_VALUE_2 = EclipseLinkDdlGenerationType.none;

	public SchemaGenerationBasicAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		this.schemaGeneration = this.subject.getEclipseLinkSchemaGeneration();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		this.schemaGeneration.addPropertyChangeListener(EclipseLinkSchemaGeneration.OUTPUT_MODE_PROPERTY, propertyChangeListener);
		this.schemaGeneration.addPropertyChangeListener(EclipseLinkSchemaGeneration.DDL_GENERATION_TYPE_PROPERTY, propertyChangeListener);
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
		
		this.verifyHasListeners(this.schemaGeneration, EclipseLinkSchemaGeneration.OUTPUT_MODE_PROPERTY);
		
		// Basic
		assertTrue(schemaGeneration.itemIsProperty(this.getPersistenceUnit().getProperty(outputModeKey)));
		assertEquals(OUTPUT_MODE_TEST_VALUE, this.schemaGeneration.getOutputMode());
		
		// Replace
		this.persistenceUnitSetProperty(outputModeKey, OUTPUT_MODE_TEST_VALUE_2);
		this.verifyPutEvent(EclipseLinkSchemaGeneration.OUTPUT_MODE_PROPERTY, OUTPUT_MODE_TEST_VALUE_2, this.schemaGeneration.getOutputMode());
		
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
		this.verifyPutEvent(EclipseLinkSchemaGeneration.OUTPUT_MODE_PROPERTY, OUTPUT_MODE_TEST_VALUE, this.schemaGeneration.getOutputMode());
		
		// Replace again
		this.persistenceUnitSetProperty(outputModeKey, OUTPUT_MODE_TEST_VALUE_2);
		this.verifyPutEvent(EclipseLinkSchemaGeneration.OUTPUT_MODE_PROPERTY, OUTPUT_MODE_TEST_VALUE_2, this.schemaGeneration.getOutputMode());
		
		// Replace by setting model object
		this.clearEvent();
		this.schemaGeneration.setOutputMode(OUTPUT_MODE_TEST_VALUE);
		this.verifyPutEvent(EclipseLinkSchemaGeneration.OUTPUT_MODE_PROPERTY, OUTPUT_MODE_TEST_VALUE, this.schemaGeneration.getOutputMode());
	}

	// ****** convenience methods *******
	@Override
	protected PersistenceUnitProperties getModel() {
		return this.schemaGeneration;
	}

	private ListValueModel<PersistenceUnit.Property> buildPropertiesAdapter(PropertyValueModel<EclipseLinkPersistenceUnit> subjectHolder) {
		return new ListAspectAdapter<EclipseLinkPersistenceUnit, PersistenceUnit.Property>(subjectHolder, PersistenceUnit.PROPERTIES_LIST) {
			@Override
			protected ListIterable<PersistenceUnit.Property> getListIterable() {
				return this.subject.getProperties();
			}

			@Override
			protected int size_() {
				return this.subject.getPropertiesSize();
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
