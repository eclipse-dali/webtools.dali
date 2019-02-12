/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMappedSuperclass;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMappingBuilder;

/**
 * @version 3.2
 * @since 3.2
 * @author Pascal Filion
 */
public class JpaManagedTypeBuilder implements IManagedTypeBuilder {

	/**
	 * {@inheritDoc}
	 */
	public IEmbeddable buildEmbeddable(JpaManagedTypeProvider provider,
	                                   Embeddable mappedClass,
	                                   IMappingBuilder<AttributeMapping> mappingBuilder) {

		return new JpaEmbeddable(provider, mappedClass, mappingBuilder);
	}

	/**
	 * {@inheritDoc}
	 */
	public IEntity buildEntity(JpaManagedTypeProvider provider,
	                           Entity mappedClass,
	                           IMappingBuilder<AttributeMapping> mappingBuilder) {

		return new JpaEntity(provider, mappedClass, mappingBuilder);
	}

	/**
	 * {@inheritDoc}
	 */
	public IMappedSuperclass buildMappedSuperclass(JpaManagedTypeProvider provider,
	                                               MappedSuperclass mappedClass,
	                                               IMappingBuilder<AttributeMapping> mappingBuilder) {

		return new JpaMappedSuperclass(provider, mappedClass, mappingBuilder);
	}
}