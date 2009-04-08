/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheType;
import org.eclipse.jpt.eclipselink.core.resource.java.TimeOfDayAnnotation;

/**
 * org.eclipse.persistence.annotations.Cache
 */
public class NullCacheAnnotation
	extends NullAnnotation
	implements CacheAnnotation
{

	protected NullCacheAnnotation(JavaResourcePersistentType parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected CacheAnnotation buildSupportingAnnotation() {
		return (CacheAnnotation) super.buildSupportingAnnotation();
	}

	// ***** type
	public CacheType getType() {
		return null;
	}

	public void setType(CacheType type) {
		if (type != null) {
			this.buildSupportingAnnotation().setType(type);
		}
	}

	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** size
	public Integer getSize() {
		return null;
	}

	public void setSize(Integer size) {
		if (size != null) {
			this.buildSupportingAnnotation().setSize(size);
		}
	}

	public TextRange getSizeTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** shared
	public Boolean getShared() {
		return null;
	}

	public void setShared(Boolean shared) {
		if (shared != null) {
			this.buildSupportingAnnotation().setShared(shared);
		}
	}

	public TextRange getSharedTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** expiry
	public Integer getExpiry() {
		return null;
	}

	public void setExpiry(Integer expiry) {
		if (expiry != null) {
			this.buildSupportingAnnotation().setExpiry(expiry);
		}
	}

	public TextRange getExpiryTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** expiry time of day
	public TimeOfDayAnnotation getExpiryTimeOfDay() {
		return null;
	}

	public TimeOfDayAnnotation addExpiryTimeOfDay() {
		return this.buildSupportingAnnotation().addExpiryTimeOfDay();
	}

	public void removeExpiryTimeOfDay() {
		// do nothing
	}

	public TextRange getExpiryTimeOfDayTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** always refresh
	public Boolean getAlwaysRefresh() {
		return null;
	}

	public void setAlwaysRefresh(Boolean alwaysRefresh) {
		if (alwaysRefresh != null) {
			this.buildSupportingAnnotation().setAlwaysRefresh(alwaysRefresh);
		}
	}

	public TextRange getAlwaysRefreshTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** refresh only if newer
	public Boolean getRefreshOnlyIfNewer() {
		return null;
	}

	public void setRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer) {
		if (refreshOnlyIfNewer != null) {
			this.buildSupportingAnnotation().setRefreshOnlyIfNewer(refreshOnlyIfNewer);
		}
	}

	public TextRange getRefreshOnlyIfNewerTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** disable hits
	public Boolean getDisableHits() {
		return null;
	}

	public void setDisableHits(Boolean disableHits) {
		if (disableHits != null) {
			this.buildSupportingAnnotation().setDisableHits(disableHits);
		}
	}

	public TextRange getDisablesHitsTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** coordination type
	public CacheCoordinationType getCoordinationType() {
		return null;
	}

	public void setCoordinationType(CacheCoordinationType coordinationType) {
		if (coordinationType != null) {
			this.buildSupportingAnnotation().setCoordinationType(coordinationType);
		}
	}

	public TextRange getCoordinationTypeTextRange(CompilationUnit astRoot) {
		return null;
	}

}
