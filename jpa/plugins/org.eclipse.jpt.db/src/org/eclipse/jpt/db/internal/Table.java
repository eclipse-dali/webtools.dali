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
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;
import org.eclipse.datatools.modelbase.sql.constraints.PrimaryKey;
import org.eclipse.datatools.modelbase.sql.tables.BaseTable;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  Wrap a DTP Table
 */
public final class Table extends DTPWrapper {
	private final Schema schema;
	private final org.eclipse.datatools.modelbase.sql.tables.Table dtpTable;
	private ICatalogObjectListener tableListener;
	
	private Set<Column> columns;  // lazy-initialized
	private Set<Column> primaryKeyColumns;  // lazy-initialized
	private Set<ForeignKey> foreignKeys;  // lazy-initialized
	private Set<Column> foreignKeyColumns;  // lazy-initialized
	
	// ********** constructors **********

	Table(Schema schema, org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		super();
		this.schema = schema;
		this.dtpTable = dtpTable;
		this.initialize();
	}


	// ********** behavior **********

	private void initialize() {
		if( this.connectionIsOnline()) {
			this.tableListener = this.buildTableListener();
			this.addCatalogObjectListener(( ICatalogObject) this.dtpTable, this.tableListener);
		}
	}
	
	protected boolean connectionIsOnline() {
		return this.schema.connectionIsOnline();
	}
	
	private ICatalogObjectListener buildTableListener() {
       return new ICatalogObjectListener() {
    	    public void notifyChanged( final ICatalogObject table, final int eventType) {     
    			if( table == Table.this.dtpTable) {	    	    	
    				Table.this.refresh();
	    			Table.this.schema.tableChanged( Table.this, eventType);
    			}
    	    }
        };
    }

    private void refresh() {
		this.disposeColumns();
		
    	this.columns = null;
    	this.primaryKeyColumns = null;
    	this.foreignKeys = null;
    	this.foreignKeyColumns = null;
    }

	@Override
	protected void dispose() {
		this.removeCatalogObjectListener(( ICatalogObject) this.dtpTable, this.tableListener);
		
		this.disposeColumns();
		this.disposeForeignKey();
	}

	private void disposeColumns() {
		if( this.columns != null) {
			for( Iterator i = this.columns(); i.hasNext(); ) {
				(( Column)i.next()).dispose();
			}
		}
	}

	private void disposeForeignKey() {
		if( this.foreignKeys != null) {
			for( Iterator i = this.foreignKeys(); i.hasNext(); ) {
				(( ForeignKey)i.next()).dispose();
			}
		}
	}

	// ********** queries **********

	@Override
	public String getName() {
		return this.dtpTable.getName();
	}
	
	boolean isCaseSensitive() {
		return this.schema.isCaseSensitive();
	}

	public String shortJavaClassName() {
		String jName = this.getName();
		if ( ! this.isCaseSensitive()) {
			jName = StringTools.capitalize(jName.toLowerCase());
		}
		return NameTools.convertToJavaIdentifier(jName);
	}

	public boolean matchesShortJavaClassName(String shortJavaClassName) {
		return this.isCaseSensitive() ?
			this.getName().equals(shortJavaClassName)
		:
			this.getName().equalsIgnoreCase(shortJavaClassName);
	}

	public String javaFieldName() {
		String jName = this.getName();
		if ( ! this.isCaseSensitive()) {
			jName = jName.toLowerCase();
		}
		return NameTools.convertToJavaIdentifier(jName);
	}

	boolean wraps(org.eclipse.datatools.modelbase.sql.tables.Table table) {
		return this.dtpTable == table;
	}

	/**
	 * return the table for the specified DTP table
	 */
	Table table(org.eclipse.datatools.modelbase.sql.tables.Table table) {
		return this.schema.table(table);
	}

	// ***** columns

	private synchronized Set<Column> getColumns() {
		if (this.columns == null) {
			this.columns = this.buildColumns();
		}
		return this.columns;
	}

	@SuppressWarnings("unchecked")
	private Set<Column> buildColumns() {
		Collection<org.eclipse.datatools.modelbase.sql.tables.Column> dtpColumns = this.dtpTable.getColumns();
		Set<Column> result = new HashSet<Column>(dtpColumns.size());
		for (org.eclipse.datatools.modelbase.sql.tables.Column c : dtpColumns) {
			result.add(new Column(this, c));
		}
		return result;
	}

	public Iterator<Column> columns() {
		return this.getColumns().iterator();
	}

	public int columnsSize() {
		return this.getColumns().size();
	}

	public boolean columnsContains(Column column) {
		return this.getColumns().contains(column);
	}

	public Iterator<String> columnNames() {
		return new TransformationIterator<Column, String>(this.columns()) {
			@Override
			protected String transform(Column next) {
				 return next.getName();
			}
		};
	}

	public boolean containsColumnNamed(String name) {
		return this.columnNamed(name) != null;
	}

