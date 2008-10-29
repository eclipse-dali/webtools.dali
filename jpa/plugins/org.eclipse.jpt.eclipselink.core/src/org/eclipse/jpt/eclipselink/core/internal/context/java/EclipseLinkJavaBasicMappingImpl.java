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
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.GenericJavaBasicMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.resource.java.ConvertAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaBasicMappingImpl extends GenericJavaBasicMapping implements EclipseLinkBasicMapping
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
	protected String specifiedConverterType(JavaResourcePersistentAttribute jrpa) {
		//check @Convert first, this is the order that EclipseLink searches
		if (jrpa.getSupportingAnnotation(ConvertAnnotation.ANNOTATION_NAME) != null) {
			return Convert.ECLIPSE_LINK_CONVERTER;
		}
		return super.specifiedConverterType(jrpa);
	}
	
	//************ EclipselinkJavaBasicMapping implementation ****************
	
	public Mutable getMutable() {
		return this.mutable;
	}

	
	//************ initialization/update ****************

	@Override
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		super.initialize(jrpa);
		this.mutable.initialize(jrpa);
	}
	
	@Override
	public void update(JavaResourcePersistentAttribute jrpa) {
		super.update(jrpa);
		this.mutable.update(jrpa);
	}
	
	
	//************ validation ****************
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.mutable.validate(messages, astRoot);
	}
}
