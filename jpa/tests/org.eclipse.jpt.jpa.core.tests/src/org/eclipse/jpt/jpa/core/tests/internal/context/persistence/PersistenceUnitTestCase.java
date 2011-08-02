/*******************************************************************************
* Copyright (c) 2009, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.persistence;

import java.util.Iterator;

import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.model.event.ListEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

/**
 *  PersistenceUnitTestCase
 */
@SuppressWarnings("nls")
public abstract class PersistenceUnitTestCase extends ContextModelTestCase
{
	protected PropertyChangeEvent propertyChangedEvent;

	protected int propertyChangedEventCount;

	protected int propertiesTotal;

	protected int modelPropertiesSizeOriginal;

	protected int modelPropertiesSize;

	// ********** constructors **********
	protected PersistenceUnitTestCase(String name) {
		super(name);
	}
	
	// ****** abstract methods *******
	protected abstract PersistenceUnitProperties getModel();

	/**
	 * Initializes directly the PU properties before testing. Cannot use
	 * Property Holder to initialize because it is not created yet
	 */
	protected abstract void populatePu();

	/**
	 * Gets the model's property identified by the given propertyName.
	 * 
	 * @param propertyName
	 *            name of property to get
	 * @throws Exception
	 */
	protected abstract Object getProperty(String propertyName) throws Exception;


	/**
	 * Sets the model's property identified by the given propertyName.
	 * Used in verifySetProperty()
	 * 
	 * @param propertyName
	 *            name of property to be set
	 * @param newValue
	 *            value of property
	 * @throws Exception
	 */
	protected abstract void setProperty(String propertyName, Object newValue) throws Exception;

	
	// ****** convenience test methods *******

	protected String getPropertyStringValueOf(Object value) {
		return AbstractPersistenceUnitProperties.getPropertyStringValueOf(value);
	}

	protected <E> E getFirstElement(Iterator<E> iterator) {
		if(iterator.hasNext()) {
			return iterator.next();
		}
		return null;
	}

	/**
	 * Put into persistenceUnit properties. Do not allows to put duplicate entry.
	 * 
	 * @param key -
	 *            PersistenceUnit property key
	 * @param value -
	 *            property value
	 */
	protected void persistenceUnitSetProperty(String key, Object value) {
		
		this.persistenceUnitSetProperty( key, value, false);
	}
	
	protected void persistenceUnitSetProperty(String key, Object value, boolean allowDuplicates) {
		if (key == null) {
			throw new IllegalArgumentException("PersistenceUnit property key cannot be null");
		}
		if (value == null)
			this.setNullProperty(key);
		else
			this.putProperty_(key, value, allowDuplicates);
	}

	private void putProperty_(String puKey, Object value, boolean allowDuplicates) {
		this.clearEvent();
		this.getPersistenceUnit().setProperty(puKey, this.getPropertyStringValueOf(value), allowDuplicates);
	}

	protected void setNullProperty(String puKey) {
		this.clearEvent();
		this.getPersistenceUnit().setProperty(puKey, null, false);
	}

	protected void clearEvent() {
		this.propertyChangedEvent = null;
		this.propertyChangedEventCount = 0;
	}
	
	protected void throwMissingDefinition(String methodName, String propertyName) throws NoSuchFieldException {
		throw new NoSuchFieldException("Missing Definition for: " + methodName + "( " + propertyName + ")");
	}

	public void throwUnsupportedOperationException(ListEvent e) {
		throw new UnsupportedOperationException(e.getListName());
	}

