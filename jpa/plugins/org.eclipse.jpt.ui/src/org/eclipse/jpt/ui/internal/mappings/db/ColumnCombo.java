/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.db;

import java.util.Iterator;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This database object combo handles showing a single or multiple tables'
 * columns.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class ColumnCombo<T extends JpaNode> extends DatabaseObjectCombo<T>
{
	/**
	 * Creates a new <code>ColumnCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ColumnCombo(Pane<? extends T> parentPane,
	                   Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>ColumnCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public ColumnCombo(Pane<?> parentPane,
	                   PropertyValueModel<? extends T> subjectHolder,
	                   Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>ColumnCombo</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public ColumnCombo(PropertyValueModel<? extends T> subjectHolder,
	                   Composite parent,
	                   WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/**
	 * Returns the database table that supplies the column names.
	 * Assume the subject is not null.
	 *
	 * @return The database table that supplies the column names.
	 */
	protected abstract Table getDbTable_();

	/**
	 * Returns the database table that supplies the column names.
	 *
	 * @return The database table that supplies the column names.
	 */
	protected Table getDbTable() {
		return (this.getSubject() == null) ? null : this.getDbTable_();

	}

	@Override
	protected void tableChanged(Table table) {
		super.tableChanged(table);
		if (this.getDbTable() == table) {
			this.doPopulate();
		}
	}

	@Override
	protected Iterator<String> values() {
		Table table = this.getDbTable();
		return (table == null) ? EmptyIterator.<String>instance() : table.columnNames();
	}

}
