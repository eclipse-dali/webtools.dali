/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpql;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.persistence.jpa.jpql.TypeHelper;
import org.eclipse.persistence.jpa.jpql.spi.ITypeDeclaration;
import org.eclipse.persistence.jpa.jpql.spi.ITypeRepository;

/**
 * The concrete implementation of {@link ITypeRepository} that is wrapping the design-time
 * representation of a type repository.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
final class JpaTypeRepository implements ITypeRepository {

	/**
	 * The Java project that gives access the design-time objects representing the Java types.
	 */
	private final IJavaProject javaProject;

	/**
	 * Keeps a map of the primitives for fast access.
	 */
	private Map<String, Class<?>> primitives;

	/**
	 * The cached helper that is using this repository for retrieving {@link IJpaType types}.
	 */
	private TypeHelper typeHelper;

	/**
	 * The types that have been cached for faster access.
	 */
	private final Map<String, IJpaType> types;

	/**
	 * The {@link IJpaType} that represents a unresolvable or simply an unknown type, which is created
	 * when {@link #getType(String)} is invoked with {@link IJpaType#UNRESOLVABLE_TYPE}.
	 */
	private IJpaType unresolvableType;

	/**
	 * Creates a new <code>JpaTypeRepository</code>.
	 *
	 * @param javaProject The Java project that gives access the design-time objects representing
	 * the Java types
	 */
	JpaTypeRepository(IJavaProject javaProject) {
		super();
		this.javaProject = javaProject;
		this.types       = new HashMap<String, IJpaType>();
	}

	private String buildInnerTypeName(String typeName, int index) {
		StringBuilder sb = new StringBuilder();
		sb.append(typeName.substring(0, index));
		sb.append("$");
		sb.append(typeName.substring(index + 1, typeName.length()));
		return sb.toString();
	}

	private Map<String, Class<?>> buildPrimitives() {
		Map<String, Class<?>> primitives = new HashMap<String, Class<?>>();
		primitives.put(Byte     .TYPE.getName(), Byte     .TYPE);
		primitives.put(Short    .TYPE.getName(), Short    .TYPE);
		primitives.put(Character.TYPE.getName(), Character.TYPE);
		primitives.put(Integer  .TYPE.getName(), Integer  .TYPE);
		primitives.put(Long     .TYPE.getName(), Long     .TYPE);
		primitives.put(Float    .TYPE.getName(), Float    .TYPE);
		primitives.put(Double   .TYPE.getName(), Double   .TYPE);
		primitives.put(Boolean  .TYPE.getName(), Boolean  .TYPE);
		return primitives;
	}

	private IJpaType buildType(Class<?> javaType) {
		JavaType jpaType = new JavaType(this, javaType);
		types.put(jpaType.getName(), jpaType);
		return jpaType;
	}

	private IJpaType buildType(IType type) {
		JpaType jpaType = new JpaType(this, type);
		types.put(jpaType.getName(), jpaType);
		return jpaType;
	}

	private IJpaType buildType(String typeName) {
		IJpaType jpaType = new SimpleType(this, typeName);
		types.put(typeName, jpaType);
		return jpaType;
	}

	private String convertToJavaArrayType(String typeName) {

		int index = typeName.indexOf("[]");
		int dimensionality = (typeName.length() - index) / 2;
		StringBuilder sb = new StringBuilder();

		while (dimensionality-- > 0) {
			sb.append("[");
		}

		String type = typeName.substring(0, index);
		Class<?> primitive = primitives.get(type);

		if (primitive != null) {
			if (primitive == Byte.TYPE) {
				sb.append("B");
			}
			else if (primitive == Character.TYPE) {
				sb.append("C");
			}
			else if (primitive == Short.TYPE) {
				sb.append("S");
			}
			else if (primitive == Integer.TYPE) {
				sb.append("I");
			}
			else if (primitive == Long.TYPE) {
				sb.append("L");
			}
			else if (primitive == Float.TYPE) {
				sb.append("F");
			}
			else if (primitive == Double.TYPE) {
				sb.append("D");
			}
			else if (primitive == Boolean.TYPE) {
				sb.append("Z");
			}
		}
		else {
			sb.append(type);
			sb.append(";");
		}

		return sb.toString();
	}

	private Class<?> findPrimitive(String typeName) {
		if (primitives == null) {
			primitives = buildPrimitives();
		}
		return primitives.get(typeName);
	}

	/**
	 * Retrieves the design-time Java type for the given type name, which has to be the fully
	 * qualified type name.
	 *
	 * @param typeName The fully qualified type name
	 * @return The design-time Java type if it could be retrieved; <code>null</code> otherwise
	 */
	IType findType(String typeName) {
		try {
			return javaProject.findType(typeName);
		}
		catch (JavaModelException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public IJpaType getEnumType(String enumTypeName) {

		// Get the position of the last dot so the enum constant can be removed
		int lastDotIndex = enumTypeName.lastIndexOf(".");

		if (lastDotIndex == -1) {
			return null;
		}

		// Retrieve the fully qualified enum type name
		String typeName = enumTypeName.substring(0, lastDotIndex);

		// Attempt to load the enum type
		IType type = findType(typeName);
		IJpaType jpaType;

		if (type != null) {
			jpaType = buildType(type);
		}
		else {
			jpaType = loadInnerType(typeName);
		}

		return (jpaType != null) && jpaType.isEnum() ? jpaType : null;
	}

	/**
	 * Returns the Java project, which gives access to the class path.
	 *
	 * @return The Java project, which gives access to the class path
	 */
	IJavaProject getJavaProject() {
		return javaProject;
	}

	/**
	 * {@inheritDoc}
	 */
	public IJpaType getType(Class<?> javaClass) {
		IJpaType type = types.get(javaClass.getName());
		if (type == null) {
			type = buildType(javaClass);
		}
		return type;
	}

	/**
	 * Retrieves the {@link IType} for the given {@link IResource}.
	 *
	 * @param resource The workspace location of the {@link IType} to retrieve
	 * @return The design-time representation of a Java type
	 */
	IType getType(IResource resource) {
		try {
			return (IType) javaProject.findElement((IPath) resource);
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public IJpaType getType(String typeName) {

		if (IJpaType.UNRESOLVABLE_TYPE == typeName) {
			return unresolvableType();
		}

		if (typeName.endsWith("[]")) {
			return loadArrayType(typeName);
		}

		if (typeName.charAt(0) == '[') {
			return loadJavaArrayType(typeName);
		}

		return loadTypeImp(typeName);
	}

	/**
	 * {@inheritDoc}
	 */
	public TypeHelper getTypeHelper() {
		if (typeHelper == null) {
			typeHelper = new TypeHelper(this);
		}
		return typeHelper;
	}

	private IJpaType loadArrayType(String typeName) {

		IJpaType type = types.get(typeName);

		if (type == null) {
			try {
				// Try to see if the type is a JDK class, otherwise, just use the type name
				// since IJpaType doesn't support array types
				String javaTypeName = convertToJavaArrayType(typeName);
				type = buildType(Class.forName(javaTypeName));
			}
			catch (Exception e) {

				int index = typeName.indexOf("[]");
				int dimensionality = (typeName.length() - index) / 2;

				type = getType(typeName.substring(0, index));

				type.setTypeDeclaration(
					new JpaTypeDeclaration(type, new ITypeDeclaration[0], dimensionality)
				);
			}

			types.put(typeName, type);
		}

		return type;
	}

	private IJpaType loadInnerType(String typeName) {

		while (true) {

			int index = typeName.lastIndexOf(".");

			if (index == -1) {
				return null;
			}

			typeName = buildInnerTypeName(typeName, index);
			IJpaType cachedType = types.get(typeName);

			if (cachedType != null) {
				return cachedType;
			}

			// Attempt to load the Java type
			IType type = findType(typeName);

			// A Java type exists, return it
			if (type != null) {
				return buildType(type);
			}
		}
	}

	private IJpaType loadJavaArrayType(String typeName) {

		IJpaType type = types.get(typeName);

		if (type == null) {
			try {
				// Try to see if the type is a JDK class, otherwise, just use the type name
				// since IJpaType doesn't support array types
				type = buildType(Class.forName(typeName));
			}
			catch (Exception e) {

				int index = typeName.lastIndexOf("[");
				int dimensionality = index / 2;

				type = getType(typeName.substring(index, typeName.endsWith(";") ? typeName.length() - 1 : typeName.length()));

				type.setTypeDeclaration(
					new JpaTypeDeclaration(type, new ITypeDeclaration[0], dimensionality)
				);
			}

			types.put(typeName, type);
		}

		return type;
	}

	private IJpaType loadTypeImp(String typeName) {

		IJpaType type = types.get(typeName);

		// The type was already cached, simply return it
		if (type != null) {
			return type;
		}

		// First check for primitive, they don't have a corresponding IJpaType
		Class<?> primitive = findPrimitive(typeName);

		if (primitive != null) {
			return buildType(primitive);
		}

		// Attempt to load the Java type
		IType iType = findType(typeName);

		// A Java type exists, return it
		if (iType != null) {
			return buildType(iType);
		}

		// Now try with a possible inner enum type
		type = loadInnerType(typeName);

		// No Java type exists, create a "null" IJpaType
		if (type == null) {
			type = buildType(typeName);
		}

		return type;
	}

	private IJpaType unresolvableType() {
		if (unresolvableType == null) {
			unresolvableType = new SimpleType(this, IJpaType.UNRESOLVABLE_TYPE);
		}
		return unresolvableType;
	}
}