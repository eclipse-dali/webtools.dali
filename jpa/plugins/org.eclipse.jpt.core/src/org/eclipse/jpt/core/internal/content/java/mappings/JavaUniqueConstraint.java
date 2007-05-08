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

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IUniqueConstraint;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Unique Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaUniqueConstraint()
 * @model kind="class"
 * @generated
 */
public class JavaUniqueConstraint extends JavaEObject
	implements IUniqueConstraint
{
	/**
	 * The cached value of the '{@link #getColumnNames() <em>Column Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> columnNames;

	private final Member member;

	private final IndexedDeclarationAnnotationAdapter idaa;

	private final IndexedAnnotationAdapter annotationAdapter;

	protected JavaUniqueConstraint() {
		super();
		throw new UnsupportedOperationException("Use JavaUniqueConstraint(Member) instead");
	}

	protected JavaUniqueConstraint(Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super();
		this.member = member;
		this.idaa = idaa;
		this.annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_UNIQUE_CONSTRAINT;
	}

	/**
	 * Returns the value of the '<em><b>Column Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Names</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Names</em>' attribute list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIUniqueConstraint_ColumnNames()
	 * @model type="java.lang.String"
	 * @generated
	 */
	public EList<String> getColumnNames() {
		if (columnNames == null) {
			columnNames = new EDataTypeUniqueEList<String>(String.class, this, JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES);
		}
		return columnNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES :
				return getColumnNames();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES :
				getColumnNames().clear();
				getColumnNames().addAll((Collection<? extends String>) newValue);
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
			case JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES :
				getColumnNames().clear();
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
			case JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES :
				return columnNames != null && !columnNames.isEmpty();
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
		if (baseClass == IUniqueConstraint.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES :
					return JpaCoreMappingsPackage.IUNIQUE_CONSTRAINT__COLUMN_NAMES;
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
		if (baseClass == IUniqueConstraint.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IUNIQUE_CONSTRAINT__COLUMN_NAMES :
					return JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES;
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
		result.append(" (columnNames: ");
		result.append(columnNames);
		result.append(')');
		return result.toString();
	}

	public ITextRange getTextRange() {
		return this.member.annotationTextRange(this.idaa);
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
	static JavaUniqueConstraint createSecondaryTableUniqueConstraint(JavaSecondaryTable secondaryTable, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaUniqueConstraint(member, buildSecondaryTableUniqueConstraintAnnotationAdapter(secondaryTable, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildSecondaryTableUniqueConstraintAnnotationAdapter(JavaSecondaryTable secondaryTable, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(secondaryTable.getDeclarationAnnotationAdapter(), JPA.SECONDARY_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static JavaUniqueConstraint createJoinTableUniqueConstraint(Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaUniqueConstraint(member, buildJoinTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JavaJoinTable.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static JavaUniqueConstraint createTableUniqueConstraint(Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaUniqueConstraint(member, buildTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JavaTable.DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}
} // JavaUniqueConstraint
