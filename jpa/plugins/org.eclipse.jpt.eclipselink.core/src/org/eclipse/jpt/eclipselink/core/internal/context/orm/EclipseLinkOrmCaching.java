/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.persistence.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.CacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.context.CacheType;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.core.context.ExistenceType;
import org.eclipse.jpt.eclipselink.core.context.ExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCaching;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder;

public class EclipseLinkOrmCaching extends AbstractXmlContextNode
	implements Caching
{
	protected XmlCacheHolder resource;
	
	protected int defaultSize;
	protected Integer specifiedSize;
	
	protected boolean defaultShared;
	protected Boolean specifiedShared;
	
	protected CacheType defaultType;
	protected CacheType specifiedType;
	
	protected boolean defaultAlwaysRefresh;
	protected Boolean specifiedAlwaysRefresh;
	
	protected boolean defaultRefreshOnlyIfNewer;
	protected Boolean specifiedRefreshOnlyIfNewer;
	
	protected boolean defaultDisableHits;
	protected Boolean specifiedDisableHits;
	
	protected CacheCoordinationType defaultCoordinationType;
	protected CacheCoordinationType specifiedCoordinationType;
	
	protected ExistenceType specifiedExistenceType;
	protected ExistenceType defaultExistenceType;
	
	
	public EclipseLinkOrmCaching(OrmTypeMapping parent) {
		super(parent);
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
				if (this.getResourceCache().isAllFeaturesUnset()) {
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
				if (this.getResourceCache().isAllFeaturesUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedShared != null) {
				addResourceCache();
				getResourceCache().setShared(newSpecifiedShared);
			}
		}
		firePropertyChanged(SPECIFIED_SHARED_PROPERTY, oldSpecifiedShared, newSpecifiedShared);		
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
				if (this.getResourceCache().isAllFeaturesUnset()) {
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
				if (this.getResourceCache().isAllFeaturesUnset()) {
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
				if (this.getResourceCache().isAllFeaturesUnset()) {
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
	
	public CacheType getType() {
		return (this.specifiedType == null) ? this.defaultType : this.specifiedType;
	}

	public CacheType getDefaultType() {
		return this.defaultType;
	}
	
	protected void setDefaultType(CacheType newDefaultType) {
		CacheType oldDefaultType= this.defaultType;
		this.defaultType = newDefaultType;
		firePropertyChanged(DEFAULT_TYPE_PROPERTY, oldDefaultType, newDefaultType);				
	}
	
	public CacheType getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(CacheType newSpecifiedType) {
		CacheType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		if (oldSpecifiedType != newSpecifiedType) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setType(CacheType.toOrmResourceModel(newSpecifiedType));						
				if (this.getResourceCache().isAllFeaturesUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedType != null) {
				addResourceCache();
				getResourceCache().setType(CacheType.toOrmResourceModel(newSpecifiedType));
			}
		}
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);		
	}
	
	protected void setSpecifiedType_(CacheType newSpecifiedType) {
		CacheType oldSpecifiedType= this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);		
	}
	
	
	public CacheCoordinationType getCoordinationType() {
		return (this.specifiedCoordinationType == null) ? this.defaultCoordinationType : this.specifiedCoordinationType;
	}
	
	public CacheCoordinationType getDefaultCoordinationType() {
		return this.defaultCoordinationType;
	}
	
	protected void setDefaultCoordinationType(CacheCoordinationType newDefaultcCoordinationType) {
		CacheCoordinationType oldDefaultcCoordinationType= this.defaultCoordinationType;
		this.defaultCoordinationType = newDefaultcCoordinationType;
		firePropertyChanged(DEFAULT_COORDINATION_TYPE_PROPERTY, oldDefaultcCoordinationType, newDefaultcCoordinationType);				
	}
	
	public CacheCoordinationType getSpecifiedCoordinationType() {
		return this.specifiedCoordinationType;
	}
	
	public void setSpecifiedCoordinationType(CacheCoordinationType newSpecifiedCoordinationType) {
		CacheCoordinationType oldSpecifiedCoordinationType = this.specifiedCoordinationType;
		this.specifiedCoordinationType = newSpecifiedCoordinationType;
		if (oldSpecifiedCoordinationType != newSpecifiedCoordinationType) {
			if (this.getResourceCache() != null) {
				this.getResourceCache().setCoordinationType(CacheCoordinationType.toOrmResourceModel(newSpecifiedCoordinationType));						
				if (this.getResourceCache().isAllFeaturesUnset()) {
					removeResourceCache();
				}
			}
			else if (newSpecifiedCoordinationType != null) {
				addResourceCache();
				getResourceCache().setCoordinationType(CacheCoordinationType.toOrmResourceModel(newSpecifiedCoordinationType));
			}
		}
		firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, oldSpecifiedCoordinationType, newSpecifiedCoordinationType);		
	}
	
	protected void setSpecifiedCoordinationType_(CacheCoordinationType newSpecifiedCoordinationType) {
		CacheCoordinationType oldSpecifiedCoordinationType = this.specifiedCoordinationType;
		this.specifiedCoordinationType = newSpecifiedCoordinationType;
		firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, oldSpecifiedCoordinationType, newSpecifiedCoordinationType);		
	}
	
	public ExistenceType getExistenceType() {
		return (this.specifiedExistenceType == null) ? this.defaultExistenceType : this.specifiedExistenceType;
	}
	
	public ExistenceType getDefaultExistenceType() {
		return this.defaultExistenceType;
	}
	
	public ExistenceType getSpecifiedExistenceType() {
		return this.specifiedExistenceType;
	}
	
	public void setSpecifiedExistenceType(ExistenceType newSpecifiedExistenceType) {
//		ExistenceType oldSpecifiedExistenceType = this.specifiedExistenceType;
//		this.specifiedExistenceType = newSpecifiedExistenceType;
//		if (oldSpecifiedExistenceType != newSpecifiedExistenceType) {
//			if (this.getResourceExistenceChecking() != null) {
//				this.getResourceExistenceChecking().set(ExistenceType.toOrmResourceModel(newSpecifiedExistenceType));						
//				if (this.getResourceExistenceChecking().isAllFeaturesUnset()) {
//					removeResourceExistenceChecking();
//				}
//			}
//			else if (newSpecifiedExistenceType != null) {
//				addResourceExistenceChecking();
//				getResourceExistenceChecking().setExistenceType(ExistenceType.toOrmResourceModel(newSpecifiedExistenceType));
//			}
//		}
//		firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, oldSpecifiedExistenceType, newSpecifiedExistenceType);		
		
	}
	
	public Integer getExpiry() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setExpiry(Integer expiry) {
		// TODO Auto-generated method stub
		
	}
	
	public ExpiryTimeOfDay getExpiryTimeOfDay() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ExpiryTimeOfDay addExpiryTimeOfDay() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void removeExpiryTimeOfDay() {
		// TODO Auto-generated method stub
		
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
	
//	protected XmlExistenceChecking getResourceExistenceChecking() {
//		return this.resource.getExistenceChecking();
//	}
//	
//	protected void addResourceExistenceChecking() {
//		this.resource.setExistenceChecking(EclipseLinkOrmFactory.eINSTANCE.createXmlExistenceChecking());		
//	}
//	
//	protected void removeResourceExistenceChecking() {
//		this.resource.setExistenceChecking(null);
//	}
	
	// **************** initialize/update **************************************
	
	protected void initialize(XmlCacheHolder resource, JavaCaching javaCaching) {
		this.resource = resource;
		XmlCache resourceCache = getResourceCache();
		this.defaultSize = this.defaultSize(javaCaching);
		this.specifiedSize = specifiedSize(resourceCache);
		this.defaultShared = this.defaultShared(javaCaching);
		this.specifiedShared = this.specifiedShared(resourceCache);
		this.defaultAlwaysRefresh = this.defaultAlwaysRefresh(javaCaching);
		this.specifiedAlwaysRefresh = this.specifiedAlwaysRefresh(resourceCache);
		this.defaultRefreshOnlyIfNewer = this.defaultRefreshOnlyIfNewer(javaCaching);
		this.specifiedRefreshOnlyIfNewer = this.specifiedRefreshOnlyIfNewer(resourceCache);
		this.defaultDisableHits = this.defaultDisableHits(javaCaching);
		this.specifiedDisableHits = this.specifiedDisableHits(resourceCache);
		this.defaultType = this.defaultType(javaCaching);
		this.specifiedType = this.specifiedType(resourceCache);
		this.defaultCoordinationType = this.defaultCoordinationType(javaCaching);
		this.specifiedCoordinationType = this.specifiedCoordinationType(resourceCache);
//		this.defaultExistenceType = this.defaultExistenceType(javaCaching);
//		this.specifiedExistenceType = this.specifiedExistenceType(getResourceCache());
	}
	
	protected void update(XmlCacheHolder resource, JavaCaching javaCaching) {
		this.resource = resource;
		XmlCache resourceCache = getResourceCache();
		setDefaultSize(this.defaultSize(javaCaching));
		setSpecifiedSize_(this.specifiedSize(resourceCache));
		setDefaultShared(this.defaultShared(javaCaching));
		setSpecifiedShared_(this.specifiedShared(resourceCache));
		setDefaultAlwaysRefresh(this.defaultAlwaysRefresh(javaCaching));
		setSpecifiedAlwaysRefresh_(this.specifiedAlwaysRefresh(resourceCache));
		setDefaultRefreshOnlyIfNewer(this.defaultRefreshOnlyIfNewer(javaCaching));
		setSpecifiedRefreshOnlyIfNewer_(this.specifiedRefreshOnlyIfNewer(resourceCache));
		setDefaultDisableHits(this.defaultDisableHits(javaCaching));
		setSpecifiedDisableHits_(this.specifiedDisableHits(resourceCache));
		setDefaultType(this.defaultType(javaCaching));
		setSpecifiedType_(this.specifiedType(resourceCache));
		setDefaultCoordinationType(this.defaultCoordinationType(javaCaching));
		setSpecifiedCoordinationType_(this.specifiedCoordinationType(resourceCache));
	}
	
	protected int defaultSize(JavaCaching javaCaching) {
		return (javaCaching == null) ? DEFAULT_SIZE : javaCaching.getSize();
	}
	
	protected Integer specifiedSize(XmlCache resource) {
		return (resource == null) ? null : resource.getSize();
	}
	
	protected boolean defaultShared(JavaCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_SHARED;
		}
		return getResourceCache() == null ? javaCaching.isShared() : DEFAULT_SHARED;
	}
	
	protected Boolean specifiedShared(XmlCache resource) {
		return (resource == null) ? null : resource.getShared();
	}
	
	protected boolean defaultAlwaysRefresh(JavaCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_ALWAYS_REFRESH;
		}
		return getResourceCache() == null ? javaCaching.isAlwaysRefresh() : DEFAULT_ALWAYS_REFRESH;
	}
	
	protected Boolean specifiedAlwaysRefresh(XmlCache resource) {
		return (resource == null) ? null : resource.getAlwaysRefresh();
	}
	
	protected boolean defaultRefreshOnlyIfNewer(JavaCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_REFRESH_ONLY_IF_NEWER;
		}
		return getResourceCache() == null ? javaCaching.isRefreshOnlyIfNewer() : DEFAULT_REFRESH_ONLY_IF_NEWER;
	}
	
	protected Boolean specifiedRefreshOnlyIfNewer(XmlCache resource) {
		return (resource == null) ? null : resource.getRefreshOnlyIfNewer();
	}
	
	protected boolean defaultDisableHits(JavaCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_DISABLE_HITS;
		}
		return getResourceCache() == null ? javaCaching.isDisableHits() : DEFAULT_DISABLE_HITS;
	}
	
	protected Boolean specifiedDisableHits(XmlCache resource) {
		return (resource == null) ? null : resource.getDisableHits();
	}
	
	protected CacheType defaultType(JavaCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_TYPE;
		}
		return getResourceCache() == null ? javaCaching.getType() : DEFAULT_TYPE;
	}
	
	protected CacheType specifiedType(XmlCache resource) {
		return (resource == null) ? null : CacheType.fromOrmResourceModel(resource.getType());
	}
	
	protected CacheCoordinationType defaultCoordinationType(JavaCaching javaCaching) {
		if (javaCaching == null) {
			return DEFAULT_COORDINATION_TYPE;
		}
		return getResourceCache() == null ? javaCaching.getCoordinationType() : DEFAULT_COORDINATION_TYPE;
	}
	
	protected CacheCoordinationType specifiedCoordinationType(XmlCache resource) {
		return (resource == null) ? null : CacheCoordinationType.fromOrmResourceModel(resource.getCoordinationType());
	}
	
	protected ExistenceType defaultExistenceType(JavaCaching javaCaching) {
		return (javaCaching == null) ? DEFAULT_EXISTENCE_TYPE : javaCaching.getExistenceType();
	}
//	
//	protected ExistenceType specifiedExistenceType(XmlCache resource) {
//		return (resource == null) ? null : resource.getExistenceType();
//	}
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		XmlCache resource = getResourceCache();
		return resource == null ? null : resource.getValidationTextRange();
	}

}
