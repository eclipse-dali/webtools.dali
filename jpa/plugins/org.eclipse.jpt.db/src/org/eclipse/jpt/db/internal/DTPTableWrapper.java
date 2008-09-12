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

import org.eclipse.datatools.modelbase.sql.constraints.PrimaryKey;
import org.eclipse.datatools.modelbase.sql.tables.BaseTable;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  Wrap a DTP Table
 */
final class DTPTableWrapper
	extends DTPDatabaseObjectWrapper
	implements Table
{
	// the wrapped DTP table
	private final org.eclipse.datatools.modelbase.sql.tables.Table dtpTable;

	// lazy-initialized
	private DTPColumnWrapper[] columns;

	// lazy-initialized
	private DTPColumnWrapper[] primaryKeyColumns;

	// lazy-initialized
	private DTPForeignKeyWrapper[] foreignKeys;


	private static final DTPColumnWrapper[] EMPTY_COLUMNS = new DTPColumnWrapper[0];
	private static final DTPForeignKeyWrapper[] EMPTY_FOREIGN_KEYS = new DTPForeignKeyWrapper[0];


	// ********** constructor **********

	DTPTableWrapper(DTPSchemaWrapper schema, org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		super(schema, dtpTable);
		this.dtpTable = dtpTable;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().tableChanged(this);
	}


	// ********** Table implementation **********

	public String getName() {
		return this.dtpTable.getName();
	}

	public DTPSchemaWrapper getSchema() {
		return (DTPSchemaWrapper) this.getParent();
	}

	// ***** columns

	public Iterator<Column> columns() {
		return new ArrayIterator<Column>(this.getColumns());
	}

	private Iterator<DTPColumnWrapper> columnWrappers() {
		return new ArrayIterator<DTPColumnWrapper>(this.getColumns());
	}

	private synchronized DTPColumnWrapper[] getColumns() {
		if (this.columns == null) {
			this.columns = this.buildColumns();
		}
		return this.columns;
	}

	private DTPColumnWrapper[] buildColumns() {
		List<org.eclipse.datatools.modelbase.sql.tables.Column> dtpColumns = this.getDTPColumns();
		DTPColumnWrapper[] result = new DTPColumnWrapper[dtpColumns.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPColumnWrapper(this, dtpColumns.get(i));
		}
		return CollectionTools.sort(result);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> getDTPColumns() {
		return this.dtpTable.getColumns();
	}

	public int columnsSize() {
		return this.getColumns().length;
	}

	public DTPColumnWrapper getColumnNamed(String name) {
		return this.selectDatabaseObjectNamed(this.getColumns(), name);
	}

	/**
	 * return the column for the specified DTP column
	 */
	DTPColumnWrapper getColumn(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		// try to short-circuit the search
		return this.wraps(dtpColumn.getTable()) ?
						this.getColumn_(dtpColumn)
					:
						this.getSchema().getColumn(dtpColumn);
	}

	/**
	 * assume the table contains the specified column
	 */
	DTPColumnWrapper getColumn_(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		for (DTPColumnWrapper column : this.getColumns()) {
			if (column.wraps(dtpColumn)) {
				return column;
			}
		}
		throw new IllegalArgumentException("invalid DTP column: " + dtpColumn);  //$NON-NLS-1$
	}

	public Iterator<String> sortedColumnIdentifiers() {
		// the columns are already sorted
		return new TransformationIterator<DTPColumnWrapper, String>(this.columnWrappers()) {
			@Override
			protected String transform(DTPColumnWrapper next) {
				 return next.getIdentifier();
			}
		};
	}

	public DTPColumnWrapper getColumnForIdentifier(String identifier) {
		return this.selectDatabaseObjectForIdentifier(this.getColumns(), identifier);
	}

	// ***** primaryKeyColumns

	public Iterator<Column> primaryKeyColumns() {
		return new ArrayIterator<Column>(this.getPrimaryKeyColumns());
	}

	public DTPColumnWrapper getPrimaryKeyColumn() {
		DTPColumnWrapper[] pkColumns = this.getPrimaryKeyColumns();
		if (pkColumns.length != 1) {
			throw new IllegalStateException("multiple primary key columns: " + pkColumns.length);  //$NON-NLS-1$
		}
		return pkColumns[0];
	}

	private synchronized DTPColumnWrapper[] getPrimaryKeyColumns() {
		if (this.primaryKeyColumns == null) {
			this.primaryKeyColumns = this.buildPrimaryKeyColumns();
		}
		return this.primaryKeyColumns;
	}

	private DTPColumnWrapper[] buildPrimaryKeyColumns() {
		if ( ! (this.dtpTable instanceof BaseTable)) {
			return EMPTY_COLUMNS;
		}
		PrimaryKey pk = ((BaseTable) this.dtpTable).getPrimaryKey();
		if (pk == null) {
			// no PK was defined
			return EMPTY_COLUMNS;
		}
		List<org.eclipse.datatools.modelbase.sql.tables.Column> pkColumns = this.getColumns(pk);
		DTPColumnWrapper[] result = new DTPColumnWrapper[pkColumns.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = this.getColumn(pkColumns.get(i));
		}
		return result;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> getColumns(PrimaryKey pk) {
		return pk.getMembers();
	}

	public int primaryKeyColumnsSize() {
		return this.getPrimaryKeyColumns().length;
	}

	boolean primaryKeyColumnsContains(Column column) {
		return CollectionTools.contains(this.getPrimaryKeyColumns(), column);
	}

	// ***** foreignKeys

	public Iterator<ForeignKey> foreignKeys() {
		return new ArrayIterator<ForeignKey>(this.getForeignKeys());
	}

	private synchronized DTPForeignKeyWrapper[] getForeignKeys() {
		if (this.foreignKeys == null) {
			this.foreignKeys = this.buildForeignKeys();
		}
		return this.foreignKeys;
	}

	private DTPForeignKeyWrapper[] buildForeignKeys() {
		if ( ! (this.dtpTable instanceof BaseTable)) {
			return EMPTY_FOREIGN_KEYS;
		}
		List<org.eclipse.datatools.modelbase.sql.constraints.ForeignKey> dtpForeignKeys = this.getDTPForeignKeys();
		DTPForeignKeyWrapper[] result = new DTPForeignKeyWrapper[dtpForeignKeys.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPForeignKeyWrapper(this, dtpForeignKeys.get(i));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.constraints.ForeignKey> getDTPForeignKeys() {
		return ((BaseTable) this.dtpTable).getForeignKeys();
	}

	public int foreignKeysSize() {
		return this.getForeignKeys().length;
	}

	/**
	 * return whether the specified column is a base column for at least one
	 * of the the table's foreign keys
	 */
	boolean foreignKeyBaseColumnsContains(Column column) {
		for (DTPForeignKeyWrapper fkWrapper : this.getForeignKeys()) {
			if (fkWrapper.baseColumnsContains(column)) {
				return true;
			}
		}
		return false;
	}

	// ***** join table

	public boolean isPossibleJoinTable() {
		if (this.getForeignKeys().length != 2) {
			return false;  // the table must have exactly 2 foreign keys
		}
		for (Column column : this.getColumns()) {
			if ( ! this.foreignKeyBaseColumnsContains(column)) {
				return false;  // all the table's columns must belong to one (or both) of the 2 foreign keys
			}
		}
		return true;
	}

	/**
	 * If the table name is FOO_BAR and it joins tables FOO and BAR,
	 * return the foreign key to FOO;
	 * if the table name is BAR_FOO and it joins tables FOO and BAR,
	 * return the foreign key to BAR;
	 * otherwise simply return the first foreign key in the array.
	 */
	public ForeignKey getJoinTableOwningForeignKey() {
		ForeignKey fk0 = this.getForeignKeys()[0];
		String name0 = fk0.getReferencedTable().getName();

		ForeignKey fk1 = this.getForeignKeys()[1];
		String name1 = fk1.getReferencedTable().getName();

		return this.getName().equals(name1 + '_' + name0) ? fk1 : fk0;
	}

	public ForeignKey getJoinTableNonOwningForeignKey() {
		ForeignKey fk0 = this.getForeignKeys()[0];
		ForeignKey fk1 = this.getForeignKeys()[1];
		ForeignKey ofk = this.getJoinTableOwningForeignKey();
		return (ofk == fk0) ? fk1 : fk0;
	}

	/**
	 * Hmmm....
	 * We might want to go to the platform to allow a vendor-specific
	 * comparison here;
	 * but, since all the names are coming directly from the database
	 * (i.e. there are no conversions to Java identifiers etc.), it seems
	 * like we can just compare them directly and ignore case-sensitivity
	 * issues....  ~bjv
	 */
	public boolean joinTableNameIsDefault() {
		return this.getName().equals(this.buildDefaultJoinTableName());
	}

	private String buildDefaultJoinTableName() {
		return this.getJoinTableOwningTable().getName()
					+ '_'
					+ this.getJoinTableNonOwningTable().getName();
	}

	private Table getJoinTableOwningTable() {
		return this.getJoinTableOwningForeignKey().getReferencedTable();
	}

	private Table getJoinTableNonOwningTable() {
		return this.getJoinTableNonOwningForeignKey().getReferencedTable();
	}


	// ********** Comparable implementation **********

	public int compareTo(Table table) {
		return Collator.getInstance().compare(this.getName(), table.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.tables.Table table) {
		return this.dtpTable == table;
	}

	/**
	 * return the table for the specified DTP table
	 */
	DTPTableWrapper getTable(org.eclipse.datatools.modelbase.sql.tables.Table table) {
		// try to short-circuit the search
		return this.wraps(table) ? this : this.getSchema().getTable(table);
	}


	// ********** listening **********

	@Override
	synchronized void startListening() {
		if (this.foreignKeys != null) {
			this.startForeignKeys();
		}
		if (this.columns != null) {
			this.startColumns();
		}
		super.startListening();
	}

	private void startForeignKeys() {
		for (DTPForeignKeyWrapper foreignKey : this.foreignKeys) {
			foreignKey.startListening();
		}
	}

	private void startColumns() {
		for (DTPColumnWrapper column : this.columns) {
			column.startListening();
		}
	}

	@Override
	synchronized void stopListening() {
		if (this.foreignKeys != null) {
			this.stopForeignKeys();
		}
		if (this.columns != null) {
			this.stopColumns();
		}
		super.stopListening();
	}

	private void stopForeignKeys() {
		for (DTPForeignKeyWrapper foreignKey : this.foreignKeys) {
			foreignKey.stopListening();
		}
	}

	private void stopColumns() {
		for (DTPColumnWrapper column : this.columns) {
			column.stopListening();
		}
	}


	// ********** clear **********

	@Override
	void clear() {
		if (this.foreignKeys != null) {
			this.clearForeignKeys();
		}

		// the table does not "contain" the pk columns, so no need to forward #clear()
		this.primaryKeyColumns = null;

		if (this.columns != null) {
			this.clearColumns();
		}
	}

	private void clearForeignKeys() {
		this.stopForeignKeys();
		for (DTPForeignKeyWrapper foreignKey : this.foreignKeys) {
			foreignKey.clear();
		}
    	this.foreignKeys = null;
	}

	private void clearColumns() {
		this.stopColumns();
		for (DTPColumnWrapper column : this.columns) {
			column.clear();
		}
    	this.columns = null;
	}

}
