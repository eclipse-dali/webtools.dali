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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  Wrap a DTP ForeignKey
 */
final class DTPForeignKeyWrapper
	extends DTPWrapper
	implements ForeignKey
{
	// backpointer to parent
	private final DTPTableWrapper baseTable;

	// the wrapped DTP foreign key
	private final org.eclipse.datatools.modelbase.sql.constraints.ForeignKey dtpForeignKey;

	// lazy-initialized
	private DTPTableWrapper referencedTable;

	// lazy-initialized
	private LocalColumnPair[] columnPairs;

	// lazy-initialized
	private String defaultEntityFieldName;
	private boolean defaultEntityFieldNameCalculated = false;


	// ********** constructor **********

	DTPForeignKeyWrapper(DTPTableWrapper baseTable, org.eclipse.datatools.modelbase.sql.constraints.ForeignKey dtpForeignKey) {
		super(baseTable, dtpForeignKey);
		this.baseTable = baseTable;
		this.dtpForeignKey = dtpForeignKey;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged(int eventType) {
		// clear stuff so it will be rebuilt
		this.dispose_();
		this.getConnectionProfile().foreignKeyChanged(this, eventType);
	}


	// ********** ForeignKey implementation **********

	@Override
	public String getName() {
		return this.dtpForeignKey.getName();
	}

	public DTPTableWrapper getBaseTable() {
		return this.baseTable;
	}

	public synchronized DTPTableWrapper getReferencedTable() {
		if (this.referencedTable == null) {
			this.referencedTable = this.baseTable.table(this.dtpForeignKey.getUniqueConstraint().getBaseTable());
		}
		return this.referencedTable;
	}

	// ***** column pairs

	public Iterator<ColumnPair> columnPairs() {
		return new ArrayIterator<ColumnPair>(this.columnPairs_());
	}

	public LocalColumnPair columnPair() {
		LocalColumnPair[] pairs = this.columnPairs_();
		if (pairs.length != 1) {
			throw new IllegalStateException("multiple column pairs: " + pairs.length);  //$NON-NLS-1$
		}
		return pairs[0];
	}

	private Iterator<LocalColumnPair> localColumnPairs() {
		return new ArrayIterator<LocalColumnPair>(this.columnPairs_());
	}

	private synchronized LocalColumnPair[] columnPairs_() {
		if (this.columnPairs == null) {
			this.columnPairs = this.buildColumnPairs();
		}
		return this.columnPairs;
	}

	private LocalColumnPair[] buildColumnPairs() {
		List<org.eclipse.datatools.modelbase.sql.tables.Column> baseColumns = this.dtpBaseColumns();
		int size = baseColumns.size();
		List<org.eclipse.datatools.modelbase.sql.tables.Column> refColumns = this.dtpRefColumns();
		if (refColumns.size() != size) {
			throw new IllegalStateException(this.getBaseTable().getName() + "." + this.getName() +  //$NON-NLS-1$
								" - mismatched sizes: " + size + " vs. " + refColumns.size());  //$NON-NLS-1$  //$NON-NLS-2$
		}
		LocalColumnPair[] result = new LocalColumnPair[baseColumns.size()];
		for (int i = baseColumns.size(); i-- > 0; ) {
			DTPColumnWrapper baseColumn = this.baseTable.column(baseColumns.get(i));
			DTPColumnWrapper refColumn = this.baseTable.column(refColumns.get(i));
			result[i] = new LocalColumnPair(baseColumn, refColumn);
		}
		return result;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> dtpBaseColumns() {
		return this.dtpForeignKey.getMembers();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> dtpRefColumns() {
		return this.dtpForeignKey.getUniqueConstraint().getMembers();
	}

	public int columnPairsSize() {
		return this.columnPairs_().length;
	}

	public Iterator<Column> baseColumns() {
		return new TransformationIterator<LocalColumnPair, Column>(this.localColumnPairs()) {
			@Override
			protected Column transform(LocalColumnPair pair) {
				return pair.baseColumn();
			}
		};
	}

	boolean baseColumnsContains(Column column) {
		return CollectionTools.contains(this.baseColumns(), column);
	}

	public Iterator<Column> nonPrimaryKeyBaseColumns() {
		return new FilteringIterator<Column, Column>(this.baseColumns()) {
			@Override
			protected boolean accept(Column column) {
				return ! DTPForeignKeyWrapper.this.getBaseTable().primaryKeyColumnsContains(column);
			}
		};
	}

	public Iterator<Column> referencedColumns() {
		return new TransformationIterator<LocalColumnPair, Column>(this.localColumnPairs()) {
			@Override
			protected Column transform(LocalColumnPair columnPair) {
				return columnPair.referencedColumn();
			}
		};
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
	private synchronized String defaultEntityFieldName() {
		if ( ! this.defaultEntityFieldNameCalculated) {
			this.defaultEntityFieldNameCalculated = true;
			this.defaultEntityFieldName = this.buildDefaultEntityFieldName();
		}
		return this.defaultEntityFieldName;
	}

	/**
	 * @see #defaultEntityFieldName()
	 */
	private String buildDefaultEntityFieldName() {
		if ( ! this.referencesSingleColumnPrimaryKey()) {
			return null;
		}
		LocalColumnPair columnPair = this.columnPairs_()[0];
		String baseColName = columnPair.baseColumn().getName();
		String refColName = columnPair.referencedColumn().getName();
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

	// ***** miscellaneous

	public String getJavaFieldName() {
		String fieldName = this.defaultEntityFieldName();
		return (fieldName != null) ? fieldName : this.nonDefaultEntityFieldName();
	}

	public boolean defaultMatchesJavaFieldName(String javaFieldName) {
		return this.isCaseSensitive() ?
			javaFieldName.equals(this.defaultEntityFieldName())
		:
			javaFieldName.equalsIgnoreCase(this.defaultEntityFieldName());
	}

	public boolean isDefaultFor(String javaFieldName) {
		if (this.columnPairsSize() != 1) {
			return false;
		}

		if (this.getReferencedTable().primaryKeyColumnsSize() != 1) {
			return false;
		}

		LocalColumnPair columnPair = this.columnPairs_()[0];
		Column pkColumn = this.getReferencedTable().primaryKeyColumn();
		if (columnPair.referencedColumn() != pkColumn) {
			return false;
		}

		return columnPair.baseColumn().matchesJavaFieldName(javaFieldName + "_" + pkColumn.getName());  //$NON-NLS-1$
	}

	public boolean referencesSingleColumnPrimaryKey() {
		if (this.columnPairsSize() != 1) {
			return false;
		}
		if (this.getReferencedTable().primaryKeyColumnsSize() != 1) {
			return false;
		}

		return this.columnPair().referencedColumn() == this.getReferencedTable().primaryKeyColumn();
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
			this.columnPair().baseColumn().getJavaFieldName()
		:
			this.getReferencedTable().getJavaFieldName();
	}


	// ********** Comparable implementation **********

	public int compareTo(ForeignKey foreignKey) {
		return Collator.getInstance().compare(this.getName(), foreignKey.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.constraints.ForeignKey foreignKey) {
		return this.dtpForeignKey == foreignKey;
	}

	boolean isCaseSensitive() {
		return this.baseTable.isCaseSensitive();
	}

	DTPDatabaseWrapper database() {
		return this.baseTable.database();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getName() + ": " + Arrays.asList(this.columnPairs_()));  //$NON-NLS-1$
	}


	// ********** disposal **********

	@Override
	synchronized void dispose() {
		this.dispose_();
		super.dispose();
	}

	private void dispose_() {
		// the foreign key does not "contain" any other objects,
		// so we don't need to forward the #dispose()
		this.defaultEntityFieldNameCalculated = false;
		this.defaultEntityFieldName = null;
		this.columnPairs =  null;
		this.referencedTable = null;
	}


	// ********** column pair implementation **********

	private static class LocalColumnPair implements ColumnPair {
		private final DTPColumnWrapper baseColumn;
		private final DTPColumnWrapper referencedColumn;

		LocalColumnPair(DTPColumnWrapper baseColumn, DTPColumnWrapper referencedColumn) {
			super();
			this.baseColumn = baseColumn;
			this.referencedColumn = referencedColumn;
		}


		// ********** ColumnPair implementation **********

		public DTPColumnWrapper baseColumn() {
			return this.baseColumn;
		}

		public DTPColumnWrapper referencedColumn() {
			return this.referencedColumn;
		}


		// ********** Comparable implementation **********

		public int compareTo(ColumnPair columnPair) {
			return Collator.getInstance().compare(this.baseColumn().getName(), columnPair.baseColumn().getName());
		}


		// ********** Object overrides **********

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.baseColumn.getName() + "=>" + this.referencedColumn.getName());  //$NON-NLS-1$
		}

	}

}
