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
 * A representation of the literals of the enumeration '<em><b>Cache Coordination Type</b></em>',
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
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCacheCoordinationType()
 * @model
 * @generated
 */
public enum CacheCoordinationType implements Enumerator
{
	/**
	 * The '<em><b>SEND OBJECT CHANGES</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SEND_OBJECT_CHANGES_VALUE
	 * @generated
	 * @ordered
	 */
	SEND_OBJECT_CHANGES(0, "SEND_OBJECT_CHANGES", "SEND_OBJECT_CHANGES"),

	/**
	 * The '<em><b>INVALIDATE CHANGED OBJECTS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INVALIDATE_CHANGED_OBJECTS_VALUE
	 * @generated
	 * @ordered
	 */
	INVALIDATE_CHANGED_OBJECTS(1, "INVALIDATE_CHANGED_OBJECTS", "INVALIDATE_CHANGED_OBJECTS"),

	/**
	 * The '<em><b>SEND NEW OBJECTS WITH CHANGES</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SEND_NEW_OBJECTS_WITH_CHANGES_VALUE
	 * @generated
	 * @ordered
	 */
	SEND_NEW_OBJECTS_WITH_CHANGES(2, "SEND_NEW_OBJECTS_WITH_CHANGES", "SEND_NEW_OBJECTS_WITH_CHANGES"),

	/**
	 * The '<em><b>NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(3, "NONE", "NONE");

	/**
	 * The '<em><b>SEND OBJECT CHANGES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SEND OBJECT CHANGES</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SEND_OBJECT_CHANGES
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SEND_OBJECT_CHANGES_VALUE = 0;

	/**
	 * The '<em><b>INVALIDATE CHANGED OBJECTS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INVALIDATE CHANGED OBJECTS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INVALIDATE_CHANGED_OBJECTS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INVALIDATE_CHANGED_OBJECTS_VALUE = 1;

	/**
	 * The '<em><b>SEND NEW OBJECTS WITH CHANGES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SEND NEW OBJECTS WITH CHANGES</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SEND_NEW_OBJECTS_WITH_CHANGES
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SEND_NEW_OBJECTS_WITH_CHANGES_VALUE = 2;

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
	public static final int NONE_VALUE = 3;

	/**
	 * An array of all the '<em><b>Cache Coordination Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CacheCoordinationType[] VALUES_ARRAY =
		new CacheCoordinationType[]
		{
			SEND_OBJECT_CHANGES,
			INVALIDATE_CHANGED_OBJECTS,
			SEND_NEW_OBJECTS_WITH_CHANGES,
			NONE,
		};

	/**
	 * A public read-only list of all the '<em><b>Cache Coordination Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CacheCoordinationType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Cache Coordination Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CacheCoordinationType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			CacheCoordinationType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Cache Coordination Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CacheCoordinationType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			CacheCoordinationType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Cache Coordination Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CacheCoordinationType get(int value)
	{
		switch (value)
		{
			case SEND_OBJECT_CHANGES_VALUE: return SEND_OBJECT_CHANGES;
			case INVALIDATE_CHANGED_OBJECTS_VALUE: return INVALIDATE_CHANGED_OBJECTS;
			case SEND_NEW_OBJECTS_WITH_CHANGES_VALUE: return SEND_NEW_OBJECTS_WITH_CHANGES;
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
	private CacheCoordinationType(int value, String name, String literal)
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
	
} //CacheCoordinationType
