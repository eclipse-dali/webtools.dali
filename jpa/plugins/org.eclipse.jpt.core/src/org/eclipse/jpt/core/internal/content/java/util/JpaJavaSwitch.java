/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.util;

import java.util.List;
import org.eclipse.emf.ecore.EClass;
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
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit;
import org.eclipse.jpt.core.internal.content.java.JpaJavaPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage
 * @generated
 */
public class JpaJavaSwitch<T>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static JpaJavaPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaJavaSwitch() {
		if (modelPackage == null) {
			modelPackage = JpaJavaPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case JpaJavaPackage.JAVA_EOBJECT : {
				JavaEObject javaEObject = (JavaEObject) theEObject;
				T result = caseJavaEObject(javaEObject);
				if (result == null)
					result = caseJpaEObject(javaEObject);
				if (result == null)
					result = caseIJpaSourceObject(javaEObject);
				if (result == null)
					result = caseIJpaEObject(javaEObject);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaPackage.JPA_COMPILATION_UNIT : {
				JpaCompilationUnit jpaCompilationUnit = (JpaCompilationUnit) theEObject;
				T result = caseJpaCompilationUnit(jpaCompilationUnit);
				if (result == null)
					result = caseJavaEObject(jpaCompilationUnit);
				if (result == null)
					result = caseIJpaRootContentNode(jpaCompilationUnit);
				if (result == null)
					result = caseJpaEObject(jpaCompilationUnit);
				if (result == null)
					result = caseIJpaSourceObject(jpaCompilationUnit);
				if (result == null)
					result = caseIJpaContentNode(jpaCompilationUnit);
				if (result == null)
					result = caseIJpaEObject(jpaCompilationUnit);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE : {
				JavaPersistentType javaPersistentType = (JavaPersistentType) theEObject;
				T result = caseJavaPersistentType(javaPersistentType);
				if (result == null)
					result = caseJavaEObject(javaPersistentType);
				if (result == null)
					result = caseIPersistentType(javaPersistentType);
				if (result == null)
					result = caseJpaEObject(javaPersistentType);
				if (result == null)
					result = caseIJpaSourceObject(javaPersistentType);
				if (result == null)
					result = caseIJpaContentNode(javaPersistentType);
				if (result == null)
					result = caseIJpaEObject(javaPersistentType);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE : {
				JavaPersistentAttribute javaPersistentAttribute = (JavaPersistentAttribute) theEObject;
				T result = caseJavaPersistentAttribute(javaPersistentAttribute);
				if (result == null)
					result = caseJavaEObject(javaPersistentAttribute);
				if (result == null)
					result = caseIPersistentAttribute(javaPersistentAttribute);
				if (result == null)
					result = caseJpaEObject(javaPersistentAttribute);
				if (result == null)
					result = caseIJpaSourceObject(javaPersistentAttribute);
				if (result == null)
					result = caseIJpaContentNode(javaPersistentAttribute);
				if (result == null)
					result = caseIJpaEObject(javaPersistentAttribute);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaPackage.IJAVA_TYPE_MAPPING : {
				IJavaTypeMapping iJavaTypeMapping = (IJavaTypeMapping) theEObject;
				T result = caseIJavaTypeMapping(iJavaTypeMapping);
				if (result == null)
					result = caseITypeMapping(iJavaTypeMapping);
				if (result == null)
					result = caseIJpaSourceObject(iJavaTypeMapping);
				if (result == null)
					result = caseIJpaEObject(iJavaTypeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaPackage.IJAVA_ATTRIBUTE_MAPPING : {
				IJavaAttributeMapping iJavaAttributeMapping = (IJavaAttributeMapping) theEObject;
				T result = caseIJavaAttributeMapping(iJavaAttributeMapping);
				if (result == null)
					result = caseIAttributeMapping(iJavaAttributeMapping);
				if (result == null)
					result = caseIJpaSourceObject(iJavaAttributeMapping);
				if (result == null)
					result = caseIJpaEObject(iJavaAttributeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default :
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJava Attribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJava Attribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJavaAttributeMapping(IJavaAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJava Type Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJava Type Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJavaTypeMapping(IJavaTypeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Java EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Java EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaEObject(JavaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Jpa Compilation Unit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Jpa Compilation Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJpaCompilationUnit(JpaCompilationUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Java Persistent Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Java Persistent Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaPersistentType(JavaPersistentType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Java Persistent Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Java Persistent Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaPersistentAttribute(JavaPersistentAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaEObject(IJpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJpa Source Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJpa Source Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaSourceObject(IJpaSourceObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJpa Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJpa Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaContentNode(IJpaContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IJpa Root Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IJpa Root Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaRootContentNode(IJpaRootContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IPersistent Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IPersistent Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPersistentType(IPersistentType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IPersistent Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IPersistent Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPersistentAttribute(IPersistentAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IAttribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IAttribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAttributeMapping(IAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IType Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IType Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITypeMapping(ITypeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Jpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Jpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJpaEObject(JpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}
} //JpaCoreJavaSwitch
