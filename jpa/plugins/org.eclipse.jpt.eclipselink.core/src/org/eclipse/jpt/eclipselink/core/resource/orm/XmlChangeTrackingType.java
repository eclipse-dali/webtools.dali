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

package org.eclipse.jpt.eclipselink.core.resource.orm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * 
 * A representation of the literals of the enumeration '<em><b>Xml Change Tracking Type</b></em>',
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
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlChangeTrackingType()
 * @model
 * @generated
 */
public enum XmlChangeTrackingType implements Enumerator
{
	/**
	 * The '<em><b>ATTRIBUTE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ATTRIBUTE_VALUE
	 * @generated
	 * @ordered
	 */
	ATTRIBUTE(0, "ATTRIBUTE", "ATTRIBUTE"), /**
	 * The '<em><b>OBJECT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJECT_VALUE
	 * @generated
	 * @ordered
	 */
	OBJECT(1, "OBJECT", "OBJECT"), /**
	 * The '<em><b>DEFERRED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEFERRED_VALUE
	 * @generated
	 * @ordered
	 */
	DEFERRED(2, "DEFERRED", "DEFERRED"), /**
	 * The '<em><b>AUTO</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AUTO_VALUE
	 * @generated
	 * @ordered
	 */
	AUTO(3, "AUTO", "AUTO")
	;

	/**
	 * The '<em><b>ATTRIBUTE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ATTRIBUTE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ATTRIBUTE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_VALUE = 0;

/**
	 * The '<em><b>OBJECT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OBJECT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OBJECT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OBJECT_VALUE = 1;

/**
	 * The '<em><b>DEFERRED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DEFERRED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEFERRED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DEFERRED_VALUE = 2;

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
	public static final int AUTO_VALUE = 3;

	/**
	 * An array of all the '<em><b>Xml Change Tracking Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final XmlChangeTrackingType[] VALUES_ARRAY =
		new XmlChangeTrackingType[]
		{
			ATTRIBUTE,
			OBJECT,
			DEFERRED,
			AUTO,
		};

	/**
	 * A public read-only list of all the '<em><b>Xml Change Tracking Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<XmlChangeTrackingType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Xml Change Tracking Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlChangeTrackingType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			XmlChangeTrackingType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Xml Change Tracking Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlChangeTrackingType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			XmlChangeTrackingType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Xml Change Tracking Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlChangeTrackingType get(int value)
	{
		switch (value)
		{
			case ATTRIBUTE_VALUE: return ATTRIBUTE;
			case OBJECT_VALUE: return OBJECT;
			case DEFERRED_VALUE: return DEFERRED;
			case AUTO_VALUE: return AUTO;
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
	private XmlChangeTrackingType(int value, String name, String literal)
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
	
} //XmlChangeTrackingType
