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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.mappings.IQueryHint;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Query Hint</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaQueryHint()
 * @model kind="class"
 * @generated
 */
public class JavaQueryHint extends JavaEObject implements IQueryHint
{
	private final Member member;

	// hold this so we can get the annotation's text range
	private final IndexedDeclarationAnnotationAdapter idaa;

	private final IndexedAnnotationAdapter annotationAdapter;

	private final AnnotationElementAdapter nameAdapter;

	private final AnnotationElementAdapter valueAdapter;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	protected JavaQueryHint() {
		throw new UnsupportedOperationException("Use JavaQueryHint(Member, IndexedDeclarationAnnotationAdapter) instead");
	}

	protected JavaQueryHint(Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super();
		this.member = member;
		this.idaa = idaa;
		this.annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		this.nameAdapter = this.buildAdapter(nameAdapter(idaa));
		this.valueAdapter = this.buildAdapter(valueAdapter(idaa));
	}

	// ********** initialization **********
	protected AnnotationElementAdapter buildAdapter(DeclarationAnnotationElementAdapter daea) {
		return new ShortCircuitAnnotationElementAdapter(this.member, daea);
	}

	protected DeclarationAnnotationElementAdapter nameAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter(daa, JPA.QUERY_HINT__NAME);
	}

	protected DeclarationAnnotationElementAdapter valueAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter(daa, JPA.QUERY_HINT__VALUE);
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IQueryHint.class)) {
			case JpaCoreMappingsPackage.IQUERY_HINT__NAME :
				this.nameAdapter.setValue(notification.getNewValue());
				break;
			case JpaCoreMappingsPackage.IQUERY_HINT__VALUE :
				this.valueAdapter.setValue(notification.getNewValue());
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
		return JpaJavaMappingsPackage.Literals.JAVA_QUERY_HINT;
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIQueryHint_Name()
	 * @model
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_QUERY_HINT__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIQueryHint_Value()
	 * @model
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_QUERY_HINT__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT__NAME :
				return getName();
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT__VALUE :
				return getValue();
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
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT__NAME :
				setName((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT__VALUE :
				setValue((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT__NAME :
				setName(NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT__VALUE :
				setValue(VALUE_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT__NAME :
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT__VALUE :
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		if (baseClass == IQueryHint.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_QUERY_HINT__NAME :
					return JpaCoreMappingsPackage.IQUERY_HINT__NAME;
				case JpaJavaMappingsPackage.JAVA_QUERY_HINT__VALUE :
					return JpaCoreMappingsPackage.IQUERY_HINT__VALUE;
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
		if (baseClass == IQueryHint.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IQUERY_HINT__NAME :
					return JpaJavaMappingsPackage.JAVA_QUERY_HINT__NAME;
				case JpaCoreMappingsPackage.IQUERY_HINT__VALUE :
					return JpaJavaMappingsPackage.JAVA_QUERY_HINT__VALUE;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

	// ********* IJpaSourceObject implementation ***********
	public ITextRange getTextRange() {
		return this.member.annotationTextRange(this.idaa);
	}

	protected void updateFromJava(CompilationUnit astRoot) {
		this.setName((String) this.nameAdapter.getValue(astRoot));
		this.setValue((String) this.valueAdapter.getValue(astRoot));
	}

	/**
	 * allow owners to verify the annotation
	 */
	public Annotation annotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	// ********** persistence model -> java annotations **********
	void moveAnnotation(int newIndex) {
		this.annotationAdapter.moveAnnotation(newIndex);
	}

	void newAnnotation() {
		this.annotationAdapter.newMarkerAnnotation();
	}

	void removeAnnotation() {
		this.annotationAdapter.removeAnnotation();
	}

	// ********** static methods **********
	static JavaQueryHint createNamedQueryQueryHint(JavaNamedQuery namedQuery, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaQueryHint(member, buildNamedQueryQueryHintAnnotationAdapter(namedQuery, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedQueryQueryHintAnnotationAdapter(JavaNamedQuery namedQuery, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedQuery.getDeclarationAnnotationAdapter(), JPA.NAMED_QUERY__HINTS, index, JPA.QUERY_HINT);
	}

	static JavaQueryHint createNamedNativeQueryQueryHint(JavaNamedNativeQuery namedNativeQuery, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaQueryHint(member, buildNamedNativeQueryQueryHintAnnotationAdapter(namedNativeQuery, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildNamedNativeQueryQueryHintAnnotationAdapter(JavaNamedNativeQuery namedNativeQuery, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(namedNativeQuery.getDeclarationAnnotationAdapter(), JPA.NAMED_NATIVE_QUERY__HINTS, index, JPA.QUERY_HINT);
	}
} // JavaQueryHint
