/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.java.JpaJavaPackage
 * @generated
 */
public class JpaJavaFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final JpaJavaFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JpaJavaFactory init() {
		try {
			JpaJavaFactory theJpaJavaFactory = (JpaJavaFactory) EPackage.Registry.INSTANCE.getEFactory("jpt.java.xmi");
			if (theJpaJavaFactory != null) {
				return theJpaJavaFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new JpaJavaFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaJavaFactory() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case JpaJavaPackage.JPA_COMPILATION_UNIT :
				return createJpaCompilationUnit();
			case JpaJavaPackage.JAVA_PERSISTENT_TYPE :
				return createJavaPersistentType();
			case JpaJavaPackage.JAVA_PERSISTENT_ATTRIBUTE :
				return createJavaPersistentAttribute();
			default :
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaCompilationUnit createJpaCompilationUnit() {
		JpaCompilationUnit jpaCompilationUnit = new JpaCompilationUnit();
		return jpaCompilationUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaPersistentType createJavaPersistentType() {
		JavaPersistentType javaPersistentType = new JavaPersistentType();
		return javaPersistentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaPersistentAttribute createJavaPersistentAttribute() {
		JavaPersistentAttribute javaPersistentAttribute = new JavaPersistentAttribute();
		return javaPersistentAttribute;
	}

	public JavaPersistentAttribute createJavaPersistentAttribute(Attribute attribute) {
		JavaPersistentAttribute javaPersistentAttribute = new JavaPersistentAttribute(attribute);
		return javaPersistentAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaJavaPackage getJpaJavaPackage() {
		return (JpaJavaPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static JpaJavaPackage getPackage() {
		return JpaJavaPackage.eINSTANCE;
	}
} //JpaCoreJavaFactory
