/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>EXml Access Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlAccessType()
 * @model
 * @generated
 */
public enum EXmlAccessType implements Enumerator
{
	/**
	 * The '<em><b>FIELD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIELD_VALUE
	 * @generated
	 * @ordered
	 */
	FIELD(0, "FIELD", "FIELD"),

	/**
	 * The '<em><b>NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(1, "NONE", "NONE"),

	/**
	 * The '<em><b>PROPERTY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY(2, "PROPERTY", "PROPERTY"),

	/**
	 * The '<em><b>PUBLIC MEMBER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PUBLIC_MEMBER_VALUE
	 * @generated
	 * @ordered
	 */
	PUBLIC_MEMBER(3, "PUBLIC_MEMBER", "PUBLIC_MEMBER");

	/**
	 * The '<em><b>FIELD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FIELD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FIELD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FIELD_VALUE = 0;

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
	public static final int NONE_VALUE = 1;

	/**
	 * The '<em><b>PROPERTY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_VALUE = 2;

	/**
	 * The '<em><b>PUBLIC MEMBER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PUBLIC MEMBER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PUBLIC_MEMBER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PUBLIC_MEMBER_VALUE = 3;

	/**
	 * An array of all the '<em><b>EXml Access Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EXmlAccessType[] VALUES_ARRAY =
		new EXmlAccessType[]
		{
			FIELD,
			NONE,
			PROPERTY,
			PUBLIC_MEMBER,
		};

	/**
	 * A public read-only list of all the '<em><b>EXml Access Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EXmlAccessType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>EXml Access Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EXmlAccessType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			EXmlAccessType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EXml Access Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EXmlAccessType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			EXmlAccessType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EXml Access Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EXmlAccessType get(int value)
	{
		switch (value)
		{
			case FIELD_VALUE: return FIELD;
			case NONE_VALUE: return NONE;
			case PROPERTY_VALUE: return PROPERTY;
			case PUBLIC_MEMBER_VALUE: return PUBLIC_MEMBER;
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
	private EXmlAccessType(int value, String name, String literal)
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
	
} //EXmlAccessType
