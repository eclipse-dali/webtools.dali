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
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                    ------------------------------------------------------ |
 * | Table:             | TableCombo                                       |v| |
 * |                    ------------------------------------------------------ |
 * |                    ------------------------------------------------------ |
 * | Column Definition: | I                                                  | |
 * |                    ------------------------------------------------------ |
 * |                                                                           |
 * | x Insertable                                                              |
 * |                                                                           |
 * | x Nullable                                                                |
 * |                                                                           |
 * | x Unique                                                                  |
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
public class JoinColumnDialogPane extends BaseJoinColumnDialogPane<JoinColumnStateObject>
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

	private WritablePropertyValueModel<String> buildColumnDefinitionHolder() {
		return new PropertyAspectAdapter<JoinColumnStateObject, String>(getSubjectHolder(), JoinColumnStateObject.COLUMN_DEFINITION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getColumnDefinition();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setColumnDefinition(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Composite buildContainer(Composite parent) {
		return buildSubPane(parent, 0, 7, 0, 5);
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
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

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
							JptUiMappingsMessages.JoinColumnDialogPane_insertableWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.JoinColumnDialogPane_insertable;
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildNullableHolder() {
		return new PropertyAspectAdapter<JoinColumnStateObject, Boolean>(
			getSubjectHolder(),
			JoinColumnStateObject.NULLABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getNullable();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setNullable(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildNullableStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildNullableHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {

					Boolean defaultValue = subject().getDefaultNullable();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							JptUiMappingsMessages.JoinColumnDialogPane_nullableWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.JoinColumnDialogPane_nullable;
			}
		};
	}

	private ModifyListener buildTableComboSelectionListener() {
		return new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				if (!isPopulating()) {
					setPopulating(true);

					try {
						Combo combo = (Combo) e.widget;
						String value = combo.getText();
						boolean defaultValue = value.equals(combo.getItem(0));
						subject().setTable(defaultValue ? null : value);
						populateNameCombo();
					}
					finally {
						setPopulating(false);
					}
				}
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildUniqueHolder() {
		return new PropertyAspectAdapter<JoinColumnStateObject, Boolean>(
			getSubjectHolder(),
			JoinColumnStateObject.UNIQUE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getUnique();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setUnique(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildUniqueStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildUniqueHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {

					Boolean defaultValue = subject().getDefaultUnique();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							JptUiMappingsMessages.JoinColumnDialogPane_uniqueWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.JoinColumnDialogPane_unique;
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
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

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
							JptUiMappingsMessages.JoinColumnDialogPane_updatableWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.JoinColumnDialogPane_updatable;
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

	private void initializeDetailsPane(Composite container) {

		// Column Definition widgets
		buildLabeledText(
			container,
			JptUiMappingsMessages.ColumnComposite_columnDefinition,
			buildColumnDefinitionHolder()
		);

		// Insertable check box
		buildTriStateCheckBoxWithDefault(
			buildSubPane(container, 4),
			JptUiMappingsMessages.JoinColumnDialogPane_insertable,
			buildInsertableHolder(),
			buildInsertableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable check box
		buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.JoinColumnDialogPane_updatable,
			buildUpdatableHolder(),
			buildUpdatableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);

		// Unique tri-state check box
		buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_unique,
			buildUniqueHolder(),
			buildUniqueStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UNIQUE
		);

		// Nullable tri-state check box
		buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_nullable,
			buildNullableHolder(),
			buildNullableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_NULLABLE
		);
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
			JptUiMappingsMessages.JoinColumnDialogPane_table,
			buildTableComboSelectionListener(),
			JpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
		);

		initializeDetailsPane(container);
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
				JptUiMappingsMessages.JoinColumnDialogPane_defaultWithOneParam,
				defaultTableName
			));
		}
		else {
			tableCombo.add(JptUiMappingsMessages.JoinColumnDialogPane_defaultEmpty);
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

		if (table != null) {
			tableCombo.setText(table);
		}
		else {
			tableCombo.select(0);
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