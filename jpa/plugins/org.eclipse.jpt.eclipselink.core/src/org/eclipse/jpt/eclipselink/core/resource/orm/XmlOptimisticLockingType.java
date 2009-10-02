/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
 * A representation of the literals of the enumeration '<em><b>Xml Optimistic Locking Type</b></em>',
 * and utility methods for working with them.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 * 
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlOptimisticLockingType()
 * @model
 * @generated
 */
public enum XmlOptimisticLockingType implements Enumerator
{
	/**
	 * The '<em><b>ALL COLUMNS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALL_COLUMNS_VALUE
	 * @generated
	 * @ordered
	 */
	ALL_COLUMNS(0, "ALL_COLUMNS", "ALL_COLUMNS"),

	/**
	 * The '<em><b>CHANGED COLUMNS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGED_COLUMNS_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGED_COLUMNS(1, "CHANGED_COLUMNS", "CHANGED_COLUMNS"),

	/**
	 * The '<em><b>SELECTED COLUMNS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SELECTED_COLUMNS_VALUE
	 * @generated
	 * @ordered
	 */
	SELECTED_COLUMNS(2, "SELECTED_COLUMNS", "SELECTED_COLUMNS"),

	/**
	 * The '<em><b>VERSION COLUMN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VERSION_COLUMN_VALUE
	 * @generated
	 * @ordered
	 */
	VERSION_COLUMN(3, "VERSION_COLUMN", "VERSION_COLUMN");

	/**
	 * The '<em><b>ALL COLUMNS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ALL COLUMNS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALL_COLUMNS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ALL_COLUMNS_VALUE = 0;

	/**
	 * The '<em><b>CHANGED COLUMNS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHANGED COLUMNS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANGED_COLUMNS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CHANGED_COLUMNS_VALUE = 1;

	/**
	 * The '<em><b>SELECTED COLUMNS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SELECTED COLUMNS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SELECTED_COLUMNS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SELECTED_COLUMNS_VALUE = 2;

	/**
	 * The '<em><b>VERSION COLUMN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>VERSION COLUMN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VERSION_COLUMN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int VERSION_COLUMN_VALUE = 3;

	/**
	 * An array of all the '<em><b>Xml Optimistic Locking Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final XmlOptimisticLockingType[] VALUES_ARRAY =
		new XmlOptimisticLockingType[]
		{
			ALL_COLUMNS,
			CHANGED_COLUMNS,
			SELECTED_COLUMNS,
			VERSION_COLUMN,
		};

	/**
	 * A public read-only list of all the '<em><b>Xml Optimistic Locking Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<XmlOptimisticLockingType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Xml Optimistic Locking Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlOptimisticLockingType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			XmlOptimisticLockingType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Xml Optimistic Locking Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlOptimisticLockingType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			XmlOptimisticLockingType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Xml Optimistic Locking Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlOptimisticLockingType get(int value)
	{
		switch (value)
		{
			case ALL_COLUMNS_VALUE: return ALL_COLUMNS;
			case CHANGED_COLUMNS_VALUE: return CHANGED_COLUMNS;
			case SELECTED_COLUMNS_VALUE: return SELECTED_COLUMNS;
			case VERSION_COLUMN_VALUE: return VERSION_COLUMN;
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
	private XmlOptimisticLockingType(int value, String name, String literal)
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
	
} //XmlOptimisticLockingType
