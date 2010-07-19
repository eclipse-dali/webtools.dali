/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.persistence.caching;

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Entity;

/**
 *  Caching
 */
public interface Caching extends PersistenceUnitProperties
{
	CacheType getDefaultCacheTypeDefault();
	CacheType getCacheTypeDefault();
	void setCacheTypeDefault(CacheType cacheTypeDefault);
		static final String CACHE_TYPE_DEFAULT_PROPERTY = "cacheTypeDefault"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_TYPE_DEFAULT = "eclipselink.cache.type.default"; //$NON-NLS-1$
		static final CacheType DEFAULT_CACHE_TYPE_DEFAULT = CacheType.soft_weak;

	Integer getDefaultCacheSizeDefault();
	Integer getCacheSizeDefault();
	void setCacheSizeDefault(Integer cacheSizeDefault);
		static final String CACHE_SIZE_DEFAULT_PROPERTY = "cacheSizeDefault"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_SIZE_DEFAULT = "eclipselink.cache.size.default"; //$NON-NLS-1$
		static final Integer DEFAULT_CACHE_SIZE_DEFAULT = Integer.valueOf(100);

	Boolean getDefaultSharedCacheDefault();
	Boolean getSharedCacheDefault();
	void setSharedCacheDefault(Boolean sharedCacheDefault);
		static final String SHARED_CACHE_DEFAULT_PROPERTY = "sharedCacheDefault"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_SHARED_DEFAULT = "eclipselink.cache.shared.default"; //$NON-NLS-1$
		static final Boolean DEFAULT_SHARED_CACHE_DEFAULT = Boolean.TRUE;


	CacheType getDefaultCacheType();
	CacheType getCacheTypeOf(String entityName);
	void setCacheTypeOf(String entityName, CacheType cacheType);
		static final String CACHE_TYPE_PROPERTY = "cacheType"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_TYPE = "eclipselink.cache.type."; //$NON-NLS-1$
		static final CacheType DEFAULT_CACHE_TYPE = CacheType.soft_weak;

	Integer getDefaultCacheSize();
	Integer getCacheSizeOf(String entityName);
	void setCacheSizeOf(String entityName, Integer cacheSize);
		static final String CACHE_SIZE_PROPERTY = "cacheSize"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_SIZE = "eclipselink.cache.size."; //$NON-NLS-1$
		static final Integer DEFAULT_CACHE_SIZE = Integer.valueOf(100);

	Boolean getDefaultSharedCache();
	Boolean getSharedCacheOf(String entityName);
	void setSharedCacheOf(String entityName, Boolean sharedCache);
		static final String SHARED_CACHE_PROPERTY = "sharedCache"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_SHARED_CACHE = "eclipselink.cache.shared."; //$NON-NLS-1$
		static final Boolean DEFAULT_SHARED_CACHE = Boolean.TRUE;

	FlushClearCache getDefaultFlushClearCache();
	FlushClearCache getFlushClearCache();
	void setFlushClearCache(FlushClearCache newFlushClearCache);
		static final String FLUSH_CLEAR_CACHE_PROPERTY = "flushClearCache"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_FLUSH_CLEAR_CACHE = "eclipselink.flush-clear.cache"; //$NON-NLS-1$
		static final FlushClearCache DEFAULT_FLUSH_CLEAR_CACHE = FlushClearCache.drop_invalidate;

	void removeDefaultCachingProperties();

	ListIterator<Entity> entities();
	Iterator<String> entityNames();
	int entitiesSize();
	boolean entityExists(String entity);
	Entity addEntity(String entity);
	void removeEntity(String entity);
		String ENTITIES_LIST = "entities"; //$NON-NLS-1$

}
