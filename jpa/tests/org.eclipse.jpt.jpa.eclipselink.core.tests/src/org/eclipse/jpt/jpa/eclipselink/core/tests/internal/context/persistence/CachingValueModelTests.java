/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCachingEntity;

/**
 * CachingValueModelTests
 */
@SuppressWarnings("nls")
public class CachingValueModelTests extends EclipseLinkPersistenceUnitTestCase
{
	private EclipseLinkCaching caching;
	private PropertyValueModel<EclipseLinkCaching> cachingHolder;
	
	private ModifiablePropertyValueModel<EclipseLinkCacheType> cacheTypeHolder;
	private PropertyChangeListener cacheTypeListener;
	private PropertyChangeEvent cacheTypeEvent;

	private ModifiablePropertyValueModel<Boolean> sharedCacheHolder;
	private PropertyChangeListener sharedCacheListener;
	private PropertyChangeEvent sharedCacheEvent;

	private ModifiablePropertyValueModel<EclipseLinkCacheType> cacheTypeDefaultHolder;
	private PropertyChangeListener cacheTypeDefaultListener;
	private PropertyChangeEvent cacheTypeDefaultEvent;

	private ModifiablePropertyValueModel<Boolean> sharedCacheDefaultHolder;
	private PropertyChangeListener sharedCacheDefaultListener;
	private PropertyChangeEvent sharedCacheDefaultEvent;

	public static final String ENTITY_NAME_TEST_VALUE = "Employee";
	public static final EclipseLinkCacheType CACHE_TYPE_TEST_VALUE = EclipseLinkCacheType.hard_weak;
	public static final Boolean SHARED_CACHE_TEST_VALUE = Boolean.FALSE;
	public static final EclipseLinkCacheType CACHE_TYPE_DEFAULT_TEST_VALUE = EclipseLinkCacheType.weak;
	public static final Boolean SHARED_CACHE_DEFAULT_TEST_VALUE = Boolean.FALSE;

	public CachingValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.caching = this.subject.getCaching(); // Subject
		this.cachingHolder = new SimplePropertyValueModel<EclipseLinkCaching>(this.caching);
		
