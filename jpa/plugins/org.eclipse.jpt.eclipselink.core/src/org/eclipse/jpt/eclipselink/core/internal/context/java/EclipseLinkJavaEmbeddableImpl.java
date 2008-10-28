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
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEmbeddable;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaEmbeddableImpl extends GenericJavaEmbeddable implements EclipseLinkJavaEmbeddable
{
	
	protected final JavaConverterHolder converterHolder;
	
	protected final EclipseLinkJavaCustomizer customizer;
	
	protected final EclipseLinkJavaChangeTracking changeTracking;
	
	public EclipseLinkJavaEmbeddableImpl(JavaPersistentType parent) {
		super(parent);
		this.converterHolder = new EclipseLinkJavaConverterHolder(this);
		this.customizer = new EclipseLinkJavaCustomizer(this);
		this.changeTracking = new EclipseLinkJavaChangeTracking(this);
	}
	
	public JavaConverterHolder getConverterHolder() {
		return this.converterHolder;
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
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.converterHolder.validate(messages, astRoot);
		this.customizer.validate(messages, astRoot);
		this.changeTracking.validate(messages, astRoot);
	}
}
