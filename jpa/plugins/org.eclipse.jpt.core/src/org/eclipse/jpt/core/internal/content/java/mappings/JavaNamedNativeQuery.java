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
import org.eclipse.jpt.core.internal.jdtutility.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.INamedNativeQuery;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Named Native Query</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNamedNativeQuery()
 * @model kind="class"
 * @generated
 */
public class JavaNamedNativeQuery extends JavaAbstractQuery
	implements INamedNativeQuery
{
	/**
	 * The default value of the '{@link #getResultClass() <em>Result Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultClass()
	 * @generated
	 * @ordered
	 */
	protected static final String RESULT_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultClass() <em>Result Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultClass()
	 * @generated
	 * @ordered
	 */
	protected String resultClass = RESULT_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultSetMapping() <em>Result Set Mapping</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultSetMapping()
	 * @generated
	 * @ordered
	 */
	protected static final String RESULT_SET_MAPPING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultSetMapping() <em>Result Set Mapping</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultSetMapping()
	 * @generated
	 * @ordered
	 */
	protected String resultSetMapping = RESULT_SET_MAPPING_EDEFAULT;

	public static final SimpleDeclarationAnnotationAdapter SINGLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.NAMED_NATIVE_QUERY);

	public static final SimpleDeclarationAnnotationAdapter MULTIPLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.NAMED_NATIVE_QUERIES);

	private final AnnotationElementAdapter<String> resultClassAdapter;

	private final AnnotationElementAdapter<String> resultSetMappingAdapter;

	protected JavaNamedNativeQuery() {
		throw new UnsupportedOperationException("Use JavaNamedNativeQuery(Member) instead");
	}

	protected JavaNamedNativeQuery(Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(member, idaa);
		this.resultClassAdapter = this.buildAdapter(resultClassAdapter(idaa));
		this.resultSetMappingAdapter = this.buildAdapter(resultSetMappingAdapter(idaa));
	}

	// ********** initialization **********
	protected DeclarationAnnotationElementAdapter<String> resultClassAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.NAMED_NATIVE_QUERY__RESULT_CLASS);
	}

	protected DeclarationAnnotationElementAdapter<String> resultSetMappingAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JPA.NAMED_NATIVE_QUERY__RESULT_SET_MAPPING);
	}

	@Override
	protected String nameElementName() {
		return JPA.NAMED_NATIVE_QUERY__NAME;
	}

	@Override
	protected String queryElementName() {
		return JPA.NAMED_NATIVE_QUERY__QUERY;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(INamedNativeQuery.class)) {
			case JpaCoreMappingsPackage.INAMED_NATIVE_QUERY__RESULT_CLASS :
				this.resultClassAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaCoreMappingsPackage.INAMED_NATIVE_QUERY__RESULT_SET_MAPPING :
				this.resultSetMappingAdapter.setValue((String) notification.getNewValue());
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
		return JpaJavaMappingsPackage.Literals.JAVA_NAMED_NATIVE_QUERY;
	}

	/**
	 * Returns the value of the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Class</em>' attribute.
	 * @see #setResultClass(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getINamedNativeQuery_ResultClass()
	 * @model
	 * @generated
	 */
	public String getResultClass() {
		return resultClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery#getResultClass <em>Result Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Class</em>' attribute.
	 * @see #getResultClass()
	 * @generated
	 */
	public void setResultClass(String newResultClass) {
		String oldResultClass = resultClass;
		resultClass = newResultClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_CLASS, oldResultClass, resultClass));
	}

	/**
	 * Returns the value of the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Set Mapping</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Set Mapping</em>' attribute.
	 * @see #setResultSetMapping(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getINamedNativeQuery_ResultSetMapping()
	 * @model
	 * @generated
	 */
	public String getResultSetMapping() {
		return resultSetMapping;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery#getResultSetMapping <em>Result Set Mapping</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Set Mapping</em>' attribute.
	 * @see #getResultSetMapping()
	 * @generated
	 */
	public void setResultSetMapping(String newResultSetMapping) {
		String oldResultSetMapping = resultSetMapping;
		resultSetMapping = newResultSetMapping;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING, oldResultSetMapping, resultSetMapping));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_CLASS :
				return getResultClass();
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING :
				return getResultSetMapping();
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
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_CLASS :
				setResultClass((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING :
				setResultSetMapping((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_CLASS :
				setResultClass(RESULT_CLASS_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING :
				setResultSetMapping(RESULT_SET_MAPPING_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_CLASS :
				return RESULT_CLASS_EDEFAULT == null ? resultClass != null : !RESULT_CLASS_EDEFAULT.equals(resultClass);
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING :
				return RESULT_SET_MAPPING_EDEFAULT == null ? resultSetMapping != null : !RESULT_SET_MAPPING_EDEFAULT.equals(resultSetMapping);
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
		if (baseClass == INamedNativeQuery.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_CLASS :
					return JpaCoreMappingsPackage.INAMED_NATIVE_QUERY__RESULT_CLASS;
				case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING :
					return JpaCoreMappingsPackage.INAMED_NATIVE_QUERY__RESULT_SET_MAPPING;
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
		if (baseClass == INamedNativeQuery.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.INAMED_NATIVE_QUERY__RESULT_CLASS :
					return JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_CLASS;
				case JpaCoreMappingsPackage.INAMED_NATIVE_QUERY__RESULT_SET_MAPPING :
					return JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY__RESULT_SET_MAPPING;
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
		result.append(" (resultClass: ");
		result.append(resultClass);
		result.append(", resultSetMapping: ");
		result.append(resultSetMapping);
		result.append(')');
		return result.toString();
	}

	@Override
	protected void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setResultClass(this.resultClassAdapter.getValue(astRoot));
		this.setResultSetMapping(this.resultSetMappingAdapter.getValue(astRoot));
	}

	@Override
	protected JavaQueryHint createJavaQueryHint(int index) {
		return JavaQueryHint.createNamedNativeQueryQueryHint(this.getDeclarationAnnotationAdapter(), this.getMember(), index);
	}

	// ********** static methods **********
	static JavaNamedNativeQuery createJavaNamedNativeQuery(Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaNamedNativeQuery(member, buildAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildAnnotationAdapter(int index) {
		return new CombinationIndexedDeclarationAnnotationAdapter(SINGLE_DECLARATION_ANNOTATION_ADAPTER, MULTIPLE_DECLARATION_ANNOTATION_ADAPTER, index, JPA.NAMED_NATIVE_QUERY);
	}
}
