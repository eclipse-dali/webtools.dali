/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.caching;

import org.eclipse.jpt.eclipselink.core.internal.context.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

/**
 * EntityCacheProperties
 */
@SuppressWarnings("nls")
public class EntityCacheProperties extends AbstractModel {

	private Caching caching;
	private String entityName;

	private static final long serialVersionUID = 1L;

	public static final String CACHE_SIZE_PROPERTY = Caching.CACHE_SIZE_PROPERTY;
	public static final String CACHE_TYPE_PROPERTY = Caching.CACHE_TYPE_PROPERTY;
	public static final String SHARED_CACHE_PROPERTY = Caching.SHARED_CACHE_DEFAULT_PROPERTY;

	// ********** constructors **********
	public EntityCacheProperties(Caching caching, String entityName) {
		super();
		this.caching    = caching;
		this.entityName = entityName;
	}

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
		this.caching.setCacheSize(cacheSize, this.entityName);
		this.firePropertyChanged(CACHE_SIZE_PROPERTY, oldCacheSize, cacheSize);
	}

	public void setCacheType(CacheType cacheType) {
		CacheType oldCacheType = this.getCacheType();
		this.caching.setCacheType(cacheType, this.entityName);
		this.firePropertyChanged(CACHE_TYPE_PROPERTY, oldCacheType, cacheType);
	}

	public void setSharedCache(Boolean sharedCache) {
		Boolean oldSharedCache = this.getSharedCache();
		this.caching.setSharedCache(sharedCache, this.entityName);
		this.firePropertyChanged(SHARED_CACHE_PROPERTY, oldSharedCache, sharedCache);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringTools.buildSimpleToStringOn(this, sb);
		sb.append(" (");
		this.toString(sb);
		sb.append(')');
		return sb.toString();
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
