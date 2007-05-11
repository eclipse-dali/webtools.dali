/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import java.util.Iterator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.mappings.IGeneratedValue;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.TemporalType;
import org.eclipse.jpt.utility.internal.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Id</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaId()
 * @model kind="class"
 * @generated
 */
public class JavaId extends JavaAttributeMapping implements IId
{
	private AnnotationAdapter generatedValueAnnotationAdapter;

	private AnnotationAdapter tableGeneratorAnnotationAdapter;

	private AnnotationAdapter sequenceGeneratorAnnotationAdapter;

	private final AnnotationAdapter temporalAnnotationAdapter;

	private final AnnotationElementAdapter<String> temporalValueAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ID);

	private static final DeclarationAnnotationAdapter TEMPORAL_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.TEMPORAL);

	private static final DeclarationAnnotationElementAdapter<String> TEMPORAL_VALUE_ADAPTER = buildTemporalValueAdapter();

	/**
	 * The cached value of the '{@link #getColumn() <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected IColumn column;

	/**
	 * The cached value of the '{@link #getGeneratedValue() <em>Generated Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeneratedValue()
	 * @generated
	 * @ordered
	 */
	protected IGeneratedValue generatedValue;

	/**
	 * The default value of the '{@link #getTemporal() <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporal()
	 * @generated
	 * @ordered
	 */
	protected static final TemporalType TEMPORAL_EDEFAULT = TemporalType.NULL;

	/**
	 * The cached value of the '{@link #getTemporal() <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporal()
	 * @generated
	 * @ordered
	 */
	protected TemporalType temporal = TEMPORAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTableGenerator() <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableGenerator()
	 * @generated
	 * @ordered
	 */
	protected ITableGenerator tableGenerator;

	/**
	 * The cached value of the '{@link #getSequenceGenerator() <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceGenerator()
	 * @generated
	 * @ordered
	 */
	protected ISequenceGenerator sequenceGenerator;

	protected JavaId() {
		throw new UnsupportedOperationException("Use JavaId(Attribute) instead");
	}

	protected JavaId(Attribute attribute) {
		super(attribute);
		this.column = JavaColumn.createColumnMappingColumn(buildColumnOwner(), getAttribute());
		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__COLUMN, null, null);
		this.temporalAnnotationAdapter = new MemberAnnotationAdapter(this.getAttribute(), TEMPORAL_ADAPTER);
		this.temporalValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, TEMPORAL_VALUE_ADAPTER);
		this.generatedValueAnnotationAdapter = this.buildAnnotationAdapter(JavaGeneratedValue.DECLARATION_ANNOTATION_ADAPTER);
		this.tableGeneratorAnnotationAdapter = this.buildAnnotationAdapter(JavaTableGenerator.DECLARATION_ANNOTATION_ADAPTER);
		this.sequenceGeneratorAnnotationAdapter = this.buildAnnotationAdapter(JavaSequenceGenerator.DECLARATION_ANNOTATION_ADAPTER);
	}

	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IId.class)) {
			case JpaCoreMappingsPackage.IID__TABLE_GENERATOR :
				attributeChanged(notification.getNewValue(), this.tableGeneratorAnnotationAdapter);
				break;
			case JpaCoreMappingsPackage.IID__SEQUENCE_GENERATOR :
				attributeChanged(notification.getNewValue(), this.sequenceGeneratorAnnotationAdapter);
				break;
			case JpaCoreMappingsPackage.IID__GENERATED_VALUE :
				attributeChanged(notification.getNewValue(), this.generatedValueAnnotationAdapter);
			default :
				break;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_ID;
	}

	/**
	 * Returns the value of the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIId_Column()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public IColumn getColumn() {
		return column;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColumn(IColumn newColumn, NotificationChain msgs) {
		IColumn oldColumn = column;
		column = newColumn;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__COLUMN, oldColumn, newColumn);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generated Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generated Value</em>' containment reference.
	 * @see #setGeneratedValue(IGeneratedValue)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIId_GeneratedValue()
	 * @model containment="true"
	 * @generated
	 */
	public IGeneratedValue getGeneratedValue() {
		return generatedValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGeneratedValue(IGeneratedValue newGeneratedValue, NotificationChain msgs) {
		IGeneratedValue oldGeneratedValue = generatedValue;
		generatedValue = newGeneratedValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE, oldGeneratedValue, newGeneratedValue);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaId#getGeneratedValue <em>Generated Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generated Value</em>' containment reference.
	 * @see #getGeneratedValue()
	 * @generated
	 */
	public void setGeneratedValue(IGeneratedValue newGeneratedValue) {
		if (newGeneratedValue != generatedValue) {
			NotificationChain msgs = null;
			if (generatedValue != null)
				msgs = ((InternalEObject) generatedValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE, null, msgs);
			if (newGeneratedValue != null)
				msgs = ((InternalEObject) newGeneratedValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE, null, msgs);
			msgs = basicSetGeneratedValue(newGeneratedValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE, newGeneratedValue, newGeneratedValue));
	}

	private static void attributeChanged(Object value, AnnotationAdapter annotationAdapter) {
		Annotation annotation = annotationAdapter.getAnnotation();
		if (value == null) {
			if (annotation != null) {
				annotationAdapter.removeAnnotation();
			}
		}
		else {
			if (annotation == null) {
				annotationAdapter.newMarkerAnnotation();
			}
		}
	}

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIId_Temporal()
	 * @model
	 * @generated
	 */
	public TemporalType getTemporal() {
		return temporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaId#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	public void setTemporalGen(TemporalType newTemporal) {
		TemporalType oldTemporal = temporal;
		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__TEMPORAL, oldTemporal, temporal));
	}

	public void setTemporal(TemporalType newTemporal) {
		if (newTemporal != TemporalType.NULL) {
			if (this.temporalAnnotationAdapter.getAnnotation() == null) {
				this.temporalAnnotationAdapter.newMarkerAnnotation();
			}
			this.temporalValueAdapter.setValue(newTemporal.convertToJavaAnnotationValue());
		}
		else if (this.temporalAnnotationAdapter.getAnnotation() != null) {
			this.temporalAnnotationAdapter.removeAnnotation();
		}
		setTemporalGen(newTemporal);
	}

	/**
	 * Returns the value of the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generator</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generator</em>' containment reference.
	 * @see #setTableGenerator(ITableGenerator)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIId_TableGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public ITableGenerator getTableGenerator() {
		return tableGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTableGenerator(ITableGenerator newTableGenerator, NotificationChain msgs) {
		ITableGenerator oldTableGenerator = tableGenerator;
		tableGenerator = newTableGenerator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaId#getTableGenerator <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Generator</em>' containment reference.
	 * @see #getTableGenerator()
	 * @generated
	 */
	public void setTableGenerator(ITableGenerator newTableGenerator) {
		if (newTableGenerator != tableGenerator) {
			NotificationChain msgs = null;
			if (tableGenerator != null)
				msgs = ((InternalEObject) tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR, null, msgs);
			if (newTableGenerator != null)
				msgs = ((InternalEObject) newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR, null, msgs);
			msgs = basicSetTableGenerator(newTableGenerator, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generator</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #setSequenceGenerator(ISequenceGenerator)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIId_SequenceGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public ISequenceGenerator getSequenceGenerator() {
		return sequenceGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSequenceGenerator(ISequenceGenerator newSequenceGenerator, NotificationChain msgs) {
		ISequenceGenerator oldSequenceGenerator = sequenceGenerator;
		sequenceGenerator = newSequenceGenerator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaId#getSequenceGenerator <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public void setSequenceGenerator(ISequenceGenerator newSequenceGenerator) {
		if (newSequenceGenerator != sequenceGenerator) {
			NotificationChain msgs = null;
			if (sequenceGenerator != null)
				msgs = ((InternalEObject) sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR, null, msgs);
			if (newSequenceGenerator != null)
				msgs = ((InternalEObject) newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR, null, msgs);
			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ID__COLUMN :
				return basicSetColumn(null, msgs);
			case JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE :
				return basicSetGeneratedValue(null, msgs);
			case JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR :
				return basicSetTableGenerator(null, msgs);
			case JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR :
				return basicSetSequenceGenerator(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ID__COLUMN :
				return getColumn();
			case JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE :
				return getGeneratedValue();
			case JpaJavaMappingsPackage.JAVA_ID__TEMPORAL :
				return getTemporal();
			case JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR :
				return getTableGenerator();
			case JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR :
				return getSequenceGenerator();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE :
				setGeneratedValue((IGeneratedValue) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ID__TEMPORAL :
				setTemporal((TemporalType) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR :
				setTableGenerator((ITableGenerator) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR :
				setSequenceGenerator((ISequenceGenerator) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE :
				setGeneratedValue((IGeneratedValue) null);
				return;
			case JpaJavaMappingsPackage.JAVA_ID__TEMPORAL :
				setTemporal(TEMPORAL_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR :
				setTableGenerator((ITableGenerator) null);
				return;
			case JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR :
				setSequenceGenerator((ISequenceGenerator) null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ID__COLUMN :
				return column != null;
			case JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE :
				return generatedValue != null;
			case JpaJavaMappingsPackage.JAVA_ID__TEMPORAL :
				return temporal != TEMPORAL_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR :
				return tableGenerator != null;
			case JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR :
				return sequenceGenerator != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IColumnMapping.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IId.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_ID__COLUMN :
					return JpaCoreMappingsPackage.IID__COLUMN;
				case JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE :
					return JpaCoreMappingsPackage.IID__GENERATED_VALUE;
				case JpaJavaMappingsPackage.JAVA_ID__TEMPORAL :
					return JpaCoreMappingsPackage.IID__TEMPORAL;
				case JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR :
					return JpaCoreMappingsPackage.IID__TABLE_GENERATOR;
				case JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR :
					return JpaCoreMappingsPackage.IID__SEQUENCE_GENERATOR;
				default :
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IColumnMapping.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IId.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IID__COLUMN :
					return JpaJavaMappingsPackage.JAVA_ID__COLUMN;
				case JpaCoreMappingsPackage.IID__GENERATED_VALUE :
					return JpaJavaMappingsPackage.JAVA_ID__GENERATED_VALUE;
				case JpaCoreMappingsPackage.IID__TEMPORAL :
					return JpaJavaMappingsPackage.JAVA_ID__TEMPORAL;
				case JpaCoreMappingsPackage.IID__TABLE_GENERATOR :
					return JpaJavaMappingsPackage.JAVA_ID__TABLE_GENERATOR;
				case JpaCoreMappingsPackage.IID__SEQUENCE_GENERATOR :
					return JpaJavaMappingsPackage.JAVA_ID__SEQUENCE_GENERATOR;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (temporal: ");
		result.append(temporal);
		result.append(')');
		return result.toString();
	}

	public String getKey() {
		return IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.updateTemporalFromJava(astRoot);
		this.getJavaColumn().updateFromJava(astRoot);
		this.updateGeneratedValueFromJava(astRoot);
		this.updateTableGeneratorFromJava(astRoot);
		this.updateSequenceGeneratorFromJava(astRoot);
	}

	private void updateGeneratedValueFromJava(CompilationUnit astRoot) {
		if (this.generatedValueAnnotationAdapter.getAnnotation(astRoot) == null) {
			if (getGeneratedValue() != null) {
				setGeneratedValue(null);
			}
		}
		else {
			if (getGeneratedValue() == null) {
				setGeneratedValue(createGeneratedValue());
			}
			((JavaGeneratedValue) getGeneratedValue()).updateFromJava(astRoot);
		}
	}

	private void updateTableGeneratorFromJava(CompilationUnit astRoot) {
		if (this.tableGeneratorAnnotationAdapter.getAnnotation(astRoot) == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(createTableGenerator());
			}
			((JavaTableGenerator) getTableGenerator()).updateFromJava(astRoot);
		}
	}

	private void updateSequenceGeneratorFromJava(CompilationUnit astRoot) {
		if (this.sequenceGeneratorAnnotationAdapter.getAnnotation(astRoot) == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(createSequenceGenerator());
			}
			((JavaSequenceGenerator) getSequenceGenerator()).updateFromJava(astRoot);
		}
	}

	/*
	 * The @Temporal annotation is a bit different than most JPA annotations.
	 * For some indecipherable reason it has no default value (e.g. TIMESTAMP).
	 * Also, it is *required* for any attribute declared with a type of
	 * java.util.Date or java.util.Calendar; otherwise, it is *prohibited*.
	 * As a result we allow a Basic mapping to have a null 'temporal',
	 * indicating that the annotation is completely missing, as opposed
	 * to the annotation being present but its value is invalid (e.g.
	 * @Temporal(FRIDAY)).
	 * 
	 * TODO this comment is wrong now, revisit this with Brian at some point
	 */
	private void updateTemporalFromJava(CompilationUnit astRoot) {
		if (this.temporalAnnotationAdapter.getAnnotation(astRoot) == null) {
			setTemporalGen(TemporalType.NULL);
		}
		else {
			setTemporalGen(TemporalType.fromJavaAnnotationValue(this.temporalValueAdapter.getValue(astRoot)));
		}
	}

	private JavaColumn getJavaColumn() {
		return (JavaColumn) this.column;
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getJavaColumn().candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String primaryKeyColumnName() {
		return this.getColumn().getName();
	}

	public IGeneratedValue createGeneratedValue() {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaGeneratedValue(getAttribute());
	}

	public ISequenceGenerator createSequenceGenerator() {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaSequenceGenerator(getAttribute());
	}

	public ITableGenerator createTableGenerator() {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaTableGenerator(getAttribute());
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	@Override
	public boolean isIdMapping() {
		return true;
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildTemporalValueAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(TEMPORAL_ADAPTER, JPA.TEMPORAL__VALUE, false);
	}
}
