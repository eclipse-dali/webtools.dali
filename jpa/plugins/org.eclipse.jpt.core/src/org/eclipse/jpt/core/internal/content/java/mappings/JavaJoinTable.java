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
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Join Table</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaJoinTable()
 * @model kind="class"
 * @generated
 */
public class JavaJoinTable extends AbstractJavaTable implements IJoinTable
{
	/**
	 * The cached value of the '{@link #getSpecifiedJoinColumns() <em>Specified Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> specifiedJoinColumns;

	/**
	 * The cached value of the '{@link #getDefaultJoinColumns() <em>Default Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> defaultJoinColumns;

	/**
	 * The cached value of the '{@link #getSpecifiedInverseJoinColumns() <em>Specified Inverse Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedInverseJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> specifiedInverseJoinColumns;

	/**
	 * The cached value of the '{@link #getDefaultInverseJoinColumns() <em>Default Inverse Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultInverseJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<IJoinColumn> defaultInverseJoinColumns;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.JOIN_TABLE);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__NAME);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__CATALOG);

	protected JavaJoinTable() {
		super();
		throw new UnsupportedOperationException("Use JavaJoinTable(Owner, Member) instead");
	}

	protected JavaJoinTable(Owner owner, Member member) {
		super(owner, member, DECLARATION_ANNOTATION_ADAPTER);
		this.getDefaultJoinColumns().add(this.createJoinColumn(new JoinColumnOwner(this), member));
		this.getDefaultInverseJoinColumns().add(this.createJoinColumn(new InverseJoinColumnOwner(this), member));
	}

	// ********** AbstractJavaTable implementation **********
	@Override
	protected DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @JoinTable is never nested
		return NAME_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> schemaAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @JoinTable is never nested
		return SCHEMA_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> catalogAdapter(DeclarationAnnotationAdapter declarationAnnotationAdapter) {
		// ignore the daa passed in, @JoinTable is never nested
		return CATALOG_ADAPTER;
	}

	private IJoinColumn createJoinColumn(IJoinColumn.Owner joinColumnOwner, Member joinColumnMember) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaJoinColumn(joinColumnOwner, joinColumnMember);
	}

	/**
	 * check for changes to the 'specifiedJoinColumns' and
	 * 'specifiedInverseJoinColumns' lists so we can notify the
	 * model adapter of any changes;
	 * also listen for changes to the 'defaultJoinColumns' and
	 * 'defaultInverseJoinColumns' lists so we can spank the developer
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IJoinTable.class)) {
			case JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				specifiedJoinColumnsChanged(notification);
				break;
			case JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				specifiedInverseJoinColumnsChanged(notification);
				break;
			default :
				break;
		}
	}

	@SuppressWarnings("unchecked")
	void specifiedJoinColumnsChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				specifiedJoinColumnAdded(notification.getPosition(), (IJoinColumn) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				specifiedJoinColumnsAdded(notification.getPosition(), (List<IJoinColumn>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				specifiedJoinColumnRemoved(notification.getPosition(), (IJoinColumn) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					specifiedJoinColumnsCleared((List<IJoinColumn>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					specifiedJoinColumnsRemoved((int[]) notification.getNewValue(), (List<IJoinColumn>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					specifiedJoinColumnSet(notification.getPosition(), (IJoinColumn) notification.getOldValue(), (IJoinColumn) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				specifiedJoinColumnMoved(notification.getOldIntValue(), notification.getPosition(), (IJoinColumn) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	@SuppressWarnings("unchecked")
	void specifiedInverseJoinColumnsChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				specifiedInverseJoinColumnAdded(notification.getPosition(), (IJoinColumn) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				specifiedInverseJoinColumnsAdded(notification.getPosition(), (List<IJoinColumn>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				specifiedInverseJoinColumnRemoved(notification.getPosition(), (IJoinColumn) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					specifiedInverseJoinColumnsCleared((List<IJoinColumn>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					specifiedInverseJoinColumnsRemoved((int[]) notification.getNewValue(), (List<IJoinColumn>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					specifiedInverseJoinColumnSet(notification.getPosition(), (IJoinColumn) notification.getOldValue(), (IJoinColumn) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				specifiedInverseJoinColumnMoved(notification.getOldIntValue(), notification.getPosition(), (IJoinColumn) notification.getNewValue());
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
		return JpaJavaMappingsPackage.Literals.JAVA_JOIN_TABLE;
	}

	public EList<IJoinColumn> getJoinColumns() {
		return this.getSpecifiedJoinColumns().isEmpty() ? this.getDefaultJoinColumns() : this.getSpecifiedJoinColumns();
	}

	/**
	 * Returns the value of the '<em><b>Specified Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIJoinTable_SpecifiedJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getSpecifiedJoinColumns() {
		if (specifiedJoinColumns == null) {
			specifiedJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS);
		}
		return specifiedJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Default Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIJoinTable_DefaultJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getDefaultJoinColumns() {
		if (defaultJoinColumns == null) {
			defaultJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_JOIN_COLUMNS);
		}
		return defaultJoinColumns;
	}

	public EList<IJoinColumn> getInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumns().isEmpty() ? this.getDefaultInverseJoinColumns() : this.getSpecifiedInverseJoinColumns();
	}

	/**
	 * Returns the value of the '<em><b>Specified Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIJoinTable_SpecifiedInverseJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getSpecifiedInverseJoinColumns() {
		if (specifiedInverseJoinColumns == null) {
			specifiedInverseJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS);
		}
		return specifiedInverseJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Default Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIJoinTable_DefaultInverseJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getDefaultInverseJoinColumns() {
		if (defaultInverseJoinColumns == null) {
			defaultInverseJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS);
		}
		return defaultInverseJoinColumns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__JOIN_COLUMNS :
				return ((InternalEList<?>) getJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__INVERSE_JOIN_COLUMNS :
				return ((InternalEList<?>) getInverseJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedInverseJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultInverseJoinColumns()).basicRemove(otherEnd, msgs);
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
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__JOIN_COLUMNS :
				return getJoinColumns();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				return getSpecifiedJoinColumns();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				return getDefaultJoinColumns();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__INVERSE_JOIN_COLUMNS :
				return getInverseJoinColumns();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				return getSpecifiedInverseJoinColumns();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				return getDefaultInverseJoinColumns();
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
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				getSpecifiedJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				getDefaultJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				getSpecifiedInverseJoinColumns().clear();
				getSpecifiedInverseJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				getDefaultInverseJoinColumns().clear();
				getDefaultInverseJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
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
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				getSpecifiedInverseJoinColumns().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				getDefaultInverseJoinColumns().clear();
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
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__JOIN_COLUMNS :
				return !getJoinColumns().isEmpty();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
				return specifiedJoinColumns != null && !specifiedJoinColumns.isEmpty();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
				return defaultJoinColumns != null && !defaultJoinColumns.isEmpty();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__INVERSE_JOIN_COLUMNS :
				return !getInverseJoinColumns().isEmpty();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
				return specifiedInverseJoinColumns != null && !specifiedInverseJoinColumns.isEmpty();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
				return defaultInverseJoinColumns != null && !defaultInverseJoinColumns.isEmpty();
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
		if (baseClass == IJoinTable.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__INVERSE_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__INVERSE_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS;
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
		if (baseClass == IJoinTable.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IJOIN_TABLE__JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_JOIN_TABLE__JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__INVERSE_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_JOIN_TABLE__INVERSE_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_JOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_JOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public IRelationshipMapping relationshipMapping() {
		return (IRelationshipMapping) this.eContainer();
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.updateSpecifiedJoinColumnsFromJava(astRoot);
		this.updateSpecifiedInverseJoinColumnsFromJava(astRoot);
	}

	@Override
	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		this.setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_JOIN_TABLE_NAME_KEY));
	}

	public boolean isSpecified() {
		return getMember().containsAnnotation(DECLARATION_ANNOTATION_ADAPTER);
	}

	/**
	 * here we just worry about getting the join column lists the same size;
	 * then we delegate to the join columns to synch themselves up
	 */
	private void updateSpecifiedJoinColumnsFromJava(CompilationUnit astRoot) {
		// synchronize the model join columns with the Java source
		List<IJoinColumn> joinColumns = this.getSpecifiedJoinColumns();
		int persSize = joinColumns.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaJoinColumn joinColumn = (JavaJoinColumn) joinColumns.get(i);
			if (joinColumn.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			joinColumn.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model join columns beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				joinColumns.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaJoinColumn joinColumn = this.createJavaJoinColumn(javaSize);
				if (joinColumn.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					this.getSpecifiedJoinColumns().add(joinColumn);
					joinColumn.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	/**
	 * here we just worry about getting the inverse join column lists the same size;
	 * then we delegate to the join columns to synch themselves up
	 */
	private void updateSpecifiedInverseJoinColumnsFromJava(CompilationUnit astRoot) {
		// synchronize the model join columns with the Java source
		List<IJoinColumn> inverseJoinColumns = this.getSpecifiedInverseJoinColumns();
		int persSize = inverseJoinColumns.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaJoinColumn inverseJoinColumn = (JavaJoinColumn) inverseJoinColumns.get(i);
			if (inverseJoinColumn.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			inverseJoinColumn.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model join columns beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				inverseJoinColumns.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaJoinColumn inverseJoinColumn = this.createJavaInverseJoinColumn(javaSize);
				if (inverseJoinColumn.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					this.getSpecifiedInverseJoinColumns().add(inverseJoinColumn);
					inverseJoinColumn.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	// ********** jpa model -> java annotations **********
	////////////////////////////////////////////////////////
	/**
	 * slide over all the annotations that follow the new join column
	 */
	public void specifiedJoinColumnAdded(int index, IJoinColumn joinColumn) {
		// JoinColumn was added to jpa model when updating from java, do not need
		// to edit the java in this case. TODO is there a better way to handle this??
		if (((JavaJoinColumn) joinColumn).annotation(getMember().astRoot()) == null) {
			this.synchJoinColumnAnnotationsAfterAdd(index + 1);
			((JavaJoinColumn) joinColumn).newAnnotation();
		}
	}

	// bjv look at this
	public void specifiedJoinColumnsAdded(int index, List<IJoinColumn> joinColumns) {
		// JoinColumn was added to jpa model when updating from java, do not need
		// to edit the java in this case. TODO is there a better way to handle this??
		if (!joinColumns.isEmpty() && ((JavaJoinColumn) joinColumns.get(0)).annotation(getMember().astRoot()) == null) {
			this.synchJoinColumnAnnotationsAfterAdd(index + joinColumns.size());
			for (IJoinColumn joinColumn : joinColumns) {
				((JavaJoinColumn) joinColumn).newAnnotation();
			}
		}
	}

	public void specifiedJoinColumnRemoved(int index, IJoinColumn joinColumn) {
		((JavaJoinColumn) joinColumn).removeAnnotation();
		this.synchJoinColumnAnnotationsAfterRemove(index);
	}

	public void specifiedJoinColumnsRemoved(int[] indexes, List<IJoinColumn> joinColumns) {
		for (IJoinColumn joinColumn : joinColumns) {
			((JavaJoinColumn) joinColumn).removeAnnotation();
		}
		this.synchJoinColumnAnnotationsAfterRemove(indexes[0]);
	}

	public void specifiedJoinColumnsCleared(List<IJoinColumn> joinColumns) {
		for (IJoinColumn joinColumn : joinColumns) {
			((JavaJoinColumn) joinColumn).removeAnnotation();
		}
	}

	public void specifiedJoinColumnSet(int index, IJoinColumn oldJoinColumn, IJoinColumn newJoinColumn) {
		((JavaJoinColumn) newJoinColumn).newAnnotation();
	}

	public void specifiedJoinColumnMoved(int sourceIndex, int targetIndex, IJoinColumn joinColumn) {
		List<IJoinColumn> joinColumns = this.getSpecifiedJoinColumns();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch(joinColumns.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchJoinColumnAnnotationsAfterAdd(int index) {
		List<IJoinColumn> joinColumns = this.getSpecifiedJoinColumns();
		for (int i = joinColumns.size(); i-- > index;) {
			this.synch(joinColumns.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchJoinColumnAnnotationsAfterRemove(int index) {
		List<IJoinColumn> joinColumns = this.getSpecifiedJoinColumns();
		for (int i = index; i < joinColumns.size(); i++) {
			this.synch(joinColumns.get(i), i);
		}
	}

	////////////////////////////////////////////////////////
	/**
	 * slide over all the annotations that follow the new inverse join column
	 */
	public void specifiedInverseJoinColumnAdded(int index, IJoinColumn inverseJoinColumn) {
		if (((JavaJoinColumn) inverseJoinColumn).annotation(getMember().astRoot()) == null) {
			this.synchInverseJoinColumnAnnotationsAfterAdd(index + 1);
			((JavaJoinColumn) inverseJoinColumn).newAnnotation();
		}
	}

	public void specifiedInverseJoinColumnsAdded(int index, List<IJoinColumn> inverseJoinColumns) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (!inverseJoinColumns.isEmpty() && ((JavaJoinColumn) inverseJoinColumns.get(0)).annotation(getMember().astRoot()) == null) {
			this.synchInverseJoinColumnAnnotationsAfterAdd(index + inverseJoinColumns.size());
			for (IJoinColumn inverseJoinColumn : inverseJoinColumns) {
				((JavaJoinColumn) inverseJoinColumn).newAnnotation();
			}
		}
	}

	public void specifiedInverseJoinColumnRemoved(int index, IJoinColumn inverseJoinColumn) {
		((JavaJoinColumn) inverseJoinColumn).removeAnnotation();
		this.synchInverseJoinColumnAnnotationsAfterRemove(index);
	}

	public void specifiedInverseJoinColumnsRemoved(int[] indexes, List<IJoinColumn> inverseJoinColumns) {
		for (IJoinColumn inverseJoinColumn : inverseJoinColumns) {
			((JavaJoinColumn) inverseJoinColumn).removeAnnotation();
		}
		this.synchInverseJoinColumnAnnotationsAfterRemove(indexes[0]);
	}

	public void specifiedInverseJoinColumnsCleared(List<IJoinColumn> inverseJoinColumns) {
		for (IJoinColumn inverseJoinColumn : inverseJoinColumns) {
			((JavaJoinColumn) inverseJoinColumn).removeAnnotation();
		}
	}

	public void specifiedInverseJoinColumnSet(int index, IJoinColumn oldInverseJoinColumn, IJoinColumn newInverseJoinColumn) {
		((JavaJoinColumn) newInverseJoinColumn).newAnnotation();
	}

	public void specifiedInverseJoinColumnMoved(int sourceIndex, int targetIndex, IJoinColumn inverseJoinColumn) {
		List<IJoinColumn> inverseJoinColumns = this.getSpecifiedInverseJoinColumns();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch(inverseJoinColumns.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model inverse join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchInverseJoinColumnAnnotationsAfterAdd(int index) {
		List<IJoinColumn> inverseJoinColumns = this.getSpecifiedInverseJoinColumns();
		for (int i = inverseJoinColumns.size(); i-- > index;) {
			this.synch(inverseJoinColumns.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model inverse join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchInverseJoinColumnAnnotationsAfterRemove(int index) {
		List<IJoinColumn> inverseJoinColumns = this.getSpecifiedInverseJoinColumns();
		for (int i = index; i < inverseJoinColumns.size(); i++) {
			this.synch(inverseJoinColumns.get(i), i);
		}
	}

	private void synch(IJoinColumn joinColumn, int index) {
		((JavaJoinColumn) joinColumn).moveAnnotation(index);
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (IJoinColumn column : this.getJoinColumns()) {
			result = ((JavaJoinColumn) column).candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		for (IJoinColumn column : this.getInverseJoinColumns()) {
			result = ((JavaJoinColumn) column).candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	protected JavaUniqueConstraint createJavaUniqueConstraint(int index) {
		return JavaUniqueConstraint.createJoinTableUniqueConstraint(new UniqueConstraintOwner(this), this.getMember(), index);
	}

	// ********** IJoinTable implementation **********
	public IJoinColumn createJoinColumn(int index) {
		return this.createJavaJoinColumn(index);
	}

	private JavaJoinColumn createJavaJoinColumn(int index) {
		return JavaJoinColumn.createJoinTableJoinColumn(new JoinColumnOwner(this), this.getMember(), index);
	}

	public IJoinColumn createInverseJoinColumn(int index) {
		return this.createJavaInverseJoinColumn(index);
	}

	private JavaJoinColumn createJavaInverseJoinColumn(int index) {
		return JavaJoinColumn.createJoinTableInverseJoinColumn(new InverseJoinColumnOwner(this), this.getMember(), index);
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.getSpecifiedJoinColumns().isEmpty();
	}

	public boolean containsSpecifiedInverseJoinColumns() {
		return !this.getSpecifiedInverseJoinColumns().isEmpty();
	}
}
