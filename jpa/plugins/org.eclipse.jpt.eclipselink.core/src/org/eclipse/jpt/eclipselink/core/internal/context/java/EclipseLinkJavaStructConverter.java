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

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.StructConverterAnnotation;

public class EclipseLinkJavaStructConverter extends EclipseLinkJavaConverter
	implements StructConverter
{	
	private String converterClass;
	
	
	public EclipseLinkJavaStructConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		super(parent);
		this.initialize(jrpm);
	}
	
	
	public String getType() {
		return EclipseLinkConverter.STRUCT_CONVERTER;
	}
	
	@Override
	public String getAnnotationName() {
		return StructConverterAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected StructConverterAnnotation getAnnotation() {
		return (StructConverterAnnotation) super.getAnnotation();
	}
	
	
	// **************** converter class ****************************************
	
	public String getConverterClass() {
		return this.converterClass;
	}
	
	public void setConverterClass(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		getAnnotation().setConverter(newConverterClass);
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}
	
	protected void setConverterClass_(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}
	
	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(JavaResourcePersistentMember jrpm) {
		super.initialize(jrpm);
		this.converterClass = this.converterClass(getAnnotation());
	}
	
	@Override
	public void update(JavaResourcePersistentMember jrpm) {
		super.update(jrpm);
		this.setConverterClass_(this.converterClass(getAnnotation()));
	}
	
	protected String converterClass(StructConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getConverter();
	}
}
