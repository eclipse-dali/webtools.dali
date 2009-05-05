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

import com.ibm.icu.text.Collator;
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
	extends DTPDatabaseObjectWrapper
	implements ForeignKey
{
	// the wrapped DTP foreign key
	private final org.eclipse.datatools.modelbase.sql.constraints.ForeignKey dtpForeignKey;

	// lazy-initialized
	private DTPTableWrapper referencedTable;

	// lazy-initialized
	private LocalColumnPair[] columnPairs;

	// lazy-initialized - but it can be 'null' so we use a flag
	private String defaultAttributeName;
	private boolean defaultAttributeNameCalculated = false;


	// ********** constructor **********

	DTPForeignKeyWrapper(DTPTableWrapper baseTable, org.eclipse.datatools.modelbase.sql.constraints.ForeignKey dtpForeignKey) {
		super(baseTable, dtpForeignKey);
		this.dtpForeignKey = dtpForeignKey;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().foreignKeyChanged(this);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getName() + ": " + Arrays.asList(this.getColumnPairs()));  //$NON-NLS-1$
	}


	// ********** ForeignKey implementation **********

	public String getName() {
		return this.dtpForeignKey.getName();
	}

	public DTPTableWrapper getBaseTable() {
		return (DTPTableWrapper) this.getParent();
	}

	public synchronized DTPTableWrapper getReferencedTable() {
		if (this.referencedTable == null) {
			this.referencedTable = this.getBaseTable().getTable(this.dtpForeignKey.getUniqueConstraint().getBaseTable());
		}
		return this.referencedTable;
	}

	public boolean referencesSingleColumnPrimaryKey() {
		if (this.columnPairsSize() != 1) {
			return false;
		}
		if (this.getReferencedTable().primaryKeyColumnsSize() != 1) {
			return false;
		}
		return this.getColumnPair().getReferencedColumn() == this.getReferencedTable().getPrimaryKeyColumn();
	}

	// ***** column pairs

	public Iterator<ColumnPair> columnPairs() {
		return new ArrayIterator<ColumnPair>(this.getColumnPairs());
	}

	public LocalColumnPair getColumnPair() {
		LocalColumnPair[] pairs = this.getColumnPairs();
		if (pairs.length != 1) {
			throw new IllegalStateException("multiple column pairs: " + pairs.length);  //$NON-NLS-1$
		}
		return pairs[0];
	}

	private Iterator<LocalColumnPair> localColumnPairs() {
		return new ArrayIterator<LocalColumnPair>(this.getColumnPairs());
	}

	private synchronized LocalColumnPair[] getColumnPairs() {
		if (this.columnPairs == null) {
			this.columnPairs = this.buildColumnPairs();
		}
		return this.columnPairs;
	}

	private LocalColumnPair[] buildColumnPairs() {
		List<org.eclipse.datatools.modelbase.sql.tables.Column> baseColumns = this.getDTPBaseColumns();
		int size = baseColumns.size();
		List<org.eclipse.datatools.modelbase.sql.tables.Column> refColumns = this.getDTPReferenceColumns();
		if (refColumns.size() != size) {
			throw new IllegalStateException(this.getBaseTable().getName() + '.' + this.getName() +
								" - mismatched sizes: " + size + " vs. " + refColumns.size());  //$NON-NLS-1$  //$NON-NLS-2$
		}
		LocalColumnPair[] result = new LocalColumnPair[baseColumns.size()];
		for (int i = baseColumns.size(); i-- > 0; ) {
			result[i] = new LocalColumnPair(
								this.getBaseTable().getColumn(baseColumns.get(i)),
								this.getBaseTable().getColumn(refColumns.get(i))
						);
		}
		return result;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> getDTPBaseColumns() {
		return this.dtpForeignKey.getMembers();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> getDTPReferenceColumns() {
		return this.dtpForeignKey.getUniqueConstraint().getMembers();
	}

	public int columnPairsSize() {
		return this.getColumnPairs().length;
	}

	public Iterator<Column> baseColumns() {
		return new TransformationIterator<LocalColumnPair, Column>(this.localColumnPairs()) {
			@Override
			protected Column transform(LocalColumnPair pair) {
				return pair.getBaseColumn();
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
				return ! column.isPartOfPrimaryKey();
			}
		};
	}

	public Iterator<Column> referencedColumns() {
		return new TransformationIterator<LocalColumnPair, Column>(this.localColumnPairs()) {
			@Override
			protected Column transform(LocalColumnPair columnPair) {
				return columnPair.getReferencedColumn();
			}
		};
	}

	// ***** attribute name

	public String getAttributeName() {
		String defaultName = this.getDefaultAttributeName();
		return (defaultName != null) ? defaultName : this.getNonDefaultAttributeName();
	}

	public synchronized String getDefaultAttributeName() {
		if ( ! this.defaultAttributeNameCalculated) {
			this.defaultAttributeNameCalculated = true;
			this.defaultAttributeName = this.buildDefaultAttributeName();
		}
		return this.defaultAttributeName;
	}

	private String buildDefaultAttributeName() {
		if ( ! this.referencesSingleColumnPrimaryKey()) {
			return null;
		}
		LocalColumnPair columnPair = this.getColumnPair();
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
		return baseColName.substring(0, _index);
	}

	/**
	 * If this is a simple (single-column) foreign key, use the name of the
	 * single base column to build a name. If this is a compound foreign key,
	 * return the name of the referenced table.
	 */
	// TODO if there is only one FK to a given table, use the table's name instead of the column's name?
	private String getNonDefaultAttributeName() {
		return (this.columnPairsSize() == 1) ?
						this.getNonDefaultAttributeNameFromBaseColumn()
					:
						this.getReferencedTable().getName();
	}

	/**
	 * The underscore check is helpful when the referenced column is NOT the
	 * primary key of the referenced table (i.e. it has only a UNIQUE constraint).
	 *     ForeignKey(EMP.CUBICLE_ID => CUBICLE.ID) => "CUBICLE"
	 *     ForeignKey(EMP.CUBICLEID => CUBICLE.ID) => "CUBICLE"
	 *     ForeignKey(EMP.CUBICLE_PK => CUBICLE.ID) => "CUBICLE_PK"
	 */
	private String getNonDefaultAttributeNameFromBaseColumn() {
		LocalColumnPair columnPair = this.getColumnPair();
		String baseColName = columnPair.getBaseColumn().getName();
		String refColName = columnPair.getReferencedColumn().getName();
		int len = baseColName.length();
		int refLen = refColName.length();
		if ((len > refLen) && baseColName.endsWith(refColName)) {
			len = len - refLen;
			if ((len > 1) && baseColName.charAt(len - 1) == '_') {
				len = len - 1;
			}
		}
		return baseColName.substring(0, len);
	}

	/**
	 * Examples:
	 * Oracle etc.
	 *     ForeignKey(FOO_ID => ID) vs. "foo" => null
	 *     ForeignKey(FOO_ID => FOO_ID) vs. "foo" => "FOO_ID"
	 *     ForeignKey(FOO => ID) vs. "foo" => "FOO"
	 *     ForeignKey(Foo_ID => ID) vs. "foo" => "\"Foo_ID\""
	 *     
	 * PostgreSQL etc.
	 *     ForeignKey(foo_id => id) vs. "foo" => null
	 *     ForeignKey(foo_id => foo_id) vs. "foo" => "foo_id"
	 *     ForeignKey(foo => id) vs. "foo" => "foo"
	 *     ForeignKey(Foo_ID => ID) vs. "foo" => "\"Foo_ID\""
	 *     
	 * SQL Server etc.
	 *     ForeignKey(foo_ID => ID) vs. "foo" => null
	 *     ForeignKey(FOO_ID => FOO_ID) vs. "foo" => "FOO_ID"
	 *     ForeignKey(FOO => ID) vs. "foo" => "FOO"
	 *     ForeignKey(Foo_ID => ID) vs. "foo" => "Foo_ID"
	 */
	public String getJoinColumnAnnotationIdentifier(String attributeName) {
		String baseColumnName = this.getColumnPair().getBaseColumn().getName();
		String defaultBaseColumnName = attributeName + '_' + this.getReferencedTable().getPrimaryKeyColumn().getName();
		return this.getDatabase().convertNameToIdentifier(baseColumnName, defaultBaseColumnName);
	}


	// ********** Comparable implementation **********

	public int compareTo(ForeignKey foreignKey) {
		return Collator.getInstance().compare(this.getName(), foreignKey.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.constraints.ForeignKey foreignKey) {
		return this.dtpForeignKey == foreignKey;
	}

	@Override
	void clear() {
		// the foreign key does not "contain" any other objects,
		// so we don't need to forward the #clear()
		this.defaultAttributeNameCalculated = false;
		this.defaultAttributeName = null;
		this.columnPairs =  null;
		this.referencedTable = null;
	}


	// ********** column pair implementation **********

	private static class LocalColumnPair implements ColumnPair {
		private final DTPColumnWrapper baseColumn;
		private final DTPColumnWrapper referencedColumn;

		LocalColumnPair(DTPColumnWrapper baseColumn, DTPColumnWrapper referencedColumn) {
			super();
			if ((baseColumn == null) || (referencedColumn == null)) {
				throw new NullPointerException();
			}
			this.baseColumn = baseColumn;
			this.referencedColumn = referencedColumn;
		}

		public DTPColumnWrapper getBaseColumn() {
			return this.baseColumn;
		}

		public DTPColumnWrapper getReferencedColumn() {
			return this.referencedColumn;
		}

		public int compareTo(ColumnPair columnPair) {
			return Collator.getInstance().compare(this.getBaseColumn().getName(), columnPair.getBaseColumn().getName());
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.baseColumn.getName() + "=>" + this.referencedColumn.getName());  //$NON-NLS-1$
		}

	}

}
