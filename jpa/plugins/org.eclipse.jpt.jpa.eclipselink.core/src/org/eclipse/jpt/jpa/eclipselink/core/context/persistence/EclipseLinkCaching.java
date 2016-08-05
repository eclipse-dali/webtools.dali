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

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
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
		Transformer<EclipseLinkCaching, EclipseLinkCacheType> CACHE_TYPE_DEFAULT_TRANSFORMER = new CacheTypeDefaultTransformer();
		class CacheTypeDefaultTransformer
			extends TransformerAdapter<EclipseLinkCaching, EclipseLinkCacheType>
		{
			@Override
			public EclipseLinkCacheType transform(EclipseLinkCaching caching) {
				return caching.getCacheTypeDefault();
			}
		}
	void setCacheTypeDefault(EclipseLinkCacheType cacheTypeDefault);
		String CACHE_TYPE_DEFAULT_PROPERTY = "cacheTypeDefault"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_CACHE_TYPE_DEFAULT = "eclipselink.cache.type.default"; //$NON-NLS-1$
		EclipseLinkCacheType DEFAULT_CACHE_TYPE_DEFAULT = EclipseLinkCacheType.soft_weak;
		BiClosure<EclipseLinkCaching, EclipseLinkCacheType> SET_CACHE_TYPE_DEFAULT_CLOSURE = new SetCacheTypeDefaultClosure();
		class SetCacheTypeDefaultClosure
			extends BiClosureAdapter<EclipseLinkCaching, EclipseLinkCacheType>
		{
			@Override
			public void execute(EclipseLinkCaching caching, EclipseLinkCacheType cacheTypeDefault) {
				caching.setCacheTypeDefault(cacheTypeDefault);
			}
		}

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
		String CACHE_SIZE_DEFAULT_PROPERTY = "cacheSizeDefault"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_CACHE_SIZE_DEFAULT = "eclipselink.cache.size.default"; //$NON-NLS-1$
		Integer DEFAULT_CACHE_SIZE_DEFAULT = Integer.valueOf(100);

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
		Transformer<EclipseLinkCaching, Boolean> SHARED_CACHE_DEFAULT_TRANSFORMER = new SharedCacheDefaultTransformer();
		class SharedCacheDefaultTransformer
			extends TransformerAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public Boolean transform(EclipseLinkCaching caching) {
				return caching.getSharedCacheDefault();
			}
		}
	void setSharedCacheDefault(Boolean sharedCacheDefault);
		String SHARED_CACHE_DEFAULT_PROPERTY = "sharedCacheDefault"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_CACHE_SHARED_DEFAULT = "eclipselink.cache.shared.default"; //$NON-NLS-1$
		Boolean DEFAULT_SHARED_CACHE_DEFAULT = Boolean.TRUE;
		BiClosure<EclipseLinkCaching, Boolean> SET_SHARED_CACHE_DEFAULT_CLOSURE = new SetSharedCacheDefaultClosure();
		class SetSharedCacheDefaultClosure
			extends BiClosureAdapter<EclipseLinkCaching, Boolean>
		{
			@Override
			public void execute(EclipseLinkCaching caching, Boolean sharedCacheDefault) {
				caching.setSharedCacheDefault(sharedCacheDefault);
			}
		}


	EclipseLinkCacheType getDefaultEntityCacheType();
	EclipseLinkCacheType getEntityCacheType(String entityName);
	void setEntityCacheType(String entityName, EclipseLinkCacheType cacheType);
		// EclipseLink key string
		String ECLIPSELINK_CACHE_TYPE = "eclipselink.cache.type."; //$NON-NLS-1$
		EclipseLinkCacheType DEFAULT_ENTITY_CACHE_TYPE = EclipseLinkCacheType.soft_weak;

	Integer getDefaultEntityCacheSize();
	Integer getEntityCacheSize(String entityName);
	void setEntityCacheSize(String entityName, Integer cacheSize);
		// EclipseLink key string
		String ECLIPSELINK_CACHE_SIZE = "eclipselink.cache.size."; //$NON-NLS-1$
		Integer DEFAULT_ENTITY_CACHE_SIZE = Integer.valueOf(100);

	Boolean getDefaultEntitySharedCache();
	Boolean getEntitySharedCache(String entityName);
	void setEntitySharedCache(String entityName, Boolean sharedCache);
		// EclipseLink key string
		String ECLIPSELINK_SHARED_CACHE = "eclipselink.cache.shared."; //$NON-NLS-1$
		Boolean DEFAULT_ENTITY_SHARED_CACHE = Boolean.TRUE;

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
		String FLUSH_CLEAR_CACHE_PROPERTY = "flushClearCache"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_FLUSH_CLEAR_CACHE = "eclipselink.flush-clear.cache"; //$NON-NLS-1$
		EclipseLinkFlushClearCache DEFAULT_FLUSH_CLEAR_CACHE = EclipseLinkFlushClearCache.drop_invalidate;

	void removeDefaultCachingProperties();

	ListIterable<EclipseLinkCachingEntity> getCachingEntities();
	Iterable<String> getCachingEntityNames();
	int getCachingEntitiesSize();
	boolean cachingEntityExists(String entityName);
	EclipseLinkCachingEntity addCachingEntity(String entityName);
	void removeCachingEntity(String entityName);
		String CACHING_ENTITIES_LIST = "cachingEntities"; //$NON-NLS-1$

}
