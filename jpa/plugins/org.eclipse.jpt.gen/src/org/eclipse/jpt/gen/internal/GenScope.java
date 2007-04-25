/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

class GenScope {
	private final Map<Table, GenTable> genTables = new HashMap<Table, GenTable>();

	private IProgressMonitor progressMonitor;
	
	// ********** construction/initialization **********

	GenScope(Collection<Table> tables, EntityGenerator.Config entityConfig, IProgressMonitor progressMonitor) {
		super();
		this.initialize(tables, entityConfig, progressMonitor);
	}

	private void checkCanceled() {
		if (this.progressMonitor.isCanceled()) {
			throw new OperationCanceledException();
		}		
	}
	
	private void initialize(Collection<Table> tables, EntityGenerator.Config entityConfig, IProgressMonitor monitor) {
		this.progressMonitor = monitor;
		this.buildGenTables(tables, entityConfig);
		checkCanceled();
		this.configureManyToManyRelations();
		checkCanceled();
		this.configureManyToOneRelations();
		checkCanceled();
		this.configureFieldNames();
		checkCanceled();
	}

	private void buildGenTables(Collection<Table> tables, EntityGenerator.Config entityConfig) {
		int size = tables.size();
		// pass around a growing list of the entity names so we can avoid duplicates
		Set<String> entityNames = new HashSet<String>(size);
		for (Table table : tables) {
			this.buildGenTable(table, entityConfig, entityNames);
			this.progressMonitor.worked(40/size);
		}
	}

	private void buildGenTable(Table table, EntityGenerator.Config entityConfig, Collection<String> entityNames) {
		this.genTables.put(table, new GenTable(this, table, entityConfig, entityNames));
	}

	/**
	 * find all the "join" tables
	 */
	private void configureManyToManyRelations() {
		int tablesSize = CollectionTools.size(this.tables());

		//first time takes the longest, should we take that into account?
		for (Iterator<GenTable> stream = this.tables(); stream.hasNext(); ) {
			checkCanceled();
			stream.next().configureManyToManyRelations();
			this.progressMonitor.worked(730/tablesSize);
		}
		// revert any "join" table that is referenced by another table back to an "entity" table
		Set<GenTable> referencedTables = this.buildReferencedTables();
		tablesSize = CollectionTools.size(this.joinTables());
		for (Iterator<GenTable> stream = this.joinTables(); stream.hasNext(); ) {
			GenTable joinGenTable = stream.next();
			if (referencedTables.contains(joinGenTable)) {
				joinGenTable.clearJoinTableRelation();
			}
			this.progressMonitor.worked(40/tablesSize);
		}
	}

	/**
	 * find all the many-to-one and one-to-many relations
	 */
	private void configureManyToOneRelations() {
		int tablesSize = CollectionTools.size(this.entityTables());
		for (Iterator<GenTable> stream = this.entityTables(); stream.hasNext(); ) {
			stream.next().configureManyToOneRelations();
			this.progressMonitor.worked(50/tablesSize);
		}
	}

	/**
	 * determine all the Java field names up-front because we will
	 * need them for things like 'mappedBy' annotation elements
	 */
	private void configureFieldNames() {
		int tablesSize = CollectionTools.size(this.entityTables());
		for (Iterator<GenTable> stream = this.entityTables(); stream.hasNext(); ) {
			stream.next().configureFieldNames();
			this.progressMonitor.worked(50/tablesSize);
		}
	}


	// ********** package API **********

	/**
	 * return only the gen tables that are suitable for generating
	 * entities (i.e. exclude the "join" tables)
	 */
	Iterator<GenTable> entityTables() {
		return new FilteringIterator<GenTable>(this.tables()) {
			@Override
			protected boolean accept(Object next) {
				return ! ((GenTable) next).isJoinTable();
			}
		};
	}

	int numEntityTables() {
		return CollectionTools.size(entityTables());
	}
	
	/**
	 * return the gen table corresponding to the specified table
	 */
	GenTable genTable(Table table) {
		return this.genTables.get(table);
	}


	// ********** internal API **********

	private Iterator<GenTable> tables() {
		return this.genTables.values().iterator();
	}

	/**
	 * return only the "join" gen tables
	 */
	private Iterator<GenTable> joinTables() {
		return new FilteringIterator<GenTable>(this.tables()) {
			@Override
			protected boolean accept(Object next) {
				return ((GenTable) next).isJoinTable();
			}
		};
	}

	/**
	 * build a set of the tables that are referenced by other tables
	 * in the scope
	 */
	private Set<GenTable> buildReferencedTables() {
		int size = CollectionTools.size(this.tables());
		Set<GenTable> referencedTables = new HashSet<GenTable>(this.genTables.size());
		for (Iterator<GenTable> stream = this.tables(); stream.hasNext(); ) {
			stream.next().addReferencedTablesTo(referencedTables);
			this.progressMonitor.worked(20/size);
		}
		return referencedTables;
	}

}
