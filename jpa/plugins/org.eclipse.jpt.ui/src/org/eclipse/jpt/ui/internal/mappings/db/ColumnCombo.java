/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.db;

import java.util.List;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This combo manages a table's columns.
 *
 * @see IColumn
 *
 * @version 2.0
 * @since 2.0
 */
public class ColumnCombo extends AbstractDatabaseObjectCombo<IColumn>
{
	/**
	 * Creates a new <code>ColumnCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ColumnCombo(AbstractFormPane<? extends IColumn> parentPane,
	                   Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>ColumnCombo</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public ColumnCombo(PropertyValueModel<IColumn> subjectHolder,
	                   Composite parent,
	                   TabbedPropertySheetWidgetFactory widgetFactory)
	{
		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {

		this.getCombo().removeAll();

		if (subject() != null) {

			this.populateDefaultColumnName();

			if (this.connectionProfile().isConnected()) {

				Table table = table();

				if (table != null) {

					List<String> columnNames = CollectionTools.sort(CollectionTools.list(table.columnNames()));

					for (String columnName : columnNames) {
						this.getCombo().add(columnName);
					}
				}
			}

			populateColumnName();
		}
	}

	private void populateColumnName() {
		String specifiedColumnName = this.subject().getSpecifiedName();
		if (specifiedColumnName != null) {
			if (!this.getCombo().getText().equals(specifiedColumnName)) {
				this.getCombo().setText(specifiedColumnName);
			}
		}
		else {
			String defaultColumnName = this.subject().getDefaultName();
			if (!this.getCombo().getText().equals(NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultColumnName))) {
				this.getCombo().select(0);
			}
		}
	}

	private void populateDefaultColumnName() {
		String defaultColumnName = subject().getDefaultName();
		int selectionIndex = getCombo().getSelectionIndex();
		getCombo().add(NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultColumnName));

		if (selectionIndex == 0) {
			// Combo text does not update when switching between 2 mappings of the
			// same type that both have a default subject() name. clear the
			// selection and then set it again
			getCombo().clearSelection();
			getCombo().select(0);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void schemaChanged(Schema schema) {
	}

	protected final Table table() {
		return this.subject().dbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void tableChanged(Table table) {
		if (table == table()) {
			this.doPopulate();
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void valueChanged(String value) {

		if (subject() == null) {
			return;
		}

		if (StringTools.stringIsEmpty(value)) {

			value = null;

			if (StringTools.stringIsEmpty(subject().getSpecifiedName())) {
				return;
			}
		}

		if (value != null &&
		    getCombo().getItemCount() > 0 &&
		    value.equals(getCombo().getItem(0)))
		{
			value = null;
		}

		if (subject().getSpecifiedName() == null &&
		    value != null)
		{
			subject().setSpecifiedName(value);
		}

		if (subject().getSpecifiedName() != null &&
		    !subject().getSpecifiedName().equals(value))
		{
			subject().setSpecifiedName(value);
		}
	}
}
