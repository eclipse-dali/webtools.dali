/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpql.spi;

import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedType;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedTypeProvider;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedTypeVisitor;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.tools.spi.IType;

/**
 * The concrete implementation of {@link IManagedType} that is wrapping the design-time
 * representation a "null" managed type.
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
public class JpaNullManagedType implements IManagedType {

	/**
	 * The provider of JPA managed types.
	 */
	private final JpaManagedTypeProvider provider;

	/**
	 * The cached {@link IType} of this "null" managed type.
	 */
	private IType type;

	/**
	 * The design-time model object wrapped by this class.
	 */
	private final TypeMapping typeMapping;

	/**
	 * Creates a new <code>JpaNullManagedType</code>.
	 *
	 * @param managedType The provider of JPA managed types
	 * @param typeMapping The design-time model object wrapped by this class
	 */
	public JpaNullManagedType(JpaManagedTypeProvider provider, TypeMapping typeMapping) {
		super();
		this.provider    = provider;
		this.typeMapping = typeMapping;
	}

	/**
	 * {@inheritDoc}
	 */
	public void accept(IManagedTypeVisitor visitor) {
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(IManagedType managedType) {
		return getType().getName().compareTo(managedType.getType().getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public IMapping getMappingNamed(String name) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public IManagedTypeProvider getProvider() {
		return provider;
	}

	/**
	 * {@inheritDoc}
	 */
	public IType getType() {
		if (type == null) {
			type = provider.getTypeRepository().getType(typeMapping.getPersistentType().getName());
		}
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<IMapping> mappings() {
		return EmptyIterable.instance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getType().getName();
	}
}