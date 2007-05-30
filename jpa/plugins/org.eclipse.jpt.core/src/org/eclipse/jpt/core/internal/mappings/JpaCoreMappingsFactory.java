/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage
 * @generated
 */
public class JpaCoreMappingsFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final JpaCoreMappingsFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JpaCoreMappingsFactory init() {
		try {
			JpaCoreMappingsFactory theJpaCoreMappingsFactory = (JpaCoreMappingsFactory) EPackage.Registry.INSTANCE.getEFactory("jpt.core.mappings.xmi");
			if (theJpaCoreMappingsFactory != null) {
				return theJpaCoreMappingsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new JpaCoreMappingsFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaCoreMappingsFactory() {
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
			default :
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case JpaCoreMappingsPackage.DEFAULT_EAGER_FETCH_TYPE :
				return createDefaultEagerFetchTypeFromString(eDataType, initialValue);
			case JpaCoreMappingsPackage.DEFAULT_LAZY_FETCH_TYPE :
				return createDefaultLazyFetchTypeFromString(eDataType, initialValue);
			case JpaCoreMappingsPackage.DEFAULT_FALSE_BOOLEAN :
				return createDefaultFalseBooleanFromString(eDataType, initialValue);
			case JpaCoreMappingsPackage.DEFAULT_TRUE_BOOLEAN :
				return createDefaultTrueBooleanFromString(eDataType, initialValue);
			case JpaCoreMappingsPackage.TEMPORAL_TYPE :
				return createTemporalTypeFromString(eDataType, initialValue);
			case JpaCoreMappingsPackage.INHERITANCE_TYPE :
				return createInheritanceTypeFromString(eDataType, initialValue);
			case JpaCoreMappingsPackage.DISCRIMINATOR_TYPE :
				return createDiscriminatorTypeFromString(eDataType, initialValue);
			case JpaCoreMappingsPackage.GENERATION_TYPE :
				return createGenerationTypeFromString(eDataType, initialValue);
			case JpaCoreMappingsPackage.ENUM_TYPE :
				return createEnumTypeFromString(eDataType, initialValue);
			case JpaCoreMappingsPackage.CASCADE_TYPE :
				return createCascadeTypeFromString(eDataType, initialValue);
			default :
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case JpaCoreMappingsPackage.DEFAULT_EAGER_FETCH_TYPE :
				return convertDefaultEagerFetchTypeToString(eDataType, instanceValue);
			case JpaCoreMappingsPackage.DEFAULT_LAZY_FETCH_TYPE :
				return convertDefaultLazyFetchTypeToString(eDataType, instanceValue);
			case JpaCoreMappingsPackage.DEFAULT_FALSE_BOOLEAN :
				return convertDefaultFalseBooleanToString(eDataType, instanceValue);
			case JpaCoreMappingsPackage.DEFAULT_TRUE_BOOLEAN :
				return convertDefaultTrueBooleanToString(eDataType, instanceValue);
			case JpaCoreMappingsPackage.TEMPORAL_TYPE :
				return convertTemporalTypeToString(eDataType, instanceValue);
			case JpaCoreMappingsPackage.INHERITANCE_TYPE :
				return convertInheritanceTypeToString(eDataType, instanceValue);
			case JpaCoreMappingsPackage.DISCRIMINATOR_TYPE :
				return convertDiscriminatorTypeToString(eDataType, instanceValue);
			case JpaCoreMappingsPackage.GENERATION_TYPE :
				return convertGenerationTypeToString(eDataType, instanceValue);
			case JpaCoreMappingsPackage.ENUM_TYPE :
				return convertEnumTypeToString(eDataType, instanceValue);
			case JpaCoreMappingsPackage.CASCADE_TYPE :
				return convertCascadeTypeToString(eDataType, instanceValue);
			default :
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefaultEagerFetchType createDefaultEagerFetchTypeFromString(EDataType eDataType, String initialValue) {
		DefaultEagerFetchType result = DefaultEagerFetchType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDefaultEagerFetchTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefaultLazyFetchType createDefaultLazyFetchTypeFromString(EDataType eDataType, String initialValue) {
		DefaultLazyFetchType result = DefaultLazyFetchType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDefaultLazyFetchTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefaultFalseBoolean createDefaultFalseBooleanFromString(EDataType eDataType, String initialValue) {
		DefaultFalseBoolean result = DefaultFalseBoolean.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDefaultFalseBooleanToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefaultTrueBoolean createDefaultTrueBooleanFromString(EDataType eDataType, String initialValue) {
		DefaultTrueBoolean result = DefaultTrueBoolean.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDefaultTrueBooleanToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemporalType createTemporalTypeFromString(EDataType eDataType, String initialValue) {
		TemporalType result = TemporalType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTemporalTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceType createInheritanceTypeFromString(EDataType eDataType, String initialValue) {
		InheritanceType result = InheritanceType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInheritanceTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiscriminatorType createDiscriminatorTypeFromString(EDataType eDataType, String initialValue) {
		DiscriminatorType result = DiscriminatorType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDiscriminatorTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenerationType createGenerationTypeFromString(EDataType eDataType, String initialValue) {
		GenerationType result = GenerationType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGenerationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumType createEnumTypeFromString(EDataType eDataType, String initialValue) {
		EnumType result = EnumType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnumTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CascadeType createCascadeTypeFromString(EDataType eDataType, String initialValue) {
		CascadeType result = CascadeType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCascadeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaCoreMappingsPackage getJpaCoreMappingsPackage() {
		return (JpaCoreMappingsPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static JpaCoreMappingsPackage getPackage() {
		return JpaCoreMappingsPackage.eINSTANCE;
	}
} //MappingsFactoryImpl
