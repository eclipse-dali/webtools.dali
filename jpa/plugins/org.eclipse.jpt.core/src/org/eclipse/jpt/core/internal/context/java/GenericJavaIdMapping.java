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
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.java.JavaColumn;
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
import org.eclipse.jpt.core.resource.java.Id;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.Temporal;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaIdMapping extends AbstractJavaAttributeMapping<Id> implements JavaIdMapping
{
	protected final JavaColumn column;

	protected JavaGeneratedValue generatedValue;

	protected TemporalType temporal;

	protected JavaTableGenerator tableGenerator;

	protected JavaSequenceGenerator sequenceGenerator;

	public GenericJavaIdMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.column = createJavaColumn();
	}

	protected JavaColumn createJavaColumn() {
		return jpaFactory().buildJavaColumn(this, this);
	}

	@Override
	public void initializeFromResource(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super.initializeFromResource(resourcePersistentAttribute);
		this.column.initializeFromResource(this.columnResource());
		this.temporal = this.temporal(this.temporalResource());
		this.initializeTableGenerator(resourcePersistentAttribute);
		this.initializeSequenceGenerator(resourcePersistentAttribute);
		this.initializeGeneratedValue(resourcePersistentAttribute);
		this.updatePersistenceUnitGenerators();
	}
	
	protected void initializeTableGenerator(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		TableGeneratorAnnotation tableGeneratorResource = tableGenerator(resourcePersistentAttribute);
		if (tableGeneratorResource != null) {
			this.tableGenerator = jpaFactory().buildJavaTableGenerator(this);
			this.tableGenerator.initializeFromResource(tableGeneratorResource);
		}
	}
	
	protected void initializeSequenceGenerator(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		SequenceGeneratorAnnotation sequenceGeneratorResource = sequenceGenerator(resourcePersistentAttribute);
		if (sequenceGeneratorResource != null) {
			this.sequenceGenerator = jpaFactory().buildJavaSequenceGenerator(this);
			this.sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
		}
	}
	
	protected void initializeGeneratedValue(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		GeneratedValueAnnotation generatedValueResource = generatedValue(resourcePersistentAttribute);
		if (generatedValueResource != null) {
			this.generatedValue = jpaFactory().buildJavaGeneratedValue(this);
			this.generatedValue.initializeFromResource(generatedValueResource);
		}
	}
	
	protected Temporal temporalResource() {
		return (Temporal) getResourcePersistentAttribute().nonNullAnnotation(Temporal.ANNOTATION_NAME);
	}
	
	public ColumnAnnotation columnResource() {
		return (ColumnAnnotation) getResourcePersistentAttribute().nonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	//************** IJavaAttributeMapping implementation ***************

	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	public String annotationName() {
		return Id.ANNOTATION_NAME;
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
		return attributeName();
	}
	
	public String defaultTableName() {
		return getTypeMapping().getTableName();
	}

	//************** IIdMapping implementation ***************
	
	public JavaColumn getColumn() {
		return this.column;
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.temporalResource().setValue(TemporalType.toJavaResourceModel(newTemporal));
		firePropertyChanged(ColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setTemporal_(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		firePropertyChanged(ColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	public JavaGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists");
		}
		this.generatedValue = jpaFactory().buildJavaGeneratedValue(this);
		GeneratedValueAnnotation generatedValueResource = (GeneratedValueAnnotation) getResourcePersistentAttribute().addAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
		this.generatedValue.initializeFromResource(generatedValueResource);
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
		this.tableGenerator = jpaFactory().buildJavaTableGenerator(this);
		TableGeneratorAnnotation tableGeneratorResource = (TableGeneratorAnnotation) getResourcePersistentAttribute().addAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		this.tableGenerator.initializeFromResource(tableGeneratorResource);
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
		
		this.sequenceGenerator = jpaFactory().buildJavaSequenceGenerator(this);
		SequenceGeneratorAnnotation sequenceGeneratorResource = (SequenceGeneratorAnnotation) getResourcePersistentAttribute().addAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		this.sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
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
	
	@Override
	public void update(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super.update(resourcePersistentAttribute);
		this.column.update(this.columnResource());
		this.setTemporal_(this.temporal(this.temporalResource()));
		this.updateTableGenerator(resourcePersistentAttribute);
		this.updateSequenceGenerator(resourcePersistentAttribute);
		this.updateGeneratedValue(resourcePersistentAttribute);
		this.updatePersistenceUnitGenerators();
	}
	
	protected TemporalType temporal(Temporal temporal) {
		return TemporalType.fromJavaResourceModel(temporal.getValue());
	}

	protected void updateTableGenerator(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		TableGeneratorAnnotation tableGeneratorResource = tableGenerator(resourcePersistentAttribute);
		if (tableGeneratorResource == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(jpaFactory().buildJavaTableGenerator(this));
				getTableGenerator().initializeFromResource(tableGeneratorResource);
			}
			else {
				getTableGenerator().update(tableGeneratorResource);
			}
		}
	}
	
	protected void updateSequenceGenerator(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		SequenceGeneratorAnnotation sequenceGeneratorResource = sequenceGenerator(resourcePersistentAttribute);
		if (sequenceGeneratorResource == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(jpaFactory().buildJavaSequenceGenerator(this));
				getSequenceGenerator().initializeFromResource(sequenceGeneratorResource);
			}
			else {
				getSequenceGenerator().update(sequenceGeneratorResource);
			}
		}
	}
	
	protected void updateGeneratedValue(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		GeneratedValueAnnotation generatedValueResource = generatedValue(resourcePersistentAttribute);
		if (generatedValueResource == null) {
			if (getGeneratedValue() != null) {
				setGeneratedValue(null);
			}
		}
		else {
			if (getGeneratedValue() == null) {
				setGeneratedValue(jpaFactory().buildJavaGeneratedValue(this));
				getGeneratedValue().initializeFromResource(generatedValueResource);
			}
			else {
				getGeneratedValue().update(generatedValueResource);
			}
		}
	}
	
	protected TableGeneratorAnnotation tableGenerator(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		return (TableGeneratorAnnotation) resourcePersistentAttribute.annotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected SequenceGeneratorAnnotation sequenceGenerator(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		return (SequenceGeneratorAnnotation) resourcePersistentAttribute.annotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected GeneratedValueAnnotation generatedValue(JavaResourcePersistentAttribute resourcePersistentAttribute) {
		return (GeneratedValueAnnotation) resourcePersistentAttribute.annotation(GeneratedValueAnnotation.ANNOTATION_NAME);
	}
	
	protected void updatePersistenceUnitGenerators() {
		if (getTableGenerator() != null && getTableGenerator().getName() != null) {
			getPersistenceUnit().addGenerator(getTableGenerator());
		}
		
		if (getSequenceGenerator() != null && getSequenceGenerator().getName() != null) {
			getPersistenceUnit().addGenerator(getSequenceGenerator());
		}
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
	public String primaryKeyColumnName() {
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
		
		addColumnMessages(messages, astRoot);
		addGeneratedValueMessages(messages, astRoot);
		addGeneratorMessages(messages, astRoot);
	}
		
	protected void addColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		JavaColumn column = this.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.connectionProfileIsActive();
		
		if (doContinue && this.getTypeMapping().tableNameIsInvalid(table)) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.tableTextRange(astRoot))
				);
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.nameTextRange(astRoot))
				);
		}
	}
	
	protected void addGeneratedValueMessages(List<IMessage> messages, CompilationUnit astRoot) {
		JavaGeneratedValue generatedValue = this.getGeneratedValue();
		if (generatedValue == null) {
			return;
		}
		String generatorName = generatedValue.getGenerator();
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
				generatedValue.generatorTextRange(astRoot))
			);
	}
	
	protected void addGeneratorMessages(List<IMessage> messages, CompilationUnit astRoot) {
		List<Generator> masterList = CollectionTools.list(getPersistenceUnit().allGenerators());
		
		for (Iterator<JavaGenerator> stream = this.generators(); stream.hasNext() ; ) {
			JavaGenerator current = stream.next();
			masterList.remove(current);
			
			for (Generator each : masterList) {
				if (! each.overrides(current) && each.getName().equals(current.getName())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {current.getName()},
							current,
							current.nameTextRange(astRoot))
					);
				}
			}
			
			masterList.add(current);
		}
	}
}
