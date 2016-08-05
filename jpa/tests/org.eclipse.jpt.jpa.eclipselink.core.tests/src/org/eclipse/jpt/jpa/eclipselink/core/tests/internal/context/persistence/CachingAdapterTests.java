/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCachingEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkFlushClearCache;

/**
 * Tests the update of model objects by the Caching adapter when the
 * PersistenceUnit changes.
 */
@SuppressWarnings("nls")
public class CachingAdapterTests extends EclipseLinkPersistenceUnitTestCase
{
	private EclipseLinkCaching caching;
	private ListEvent entitiesEvent;

	public static final String ENTITY_TEST = "Employee";
	public static final String ENTITY_TEST_2 = "Address";

	public static final String CACHE_TYPE_DEFAULT_KEY = EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE_DEFAULT;
	public static final EclipseLinkCacheType CACHE_TYPE_DEFAULT_TEST_VALUE = EclipseLinkCacheType.soft_weak;
	public static final EclipseLinkCacheType CACHE_TYPE_DEFAULT_TEST_VALUE_2 = EclipseLinkCacheType.full;

	public static final String CACHE_SIZE_DEFAULT_KEY = EclipseLinkCaching.ECLIPSELINK_CACHE_SIZE_DEFAULT;
	public static final Integer CACHE_SIZE_DEFAULT_TEST_VALUE = 12345;
	public static final Integer CACHE_SIZE_DEFAULT_TEST_VALUE_2 = 67890;

	public static final String SHARED_CACHE_DEFAULT_KEY = EclipseLinkCaching.ECLIPSELINK_CACHE_SHARED_DEFAULT;
	public static final Boolean SHARED_CACHE_DEFAULT_TEST_VALUE = false;
	public static final Boolean SHARED_CACHE_DEFAULT_TEST_VALUE_2 = true;

	public static final String CACHE_TYPE_KEY = EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE + ENTITY_TEST;
	public static final EclipseLinkCacheType CACHE_TYPE_TEST_VALUE = EclipseLinkCacheType.soft_weak;
	public static final EclipseLinkCacheType CACHE_TYPE_TEST_VALUE_2 = EclipseLinkCacheType.full;

	public static final String SHARED_CACHE_KEY = EclipseLinkCaching.ECLIPSELINK_SHARED_CACHE + ENTITY_TEST;
	public static final Boolean SHARED_CACHE_TEST_VALUE = false;
	public static final Boolean SHARED_CACHE_TEST_VALUE_2 = true;

	public static final String CACHE_SIZE_KEY = EclipseLinkCaching.ECLIPSELINK_CACHE_SIZE + ENTITY_TEST;
	public static final Integer CACHE_SIZE_TEST_VALUE = 12345;
	public static final Integer CACHE_SIZE_TEST_VALUE_2 = 67890;
	
	public static final String FLUSH_CLEAR_CACHE_KEY = EclipseLinkCaching.ECLIPSELINK_FLUSH_CLEAR_CACHE;
	public static final EclipseLinkFlushClearCache FLUSH_CLEAR_CACHE_TEST_VALUE = EclipseLinkFlushClearCache.drop;
	public static final EclipseLinkFlushClearCache FLUSH_CLEAR_CACHE_TEST_VALUE_2 = EclipseLinkFlushClearCache.merge;

