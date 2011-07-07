/**
 * <copyright>
 * </copyright>
 *
 * $Id: XmlMultitenantType.java,v 1.1 2011/07/07 19:27:07 kmoore Exp $
 */
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Xml Multitenant Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlMultitenantType()
 * @model
 * @generated
 */
public enum XmlMultitenantType implements Enumerator
{
	/**
	 * The '<em><b>SINGLE TABLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SINGLE_TABLE_VALUE
	 * @generated
	 * @ordered
	 */
	SINGLE_TABLE(0, "SINGLE_TABLE", "SINGLE_TABLE"),

	/**
	 * The '<em><b>TABLE PER TENANT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TABLE_PER_TENANT_VALUE
	 * @generated
	 * @ordered
	 */
	TABLE_PER_TENANT(1, "TABLE_PER_TENANT", "CHANGED_COLUMNS");

	/**
	 * The '<em><b>SINGLE TABLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SINGLE TABLE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SINGLE_TABLE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_TABLE_VALUE = 0;

	/**
	 * The '<em><b>TABLE PER TENANT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TABLE PER TENANT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TABLE_PER_TENANT
	 * @model literal="CHANGED_COLUMNS"
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_PER_TENANT_VALUE = 1;

	/**
	 * An array of all the '<em><b>Xml Multitenant Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final XmlMultitenantType[] VALUES_ARRAY =
		new XmlMultitenantType[]
		{
			SINGLE_TABLE,
			TABLE_PER_TENANT,
		};

	/**
	 * A public read-only list of all the '<em><b>Xml Multitenant Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<XmlMultitenantType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Xml Multitenant Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlMultitenantType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			XmlMultitenantType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Xml Multitenant Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlMultitenantType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			XmlMultitenantType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Xml Multitenant Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XmlMultitenantType get(int value)
	{
		switch (value)
		{
			case SINGLE_TABLE_VALUE: return SINGLE_TABLE;
			case TABLE_PER_TENANT_VALUE: return TABLE_PER_TENANT;
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
	private XmlMultitenantType(int value, String name, String literal)
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
	
} //XmlMultitenantType
