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
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |        ------------------------------------------------------------------ |
 * | Table: | TableCombo                                                   |v| |
 * |        ------------------------------------------------------------------ |
 * |                                                                           |
 * | x Insertable                                                              |
 * |                                                                           |
 * | x Updatable                                                               |
 * |                                                                           |
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

	private WritablePropertyValueModel<Boolean> buildInsertableHolder() {
		return new PropertyAspectAdapter<JoinColumnStateObject, Boolean>(getSubjectHolder(), JoinColumnStateObject.INSERTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getInsertable();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setInsertable(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.value();
				super.subjectChanged();
				Object newValue = this.value();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildInsertableStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildInsertableHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {

					Boolean defaultValue = subject().getDefaultInsertable();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							JptUiMappingsMessages.ColumnComposite_insertableWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.JoinColumnDialog_insertable;
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

	private WritablePropertyValueModel<Boolean> buildUpdatableHolder() {
		return new PropertyAspectAdapter<JoinColumnStateObject, Boolean>(getSubjectHolder(), JoinColumnStateObject.UPDATABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getUpdatable();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setUpdatable(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.value();
				super.subjectChanged();
				Object newValue = this.value();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildUpdatableStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildUpdatableHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {

					Boolean defaultValue = subject().getDefaultUpdatable();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							JptUiMappingsMessages.ColumnComposite_updatableWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.JoinColumnDialog_updatable;
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

		// Insertable check box
		buildTriStateCheckBoxWithDefault(
			buildSubPane(container, 4),
			JptUiMappingsMessages.JoinColumnDialog_insertable,
			buildInsertableHolder(),
			buildInsertableStringHolder(),
			IJpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable check box
		buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.JoinColumnDialog_updatable,
			buildUpdatableHolder(),
			buildUpdatableStringHolder(),
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