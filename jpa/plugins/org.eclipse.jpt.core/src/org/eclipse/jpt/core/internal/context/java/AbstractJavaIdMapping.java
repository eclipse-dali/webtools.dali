/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractJavaIdMapping
	extends AbstractJavaAttributeMapping<IdAnnotation>
	implements JavaIdMapping
{
	protected final JavaColumn column;

	protected JavaGeneratedValue generatedValue;

	protected final JavaGeneratorContainer generatorContainer;
	
	protected JavaConverter specifiedConverter;
	
	
	protected AbstractJavaIdMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = this.getJpaFactory().buildJavaColumn(this, this);
		this.generatorContainer = this.buildGeneratorContainer();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.column.initialize(this.getResourceColumn());
		this.generatorContainer.initialize(this.getResourcePersistentAttribute());
		this.initializeGeneratedValue();
		this.specifiedConverter = this.buildSpecifiedConverter(this.getResourceConverterType());
	}

	protected void initializeGeneratedValue() {
		GeneratedValueAnnotation resourceGeneratedValue = this.getResourceGeneratedValue();
		if (resourceGeneratedValue != null) {
			this.generatedValue = this.buildGeneratedValue(resourceGeneratedValue);
		}
	}

	public ColumnAnnotation getResourceColumn() {
		return (ColumnAnnotation) this.getResourcePersistentAttribute().
				getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	private JavaGeneratorContainer buildGeneratorContainer() {
		return this.getJpaFactory().buildJavaGeneratorContainer(this);
	}

	//************** JavaAttributeMapping implementation ***************

	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return IdAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.COLUMN);
		names.add(JPA.GENERATED_VALUE);
		names.add(JPA.TEMPORAL);
		names.add(JPA.TABLE_GENERATOR);
		names.add(JPA.SEQUENCE_GENERATOR);
	}
	
	public String getDefaultColumnName() {
		return getName();
	}
	
	public String getDefaultTableName() {
		return getTypeMapping().getPrimaryTableName();
	}

	//************** IdMapping implementation ***************
	
	public JavaColumn getColumn() {
		return this.column;
	}
	
	public JavaGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists"); //$NON-NLS-1$
		}
		this.generatedValue = getJpaFactory().buildJavaGeneratedValue(this);
		GeneratedValueAnnotation generatedValueResource = 
				(GeneratedValueAnnotation) getResourcePersistentAttribute().
					addAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
		this.generatedValue.initialize(generatedValueResource);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, null, this.generatedValue);
		return this.generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed"); //$NON-NLS-1$
		}
		JavaGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = null;
		getResourcePersistentAttribute().removeAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, null);
	}
	
	public JavaGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}
	
	protected void setGeneratedValue(JavaGeneratedValue newGeneratedValue) {
		JavaGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = newGeneratedValue;
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, newGeneratedValue);
	}
	
	public JavaGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}
	
	public JavaConverter getSpecifiedConverter() {
		return this.specifiedConverter;
	}
	
	protected String getSpecifedConverterType() {
		if (this.specifiedConverter == null) {
			//TODO this is only ever null in the case that the mapping type is changed
			//via PersistentAttribute.setSpecifiedMappingKey.  In this case, the 
			//initialize method is never called.
			return null;
		}
		return this.specifiedConverter.getType();
	}
	
	public void setSpecifiedConverter(String converterType) {
		if (this.valuesAreEqual(getSpecifedConverterType(), converterType)) {
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
		this.generatorContainer.update(this.getResourcePersistentAttribute());
		this.updateGeneratedValue();
		if (this.valuesAreEqual(getResourceConverterType(), getSpecifedConverterType())) {
			getSpecifiedConverter().update(this.getResourcePersistentAttribute());
		}
		else {
			JavaConverter javaConverter = buildSpecifiedConverter(getResourceConverterType());
			setSpecifiedConverter(javaConverter);
		}
	}

	protected void updateGeneratedValue() {
		GeneratedValueAnnotation resourceGeneratedValue = getResourceGeneratedValue();
		if (resourceGeneratedValue == null) {
			if (getGeneratedValue() != null) {
				setGeneratedValue(null);
			}
		}
		else {
			if (getGeneratedValue() == null) {
				setGeneratedValue(buildGeneratedValue(resourceGeneratedValue));
			}
			else {
				getGeneratedValue().update(resourceGeneratedValue);
			}
		}
	}
	
	protected JavaGeneratedValue buildGeneratedValue(GeneratedValueAnnotation resourceGeneratedValue) {
		JavaGeneratedValue gv = getJpaFactory().buildJavaGeneratedValue(this);
		gv.initialize(resourceGeneratedValue);
		return gv;
	}

	protected TableGeneratorAnnotation getResourceTableGenerator() {
		return (TableGeneratorAnnotation) this.getResourcePersistentAttribute().
				getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected SequenceGeneratorAnnotation getResourceSequenceGenerator() {
		return (SequenceGeneratorAnnotation) this.getResourcePersistentAttribute().
				getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected GeneratedValueAnnotation getResourceGeneratedValue() {
		return (GeneratedValueAnnotation) this.getResourcePersistentAttribute().
				getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}
	
	protected JavaConverter buildSpecifiedConverter(String converterType) {
		if (this.valuesAreEqual(converterType, Converter.NO_CONVERTER)) {
			return getJpaFactory().buildJavaNullConverter(this);			
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

	// ********** code assist **********

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
		if (this.generatorTouches(pos, astRoot)) {
			result = this.persistenceGeneratorNames(filter);
			if (result != null) {
				return result;
			}
		}
		result = this.getGeneratorContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = getSpecifiedConverter().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	// ********** code assist: generator 
	
	protected boolean generatorTouches(int pos, CompilationUnit astRoot) {
		if (getResourceGeneratedValue() != null) {
			return this.getResourceGeneratedValue().generatorTouches(pos, astRoot);
		}
		return false;
	}
	
	protected Iterator<String> persistenceGeneratorNames() {
		if(this.getPersistenceUnit().generatorsSize() == 0) {
			return EmptyIterator.<String> instance();
		}
		return CollectionTools.iterator(this.getPersistenceUnit().uniqueGeneratorNames());
	}
	
	private Iterator<String> generatorNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.persistenceGeneratorNames(), filter);
	}
	
	protected Iterator<String> persistenceGeneratorNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.generatorNames(filter));
	}

	// *************************************************************************
	
	@Override
	public String getPrimaryKeyColumnName() {
		return this.getColumn().getName();
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	@Override
	public boolean isIdMapping() {
		return true;
	}

	//*********** Validation ************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		if (this.shouldValidateAgainstDatabase()) {
			this.validateColumn(messages, astRoot);
		}
		if (this.getGeneratedValue() != null) {
			this.getGeneratedValue().validate(messages, reporter, astRoot);
		}
		this.getGeneratorContainer().validate(messages, reporter, astRoot);
		this.getSpecifiedConverter().validate(messages, reporter, astRoot);
	}
		
	protected void validateColumn(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getTypeMapping().tableNameIsInvalid(this.column.getTable())) {
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
