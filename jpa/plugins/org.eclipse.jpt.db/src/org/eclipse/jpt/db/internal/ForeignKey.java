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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  Wrap a DTP ForeignKey
 */
public final class ForeignKey extends DTPWrapper {
	private final Table baseTable;
	private final org.eclipse.datatools.modelbase.sql.constraints.ForeignKey dtpForeignKey;
	private ICatalogObjectListener foreignKeyListener;

	private Table referencedTable;  // lazy-initialized
	private Set<ColumnPair> columnPairs;  // lazy-initialized
	private String defaultEntityFieldName;  // lazy-initialized
	private boolean defaultEntityFieldNameCalculated = false;


	// ********** constructors **********

	ForeignKey(Table baseTable, org.eclipse.datatools.modelbase.sql.constraints.ForeignKey dtpForeignKey) {
		super();
		this.baseTable = baseTable;
		this.dtpForeignKey = dtpForeignKey;
		this.initialize();
	}

	// ********** behavior **********

	private void initialize() {
		if( this.connectionIsOnline()) {
			this.foreignKeyListener = this.buildForeignKeyListener();
			this.addCatalogObjectListener(( ICatalogObject) this.dtpForeignKey, this.foreignKeyListener);
		}
	}
	
	protected boolean connectionIsOnline() {
		return this.baseTable.connectionIsOnline();
	}
	
	private ICatalogObjectListener buildForeignKeyListener() {
       return new ICatalogObjectListener() {
    	    public void notifyChanged( final ICatalogObject foreignKey, final int eventType) { 
//				TODO
//    			if( foreignKey == ForeignKey.this.dtpForeignKey) {	    	    	
//    				ForeignKey.this.baseTable.foreignKeyChanged( ForeignKey.this, eventType);
//    			}
    	    }
        };
    }

	protected void dispose() {
		
		this.removeCatalogObjectListener(( ICatalogObject) this.dtpForeignKey, this.foreignKeyListener);
	}

	// ********** queries **********

	public Table getBaseTable() {
		return this.baseTable;
	}

	@Override
	public String getName() {
		return this.dtpForeignKey.getName();
	}

	boolean isCaseSensitive() {
		return this.baseTable.isCaseSensitive();
	}

