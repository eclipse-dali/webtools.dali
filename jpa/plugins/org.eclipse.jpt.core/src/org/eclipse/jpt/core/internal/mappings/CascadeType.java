/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.jpt.core.internal.mappings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Cascade Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getCascadeType()
 * @model
 * @generated
 */
public enum CascadeType implements Enumerator {
	/**
	 * The '<em><b>ALL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALL_VALUE
	 * @generated
	 * @ordered
	 */
	ALL(0, "ALL", "All"),
	/**
	 * The '<em><b>PERSIST</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PERSIST_VALUE
	 * @generated
	 * @ordered
	 */
	PERSIST(1, "PERSIST", "Persist"),
	/**
	 * The '<em><b>MERGE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MERGE_VALUE
	 * @generated
	 * @ordered
	 */
	MERGE(2, "MERGE", "Merge"),
	/**
	 * The '<em><b>REMOVE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REMOVE_VALUE
	 * @generated
	 * @ordered
	 */
	REMOVE(3, "REMOVE", "Remove"),
	/**
	 * The '<em><b>REFRESH</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REFRESH_VALUE
	 * @generated
	 * @ordered
	 */
	REFRESH(4, "REFRESH", "Refresh");
	/**
	 * The '<em><b>ALL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ALL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALL
	 * @model literal="All"
	 * @generated
	 * @ordered
	 */
	public static final int ALL_VALUE = 0;

	/**
	 * The '<em><b>PERSIST</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PERSIST</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PERSIST
	 * @model literal="Persist"
	 * @generated
	 * @ordered
	 */
	public static final int PERSIST_VALUE = 1;

	/**
	 * The '<em><b>MERGE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MERGE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MERGE
	 * @model literal="Merge"
	 * @generated
	 * @ordered
	 */
	public static final int MERGE_VALUE = 2;

	/**
	 * The '<em><b>REMOVE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REMOVE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REMOVE
	 * @model literal="Remove"
	 * @generated
	 * @ordered
	 */
	public static final int REMOVE_VALUE = 3;

	/**
	 * The '<em><b>REFRESH</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REFRESH</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REFRESH
	 * @model literal="Refresh"
	 * @generated
	 * @ordered
	 */
	public static final int REFRESH_VALUE = 4;

	/**
	 * An array of all the '<em><b>Cascade Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CascadeType[] VALUES_ARRAY = new CascadeType[] {
		ALL, PERSIST, MERGE, REMOVE, REFRESH,
	};

	/**
	 * A public read-only list of all the '<em><b>Cascade Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CascadeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Cascade Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CascadeType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CascadeType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Cascade Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CascadeType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CascadeType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Cascade Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CascadeType get(int value) {
		switch (value) {
			case ALL_VALUE :
				return ALL;
			case PERSIST_VALUE :
				return PERSIST;
			case MERGE_VALUE :
				return MERGE;
			case REMOVE_VALUE :
				return REMOVE;
			case REFRESH_VALUE :
				return REFRESH;
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
	private CascadeType(int value, String name, String literal) {
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

	public static CascadeType[] fromJavaAnnotationValue(String[] javaAnnotationValues) {
		if (javaAnnotationValues == null) {
			return new CascadeType[0];
		}
		CascadeType[] cascadeTypes = new CascadeType[javaAnnotationValues.length];
		for (int i = 0; i < javaAnnotationValues.length; i++) {
			String javaAnnotationValue = javaAnnotationValues[i];
			if (javaAnnotationValue != null) {
				if (javaAnnotationValue.equals(JPA.CASCADE_TYPE__ALL)) {
					cascadeTypes[i] = ALL;
				}
				else if (javaAnnotationValue.equals(JPA.CASCADE_TYPE__PERSIST)) {
					cascadeTypes[i] = PERSIST;
				}
				else if (javaAnnotationValue.equals(JPA.CASCADE_TYPE__MERGE)) {
					cascadeTypes[i] = MERGE;
				}
				else if (javaAnnotationValue.equals(JPA.CASCADE_TYPE__REMOVE)) {
					cascadeTypes[i] = REMOVE;
				}
				else if (javaAnnotationValue.equals(JPA.CASCADE_TYPE__REFRESH)) {
					cascadeTypes[i] = REFRESH;
				}
			}
		}
		return cascadeTypes;
	}

	public static String[] toJavaAnnotationValue(CascadeType[] cascadeTypes) {
		String[] javaAnnotationValues = new String[cascadeTypes.length];
		for (int i = 0; i < cascadeTypes.length; i++) {
			CascadeType cascadeType = cascadeTypes[i];
			if (cascadeType == ALL) {
				javaAnnotationValues[i] = JPA.CASCADE_TYPE__ALL;
			}
			else if (cascadeType == PERSIST) {
				javaAnnotationValues[i] = JPA.CASCADE_TYPE__PERSIST;
			}
			else if (cascadeType == MERGE) {
				javaAnnotationValues[i] = JPA.CASCADE_TYPE__MERGE;
			}
			else if (cascadeType == REMOVE) {
				javaAnnotationValues[i] = JPA.CASCADE_TYPE__REMOVE;
			}
			else if (cascadeType == REFRESH) {
				javaAnnotationValues[i] = JPA.CASCADE_TYPE__REFRESH;
			}
		}
		return javaAnnotationValues;
	}
} //CascadeType
