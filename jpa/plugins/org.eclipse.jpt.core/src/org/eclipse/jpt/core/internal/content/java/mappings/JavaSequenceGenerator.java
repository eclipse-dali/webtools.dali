/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Sequence Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaSequenceGenerator()
 * @model kind="class"
 * @generated
 */
public class JavaSequenceGenerator extends JavaGenerator
	implements ISequenceGenerator
{
	private final AnnotationElementAdapter sequenceNameAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.SEQUENCE_GENERATOR);

	private static final DeclarationAnnotationElementAdapter NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter INITIAL_VALUE_ADAPTER = buildNumberAdapter(JPA.SEQUENCE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter ALLOCATION_SIZE_ADAPTER = buildNumberAdapter(JPA.SEQUENCE_GENERATOR__ALLOCATION_SIZE);

	private static final DeclarationAnnotationElementAdapter SEQUENCE_NAME_ADAPTER = buildAdapter(JPA.SEQUENCE_GENERATOR__SEQUENCE_NAME);

	/**
	 * The default value of the '{@link #getSequenceName() <em>Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceName()
	 * @generated
	 * @ordered
	 */
	protected static final String SEQUENCE_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedSequenceName() <em>Specified Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSequenceName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_SEQUENCE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedSequenceName() <em>Specified Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSequenceName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedSequenceName = SPECIFIED_SEQUENCE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultSequenceName() <em>Default Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSequenceName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_SEQUENCE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultSequenceName() <em>Default Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSequenceName()
	 * @generated
	 * @ordered
	 */
	protected String defaultSequenceName = DEFAULT_SEQUENCE_NAME_EDEFAULT;

	protected JavaSequenceGenerator() {
		throw new UnsupportedOperationException("Use JavaSequenceGenerator(Member) instead");
	}

	protected JavaSequenceGenerator(Member member) {
		super(member);
		this.sequenceNameAdapter = this.buildAdapter(SEQUENCE_NAME_ADAPTER);
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(ISequenceGenerator.class)) {
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
				this.sequenceNameAdapter.setValue(notification.getNewIntValue());
				break;
			default :
				break;
		}
	}

	// ********** initialization **********
	protected DeclarationAnnotationAdapter annotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	protected DeclarationAnnotationElementAdapter nameAdapter() {
		return NAME_ADAPTER;
	}

	protected DeclarationAnnotationElementAdapter initialValueAdapter() {
		return INITIAL_VALUE_ADAPTER;
	}

	protected DeclarationAnnotationElementAdapter allocationSizeAdapter() {
		return ALLOCATION_SIZE_ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_SEQUENCE_GENERATOR;
	}

	public String getSequenceName() {
		return (this.getSpecifiedSequenceName() == null) ? getDefaultSequenceName() : this.getSpecifiedSequenceName();
	}

	/**
	 * Returns the value of the '<em><b>Specified Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Sequence Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Sequence Name</em>' attribute.
	 * @see #setSpecifiedSequenceName(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getISequenceGenerator_SpecifiedSequenceName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedSequenceName() {
		return specifiedSequenceName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator#getSpecifiedSequenceName <em>Specified Sequence Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Sequence Name</em>' attribute.
	 * @see #getSpecifiedSequenceName()
	 * @generated
	 */
	public void setSpecifiedSequenceName(String newSpecifiedSequenceName) {
		String oldSpecifiedSequenceName = specifiedSequenceName;
		specifiedSequenceName = newSpecifiedSequenceName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME, oldSpecifiedSequenceName, specifiedSequenceName));
	}

	/**
	 * Returns the value of the '<em><b>Default Sequence Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Sequence Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Sequence Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getISequenceGenerator_DefaultSequenceName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultSequenceName() {
		return defaultSequenceName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SEQUENCE_NAME :
				return getSequenceName();
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
				return getSpecifiedSequenceName();
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME :
				return getDefaultSequenceName();
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
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
				setSpecifiedSequenceName((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
				setSpecifiedSequenceName(SPECIFIED_SEQUENCE_NAME_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SEQUENCE_NAME :
				return SEQUENCE_NAME_EDEFAULT == null ? getSequenceName() != null : !SEQUENCE_NAME_EDEFAULT.equals(getSequenceName());
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
				return SPECIFIED_SEQUENCE_NAME_EDEFAULT == null ? specifiedSequenceName != null : !SPECIFIED_SEQUENCE_NAME_EDEFAULT.equals(specifiedSequenceName);
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME :
				return DEFAULT_SEQUENCE_NAME_EDEFAULT == null ? defaultSequenceName != null : !DEFAULT_SEQUENCE_NAME_EDEFAULT.equals(defaultSequenceName);
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
		if (baseClass == ISequenceGenerator.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SEQUENCE_NAME :
					return JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__SEQUENCE_NAME;
				case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
					return JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME;
				case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME :
					return JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME;
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
		if (baseClass == ISequenceGenerator.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__SEQUENCE_NAME :
					return JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SEQUENCE_NAME;
				case JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME :
					return JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__SPECIFIED_SEQUENCE_NAME;
				case JpaCoreMappingsPackage.ISEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME :
					return JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR__DEFAULT_SEQUENCE_NAME;
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
		result.append(" (specifiedSequenceName: ");
		result.append(specifiedSequenceName);
		result.append(", defaultSequenceName: ");
		result.append(defaultSequenceName);
		result.append(')');
		return result.toString();
	}

	// ********** java annotations -> persistence model **********
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		setSpecifiedSequenceName((String) this.sequenceNameAdapter.getValue(astRoot));
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	private static DeclarationAnnotationElementAdapter buildNumberAdapter(String elementName) {
		return buildNumberAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}
} // JavaSequenceGenerator
