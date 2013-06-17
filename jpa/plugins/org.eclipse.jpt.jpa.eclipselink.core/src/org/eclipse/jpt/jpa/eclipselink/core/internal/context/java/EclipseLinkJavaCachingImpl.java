/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.NullJavaCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheIsolationType2_2;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTimeOfDay;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ExistenceCheckingAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TimeOfDayAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaCachingImpl
	extends AbstractJavaContextModel<EclipseLinkJavaNonEmbeddableTypeMapping>
	implements EclipseLinkJavaCaching, JavaCacheableReference2_0
{
	protected EclipseLinkCacheType specifiedType;
	protected Integer specifiedSize;
	protected Boolean specifiedShared;
	protected Boolean specifiedAlwaysRefresh;
	protected Boolean specifiedRefreshOnlyIfNewer;
	protected Boolean specifiedDisableHits;

	protected EclipseLinkCacheCoordinationType specifiedCoordinationType;

	protected Integer expiry;
	protected EclipseLinkJavaTimeOfDay expiryTimeOfDay;

	protected boolean existenceChecking;
	protected EclipseLinkExistenceType specifiedExistenceType;
	protected EclipseLinkExistenceType defaultExistenceType;

	protected final Cacheable2_0 cacheable;

	protected EclipseLinkCacheIsolationType2_2 specifiedIsolation;

	public EclipseLinkJavaCachingImpl(EclipseLinkJavaNonEmbeddableTypeMapping parent) {
		super(parent);

		CacheAnnotation cacheAnnotation = this.getCacheAnnotation();
		this.specifiedType = EclipseLinkCacheType.fromJavaResourceModel(cacheAnnotation.getType());
		this.specifiedSize = cacheAnnotation.getSize();
		this.specifiedShared = cacheAnnotation.getShared();
		this.specifiedAlwaysRefresh = cacheAnnotation.getAlwaysRefresh();
		this.specifiedRefreshOnlyIfNewer = cacheAnnotation.getRefreshOnlyIfNewer();
		this.specifiedDisableHits = cacheAnnotation.getDisableHits();

		this.specifiedCoordinationType = EclipseLinkCacheCoordinationType.fromJavaResourceModel(cacheAnnotation.getCoordinationType());

		this.expiry = cacheAnnotation.getExpiry();
		this.expiryTimeOfDay = this.buildExpiryTimeOfDay(cacheAnnotation.getExpiryTimeOfDay());

		ExistenceCheckingAnnotation ecAnnotation = this.getExistenceCheckingAnnotation();
		this.existenceChecking = (ecAnnotation != null);
		this.specifiedExistenceType = this.buildSpecifiedExistenceType(ecAnnotation);

		this.cacheable = this.buildCacheable();

		this.specifiedIsolation = EclipseLinkCacheIsolationType2_2.fromJavaResourceModel(cacheAnnotation.getIsolation());
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();

		CacheAnnotation cacheAnnotation = this.getCacheAnnotation();
		this.setSpecifiedType_(EclipseLinkCacheType.fromJavaResourceModel(cacheAnnotation.getType()));
		this.setSpecifiedSize_(cacheAnnotation.getSize());
		this.setSpecifiedShared_(cacheAnnotation.getShared());
		this.setSpecifiedAlwaysRefresh_(cacheAnnotation.getAlwaysRefresh());
		this.setSpecifiedRefreshOnlyIfNewer_(cacheAnnotation.getRefreshOnlyIfNewer());
		this.setSpecifiedDisableHits_(cacheAnnotation.getDisableHits());

		this.setSpecifiedCoordinationType_(EclipseLinkCacheCoordinationType.fromJavaResourceModel(cacheAnnotation.getCoordinationType()));

		this.setExpiry_(cacheAnnotation.getExpiry());
		this.syncExpiryTimeOfDay(cacheAnnotation.getExpiryTimeOfDay());

		ExistenceCheckingAnnotation ecAnnotation = this.getExistenceCheckingAnnotation();
		this.setExistenceChecking_(ecAnnotation != null);
		this.setSpecifiedExistenceType_(this.buildSpecifiedExistenceType(ecAnnotation));

		this.cacheable.synchronizeWithResourceModel();

		this.setSpecifiedIsolation_(EclipseLinkCacheIsolationType2_2.fromJavaResourceModel(cacheAnnotation.getIsolation()));
	}

	@Override
	public void update() {
		super.update();
		if (this.expiryTimeOfDay != null) {
			this.expiryTimeOfDay.update();
		}
		this.setDefaultExistenceType(this.buildDefaultExistenceType());
		this.cacheable.update();
	}


	// ********** type **********

	public EclipseLinkCacheType getType() {
		return (this.specifiedType != null) ? this.specifiedType : this.getDefaultType();
	}

	public EclipseLinkCacheType getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(EclipseLinkCacheType type) {
		this.getCacheAnnotation().setType(EclipseLinkCacheType.toJavaResourceModel(type));
		this.setSpecifiedType_(type);

		if (type != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedType_(EclipseLinkCacheType type) {
		EclipseLinkCacheType old = this.specifiedType;
		this.specifiedType = type;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, type);
	}

	public EclipseLinkCacheType getDefaultType() {
		String puDefaultCacheTypeName = ((EclipseLinkPersistenceUnit)getPersistenceUnit()).getDefaultCacheTypePropertyValue();
		if (!StringTools.isBlank(puDefaultCacheTypeName)) {
			try { 
				return EclipseLinkCacheType.valueOf(StringTools.convertCamelCaseToAllCaps(puDefaultCacheTypeName));
			} catch (IllegalArgumentException exception) {
				//no match, return default
			}
		}
		return DEFAULT_TYPE;
	}
	
	// ********** size **********

	public int getSize() {
		return (this.specifiedSize != null) ? this.specifiedSize.intValue() : this.getDefaultSize();
	}

	public Integer getSpecifiedSize() {
		return this.specifiedSize;
	}

	public void setSpecifiedSize(Integer size) {
		this.getCacheAnnotation().setSize(size);
		this.setSpecifiedSize_(size);

		if (size != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedSize_(Integer size) {
		Integer old = this.specifiedSize;
		this.specifiedSize = size;
		this.firePropertyChanged(SPECIFIED_SIZE_PROPERTY, old, size);
	}

	public int getDefaultSize() {
		String puDefaultCacheSize = ((EclipseLinkPersistenceUnit)getPersistenceUnit()).getDefaultCacheSizePropertyValue();
		if (!StringTools.isBlank(puDefaultCacheSize)) {
			try {
				return Integer.valueOf(puDefaultCacheSize).intValue();
			} catch (NumberFormatException exception) {
				//couldn't parse, return default
			}
		}
		return DEFAULT_SIZE;
	}


	// ********** shared **********

	public boolean isShared() {
		return (this.specifiedShared != null) ? this.specifiedShared.booleanValue() : this.isDefaultShared();
	}

	public Boolean getSpecifiedShared() {
		return this.specifiedShared;
	}

	public void setSpecifiedShared(Boolean shared) {
		this.getCacheAnnotation().setShared(shared);
		this.setSpecifiedShared_(shared);

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

	public boolean isDefaultShared() {
		String puDefaultSharedCache = ((EclipseLinkPersistenceUnit)getPersistenceUnit()).getDefaultCacheSharedPropertyValue();
		return !StringTools.isBlank(puDefaultSharedCache) ? Boolean.valueOf(puDefaultSharedCache).booleanValue() : DEFAULT_SHARED;
	}


	// ********** always refresh **********

	public boolean isAlwaysRefresh() {
		return (this.specifiedAlwaysRefresh != null) ? this.specifiedAlwaysRefresh.booleanValue() : this.isDefaultAlwaysRefresh();
	}

	public Boolean getSpecifiedAlwaysRefresh() {
		return this.specifiedAlwaysRefresh;
	}

	public void setSpecifiedAlwaysRefresh(Boolean alwaysRefresh) {
		this.getCacheAnnotation().setAlwaysRefresh(alwaysRefresh);
		this.setSpecifiedAlwaysRefresh_(alwaysRefresh);

		if (alwaysRefresh != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedAlwaysRefresh_(Boolean alwaysRefresh) {
		Boolean old = this.specifiedAlwaysRefresh;
		this.specifiedAlwaysRefresh = alwaysRefresh;
		this.firePropertyChanged(SPECIFIED_ALWAYS_REFRESH_PROPERTY, old, alwaysRefresh);
	}

	public boolean isDefaultAlwaysRefresh() {
		return DEFAULT_ALWAYS_REFRESH;
	}


	// ********** refresh only if newer **********

	public boolean isRefreshOnlyIfNewer() {
		return (this.specifiedRefreshOnlyIfNewer != null) ? this.specifiedRefreshOnlyIfNewer.booleanValue() : this.isDefaultRefreshOnlyIfNewer();
	}

	public Boolean getSpecifiedRefreshOnlyIfNewer() {
		return this.specifiedRefreshOnlyIfNewer;
	}

	public void setSpecifiedRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer) {
		this.getCacheAnnotation().setRefreshOnlyIfNewer(refreshOnlyIfNewer);
		this.setSpecifiedRefreshOnlyIfNewer_(refreshOnlyIfNewer);

		if (refreshOnlyIfNewer != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedRefreshOnlyIfNewer_(Boolean refreshOnlyIfNewer) {
		Boolean old = this.specifiedRefreshOnlyIfNewer;
		this.specifiedRefreshOnlyIfNewer = refreshOnlyIfNewer;
		this.firePropertyChanged(SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY, old, refreshOnlyIfNewer);
	}

	public boolean isDefaultRefreshOnlyIfNewer() {
		return DEFAULT_REFRESH_ONLY_IF_NEWER;
	}


	// ********** disable hits **********

	public boolean isDisableHits() {
		return (this.specifiedDisableHits != null) ? this.specifiedDisableHits.booleanValue() : this.isDefaultDisableHits();
	}

	public Boolean getSpecifiedDisableHits() {
		return this.specifiedDisableHits;
	}

	public void setSpecifiedDisableHits(Boolean disableHits) {
		this.getCacheAnnotation().setDisableHits(disableHits);
		this.setSpecifiedDisableHits_(disableHits);

		if (disableHits != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedDisableHits_(Boolean disableHits) {
		Boolean old = this.specifiedDisableHits;
		this.specifiedDisableHits = disableHits;
		this.firePropertyChanged(SPECIFIED_DISABLE_HITS_PROPERTY, old, disableHits);
	}

	public boolean isDefaultDisableHits() {
		return DEFAULT_DISABLE_HITS;
	}


	// ********** coordination type **********

	public EclipseLinkCacheCoordinationType getCoordinationType() {
		return (this.specifiedCoordinationType != null) ? this.specifiedCoordinationType : this.getDefaultCoordinationType();
	}

	public EclipseLinkCacheCoordinationType getSpecifiedCoordinationType() {
		return this.specifiedCoordinationType;
	}

	public void setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType type) {
		this.getCacheAnnotation().setCoordinationType(EclipseLinkCacheCoordinationType.toJavaResourceModel(type));
		this.setSpecifiedCoordinationType_(type);

		if (type != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedCoordinationType_(EclipseLinkCacheCoordinationType type) {
		EclipseLinkCacheCoordinationType old = this.specifiedCoordinationType;
		this.specifiedCoordinationType = type;
		this.firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, old, type);
	}

	public EclipseLinkCacheCoordinationType getDefaultCoordinationType() {
		return DEFAULT_COORDINATION_TYPE;
	}


	// ********** expiry **********

	public Integer getExpiry() {
		return this.expiry;
	}

	public void setExpiry(Integer expiry) {
		this.getCacheAnnotation().setExpiry(expiry);
		this.setExpiry_(expiry);

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


	// ********** expiry time of day **********

	public EclipseLinkTimeOfDay getExpiryTimeOfDay() {
		return this.expiryTimeOfDay;
	}

	public EclipseLinkTimeOfDay addExpiryTimeOfDay() {
		if (this.expiryTimeOfDay != null) {
			throw new IllegalStateException("expiry time of day already exists: " + this.expiryTimeOfDay); //$NON-NLS-1$
		}
		TimeOfDayAnnotation timeOfDayAnnotation = this.getCacheAnnotation().addExpiryTimeOfDay();
		EclipseLinkJavaTimeOfDay timeOfDay = this.buildExpiryTimeOfDay(timeOfDayAnnotation);
		this.setExpiryTimeOfDay(timeOfDay);

		this.setExpiry(null);
		this.setSpecifiedShared(null);

		return timeOfDay;
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
		this.getCacheAnnotation().removeExpiryTimeOfDay();
		this.setExpiryTimeOfDay(null);
	}

	public void setExpiryTimeOfDay(EclipseLinkJavaTimeOfDay timeOfDay) {
		EclipseLinkJavaTimeOfDay old = this.expiryTimeOfDay;
		this.expiryTimeOfDay = timeOfDay;
		this.firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, old, timeOfDay);
	}

	protected void syncExpiryTimeOfDay(TimeOfDayAnnotation timeOfDayAnnotation) {
		if (timeOfDayAnnotation == null) {
			if (this.expiryTimeOfDay != null) {
				this.setExpiryTimeOfDay(null);
			}
		} else {
			if ((this.expiryTimeOfDay != null) && (this.expiryTimeOfDay.getTimeOfDayAnnotation() == timeOfDayAnnotation)) {
				this.expiryTimeOfDay.synchronizeWithResourceModel();
			} else {
				this.setExpiryTimeOfDay(this.buildExpiryTimeOfDay(timeOfDayAnnotation));
			}
		}
	}

	protected EclipseLinkJavaTimeOfDay buildExpiryTimeOfDay(TimeOfDayAnnotation timeOfDayAnnotation) {
		return (timeOfDayAnnotation == null) ? null : new EclipseLinkJavaTimeOfDay(this, timeOfDayAnnotation);
	}


	// ********** existence checking **********

	public boolean isExistenceChecking() {
		return this.existenceChecking;
	}

	public void setExistenceChecking(boolean existenceChecking) {
		if (existenceChecking != this.existenceChecking) {
			if (existenceChecking) {
				this.addExistenceCheckingAnnotation();
			} else {
				this.setSpecifiedExistenceType(null);
				this.removeExistenceCheckingAnnotation();
			}
			this.setExistenceChecking_(existenceChecking);
		}
	}

	protected void setExistenceChecking_(boolean existenceChecking) {
		boolean old = this.existenceChecking;
		this.existenceChecking = existenceChecking;
		this.firePropertyChanged(EXISTENCE_CHECKING_PROPERTY, old, existenceChecking);
	}


	// ********** existence type **********

	public EclipseLinkExistenceType getExistenceType() {
		return (this.specifiedExistenceType != null) ? this.specifiedExistenceType : this.defaultExistenceType;
	}

	public EclipseLinkExistenceType getSpecifiedExistenceType() {
		return this.specifiedExistenceType;
	}

	public void setSpecifiedExistenceType(EclipseLinkExistenceType type) {
		if (ObjectTools.notEquals(type, this.specifiedExistenceType)) {
			if (type != null) {
				this.setExistenceChecking(true);
			}
			this.getExistenceCheckingAnnotation().setValue(EclipseLinkExistenceType.toJavaResourceModel(type));
			this.setSpecifiedExistenceType_(type);
		}
	}

	protected void setSpecifiedExistenceType_(EclipseLinkExistenceType type) {
		EclipseLinkExistenceType old = this.specifiedExistenceType;
		this.specifiedExistenceType = type;
		this.firePropertyChanged(SPECIFIED_EXISTENCE_TYPE_PROPERTY, old, type);
	}

	protected EclipseLinkExistenceType buildSpecifiedExistenceType(ExistenceCheckingAnnotation ecAnnotation) {
		return (ecAnnotation == null) ? null : EclipseLinkExistenceType.fromJavaResourceModel(ecAnnotation.getValue());
	}

	public EclipseLinkExistenceType getDefaultExistenceType() {
		return this.defaultExistenceType;
	}

	protected void setDefaultExistenceType(EclipseLinkExistenceType type) {
		EclipseLinkExistenceType old = this.defaultExistenceType;
		this.defaultExistenceType = type;
		this.firePropertyChanged(DEFAULT_EXISTENCE_TYPE_PROPERTY, old, type);
	}

	protected EclipseLinkExistenceType buildDefaultExistenceType() {
		return this.existenceChecking ? EclipseLinkExistenceType.CHECK_CACHE : DEFAULT_EXISTENCE_TYPE;
	}


	// ********** cacheable **********

	public Cacheable2_0 getCacheable() {
		return this.cacheable;
	}

	public boolean calculateDefaultCacheable() {
		CacheableReference2_0 superCacheableHolder = this.getCacheableSuperPersistentType(this.getPersistentType());
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

	protected Cacheable2_0 buildCacheable() {
		return this.isJpa2_0Compatible() ?
				this.getJpaFactory2_0().buildJavaCacheable(this) :
				new NullJavaCacheable2_0(this);
	}


	// ********** isolation **********

	public EclipseLinkCacheIsolationType2_2 getIsolation() {
		return (this.specifiedIsolation != null) ? this.specifiedIsolation : this.getDefaultIsolation();
	}

	public EclipseLinkCacheIsolationType2_2 getSpecifiedIsolation() {
		return this.specifiedIsolation;
	}

	public void setSpecifiedIsolation(EclipseLinkCacheIsolationType2_2 isolation) {
		this.getCacheAnnotation().setIsolation(EclipseLinkCacheIsolationType2_2.toJavaResourceModel(isolation));
		this.setSpecifiedIsolation_(isolation);

		if (isolation != null) {
			this.setSpecifiedShared(null);
		}
	}

	protected void setSpecifiedIsolation_(EclipseLinkCacheIsolationType2_2 isolation) {
		EclipseLinkCacheIsolationType2_2 old = this.specifiedIsolation;
		this.specifiedIsolation = isolation;
		this.firePropertyChanged(SPECIFIED_ISOLATION_PROPERTY, old, isolation);
	}

	public EclipseLinkCacheIsolationType2_2 getDefaultIsolation() {
		return DEFAULT_ISOLATION;
	}

	// ********** cache annotation **********

	protected CacheAnnotation getCacheAnnotation() {
		return (CacheAnnotation) this.getJavaResourceType().getNonNullAnnotation(this.getCacheAnnotationName());
	}

	protected String getCacheAnnotationName() {
		return CacheAnnotation.ANNOTATION_NAME;
	}


	// ********** existence checking annotation **********

	protected ExistenceCheckingAnnotation getExistenceCheckingAnnotation() {
		return (ExistenceCheckingAnnotation) this.getJavaResourceType().getAnnotation(this.getExistenceCheckingAnnotationName());
	}

	protected ExistenceCheckingAnnotation addExistenceCheckingAnnotation() {
		return (ExistenceCheckingAnnotation) this.getJavaResourceType().addAnnotation(this.getExistenceCheckingAnnotationName());
	}

	protected void removeExistenceCheckingAnnotation() {
		this.getJavaResourceType().removeAnnotation(this.getExistenceCheckingAnnotationName());
	}

	protected String getExistenceCheckingAnnotationName() {
		return ExistenceCheckingAnnotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	protected EclipseLinkJavaNonEmbeddableTypeMapping getTypeMapping() {
		return this.parent;
	}

	protected JavaPersistentType getPersistentType() {
		return this.getTypeMapping().getPersistentType();
	}

	public JavaResourceType getJavaResourceType() {
		return this.getTypeMapping().getJavaResourceType();
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateExpiry(messages);
	}
	
	protected void validateExpiry(List<IMessage> messages) {
		if ((this.expiry != null) && (this.expiryTimeOfDay != null)) {
			messages.add(
				this.buildValidationMessage(
					this.getValidationTextRange(),
					JptJpaEclipseLinkCoreValidationMessages.CACHE_EXPIRY_AND_EXPIRY_TIME_OF_DAY_BOTH_SPECIFIED,
					this.getPersistentType().getName()
				)
			);
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getCacheAnnotation().getTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}
}
