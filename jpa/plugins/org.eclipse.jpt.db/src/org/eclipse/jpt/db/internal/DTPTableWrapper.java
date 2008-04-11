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
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  Wrap a DTP Table
 */
final class DTPTableWrapper
	extends DTPWrapper
	implements Table
{
	// backpointer to parent
	private final DTPSchemaWrapper schema;

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
		this.schema = schema;
		this.dtpTable = dtpTable;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged(int eventType) {
		// clear stuff so it can be rebuilt
		this.dispose_();
		this.getConnectionProfile().tableChanged(this, eventType);
	}


	// ********** Table implementation **********

	@Override
	public String getName() {
		return this.dtpTable.getName();
	}

	public String getShortJavaClassName() {
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

	public String getJavaFieldName() {
		String jName = this.getName();
		if ( ! this.isCaseSensitive()) {
			jName = jName.toLowerCase();
		}
		return NameTools.convertToJavaIdentifier(jName);
	}

	// ***** columns

	public Iterator<Column> columns() {
		return new ArrayIterator<Column>(this.columns_());
	}

	private Iterator<DTPColumnWrapper> columnWrappers() {
		return new ArrayIterator<DTPColumnWrapper>(this.columns_());
	}

	private synchronized DTPColumnWrapper[] columns_() {
		if (this.columns == null) {
			this.columns = this.buildColumns();
		}
		return this.columns;
	}

	private DTPColumnWrapper[] buildColumns() {
		List<org.eclipse.datatools.modelbase.sql.tables.Column> dtpColumns = this.dtpColumns();
		DTPColumnWrapper[] result = new DTPColumnWrapper[dtpColumns.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPColumnWrapper(this, dtpColumns.get(i));
		}
		return result;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> dtpColumns() {
		return this.dtpTable.getColumns();
	}

	public int columnsSize() {
		return this.columns_().length;
	}

	public Iterator<String> columnNames() {
		return new TransformationIterator<DTPColumnWrapper, String>(this.columnWrappers()) {
			@Override
			protected String transform(DTPColumnWrapper next) {
				 return next.getName();
			}
		};
	}

	public boolean containsColumnNamed(String name) {
		return this.columnNamed(name) != null;
	}

	public DTPColumnWrapper columnNamed(String name) {
		return this.isCaseSensitive() ? this.columnNamedCaseSensitive(name) : this.columnNamedIgnoreCase(name);
	}

	private DTPColumnWrapper columnNamedCaseSensitive(String name) {
		for (Iterator<DTPColumnWrapper> stream = this.columnWrappers(); stream.hasNext(); ) {
			DTPColumnWrapper column = stream.next();
			if (column.getName().equals(name)) {
				return column;
			}
		}
		return null;
	}

	private DTPColumnWrapper columnNamedIgnoreCase(String name) {
		for (Iterator<DTPColumnWrapper> stream = this.columnWrappers(); stream.hasNext(); ) {
			DTPColumnWrapper column = stream.next();
			if (StringTools.stringsAreEqualIgnoreCase(column.getName(), name)) {
				return column;
			}
		}
		return null;
	}

	/**
	 * return the column for the specified DTP column
	 */
	DTPColumnWrapper column(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		if (dtpColumn.getTable() != this.dtpTable) {
			return this.schema.column(dtpColumn);
		}
		for (DTPColumnWrapper column : this.columns_()) {
			if (column.wraps(dtpColumn)) {
				return column;
			}
		}
		throw new IllegalArgumentException("invalid DTP column: " + dtpColumn);  //$NON-NLS-1$
	}

	// ***** primaryKeyColumns

	public Iterator<Column> primaryKeyColumns() {
		return new ArrayIterator<Column>(this.primaryKeyColumns_());
	}

	public DTPColumnWrapper primaryKeyColumn() {
		DTPColumnWrapper[] pkColumns = this.primaryKeyColumns_();
		if (pkColumns.length != 1) {
			throw new IllegalStateException("multiple primary key columns: " + pkColumns.length);  //$NON-NLS-1$
		}
		return pkColumns[0];
	}

	private synchronized DTPColumnWrapper[] primaryKeyColumns_() {
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
		List<org.eclipse.datatools.modelbase.sql.tables.Column> pkColumns = this.columns(pk);
		DTPColumnWrapper[] result = new DTPColumnWrapper[pkColumns.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = this.column(pkColumns.get(i));
		}
		return result;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> columns(PrimaryKey pk) {
		return pk.getMembers();
	}

	public int primaryKeyColumnsSize() {
		return this.primaryKeyColumns_().length;
	}

	public boolean primaryKeyColumnsContains(Column column) {
		return CollectionTools.contains(this.primaryKeyColumns_(), column);
	}

	// ***** foreignKeys

	public Iterator<ForeignKey> foreignKeys() {
		return new ArrayIterator<ForeignKey>(this.foreignKeys_());
	}

	private Iterator<DTPForeignKeyWrapper> foreignKeyWrappers() {
		return new ArrayIterator<DTPForeignKeyWrapper>(this.foreignKeys_());
	}

	private synchronized DTPForeignKeyWrapper[] foreignKeys_() {
		if (this.foreignKeys == null) {
			this.foreignKeys = this.buildForeignKeys();
		}
		return this.foreignKeys;
	}

	private DTPForeignKeyWrapper[] buildForeignKeys() {
		if ( ! (this.dtpTable instanceof BaseTable)) {
			return EMPTY_FOREIGN_KEYS;
		}
		List<org.eclipse.datatools.modelbase.sql.constraints.ForeignKey> dtpForeignKeys = this.dtpForeignKeys();
		DTPForeignKeyWrapper[] result = new DTPForeignKeyWrapper[dtpForeignKeys.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPForeignKeyWrapper(this, dtpForeignKeys.get(i));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.constraints.ForeignKey> dtpForeignKeys() {
		return ((BaseTable) this.dtpTable).getForeignKeys();
	}

	public int foreignKeysSize() {
		return this.foreignKeys_().length;
	}

	public boolean foreignKeyBaseColumnsContains(Column column) {
		for (Iterator<DTPForeignKeyWrapper> stream = this.foreignKeyWrappers(); stream.hasNext(); ) {
			DTPForeignKeyWrapper foreignKey = stream.next();
			if (foreignKey.baseColumnsContains(column)) {
				return true;
			}
		}
		return false;
	}


	// ********** Comparable implementation **********

	public int compareTo(Table table) {
		return Collator.getInstance().compare(this.getName(), table.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.tables.Table table) {
		return this.dtpTable == table;
	}

	boolean isCaseSensitive() {
		return this.schema.isCaseSensitive();
	}

	DTPTableWrapper table(org.eclipse.datatools.modelbase.sql.tables.Table table) {
		return this.schema.table(table);
	}

	DTPDatabaseWrapper database() {
		return this.schema.database();
	}


	// ********** disposal **********

	@Override
	synchronized void dispose() {
		this.dispose_();
		super.dispose();
	}

	private void dispose_() {
		this.disposeForeignKeys();
		// the table does not "contain" the pk columns, so no need to forward #dispose()
		this.primaryKeyColumns = null;
		this.disposeColumns();
	}

	private void disposeForeignKeys() {
		if (this.foreignKeys != null) {
			for (DTPForeignKeyWrapper foreignKey : this.foreignKeys) {
				foreignKey.dispose();
			}
	    	this.foreignKeys = null;
		}
	}

	private void disposeColumns() {
		if (this.columns != null) {
			for (DTPColumnWrapper column : this.columns) {
				column.dispose();
			}
	    	this.columns = null;
		}
	}

}
