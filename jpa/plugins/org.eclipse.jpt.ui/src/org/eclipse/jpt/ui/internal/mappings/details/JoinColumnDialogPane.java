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

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumDialogComboViewer;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |             ------------------------------------------------------------- |
 * | Table:      | TableCombo                                              |v| |
 * |             ------------------------------------------------------------- |
 * |             ------------------------------------------------------------- |
 * | Insertable: | EnumDialogComboViewer                                   |v| |
 * |             ------------------------------------------------------------- |
 * |             ------------------------------------------------------------- |
 * | Updatable:  | EnumDialogComboViewer                                   |v| |
 * |             ------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JoinColumnStateObject
 * @see JoinColumnDialog - The parent container
 * @see EnumDialogComboViewer
 *
 * @version 2.0
 * @since 1.0
 */
public class JoinColumnDialogPane extends AbstractJoinColumnDialogPane<JoinColumnStateObject>
{
	private Combo tableCombo;

	/**
	 * Creates a new <code>JoinColumnDialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public JoinColumnDialogPane(PropertyValueModel<? extends JoinColumnStateObject> subjectHolder,
	                            Composite parent)
	{
		super(subjectHolder, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(JoinColumnStateObject.TABLE_PROPERTY);
	}

	private EnumDialogComboViewer<JoinColumnStateObject, Boolean> buildInsertableCombo(Composite container) {

		return new EnumDialogComboViewer<JoinColumnStateObject, Boolean>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(JoinColumnStateObject.INSERTABLE_PROPERTY);
			}

			@Override
			protected Boolean[] choices() {
				return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
			}

			@Override
			protected Boolean defaultValue() {
				return subject().getDefaultInsertable();
			}

			@Override
			protected String displayString(Boolean value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					JoinColumnDialogPane.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getInsertable();
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setInsertable(value);
			}
		};
	}

	private ModifyListener buildTableComboSelectionListener() {
		return new ModifyListener() {

			private boolean isDefaultTable(int selectedIndex, String value) {
				return (selectedIndex == 0) && value.equals(subject().defaultTableName());
			}

			public void modifyText(ModifyEvent e) {

				if (!isPopulating()) {
					setPopulating(true);

					try {
						Combo combo = (Combo) e.widget;
						String table = combo.getText();
						boolean defaultTable = isDefaultTable(combo.getSelectionIndex(), table);

						subject().setTable(table);
						subject().setDefaultTableSelected(defaultTable);

						populateNameCombo();
					}
					finally {
						setPopulating(false);
					}
				}
			}
		};
	}

	private EnumDialogComboViewer<JoinColumnStateObject, Boolean> buildUpdatableCombo(Composite container) {

		return new EnumDialogComboViewer<JoinColumnStateObject, Boolean>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(JoinColumnStateObject.UPDATABLE_PROPERTY);
			}

			@Override
			protected Boolean[] choices() {
				return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
			}

			@Override
			protected Boolean defaultValue() {
				return subject().getDefaultUpdatable();
			}

			@Override
			protected String displayString(Boolean value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					JoinColumnDialogPane.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getUpdatable();
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setUpdatable(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateTableCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
		super.initializeLayout(container);

		// Join Referenced Column widgets
		tableCombo = buildLabeledEditableCombo(
			container,
			JptUiMappingsMessages.JoinColumnDialog_table,
			buildTableComboSelectionListener(),
			IJpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
		);

		// Insertable widgets
		EnumDialogComboViewer<JoinColumnStateObject, Boolean> insertableCombo =
			buildInsertableCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.JoinColumnDialog_insertable,
			insertableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable widgets
		EnumDialogComboViewer<JoinColumnStateObject, Boolean> updatableCombo =
			buildUpdatableCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.JoinColumnDialog_updatable,
			updatableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);
	}

	private void populateTableCombo() {
		JoinColumnStateObject subject = subject();
		tableCombo.removeAll();

		if (subject == null) {
			return;
		}

		// Add the default table if one exists
		String defaultTableName = subject.defaultTableName();

		if (defaultTableName != null) {
			tableCombo.add(NLS.bind(
				JptUiMappingsMessages.JoinColumnDialog_defaultWithOneParam,
				defaultTableName
			));
		}

		// Populate the combo with the table names
		Schema schema = subject.getSchema();

		if (schema != null) {
			Iterator<String> tables = schema.tableNames();

			for (Iterator<String> iter = CollectionTools.sort(tables); iter.hasNext(); ) {
				tableCombo.add(iter.next());
			}
		}

		// Update the selected table
		String table = subject.getTable();

		if ((table != null) && !subject.isDefaultTableSelected()) {
			tableCombo.setText(table);
		}
		else if (defaultTableName != null) {
			tableCombo.select(0);
		}
		else {
			tableCombo.select(-1);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == JoinColumnStateObject.TABLE_PROPERTY) {
			populateTableCombo();
		}
	}
}