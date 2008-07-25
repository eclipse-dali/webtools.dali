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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.CacheType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheAnnotation;

public class EclipseLinkJavaCachingImpl extends AbstractJavaJpaContextNode implements EclipseLinkJavaCaching
{
	
	protected CacheType specifiedCacheType;
	protected Boolean specifiedShared;
	protected Boolean specifiedAlwaysRefresh;
	protected Boolean specifiedRefreshOnlyIfNewer;
	protected Boolean specifiedDisableHits;
	
	protected JavaResourcePersistentType resourcePersistentType;
	
	public EclipseLinkJavaCachingImpl(JavaTypeMapping parent) {
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
		return (CacheAnnotation) this.resourcePersistentType.getNonNullAnnotation(getCacheAnnotationName());
	}

	protected String getCacheAnnotationName() {
		return CacheAnnotation.ANNOTATION_NAME;
	}
	
	
	public CacheType getCacheType() {
		return (this.getSpecifiedCacheType() == null) ? this.getDefaultCacheType() : this.getSpecifiedCacheType();
	}

	public CacheType getDefaultCacheType() {
		return DEFAULT_CACHE_TYPE;
	}
		
	public CacheType getSpecifiedCacheType() {
		return this.specifiedCacheType;
	}
	
	public void setSpecifiedCacheType(CacheType newSpecifiedCacheType) {
		CacheType oldCacheType = this.specifiedCacheType;
		this.specifiedCacheType = newSpecifiedCacheType;
		this.getCacheAnnotation().setType(CacheType.toJavaResourceModel(newSpecifiedCacheType));
		firePropertyChanged(SPECIFIED_CACHE_TYPE_PROPERTY, oldCacheType, newSpecifiedCacheType);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedCacheType_(CacheType newSpecifiedCacheType) {
		CacheType oldCacheType = this.specifiedCacheType;
		this.specifiedCacheType = newSpecifiedCacheType;
		firePropertyChanged(SPECIFIED_CACHE_TYPE_PROPERTY, oldCacheType, newSpecifiedCacheType);
	}

	public Boolean getShared() {
		return (this.getSpecifiedShared() == null) ? this.getDefaultShared() : this.getSpecifiedShared();
	}
	
	public Boolean getDefaultShared() {
		return EclipseLinkCaching.DEFAULT_SHARED;
	}
	
	public Boolean getSpecifiedShared() {
		return this.specifiedShared;
	}
	
	//TODO when this is set to false need to set all other caching option except existence checking to their default
	public void setSpecifiedShared(Boolean newSpecifiedShared) {
		Boolean oldShared = this.specifiedShared;
		this.specifiedShared = newSpecifiedShared;
		this.getCacheAnnotation().setShared(newSpecifiedShared);
		firePropertyChanged(EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY, oldShared, newSpecifiedShared);
	}

	protected void setSpecifiedShared_(Boolean newSpecifiedShared) {
		Boolean oldShared = this.specifiedShared;
		this.specifiedShared = newSpecifiedShared;
		firePropertyChanged(EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY, oldShared, newSpecifiedShared);
	}

	public Boolean getAlwaysRefresh() {
		return (this.getSpecifiedAlwaysRefresh() == null) ? this.getDefaultAlwaysRefresh() : this.getSpecifiedAlwaysRefresh();
	}
	
	public Boolean getDefaultAlwaysRefresh() {
		return EclipseLinkCaching.DEFAULT_ALWAYS_REFRESH;
	}
	
	public Boolean getSpecifiedAlwaysRefresh() {
		return this.specifiedAlwaysRefresh;
	}
	
	public void setSpecifiedAlwaysRefresh(Boolean newSpecifiedAlwaysRefresh) {
		Boolean oldAlwaysRefresh = this.specifiedAlwaysRefresh;
		this.specifiedAlwaysRefresh = newSpecifiedAlwaysRefresh;
		this.getCacheAnnotation().setAlwaysRefresh(newSpecifiedAlwaysRefresh);
		firePropertyChanged(EclipseLinkCaching.SPECIFIED_ALWAYS_REFRESH_PROPERTY, oldAlwaysRefresh, newSpecifiedAlwaysRefresh);
	}

	protected void setSpecifiedAlwaysRefresh_(Boolean newSpecifiedAlwaysRefresh) {
		Boolean oldAlwaysRefresh = this.specifiedAlwaysRefresh;
		this.specifiedAlwaysRefresh = newSpecifiedAlwaysRefresh;
		firePropertyChanged(EclipseLinkCaching.SPECIFIED_ALWAYS_REFRESH_PROPERTY, oldAlwaysRefresh, newSpecifiedAlwaysRefresh);
	}

	public Boolean getRefreshOnlyIfNewer() {
		return (this.getSpecifiedRefreshOnlyIfNewer() == null) ? this.getDefaultRefreshOnlyIfNewer() : this.getSpecifiedRefreshOnlyIfNewer();
	}
	
	public Boolean getDefaultRefreshOnlyIfNewer() {
		return EclipseLinkCaching.DEFAULT_REFRESH_ONLY_IF_NEWER;
	}
	
	public Boolean getSpecifiedRefreshOnlyIfNewer() {
		return this.specifiedRefreshOnlyIfNewer;
	}
	
	public void setSpecifiedRefreshOnlyIfNewer(Boolean newSpecifiedRefreshOnlyIfNewer) {
		Boolean oldRefreshOnlyIfNewer = this.specifiedRefreshOnlyIfNewer;
		this.specifiedRefreshOnlyIfNewer = newSpecifiedRefreshOnlyIfNewer;
		this.getCacheAnnotation().setRefreshOnlyIfNewer(newSpecifiedRefreshOnlyIfNewer);
		firePropertyChanged(EclipseLinkCaching.SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY, oldRefreshOnlyIfNewer, newSpecifiedRefreshOnlyIfNewer);
	}

	protected void setSpecifiedRefreshOnlyIfNewer_(Boolean newSpecifiedRefreshOnlyIfNewer) {
		Boolean oldRefreshOnlyIfNewer = this.specifiedRefreshOnlyIfNewer;
		this.specifiedRefreshOnlyIfNewer = newSpecifiedRefreshOnlyIfNewer;
		firePropertyChanged(EclipseLinkCaching.SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY, oldRefreshOnlyIfNewer, newSpecifiedRefreshOnlyIfNewer);
	}

	public Boolean getDisableHits() {
		return (this.getSpecifiedDisableHits() == null) ? this.getDefaultDisableHits() : this.getSpecifiedDisableHits();
	}
	
	public Boolean getDefaultDisableHits() {
		return EclipseLinkCaching.DEFAULT_DISABLE_HITS;
	}
	
	public Boolean getSpecifiedDisableHits() {
		return this.specifiedDisableHits;
	}
	
	public void setSpecifiedDisableHits(Boolean newSpecifiedDisableHits) {
		Boolean oldDisableHits = this.specifiedDisableHits;
		this.specifiedDisableHits = newSpecifiedDisableHits;
		this.getCacheAnnotation().setDisableHits(newSpecifiedDisableHits);
		firePropertyChanged(EclipseLinkCaching.SPECIFIED_DISABLE_HITS_PROPERTY, oldDisableHits, newSpecifiedDisableHits);
	}

	protected void setSpecifiedDisableHits_(Boolean newSpecifiedDisableHits) {
		Boolean oldDisableHits = this.specifiedDisableHits;
		this.specifiedDisableHits = newSpecifiedDisableHits;
		firePropertyChanged(EclipseLinkCaching.SPECIFIED_DISABLE_HITS_PROPERTY, oldDisableHits, newSpecifiedDisableHits);
	}
	
	public void initialize(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		initializeFromResource(getCacheAnnotation());	
	}

	protected void initializeFromResource(CacheAnnotation cache) {
		this.specifiedCacheType = this.specifiedCacheType(cache);
		this.specifiedShared = this.specifiedShared(cache);
		this.specifiedAlwaysRefresh = this.specifiedAlwaysRefresh(cache);
		this.specifiedRefreshOnlyIfNewer = this.specifiedRefreshOnlyIfNewer(cache);
		this.specifiedDisableHits = this.specifiedDisableHits(cache);
	}
	
	public void update(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		update(getCacheAnnotation());
	}
	
	protected void update(CacheAnnotation cache) {
		setSpecifiedCacheType_(this.specifiedCacheType(cache));
		setSpecifiedShared_(this.specifiedShared(cache));
		setSpecifiedAlwaysRefresh_(this.specifiedAlwaysRefresh(cache));
		setSpecifiedRefreshOnlyIfNewer_(this.specifiedRefreshOnlyIfNewer(cache));
		setSpecifiedDisableHits_(this.specifiedDisableHits(cache));
	}

	protected CacheType specifiedCacheType(CacheAnnotation cache) {
		return CacheType.fromJavaResourceModel(cache.getType());
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
	

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = getCacheAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

}
