/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaMappedSuperclass;
import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkMappedSuperclassImpl
	extends AbstractJavaMappedSuperclass
	implements JavaEclipseLinkMappedSuperclass, CacheableHolder2_0
{
	protected JavaEclipseLinkCaching eclipseLinkCaching;
	
	protected final JavaEclipseLinkConverterHolder converterHolder;
	
	protected final JavaEclipseLinkReadOnly readOnly;
	
	protected final JavaEclipseLinkCustomizer customizer;
	
	protected final JavaEclipseLinkChangeTracking changeTracking;
	
	
	public JavaEclipseLinkMappedSuperclassImpl(JavaPersistentType parent) {
		super(parent);
		this.eclipseLinkCaching = new JavaEclipseLinkCachingImpl(this);
		this.converterHolder = new JavaEclipseLinkConverterHolderImpl(this);
		this.readOnly = new JavaEclipseLinkReadOnly(this);
		this.customizer = new JavaEclipseLinkCustomizer(this);
		this.changeTracking = new JavaEclipseLinkChangeTracking(this);
	}
	
	
	public boolean usesPrimaryKeyColumns() {
		return getResourcePersistentType().getAnnotation(EclipseLink.PRIMARY_KEY) != null;
	}
	
	public JavaEclipseLinkCaching getCaching() {
		return this.eclipseLinkCaching;
	}
	
	public JavaEclipseLinkConverterHolder getConverterHolder() {
		return this.converterHolder;
	}
	
	public EclipseLinkReadOnly getReadOnly() {
		return this.readOnly;
	}
	
	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}
	
	public EclipseLinkChangeTracking getChangeTracking() {
		return this.changeTracking;
	}
	
	public Cacheable2_0 getCacheable() {
		return ((CacheableHolder2_0) getCaching()).getCacheable();
	}
	
	public boolean calculateDefaultCacheable() {
		return ((CacheableHolder2_0) getCaching()).calculateDefaultCacheable();
	}
	
	@Override
	public void initialize(JavaResourcePersistentType jrpt) {
		super.initialize(jrpt);
		this.eclipseLinkCaching.initialize(jrpt);
		this.converterHolder.initialize(jrpt);
		this.readOnly.initialize(jrpt);
		this.customizer.initialize(jrpt);
		this.changeTracking.initialize(jrpt);
	}
	
	@Override
	public void update(JavaResourcePersistentType jrpt) {
		super.update(jrpt);
		this.eclipseLinkCaching.update(jrpt);
		this.converterHolder.update(jrpt);
		this.readOnly.update(jrpt);
		this.customizer.update(jrpt);
		this.changeTracking.update(jrpt);
	}
	
	
	//********** Validation ********************************************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.eclipseLinkCaching.validate(messages, reporter, astRoot);
		this.converterHolder.validate(messages, reporter, astRoot);
		this.readOnly.validate(messages, reporter, astRoot);
		this.customizer.validate(messages, reporter, astRoot);
		this.changeTracking.validate(messages, reporter, astRoot);
	}
	
	@Override
	protected JptValidator buildPrimaryKeyValidator(CompilationUnit astRoot) {
		return new EclipseLinkMappedSuperclassPrimaryKeyValidator(this, buildTextRangeResolver(astRoot));
	}
}
