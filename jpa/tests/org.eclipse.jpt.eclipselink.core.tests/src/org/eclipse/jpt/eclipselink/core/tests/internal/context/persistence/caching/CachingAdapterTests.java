/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.caching;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.eclipselink.core.context.persistence.caching.FlushClearCache;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Entity;
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
 * Tests the update of model objects by the Caching adapter when the
 * PersistenceUnit changes.
 */
@SuppressWarnings("nls")
public class CachingAdapterTests extends EclipseLinkPersistenceUnitTestCase
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
	
	public static final String FLUSH_CLEAR_CACHE_KEY = Caching.ECLIPSELINK_FLUSH_CLEAR_CACHE;
	public static final FlushClearCache FLUSH_CLEAR_CACHE_TEST_VALUE = FlushClearCache.drop;
	public static final FlushClearCache FLUSH_CLEAR_CACHE_TEST_VALUE_2 = FlushClearCache.merge;

	public CachingAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.caching = this.getPersistenceUnit().getCaching();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.caching.addPropertyChangeListener(Caching.CACHE_TYPE_DEFAULT_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Caching.CACHE_SIZE_DEFAULT_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Caching.SHARED_CACHE_DEFAULT_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Entity.CACHE_TYPE_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Entity.CACHE_SIZE_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Entity.SHARED_CACHE_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(Caching.FLUSH_CLEAR_CACHE_PROPERTY, propertyChangeListener);
		
		ListChangeListener entitiesChangeListener = this.buildEntitiesChangeListener();
		this.caching.addListChangeListener(Caching.ENTITIES_LIST, entitiesChangeListener);
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
		
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(CACHE_TYPE_DEFAULT_KEY, CACHE_TYPE_DEFAULT_TEST_VALUE);
		this.persistenceUnitSetProperty("misc.property.2", "value.2");
		this.persistenceUnitSetProperty(CACHE_SIZE_DEFAULT_KEY, CACHE_SIZE_DEFAULT_TEST_VALUE);
		this.persistenceUnitSetProperty(SHARED_CACHE_DEFAULT_KEY, SHARED_CACHE_DEFAULT_TEST_VALUE);
		this.persistenceUnitSetProperty("misc.property.3", "value.3");
		this.persistenceUnitSetProperty("misc.property.4", "value.4");
		this.persistenceUnitSetProperty(CACHE_SIZE_KEY, CACHE_SIZE_TEST_VALUE);
		this.persistenceUnitSetProperty(CACHE_TYPE_KEY, CACHE_TYPE_TEST_VALUE);
		this.persistenceUnitSetProperty(SHARED_CACHE_KEY, SHARED_CACHE_TEST_VALUE);
		this.persistenceUnitSetProperty(FLUSH_CLEAR_CACHE_KEY, FLUSH_CLEAR_CACHE_TEST_VALUE);
		return;
	}

	// ********** Listeners **********
	private ListChangeListener buildEntitiesChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent e) {
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsRemoved(ListRemoveEvent e) {
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsReplaced(ListReplaceEvent e) {
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsMoved(ListMoveEvent e) {
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}

			public void listCleared(ListClearEvent e) {
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
		int originalNumberOfEntities = this.caching.entitiesSize();
		
		this.caching.addEntity(ENTITY_TEST_2);
		assertEquals("Entity not added", this.caching.entitiesSize(), originalNumberOfEntities + 1);
		
		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getListName(), Caching.ENTITIES_LIST);

		// remove
		this.clearEvent();
		this.caching.removeEntity(ENTITY_TEST_2);
		assertEquals("Entity not removed", this.caching.entitiesSize(), originalNumberOfEntities);

		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getListName(), Caching.ENTITIES_LIST);
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
			Entity.CACHE_TYPE_PROPERTY,
			CACHE_TYPE_KEY,
			CACHE_TYPE_TEST_VALUE,
			CACHE_TYPE_TEST_VALUE_2);
	}

	public void testAddRemoveCacheType() throws Exception {
		this.verifyAddRemoveCachingProperty(
			Entity.CACHE_TYPE_PROPERTY,
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
			Entity.CACHE_SIZE_PROPERTY,
			CACHE_SIZE_KEY,
			CACHE_SIZE_TEST_VALUE,
			CACHE_SIZE_TEST_VALUE_2);
	}

	public void testAddRemoveCacheSize() throws Exception {
		this.verifyAddRemoveCachingProperty(
			Entity.CACHE_SIZE_PROPERTY,
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
			Entity.SHARED_CACHE_PROPERTY,
			SHARED_CACHE_KEY,
			SHARED_CACHE_TEST_VALUE,
			SHARED_CACHE_TEST_VALUE_2);
	}

	public void testAddRemoveSharedCache() throws Exception {
		this.verifyAddRemoveCachingProperty(
			Entity.SHARED_CACHE_PROPERTY,
			SHARED_CACHE_KEY,
			SHARED_CACHE_TEST_VALUE,
			SHARED_CACHE_TEST_VALUE_2);
	}

	// ********** FlushClearCache tests **********
	public void testSetFlushClearCache() throws Exception {
		this.verifyModelInitialized(
			FLUSH_CLEAR_CACHE_KEY,
			FLUSH_CLEAR_CACHE_TEST_VALUE);
		this.verifySetProperty(
			FLUSH_CLEAR_CACHE_KEY,
			FLUSH_CLEAR_CACHE_TEST_VALUE,
			FLUSH_CLEAR_CACHE_TEST_VALUE_2);
	}

	public void testAddRemoveFlushClearCache() throws Exception {
		this.verifyAddRemoveProperty(
			FLUSH_CLEAR_CACHE_KEY,
			FLUSH_CLEAR_CACHE_TEST_VALUE,
			FLUSH_CLEAR_CACHE_TEST_VALUE_2);
	}

	// ****** convenience methods *******
	@Override
	protected PersistenceUnitProperties getModel() {
		return this.caching;
	}

	protected void verifySetCachingProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		// Replace
		this.persistenceUnitSetProperty(key, testValue2);
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, testValue2);
		
		// Replace by setting model object
		this.clearEvent();
		this.setCachingProperty(propertyName, ENTITY_TEST, testValue1);
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, testValue1);
	}

	protected void verifyAddRemoveCachingProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.getPersistenceUnit().removeProperty(key);
		assertNull(this.getPersistenceUnit().getProperty(key));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, null);
		
		// Add original Property
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitSetProperty(key, testValue1);
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, testValue1);
		
		// Set to null
		this.persistenceUnitSetProperty(key, null);
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, null);
		
		// Replace
		this.persistenceUnitSetProperty(key, testValue2);
		this.verifyPutCachingProperty(propertyName, ENTITY_TEST, testValue2);
	}

	protected void verifyPutCachingProperty(String propertyName, String entityName, Object expectedValue) throws Exception {
		this.verifyEvent(propertyName);
		this.verifyCachingEvent(propertyName, entityName, expectedValue);
	}

	protected void verifyCachingEvent(String propertyName, String entityName, Object expectedValue) throws Exception {
		// verify event value
		Entity entity = (Entity) this.propertyChangedEvent.getNewValue();
		if (propertyName.equals(Entity.CACHE_TYPE_PROPERTY)) {
			assertEquals(expectedValue, entity.getParent().getCacheTypeOf(entityName));
			assertEquals(expectedValue, this.caching.getCacheTypeOf(entityName));
		}
		else if (propertyName.equals(Entity.CACHE_SIZE_PROPERTY)) {
			assertEquals(expectedValue, entity.getParent().getCacheSizeOf(entityName));
			assertEquals(expectedValue, this.caching.getCacheSizeOf(entityName));
		}
		else if (propertyName.equals(Entity.SHARED_CACHE_PROPERTY)) {
			assertEquals(expectedValue, entity.getParent().getSharedCacheOf(entityName));
			assertEquals(expectedValue, this.caching.getSharedCacheOf(entityName));
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
		else if (propertyName.equals(Caching.FLUSH_CLEAR_CACHE_PROPERTY))
			this.caching.setFlushClearCache((FlushClearCache) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}

	protected void setCachingProperty(String propertyName, String entityName, Object newValue) throws NoSuchFieldException {
		if (propertyName.equals(Entity.CACHE_TYPE_PROPERTY))
			this.caching.setCacheTypeOf(entityName, (CacheType) newValue);
		else if (propertyName.equals(Entity.CACHE_SIZE_PROPERTY))
			this.caching.setCacheSizeOf(entityName, (Integer) newValue);
		else if (propertyName.equals(Entity.SHARED_CACHE_PROPERTY))
			this.caching.setSharedCacheOf(entityName, (Boolean) newValue);
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
		else if (propertyName.equals(Entity.CACHE_SIZE_PROPERTY))
			modelValue = this.caching.getCacheSizeOf(ENTITY_TEST);
		else if (propertyName.equals(Entity.CACHE_TYPE_PROPERTY))
			modelValue = this.caching.getCacheTypeOf(ENTITY_TEST);
		else if (propertyName.equals(Entity.SHARED_CACHE_PROPERTY))
			modelValue = this.caching.getSharedCacheOf(ENTITY_TEST);
		else if (propertyName.equals(Caching.FLUSH_CLEAR_CACHE_PROPERTY))
			modelValue = this.caching.getFlushClearCache();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
}
