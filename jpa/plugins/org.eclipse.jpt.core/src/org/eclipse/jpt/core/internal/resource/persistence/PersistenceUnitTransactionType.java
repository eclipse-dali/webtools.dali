/**
 * <copyright>
 * </copyright>
 *
 * $Id: PersistenceUnitTransactionType.java,v 1.1 2007/07/30 20:13:46 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.persistence;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Unit Transaction Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage#getPersistenceUnitTransactionType()
 * @model
 * @generated
 */
public enum PersistenceUnitTransactionType implements Enumerator
{
	/**
	 * The '<em><b>JTA</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JTA_VALUE
	 * @generated
	 * @ordered
	 */
	JTA(0, "JTA", "JTA"),

	/**
	 * The '<em><b>RESOURCE LOCAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESOURCE_LOCAL_VALUE
	 * @generated
	 * @ordered
	 */
	RESOURCE_LOCAL(1, "RESOURCE_LOCAL", "RESOURCE_LOCAL");

	/**
	 * The '<em><b>JTA</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>JTA</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JTA
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int JTA_VALUE = 0;

	/**
	 * The '<em><b>RESOURCE LOCAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RESOURCE LOCAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RESOURCE_LOCAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RESOURCE_LOCAL_VALUE = 1;

	/**
	 * An array of all the '<em><b>Unit Transaction Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PersistenceUnitTransactionType[] VALUES_ARRAY =
		new PersistenceUnitTransactionType[]
		{
			JTA,
			RESOURCE_LOCAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Unit Transaction Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PersistenceUnitTransactionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Unit Transaction Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PersistenceUnitTransactionType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			PersistenceUnitTransactionType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unit Transaction Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PersistenceUnitTransactionType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			PersistenceUnitTransactionType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unit Transaction Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PersistenceUnitTransactionType get(int value)
	{
		switch (value)
		{
			case JTA_VALUE: return JTA;
			case RESOURCE_LOCAL_VALUE: return RESOURCE_LOCAL;
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
	private PersistenceUnitTransactionType(int value, String name, String literal)
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
	
} //PersistenceUnitTransactionType
