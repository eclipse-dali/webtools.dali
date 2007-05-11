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
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
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
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.mappings.IQuery;
import org.eclipse.jpt.core.internal.mappings.IQueryHint;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Abstract Query</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaAbstractQuery()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class JavaAbstractQuery extends JavaEObject implements IQuery
{
	private final Member member;

	// hold this so we can get the annotation's text range
	private final IndexedDeclarationAnnotationAdapter idaa;

	// hold this so we can get the 'name' text range
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;

	// hold this so we can get the 'query' text range
	private final DeclarationAnnotationElementAdapter<String> queryDeclarationAdapter;

	private final IndexedAnnotationAdapter annotationAdapter;

	private final AnnotationElementAdapter<String> nameAdapter;

	private final AnnotationElementAdapter<String> queryAdapter;

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
	 * The default value of the '{@link #getQuery() <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected static final String QUERY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQuery() <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected String query = QUERY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getHints() <em>Hints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHints()
	 * @generated
	 * @ordered
	 */
	protected EList<IQueryHint> hints;

	protected JavaAbstractQuery() {
		throw new UnsupportedOperationException("Use JavaAbstractQuery(Member) instead");
	}

	protected JavaAbstractQuery(Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super();
		this.member = member;
		this.idaa = idaa;
		this.annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		this.nameDeclarationAdapter = nameAdapter(this.idaa);
		this.queryDeclarationAdapter = queryAdapter(this.idaa);
		this.nameAdapter = this.buildAdapter(this.nameDeclarationAdapter);
		this.queryAdapter = this.buildAdapter(this.queryDeclarationAdapter);
	}

	// ********** initialization **********
	protected AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new ShortCircuitAnnotationElementAdapter<String>(this.member, daea);
	}

	protected DeclarationAnnotationElementAdapter<String> nameAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, nameElementName());
	}

	protected DeclarationAnnotationElementAdapter<String> queryAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, queryElementName());
	}

	protected abstract String nameElementName();

	protected abstract String queryElementName();

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IQuery.class)) {
			case JpaCoreMappingsPackage.IQUERY__NAME :
				this.nameAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaCoreMappingsPackage.IQUERY__QUERY :
				this.queryAdapter.setValue((String) notification.getNewValue());
				break;
			case JpaCoreMappingsPackage.IQUERY__HINTS :
				hintsChanged(notification);
				break;
			default :
				break;
		}
	}

	@SuppressWarnings("unchecked")
	void hintsChanged(Notification notification) {
		switch (notification.getEventType()) {
			case Notification.ADD :
				hintAdded(notification.getPosition(), (IQueryHint) notification.getNewValue());
				break;
			case Notification.ADD_MANY :
				hintsAdded(notification.getPosition(), (List<IQueryHint>) notification.getNewValue());
				break;
			case Notification.REMOVE :
				hintRemoved(notification.getPosition(), (IQueryHint) notification.getOldValue());
				break;
			case Notification.REMOVE_MANY :
				if (notification.getPosition() == Notification.NO_INDEX) {
					hintsCleared((List<IQueryHint>) notification.getOldValue());
				}
				else {
					// Notification.getNewValue() returns an array of the positions of objects that were removed
					hintsRemoved((int[]) notification.getNewValue(), (List<IQueryHint>) notification.getOldValue());
				}
				break;
			case Notification.SET :
				if (!notification.isTouch()) {
					hintSet(notification.getPosition(), (IQueryHint) notification.getOldValue(), (IQueryHint) notification.getNewValue());
				}
				break;
			case Notification.MOVE :
				// Notification.getOldValue() returns the source index
				// Notification.getPositon() returns the target index
				// Notification.getNewValue() returns the moved object
				hintMoved(notification.getOldIntValue(), notification.getPosition(), (IQueryHint) notification.getNewValue());
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
		return JpaJavaMappingsPackage.Literals.JAVA_ABSTRACT_QUERY;
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIQuery_Name()
	 * @model
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query</em>' attribute.
	 * @see #setQuery(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIQuery_Query()
	 * @model
	 * @generated
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery#getQuery <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query</em>' attribute.
	 * @see #getQuery()
	 * @generated
	 */
	public void setQuery(String newQuery) {
		String oldQuery = query;
		query = newQuery;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__QUERY, oldQuery, query));
	}

	/**
	 * Returns the value of the '<em><b>Hints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.mappings.IQueryHint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hints</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIQuery_Hints()
	 * @model type="org.eclipse.jpt.core.internal.mappings.IQueryHint" containment="true"
	 * @generated
	 */
	public EList<IQueryHint> getHints() {
		if (hints == null) {
			hints = new EObjectContainmentEList<IQueryHint>(IQueryHint.class, this, JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__HINTS);
		}
		return hints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__HINTS :
				return ((InternalEList<?>) getHints()).basicRemove(otherEnd, msgs);
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
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__NAME :
				return getName();
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__QUERY :
				return getQuery();
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__HINTS :
				return getHints();
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
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__NAME :
				setName((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__QUERY :
				setQuery((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__HINTS :
				getHints().clear();
				getHints().addAll((Collection<? extends IQueryHint>) newValue);
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
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__NAME :
				setName(NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__QUERY :
				setQuery(QUERY_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__HINTS :
				getHints().clear();
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
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__NAME :
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__QUERY :
				return QUERY_EDEFAULT == null ? query != null : !QUERY_EDEFAULT.equals(query);
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__HINTS :
				return hints != null && !hints.isEmpty();
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
		if (baseClass == IQuery.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__NAME :
					return JpaCoreMappingsPackage.IQUERY__NAME;
				case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__QUERY :
					return JpaCoreMappingsPackage.IQUERY__QUERY;
				case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__HINTS :
					return JpaCoreMappingsPackage.IQUERY__HINTS;
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
		if (baseClass == IQuery.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IQUERY__NAME :
					return JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__NAME;
				case JpaCoreMappingsPackage.IQUERY__QUERY :
					return JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__QUERY;
				case JpaCoreMappingsPackage.IQUERY__HINTS :
					return JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY__HINTS;
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
		result.append(", query: ");
		result.append(query);
		result.append(')');
		return result.toString();
	}

	protected Member getMember() {
		return this.member;
	}

	// ********** jpa model -> java annotations **********
	/**
	 * slide over all the annotations that follow the new join column
	 */
	public void hintAdded(int index, IQueryHint hint) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (((JavaQueryHint) hint).annotation(getMember().astRoot()) == null) {
			this.synchHintAnnotationsAfterAdd(index + 1);
			((JavaQueryHint) hint).newAnnotation();
		}
	}

	// bjv look at this
	public void hintsAdded(int index, List<IQueryHint> queryHints) {
		//JoinColumn was added to persistence model when udating from java, do not need
		//to edit the java in this case. TODO is there a better way to handle this??
		if (!queryHints.isEmpty() && ((JavaQueryHint) queryHints.get(0)).annotation(getMember().astRoot()) == null) {
			this.synchHintAnnotationsAfterAdd(index + queryHints.size());
			for (IQueryHint hint : queryHints) {
				((JavaQueryHint) hint).newAnnotation();
			}
		}
	}

	public void hintRemoved(int index, IQueryHint hint) {
		((JavaQueryHint) hint).removeAnnotation();
		this.synchHintAnnotationsAfterRemove(index);
	}

	public void hintsRemoved(int[] indexes, List<IQueryHint> queryHints) {
		for (IQueryHint hint : queryHints) {
			((JavaQueryHint) hint).removeAnnotation();
		}
		this.synchHintAnnotationsAfterRemove(indexes[0]);
	}

	public void hintsCleared(List<IQueryHint> queryHints) {
		for (IQueryHint hint : queryHints) {
			((JavaQueryHint) hint).removeAnnotation();
		}
	}

	public void hintSet(int index, IQueryHint oldHint, IQueryHint newHint) {
		((JavaQueryHint) newHint).newAnnotation();
	}

	public void hintMoved(int sourceIndex, int targetIndex, IQueryHint hint) {
		List<IQueryHint> queryHints = this.getHints();
		int begin = Math.min(sourceIndex, targetIndex);
		int end = Math.max(sourceIndex, targetIndex);
		for (int i = begin; i-- > end;) {
			this.synch(queryHints.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the end of the list to prevent overlap
	 */
	private void synchHintAnnotationsAfterAdd(int index) {
		List<IQueryHint> queryHints = this.getHints();
		for (int i = queryHints.size(); i-- > index;) {
			this.synch(queryHints.get(i), i);
		}
	}

	/**
	 * synchronize the annotations with the model join columns,
	 * starting at the specified index to prevent overlap
	 */
	private void synchHintAnnotationsAfterRemove(int index) {
		List<IQueryHint> queryHints = this.getHints();
		for (int i = index; i < queryHints.size(); i++) {
			this.synch(queryHints.get(i), i);
		}
	}

	private void synch(IQueryHint queryHint, int index) {
		((JavaQueryHint) queryHint).moveAnnotation(index);
	}

	/**
	 * allow owners to verify the annotation
	 */
	public Annotation annotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	protected void updateFromJava(CompilationUnit astRoot) {
		this.setName(this.nameAdapter.getValue(astRoot));
		this.setQuery(this.queryAdapter.getValue(astRoot));
		this.updateQueryHintsFromJava(astRoot);
	}

	/**
	 * here we just worry about getting the named query lists the same size;
	 * then we delegate to the named queries to synch themselves up
	 */
	private void updateQueryHintsFromJava(CompilationUnit astRoot) {
		// synchronize the model named queries with the Java source
		List<IQueryHint> queryHints = this.getHints();
		int persSize = queryHints.size();
		int javaSize = 0;
		boolean allJavaAnnotationsFound = false;
		for (int i = 0; i < persSize; i++) {
			JavaQueryHint hint = (JavaQueryHint) queryHints.get(i);
			if (hint.annotation(astRoot) == null) {
				allJavaAnnotationsFound = true;
				break; // no need to go any further
			}
			hint.updateFromJava(astRoot);
			javaSize++;
		}
		if (allJavaAnnotationsFound) {
			// remove any model named queries beyond those that correspond to the Java annotations
			while (persSize > javaSize) {
				persSize--;
				queryHints.remove(persSize);
			}
		}
		else {
			// add new model join columns until they match the Java annotations
			while (!allJavaAnnotationsFound) {
				JavaQueryHint javaQueryHint = createJavaQueryHint(javaSize);
				if (javaQueryHint.annotation(astRoot) == null) {
					allJavaAnnotationsFound = true;
				}
				else {
					getHints().add(javaQueryHint);
					javaQueryHint.updateFromJava(astRoot);
					javaSize++;
				}
			}
		}
	}

	public JavaQueryHint createQueryHint(int index) {
		return createJavaQueryHint(index);
	}

	protected abstract JavaQueryHint createJavaQueryHint(int index);

	protected IndexedDeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return this.idaa;
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

	// ********* IJpaSourceObject implementation ***********
	public ITextRange validationTextRange() {
		return this.member.annotationTextRange(this.idaa);
	}

	// ********** static methods **********
	protected static DeclarationAnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(annotationAdapter, elementName);
	}
} // JavaAbstractQuery
