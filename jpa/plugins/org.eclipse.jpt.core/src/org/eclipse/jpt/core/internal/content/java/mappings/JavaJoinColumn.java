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

import java.util.Iterator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Join Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaJoinColumn()
 * @model kind="class"
 * @generated
 */
public class JavaJoinColumn extends AbstractJavaColumn implements IJoinColumn
{
	/**
	 * The default value of the '{@link #getReferencedColumnName() <em>Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedReferencedColumnName() <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedReferencedColumnName() <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedReferencedColumnName = SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultReferencedColumnName() <em>Default Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultReferencedColumnName() <em>Default Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected String defaultReferencedColumnName = DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT;

	private final IndexedAnnotationAdapter annotationAdapter;

	// hold this so we can get the 'referenced column name' text range
	private final DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;

	private final AnnotationElementAdapter<String> referencedColumnNameAdapter;

	public static final SimpleDeclarationAnnotationAdapter SINGLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.JOIN_COLUMN);

	public static final SimpleDeclarationAnnotationAdapter MULTIPLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.JOIN_COLUMNS);

	protected JavaJoinColumn() {
		super();
		throw new UnsupportedOperationException("Use JavaJoinColumn(Owner, Member, IndexedDeclarationAnnotationAdapter) instead");
	}

	protected JavaJoinColumn(IJoinColumn.Owner owner, Member member, IndexedDeclarationAnnotationAdapter daa) {
		super(owner, member, daa);
		this.annotationAdapter = new MemberIndexedAnnotationAdapter(member, daa);
		this.referencedColumnNameDeclarationAdapter = this.buildStringElementAdapter(JPA.JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		this.referencedColumnNameAdapter = this.buildShortCircuitElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}

	@Override
	protected String nameElementName() {
		return JPA.JOIN_COLUMN__NAME;
	}

	@Override
	protected String columnDefinitionElementName() {
		return JPA.JOIN_COLUMN__COLUMN_DEFINITION;
	}

	@Override
	protected String tableElementName() {
		return JPA.JOIN_COLUMN__TABLE;
	}

	@Override
	protected String uniqueElementName() {
		return JPA.JOIN_COLUMN__UNIQUE;
	}

	@Override
	protected String nullableElementName() {
		return JPA.JOIN_COLUMN__NULLABLE;
	}

	@Override
	protected String insertableElementName() {
		return JPA.JOIN_COLUMN__INSERTABLE;
	}

	@Override
	protected String updatableElementName() {
		return JPA.JOIN_COLUMN__UPDATABLE;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IAbstractJoinColumn.class)) {
			case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				this.referencedColumnNameAdapter.setValue((String) notification.getNewValue());
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
		return JpaJavaMappingsPackage.Literals.JAVA_JOIN_COLUMN;
	}

	/**
	 * Returns the value of the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIJoinColumn_ReferencedColumnName()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getReferencedColumnName() {
		return (this.specifiedReferencedColumnName == null) ? this.defaultReferencedColumnName : this.specifiedReferencedColumnName;
	}

	/**
	 * Returns the value of the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Referenced Column Name</em>' attribute.
	 * @see #setSpecifiedReferencedColumnName(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractJoinColumn_SpecifiedReferencedColumnName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedReferencedColumnName() {
		return specifiedReferencedColumnName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn#getSpecifiedReferencedColumnName <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Referenced Column Name</em>' attribute.
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 */
	public void setSpecifiedReferencedColumnName(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = specifiedReferencedColumnName;
		specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME, oldSpecifiedReferencedColumnName, specifiedReferencedColumnName));
	}

	/**
	 * Returns the value of the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Referenced Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAbstractJoinColumn_DefaultReferencedColumnName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultReferencedColumnName() {
		return defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME, oldDefaultReferencedColumnName, newDefaultReferencedColumnName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
				return getReferencedColumnName();
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				return getSpecifiedReferencedColumnName();
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
				return getDefaultReferencedColumnName();
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
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				setSpecifiedReferencedColumnName((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				setSpecifiedReferencedColumnName(SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
				return REFERENCED_COLUMN_NAME_EDEFAULT == null ? getReferencedColumnName() != null : !REFERENCED_COLUMN_NAME_EDEFAULT.equals(getReferencedColumnName());
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				return SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT == null ? specifiedReferencedColumnName != null : !SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT.equals(specifiedReferencedColumnName);
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
				return DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT == null ? defaultReferencedColumnName != null : !DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT.equals(defaultReferencedColumnName);
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
		if (baseClass == IAbstractJoinColumn.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
					return JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
					return JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME;
				case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
					return JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME;
				default :
					return -1;
			}
		}
		if (baseClass == IJoinColumn.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == IAbstractJoinColumn.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__REFERENCED_COLUMN_NAME;
				case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME;
				case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
					return JpaJavaMappingsPackage.JAVA_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME;
				default :
					return -1;
			}
		}
		if (baseClass == IJoinColumn.class) {
			switch (baseFeatureID) {
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
		result.append(" (specifiedReferencedColumnName: ");
		result.append(specifiedReferencedColumnName);
		result.append(", defaultReferencedColumnName: ");
		result.append(defaultReferencedColumnName);
		result.append(')');
		return result.toString();
	}

	@Override
	public IJoinColumn.Owner getOwner() {
		return (IJoinColumn.Owner) super.getOwner();
	}

	public Table dbReferencedColumnTable() {
		return getOwner().dbReferencedColumnTable();
	}

	public Column dbReferencedColumn() {
		Table table = this.dbReferencedColumnTable();
		return (table == null) ? null : table.columnNamed(this.getReferencedColumnName());
	}

	@Override
	public boolean tableIsAllowed() {
		return this.getOwner().tableIsAllowed();
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.referencedColumnNameDeclarationAdapter, pos, astRoot);
	}

	private Iterator<String> candidateReferencedColumnNames() {
		Table table = this.getOwner().dbReferencedColumnTable();
		return (table != null) ? table.columnNames() : EmptyIterator.<String> instance();
	}

	private Iterator<String> candidateReferencedColumnNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateReferencedColumnNames(), filter);
	}

	private Iterator<String> quotedCandidateReferencedColumnNames(Filter<String> filter) {
		return StringTools.quote(this.candidateReferencedColumnNames(filter));
	}

	@Override
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedCandidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.referencedColumnNameTouches(pos, astRoot)) {
			return this.quotedCandidateReferencedColumnNames(filter);
		}
		return null;
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}

	public ITextRange referencedColumnNameTextRange() {
		return elementTextRange(this.referencedColumnNameDeclarationAdapter);
	}

	/**
	 * allow owners to verify the annotation
	 */
	public Annotation annotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setSpecifiedReferencedColumnName(this.referencedColumnNameAdapter.getValue(astRoot));
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		this.setDefaultReferencedColumnName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_REFERENCED_COLUMN_NAME_KEY));
		this.setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_NAME_KEY));
		this.setDefaultTable((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_TABLE_KEY));
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
	static JavaJoinColumn createSingleRelationshipMappingJoinColumn(IJoinColumn.Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaJoinColumn(owner, member, buildSingleRelationshipMappingAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildSingleRelationshipMappingAnnotationAdapter(int index) {
		return new CombinationIndexedDeclarationAnnotationAdapter(SINGLE_DECLARATION_ANNOTATION_ADAPTER, MULTIPLE_DECLARATION_ANNOTATION_ADAPTER, index, JPA.JOIN_COLUMN);
	}

	static JavaJoinColumn createAssociationOverrideJoinColumn(DeclarationAnnotationAdapter associationOverrideAdapter, IJoinColumn.Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaJoinColumn(owner, member, buildAssociationOverrideAnnotationAdapter(associationOverrideAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildAssociationOverrideAnnotationAdapter(DeclarationAnnotationAdapter associationOverrideAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(associationOverrideAdapter, JPA.ASSOCIATION_OVERRIDE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

	static JavaJoinColumn createJoinTableJoinColumn(IJoinColumn.Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaJoinColumn(owner, member, buildJoinTableAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JavaJoinTable.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

	static JavaJoinColumn createJoinTableInverseJoinColumn(IJoinColumn.Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaJoinColumn(owner, member, buildJoinTableInverseAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableInverseAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JavaJoinTable.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__INVERSE_JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}
}
