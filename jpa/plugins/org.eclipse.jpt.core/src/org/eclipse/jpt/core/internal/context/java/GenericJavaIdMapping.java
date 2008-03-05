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
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
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
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaIdMapping extends AbstractJavaAttributeMapping implements JavaIdMapping
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
	public void initializeFromResource(JavaResourcePersistentAttribute persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.column.initializeFromResource(this.columnResource());
		this.temporal = this.temporal(this.temporalResource());
		this.initializeTableGenerator(persistentAttributeResource);
		this.initializeSequenceGenerator(persistentAttributeResource);
		this.initializeGeneratedValue(persistentAttributeResource);
	}
	
	protected void initializeTableGenerator(JavaResourcePersistentAttribute persistentAttributeResource) {
		TableGeneratorAnnotation tableGeneratorResource = tableGenerator(persistentAttributeResource);
		if (tableGeneratorResource != null) {
			this.tableGenerator = jpaFactory().buildJavaTableGenerator(this);
			this.tableGenerator.initializeFromResource(tableGeneratorResource);
		}
	}
	
	protected void initializeSequenceGenerator(JavaResourcePersistentAttribute persistentAttributeResource) {
		SequenceGeneratorAnnotation sequenceGeneratorResource = sequenceGenerator(persistentAttributeResource);
		if (sequenceGeneratorResource != null) {
			this.sequenceGenerator = jpaFactory().buildJavaSequenceGenerator(this);
			this.sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
		}
	}
	
	protected void initializeGeneratedValue(JavaResourcePersistentAttribute persistentAttributeResource) {
		GeneratedValueAnnotation generatedValueResource = generatedValue(persistentAttributeResource);
		if (generatedValueResource != null) {
			this.generatedValue = jpaFactory().buildJavaGeneratedValue(this);
			this.generatedValue.initializeFromResource(generatedValueResource);
		}
	}
	
	protected Temporal temporalResource() {
		return (Temporal) this.persistentAttributeResource.nonNullAnnotation(Temporal.ANNOTATION_NAME);
	}
	
	public ColumnAnnotation columnResource() {
		return (ColumnAnnotation) this.persistentAttributeResource.nonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
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

	public String defaultColumnName() {
		return attributeName();
	}
	
	public String defaultTableName() {
		return typeMapping().tableName();
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
		GeneratedValueAnnotation generatedValueResource = (GeneratedValueAnnotation) this.persistentAttributeResource.addAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
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
		this.persistentAttributeResource.removeAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME);
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
		TableGeneratorAnnotation tableGeneratorResource = (TableGeneratorAnnotation) this.persistentAttributeResource.addAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
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
		this.persistentAttributeResource.removeAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
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
		SequenceGeneratorAnnotation sequenceGeneratorResource = (SequenceGeneratorAnnotation) this.persistentAttributeResource.addAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
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
		this.persistentAttributeResource.removeAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
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

	
	@Override
	public void update(JavaResourcePersistentAttribute persistentAttributeResource) {
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

	protected void updateTableGenerator(JavaResourcePersistentAttribute persistentAttributeResource) {
		TableGeneratorAnnotation tableGeneratorResource = tableGenerator(persistentAttributeResource);
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
	
	protected void updateSequenceGenerator(JavaResourcePersistentAttribute persistentAttributeResource) {
		SequenceGeneratorAnnotation sequenceGeneratorResource = sequenceGenerator(persistentAttributeResource);
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
	
	protected void updateGeneratedValue(JavaResourcePersistentAttribute persistentAttributeResource) {
		GeneratedValueAnnotation generatedValueResource = generatedValue(persistentAttributeResource);
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
	
	protected TableGeneratorAnnotation tableGenerator(JavaResourcePersistentAttribute persistentAttributeResource) {
		return (TableGeneratorAnnotation) persistentAttributeResource.annotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected SequenceGeneratorAnnotation sequenceGenerator(JavaResourcePersistentAttribute persistentAttributeResource) {
		return (SequenceGeneratorAnnotation) persistentAttributeResource.annotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	protected GeneratedValueAnnotation generatedValue(JavaResourcePersistentAttribute persistentAttributeResource) {
		return (GeneratedValueAnnotation) persistentAttributeResource.annotation(GeneratedValueAnnotation.ANNOTATION_NAME);
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
	
		//TODO njh there is no generator repos yet
//		addGeneratorMessages(messages);
	}
		
	protected void addColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		JavaColumn column = this.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.isConnected();
		
		if (doContinue && this.typeMapping().tableNameIsInvalid(table)) {
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
	
	protected void addGeneratorMessages(List<IMessage> messages, CompilationUnit astRoot) {
		JavaGeneratedValue generatedValue = this.getGeneratedValue();
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
