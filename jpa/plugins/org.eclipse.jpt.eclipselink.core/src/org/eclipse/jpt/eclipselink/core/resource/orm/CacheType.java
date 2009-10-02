/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.eclipselink.core.resource.orm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 * 
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheType()
 * @model
 * @generated
 */
public enum CacheType implements Enumerator
{
	/**
	 * The '<em><b>FULL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FULL_VALUE
	 * @generated
	 * @ordered
	 */
	FULL(0, "FULL", "FULL"),

	/**
	 * The '<em><b>WEAK</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WEAK_VALUE
	 * @generated
	 * @ordered
	 */
	WEAK(1, "WEAK", "WEAK"),

	/**
	 * The '<em><b>SOFT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SOFT_VALUE
	 * @generated
	 * @ordered
	 */
	SOFT(2, "SOFT", "SOFT"),

	/**
	 * The '<em><b>SOFT WEAK</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SOFT_WEAK_VALUE
	 * @generated
	 * @ordered
	 */
	SOFT_WEAK(3, "SOFT_WEAK", "SOFT_WEAK"),

	/**
	 * The '<em><b>HARD WEAK</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HARD_WEAK_VALUE
	 * @generated
	 * @ordered
	 */
	HARD_WEAK(4, "HARD_WEAK", "HARD_WEAK"),

	/**
	 * The '<em><b>CACHE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CACHE_VALUE
	 * @generated
	 * @ordered
	 */
	CACHE(5, "CACHE", "CACHE"),

	/**
	 * The '<em><b>NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(6, "NONE", "NONE");

	/**
	 * The '<em><b>FULL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FULL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FULL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FULL_VALUE = 0;

	/**
	 * The '<em><b>WEAK</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WEAK</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WEAK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WEAK_VALUE = 1;

	/**
	 * The '<em><b>SOFT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SOFT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SOFT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SOFT_VALUE = 2;

	/**
	 * The '<em><b>SOFT WEAK</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SOFT WEAK</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SOFT_WEAK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SOFT_WEAK_VALUE = 3;

	/**
	 * The '<em><b>HARD WEAK</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>HARD WEAK</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HARD_WEAK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int HARD_WEAK_VALUE = 4;

	/**
	 * The '<em><b>CACHE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CACHE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CACHE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CACHE_VALUE = 5;

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
	public static final int NONE_VALUE = 6;

	/**
	 * An array of all the '<em><b>Cache Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CacheType[] VALUES_ARRAY =
		new CacheType[]
		{
			FULL,
			WEAK,
			SOFT,
			SOFT_WEAK,
			HARD_WEAK,
			CACHE,
			NONE,
		};

	/**
	 * A public read-only list of all the '<em><b>Cache Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CacheType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Cache Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CacheType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			CacheType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Cache Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CacheType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			CacheType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Cache Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CacheType get(int value)
	{
		switch (value)
		{
			case FULL_VALUE: return FULL;
			case WEAK_VALUE: return WEAK;
			case SOFT_VALUE: return SOFT;
			case SOFT_WEAK_VALUE: return SOFT_WEAK;
			case HARD_WEAK_VALUE: return HARD_WEAK;
			case CACHE_VALUE: return CACHE;
			case NONE_VALUE: return NONE;
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
	private CacheType(int value, String name, String literal)
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
	
} //CacheType
