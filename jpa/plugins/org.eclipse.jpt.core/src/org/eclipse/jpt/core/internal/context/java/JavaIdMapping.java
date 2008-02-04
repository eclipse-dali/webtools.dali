/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class JavaIdMapping extends JavaAttributeMapping implements IJavaIdMapping
{
	protected final IJavaColumn column;

	protected IJavaGeneratedValue generatedValue;

	protected TemporalType temporal;

	protected IJavaTableGenerator tableGenerator;

	protected IJavaSequenceGenerator sequenceGenerator;

	public JavaIdMapping(IJavaPersistentAttribute parent) {
		super(parent);
		this.column = createJavaColumn();
	}

	protected IJavaColumn createJavaColumn() {
		return jpaFactory().createJavaColumn(this, this);
	}

	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.column.initializeFromResource(this.columnResource());
		this.temporal = this.temporal(this.temporalResource());
		this.initializeTableGenerator(persistentAttributeResource);
		this.initializeSequenceGenerator(persistentAttributeResource);
		this.initializeGeneratedValue(persistentAttributeResource);
	}
	
	protected void initializeTableGenerator(JavaPersistentAttributeResource persistentAttributeResource) {
		TableGenerator tableGeneratorResource = tableGenerator(persistentAttributeResource);
		if (tableGeneratorResource != null) {
			this.tableGenerator = jpaFactory().createJavaTableGenerator(this);
			this.tableGenerator.initializeFromResource(tableGeneratorResource);
		}
	}
	
	protected void initializeSequenceGenerator(JavaPersistentAttributeResource persistentAttributeResource) {
		SequenceGenerator sequenceGeneratorResource = sequenceGenerator(persistentAttributeResource);
		if (sequenceGeneratorResource != null) {
			this.sequenceGenerator = jpaFactory().createJavaSequenceGenerator(this);
			this.sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
		}
	}
	
	protected void initializeGeneratedValue(JavaPersistentAttributeResource persistentAttributeResource) {
		GeneratedValue generatedValueResource = generatedValue(persistentAttributeResource);
		if (generatedValueResource != null) {
			this.generatedValue = jpaFactory().createJavaGeneratedValue(this);
			this.generatedValue.initializeFromResource(generatedValueResource);
		}
	}
	
	protected Temporal temporalResource() {
		return (Temporal) this.persistentAttributeResource.nonNullAnnotation(Temporal.ANNOTATION_NAME);
	}
	
	public Column columnResource() {
		return (Column) this.persistentAttributeResource.nonNullAnnotation(Column.ANNOTATION_NAME);
	}

	//************** IJavaAttributeMapping implementation ***************

	public String getKey() {
		return IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
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

	public String defaultColumnName() {
		return attributeName();
	}
	
	public String defaultTableName() {
		return typeMapping().tableName();
	}

	//************** IIdMapping implementation ***************
	
	public IJavaColumn getColumn() {
		return this.column;
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.temporalResource().setValue(TemporalType.toJavaResourceModel(newTemporal));
		firePropertyChanged(IColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
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
		firePropertyChanged(IColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	public IJavaGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists");
		}
		this.generatedValue = jpaFactory().createJavaGeneratedValue(this);
		GeneratedValue generatedValueResource = (GeneratedValue) this.persistentAttributeResource.addAnnotation(GeneratedValue.ANNOTATION_NAME);
		this.generatedValue.initializeFromResource(generatedValueResource);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, null, this.generatedValue);
		return this.generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed");
		}
		IJavaGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = null;
		this.persistentAttributeResource.removeAnnotation(GeneratedValue.ANNOTATION_NAME);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, null);
	}
	
	public IJavaGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}
	
	protected void setGeneratedValue(IJavaGeneratedValue newGeneratedValue) {
		IJavaGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = newGeneratedValue;
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, newGeneratedValue);
	}

	public IJavaTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		this.tableGenerator = jpaFactory().createJavaTableGenerator(this);
		TableGenerator tableGeneratorResource = (TableGenerator) this.persistentAttributeResource.addAnnotation(TableGenerator.ANNOTATION_NAME);
		this.tableGenerator.initializeFromResource(tableGeneratorResource);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		IJavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.persistentAttributeResource.removeAnnotation(TableGenerator.ANNOTATION_NAME);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);
	}
	
	public IJavaTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}
	
	protected void setTableGenerator(IJavaTableGenerator newTableGenerator) {
		IJavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}

	public IJavaSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		
		this.sequenceGenerator = jpaFactory().createJavaSequenceGenerator(this);
		SequenceGenerator sequenceGeneratorResource = (SequenceGenerator) this.persistentAttributeResource.addAnnotation(SequenceGenerator.ANNOTATION_NAME);
		this.sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		IJavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.persistentAttributeResource.removeAnnotation(SequenceGenerator.ANNOTATION_NAME);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, null);
	}
	
	public IJavaSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(IJavaSequenceGenerator newSequenceGenerator) {
		IJavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	
	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.column.update(this.columnResource());
		this.setTemporal_(this.temporal(this.temporalResource()));
		this.updateTableGenerator(persistentAttributeResource);
		this.updateSequenceGenerator(persistentAttributeResource);
		this.updateGeneratedValue(persistentAttributeResource);
	}
	
	protected TemporalType temporal(Temporal temporal) {
		return TemporalType.fromJavaResourceModel(temporal.getValue());
	}

	protected void updateTableGenerator(JavaPersistentAttributeResource persistentAttributeResource) {
		TableGenerator tableGeneratorResource = tableGenerator(persistentAttributeResource);
		if (tableGeneratorResource == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(jpaFactory().createJavaTableGenerator(this));
				getTableGenerator().initializeFromResource(tableGeneratorResource);
			}
			else {
				getTableGenerator().update(tableGeneratorResource);
			}
		}
	}
	
	protected void updateSequenceGenerator(JavaPersistentAttributeResource persistentAttributeResource) {
		SequenceGenerator sequenceGeneratorResource = sequenceGenerator(persistentAttributeResource);
		if (sequenceGeneratorResource == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(jpaFactory().createJavaSequenceGenerator(this));
				getSequenceGenerator().initializeFromResource(sequenceGeneratorResource);
			}
			else {
				getSequenceGenerator().update(sequenceGeneratorResource);
			}
		}
	}
	
	protected void updateGeneratedValue(JavaPersistentAttributeResource persistentAttributeResource) {
		GeneratedValue generatedValueResource = generatedValue(persistentAttributeResource);
		if (generatedValueResource == null) {
			if (getGeneratedValue() != null) {
				setGeneratedValue(null);
			}
		}
		else {
			if (getGeneratedValue() == null) {
				setGeneratedValue(jpaFactory().createJavaGeneratedValue(this));
				getGeneratedValue().initializeFromResource(generatedValueResource);
			}
			else {
				getGeneratedValue().update(generatedValueResource);
			}
		}
	}
	
	protected TableGenerator tableGenerator(JavaPersistentAttributeResource persistentAttributeResource) {
		return (TableGenerator) persistentAttributeResource.annotation(TableGenerator.ANNOTATION_NAME);
	}
	
	protected SequenceGenerator sequenceGenerator(JavaPersistentAttributeResource persistentAttributeResource) {
		return (SequenceGenerator) persistentAttributeResource.annotation(SequenceGenerator.ANNOTATION_NAME);
	}
	
	protected GeneratedValue generatedValue(JavaPersistentAttributeResource persistentAttributeResource) {
		return (GeneratedValue) persistentAttributeResource.annotation(GeneratedValue.ANNOTATION_NAME);
	}


