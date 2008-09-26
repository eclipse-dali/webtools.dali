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
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkNamedConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConvert;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaNamedConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.ConvertAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.ConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.StructConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.TypeConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkJavaConvertImpl extends AbstractJavaJpaContextNode implements EclipseLinkJavaConvert
{
	private String specifiedConverterName;
	
	private JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	private EclipseLinkJavaNamedConverter converter;
	
	public EclipseLinkJavaConvertImpl(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		super(parent);
		this.initialize(jrpa);
	}

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}

	public String getType() {
		return EclipseLinkConvert.ECLIPSE_LINK_CONVERTER;
	}

	protected String getAnnotationName() {
		return ConvertAnnotation.ANNOTATION_NAME;
	}
		
	public void addToResourceModel() {
		this.resourcePersistentAttribute.addAnnotation(getAnnotationName());
	}
	
	public void removeFromResourceModel() {
		this.resourcePersistentAttribute.removeAnnotation(getAnnotationName());
		if (getConverter() != null) {
			getConverter().removeFromResourceModel();
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceConvert().getTextRange(astRoot);
	}

	protected ConvertAnnotation getResourceConvert() {
		return (ConvertAnnotation) this.resourcePersistentAttribute.getAnnotation(getAnnotationName());
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

	public EclipseLinkJavaNamedConverter getConverter() {
		return this.converter;
	}
	
	protected String getConverterType() {
		if (this.converter == null) {
			return EclipseLinkNamedConverter.NO_CONVERTER;
		}
		return this.converter.getType();
	}

	public void setConverter(String converterType) {
		if (getConverterType() == converterType) {
			return;
		}
		EclipseLinkJavaNamedConverter oldConverter = this.converter;
		EclipseLinkJavaNamedConverter newConverter = buildConverter(converterType);
		this.converter = null;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.converter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(EclipseLinkJavaNamedConverter newConverter) {
		EclipseLinkJavaNamedConverter oldConverter = this.converter;
		this.converter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		this.specifiedConverterName = this.specifiedConverterName(getResourceConvert());
		this.converter = this.buildConverter(this.converterType(jrpa));
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		this.setSpecifiedConverterName_(this.specifiedConverterName(getResourceConvert()));
		if (converterType(jrpa) == getConverterType()) {
			getConverter().update(jrpa);
		}
		else {
			EclipseLinkJavaNamedConverter javaConverter = buildConverter(converterType(jrpa));
			setConverter(javaConverter);
		}
	}
	
	protected String specifiedConverterName(ConvertAnnotation resourceConvert) {
		return resourceConvert == null ? null : resourceConvert.getValue();
	}

	
	protected EclipseLinkJavaNamedConverter buildConverter(String converterType) {
		if (converterType == EclipseLinkNamedConverter.NO_CONVERTER) {
			return null;
		}
		if (converterType == EclipseLinkNamedConverter.CONVERTER) {
			return getJpaFactory().buildEclipseLinkJavaConverter(this, this.resourcePersistentAttribute);
		}
		else if (converterType == EclipseLinkNamedConverter.TYPE_CONVERTER) {
			return getJpaFactory().buildEclipseLinkJavaTypeConverter(this, this.resourcePersistentAttribute);
		}
		else if (converterType == EclipseLinkNamedConverter.OBJECT_TYPE_CONVERTER) {
			return getJpaFactory().buildEclipseLinkJavaObjectTypeConverter(this, this.resourcePersistentAttribute);
		}
		else if (converterType == EclipseLinkNamedConverter.STRUCT_CONVERTER) {
			return getJpaFactory().buildEclipseLinkJavaStructConverter(this, this.resourcePersistentAttribute);
		}
		return null;
	}
	
	protected String converterType(JavaResourcePersistentAttribute jrpa) {
		if (jrpa.getAnnotation(ConverterAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkNamedConverter.CONVERTER;
		}
		else if (jrpa.getAnnotation(TypeConverterAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkNamedConverter.TYPE_CONVERTER;
		}
		else if (jrpa.getAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkNamedConverter.OBJECT_TYPE_CONVERTER;
		}
		else if (jrpa.getAnnotation(StructConverterAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkNamedConverter.STRUCT_CONVERTER;
		}
		
		return null;
	}

	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		if (getConverter() != null) {
			getConverter().validate(messages, astRoot);
		}
	}

}
