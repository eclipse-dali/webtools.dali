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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.utility.internal.Filter;


public class JavaIdMapping extends JavaAttributeMapping implements IJavaIdMapping
{
	protected IJavaColumn column;

	protected IJavaGeneratedValue generatedValue;

	protected TemporalType temporal;

	protected IJavaTableGenerator tableGenerator;

	protected IJavaSequenceGenerator sequenceGenerator;

	public JavaIdMapping(IJavaPersistentAttribute parent) {
		super(parent);
		this.column = jpaFactory().createJavaColumn(this);
	}

	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.column.initializeFromResource(persistentAttributeResource);
		this.temporal = this.temporal(temporalResource());
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
	
	//************** IJavaAttributeMapping implementation ***************

	public String getKey() {
		return IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	public String annotationName() {
		return Id.ANNOTATION_NAME;
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
		firePropertyChanged(IIdMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	public IJavaGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists");
		}
		IJavaGeneratedValue generatedValue = jpaFactory().createJavaGeneratedValue(this);
		setGeneratedValue(generatedValue);
		return generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed");
		}
		setGeneratedValue(null);
	}
	
	public IJavaGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}
	
	protected void setGeneratedValue(IJavaGeneratedValue newGeneratedValue) {
		IJavaGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = newGeneratedValue;
		if (newGeneratedValue != null) {
			this.persistentAttributeResource.addAnnotation(GeneratedValue.ANNOTATION_NAME);
		}
		else {
			this.persistentAttributeResource.removeAnnotation(GeneratedValue.ANNOTATION_NAME);
		}
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, newGeneratedValue);
	}

	public IJavaTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		IJavaTableGenerator tableGenerator = jpaFactory().createJavaTableGenerator(this);
		setTableGenerator(tableGenerator);
		return tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		setTableGenerator(null);
	}
	
	public IJavaTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}
	
	protected void setTableGenerator(IJavaTableGenerator newTableGenerator) {
		IJavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		if (newTableGenerator != null) {
			this.persistentAttributeResource.addAnnotation(TableGenerator.ANNOTATION_NAME);
		}
		else {
			this.persistentAttributeResource.removeAnnotation(TableGenerator.ANNOTATION_NAME);
		}
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}

	public IJavaSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		IJavaSequenceGenerator sequenceGenerator = jpaFactory().createJavaSequenceGenerator(this);
		setSequenceGenerator(sequenceGenerator);
		return sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		this.setSequenceGenerator(null);
	}
	
	public IJavaSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(IJavaSequenceGenerator newSequenceGenerator) {
		IJavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		if (newSequenceGenerator != null) {
			this.persistentAttributeResource.addAnnotation(SequenceGenerator.ANNOTATION_NAME);
		}
		else {
			this.persistentAttributeResource.removeAnnotation(SequenceGenerator.ANNOTATION_NAME);
		}
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	
	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.column.update(persistentAttributeResource);
		this.setTemporal(this.temporal(temporalResource()));
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
				IJavaTableGenerator tableGenerator = addTableGenerator();
				tableGenerator.initializeFromResource(tableGeneratorResource);
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
				IJavaSequenceGenerator sequenceGenerator = addSequenceGenerator();
				sequenceGenerator.initializeFromResource(sequenceGeneratorResource);
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
				IJavaGeneratedValue generatedValue = addGeneratedValue();
				generatedValue.initializeFromResource(generatedValueResource);
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

}