//	private void updateGeneratedValueFromJava(CompilationUnit astRoot) {
//		if (this.generatedValueAnnotationAdapter.getAnnotation(astRoot) == null) {
//			if (getGeneratedValue() != null) {
//				setGeneratedValue(null);
//			}
//		}
//		else {
//			if (getGeneratedValue() == null) {
//				setGeneratedValue(createGeneratedValue());
//			}
//			((JavaGeneratedValue) getGeneratedValue()).updateFromJava(astRoot);
//		}
//	}


	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getColumn().candidateValuesFor(pos, filter, astRoot);
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
	
		//TODO njh there is no generator repos yet
//		addGeneratorMessages(messages);
	}
		
	protected void addColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		IJavaColumn column = this.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.isConnected();
		
		if (doContinue && this.typeMapping().tableNameIsInvalid(table)) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.tableTextRange(astRoot))
				);
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.nameTextRange(astRoot))
				);
		}
	}
	
	protected void addGeneratorMessages(List<IMessage> messages, CompilationUnit astRoot) {
		IJavaGeneratedValue generatedValue = this.getGeneratedValue();
		if (generatedValue == null) {
			return;
		}
		String generatorName = generatedValue.getGenerator();
		if (generatorName == null) {
			return;
		}
//		IGeneratorRepository generatorRepository = persistenceUnit().getGeneratorRepository();		
//		IJavaGenerator generator = generatorRepository.generator(generatorName);
//		
//		if (generator == null) {
//			messages.add(
//				JpaValidationMessages.buildMessage(
//					IMessage.HIGH_SEVERITY,
//					IJpaValidationMessages.GENERATED_VALUE_UNRESOLVED_GENERATOR,
//					new String[] {generatorName}, 
//					generatedValue, generatedValue.generatorTextRange())
//			);
//		}
	}
	
	
}
