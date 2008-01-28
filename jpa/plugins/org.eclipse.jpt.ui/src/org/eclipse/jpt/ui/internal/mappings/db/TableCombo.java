/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.db;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @version 2.0
 * @since 2.0
 */
public class TableCombo extends AbstractDatabaseObjectCombo<IColumn>
{
	/**
	 * Creates a new <code>TableCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TableCombo(AbstractFormPane<? extends IColumn> parentPane,
	                  Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>TableCombo</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public TableCombo(PropertyValueModel<? extends IColumn> subjectHolder,
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

		if (this.subject() != null) {
			this.populateDefaultColumnTable();

			if (this.subject() != null) {
				for (Iterator<String> iter = this.subject().owner().typeMapping().associatedTableNamesIncludingInherited(); iter.hasNext(); ) {
					this.getCombo().add(iter.next());
				}
			}

			this.populateColumnTable();
		}
	}

	private void populateColumnTable() {
		String tableName = this.subject().getSpecifiedTable();
		String defaultTableName = this.subject().getDefaultTable();
		if (tableName != null) {
			if (!this.getCombo().getText().equals(tableName)) {
				this.getCombo().setText(tableName);
			}
		}
		else {
			if (!this.getCombo().getText().equals(NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName))) {
				this.getCombo().select(0);
			}
		}
	}

	private void populateDefaultColumnTable() {
		String defaultTableName = subject().getDefaultTable();
		int selectionIndex = getCombo().getSelectionIndex();
		getCombo().add(NLS.bind(JptUiMappingsMessages.ColumnComposite_defaultWithOneParam, defaultTableName));

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

		if (this.subject() == null) {
			return;
		}

		if (StringTools.stringIsEmpty(value)) {
			value = null;

			if (subject().getSpecifiedTable() == null || subject().getSpecifiedTable().equals("")) {
				return;
			}
		}

		if (value != null && getCombo().getItemCount() > 0 && value.equals(getCombo().getItem(0))) {
			value = null;
		}

		if (subject().getSpecifiedTable() == null && value != null) {
			subject().setSpecifiedTable(value);
		}
		if (subject().getSpecifiedTable() != null && !subject().getSpecifiedTable().equals(value)) {
			subject().setSpecifiedTable(value);
		}
	}
}
