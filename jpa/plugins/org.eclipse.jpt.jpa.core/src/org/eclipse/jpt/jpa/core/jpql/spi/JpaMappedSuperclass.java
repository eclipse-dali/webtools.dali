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

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeVisitor;
import org.eclipse.persistence.jpa.jpql.spi.IMappedSuperclass;
import org.eclipse.persistence.jpa.jpql.spi.IMappingBuilder;

/**
 * The concrete implementation of {@link IMappedSuperclass} that is wrapping the design-time
 * representation of a JPA mapped superclass.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.2
 * @since 3.0
 * @author Pascal Filion
 */
public class JpaMappedSuperclass extends JpaManagedType
                                 implements IMappedSuperclass {

	/**
	 * Creates a new <code>JpaMappedSuperclass</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param mappedSuperclass The design-time model object wrapped by this class
	 * @param mappingBuilder The builder that is responsible to create the {@link IMapping} wrapping
	 * a persistent attribute or property
	 */
	public JpaMappedSuperclass(JpaManagedTypeProvider provider,
	                           MappedSuperclass mappedSuperclass,
	                           IMappingBuilder<AttributeMapping> mappingBuilder) {

		super(provider, mappedSuperclass, mappingBuilder);
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
	public MappedSuperclass getManagedType() {
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