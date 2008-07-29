/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * Build a GenTable for each db.Table passed in.
 * Determine all the relations among the tables in the scope:
 *     many-to-many
 *     many-to-one
 *     one-to-many
 * Make a first pass to determine each entity table's Java attribute names,
 * because we will need them on subsequent passes.
 */
class GenScope {
	private final EntityGenerator.Config entityConfig;
	private final HashMap<Table, GenTable> genTables;


	// ********** construction/initialization **********

	GenScope(EntityGenerator.Config entityConfig, IProgressMonitor progressMonitor) {
		super();
		this.entityConfig = entityConfig;
		this.genTables = new HashMap<Table, GenTable>(entityConfig.tablesSize());
		SubMonitor sm = SubMonitor.convert(progressMonitor, JptGenMessages.GenScope_taskName, 4);

		this.buildGenTables();
		sm.worked(1);
		this.checkCanceled(sm);

		this.buildManyToManyRelations();
		sm.worked(1);
		this.checkCanceled(sm);

		this.buildManyToOneRelations();  // this will also build the corresponding one-to-many relations
		sm.worked(1);
		this.checkCanceled(sm);

		this.buildAttributeNames();
		sm.worked(1);
		this.checkCanceled(sm);
	}

	private void buildGenTables() {
		for (Iterator<Table> stream = entityConfig.tables(); stream.hasNext(); ) {
			Table table = stream.next();
			this.genTables.put(table, new GenTable(this, table));
		}
	}

	/**
	 * find all the "join" tables
	 */
	private void buildManyToManyRelations() {
		for (Iterator<GenTable> stream = this.genTables(); stream.hasNext(); ) {
			stream.next().buildJoinTableRelation();
		}

		// revert any "join" table that is referenced by another table back to an "entity" table
		HashSet<GenTable> referencedGenTables = this.buildReferencedGenTables();
		for (Iterator<GenTable> stream = this.joinGenTables(); stream.hasNext(); ) {
			GenTable joinGenTable = stream.next();
			if (referencedGenTables.contains(joinGenTable)) {
				joinGenTable.clearJoinTableRelation();
			}
		}
	}

	/**
	 * find all the many-to-one and corresponding one-to-many relations
	 */
	private void buildManyToOneRelations() {
		for (Iterator<GenTable> stream = this.entityGenTables(); stream.hasNext(); ) {
			stream.next().buildManyToOneRelations();
		}
	}

	/**
	 * determine all the Java attribute names up-front because we will
	 * need them for things like 'mappedBy' annotation elements
	 */
	private void buildAttributeNames() {
		for (Iterator<GenTable> stream = this.entityGenTables(); stream.hasNext(); ) {
			stream.next().buildAttributeNames();
		}
	}


	// ********** package API **********

	EntityGenerator.Config getEntityConfig() {
		return this.entityConfig;
	}

	/**
	 * return only the gen tables that are suitable for generating
	 * entities (i.e. exclude the "join" tables)
	 */
	Iterator<GenTable> entityGenTables() {
		return new FilteringIterator<GenTable, GenTable>(this.genTables()) {
			@Override
			protected boolean accept(GenTable genTable) {
				return ! genTable.isJoinTable();
			}
		};
	}

	int entityTablesSize() {
		return CollectionTools.size(this.entityGenTables());
	}

	/**
	 * return the gen table corresponding to the specified db table;
	 * return null if the gen table is not "in-scope" (e.g. a db foreign key
	 * might have a reference to a db table that was not included in the
	 * scope, so we won't have a corresponding gen table)
	 */
	GenTable getGenTable(Table table) {
		return this.genTables.get(table);
	}


	// ********** internal methods **********

	private Iterator<GenTable> genTables() {
		return this.genTables.values().iterator();
	}

	private int genTablesSize() {
		return this.genTables.size();
	}

	/**
	 * return only the "join" gen tables
	 */
	private Iterator<GenTable> joinGenTables() {
		return new FilteringIterator<GenTable, GenTable>(this.genTables()) {
			@Override
			protected boolean accept(GenTable genTable) {
				return genTable.isJoinTable();
			}
		};
	}

	/**
	 * build a set of the gen tables that are referenced by other gen tables
	 * in the scope
	 */
	private HashSet<GenTable> buildReferencedGenTables() {
		HashSet<GenTable> referencedGenTables = new HashSet<GenTable>(this.genTablesSize());
		for (Iterator<GenTable> stream = this.genTables(); stream.hasNext(); ) {
			stream.next().addReferencedGenTablesTo(referencedGenTables);
		}
		return referencedGenTables;
	}

	private void checkCanceled(IProgressMonitor progressMonitor) {
		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.genTables);
	}

}
