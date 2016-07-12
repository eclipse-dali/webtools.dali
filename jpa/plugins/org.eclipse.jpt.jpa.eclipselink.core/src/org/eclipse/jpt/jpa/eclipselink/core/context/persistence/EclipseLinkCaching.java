/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 * EclipseLink Caching
 */
public interface EclipseLinkCaching
	extends PersistenceUnitProperties
{
	EclipseLinkCacheType getDefaultCacheTypeDefault();
	Transformer<EclipseLinkCaching, EclipseLinkCacheType> DEFAULT_CACHE_TYPE_DEFAULT_TRANSFORMER = new DefaultCacheTypeDefaultTransformer();
	class DefaultCacheTypeDefaultTransformer
		extends TransformerAdapter<EclipseLinkCaching, EclipseLinkCacheType>
	{
		@Override
		public EclipseLinkCacheType transform(EclipseLinkCaching caching) {
			return caching.getDefaultCacheTypeDefault();
		}
	}

	EclipseLinkCacheType getCacheTypeDefault();
	void setCacheTypeDefault(EclipseLinkCacheType cacheTypeDefault);
		static final String CACHE_TYPE_DEFAULT_PROPERTY = "cacheTypeDefault"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_TYPE_DEFAULT = "eclipselink.cache.type.default"; //$NON-NLS-1$
		static final EclipseLinkCacheType DEFAULT_CACHE_TYPE_DEFAULT = EclipseLinkCacheType.soft_weak;

	Integer getDefaultCacheSizeDefault();
	Transformer<EclipseLinkCaching, Integer> DEFAULT_CACHE_SIZE_DEFAULT_TRANSFORMER = new DefaultCacheSizeDefaultTransformer();
	class DefaultCacheSizeDefaultTransformer
		extends TransformerAdapter<EclipseLinkCaching, Integer>
	{
		@Override
		public Integer transform(EclipseLinkCaching caching) {
			return caching.getDefaultCacheSizeDefault();
		}
	}

	Integer getCacheSizeDefault();
	void setCacheSizeDefault(Integer cacheSizeDefault);
		static final String CACHE_SIZE_DEFAULT_PROPERTY = "cacheSizeDefault"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_SIZE_DEFAULT = "eclipselink.cache.size.default"; //$NON-NLS-1$
		static final Integer DEFAULT_CACHE_SIZE_DEFAULT = Integer.valueOf(100);

	Boolean getDefaultSharedCacheDefault();
	Transformer<EclipseLinkCaching, Boolean> DEFAULT_SHARED_CACHE_DEFAULT_TRANSFORMER = new DefaultSharedCacheDefaultTransformer();
	class DefaultSharedCacheDefaultTransformer
		extends TransformerAdapter<EclipseLinkCaching, Boolean>
	{
		@Override
		public Boolean transform(EclipseLinkCaching caching) {
			return caching.getDefaultSharedCacheDefault();
		}
	}

	Boolean getSharedCacheDefault();
	void setSharedCacheDefault(Boolean sharedCacheDefault);
		static final String SHARED_CACHE_DEFAULT_PROPERTY = "sharedCacheDefault"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_SHARED_DEFAULT = "eclipselink.cache.shared.default"; //$NON-NLS-1$
		static final Boolean DEFAULT_SHARED_CACHE_DEFAULT = Boolean.TRUE;


	EclipseLinkCacheType getDefaultCacheType();
	EclipseLinkCacheType getCacheTypeOf(String entityName);
	void setCacheTypeOf(String entityName, EclipseLinkCacheType cacheType);
		static final String CACHE_TYPE_PROPERTY = "cacheType"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_TYPE = "eclipselink.cache.type."; //$NON-NLS-1$
		static final EclipseLinkCacheType DEFAULT_CACHE_TYPE = EclipseLinkCacheType.soft_weak;

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

	EclipseLinkFlushClearCache getDefaultFlushClearCache();
	Transformer<EclipseLinkCaching, EclipseLinkFlushClearCache> DEFAULT_FLUSH_CLEAR_CACHE_TRANSFORMER = new DefaultFlushClearCacheTransformer();
	class DefaultFlushClearCacheTransformer
		extends TransformerAdapter<EclipseLinkCaching, EclipseLinkFlushClearCache>
	{
		@Override
		public EclipseLinkFlushClearCache transform(EclipseLinkCaching caching) {
			return caching.getDefaultFlushClearCache();
		}
	}

	EclipseLinkFlushClearCache getFlushClearCache();
	void setFlushClearCache(EclipseLinkFlushClearCache newFlushClearCache);
		static final String FLUSH_CLEAR_CACHE_PROPERTY = "flushClearCache"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_FLUSH_CLEAR_CACHE = "eclipselink.flush-clear.cache"; //$NON-NLS-1$
		static final EclipseLinkFlushClearCache DEFAULT_FLUSH_CLEAR_CACHE = EclipseLinkFlushClearCache.drop_invalidate;

	void removeDefaultCachingProperties();

	ListIterable<EclipseLinkCachingEntity> getEntities();
	Iterable<String> getEntityNames();
	int getEntitiesSize();
	boolean entityExists(String entity);
	EclipseLinkCachingEntity addEntity(String entity);
	void removeEntity(String entity);
		String ENTITIES_LIST = "entities"; //$NON-NLS-1$

}
