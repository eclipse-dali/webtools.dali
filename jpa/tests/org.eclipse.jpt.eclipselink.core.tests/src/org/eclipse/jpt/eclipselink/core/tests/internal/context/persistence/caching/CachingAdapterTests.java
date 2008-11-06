/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.caching;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.persistence.GenericProperty;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.PersistenceUnitPropertyListListener;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.CacheProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Tests the update of model objects by the Caching adapter when the
 * PersistenceUnit changes.
 */
@SuppressWarnings("nls")
public class CachingAdapterTests extends PersistenceUnitTestCase
{
	private Caching caching;
	private ListChangeEvent entitiesEvent;

	public static final String ENTITY_TEST = "Employee";
	public static final String ENTITY_TEST_2 = "Address";

	public static final String CACHE_TYPE_DEFAULT_KEY = Caching.ECLIPSELINK_CACHE_TYPE_DEFAULT;
	public static final CacheType CACHE_TYPE_DEFAULT_TEST_VALUE = CacheType.soft_weak;
	public static final CacheType CACHE_TYPE_DEFAULT_TEST_VALUE_2 = CacheType.full;

	public static final String CACHE_SIZE_DEFAULT_KEY = Caching.ECLIPSELINK_CACHE_SIZE_DEFAULT;
	public static final Integer CACHE_SIZE_DEFAULT_TEST_VALUE = 12345;
	public static final Integer CACHE_SIZE_DEFAULT_TEST_VALUE_2 = 67890;

	public static final String SHARED_CACHE_DEFAULT_KEY = Caching.ECLIPSELINK_CACHE_SHARED_DEFAULT;
	public static final Boolean SHARED_CACHE_DEFAULT_TEST_VALUE = false;
	public static final Boolean SHARED_CACHE_DEFAULT_TEST_VALUE_2 = true;

	public static final String CACHE_TYPE_KEY = Caching.ECLIPSELINK_CACHE_TYPE + ENTITY_TEST;
	public static final CacheType CACHE_TYPE_TEST_VALUE = CacheType.soft_weak;
	public static final CacheType CACHE_TYPE_TEST_VALUE_2 = CacheType.full;

	public static final String SHARED_CACHE_KEY = Caching.ECLIPSELINK_SHARED_CACHE + ENTITY_TEST;
	public static final Boolean SHARED_CACHE_TEST_VALUE = false;
	public static final Boolean SHARED_CACHE_TEST_VALUE_2 = true;

	public static final String CACHE_SIZE_KEY = Caching.ECLIPSELINK_CACHE_SIZE + ENTITY_TEST;
	public static final Integer CACHE_SIZE_TEST_VALUE = 12345;
	public static final Integer CACHE_SIZE_TEST_VALUE_2 = 67890;

	public CachingAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.caching = this.persistenceUnit().getCaching();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.caching.addPropertyChangeListener(Caching.CACHE_TYPE_DEFAULT_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Caching.CACHE_SIZE_DEFAULT_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Caching.SHARED_CACHE_DEFAULT_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Caching.CACHE_TYPE_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Caching.CACHE_SIZE_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Caching.SHARED_CACHE_PROPERTY, propertyChangeListener);
		
