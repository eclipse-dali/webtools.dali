/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.NullOrmCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheIsolationType2_2;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTimeOfDay;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCache;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlCacheHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTimeOfDay;

public class EclipseLinkOrmCachingImpl
	extends AbstractOrmXmlContextModel<EclipseLinkOrmNonEmbeddableTypeMapping>
	implements EclipseLinkCaching, OrmCacheableReference2_0
{
	/** This is present only if it should actually be used. */
	protected EclipseLinkJavaCaching javaCachingForDefaults;

	protected EclipseLinkCacheType specifiedType;
	protected EclipseLinkCacheType defaultType = DEFAULT_TYPE;
	protected EclipseLinkCacheType type = DEFAULT_TYPE;

	protected Integer specifiedSize;
	protected int defaultSize = DEFAULT_SIZE;
	protected int size = DEFAULT_SIZE;

	protected Boolean specifiedShared;
	protected boolean defaultShared = DEFAULT_SHARED;
	protected boolean shared = DEFAULT_SHARED;

	protected Boolean specifiedAlwaysRefresh;
	protected boolean defaultAlwaysRefresh = DEFAULT_ALWAYS_REFRESH;
	protected boolean alwaysRefresh = DEFAULT_ALWAYS_REFRESH;

	protected Boolean specifiedRefreshOnlyIfNewer;
	protected boolean defaultRefreshOnlyIfNewer = DEFAULT_REFRESH_ONLY_IF_NEWER;
	protected boolean refreshOnlyIfNewer = DEFAULT_REFRESH_ONLY_IF_NEWER;

	protected Boolean specifiedDisableHits;
	protected boolean defaultDisableHits = DEFAULT_DISABLE_HITS;
	protected boolean disableHits = DEFAULT_DISABLE_HITS;

	protected EclipseLinkCacheCoordinationType specifiedCoordinationType;
	protected EclipseLinkCacheCoordinationType defaultCoordinationType = DEFAULT_COORDINATION_TYPE;
	protected EclipseLinkCacheCoordinationType coordinationType = DEFAULT_COORDINATION_TYPE;

	protected Integer expiry;
	protected EclipseLinkOrmTimeOfDay expiryTimeOfDay;

	protected EclipseLinkExistenceType specifiedExistenceType;
	protected EclipseLinkExistenceType defaultExistenceType = DEFAULT_EXISTENCE_TYPE;
	protected EclipseLinkExistenceType existenceType = DEFAULT_EXISTENCE_TYPE;

	protected final Cacheable2_0 cacheable;

	protected EclipseLinkCacheIsolationType2_2 specifiedIsolation;
	protected EclipseLinkCacheIsolationType2_2 defaultIsolation = DEFAULT_ISOLATION;
	protected EclipseLinkCacheIsolationType2_2 isolation = DEFAULT_ISOLATION;


	public EclipseLinkOrmCachingImpl(EclipseLinkOrmNonEmbeddableTypeMapping parent) {
		super(parent);

		this.specifiedType = this.buildSpecifiedType();
		this.specifiedSize = this.buildSpecifiedSize();
		this.specifiedShared = this.buildSpecifiedShared();
		this.specifiedAlwaysRefresh = this.buildSpecifiedAlwaysRefresh();
		this.specifiedRefreshOnlyIfNewer = this.buildSpecifiedRefreshOnlyIfNewer();
		this.specifiedDisableHits = this.buildSpecifiedDisableHits();

		this.specifiedCoordinationType = this.buildSpecifiedCoordinationType();

		this.expiry = this.buildExpiry();
		this.expiryTimeOfDay = this.buildExpiryTimeOfDay();

		this.specifiedExistenceType = this.buildSpecifiedExistenceType();

		this.cacheable = this.buildCacheable();

		this.specifiedIsolation = this.buildSpecifiedIsolation();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);

		this.setSpecifiedType_(this.buildSpecifiedType());
		this.setSpecifiedSize_(this.buildSpecifiedSize());
		this.setSpecifiedShared_(this.buildSpecifiedShared());
		this.setSpecifiedAlwaysRefresh_(this.buildSpecifiedAlwaysRefresh());
		this.setSpecifiedRefreshOnlyIfNewer_(this.buildSpecifiedRefreshOnlyIfNewer());
		this.setSpecifiedDisableHits_(this.buildSpecifiedDisableHits());

		this.setSpecifiedCoordinationType_(this.buildSpecifiedCoordinationType());

		this.setExpiry_(this.buildExpiry());
		this.syncExpiryTimeOfDay(monitor);

		this.setSpecifiedExistenceType_(this.buildSpecifiedExistenceType());

		this.cacheable.synchronizeWithResourceModel(monitor);

		this.setSpecifiedIsolation_(this.buildSpecifiedIsolation());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.javaCachingForDefaults = this.buildJavaCachingForDefaults();

		this.setDefaultType(this.buildDefaultType());
		this.setType(this.buildType());

		this.setDefaultSize(this.buildDefaultSize());
		this.setSize(this.buildSize());

		this.setDefaultShared(this.buildDefaultShared());
		this.setShared(this.buildShared());

		this.setDefaultAlwaysRefresh(this.buildDefaultAlwaysRefresh());
		this.setAlwaysRefresh(this.buildAlwaysRefresh());

		this.setDefaultRefreshOnlyIfNewer(this.buildDefaultRefreshOnlyIfNewer());
		this.setRefreshOnlyIfNewer(this.buildRefreshOnlyIfNewer());

		this.setDefaultDisableHits(this.buildDefaultDisableHits());
		this.setDisableHits(this.buildDisableHits());

		this.setDefaultCoordinationType(this.buildDefaultCoordinationType());
		this.setCoordinationType(this.buildCoordinationType());

		if (this.expiryTimeOfDay != null) {
			this.expiryTimeOfDay.update(monitor);
		}

		// existence checking is its own xml attribute, it is not part of the cache xml element
		this.setDefaultExistenceType(this.buildDefaultExistenceType());
		this.setExistenceType(this.buildExistenceType());

		this.cacheable.update(monitor);

		this.setDefaultIsolation(this.buildDefaultIsolation());
		this.setIsolation(this.buildIsolation());
	}


	// ********** type **********

	public EclipseLinkCacheType getType() {
		return this.type;
	}

	protected void setType(EclipseLinkCacheType type) {
		EclipseLinkCacheType old = this.type;
		this.firePropertyChanged(TYPE_PROPERTY, old, this.type = type);
	}

	protected EclipseLinkCacheType buildType() {
		return (this.specifiedType != null) ? this.specifiedType : this.defaultType;
	}

	public EclipseLinkCacheType getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(EclipseLinkCacheType type) {
		if (ObjectTools.notEquals(this.specifiedType, type)) {
			XmlCache xmlCache = this.getXmlCacheForUpdate();
			this.setSpecifiedType_(type);
			xmlCache.setType(EclipseLinkCacheType.toOrmResourceModel(type));
			this.removeXmlCacheIfUnset();
		}

		if (type != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedType_(EclipseLinkCacheType type) {
		EclipseLinkCacheType old = this.specifiedType;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, this.specifiedType = type);
	}

	protected EclipseLinkCacheType buildSpecifiedType() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : EclipseLinkCacheType.fromOrmResourceModel(xmlCache.getType());
	}

	public EclipseLinkCacheType getDefaultType() {
		return this.defaultType;
	}

	protected EclipseLinkCacheType buildDefaultType() {
		String puDefaultCacheTypeName = this.getPersistenceUnit().getDefaultCacheTypePropertyValue();
		if (StringTools.isNotBlank(puDefaultCacheTypeName)) {
			try { 
				return EclipseLinkCacheType.valueOf(StringTools.convertCamelCaseToAllCaps(puDefaultCacheTypeName));
			} catch (IllegalArgumentException exception) {
				// could not parse, fall through
			}
		}
		return (this.javaCachingForDefaults != null) ? this.javaCachingForDefaults.getType() : DEFAULT_TYPE;
	}

	protected void setDefaultType(EclipseLinkCacheType type) {
		EclipseLinkCacheType old = this.defaultType;
		this.firePropertyChanged(DEFAULT_TYPE_PROPERTY, old, this.defaultType = type);
	}


	// ********** size **********

	public int getSize() {
		return this.size;
	}

	protected void setSize(int size) {
		int old = this.size;
		this.firePropertyChanged(SIZE_PROPERTY, old, this.size = size);
	}

	protected int buildSize() {
		return (this.specifiedSize != null) ? this.specifiedSize.intValue() : this.defaultSize;
	}

	public Integer getSpecifiedSize() {
		return this.specifiedSize;
	}

	public void setSpecifiedSize(Integer size) {
		if (ObjectTools.notEquals(this.specifiedSize, size)) {
			XmlCache xmlCache = this.getXmlCacheForUpdate();
			this.setSpecifiedSize_(size);
			xmlCache.setSize(size);
			this.removeXmlCacheIfUnset();
		}

		if (size != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedSize_(Integer size) {
		Integer old = this.specifiedSize;
		this.firePropertyChanged(SPECIFIED_SIZE_PROPERTY, old, this.specifiedSize = size);
	}

	protected Integer buildSpecifiedSize() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getSize();
	}

	public int getDefaultSize() {
		return this.defaultSize;
	}

	protected int buildDefaultSize() {
		String puDefaultCacheSize = this.getPersistenceUnit().getDefaultCacheSizePropertyValue();
		if (StringTools.isNotBlank(puDefaultCacheSize)) {
			try {
				return Integer.valueOf(puDefaultCacheSize).intValue();
			} catch (NumberFormatException exception) {
				// could not parse, fall through
			}
		}
		return (this.javaCachingForDefaults != null) ? this.javaCachingForDefaults.getSize() : DEFAULT_SIZE;
	}

	protected void setDefaultSize(int size) {
		int old = this.defaultSize;
		this.firePropertyChanged(DEFAULT_SIZE_PROPERTY, old, this.defaultSize = size);
	}


	// ********** shared **********

	public boolean isShared() {
		return this.shared;
	}

	protected void setShared(boolean shared) {
		boolean old = this.shared;
		this.firePropertyChanged(SHARED_PROPERTY, old, this.shared = shared);
	}

	protected boolean buildShared() {
		return (this.specifiedShared != null) ? this.specifiedShared.booleanValue() : this.defaultShared;
	}

	public Boolean getSpecifiedShared() {
		return this.specifiedShared;
	}

	public void setSpecifiedShared(Boolean shared) {
		if (ObjectTools.notEquals(this.specifiedShared, shared)) {
			XmlCache xmlCache = this.getXmlCacheForUpdate();
			this.setSpecifiedShared_(shared);
			xmlCache.setShared(shared);
			this.removeXmlCacheIfUnset();
		}

		if ((shared != null) && ! shared.booleanValue()) {  // Boolean.FALSE
			this.setSpecifiedType(null);
			this.setSpecifiedSize(null);
			this.setSpecifiedAlwaysRefresh(null);
			this.setSpecifiedRefreshOnlyIfNewer(null);
			this.setSpecifiedDisableHits(null);
			this.setSpecifiedCoordinationType(null);
			this.setExpiry(null);
			this.removeExpiryTimeOfDayIfNecessary();
		}
	}

	protected void setSpecifiedShared_(Boolean shared) {
		Boolean old = this.specifiedShared;
		this.specifiedShared = shared;
		this.firePropertyChanged(SPECIFIED_SHARED_PROPERTY, old, shared);
	}

	protected Boolean buildSpecifiedShared() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getShared();
	}

	public boolean getDefaultShared() {
		return this.defaultShared;
	}

	protected boolean buildDefaultShared() {
		String puDefaultSharedCache = this.getPersistenceUnit().getDefaultCacheSharedPropertyValue();
		if (StringTools.isNotBlank(puDefaultSharedCache)) {
			return Boolean.valueOf(puDefaultSharedCache).booleanValue();
		}
		return (this.javaCachingForDefaults != null) ? this.javaCachingForDefaults.isShared() : DEFAULT_SHARED;
	}

	protected void setDefaultShared(boolean shared) {
		boolean old = this.defaultShared;
		this.firePropertyChanged(DEFAULT_SHARED_PROPERTY, old, this.defaultShared = shared);
	}


	// ********** always refresh **********

	public boolean isAlwaysRefresh() {
		return this.alwaysRefresh;
	}

	protected void setAlwaysRefresh(boolean alwaysRefresh) {
		boolean old = this.alwaysRefresh;
		this.firePropertyChanged(ALWAYS_REFRESH_PROPERTY, old, this.alwaysRefresh = alwaysRefresh);
	}

	protected boolean buildAlwaysRefresh() {
		return (this.specifiedAlwaysRefresh != null) ? this.specifiedAlwaysRefresh.booleanValue() : this.defaultAlwaysRefresh;
	}

	public Boolean getSpecifiedAlwaysRefresh() {
		return this.specifiedAlwaysRefresh;
	}

	public void setSpecifiedAlwaysRefresh(Boolean alwaysRefresh) {
		if (ObjectTools.notEquals(this.specifiedAlwaysRefresh, alwaysRefresh)) {
			XmlCache xmlCache = this.getXmlCacheForUpdate();
			this.setSpecifiedAlwaysRefresh_(alwaysRefresh);
			xmlCache.setAlwaysRefresh(alwaysRefresh);
			this.removeXmlCacheIfUnset();
		}

		if (alwaysRefresh != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedAlwaysRefresh_(Boolean alwaysRefresh) {
		Boolean old = this.specifiedAlwaysRefresh;
		this.firePropertyChanged(SPECIFIED_ALWAYS_REFRESH_PROPERTY, old, this.specifiedAlwaysRefresh = alwaysRefresh);
	}

	protected Boolean buildSpecifiedAlwaysRefresh() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getAlwaysRefresh();
	}

	public boolean getDefaultAlwaysRefresh() {
		return this.defaultAlwaysRefresh;
	}

	protected void setDefaultAlwaysRefresh(boolean alwaysRefresh) {
		boolean old = this.defaultAlwaysRefresh;
		this.firePropertyChanged(DEFAULT_ALWAYS_REFRESH_PROPERTY, old, this.defaultAlwaysRefresh = alwaysRefresh);
	}

	protected boolean buildDefaultAlwaysRefresh() {
		return (this.javaCachingForDefaults != null) ? this.javaCachingForDefaults.isAlwaysRefresh() : DEFAULT_ALWAYS_REFRESH;
	}


	// ********** refresh only if newer **********

	public boolean isRefreshOnlyIfNewer() {
		return this.refreshOnlyIfNewer;
	}

	protected void setRefreshOnlyIfNewer(boolean refreshOnlyIfNewer) {
		boolean old = this.refreshOnlyIfNewer;
		this.firePropertyChanged(REFRESH_ONLY_IF_NEWER_PROPERTY, old, this.refreshOnlyIfNewer = refreshOnlyIfNewer);
	}

	protected boolean buildRefreshOnlyIfNewer() {
		return (this.specifiedRefreshOnlyIfNewer != null) ? this.specifiedRefreshOnlyIfNewer.booleanValue() : this.defaultRefreshOnlyIfNewer;
	}

	public Boolean getSpecifiedRefreshOnlyIfNewer() {
		return this.specifiedRefreshOnlyIfNewer;
	}

	public void setSpecifiedRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer) {
		if (ObjectTools.notEquals(this.specifiedRefreshOnlyIfNewer, refreshOnlyIfNewer)) {
			XmlCache xmlCache = this.getXmlCacheForUpdate();
			this.setSpecifiedRefreshOnlyIfNewer_(refreshOnlyIfNewer);
			xmlCache.setRefreshOnlyIfNewer(refreshOnlyIfNewer);
			this.removeXmlCacheIfUnset();
		}

		if (refreshOnlyIfNewer != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedRefreshOnlyIfNewer_(Boolean refreshOnlyIfNewer) {
		Boolean old = this.specifiedRefreshOnlyIfNewer;
		this.firePropertyChanged(SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY, old, this.specifiedRefreshOnlyIfNewer = refreshOnlyIfNewer);
	}

	protected Boolean buildSpecifiedRefreshOnlyIfNewer() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getRefreshOnlyIfNewer();
	}

	public boolean getDefaultRefreshOnlyIfNewer() {
		return this.defaultRefreshOnlyIfNewer;
	}

	protected void setDefaultRefreshOnlyIfNewer(boolean refreshOnlyIfNewer) {
		boolean old = this.defaultRefreshOnlyIfNewer;
		this.firePropertyChanged(DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY, old, this.defaultRefreshOnlyIfNewer = refreshOnlyIfNewer);
	}

	protected boolean buildDefaultRefreshOnlyIfNewer() {
		return (this.javaCachingForDefaults != null) ? this.javaCachingForDefaults.isRefreshOnlyIfNewer() : DEFAULT_REFRESH_ONLY_IF_NEWER;
	}


	// ********** disable hits **********

	public boolean isDisableHits() {
		return this.disableHits;
	}

	protected void setDisableHits(boolean disableHits) {
		boolean old = this.disableHits;
		this.firePropertyChanged(DISABLE_HITS_PROPERTY, old, this.disableHits = disableHits);
	}

	protected boolean buildDisableHits() {
		return (this.specifiedDisableHits != null) ? this.specifiedDisableHits.booleanValue() : this.defaultDisableHits;
	}

	public Boolean getSpecifiedDisableHits() {
		return this.specifiedDisableHits;
	}

	public void setSpecifiedDisableHits(Boolean disableHits) {
		if (ObjectTools.notEquals(this.specifiedDisableHits, disableHits)) {
			XmlCache xmlCache = this.getXmlCacheForUpdate();
			this.setSpecifiedDisableHits_(disableHits);
			xmlCache.setDisableHits(disableHits);
			this.removeXmlCacheIfUnset();
		}

		if (disableHits != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedDisableHits_(Boolean disableHits) {
		Boolean old = this.specifiedDisableHits;
		this.firePropertyChanged(SPECIFIED_DISABLE_HITS_PROPERTY, old, this.specifiedDisableHits = disableHits);
	}

	protected Boolean buildSpecifiedDisableHits() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getDisableHits();
	}

	public boolean getDefaultDisableHits() {
		return this.defaultDisableHits;
	}

	protected void setDefaultDisableHits(boolean disableHits) {
		boolean old = this.defaultDisableHits;
		this.firePropertyChanged(DEFAULT_DISABLE_HITS_PROPERTY, old, this.defaultDisableHits = disableHits);
	}

	protected boolean buildDefaultDisableHits() {
		return (this.javaCachingForDefaults != null) ? this.javaCachingForDefaults.isDisableHits() : DEFAULT_DISABLE_HITS;
	}


	// ********** coordination type **********

	public EclipseLinkCacheCoordinationType getCoordinationType() {
		return this.coordinationType;
	}

	protected void setCoordinationType(EclipseLinkCacheCoordinationType type) {
		EclipseLinkCacheCoordinationType old = this.coordinationType;
		this.firePropertyChanged(COORDINATION_TYPE_PROPERTY, old, this.coordinationType = type);
	}

	protected EclipseLinkCacheCoordinationType buildCoordinationType() {
		return (this.specifiedCoordinationType != null) ? this.specifiedCoordinationType : this.defaultCoordinationType;
	}

	public EclipseLinkCacheCoordinationType getSpecifiedCoordinationType() {
		return this.specifiedCoordinationType;
	}

	public void setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType type) {
		if (ObjectTools.notEquals(this.specifiedCoordinationType, type)) {
			XmlCache xmlCache = this.getXmlCacheForUpdate();
			this.setSpecifiedCoordinationType_(type);
			xmlCache.setCoordinationType(EclipseLinkCacheCoordinationType.toOrmResourceModel(type));
			this.removeXmlCacheIfUnset();
		}

		if (type != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedCoordinationType_(EclipseLinkCacheCoordinationType type) {
		EclipseLinkCacheCoordinationType old = this.specifiedCoordinationType;
		this.firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, old, this.specifiedCoordinationType = type);
	}

	protected EclipseLinkCacheCoordinationType buildSpecifiedCoordinationType() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : EclipseLinkCacheCoordinationType.fromOrmResourceModel(xmlCache.getCoordinationType());
	}

	public EclipseLinkCacheCoordinationType getDefaultCoordinationType() {
		return this.defaultCoordinationType;
	}

	protected void setDefaultCoordinationType(EclipseLinkCacheCoordinationType type) {
		EclipseLinkCacheCoordinationType old= this.defaultCoordinationType;
		this.firePropertyChanged(DEFAULT_COORDINATION_TYPE_PROPERTY, old, this.defaultCoordinationType = type);
	}

	protected EclipseLinkCacheCoordinationType buildDefaultCoordinationType() {
		return (this.javaCachingForDefaults != null) ? this.javaCachingForDefaults.getCoordinationType() : DEFAULT_COORDINATION_TYPE;
	}


	// ********** expiry **********

	public Integer getExpiry() {
		return this.expiry;
	}

	public void setExpiry(Integer expiry) {
		if (ObjectTools.notEquals(this.expiry, expiry)) {
			XmlCache xmlCache = this.getXmlCacheForUpdate();
			this.setExpiry_(expiry);
			xmlCache.setExpiry(expiry);
			this.removeXmlCacheIfUnset();
		}

		if (expiry != null) {
			this.removeExpiryTimeOfDayIfNecessary();
			this.setSpecifiedShared(null);
		}
	}

	protected void setExpiry_(Integer expiry) {
		Integer old = this.expiry;
		this.firePropertyChanged(EXPIRY_PROPERTY, old, this.expiry = expiry);
	}

	protected Integer buildExpiry() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getExpiry();
	}


	// ********** expiry time of day **********

	public EclipseLinkTimeOfDay getExpiryTimeOfDay() {
		return this.expiryTimeOfDay;
	}

	public EclipseLinkTimeOfDay addExpiryTimeOfDay() {
		if (this.expiryTimeOfDay != null) {
			throw new IllegalStateException("expiry time of day already exists: " + this.expiryTimeOfDay); //$NON-NLS-1$
		}

		XmlCache xmlCache = this.getXmlCacheForUpdate();
		XmlTimeOfDay xmlTimeOfDay = this.buildXmlTimeOfDay();
		EclipseLinkOrmTimeOfDay timeOfDay = this.buildExpiryTimeOfDay(xmlTimeOfDay);
		this.setExpiryTimeOfDay(timeOfDay);
		xmlCache.setExpiryTimeOfDay(xmlTimeOfDay);

		this.setExpiry(null);
		this.setSpecifiedShared(null);

		return timeOfDay;
	}

	protected XmlTimeOfDay buildXmlTimeOfDay() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTimeOfDay();
	}

	public void removeExpiryTimeOfDay() {
		if (this.expiryTimeOfDay == null) {
			throw new IllegalStateException("expiry time of day does not exist"); //$NON-NLS-1$
		}
		this.removeExpiryTimeOfDay_();
	}

	protected void removeExpiryTimeOfDayIfNecessary() {
		if (this.expiryTimeOfDay != null) {
			this.removeExpiryTimeOfDay_();
		}
	}

	protected void removeExpiryTimeOfDay_() {
		this.setExpiryTimeOfDay(null);
		this.getXmlCache().setExpiryTimeOfDay(null);
		this.removeXmlCacheIfUnset();
	}

	protected void setExpiryTimeOfDay(EclipseLinkOrmTimeOfDay timeOfDay) {
		EclipseLinkOrmTimeOfDay old = this.expiryTimeOfDay;
		this.firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, old, this.expiryTimeOfDay = timeOfDay);
	}

	protected void syncExpiryTimeOfDay(IProgressMonitor monitor) {
		XmlTimeOfDay xmlTimeOfDay = this.getXmlExpiryTimeOfDay();
		if (xmlTimeOfDay == null) {
			if (this.expiryTimeOfDay != null) {
				this.setExpiryTimeOfDay(null);
			}
		} else {
			if ((this.expiryTimeOfDay != null) && (this.expiryTimeOfDay.getXmlTimeOfDay() == xmlTimeOfDay)) {
				this.expiryTimeOfDay.synchronizeWithResourceModel(monitor);
			} else {
				this.setExpiryTimeOfDay(this.buildExpiryTimeOfDay(xmlTimeOfDay));
			}
		}
	}

	protected EclipseLinkOrmTimeOfDay buildExpiryTimeOfDay() {
		return this.buildExpiryTimeOfDay(this.getXmlExpiryTimeOfDay());
	}

	protected XmlTimeOfDay getXmlExpiryTimeOfDay() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getExpiryTimeOfDay();
	}

	protected EclipseLinkOrmTimeOfDay buildExpiryTimeOfDay(XmlTimeOfDay xmlTimeOfDay) {
		return (xmlTimeOfDay == null) ? null : new EclipseLinkOrmTimeOfDay(this, xmlTimeOfDay);
	}


	// ********** existence type **********

	public EclipseLinkExistenceType getExistenceType() {
		return this.existenceType;
	}

	protected void setExistenceType(EclipseLinkExistenceType type) {
		EclipseLinkExistenceType old = this.existenceType;
		this.firePropertyChanged(EXISTENCE_TYPE_PROPERTY, old, this.existenceType = type);
	}

	protected EclipseLinkExistenceType buildExistenceType() {
		return (this.specifiedExistenceType != null) ? this.specifiedExistenceType : this.defaultExistenceType;
	}

	public EclipseLinkExistenceType getSpecifiedExistenceType() {
		return this.specifiedExistenceType;
	}

	public void setSpecifiedExistenceType(EclipseLinkExistenceType type) {
		if (this.setSpecifiedExistenceType_(type)) {
			this.getXmlCacheHolder().setExistenceChecking(EclipseLinkExistenceType.toOrmResourceModel(type));
		}
	}

	protected boolean setSpecifiedExistenceType_(EclipseLinkExistenceType type) {
		EclipseLinkExistenceType old = this.specifiedExistenceType;
		return this.firePropertyChanged(SPECIFIED_EXISTENCE_TYPE_PROPERTY, old, this.specifiedExistenceType = type);
	}

	protected EclipseLinkExistenceType buildSpecifiedExistenceType() {
		XmlCacheHolder xmlCacheHolder = this.getXmlCacheHolder();
		return (xmlCacheHolder == null) ? null : EclipseLinkExistenceType.fromOrmResourceModel(xmlCacheHolder.getExistenceChecking());
	}

	public EclipseLinkExistenceType getDefaultExistenceType() {
		return this.defaultExistenceType;
	}

	protected void setDefaultExistenceType(EclipseLinkExistenceType type) {
		EclipseLinkExistenceType old = this.defaultExistenceType;
		this.firePropertyChanged(DEFAULT_EXISTENCE_TYPE_PROPERTY, old, this.defaultExistenceType = type);
	}

	protected EclipseLinkExistenceType buildDefaultExistenceType() {
		return (this.javaCachingForDefaults != null) ? this.javaCachingForDefaults.getExistenceType() : DEFAULT_EXISTENCE_TYPE;
	}


	// ********** cacheable **********

	public Cacheable2_0 getCacheable() {
		return this.cacheable;
	}

	protected Cacheable2_0 buildCacheable() {
		return this.isOrmXml2_0Compatible() ?
				this.getContextModelFactory2_0().buildOrmCacheable(this) :
				new NullOrmCacheable2_0(this);
	}

	public boolean calculateDefaultCacheable() {
		if ( ! this.getTypeMapping().isMetadataComplete()) {
			JavaPersistentType javaPersistentType = this.getTypeMapping().getPersistentType().getJavaPersistentType();
			if ((javaPersistentType != null) && (javaPersistentType.getMapping() instanceof CacheableReference2_0)) {
				return ((CacheableReference2_0) javaPersistentType.getMapping()).getCacheable().isCacheable();
			}
		}
		CacheableReference2_0 superCacheableHolder = this.getCacheableSuperPersistentType(this.getTypeMapping().getPersistentType());
		return (superCacheableHolder != null) ?
				superCacheableHolder.getCacheable().isCacheable() :
				((PersistenceUnit2_0) this.getPersistenceUnit()).calculateDefaultCacheable();
	}

	protected CacheableReference2_0 getCacheableSuperPersistentType(PersistentType persistentType) {
		PersistentType superPersistentType = persistentType.getSuperPersistentType();
		if (superPersistentType == null) {
			return null;
		}
		TypeMapping superMapping = superPersistentType.getMapping();
		return (superMapping instanceof CacheableReference2_0) ?
				(CacheableReference2_0) superMapping :
				this.getCacheableSuperPersistentType(superPersistentType);  // recurse
	}


	// ********** isolation **********

	public EclipseLinkCacheIsolationType2_2 getIsolation() {
		return this.isolation;
	}

	protected void setIsolation(EclipseLinkCacheIsolationType2_2 isolation) {
		EclipseLinkCacheIsolationType2_2 old = this.isolation;
		this.firePropertyChanged(ISOLATION_PROPERTY, old, this.isolation = isolation);
	}

	protected EclipseLinkCacheIsolationType2_2 buildIsolation() {
		return (this.specifiedIsolation != null) ? this.specifiedIsolation : this.defaultIsolation;
	}

	public EclipseLinkCacheIsolationType2_2 getSpecifiedIsolation() {
		return this.specifiedIsolation;
	}

	public void setSpecifiedIsolation(EclipseLinkCacheIsolationType2_2 isolation) {
		if (ObjectTools.notEquals(this.specifiedIsolation, isolation)) {
			XmlCache xmlCache = this.getXmlCacheForUpdate();
			this.setSpecifiedIsolation_(isolation);
			xmlCache.setIsolation(EclipseLinkCacheIsolationType2_2.toOrmResourceModel(isolation));
			this.removeXmlCacheIfUnset();
		}

		if (isolation != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedIsolation_(EclipseLinkCacheIsolationType2_2 isolation) {
		EclipseLinkCacheIsolationType2_2 old = this.specifiedIsolation;
		this.firePropertyChanged(SPECIFIED_ISOLATION_PROPERTY, old, this.specifiedIsolation = isolation);
	}

	protected EclipseLinkCacheIsolationType2_2 buildSpecifiedIsolation() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : EclipseLinkCacheIsolationType2_2.fromOrmResourceModel(xmlCache.getIsolation());
	}

	public EclipseLinkCacheIsolationType2_2 getDefaultIsolation() {
		return this.defaultIsolation;
	}

	protected void setDefaultIsolation(EclipseLinkCacheIsolationType2_2 isolation) {
		EclipseLinkCacheIsolationType2_2 old = this.defaultIsolation;
		this.firePropertyChanged(DEFAULT_ISOLATION_PROPERTY, old, this.defaultIsolation = isolation);
	}

	protected EclipseLinkCacheIsolationType2_2 buildDefaultIsolation() {
		return (this.javaCachingForDefaults != null) ? this.javaCachingForDefaults.getIsolation() : DEFAULT_ISOLATION;
	}


	// ********** XML cache **********

	/**
	 * Return <code>null</code> if the XML cache does not exists.
	 */
	protected XmlCache getXmlCache() {
		return this.getXmlCacheHolder().getCache();
	}

	/**
	 * Build the XML cache if it does not exist.
	 */
	protected XmlCache getXmlCacheForUpdate() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache != null) ? xmlCache : this.buildXmlCache();
	}

	protected XmlCache buildXmlCache() {
		XmlCache xmlCache = EclipseLinkOrmFactory.eINSTANCE.createXmlCache();
		this.getXmlCacheHolder().setCache(xmlCache);
		return xmlCache;
	}

	protected void removeXmlCacheIfUnset() {
		if (this.getXmlCache().isUnset()) {
			this.removeXmlCache();
		}
	}

	protected void removeXmlCache() {
		this.getXmlCacheHolder().setCache(null);
	}


	// ********** misc **********

	protected EclipseLinkOrmNonEmbeddableTypeMapping getTypeMapping() {
		return this.parent;
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	protected XmlTypeMapping getXmlTypeMapping() {
		return this.getTypeMapping().getXmlTypeMapping();
	}

	public XmlCacheable_2_0 getXmlCacheable() {
		return (XmlCacheable_2_0) this.getXmlTypeMapping();
	}

	protected XmlCacheHolder getXmlCacheHolder() {
		return (XmlCacheHolder) this.getXmlTypeMapping();
	}

	protected EclipseLinkJavaCaching buildJavaCachingForDefaults() {
		if (this.getXmlCache() != null) {
			return null; // ignore Java if the XML is present
		}
		EclipseLinkJavaNonEmbeddableTypeMapping javaTypeMapping = this.getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : javaTypeMapping.getCaching();
	}

	protected EclipseLinkJavaNonEmbeddableTypeMapping getJavaTypeMappingForDefaults() {
		return this.getTypeMapping().getJavaTypeMappingForDefaults();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getValidationTextRange();
	}
}
