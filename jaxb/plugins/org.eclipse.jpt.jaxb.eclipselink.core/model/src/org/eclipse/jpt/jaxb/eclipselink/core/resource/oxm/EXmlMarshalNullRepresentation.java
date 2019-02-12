/*******************************************************************************
 * Copyright (c) 2012, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>EXml Marshal Null Representation</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlMarshalNullRepresentation()
 * @model
 * @generated
 */
public enum EXmlMarshalNullRepresentation implements Enumerator
{
	/**
	 * The '<em><b>XSI NIL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XSI_NIL_VALUE
	 * @generated
	 * @ordered
	 */
	XSI_NIL(0, "XSI_NIL", "XSI_NIL"),

	/**
	 * The '<em><b>ABSENT NODE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABSENT_NODE_VALUE
	 * @generated
	 * @ordered
	 */
	ABSENT_NODE(1, "ABSENT_NODE", "ABSENT_NODE"),

	/**
	 * The '<em><b>EMPTY NODE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EMPTY_NODE_VALUE
	 * @generated
	 * @ordered
	 */
	EMPTY_NODE(2, "EMPTY_NODE", "EMPTY_NODE");

	/**
	 * The '<em><b>XSI NIL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XSI NIL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XSI_NIL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int XSI_NIL_VALUE = 0;

	/**
	 * The '<em><b>ABSENT NODE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ABSENT NODE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ABSENT_NODE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ABSENT_NODE_VALUE = 1;

	/**
	 * The '<em><b>EMPTY NODE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EMPTY NODE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EMPTY_NODE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EMPTY_NODE_VALUE = 2;

	/**
	 * An array of all the '<em><b>EXml Marshal Null Representation</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EXmlMarshalNullRepresentation[] VALUES_ARRAY =
		new EXmlMarshalNullRepresentation[]
		{
			XSI_NIL,
			ABSENT_NODE,
			EMPTY_NODE,
		};

	/**
	 * A public read-only list of all the '<em><b>EXml Marshal Null Representation</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EXmlMarshalNullRepresentation> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>EXml Marshal Null Representation</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EXmlMarshalNullRepresentation get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			EXmlMarshalNullRepresentation result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EXml Marshal Null Representation</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EXmlMarshalNullRepresentation getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			EXmlMarshalNullRepresentation result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EXml Marshal Null Representation</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EXmlMarshalNullRepresentation get(int value)
	{
		switch (value)
		{
			case XSI_NIL_VALUE: return XSI_NIL;
			case ABSENT_NODE_VALUE: return ABSENT_NODE;
			case EMPTY_NODE_VALUE: return EMPTY_NODE;
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
	private EXmlMarshalNullRepresentation(int value, String name, String literal)
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
	
} //EXmlMarshalNullRepresentation