	public CachingAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.caching = this.getPersistenceUnit().getCaching();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.caching.addPropertyChangeListener(EclipseLinkCaching.CACHE_TYPE_DEFAULT_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(EclipseLinkCaching.CACHE_SIZE_DEFAULT_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(EclipseLinkCaching.SHARED_CACHE_DEFAULT_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(EclipseLinkCachingEntity.CACHE_TYPE_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(EclipseLinkCachingEntity.CACHE_SIZE_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(EclipseLinkCachingEntity.SHARED_CACHE_PROPERTY, propertyChangeListener);
		this.caching.addPropertyChangeListener(EclipseLinkCaching.FLUSH_CLEAR_CACHE_PROPERTY, propertyChangeListener);
		
		ListChangeListener entitiesChangeListener = this.buildEntitiesChangeListener();
		this.caching.addListChangeListener(EclipseLinkCaching.CACHING_ENTITIES_LIST, entitiesChangeListener);
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
				CachingAdapterTests.this.entityAdded(e);
			}

			public void itemsRemoved(ListRemoveEvent e) {
				CachingAdapterTests.this.entityRemoved(e);
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
				CachingAdapterTests.this.throwUnsupportedOperationException(e);
			}
		};
	}

	@Override
	protected void clearEvent() {
		super.clearEvent();
		this.entitiesEvent = null;
	}

	void entityAdded(ListAddEvent e) {
		this.entitiesEvent = e;
	}

	void entityRemoved(ListRemoveEvent e) {
		this.entitiesEvent = e;
	}

	// ********** entities list **********
	public void testEntitiesList() throws Exception {
		// add
		this.clearEvent();
		int originalNumberOfEntities = this.caching.getCachingEntitiesSize();
		
		this.caching.addCachingEntity(ENTITY_TEST_2);
		assertEquals("Entity not added", this.caching.getCachingEntitiesSize(), originalNumberOfEntities + 1);
		
		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getListName(), EclipseLinkCaching.CACHING_ENTITIES_LIST);

		// remove
		this.clearEvent();
		this.caching.removeCachingEntity(ENTITY_TEST_2);
		assertEquals("Entity not removed", this.caching.getCachingEntitiesSize(), originalNumberOfEntities);

		// verify event received
		assertNotNull("No Event Fired.", this.entitiesEvent);
		// verify event for the expected property
		assertEquals("Wrong Event.", this.entitiesEvent.getListName(), EclipseLinkCaching.CACHING_ENTITIES_LIST);
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
	public void testAddRemoveCacheType() throws Exception {
		this.verifyAddRemoveCachingProperty(
			EclipseLinkCachingEntity.CACHE_TYPE_PROPERTY,
			CACHE_TYPE_KEY,
			CACHE_TYPE_TEST_VALUE,
			CACHE_TYPE_TEST_VALUE_2);
	}

	// ********** CacheSize **********
	/**
	 * Tests the update of CacheSize property by the Caching adapter when the PU
	 * or the model changes.
	 */
	public void testAddRemoveCacheSize() throws Exception {
		this.verifyAddRemoveCachingProperty(
			EclipseLinkCachingEntity.CACHE_SIZE_PROPERTY,
			CACHE_SIZE_KEY,
			CACHE_SIZE_TEST_VALUE,
			CACHE_SIZE_TEST_VALUE_2);
	}

	// ********** SharedCache **********
	/**
	 * Tests the update of SharedCache property by the Caching adapter when the
	 * PU or the model changes.
	 */
	public void testAddRemoveSharedCache() throws Exception {
		this.verifyAddRemoveCachingProperty(
			EclipseLinkCachingEntity.SHARED_CACHE_PROPERTY,
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

	protected void verifyAddRemoveCachingProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.getPersistenceUnit().removeProperty(key);
		assertNull(this.getPersistenceUnit().getProperty(key));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		
		// Add original Property
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitSetProperty(key, testValue1);
		
		// Set to null
		this.persistenceUnitSetProperty(key, null);
		
		// Replace
		this.persistenceUnitSetProperty(key, testValue2);
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(EclipseLinkCaching.CACHE_TYPE_DEFAULT_PROPERTY))
			this.caching.setCacheTypeDefault((EclipseLinkCacheType) newValue);
		else if (propertyName.equals(EclipseLinkCaching.CACHE_SIZE_DEFAULT_PROPERTY))
			this.caching.setCacheSizeDefault((Integer) newValue);
		else if (propertyName.equals(EclipseLinkCaching.SHARED_CACHE_DEFAULT_PROPERTY))
			this.caching.setSharedCacheDefault((Boolean) newValue);
		else if (propertyName.equals(EclipseLinkCaching.FLUSH_CLEAR_CACHE_PROPERTY))
			this.caching.setFlushClearCache((EclipseLinkFlushClearCache) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}

	protected void setCachingProperty(String propertyName, String entityName, Object newValue) throws NoSuchFieldException {
		if (propertyName.equals(EclipseLinkCachingEntity.CACHE_TYPE_PROPERTY))
			this.caching.setEntityCacheType(entityName, (EclipseLinkCacheType) newValue);
		else if (propertyName.equals(EclipseLinkCachingEntity.CACHE_SIZE_PROPERTY))
			this.caching.setEntityCacheSize(entityName, (Integer) newValue);
		else if (propertyName.equals(EclipseLinkCachingEntity.SHARED_CACHE_PROPERTY))
			this.caching.setEntitySharedCache(entityName, (Boolean) newValue);
		else
			this.throwMissingDefinition("setCachingProperty", propertyName);
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(EclipseLinkCaching.CACHE_TYPE_DEFAULT_PROPERTY))
			modelValue = this.caching.getCacheTypeDefault();
		else if (propertyName.equals(EclipseLinkCaching.CACHE_SIZE_DEFAULT_PROPERTY))
			modelValue = this.caching.getCacheSizeDefault();
		else if (propertyName.equals(EclipseLinkCaching.SHARED_CACHE_DEFAULT_PROPERTY))
			modelValue = this.caching.getSharedCacheDefault();
		else if (propertyName.equals(EclipseLinkCachingEntity.CACHE_SIZE_PROPERTY))
			modelValue = this.caching.getEntityCacheSize(ENTITY_TEST);
		else if (propertyName.equals(EclipseLinkCachingEntity.CACHE_TYPE_PROPERTY))
			modelValue = this.caching.getEntityCacheType(ENTITY_TEST);
		else if (propertyName.equals(EclipseLinkCachingEntity.SHARED_CACHE_PROPERTY))
			modelValue = this.caching.getEntitySharedCache(ENTITY_TEST);
		else if (propertyName.equals(EclipseLinkCaching.FLUSH_CLEAR_CACHE_PROPERTY))
			modelValue = this.caching.getFlushClearCache();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
}
