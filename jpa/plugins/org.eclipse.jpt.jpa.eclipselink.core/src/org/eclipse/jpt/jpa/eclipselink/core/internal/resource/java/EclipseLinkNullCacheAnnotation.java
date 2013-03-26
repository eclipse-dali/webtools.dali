/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TimeOfDayAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.Cache</code>
 */
public final class EclipseLinkNullCacheAnnotation
	extends NullAnnotation<CacheAnnotation>
	implements CacheAnnotation
{
	protected EclipseLinkNullCacheAnnotation(JavaResourceAnnotatedElement parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// ***** type
	public CacheType getType() {
		return null;
	}

	public void setType(CacheType type) {
		if (type != null) {
			this.addAnnotation().setType(type);
		}
	}

	public TextRange getTypeTextRange() {
		return null;
	}

	// ***** size
	public Integer getSize() {
		return null;
	}

	public void setSize(Integer size) {
		if (size != null) {
			this.addAnnotation().setSize(size);
		}
	}

	public TextRange getSizeTextRange() {
		return null;
	}

	// ***** shared
	public Boolean getShared() {
		return null;
	}

	public void setShared(Boolean shared) {
		if (shared != null) {
			this.addAnnotation().setShared(shared);
		}
	}

	public TextRange getSharedTextRange() {
		return null;
	}

	// ***** always refresh
	public Boolean getAlwaysRefresh() {
		return null;
	}

	public void setAlwaysRefresh(Boolean alwaysRefresh) {
		if (alwaysRefresh != null) {
			this.addAnnotation().setAlwaysRefresh(alwaysRefresh);
		}
	}

	public TextRange getAlwaysRefreshTextRange() {
		return null;
	}

	// ***** refresh only if newer
	public Boolean getRefreshOnlyIfNewer() {
		return null;
	}

	public void setRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer) {
		if (refreshOnlyIfNewer != null) {
			this.addAnnotation().setRefreshOnlyIfNewer(refreshOnlyIfNewer);
		}
	}

	public TextRange getRefreshOnlyIfNewerTextRange() {
		return null;
	}

	// ***** disable hits
	public Boolean getDisableHits() {
		return null;
	}

	public void setDisableHits(Boolean disableHits) {
		if (disableHits != null) {
			this.addAnnotation().setDisableHits(disableHits);
		}
	}

	public TextRange getDisableHitsTextRange() {
		return null;
	}

	// ***** coordination type
	public CacheCoordinationType getCoordinationType() {
		return null;
	}

	public void setCoordinationType(CacheCoordinationType coordinationType) {
		if (coordinationType != null) {
			this.addAnnotation().setCoordinationType(coordinationType);
		}
	}

	public TextRange getCoordinationTypeTextRange() {
		return null;
	}

	// ***** expiry
	public Integer getExpiry() {
		return null;
	}

	public void setExpiry(Integer expiry) {
		if (expiry != null) {
			this.addAnnotation().setExpiry(expiry);
		}
	}

	public TextRange getExpiryTextRange() {
		return null;
	}

	// ***** expiry time of day
	public TimeOfDayAnnotation getExpiryTimeOfDay() {
		return null;
	}

	public TimeOfDayAnnotation addExpiryTimeOfDay() {
		return this.addAnnotation().addExpiryTimeOfDay();
	}

	public void removeExpiryTimeOfDay() {
		// do nothing
	}

	public TextRange getExpiryTimeOfDayTextRange() {
		return null;
	}


	// ***** isolation
	public CacheIsolationType2_2 getIsolation() {
		return null;
	}

	public void setIsolation(CacheIsolationType2_2 isolation) {
		if (isolation != null) {
			this.addAnnotation().setIsolation(isolation);
		}
	}

	public TextRange getIsolationTextRange() {
		return null;
	}
}
