/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.JpaFile;
import org.eclipse.jpt.core.internal.jdtutility.ASTNodeTextRange;
import org.eclipse.jpt.core.internal.jdtutility.AttributeAnnotationTools;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java File Content</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJpaCompilationUnit()
 * @model kind="class"
 * @generated
 */
public class JpaCompilationUnit extends JavaEObject
	implements IJpaRootContentNode
{
	//can this just have one JavaType, or does it need to be multiple. 
	//only 1 primary type that can be annotated according to the spec? - kfm
	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<JavaPersistentType> types;

	private ICompilationUnit compilationUnit;

	protected JpaCompilationUnit() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaPackage.Literals.JPA_COMPILATION_UNIT;
	}

	/**
	 * Returns the value of the '<em><b>Jpa File</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.jpt.core.internal.JpaFile#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jpa File</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jpa File</em>' container reference.
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getIJpaRootContentNode_JpaFile()
	 * @see org.eclipse.jpt.core.internal.JpaFile#getContent
	 * @model opposite="content" transient="false" changeable="false"
	 * @generated
	 */
	@Override
	public IJpaFile getJpaFile() {
		if (eContainerFeatureID != JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE)
			return null;
		return (IJpaFile) eContainer();
	}

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage#getJpaCompilationUnit_Types()
	 * @model containment="true"
	 * @generated
	 */
	public EList<JavaPersistentType> getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList<JavaPersistentType>(JavaPersistentType.class, this, JpaJavaPackage.JPA_COMPILATION_UNIT__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE :
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE :
				return eBasicSetContainer(null, JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE, msgs);
			case JpaJavaPackage.JPA_COMPILATION_UNIT__TYPES :
				return ((InternalEList<?>) getTypes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE :
				return eInternalContainer().eInverseRemove(this, JpaCorePackage.JPA_FILE__CONTENT, JpaFile.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE :
				return getJpaFile();
			case JpaJavaPackage.JPA_COMPILATION_UNIT__TYPES :
				return getTypes();
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
			case JpaJavaPackage.JPA_COMPILATION_UNIT__TYPES :
				getTypes().clear();
				getTypes().addAll((Collection<? extends JavaPersistentType>) newValue);
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
			case JpaJavaPackage.JPA_COMPILATION_UNIT__TYPES :
				getTypes().clear();
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
			case JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE :
				return getJpaFile() != null;
			case JpaJavaPackage.JPA_COMPILATION_UNIT__TYPES :
				return types != null && !types.isEmpty();
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
		if (baseClass == IJpaContentNode.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IJpaRootContentNode.class) {
			switch (derivedFeatureID) {
				case JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE :
					return JpaCorePackage.IJPA_ROOT_CONTENT_NODE__JPA_FILE;
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
		if (baseClass == IJpaContentNode.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IJpaRootContentNode.class) {
			switch (baseFeatureID) {
				case JpaCorePackage.IJPA_ROOT_CONTENT_NODE__JPA_FILE :
					return JpaJavaPackage.JPA_COMPILATION_UNIT__JPA_FILE;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public ITextRange fullTextRange() {
		return new ASTNodeTextRange(this.astRoot());
	}

	public ITextRange validationTextRange() {
		return this.selectionTextRange();
	}

	/**
	 * Return null for selection textRange.  Entire java file will appear selected when
	 * switching files otherwise
	 */
	public ITextRange selectionTextRange() {
		return null;
	}

	public Object getId() {
		return IJavaContentNodes.COMPILATION_UNIT_ID;
	}

	@Override
	public IJpaRootContentNode getRoot() {
		return this;
	}

	public void setFile(IFile file) {
		this.compilationUnit = JavaCore.createCompilationUnitFrom(file);
		try {
			this.compilationUnit.open(null);
		}
		catch (JavaModelException jme) {
			// do nothing - we just won't have a primary type in this case
		}
		this.synchronizePersistentTypes();
	}

	public JavaPersistentType addJavaPersistentType(IType primaryType, CompilationUnit astRoot) {
		return this.addJavaPersistentType(this.createJavaPersistentType(), primaryType, astRoot);
	}

	public JavaPersistentType createJavaPersistentType() {
		return JpaJavaFactory.eINSTANCE.createJavaPersistentType();
	}

	private JavaPersistentType addJavaPersistentType(JavaPersistentType javaPersistentType, IType primaryType, CompilationUnit astRoot) {
		this.getTypes().add(javaPersistentType);
		javaPersistentType.setJdtType(primaryType, astRoot);
		return javaPersistentType;
	}

	public IJpaContentNode getContentNode(int offset) {
		for (JavaPersistentType javaType : this.getTypes()) {
			if (javaType.includes(offset)) {
				IJpaContentNode contentNode = javaType.contentNodeAt(offset);
				if (contentNode != null) {
					return contentNode;
				}
				return javaType;
			}
		}
		return this;
	}

	public void handleJavaElementChangedEvent(ElementChangedEvent event) {
		synchWithJavaDelta(event.getDelta());
	}

	private void synchWithJavaDelta(IJavaElementDelta delta) {
		switch (delta.getElement().getElementType()) {
			case IJavaElement.JAVA_MODEL :
			case IJavaElement.JAVA_PROJECT :
			case IJavaElement.PACKAGE_FRAGMENT_ROOT :
			case IJavaElement.PACKAGE_FRAGMENT :
				this.synchChildrenWithJavaDelta(delta);
				break;
			case IJavaElement.COMPILATION_UNIT :
				this.synchCompilationUnitWithJavaDelta(delta);
				break;
			default :
				break; // the event is somehow lower than a compilation unit
		}
	}

	private void synchChildrenWithJavaDelta(IJavaElementDelta delta) {
		for (IJavaElementDelta child : delta.getAffectedChildren()) {
			this.synchWithJavaDelta(child); // recurse
		}
	}

	private void synchCompilationUnitWithJavaDelta(IJavaElementDelta delta) {
		// ignore changes to/from primary working copy - no content has changed
		// this checks that no flags other than F_PRIMARY_WORKING_COPY are set
		if ((delta.getFlags() & ~IJavaElementDelta.F_PRIMARY_WORKING_COPY) == 0) {
			return;
		}
		// synchronize if the change is for this compilation unit
		if (delta.getElement().equals(this.compilationUnit)) {
			this.synchronizePersistentTypes();
		}
	}

	private void synchronizePersistentTypes() {
		CompilationUnit astRoot = this.astRoot();
		List<JavaPersistentType> persistentTypesToRemove = new ArrayList<JavaPersistentType>(this.getTypes());
		IType[] iTypes = this.compilationUnitTypes();
		for (IType iType : iTypes) {
			JavaPersistentType persistentType = this.persistentTypeFor(iType);
			if (persistentType == null) {
				if (AttributeAnnotationTools.typeIsPersistable(iType)) {
					persistentType = this.addJavaPersistentType(iType, astRoot);
				}
			}
			if (persistentType != null) {
				persistentTypesToRemove.remove(persistentType);
				if (AttributeAnnotationTools.typeIsPersistable(iType)) {
					persistentType.updateFromJava(astRoot);
				}
				else {
					this.getTypes().remove(persistentType);
				}
			}
		}
		this.getTypes().removeAll(persistentTypesToRemove);
	}

	private JavaPersistentType persistentTypeFor(IType iType) {
		for (JavaPersistentType persistentType : this.getTypes()) {
			if (persistentType.isFor(iType)) {
				return persistentType;
			}
		}
		return null;
	}

	private IType[] compilationUnitTypes() {
		try {
			return this.compilationUnit.getTypes();
		}
		catch (JavaModelException e) {
			//TODO not throwing an exception because of tests, should I be?
			//throw new RuntimeException(e);
			return new IType[0];
		}
	}

	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter) {
		CompilationUnit astRoot = this.astRoot();
		for (JavaPersistentType persistentType : this.getTypes()) {
			Iterator<String> values = persistentType.candidateValuesFor(pos, filter, astRoot);
			if (values != null) {
				return values;
			}
		}
		return EmptyIterator.instance();
	}

	public CompilationUnit astRoot() {
		return JDTTools.createASTRoot(this.compilationUnit);
	}

	public void dispose() {
	// TODO
	}
}
