/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpql;

import java.util.Map;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.persistence.jpa.jpql.spi.IQuery;

/**
 * The concrete implementation of {@link IEntity} that is wrapping the design-time representation
 * of a JPA entity defined in an ORM configuration.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
final class JpaOrmEntity extends JpaEntity {

	/**
	 * Creates a new <code>JpaOrmEntity</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param entity The design-time model object wrapped by this class
	 */
	JpaOrmEntity(JpaMappingFile provider, Entity entity) {
		super(provider, entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	void initializeQueries(Map<String, IQuery> queries) {
		super.initializeQueries(queries);

		JpaManagedTypeProvider provider = getProvider();
		OrmPersistentType type = (OrmPersistentType) getManagedType().getPersistentType();
		JavaTypeMapping mapping = type.getJavaPersistentType().getMapping();

		if (mapping instanceof Entity) {
			Entity entity = (Entity) mapping;
			for (NamedQuery namedQuery : entity.getQueryContainer().getNamedQueries()) {
				queries.put(namedQuery.getName(), buildQuery(provider, namedQuery));
			}
		}
	}
}