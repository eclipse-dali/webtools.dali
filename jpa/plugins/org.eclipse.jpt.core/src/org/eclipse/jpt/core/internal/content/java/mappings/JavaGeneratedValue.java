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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.GenerationType;
import org.eclipse.jpt.core.internal.mappings.IGeneratedValue;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Generated Value</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaGeneratedValue()
 * @model kind="class"
 * @generated
 */
public class JavaGeneratedValue extends JavaEObject implements IGeneratedValue
{
	private Member member;

	private final AnnotationElementAdapter<String> strategyAdapter;

	private final AnnotationElementAdapter<String> generatorAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.GENERATED_VALUE);

	private static final DeclarationAnnotationElementAdapter<String> STRATEGY_ADAPTER = buildStrategyAdapter();

	private static final DeclarationAnnotationElementAdapter<String> GENERATOR_ADAPTER = buildGeneratorAdapter();

	/**
	 * The default value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final GenerationType STRATEGY_EDEFAULT = GenerationType.DEFAULT;

	/**
	 * The cached value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected GenerationType strategy = STRATEGY_EDEFAULT;

	/**
	 * The default value of the '{@link #getGenerator() <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenerator()
	 * @generated
	 * @ordered
	 */
	protected static final String GENERATOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGenerator() <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenerator()
	 * @generated
	 * @ordered
	 */
	protected String generator = GENERATOR_EDEFAULT;

	protected JavaGeneratedValue() {
		throw new UnsupportedOperationException("Use JavaGeneratedValue(Member) instead");
	}

	protected JavaGeneratedValue(Member member) {
		super();
		this.member = member;
		this.strategyAdapter = new ShortCircuitAnnotationElementAdapter<String>(this.member, STRATEGY_ADAPTER);
		this.generatorAdapter = new ShortCircuitAnnotationElementAdapter<String>(this.member, GENERATOR_ADAPTER);
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IGeneratedValue.class)) {
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__GENERATOR :
				this.generatorAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__STRATEGY :
				this.strategyAdapter.setValue(((GenerationType) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
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
		return JpaJavaMappingsPackage.Literals.JAVA_GENERATED_VALUE;
	}

	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.GenerationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.GenerationType
	 * @see #setStrategy(GenerationType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIGeneratedValue_Strategy()
	 * @model
	 * @generated
	 */
	public GenerationType getStrategy() {
		return strategy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.GenerationType
	 * @see #getStrategy()
	 * @generated
	 */
	public void setStrategy(GenerationType newStrategy) {
		GenerationType oldStrategy = strategy;
		strategy = newStrategy == null ? STRATEGY_EDEFAULT : newStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__STRATEGY, oldStrategy, strategy));
	}

	/**
	 * Returns the value of the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generator</em>' attribute.
	 * @see #setGenerator(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIGeneratedValue_Generator()
	 * @model
	 * @generated
	 */
	public String getGenerator() {
		return generator;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue#getGenerator <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generator</em>' attribute.
	 * @see #getGenerator()
	 * @generated
	 */
	public void setGenerator(String newGenerator) {
		String oldGenerator = generator;
		generator = newGenerator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__GENERATOR, oldGenerator, generator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__STRATEGY :
				return getStrategy();
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__GENERATOR :
				return getGenerator();
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
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__STRATEGY :
				setStrategy((GenerationType) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__GENERATOR :
				setGenerator((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__STRATEGY :
				setStrategy(STRATEGY_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__GENERATOR :
				setGenerator(GENERATOR_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__STRATEGY :
				return strategy != STRATEGY_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__GENERATOR :
				return GENERATOR_EDEFAULT == null ? generator != null : !GENERATOR_EDEFAULT.equals(generator);
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
		if (baseClass == IGeneratedValue.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__STRATEGY :
					return JpaCoreMappingsPackage.IGENERATED_VALUE__STRATEGY;
				case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__GENERATOR :
					return JpaCoreMappingsPackage.IGENERATED_VALUE__GENERATOR;
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
		if (baseClass == IGeneratedValue.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IGENERATED_VALUE__STRATEGY :
					return JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__STRATEGY;
				case JpaCoreMappingsPackage.IGENERATED_VALUE__GENERATOR :
					return JpaJavaMappingsPackage.JAVA_GENERATED_VALUE__GENERATOR;
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
		result.append(" (strategy: ");
		result.append(strategy);
		result.append(", generator: ");
		result.append(generator);
		result.append(')');
		return result.toString();
	}

	public ITextRange validationTextRange() {
		return this.member.annotationTextRange(DECLARATION_ANNOTATION_ADAPTER);
	}

	public ITextRange generatorTextRange() {
		return elementTextRange(this.member.annotationElementTextRange(GENERATOR_ADAPTER));
	}

	// ********** java annotations -> persistence model **********
	public void updateFromJava(CompilationUnit astRoot) {
		setStrategy(GenerationType.fromJavaAnnotationValue(this.strategyAdapter.getValue(astRoot)));
		setGenerator(this.generatorAdapter.getValue(astRoot));
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildStrategyAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.GENERATED_VALUE__STRATEGY, false);
	}

	private static DeclarationAnnotationElementAdapter<String> buildGeneratorAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.GENERATED_VALUE__GENERATOR, false);
	}
} // JavaGeneratedValue
