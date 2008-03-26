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
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This database object combo handles showing the database's tables.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class TableCombo<T extends JpaNode> extends AbstractDatabaseObjectCombo<T>
{
	/**
	 * Creates a new <code>TableCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TableCombo(AbstractPane<? extends T> parentPane,
	                  Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>TableCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public TableCombo(AbstractPane<?> parentPane,
	                  PropertyValueModel<? extends T> subjectHolder,
	                  Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>TableCombo</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public TableCombo(PropertyValueModel<? extends T> subjectHolder,
	                  Composite parent,
	                  WidgetFactory widgetFactory)
	{
		super(subjectHolder, parent, widgetFactory);
	}

	/**
	 * Retrieves the name of the schema from where the table is located.
	 *
	 * @return The table's schema name
	 */
	protected abstract String schemaName();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void tableChanged(Table table) {
		super.tableChanged(table);

		if (table == table()) {
			this.doPopulate();
		}
	}

	/**
	 * Returns the selected database table.
	 *
	 * @return The selected table
	 */
	protected abstract Table table();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Iterator<String> values() {

		if (subject() != null) {
			Database database = database();
			String schemaName = schemaName();

			if ((schemaName != null) && (database != null)) {
				Schema schema = database.schemaNamed(schemaName);

				if (schema != null) {
					return schema.tableNames();
				}
			}
		}

		return EmptyIterator.instance();
	}
}