		this.cacheTypeHolder = this.buildCacheTypeAA(this.cachingHolder);
		this.cacheTypeListener = this.buildCacheTypeChangeListener();
		this.cacheTypeHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.cacheTypeListener);
		this.cacheTypeEvent = null;
		
		this.sharedCacheHolder = this.buildSharedCacheAA(this.cachingHolder);
		this.sharedCacheListener = this.buildSharedCacheChangeListener();
		this.sharedCacheHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.sharedCacheListener);
		this.sharedCacheEvent = null;
		
		this.cacheTypeDefaultHolder = this.buildCacheTypeDefaultAA(this.cachingHolder);
		this.cacheTypeDefaultListener = this.buildCacheTypeDefaultChangeListener();
		this.cacheTypeDefaultHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.cacheTypeDefaultListener);
		this.cacheTypeDefaultEvent = null;
		
		this.sharedCacheDefaultHolder = this.buildSharedCacheDefaultAA(this.cachingHolder);
		this.sharedCacheDefaultListener = this.buildSharedCacheDefaultChangeListener();
		this.sharedCacheDefaultHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.sharedCacheDefaultListener);
		this.sharedCacheDefaultEvent = null;
	}

	public void testHasListeners() {
		AbstractModel subjectCaching = (AbstractModel) this.caching; // Subject
		PropertyAspectAdapter<EclipseLinkCaching, EclipseLinkCacheType> cacheTypeAA = 
			(PropertyAspectAdapter<EclipseLinkCaching, EclipseLinkCacheType>) this.cacheTypeHolder;
		assertTrue(cacheTypeAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectCaching.hasAnyPropertyChangeListeners(EclipseLinkCaching.CACHE_TYPE_PROPERTY));
		
		cacheTypeAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.cacheTypeListener);
		assertFalse(subjectCaching.hasAnyPropertyChangeListeners(EclipseLinkCaching.CACHE_TYPE_PROPERTY));
		assertFalse(cacheTypeAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		
		PropertyAspectAdapter<EclipseLinkCaching, Boolean> sharedCacheAA = 
			(PropertyAspectAdapter<EclipseLinkCaching, Boolean>) this.sharedCacheHolder;
		assertTrue(sharedCacheAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectCaching.hasAnyPropertyChangeListeners(EclipseLinkCaching.SHARED_CACHE_PROPERTY));
		
		sharedCacheAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.sharedCacheListener);
		assertFalse(subjectCaching.hasAnyPropertyChangeListeners(EclipseLinkCaching.SHARED_CACHE_PROPERTY));
		assertFalse(sharedCacheAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		
		PropertyAspectAdapter<EclipseLinkCaching, EclipseLinkCacheType> cacheTypeDefaultAA = 
			(PropertyAspectAdapter<EclipseLinkCaching, EclipseLinkCacheType>) this.cacheTypeDefaultHolder;
		assertTrue(cacheTypeDefaultAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectCaching.hasAnyPropertyChangeListeners(EclipseLinkCaching.CACHE_TYPE_DEFAULT_PROPERTY));
		
		cacheTypeDefaultAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.cacheTypeDefaultListener);
		assertFalse(subjectCaching.hasAnyPropertyChangeListeners(EclipseLinkCaching.CACHE_TYPE_DEFAULT_PROPERTY));
		assertFalse(cacheTypeDefaultAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		
		PropertyAspectAdapter<EclipseLinkCaching, Boolean> sharedCacheDefaultAA = 
			(PropertyAspectAdapter<EclipseLinkCaching, Boolean>) this.sharedCacheDefaultHolder;
		assertTrue(sharedCacheDefaultAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectCaching.hasAnyPropertyChangeListeners(EclipseLinkCaching.SHARED_CACHE_DEFAULT_PROPERTY));
		
		sharedCacheDefaultAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.sharedCacheDefaultListener);
		assertFalse(subjectCaching.hasAnyPropertyChangeListeners(EclipseLinkCaching.SHARED_CACHE_DEFAULT_PROPERTY));
		assertFalse(sharedCacheDefaultAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	/**
	 * Initializes directly the PU properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.persistenceUnitSetProperty(
			EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE + ENTITY_NAME_TEST_VALUE, 
			CACHE_TYPE_TEST_VALUE);
		this.persistenceUnitSetProperty(
			EclipseLinkCaching.ECLIPSELINK_SHARED_CACHE + ENTITY_NAME_TEST_VALUE, 
			SHARED_CACHE_TEST_VALUE);
		this.persistenceUnitSetProperty(
			EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE_DEFAULT, 
			CACHE_TYPE_DEFAULT_TEST_VALUE);
		this.persistenceUnitSetProperty(
			EclipseLinkCaching.ECLIPSELINK_CACHE_SHARED_DEFAULT, 
			SHARED_CACHE_DEFAULT_TEST_VALUE);
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.caching;
	}

	/** ****** CacheType ******* */
	private ModifiablePropertyValueModel<EclipseLinkCacheType> buildCacheTypeAA(PropertyValueModel<EclipseLinkCaching> subjectHolder) {
		return new PropertyAspectAdapter<EclipseLinkCaching, EclipseLinkCacheType>(subjectHolder, EclipseLinkCaching.CACHE_TYPE_PROPERTY) {
			@Override
			protected EclipseLinkCacheType buildValue_() {
				return this.subject.getCacheTypeOf(ENTITY_NAME_TEST_VALUE);
			}

			@Override
			protected void setValue_(EclipseLinkCacheType enumValue) {
				this.subject.setCacheTypeOf(ENTITY_NAME_TEST_VALUE, enumValue);
			}
		};
	}

	private PropertyChangeListener buildCacheTypeChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				CachingValueModelTests.this.cacheTypeEvent = e;
			}
		};
	}

	/** ****** SharedCache ******* */
	private ModifiablePropertyValueModel<Boolean> buildSharedCacheAA(PropertyValueModel<EclipseLinkCaching> subjectHolder) {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(subjectHolder, EclipseLinkCaching.SHARED_CACHE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSharedCacheOf(ENTITY_NAME_TEST_VALUE);
			}

			@Override
			protected void setValue_(Boolean enumValue) {
				this.subject.setSharedCacheOf(ENTITY_NAME_TEST_VALUE, enumValue);
			}
		};
	}

	private PropertyChangeListener buildSharedCacheChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				CachingValueModelTests.this.sharedCacheEvent = e;
			}
		};
	}

	/** ****** CacheTypeDefault ******* */
	private ModifiablePropertyValueModel<EclipseLinkCacheType> buildCacheTypeDefaultAA(PropertyValueModel<EclipseLinkCaching> subjectHolder) {
		return new PropertyAspectAdapter<EclipseLinkCaching, EclipseLinkCacheType>(subjectHolder, EclipseLinkCaching.CACHE_TYPE_DEFAULT_PROPERTY) {
			@Override
			protected EclipseLinkCacheType buildValue_() {
				return this.subject.getCacheTypeDefault();
			}

			@Override
			protected void setValue_(EclipseLinkCacheType enumValue) {
				this.subject.setCacheTypeDefault(enumValue);
			}
		};
	}

	private PropertyChangeListener buildCacheTypeDefaultChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				CachingValueModelTests.this.cacheTypeDefaultEvent = e;
			}
		};
	}

	/** ****** SharedCacheDefault ******* */
	private ModifiablePropertyValueModel<Boolean> buildSharedCacheDefaultAA(PropertyValueModel<EclipseLinkCaching> subjectHolder) {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(subjectHolder, EclipseLinkCaching.SHARED_CACHE_DEFAULT_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSharedCacheDefault();
			}

			@Override
			protected void setValue_(Boolean enumValue) {
				this.subject.setSharedCacheDefault(enumValue);
			}
		};
	}

	private PropertyChangeListener buildSharedCacheDefaultChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				CachingValueModelTests.this.sharedCacheDefaultEvent = e;
			}
		};
	}

	/** ****** Basic Entity's Properties Tests ******* */

	public void testClone() {
		EclipseLinkCachingEntity entity = this.buildEntity("TestEntity", EclipseLinkCacheType.full, 100, true);

		this.verifyClone(entity, entity.clone());
	}
	
	public void testEquals() {
		EclipseLinkCachingEntity entity1 = this.buildEntity("TestEntityA", EclipseLinkCacheType.full, 100, true);
		EclipseLinkCachingEntity entity2 = this.buildEntity("TestEntityB", EclipseLinkCacheType.full, 100, true);
		assertEquals(entity1, entity2);
		EclipseLinkCachingEntity entity3 = this.buildEntity("TestEntityC", EclipseLinkCacheType.full, 100, true);
		assertEquals(entity1, entity3);
		assertEquals(entity2, entity3);
	}
	
	public void testIsEmpty() {
		EclipseLinkCachingEntity entity = this.buildEntity("TestEntity");
		assertTrue(entity.isEmpty());
		this.caching.setCacheSizeOf(entity.getName(), 100);
		assertFalse(entity.isEmpty());
	}

	private void verifyClone(EclipseLinkCachingEntity original, EclipseLinkCachingEntity clone) {
		assertNotSame(original, clone);
		assertEquals(original, original);
		assertEquals(original, clone);
	}

	private EclipseLinkCachingEntity buildEntity(String name) {
		return this.caching.addEntity(name);
	}

	private EclipseLinkCachingEntity buildEntity(String name, EclipseLinkCacheType cacheType, Integer size, Boolean isShared) {
		EclipseLinkCachingEntity entity = this.caching.addEntity(name);
		this.caching.setCacheTypeOf(entity.getName(), cacheType);
		this.caching.setCacheSizeOf(entity.getName(), size);
		this.caching.setSharedCacheOf(entity.getName(), isShared);
		return entity;
	}
	
	/** ****** Caching Tests ******* */
	public void testValue() {
		/** ****** CacheType - defaults for entity level caching are equal to the persistence unit settings ******* */
		this.verifyCacheTypeAAValue(CACHE_TYPE_TEST_VALUE);
		assertEquals(this.caching.getCacheTypeDefault(), this.caching.getDefaultCacheType());
		/** ****** SharedCache - defaults for entity level caching are equal to the persistence unit settings ******* */
		this.verifySharedCacheAAValue(SHARED_CACHE_TEST_VALUE);
		assertEquals(this.caching.getSharedCacheDefault(), this.caching.getDefaultSharedCache());
		/** ****** CacheTypeDefault ******* */
		this.verifyCacheTypeDefaultAAValue(CACHE_TYPE_DEFAULT_TEST_VALUE);
		assertEquals(EclipseLinkCaching.DEFAULT_CACHE_TYPE_DEFAULT, this.caching.getDefaultCacheTypeDefault());
		/** ****** SharedCacheDefault ******* */
		this.verifySharedCacheDefaultAAValue(SHARED_CACHE_DEFAULT_TEST_VALUE);
		assertEquals(EclipseLinkCaching.DEFAULT_SHARED_CACHE_DEFAULT, this.caching.getDefaultSharedCacheDefault());
	}

	public void testSetValue() throws Exception {
		/** ****** CacheType ******* */
		this.cacheTypeEvent = null;
		this.verifyHasListeners(this.cacheTypeHolder, PropertyValueModel.VALUE);
		EclipseLinkCacheType newCacheType = EclipseLinkCacheType.full;
		// Modify the property holder
		this.cacheTypeHolder.setValue(newCacheType);
		this.verifyCacheTypeAAValue(newCacheType);
		assertNotNull(this.cacheTypeEvent);
		/** ****** SharedCache ******* */
		this.sharedCacheEvent = null;
		this.verifyHasListeners(this.sharedCacheHolder, PropertyValueModel.VALUE);
		Boolean newSharedCache = !SHARED_CACHE_TEST_VALUE;
		// Modify the property holder
		this.sharedCacheHolder.setValue(newSharedCache);
		this.verifySharedCacheAAValue(newSharedCache);
		assertNotNull(this.sharedCacheEvent);
		/** ****** CacheTypeDefault ******* */
		this.cacheTypeDefaultEvent = null;
		this.verifyHasListeners(this.cacheTypeDefaultHolder, PropertyValueModel.VALUE);
		EclipseLinkCacheType newCacheTypeDefault = EclipseLinkCacheType.none;
		// Modify the property holder
		this.cacheTypeDefaultHolder.setValue(newCacheTypeDefault);
		this.verifyCacheTypeDefaultAAValue(newCacheTypeDefault);
		assertNotNull(this.cacheTypeDefaultEvent);
		/** ****** SharedCacheDefault ******* */
		this.sharedCacheDefaultEvent = null;
		this.verifyHasListeners(this.sharedCacheDefaultHolder, PropertyValueModel.VALUE);
		Boolean newSharedCacheDefault = !SHARED_CACHE_DEFAULT_TEST_VALUE;
		// Modify the property holder
		this.sharedCacheDefaultHolder.setValue(newSharedCacheDefault);
		this.verifySharedCacheDefaultAAValue(newSharedCacheDefault);
		assertNotNull(this.sharedCacheDefaultEvent);
	}

	public void testSetNullValue() {
		String notDeleted = "Property not deleted";
		/** ****** CacheType ******* */
		this.cacheTypeEvent = null;
		// Setting the property holder
		this.cacheTypeHolder.setValue(null);
		// testing Holder
		this.verifyCacheTypeAAValue(null);
		assertNotNull(this.cacheTypeEvent);
		// testing PU properties
		this.verifyPuHasNotProperty(EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE + ENTITY_NAME_TEST_VALUE, notDeleted);
		/** ****** SharedCache ******* */
		this.sharedCacheEvent = null;
		// Setting the property holder
		this.sharedCacheHolder.setValue(null);
		// testing Holder
		this.verifySharedCacheAAValue(null);
		assertNotNull(this.sharedCacheEvent);
		// testing PU properties
		this.verifyPuHasNotProperty(EclipseLinkCaching.ECLIPSELINK_SHARED_CACHE + ENTITY_NAME_TEST_VALUE, notDeleted);
		/** ****** CacheTypeDefault ******* */
		this.cacheTypeDefaultEvent = null;
		// Setting the property holder
		this.cacheTypeDefaultHolder.setValue(null);
		// testing Holder
		this.verifyCacheTypeDefaultAAValue(null);
		assertNotNull(this.cacheTypeDefaultEvent);
		// testing PU properties
		this.verifyPuHasNotProperty(EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE_DEFAULT, notDeleted);
		/** ****** SharedCacheDefault ******* */
		this.sharedCacheDefaultEvent = null;
		// Setting the property holder
		this.sharedCacheDefaultHolder.setValue(null);
		// testing Holder
		this.verifySharedCacheDefaultAAValue(null);
		assertNotNull(this.sharedCacheDefaultEvent);
		// testing PU properties
		this.verifyPuHasNotProperty(EclipseLinkCaching.ECLIPSELINK_CACHE_SHARED_DEFAULT, notDeleted);
	}

	/** ****** convenience methods ******* */
	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyCacheTypeAAValue(EclipseLinkCacheType testValue) {
		this.verifyAAValue(
			testValue, 
			this.caching.getCacheTypeOf(ENTITY_NAME_TEST_VALUE), 
			this.cacheTypeHolder, 
			EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE + ENTITY_NAME_TEST_VALUE);
	}

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifySharedCacheAAValue(Boolean testValue) {
		this.verifyAAValue(
			testValue, 
			this.caching.getSharedCacheOf(ENTITY_NAME_TEST_VALUE), 
			this.sharedCacheHolder, 
			EclipseLinkCaching.ECLIPSELINK_SHARED_CACHE + ENTITY_NAME_TEST_VALUE);
	}

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyCacheTypeDefaultAAValue(EclipseLinkCacheType testValue) {
		this.verifyAAValue(
			testValue, 
			this.caching.getCacheTypeDefault(), 
			this.cacheTypeDefaultHolder, 
			EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE_DEFAULT);
	}

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifySharedCacheDefaultAAValue(Boolean testValue) {
		this.verifyAAValue(
			testValue, 
			this.caching.getSharedCacheDefault(), 
			this.sharedCacheDefaultHolder, 
			EclipseLinkCaching.ECLIPSELINK_CACHE_SHARED_DEFAULT);
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
