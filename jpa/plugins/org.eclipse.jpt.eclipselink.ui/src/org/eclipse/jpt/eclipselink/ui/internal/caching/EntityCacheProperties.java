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
public class EntityCacheProperties extends AbstractModel {

	private Caching caching;
	private String entityName;

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

	public Boolean getSharedCacheDefault() {
		return this.caching.getDefaultSharedCache();
	}

	public void setCacheSize(Integer cachingSize) {
		this.caching.setCacheSize(cachingSize, this.entityName);
	}

	public void setCacheType(CacheType cacheType) {
		this.caching.setCacheType(cacheType, this.entityName);
	}

	public void setSharedCache(Boolean sharedCache) {
		this.caching.setSharedCache(sharedCache, this.entityName);
	}
}
