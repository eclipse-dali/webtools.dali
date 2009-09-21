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

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaIdMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConvertAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkIdMapping 
	extends AbstractJavaIdMapping
	implements EclipseLinkIdMapping
{
	protected final JavaEclipseLinkMutable mutable;
	
	public JavaEclipseLinkIdMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.mutable = new JavaEclipseLinkMutable(this);
	}
	
	//************** JavaAttributeMapping implementation ***************
	
	@Override
	protected String[] buildSupportingAnnotationNames() {
		return ArrayTools.addAll(
			super.buildSupportingAnnotationNames(),
			EclipseLink.MUTABLE,
			EclipseLink.CONVERT);
	}		

	
	//************** AbstractJavaIdMapping overrides ***************

	@Override
	protected JavaConverter buildSpecifiedConverter(String converterType) {
		JavaConverter javaConverter = super.buildSpecifiedConverter(converterType);
		if (javaConverter != null) {
			return javaConverter;
		}
		if (converterType == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) {
			return new JavaEclipseLinkConvert(this, this.resourcePersistentAttribute);
		}
		return null;
	}
	
	@Override
	protected String getResourceConverterType() {
		//check @Convert first, this is the order that EclipseLink searches
		if (this.resourcePersistentAttribute.getAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkConvert.ECLIPSE_LINK_CONVERTER;
		}
		return super.getResourceConverterType();
	}
	
	
	//************ EclipselinkIdMapping implementation ****************
	
	public EclipseLinkMutable getMutable() {
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
	
	// ********** code assist **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (getConverter() != null) {
			result = getConverter().javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	//************ validation ****************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.mutable.validate(messages, reporter, astRoot);
	}
}
