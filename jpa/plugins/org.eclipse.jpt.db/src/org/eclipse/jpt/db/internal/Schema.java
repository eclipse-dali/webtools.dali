/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
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
public final class Schema extends DTPWrapper {
	private final Database database;
	private final org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema;
	private ICatalogObjectListener schemaListener;
	
	private Set tables;  // lazy-initialized
	private Set sequences;  // lazy-initialized
	

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

	private void refresh() {
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

	protected void dispose() {
		this.removeCatalogObjectListener(( ICatalogObject) this.dtpSchema, this.schemaListener);

		this.disposeTables();
		this.disposeSequences();
	}

	private void disposeTables() {
		if( this.tables != null) {
			for( Iterator i = this.tables(); i.hasNext(); ) {
				(( Table)i.next()).dispose();
			}
		}
	}

	private void disposeSequences() {
		if( this.sequences != null) {
			for( Iterator i = this.sequences(); i.hasNext(); ) {
				(( Sequence)i.next()).dispose();
			}
		}
	}
	
	// ********** queries **********

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

	private synchronized Collection getTables() {
		if( this.tables == null) {
			this.tables = this.buildTables();
		}
		return this.tables;
	}

	private Set buildTables() {
		Collection dtpTables = this.dtpSchema.getTables();
		Set result = new HashSet( dtpTables.size());
		for( Iterator i = dtpTables.iterator(); i.hasNext(); ) {
			result.add( this.wrap(( org.eclipse.datatools.modelbase.sql.tables.Table) i.next()));
		}
		return result;
	}
	
	public Iterator tables() {
		return this.getTables().iterator();
	}

	public int tablesSize() {
		return this.getTables().size();
	}

	public boolean tablesContains( Column column) {
		return this.getTables().contains( column);
	}

	public Iterator<String> tableNames() {
		return new TransformationIterator( this.tables()) {
			protected Object transform( Object next) {
				 return (( Table) next).getName();
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
		for( Iterator i = this.tables(); i.hasNext(); ) {
			Table table = ( Table) i.next();
			if( table.getName().equals( name)) {
				return table;
			}
		}
		return null;
	}
	
	private Table tableNamedIgnoreCase( String name) {
		for( Iterator i = this.tables(); i.hasNext(); ) {
			Table table = ( Table) i.next();
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
		for( Iterator i = this.tables(); i.hasNext(); ) {
			Table table = ( Table) i.next();
			if( table.wraps( dtpTable)) {
				return table;
			}
		}
		throw new IllegalArgumentException( "invalid DTP table: " + dtpTable);
	}

	// ***** sequences

	private synchronized Collection getSequences() {
		if( this.sequences == null) {
			this.sequences = this.buildSequences();
		}
		return this.sequences;
	}

	private Set buildSequences() {
		Collection dtpSequences = this.dtpSchema.getSequences();
		Set result = new HashSet( dtpSequences.size());
		for( Iterator i = dtpSequences.iterator(); i.hasNext(); ) {
			result.add( this.wrap(( org.eclipse.datatools.modelbase.sql.schema.Sequence) i.next()));
		}
		return result;
	}

	public Iterator sequences() {
		return this.getSequences().iterator();
	}

	public int sequencesSize() {
		return this.getSequences().size();
	}

	public boolean sequencesContains( Column column) {
		return this.getSequences().contains( column);
	}

	public Iterator sequenceNames() {
		return new TransformationIterator(this.sequences()) {
			protected Object transform( Object next) {
				 return (( Sequence)next).getName();
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
		for( Iterator i = this.sequences(); i.hasNext(); ) {
			Sequence sequence = ( Sequence) i.next();
			if( sequence.getName().equals( name)) {
				return sequence;
			}
		}
		return null;
	}

	private Sequence sequenceNamedIgnoreCase( String name) {
		for( Iterator i = this.sequences(); i.hasNext(); ) {
			Sequence sequence = ( Sequence) i.next();
			if( sequence.getName().equalsIgnoreCase( name)) {
				return sequence;
			}
		}
		return null;
	}
	
	boolean wraps( org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		return this.dtpSchema == dtpSchema;
	}

	// ********** Comparable implementation **********

	public int compareTo( Object o) {
		return Collator.getInstance().compare( this.getName(), (( Schema)o).getName());
	}

}