	boolean wraps(org.eclipse.datatools.modelbase.sql.constraints.ForeignKey foreignKey) {
		return this.dtpForeignKey == foreignKey;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getName() + ": " + this.getColumnPairs());
	}

	public Table getReferencedTable() {
		if (this.referencedTable == null) {
			this.referencedTable = this.baseTable.table(this.dtpForeignKey.getUniqueConstraint().getBaseTable());
		}
		return this.referencedTable;
	}

	/**
	 * return the foreign key's "base" columns
	 */
	public Iterator<Column> baseColumns() {
		return new TransformationIterator<ColumnPair, Column>(this.columnPairs()) {
			@Override
			protected Column transform(ColumnPair pair) {
				return pair.getBaseColumn();
			}
		};
	}

	/**
	 * return the foreign key's "base" columns that are not part of
	 * the base table's primary key
	 */
	public Iterator<Column> nonPrimaryKeyBaseColumns() {
		return new FilteringIterator(this.baseColumns()) {
			@Override
			protected boolean accept(Object o) {
				return ! ForeignKey.this.getBaseTable().primaryKeyColumnsContains((Column) o);
			}
		};
	}

	/**
	 * return the foreign key's "referenced" columns
	 */
	public Iterator<Column> referencedColumns() {
		return new TransformationIterator(this.columnPairs()) {
			@Override
			protected Object transform(Object next) {
				return ((ColumnPair) next).getReferencedColumn();
			}
		};
	}

	public String javaFieldName() {
		String fieldName = this.getDefaultEntityFieldName();
		return (fieldName == null) ?
			this.nonDefaultEntityFieldName()
		:
			fieldName;
	}

	public boolean matchesJavaFieldName(String javaFieldName) {
		return this.isCaseSensitive() ?
			javaFieldName.equals(this.getDefaultEntityFieldName())
		:
			javaFieldName.equalsIgnoreCase(this.getDefaultEntityFieldName());
	}

	public boolean isDefaultFor(String javaFieldName) {
		if (this.columnPairsSize() != 1) {
			return false;
		}

		if (this.getReferencedTable().primaryKeyColumnsSize() != 1) {
			return false;
		}

		ColumnPair columnPair = this.columnPairs().next();
		Column pkColumn = (Column) this.getReferencedTable().primaryKeyColumns().next();
		if (columnPair.getReferencedColumn() != pkColumn) {
			return false;
		}

		return columnPair.getBaseColumn().matchesJavaFieldName(javaFieldName + "_" + pkColumn.getName());
	}


	// ***** column pairs

	private synchronized Set<ColumnPair> getColumnPairs() {
		if (this.columnPairs == null) {
			this.columnPairs = this.buildColumnPairs();
		}
		return this.columnPairs;
	}

	private Set<ColumnPair> buildColumnPairs() {
		List<org.eclipse.datatools.modelbase.sql.tables.Column> baseColumns = this.dtpForeignKey.getMembers();
		int size = baseColumns.size();
		List<org.eclipse.datatools.modelbase.sql.tables.Column> refColumns = this.dtpForeignKey.getUniqueConstraint().getMembers();
		if (refColumns.size() != size) {
			throw new IllegalStateException("mismatched sizes: " + size + " vs. " + refColumns.size());
		}
		Set<ColumnPair> result = new HashSet<ColumnPair>(baseColumns.size());
		for (int i = baseColumns.size(); i-- > 0; ) {
			Column baseColumn = this.baseTable.column(baseColumns.get(i));
			Column refColumn = this.baseTable.column(refColumns.get(i));
			result.add(new ColumnPair(baseColumn, refColumn));
		}
		return result;
	}

	public Iterator<ColumnPair> columnPairs() {
		return this.getColumnPairs().iterator();
	}

	public int columnPairsSize() {
		return this.getColumnPairs().size();
	}


	// ***** default entity field name

	/**
	 * If the name of the "base" column adheres to the EJB standard for a
	 * default mapping (i.e. it ends with an underscore followed by the name
	 * of the "referenced" column, and the "referenced" column is the single
	 * primary key column of the "referenced" table), return the corresponding
	 * default entity field name:
	 *     ForeignKey(EMP.CUBICLE_ID => CUBICLE.ID) => "CUBICLE"
	 * Return a null if it does not adhere to the EJB standard:
	 *     ForeignKey(EMP.CUBICLE_ID => CUBICLE.CUBICLE_ID) => null
	 */
	private String getDefaultEntityFieldName() {
		if ( ! this.defaultEntityFieldNameCalculated) {
			this.defaultEntityFieldNameCalculated = true;
			this.defaultEntityFieldName = this.buildDefaultEntityFieldName();
		}
		return this.defaultEntityFieldName;
	}

	/**
	 * @see #getDefaultEntityFieldName()
	 */
	private String buildDefaultEntityFieldName() {
		if ( ! this.referencesSingleColumnPrimaryKey()) {
			return null;
		}
		ColumnPair columnPair = this.columnPairs().next();
		String baseColName = columnPair.getBaseColumn().getName();
		String refColName = columnPair.getReferencedColumn().getName();
		if (baseColName.length() <= (refColName.length() + 1)) {
			return null;
		}
		if ( ! baseColName.endsWith(refColName)) {
			return null;
		}
		int _index = baseColName.length() - refColName.length() - 1;
		if (baseColName.charAt(_index) != '_') {
			return null;
		}
		String name = baseColName.substring(0, _index);
		return this.isCaseSensitive() ? name : name.toLowerCase();
	}

	/**
	 * Return whether the foreign key references the primary key of the
	 * "referenced" table and that primary key has only a single column.
	 */
	public boolean referencesSingleColumnPrimaryKey() {
		if (this.columnPairsSize() != 1) {
			return false;
		}
		if (this.getReferencedTable().primaryKeyColumnsSize() != 1) {
			return false;
		}

		ColumnPair columnPair = this.columnPairs().next();
		return columnPair.getReferencedColumn() == this.getReferencedTable().primaryKeyColumns().next();
	}

	/**
	 * If this is a simple (single-column) foreign key, return the java field
	 * name of the single base column. If this is a compound foreign key,
	 * return the java field name of the referenced table.
	 */
	// TODO if there is only one FK to a given table, use the table's name instead of the column's name?
	// TODO if the FK column name ends with the PK column name, strip the PK column name?
	private String nonDefaultEntityFieldName() {
		return (this.columnPairsSize() == 1) ?
			this.columnPairs().next().getBaseColumn().javaFieldName()
		:
			this.getReferencedTable().javaFieldName();
	}


	// ********** Comparable implementation **********

	public int compareTo(Object o) {
		return Collator.getInstance().compare(this.getName(), ((ForeignKey) o).getName());
	}


	// ********** member class **********

	public static class ColumnPair implements Comparable<ColumnPair> {
		private final Column baseColumn;
		private final Column referencedColumn;

		ColumnPair(Column baseColumn, Column referencedColumn) {
			super();
			this.baseColumn = baseColumn;
			this.referencedColumn = referencedColumn;
		}

		public Column getBaseColumn() {
			return this.baseColumn;
		}

		public Column getReferencedColumn() {
			return this.referencedColumn;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, baseColumn.getName() + "=>" + this.referencedColumn.getName());
		}

		public int compareTo(ColumnPair cp) {
			return Collator.getInstance().compare(this.getBaseColumn().getName(), cp.getBaseColumn().getName());
		}

	}

}