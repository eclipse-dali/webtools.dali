/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.CacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.context.CacheType;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.core.context.ExistenceType;
import org.eclipse.jpt.eclipselink.core.context.ExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCaching;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.ExistenceCheckingAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.TimeOfDayAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaCaching extends AbstractJavaJpaContextNode implements JavaCaching
{
	
	protected CacheType specifiedType;
	protected Integer specifiedSize;
	protected Boolean specifiedShared;
	protected Boolean specifiedAlwaysRefresh;
	protected Boolean specifiedRefreshOnlyIfNewer;
	protected Boolean specifiedDisableHits;
	
	protected boolean existenceChecking;
	protected ExistenceType specifiedExistenceType;
	protected ExistenceType defaultExistenceType;

	protected CacheCoordinationType specifiedCoordinationType;
	
	protected Integer expiry;
	protected EclipseLinkJavaExpiryTimeOfDay expiryTimeOfDay;
	
	
	protected JavaResourcePersistentType resourcePersistentType;
	
	public EclipseLinkJavaCaching(JavaTypeMapping parent) {
		super(parent);
	}
	
	@Override
	public JavaTypeMapping getParent() {
		return (JavaTypeMapping) super.getParent();
	}
	

	//query for the cache annotation every time on setters.
	//call one setter and the CacheAnnotation could change. 
	//You could call more than one setter before this object has received any notification
	//from the java resource model
	protected CacheAnnotation getCacheAnnotation() {
		return (CacheAnnotation) this.resourcePersistentType.getNonNullSupportingAnnotation(getCacheAnnotationName());
	}
	
	protected ExistenceCheckingAnnotation getExistenceCheckingAnnotation() {
		return (ExistenceCheckingAnnotation) this.resourcePersistentType.getSupportingAnnotation(getExistenceCheckingAnnotationName());
	}

	protected String getCacheAnnotationName() {
		return CacheAnnotation.ANNOTATION_NAME;
	}
	
	protected String getExistenceCheckingAnnotationName() {
		return ExistenceCheckingAnnotation.ANNOTATION_NAME;
	}
	
	public CacheType getType() {
		return (this.getSpecifiedType() == null) ? this.getDefaultType() : this.getSpecifiedType();
	}

	public CacheType getDefaultType() {
		return DEFAULT_TYPE;
	}
		
	public CacheType getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(CacheType newSpecifiedType) {
		CacheType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		this.getCacheAnnotation().setType(CacheType.toJavaResourceModel(newSpecifiedType));
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedType_(CacheType newSpecifiedType) {
		CacheType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);
	}

	public int getSize() {
		return (this.getSpecifiedSize() == null) ? getDefaultSize() : this.getSpecifiedSize().intValue();
	}

	public int getDefaultSize() {
		return Caching.DEFAULT_SIZE;
	}
	
	public Integer getSpecifiedSize() {
		return this.specifiedSize;
	}

	public void setSpecifiedSize(Integer newSpecifiedSize) {
		Integer oldSpecifiedSize = this.specifiedSize;
		this.specifiedSize = newSpecifiedSize;
		getCacheAnnotation().setSize(newSpecifiedSize);
		firePropertyChanged(SPECIFIED_SIZE_PROPERTY, oldSpecifiedSize, newSpecifiedSize);
	}
	
	protected void setSpecifiedSize_(Integer newSpecifiedSize) {
		Integer oldSpecifiedSize = this.specifiedSize;
		this.specifiedSize = newSpecifiedSize;
		firePropertyChanged(SPECIFIED_SIZE_PROPERTY, oldSpecifiedSize, newSpecifiedSize);
	}


	public boolean isShared() {
		return (this.specifiedShared == null) ? this.isDefaultShared() : this.specifiedShared.booleanValue();
	}
	
	public boolean isDefaultShared() {
		return Caching.DEFAULT_SHARED;
	}
	
	public Boolean getSpecifiedShared() {
		return this.specifiedShared;
	}
	
	public void setSpecifiedShared(Boolean newSpecifiedShared) {
		Boolean oldShared = this.specifiedShared;
		this.specifiedShared = newSpecifiedShared;
		this.getCacheAnnotation().setShared(newSpecifiedShared);
		firePropertyChanged(Caching.SPECIFIED_SHARED_PROPERTY, oldShared, newSpecifiedShared);
		
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
		firePropertyChanged(Caching.SPECIFIED_SHARED_PROPERTY, oldSpecifiedShared, newSpecifiedShared);
	}

	public boolean isAlwaysRefresh() {
		return (this.specifiedAlwaysRefresh == null) ? this.isDefaultAlwaysRefresh() : this.specifiedAlwaysRefresh.booleanValue();
	}
	
	public boolean isDefaultAlwaysRefresh() {
		return Caching.DEFAULT_ALWAYS_REFRESH;
	}
	
	public Boolean getSpecifiedAlwaysRefresh() {
		return this.specifiedAlwaysRefresh;
	}
	
	public void setSpecifiedAlwaysRefresh(Boolean newSpecifiedAlwaysRefresh) {
		Boolean oldAlwaysRefresh = this.specifiedAlwaysRefresh;
		this.specifiedAlwaysRefresh = newSpecifiedAlwaysRefresh;
		this.getCacheAnnotation().setAlwaysRefresh(newSpecifiedAlwaysRefresh);
		firePropertyChanged(Caching.SPECIFIED_ALWAYS_REFRESH_PROPERTY, oldAlwaysRefresh, newSpecifiedAlwaysRefresh);
	}

	protected void setSpecifiedAlwaysRefresh_(Boolean newSpecifiedAlwaysRefresh) {
		Boolean oldAlwaysRefresh = this.specifiedAlwaysRefresh;
		this.specifiedAlwaysRefresh = newSpecifiedAlwaysRefresh;
		firePropertyChanged(Caching.SPECIFIED_ALWAYS_REFRESH_PROPERTY, oldAlwaysRefresh, newSpecifiedAlwaysRefresh);
	}

	public boolean isRefreshOnlyIfNewer() {
		return (this.specifiedRefreshOnlyIfNewer == null) ? this.isDefaultRefreshOnlyIfNewer() : this.specifiedRefreshOnlyIfNewer.booleanValue();
	}
	
	public boolean isDefaultRefreshOnlyIfNewer() {
		return Caching.DEFAULT_REFRESH_ONLY_IF_NEWER;
	}
	
	public Boolean getSpecifiedRefreshOnlyIfNewer() {
		return this.specifiedRefreshOnlyIfNewer;
	}
	
	public void setSpecifiedRefreshOnlyIfNewer(Boolean newSpecifiedRefreshOnlyIfNewer) {
		Boolean oldRefreshOnlyIfNewer = this.specifiedRefreshOnlyIfNewer;
		this.specifiedRefreshOnlyIfNewer = newSpecifiedRefreshOnlyIfNewer;
		this.getCacheAnnotation().setRefreshOnlyIfNewer(newSpecifiedRefreshOnlyIfNewer);
		firePropertyChanged(Caching.SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY, oldRefreshOnlyIfNewer, newSpecifiedRefreshOnlyIfNewer);
	}

	protected void setSpecifiedRefreshOnlyIfNewer_(Boolean newSpecifiedRefreshOnlyIfNewer) {
		Boolean oldRefreshOnlyIfNewer = this.specifiedRefreshOnlyIfNewer;
		this.specifiedRefreshOnlyIfNewer = newSpecifiedRefreshOnlyIfNewer;
		firePropertyChanged(Caching.SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY, oldRefreshOnlyIfNewer, newSpecifiedRefreshOnlyIfNewer);
	}

	public boolean isDisableHits() {
		return (this.specifiedDisableHits == null) ? this.isDefaultDisableHits() : this.specifiedDisableHits.booleanValue();
	}
	
	public boolean isDefaultDisableHits() {
		return Caching.DEFAULT_DISABLE_HITS;
	}
	
	public Boolean getSpecifiedDisableHits() {
		return this.specifiedDisableHits;
	}
	
	public void setSpecifiedDisableHits(Boolean newSpecifiedDisableHits) {
		Boolean oldDisableHits = this.specifiedDisableHits;
		this.specifiedDisableHits = newSpecifiedDisableHits;
		this.getCacheAnnotation().setDisableHits(newSpecifiedDisableHits);
		firePropertyChanged(Caching.SPECIFIED_DISABLE_HITS_PROPERTY, oldDisableHits, newSpecifiedDisableHits);
	}

	protected void setSpecifiedDisableHits_(Boolean newSpecifiedDisableHits) {
		Boolean oldDisableHits = this.specifiedDisableHits;
		this.specifiedDisableHits = newSpecifiedDisableHits;
		firePropertyChanged(Caching.SPECIFIED_DISABLE_HITS_PROPERTY, oldDisableHits, newSpecifiedDisableHits);
	}
	
	public CacheCoordinationType getCoordinationType() {
		return (this.getSpecifiedCoordinationType() == null) ? this.getDefaultCoordinationType() : this.getSpecifiedCoordinationType();
	}

	public CacheCoordinationType getDefaultCoordinationType() {
		return DEFAULT_COORDINATION_TYPE;
	}
		
	public CacheCoordinationType getSpecifiedCoordinationType() {
		return this.specifiedCoordinationType;
	}
	
	public void setSpecifiedCoordinationType(CacheCoordinationType newSpecifiedCoordinationType) {
		CacheCoordinationType oldSpecifiedCoordinationType = this.specifiedCoordinationType;
		this.specifiedCoordinationType = newSpecifiedCoordinationType;
		this.getCacheAnnotation().setCoordinationType(CacheCoordinationType.toJavaResourceModel(newSpecifiedCoordinationType));
		firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, oldSpecifiedCoordinationType, newSpecifiedCoordinationType);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedCoordinationType_(CacheCoordinationType newSpecifiedCoordinationType) {
		CacheCoordinationType oldSpecifiedCoordinationType = this.specifiedCoordinationType;
		this.specifiedCoordinationType = newSpecifiedCoordinationType;
		firePropertyChanged(SPECIFIED_COORDINATION_TYPE_PROPERTY, oldSpecifiedCoordinationType, newSpecifiedCoordinationType);
	}
	
	public boolean hasExistenceChecking() {
		return this.existenceChecking;
	}
	
	public void setExistenceChecking(boolean newExistenceChecking) {
		boolean oldExistenceChecking = this.existenceChecking;
		this.existenceChecking = newExistenceChecking;
		if (newExistenceChecking) {
			this.resourcePersistentType.addSupportingAnnotation(getExistenceCheckingAnnotationName());
		}
		else {
			this.resourcePersistentType.removeSupportingAnnotation(getExistenceCheckingAnnotationName());
		}
		firePropertyChanged(EXISTENCE_CHECKING_PROPERTY, oldExistenceChecking, newExistenceChecking);
		setDefaultExistenceType(caclulateDefaultExistenceType());
	}
	
	protected void setExistenceChecking_(boolean newExistenceChecking) {
		boolean oldExistenceChecking = this.existenceChecking;
		this.existenceChecking = newExistenceChecking;
		firePropertyChanged(EXISTENCE_CHECKING_PROPERTY, oldExistenceChecking, newExistenceChecking);
	}
	
	protected ExistenceType caclulateDefaultExistenceType() {
		if (hasExistenceChecking()) {
			return ExistenceType.CHECK_CACHE;
		}
		return DEFAULT_EXISTENCE_TYPE;
	}
	
	public ExistenceType getExistenceType() {
		return (this.getSpecifiedExistenceType() == null) ? this.getDefaultExistenceType() : this.getSpecifiedExistenceType();
	}

	public ExistenceType getDefaultExistenceType() {
		return this.defaultExistenceType;
	}
	
	protected void setDefaultExistenceType(ExistenceType newDefaultExistenceType) {
		ExistenceType oldDefaultExistenceType = this.defaultExistenceType;
		this.defaultExistenceType = newDefaultExistenceType;
		firePropertyChanged(DEFAULT_EXISTENCE_TYPE_PROPERTY, oldDefaultExistenceType, newDefaultExistenceType);
	}
	
	public ExistenceType getSpecifiedExistenceType() {
		return this.specifiedExistenceType;
	}
	
	public void setSpecifiedExistenceType(ExistenceType newSpecifiedExistenceType) {
		if (!hasExistenceChecking()) {
			if (newSpecifiedExistenceType != null) {
				setExistenceChecking(true);
			}
			else {
				return;
			}
		}
		ExistenceType oldSpecifiedExistenceType = this.specifiedExistenceType;
		this.specifiedExistenceType = newSpecifiedExistenceType;
		this.getExistenceCheckingAnnotation().setValue(ExistenceType.toJavaResourceModel(newSpecifiedExistenceType));
		firePropertyChanged(SPECIFIED_EXISTENCE_TYPE_PROPERTY, oldSpecifiedExistenceType, newSpecifiedExistenceType);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedExistenceType_(ExistenceType newSpecifiedExistenceType) {
		ExistenceType oldSpecifiedExistenceType = this.specifiedExistenceType;
		this.specifiedExistenceType = newSpecifiedExistenceType;
		firePropertyChanged(SPECIFIED_EXISTENCE_TYPE_PROPERTY, oldSpecifiedExistenceType, newSpecifiedExistenceType);
	}

	public Integer getExpiry() {
		return this.expiry;
	}
	
	public void setExpiry(Integer newExpiry) {
		Integer oldExpiry = this.expiry;
		this.expiry = newExpiry;
		getCacheAnnotation().setExpiry(newExpiry);
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
	
	public ExpiryTimeOfDay getExpiryTimeOfDay() {
		return this.expiryTimeOfDay;
	}
	
	public ExpiryTimeOfDay addExpiryTimeOfDay() {
		if (this.expiryTimeOfDay != null) {
			throw new IllegalStateException("expiryTimeOfDay already exists, use getExpiryTimeOfDay()"); //$NON-NLS-1$
		}
		if (this.resourcePersistentType.getSupportingAnnotation(getCacheAnnotationName()) == null) {
			this.resourcePersistentType.addSupportingAnnotation(getCacheAnnotationName());
		}
		EclipseLinkJavaExpiryTimeOfDay newExpiryTimeOfDay = new EclipseLinkJavaExpiryTimeOfDay(this);
		this.expiryTimeOfDay = newExpiryTimeOfDay;
		TimeOfDayAnnotation timeOfDayAnnotation = getCacheAnnotation().addExpiryTimeOfDay();
		newExpiryTimeOfDay.initialize(timeOfDayAnnotation);
		firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, null, newExpiryTimeOfDay);
		setExpiry(null);
		return newExpiryTimeOfDay;
	}
	
	public void removeExpiryTimeOfDay() {
		if (this.expiryTimeOfDay == null) {
			throw new IllegalStateException("timeOfDayExpiry does not exist"); //$NON-NLS-1$
		}
		ExpiryTimeOfDay oldExpiryTimeOfDay = this.expiryTimeOfDay;
		this.expiryTimeOfDay = null;
		getCacheAnnotation().removeExpiryTimeOfDay();
		firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, oldExpiryTimeOfDay, null);
	}
	
	protected void setExpiryTimeOfDay(EclipseLinkJavaExpiryTimeOfDay newExpiryTimeOfDay) {
		EclipseLinkJavaExpiryTimeOfDay oldExpiryTimeOfDay = this.expiryTimeOfDay;
		this.expiryTimeOfDay = newExpiryTimeOfDay;
		firePropertyChanged(EXPIRY_TIME_OF_DAY_PROPERTY, oldExpiryTimeOfDay, newExpiryTimeOfDay);
	}
	
	public void initialize(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		initialize(getCacheAnnotation());
		initialize(getExistenceCheckingAnnotation());
	}

	protected void initialize(CacheAnnotation cache) {
		this.specifiedType = this.specifiedType(cache);
		this.specifiedSize = this.specifiedSize(cache);
		this.specifiedShared = this.specifiedShared(cache);
		this.specifiedAlwaysRefresh = this.specifiedAlwaysRefresh(cache);
		this.specifiedRefreshOnlyIfNewer = this.specifiedRefreshOnlyIfNewer(cache);
		this.specifiedDisableHits = this.specifiedDisableHits(cache);
		this.specifiedCoordinationType = this.specifiedCoordinationType(cache);
		this.initializeExpiry(cache);
	}
	
	protected void initialize(ExistenceCheckingAnnotation existenceChecking) {
		this.existenceChecking = existenceChecking != null;
		this.specifiedExistenceType = specifiedExistenceType(existenceChecking);
		this.defaultExistenceType = this.caclulateDefaultExistenceType();
	}

	protected void initializeExpiry(CacheAnnotation cache) {
		if (cache.getExpiryTimeOfDay() == null) {
			this.expiry = cache.getExpiry();
		}
		else {
			if (cache.getExpiry() == null) { //handle with validation if both expiry and expiryTimeOfDay are set
				this.expiryTimeOfDay = new EclipseLinkJavaExpiryTimeOfDay(this);
				this.expiryTimeOfDay.initialize(cache.getExpiryTimeOfDay());
			}
		}
	}
	
	public void update(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		update(getCacheAnnotation());
		update(getExistenceCheckingAnnotation());
		updateExpiry(getCacheAnnotation());
	}
	
	protected void update(CacheAnnotation cache) {
		setSpecifiedType_(this.specifiedType(cache));
		setSpecifiedSize_(this.specifiedSize(cache));
		setSpecifiedShared_(this.specifiedShared(cache));
		setSpecifiedAlwaysRefresh_(this.specifiedAlwaysRefresh(cache));
		setSpecifiedRefreshOnlyIfNewer_(this.specifiedRefreshOnlyIfNewer(cache));
		setSpecifiedDisableHits_(this.specifiedDisableHits(cache));
		setSpecifiedCoordinationType_(this.specifiedCoordinationType(cache));
	}

	protected void update(ExistenceCheckingAnnotation existenceChecking) {
		setExistenceChecking_(existenceChecking != null);
		setSpecifiedExistenceType_(specifiedExistenceType(existenceChecking));
		setDefaultExistenceType(caclulateDefaultExistenceType());
	}
	
	protected void updateExpiry(CacheAnnotation cache) {
		if (cache.getExpiryTimeOfDay() == null) {
			setExpiryTimeOfDay(null);
			setExpiry_(cache.getExpiry());
		}
		else {
			if (this.expiryTimeOfDay != null) {
				this.expiryTimeOfDay.update(cache.getExpiryTimeOfDay());
			}
			else if (cache.getExpiry() == null){
				setExpiryTimeOfDay(new EclipseLinkJavaExpiryTimeOfDay(this));
				this.expiryTimeOfDay.initialize(cache.getExpiryTimeOfDay());
			}
			else { //handle with validation if both expiry and expiryTimeOfDay are set
				setExpiryTimeOfDay(null);
			}
		}
	}

	protected CacheType specifiedType(CacheAnnotation cache) {
		return CacheType.fromJavaResourceModel(cache.getType());
	}

	protected Integer specifiedSize(CacheAnnotation cache) {
		return cache.getSize();
	}
	
	protected Boolean specifiedShared(CacheAnnotation cache) {
		return cache.getShared();
	}	
	
	protected Boolean specifiedAlwaysRefresh(CacheAnnotation cache) {
		return cache.getAlwaysRefresh();
	}	
	
	protected Boolean specifiedRefreshOnlyIfNewer(CacheAnnotation cache) {
		return cache.getRefreshOnlyIfNewer();
	}	
	
	protected Boolean specifiedDisableHits(CacheAnnotation cache) {
		return cache.getDisableHits();
	}
	
	protected CacheCoordinationType specifiedCoordinationType(CacheAnnotation cache) {
		return CacheCoordinationType.fromJavaResourceModel(cache.getCoordinationType());
	}
	
	protected Integer expiry(CacheAnnotation cache) {
		return cache.getExpiry();
	}
	
	protected ExistenceType specifiedExistenceType(ExistenceCheckingAnnotation existenceChecking) {
		if (existenceChecking == null) {
			return null;
		}
		return ExistenceType.fromJavaResourceModel(existenceChecking.getValue());
	}


	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getCacheAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.validateExpiry(messages, astRoot);
	}

	protected void validateExpiry(List<IMessage> messages, CompilationUnit astRoot) {
		CacheAnnotation cache = getCacheAnnotation();
		if (cache.getExpiry() != null && cache.getExpiryTimeOfDay() != null) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CACHE_EXPIRY_AND_EXPIRY_TIME_OF_DAY_BOTH_SPECIFIED,
					new String[] {this.getParent().getPersistentType().getName()},
					this, 
					getValidationTextRange(astRoot)
				)
			);
		}
	}
}
