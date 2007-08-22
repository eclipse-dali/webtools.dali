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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.BooleanStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.utility.internal.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Single Relationship Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaSingleRelationshipMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class JavaSingleRelationshipMapping
	extends JavaRelationshipMapping implements ISingleRelationshipMapping
{
	private AnnotationElementAdapter<String> optionalAdapter;

	/**
	 * The default value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultEagerFetchType FETCH_EDEFAULT = DefaultEagerFetchType.DEFAULT;

	/**
	 * The cached value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected DefaultEagerFetchType fetch = FETCH_EDEFAULT;

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
	 * The default value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultTrueBoolean OPTIONAL_EDEFAULT = DefaultTrueBoolean.DEFAULT;

	/**
	 * The cached value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected DefaultTrueBoolean optional = OPTIONAL_EDEFAULT;

	protected JavaSingleRelationshipMapping() {
		throw new UnsupportedOperationException("Use JavaSingleRelationshipMapping(Attribute) instead");
	}

	protected JavaSingleRelationshipMapping(Attribute attribute) {
		super(attribute);
		this.optionalAdapter = this.buildAnnotationElementAdapter(this.optionalAdapter());
		this.getDefaultJoinColumns().add(this.createJoinColumn(new ISingleRelationshipMapping.JoinColumnOwner(this), attribute));
	}

	private IJoinColumn createJoinColumn(IJoinColumn.Owner joinColumnOwner, Member member) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaJoinColumn(joinColumnOwner, member);
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
		switch (notification.getFeatureID(ISingleRelationshipMapping.class)) {
			case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
				this.optionalAdapter.setValue(((DefaultTrueBoolean) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
			case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				specifiedJoinColumnsChanged(notification);
				break;
			case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__FETCH :
				this.getFetchAdapter().setValue(((DefaultEagerFetchType) notification.getNewValue()).convertToJavaAnnotationValue());
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

	// ********** jpa model -> java annotations **********
	/**
	 * slide over all the annotations that follow the new join column
	 */
	public void specifiedJoinColumnAdded(int index, IJoinColumn joinColumn) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (((JavaJoinColumn) joinColumn).annotation(getAttribute().astRoot()) == null) {
			this.synchJoinColumnAnnotationsAfterAdd(index + 1);
			((JavaJoinColumn) joinColumn).newAnnotation();
		}
	}

	public void specifiedJoinColumnsAdded(int index, List<IJoinColumn> joinColumns) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (!joinColumns.isEmpty() && ((JavaJoinColumn) joinColumns.get(0)).annotation(getAttribute().astRoot()) == null) {
			this.synchJoinColumnAnnotationsAfterAdd(index + joinColumns.size());
			for (Iterator<IJoinColumn> stream = joinColumns.iterator(); stream.hasNext();) {
				JavaJoinColumn joinColumn = (JavaJoinColumn) stream.next();
				joinColumn.newAnnotation();
			}
		}
	}

	public void specifiedJoinColumnRemoved(int index, IJoinColumn joinColumn) {
		((JavaJoinColumn) joinColumn).removeAnnotation();
		this.synchJoinColumnAnnotationsAfterRemove(index);
	}

	public void specifiedJoinColumnsRemoved(int[] indexes, List<IJoinColumn> joinColumns) {
		for (Iterator<IJoinColumn> stream = joinColumns.iterator(); stream.hasNext();) {
			JavaJoinColumn joinColumn = (JavaJoinColumn) stream.next();
			joinColumn.removeAnnotation();
		}
		this.synchJoinColumnAnnotationsAfterRemove(indexes[0]);
	}

	public void specifiedJoinColumnsCleared(List<IJoinColumn> joinColumns) {
		for (Iterator<IJoinColumn> stream = joinColumns.iterator(); stream.hasNext();) {
			JavaJoinColumn joinColumn = (JavaJoinColumn) stream.next();
			joinColumn.removeAnnotation();
		}
	}

	public void specifiedJoinColumnSet(int index, IJoinColumn oldJoinColumn, IJoinColumn newJoinColumn) {
		((JavaJoinColumn) newJoinColumn).newAnnotation();
	}

	public void specifiedJoinColumnMoved(int sourceIndex, int targetIndex, IJoinColumn joinColumn) {
		List<IJoinColumn> joinColumns = getSpecifiedJoinColumns();
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
		List<IJoinColumn> joinColumns = getSpecifiedJoinColumns();
		for (int i = joinColumns.size(); i-- > index;) {
			this.synch(joinColumns.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchJoinColumnAnnotationsAfterRemove(int index) {
		List<IJoinColumn> joinColumns = getSpecifiedJoinColumns();
		for (int i = index; i < joinColumns.size(); i++) {
			this.synch(joinColumns.get(i), i);
		}
	}

	private void synch(IJoinColumn joinColumn, int index) {
		((JavaJoinColumn) joinColumn).moveAnnotation(index);
	}

	/**
	 * return the Java adapter's 'optional' element adapter config
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> optionalAdapter();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_SINGLE_RELATIONSHIP_MAPPING;
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #setFetch(DefaultEagerFetchType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getISingleRelationshipMapping_Fetch()
	 * @model
	 * @generated
	 */
	public DefaultEagerFetchType getFetch() {
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultEagerFetchType
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(DefaultEagerFetchType newFetch) {
		DefaultEagerFetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH, oldFetch, fetch));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getISingleRelationshipMapping_SpecifiedJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getSpecifiedJoinColumns() {
		if (specifiedJoinColumns == null) {
			specifiedJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS);
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getISingleRelationshipMapping_DefaultJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<IJoinColumn> getDefaultJoinColumns() {
		if (defaultJoinColumns == null) {
			defaultJoinColumns = new EObjectContainmentEList<IJoinColumn>(IJoinColumn.class, this, JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS);
		}
		return defaultJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #setOptional(DefaultTrueBoolean)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getISingleRelationshipMapping_Optional()
	 * @model
	 * @generated
	 */
	public DefaultTrueBoolean getOptional() {
		return optional;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping#getOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean
	 * @see #getOptional()
	 * @generated
	 */
	public void setOptional(DefaultTrueBoolean newOptional) {
		DefaultTrueBoolean oldOptional = optional;
		optional = newOptional == null ? OPTIONAL_EDEFAULT : newOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL, oldOptional, optional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
				return ((InternalEList<?>) getJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				return ((InternalEList<?>) getSpecifiedJoinColumns()).basicRemove(otherEnd, msgs);
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
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
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH :
				return getFetch();
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
				return getJoinColumns();
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				return getSpecifiedJoinColumns();
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				return getDefaultJoinColumns();
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
				return getOptional();
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
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH :
				setFetch((DefaultEagerFetchType) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				getSpecifiedJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				getDefaultJoinColumns().addAll((Collection<? extends IJoinColumn>) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
				setOptional((DefaultTrueBoolean) newValue);
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
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH :
				setFetch(FETCH_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				getSpecifiedJoinColumns().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				getDefaultJoinColumns().clear();
				return;
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
				setOptional(OPTIONAL_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH :
				return fetch != FETCH_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
				return !getJoinColumns().isEmpty();
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
				return specifiedJoinColumns != null && !specifiedJoinColumns.isEmpty();
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
				return defaultJoinColumns != null && !defaultJoinColumns.isEmpty();
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
				return optional != OPTIONAL_EDEFAULT;
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
		if (baseClass == ISingleRelationshipMapping.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__FETCH;
				case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;
				case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
					return JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__OPTIONAL;
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
		if (baseClass == ISingleRelationshipMapping.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__FETCH :
					return JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__FETCH;
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS :
					return JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__DEFAULT_JOIN_COLUMNS;
				case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__OPTIONAL :
					return JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL;
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
		result.append(" (fetch: ");
		result.append(fetch);
		result.append(", optional: ");
		result.append(optional);
		result.append(')');
		return result.toString();
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.updateFetchFromJava(astRoot);
		this.updateSpecifiedJoinColumnsFromJava(astRoot);
		this.setOptional(DefaultTrueBoolean.fromJavaAnnotationValue(this.optionalAdapter.getValue(astRoot)));
	}

	/**
	 * here we just worry about getting the join column lists the same size;
	 * then we delegate to the join columns to synch themselves up
	 */
	private void updateSpecifiedJoinColumnsFromJava(CompilationUnit astRoot) {
		// synchronize the model join columns with the Java source
		List<IJoinColumn> joinColumns = getSpecifiedJoinColumns();
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
					getSpecifiedJoinColumns().add(joinColumn);
					joinColumn.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	@Override
	protected void updateFetchFromJava(CompilationUnit astRoot) {
		setFetch(DefaultEagerFetchType.fromJavaAnnotationValue(this.getFetchAdapter().getValue(astRoot)));
	}

	/**
	 * extend to eliminate any "container" types
	 */
	@Override
	protected String javaDefaultTargetEntity(ITypeBinding typeBinding) {
		String typeName = super.javaDefaultTargetEntity(typeBinding);
		// if the attribute is a container, don't use it
		return typeNamedIsContainer(typeName) ? null : typeName;
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.getSpecifiedJoinColumns().isEmpty();
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
		return null;
	}

	public IJoinColumn createJoinColumn(int index) {
		return this.createJavaJoinColumn(index);
	}

	private JavaJoinColumn createJavaJoinColumn(int index) {
		return JavaJoinColumn.createSingleRelationshipMappingJoinColumn(new JoinColumnOwner(this), this.getAttribute(), index);
	}

	// ********** static methods **********
	protected static DeclarationAnnotationElementAdapter<String> buildOptionalAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, false, BooleanStringExpressionConverter.instance());
	}
} // JavaSingleRelationshipMapping
