/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.customization;

import java.util.ListIterator;

import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.persistence.GenericProperty;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkJpaProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitPropertyListListener;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.CustomizerProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.EclipseLinkCustomization;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Weaving;
import org.eclipse.jpt.eclipselink.core.tests.internal.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Tests the update of model objects by the Customization adapter when the
 * PersistenceUnit changes.
 */
public class CustomizationAdapterTests extends PersistenceUnitTestCase
{
	private Customization customization;
	private ListChangeEvent entitiesEvent;
	private ListChangeEvent sessionCustomizersEvent;

	public static final String ENTITY_TEST = "Employee";
	public static final String ENTITY_TEST_2 = "Address";

	public static final String THROW_EXCEPTIONS_KEY = Customization.ECLIPSELINK_THROW_EXCEPTIONS;
	public static final Boolean THROW_EXCEPTIONS_TEST_VALUE = false;
	public static final Boolean THROW_EXCEPTIONS_TEST_VALUE_2 = ! THROW_EXCEPTIONS_TEST_VALUE;

	public static final String WEAVING_LAZY_KEY = Customization.ECLIPSELINK_WEAVING_LAZY;
	public static final Boolean WEAVING_LAZY_TEST_VALUE = false;
	public static final Boolean WEAVING_LAZY_TEST_VALUE_2 = ! WEAVING_LAZY_TEST_VALUE;

	public static final String WEAVING_CHANGE_TRACKING_KEY = Customization.ECLIPSELINK_WEAVING_CHANGE_TRACKING;
	public static final Boolean WEAVING_CHANGE_TRACKING_TEST_VALUE = false;
	public static final Boolean WEAVING_CHANGE_TRACKING_TEST_VALUE_2 = ! WEAVING_CHANGE_TRACKING_TEST_VALUE;

	public static final String WEAVING_FETCH_GROUPS_KEY = Customization.ECLIPSELINK_WEAVING_FETCH_GROUPS;
	public static final Boolean WEAVING_FETCH_GROUPS_TEST_VALUE = false;
	public static final Boolean WEAVING_FETCH_GROUPS_TEST_VALUE_2 = ! WEAVING_FETCH_GROUPS_TEST_VALUE;

	private static final String SESSION_CUSTOMIZER_KEY = Customization.ECLIPSELINK_SESSION_CUSTOMIZER;
	private static final String SESSION_CUSTOMIZER_TEST_VALUE = "java.lang.String";
	private static final String SESSION_CUSTOMIZER_TEST_VALUE_2 = "java.lang.Boolean";

	public static final String WEAVING_KEY = Customization.ECLIPSELINK_WEAVING;
	public static final Weaving WEAVING_TEST_VALUE = Weaving.false_;
	public static final Weaving WEAVING_TEST_VALUE_2 = Weaving.static_;

	public static final String CUSTOMIZER_KEY = Customization.ECLIPSELINK_DESCRIPTOR_CUSTOMIZER + ENTITY_TEST;
	public static final String CUSTOMIZER_TEST_VALUE = "acme.sessions.DescriptorCustomizer";
	public static final String CUSTOMIZER_TEST_VALUE_2 = "acme.sessions.Customizer";

	public CustomizationAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.customization = this.persistenceUnitProperties.getCustomization();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.customization.addPropertyChangeListener(Customization.THROW_EXCEPTIONS_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_LAZY_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_CHANGE_TRACKING_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_FETCH_GROUPS_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_PROPERTY, propertyChangeListener);
		
		this.customization.addPropertyChangeListener(Customization.DESCRIPTOR_CUSTOMIZER_PROPERTY, propertyChangeListener);
		
		this.customization.addPropertyChangeListener(Customization.SESSION_CUSTOMIZER_PROPERTY, propertyChangeListener);

		ListChangeListener sessionCustomizersChangeListener = this.buildSessionCustomizersChangeListener();
		this.customization.addListChangeListener(Customization.SESSION_CUSTOMIZER_LIST_PROPERTY, sessionCustomizersChangeListener);
		
