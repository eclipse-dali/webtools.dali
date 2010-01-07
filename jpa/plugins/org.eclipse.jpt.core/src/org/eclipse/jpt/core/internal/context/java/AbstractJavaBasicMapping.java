/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.LobAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaBasicMapping
	extends AbstractJavaAttributeMapping<BasicAnnotation>
	implements JavaBasicMapping
{
	protected FetchType specifiedFetch;

	protected Boolean specifiedOptional;
	
	protected final JavaColumn column;
	
	protected JavaConverter converter;
	
	protected final JavaConverter nullConverter;
	
	protected AbstractJavaBasicMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = getJpaFactory().buildJavaColumn(this, this);
		this.nullConverter = getJpaFactory().buildJavaNullConverter(this);
		this.converter = this.nullConverter;
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.column.initialize(this.getResourceColumn());
		this.converter = this.buildConverter(this.getResourceConverterType());
		this.specifiedFetch = this.getResourceFetch();
		this.specifiedOptional = this.getResourceOptional();
	}
	
	public ColumnAnnotation getResourceColumn() {
		return (ColumnAnnotation) getResourcePersistentAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}
	
	//************** AttributeMapping implementation ***************
	public String getKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	//************** JavaAttributeMapping implementation ***************
	public String getAnnotationName() {
		return BasicAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.COLUMN);
		names.add(JPA.LOB);
		names.add(JPA.TEMPORAL);
		names.add(JPA.ENUMERATED);
	}
	
	public String getDefaultColumnName() {
		return getName();
	}

	public String getDefaultTableName() {
		return getTypeMapping().getPrimaryTableName();
	}
	
	public boolean tableIsAllowed() {
		return true;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return getTypeMapping().tableNameIsInvalid(tableName);
	}

	//************** BasicMapping implementation ***************

	public JavaColumn getColumn() {
		return this.column;
	}
	
	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}
		
	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.mappingAnnotation.setFetch(FetchType.toJavaResourceModel(newSpecifiedFetch));
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedFetch_(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	public boolean isOptional() {
		return (this.getSpecifiedOptional() == null) ? this.isDefaultOptional() : this.getSpecifiedOptional().booleanValue();
	}
	
	public boolean isDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		this.mappingAnnotation.setOptional(newSpecifiedOptional);
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
	}

	protected void setSpecifiedOptional_(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
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
		this.setSpecifiedFetch_(this.getResourceFetch());
		this.setSpecifiedOptional_(this.getResourceOptional());
	}
	
	protected FetchType getResourceFetch() {
		return FetchType.fromJavaResourceModel(this.mappingAnnotation.getFetch());
	}
	
	protected Boolean getResourceOptional() {
		return this.mappingAnnotation.getOptional();
	}
	
	protected JavaConverter buildConverter(String converterType) {
		if (this.valuesAreEqual(converterType, Converter.NO_CONVERTER)) {
			return this.nullConverter;		
		}
		if (this.valuesAreEqual(converterType, Converter.ENUMERATED_CONVERTER)) {
			return getJpaFactory().buildJavaEnumeratedConverter(this, this.getResourcePersistentAttribute());
		}
		if (this.valuesAreEqual(converterType, Converter.TEMPORAL_CONVERTER)) {
			return getJpaFactory().buildJavaTemporalConverter(this, this.getResourcePersistentAttribute());
		}
		if (this.valuesAreEqual(converterType, Converter.LOB_CONVERTER)) {
			return getJpaFactory().buildJavaLobConverter(this, this.getResourcePersistentAttribute());
		}
		return null;
	}
	
	protected String getResourceConverterType() {
		if (this.getResourcePersistentAttribute().getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME) != null) {
			return Converter.ENUMERATED_CONVERTER;
		}
		if (this.getResourcePersistentAttribute().getAnnotation(TemporalAnnotation.ANNOTATION_NAME) != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		if (this.getResourcePersistentAttribute().getAnnotation(LobAnnotation.ANNOTATION_NAME) != null) {
			return Converter.LOB_CONVERTER;
		}
		return Converter.NO_CONVERTER;
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
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


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.shouldValidateAgainstDatabase()) {
			this.validateColumn(messages, astRoot);
		}
		this.getConverter().validate(messages, reporter, astRoot);
	}
	
	protected void validateColumn(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.tableNameIsInvalid(this.column.getTable())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
					new String[] {this.column.getTable(), this.column.getName()}, 
					this.column,
					this.column.getTableTextRange(astRoot)
				)
			);
			return;
		}
		
		if ( ! this.column.isResolved() && this.column.getDbTable() != null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
					new String[] {this.column.getName()}, 
					this.column,
					this.column.getNameTextRange(astRoot)
				)
			);
		}
	}
}
