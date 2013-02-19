/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm.v2_1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Constraint Mode 21</b></em>',
 * and utility methods for working with them.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package#getConstraintMode_2_1()
 * @model
 * @generated
 */
public enum ConstraintMode_2_1 implements Enumerator
{
	/**
	 * The '<em><b>CONSTRAINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONSTRAINT_VALUE
	 * @generated
	 * @ordered
	 */
	CONSTRAINT(0, "CONSTRAINT", "CONSTRAINT"),

	/**
	 * The '<em><b>NO CONSTRAINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_CONSTRAINT_VALUE
	 * @generated
	 * @ordered
	 */
	NO_CONSTRAINT(1, "NO_CONSTRAINT", "NO_CONSTRAINT"),

	/**
	 * The '<em><b>PROVIDER DEFAULT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROVIDER_DEFAULT_VALUE
	 * @generated
	 * @ordered
	 */
	PROVIDER_DEFAULT(2, "PROVIDER_DEFAULT", "PROVIDER_DEFAULT");

	/**
	 * The '<em><b>CONSTRAINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CONSTRAINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONSTRAINT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONSTRAINT_VALUE = 0;

	/**
	 * The '<em><b>NO CONSTRAINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NO CONSTRAINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_CONSTRAINT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NO_CONSTRAINT_VALUE = 1;

	/**
	 * The '<em><b>PROVIDER DEFAULT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROVIDER DEFAULT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROVIDER_DEFAULT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROVIDER_DEFAULT_VALUE = 2;

	/**
	 * An array of all the '<em><b>Constraint Mode 21</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ConstraintMode_2_1[] VALUES_ARRAY =
		new ConstraintMode_2_1[]
		{
			CONSTRAINT,
			NO_CONSTRAINT,
			PROVIDER_DEFAULT,
		};

	/**
	 * A public read-only list of all the '<em><b>Constraint Mode 21</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ConstraintMode_2_1> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Constraint Mode 21</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConstraintMode_2_1 get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			ConstraintMode_2_1 result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Constraint Mode 21</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConstraintMode_2_1 getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			ConstraintMode_2_1 result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Constraint Mode 21</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConstraintMode_2_1 get(int value)
	{
		switch (value)
		{
			case CONSTRAINT_VALUE: return CONSTRAINT;
			case NO_CONSTRAINT_VALUE: return NO_CONSTRAINT;
			case PROVIDER_DEFAULT_VALUE: return PROVIDER_DEFAULT;
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
	private ConstraintMode_2_1(int value, String name, String literal)
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
	
} //ConstraintMode_2_1
