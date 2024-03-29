/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal;

import java.util.List;
import org.eclipse.datatools.modelbase.sql.constraints.PrimaryKey;
import org.eclipse.datatools.modelbase.sql.tables.BaseTable;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.DatabaseObject;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Wrap a DTP Table
 */
final class DTPTableWrapper
	extends DTPDatabaseObjectWrapper<DTPSchemaWrapper, org.eclipse.datatools.modelbase.sql.tables.Table>
	implements Table
{
	/** lazy-initialized */
	private DTPColumnWrapper[] columns;

	/** lazy-initialized */
	private DTPColumnWrapper[] primaryKeyColumns;

	/** lazy-initialized */
	private DTPForeignKeyWrapper[] foreignKeys;


	private static final DTPColumnWrapper[] EMPTY_COLUMNS = new DTPColumnWrapper[0];
	private static final DTPForeignKeyWrapper[] EMPTY_FOREIGN_KEYS = new DTPForeignKeyWrapper[0];


	// ********** constructor **********

	DTPTableWrapper(DTPSchemaWrapper schema, org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		super(schema, dtpTable);
	}


	// ********** columns **********

	public Iterable<Column> getColumns() {
		return IterableTools.<Column>iterable(this.getColumnArray());
	}

	private Iterable<DTPColumnWrapper> getColumnWrappers() {
		return IterableTools.iterable(this.getColumnArray());
	}

	private synchronized DTPColumnWrapper[] getColumnArray() {
		if (this.columns == null) {
			this.columns = this.buildColumnArray();
		}
		return this.columns;
	}

	private DTPColumnWrapper[] buildColumnArray() {
		List<org.eclipse.datatools.modelbase.sql.tables.Column> dtpColumns = this.getDTPColumns();
		DTPColumnWrapper[] result = new DTPColumnWrapper[dtpColumns.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPColumnWrapper(this, dtpColumns.get(i));
		}
		return ArrayTools.sort(result, DEFAULT_COMPARATOR);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> getDTPColumns() {
		return this.dtpObject.getColumns();
	}

	public int getColumnsSize() {
		return this.getColumnArray().length;
	}

	public DTPColumnWrapper getColumnNamed(String name) {
		return this.selectDatabaseObjectNamed(this.getColumnWrappers(), name);
	}

	/**
	 * Return the column for the specified DTP column.
	 */
	DTPColumnWrapper getColumn(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		// try to short-circuit the search
		return this.wraps(dtpColumn.getTable()) ?
						this.getColumn_(dtpColumn) :
						this.getSchema().getColumn(dtpColumn);
	}

	/**
	 * Pre-condition: The table contains the specified column.
	 */
	DTPColumnWrapper getColumn_(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		for (DTPColumnWrapper column : this.getColumnArray()) {
			if (column.wraps(dtpColumn)) {
				return column;
			}
		}
		throw new IllegalArgumentException("invalid DTP column: " + dtpColumn);  //$NON-NLS-1$
	}

	public Iterable<String> getSortedColumnIdentifiers() {
		// the columns are already sorted
		return new TransformationIterable<DatabaseObject, String>(this.getColumnWrappers(), IDENTIFIER_TRANSFORMER);
	}

	public Column getColumnForIdentifier(String identifier) {
		return this.getDTPDriverAdapter().selectColumnForIdentifier(this.getColumns(), identifier);
	}


	// ********** primary key columns **********

	public Iterable<Column> getPrimaryKeyColumns() {
		return IterableTools.<Column>iterable(this.getPrimaryKeyColumnArray());
	}

	public DTPColumnWrapper getPrimaryKeyColumn() {
		DTPColumnWrapper[] pkColumns = this.getPrimaryKeyColumnArray();
		if (pkColumns.length != 1) {
			throw new IllegalStateException("multiple primary key columns: " + pkColumns.length);  //$NON-NLS-1$
		}
		return pkColumns[0];
	}

	private synchronized DTPColumnWrapper[] getPrimaryKeyColumnArray() {
		if (this.primaryKeyColumns == null) {
			this.primaryKeyColumns = this.buildPrimaryKeyColumnArray();
		}
		return this.primaryKeyColumns;
	}

	private DTPColumnWrapper[] buildPrimaryKeyColumnArray() {
		if ( ! (this.dtpObject instanceof BaseTable)) {
			return EMPTY_COLUMNS;
		}
		PrimaryKey pk = ((BaseTable) this.dtpObject).getPrimaryKey();
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

	public int getPrimaryKeyColumnsSize() {
		return this.getPrimaryKeyColumnArray().length;
	}

	boolean primaryKeyColumnsContains(Column column) {
		return ArrayTools.contains(this.getPrimaryKeyColumnArray(), column);
	}


	// ********** foreign keys **********

	public Iterable<ForeignKey> getForeignKeys() {
		return IterableTools.<ForeignKey>iterable(this.getForeignKeyArray());
	}

	private synchronized DTPForeignKeyWrapper[] getForeignKeyArray() {
		if (this.foreignKeys == null) {
			this.foreignKeys = this.buildForeignKeyArray();
		}
		return this.foreignKeys;
	}

	private DTPForeignKeyWrapper[] buildForeignKeyArray() {
		if ( ! (this.dtpObject instanceof BaseTable)) {
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
		return ((BaseTable) this.dtpObject).getForeignKeys();
	}

	public int getForeignKeysSize() {
		return this.getForeignKeyArray().length;
	}

	/**
	 * return whether the specified column is a base column for at least one
	 * of the the table's foreign keys
	 */
	boolean foreignKeyBaseColumnsContains(Column column) {
		for (DTPForeignKeyWrapper fkWrapper : this.getForeignKeyArray()) {
			if (fkWrapper.baseColumnsContains(column)) {
				return true;
			}
		}
		return false;
	}


	// ********** join table **********

	public boolean isPossibleJoinTable() {
		if (this.getForeignKeyArray().length != 2) {
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
	 * If the table name is <code>FOO_BAR</code>
	 * and it joins tables <code>FOO</code> and <code>BAR</code>,
	 * return the foreign key to <code>FOO</code>;
	 * if the table name is <code>BAR_FOO</code>
	 * and it joins tables <code>FOO</code> and <code>BAR</code>,
	 * return the foreign key to <code>BAR</code>;
	 * otherwise simply return the first foreign key in the array.
	 */
	public ForeignKey getJoinTableOwningForeignKey() {
		ForeignKey fk0 = this.getForeignKeyArray()[0];
		String name0 = fk0.getReferencedTable().getName();

		ForeignKey fk1 = this.getForeignKeyArray()[1];
		String name1 = fk1.getReferencedTable().getName();

		return this.getName().equals(name1 + '_' + name0) ? fk1 : fk0;
	}

	public ForeignKey getJoinTableNonOwningForeignKey() {
		ForeignKey fk0 = this.getForeignKeyArray()[0];
		ForeignKey fk1 = this.getForeignKeyArray()[1];
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


	// ********** misc **********

	public DTPSchemaWrapper getSchema() {
		return this.parent;
	}

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().tableChanged(this);
	}

	boolean wraps(org.eclipse.datatools.modelbase.sql.tables.Table table) {
		return this.dtpObject == table;
	}

	/**
	 * Return the table for the specified DTP table.
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
	synchronized void clear() {
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
