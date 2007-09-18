/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.content.java.IDefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * 
 */
public class JavaBasicProvider
	implements IDefaultJavaAttributeMappingProvider
{

	// singleton
	private static final JavaBasicProvider INSTANCE = new JavaBasicProvider();

	/**
	 * Return the singleton.
	 */
	public static IDefaultJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaBasicProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public boolean defaultApplies(Attribute attribute, DefaultsContext defaultsContext) {
		CompilationUnit astRoot = defaultsContext.astRoot();
		return typeIsBasic(attribute.typeBinding(astRoot), astRoot.getAST());
	}

	public IJavaAttributeMapping buildMapping(Attribute attribute, IJpaFactory factory) {
		return factory.createJavaBasic(attribute);
	}

	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return JavaBasic.DECLARATION_ANNOTATION_ADAPTER;
	}

	// ********** static methods **********

	/**
	 * From the JPA spec, when the basic mapping applies:
	 * If the type of the attribute (field or property) is one of the following
	 * it must be mapped as @Basic:
	 *     primitive types
	 *     byte[]
	 *     Byte[]
	 *     char[]
	 *     Character[]
	 *     primitive wrappers
	 *     java.lang.String
	 *     java.math.BigInteger
	 *     java.math.BigDecimal
	 *     java.util.Date
	 *     java.util.Calendar
	 *     java.sql.Date
	 *     java.sql.Time
	 *     java.sql.Timestamp
	 *     enums
	 *     any other type that implements java.io.Serializable
	 */
	public static boolean typeIsBasic(ITypeBinding typeBinding, AST ast) {
		if (typeBinding == null) {
			return false; // type not found
		}
		if (typeBinding.isPrimitive()) {
			return true;
		}
		if (typeBinding.isArray()) {
			if (typeBinding.getDimensions() > 1) {
				return false; // multi-dimensional arrays are not supported
			}
			ITypeBinding elementTypeBinding = typeBinding.getElementType();
			if (elementTypeBinding == null) {
				return false;// unable to resolve the type
			}
			return elementTypeIsValid(elementTypeBinding.getQualifiedName());
		}
		String typeName = typeBinding.getQualifiedName();
		if (typeIsPrimitiveWrapper(typeName)) {
			return true;
		}
		if (typeIsOtherSupportedType(typeName)) {
			return true;
		}
		if (typeBinding.isEnum()) {
			return true;
		}
		if (typeImplementsSerializable(typeBinding, ast)) {
			return true;
		}
		return false;
	}

	/**
	 * Return whether the specified type is a valid element type for
	 * a one-dimensional array:
	 *     byte
	 *     char
	 *     java.lang.Byte
	 *     java.lang.Character
	 */
	private static boolean elementTypeIsValid(String elementTypeName) {
		return CollectionTools.contains(VALID_ELEMENT_TYPE_NAMES, elementTypeName);
	}

	private static final String[] VALID_ELEMENT_TYPE_NAMES = {
		byte.class.getName(),
		char.class.getName(),
		java.lang.Byte.class.getName(),
		java.lang.Character.class.getName()
	};

	/**
	 * Return whether the specified type is a primitive wrapper.
	 */
	private static boolean typeIsPrimitiveWrapper(String typeName) {
		return CollectionTools.contains(PRIMITIVE_WRAPPER_TYPE_NAMES, typeName);
	}
	
	private static final String[] PRIMITIVE_WRAPPER_TYPE_NAMES = {
		java.lang.Byte.class.getName(),
		java.lang.Character.class.getName(),
		java.lang.Double.class.getName(),
		java.lang.Float.class.getName(),
		java.lang.Integer.class.getName(),
		java.lang.Long.class.getName(),
		java.lang.Short.class.getName(),
		java.lang.Boolean.class.getName(),
	};

	/**
	 * Return whether the specified type is among the various other types
	 * that default to a Basic mapping.
	 */
	private static boolean typeIsOtherSupportedType(String typeName) {
		return CollectionTools.contains(OTHER_SUPPORTED_TYPE_NAMES, typeName);
	}
	
	private static final String[] OTHER_SUPPORTED_TYPE_NAMES = {
		java.lang.String.class.getName(),
		java.math.BigInteger.class.getName(),
		java.math.BigDecimal.class.getName(),
		java.util.Date.class.getName(),
		java.util.Calendar.class.getName(),
		java.sql.Date.class.getName(),
		java.sql.Time.class.getName(),
		java.sql.Timestamp.class.getName(),
	};
	
	/**
	 * Return whether the specified type implements java.io.Serializable.
	 */
	private static boolean typeImplementsSerializable(ITypeBinding typeBinding, AST ast) {
		ITypeBinding serializableTypeBinding = ast.resolveWellKnownType(SERIALIZABLE_TYPE_NAME);
		return typeBinding.isAssignmentCompatible(serializableTypeBinding);
	}

	private static final String SERIALIZABLE_TYPE_NAME = java.io.Serializable.class.getName();
}
