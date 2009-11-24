/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay;

public class OrmEclipseLinkCachingImpl 
	extends AbstractOrmXmlContextNode
	implements 
		OrmEclipseLinkCaching,
		OrmCacheableHolder2_0
{
	protected final XmlCacheHolder resource;
	
	protected int defaultSize;
	protected Integer specifiedSize;
	
	protected boolean defaultShared;
	protected Boolean specifiedShared;
	
	protected EclipseLinkCacheType defaultType;
	protected EclipseLinkCacheType specifiedType;
	
	protected boolean defaultAlwaysRefresh;
	protected Boolean specifiedAlwaysRefresh;
	
	protected boolean defaultRefreshOnlyIfNewer;
	protected Boolean specifiedRefreshOnlyIfNewer;
	
	protected boolean defaultDisableHits;
	protected Boolean specifiedDisableHits;
	
	protected EclipseLinkCacheCoordinationType defaultCoordinationType;
	protected EclipseLinkCacheCoordinationType specifiedCoordinationType;
	
	protected EclipseLinkExistenceType specifiedExistenceType;
	protected EclipseLinkExistenceType defaultExistenceType;
	
	protected Integer expiry;
	protected OrmEclipseLinkExpiryTimeOfDay expiryTimeOfDay;

	protected final OrmCacheable2_0 cacheable;
	
	public OrmEclipseLinkCachingImpl(OrmTypeMapping parent, XmlCacheHolder resource, XmlCacheable_2_0 cacheableResource, JavaEclipseLinkCaching javaCaching) {
		super(parent);
		this.resource = resource;
		XmlCache resourceCache = getResourceCache();
		this.defaultSize = this.defaultSize(javaCaching);
		this.specifiedSize = getResourceSize(resourceCache);
		this.defaultShared = this.defaultShared(javaCaching);
		this.specifiedShared = this.getResourceShared(resourceCache);
		this.defaultAlwaysRefresh = this.defaultAlwaysRefresh(javaCaching);
		this.specifiedAlwaysRefresh = this.getResourceAlwaysRefresh(resourceCache);
		this.defaultRefreshOnlyIfNewer = this.defaultRefreshOnlyIfNewer(javaCaching);
		this.specifiedRefreshOnlyIfNewer = this.getResourceRefreshOnlyIfNewer(resourceCache);
		this.defaultDisableHits = this.defaultDisableHits(javaCaching);
		this.specifiedDisableHits = this.getResourceDisableHits(resourceCache);
		this.defaultType = this.defaultType(javaCaching);
		this.specifiedType = this.getResourceType(resourceCache);
		this.defaultCoordinationType = this.defaultCoordinationType(javaCaching);
		this.specifiedCoordinationType = this.getResourceCoordinationType(resourceCache);
		this.defaultExistenceType = this.defaultExistenceType(javaCaching);
		this.specifiedExistenceType = this.getResourceExistenceChecking();
		this.initializeExpiry(resourceCache);
		this.cacheable = ((OrmXml2_0ContextNodeFactory) getXmlContextNodeFactory()).buildOrmCacheable(this, cacheableResource);
	}

	@Override
	public OrmTypeMapping getParent() {
		return (OrmTypeMapping) super.getParent();
	}
	
	public int getSize() {
		return (this.specifiedSize == null) ? this.defaultSize : this.specifiedSize.intValue();
	}
	
	public int getDefaultSize() {
		return this.defaultSize;
	}
	
	protected void setDefaultSize(int newSize) {
		int oldSize = this.defaultSize;
		this.defaultSize = newSize;
		firePropertyChanged(DEFAULT_SIZE_PROPERTY, oldSize, newSize);
	}
	
	public Integer getSpecifiedSize() {
		return this.specifiedSize;
	}
	
	public void setSpecifiedSize(Integer newSpecifiedSize) {
		Integer oldSpecifiedSize = this.specifiedSize;
		this.specifiedSize = newSpecifiedSize;
		if (oldSpecifiedSize != newSpecifiedSize) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setSize(newSpecifiedSize);						
				if (this.getResourceCache().isUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedSize != null) {
				addResourceCache();
				getResourceCache().setSize(newSpecifiedSize);
			}
		}
		firePropertyChanged(SPECIFIED_SIZE_PROPERTY, oldSpecifiedSize, newSpecifiedSize);		
	}
	
	protected void setSpecifiedSize_(Integer newSpecifiedSize) {
		Integer oldSpecifiedSize = this.specifiedSize;
		this.specifiedSize = newSpecifiedSize;
		firePropertyChanged(SPECIFIED_SIZE_PROPERTY, oldSpecifiedSize, newSpecifiedSize);
	}
	
	
	public boolean isShared() {
		return (this.specifiedShared == null) ? this.defaultShared : this.specifiedShared.booleanValue();
	}
	
	public boolean isDefaultShared() {
		return this.defaultShared;
	}
	
	protected void setDefaultShared(boolean newDefaultShared) {
		boolean oldDefaultShared = this.defaultShared;
		this.defaultShared = newDefaultShared;
		firePropertyChanged(DEFAULT_SHARED_PROPERTY, oldDefaultShared, newDefaultShared);	
	}
	
	public Boolean getSpecifiedShared() {
		return this.specifiedShared;
	}
	
	public void setSpecifiedShared(Boolean newSpecifiedShared) {
		Boolean oldSpecifiedShared = this.specifiedShared;
		this.specifiedShared = newSpecifiedShared;
		if (oldSpecifiedShared != newSpecifiedShared) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setShared(newSpecifiedShared);						
				if (this.getResourceCache().isUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedShared != null) {
				addResourceCache();
				getResourceCache().setShared(newSpecifiedShared);
			}
		}
		firePropertyChanged(SPECIFIED_SHARED_PROPERTY, oldSpecifiedShared, newSpecifiedShared);		
		if (newSpecifiedShared == Boolean.FALSE) {
			setSpecifiedType(null);
			setSpecifiedSize(null);
			setSpecifiedAlwaysRefresh(null);
			setSpecifiedRefreshOnlyIfNewer(null);
			setSpecifiedDisableHits(null);
			setSpecifiedCoordinationType(null);
			setExpiry(null);
			if (this.expiryTimeOfDay != null) {
				removeExpiryTimeOfDay();
			}
		}
	}
	
	protected void setSpecifiedShared_(Boolean newSpecifiedShared) {
		Boolean oldSpecifiedShared = this.specifiedShared;
		this.specifiedShared = newSpecifiedShared;
		firePropertyChanged(SPECIFIED_SHARED_PROPERTY, oldSpecifiedShared, newSpecifiedShared);	
	}
	
	
	public boolean isAlwaysRefresh() {
		return (this.specifiedAlwaysRefresh == null) ? this.defaultAlwaysRefresh : this.specifiedAlwaysRefresh.booleanValue();
	}
	
	public boolean isDefaultAlwaysRefresh() {
		return this.defaultAlwaysRefresh;
	}
	
	protected void setDefaultAlwaysRefresh(boolean newDefaultAlwaysRefresh) {
		boolean oldDefaultAlwaysRefresh = this.defaultAlwaysRefresh;
		this.defaultAlwaysRefresh = newDefaultAlwaysRefresh;
		firePropertyChanged(DEFAULT_ALWAYS_REFRESH_PROPERTY, oldDefaultAlwaysRefresh, newDefaultAlwaysRefresh);	
	}
	
	public Boolean getSpecifiedAlwaysRefresh() {
		return this.specifiedAlwaysRefresh;
	}
	
	public void setSpecifiedAlwaysRefresh(Boolean newSpecifiedAlwaysRefresh) {
		Boolean oldSpecifiedAlwaysRefresh = this.specifiedAlwaysRefresh;
		this.specifiedAlwaysRefresh = newSpecifiedAlwaysRefresh;
		if (oldSpecifiedAlwaysRefresh != newSpecifiedAlwaysRefresh) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setAlwaysRefresh(newSpecifiedAlwaysRefresh);						
				if (this.getResourceCache().isUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedAlwaysRefresh != null) {
				addResourceCache();
				getResourceCache().setAlwaysRefresh(newSpecifiedAlwaysRefresh);
			}
		}
		firePropertyChanged(SPECIFIED_ALWAYS_REFRESH_PROPERTY, oldSpecifiedAlwaysRefresh, newSpecifiedAlwaysRefresh);		
	}
	
	protected void setSpecifiedAlwaysRefresh_(Boolean newSpecifiedAlwaysRefresh) {
		Boolean oldSpecifiedAlwaysRefresh = this.specifiedAlwaysRefresh;
		this.specifiedAlwaysRefresh = newSpecifiedAlwaysRefresh;
		firePropertyChanged(SPECIFIED_ALWAYS_REFRESH_PROPERTY, oldSpecifiedAlwaysRefresh, newSpecifiedAlwaysRefresh);	
	}
	
	
	public boolean isRefreshOnlyIfNewer() {
		return (this.specifiedRefreshOnlyIfNewer == null) ? this.defaultRefreshOnlyIfNewer : this.specifiedRefreshOnlyIfNewer.booleanValue();
	}
	
	public boolean isDefaultRefreshOnlyIfNewer() {
		return this.defaultRefreshOnlyIfNewer;
	}
	
	protected void setDefaultRefreshOnlyIfNewer(boolean newDefaultRefreshOnlyIfNewer) {
		boolean oldDefaultRefreshOnlyIfNewer = this.defaultRefreshOnlyIfNewer;
		this.defaultRefreshOnlyIfNewer = newDefaultRefreshOnlyIfNewer;
		firePropertyChanged(DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY, oldDefaultRefreshOnlyIfNewer, newDefaultRefreshOnlyIfNewer);	
	}
	
	public Boolean getSpecifiedRefreshOnlyIfNewer() {
		return this.specifiedRefreshOnlyIfNewer;
	}
	
	public void setSpecifiedRefreshOnlyIfNewer(Boolean newSpecifiedRefreshOnlyIfNewer) {
		Boolean oldSpecifiedRefreshOnlyIfNewer = this.specifiedRefreshOnlyIfNewer;
		this.specifiedRefreshOnlyIfNewer = newSpecifiedRefreshOnlyIfNewer;
		if (oldSpecifiedRefreshOnlyIfNewer != newSpecifiedRefreshOnlyIfNewer) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setRefreshOnlyIfNewer(newSpecifiedRefreshOnlyIfNewer);						
				if (this.getResourceCache().isUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedRefreshOnlyIfNewer != null) {
				addResourceCache();
				getResourceCache().setRefreshOnlyIfNewer(newSpecifiedRefreshOnlyIfNewer);
			}
		}
		firePropertyChanged(SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY, oldSpecifiedRefreshOnlyIfNewer, newSpecifiedRefreshOnlyIfNewer);		
	}
	
	protected void setSpecifiedRefreshOnlyIfNewer_(Boolean newSpecifiedRefreshOnlyIfNewer) {
		Boolean oldSpecifiedRefreshOnlyIfNewer = this.specifiedRefreshOnlyIfNewer;
		this.specifiedRefreshOnlyIfNewer = newSpecifiedRefreshOnlyIfNewer;
		firePropertyChanged(SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY, oldSpecifiedRefreshOnlyIfNewer, newSpecifiedRefreshOnlyIfNewer);	
	}
	
	
	public boolean isDisableHits() {
		return (this.specifiedDisableHits == null) ? this.defaultDisableHits : this.specifiedDisableHits.booleanValue();
	}
	
	public boolean isDefaultDisableHits() {
		return this.defaultDisableHits;
	}
	
	protected void setDefaultDisableHits(boolean newDefaultDisableHits) {
		boolean oldDefaultDisableHits = this.defaultDisableHits;
		this.defaultDisableHits = newDefaultDisableHits;
		firePropertyChanged(DEFAULT_DISABLE_HITS_PROPERTY, oldDefaultDisableHits, newDefaultDisableHits);	
	}
	
	public Boolean getSpecifiedDisableHits() {
		return this.specifiedDisableHits;
	}
	
	public void setSpecifiedDisableHits(Boolean newSpecifiedDisableHits) {
		Boolean oldSpecifiedDisableHits = this.specifiedDisableHits;
		this.specifiedDisableHits = newSpecifiedDisableHits;
		if (oldSpecifiedDisableHits != newSpecifiedDisableHits) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setDisableHits(newSpecifiedDisableHits);						
				if (this.getResourceCache().isUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedDisableHits != null) {
				addResourceCache();
				getResourceCache().setDisableHits(newSpecifiedDisableHits);
			}
		}
		firePropertyChanged(SPECIFIED_DISABLE_HITS_PROPERTY, oldSpecifiedDisableHits, newSpecifiedDisableHits);		
	}
	
	protected void setSpecifiedDisableHits_(Boolean newSpecifiedDisableHits) {
		Boolean oldSpecifiedDisableHits = this.specifiedDisableHits;
		this.specifiedDisableHits = newSpecifiedDisableHits;
		firePropertyChanged(SPECIFIED_DISABLE_HITS_PROPERTY, oldSpecifiedDisableHits, newSpecifiedDisableHits);	
	}
	
	public EclipseLinkCacheType getType() {
		return (this.specifiedType == null) ? this.defaultType : this.specifiedType;
	}

	public EclipseLinkCacheType getDefaultType() {
		return this.defaultType;
	}
	
	protected void setDefaultType(EclipseLinkCacheType newDefaultType) {
		EclipseLinkCacheType oldDefaultType= this.defaultType;
		this.defaultType = newDefaultType;
		firePropertyChanged(DEFAULT_TYPE_PROPERTY, oldDefaultType, newDefaultType);				
	}
	
	public EclipseLinkCacheType getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(EclipseLinkCacheType newSpecifiedType) {
		EclipseLinkCacheType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		if (oldSpecifiedType != newSpecifiedType) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setType(EclipseLinkCacheType.toOrmResourceModel(newSpecifiedType));						
				if (this.getResourceCache().isUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedType != null) {
				addResourceCache();
				getResourceCache().setType(EclipseLinkCacheType.toOrmResourceModel(newSpecifiedType));
			}
		}
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);		
	}
	
	protected void setSpecifiedType_(EclipseLinkCacheType newSpecifiedType) {
		EclipseLinkCacheType oldSpecifiedType= this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);		
	}
	
	
	public EclipseLinkCacheCoordinationType getCoordinationType() {
		return (this.specifiedCoordinationType == null) ? this.defaultCoordinationType : this.specifiedCoordinationType;
	}
	
	public EclipseLinkCacheCoordinationType getDefaultCoordinationType() {
		return this.defaultCoordinationType;
	}
	
	protected void setDefaultCoordinationType(EclipseLinkCacheCoordinationType newDefaultcCoordinationType) {
		EclipseLinkCacheCoordinationType oldDefaultcCoordinationType= this.defaultCoordinationType;
		this.defaultCoordinationType = newDefaultcCoordinationType;
		firePropertyChanged(DEFAULT_COORDINATION_TYPE_PROPERTY, oldDefaultcCoordinationType, newDefaultcCoordinationType);				
	}
	
	public EclipseLinkCacheCoordinationType getSpecifiedCoordinationType() {
		return this.specifiedCoordinationType;
	}
	
	public void setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType newSpecifiedCoordinationType) {
		EclipseLinkCacheCoordinationType oldSpecifiedCoordinationType = this.specifiedCoordinationType;
		this.specifiedCoordinationType = newSpecifiedCoordinationType;
		if (oldSpecifiedCoordinationType != newSpecifiedCoordinationType) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setCoordinationType(EclipseLinkCacheCoordinationType.toOrmResourceModel(newSpecifiedCoordinationType));						
				if (this.getResourceCache().isUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedCoordinationType != null) {
				addResourceCache();
				getResourceCache().setCoordinationType(EclipseLinkCacheCoordinationType.toOrmResourceModel(newSpecifiedCoordinationType));
			}
		}
		firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, oldSpecifiedCoordinationType, newSpecifiedCoordinationType);		
	}
	
	protected void setSpecifiedCoordinationType_(EclipseLinkCacheCoordinationType newSpecifiedCoordinationType) {
		EclipseLinkCacheCoordinationType oldSpecifiedCoordinationType = this.specifiedCoordinationType;
		this.specifiedCoordinationType = newSpecifiedCoordinationType;
		firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, oldSpecifiedCoordinationType, newSpecifiedCoordinationType);		
	}
	
	public EclipseLinkExistenceType getExistenceType() {
		return (this.specifiedExistenceType == null) ? this.defaultExistenceType : this.specifiedExistenceType;
	}
	
	public EclipseLinkExistenceType getDefaultExistenceType() {
		return this.defaultExistenceType;
	}
	
	protected void setDefaultExistenceType(EclipseLinkExistenceType newDefaultExistenceType) {
		EclipseLinkExistenceType oldDefaultExistenceType = this.defaultExistenceType;
		this.defaultExistenceType = newDefaultExistenceType;
		firePropertyChanged(DEFAULT_EXISTENCE_TYPE_PROPERTY, oldDefaultExistenceType, newDefaultExistenceType);
	}
	
	public EclipseLinkExistenceType getSpecifiedExistenceType() {
		return this.specifiedExistenceType;
	}
	
	public void setSpecifiedExistenceType(EclipseLinkExistenceType newSpecifiedExistenceType) {
		EclipseLinkExistenceType oldSpecifiedExistenceType = this.specifiedExistenceType;
		this.specifiedExistenceType = newSpecifiedExistenceType;
		this.resource.setExistenceChecking(EclipseLinkExistenceType.toOrmResourceModel(newSpecifiedExistenceType));
		firePropertyChanged(SPECIFIED_EXISTENCE_TYPE_PROPERTY, oldSpecifiedExistenceType, newSpecifiedExistenceType);			
	}
	
	protected void setSpecifiedExistenceType_(EclipseLinkExistenceType newSpecifiedExistenceType) {
		EclipseLinkExistenceType oldSpecifiedExistenceType = this.specifiedExistenceType;
		this.specifiedExistenceType = newSpecifiedExistenceType;
		firePropertyChanged(SPECIFIED_EXISTENCE_TYPE_PROPERTY, oldSpecifiedExistenceType, newSpecifiedExistenceType);		
	}	
	
	public Integer getExpiry() {
		return this.expiry;
	}
	
	public void setExpiry(Integer newExpiry) {
		Integer oldExpiry = this.expiry;
		this.expiry = newExpiry;
		if (oldExpiry != newExpiry) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setExpiry(newExpiry);						
				if (this.getResourceCache().isUnset()) {
					removeResourceCache();
				}
			}
			else if (newExpiry != null) {
				addResourceCache();
				this.getResourceCache().setExpiry(newExpiry);						
			}
		}
		firePropertyChanged(EXPIRY_PROPERTY, oldExpiry, newExpiry);
		if (newExpiry != null && this.expiryTimeOfDay != null) {
			removeExpiryTimeOfDay();
		}
	}
	
	protected void setExpiry_(Integer newExpiry) {
		Integer oldExpiry = this.expiry;
		this.expiry = newExpiry;		
		firePropertyChanged(EXPIRY_PROPERTY, oldExpiry, newExpiry);
	}
	
	public EclipseLinkExpiryTimeOfDay getExpiryTimeOfDay() {
		return this.expiryTimeOfDay;
	}
	
	public EclipseLinkExpiryTimeOfDay addExpiryTimeOfDay() {
		if (this.expiryTimeOfDay != null) {
			throw new IllegalStateException("expiryTimeOfDay already exists, use getExpiryTimeOfDay()"); //$NON-NLS-1$
		}
		if (getResourceCache() == null) {
			addResourceCache();
		}
		OrmEclipseLinkExpiryTimeOfDay newExpiryTimeOfDay = new OrmEclipseLinkExpiryTimeOfDay(this);
		this.expiryTimeOfDay = newExpiryTimeOfDay;
		XmlTimeOfDay resourceTimeOfDay = EclipseLinkOrmFactory.eINSTANCE.createXmlTimeOfDay();
		newExpiryTimeOfDay.initialize(resourceTimeOfDay);
		getResourceCache().setExpiryTimeOfDay(resourceTimeOfDay);
		firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, null, newExpiryTimeOfDay);
		setExpiry(null);
		return newExpiryTimeOfDay;
	}
	
	public void removeExpiryTimeOfDay() {
		if (this.expiryTimeOfDay == null) {
			throw new IllegalStateException("timeOfDayExpiry does not exist"); //$NON-NLS-1$
		}
		EclipseLinkExpiryTimeOfDay oldExpiryTimeOfDay = this.expiryTimeOfDay;
		this.expiryTimeOfDay = null;
		getResourceCache().setExpiryTimeOfDay(null);
		if (this.getResourceCache().isUnset()) {
			removeResourceCache();
		}
		firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, oldExpiryTimeOfDay, null);
	}
	
	protected void setExpiryTimeOfDay(OrmEclipseLinkExpiryTimeOfDay newExpiryTimeOfDay) {
		OrmEclipseLinkExpiryTimeOfDay oldExpiryTimeOfDay = this.expiryTimeOfDay;
		this.expiryTimeOfDay = newExpiryTimeOfDay;
		firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, oldExpiryTimeOfDay, newExpiryTimeOfDay);
	}
	
	protected XmlCache getResourceCache() {
		return this.resource.getCache();
	}
	
	protected void addResourceCache() {
		this.resource.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());		
	}
	
	protected void removeResourceCache() {
		this.resource.setCache(null);
	}
	
	public OrmCacheable2_0 getCacheable() {
		return this.cacheable;
	}
	
	public boolean calculateDefaultCacheable() {
		if (!getParent().isMetadataComplete()) {
			JavaPersistentType javaPersistentType = getParent().getPersistentType().getJavaPersistentType();
			if (javaPersistentType != null && javaPersistentType.getMapping() instanceof CacheableHolder2_0) {
				return ((CacheableHolder2_0) javaPersistentType.getMapping()).getCacheable().isCacheable();
			}
		}
		CacheableHolder2_0 cacheableHolder = getCacheableSuperPersistentType(getParent().getPersistentType());
		if (cacheableHolder != null) {
			return cacheableHolder.getCacheable().isCacheable();
		}
		return ((PersistenceUnit2_0) getPersistenceUnit()).calculateDefaultCacheable();
	}
	
	protected CacheableHolder2_0 getCacheableSuperPersistentType(PersistentType persistentType) {
		PersistentType superPersistentType = persistentType.getSuperPersistentType();
		if (superPersistentType != null) {
			if (superPersistentType.getMapping() instanceof CacheableHolder2_0) {
				return (CacheableHolder2_0) superPersistentType.getMapping();
			}
			return getCacheableSuperPersistentType(superPersistentType);
		}
		return null;
	}
	
	// **************** initialize/update **************************************

	protected void initializeExpiry(XmlCache resourceCache) {
		if (resourceCache == null) {
			return;
		}
		if (resourceCache.getExpiryTimeOfDay() == null) {
			this.expiry = resourceCache.getExpiry();
		}
		else {
			if (resourceCache.getExpiry() == null) { //handle with validation if both expiry and expiryTimeOfDay are set
				this.expiryTimeOfDay = new OrmEclipseLinkExpiryTimeOfDay(this);
				this.expiryTimeOfDay.initialize(resourceCache.getExpiryTimeOfDay());
			}
		}
	}

	public void update(JavaEclipseLinkCaching javaCaching) {
		XmlCache resourceCache = getResourceCache();
		setDefaultSize(this.defaultSize(javaCaching));
		setSpecifiedSize_(this.getResourceSize(resourceCache));
		setDefaultShared(this.defaultShared(javaCaching));
		setSpecifiedShared_(this.getResourceShared(resourceCache));
		setDefaultAlwaysRefresh(this.defaultAlwaysRefresh(javaCaching));
		setSpecifiedAlwaysRefresh_(this.getResourceAlwaysRefresh(resourceCache));
		setDefaultRefreshOnlyIfNewer(this.defaultRefreshOnlyIfNewer(javaCaching));
		setSpecifiedRefreshOnlyIfNewer_(this.getResourceRefreshOnlyIfNewer(resourceCache));
		setDefaultDisableHits(this.defaultDisableHits(javaCaching));
		setSpecifiedDisableHits_(this.getResourceDisableHits(resourceCache));
		setDefaultType(this.defaultType(javaCaching));
		setSpecifiedType_(this.getResourceType(resourceCache));
		setDefaultCoordinationType(this.defaultCoordinationType(javaCaching));
		setSpecifiedCoordinationType_(this.getResourceCoordinationType(resourceCache));
		setDefaultExistenceType(this.defaultExistenceType(javaCaching));
		setSpecifiedExistenceType_(this.getResourceExistenceChecking());
		this.updateExpiry(resourceCache);
		this.cacheable.update();
	}
	
	protected void updateExpiry(XmlCache resourceCache) {
		if (resourceCache == null) {
			setExpiryTimeOfDay(null);
			setExpiry_(null);
			return;
		}
		if (resourceCache.getExpiryTimeOfDay() == null) {
			setExpiryTimeOfDay(null);
			setExpiry_(resourceCache.getExpiry());
		}
		else {
			if (this.expiryTimeOfDay != null) {
				this.expiryTimeOfDay.update(resourceCache.getExpiryTimeOfDay());
			}
			else if (resourceCache.getExpiry() == null){
				setExpiryTimeOfDay(new OrmEclipseLinkExpiryTimeOfDay(this));
				this.expiryTimeOfDay.initialize(resourceCache.getExpiryTimeOfDay());
			}
			else { //handle with validation if both expiry and expiryTimeOfDay are set
				setExpiryTimeOfDay(null);
			}
		}
	}
	
	protected int defaultSize(JavaEclipseLinkCaching javaCaching) {
		return (javaCaching == null) ? DEFAULT_SIZE : javaCaching.getSize();
	}
	
	protected Integer getResourceSize(XmlCache resource) {
		return (resource == null) ? null : resource.getSize();
	}
	
	protected boolean defaultShared(JavaEclipseLinkCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_SHARED;
		}
		return getResourceCache() == null ? javaCaching.isShared() : DEFAULT_SHARED;
	}
	
	protected Boolean getResourceShared(XmlCache resource) {
		return (resource == null) ? null : resource.getShared();
	}
	
	protected boolean defaultAlwaysRefresh(JavaEclipseLinkCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_ALWAYS_REFRESH;
		}
		return getResourceCache() == null ? javaCaching.isAlwaysRefresh() : DEFAULT_ALWAYS_REFRESH;
	}
	
	protected Boolean getResourceAlwaysRefresh(XmlCache resource) {
		return (resource == null) ? null : resource.getAlwaysRefresh();
	}
	
	protected boolean defaultRefreshOnlyIfNewer(JavaEclipseLinkCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_REFRESH_ONLY_IF_NEWER;
		}
		return getResourceCache() == null ? javaCaching.isRefreshOnlyIfNewer() : DEFAULT_REFRESH_ONLY_IF_NEWER;
	}
	
	protected Boolean getResourceRefreshOnlyIfNewer(XmlCache resource) {
		return (resource == null) ? null : resource.getRefreshOnlyIfNewer();
	}
	
	protected boolean defaultDisableHits(JavaEclipseLinkCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_DISABLE_HITS;
		}
		return getResourceCache() == null ? javaCaching.isDisableHits() : DEFAULT_DISABLE_HITS;
	}
	
	protected Boolean getResourceDisableHits(XmlCache resource) {
		return (resource == null) ? null : resource.getDisableHits();
	}
	
	protected EclipseLinkCacheType defaultType(JavaEclipseLinkCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_TYPE;
		}
		return getResourceCache() == null ? javaCaching.getType() : DEFAULT_TYPE;
	}
	
	protected EclipseLinkCacheType getResourceType(XmlCache resource) {
		return (resource == null) ? null : EclipseLinkCacheType.fromOrmResourceModel(resource.getType());
	}
	
	protected EclipseLinkCacheCoordinationType defaultCoordinationType(JavaEclipseLinkCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_COORDINATION_TYPE;
		}
		return getResourceCache() == null ? javaCaching.getCoordinationType() : DEFAULT_COORDINATION_TYPE;
	}
	
	protected EclipseLinkCacheCoordinationType getResourceCoordinationType(XmlCache resource) {
		return (resource == null) ? null : EclipseLinkCacheCoordinationType.fromOrmResourceModel(resource.getCoordinationType());
	}
	
	protected EclipseLinkExistenceType defaultExistenceType(JavaEclipseLinkCaching javaCaching) {
		return (javaCaching == null) ? DEFAULT_EXISTENCE_TYPE : javaCaching.getExistenceType();
	}
	
	protected EclipseLinkExistenceType getResourceExistenceChecking() {
		return (this.resource == null) ? null : EclipseLinkExistenceType.fromOrmResourceModel(this.resource.getExistenceChecking());
	}
	
	protected Integer getResourceExpiry(XmlCache resource) {
		return (resource == null) ? null : resource.getExpiry();
	}
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		XmlCache resource = getResourceCache();
		return resource == null ? null : resource.getValidationTextRange();
	}

}
