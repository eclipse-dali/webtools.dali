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
 * A representation of the literals of the enumeration '<em><b>Existence Type</b></em>',
 * and utility methods for working with them.
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
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getExistenceType()
 * @model
 * @generated
 */
public enum ExistenceType implements Enumerator
{
	/**
	 * The '<em><b>CHECK CACHE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHECK_CACHE_VALUE
	 * @generated
	 * @ordered
	 */
	CHECK_CACHE(0, "CHECK_CACHE", "CHECK_CACHE"),

	/**
	 * The '<em><b>CHECK DATABASE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHECK_DATABASE_VALUE
	 * @generated
	 * @ordered
	 */
	CHECK_DATABASE(1, "CHECK_DATABASE", "CHECK_DATABASE"),

	/**
	 * The '<em><b>ASSUME EXISTENCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASSUME_EXISTENCE_VALUE
	 * @generated
	 * @ordered
	 */
	ASSUME_EXISTENCE(2, "ASSUME_EXISTENCE", "ASSUME_EXISTENCE"),

	/**
	 * The '<em><b>ASSUME NON EXISTENCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASSUME_NON_EXISTENCE_VALUE
	 * @generated
	 * @ordered
	 */
	ASSUME_NON_EXISTENCE(3, "ASSUME_NON_EXISTENCE", "ASSUME_NON_EXISTENCE");

	/**
	 * The '<em><b>CHECK CACHE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHECK CACHE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHECK_CACHE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CHECK_CACHE_VALUE = 0;

	/**
	 * The '<em><b>CHECK DATABASE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHECK DATABASE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHECK_DATABASE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CHECK_DATABASE_VALUE = 1;

	/**
	 * The '<em><b>ASSUME EXISTENCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ASSUME EXISTENCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ASSUME_EXISTENCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ASSUME_EXISTENCE_VALUE = 2;

	/**
	 * The '<em><b>ASSUME NON EXISTENCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ASSUME NON EXISTENCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ASSUME_NON_EXISTENCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ASSUME_NON_EXISTENCE_VALUE = 3;

	/**
	 * An array of all the '<em><b>Existence Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ExistenceType[] VALUES_ARRAY =
		new ExistenceType[]
		{
			CHECK_CACHE,
			CHECK_DATABASE,
			ASSUME_EXISTENCE,
			ASSUME_NON_EXISTENCE,
		};

	/**
	 * A public read-only list of all the '<em><b>Existence Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ExistenceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Existence Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ExistenceType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			ExistenceType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Existence Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ExistenceType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			ExistenceType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Existence Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ExistenceType get(int value)
	{
		switch (value)
		{
			case CHECK_CACHE_VALUE: return CHECK_CACHE;
			case CHECK_DATABASE_VALUE: return CHECK_DATABASE;
			case ASSUME_EXISTENCE_VALUE: return ASSUME_EXISTENCE;
			case ASSUME_NON_EXISTENCE_VALUE: return ASSUME_NON_EXISTENCE;
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
	private ExistenceType(int value, String name, String literal)
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
	
} //ExistenceType
