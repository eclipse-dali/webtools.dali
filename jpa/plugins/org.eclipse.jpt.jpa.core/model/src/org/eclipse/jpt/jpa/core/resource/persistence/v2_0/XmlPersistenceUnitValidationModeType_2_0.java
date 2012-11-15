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

package org.eclipse.jpt.jpa.core.resource.persistence.v2_0;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Xml Persistence Unit Validation Mode Type 20</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jpa.core.resource.persistence.v2_0.PersistenceV2_0Package#getXmlPersistenceUnitValidationModeType_2_0()
 * @model
 * @generated
 */
public enum XmlPersistenceUnitValidationModeType_2_0 implements Enumerator
{
	/**
	 * The '<em><b>AUTO</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AUTO_VALUE
	 * @generated
	 * @ordered
	 */
	AUTO(0, "AUTO", "AUTO"),

	/**
	 * The '<em><b>CALLBACK</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CALLBACK_VALUE
	 * @generated
	 * @ordered
	 */
	CALLBACK(1, "CALLBACK", "CALLBACK"),

	/**
	 * The '<em><b>NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(2, "NONE", "NONE");

	/**
	 * The '<em><b>AUTO</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>AUTO</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AUTO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int AUTO_VALUE = 0;

	/**
	 * The '<em><b>CALLBACK</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CALLBACK</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CALLBACK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CALLBACK_VALUE = 1;

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
	public static final int NONE_VALUE = 2;

	/**
	 * An array of all the '<em><b>Xml Persistence Unit Validation Mode Type 20</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final XmlPersistenceUnitValidationModeType_2_0[] VALUES_ARRAY =
		new XmlPersistenceUnitValidationModeType_2_0[]
		{
			AUTO,
			CALLBACK,
			NONE,
		};

	/**
	 * A public read-only list of all the '<em><b>Xml Persistence Unit Validation Mode Type 20</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<XmlPersistenceUnitValidationModeType_2_0> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Xml Persistence Unit Validation Mode Type 20</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlPersistenceUnitValidationModeType_2_0 get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			XmlPersistenceUnitValidationModeType_2_0 result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Xml Persistence Unit Validation Mode Type 20</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlPersistenceUnitValidationModeType_2_0 getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			XmlPersistenceUnitValidationModeType_2_0 result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Xml Persistence Unit Validation Mode Type 20</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlPersistenceUnitValidationModeType_2_0 get(int value)
	{
		switch (value)
		{
			case AUTO_VALUE: return AUTO;
			case CALLBACK_VALUE: return CALLBACK;
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
	private XmlPersistenceUnitValidationModeType_2_0(int value, String name, String literal)
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
	
} //XmlPersistenceUnitValidationModeType_2_0
