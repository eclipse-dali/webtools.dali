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

import java.lang.annotation.Annotation;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.persistence.jpa.jpql.spi.IConstructor;
import org.eclipse.persistence.jpa.jpql.spi.IType;
import org.eclipse.persistence.jpa.jpql.spi.ITypeDeclaration;

/**
 * The concrete implementation of {@link IType} that is wrapping the type name only.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
final class SimpleType implements IJpaType {

	/**
	 * Caches the type hierarchy of the {@link IType} in order to prevent rebuilding it each time.
	 */
	private ITypeDeclaration typeDeclaration;

	/**
	 * The fully qualified name of the Java type.
	 */
	private final String typeName;

	/**
	 * The external form of a type repository.
	 */
	private final JpaTypeRepository typeRepository;

	/**
	 * Creates a new <code>SimpleType</code>.
	 *
	 * @param typeName The fully qualified name of the Java type
	 */
	SimpleType(JpaTypeRepository typeRepository, String typeName) {
		super();
		this.typeRepository = typeRepository;
		this.typeName       = typeName;
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<IConstructor> constructors() {
		return EmptyIterable.instance();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(IType type) {
		return typeName.equals(type.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getEnumConstants() {
		return StringTools.EMPTY_STRING_ARRAY;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return typeName;
	}

	/**
	 * {@inheritDoc}
	 */
	public ITypeDeclaration getTypeDeclaration() {
		if (typeDeclaration == null) {
			typeDeclaration = new JpaTypeDeclaration(this, new ITypeDeclaration[0]);
		}
		return typeDeclaration;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAssignableTo(IType type) {
		return typeRepository.equals(type.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEnum() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isResolvable() {
		return false;
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