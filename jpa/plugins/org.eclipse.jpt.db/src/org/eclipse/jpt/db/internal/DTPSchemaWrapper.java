/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.text.Collator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Sequence;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  Wrap a DTP Schema
 */
final class DTPSchemaWrapper
	extends DTPWrapper
	implements Schema
{
	// backpointer to parent
	private final DTPSchemaContainerWrapper container;

	// the wrapped DTP schema
	private final org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema;

	// lazy-initialized
	private DTPTableWrapper[] tables;

	// lazy-initialized
	private DTPSequenceWrapper[] sequences;


	// ********** constructor **********

	DTPSchemaWrapper(DTPSchemaContainerWrapper container, org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		super(container, dtpSchema);
		this.container = container;
		this.dtpSchema = dtpSchema;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged(int eventType) {
		// clear stuff so it can be rebuilt
		this.dispose_();
		this.getConnectionProfile().schemaChanged(this, eventType);
	}


	// ********** Schema implementation **********

	@Override
	public String getName() {
		return this.dtpSchema.getName();
	}

	// ***** tables

	public Iterator<Table> tables() {
		return new ArrayIterator<Table>(this.tables_());
	}

	private Iterator<DTPTableWrapper> tableWrappers() {
		return new ArrayIterator<DTPTableWrapper>(this.tables_());
	}

	private synchronized DTPTableWrapper[] tables_() {
		if (this.tables == null) {
			this.tables = this.buildTables();
		}
		return this.tables;
	}

	private DTPTableWrapper[] buildTables() {
		List<org.eclipse.datatools.modelbase.sql.tables.Table> dtpTables = this.dtpTables();
		DTPTableWrapper[] result = new DTPTableWrapper[dtpTables.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPTableWrapper(this, dtpTables.get(i));
		}
		return result;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Table> dtpTables() {
		return this.dtpSchema.getTables();
	}

	public int tablesSize() {
		return this.tables_().length;
	}

	public Iterator<String> tableNames() {
		return new TransformationIterator<DTPTableWrapper, String>(this.tableWrappers()) {
			@Override
			protected String transform(DTPTableWrapper table) {
				 return table.getName();
			}
		};
	}

	public boolean containsTableNamed(String name) {
		return this.tableNamed(name) != null;
	}

	public DTPTableWrapper tableNamed(String name) {
		return this.isCaseSensitive() ? this.tableNamedCaseSensitive(name) : this.tableNamedIgnoreCase(name);
	}

	private DTPTableWrapper tableNamedCaseSensitive(String name) {
		for (Iterator<DTPTableWrapper> stream = this.tableWrappers(); stream.hasNext(); ) {
			DTPTableWrapper table = stream.next();
			if (table.getName().equals(name)) {
				return table;
			}
		}
		return null;
	}

	private DTPTableWrapper tableNamedIgnoreCase(String name) {
		for (Iterator<DTPTableWrapper> stream = this.tableWrappers(); stream.hasNext(); ) {
			DTPTableWrapper table = stream.next();
			if (StringTools.stringsAreEqualIgnoreCase(table.getName(), name)) {
				return table;
			}
		}
		return null;
	}

	/**
	 * return the table for the specified DTP table
	 */
	DTPTableWrapper table(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		if (dtpTable.getSchema() != this.dtpSchema) {
			return this.database().table(dtpTable);
		}
		for (Iterator<DTPTableWrapper> stream = this.tableWrappers(); stream.hasNext(); ) {
			DTPTableWrapper table = stream.next();
			if (table.wraps(dtpTable)) {
				return table;
			}
		}
		throw new IllegalArgumentException("invalid DTP table: " + dtpTable);  //$NON-NLS-1$
	}

	// ***** sequences

	public Iterator<Sequence> sequences() {
		return new ArrayIterator<Sequence>(this.sequences_());
	}

	private Iterator<DTPSequenceWrapper> sequenceWrappers() {
		return new ArrayIterator<DTPSequenceWrapper>(this.sequences_());
	}

	private synchronized DTPSequenceWrapper[] sequences_() {
		if (this.sequences == null) {
			this.sequences = this.buildSequences();
		}
		return this.sequences;
	}

	private DTPSequenceWrapper[] buildSequences() {
		List<org.eclipse.datatools.modelbase.sql.schema.Sequence> dtpSequences = this.dtpSequences();
		DTPSequenceWrapper[] result = new DTPSequenceWrapper[dtpSequences.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPSequenceWrapper(this, dtpSequences.get(i));
		}
		return result;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.schema.Sequence> dtpSequences() {
		return this.dtpSchema.getSequences();
	}

	public int sequencesSize() {
		return this.sequences_().length;
	}

	public Iterator<String> sequenceNames() {
		return new TransformationIterator<DTPSequenceWrapper, String>(this.sequenceWrappers()) {
			@Override
			protected String transform(DTPSequenceWrapper sequence) {
				 return sequence.getName();
			}
		};
	}

	public boolean containsSequenceNamed(String name) {
		return this.sequenceNamed(name) != null;
	}

	public DTPSequenceWrapper sequenceNamed(String name) {
		return this.isCaseSensitive() ? this.sequenceNamedCaseSensitive(name) : this.sequenceNamedIgnoreCase(name);
	}
	
	private DTPSequenceWrapper sequenceNamedCaseSensitive(String name) {
		for (Iterator<DTPSequenceWrapper> stream = this.sequenceWrappers(); stream.hasNext(); ) {
			DTPSequenceWrapper sequence = stream.next();
			if (sequence.getName().equals(name)) {
				return sequence;
			}
		}
		return null;
	}

	private DTPSequenceWrapper sequenceNamedIgnoreCase(String name) {
		for (Iterator<DTPSequenceWrapper> stream = this.sequenceWrappers(); stream.hasNext(); ) {
			DTPSequenceWrapper sequence = stream.next();
			if (sequence.getName().equalsIgnoreCase(name)) {
				return sequence;
			}
		}
		return null;
	}


	// ********** Comparable implementation **********

	public int compareTo(Schema schema) {
		return Collator.getInstance().compare(this.getName(), schema.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.schema.Schema schema) {
		return this.dtpSchema == schema;
	}

	boolean isCaseSensitive() {
		return this.database().isCaseSensitive();
	}

	DTPColumnWrapper column(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.database().column(dtpColumn);
	}

	DTPDatabaseWrapper database() {
		return this.container.database();
	}


	// ********** disposal **********

	@Override
	synchronized void dispose() {
		this.dispose_();
		super.dispose();
	}

	private void dispose_() {
		this.disposeSequences();
		this.disposeTables();
	}

	private void disposeSequences() {
		if (this.sequences != null) {
			for (DTPSequenceWrapper sequence : this.sequences) {
				sequence.dispose();
			}
			this.sequences = null;
		}
	}

	private void disposeTables() {
		if (this.tables != null) {
			for (DTPTableWrapper table : this.tables) {
				table.dispose();
			}
			this.tables = null;
		}
	}

}
