/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
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
package org.eclipse.jpt.jpa.core.jpql.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.persistence.jpa.jpql.TypeHelper;
import org.eclipse.persistence.jpa.jpql.spi.IConstructor;
import org.eclipse.persistence.jpa.jpql.spi.IType;
import org.eclipse.persistence.jpa.jpql.spi.ITypeDeclaration;

/**
 * The concrete implementation of {@link IType} that is wrapping a Java type.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 3.0
 * @author Pascal Filion
 */
public class JavaType implements IJpaType {

	/**
	 * The cached {@link IConstructor IConstructors}.
	 */
	private Collection<IConstructor> constructors;

	/**
	 * The list of names for the {@link Enum}'s constants otherwise an empty array.
	 */
	private String[] enumConstants;

	/**
	 * The actual Java type.
	 */
	private Class<?> type;

	/**
	 * The cached {@link ITypeDeclaration} for this {@link IType}.
	 */
	private ITypeDeclaration typeDeclaration;

	/**
	 * The fully qualified name of the Java type.
	 */
	private String typeName;

	/**
	 * The external form of a type repository.
	 */
	private JpaTypeRepository typeRepository;

	/**
	 * Creates a new <code>JavaType</code>.
	 *
	 * @param typeRepository The external form of a type repository
	 * @param type The actual Java type wrapped by this class
	 */
	public JavaType(JpaTypeRepository typeRepository, Class<?> type) {
		super();
		this.type           = type;
		this.typeName       = type.getName();
		this.typeRepository = typeRepository;
	}

	protected IConstructor buildConstructor(Constructor<?> constructor) {
		return new JavaConstructor(this, constructor);
	}

	protected Collection<IConstructor> buildConstructors() {

		Constructor<?>[] javaConstructors = type.getDeclaredConstructors();
		Collection<IConstructor> constructors = new ArrayList<IConstructor>(javaConstructors.length);

		for (Constructor<?> javaConstructor : javaConstructors) {
			constructors.add(buildConstructor(javaConstructor));
		}

		return constructors;
	}

	protected String[] buildEnumConstants() {

		if (!type.isEnum()) {
			return ExpressionTools.EMPTY_STRING_ARRAY;
		}

		Object[] enumConstants = type.getEnumConstants();
		String[] names = new String[enumConstants.length];

		for (int index = enumConstants.length; --index >= 0; ) {
			names[index] = ((Enum<?>) enumConstants[index]).name();
		}

		return names;
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<IConstructor> constructors() {
		if (constructors == null) {
			constructors = buildConstructors();
		}
		return new SnapshotCloneIterable<IConstructor>(constructors);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(IType type) {
		return (this == type) || typeName.equals(type.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		return equals((IType) object);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getEnumConstants() {
		if (enumConstants == null) {
			enumConstants = buildEnumConstants();
		}
		return enumConstants;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return typeName;
	}

	/**
	 * Returns the encapsulated {@link Class}, which is the actual type.
	 *
	 * @return The actual Java type, if <code>null</code> is returned; then the class could not be resolved
	 */
	protected Class<?> getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	public ITypeDeclaration getTypeDeclaration() {
		if (typeDeclaration == null) {
			typeDeclaration = new JavaTypeDeclaration(typeRepository, this, null, (type != null) ? type.isArray() : false);
		}
		return typeDeclaration;
	}

	/**
	 * Returns the type repository for the application.
	 *
	 * @return The repository of {@link IType ITypes}
	 */
	protected JpaTypeRepository getTypeRepository() {
		return typeRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return type.isAnnotationPresent(annotationType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return typeName.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAssignableTo(IType otherType) {

		if (equals(otherType)) {
			return true;
		}

		// Make sure both types are not primitives since isAssignableFrom() does not work.
		// For instance long and Long can't be compared but they are valid for JPQL query
		TypeHelper typeHelper = typeRepository.getTypeHelper();
		IType thisType = typeHelper.convertPrimitive(this);
		otherType = typeHelper.convertPrimitive(otherType);

		// Both are JavaType, even doing convertPrimitive() on this type always returns
		// a JavaType when converting a primitive into its Class object
		if (otherType instanceof JavaType) {
			Class<?> thisClass  = ((JavaType) thisType) .type;
			Class<?> otherClass = ((JavaType) otherType).type;
			return otherClass.isAssignableFrom(thisClass);
		}

		// For JpaType, convert this type into a JpaType and use its isAssignableTo()
		if (otherType instanceof JpaType) {
			org.eclipse.jdt.core.IType jdtType = typeRepository.findType(typeName);

			if (jdtType != null) {
				JpaType jpaType = new JpaType(typeRepository, jdtType);
				return jpaType.isAssignableTo(otherType);
			}

			return false;
		}

		// SimpleType cannot be validated since it's wrapping an unknown type
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEnum() {
		return (type != null) && type.isEnum();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isResolvable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTypeDeclaration(JpaTypeDeclaration typeDeclaration) {
		this.typeDeclaration = typeDeclaration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return typeName;
	}
}