	protected PropertyChangeListener buildPropertyChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				PersistenceUnitTestCase.this.propertyChangedEvent = event;
				PersistenceUnitTestCase.this.propertyChangedEventCount++;
			}
	
			@Override
			public String toString() {
				return "PersistenceUnit listener";
			}
		};
	}
	
	/**
	 * Verify if the property exists in the pesistence.xml
	 */
	protected boolean propertyExists(String puPropertyName) {
		return this.getPersistenceUnit().getProperty(puPropertyName) != null;
	}

	protected boolean propertyValueEquals(String puPropertyName, String propertyValue) {
		return this.getPersistenceUnit().getProperty(puPropertyName).getValue().equals(propertyValue);
	}
	
	// ****** verify PersistenceUnit properties *******
	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyAAValue(Boolean expectedValue, Boolean subjectValue, PropertyValueModel<Boolean> aa, String persistenceXmlKey) {
		assertEquals(expectedValue, subjectValue);
		assertEquals(expectedValue, aa.getValue());
		if (expectedValue != null) {
			assertTrue("PersistenceUnit property value not equals", this.propertyValueEquals(persistenceXmlKey, expectedValue.toString()));
		}
	}

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected <T extends Enum<T>> void verifyAAValue(T expectedValue, T subjectValue, PropertyValueModel<? extends Enum<T>> aa, String puKey) {
		assertEquals(expectedValue, subjectValue);
		assertEquals(expectedValue, aa.getValue());
		if (expectedValue != null) {
			assertTrue("PersistenceUnit property value not equals", this.propertyValueEquals(puKey, this.getPropertyStringValueOf(expectedValue)));
		}
	}

	/**
	 * Performs the following tests:<br>
	 * 1. verify total number of PersistenceUnit properties<br>
	 * 2. verify PU has the given propertyName<br>
	 * 3. verify listening to propertyListAdapter<br>
	 * 4. verify that the model can identify propertyName<br>
	 */
	protected void verifyInitialState(String propertyName, String puKey, ListValueModel<PersistenceUnit.Property> propertyListAdapter) throws Exception {
		assertEquals("Total not updated in populatePu(): ", propertyListAdapter.size(), this.propertiesTotal);
		this.verifyPuHasProperty(puKey, "Property not added to populatePu()");
		this.verifyHasListeners(propertyListAdapter);
		this.verifyHasListeners(this.getModel(), propertyName);
		
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(puKey);
		assertTrue("model.itemIsProperty() is false: ", this.getModel().itemIsProperty(property));
		assertEquals("propertyIdFor() not updated: ", propertyName, this.getModel().propertyIdOf(property));
	}

	/**
	 * Verifies that the persistence unit is populated, and that the model for
	 * the tested Property is initialized with the value from the persistence
	 * unit.
	 * @throws Exception 
	 */
	protected void verifyModelInitialized(String puKey, Object expectedValue) throws Exception {
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(puKey);
		assertTrue("model.itemIsProperty() is false: ", this.getModel().itemIsProperty(property));

		assertTrue("PersistenceUnit not populated - populatedPu()", this.propertyValueEquals(puKey, this.getPropertyStringValueOf(expectedValue)));
		String propertyName = this.getModel().propertyIdOf(property);
		Object modelValue = this.getProperty(propertyName);
		assertEquals(
			"Model not initialized - model.initializeProperties() - modelValue = " + modelValue, 
			expectedValue, 
			modelValue);
	}

	/**
	 * Performs the following operations with the property:<br>
	 * 1. verifies the initial state<br>
	 * 2. persistenceUnit putProperty<br>
	 * 3. adapter setProperty<br>
	 */
	protected void verifySetProperty(String puKey, Object testValue1, Object testValue2) throws Exception {
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(puKey);
		String propertyName = this.getModel().propertyIdOf(property);

		// Replace
		this.persistenceUnitSetProperty(puKey, testValue2);
		this.verifyPutProperty(propertyName, testValue2);
		
		// Replace by setting model object
		this.clearEvent();
		this.setProperty(propertyName, testValue1);
		this.verifyPutProperty(propertyName, testValue1);
	}

	/**
	 * Performs the following operations with the property:<br>
	 * 1. performs a remove on the PU<br>
	 * 2. performs a add with putProperty<br>
	 * 3. performs a replace with putProperty<br>
	 */
	protected void verifyAddRemoveProperty(String puKey, Object testValue1, Object testValue2) throws Exception {
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(puKey);
		String propertyName = this.getModel().propertyIdOf(property);

		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.verifyPuHasProperty(puKey,  "persistenceUnit.properties doesn't contains: ");
		this.getPersistenceUnit().removeProperty(puKey);
		assertNull(this.getPersistenceUnit().getProperty(puKey));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		this.verifyPutProperty(propertyName, null);
		
		// Add original CacheTypeDefault
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitSetProperty(puKey, testValue1);
		this.verifyPutProperty(propertyName, testValue1);
		
		// Replace
		this.persistenceUnitSetProperty(puKey, testValue2);
		this.verifyPutProperty(propertyName, testValue2);
	}
	
	/**
	 * Verifies the model's property identified by the given propertyName
	 * Used in verifySetProperty() and verifyAddRemoveProperty
	 * 
	 * @param propertyName
	 *            name of property to be verified
	 * @param expectedValue
	 * @throws Exception
	 */
	protected void verifyPutProperty(String propertyName, Object expectedValue) throws Exception {

		this.verifyPutEvent(propertyName, this.getProperty(propertyName), expectedValue);
	}

	/**
	 * Verifies the event of the put() action.
	 * 
	 * @param propertyName
	 *            name of property to be verified
	 * @param propertyValue
	 *            value of property
	 * @param expectedValue
	 * @throws Exception
	 */
	protected void verifyPutEvent(String propertyName, Object propertyValue, Object expectedValue) {
		
		this.verifyEvent(propertyName);
		this.verifyEventValue(propertyValue, expectedValue);
	}

	/**
	 * Performs the following tests:<br>
	 * 1. verifies the new value of this.propertyChangedEvent<br>
	 * 2. verifies the given value<br>
	 */
	protected void verifyEventValue(Object value, Object expectedValue) {
		// verify event value
		assertEquals(expectedValue, this.propertyChangedEvent.getNewValue());
		assertEquals(expectedValue, value);
	}

	/**
	 * Performs the following tests:<br>
	 * 1. verifies that an event is fired<br>
	 * 2. verifies that it is the correct event<br>
	 * 3. verifies that a single event is fired<br>
	 */
	protected void verifyEvent(String propertyName) {
		// verify event received
		assertNotNull("No Event Fired.", this.propertyChangedEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.propertyChangedEvent.getPropertyName(), propertyName);
		// verify event occurrence
		assertTrue("No Event Received.", this.propertyChangedEventCount > 0);
		assertTrue("Multiple Event Received (" +  this.propertyChangedEventCount + ")",
			this.propertyChangedEventCount < 2);
	}

	protected void verifyHasNoListeners(ListValueModel<?> listValueModel) throws Exception {
		assertTrue(((AbstractModel) listValueModel).hasNoListChangeListeners(ListValueModel.LIST_VALUES));
	}

	protected void verifyHasListeners(ListValueModel<?> listValueModel) throws Exception {
		assertTrue(((AbstractModel) listValueModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

	protected void verifyHasListeners(PersistenceUnitProperties model, String propertyName) throws Exception {
		assertTrue("Listener not added in setUp() - " + propertyName, ((AbstractModel) model).hasAnyPropertyChangeListeners(propertyName));
	}

	protected void verifyHasListeners(PropertyValueModel<?> pvm, String propertyName) throws Exception {
		assertTrue(((AbstractModel) pvm).hasAnyPropertyChangeListeners(propertyName));
	}

	protected void verifyPuHasProperty(String puPropertyName, String msg) {
		assertTrue(msg + " - " + puPropertyName, this.propertyExists(puPropertyName));
	}

	protected void verifyPuHasNotProperty(String puPropertyName, String msg) {
		assertFalse(msg + " - " + puPropertyName, this.propertyExists(puPropertyName));
	}

}
