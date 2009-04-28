/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.caching;

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.caching.Caching;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * EntityCacheProperties
 */
@SuppressWarnings("nls")
public class EntityCacheProperties extends AbstractModel {

	private Caching caching;
	private String entityName;
	
	private PropertyValueModel<CacheType> cacheTypeHolder;
	private PropertyChangeListener cacheTypeListener;
	private PropertyValueModel<Integer> cacheSizeHolder;
	private PropertyChangeListener cacheSizeListener;
	private PropertyValueModel<Boolean> sharedCacheHolder;
	private PropertyChangeListener sharedCacheListener;

	public static final String CACHE_TYPE_PROPERTY = Caching.CACHE_TYPE_PROPERTY;
	public static final String CACHE_SIZE_PROPERTY = Caching.CACHE_SIZE_PROPERTY;
	public static final String SHARED_CACHE_PROPERTY = Caching.SHARED_CACHE_PROPERTY;

	private static final long serialVersionUID = 1L;

	// ********** constructors **********
	public EntityCacheProperties(Caching caching, String entityName) {
		super();
		this.caching    = caching;
		this.entityName = entityName;
		
		PropertyValueModel<Caching> cachingHolder = new SimplePropertyValueModel<Caching>(this.caching);
		this.initialize(cachingHolder);

		this.engageListeners();
	}
	
	protected void initialize(PropertyValueModel<Caching> cachingHolder) {
		this.cacheTypeHolder = this.buildCacheTypeAA(cachingHolder);
		this.cacheTypeListener = this.buildCacheTypeChangeListener();

		this.cacheSizeHolder = this.buildCacheSizeAA(cachingHolder);
		this.cacheSizeListener = this.buildCacheSizeChangeListener();

		this.sharedCacheHolder = this.buildSharedCacheAA(cachingHolder);
		this.sharedCacheListener = this.buildSharedCacheChangeListener();
	}
	
	// ********** behavior **********
	public boolean entityNameIsValid() {
		return !StringTools.stringIsEmpty(this.entityName);
	}

	public Integer getCacheSize() {
		return this.caching.getCacheSize(this.entityName);
	}

	public CacheType getCacheType() {
		return this.caching.getCacheType(this.entityName);
	}

	public Caching getCaching() {
		return caching;
	}

	public Integer getDefaultCacheSize() {
		return this.caching.getDefaultCacheSize();
	}

	public CacheType getDefaultCacheType() {
		return this.caching.getDefaultCacheType();
	}

	public String getEntityName() {
		return this.entityName;
	}

	public Boolean getSharedCache() {
		return this.caching.getSharedCache(this.entityName);
	}

	public Boolean getDefaultSharedCache() {
		return this.caching.getDefaultSharedCache();
	}

	public void setCacheSize(Integer cacheSize) {
		Integer oldCacheSize = this.getCacheSize();
		if (this.attributeValueHasChanged(oldCacheSize, cacheSize)) {
			this.caching.setCacheSize(cacheSize, this.entityName);
			this.firePropertyChanged(CACHE_SIZE_PROPERTY, oldCacheSize, cacheSize);
		}
	}

	public void setCacheType(CacheType cacheType) {
		CacheType oldCacheType = this.getCacheType();
		if (this.attributeValueHasChanged(oldCacheType, cacheType)) {
			this.caching.setCacheType(cacheType, this.entityName);
			this.firePropertyChanged(CACHE_TYPE_PROPERTY, oldCacheType, cacheType);
		}
	}

	public void setSharedCache(Boolean sharedCache) {
		Boolean oldSharedCache = this.getSharedCache();
		if (this.attributeValueHasChanged(oldSharedCache, sharedCache)) {
			this.caching.setSharedCache(sharedCache, this.entityName);
			this.firePropertyChanged(SHARED_CACHE_PROPERTY, oldSharedCache, sharedCache);
		}
	}
	
	// ********** PropertyChangeListener **********
	
	private PropertyValueModel<CacheType> buildCacheTypeAA(PropertyValueModel<Caching> subjectHolder) {
		return new PropertyAspectAdapter<Caching, CacheType>(
								subjectHolder, CACHE_TYPE_PROPERTY) {
			@Override
			protected CacheType buildValue_() {
				return this.subject.getCacheType(EntityCacheProperties.this.entityName);
			}
		};
	}
	
	private PropertyChangeListener buildCacheTypeChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				EntityCacheProperties.this.firePropertyChanged(CACHE_TYPE_PROPERTY, e.getOldValue(), e.getNewValue());
			}
		};
	}
	
	private PropertyValueModel<Integer> buildCacheSizeAA(PropertyValueModel<Caching> subjectHolder) {
		return new PropertyAspectAdapter<Caching, Integer>(
								subjectHolder, CACHE_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return this.subject.getCacheSize(EntityCacheProperties.this.entityName);
			}
		};
	}
	
	private PropertyChangeListener buildCacheSizeChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				EntityCacheProperties.this.firePropertyChanged(CACHE_SIZE_PROPERTY, e.getOldValue(), e.getNewValue());
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildSharedCacheAA(PropertyValueModel<Caching> subjectHolder) {
		return new PropertyAspectAdapter<Caching, Boolean>(
								subjectHolder, SHARED_CACHE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSharedCache(EntityCacheProperties.this.entityName);
			}
		};
	}
	
	private PropertyChangeListener buildSharedCacheChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				EntityCacheProperties.this.firePropertyChanged(SHARED_CACHE_PROPERTY, e.getOldValue(), e.getNewValue());
			}
		};
	}

	public void engageListeners() {
		this.cacheTypeHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.cacheTypeListener);
		this.cacheSizeHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.cacheSizeListener);
		this.sharedCacheHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.sharedCacheListener);
	}
	
	public void disengageListeners() {
		this.cacheTypeHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.cacheTypeListener);
		this.cacheSizeHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.cacheSizeListener);
		this.sharedCacheHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.sharedCacheListener);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append("name: ");
		sb.append(this.entityName);
		sb.append(", type: ");
		sb.append(this.getCacheType());
		sb.append(", size: ");
		sb.append(this.getCacheSize());
		sb.append(", isShared: ");
		sb.append(this.getSharedCache());
	}
}
