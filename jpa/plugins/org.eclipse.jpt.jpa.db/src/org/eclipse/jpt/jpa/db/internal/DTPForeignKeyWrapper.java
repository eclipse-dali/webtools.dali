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

import java.util.Arrays;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ForeignKey;

/**
 *  Wrap a DTP ForeignKey
 */
final class DTPForeignKeyWrapper
	extends DTPDatabaseObjectWrapper<DTPTableWrapper, org.eclipse.datatools.modelbase.sql.constraints.ForeignKey>
	implements ForeignKey
{
	/** lazy-initialized */
	private DTPTableWrapper referencedTable;

	/** lazy-initialized */
	private LocalColumnPair[] columnPairs;

	/** lazy-initialized - but it can be 'null' so we use a flag */
	private String defaultAttributeName;
	private boolean defaultAttributeNameCalculated = false;


	// ********** constructor **********

	DTPForeignKeyWrapper(DTPTableWrapper baseTable, org.eclipse.datatools.modelbase.sql.constraints.ForeignKey dtpForeignKey) {
		super(baseTable, dtpForeignKey);
	}


	// ********** DTPDatabaseObjectWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().foreignKeyChanged(this);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.getName() + ": " + Arrays.asList(this.getColumnPairArray()));  //$NON-NLS-1$
	}


	// ********** ForeignKey implementation **********

	public DTPTableWrapper getBaseTable() {
		return this.parent;
	}

	public synchronized DTPTableWrapper getReferencedTable() {
		if (this.referencedTable == null) {
			this.referencedTable = this.getBaseTable().getTable(this.dtpObject.getUniqueConstraint().getBaseTable());
		}
		return this.referencedTable;
	}

	public boolean referencesSingleColumnPrimaryKey() {
		if (this.getColumnPairsSize() != 1) {
			return false;
		}
		if (this.getReferencedTable().getPrimaryKeyColumnsSize() != 1) {
			return false;
		}
		return this.getColumnPair().getReferencedColumn() == this.getReferencedTable().getPrimaryKeyColumn();
	}

	// ***** column pairs

	public Iterable<ColumnPair> getColumnPairs() {
		return IterableTools.<ColumnPair>iterable(this.getColumnPairArray());
	}

	public LocalColumnPair getColumnPair() {
		LocalColumnPair[] pairs = this.getColumnPairArray();
		if (pairs.length != 1) {
			throw new IllegalStateException("multiple column pairs: " + pairs.length);  //$NON-NLS-1$
		}
		return pairs[0];
	}

	private Iterable<LocalColumnPair> getLocalColumnPairs() {
		return IterableTools.iterable(this.getColumnPairArray());
	}

	private synchronized LocalColumnPair[] getColumnPairArray() {
		if (this.columnPairs == null) {
			this.columnPairs = this.buildColumnPairArray();
		}
		return this.columnPairs;
	}

	private LocalColumnPair[] buildColumnPairArray() {
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
		return ArrayTools.sort(result, ColumnPair.BASE_COLUMN_COMPARATOR);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> getDTPBaseColumns() {
		return this.dtpObject.getMembers();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.tables.Column> getDTPReferenceColumns() {
		return this.dtpObject.getUniqueConstraint().getMembers();
	}

	public int getColumnPairsSize() {
		return this.getColumnPairArray().length;
	}

	public Iterable<Column> getBaseColumns() {
		return IterableTools.transform(this.getLocalColumnPairs(), LocalColumnPair.BASE_COLUMN_TRANSFORMER);
	}

	boolean baseColumnsContains(Column column) {
		return IterableTools.contains(this.getBaseColumns(), column);
	}

	public Iterable<Column> getNonPrimaryKeyBaseColumns() {
		return IterableTools.filter(this.getBaseColumns(), IS_NOT_PART_OF_PRIMARY_KEY);
	}
	private static final Predicate<Column> IS_NOT_PART_OF_PRIMARY_KEY = PredicateTools.not(Column.IS_PART_OF_PRIMARY_KEY);

	public Iterable<Column> getReferencedColumns() {
		return IterableTools.transform(this.getLocalColumnPairs(), LocalColumnPair.REFERENCED_COLUMN_TRANSFORMER);
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
		return (this.getColumnPairsSize() == 1) ?
						this.getNonDefaultAttributeNameFromBaseColumn() :
						this.getReferencedTable().getName();
	}

	/**
	 * The underscore check is helpful when the referenced column is <em>not</em> the
	 * primary key of the referenced table (i.e. it has only a <em>unique</em> constraint).
	 * <pre>
	 *     ForeignKey(EMP.CUBICLE_ID => CUBICLE.ID) => "CUBICLE"
	 *     ForeignKey(EMP.CUBICLEID  => CUBICLE.ID) => "CUBICLE"
	 *     ForeignKey(EMP.CUBICLE_PK => CUBICLE.ID) => "CUBICLE_PK"
	 * </pre>
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
	 * Examples:<ul>
	 * <li>Oracle etc.<ul><code>
	 *     <li>ForeignKey(FOO_ID => ID) vs. "foo" => null
	 *     <li>ForeignKey(FOO_ID => FOO_ID) vs. "foo" => "FOO_ID"
	 *     <li>ForeignKey(FOO => ID) vs. "foo" => "FOO"
	 *     <li>ForeignKey(Foo_ID => ID) vs. "foo" => "\"Foo_ID\""
	 * </code></ul>
	 * <li>PostgreSQL etc.<ul><code>
	 *     <li>ForeignKey(foo_id => id) vs. "foo" => null
	 *     <li>ForeignKey(foo_id => foo_id) vs. "foo" => "foo_id"
	 *     <li>ForeignKey(foo => id) vs. "foo" => "foo"
	 *     <li>ForeignKey(Foo_ID => ID) vs. "foo" => "\"Foo_ID\""
	 * </code></ul>
	 * <li>SQL Server etc.<ul><code>
	 *     <li>ForeignKey(foo_ID => ID) vs. "foo" => null
	 *     <li>ForeignKey(FOO_ID => FOO_ID) vs. "foo" => "FOO_ID"
	 *     <li>ForeignKey(FOO => ID) vs. "foo" => "FOO"
	 *     <li>ForeignKey(Foo_ID => ID) vs. "foo" => "Foo_ID"
	 * </code></ul>
	 * </ul>
	 */
	public String getJoinColumnAnnotationIdentifier(String attributeName) {
		String baseColumnName = this.getColumnPair().getBaseColumn().getName();
		String defaultBaseColumnName = attributeName + '_' + this.getReferencedTable().getPrimaryKeyColumn().getName();
		return this.getDTPDriverAdapter().convertNameToIdentifier(baseColumnName, defaultBaseColumnName);
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.constraints.ForeignKey foreignKey) {
		return this.dtpObject == foreignKey;
	}

	@Override
	synchronized void clear() {
		// the foreign key does not "contain" any other objects,
		// so we don't need to forward the #clear()
		this.defaultAttributeNameCalculated = false;
		this.defaultAttributeName = null;
		this.columnPairs =  null;
		this.referencedTable = null;
	}


	// ********** column pair implementation **********

	static class LocalColumnPair implements ColumnPair {
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
		public static final Transformer<LocalColumnPair, Column> BASE_COLUMN_TRANSFORMER = new BaseColumnTransformer();
		public static class BaseColumnTransformer
			extends TransformerAdapter<LocalColumnPair, Column>
		{
			@Override
			public Column transform(LocalColumnPair pair) {
				return pair.getBaseColumn();
			}
		}

		public DTPColumnWrapper getReferencedColumn() {
			return this.referencedColumn;
		}
		public static final Transformer<LocalColumnPair, Column> REFERENCED_COLUMN_TRANSFORMER = new ReferencedColumnTransformer();
		public static class ReferencedColumnTransformer
			extends TransformerAdapter<LocalColumnPair, Column>
		{
			@Override
			public Column transform(LocalColumnPair pair) {
				return pair.getReferencedColumn();
			}
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.baseColumn.getName() + "=>" + this.referencedColumn.getName());  //$NON-NLS-1$
		}

	}
}
