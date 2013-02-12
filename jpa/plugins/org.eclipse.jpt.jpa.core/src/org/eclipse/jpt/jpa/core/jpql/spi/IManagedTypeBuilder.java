/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMappedSuperclass;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMappingBuilder;

/**
 * This builder is responsible to create the concrete instance of a {@link org.eclipse.persistence.
 * jpa.jpql.spi.IManagedType IManagedType}
 *
 * @version 3.2
 * @since 3.2
 * @author Pascal Filion
 */
public interface IManagedTypeBuilder {

	/**
	 * Creates a new concrete instance of an {@link IEmbeddable}.
	 *
	 * @param provider The provider of JPA managed types
	 * @param mappedClass The design-time representation of an embeddable
	 * @param mappingBuilder The builder that is responsible to create the {@link IMapping} wrapping
	 * a persistent attribute or property
	 * @return A new instance of {@link IEmbeddable}
	 */
	IEmbeddable buildEmbeddable(JpaManagedTypeProvider provider,
	                            Embeddable mappedClass,
	                            IMappingBuilder<AttributeMapping> mappingBuilder);

	/**
	 * Creates a new concrete instance of an {@link IEntity}.
	 *
	 * @param provider The provider of JPA managed types
	 * @param mappedClass The design-time representation of an entity
	 * @param mappingBuilder The builder that is responsible to create the {@link IMapping} wrapping
	 * a persistent attribute or property
	 * @return A new instance of {@link IEntity}
	 */
	IEntity buildEntity(JpaManagedTypeProvider provider,
	                    Entity mappedClass,
	                    IMappingBuilder<AttributeMapping> mappingBuilder);

	/**
	 * Creates a new concrete instance of a {@link IMappedSuperclass}.
	 *
	 * @param provider The provider of JPA managed types
	 * @param mappedClass The design-time representation of a mapped superclass
	 * @param mappingBuilder The builder that is responsible to create the {@link IMapping} wrapping
	 * a persistent attribute or property
	 * @return A new instance of {@link IMappedSuperclass}
	 */
	IMappedSuperclass buildMappedSuperclass(JpaManagedTypeProvider provider,
	                                        MappedSuperclass mappedClass,
	                                        IMappingBuilder<AttributeMapping> mappingBuilder);
}