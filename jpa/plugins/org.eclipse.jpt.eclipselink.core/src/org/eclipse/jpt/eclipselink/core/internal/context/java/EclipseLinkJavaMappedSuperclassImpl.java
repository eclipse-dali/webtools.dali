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
import org.eclipse.jpt.core.internal.context.java.GenericJavaMappedSuperclass;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCustomizer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaReadOnly;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaMappedSuperclassImpl extends GenericJavaMappedSuperclass implements EclipseLinkJavaMappedSuperclass
{
	protected JavaCaching eclipseLinkCaching;
	
	protected final JavaConverterHolder converterHolder;
	
	protected final JavaReadOnly readOnly;

	protected final JavaCustomizer customizer;

	protected final JavaChangeTracking changeTracking;

	public EclipseLinkJavaMappedSuperclassImpl(JavaPersistentType parent) {
		super(parent);
		this.eclipseLinkCaching = getJpaFactory().buildJavaCaching(this);
		this.converterHolder = getJpaFactory().buildJavaConverterHolder(this);
		this.readOnly = getJpaFactory().buildJavaReadOnly(this);
		this.customizer = getJpaFactory().buildJavaCustomizer(this);
		this.changeTracking = getJpaFactory().buildJavaChangeTracking(this);
	}
	
	public JavaCaching getCaching() {
		return this.eclipseLinkCaching;
	}
	
	public JavaConverterHolder getConverterHolder() {
		return this.converterHolder;
	}
	
	public JavaReadOnly getReadOnly() {
		return this.readOnly;
	}
	
	public JavaCustomizer getCustomizer() {
		return this.customizer;
	}
	
	public JavaChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
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
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.eclipseLinkCaching.validate(messages, astRoot);
		this.converterHolder.validate(messages, astRoot);
		this.readOnly.validate(messages, astRoot);
		this.customizer.validate(messages, astRoot);
		this.changeTracking.validate(messages, astRoot);
	}
}
