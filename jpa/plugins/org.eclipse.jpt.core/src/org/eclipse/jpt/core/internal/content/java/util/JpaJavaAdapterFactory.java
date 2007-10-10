/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaEObject;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.IJpaSourceObject;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.JpaEObject;
import org.eclipse.jpt.core.internal.content.java.*;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit;
import org.eclipse.jpt.core.internal.content.java.JpaJavaPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage
 * @generated
 */
public class JpaJavaAdapterFactory extends AdapterFactoryImpl
{
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static JpaJavaPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaJavaAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = JpaJavaPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JpaJavaSwitch<Adapter> modelSwitch = new JpaJavaSwitch<Adapter>() {
		@Override
		public Adapter caseJavaEObject(JavaEObject object) {
			return createJavaEObjectAdapter();
		}

		@Override
		public Adapter caseJpaCompilationUnit(JpaCompilationUnit object) {
			return createJpaCompilationUnitAdapter();
		}

		@Override
		public Adapter caseJavaPersistentType(JavaPersistentType object) {
			return createJavaPersistentTypeAdapter();
		}

		@Override
		public Adapter caseJavaPersistentAttribute(JavaPersistentAttribute object) {
			return createJavaPersistentAttributeAdapter();
		}

		@Override
		public Adapter caseIJavaTypeMapping(IJavaTypeMapping object) {
			return createIJavaTypeMappingAdapter();
		}

		@Override
		public Adapter caseIJavaAttributeMapping(IJavaAttributeMapping object) {
			return createIJavaAttributeMappingAdapter();
		}

		@Override
		public Adapter caseIJpaEObject(IJpaEObject object) {
			return createIJpaEObjectAdapter();
		}

		@Override
		public Adapter caseJpaEObject(JpaEObject object) {
			return createJpaEObjectAdapter();
		}

		@Override
		public Adapter caseIJpaSourceObject(IJpaSourceObject object) {
			return createIJpaSourceObjectAdapter();
		}

		@Override
		public Adapter caseIJpaContentNode(IJpaContentNode object) {
			return createIJpaContentNodeAdapter();
		}

		@Override
		public Adapter caseIJpaRootContentNode(IJpaRootContentNode object) {
			return createIJpaRootContentNodeAdapter();
		}

		@Override
		public Adapter caseIPersistentType(IPersistentType object) {
			return createIPersistentTypeAdapter();
		}

		@Override
		public Adapter caseIPersistentAttribute(IPersistentAttribute object) {
			return createIPersistentAttributeAdapter();
		}

		@Override
		public Adapter caseITypeMapping(ITypeMapping object) {
			return createITypeMappingAdapter();
		}

		@Override
		public Adapter caseIAttributeMapping(IAttributeMapping object) {
			return createIAttributeMappingAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping <em>IJava Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping
	 * @generated
	 */
	public Adapter createIJavaAttributeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping <em>IJava Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping
	 * @generated
	 */
	public Adapter createIJavaTypeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.JavaEObject <em>Java EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaEObject
	 * @generated
	 */
	public Adapter createJavaEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit <em>Jpa Compilation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit
	 * @generated
	 */
	public Adapter createJpaCompilationUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentType <em>Java Persistent Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentType
	 * @generated
	 */
	public Adapter createJavaPersistentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute <em>Java Persistent Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute
	 * @generated
	 */
	public Adapter createJavaPersistentAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaEObject <em>IJpa EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaEObject
	 * @generated
	 */
	public Adapter createIJpaEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaSourceObject <em>IJpa Source Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaSourceObject
	 * @generated
	 */
	public Adapter createIJpaSourceObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaContentNode <em>IJpa Content Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaContentNode
	 * @generated
	 */
	public Adapter createIJpaContentNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaRootContentNode <em>IJpa Root Content Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaRootContentNode
	 * @generated
	 */
	public Adapter createIJpaRootContentNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IPersistentType <em>IPersistent Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IPersistentType
	 * @generated
	 */
	public Adapter createIPersistentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IPersistentAttribute <em>IPersistent Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IPersistentAttribute
	 * @generated
	 */
	public Adapter createIPersistentAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IAttributeMapping <em>IAttribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IAttributeMapping
	 * @generated
	 */
	public Adapter createIAttributeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.ITypeMapping <em>IType Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.ITypeMapping
	 * @generated
	 */
	public Adapter createITypeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.JpaEObject <em>Jpa EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.JpaEObject
	 * @generated
	 */
	public Adapter createJpaEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}
} //JpaCoreJavaAdapterFactory