		ListChangeListener entitiesChangeListener = this.buildEntitiesChangeListener();
		this.customization.addListChangeListener(Customization.ENTITIES_LIST_PROPERTY, entitiesChangeListener);
		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 7;
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 4; // 4 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		this.persistenceUnitPut("misc.property.1", "value.1");
		this.persistenceUnitPut(THROW_EXCEPTIONS_KEY, THROW_EXCEPTIONS_TEST_VALUE.toString());
		this.persistenceUnitPut(WEAVING_LAZY_KEY, WEAVING_LAZY_TEST_VALUE.toString());
		this.persistenceUnitPut(WEAVING_CHANGE_TRACKING_KEY, WEAVING_CHANGE_TRACKING_TEST_VALUE.toString());
		this.persistenceUnitPut(WEAVING_FETCH_GROUPS_KEY, WEAVING_FETCH_GROUPS_TEST_VALUE.toString());
		this.persistenceUnitPut("misc.property.2", "value.2");
		this.persistenceUnitPut(SESSION_CUSTOMIZER_KEY, SESSION_CUSTOMIZER_TEST_VALUE.toString());
		this.persistenceUnitPut(WEAVING_KEY, WEAVING_TEST_VALUE);
		this.persistenceUnitPut("misc.property.3", "value.3");
		this.persistenceUnitPut("misc.property.4", "value.4");
		this.persistenceUnitPut(CUSTOMIZER_KEY, CUSTOMIZER_TEST_VALUE);
		return;
	}

	// ********** Listeners **********
	private ListChangeListener buildEntitiesChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsRemoved(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsReplaced(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsMoved(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void listCleared(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void listChanged(ListChangeEvent e) {
				CustomizationAdapterTests.this.entityChanged(e);
			}
		};
	}
	
	private ListChangeListener buildSessionCustomizersChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsRemoved(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsReplaced(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsMoved(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void listCleared(ListChangeEvent e) {
				CustomizationAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void listChanged(ListChangeEvent e) {
				CustomizationAdapterTests.this.sessionCustomizerChanged(e);
			}
		};
	}

	@Override
	protected void clearEvent() {
		super.clearEvent();
		this.entitiesEvent = null;
		this.sessionCustomizersEvent = null;
	}

	void entityChanged(ListChangeEvent e) {
		this.entitiesEvent = e;
	}

	void sessionCustomizerChanged(ListChangeEvent e) {
		this.sessionCustomizersEvent = e;
	}

	// ********** entities list **********
	public void testEntitiesList() throws Exception {
		// add
		this.clearEvent();
		this.customization.addEntity(ENTITY_TEST_2);
		
		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getAspectName(), Customization.ENTITIES_LIST_PROPERTY);
		
		// remove
		this.clearEvent();
		this.customization.removeEntity(ENTITY_TEST_2);
		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getAspectName(), Customization.ENTITIES_LIST_PROPERTY);
	}

	// ********** sessionCustomizers list **********
	public void testSessionCustomizersList() throws Exception {
		// add
		this.clearEvent();
		ClassRef classRef = this.customization.addSessionCustomizer(SESSION_CUSTOMIZER_TEST_VALUE_2);
		
		// verify event received
		assertNotNull("No Event Fired.", this.sessionCustomizersEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.sessionCustomizersEvent.getAspectName(), Customization.SESSION_CUSTOMIZER_LIST_PROPERTY);
		
		// remove
		this.clearEvent();
		
		this.customization.removeSessionCustomizer(classRef);
		// verify event received
		assertNotNull("No Event Fired.", this.sessionCustomizersEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.sessionCustomizersEvent.getAspectName(), Customization.SESSION_CUSTOMIZER_LIST_PROPERTY);
	}

	// ********** Listeners tests **********
	public void testHasListeners() throws Exception {
		// new
		ListAspectAdapter<PersistenceUnit, Property> propertiesAdapter = 
			(ListAspectAdapter<PersistenceUnit, Property>) ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertiesAdapter();
		GenericProperty ctdProperty = (GenericProperty) this.persistenceUnit().getProperty(THROW_EXCEPTIONS_KEY);
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();
		
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(ctdProperty.hasAnyPropertyChangeListeners(Property.VALUE_PROPERTY));
		this.verifyHasListeners(this.customization, Customization.THROW_EXCEPTIONS_PROPERTY);
		this.verifyHasListeners(this.customization, Customization.WEAVING_LAZY_PROPERTY);
		this.verifyHasListeners(this.customization, Customization.WEAVING_CHANGE_TRACKING_PROPERTY);
		this.verifyHasListeners(this.customization, Customization.WEAVING_FETCH_GROUPS_PROPERTY);
		this.verifyHasListeners(propertyListAdapter);
		
		EclipseLinkCustomization elCustomization = (EclipseLinkCustomization) this.customization;
		PersistenceUnitPropertyListListener propertyListListener = elCustomization.propertyListListener();
		propertyListAdapter.removeListChangeListener(ListValueModel.LIST_VALUES, propertyListListener);
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES)); // other properties are still listening
		this.verifyHasListeners(this.customization, Customization.THROW_EXCEPTIONS_PROPERTY);
		this.verifyHasListeners(this.customization, Customization.WEAVING_LAZY_PROPERTY);
		this.verifyHasListeners(this.customization, Customization.WEAVING_CHANGE_TRACKING_PROPERTY);
		this.verifyHasListeners(this.customization, Customization.WEAVING_FETCH_GROUPS_PROPERTY);
	}



	// ********** ThrowExceptions tests **********
	public void testSetThrowExceptions() throws Exception {
		this.verifyModelInitialized(
			THROW_EXCEPTIONS_KEY,
			THROW_EXCEPTIONS_TEST_VALUE);
		this.verifySetProperty(
			THROW_EXCEPTIONS_KEY,
			THROW_EXCEPTIONS_TEST_VALUE,
			THROW_EXCEPTIONS_TEST_VALUE_2);
	}

	public void testAddRemoveThrowExceptions() throws Exception {
		this.verifyAddRemoveProperty(
			THROW_EXCEPTIONS_KEY,
			THROW_EXCEPTIONS_TEST_VALUE,
			THROW_EXCEPTIONS_TEST_VALUE_2);
	}

	// ********** WeavingLazy tests **********
	public void testSetWeavingLazy() throws Exception {
		this.verifyModelInitialized(
			WEAVING_LAZY_KEY,
			WEAVING_LAZY_TEST_VALUE);
		this.verifySetProperty(
			WEAVING_LAZY_KEY,
			WEAVING_LAZY_TEST_VALUE,
			WEAVING_LAZY_TEST_VALUE_2);
	}

	public void testAddRemoveWeavingLazy() throws Exception {
		this.verifyAddRemoveProperty(
			WEAVING_LAZY_KEY,
			WEAVING_LAZY_TEST_VALUE,
			WEAVING_LAZY_TEST_VALUE_2);
	}

	// ********** WeavingChangeTracking tests **********
	public void testSetWeavingChangeTracking() throws Exception {
		this.verifyModelInitialized(
			WEAVING_CHANGE_TRACKING_KEY,
			WEAVING_CHANGE_TRACKING_TEST_VALUE);
		this.verifySetProperty(
			WEAVING_CHANGE_TRACKING_KEY,
			WEAVING_CHANGE_TRACKING_TEST_VALUE,
			WEAVING_CHANGE_TRACKING_TEST_VALUE_2);
	}

	public void testAddRemoveWeavingChangeTracking() throws Exception {
		this.verifyAddRemoveProperty(
			WEAVING_CHANGE_TRACKING_KEY,
			WEAVING_CHANGE_TRACKING_TEST_VALUE,
			WEAVING_CHANGE_TRACKING_TEST_VALUE_2);
	}

	// ********** WeavingFetchGroups tests **********
	public void testSetWeavingFetchGroups() throws Exception {
		this.verifyModelInitialized(
			WEAVING_FETCH_GROUPS_KEY,
			WEAVING_FETCH_GROUPS_TEST_VALUE);
		this.verifySetProperty(
			WEAVING_FETCH_GROUPS_KEY,
			WEAVING_FETCH_GROUPS_TEST_VALUE,
			WEAVING_FETCH_GROUPS_TEST_VALUE_2);
	}

	public void testAddRemoveWeavingFetchGroups() throws Exception {
		this.verifyAddRemoveProperty(
			WEAVING_FETCH_GROUPS_KEY,
			WEAVING_FETCH_GROUPS_TEST_VALUE,
			WEAVING_FETCH_GROUPS_TEST_VALUE_2);
	}

	// ********** SessionCustomizer tests **********
	public void testSetSessionCustomizer() throws Exception {
		this.verifyModelInitialized(
			SESSION_CUSTOMIZER_KEY,
			SESSION_CUSTOMIZER_TEST_VALUE);
		this.verifySetSessionCustomizationProperty(
			Customization.SESSION_CUSTOMIZER_PROPERTY,
			SESSION_CUSTOMIZER_KEY,
			SESSION_CUSTOMIZER_TEST_VALUE,
			SESSION_CUSTOMIZER_TEST_VALUE_2);
	}

	public void testAddRemoveSessionCustomizer() throws Exception {
		this.verifyAddRemoveSessionCustomizationProperty(
			Customization.SESSION_CUSTOMIZER_PROPERTY,
			SESSION_CUSTOMIZER_KEY,
			SESSION_CUSTOMIZER_TEST_VALUE,
			SESSION_CUSTOMIZER_TEST_VALUE_2);
	}

	// ********** Weaving tests **********
	/**
	 * Tests the update of Weaving property by the Customization adapter when
	 * the PU or the model changes.
	 */
	public void testSetWeaving() throws Exception {
		this.verifyModelInitialized(
			WEAVING_KEY,
			WEAVING_TEST_VALUE);
		this.verifySetProperty(
			WEAVING_KEY,
			WEAVING_TEST_VALUE,
			WEAVING_TEST_VALUE_2);
	}

	public void testAddRemoveWeaving() throws Exception {
		this.verifyAddRemoveProperty(
			WEAVING_KEY,
			WEAVING_TEST_VALUE,
			WEAVING_TEST_VALUE_2);
	}

	// ********** Customization class **********
	/**
	 * Tests the update of Customization property by the Customization adapter when the
	 * PU or the model changes.
	 */
	public void testSetCustomization() throws Exception {
		this.verifyModelInitialized(
			CUSTOMIZER_KEY,
			CUSTOMIZER_TEST_VALUE);
		this.verifySetCustomizationProperty(
			Customization.DESCRIPTOR_CUSTOMIZER_PROPERTY,
			CUSTOMIZER_KEY,
			CUSTOMIZER_TEST_VALUE,
			CUSTOMIZER_TEST_VALUE_2);
	}

	public void testAddRemoveCustomization() throws Exception {
		this.verifyAddRemoveCustomizationProperty(
			Customization.DESCRIPTOR_CUSTOMIZER_PROPERTY,
			CUSTOMIZER_KEY,
			CUSTOMIZER_TEST_VALUE,
			CUSTOMIZER_TEST_VALUE_2);
	}


	// ****** convenience methods *******

	// ********** verify SessionCustomizer property **********
	protected void verifySetSessionCustomizationProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();
		// Basic
		this.verifyInitialState(propertyName, key, propertyListAdapter);
		
		// Replace
		this.persistenceUnitPut(key, testValue2, true); 
		this.propertiesTotal++;
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutSessionCustomizerProperty(propertyName, testValue1);
	}
	
	protected void verifyAddRemoveSessionCustomizationProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.persistenceUnit().removeProperty(key, (String) testValue1);
		assertFalse(this.customization.sessionCustomizerExists(key));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		
		// Add original Property
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitPut(key, testValue1, true); 
		this.verifyPutSessionCustomizerProperty(propertyName, testValue1);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
	}

	protected void verifyPutSessionCustomizerProperty(String propertyName, Object expectedValue) throws Exception {
		// verify event received
		assertNotNull("No Event Fired.", this.sessionCustomizersEvent);
		this.verifySessionCustomizerEvent(propertyName, expectedValue);
	}

	protected void verifySessionCustomizerEvent(String propertyName, Object expectedValue) throws Exception {
		// verify event value
		EclipseLinkCustomization customization = (EclipseLinkCustomization) this.sessionCustomizersEvent.getSource();
		assertTrue(customization.sessionCustomizerExists((String) expectedValue));
		 return;
	}
	
	// ********** verify entity property **********
	protected void verifySetCustomizationProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();
		// Basic
		this.verifyInitialState(propertyName, key, propertyListAdapter);
		
		// Replace
		this.persistenceUnitPut(key, testValue2);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, testValue2);
		
		// Replace by setting model object
		this.clearEvent();
		this.setCustomizationProperty(propertyName, ENTITY_TEST, testValue1);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, testValue1);
	}

	protected void verifyAddRemoveCustomizationProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.persistenceUnit().removeProperty(key);
		assertFalse(this.persistenceUnit().containsProperty(key));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, null);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		
		// Add original Property
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitPut(key, testValue1);
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, testValue1);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		
		// Replace
		this.persistenceUnitPut(key, testValue2);
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, testValue2);
	}

	protected void verifyPutCustomizationProperty(String propertyName, String entityName, Object expectedValue) throws Exception {
		this.verifyEvent(propertyName);
		this.verifyCustomizationEvent(propertyName, entityName, expectedValue);
	}

	protected void verifyCustomizationEvent(String propertyName, String entityName, Object expectedValue) throws Exception {
		// verify event value
		CustomizerProperties customizer = (CustomizerProperties) this.propertyChangedEvent.getNewValue();
		if (propertyName.equals(Customization.DESCRIPTOR_CUSTOMIZER_PROPERTY)) {
			assertEquals(expectedValue, customizer.getClassName());
			assertEquals(expectedValue, this.customization.getDescriptorCustomizer(entityName));
		}
		else {
			this.throwMissingDefinition("verifyCustomizationEvent", propertyName);
		}
	}

	protected void setCustomizationProperty(String propertyName, String entityName, Object newValue) throws NoSuchFieldException {
		if (propertyName.equals(Customization.DESCRIPTOR_CUSTOMIZER_PROPERTY))
			this.customization.setDescriptorCustomizer((String) newValue, entityName);
		else
			this.throwMissingDefinition("setCustomizationProperty", propertyName);
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(Customization.THROW_EXCEPTIONS_PROPERTY))
			this.customization.setThrowExceptions((Boolean) newValue);
		else if (propertyName.equals(Customization.WEAVING_LAZY_PROPERTY))
			this.customization.setWeavingLazy((Boolean) newValue);
		else if (propertyName.equals(Customization.WEAVING_CHANGE_TRACKING_PROPERTY))
			this.customization.setWeavingChangeTracking((Boolean) newValue);
		else if (propertyName.equals(Customization.WEAVING_FETCH_GROUPS_PROPERTY))
			this.customization.setWeavingFetchGroups((Boolean) newValue);
		else if (propertyName.equals(Customization.SESSION_CUSTOMIZER_PROPERTY))
			this.customization.addSessionCustomizer((String) newValue);
		else if (propertyName.equals(Customization.WEAVING_PROPERTY))
			this.customization.setWeaving((Weaving) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(Customization.THROW_EXCEPTIONS_PROPERTY))
			modelValue = this.customization.getThrowExceptions();
		else if (propertyName.equals(Customization.WEAVING_PROPERTY))
			modelValue = this.customization.getWeaving();
		else if (propertyName.equals(Customization.WEAVING_LAZY_PROPERTY))
			modelValue = this.customization.getWeavingLazy();
		else if (propertyName.equals(Customization.WEAVING_CHANGE_TRACKING_PROPERTY))
			modelValue = this.customization.getWeavingChangeTracking();
		else if (propertyName.equals(Customization.WEAVING_FETCH_GROUPS_PROPERTY))
			modelValue = this.customization.getWeavingFetchGroups();
		else if (propertyName.equals(Customization.SESSION_CUSTOMIZER_PROPERTY)) {
			ListIterator<ClassRef> iterator = this.customization.sessionCustomizers();
			if(iterator.hasNext()) {
				modelValue = iterator.next().getClassName();
			}
		}
		else if (propertyName.equals(Customization.DESCRIPTOR_CUSTOMIZER_PROPERTY))
			modelValue = this.customization.getDescriptorCustomizer(ENTITY_TEST);
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}

	protected PersistenceUnitProperties model() {
		return this.customization;
	}
}
