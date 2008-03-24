/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal;

import java.util.ListIterator;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkJpaProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * PersistenceUnitTestCase
 */
public abstract class PersistenceUnitTestCase extends ContextModelTestCase
{
	protected PersistenceUnit subject;

	protected PropertyValueModel<PersistenceUnit> subjectHolder;

	protected EclipseLinkProperties persistenceUnitProperties;

	protected PropertyChangeEvent propertyChangedEvent;

	protected int propertyChangedEventCount;

	protected int propertiesTotal;

	protected int modelPropertiesSizeOriginal;

	protected int modelPropertiesSize;

	protected PersistenceUnitTestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject = (PersistenceUnit) this.persistenceUnit();
		this.subjectHolder = new SimplePropertyValueModel<PersistenceUnit>(this.subject);
		this.persistenceUnitProperties = new EclipseLinkJpaProperties(this.subject);
		this.populatePu();
	}

	private ListValueModel<Property> buildPropertiesAspectAdapter(PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new ListAspectAdapter<PersistenceUnit, Property>(subjectHolder, PersistenceUnit.PROPERTIES_LIST) {
			@Override
			protected ListIterator<Property> listIterator_() {
				return this.subject.properties();
			}

			@Override
			protected int size_() {
				return this.subject.propertiesSize();
			}
		};
	}

	protected String getEclipseLinkValueString(Enum value) {
		return EclipseLinkPersistenceUnitProperties.getEclipseLinkStringValue(value);
	}

	/** ****** convenience test methods ******* */
	protected void clearEvent() {
		this.propertyChangedEvent = null;
		this.propertyChangedEventCount = 0;
	}

	/**
	 * Put into persistenceUnit properties.
	 * 
	 * @param key -
	 *            EclipseLink Key
	 * @param value -
	 *            property value
	 */
	// TODO to rename to persistenceUnitPut
	@SuppressWarnings("unchecked")
	protected void putProperty(String key, Object value) {
		if (value == null)
			this.putNullProperty(key);
		else if (value.getClass().isEnum())
			this.putEnumProperty(key, (Enum) value);
		else
			this.putProperty_(key, value);
	}

	@SuppressWarnings("unchecked")
	protected void putEnumProperty(String key, Enum value) {
		this.clearEvent();
		this.persistenceUnit().putProperty(key, this.getEclipseLinkValueString(value), false);
	}

	private void putProperty_(String key, Object value) {
		this.clearEvent();
		this.persistenceUnit().putProperty(key, value.toString(), false);
	}

	protected void putNullProperty(String key) {
		this.clearEvent();
		this.persistenceUnit().putProperty(key, null, false);
	}

	/** ****** verify methods ******* */
	protected abstract PersistenceUnitProperties model();

	/**
	 * Initializes directly the PU properties before testing. Cannot use
	 * Property Holder to initialize because it is not created yet
	 */
	protected abstract void populatePu();

	// TODO make abstract
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		throw new IllegalStateException("Missing Implementation for setting: " + propertyName);
	}

	// TODO make abstract
	protected void verifyPutProperty(String propertyName, Object expectedValue) throws Exception {
		throw new IllegalStateException("Missing Implementation for verifying: " + propertyName);
	}

	protected void throwMissingDefinition(String methodName, String propertyName) throws NoSuchFieldException {
		throw new NoSuchFieldException("Missing Definition for: " + methodName + "( " + propertyName + ")");
	}

	protected void throwUnsupportedOperationException(ListChangeEvent e) {
		throw new UnsupportedOperationException(e.aspectName());
	}

	protected void verifyAAValue(Boolean testValue, Boolean subjectValue, PropertyValueModel<Boolean> aa, String persistenceXmlKey) {
		assertEquals(testValue, subjectValue);
		assertEquals(testValue, aa.value());
		if (testValue != null) {
			assertEquals(testValue.toString(), this.persistenceUnit().getProperty(persistenceXmlKey).getValue());
		}
	}

	protected <T extends Enum<T>> void verifyAAValue(T testValue, T subjectValue, PropertyValueModel<? extends Enum<T>> aa, String persistenceXmlKey) {
		assertEquals(testValue, subjectValue);
		assertEquals(testValue, aa.value());
		if (testValue != null) {
			assertEquals(this.getEclipseLinkValueString(testValue), this.persistenceUnit().getProperty(persistenceXmlKey).getValue());
		}
	}

	protected void verifyInitialState(String propertyName, String key, ListValueModel<Property> propertyListAdapter) throws Exception {
		assertEquals("Total not updated in populatePu(): ", propertyListAdapter.size(), this.propertiesTotal);
		this.verifyPuHasProperty(key, "Property not added to populatePu()");
		this.verifyHasListeners(propertyListAdapter);
		this.verifyHasListeners(this.model(), propertyName);
		
		Property property = this.persistenceUnit().getProperty(key);
		assertTrue("itemIsProperty() not updated: ", model().itemIsProperty(property));
		assertEquals("propertyIdFor() not updated: ", propertyName, model().propertyIdFor(property));
	}

	/**
	 * Verify that the tested Property model is initialized with the value from
	 * the persistence unit.
	 */
	protected void verifyModelInitialized(Object modelValue, Object expectedValue) {
		assertEquals("Property not populatedPu() or not model.initializeProperties()", expectedValue, modelValue);
	}

	protected void verifySetProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();
		
		// Basic
		this.verifyInitialState(propertyName, key, propertyListAdapter);
		
		// Replace
		this.putProperty(key, testValue2);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutProperty(propertyName, testValue2);
		
		// Replace by setting model object
		this.clearEvent();
		this.setProperty(propertyName, testValue1);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutProperty(propertyName, testValue1);
	}

	protected void verifyAddRemoveProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();
		
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		assertTrue("persistenceUnit.properties doesn't contains: " + key, this.persistenceUnit().containsProperty(key));
		this.persistenceUnit().removeProperty(key);
		assertFalse(this.persistenceUnit().containsProperty(key));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutProperty(propertyName, null);
		
		// Add original CacheTypeDefault
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.putProperty(key, testValue1);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutProperty(propertyName, testValue1);
		
		// Replace
		this.putProperty(key, testValue2);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutProperty(propertyName, testValue2);
	}

	protected void verifyPutProperty(String propertyName, Object value, Object expectedValue) {
		this.verifyEvent(propertyName);
		this.verifyEventValue(value, expectedValue);
	}

	protected void verifyEventValue(Object value, Object expectedValue) {
		// verify event value
		assertEquals(expectedValue, this.propertyChangedEvent.newValue());
		assertEquals(expectedValue, value);
	}

	protected void verifyEvent(String propertyName) {
		// verify event received
		assertNotNull("No Event Fired.", this.propertyChangedEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.propertyChangedEvent.aspectName(), propertyName);
		// verify event occurence
		assertEquals("Multiple Event Received.", 1, this.propertyChangedEventCount);
	}

	protected void verifyHasNoListeners(ListValueModel listValueModel) throws Exception {
		assertTrue(((AbstractModel) listValueModel).hasNoListChangeListeners(ListValueModel.LIST_VALUES));
	}

	protected void verifyHasListeners(ListValueModel listValueModel) throws Exception {
		assertTrue(((AbstractModel) listValueModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

	protected void verifyHasListeners(PersistenceUnitProperties model, String propertyName) throws Exception {
		assertTrue("Listener not added in setUp() - " + propertyName, ((AbstractModel) model).hasAnyPropertyChangeListeners(propertyName));
	}

	protected void verifyHasListeners(PropertyValueModel pvm, String propertyName) throws Exception {
		assertTrue(((AbstractModel) pvm).hasAnyPropertyChangeListeners(propertyName));
	}

	protected void verifyPuHasProperty(String eclipseLinkPropertyName, String msg) {
		assertNotNull(msg + " - " + eclipseLinkPropertyName, this.persistenceUnit().getProperty(eclipseLinkPropertyName));
	}

	protected void verifyPuHasNotProperty(String eclipseLinkPropertyName, String msg) {
		assertNull(msg + " - " + eclipseLinkPropertyName, this.persistenceUnit().getProperty(eclipseLinkPropertyName));
	}
}
