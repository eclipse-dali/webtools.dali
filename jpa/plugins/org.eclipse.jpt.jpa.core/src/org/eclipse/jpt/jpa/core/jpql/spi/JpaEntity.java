/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.persistence.jpa.jpql.tools.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedTypeVisitor;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMapping;
import org.eclipse.persistence.jpa.jpql.tools.spi.IMappingBuilder;
import org.eclipse.persistence.jpa.jpql.tools.spi.IQuery;

/**
 * The concrete implementation of {@link IEntity} that is wrapping the design-time representation
 * of a JPA entity.
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
public class JpaEntity extends JpaManagedType
                       implements IEntity {

	/**
	 * The cached used to quickly retrieve any queries that have been cached.
	 */
	private Map<String, IQuery> queries;

	/**
	 * Creates a new <code>JpaEntity</code>.
	 *
	 * @param provider The provider of JPA managed types
	 * @param entity The design-time model object wrapped by this class
	 * @param mappingBuilder The builder that is responsible to create the {@link IMapping} wrapping
	 * a persistent attribute or property
	 */
	public JpaEntity(JpaManagedTypeProvider provider,
	                 Entity entity,
	                 IMappingBuilder<AttributeMapping> mappingBuilder) {

		super(provider, entity, mappingBuilder);
	}

	/**
	 * {@inheritDoc}
	 */
	public void accept(IManagedTypeVisitor visitor) {
		visitor.visit(this);
	}

	protected IQuery buildQuery(JpaManagedTypeProvider provider, NamedQuery namedQuery) {
		return new JpaQuery(provider, namedQuery);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Entity getManagedType() {
		return (Entity) super.getManagedType();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return getManagedType().getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public IQuery getNamedQuery(String queryName) {
		initializeQueries();
		return queries.get(queryName);
	}

	/**
	 * Initializes the map JPQL queries if it has not been been initialized yet.
	 */
	protected void initializeQueries() {
		if (queries == null) {
			queries = new HashMap<String, IQuery>();
			initializeQueries(queries);
		}
	}

	protected void initializeQueries(Map<String, IQuery> queries) {
		JpaManagedTypeProvider provider = getProvider();
		for (NamedQuery namedQuery : getNamedQueries()) {
			queries.put(namedQuery.getName(), buildQuery(provider, namedQuery));
		}
	}

	protected ListIterable<? extends NamedQuery> getNamedQueries() {
		return getManagedType().getQueryContainer().getNamedQueries();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getName();
	}
}