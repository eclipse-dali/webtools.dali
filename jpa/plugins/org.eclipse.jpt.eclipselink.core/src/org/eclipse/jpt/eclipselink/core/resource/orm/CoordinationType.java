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
 * A representation of the literals of the enumeration '<em><b>Coordination Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getCoordinationType()
 * @model
 * @generated
 */
public enum CoordinationType implements Enumerator
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
	 * An array of all the '<em><b>Coordination Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CoordinationType[] VALUES_ARRAY =
		new CoordinationType[]
		{
			SEND_OBJECT_CHANGES,
			INVALIDATE_CHANGED_OBJECTS,
			SEND_NEW_OBJECTS_WITH_CHANGES,
			NONE,
		};

	/**
	 * A public read-only list of all the '<em><b>Coordination Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CoordinationType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Coordination Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CoordinationType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			CoordinationType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Coordination Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CoordinationType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			CoordinationType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Coordination Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CoordinationType get(int value)
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
	private CoordinationType(int value, String name, String literal)
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
	
} //CoordinationType
