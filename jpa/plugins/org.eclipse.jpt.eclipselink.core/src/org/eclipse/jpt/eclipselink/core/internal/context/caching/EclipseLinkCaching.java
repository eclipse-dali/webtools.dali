/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.caching;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * EclipseLinkCaching encapsulates EclipseLink Caching properties.
 */
public class EclipseLinkCaching extends EclipseLinkPersistenceUnitProperties
	implements Caching
{
	// ********** EclipseLink properties **********
	private CacheType cacheTypeDefault;
	private Integer cacheSizeDefault;
	private Boolean sharedCacheDefault;

	// key = Entity name ; value = Cache properties
	private Map<String, CacheProperties> entitiesCacheProperties;

	// ********** constructors **********
	public EclipseLinkCaching(PersistenceUnit parent, ListValueModel<Property> propertyListAdapter) {
		super(parent, propertyListAdapter);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		// TOREVIEW - handle incorrect String in persistence.xml
		this.entitiesCacheProperties = 
			new HashMap<String, CacheProperties>();
		this.cacheTypeDefault = 
			this.getEnumValue(ECLIPSELINK_CACHE_TYPE_DEFAULT, CacheType.values());
		this.cacheSizeDefault = 
			this.getIntegerValue(ECLIPSELINK_CACHE_SIZE_DEFAULT);
		this.sharedCacheDefault = 
			this.getBooleanValue(ECLIPSELINK_CACHE_SHARED_DEFAULT);
		
		Set<Property> cacheTypeProperties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_CACHE_TYPE);
		Set<Property> cacheSizeProperties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_CACHE_SIZE);
		Set<Property> sharedCacheProperties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_SHARED_CACHE);
		
		this.initializeEntitiesCacheType(cacheTypeProperties);
		this.initializeEntitiesCacheSize(cacheSizeProperties);
		this.initializeEntitiesSharedCache(sharedCacheProperties);
	}

	private void initializeEntitiesCacheType(Set<Property> properties) {
		for (Property property : properties) {
			String entityName = this.getEntityName(property);
			CacheType cacheType = getEnumValueOf(property.getValue(), CacheType.values());
			this.setCacheType_(cacheType, entityName);
		}
	}

	private void initializeEntitiesCacheSize(Set<Property> properties) {
		for (Property property : properties) {
			String entityName = this.getEntityName(property);
			this.setCacheSize_(property, entityName);
		}
	}

	private void initializeEntitiesSharedCache(Set<Property> properties) {
		for (Property property : properties) {
			String entityName = this.getEntityName(property);
			this.setSharedCache_(property, entityName);
		}
	}

	// ********** behavior **********
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
		
		// Don't need to initialize propertyNames for: 
		// cacheType, sharedCache, cacheSize
	}

	/**
	 * Method used for identifying the given property.
	 */
	@Override
	public boolean itemIsProperty(Property item) {
		boolean isProperty = super.itemIsProperty(item);
		
		if ( ! isProperty) {
				if (item.getName().startsWith(ECLIPSELINK_CACHE_TYPE) || 
						item.getName().startsWith(ECLIPSELINK_CACHE_SIZE) || 
						item.getName().startsWith(ECLIPSELINK_SHARED_CACHE)) {
					return true;
				}
				return false;
		}
		return true;
	}

	/**
	 * Returns the property name used for change notification of the given
	 * property.
	 */
	@Override
	public String propertyIdFor(Property property) {
		try {
			return super.propertyIdFor(property);
		}
		catch (IllegalArgumentException e) {
			if (property.getName().startsWith(ECLIPSELINK_CACHE_TYPE)) {
				return CACHE_TYPE_PROPERTY;
			}
			else if (property.getName().startsWith(ECLIPSELINK_CACHE_SIZE)) {
				return CACHE_SIZE_PROPERTY;
			}
			else if (property.getName().startsWith(ECLIPSELINK_SHARED_CACHE)) {
				return SHARED_CACHE_PROPERTY;
			}
		}
		throw new IllegalArgumentException("Illegal property: " + property.toString());
	}

	public void propertyChanged(PropertyChangeEvent event) {
		String aspectName = event.getAspectName();
		if (aspectName.equals(CACHE_TYPE_DEFAULT_PROPERTY)) {
			this.cacheTypeDefaultChanged(event);
		}
		else if (aspectName.equals(CACHE_SIZE_DEFAULT_PROPERTY)) {
			this.cacheSizeDefaultChanged(event);
		}
		else if (aspectName.equals(SHARED_CACHE_DEFAULT_PROPERTY)) {
			this.sharedCacheDefaultChanged(event);
		}
		else if (aspectName.equals(CACHE_TYPE_PROPERTY)) {
			this.cacheTypeChanged(event);
		}
		else if (aspectName.equals(CACHE_SIZE_PROPERTY)) {
			this.cacheSizeChanged(event);
		}
		else if (aspectName.equals(SHARED_CACHE_PROPERTY)) {
			this.sharedCacheChanged(event);
		}
		else {
			throw new IllegalArgumentException("Illegal event received - property not applicable: " + aspectName);
		}
		return;
	}

	// ********** CacheType **********
	public CacheType getCacheType(String entityName) {
		CacheProperties cache = this.cachePropertiesOf(entityName);
		return (cache == null) ? null : cache.getType();
	}

	public void setCacheType(CacheType newCacheType, String entityName) {
		CacheProperties old = this.setCacheType_(newCacheType, entityName);
		this.putEnumValue(ECLIPSELINK_CACHE_TYPE, entityName, newCacheType, false);
		this.firePropertyChanged(CACHE_TYPE_PROPERTY, old, this.cachePropertiesOf(entityName));
	}

	private void cacheTypeChanged(PropertyChangeEvent event) {
		Property property = (Property) event.getNewValue();
		// property == null when removed
		String entityName = (property == null) ? this.getEntityName((Property) event.getOldValue()) : this.getEntityName(property);
		CacheProperties old = this.setCacheType_(property, entityName);
		this.firePropertyChanged(event.getAspectName(), old, this.cachePropertiesOf(entityName));
	}

	public CacheType getDefaultCacheType() {
		return DEFAULT_CACHE_TYPE;
	}

	// ********** CacheSize **********
	public Integer getCacheSize(String entityName) {
		CacheProperties cache = this.cachePropertiesOf(entityName);
		return (cache == null) ? null : cache.getSize();
	}

	public void setCacheSize(Integer newCacheSize, String entityName) {
		CacheProperties old = this.setCacheSize_(newCacheSize, entityName);
		this.putIntegerValue(ECLIPSELINK_CACHE_SIZE + entityName, newCacheSize);
		this.firePropertyChanged(CACHE_SIZE_PROPERTY, old, this.cachePropertiesOf(entityName));
	}

	private void cacheSizeChanged(PropertyChangeEvent event) {
		Property property = (Property) event.getNewValue();
		// property == null when removed
		String entityName = (property == null) ? this.getEntityName((Property) event.getOldValue()) : this.getEntityName(property);
		CacheProperties old = this.setCacheSize_(property, entityName);
		this.firePropertyChanged(event.getAspectName(), old, this.cachePropertiesOf(entityName));
	}

	public Integer getDefaultCacheSize() {
		return DEFAULT_CACHE_SIZE;
	}

	// ********** SharedCache **********
	public Boolean getSharedCache(String entityName) {
		CacheProperties cache = this.cachePropertiesOf(entityName);
		return (cache == null) ? null : cache.isShared();
	}

	public void setSharedCache(Boolean newSharedCache, String entityName) {
		CacheProperties old = this.setSharedCache_(newSharedCache, entityName);
		this.putBooleanValue(ECLIPSELINK_SHARED_CACHE, entityName, newSharedCache, false);
		this.firePropertyChanged(SHARED_CACHE_PROPERTY, old, this.cachePropertiesOf(entityName));
	}

	private void sharedCacheChanged(PropertyChangeEvent event) {
		String entityName;
		Property newProperty = (Property) event.getNewValue();
		// property == null when removed
		entityName = (newProperty == null) ? 
			this.getEntityName((Property) event.getOldValue()) : this.getEntityName(newProperty);
		CacheProperties old = this.setSharedCache_(newProperty, entityName);
		this.firePropertyChanged(event.getAspectName(), old, this.cachePropertiesOf(entityName));
	}

	public Boolean getDefaultSharedCache() {
		return DEFAULT_SHARED_CACHE;
	}

	// ********** CacheTypeDefault **********
	public CacheType getCacheTypeDefault() {
		return this.cacheTypeDefault;
	}

	public void setCacheTypeDefault(CacheType newCacheTypeDefault) {
		CacheType old = this.cacheTypeDefault;
		this.cacheTypeDefault = newCacheTypeDefault;
		this.putProperty(CACHE_TYPE_DEFAULT_PROPERTY, newCacheTypeDefault);
		this.firePropertyChanged(CACHE_TYPE_DEFAULT_PROPERTY, old, newCacheTypeDefault);
	}

	private void cacheTypeDefaultChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		CacheType newValue = getEnumValueOf(stringValue, CacheType.values());
		CacheType old = this.cacheTypeDefault;
		this.cacheTypeDefault = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public CacheType getDefaultCacheTypeDefault() {
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

	private void cacheSizeDefaultChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Integer newValue = getIntegerValueOf(stringValue);
		Integer old = this.cacheSizeDefault;
		this.cacheSizeDefault = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
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

	private void sharedCacheDefaultChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.sharedCacheDefault;
		this.sharedCacheDefault = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultSharedCacheDefault() {
		return DEFAULT_SHARED_CACHE_DEFAULT;
	}

	// ****** CacheProperties *******
	/**
	 * Convenience method to update the CacheType in entitiesCache map. Returns
	 * the old value of CacheProperties
	 */
	private CacheProperties setCacheType_(Property newProperty, String entityName) {
		String stringValue = (newProperty == null) ? null : newProperty.getValue();
		CacheType newValue = getEnumValueOf(stringValue, CacheType.values());
		return this.setCacheType_(newValue, entityName);
	}

	private CacheProperties setCacheType_(CacheType newValue, String entityName) {
		CacheProperties properties = this.cachePropertiesOf(entityName);
		CacheProperties old = properties.clone();
		properties.setType(newValue);
		this.putEntityCacheProperties(entityName, properties);
		return old;
	}

	/**
	 * Convenience method to update the CacheSize in entitiesCache map. Returns
	 * the old value of CacheProperties
	 */
	private CacheProperties setCacheSize_(Property newProperty, String entityName) {
		String stringValue = (newProperty == null) ? null : newProperty.getValue();
		Integer newValue = getIntegerValueOf(stringValue);
		return this.setCacheSize_(newValue, entityName);
	}

	private CacheProperties setCacheSize_(Integer newValue, String entityName) {
		CacheProperties properties = this.cachePropertiesOf(entityName);
		CacheProperties old = properties.clone();
		properties.setSize(newValue);
		this.putEntityCacheProperties(entityName, properties);
		return old;
	}

	/**
	 * Convenience method to update the SharedCache in entitiesCacheProperties map.
	 * Returns the old value of CacheProperties
	 */
	private CacheProperties setSharedCache_(Property newProperty, String entityName) {
		String newValue = (newProperty == null) ? null : newProperty.getValue();
		return this.setSharedCache_(newValue, entityName);
	}

	private CacheProperties setSharedCache_(String newString, String entityName) {
		Boolean newValue = getBooleanValueOf(newString);
		return setSharedCache_(newValue, entityName);
	}

	private CacheProperties setSharedCache_(Boolean newValue, String entityName) {
		CacheProperties properties = this.cachePropertiesOf(entityName);
		CacheProperties old = properties.clone();
		properties.setShared(newValue);
		this.putEntityCacheProperties(entityName, properties);
		return old;
	}

	/**
	 * Returns the CacheProperties of the Entity with the given name.
	 */
	private CacheProperties cachePropertiesOf(String entityName) {
		CacheProperties properties = this.entitiesCacheProperties.get(entityName);
		if (properties == null) {
			properties = new CacheProperties(entityName);
		}
		return properties;
	}

	// ****** convenience methods *******
	
	private void putEntityCacheProperties(String entityName, CacheProperties properties) {
		this.addOrReplaceEntity(entityName, properties);
	}

	// ****** entities list *******
	
	public ListIterator<String> entities() {
		return CollectionTools.list(this.entitiesCacheProperties.keySet()).listIterator();
	}

	public int entitiesSize() {
		return this.entitiesCacheProperties.size();
	}

	public String addEntity(String entity) {
		return this.addOrReplaceEntity(entity, new CacheProperties(entity));
	}

	private String addOrReplaceEntity(String entity, CacheProperties properties) {
		if (this.entitiesCacheProperties.containsKey(entity)) {
			this.replaceEntity_(entity, properties);
			return null;
		}
		this.entitiesCacheProperties.put(entity, properties);
		this.fireListChanged(ENTITIES_LIST_PROPERTY);
		return entity;
	}

	private CacheProperties replaceEntity_(String entity, CacheProperties properties) {
		CacheProperties old = this.entitiesCacheProperties.get(entity);
		this.entitiesCacheProperties.put(entity, properties);
		// fire change ?
		return old;
	}

	public void removeEntity(String entity) {
		if (!this.entitiesCacheProperties.containsKey(entity)) {
			return;
		}
		this.entitiesCacheProperties.remove(entity);
		this.fireListChanged(ENTITIES_LIST_PROPERTY);
	}
}
