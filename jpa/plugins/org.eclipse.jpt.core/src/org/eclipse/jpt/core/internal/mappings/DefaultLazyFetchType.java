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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Default Lazy Fetch Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDefaultLazyFetchType()
 * @model
 * @generated
 */
public enum DefaultLazyFetchType implements Enumerator {
	/**
	 * The '<em><b>Default</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFAULT_VALUE
	 * @generated
	 * @ordered
	 */
	DEFAULT(0, "Default", "Default (Lazy)"), /**
	 * The '<em><b>LAZY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LAZY_VALUE
	 * @generated
	 * @ordered
	 */
	LAZY(1, "LAZY", "Lazy"), /**
	 * The '<em><b>EAGER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EAGER_VALUE
	 * @generated
	 * @ordered
	 */
	EAGER(2, "EAGER", "Eager");
	/**
	 * The '<em><b>Default</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Default</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEFAULT
	 * @model name="Default" literal="Default (Lazy)"
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT_VALUE = 0;

	/**
	 * The '<em><b>LAZY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LAZY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LAZY
	 * @model literal="Lazy"
	 * @generated
	 * @ordered
	 */
	public static final int LAZY_VALUE = 1;

	/**
	 * The '<em><b>EAGER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EAGER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EAGER
	 * @model literal="Eager"
	 * @generated
	 * @ordered
	 */
	public static final int EAGER_VALUE = 2;

	/**
	 * An array of all the '<em><b>Default Lazy Fetch Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DefaultLazyFetchType[] VALUES_ARRAY = new DefaultLazyFetchType[] {
		DEFAULT, LAZY, EAGER,
	};

	/**
	 * A public read-only list of all the '<em><b>Default Lazy Fetch Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DefaultLazyFetchType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Default Lazy Fetch Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DefaultLazyFetchType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DefaultLazyFetchType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Default Lazy Fetch Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DefaultLazyFetchType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DefaultLazyFetchType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Default Lazy Fetch Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DefaultLazyFetchType get(int value) {
		switch (value) {
			case DEFAULT_VALUE :
				return DEFAULT;
			case LAZY_VALUE :
				return LAZY;
			case EAGER_VALUE :
				return EAGER;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DefaultLazyFetchType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

	public static DefaultLazyFetchType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return DEFAULT;
		}
		if (javaAnnotationValue.equals(JPA.FETCH_TYPE__EAGER)) {
			return EAGER;
		}
		if (javaAnnotationValue.equals(JPA.FETCH_TYPE__LAZY)) {
			return LAZY;
		}
		return DEFAULT;
	}

	public String convertToJavaAnnotationValue() {
		switch (this.getValue()) {
			case DEFAULT_VALUE :
				return null;
			case EAGER_VALUE :
				return JPA.FETCH_TYPE__EAGER;
			case LAZY_VALUE :
				return JPA.FETCH_TYPE__LAZY;
			default :
				throw new IllegalArgumentException("unknown fetch type: " + this);
		}
	}
} //DefaultLazyFetchType
