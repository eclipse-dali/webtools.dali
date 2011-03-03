/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.modelbase.sql.tables.SQLTablesPackage;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.db.DatabaseObject;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Sequence;
import org.eclipse.jpt.jpa.db.Table;

/**
 *  Wrap a DTP Schema
 */
final class DTPSchemaWrapper
	extends DTPDatabaseObjectWrapper<DTPSchemaContainerWrapper<?>>
	implements Schema
{
	/** the wrapped DTP schema */
	private final org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema;

	/** lazy-initialized */
	private DTPTableWrapper[] tables;

	/** lazy-initialized */
	private DTPSequenceWrapper[] sequences;

	
	// ********** constants **********
	
	/** used for adopter product customization */
	private static final String PERSISTENT_AND_VIEW_TABLES_ONLY = "supportPersistentAndViewTablesOnly"; //$NON-NLS-1$


	// ********** constructor **********

	DTPSchemaWrapper(DTPSchemaContainerWrapper<?> container, org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		super(container);
		this.dtpSchema = dtpSchema;
	}


	// ********** DTPDatabaseObjectWrapper implementation **********

	@Override
	ICatalogObject getCatalogObject() {
		return (ICatalogObject) this.dtpSchema;
	}

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().schemaChanged(this);
	}


	// ********** Schema implementation **********

	public String getName() {
		return this.dtpSchema.getName();
	}

	public DTPSchemaContainerWrapper<?> getContainer() {
		return this.parent;
	}

	// ***** tables

	public Iterable<Table> getTables() {
		return new ArrayIterable<Table>(this.getTableArray());
	}

	private Iterable<DTPTableWrapper> getTableWrappers() {
		return new ArrayIterable<DTPTableWrapper>(this.getTableArray());
	}

	private synchronized DTPTableWrapper[] getTableArray() {
		if (this.tables == null) {
			this.tables = this.buildTableArray();
		}
		return this.tables;
	}

	private DTPTableWrapper[] buildTableArray() {
		List<org.eclipse.datatools.modelbase.sql.tables.Table> dtpTables = this.getDTPTables();
		DTPTableWrapper[] result = new DTPTableWrapper[dtpTables.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPTableWrapper(this, dtpTables.get(i));
		}
		return ArrayTools.sort(result, DEFAULT_COMPARATOR);
	}

	private List<org.eclipse.datatools.modelbase.sql.tables.Table> getDTPTables() {
		List<org.eclipse.datatools.modelbase.sql.tables.Table> dtpTables = this.getDTPTables_();
		return this.hack() ? this.hack(dtpTables) : dtpTables;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Table> getDTPTables_() {
		return this.dtpSchema.getTables();
	}

	private boolean hack() {
		// the product is null during junit testing
		IProduct product = Platform.getProduct();
		String hack = (product == null) ? null : product.getProperty(PERSISTENT_AND_VIEW_TABLES_ONLY);
		return (hack != null) && hack.equals("true"); //$NON-NLS-1$
	}

	// provides a mechanism for DTP extenders that support synonyms but don't want to have them appear
	// in Dali to filter out these table types from Dali 
	private List<org.eclipse.datatools.modelbase.sql.tables.Table> hack(List<org.eclipse.datatools.modelbase.sql.tables.Table> dtpTables) {
		List<org.eclipse.datatools.modelbase.sql.tables.Table> result = new ArrayList<org.eclipse.datatools.modelbase.sql.tables.Table>();
		for (org.eclipse.datatools.modelbase.sql.tables.Table dtpTable : dtpTables) {
			if (this.hack(dtpTable)) {
				result.add(dtpTable);
			}
		}
		return result;
	}

	private boolean hack(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		return SQLTablesPackage.eINSTANCE.getPersistentTable().isSuperTypeOf(dtpTable.eClass()) ||
					SQLTablesPackage.eINSTANCE.getViewTable().isSuperTypeOf(dtpTable.eClass());
	}

	public int getTablesSize() {
		return this.getTableArray().length;
	}

	/**
	 * return the table for the specified DTP table
	 */
	DTPTableWrapper getTable(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		// try to short-circuit the search
		return this.wraps(dtpTable.getSchema()) ?
						this.getTable_(dtpTable) :
						this.getContainer().getTable(dtpTable);
	}

	/**
	 * assume the schema contains the specified table
	 */
	DTPTableWrapper getTable_(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		for (DTPTableWrapper table : this.getTableArray()) {
			if (table.wraps(dtpTable)) {
				return table;
			}
		}
		throw new IllegalArgumentException("invalid DTP table: " + dtpTable);  //$NON-NLS-1$
	}

	public DTPTableWrapper getTableNamed(String name) {
		return this.selectDatabaseObjectNamed(this.getTableWrappers(), name);
	}

	public Iterable<String> getSortedTableIdentifiers() {
		// the tables are already sorted
		return new TransformationIterable<DatabaseObject, String>(this.getTableWrappers(), IDENTIFIER_TRANSFORMER);
	}

	public Table getTableForIdentifier(String identifier) {
		return this.getDTPDriverAdapter().selectTableForIdentifier(this.getTables(), identifier);
	}

	// ***** sequences

	public Iterable<Sequence> getSequences() {
		return new ArrayIterable<Sequence>(this.getSequenceArray());
	}

	private Iterable<DTPSequenceWrapper> getSequenceWrappers() {
		return new ArrayIterable<DTPSequenceWrapper>(this.getSequenceArray());
	}

	private synchronized DTPSequenceWrapper[] getSequenceArray() {
		if (this.sequences == null) {
			this.sequences = this.buildSequenceArray();
		}
		return this.sequences;
	}

	private DTPSequenceWrapper[] buildSequenceArray() {
		List<org.eclipse.datatools.modelbase.sql.schema.Sequence> dtpSequences = this.getDTPSequences();
		DTPSequenceWrapper[] result = new DTPSequenceWrapper[dtpSequences.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPSequenceWrapper(this, dtpSequences.get(i));
		}
		return ArrayTools.sort(result, DEFAULT_COMPARATOR);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.schema.Sequence> getDTPSequences() {
		return this.dtpSchema.getSequences();
	}

	public int getSequencesSize() {
		return this.getSequenceArray().length;
	}

	public DTPSequenceWrapper getSequenceNamed(String name) {
		return this.selectDatabaseObjectNamed(this.getSequenceWrappers(), name);
	}

	public Iterable<String> getSortedSequenceIdentifiers() {
		// the sequences are already sorted
		return new TransformationIterable<DatabaseObject, String>(this.getSequenceWrappers(), IDENTIFIER_TRANSFORMER);
	}

	public Sequence getSequenceForIdentifier(String identifier) {
		return this.getDTPDriverAdapter().selectSequenceForIdentifier(this.getSequences(), identifier);
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.schema.Schema schema) {
		return this.dtpSchema == schema;
	}

	/**
	 * Return the column for the specified DTP column.
	 */
	DTPColumnWrapper getColumn(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		// try to short-circuit the search
		return this.wraps(dtpColumn.getTable().getSchema()) ?
						this.getColumn_(dtpColumn) :
						this.getContainer().getColumn(dtpColumn);
	}

	/**
	 * Assume the schema contains the specified column.
	 */
	DTPColumnWrapper getColumn_(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.getTable_(dtpColumn.getTable()).getColumn_(dtpColumn);
	}


	// ********** listening **********

	@Override
	synchronized void startListening() {
		if (this.sequences != null) {
			this.startSequences();
		}
		if (this.tables != null) {
			this.startTables();
		}
		super.startListening();
	}

	private void startSequences() {
		for (DTPSequenceWrapper sequence : this.sequences) {
			sequence.startListening();
		}
	}

	private void startTables() {
		for (DTPTableWrapper table : this.tables) {
			table.startListening();
		}
	}

	@Override
	synchronized void stopListening() {
		if (this.sequences != null) {
			this.stopSequences();
		}
		if (this.tables != null) {
			this.stopTables();
		}
		super.stopListening();
	}

	private void stopSequences() {
		for (DTPSequenceWrapper sequence : this.sequences) {
			sequence.stopListening();
		}
	}

	private void stopTables() {
		for (DTPTableWrapper table : this.tables) {
			table.stopListening();
		}
	}


	// ********** clear **********

	@Override
	synchronized void clear() {
		if (this.sequences != null) {
			this.clearSequences();
		}
		if (this.tables != null) {
			this.clearTables();
		}
	}

	private void clearSequences() {
		this.stopSequences();
		for (DTPSequenceWrapper sequence : this.sequences) {
			sequence.clear();
		}
		this.sequences = null;
	}

	private void clearTables() {
		this.stopTables();
		for (DTPTableWrapper table : this.tables) {
			table.clear();
		}
		this.tables = null;
	}
}
