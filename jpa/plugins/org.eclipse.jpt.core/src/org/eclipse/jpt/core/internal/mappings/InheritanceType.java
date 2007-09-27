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
import org.eclipse.jpt.core.internal.resource.java.JPA;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Inheritance Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getInheritanceType()
 * @model
 * @generated
 */
public enum InheritanceType implements Enumerator {
	/**
	 * The '<em><b>Default</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFAULT_VALUE
	 * @generated
	 * @ordered
	 */
	DEFAULT(0, "Default", "Default (Single Table)"),
	/**
	 * The '<em><b>SINGLE TABLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SINGLE_TABLE_VALUE
	 * @generated
	 * @ordered
	 */
	SINGLE_TABLE(1, "SINGLE_TABLE", "Single Table"),
	/**
	 * The '<em><b>JOINED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JOINED_VALUE
	 * @generated
	 * @ordered
	 */
	JOINED(2, "JOINED", "Joined"),
	/**
	 * The '<em><b>TABLE PER CLASS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TABLE_PER_CLASS_VALUE
	 * @generated
	 * @ordered
	 */
	TABLE_PER_CLASS(3, "TABLE_PER_CLASS", "Table per Class");
	/**
	 * The '<em><b>Default</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Default</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEFAULT
	 * @model name="Default" literal="Default (Single Table)"
	 * @generated
	 * @ordered
	 */
	public static final int DEFAULT_VALUE = 0;

	/**
	 * The '<em><b>SINGLE TABLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Single Table</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SINGLE_TABLE
	 * @model literal="Single Table"
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_TABLE_VALUE = 1;

	/**
	 * The '<em><b>JOINED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Joined</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JOINED
	 * @model literal="Joined"
	 * @generated
	 * @ordered
	 */
	public static final int JOINED_VALUE = 2;

	/**
	 * The '<em><b>TABLE PER CLASS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Table Per Class</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TABLE_PER_CLASS
	 * @model literal="Table per Class"
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_PER_CLASS_VALUE = 3;

	/**
	 * An array of all the '<em><b>Inheritance Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final InheritanceType[] VALUES_ARRAY = new InheritanceType[] {
		DEFAULT, SINGLE_TABLE, JOINED, TABLE_PER_CLASS,
	};

	/**
	 * A public read-only list of all the '<em><b>Inheritance Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<InheritanceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Inheritance Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InheritanceType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			InheritanceType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Inheritance Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InheritanceType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			InheritanceType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Inheritance Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InheritanceType get(int value) {
		switch (value) {
			case DEFAULT_VALUE :
				return DEFAULT;
			case SINGLE_TABLE_VALUE :
				return SINGLE_TABLE;
			case JOINED_VALUE :
				return JOINED;
			case TABLE_PER_CLASS_VALUE :
				return TABLE_PER_CLASS;
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
	private InheritanceType(int value, String name, String literal) {
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

	public static InheritanceType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return DEFAULT;
		}
		if (javaAnnotationValue.equals(JPA.INHERITANCE_TYPE__JOINED)) {
			return JOINED;
		}
		if (javaAnnotationValue.equals(JPA.INHERITANCE_TYPE__SINGLE_TABLE)) {
			return SINGLE_TABLE;
		}
		if (javaAnnotationValue.equals(JPA.INHERITANCE_TYPE__TABLE_PER_CLASS)) {
			return TABLE_PER_CLASS;
		}
		return DEFAULT;
	}

	public String convertToJavaAnnotationValue() {
		switch (this.getValue()) {
			case DEFAULT_VALUE :
				return null;
			case JOINED_VALUE :
				return JPA.INHERITANCE_TYPE__JOINED;
			case SINGLE_TABLE_VALUE :
				return JPA.INHERITANCE_TYPE__SINGLE_TABLE;
			case TABLE_PER_CLASS_VALUE :
				return JPA.INHERITANCE_TYPE__TABLE_PER_CLASS;
			default :
				throw new IllegalArgumentException("unknown inheritance type: " + this);
		}
	}

	public boolean isSingleTable() {
		return (this.getValue() == DEFAULT_VALUE) || (this.getValue() == SINGLE_TABLE_VALUE);
	}
} //InheritanceType
