/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaVersionMapping extends AbstractJavaAttributeMapping<VersionAnnotation> implements JavaVersionMapping
{
	protected final JavaColumn column;

	protected final JavaConverter defaultConverter;
	
	protected JavaConverter specifiedConverter;

	public GenericJavaVersionMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = createJavaColumn();
		this.defaultConverter = new GenericJavaNullConverter(this);
	}

	protected JavaColumn createJavaColumn() {
		return getJpaFactory().buildJavaColumn(this, this);
	}
	
	@Override
	protected void initialize( ) {
		super.initialize();
		this.column.initialize(this.getResourceColumn());
		this.specifiedConverter = this.buildSpecifiedConverter(this.getResourceConverterType());
	}
		
	public ColumnAnnotation getResourceColumn() {
		return (ColumnAnnotation) getResourcePersistentAttribute().getNonNullSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	//************** IJavaAttributeMapping implementation ***************

	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return VersionAnnotation.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.COLUMN,
			JPA.TEMPORAL);
	}

	//************** NamedColumn.Owner implementation ***************

	public String getDefaultColumnName() {
		return getAttributeName();
	}
	
	public String getDefaultTableName() {
		return getTypeMapping().getPrimaryTableName();
	}

	//************** VersionMapping implementation ***************
	
	public JavaColumn getColumn() {
		return this.column;
	}
	
	public JavaConverter getConverter() {
		return getSpecifiedConverter() == null ? getDefaultConverter() : getSpecifiedConverter();
	}
	
	public JavaConverter getDefaultConverter() {
		return this.defaultConverter;
	}
	
	public JavaConverter getSpecifiedConverter() {
		return this.specifiedConverter;
	}
	
	protected String getSpecifedConverterType() {
		if (this.specifiedConverter == null) {
			return Converter.NO_CONVERTER;
		}
		return this.specifiedConverter.getType();
	}
	
	public void setSpecifiedConverter(String converterType) {
		if (getSpecifedConverterType() == converterType) {
			return;
		}
		JavaConverter oldConverter = this.specifiedConverter;
		JavaConverter newConverter = buildSpecifiedConverter(converterType);
		this.specifiedConverter = null;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.specifiedConverter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(SPECIFIED_CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setSpecifiedConverter(JavaConverter newConverter) {
		JavaConverter oldConverter = this.specifiedConverter;
		this.specifiedConverter = newConverter;
		firePropertyChanged(SPECIFIED_CONVERTER_PROPERTY, oldConverter, newConverter);
	}

	
	@Override
	protected void update() {
		super.update();
		this.column.update(this.getResourceColumn());
		if (getResourceConverterType() == getSpecifedConverterType()) {
			getSpecifiedConverter().update(this.resourcePersistentAttribute);
		}
		else {
			JavaConverter javaConverter = buildSpecifiedConverter(getResourceConverterType());
			setSpecifiedConverter(javaConverter);
		}
	}
	
	protected JavaConverter buildSpecifiedConverter(String converterType) {
		if (converterType == Converter.TEMPORAL_CONVERTER) {
			return getJpaFactory().buildJavaTemporalConverter(this, this.resourcePersistentAttribute);
		}
		return null;
	}
	
	protected String getResourceConverterType() {
		if (this.resourcePersistentAttribute.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME) != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		return null;
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
		return null;
	}
	
	//***********  Validation  ******************************
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		if (this.ownerIsEntity() && this.connectionProfileIsActive()) {
			this.validateColumn(messages, astRoot);
		}
		if (this.specifiedConverter != null) {
			this.specifiedConverter.validate(messages, astRoot);
		}
	}
	
	protected void validateColumn(List<IMessage> messages, CompilationUnit astRoot) {
		String tableName = this.column.getTable();
		if (this.getTypeMapping().tableNameIsInvalid(tableName)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
					new String[] {tableName, this.column.getName()}, 
					this.column,
					this.column.getTableTextRange(astRoot)
				)
			);
			return;
		}
		
		if ( ! this.column.isResolved()) {
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
