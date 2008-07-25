/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractJavaResourceNode;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheType;

public class NullCacheAnnotation extends AbstractJavaResourceNode implements CacheAnnotation, Annotation
{
	protected NullCacheAnnotation(JavaResourcePersistentMember parent) {
		super(parent);
	}
	
	@Override
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}
	
	public String getAnnotationName() {
		return CacheAnnotation.ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		//null, nothing to initialize
	}
	
	public org.eclipse.jdt.core.dom.Annotation getJdtAnnotation(CompilationUnit astRoot) {
		return null;
	}
	
	public void newAnnotation() {
		throw new UnsupportedOperationException();
	}
	
	public void removeAnnotation() {
		throw new UnsupportedOperationException();
	}
	
	protected CacheAnnotation createCacheResource() {
		return (CacheAnnotation) getParent().addAnnotation(getAnnotationName());
	}
	
	public CacheType getType() {
		return null;
	}
	
	public void setType(CacheType type) {
		if (type != null) {
			createCacheResource().setType(type);
		}
	}

	public Boolean getShared() {
		return null;
	}
	
	public void setShared(Boolean shared) {
		if (shared != null) {
			createCacheResource().setShared(shared);
		}
	}
	
	public Boolean getAlwaysRefresh() {
		return null;
	}
	
	public void setAlwaysRefresh(Boolean alwaysRefresh) {
		if (alwaysRefresh != null) {
			createCacheResource().setAlwaysRefresh(alwaysRefresh);
		}
	}
	
	public Boolean getRefreshOnlyIfNewer() {
		return null;
	}
	
	public void setRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer) {
		if (refreshOnlyIfNewer != null) {
			createCacheResource().setRefreshOnlyIfNewer(refreshOnlyIfNewer);
		}
	}
	
	public Boolean getDisableHits() {
		return null;
	}
	
	public void setDisableHits(Boolean disableHits) {
		if (disableHits != null) {
			createCacheResource().setDisableHits(disableHits);
		}
	}
	
	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getSharedTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
