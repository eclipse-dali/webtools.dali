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

import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.persistence.jpa.jpql.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeVisitor;

/**
 * The concrete implementation of {@link IEmbeddable} that is wrapping the design-time
 * representation of a JPA embeddable.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
final class JpaEmbeddable extends JpaManagedType
                          implements IEmbeddable {

	/**
	 * Creates a new <code>JpaEmbeddable</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param embeddable The design-time model object wrapped by this class
	 */
	JpaEmbeddable(JpaManagedTypeProvider provider, Embeddable embeddable) {
		super(provider, embeddable);
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
	Embeddable getManagedType() {
		return (Embeddable) super.getManagedType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getType().getName();
	}
}