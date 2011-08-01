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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.persistence.jpa.jpql.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.spi.IManagedTypeVisitor;
import org.eclipse.persistence.jpa.jpql.spi.IQuery;

/**
 * The concrete implementation of {@link IEntity} that is wrapping the design-time representation
 * of a JPA entity.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
abstract class JpaEntity extends JpaManagedType
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
	 */
	JpaEntity(JpaManagedTypeProvider provider, Entity entity) {
		super(provider, entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void accept(IManagedTypeVisitor visitor) {
		visitor.visit(this);
	}

	final IQuery buildQuery(JpaManagedTypeProvider provider, NamedQuery namedQuery) {
		return new JpaQuery(provider, namedQuery);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	Entity getManagedType() {
		return (Entity) super.getManagedType();
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getName() {
		return getManagedType().getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public IQuery getNamedQuery(String queryName) {
		initializeQueries();
		return queries.get(queryName);
	}

	private void initializeQueries() {
		if (queries == null) {
			queries = new HashMap<String, IQuery>();
			initializeQueries(queries);
		}
	}

	void initializeQueries(Map<String, IQuery> queries) {
		JpaManagedTypeProvider provider = getProvider();
		for (NamedQuery namedQuery : getNamedQueries()) {
			queries.put(namedQuery.getName(), buildQuery(provider, namedQuery));
		}
	}

	private ListIterable<? extends NamedQuery> getNamedQueries() {
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