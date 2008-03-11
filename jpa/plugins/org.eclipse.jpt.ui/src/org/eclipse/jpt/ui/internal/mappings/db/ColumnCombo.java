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
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * This database object combo handles showing a table's columns.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class ColumnCombo<T extends JpaNode> extends AbstractDatabaseObjectCombo<T>
{
	/**
	 * Creates a new <code>ColumnCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ColumnCombo(AbstractFormPane<? extends T> parentPane,
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
	public ColumnCombo(AbstractFormPane<?> parentPane,
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

	protected abstract Table table();

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

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Iterator<String> values() {

		Table table = table();

		if (table != null) {
			return table.columnNames();
		}

		return EmptyIterator.instance();
	}
}