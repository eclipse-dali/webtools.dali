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
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.resource.java.ConvertAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaBasicMappingImpl
	extends AbstractJavaBasicMapping 
	implements EclipseLinkBasicMapping
{
	
	protected final EclipseLinkJavaMutable mutable;
	
	public EclipseLinkJavaBasicMappingImpl(JavaPersistentAttribute parent) {
		super(parent);
		this.mutable = new EclipseLinkJavaMutable(this);
	}

	@Override
	protected JavaConverter buildSpecifiedConverter(String converterType) {
		JavaConverter javaConverter = super.buildSpecifiedConverter(converterType);
		if (javaConverter != null) {
			return javaConverter;
		}
		if (converterType == Convert.ECLIPSE_LINK_CONVERTER) {
			return new EclipseLinkJavaConvert(this, this.resourcePersistentAttribute);
		}
		return null;
	}
	
	@Override
	protected String getResourceConverterType() {
		//check @Convert first, this is the order that EclipseLink searches
		if (this.resourcePersistentAttribute.getSupportingAnnotation(ConvertAnnotation.ANNOTATION_NAME) != null) {
			return Convert.ECLIPSE_LINK_CONVERTER;
		}
		return super.getResourceConverterType();
	}
	
	//************ EclipselinkJavaBasicMapping implementation ****************
	
	public Mutable getMutable() {
		return this.mutable;
	}

	
	//************ initialization/update ****************

	@Override
	protected void initialize() {
		super.initialize();
		this.mutable.initialize(this.resourcePersistentAttribute);
	}
	
	@Override
	protected void update() {
		super.update();
		this.mutable.update(this.resourcePersistentAttribute);
	}
	
	
	//************ validation ****************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.mutable.validate(messages, reporter, astRoot);
	}
}
