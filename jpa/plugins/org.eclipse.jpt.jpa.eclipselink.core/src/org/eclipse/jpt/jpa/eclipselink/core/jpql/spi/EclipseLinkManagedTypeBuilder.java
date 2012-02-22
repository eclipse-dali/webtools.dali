/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkPersistentType;
import org.eclipse.persistence.jpa.jpql.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.spi.IMappedSuperclass;
import org.eclipse.persistence.jpa.jpql.spi.IMappingBuilder;

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

		if (persistentType instanceof OrmEclipseLinkPersistentType) {
			OrmEclipseLinkPersistentType ormPersistentType = (OrmEclipseLinkPersistentType) persistentType;
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

		if (persistentType instanceof OrmEclipseLinkPersistentType) {
			OrmEclipseLinkPersistentType ormPersistentType = (OrmEclipseLinkPersistentType) persistentType;
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

		if (persistentType instanceof OrmEclipseLinkPersistentType) {
			OrmEclipseLinkPersistentType ormPersistentType = (OrmEclipseLinkPersistentType) persistentType;
			if (ormPersistentType.isDynamic()) {
				return new EclipseLinkDynamicMappedSuperclass(provider, mappedSuperclass);
			}
		}

		return mappedSuperclass;
	}
}