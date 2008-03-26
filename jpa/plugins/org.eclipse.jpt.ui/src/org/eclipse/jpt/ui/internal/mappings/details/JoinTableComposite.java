/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.IJoinColumnsEditor;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |         ---------------------------------------------------------------   |
 * |   Name: |                                                           |v|   |
 * |         ---------------------------------------------------------------   |
 * |                                                                           |
 * | - Join Columns ---------------------------------------------------------- |
 * | |                                                                       | |
 * | | x Override Default                                                    | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | JoinColumnsComposite                                              | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - Inverse Join Columns -------------------------------------------------- |
 * | |                                                                       | |
 * | | x Override Default                                                    | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | JoinColumnsComposite                                              | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JoinTable
 * @see OneToManyMappingComposite - A container of this pane
 * @see ManyToManyMappingComposite - A container of this pane
 * @see JoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class JoinTableComposite extends AbstractFormPane<JoinTable>
{
	private JoinColumnsComposite<JoinTable> inverseJoinColumnsComposite;
	private JoinColumnsComposite<JoinTable> joinColumnsComposite;
	private Button overrideDefaultInverseJoinColumnsCheckBox;
	private Button overrideDefaultJoinColumnsCheckBox;

	/**
	 * Creates a new <code>JoinTableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public JoinTableComposite(AbstractFormPane<?> parentPane,
	                          PropertyValueModel<? extends JoinTable> subjectHolder,
	                          Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	/**
	 * Creates a new <code>JoinTableComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IJoinTable</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinTableComposite(PropertyValueModel<? extends JoinTable> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addInverseJoinColumn(JoinTable joinTable) {

		InverseJoinColumnInJoinTableDialog dialog =
			new InverseJoinColumnInJoinTableDialog(shell(), joinTable, null);

		dialog.openDialog(buildAddInverseJoinColumnPostExecution());
	}

	private void addInverseJoinColumnFromDialog(InverseJoinColumnInJoinTableStateObject stateObject) {

		JoinTable subject = subject();
		int index = subject.specifiedInverseJoinColumnsSize();

		JoinColumn joinColumn = subject.addSpecifiedInverseJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private void addJoinColumn(JoinTable joinTable) {

		JoinColumnInJoinTableDialog dialog =
			new JoinColumnInJoinTableDialog(shell(), joinTable, null);

		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private void addJoinColumnFromDialog(JoinColumnInJoinTableStateObject stateObject) {

		JoinTable subject = subject();
		int index = subject.specifiedJoinColumnsSize();

		JoinColumn joinColumn = subject().addSpecifiedJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private PostExecution<InverseJoinColumnInJoinTableDialog> buildAddInverseJoinColumnPostExecution() {
		return new PostExecution<InverseJoinColumnInJoinTableDialog>() {
			public void execute(InverseJoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					addInverseJoinColumnFromDialog(dialog.subject());
				}
			}
		};
	}

	private PostExecution<JoinColumnInJoinTableDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoinTableDialog>() {
			public void execute(JoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumnFromDialog(dialog.subject());
				}
			}
		};
	}

	private PostExecution<InverseJoinColumnInJoinTableDialog> buildEditInverseJoinColumnPostExecution() {
		return new PostExecution<InverseJoinColumnInJoinTableDialog>() {
			public void execute(InverseJoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editInverseJoinColumn(dialog.subject());
				}
			}
		};
	}

	private PostExecution<JoinColumnInJoinTableDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoinTableDialog>() {
			public void execute(JoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.subject());
				}
			}
		};
	}

	private InverseJoinColumnsProvider buildInverseJoinColumnsEditor() {
		return new InverseJoinColumnsProvider();
	}

	private JoinColumnsProvider buildJoinColumnsEditor() {
		return new JoinColumnsProvider();
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultHolder() {
		return new SimplePropertyValueModel<Boolean>();
	}

	private SelectionListener buildOverrideDefaultInverseSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateInverseJoinColumns();
			}
		};
	}

	private SelectionListener buildOverrideDefaultSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateJoinColumns();
			}
		};
	}

	private Composite buildPane(Composite container, int groupBoxMargin) {
		return buildSubPane(container, 0, groupBoxMargin, 10, groupBoxMargin);
	}

	private TableCombo<JoinTable> buildTableCombo(Composite container) {

		return new TableCombo<JoinTable>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Table.DEFAULT_NAME_PROPERTY);
				propertyNames.add(Table.SPECIFIED_NAME_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultName();
			}

			@Override
			protected String schemaName() {
				return subject().getSchema();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedName(value);
			}

			@Override
			protected org.eclipse.jpt.db.Table table() {
				return subject().getDbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedName();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();

		JoinTable subject = subject();
		boolean enabled = (subject != null) && subject.containsSpecifiedJoinColumns();
		boolean inverseEnabled = (subject != null) && subject.containsSpecifiedInverseJoinColumns();

		overrideDefaultJoinColumnsCheckBox.setSelection(enabled);
		overrideDefaultInverseJoinColumnsCheckBox.setSelection(inverseEnabled);

		joinColumnsComposite.enableWidgets(enabled);
		inverseJoinColumnsComposite.enableWidgets(inverseEnabled);
	}

	private void editInverseJoinColumn(InverseJoinColumnInJoinTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	private void editInverseJoinColumn(JoinColumn joinColumn) {

		InverseJoinColumnInJoinTableDialog dialog =
			new InverseJoinColumnInJoinTableDialog(shell(), subject(), joinColumn);

		dialog.openDialog(buildEditInverseJoinColumnPostExecution());
	}

	private void editJoinColumn(JoinColumn joinColumn) {

		JoinColumnInJoinTableDialog dialog =
			new JoinColumnInJoinTableDialog(shell(), subject(), joinColumn);

		dialog.openDialog(buildEditJoinColumnPostExecution());
	}

	private void editJoinColumn(JoinColumnInJoinTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		// Name widgets
		TableCombo<JoinTable> tableCombo = buildTableCombo(container);

		buildLabeledComposite(
			buildPane(container, groupBoxMargin),
			JptUiMappingsMessages.JoinTableComposite_name,
			tableCombo.getControl(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_NAME
		);

		// Join Columns group pane
		Group joinColumnGroupPane = buildTitledPane(
			container,
			JptUiMappingsMessages.JoinTableComposite_joinColumn
		);

		// Override Default Join Columns check box
		overrideDefaultJoinColumnsCheckBox = buildCheckBox(
			buildSubPane(joinColumnGroupPane, 8),
			JptUiMappingsMessages.JoinTableComposite_overrideDefaultJoinColumns,
			buildOverrideDefaultHolder()
		);

		overrideDefaultJoinColumnsCheckBox.addSelectionListener(
			buildOverrideDefaultSelectionListener()
		);

		joinColumnsComposite = new JoinColumnsComposite<JoinTable>(
			this,
			joinColumnGroupPane,
			buildJoinColumnsEditor()
		);

		// Inverse Join Columns group pane
		Group inverseJoinColumnGroupPane = buildTitledPane(
			container,
			JptUiMappingsMessages.JoinTableComposite_inverseJoinColumn
		);

		// Override Default Inverse Join Columns check box
		overrideDefaultInverseJoinColumnsCheckBox = buildCheckBox(
			buildSubPane(inverseJoinColumnGroupPane, 8),
			JptUiMappingsMessages.JoinTableComposite_overrideDefaultInverseJoinColumns,
			buildOverrideDefaultHolder()
		);

		overrideDefaultInverseJoinColumnsCheckBox.addSelectionListener(
			buildOverrideDefaultInverseSelectionListener()
		);

		inverseJoinColumnsComposite = new JoinColumnsComposite<JoinTable>(
			this,
			inverseJoinColumnGroupPane,
			buildInverseJoinColumnsEditor()
		);
	}

	private void updateInverseJoinColumns() {

		if (isPopulating()) {
			return;
		}

		JoinTable joinTable = subject();
		boolean selected = overrideDefaultInverseJoinColumnsCheckBox.getSelection();
		inverseJoinColumnsComposite.enableWidgets(selected);
		setPopulating(true);

		try {
			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				JoinColumn defaultJoinColumn = joinTable.getDefaultInverseJoinColumn(); //TODO null check, override default button disabled

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					JoinColumn joinColumn = joinTable.addSpecifiedInverseJoinColumn(0);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
			}
			else {
				for (int index = joinTable.specifiedInverseJoinColumnsSize(); --index >= 0; ) {
					joinTable.removeSpecifiedInverseJoinColumn(index);
				}
			}
		}
		finally {
			setPopulating(false);
		}
	}

	private void updateJoinColumns() {

		if (isPopulating()) {
			return;
		}

		JoinTable joinTable = subject();
		boolean selected = overrideDefaultJoinColumnsCheckBox.getSelection();
		joinColumnsComposite.enableWidgets(selected);
		setPopulating(true);

		try {
			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				JoinColumn defaultJoinColumn = joinTable.getDefaultJoinColumn(); //TODO null check, override default button disabled

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					JoinColumn joinColumn = joinTable.addSpecifiedJoinColumn(0);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
			}
			else {
				for (int index = joinTable.specifiedJoinColumnsSize(); --index >= 0; ) {
					joinTable.removeSpecifiedJoinColumn(index);
				}
			}
		}
		finally {
			setPopulating(false);
		}
	}

	private class InverseJoinColumnsProvider implements IJoinColumnsEditor<JoinTable> {

		public void addJoinColumn(JoinTable subject) {
			JoinTableComposite.this.addInverseJoinColumn(subject);
		}

		public JoinColumn defaultJoinColumn(JoinTable subject) {
			return subject.getDefaultInverseJoinColumn();
		}

		public String defaultPropertyName() {
			return JoinTable.DEFAULT_INVERSE_JOIN_COLUMN;
		}

		public void editJoinColumn(JoinTable subject, JoinColumn joinColumn) {
			JoinTableComposite.this.editInverseJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(JoinTable subject) {
			return subject.containsSpecifiedInverseJoinColumns();
		}

		public void removeJoinColumns(JoinTable subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedInverseJoinColumn(selectedIndices[index]);
			}
		}

		public ListIterator<JoinColumn> specifiedJoinColumns(JoinTable subject) {
			return subject.specifiedInverseJoinColumns();
		}

		public int specifiedJoinColumnsSize(JoinTable subject) {
			return subject.specifiedInverseJoinColumnsSize();
		}

		public String specifiedListPropertyName() {
			return JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST;
		}
	}

	private class JoinColumnsProvider implements IJoinColumnsEditor<JoinTable> {

		public void addJoinColumn(JoinTable subject) {
			JoinTableComposite.this.addJoinColumn(subject);
		}

		public JoinColumn defaultJoinColumn(JoinTable subject) {
			return subject.getDefaultJoinColumn();
		}

		public String defaultPropertyName() {
			return JoinTable.DEFAULT_JOIN_COLUMN;
		}

		public void editJoinColumn(JoinTable subject, JoinColumn joinColumn) {
			JoinTableComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(JoinTable subject) {
			return subject.containsSpecifiedJoinColumns();
		}

		public void removeJoinColumns(JoinTable subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}

		public ListIterator<JoinColumn> specifiedJoinColumns(JoinTable subject) {
			return subject.specifiedJoinColumns();
		}

		public int specifiedJoinColumnsSize(JoinTable subject) {
			return subject.specifiedJoinColumnsSize();
		}

		public String specifiedListPropertyName() {
			return JoinTable.SPECIFIED_JOIN_COLUMNS_LIST;
		}
	}
}