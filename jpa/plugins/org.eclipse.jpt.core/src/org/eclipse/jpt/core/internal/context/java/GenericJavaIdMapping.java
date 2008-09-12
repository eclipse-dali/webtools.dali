/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaGenerator;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaIdMapping extends AbstractJavaAttributeMapping<IdAnnotation> implements JavaIdMapping
{
	protected final JavaColumn column;

	protected JavaGeneratedValue generatedValue;

	protected JavaTableGenerator tableGenerator;

	protected JavaSequenceGenerator sequenceGenerator;

	protected final JavaConverter defaultConverter;
	
	protected JavaConverter specifiedConverter;
	
	
	public GenericJavaIdMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = createJavaColumn();
		this.defaultConverter = new GenericJavaNullConverter(this);
	}

	protected JavaColumn createJavaColumn() {
		return getJpaFactory().buildJavaColumn(this, this);
	}

	@Override
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		super.initialize(jrpa);
		this.column.initialize(this.getResourceColumn());
		this.initializeTableGenerator(jrpa);
		this.initializeSequenceGenerator(jrpa);
		this.initializeGeneratedValue(jrpa);
		this.updatePersistenceUnitGenerators();
		this.specifiedConverter = this.buildSpecifiedConverter(this.specifiedConverterType(jrpa));
	}
	
	protected void initializeTableGenerator(JavaResourcePersistentAttribute jrpa) {
		TableGeneratorAnnotation resourceTableGenerator = getResourceTableGenerator(jrpa);
		if (resourceTableGenerator != null) {
			this.tableGenerator = buildTableGenerator(resourceTableGenerator);
		}
	}
	
	protected void initializeSequenceGenerator(JavaResourcePersistentAttribute jrpa) {
		SequenceGeneratorAnnotation resourceSequenceGenerator = getResourceSequenceGenerator(jrpa);
		if (resourceSequenceGenerator != null) {
			this.sequenceGenerator = buildSequenceGenerator(resourceSequenceGenerator);
		}
	}
	
	protected void initializeGeneratedValue(JavaResourcePersistentAttribute jrpa) {
		GeneratedValueAnnotation resourceGeneratedValue = getResourceGeneratedValue(jrpa);
		if (resourceGeneratedValue != null) {
			this.generatedValue = buildGeneratedValue(resourceGeneratedValue);
		}
	}
	
	public ColumnAnnotation getResourceColumn() {
		return (ColumnAnnotation) getResourcePersistentAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	//************** JavaAttributeMapping implementation ***************

	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return IdAnnotation.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.COLUMN,
			JPA.GENERATED_VALUE,
			JPA.TEMPORAL,
			JPA.TABLE_GENERATOR,
			JPA.SEQUENCE_GENERATOR);
	}

	public String getDefaultColumnName() {
		return getAttributeName();
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
			throw new IllegalStateException("gemeratedValue already exists");
		}
		this.generatedValue = getJpaFactory().buildJavaGeneratedValue(this);
		GeneratedValueAnnotation generatedValueResource = (GeneratedValueAnnotation) getResourcePersistentAttribute().addAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
		this.generatedValue.initialize(generatedValueResource);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, null, this.generatedValue);
		return this.generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed");
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

	public JavaTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		this.tableGenerator = getJpaFactory().buildJavaTableGenerator(this);
		TableGeneratorAnnotation tableGeneratorResource = (TableGeneratorAnnotation) getResourcePersistentAttribute().addAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		this.tableGenerator.initialize(tableGeneratorResource);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		JavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		getResourcePersistentAttribute().removeAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);
	}
	
	public JavaTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}
	
	protected void setTableGenerator(JavaTableGenerator newTableGenerator) {
		JavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}

	public JavaSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		
		this.sequenceGenerator = getJpaFactory().buildJavaSequenceGenerator(this);
		SequenceGeneratorAnnotation sequenceGeneratorResource = (SequenceGeneratorAnnotation) getResourcePersistentAttribute().addAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		this.sequenceGenerator.initialize(sequenceGeneratorResource);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		JavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		getResourcePersistentAttribute().removeAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, null);
	}
	
	public JavaSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(JavaSequenceGenerator newSequenceGenerator) {
		JavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}
	
	@SuppressWarnings("unchecked")
	protected Iterator<JavaGenerator> generators() {
		return new CompositeIterator<JavaGenerator>(
			(getSequenceGenerator() == null) ? EmptyIterator.instance() : new SingleElementIterator(getSequenceGenerator()),
			(getTableGenerator() == null) ? EmptyIterator.instance() : new SingleElementIterator(getTableGenerator()));
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
	public void update(JavaResourcePersistentAttribute jrpa) {
		super.update(jrpa);
		this.column.update(this.getResourceColumn());
		this.updateTableGenerator(jrpa);
		this.updateSequenceGenerator(jrpa);
		this.updateGeneratedValue(jrpa);
		this.updatePersistenceUnitGenerators();
		if (specifiedConverterType(jrpa) == getSpecifedConverterType()) {
			getSpecifiedConverter().update(jrpa);
		}
		else {
			JavaConverter javaConverter = buildSpecifiedConverter(specifiedConverterType(jrpa));
			setSpecifiedConverter(javaConverter);
		}
	}
	
	protected void updateTableGenerator(JavaResourcePersistentAttribute jrpa) {
		TableGeneratorAnnotation resourceTableGenerator = getResourceTableGenerator(jrpa);
		if (resourceTableGenerator == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(buildTableGenerator(resourceTableGenerator));
			}
			else {
				getTableGenerator().update(resourceTableGenerator);
			}
		}
	}
	
	protected JavaTableGenerator buildTableGenerator(TableGeneratorAnnotation resourceTableGenerator) {
		JavaTableGenerator tableGenerator = getJpaFactory().buildJavaTableGenerator(this);
		tableGenerator.initialize(resourceTableGenerator);
		return tableGenerator;
	}
	
	protected void updateSequenceGenerator(JavaResourcePersistentAttribute jrpa) {
		SequenceGeneratorAnnotation sequenceGeneratorResource = getResourceSequenceGenerator(jrpa);
		if (sequenceGeneratorResource == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(buildSequenceGenerator(sequenceGeneratorResource));
			}
			else {
				getSequenceGenerator().update(sequenceGeneratorResource);
			}
		}
	}
	
	protected JavaSequenceGenerator buildSequenceGenerator(SequenceGeneratorAnnotation resourceSequenceGenerator) {
		JavaSequenceGenerator sequenceGenerator = getJpaFactory().buildJavaSequenceGenerator(this);
		sequenceGenerator.initialize(resourceSequenceGenerator);
		return sequenceGenerator;
	}
	
	protected void updateGeneratedValue(JavaResourcePersistentAttribute jrpa) {
		GeneratedValueAnnotation resourceGeneratedValue = getResourceGeneratedValue(jrpa);
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
		JavaGeneratedValue generatedValue = getJpaFactory().buildJavaGeneratedValue(this);
		generatedValue.initialize(resourceGeneratedValue);
		return generatedValue;
	}

	protected TableGeneratorAnnotation getResourceTableGenerator(JavaResourcePersistentAttribute jrpa) {
		return (TableGeneratorAnnotation) jrpa.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected SequenceGeneratorAnnotation getResourceSequenceGenerator(JavaResourcePersistentAttribute jrpa) {
		return (SequenceGeneratorAnnotation) jrpa.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected GeneratedValueAnnotation getResourceGeneratedValue(JavaResourcePersistentAttribute jrpa) {
		return (GeneratedValueAnnotation) jrpa.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}
	
	protected void updatePersistenceUnitGenerators() {
		if (getTableGenerator() != null) {
			getPersistenceUnit().addGenerator(getTableGenerator());
		}
		
		if (getSequenceGenerator() != null) {
			getPersistenceUnit().addGenerator(getSequenceGenerator());
		}
	}
	
	protected JavaConverter buildSpecifiedConverter(String converterType) {
		if (converterType == Converter.TEMPORAL_CONVERTER) {
			return new GenericJavaTemporalConverter(this, this.resourcePersistentAttribute);
		}
		return null;
	}
	
	protected String specifiedConverterType(JavaResourcePersistentAttribute jrpa) {
		if (jrpa.getAnnotation(TemporalAnnotation.ANNOTATION_NAME) != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		
		return null;
	}

	// *************************************************************************

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
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		if (this.entityOwned() && this.connectionProfileIsActive()) {
			this.addColumnMessages(messages, astRoot);
		}
		addGeneratedValueMessages(messages, astRoot);
		addGeneratorMessages(messages, astRoot);
	}
		
	protected void addColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getTypeMapping().tableNameIsInvalid(this.column.getTable())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {this.column.getTable(), this.column.getName()}, 
						this.column,
						this.column.getTableTextRange(astRoot))
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
						this.column.getNameTextRange(astRoot))
				);
		}
	}
	
	protected void addGeneratedValueMessages(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.generatedValue == null) {
			return;
		}
		String generatorName = this.generatedValue.getGenerator();
		if (generatorName == null) {
			return;
		}
		
		for (Generator nextMasterGenerator : CollectionTools.iterable(getPersistenceUnit().allGenerators())) {
			if (generatorName.equals(nextMasterGenerator.getName())) {
				return;
			}
		}
		
		messages.add(
			DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.ID_MAPPING_UNRESOLVED_GENERATOR_NAME,
				new String[] {generatorName},
				this,
				this.generatedValue.getGeneratorTextRange(astRoot))
			);
	}
	
	protected void addGeneratorMessages(List<IMessage> messages, CompilationUnit astRoot) {
		List<Generator> masterList = CollectionTools.list(getPersistenceUnit().allGenerators());
		
		for (Iterator<JavaGenerator> stream = this.generators(); stream.hasNext() ; ) {
			JavaGenerator current = stream.next();
			masterList.remove(current);
			
			for (Generator each : masterList) {
				if (! each.overrides(current) && each.getName() != null && each.getName().equals(current.getName())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {current.getName()},
							current,
							current.getNameTextRange(astRoot))
					);
				}
			}
			
			masterList.add(current);
		}
	}
}
