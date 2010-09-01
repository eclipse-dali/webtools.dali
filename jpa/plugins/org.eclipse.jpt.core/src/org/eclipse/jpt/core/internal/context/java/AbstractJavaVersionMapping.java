/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaVersionMapping
	extends AbstractJavaAttributeMapping<VersionAnnotation> 
	implements JavaVersionMapping
{
	protected final JavaColumn column;
	
	protected JavaConverter converter;
	
	protected final JavaConverter nullConverter;

	protected AbstractJavaVersionMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = getJpaFactory().buildJavaColumn(this, this);
		this.nullConverter = getJpaFactory().buildJavaNullConverter(this);
		this.converter = this.nullConverter;
	}
	
	@Override
	protected void initialize( ) {
		super.initialize();
		this.column.initialize(this.getResourceColumn());
		this.converter = this.buildConverter(this.getResourceConverterType());
	}
		
	public ColumnAnnotation getResourceColumn() {
		return (ColumnAnnotation) getResourcePersistentAttribute().
				getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	//************** JavaAttributeMapping implementation ***************

	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return VersionAnnotation.ANNOTATION_NAME;
	}
	
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.COLUMN);
		names.add(JPA.TEMPORAL);
	}

	//************** NamedColumn.Owner implementation ***************

	public String getDefaultColumnName() {
		return getName();
	}

	//************** BaseColumn.Owner implementation ***************
	
	public String getDefaultTableName() {
		return getTypeMapping().getPrimaryTableName();
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return getTypeMapping().tableNameIsInvalid(tableName);
	}

	public Iterator<String> candidateTableNames() {
		return getTypeMapping().associatedTableNamesIncludingInherited();
	}

	//************** VersionMapping implementation ***************
	
	public JavaColumn getColumn() {
		return this.column;
	}
	
	public JavaConverter getConverter() {
		return this.converter;
	}
	
	protected String getConverterType() {
		return this.converter.getType();
	}
	
	public void setConverter(String converterType) {
		if (this.valuesAreEqual(getConverterType(), converterType)) {
			return;
		}
		JavaConverter oldConverter = this.converter;
		JavaConverter newConverter = buildConverter(converterType);
		this.converter = this.nullConverter;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.converter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(JavaConverter newConverter) {
		JavaConverter oldConverter = this.converter;
		this.converter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}

	
	@Override
	protected void update() {
		super.update();
		this.column.update(this.getResourceColumn());
		if (this.valuesAreEqual(getResourceConverterType(), getConverterType())) {
			getConverter().update(this.getResourcePersistentAttribute());
		}
		else {
			JavaConverter javaConverter = buildConverter(getResourceConverterType());
			setConverter(javaConverter);
		}
	}
	
	protected JavaConverter buildConverter(String converterType) {
		if (this.valuesAreEqual(converterType, Converter.NO_CONVERTER)) {
			return this.nullConverter;			
		}
		if (this.valuesAreEqual(converterType, Converter.TEMPORAL_CONVERTER)) {
			return getJpaFactory().buildJavaTemporalConverter(this, this.getResourcePersistentAttribute());
		}
		return null;
	}
	
	protected String getResourceConverterType() {
		if (this.getResourcePersistentAttribute().getAnnotation(TemporalAnnotation.ANNOTATION_NAME) != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		return Converter.NO_CONVERTER;
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getColumn().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = getConverter().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}
	
	//***********  Validation  ******************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.getColumn().validate(messages, reporter, astRoot);
		this.getConverter().validate(messages, reporter, astRoot);
	}

	public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
		return new NamedColumnValidator((BaseColumn) column, (BaseColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
	}
}
