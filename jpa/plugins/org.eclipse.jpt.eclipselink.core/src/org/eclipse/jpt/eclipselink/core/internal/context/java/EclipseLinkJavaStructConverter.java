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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.StructConverterAnnotation;

public class EclipseLinkJavaStructConverter extends AbstractJavaJpaContextNode implements StructConverter, EclipseLinkJavaConverter
{	
	private JavaResourcePersistentMember resourcePersistentMember;
	
	private String name;
	
	private String converterClass;
	
	public EclipseLinkJavaStructConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		super(parent);
		this.initialize(jrpm);
	}

	public String getType() {
		return EclipseLinkConverter.STRUCT_CONVERTER;
	}

	public String getAnnotationName() {
		return StructConverterAnnotation.ANNOTATION_NAME;
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceConverter().getTextRange(astRoot);
	}

	protected StructConverterAnnotation getResourceConverter() {
		return (StructConverterAnnotation) this.resourcePersistentMember.getSupportingAnnotation(getAnnotationName());
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		getResourceConverter().setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		getResourceConverter().setConverter(newConverterClass);
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}
	
	protected void setConverterClass_(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}


	protected void initialize(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		StructConverterAnnotation resourceConverter = getResourceConverter();
		this.name = this.name(resourceConverter);
		this.converterClass = this.converterClass(resourceConverter);
	}
	
	public void update(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		StructConverterAnnotation resourceConverter = getResourceConverter();
		this.setName_(this.name(resourceConverter));
		this.setConverterClass_(this.converterClass(resourceConverter));
	}

	protected String name(StructConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getName();
	}
	
	protected String converterClass(StructConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getConverter();
	}

}
