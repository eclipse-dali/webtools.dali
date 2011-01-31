/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.jpa2.context.orm.NullOrmCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTimeOfDay;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCache;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTimeOfDay;

public class OrmEclipseLinkCachingImpl
	extends AbstractOrmXmlContextNode
	implements OrmEclipseLinkCaching, OrmCacheableHolder2_0
{
	protected EclipseLinkCacheType specifiedType;
	protected EclipseLinkCacheType defaultType;

	protected Integer specifiedSize;
	protected int defaultSize;

	protected Boolean specifiedShared;
	protected boolean defaultShared;

	protected Boolean specifiedAlwaysRefresh;
	protected boolean defaultAlwaysRefresh;

	protected Boolean specifiedRefreshOnlyIfNewer;
	protected boolean defaultRefreshOnlyIfNewer;

	protected Boolean specifiedDisableHits;
	protected boolean defaultDisableHits;

	protected EclipseLinkCacheCoordinationType specifiedCoordinationType;
	protected EclipseLinkCacheCoordinationType defaultCoordinationType;

	protected Integer expiry;
	protected EclipseLinkOrmTimeOfDay expiryTimeOfDay;

	protected EclipseLinkExistenceType specifiedExistenceType;
	protected EclipseLinkExistenceType defaultExistenceType;

	protected final OrmCacheable2_0 cacheable;


	public OrmEclipseLinkCachingImpl(OrmEclipseLinkNonEmbeddableTypeMapping parent) {
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
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();

		this.setSpecifiedType_(this.buildSpecifiedType());
		this.setSpecifiedSize_(this.buildSpecifiedSize());
		this.setSpecifiedShared_(this.buildSpecifiedShared());
		this.setSpecifiedAlwaysRefresh_(this.buildSpecifiedAlwaysRefresh());
		this.setSpecifiedRefreshOnlyIfNewer_(this.buildSpecifiedRefreshOnlyIfNewer());
		this.setSpecifiedDisableHits_(this.buildSpecifiedDisableHits());

		this.setSpecifiedCoordinationType_(this.buildSpecifiedCoordinationType());

		this.setExpiry_(this.buildExpiry());
		this.syncExpiryTimeOfDay();

		this.setSpecifiedExistenceType_(this.buildSpecifiedExistenceType());

		this.cacheable.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();

		boolean xmlCacheNotSpecified = (this.getXmlCache() == null);
		JavaEclipseLinkCaching javaCaching = this.getJavaCachingForDefaults();
		boolean javaCacheSpecified = (javaCaching != null);
		boolean useJavaValue = (xmlCacheNotSpecified && javaCacheSpecified);

		this.setDefaultType(useJavaValue ? javaCaching.getType() : DEFAULT_TYPE);
		this.setDefaultSize(useJavaValue ? javaCaching.getSize() : DEFAULT_SIZE);
		this.setDefaultShared(useJavaValue ? javaCaching.isShared() : DEFAULT_SHARED);
		this.setDefaultAlwaysRefresh(useJavaValue ? javaCaching.isAlwaysRefresh() : DEFAULT_ALWAYS_REFRESH);
		this.setDefaultRefreshOnlyIfNewer(useJavaValue ? javaCaching.isRefreshOnlyIfNewer() : DEFAULT_REFRESH_ONLY_IF_NEWER);
		this.setDefaultDisableHits(useJavaValue ? javaCaching.isDisableHits() : DEFAULT_DISABLE_HITS);

		this.setDefaultCoordinationType(useJavaValue ? javaCaching.getCoordinationType() : DEFAULT_COORDINATION_TYPE);

		if (this.expiryTimeOfDay != null) {
			this.expiryTimeOfDay.update();
		}

		// existence checking is its own xml attribute, it is not part of the cache xml element
		this.setDefaultExistenceType(javaCacheSpecified ? javaCaching.getExistenceType() : DEFAULT_EXISTENCE_TYPE);

		this.cacheable.update();
	}


	// ********** type **********

	public EclipseLinkCacheType getType() {
		return (this.specifiedType != null) ? this.specifiedType : this.defaultType;
	}

	public EclipseLinkCacheType getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(EclipseLinkCacheType type) {
		if (this.valuesAreDifferent(this.specifiedType, type)) {
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
		this.specifiedType = type;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, type);
	}

	protected EclipseLinkCacheType buildSpecifiedType() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : EclipseLinkCacheType.fromOrmResourceModel(xmlCache.getType());
	}

	public EclipseLinkCacheType getDefaultType() {
		return this.defaultType;
	}

	protected void setDefaultType(EclipseLinkCacheType type) {
		EclipseLinkCacheType old = this.defaultType;
		this.defaultType = type;
		this.firePropertyChanged(DEFAULT_TYPE_PROPERTY, old, type);
	}


	// ********** size **********

	public int getSize() {
		return (this.specifiedSize != null) ? this.specifiedSize.intValue() : this.defaultSize;
	}

	public Integer getSpecifiedSize() {
		return this.specifiedSize;
	}

	public void setSpecifiedSize(Integer size) {
		if (this.valuesAreDifferent(this.specifiedSize, size)) {
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
		this.specifiedSize = size;
		this.firePropertyChanged(SPECIFIED_SIZE_PROPERTY, old, size);
	}

	protected Integer buildSpecifiedSize() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getSize();
	}

	public int getDefaultSize() {
		return this.defaultSize;
	}

	protected void setDefaultSize(int size) {
		int old = this.defaultSize;
		this.defaultSize = size;
		this.firePropertyChanged(DEFAULT_SIZE_PROPERTY, old, size);
	}


	// ********** shared **********

	public boolean isShared() {
		return (this.specifiedShared != null) ? this.specifiedShared.booleanValue() : this.defaultShared;
	}

	public Boolean getSpecifiedShared() {
		return this.specifiedShared;
	}

	public void setSpecifiedShared(Boolean shared) {
		if (this.valuesAreDifferent(this.specifiedShared, shared)) {
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

	public boolean isDefaultShared() {
		return this.defaultShared;
	}

	protected void setDefaultShared(boolean shared) {
		boolean old = this.defaultShared;
		this.defaultShared = shared;
		this.firePropertyChanged(DEFAULT_SHARED_PROPERTY, old, shared);
	}


	// ********** always refresh **********

	public boolean isAlwaysRefresh() {
		return (this.specifiedAlwaysRefresh != null) ? this.specifiedAlwaysRefresh.booleanValue() : this.defaultAlwaysRefresh;
	}

	public Boolean getSpecifiedAlwaysRefresh() {
		return this.specifiedAlwaysRefresh;
	}

	public void setSpecifiedAlwaysRefresh(Boolean alwaysRefresh) {
		if (this.valuesAreDifferent(this.specifiedAlwaysRefresh, alwaysRefresh)) {
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
		this.specifiedAlwaysRefresh = alwaysRefresh;
		this.firePropertyChanged(SPECIFIED_ALWAYS_REFRESH_PROPERTY, old, alwaysRefresh);
	}

	protected Boolean buildSpecifiedAlwaysRefresh() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getAlwaysRefresh();
	}

	public boolean isDefaultAlwaysRefresh() {
		return this.defaultAlwaysRefresh;
	}

	protected void setDefaultAlwaysRefresh(boolean alwaysRefresh) {
		boolean old = this.defaultAlwaysRefresh;
		this.defaultAlwaysRefresh = alwaysRefresh;
		this.firePropertyChanged(DEFAULT_ALWAYS_REFRESH_PROPERTY, old, alwaysRefresh);
	}


	// ********** refresh only if newer **********

	public boolean isRefreshOnlyIfNewer() {
		return (this.specifiedRefreshOnlyIfNewer != null) ? this.specifiedRefreshOnlyIfNewer.booleanValue() : this.defaultRefreshOnlyIfNewer;
	}

	public Boolean getSpecifiedRefreshOnlyIfNewer() {
		return this.specifiedRefreshOnlyIfNewer;
	}

	public void setSpecifiedRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer) {
		if (this.valuesAreDifferent(this.specifiedRefreshOnlyIfNewer, refreshOnlyIfNewer)) {
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
		this.specifiedRefreshOnlyIfNewer = refreshOnlyIfNewer;
		this.firePropertyChanged(SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY, old, refreshOnlyIfNewer);
	}

	protected Boolean buildSpecifiedRefreshOnlyIfNewer() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getRefreshOnlyIfNewer();
	}

	public boolean isDefaultRefreshOnlyIfNewer() {
		return this.defaultRefreshOnlyIfNewer;
	}

	protected void setDefaultRefreshOnlyIfNewer(boolean refreshOnlyIfNewer) {
		boolean old = this.defaultRefreshOnlyIfNewer;
		this.defaultRefreshOnlyIfNewer = refreshOnlyIfNewer;
		this.firePropertyChanged(DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY, old, refreshOnlyIfNewer);
	}


	// ********** disable hits **********

	public boolean isDisableHits() {
		return (this.specifiedDisableHits != null) ? this.specifiedDisableHits.booleanValue() : this.defaultDisableHits;
	}

	public Boolean getSpecifiedDisableHits() {
		return this.specifiedDisableHits;
	}

	public void setSpecifiedDisableHits(Boolean disableHits) {
		if (this.valuesAreDifferent(this.specifiedDisableHits, disableHits)) {
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
		this.specifiedDisableHits = disableHits;
		this.firePropertyChanged(SPECIFIED_DISABLE_HITS_PROPERTY, old, disableHits);
	}

	protected Boolean buildSpecifiedDisableHits() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getDisableHits();
	}

	public boolean isDefaultDisableHits() {
		return this.defaultDisableHits;
	}

	protected void setDefaultDisableHits(boolean disableHits) {
		boolean old = this.defaultDisableHits;
		this.defaultDisableHits = disableHits;
		this.firePropertyChanged(DEFAULT_DISABLE_HITS_PROPERTY, old, disableHits);
	}


	// ********** coordination type **********

	public EclipseLinkCacheCoordinationType getCoordinationType() {
		return (this.specifiedCoordinationType != null) ? this.specifiedCoordinationType : this.defaultCoordinationType;
	}

	public EclipseLinkCacheCoordinationType getSpecifiedCoordinationType() {
		return this.specifiedCoordinationType;
	}

	public void setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType type) {
		if (this.valuesAreDifferent(this.specifiedCoordinationType, type)) {
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
		this.specifiedCoordinationType = type;
		this.firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, old, type);
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
		this.defaultCoordinationType = type;
		this.firePropertyChanged(DEFAULT_COORDINATION_TYPE_PROPERTY, old, type);
	}


	// ********** expiry **********

	public Integer getExpiry() {
		return this.expiry;
	}

	public void setExpiry(Integer expiry) {
		if (this.valuesAreDifferent(this.expiry, expiry)) {
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
		this.expiry = expiry;
		this.firePropertyChanged(EXPIRY_PROPERTY, old, expiry);
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
		this.expiryTimeOfDay = timeOfDay;
		this.firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, old, timeOfDay);
	}

	protected void syncExpiryTimeOfDay() {
		XmlTimeOfDay xmlTimeOfDay = this.getXmlExpiryTimeOfDay();
		if (xmlTimeOfDay == null) {
			if (this.expiryTimeOfDay != null) {
				this.setExpiryTimeOfDay(null);
			}
		} else {
			if ((this.expiryTimeOfDay != null) && (this.expiryTimeOfDay.getXmlTimeOfDay() == xmlTimeOfDay)) {
				this.expiryTimeOfDay.synchronizeWithResourceModel();
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
		this.specifiedExistenceType = type;
		return this.firePropertyChanged(SPECIFIED_EXISTENCE_TYPE_PROPERTY, old, type);
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
		this.defaultExistenceType = type;
		this.firePropertyChanged(DEFAULT_EXISTENCE_TYPE_PROPERTY, old, type);
	}


	// ********** cacheable **********

	public OrmCacheable2_0 getCacheable() {
		return this.cacheable;
	}

	protected OrmCacheable2_0 buildCacheable() {
		return this.isJpa2_0Compatible() ?
				this.getContextNodeFactory2_0().buildOrmCacheable(this) :
				new NullOrmCacheable2_0(this);
	}

	public boolean calculateDefaultCacheable() {
		if ( ! this.getTypeMapping().isMetadataComplete()) {
			JavaPersistentType javaPersistentType = this.getTypeMapping().getPersistentType().getJavaPersistentType();
			if ((javaPersistentType != null) && (javaPersistentType.getMapping() instanceof CacheableHolder2_0)) {
				return ((CacheableHolder2_0) javaPersistentType.getMapping()).getCacheable().isCacheable();
			}
		}
		CacheableHolder2_0 superCacheableHolder = this.getCacheableSuperPersistentType(this.getTypeMapping().getPersistentType());
		return (superCacheableHolder != null) ?
				superCacheableHolder.getCacheable().isCacheable() :
				((PersistenceUnit2_0) this.getPersistenceUnit()).calculateDefaultCacheable();
	}

	protected CacheableHolder2_0 getCacheableSuperPersistentType(PersistentType persistentType) {
		PersistentType superPersistentType = persistentType.getSuperPersistentType();
		if (superPersistentType == null) {
			return null;
		}
		TypeMapping superMapping = superPersistentType.getMapping();
		return (superMapping instanceof CacheableHolder2_0) ?
				(CacheableHolder2_0) superMapping :
				this.getCacheableSuperPersistentType(superPersistentType);  // recurse
	}


	// ********** XML cache **********

	/**
	 * Return null if the XML cache does not exists.
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

	@Override
	public OrmEclipseLinkNonEmbeddableTypeMapping getParent() {
		return (OrmEclipseLinkNonEmbeddableTypeMapping) super.getParent();
	}

	protected OrmEclipseLinkNonEmbeddableTypeMapping getTypeMapping() {
		return this.getParent();
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

	protected JavaEclipseLinkNonEmbeddableTypeMapping getJavaTypeMappingForDefaults() {
		return this.getTypeMapping().getJavaTypeMappingForDefaults();
	}

	protected JavaEclipseLinkCaching getJavaCachingForDefaults() {
		JavaEclipseLinkNonEmbeddableTypeMapping javaTypeMapping = this.getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : javaTypeMapping.getCaching();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		XmlCache xmlCache = this.getXmlCache();
		return (xmlCache == null) ? null : xmlCache.getValidationTextRange();
	}
}
