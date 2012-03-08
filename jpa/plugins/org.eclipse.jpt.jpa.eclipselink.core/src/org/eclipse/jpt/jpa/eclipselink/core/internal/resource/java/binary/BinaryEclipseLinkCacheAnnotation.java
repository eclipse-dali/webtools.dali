/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkCacheAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTimeOfDayAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.Cache</code>
 */
public final class BinaryEclipseLinkCacheAnnotation
	extends BinaryAnnotation
	implements EclipseLinkCacheAnnotation
{
	private CacheType type;
	private Integer size;
	private Boolean shared;
	private Integer expiry;
	private EclipseLinkTimeOfDayAnnotation expiryTimeOfDay;
	private Boolean alwaysRefresh;
	private Boolean refreshOnlyIfNewer;
	private Boolean disableHits;
	private CacheCoordinationType coordinationType;
	private CacheIsolationType2_2 isolation;


	public BinaryEclipseLinkCacheAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.type = this.buildType();
		this.size = this.buildSize();
		this.shared = this.buildShared();
		this.expiry = this.buildExpiry();
		this.expiryTimeOfDay = this.buildExpiryTimeOfDay();
		this.alwaysRefresh = this.buildAlwaysRefresh();
		this.refreshOnlyIfNewer = this.buildRefreshOnlyIfNewer();
		this.disableHits = this.buildDisableHits();
		this.coordinationType = this.buildCoordinationType();
		this.isolation = this.buildIsolation();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// TODO
	@Override
	public void update() {
		super.update();
		this.setType_(this.buildType());
		this.setSize_(this.buildSize());
		this.setShared_(this.buildShared());
		this.setExpiry_(this.buildExpiry());
		this.updateExpiryTimeOfDay();
		this.setAlwaysRefresh_(this.buildAlwaysRefresh());
		this.setRefreshOnlyIfNewer_(this.buildRefreshOnlyIfNewer());
		this.setDisableHits_(this.buildDisableHits());
		this.setCoordinationType_(this.buildCoordinationType());
		this.setIsolation_(this.buildIsolation());
	}


	// ********** CacheAnnotation implementation **********

	// ***** type
	public CacheType getType() {
		return this.type;
	}

	public void setType(CacheType type) {
		throw new UnsupportedOperationException();
	}

	private void setType_(CacheType type) {
		CacheType old = this.type;
		this.type = type;
		this.firePropertyChanged(TYPE_PROPERTY, old, type);
	}

	private CacheType buildType() {
		return CacheType.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLink.CACHE__TYPE));
	}

	public TextRange getTypeTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** size
	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		throw new UnsupportedOperationException();
	}

	private void setSize_(Integer size) {
		Integer old = this.size;
		this.size = size;
		this.firePropertyChanged(SIZE_PROPERTY, old, size);
	}

	private Integer buildSize() {
		return (Integer) this.getJdtMemberValue(EclipseLink.CACHE__SIZE);
	}

	public TextRange getSizeTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** shared
	public Boolean getShared() {
		return this.shared;
	}

	public void setShared(Boolean shared) {
		throw new UnsupportedOperationException();
	}

	private void setShared_(Boolean shared) {
		Boolean old = this.shared;
		this.shared = shared;
		this.firePropertyChanged(SHARED_PROPERTY, old, shared);
	}

	private Boolean buildShared() {
		return (Boolean) this.getJdtMemberValue(EclipseLink.CACHE__SHARED);
	}

	public TextRange getSharedTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** expiry
	public Integer getExpiry() {
		return this.expiry;
	}

	public void setExpiry(Integer expiry) {
		throw new UnsupportedOperationException();
	}

	private void setExpiry_(Integer expiry) {
		Integer old = this.expiry;
		this.expiry = expiry;
		this.firePropertyChanged(EXPIRY_PROPERTY, old, expiry);
	}

	private Integer buildExpiry() {
		return (Integer) this.getJdtMemberValue(EclipseLink.CACHE__EXPIRY);
	}

	public TextRange getExpiryTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** expiry time of day
	public EclipseLinkTimeOfDayAnnotation getExpiryTimeOfDay() {
		return this.expiryTimeOfDay;
	}

	public EclipseLinkTimeOfDayAnnotation addExpiryTimeOfDay() {
		throw new UnsupportedOperationException();
	}

	public void removeExpiryTimeOfDay() {
		throw new UnsupportedOperationException();
	}

	private EclipseLinkTimeOfDayAnnotation buildExpiryTimeOfDay() {
		IAnnotation jdtTimeOfDay = this.getJdtExpiryTimeOfDay();
		return (jdtTimeOfDay == null) ? null : this.buildTimeOfDay(jdtTimeOfDay);
	}

	private EclipseLinkTimeOfDayAnnotation buildTimeOfDay(IAnnotation jdtTimeOfDay) {
		return new BinaryEclipseLinkTimeOfDayAnnotation(this, jdtTimeOfDay);
	}

	private IAnnotation getJdtExpiryTimeOfDay() {
		return (IAnnotation) this.getJdtMemberValue(EclipseLink.CACHE__EXPIRY_TIME_OF_DAY);
	}

	private void setExpiryTimeOfDay(EclipseLinkTimeOfDayAnnotation expiryTimeOfDay) {
		EclipseLinkTimeOfDayAnnotation old = this.expiryTimeOfDay;
		this.expiryTimeOfDay = expiryTimeOfDay;
		this.firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, old, expiryTimeOfDay);
	}

	// TODO
	private void updateExpiryTimeOfDay() {
		throw new UnsupportedOperationException();
//		IAnnotation jdtTimeOfDay = this.getJdtExpiryTimeOfDay();
//		if (jdtTimeOfDay == null) {
//			this.setExpiryTimeOfDay(null);
//		} else {
//			if (this.expiryTimeOfDay == null) {
//				this.setExpiryTimeOfDay(this.buildTimeOfDay(jdtTimeOfDay));
//			} else {
//				this.expiryTimeOfDay.update(jdtTimeOfDay);
//			}
//		}
	}

	public TextRange getExpiryTimeOfDayTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** always refresh
	public Boolean getAlwaysRefresh() {
		return this.alwaysRefresh;
	}

	public void setAlwaysRefresh(Boolean alwaysRefresh) {
		throw new UnsupportedOperationException();
	}

	private void setAlwaysRefresh_(Boolean alwaysRefresh) {
		Boolean old = this.alwaysRefresh;
		this.alwaysRefresh = alwaysRefresh;
		this.firePropertyChanged(ALWAYS_REFRESH_PROPERTY, old, alwaysRefresh);
	}

	private Boolean buildAlwaysRefresh() {
		return (Boolean) this.getJdtMemberValue(EclipseLink.CACHE__ALWAYS_REFRESH);
	}

	public TextRange getAlwaysRefreshTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** refresh only if newer
	public Boolean getRefreshOnlyIfNewer() {
		return this.refreshOnlyIfNewer;
	}

	public void setRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer) {
		throw new UnsupportedOperationException();
	}

	private void setRefreshOnlyIfNewer_(Boolean refreshOnlyIfNewer) {
		Boolean old = this.refreshOnlyIfNewer;
		this.refreshOnlyIfNewer = refreshOnlyIfNewer;
		this.firePropertyChanged(REFRESH_ONLY_IF_NEWER_PROPERTY, old, refreshOnlyIfNewer);
	}

	private Boolean buildRefreshOnlyIfNewer() {
		return (Boolean) this.getJdtMemberValue(EclipseLink.CACHE__REFRESH_ONLY_IF_NEWER);
	}

	public TextRange getRefreshOnlyIfNewerTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** disable hits
	public Boolean getDisableHits() {
		return this.disableHits;
	}

	public void setDisableHits(Boolean disableHits) {
		throw new UnsupportedOperationException();
	}

	private void setDisableHits_(Boolean disableHits) {
		Boolean old = this.disableHits;
		this.disableHits = disableHits;
		this.firePropertyChanged(DISABLE_HITS_PROPERTY, old, disableHits);
	}

	private Boolean buildDisableHits() {
		return (Boolean) this.getJdtMemberValue(EclipseLink.CACHE__DISABLE_HITS);
	}

	public TextRange getDisableHitsTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** coordination type
	public CacheCoordinationType getCoordinationType() {
		return this.coordinationType;
	}

	public void setCoordinationType(CacheCoordinationType coordinationType) {
		throw new UnsupportedOperationException();
	}

	private void setCoordinationType_(CacheCoordinationType coordinationType) {
		CacheCoordinationType old = this.coordinationType;
		this.coordinationType = coordinationType;
		this.firePropertyChanged(TYPE_PROPERTY, old, coordinationType);
	}

	private CacheCoordinationType buildCoordinationType() {
		return CacheCoordinationType.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLink.CACHE__COORDINATION_TYPE));
	}

	public TextRange getCoordinationTypeTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** isolation
	public CacheIsolationType2_2 getIsolation() {
		return this.isolation;
	}

	public void setIsolation(CacheIsolationType2_2 isolation) {
		throw new UnsupportedOperationException();
	}

	private void setIsolation_(CacheIsolationType2_2 isolation) {
		CacheIsolationType2_2 old = this.isolation;
		this.isolation = isolation;
		this.firePropertyChanged(ISOLATION_PROPERTY, old, isolation);
	}

	private CacheIsolationType2_2 buildIsolation() {
		return CacheIsolationType2_2.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLink.CACHE__ISOLATION));
	}

	public TextRange getIsolationTextRange() {
		throw new UnsupportedOperationException();
	}
}
