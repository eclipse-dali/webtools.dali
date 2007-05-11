/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.text.Collator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  Wrap a DTP Schema
 */
public final class Schema extends DTPWrapper implements Comparable<Schema> {
	final Database database;
	final org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema;
	private ICatalogObjectListener schemaListener;
	
	private Set<Table> tables;  // lazy-initialized
	private Set<Sequence> sequences;  // lazy-initialized
	

	// ********** constructors **********

	Schema( Database database, org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		super();
		this.database = database;
		this.dtpSchema = dtpSchema;
		this.initialize();
	}

	// ********** behavior **********

	private void initialize() {
		if( this.connectionIsOnline()) {
			this.schemaListener = this.buildSchemaListener();
			this.addCatalogObjectListener(( ICatalogObject) this.dtpSchema, this.schemaListener);
		}
	}
	
	@Override
	protected boolean connectionIsOnline() {
		return this.database.connectionIsOnline();
	}

	private ICatalogObjectListener buildSchemaListener() {
       return new ICatalogObjectListener() {
    	    public void notifyChanged( final ICatalogObject schema, final int eventType) {     
    			if( schema == Schema.this.dtpSchema) {	
    				Schema.this.refresh();
    				Schema.this.database.schemaChanged( Schema.this, eventType);
    			}
    	    }
        };
    }

	void refresh() {
		this.disposeTables();
		this.disposeSequences();
		
		this.tables = null;
		this.sequences = null;
	}

	void tableChanged( Table table, int eventType) {
		this.database.tableChanged( table, this, eventType);
	}
	
	protected Table wrap( org.eclipse.datatools.modelbase.sql.tables.Table table) {
		return new Table( this, table);
	}
	
	protected Sequence wrap(  org.eclipse.datatools.modelbase.sql.schema.Sequence sequence) {
		return new Sequence( this, sequence);
	}

	@Override
	protected void dispose() {
		this.removeCatalogObjectListener(( ICatalogObject) this.dtpSchema, this.schemaListener);

		this.disposeTables();
		this.disposeSequences();
	}

	private void disposeTables() {
		if( this.tables != null) {
			for( Iterator<Table> stream = this.tables(); stream.hasNext(); ) {
				stream.next().dispose();
			}
		}
	}

	private void disposeSequences() {
		if( this.sequences != null) {
			for( Iterator<Sequence> stream = this.sequences(); stream.hasNext(); ) {
				stream.next().dispose();
			}
		}
	}
	
	// ********** queries **********

	@Override
	public String getName() {
		return this.dtpSchema.getName();
	}
	
	boolean isCaseSensitive() {
		return this.database.isCaseSensitive();
	}

	Column column( org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.database.column(dtpColumn);
	}


	// ********** tables **********

	private synchronized Collection<Table> getTables() {
		if( this.tables == null) {
			this.tables = this.buildTables();
		}
		return this.tables;
	}

	@SuppressWarnings("unchecked")
	private Collection<org.eclipse.datatools.modelbase.sql.tables.Table> dtpTables() {
		return this.dtpSchema.getTables();
	}

	private Set<Table> buildTables() {
		Collection<org.eclipse.datatools.modelbase.sql.tables.Table> dtpTables = this.dtpTables();
		Set<Table> result = new HashSet<Table>( dtpTables.size());
		for (org.eclipse.datatools.modelbase.sql.tables.Table dtpTable : dtpTables) {
			result.add( this.wrap(dtpTable));
		}
		return result;
	}
	
	public Iterator<Table> tables() {
		return this.getTables().iterator();
	}

	public int tablesSize() {
		return this.getTables().size();
	}

	public boolean tablesContains( Column column) {
		return this.getTables().contains( column);
	}

	public Iterator<String> tableNames() {
		return new TransformationIterator<Table, String>( this.tables()) {
			@Override
			protected String transform( Table table) {
				 return table.getName();
			}
		};
	}

	public boolean containsTableNamed( String name) {
		return this.tableNamed( name) != null;
	}

	public Table tableNamed( String name) {
		return this.isCaseSensitive() ? this.tableNamedInternal( name) : this.tableNamedIgnoreCase( name);
	}
	
	private Table tableNamedInternal( String name) {
		for( Iterator<Table> stream = this.tables(); stream.hasNext(); ) {
			Table table = stream.next();
			if( table.getName().equals( name)) {
				return table;
			}
		}
		return null;
	}
	
	private Table tableNamedIgnoreCase( String name) {
		for( Iterator<Table> stream = this.tables(); stream.hasNext(); ) {
			Table table = stream.next();
			if( StringTools.stringsAreEqualIgnoreCase( table.getName(), name)) {
				return table;
			}
		}
		return null;
	}

	/**
	 * return the table for the specified dtp table
	 */
	Table table( org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		if( dtpTable.getSchema() != this.dtpSchema) {
			return this.database.table( dtpTable);
		}
		for( Iterator<Table> stream = this.tables(); stream.hasNext(); ) {
			Table table = stream.next();
			if( table.wraps( dtpTable)) {
				return table;
			}
		}
		throw new IllegalArgumentException( "invalid DTP table: " + dtpTable);
	}

	// ***** sequences

	private synchronized Collection<Sequence> getSequences() {
		if( this.sequences == null) {
			this.sequences = this.buildSequences();
		}
		return this.sequences;
	}

	@SuppressWarnings("unchecked")
	private Collection<org.eclipse.datatools.modelbase.sql.schema.Sequence> dtpSequences() {
		return this.dtpSchema.getSequences();
	}

	private Set<Sequence> buildSequences() {
		Collection<org.eclipse.datatools.modelbase.sql.schema.Sequence> dtpSequences = this.dtpSequences();
		Set<Sequence> result = new HashSet<Sequence>( dtpSequences.size());
		for (org.eclipse.datatools.modelbase.sql.schema.Sequence dtpSequence : dtpSequences) {
			result.add( this.wrap(dtpSequence));
		}
		return result;
	}

	public Iterator<Sequence> sequences() {
		return this.getSequences().iterator();
	}

	public int sequencesSize() {
		return this.getSequences().size();
	}

	public boolean sequencesContains( Column column) {
		return this.getSequences().contains( column);
	}

	public Iterator<String> sequenceNames() {
		return new TransformationIterator<Sequence, String>(this.sequences()) {
			@Override
			protected String transform( Sequence sequence) {
				 return sequence.getName();
			}
		};
	}

	public boolean containsSequenceNamed( String name) {
		return this.sequenceNamed( name) != null;
	}

	public Sequence sequenceNamed( String name) {
		return this.isCaseSensitive() ? this.sequenceNamedInternal( name) : this.sequenceNamedIgnoreCase( name);
	}
	
	private Sequence sequenceNamedInternal( String name) {
		for( Iterator<Sequence> stream = this.sequences(); stream.hasNext(); ) {
			Sequence sequence = stream.next();
			if( sequence.getName().equals( name)) {
				return sequence;
			}
		}
		return null;
	}

	private Sequence sequenceNamedIgnoreCase( String name) {
		for( Iterator<Sequence> stream = this.sequences(); stream.hasNext(); ) {
			Sequence sequence = stream.next();
			if( sequence.getName().equalsIgnoreCase( name)) {
				return sequence;
			}
		}
		return null;
	}
	
	boolean wraps( org.eclipse.datatools.modelbase.sql.schema.Schema schema) {
		return this.dtpSchema == schema;
	}

	// ********** Comparable implementation **********

	public int compareTo( Schema schema) {
		return Collator.getInstance().compare( this.getName(), schema.getName());
	}

}
