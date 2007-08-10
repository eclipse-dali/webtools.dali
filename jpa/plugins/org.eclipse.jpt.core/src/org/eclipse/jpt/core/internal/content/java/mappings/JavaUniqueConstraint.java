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
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitArrayAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IUniqueConstraint;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

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

	private final IUniqueConstraint.Owner owner;

	private final Member member;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.UNIQUE_CONSTRAINT);

	private final IndexedDeclarationAnnotationAdapter idaa;

	private final IndexedAnnotationAdapter annotationAdapter;

	private final DeclarationAnnotationElementAdapter<String[]> columnNamesDeclarationAdapter;

	private final AnnotationElementAdapter<String[]> columnNamesAdapter;

	protected JavaUniqueConstraint() {
		super();
		throw new UnsupportedOperationException("Use JavaUniqueConstraint(IUniqueConstraint.Owner, Member, IndexedDeclarationAnnotationAdapter) instead");
	}

	protected JavaUniqueConstraint(IUniqueConstraint.Owner owner, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super();
		this.owner = owner;
		this.member = member;
		this.idaa = idaa;
		this.annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		this.columnNamesDeclarationAdapter = buildArrayAnnotationElementAdapter(idaa, JPA.UNIQUE_CONSTRAINT__COLUMN_NAMES);
		this.columnNamesAdapter = this.buildAnnotationElementAdapter(this.columnNamesDeclarationAdapter);
	}

	protected AnnotationElementAdapter<String[]> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String[]> daea) {
		return new ShortCircuitArrayAnnotationElementAdapter<String>(this.member, daea);
	}

	protected static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return buildArrayAnnotationElementAdapter(annotationAdapter, elementName, AnnotationStringArrayExpressionConverter.forStrings());
	}

	protected static DeclarationAnnotationElementAdapter<String[]> buildArrayAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String[]> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, false, converter);
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IUniqueConstraint.class)) {
			case JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES :
				this.columnNamesAdapter.setValue((String[]) getColumnNames().toArray());
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
	 * @model unique="false"
	 * @generated
	 */
	public EList<String> getColumnNames() {
		if (columnNames == null) {
			columnNames = new EDataTypeEList<String>(String.class, this, JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT__COLUMN_NAMES);
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

	public IUniqueConstraint.Owner getOwner() {
		return this.owner;
	}

	public ITextRange validationTextRange() {
		return this.member.annotationTextRange(this.idaa);
	}

	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.member.annotationElementTextRange(elementAdapter, astRoot), pos);
	}

	private boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.columnNamesDeclarationAdapter, pos, astRoot);
	}

	private Iterator<String> candidateColumnNames() {
		return this.getOwner().candidateUniqueConstraintColumnNames();
	}

	private Iterator<String> candidateColumnNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateColumnNames(), filter);
	}

	private Iterator<String> quotedCandidateColumnNames(Filter<String> filter) {
		return StringTools.quote(this.candidateColumnNames(filter));
	}

	@Override
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedCandidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.columnNamesTouches(pos, astRoot)) {
			return this.quotedCandidateColumnNames(filter);
		}
		return null;
	}

	/**
	 * allow owners to verify the annotation
	 */
	public Annotation annotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	public void updateFromJava(CompilationUnit astRoot) {
		updateColumnNamesFromJava(astRoot);
	}

	private void updateColumnNamesFromJava(CompilationUnit astRoot) {
		String[] javaColumnNames = this.columnNamesAdapter.getValue(astRoot);
		CollectionTools.retainAll(getColumnNames(), javaColumnNames);
		for (int i = 0; i < javaColumnNames.length; i++) {
			String columnName = javaColumnNames[i];
			if (!getColumnNames().contains(columnName)) {
				getColumnNames().add(columnName);
			}
		}
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
	static JavaUniqueConstraint createSecondaryTableUniqueConstraint(IUniqueConstraint.Owner owner, DeclarationAnnotationAdapter declarationAnnotationAdapter, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaUniqueConstraint(owner, member, buildSecondaryTableUniqueConstraintAnnotationAdapter(declarationAnnotationAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildSecondaryTableUniqueConstraintAnnotationAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(declarationAnnotationAdapter, JPA.SECONDARY_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static JavaUniqueConstraint createJoinTableUniqueConstraint(IUniqueConstraint.Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaUniqueConstraint(owner, member, buildJoinTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JavaJoinTable.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static JavaUniqueConstraint createTableUniqueConstraint(IUniqueConstraint.Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaUniqueConstraint(owner, member, buildTableUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildTableUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JavaTable.DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	static JavaUniqueConstraint createTableGeneratorUniqueConstraint(IUniqueConstraint.Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaUniqueConstraint(owner, member, buildTableGeneratorUniqueConstraintAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildTableGeneratorUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JavaTableGenerator.DECLARATION_ANNOTATION_ADAPTER, JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}
}
