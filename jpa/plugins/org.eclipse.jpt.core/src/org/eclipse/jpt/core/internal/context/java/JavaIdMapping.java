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
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.utility.internal.Filter;


public class JavaIdMapping extends JavaAttributeMapping implements IJavaIdMapping
{
	protected IJavaColumn column;

//	protected IGeneratedValue generatedValue;

	protected TemporalType temporal;

//	protected ITableGenerator tableGenerator;
//
//	protected ISequenceGenerator sequenceGenerator;

	public JavaIdMapping(IJavaPersistentAttribute parent) {
		super(parent);
		this.column = jpaFactory().createJavaColumn(this);
	}

	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.column.initializeFromResource(persistentAttributeResource);
		this.temporal = this.temporal(temporalResource());
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

//	public IGeneratedValue getGeneratedValue() {
//		return generatedValue;
//	}
//
//	public NotificationChain basicSetGeneratedValue(IGeneratedValue newGeneratedValue, NotificationChain msgs) {
//		IGeneratedValue oldGeneratedValue = generatedValue;
//		generatedValue = newGeneratedValue;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE, oldGeneratedValue, newGeneratedValue);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setGeneratedValue(IGeneratedValue newGeneratedValue) {
//		if (newGeneratedValue != generatedValue) {
//			NotificationChain msgs = null;
//			if (generatedValue != null)
//				msgs = ((InternalEObject) generatedValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE, null, msgs);
//			if (newGeneratedValue != null)
//				msgs = ((InternalEObject) newGeneratedValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE, null, msgs);
//			msgs = basicSetGeneratedValue(newGeneratedValue, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE, newGeneratedValue, newGeneratedValue));
//	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.temporalResource().setValue(TemporalType.toJavaResourceModel(newTemporal));
		firePropertyChanged(IIdMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	

//	public ITableGenerator getTableGenerator() {
//		return tableGenerator;
//	}
//
//	public NotificationChain basicSetTableGenerator(ITableGenerator newTableGenerator, NotificationChain msgs) {
//		ITableGenerator oldTableGenerator = tableGenerator;
//		tableGenerator = newTableGenerator;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
//	public void setTableGenerator(ITableGenerator newTableGenerator) {
//		if (newTableGenerator != tableGenerator) {
//			NotificationChain msgs = null;
//			if (tableGenerator != null)
//				msgs = ((InternalEObject) tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR, null, msgs);
//			if (newTableGenerator != null)
//				msgs = ((InternalEObject) newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR, null, msgs);
//			msgs = basicSetTableGenerator(newTableGenerator, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
//	}
//	public ISequenceGenerator getSequenceGenerator() {
//		return sequenceGenerator;
//	}
//
//	public NotificationChain basicSetSequenceGenerator(ISequenceGenerator newSequenceGenerator, NotificationChain msgs) {
//		ISequenceGenerator oldSequenceGenerator = sequenceGenerator;
//		sequenceGenerator = newSequenceGenerator;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//	public void setSequenceGenerator(ISequenceGenerator newSequenceGenerator) {
//		if (newSequenceGenerator != sequenceGenerator) {
//			NotificationChain msgs = null;
//			if (sequenceGenerator != null)
//				msgs = ((InternalEObject) sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR, null, msgs);
//			if (newSequenceGenerator != null)
//				msgs = ((InternalEObject) newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR, null, msgs);
//			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
//			if (msgs != null)
//				msgs.dispatch();
//		}
//		else if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
//	}
	
	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.column.update(persistentAttributeResource);
		this.setTemporal(this.temporal(temporalResource()));
	}
	
	protected TemporalType temporal(Temporal temporal) {
		return TemporalType.fromJavaResourceModel(temporal.getValue());
	}

	
//	@Override
//	public void updateFromJava(CompilationUnit astRoot) {
//		super.updateFromJava(astRoot);
//		this.updateTemporalFromJava(astRoot);
//		this.getJavaColumn().updateFromJava(astRoot);
//		this.updateGeneratedValueFromJava(astRoot);
//		this.updateTableGeneratorFromJava(astRoot);
//		this.updateSequenceGeneratorFromJava(astRoot);
//	}
//
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
//
//	private void updateTableGeneratorFromJava(CompilationUnit astRoot) {
//		if (this.tableGeneratorAnnotationAdapter.getAnnotation(astRoot) == null) {
//			if (getTableGenerator() != null) {
//				setTableGenerator(null);
//			}
//		}
//		else {
//			if (getTableGenerator() == null) {
//				setTableGenerator(createTableGenerator());
//			}
//			((JavaTableGenerator) getTableGenerator()).updateFromJava(astRoot);
//		}
//	}
//
//	private void updateSequenceGeneratorFromJava(CompilationUnit astRoot) {
//		if (this.sequenceGeneratorAnnotationAdapter.getAnnotation(astRoot) == null) {
//			if (getSequenceGenerator() != null) {
//				setSequenceGenerator(null);
//			}
//		}
//		else {
//			if (getSequenceGenerator() == null) {
//				setSequenceGenerator(createSequenceGenerator());
//			}
//			((JavaSequenceGenerator) getSequenceGenerator()).updateFromJava(astRoot);
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

//	public IGeneratedValue createGeneratedValue() {
//		return JpaJavaMappingsFactory.eINSTANCE.createJavaGeneratedValue(getAttribute());
//	}
//
//	public ISequenceGenerator createSequenceGenerator() {
//		return JpaJavaMappingsFactory.eINSTANCE.createJavaSequenceGenerator(getAttribute());
//	}
//
//	public ITableGenerator createTableGenerator() {
//		return JpaJavaMappingsFactory.eINSTANCE.createJavaTableGenerator(getAttribute());
//	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	@Override
	public boolean isIdMapping() {
		return true;
	}

}
