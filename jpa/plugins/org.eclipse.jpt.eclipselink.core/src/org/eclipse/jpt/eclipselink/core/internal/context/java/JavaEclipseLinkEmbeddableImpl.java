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
import org.eclipse.jpt.core.internal.context.java.AbstractJavaEmbeddable;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEmbeddable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkEmbeddableImpl extends AbstractJavaEmbeddable implements JavaEclipseLinkEmbeddable
{
	
	protected final JavaEclipseLinkConverterHolder converterHolder;
	
	protected final JavaEclipseLinkCustomizer customizer;
	
	protected final JavaEclipseLinkChangeTracking changeTracking;
	
	public JavaEclipseLinkEmbeddableImpl(JavaPersistentType parent) {
		super(parent);
		this.converterHolder = new JavaEclipseLinkConverterHolderImpl(this);
		this.customizer = new JavaEclipseLinkCustomizer(this);
		this.changeTracking = new JavaEclipseLinkChangeTracking(this);
	}
	
	public JavaEclipseLinkConverterHolder getConverterHolder() {
		return this.converterHolder;
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
		this.converterHolder.initialize(jrpt);
		this.customizer.initialize(jrpt);
		this.changeTracking.initialize(jrpt);
	}
	
	@Override
	public void update(JavaResourcePersistentType jrpt) {
		super.update(jrpt);
		this.converterHolder.update(jrpt);
		this.customizer.update(jrpt);
		this.changeTracking.update(jrpt);
	}
	
	
	//********** Validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.converterHolder.validate(messages, reporter, astRoot);
		this.customizer.validate(messages, reporter, astRoot);
		this.changeTracking.validate(messages, reporter, astRoot);
	}
}