	public Column columnNamed(String name) {
		return this.isCaseSensitive() ? this.columnNamedInternal(name) : this.columnNamedIgnoreCase(name);
	}
	
	private Column columnNamedInternal(String name) {
		for (Iterator<Column> stream = this.columns(); stream.hasNext(); ) {
			Column column = stream.next();
			if (column.getName().equals(name)) {
				return column;
			}
		}
		return null;
	}

	private Column columnNamedIgnoreCase(String name) {
		for (Iterator<Column> stream = this.columns(); stream.hasNext(); ) {
			Column column = stream.next();
			if (StringTools.stringsAreEqualIgnoreCase(column.getName(), name)) {
				return column;
			}
		}
		return null;
	}
	
	/**
	 * return the column for the specified dtp column
	 */
	Column column(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		if (dtpColumn.getTable() != this.dtpTable) {
			return this.schema.column(dtpColumn);
		}
		for (Iterator<Column> stream = this.columns(); stream.hasNext(); ) {
			Column column = stream.next();
			if (column.wraps(dtpColumn)) {
				return column;
			}
		}
		throw new IllegalArgumentException("invalid dtp column: " + dtpColumn);
	}


	// ***** primaryKeyColumns

	private synchronized Set<Column> getPrimaryKeyColumns() {
		if (this.primaryKeyColumns == null) {
			this.primaryKeyColumns = this.buildPrimaryKeyColumns();
		}
		return this.primaryKeyColumns;
	}

	@SuppressWarnings("unchecked")
	private Set<Column> buildPrimaryKeyColumns() {
		if ( ! (this.dtpTable instanceof BaseTable)) {
			return Collections.emptySet();
		}
		PrimaryKey pk = ((BaseTable) this.dtpTable).getPrimaryKey();
		if (pk == null) {
			// no PK was defined
			return Collections.emptySet();
		}
		Collection<org.eclipse.datatools.modelbase.sql.tables.Column> pkColumns = pk.getMembers();
		Set<Column> result = new HashSet<Column>(pkColumns.size());
		for (org.eclipse.datatools.modelbase.sql.tables.Column pkColumn : pkColumns) {
			result.add(this.column(pkColumn));
		}
		return result;
	}

	public Iterator<Column> primaryKeyColumns() {
		return this.getPrimaryKeyColumns().iterator();
	}

	public int primaryKeyColumnsSize() {
		return this.getPrimaryKeyColumns().size();
	}

	public boolean primaryKeyColumnsContains(Column column) {
		return this.getPrimaryKeyColumns().contains(column);
	}


	// ***** foreignKeys

	private synchronized Set<ForeignKey> getForeignKeys() {
		if (this.foreignKeys == null) {
			this.foreignKeys = this.buildForeignKeys();
		}
		return this.foreignKeys;
	}

	@SuppressWarnings("unchecked")
	private Set<ForeignKey> buildForeignKeys() {
		if ( ! (this.dtpTable instanceof BaseTable)) {
			return Collections.emptySet();
		}
		Collection<org.eclipse.datatools.modelbase.sql.constraints.ForeignKey> dtpForeignKeys = ((BaseTable) this.dtpTable).getForeignKeys();
		Set<ForeignKey> result = new HashSet<ForeignKey>(dtpForeignKeys.size());
		for (org.eclipse.datatools.modelbase.sql.constraints.ForeignKey dtpForeignKey : dtpForeignKeys) {
			result.add(new ForeignKey(this, dtpForeignKey));
		}
		return result;
	}

	public Iterator<ForeignKey> foreignKeys() {
		return this.getForeignKeys().iterator();
	}

	public int foreignKeysSize() {
		return this.getForeignKeys().size();
	}


	// ***** foreignKeyColumns

	private synchronized Set<Column> getForeignKeyColumns() {
		if (this.foreignKeyColumns == null) {
			this.foreignKeyColumns = this.buildForeignKeyColumns();
		}
		return this.foreignKeyColumns;
	}

	private Set<Column> buildForeignKeyColumns() {
		if ( ! (this.dtpTable instanceof BaseTable)) {
			return Collections.emptySet();
		}
		Set<Column> result = new HashSet<Column>(this.columnsSize());
		for (Iterator<ForeignKey> stream = this.foreignKeys(); stream.hasNext(); ) {
			CollectionTools.addAll(result, stream.next().baseColumns());
		}
		return result;
	}

	public Iterator<Column> foreignKeyColumns() {
		return this.getForeignKeyColumns().iterator();
	}

	public int foreignKeyColumnsSize() {
		return this.getForeignKeyColumns().size();
	}

	public boolean foreignKeyColumnsContains(Column column) {
		return this.getForeignKeyColumns().contains(column);
	}

	// ********** Comparable implementation **********

	public int compareTo( Object o) {
		return Collator.getInstance().compare( this.getName(), (( Table)o).getName());
	}
}
