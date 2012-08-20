/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>EXml Virtual Access Methods Schema</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlVirtualAccessMethodsSchema()
 * @model
 * @generated
 */
public enum EXmlVirtualAccessMethodsSchema implements Enumerator
{
	/**
	 * The '<em><b>NODES</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NODES_VALUE
	 * @generated
	 * @ordered
	 */
	NODES(0, "NODES", "NODES"),

	/**
	 * The '<em><b>ANY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ANY_VALUE
	 * @generated
	 * @ordered
	 */
	ANY(1, "ANY", "ANY");

	/**
	 * The '<em><b>NODES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NODES</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NODES
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NODES_VALUE = 0;

	/**
	 * The '<em><b>ANY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ANY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ANY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ANY_VALUE = 1;

	/**
	 * An array of all the '<em><b>EXml Virtual Access Methods Schema</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EXmlVirtualAccessMethodsSchema[] VALUES_ARRAY =
		new EXmlVirtualAccessMethodsSchema[]
		{
			NODES,
			ANY,
		};

	/**
	 * A public read-only list of all the '<em><b>EXml Virtual Access Methods Schema</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EXmlVirtualAccessMethodsSchema> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>EXml Virtual Access Methods Schema</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EXmlVirtualAccessMethodsSchema get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			EXmlVirtualAccessMethodsSchema result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EXml Virtual Access Methods Schema</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EXmlVirtualAccessMethodsSchema getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			EXmlVirtualAccessMethodsSchema result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EXml Virtual Access Methods Schema</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EXmlVirtualAccessMethodsSchema get(int value)
	{
		switch (value)
		{
			case NODES_VALUE: return NODES;
			case ANY_VALUE: return ANY;
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
	private EXmlVirtualAccessMethodsSchema(int value, String name, String literal)
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
	
} //EXmlVirtualAccessMethodsSchema