		ListChangeListener entitiesChangeListener = this.buildEntitiesChangeListener();
		this.caching.addListChangeListener(Caching.ENTITIES_LIST_PROPERTY, entitiesChangeListener);
		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing. 
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 6;
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 4; // 4 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		this.persistenceUnitPut("misc.property.1", "value.1");
		this.persistenceUnitPut(CACHE_TYPE_DEFAULT_KEY, CACHE_TYPE_DEFAULT_TEST_VALUE);
		this.persistenceUnitPut("misc.property.2", "value.2");
		this.persistenceUnitPut(CACHE_SIZE_DEFAULT_KEY, CACHE_SIZE_DEFAULT_TEST_VALUE);
		this.persistenceUnitPut(SHARED_CACHE_DEFAULT_KEY, SHARED_CACHE_DEFAULT_TEST_VALUE);
		this.persistenceUnitPut("misc.property.3", "value.3");
		this.persistenceUnitPut("misc.property.4", "value.4");
		this.persistenceUnitPut(CACHE_SIZE_KEY, CACHE_SIZE_TEST_VALUE);
		this.persistenceUnitPut(CACHE_TYPE_KEY, CACHE_TYPE_TEST_VALUE);
		this.persistenceUnitPut(SHARED_CACHE_KEY, SHARED_CACHE_TEST_VALUE);
		return;
	}

	// ********** Listeners **********
	private ListChangeListener buildEntitiesChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsRemoved(ListChangeEvent e) {
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsReplaced(ListChangeEvent e) {
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsMoved(ListChangeEvent e) {
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void listCleared(ListChangeEvent e) {
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void listChanged(ListChangeEvent e) {
				CachingAdapterTests.this.entityChanged(e);
			}
		};
	}

	@Override
	protected void clearEvent() {
		super.clearEvent();
		this.entitiesEvent = null;
	}

	void entityChanged(ListChangeEvent e) {
		this.entitiesEvent = e;
	}

	// ********** entities list **********
	public void testEntitiesList() throws Exception {
		// add
		this.clearEvent();
		this.caching.addEntity(ENTITY_TEST_2);
		
		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getAspectName(), Caching.ENTITIES_LIST_PROPERTY);

		// remove
		this.clearEvent();
		this.caching.removeEntity(ENTITY_TEST_2);
		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getAspectName(), Caching.ENTITIES_LIST_PROPERTY);
	}

	// ********** Listeners tests **********
	public void testHasListeners() throws Exception {
		// new
		ListAspectAdapter<PersistenceUnit, Property> propertiesAdapter = 
			(ListAspectAdapter<PersistenceUnit, Property>) this.subject.getPropertiesAdapter();
		GenericProperty ctdProperty = (GenericProperty) this.persistenceUnit().getProperty(CACHE_TYPE_DEFAULT_KEY);
		ListValueModel<Property> propertyListAdapter = this.subject.getPropertyListAdapter();
		
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(ctdProperty.hasAnyPropertyChangeListeners(Property.VALUE_PROPERTY));
		this.verifyHasListeners(this.caching, Caching.CACHE_TYPE_DEFAULT_PROPERTY);
		this.verifyHasListeners(propertyListAdapter);
		
		EclipseLinkCaching elCaching = (EclipseLinkCaching) this.caching;
		PersistenceUnitPropertyListListener propertyListListener = elCaching.propertyListListener();
		propertyListAdapter.removeListChangeListener(ListValueModel.LIST_VALUES, propertyListListener);
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES)); // other properties are still listening
		this.verifyHasListeners(this.caching, Caching.CACHE_TYPE_DEFAULT_PROPERTY);
	}

	// ********** CacheTypeDefault **********
	/**
	 * Tests the update of CacheTypeDefault property by the Caching adapter when
	 * the PU or the model changes.
	 */
	public void testSetCacheTypeDefault() throws Exception {
		this.verifyModelInitialized(
			CACHE_TYPE_DEFAULT_KEY,
			CACHE_TYPE_DEFAULT_TEST_VALUE);
		this.verifySetProperty(
			CACHE_TYPE_DEFAULT_KEY,
			CACHE_TYPE_DEFAULT_TEST_VALUE,
			CACHE_TYPE_DEFAULT_TEST_VALUE_2);
	}

	public void testAddRemoveCacheTypeDefault() throws Exception {
		this.verifyAddRemoveProperty(
			CACHE_TYPE_DEFAULT_KEY,
			CACHE_TYPE_DEFAULT_TEST_VALUE,
			CACHE_TYPE_DEFAULT_TEST_VALUE_2);
	}

	// ********** CacheSizeDefault **********
	/**
	 * Tests the update of CacheSizeDefault property by the Caching adapter when
	 * the PU or the model changes.
	 */
	public void testSetCacheSizeDefault() throws Exception {
		this.verifyModelInitialized(
			CACHE_SIZE_DEFAULT_KEY,
			CACHE_SIZE_DEFAULT_TEST_VALUE);
		this.verifySetProperty(
			CACHE_SIZE_DEFAULT_KEY,
			CACHE_SIZE_DEFAULT_TEST_VALUE,
			CACHE_SIZE_DEFAULT_TEST_VALUE_2);
	}

	public void testAddRemoveCacheSizeDefault() throws Exception {
		this.verifyAddRemoveProperty(
			CACHE_SIZE_DEFAULT_KEY,
			CACHE_SIZE_DEFAULT_TEST_VALUE,
			CACHE_SIZE_DEFAULT_TEST_VALUE_2);
	}

	// ********** SharedCacheDefault **********
	public void testSetSharedCacheDefault() throws Exception {
		this.verifyModelInitialized(
			SHARED_CACHE_DEFAULT_KEY,
			SHARED_CACHE_DEFAULT_TEST_VALUE);
		this.verifySetProperty(
			SHARED_CACHE_DEFAULT_KEY,
			SHARED_CACHE_DEFAULT_TEST_VALUE,
			SHARED_CACHE_DEFAULT_TEST_VALUE_2);
	}

	public void testAddRemoveSharedCacheDefault() throws Exception {
		this.verifyAddRemoveProperty(
			SHARED_CACHE_DEFAULT_KEY,
			SHARED_CACHE_DEFAULT_TEST_VALUE,
			SHARED_CACHE_DEFAULT_TEST_VALUE_2);
	}

	// ********** CacheType **********
	/**
	 * Tests the update of CacheType property by the Caching adapter when the PU
	 * or the model changes.
	 */
	public void testSetCacheType() throws Exception {
		this.verifyModelInitialized(
			CACHE_TYPE_KEY,
			CACHE_TYPE_TEST_VALUE);
		this.verifySetCachingProperty(
			Caching.CACHE_TYPE_PROPERTY,
			CACHE_TYPE_KEY,
			CACHE_TYPE_TEST_VALUE,
			CACHE_TYPE_TEST_VALUE_2);
	}

	public void testAddRemoveCacheType() throws Exception {
		this.verifyAddRemoveCachingProperty(
			Caching.CACHE_TYPE_PROPERTY,
			CACHE_TYPE_KEY,
			CACHE_TYPE_TEST_VALUE,
			CACHE_TYPE_TEST_VALUE_2);
	}

	// ********** CacheSize **********
	/**
	 * Tests the update of CacheSize property by the Caching adapter when the PU
	 * or the model changes.
	 */
	public void testSetCacheSize() throws Exception {
		this.verifyModelInitialized(
			CACHE_SIZE_KEY,
			CACHE_SIZE_TEST_VALUE);
		this.verifySetCachingProperty(
			Caching.CACHE_SIZE_PROPERTY,
			CACHE_SIZE_KEY,
			CACHE_SIZE_TEST_VALUE,
			CACHE_SIZE_TEST_VALUE_2);
	}

	public void testAddRemoveCacheSize() throws Exception {
		this.verifyAddRemoveCachingProperty(
			Caching.CACHE_SIZE_PROPERTY,
			CACHE_SIZE_KEY,
			CACHE_SIZE_TEST_VALUE,
			CACHE_SIZE_TEST_VALUE_2);
	}

	// ********** SharedCache **********
	/**
	 * Tests the update of SharedCache property by the Caching adapter when the
	 * PU or the model changes.
	 */
	public void testSetSharedCache() throws Exception {
		this.verifyModelInitialized(
			SHARED_CACHE_KEY,
			SHARED_CACHE_TEST_VALUE);
		this.verifySetCachingProperty(
			Caching.SHARED_CACHE_PROPERTY,
			SHARED_CACHE_KEY,
			SHARED_CACHE_TEST_VALUE,
			SHARED_CACHE_TEST_VALUE_2);
	}

	public void testAddRemoveSharedCache() throws Exception {
		this.verifyAddRemoveCachingProperty(
			Caching.SHARED_CACHE_PROPERTY,
			SHARED_CACHE_KEY,
			SHARED_CACHE_TEST_VALUE,
			SHARED_CACHE_TEST_VALUE_2);
	}

	// ****** convenience methods *******
	@Override
	protected PersistenceUnitProperties model() {
		return this.caching;
	}

	protected void verifySetCachingProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		ListValueModel<Property> propertyListAdapter = this.subject.getPropertyListAdapter();
		// Basic
		this.verifyInitialState(propertyName, key, propertyListAdapter);
		
		// Replace
		this.persistenceUnitPut(key, testValue2);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, testValue2);
		
		// Replace by setting model object
		this.clearEvent();
		this.setCachingProperty(propertyName, ENTITY_TEST, testValue1);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, testValue1);
	}

	protected void verifyAddRemoveCachingProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		ListValueModel<Property> propertyListAdapter = this.subject.getPropertyListAdapter();
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.persistenceUnit().removeProperty(key);
		assertFalse(this.persistenceUnit().containsProperty(key));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, null);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		
		// Add original Property
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitPut(key, testValue1);
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, testValue1);
		assertEquals(this.propertiesTotal, propertyListAdapter.size());
		
		// Replace
		this.persistenceUnitPut(key, testValue2);
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, testValue2);
	}

	protected void verifyPutCachingProperty(String propertyName, String entityName, Object expectedValue) throws Exception {
		this.verifyEvent(propertyName);
		this.verifyCachingEvent(propertyName, entityName, expectedValue);
	}

	protected void verifyCachingEvent(String propertyName, String entityName, Object expectedValue) throws Exception {
		// verify event value
		CacheProperties cache = (CacheProperties) this.propertyChangedEvent.getNewValue();
		if (propertyName.equals(Caching.CACHE_TYPE_PROPERTY)) {
			assertEquals(expectedValue, cache.getType());
			assertEquals(expectedValue, this.caching.getCacheType(entityName));
		}
		else if (propertyName.equals(Caching.CACHE_SIZE_PROPERTY)) {
			assertEquals(expectedValue, cache.getSize());
			assertEquals(expectedValue, this.caching.getCacheSize(entityName));
		}
		else if (propertyName.equals(Caching.SHARED_CACHE_PROPERTY)) {
			assertEquals(expectedValue, cache.isShared());
			assertEquals(expectedValue, this.caching.getSharedCache(entityName));
		}
		else {
			this.throwMissingDefinition("verifyCachingEvent", propertyName);
		}
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(Caching.CACHE_TYPE_DEFAULT_PROPERTY))
			this.caching.setCacheTypeDefault((CacheType) newValue);
		else if (propertyName.equals(Caching.CACHE_SIZE_DEFAULT_PROPERTY))
			this.caching.setCacheSizeDefault((Integer) newValue);
		else if (propertyName.equals(Caching.SHARED_CACHE_DEFAULT_PROPERTY))
			this.caching.setSharedCacheDefault((Boolean) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}

	protected void setCachingProperty(String propertyName, String entityName, Object newValue) throws NoSuchFieldException {
		if (propertyName.equals(Caching.CACHE_TYPE_PROPERTY))
			this.caching.setCacheType((CacheType) newValue, entityName);
		else if (propertyName.equals(Caching.CACHE_SIZE_PROPERTY))
			this.caching.setCacheSize((Integer) newValue, entityName);
		else if (propertyName.equals(Caching.SHARED_CACHE_PROPERTY))
			this.caching.setSharedCache((Boolean) newValue, entityName);
		else
			this.throwMissingDefinition("setCachingProperty", propertyName);
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(Caching.CACHE_TYPE_DEFAULT_PROPERTY))
			modelValue = this.caching.getCacheTypeDefault();
		else if (propertyName.equals(Caching.CACHE_SIZE_DEFAULT_PROPERTY))
			modelValue = this.caching.getCacheSizeDefault();
		else if (propertyName.equals(Caching.SHARED_CACHE_DEFAULT_PROPERTY))
			modelValue = this.caching.getSharedCacheDefault();
		else if (propertyName.equals(Caching.CACHE_SIZE_PROPERTY))
			modelValue = this.caching.getCacheSize(ENTITY_TEST);
		else if (propertyName.equals(Caching.CACHE_TYPE_PROPERTY))
			modelValue = this.caching.getCacheType(ENTITY_TEST);
		else if (propertyName.equals(Caching.SHARED_CACHE_PROPERTY))
			modelValue = this.caching.getSharedCache(ENTITY_TEST);
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
}
