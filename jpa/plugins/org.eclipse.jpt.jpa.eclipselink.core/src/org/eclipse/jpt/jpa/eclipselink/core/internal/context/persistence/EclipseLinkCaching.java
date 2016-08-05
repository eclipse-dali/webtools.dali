/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCachingEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkFlushClearCache;

/**
 * EclipseLinkCaching encapsulates EclipseLink Caching properties.
 */
public class EclipseLinkCaching
	extends EclipseLinkPersistenceUnitProperties
	implements org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching
{
	// ********** EclipseLink properties **********
	private EclipseLinkCacheType cacheTypeDefault;
	private Integer cacheSizeDefault;
	private Boolean sharedCacheDefault;
	private EclipseLinkFlushClearCache flushClearCache;

	private List<EclipseLinkCachingEntity> entities;
	
	// ********** constructors **********
	public EclipseLinkCaching(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		this.entities = new ArrayList<>();
		this.cacheTypeDefault = 
			this.getEnumValue(ECLIPSELINK_CACHE_TYPE_DEFAULT, EclipseLinkCacheType.values());
		this.cacheSizeDefault = 
			this.getIntegerValue(ECLIPSELINK_CACHE_SIZE_DEFAULT);
		this.sharedCacheDefault = 
			this.getBooleanValue(ECLIPSELINK_CACHE_SHARED_DEFAULT);
		this.flushClearCache = 
			this.getEnumValue(ECLIPSELINK_FLUSH_CLEAR_CACHE, EclipseLinkFlushClearCache.values());
		
		Set<PersistenceUnit.Property> cacheTypeProperties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_CACHE_TYPE);
		Set<PersistenceUnit.Property> cacheSizeProperties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_CACHE_SIZE);
		Set<PersistenceUnit.Property> sharedCacheProperties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_SHARED_CACHE);
		
		this.initializeEntitiesCacheType(cacheTypeProperties);
		this.initializeEntitiesCacheSize(cacheSizeProperties);
		this.initializeEntitiesSharedCache(sharedCacheProperties);
	}

	private void initializeEntitiesCacheType(Set<PersistenceUnit.Property> cacheTypeProperties) {
		for (PersistenceUnit.Property cacheTypeProperty : cacheTypeProperties) {
			this.setEntityCacheTypeOf(cacheTypeProperty);
		}
	}

	private void initializeEntitiesCacheSize(Set<PersistenceUnit.Property> cacheSizeProperties) {
		for (PersistenceUnit.Property cacheSizeProperty : cacheSizeProperties) {
			this.setEntityCacheSizeOf(cacheSizeProperty);
		}
	}

	private void initializeEntitiesSharedCache(Set<PersistenceUnit.Property> sharedCacheProperties) {
		for (PersistenceUnit.Property sharedCacheProperty : sharedCacheProperties) {
			this.setEntitySharedCacheOf(sharedCacheProperty);
		}
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		if (propertyName.equals(ECLIPSELINK_CACHE_TYPE_DEFAULT)) {
			this.cacheTypeDefaultChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_SIZE_DEFAULT)) {
			this.cacheSizeDefaultChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_SHARED_DEFAULT)) {
			this.sharedCacheDefaultChanged(newValue);
		}
		else if (propertyName.startsWith(ECLIPSELINK_CACHE_TYPE)) {
			this.cacheTypeChanged(propertyName, newValue);
		}
		else if (propertyName.startsWith(ECLIPSELINK_CACHE_SIZE)) {
			this.cacheSizeChanged(propertyName, newValue);
		}
		else if (propertyName.startsWith(ECLIPSELINK_SHARED_CACHE)) {
			this.sharedCacheChanged(propertyName, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_FLUSH_CLEAR_CACHE)) {
			this.flushClearCacheChanged(newValue);
		}
	}
	
	public void propertyRemoved(String propertyName) {
		if (propertyName.equals(ECLIPSELINK_CACHE_TYPE_DEFAULT)) {
			this.cacheTypeDefaultChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_SIZE_DEFAULT)) {
			this.cacheSizeDefaultChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_SHARED_DEFAULT)) {
			this.sharedCacheDefaultChanged(null);
		}
		else if (propertyName.startsWith(ECLIPSELINK_CACHE_TYPE)) {
			this.cacheTypeChanged(propertyName, null);
		}
		else if (propertyName.startsWith(ECLIPSELINK_CACHE_SIZE)) {
			this.cacheSizeChanged(propertyName, null);
		}
		else if (propertyName.startsWith(ECLIPSELINK_SHARED_CACHE)) {
			this.sharedCacheChanged(propertyName, null);
		}
		else if (propertyName.equals(ECLIPSELINK_FLUSH_CLEAR_CACHE)) {
			this.flushClearCacheChanged(null);
		}
	}
	
	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = EclipseLink property key;
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			ECLIPSELINK_CACHE_TYPE_DEFAULT,
			CACHE_TYPE_DEFAULT_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_CACHE_SIZE_DEFAULT,
			CACHE_SIZE_DEFAULT_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_CACHE_SHARED_DEFAULT,
			SHARED_CACHE_DEFAULT_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_FLUSH_CLEAR_CACHE,
			FLUSH_CLEAR_CACHE_PROPERTY);
		
		// Don't need to initialize propertyNames for: 
		// cacheType, sharedCache, cacheSize
	}

	/**
	 * Method used for identifying the given property.
	 */
	@Override
	public boolean itemIsProperty(PersistenceUnit.Property item) {
		boolean isProperty = super.itemIsProperty(item);
		
		if ( ! isProperty && item.getName() != null) {
				if (item.getName().startsWith(ECLIPSELINK_CACHE_TYPE) || 
						item.getName().startsWith(ECLIPSELINK_CACHE_SIZE) || 
						item.getName().startsWith(ECLIPSELINK_SHARED_CACHE)) {
					return true;
				}
		}
		return isProperty;
	}

	public EclipseLinkCachingEntity addCachingEntity(String entityName) {
		if (this.cachingEntityExists(entityName)) {
			throw new IllegalStateException("Duplicate entity: " + entityName); //$NON-NLS-1$
		}
		EclipseLinkCachingEntity newEntity = this.buildEntity(entityName);
		this.addItemToList(newEntity, this.entities, CACHING_ENTITIES_LIST);
		return newEntity;
	}

	public void removeCachingEntity(String entityName) {
		if ( ! this.cachingEntityExists(entityName)) {
			return;
		}
		EclipseLinkCachingEntity entity = this.getEntityNamed(entityName);
		this.clearEntity(entity);
		this.removeEntity(entity);
	}

	// ********** CacheType **********
	public EclipseLinkCacheType getEntityCacheType(String entityName) {
		EclipseLinkCachingEntity entity = this.getEntityNamed(entityName);
		return (entity == null) ? null : entity.getCacheType();
	}

	public void setEntityCacheType(String entityName, EclipseLinkCacheType newCacheType) {
		this.setEntityCacheTypeOf(entityName, newCacheType);
		this.putEnumValue(ECLIPSELINK_CACHE_TYPE, entityName, newCacheType, false);
	}

	private void cacheTypeChanged(String propertyName, String stringValue) {
		String entityName = this.extractEntityNameOf(propertyName);
		if( ! StringTools.isBlank(entityName)) {
			this.setEntityCacheTypeOf(entityName, stringValue); 
		}
	}
	
	public EclipseLinkCacheType getDefaultEntityCacheType() {
		return (this.cacheTypeDefault == null) ? DEFAULT_ENTITY_CACHE_TYPE : this.cacheTypeDefault;
	}

	// ********** CacheSize **********
	public Integer getEntityCacheSize(String entityName) {
		EclipseLinkCachingEntity entity = this.getEntityNamed(entityName);
		return (entity == null) ? null : entity.getCacheSize();
	}

	public void setEntityCacheSize(String entityName, Integer newCacheSize) {
		this.setEntityCacheSizeOf(entityName, newCacheSize);
		this.putIntegerValue(ECLIPSELINK_CACHE_SIZE + entityName, newCacheSize);
	}

	private void cacheSizeChanged(String propertyName, String stringValue) {
		String entityName = this.extractEntityNameOf(propertyName);
		if( ! StringTools.isBlank(entityName)) {
			this.setEntityCacheSizeOf(entityName, stringValue);
		}
	}

	public Integer getDefaultEntityCacheSize() {
		return (this.cacheSizeDefault == null) ? DEFAULT_ENTITY_CACHE_SIZE : this.cacheSizeDefault;
	}

	// ********** SharedCache **********
	public Boolean getEntitySharedCache(String entityName) {
		EclipseLinkCachingEntity entity = this.getEntityNamed(entityName);
		return (entity == null) ? null : entity.cacheIsShared();
	}

	public void setEntitySharedCache(String entityName, Boolean newSharedCache) {
		this.setEntitySharedCacheOf(entityName, newSharedCache);
		this.putBooleanValue(ECLIPSELINK_SHARED_CACHE, entityName, newSharedCache, false);
	}

	private void sharedCacheChanged(String propertyName, String stringValue) {
		String entityName = this.extractEntityNameOf(propertyName);
		if( ! StringTools.isBlank(entityName)) {
			this.setEntitySharedCacheOf(entityName, stringValue);
		}
	}

	public Boolean getDefaultEntitySharedCache() {
		return (this.sharedCacheDefault == null) ? DEFAULT_ENTITY_SHARED_CACHE : this.sharedCacheDefault;
	}

	// ********** CacheTypeDefault **********
	public EclipseLinkCacheType getCacheTypeDefault() {
		return this.cacheTypeDefault;
	}

	public void setCacheTypeDefault(EclipseLinkCacheType newCacheTypeDefault) {
		EclipseLinkCacheType old = this.cacheTypeDefault;
		this.cacheTypeDefault = newCacheTypeDefault;
		this.putProperty(CACHE_TYPE_DEFAULT_PROPERTY, newCacheTypeDefault);
		this.firePropertyChanged(CACHE_TYPE_DEFAULT_PROPERTY, old, newCacheTypeDefault);
	}

	private void cacheTypeDefaultChanged(String stringValue) {
		EclipseLinkCacheType newValue = getEnumValueOf(stringValue, EclipseLinkCacheType.values());
		EclipseLinkCacheType old = this.cacheTypeDefault;
		this.cacheTypeDefault = newValue;
		this.firePropertyChanged(CACHE_TYPE_DEFAULT_PROPERTY, old, newValue);
	}

	public EclipseLinkCacheType getDefaultCacheTypeDefault() {
		return DEFAULT_CACHE_TYPE_DEFAULT;
	}

	// ********** CacheSizeDefault **********
	public Integer getCacheSizeDefault() {
		return this.cacheSizeDefault;
	}

	public void setCacheSizeDefault(Integer newCacheSizeDefault) {
		Integer old = this.cacheSizeDefault;
		this.cacheSizeDefault = newCacheSizeDefault;
		this.putProperty(CACHE_SIZE_DEFAULT_PROPERTY, newCacheSizeDefault);
		this.firePropertyChanged(CACHE_SIZE_DEFAULT_PROPERTY, old, newCacheSizeDefault);
	}

	private void cacheSizeDefaultChanged(String stringValue) {
		Integer newValue = getIntegerValueOf(stringValue);
		Integer old = this.cacheSizeDefault;
		this.cacheSizeDefault = newValue;
		this.firePropertyChanged(CACHE_SIZE_DEFAULT_PROPERTY, old, newValue);
	}

	public Integer getDefaultCacheSizeDefault() {
		return DEFAULT_CACHE_SIZE_DEFAULT;
	}

	// ********** SharedCacheDefault **********
	public Boolean getSharedCacheDefault() {
		return this.sharedCacheDefault;
	}

	public void setSharedCacheDefault(Boolean newSharedCacheDefault) {
		Boolean old = this.sharedCacheDefault;
		this.sharedCacheDefault = newSharedCacheDefault;
		this.putProperty(SHARED_CACHE_DEFAULT_PROPERTY, newSharedCacheDefault);
		this.firePropertyChanged(SHARED_CACHE_DEFAULT_PROPERTY, old, newSharedCacheDefault);
	}

	private void sharedCacheDefaultChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.sharedCacheDefault;
		this.sharedCacheDefault = newValue;
		this.firePropertyChanged(SHARED_CACHE_DEFAULT_PROPERTY, old, newValue);
	}

	public Boolean getDefaultSharedCacheDefault() {
		return DEFAULT_SHARED_CACHE_DEFAULT;
	}

	// ********** FlushClearCache **********
	
	public EclipseLinkFlushClearCache getFlushClearCache() {
		return this.flushClearCache;
	}
	
	public void setFlushClearCache(EclipseLinkFlushClearCache newFlushClearCache) {
		EclipseLinkFlushClearCache old = this.flushClearCache;
		this.flushClearCache = newFlushClearCache;
		this.putProperty(FLUSH_CLEAR_CACHE_PROPERTY, newFlushClearCache);
		this.firePropertyChanged(FLUSH_CLEAR_CACHE_PROPERTY, old, newFlushClearCache);
	}

	private void flushClearCacheChanged(String stringValue) {
		EclipseLinkFlushClearCache newValue = getEnumValueOf(stringValue, EclipseLinkFlushClearCache.values());
		EclipseLinkFlushClearCache old = this.flushClearCache;
		this.flushClearCache = newValue;
		this.firePropertyChanged(FLUSH_CLEAR_CACHE_PROPERTY, old, newValue);
	}
	
	public EclipseLinkFlushClearCache getDefaultFlushClearCache() {
		return DEFAULT_FLUSH_CLEAR_CACHE;
	}

	// ****** Entity CacheType *******
	/**
	 * Returns the old Entity
	 */
	private EclipseLinkCachingEntity setEntityCacheTypeOf(String entityName, String stringValue) {
		 if(( ! this.cachingEntityExists(entityName)) && StringTools.isBlank(stringValue)) {
				//this is a property that is currently being added, we don't need to deal with it until the value is set
				 return null;
			 }
		EclipseLinkCacheType newValue = getEnumValueOf(stringValue, EclipseLinkCacheType.values());
		return this.setEntityCacheTypeOf(entityName, newValue);
	}

	/**
	 * Returns the old Entity
	 */
	private EclipseLinkCachingEntity setEntityCacheTypeOf(String entityName, EclipseLinkCacheType cacheType) {
		EclipseLinkCachingEntity entity = (this.cachingEntityExists(entityName)) ?
						this.getEntityNamed(entityName) :
						this.addCachingEntity(entityName);
		return this.setEntityCacheTypeOf(entity, cacheType);
	}

	/**
	 * Returns the old Entity
	 */
	private EclipseLinkCachingEntity setEntityCacheTypeOf(EclipseLinkCachingEntity entity, EclipseLinkCacheType cacheType) {
		if(entity == null) {
			throw new IllegalArgumentException();
		}
		EclipseLinkCachingEntity old = entity.clone();
		entity.setCacheType(cacheType);
		return old;
	}

	private void setEntityCacheTypeOf(PersistenceUnit.Property cacheTypeProperty) {
		String entityName = this.extractEntityNameOf(cacheTypeProperty);
		this.setEntityCacheTypeOf(entityName, cacheTypeProperty.getValue());
	}

	// ****** Entity Cache size *******
	/**
	 * Returns the old Entity
	 */
	private EclipseLinkCachingEntity setEntityCacheSizeOf(String entityName, String stringValue) {
		 if(( ! this.cachingEntityExists(entityName)) && StringTools.isBlank(stringValue)) {
				//this is a property that is currently being added, we don't need to deal with it until the value is set
				 return null;
			 }
		Integer newValue = getIntegerValueOf(stringValue);
		return this.setEntityCacheSizeOf(entityName, newValue);
	}

	/**
	 * Returns the old Entity
	 */
	private EclipseLinkCachingEntity setEntityCacheSizeOf(String entityName, Integer size) {
		EclipseLinkCachingEntity entity = (this.cachingEntityExists(entityName)) ?
						this.getEntityNamed(entityName) :
						this.addCachingEntity(entityName);
		return this.setEntityCacheSizeOf(entity, size);
	}

	/**
	 * Returns the old Entity
	 */
	private EclipseLinkCachingEntity setEntityCacheSizeOf(EclipseLinkCachingEntity entity, Integer size) {
		if(entity == null) {
			throw new IllegalArgumentException();
		}
		EclipseLinkCachingEntity old = entity.clone();
		entity.setCacheSize(size);
		return old;
	}

	private void setEntityCacheSizeOf(PersistenceUnit.Property cacheSizeProperty) {
		String entityName = this.extractEntityNameOf(cacheSizeProperty);
		this.setEntityCacheSizeOf(entityName, cacheSizeProperty.getValue());
	}

	// ****** Entity SharedCache *******
	/**
	 * Returns the old Entity
	 */
	private EclipseLinkCachingEntity setEntitySharedCacheOf(String entityName, String stringValue) {
		 if(( ! this.cachingEntityExists(entityName)) && StringTools.isBlank(stringValue)) {
				//this is a property that is currently being added, we don't need to deal with it until the value is set
				 return null;
			 }
		Boolean newValue = getBooleanValueOf(stringValue);
		return this.setEntitySharedCacheOf(entityName, newValue);
	}

	/**
	 * Returns the old Entity
	 */
	private EclipseLinkCachingEntity setEntitySharedCacheOf(String entityName, Boolean sharedCache) {
		EclipseLinkCachingEntity entity = (this.cachingEntityExists(entityName)) ?
						this.getEntityNamed(entityName) :
						this.addCachingEntity(entityName);
		return this.setEntitySharedCacheOf(entity, sharedCache);
	}

	/**
	 * Returns the old Entity
	 */
	private EclipseLinkCachingEntity setEntitySharedCacheOf(EclipseLinkCachingEntity entity, Boolean sharedCache) {
		if(entity == null) {
			throw new IllegalArgumentException();
		}
		EclipseLinkCachingEntity old = entity.clone();
		entity.setSharedCache(sharedCache);
		return old;
	}

	private void setEntitySharedCacheOf(PersistenceUnit.Property sharedCacheProperty) {
		String entityName = this.extractEntityNameOf(sharedCacheProperty);
		this.setEntitySharedCacheOf(entityName, sharedCacheProperty.getValue());
	}

	// ****** convenience methods *******

	/**
	 * Set all Entity properties to default.
	 */
	private void clearEntity(EclipseLinkCachingEntity entity) {
		if(entity.isEmpty()) {
			return;
		}
		String entityName = entity.getName();
		this.setEntityCacheType(entityName, null);
		this.setEntityCacheSize(entityName, null);
		this.setEntitySharedCache(entityName, null);
	}

	/**
	 * Returns the Entity with the given name.
	 */
	private EclipseLinkCachingEntity getEntityNamed(String name) {
		for(EclipseLinkCachingEntity entity: this.entities) {
			if(entity.getName().equals(name)) {
				return entity;
			}
		}
		return null;
	}

	private EclipseLinkCachingEntity buildEntity(String name) {
		return new EclipseLinkCachingEntity(this, name);
	}

	private void removeEntity(EclipseLinkCachingEntity entity) {
		if(entity == null) {
			throw new NullPointerException();
		}
		this.removeItemFromList(entity, this.entities, CACHING_ENTITIES_LIST);
	}

	/**
	 * Return whether the Entity exist.
	 */
	public boolean cachingEntityExists(String name) {
		for(EclipseLinkCachingEntity entity: this.entities) {
			if(entity.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes cacheTypeDefault, cacheSizeDefault, flushClearCache properties.
	 */
	public void removeDefaultCachingProperties() {
		this.setCacheTypeDefault(null);
		this.setCacheSizeDefault(null);
		this.setFlushClearCache(null);
	}

	// ****** entities list *******

	public ListIterable<EclipseLinkCachingEntity> getCachingEntities() {
		return IterableTools.cloneLive(this.entities);
	}

	public Iterable<String> getCachingEntityNames() {
		return IterableTools.transform(this.getCachingEntities(), EclipseLinkCachingEntity.NAME_TRANSFORMER);
	}

	public int getCachingEntitiesSize() {
		return this.entities.size();
	}
}
