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
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConverterHolder;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaEmbeddableImpl extends AbstractJavaEmbeddable implements EclipseLinkJavaEmbeddable
{
	
	protected final EclipseLinkJavaConverterHolder converterHolder;
	
	protected final EclipseLinkJavaCustomizer customizer;
	
	protected final EclipseLinkJavaChangeTracking changeTracking;
	
	public EclipseLinkJavaEmbeddableImpl(JavaPersistentType parent) {
		super(parent);
		this.converterHolder = new EclipseLinkJavaConverterHolderImpl(this);
		this.customizer = new EclipseLinkJavaCustomizer(this);
		this.changeTracking = new EclipseLinkJavaChangeTracking(this);
	}
	
	public EclipseLinkJavaConverterHolder getConverterHolder() {
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
