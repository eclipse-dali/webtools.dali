/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ListIterator;

import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.node.AbstractNode;
import org.eclipse.jpt.utility.internal.node.Node;

/**
 * The abstract definition of a state object used to edit or create a new
 * join column.
 *
 * @see AbstractJoinColumn
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class BaseJoinColumnStateObject extends AbstractNode
{
	/**
	 * The SQL fragment that is used when generating the DDL for the column.
	 */
	private String columnDefinition;

	/**
	 * Either the join column is being edited or <code>null</code> the state
	 * object is being created.
	 */
	private BaseJoinColumn joinColumn;

	/**
	 * The join column's name or <code>null</code> if not defined.
	 */
	private String name;

	/**
	 * The owner of the join column to create or where it is located.
	 */
	private Object owner;

	/**
	 * The referenced column name or <code>null</code> if not defined.
	 */
	private String referencedColumnName;

	/**
	 * The table
	 */
	private String table;

	/**
	 * Keeps track of the <code>Validator</code> since this is the root object.
	 */
	private Validator validator;

	/**
	 * Identifies a change in the column definition property.
	 */
	public static final String COLUMN_DEFINITION_PROPERTY = "columnDefinition";

	/**
	 * Identifies a change in the name property.
	 */
	public static final String NAME_PROPERTY = "name";

	/**
	 * Identifies a change in the list of names.
	 */
	public static final String NAMES_LIST = "names";

	/**
	 * Identifies a change in the list of reference column names.
	 */
	public static final String REFERENCE_COLUMN_NAMES_LIST = "referenceColumnNames";

	/**
	 * Identifies a change in the referenced column name property.
	 */
	public static final String REFERENCED_COLUMN_NAME_PROPERTY = "referencedColumnName";

	/**
	 * Identifies a change in the table property.
	 */
	public static final String TABLE_PROPERTY = "table";

	/**
	 * Creates a new <code>AbstractJoinColumnStateObject</code>.
	 *
	 * @param owner The owner of the join column to create or where it is located
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public BaseJoinColumnStateObject(Object owner, BaseJoinColumn joinColumn) {
		super(null);
		initialize(owner, joinColumn);
	}

	@Override
	protected final void checkParent(Node parentNode) {
		// This is the root of the Join Column state object
	}

	private static ListIterator<String> columnNames(Table table) {
		if (table == null) {
			return EmptyListIterator.instance();
		}
		return CollectionTools.list(table.sortedColumnIdentifiers()).listIterator();
	}
	
	private static int columnsSize(Table table) {
		if (table == null) {
			return 0;
		}
		return table.columnsSize();
	}
	
	public final String displayString() {
		return "";
	}

	/**
	 * Returns the SQL fragment that is used when generating the DDL for the
	 * column.
	 *
	 * @return The edited column name or <code>null</code> if not used
	 */
	public String getColumnDefinition() {
		return columnDefinition;
	}

	/**
	 * Returns the default name if the join column is being edited otherwise
	 * <code>null</code> is returned.
	 *
	 * @return Either the default name defined by the join column or <code>null</code>
	 */
	public String getDefaultName() {
		if (this.joinColumn == null) {
			return null;
		}

		return this.joinColumn.getDefaultName();
	}

	/**
	 * Returns the default referenced column name if the join column is being
	 * edited otherwise <code>null</code> is returned.
	 *
	 * @return Either the default referenced column name defined by the join
	 * column or <code>null</code>
	 */
	public String getDefaultReferencedColumnName() {
		if (this.joinColumn == null) {
			return null;
		}

		return this.joinColumn.getDefaultReferencedColumnName();
	}

	/**
	 * Returns
	 *
	 * @return
	 */
	public abstract String getDefaultTable();

	/**
	 * Returns the edited join column or <code>null</code> if this state object
	 * is used to create a new one.
	 *
	 * @return The edited join column or <code>null</code>
	 */
	public BaseJoinColumn getJoinColumn() {
		return this.joinColumn;
	}

	/**
	 * Returns the name of the join column.
	 *
	 * @return Either join column's name or <code>null</code> to use the default
	 * name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the database table if one can be found.
	 *
	 * @return The database table
	 */
	public abstract Table getNameTable();

	/**
	 * Returns the owner where the join column is located or where a new one can
	 * be added.
	 *
	 * @return The parent of the join column
	 */
	public Object getOwner() {
		return owner;
	}

	/**
	 * Returns the referenced column name of the join column.
	 *
	 * @return Either join column's referenced column name or <code>null</code>
	 * to use the default name
	 */
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}

	/**
	 * Returns
	 *
	 * @return
	 */
	public abstract Table getReferencedNameTable();

	/**
	 * Returns
	 *
	 * @return
	 */
	public String getTable() {
		return table;
	}

	@Override
	public final Validator getValidator() {
		return this.validator;
	}

	@Override
	protected void initialize() {
		super.initialize();
		validator = NULL_VALIDATOR;
	}

	/**
	 * Initializes this state object.
	 *
	 * @param owner The owner of the join column to create or where it is located
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	protected void initialize(Object o, BaseJoinColumn jc) {

		this.owner      = o;
		this.joinColumn = jc;
		this.table      = this.getInitialTable();

		if (jc != null) {
			this.name                 = jc.getSpecifiedName();
			this.columnDefinition     = jc.getColumnDefinition();
			this.referencedColumnName = jc.getSpecifiedReferencedColumnName();
		}
	}

	/**
	 * Returns
	 */
	protected abstract String getInitialTable();

	/**
	 * Returns the column names if the database table can be resolved.
	 *
	 * @return The names of the table's columns or an empty iterator if the table
	 * can't be resolved
	 */
	public ListIterator<String> names() {
		return columnNames(getNameTable());
	}

	public int columnsSize() {
		return columnsSize(getNameTable());
	}
	
	/**
	 * Returns the reference column names if the database table can be resolved.
	 *
	 * @return The names of the table's columns or an empty iterator if the table
	 * can't be resolved
	 */
	public ListIterator<String> referenceColumnNames() {
		return columnNames(getReferencedNameTable());
	}
	
	public int referenceColumnsSize() {
		return columnsSize(getReferencedNameTable());
	}

	/**
	 * Sets the SQL fragment that is used when generating the DDL for the column.
	 *
	 * @param columnDefinition The new join column's column definition or
	 * <code>null</code> to clear the value
	 */
	public void setColumnDefinition(String columnDefinition) {
		String oldColumnDefinition = this.columnDefinition;
		this.columnDefinition = columnDefinition;
		firePropertyChanged(COLUMN_DEFINITION_PROPERTY, oldColumnDefinition, columnDefinition);
	}

	/**
	 * Sets the name of the join column.
	 *
	 * @param name The new join column's name or <code>null</code> to use the
	 * default name
	 */
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		firePropertyChanged(NAME_PROPERTY, oldName, name);
	}

	/**
	 * Sets the referenced column name of the join column.
	 *
	 * @param referencedColumnName The new join column's referenced column name
	 * or <code>null</code> to use the default referenced column name
	 */
	public void setReferencedColumnName(String referencedColumnName) {
		String oldReferencedColumnName = this.referencedColumnName;
		this.referencedColumnName = referencedColumnName;
		firePropertyChanged(REFERENCED_COLUMN_NAME_PROPERTY, oldReferencedColumnName, referencedColumnName);
	}

	public void setTable(String table) {
		String oldTable = this.table;
		this.table = table;
		firePropertyChanged(TABLE_PROPERTY, oldTable, table);
		tableChanged();
	}

	@Override
	public final void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * The table from which the column names are used has changed, notifies the
	 * listeners the list of names and reference column names should be updated.
	 */
	protected void tableChanged() {
		fireListChanged(NAMES_LIST);
		fireListChanged(REFERENCE_COLUMN_NAMES_LIST);
	}

	/**
	 * Retrieves the list of all the table names contains in the associated
	 * schema. The default returns an empty iterator.
	 *
	 * @return The names of the tables
	 */
	public ListIterator<String> tables() {
		return EmptyListIterator.instance();
	}

	/**
	 * Updates the given join column with the values contained in this state
	 * object.
	 *
	 * @param joinColumn The join column to update
	 */
	public void updateJoinColumn(BaseJoinColumn jc) {

		// Name
		if (valuesAreDifferent(name, jc.getSpecifiedName())) {
			jc.setSpecifiedName(name);
		}

		// Referenced Column Name
		if (valuesAreDifferent(referencedColumnName, jc.getSpecifiedReferencedColumnName())) {
			jc.setSpecifiedReferencedColumnName(referencedColumnName);
		}

		// Column Definition
		if (valuesAreDifferent(columnDefinition, jc.getColumnDefinition())) {
			jc.setColumnDefinition(columnDefinition);
		}
	}
}