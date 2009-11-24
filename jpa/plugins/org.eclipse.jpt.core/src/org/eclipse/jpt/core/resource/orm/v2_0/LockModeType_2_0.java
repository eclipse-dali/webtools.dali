/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.resource.orm.v2_0;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Lock Mode Type 20</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getLockModeType_2_0()
 * @model
 * @generated
 */
public enum LockModeType_2_0 implements Enumerator
{
	/**
	 * The '<em><b>NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(0, "NONE", "NONE"), /**
	 * The '<em><b>READ</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #READ_VALUE
	 * @generated
	 * @ordered
	 */
	READ(1, "READ", "READ"),

	/**
	 * The '<em><b>WRITE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WRITE_VALUE
	 * @generated
	 * @ordered
	 */
	WRITE(2, "WRITE", "WRITE"),

	/**
	 * The '<em><b>OPTIMISTIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OPTIMISTIC_VALUE
	 * @generated
	 * @ordered
	 */
	OPTIMISTIC(3, "OPTIMISTIC", "OPTIMISTIC"),

	/**
	 * The '<em><b>OPTIMISTIC FORCE INCREMENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OPTIMISTIC_FORCE_INCREMENT_VALUE
	 * @generated
	 * @ordered
	 */
	OPTIMISTIC_FORCE_INCREMENT(4, "OPTIMISTIC_FORCE_INCREMENT", "OPTIMISTIC_FORCE_INCREMENT"),

	/**
	 * The '<em><b>PESSIMISTIC READ</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PESSIMISTIC_READ_VALUE
	 * @generated
	 * @ordered
	 */
	PESSIMISTIC_READ(5, "PESSIMISTIC_READ", "PESSIMISTIC_READ"),

	/**
	 * The '<em><b>PESSIMISTIC WRITE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PESSIMISTIC_WRITE_VALUE
	 * @generated
	 * @ordered
	 */
	PESSIMISTIC_WRITE(6, "PESSIMISTIC_WRITE", "PESSIMISTIC_WRITE"),

	/**
	 * The '<em><b>PESSIMISTIC FORCE INCREMENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PESSIMISTIC_FORCE_INCREMENT_VALUE
	 * @generated
	 * @ordered
	 */
	PESSIMISTIC_FORCE_INCREMENT(7, "PESSIMISTIC_FORCE_INCREMENT", "PESSIMISTIC_FORCE_INCREMENT");

	/**
	 * The '<em><b>NONE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NONE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 0;

	/**
	 * The '<em><b>READ</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>READ</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #READ
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int READ_VALUE = 1;

	/**
	 * The '<em><b>WRITE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WRITE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WRITE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WRITE_VALUE = 2;

	/**
	 * The '<em><b>OPTIMISTIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OPTIMISTIC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OPTIMISTIC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OPTIMISTIC_VALUE = 3;

	/**
	 * The '<em><b>OPTIMISTIC FORCE INCREMENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OPTIMISTIC FORCE INCREMENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OPTIMISTIC_FORCE_INCREMENT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OPTIMISTIC_FORCE_INCREMENT_VALUE = 4;

	/**
	 * The '<em><b>PESSIMISTIC READ</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PESSIMISTIC READ</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PESSIMISTIC_READ
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PESSIMISTIC_READ_VALUE = 5;

	/**
	 * The '<em><b>PESSIMISTIC WRITE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PESSIMISTIC WRITE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PESSIMISTIC_WRITE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PESSIMISTIC_WRITE_VALUE = 6;

	/**
	 * The '<em><b>PESSIMISTIC FORCE INCREMENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PESSIMISTIC FORCE INCREMENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PESSIMISTIC_FORCE_INCREMENT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PESSIMISTIC_FORCE_INCREMENT_VALUE = 7;

	/**
	 * An array of all the '<em><b>Lock Mode Type 20</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final LockModeType_2_0[] VALUES_ARRAY =
		new LockModeType_2_0[]
		{
			NONE,
			READ,
			WRITE,
			OPTIMISTIC,
			OPTIMISTIC_FORCE_INCREMENT,
			PESSIMISTIC_READ,
			PESSIMISTIC_WRITE,
			PESSIMISTIC_FORCE_INCREMENT,
		};

	/**
	 * A public read-only list of all the '<em><b>Lock Mode Type 20</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<LockModeType_2_0> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Lock Mode Type 20</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LockModeType_2_0 get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			LockModeType_2_0 result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Lock Mode Type 20</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LockModeType_2_0 getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			LockModeType_2_0 result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Lock Mode Type 20</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LockModeType_2_0 get(int value)
	{
		switch (value)
		{
			case NONE_VALUE: return NONE;
			case READ_VALUE: return READ;
			case WRITE_VALUE: return WRITE;
			case OPTIMISTIC_VALUE: return OPTIMISTIC;
			case OPTIMISTIC_FORCE_INCREMENT_VALUE: return OPTIMISTIC_FORCE_INCREMENT;
			case PESSIMISTIC_READ_VALUE: return PESSIMISTIC_READ;
			case PESSIMISTIC_WRITE_VALUE: return PESSIMISTIC_WRITE;
			case PESSIMISTIC_FORCE_INCREMENT_VALUE: return PESSIMISTIC_FORCE_INCREMENT;
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
	private LockModeType_2_0(int value, String name, String literal)
	{
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue()
	{
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName()
	{
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral()
	{
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		return literal;
	}
	
} //LockModeType_2_0
