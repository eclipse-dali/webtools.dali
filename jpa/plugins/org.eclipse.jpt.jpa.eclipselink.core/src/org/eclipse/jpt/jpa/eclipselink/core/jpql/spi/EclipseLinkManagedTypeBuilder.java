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
package org.eclipse.jpt.jpa.eclipselink.core.jpql.spi;

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.jpql.spi.IManagedTypeBuilder;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaEmbeddable;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaEntity;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedTypeBuilder;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaManagedTypeProvider;
import org.eclipse.jpt.jpa.core.jpql.spi.JpaMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMappedSuperclass;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMappingBuilder;

/**
 * This implementation of {@link IManagedTypeBuilder} adds support for dynamic managed types.
 *
 * @version 3.2
 * @since 3.2
 * @author Pascal Filion
 */
public class EclipseLinkManagedTypeBuilder implements IManagedTypeBuilder {

	/**
	 * This builder is used to create the default implementation of an {@link org.eclipse.persistence.
	 * jpa.jpql.spi.IManagedType IManagedType} which is only wrapped if the managed type is dynamic.
	 */
	private JpaManagedTypeBuilder builder;

	/**
	 * Creates a new <code>EclipseLinkManagedTypeBuilder</code>.
	 */
	public EclipseLinkManagedTypeBuilder() {
		super();
		builder = new JpaManagedTypeBuilder();
	}

	/**
	 * {@inheritDoc}
	 */
	public IEmbeddable buildEmbeddable(JpaManagedTypeProvider provider,
	                                   Embeddable mappedClass,
	                                   IMappingBuilder<AttributeMapping> mappingBuilder) {

		JpaEmbeddable embeddable = (JpaEmbeddable) builder.buildEmbeddable(
			provider,
			mappedClass,
			mappingBuilder
		);

		PersistentType persistentType = mappedClass.getPersistentType();

		if (persistentType instanceof EclipseLinkOrmPersistentType) {
			EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) persistentType;
			if (ormPersistentType.isDynamic()) {
				return new EclipseLinkDynamicEmbeddable(provider, embeddable);
			}
		}

		return embeddable;
	}

	/**
	 * {@inheritDoc}
	 */
	public IEntity buildEntity(JpaManagedTypeProvider provider,
	                           Entity mappedClass,
	                           IMappingBuilder<AttributeMapping> mappingBuilder) {

		JpaEntity entity = (JpaEntity) builder.buildEntity(provider, mappedClass, mappingBuilder);
		PersistentType persistentType = mappedClass.getPersistentType();

		if (persistentType instanceof EclipseLinkOrmPersistentType) {
			EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) persistentType;
			if (ormPersistentType.isDynamic()) {
				return new EclipseLinkDynamicEntity(provider, entity);
			}
		}

		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	public IMappedSuperclass buildMappedSuperclass(JpaManagedTypeProvider provider,
	                                               MappedSuperclass mappedClass,
	                                               IMappingBuilder<AttributeMapping> mappingBuilder) {

		JpaMappedSuperclass mappedSuperclass = (JpaMappedSuperclass) builder.buildMappedSuperclass(
			provider,
			mappedClass,
			mappingBuilder
		);

		PersistentType persistentType = mappedClass.getPersistentType();

		if (persistentType instanceof EclipseLinkOrmPersistentType) {
			EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) persistentType;
			if (ormPersistentType.isDynamic()) {
				return new EclipseLinkDynamicMappedSuperclass(provider, mappedSuperclass);
			}
		}

		return mappedSuperclass;
	}
}