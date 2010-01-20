/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.customization;

import java.util.ListIterator;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Profiler;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Weaving;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.EclipseLinkCustomization;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.Entity;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.EclipseLinkPersistenceUnitTestCase;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;

/**
 * Tests the update of model objects by the Customization adapter when the
 * PersistenceUnit changes.
 */
@SuppressWarnings("nls")
public class EclipseLinkCustomizationTests extends EclipseLinkPersistenceUnitTestCase
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

	public static final String WEAVING_INTERNAL_KEY = Customization.ECLIPSELINK_WEAVING_INTERNAL;
	public static final Boolean WEAVING_INTERNAL_TEST_VALUE = false;
	public static final Boolean WEAVING_INTERNAL_TEST_VALUE_2 = ! WEAVING_INTERNAL_TEST_VALUE;

	public static final String WEAVING_EAGER_KEY = Customization.ECLIPSELINK_WEAVING_EAGER;
	public static final Boolean WEAVING_EAGER_TEST_VALUE = true;
	public static final Boolean WEAVING_EAGER_TEST_VALUE_2 = ! WEAVING_EAGER_TEST_VALUE;

	public static final String VALIDATION_ONLY_KEY = Customization.ECLIPSELINK_VALIDATION_ONLY;
	public static final Boolean VALIDATION_ONLY_TEST_VALUE = false;
	public static final Boolean VALIDATION_ONLY_TEST_VALUE_2 = ! VALIDATION_ONLY_TEST_VALUE;

	public static final String VALIDATE_SCHEMA_KEY = Customization.ECLIPSELINK_VALIDATE_SCHEMA;
	public static final Boolean VALIDATE_SCHEMA_TEST_VALUE = true;
	public static final Boolean VALIDATE_SCHEMA_TEST_VALUE_2 = ! VALIDATE_SCHEMA_TEST_VALUE;

	private static final String SESSION_CUSTOMIZER_KEY = Customization.ECLIPSELINK_SESSION_CUSTOMIZER;
	private static final String SESSION_CUSTOMIZER_TEST_VALUE = "java.lang.String";
	private static final String SESSION_CUSTOMIZER_TEST_VALUE_2 = "java.lang.Boolean";

	public static final String WEAVING_KEY = Customization.ECLIPSELINK_WEAVING;
	public static final Weaving WEAVING_TEST_VALUE = Weaving.false_;
	public static final Weaving WEAVING_TEST_VALUE_2 = Weaving.static_;

	public static final String CUSTOMIZER_KEY = Customization.ECLIPSELINK_DESCRIPTOR_CUSTOMIZER + ENTITY_TEST;
	public static final String CUSTOMIZER_TEST_VALUE = "acme.sessions.DescriptorCustomizer";
	public static final String CUSTOMIZER_TEST_VALUE_2 = "acme.sessions.Customizer";

	private static final String PROFILER_KEY = Customization.ECLIPSELINK_PROFILER;
	private static final Profiler PROFILER_TEST_VALUE = Profiler.query_monitor;
	private static final String PROFILER_TEST_VALUE_2 = "custom.profiler.test";
	
	public static final String EXCEPTION_HANDLER_KEY = Customization.ECLIPSELINK_EXCEPTION_HANDLER;
	public static final String EXCEPTION_HANDLER_TEST_VALUE = "acme.CustomSessionEventListener";
	public static final String EXCEPTION_HANDLER_TEST_VALUE_2 = "oracle.sessions.CustomSessionEventListener";

	// ********** constructors **********
	public EclipseLinkCustomizationTests(String name) {
		super(name);
	}

	// ********** behavior **********
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.customization = this.subject.getCustomization();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.customization.addPropertyChangeListener(Customization.THROW_EXCEPTIONS_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_LAZY_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_CHANGE_TRACKING_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_FETCH_GROUPS_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_INTERNAL_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_EAGER_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.WEAVING_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.VALIDATION_ONLY_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.VALIDATE_SCHEMA_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Entity.DESCRIPTOR_CUSTOMIZER_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.SESSION_CUSTOMIZER_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.PROFILER_PROPERTY, propertyChangeListener);
		this.customization.addPropertyChangeListener(Customization.EXCEPTION_HANDLER_PROPERTY, propertyChangeListener);

		ListChangeListener sessionCustomizersChangeListener = this.buildSessionCustomizersChangeListener();
		this.customization.addListChangeListener(Customization.SESSION_CUSTOMIZER_LIST, sessionCustomizersChangeListener);
		
		ListChangeListener entitiesChangeListener = this.buildEntitiesChangeListener();
		this.customization.addListChangeListener(Customization.ENTITIES_LIST, entitiesChangeListener);
		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 13;
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 4; // 4 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(THROW_EXCEPTIONS_KEY, THROW_EXCEPTIONS_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(WEAVING_LAZY_KEY, WEAVING_LAZY_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(WEAVING_CHANGE_TRACKING_KEY, WEAVING_CHANGE_TRACKING_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(WEAVING_FETCH_GROUPS_KEY, WEAVING_FETCH_GROUPS_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(WEAVING_INTERNAL_KEY, WEAVING_INTERNAL_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(WEAVING_EAGER_KEY, WEAVING_EAGER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(VALIDATION_ONLY_KEY, VALIDATION_ONLY_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(VALIDATE_SCHEMA_KEY, VALIDATE_SCHEMA_TEST_VALUE.toString());
		this.persistenceUnitSetProperty("misc.property.2", "value.2");
		this.persistenceUnitSetProperty(SESSION_CUSTOMIZER_KEY, SESSION_CUSTOMIZER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(WEAVING_KEY, WEAVING_TEST_VALUE);
		this.persistenceUnitSetProperty("misc.property.3", "value.3");
		this.persistenceUnitSetProperty("misc.property.4", "value.4");
		this.persistenceUnitSetProperty(CUSTOMIZER_KEY, CUSTOMIZER_TEST_VALUE);
		this.persistenceUnitSetProperty(PROFILER_KEY, PROFILER_TEST_VALUE);
		this.persistenceUnitSetProperty(EXCEPTION_HANDLER_KEY, EXCEPTION_HANDLER_TEST_VALUE);
		return;
	}

	// ********** Listeners **********
	private ListChangeListener buildEntitiesChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsRemoved(ListRemoveEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsReplaced(ListReplaceEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsMoved(ListMoveEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void listCleared(ListClearEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void listChanged(ListChangeEvent e) {
				EclipseLinkCustomizationTests.this.entityChanged(e);
			}
		};
	}
	
	private ListChangeListener buildSessionCustomizersChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsRemoved(ListRemoveEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsReplaced(ListReplaceEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsMoved(ListMoveEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void listCleared(ListClearEvent e) {
				EclipseLinkCustomizationTests.this.throwUnsupportedOperationException(e);
			}

			public void listChanged(ListChangeEvent e) {
				EclipseLinkCustomizationTests.this.sessionCustomizerChanged(e);
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
		else if (propertyName.equals(Customization.WEAVING_INTERNAL_PROPERTY))
			this.customization.setWeavingInternal((Boolean) newValue);
		else if (propertyName.equals(Customization.WEAVING_EAGER_PROPERTY))
			this.customization.setWeavingEager((Boolean) newValue);
		else if (propertyName.equals(Customization.VALIDATION_ONLY_PROPERTY))
			this.customization.setValidationOnly((Boolean) newValue);
		else if (propertyName.equals(Customization.VALIDATE_SCHEMA_PROPERTY))
			this.customization.setValidateSchema((Boolean) newValue);
		else if (propertyName.equals(Customization.EXCEPTION_HANDLER_PROPERTY))
			this.customization.setExceptionHandler((String) newValue);
		else if (propertyName.equals(Customization.SESSION_CUSTOMIZER_PROPERTY))
			this.customization.addSessionCustomizer((String) newValue);
		else if (propertyName.equals(Customization.WEAVING_PROPERTY))
			this.customization.setWeaving((Weaving) newValue);
		else if (propertyName.equals(Customization.PROFILER_PROPERTY)) {
			if (newValue.getClass().isEnum())
				this.customization.setProfiler((Profiler) newValue);
			else
				this.customization.setProfiler((String) newValue);
		}
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
		else if (propertyName.equals(Customization.WEAVING_INTERNAL_PROPERTY))
			modelValue = this.customization.getWeavingInternal();
		else if (propertyName.equals(Customization.WEAVING_EAGER_PROPERTY))
			modelValue = this.customization.getWeavingEager();
		else if (propertyName.equals(Customization.VALIDATION_ONLY_PROPERTY))
			modelValue = this.customization.getValidationOnly();
		else if (propertyName.equals(Customization.VALIDATE_SCHEMA_PROPERTY))
			modelValue = this.customization.getValidateSchema();
		else if (propertyName.equals(Customization.EXCEPTION_HANDLER_PROPERTY))
			modelValue = this.customization.getExceptionHandler();
		else if (propertyName.equals(Customization.PROFILER_PROPERTY))
			modelValue = this.customization.getProfiler();
		else if (propertyName.equals(Customization.SESSION_CUSTOMIZER_PROPERTY)) {
			ListIterator<String> iterator = this.customization.sessionCustomizers();
			if(iterator.hasNext()) {
				modelValue = iterator.next();
			}
		}
		else if (propertyName.equals(Entity.DESCRIPTOR_CUSTOMIZER_PROPERTY))
			modelValue = this.customization.getDescriptorCustomizerOf(ENTITY_TEST);
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
	
	@Override
	protected void verifyPutProperty(String propertyName, Object expectedValue) throws Exception {
		Object expectedValue_ = expectedValue;
		if (propertyName.equals(Customization.PROFILER_PROPERTY)) {
			
			expectedValue_ = (expectedValue != null && expectedValue.getClass().isEnum()) ?
				this.getPropertyStringValueOf(PROFILER_TEST_VALUE) : // model is storing EclipseLinkStringValue
				expectedValue;
		}
		super.verifyPutProperty(propertyName, expectedValue_);
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.customization;
	}

	// ********** entities list **********
	public void testEntitiesList() throws Exception {
		// add
		this.clearEvent();
		this.customization.addEntity(ENTITY_TEST_2);
		
		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getListName(), Customization.ENTITIES_LIST);
		
		// remove
		this.clearEvent();
		this.customization.removeEntity(ENTITY_TEST_2);
		
		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getListName(), Customization.ENTITIES_LIST);
	}

	// ********** sessionCustomizers list **********
	public void testSessionCustomizersList() throws Exception {
		// add
		this.clearEvent();
		String className = this.customization.addSessionCustomizer(SESSION_CUSTOMIZER_TEST_VALUE_2);
		
		// verify event received
		assertNotNull("No Event Fired.", this.sessionCustomizersEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.sessionCustomizersEvent.getListName(), Customization.SESSION_CUSTOMIZER_LIST);
		
		// remove
		this.clearEvent();
		
		this.customization.removeSessionCustomizer(className);
		// verify event received
		assertNotNull("No Event Fired.", this.sessionCustomizersEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.sessionCustomizersEvent.getListName(), Customization.SESSION_CUSTOMIZER_LIST);
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

	// ********** WeavingInternal tests **********
	public void testSetWeavingInternal() throws Exception {
		this.verifyModelInitialized(
			WEAVING_INTERNAL_KEY,
			WEAVING_INTERNAL_TEST_VALUE);
		this.verifySetProperty(
			WEAVING_INTERNAL_KEY,
			WEAVING_INTERNAL_TEST_VALUE,
			WEAVING_INTERNAL_TEST_VALUE_2);
	}

	public void testAddRemoveWeavingInternal() throws Exception {
		this.verifyAddRemoveProperty(
			WEAVING_INTERNAL_KEY,
			WEAVING_INTERNAL_TEST_VALUE,
			WEAVING_INTERNAL_TEST_VALUE_2);
	}

	// ********** WeavingEager tests **********
	public void testSetWeavingEager() throws Exception {
		this.verifyModelInitialized(
			WEAVING_EAGER_KEY,
			WEAVING_EAGER_TEST_VALUE);
		this.verifySetProperty(
			WEAVING_EAGER_KEY,
			WEAVING_EAGER_TEST_VALUE,
			WEAVING_EAGER_TEST_VALUE_2);
	}

	public void testAddRemoveWeavingEager() throws Exception {
		this.verifyAddRemoveProperty(
			WEAVING_EAGER_KEY,
			WEAVING_EAGER_TEST_VALUE,
			WEAVING_EAGER_TEST_VALUE_2);
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

	// ********** ValidationOnly tests **********
	public void testSetValidationOnly() throws Exception {
		this.verifyModelInitialized(
			VALIDATION_ONLY_KEY,
			VALIDATION_ONLY_TEST_VALUE);
		this.verifySetProperty(
			VALIDATION_ONLY_KEY,
			VALIDATION_ONLY_TEST_VALUE,
			VALIDATION_ONLY_TEST_VALUE_2);
	}

	public void testAddRemoveValidationOnly() throws Exception {
		this.verifyAddRemoveProperty(
			VALIDATION_ONLY_KEY,
			VALIDATION_ONLY_TEST_VALUE,
			VALIDATION_ONLY_TEST_VALUE_2);
	}

	// ********** ValidateSchema tests **********
	public void testSetValidateSchema() throws Exception {
		this.verifyModelInitialized(
			VALIDATE_SCHEMA_KEY,
			VALIDATE_SCHEMA_TEST_VALUE);
		this.verifySetProperty(
			VALIDATE_SCHEMA_KEY,
			VALIDATE_SCHEMA_TEST_VALUE,
			VALIDATE_SCHEMA_TEST_VALUE_2);
	}

	public void testAddRemoveValidateSchema() throws Exception {
		this.verifyAddRemoveProperty(
			VALIDATE_SCHEMA_KEY,
			VALIDATE_SCHEMA_TEST_VALUE,
			VALIDATE_SCHEMA_TEST_VALUE_2);
	}

	// ********** ExceptionHandler tests **********
	public void testSetExceptionHandler() throws Exception {
		this.verifyModelInitialized(
			EXCEPTION_HANDLER_KEY,
			EXCEPTION_HANDLER_TEST_VALUE);
		this.verifySetProperty(
			EXCEPTION_HANDLER_KEY,
			EXCEPTION_HANDLER_TEST_VALUE,
			EXCEPTION_HANDLER_TEST_VALUE_2);
	}

	public void testAddRemoveExceptionHandler() throws Exception {
		this.verifyAddRemoveProperty(
			EXCEPTION_HANDLER_KEY,
			EXCEPTION_HANDLER_TEST_VALUE,
			EXCEPTION_HANDLER_TEST_VALUE_2);
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
			Entity.DESCRIPTOR_CUSTOMIZER_PROPERTY,
			CUSTOMIZER_KEY,
			CUSTOMIZER_TEST_VALUE,
			CUSTOMIZER_TEST_VALUE_2);
	}

	public void testAddRemoveCustomization() throws Exception {
		this.verifyAddRemoveCustomizationProperty(
			Entity.DESCRIPTOR_CUSTOMIZER_PROPERTY,
			CUSTOMIZER_KEY,
			CUSTOMIZER_TEST_VALUE,
			CUSTOMIZER_TEST_VALUE_2);
	}
	
	// ********** Profiler tests **********
	public void testSetProfiler() throws Exception {
		this.verifyModelInitialized(
			PROFILER_KEY,
			this.getPropertyStringValueOf(PROFILER_TEST_VALUE)); // model is storing EclipseLinkStringValue
		// verify set enum value
		this.verifySetProperty(
			PROFILER_KEY,
			PROFILER_TEST_VALUE,
			PROFILER_TEST_VALUE_2);
		// verify set custom and literal value
		this.verifySetProfiler(
			PROFILER_KEY,
			PROFILER_TEST_VALUE,
			PROFILER_TEST_VALUE_2);
	}

	public void testAddRemoveProfiler() throws Exception {
		this.verifyAddRemoveProperty(
			PROFILER_KEY,
			PROFILER_TEST_VALUE,
			PROFILER_TEST_VALUE_2);
	}
	
	/**
	 * Verifies setting custom profiler and literals.
	 */
	protected void verifySetProfiler(String elKey, Object testValue1, Object testValue2) throws Exception {
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(elKey);
		String propertyName = this.getModel().propertyIdOf(property);
		// test set custom profiler.
		this.clearEvent();
		this.setProperty(propertyName, testValue2);
		this.verifyPutProperty(propertyName, testValue2);

		// test set (Profiler) null
		this.clearEvent();
		this.customization.setProfiler((Profiler) null);
		assertNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, null);
		
		// test set enum literal
		this.clearEvent();
		this.setProperty(propertyName, testValue1.toString());
		assertNotNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, this.getPropertyStringValueOf(testValue1));

		// test set (String) null
		this.clearEvent();
		this.customization.setProfiler((String) null);
		assertNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, null);
	}



	// ****** convenience methods *******

	// ********** verify SessionCustomizer property **********
	protected void verifySetSessionCustomizationProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		// Replace
		this.persistenceUnitSetProperty(key, testValue2, true); 
		this.propertiesTotal++;
		this.verifyPutSessionCustomizerProperty(propertyName, testValue1);
	}
	
	@SuppressWarnings("unused") 
	protected void verifyAddRemoveSessionCustomizationProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.getPersistenceUnit().removeProperty(key, (String) testValue1);
		assertFalse(this.customization.sessionCustomizerExists(key));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		
		// Add original Property
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitSetProperty(key, testValue1, true); 
		this.verifyPutSessionCustomizerProperty(propertyName, testValue1);
	}

	protected void verifyPutSessionCustomizerProperty(String propertyName, Object expectedValue) throws Exception {
		// verify event received
		assertNotNull("No Event Fired.", this.sessionCustomizersEvent);
		this.verifySessionCustomizerEvent(propertyName, expectedValue);
	}

	@SuppressWarnings("unused")
	protected void verifySessionCustomizerEvent(String propertyName, Object expectedValue) throws Exception {
		// verify event value
		EclipseLinkCustomization customization = (EclipseLinkCustomization) this.sessionCustomizersEvent.getSource();
		assertTrue(customization.sessionCustomizerExists((String) expectedValue));
		 return;
	}
	
	// ********** verify entity property **********
	protected void verifySetCustomizationProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		// Replace
		this.persistenceUnitSetProperty(key, testValue2);
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, testValue2);
		
		// Replace by setting model object
		this.clearEvent();
		this.setCustomizationProperty(propertyName, ENTITY_TEST, testValue1);
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, testValue1);
	}

	protected void verifyAddRemoveCustomizationProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.getPersistenceUnit().removeProperty(key);
		assertNull(this.getPersistenceUnit().getProperty(key));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, null);
		
		// Add original Property
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitSetProperty(key, testValue1);
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, testValue1);
		
		// Replace
		this.persistenceUnitSetProperty(key, testValue2);
		this.verifyPutCustomizationProperty(propertyName, ENTITY_TEST, testValue2);
	}

	protected void verifyPutCustomizationProperty(String propertyName, String entityName, Object expectedValue) throws Exception {
		this.verifyEvent(propertyName);
		this.verifyCustomizationEvent(propertyName, entityName, expectedValue);
	}

	protected void verifyCustomizationEvent(String propertyName, String entityName, Object expectedValue) throws Exception {
		// verify event value
		Entity entity = (Entity) this.propertyChangedEvent.getNewValue();
		if (propertyName.equals(Entity.DESCRIPTOR_CUSTOMIZER_PROPERTY)) {
			assertEquals(expectedValue, entity.getParent().getDescriptorCustomizerOf(entityName));
			assertEquals(expectedValue, this.customization.getDescriptorCustomizerOf(entityName));
		}
		else {
			this.throwMissingDefinition("verifyCustomizationEvent", propertyName);
		}
	}

	protected void setCustomizationProperty(String propertyName, String entityName, Object newValue) throws NoSuchFieldException {
		if (propertyName.equals(Entity.DESCRIPTOR_CUSTOMIZER_PROPERTY))
			this.customization.setDescriptorCustomizerOf(entityName, (String) newValue);
		else
			this.throwMissingDefinition("setCustomizationProperty", propertyName);
	}
}
