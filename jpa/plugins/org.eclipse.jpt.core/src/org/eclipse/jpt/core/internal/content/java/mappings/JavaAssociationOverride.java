/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.jdtutility.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.utility.internal.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Association Override</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAssociationOverride()
 * @model kind="class"
 * @generated
 */
public class JavaAssociationOverride extends JavaOverride
	implements IAssociationOverride
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

	public static final SimpleDeclarationAnnotationAdapter SINGLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ASSOCIATION_OVERRIDE);

	public static final SimpleDeclarationAnnotationAdapter MULTIPLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ASSOCIATION_OVERRIDES);

	protected JavaAssociationOverride() {
		throw new UnsupportedOperationException("use JavaAssociationOverride(Owner, Member, IndexedDeclarationAnnotationAdapter)");
	}

	protected JavaAssociationOverride(Owner owner, Member member, IndexedDeclarationAnnotationAdapter daa) {
		super(owner, member, daa);
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
			case JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				specifiedJoinColumnsChanged(notification);
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

	@Override
	protected String nameElementName() {
		return JPA.ASSOCIATION_OVERRIDE__NAME;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_ASSOCIATION_OVERRIDE;
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAssociationOverride_SpecifiedJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getSpecifiedJoinColumns() {
		if (specifiedJoinColumns == null) {
			specifiedJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS);
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIAssociationOverride_DefaultJoinColumns()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IJoinColumn" containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getDefaultJoinColumns() {
		if (defaultJoinColumns == null) {
			defaultJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS);
		}
		return defaultJoinColumns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__JOIN_COLUMNS :
				return ((InternalEList<?>) getJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				return ((InternalEList<?>) getDefaultJoinColumns()).basicRemove(otherEnd, msgs);
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
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__JOIN_COLUMNS :
				return getJoinColumns();
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				return getSpecifiedJoinColumns();
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				return getDefaultJoinColumns();
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
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				getSpecifiedJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				getDefaultJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
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
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
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
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__JOIN_COLUMNS :
				return !getJoinColumns().isEmpty();
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
				return specifiedJoinColumns != null && !specifiedJoinColumns.isEmpty();
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
				return defaultJoinColumns != null && !defaultJoinColumns.isEmpty();
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
		if (baseClass == IAssociationOverride.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS;
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
		if (baseClass == IAssociationOverride.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__SPECIFIED_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE__DEFAULT_JOIN_COLUMNS;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public ITypeMapping typeMapping() {
		return (ITypeMapping) eContainer();
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.getSpecifiedJoinColumns().isEmpty();
	}

	public IJoinColumn createJoinColumn(int index) {
		return this.createJavaJoinColumn(index);
	}

	private JavaJoinColumn createJavaJoinColumn(int index) {
		return JavaJoinColumn.createAssociationOverrideJoinColumn(this, new JoinColumnOwner(this), this.getMember(), index);
	}

	// ********** jpa model -> java annotations **********
	/**
	 * slide over all the annotations that follow the new join column
	 */
	public void specifiedJoinColumnAdded(int index, IJoinColumn joinColumn) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (((JavaJoinColumn) joinColumn).annotation(getMember().astRoot()) == null) {
			this.synchJoinColumnAnnotationsAfterAdd(index + 1);
			((JavaJoinColumn) joinColumn).newAnnotation();
		}
	}

	// bjv look at this
	public void specifiedJoinColumnsAdded(int index, List<IJoinColumn> joinColumns) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
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

	private void synch(IJoinColumn joinColumn, int index) {
		((JavaJoinColumn) joinColumn).moveAnnotation(index);
	}

	@Override
	protected Iterator<String> candidateNames() {
		return this.getOwner().getTypeMapping().overridableAssociationNames();
	}

	@Override
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedCandidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (IJoinColumn column : this.getJoinColumns()) {
			result = ((JavaJoinColumn) column).connectedCandidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	static JavaAssociationOverride createAssociationOverride(Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaAssociationOverride(owner, member, buildAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildAnnotationAdapter(int index) {
		return new CombinationIndexedDeclarationAnnotationAdapter(SINGLE_DECLARATION_ANNOTATION_ADAPTER, MULTIPLE_DECLARATION_ANNOTATION_ADAPTER, index, JPA.ASSOCIATION_OVERRIDE);
	}
}
