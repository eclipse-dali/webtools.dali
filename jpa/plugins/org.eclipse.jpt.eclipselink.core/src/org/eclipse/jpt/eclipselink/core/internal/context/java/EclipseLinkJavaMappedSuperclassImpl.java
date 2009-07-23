/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.java.AbstractJavaMappedSuperclass;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConverterHolder;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaMappedSuperclassImpl extends AbstractJavaMappedSuperclass implements EclipseLinkJavaMappedSuperclass
{
	protected EclipseLinkJavaCaching eclipseLinkCaching;
	
	protected final EclipseLinkJavaConverterHolder converterHolder;
	
	protected final EclipseLinkJavaReadOnly readOnly;

	protected final EclipseLinkJavaCustomizer customizer;

	protected final EclipseLinkJavaChangeTracking changeTracking;

	public EclipseLinkJavaMappedSuperclassImpl(JavaPersistentType parent) {
		super(parent);
		this.eclipseLinkCaching = new EclipseLinkJavaCachingImpl(this);
		this.converterHolder = new EclipseLinkJavaConverterHolderImpl(this);
		this.readOnly = new EclipseLinkJavaReadOnly(this);
		this.customizer = new EclipseLinkJavaCustomizer(this);
		this.changeTracking = new EclipseLinkJavaChangeTracking(this);
	}
	
	public EclipseLinkJavaCaching getCaching() {
		return this.eclipseLinkCaching;
	}
	
	public EclipseLinkJavaConverterHolder getConverterHolder() {
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
}
