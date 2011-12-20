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
package org.eclipse.jpt.jpa.core.jpql.spi;

import org.eclipse.persistence.jpa.jpql.spi.IType;
import org.eclipse.persistence.jpa.jpql.spi.ITypeDeclaration;

/**
 * The concrete implementation of {@link ITypeDeclaration} that is wrapping the design-time
 * representation of the declaration description of a type.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.1
 * @since 3.0
 * @author Pascal Filion
 */
public class JpaTypeDeclaration implements ITypeDeclaration {

	/**
	 * The dimensionality of the array or 0 if the type is not an array.
	 */
	private int dimensionality;

	/**
	 * The generics of the given type or an empty list if the type is not parameterized.
	 */
	private final ITypeDeclaration[] genericTypes;

	/**
	 * The external form of the Java type.
	 */
	private final IType type;

	/**
	 * Creates a new <code>JpaTypeDeclaration</code>.
	 *
	 * @param type The external form of the Java type
	 * @param genericTypes The generics of the given type or an empty list if the type is not
	 * parameterized
	 */
	public JpaTypeDeclaration(IType type, ITypeDeclaration[] genericTypes) {
		this(type, genericTypes, 0);
	}

	/**
	 * Creates a new <code>JpaTypeDeclaration</code>.
	 *
	 * @param type The external form of the Java type
	 * @param genericTypes The generics of the given type or an empty list if the type is not
	 * parameterized
	 * @param dimensionality The dimensionality of the array or 0 if it's not an array
	 */
	public JpaTypeDeclaration(IType type, ITypeDeclaration[] genericTypes, int dimensionality) {
		super();
		this.type           = type;
		this.genericTypes   = genericTypes;
		this.dimensionality = dimensionality;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getDimensionality() {
		return dimensionality;
	}

	/**
	 * {@inheritDoc}
	 */
	public IType getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	public ITypeDeclaration[] getTypeParameters() {
		return genericTypes;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isArray() {
		return dimensionality > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return type.getName();
	}
}