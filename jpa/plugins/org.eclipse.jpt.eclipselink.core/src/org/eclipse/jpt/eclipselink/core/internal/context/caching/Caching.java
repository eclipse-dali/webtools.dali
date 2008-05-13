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

import java.util.ListIterator;

import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;

/**
 *  Caching
 */
public interface Caching extends PersistenceUnitProperties
{
	CacheType getDefaultCacheTypeDefault();
	CacheType getCacheTypeDefault();
	void setCacheTypeDefault(CacheType cacheTypeDefault);
			static final String CACHE_TYPE_DEFAULT_PROPERTY = "cacheTypeDefaultProperty";
			// EclipseLink key string
			static final String ECLIPSELINK_CACHE_TYPE_DEFAULT = "eclipselink.cache.type.default";
			static final CacheType DEFAULT_CACHE_TYPE_DEFAULT = CacheType.soft_weak;

	Integer getDefaultCacheSizeDefault();
	Integer getCacheSizeDefault();
	void setCacheSizeDefault(Integer cacheSizeDefault);
			static final String CACHE_SIZE_DEFAULT_PROPERTY = "cacheSizeDefaultProperty";
			// EclipseLink key string
			static final String ECLIPSELINK_CACHE_SIZE_DEFAULT = "eclipselink.cache.size.default";
			static final Integer DEFAULT_CACHE_SIZE_DEFAULT = new Integer(1000);

	Boolean getDefaultSharedCacheDefault();
	Boolean getSharedCacheDefault();
	void setSharedCacheDefault(Boolean sharedCacheDefault);
		static final String SHARED_CACHE_DEFAULT_PROPERTY = "sharedCacheDefaultProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_SHARED_DEFAULT = "eclipselink.cache.shared.default";
		static final Boolean DEFAULT_SHARED_CACHE_DEFAULT = Boolean.TRUE;


	CacheType getDefaultCacheType();
	CacheType getCacheType(String entityName);
	void setCacheType(CacheType cacheType, String entityName);
			static final String CACHE_TYPE_PROPERTY = "cacheTypeProperty";
			// EclipseLink key string
			static final String ECLIPSELINK_CACHE_TYPE = "eclipselink.cache.type.";
			static final CacheType DEFAULT_CACHE_TYPE = CacheType.soft_weak;

	Integer getDefaultCacheSize();
	Integer getCacheSize(String entityName);
	void setCacheSize(Integer cacheSize, String entityName);
			static final String CACHE_SIZE_PROPERTY = "cacheSizeProperty";
			// EclipseLink key string
			static final String ECLIPSELINK_CACHE_SIZE = "eclipselink.cache.size.";
			static final Integer DEFAULT_CACHE_SIZE = new Integer(1000);

	Boolean getDefaultSharedCache();
	Boolean getSharedCache(String entityName);
	void setSharedCache(Boolean sharedCache, String entityName);
			static final String SHARED_CACHE_PROPERTY = "sharedCacheProperty";
			// EclipseLink key string
			static final String ECLIPSELINK_SHARED_CACHE = "eclipselink.cache.shared.";
			static final Boolean DEFAULT_SHARED_CACHE = Boolean.TRUE;


	ListIterator<String> entities();
	int entitiesSize();
	boolean entityExists(String entity);
	String addEntity(String entity);
	void removeEntity(String entity);
		String ENTITIES_LIST_PROPERTY = "entitiesListProperty";

}
