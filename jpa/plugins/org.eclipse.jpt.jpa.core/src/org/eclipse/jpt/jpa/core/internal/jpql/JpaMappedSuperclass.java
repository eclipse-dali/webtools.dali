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

import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeVisitor;
import org.eclipse.persistence.jpa.jpql.spi.IMappedSuperclass;

/**
 * The concrete implementation of {@link IMappedSuperclass} that is wrapping the design-time
 * representation of a JPA mapped superclass.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
final class JpaMappedSuperclass extends JpaManagedType
                                implements IMappedSuperclass {

	/**
	 * Creates a new <code>JpaMappedSuperclass</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param mappedSuperclass The design-time model object wrapped by this class
	 */
	JpaMappedSuperclass(JpaManagedTypeProvider provider, MappedSuperclass mappedSuperclass) {
		super(provider, mappedSuperclass);
	}

	/**
	 * {@inheritDoc}
	 */
	public void accept(IManagedTypeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	MappedSuperclass getManagedType() {
		return (MappedSuperclass) super.getManagedType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getType().getName();
	}
}