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
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConvertAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaConvert extends AbstractJavaJpaContextNode implements Convert, JavaConverter
{
	private String specifiedConverterName;
	
	private JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	private EclipseLinkJavaConverter converter;
	
	public EclipseLinkJavaConvert(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		super(parent);
		this.initialize(jrpa);
	}

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}

	public String getType() {
		return Convert.ECLIPSE_LINK_CONVERTER;
	}

	protected String getAnnotationName() {
		return EclipseLinkConvertAnnotation.ANNOTATION_NAME;
	}
		
	public void addToResourceModel() {
		this.resourcePersistentAttribute.addSupportingAnnotation(getAnnotationName());
	}
	
	public void removeFromResourceModel() {
		this.resourcePersistentAttribute.removeSupportingAnnotation(getAnnotationName());
		if (getConverter() != null) {
			this.resourcePersistentAttribute.removeSupportingAnnotation(getConverter().getAnnotationName());
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceConvert().getTextRange(astRoot);
	}

	protected EclipseLinkConvertAnnotation getResourceConvert() {
		return (EclipseLinkConvertAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(getAnnotationName());
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

	public EclipseLinkJavaConverter getConverter() {
		return this.converter;
	}
	
	protected String getConverterType() {
		if (this.converter == null) {
			return EclipseLinkConverter.NO_CONVERTER;
		}
		return this.converter.getType();
	}

	public void setConverter(String converterType) {
		if (getConverterType() == converterType) {
			return;
		}
		EclipseLinkJavaConverter oldConverter = this.converter;
		EclipseLinkJavaConverter newConverter = buildConverter(converterType);
		this.converter = null;
		if (oldConverter != null) {
			this.resourcePersistentAttribute.removeSupportingAnnotation(oldConverter.getAnnotationName());
		}
		this.converter = newConverter;
		if (newConverter != null) {
			this.resourcePersistentAttribute.addSupportingAnnotation(newConverter.getAnnotationName());
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(EclipseLinkJavaConverter newConverter) {
		EclipseLinkJavaConverter oldConverter = this.converter;
		this.converter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		this.specifiedConverterName = this.getResourceConverterName();
		this.converter = this.buildConverter(this.getResourceConverterType());
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		this.setSpecifiedConverterName_(this.getResourceConverterName());
		if (getResourceConverterType() == getConverterType()) {
			getConverter().update(this.resourcePersistentAttribute);
		}
		else {
			EclipseLinkJavaConverter javaConverter = buildConverter(getResourceConverterType());
			setConverter(javaConverter);
		}
	}
	
	protected String getResourceConverterName() {
		EclipseLinkConvertAnnotation resourceConvert = getResourceConvert();
		return resourceConvert == null ? null : resourceConvert.getValue();
	}

	
	protected EclipseLinkJavaConverter buildConverter(String converterType) {
		if (converterType == EclipseLinkConverter.NO_CONVERTER) {
			return null;
		}
		if (converterType == EclipseLinkConverter.CUSTOM_CONVERTER) {
			return buildCustomConverter();
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			return buildTypeConverter();
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			return buildObjectTypeConverter();
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			return buildStructConverter();
		}
		return null;
	}
	
	protected EclipseLinkJavaCustomConverter buildCustomConverter() {
		EclipseLinkJavaCustomConverter contextConverter = new EclipseLinkJavaCustomConverter(this);
		contextConverter.initialize(this.resourcePersistentAttribute);
		return contextConverter;
	}

	protected EclipseLinkJavaTypeConverter buildTypeConverter() {
		EclipseLinkJavaTypeConverter contextConverter = new EclipseLinkJavaTypeConverter(this);
		contextConverter.initialize(this.resourcePersistentAttribute);
		return contextConverter;
	}

	protected EclipseLinkJavaObjectTypeConverter buildObjectTypeConverter() {
		EclipseLinkJavaObjectTypeConverter contextConverter = new EclipseLinkJavaObjectTypeConverter(this);
		contextConverter.initialize(this.resourcePersistentAttribute);
		return contextConverter;
	}

	protected EclipseLinkJavaStructConverter buildStructConverter() {
		EclipseLinkJavaStructConverter contextConverter = new EclipseLinkJavaStructConverter(this);
		contextConverter.initialize(this.resourcePersistentAttribute);
		return contextConverter;
	}

	protected String getResourceConverterType() {
		if (this.resourcePersistentAttribute.getSupportingAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkConverter.CUSTOM_CONVERTER;
		}
		else if (this.resourcePersistentAttribute.getSupportingAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkConverter.TYPE_CONVERTER;
		}
		else if (this.resourcePersistentAttribute.getSupportingAnnotation(EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkConverter.OBJECT_TYPE_CONVERTER;
		}
		else if (this.resourcePersistentAttribute.getSupportingAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME) != null) {
			return EclipseLinkConverter.STRUCT_CONVERTER;
		}
		
		return null;
	}

	//*************** code assist ******************
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.convertValueTouches(pos, astRoot)) {
			result = this.persistenceConvertersNames(filter);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	protected boolean convertValueTouches(int pos, CompilationUnit astRoot) {
		if (getResourceConvert() != null) {
			return this.getResourceConvert().valueTouches(pos, astRoot);
		}
		return false;
	}

	protected Iterator<String> persistenceConvertersNames() {
		if(this.getEclipseLinkPersistenceUnit().convertersSize() == 0) {
			return EmptyIterator.<String> instance();
		}
		return CollectionTools.iterator(this.getEclipseLinkPersistenceUnit().uniqueConverterNames());
	}

	private Iterator<String> convertersNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.persistenceConvertersNames(), filter);
	}

	protected Iterator<String> persistenceConvertersNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.convertersNames(filter));
	}

	protected EclipseLinkPersistenceUnit getEclipseLinkPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) this.getPersistenceUnit();
	}
	
	//****************** validation ********************
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (getConverter() != null) {
			getConverter().validate(messages, reporter, astRoot);
		}
	}

}
