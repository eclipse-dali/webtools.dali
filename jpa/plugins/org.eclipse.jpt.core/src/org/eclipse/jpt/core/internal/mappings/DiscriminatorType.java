/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
 * A representation of the literals of the enumeration '<em><b>Discriminator Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getDiscriminatorType()
 * @model
 * @generated
 */
public enum DiscriminatorType implements Enumerator {
	/**
	 * The '<em><b>Default</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFAULT_VALUE
	 * @generated
	 * @ordered
	 */
	DEFAULT(0, "Default", "Default (String)"),
	/**
	 * The '<em><b>STRING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRING_VALUE
	 * @generated
	 * @ordered
	 */
	STRING(1, "STRING", "String"),
	/**
	 * The '<em><b>CHAR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHAR_VALUE
	 * @generated
	 * @ordered
	 */
	CHAR(2, "CHAR", "Char"),
	/**
	 * The '<em><b>INTEGER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTEGER_VALUE
	 * @generated
	 * @ordered
	 */
	INTEGER(3, "INTEGER", "Integer");
	/**
	 * The '<em><b>Default</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Default</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEFAULT
	 * @model name="Default" literal="Default (String)"
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT_VALUE = 0;

	/**
	 * The '<em><b>STRING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>String</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRING
	 * @model literal="String"
	 * @generated
	 * @ordered
	 */
	public static final int STRING_VALUE = 1;

	/**
	 * The '<em><b>CHAR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Char</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHAR
	 * @model literal="Char"
	 * @generated
	 * @ordered
	 */
	public static final int CHAR_VALUE = 2;

	/**
	 * The '<em><b>INTEGER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Integer</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INTEGER
	 * @model literal="Integer"
	 * @generated
	 * @ordered
	 */
	public static final int INTEGER_VALUE = 3;

	/**
	 * An array of all the '<em><b>Discriminator Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DiscriminatorType[] VALUES_ARRAY = new DiscriminatorType[] {
		DEFAULT, STRING, CHAR, INTEGER,
	};

	/**
	 * A public read-only list of all the '<em><b>Discriminator Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DiscriminatorType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Discriminator Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DiscriminatorType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DiscriminatorType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Discriminator Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DiscriminatorType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DiscriminatorType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Discriminator Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DiscriminatorType get(int value) {
		switch (value) {
			case DEFAULT_VALUE :
				return DEFAULT;
			case STRING_VALUE :
				return STRING;
			case CHAR_VALUE :
				return CHAR;
			case INTEGER_VALUE :
				return INTEGER;
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
	private DiscriminatorType(int value, String name, String literal) {
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

	public static DiscriminatorType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return DEFAULT;
		}
		if (javaAnnotationValue.equals(JPA.DISCRIMINATOR_TYPE__CHAR)) {
			return CHAR;
		}
		if (javaAnnotationValue.equals(JPA.DISCRIMINATOR_TYPE__INTEGER)) {
			return INTEGER;
		}
		if (javaAnnotationValue.equals(JPA.DISCRIMINATOR_TYPE__STRING)) {
			return STRING;
		}
		return DEFAULT;
	}

	public String convertToJavaAnnotationValue() {
		switch (this.getValue()) {
			case DiscriminatorType.DEFAULT_VALUE :
				return null;
			case CHAR_VALUE :
				return JPA.DISCRIMINATOR_TYPE__CHAR;
			case INTEGER_VALUE :
				return JPA.DISCRIMINATOR_TYPE__INTEGER;
			case STRING_VALUE :
				return JPA.DISCRIMINATOR_TYPE__STRING;
			default :
				throw new IllegalArgumentException("unknown discriminator type: " + this);
		}
	}

	public boolean isString() {
		return (this.getValue() == DEFAULT_VALUE) || (this.getValue() == STRING_VALUE);
	}
} //DiscriminatorType
