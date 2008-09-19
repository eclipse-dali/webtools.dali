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
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConvert;
import org.eclipse.jpt.eclipselink.core.resource.java.ConvertAnnotation;

public class EclipseLinkJavaConvertImpl extends AbstractJavaJpaContextNode implements EclipseLinkJavaConvert
{
	private String specifiedConverterName;
	
	private JavaResourcePersistentAttribute resourcePersistenceAttribute;
	
	public EclipseLinkJavaConvertImpl(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		super(parent);
		this.initialize(jrpa);
	}

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}

	public String getType() {
		return EclipseLinkJavaConvert.ECLIPSE_LINK_CONVERTER;
	}

	protected String getAnnotationName() {
		return ConvertAnnotation.ANNOTATION_NAME;
	}
		
	public void addToResourceModel() {
		this.resourcePersistenceAttribute.addAnnotation(getAnnotationName());
	}
	
	public void removeFromResourceModel() {
		this.resourcePersistenceAttribute.removeAnnotation(getAnnotationName());
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceConvert().getTextRange(astRoot);
	}

	protected ConvertAnnotation getResourceConvert() {
		return (ConvertAnnotation) this.resourcePersistenceAttribute.getAnnotation(getAnnotationName());
	}
	
	public String getConverterName() {
		return getSpecifiedConverterName() == null ? getDefaultConverterName() : getSpecifiedConverterName();
	}

	public String getDefaultConverterName() {
		return DEFAULT_CONVERTER_NAME;
	}

	public String getSpecifiedConverterName() {
		return this.specifiedConverterName;
	}

	public void setSpecifiedConverterName(String newSpecifiedConverterName) {
		String oldSpecifiedConverterName = this.specifiedConverterName;
		this.specifiedConverterName = newSpecifiedConverterName;
		getResourceConvert().setValue(newSpecifiedConverterName);
		firePropertyChanged(SPECIFIED_CONVERTER_NAME_PROPERTY, oldSpecifiedConverterName, newSpecifiedConverterName);
	}
	
	protected void setSpecifiedConverterName_(String newSpecifiedConverterName) {
		String oldSpecifiedConverterName = this.specifiedConverterName;
		this.specifiedConverterName = newSpecifiedConverterName;
		firePropertyChanged(SPECIFIED_CONVERTER_NAME_PROPERTY, oldSpecifiedConverterName, newSpecifiedConverterName);
	}

	protected void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistenceAttribute = jrpa;
		this.specifiedConverterName = this.specifiedConverterName(getResourceConvert());
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistenceAttribute = jrpa;
		this.setSpecifiedConverterName_(this.specifiedConverterName(getResourceConvert()));
	}
	
	protected String specifiedConverterName(ConvertAnnotation resourceConvert) {
		return resourceConvert == null ? null : resourceConvert.getValue();
	}

}
