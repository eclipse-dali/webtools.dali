/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.persistence.jpa.jpql.tools.spi.IManagedTypeProvider;
import org.eclipse.persistence.jpa.jpql.tools.spi.IQuery;

/**
 * The concrete implementation of {@link IQuery} that is wrapping the design-time representation
 * of a JPQL query.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.1
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings("nls")
public class JpaQuery implements IQuery {

	/**
	 * The actual JPQL query, which can differ from the one owned by the model object, which happens
	 * when the model is out of sync because it has not been updated yet.
	 */
	private String actualQuery;

	/**
	 *  The provider of managed types.
	 */
	private IManagedTypeProvider provider;

	/**
	 * The model object holding onto the JPQL query.
	 */
	private NamedQuery query;

	/**
	 * Creates a new <code>JpaQuery</code>.
	 *
	 * @param provider The provider of managed types
	 * @param query The model object of the JPQL query
	 */
	public JpaQuery(IManagedTypeProvider provider, NamedQuery query) {
		this(provider, query, query.getQuery());
	}

	/**
	 * Creates a new <code>JpaQuery</code>.
	 *
	 * @param provider The provider of managed types
	 * @param query The model object of the JPQL query
	 * @param actualQuery The actual JPQL query, which can differ from the one owned by the model
	 * object, which happens when the model is out of sync because it has not been updated yet
	 */
	public JpaQuery(IManagedTypeProvider provider, NamedQuery query, String actualQuery) {
		super();
		initialize(provider, query, actualQuery);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpression() {
		return actualQuery;
	}

	/**
	 * {@inheritDoc}
	 */
	public IManagedTypeProvider getProvider() {
		return provider;
	}

	/**
	 * Returns the encapsulated {@link NamedQuery}, which is the actual object.
	 *
	 * @return The design-time representation of a JPQL query
	 */
	protected NamedQuery getQuery() {
		return query;
	}

	protected void initialize(IManagedTypeProvider provider, NamedQuery query, String actualQuery) {

		this.query       = query;
		this.provider    = provider;
		this.actualQuery = actualQuery;

		if (this.actualQuery == null) {
			this.actualQuery = StringTools.EMPTY_STRING;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(", query=");
		sb.append(getExpression());
		return sb.toString();
	}
}