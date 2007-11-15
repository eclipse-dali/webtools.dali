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

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.resource.java.Id;


public class JavaIdMapping extends JavaAttributeMapping implements IJavaIdMapping
{
//	protected IColumn column;
//
//	protected IGeneratedValue generatedValue;
//
//	protected static final TemporalType TEMPORAL_EDEFAULT = TemporalType.NULL;
//
//	protected TemporalType temporal = TEMPORAL_EDEFAULT;
//
//	protected ITableGenerator tableGenerator;
//
//	protected ISequenceGenerator sequenceGenerator;

	public JavaIdMapping(IJavaPersistentAttribute parent) {
		super(parent);
//		this.column = JavaColumn.createColumnMappingColumn(buildColumnOwner(), getAttribute());
//		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__COLUMN, null, null);
//		this.temporalAnnotationAdapter = new MemberAnnotationAdapter(this.getAttribute(), TEMPORAL_ADAPTER);
//		this.temporalValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, TEMPORAL_VALUE_ADAPTER);
//		this.generatedValueAnnotationAdapter = this.buildAnnotationAdapter(JavaGeneratedValue.DECLARATION_ANNOTATION_ADAPTER);
//		this.tableGeneratorAnnotationAdapter = this.buildAnnotationAdapter(JavaTableGenerator.DECLARATION_ANNOTATION_ADAPTER);
//		this.sequenceGeneratorAnnotationAdapter = this.buildAnnotationAdapter(JavaSequenceGenerator.DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getKey() {
		return IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	public String annotationName() {
		return Id.ANNOTATION_NAME;
	}
	
//	public IColumn getColumn() {
//		return column;
//	}
//
//	public NotificationChain basicSetColumn(IColumn newColumn, NotificationChain msgs) {
//		IColumn oldColumn = column;
//		column = newColumn;
//		if (eNotificationRequired()) {
//			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__COLUMN, oldColumn, newColumn);
//			if (msgs == null)
//				msgs = notification;
//			else
//				msgs.add(notification);
//		}
//		return msgs;
//	}
//
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
//
//	private static void attributeChanged(Object value, AnnotationAdapter annotationAdapter) {
//		Annotation annotation = annotationAdapter.getAnnotation();
//		if (value == null) {
//			if (annotation != null) {
//				annotationAdapter.removeAnnotation();
//			}
//		}
//		else {
//			if (annotation == null) {
//				annotationAdapter.newMarkerAnnotation();
//			}
//		}
//	}
//
//	public TemporalType getTemporal() {
//		return temporal;
//	}
//
//	public void setTemporalGen(TemporalType newTemporal) {
//		TemporalType oldTemporal = temporal;
//		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__TEMPORAL, oldTemporal, temporal));
//	}
//
//	public void setTemporal(TemporalType newTemporal) {
//		if (newTemporal != TemporalType.NULL) {
//			if (this.temporalAnnotationAdapter.getAnnotation() == null) {
//				this.temporalAnnotationAdapter.newMarkerAnnotation();
//			}
//			this.temporalValueAdapter.setValue(newTemporal.convertToJavaAnnotationValue());
//		}
//		else if (this.temporalAnnotationAdapter.getAnnotation() != null) {
//			this.temporalAnnotationAdapter.removeAnnotation();
//		}
//		setTemporalGen(newTemporal);
//	}
//
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
//
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
//
//	/*
//	 * The @Temporal annotation is a bit different than most JPA annotations.
//	 * For some indecipherable reason it has no default value (e.g. TIMESTAMP).
//	 * Also, it is *required* for any attribute declared with a type of
//	 * java.util.Date or java.util.Calendar; otherwise, it is *prohibited*.
//	 * As a result we allow a Basic mapping to have a null 'temporal',
//	 * indicating that the annotation is completely missing, as opposed
//	 * to the annotation being present but its value is invalid (e.g.
//	 * @Temporal(FRIDAY)).
//	 * 
//	 * TODO this comment is wrong now, revisit this with Brian at some point
//	 */
//	private void updateTemporalFromJava(CompilationUnit astRoot) {
//		if (this.temporalAnnotationAdapter.getAnnotation(astRoot) == null) {
//			setTemporalGen(TemporalType.NULL);
//		}
//		else {
//			setTemporalGen(TemporalType.fromJavaAnnotationValue(this.temporalValueAdapter.getValue(astRoot)));
//		}
//	}
//
//	private JavaColumn getJavaColumn() {
//		return (JavaColumn) this.column;
//	}
//
//	@Override
//	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
//		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
//		if (result != null) {
//			return result;
//		}
//		result = this.getJavaColumn().candidateValuesFor(pos, filter, astRoot);
//		if (result != null) {
//			return result;
//		}
//		return null;
//	}
//
//	@Override
//	public String primaryKeyColumnName() {
//		return this.getColumn().getName();
//	}
//
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
