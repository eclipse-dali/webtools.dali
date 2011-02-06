/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2;

import java.util.Iterator;

import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.db.DatabaseIdentifierAdapter;

/**
 * Conversions are determined by the <code>delimited-identifiers</code>
 * flag in <code>orm.xml</code>.
 * <p>
 * Assume we are in a JPA 2.0-compatible project.
 */
public class GenericJpaDatabaseIdentifierAdapter
	implements DatabaseIdentifierAdapter
{
	private final JpaDataSource dataSource;

	public GenericJpaDatabaseIdentifierAdapter(JpaDataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	/**
	 * If the flag is set, "identifiers" are treated as "names".
	 */
	public String convertIdentifierToName(String identifier, DefaultCallback defaultCallback) {
		return this.getDefaultDelimitedIdentifiers() ? identifier : defaultCallback.convertIdentifierToName(identifier);
	}

	/**
	 * If the flag is set, "names" are treated as "identifiers".
	 */
	public String convertNameToIdentifier(String name, DefaultCallback defaultCallback) {
		return this.getDefaultDelimitedIdentifiers() ? name : defaultCallback.convertNameToIdentifier(name);
	}

	protected boolean getDefaultDelimitedIdentifiers() {
		PersistenceUnit2_0 pu = this.getPersistenceUnit();
		return (pu != null) && pu.getDefaultDelimitedIdentifiers();
	}

	protected PersistenceUnit2_0 getPersistenceUnit() {
		Persistence p = this.getPersistence();
		if (p == null) {
			return null;
		}
		Iterator<PersistenceUnit> units = this.getPersistence().persistenceUnits();
		return (PersistenceUnit2_0) (units.hasNext() ? units.next() : null);
	}

	protected Persistence getPersistence() {
		PersistenceXml pxml = this.getPersistenceXml();
		return (pxml == null) ? null : pxml.getPersistence();
	}

	protected PersistenceXml getPersistenceXml() {
		// TODO this null check can be removed if the data source is moved to the persistence unit;
		// the root context node can be null during construction;
		// this shouldn't be a problem since the default-delimiters flag
		// is recalculated during the initial, post-project construction, "update"
		JpaRootContextNode rcn = this.dataSource.getJpaProject().getRootContextNode();
		return (rcn == null) ? null : rcn.getPersistenceXml();
	}

}
