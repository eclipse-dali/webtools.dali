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
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaMappedSuperclassImpl extends AbstractJavaMappedSuperclass implements EclipseLinkJavaMappedSuperclass
{
	protected JavaCaching eclipseLinkCaching;
	
	protected final JavaConverterHolder converterHolder;
	
	protected final EclipseLinkJavaReadOnly readOnly;

	protected final EclipseLinkJavaCustomizer customizer;

	protected final EclipseLinkJavaChangeTracking changeTracking;

	public EclipseLinkJavaMappedSuperclassImpl(JavaPersistentType parent) {
		super(parent);
		this.eclipseLinkCaching = new EclipseLinkJavaCaching(this);
		this.converterHolder = new EclipseLinkJavaConverterHolder(this);
		this.readOnly = new EclipseLinkJavaReadOnly(this);
		this.customizer = new EclipseLinkJavaCustomizer(this);
		this.changeTracking = new EclipseLinkJavaChangeTracking(this);
	}
	
	public JavaCaching getCaching() {
		return this.eclipseLinkCaching;
	}
	
	public JavaConverterHolder getConverterHolder() {
		return this.converterHolder;
	}
	
	public ReadOnly getReadOnly() {
		return this.readOnly;
	}
	
	public Customizer getCustomizer() {
		return this.customizer;
	}
	
	public ChangeTracking getChangeTracking() {
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
