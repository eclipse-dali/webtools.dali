/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.IJoinColumnsEditor;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
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
 * @see IJoinTable
 * @see OneToManyMappingComposite - A container of this pane
 * @see ManyToManyMappingComposite - A container of this pane
 * @see JoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class JoinTableComposite extends AbstractFormPane<IJoinTable>
{
	private JoinColumnsComposite<IJoinTable> inverseJoinColumnsComposite;
	private JoinColumnsComposite<IJoinTable> joinColumnsComposite;
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
	                          PropertyValueModel<? extends IJoinTable> subjectHolder,
	                          Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>JoinTableComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IJoinTable</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinTableComposite(PropertyValueModel<? extends IJoinTable> subjectHolder,
	                          Composite parent,
	                          IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addInverseJoinColumn(IJoinTable joinTable) {

		InverseJoinColumnDialog dialog = new InverseJoinColumnDialog(shell(), joinTable);
		dialog.openDialog(buildAddInverseJoinColumnPostExecution());
	}

	private void addInverseJoinColumnFromDialog(JoinColumnInJoinTableStateObject stateObject) {

		IJoinTable subject = subject();
		int index = subject.specifiedInverseJoinColumnsSize();

		IJoinColumn joinColumn = subject.addSpecifiedInverseJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private void addJoinColumn(IJoinTable joinTable) {

		JoinColumnInJoinTableDialog dialog = new JoinColumnInJoinTableDialog(shell(), joinTable);
		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private void addJoinColumnFromDialog(JoinColumnInJoinTableStateObject stateObject) {

		IJoinTable subject = subject();
		int index = subject.specifiedJoinColumnsSize();

		IJoinColumn joinColumn = subject().addSpecifiedJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private PostExecution<InverseJoinColumnDialog> buildAddInverseJoinColumnPostExecution() {
		return new PostExecution<InverseJoinColumnDialog>() {
			public void execute(InverseJoinColumnDialog dialog) {
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

	private PostExecution<JoinColumnInJoinTableDialog> buildEditInverseJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoinTableDialog>() {
			public void execute(JoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.subject());
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

	private TableCombo<IJoinTable> buildTableCombo(Composite container) {

		return new TableCombo<IJoinTable>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(IJoinTable.DEFAULT_NAME_PROPERTY);
				propertyNames.add(IJoinTable.SPECIFIED_NAME_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultName();
			}

			@Override
			protected IJpaProject jpaProject() {
				return subject().jpaProject();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedName(value);
			}

			@Override
			protected Table table() {
				return subject().dbTable();
			}

			private Schema tableSchema() {
				return database().schemaNamed(subject().getSchema());
			}

			@Override
			protected String value() {
				return subject().getSpecifiedName();
			}

			@Override
			protected Iterator<String> values() {
				Schema schema = tableSchema();

				if (schema != null) {
					return schema.tableNames();
				}

				return EmptyIterator.instance();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();

		IJoinTable subject = subject();
		boolean enabled = (subject != null) && subject.containsSpecifiedJoinColumns();
		boolean inverseEnabled = (subject != null) && subject.containsSpecifiedInverseJoinColumns();

		overrideDefaultJoinColumnsCheckBox.setSelection(enabled);
		overrideDefaultInverseJoinColumnsCheckBox.setSelection(enabled);

		joinColumnsComposite.enableWidgets(enabled);
		inverseJoinColumnsComposite.enableWidgets(inverseEnabled);
	}

	private void editInverseJoinColumn(IJoinColumn joinColumn) {

		InverseJoinColumnDialog dialog =
			new InverseJoinColumnDialog(shell(), joinColumn);

		dialog.openDialog(buildEditInverseJoinColumnPostExecution());
	}

	private void editJoinColumn(IJoinColumn joinColumn) {

		JoinColumnInJoinTableDialog dialog =
			new JoinColumnInJoinTableDialog(shell(), joinColumn);

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
		TableCombo<IJoinTable> tableCombo = buildTableCombo(container);

		buildLabeledComposite(
			buildPane(container, groupBoxMargin),
			JptUiMappingsMessages.JoinTableComposite_name,
			tableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_NAME
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

		joinColumnsComposite = new JoinColumnsComposite<IJoinTable>(
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

		inverseJoinColumnsComposite = new JoinColumnsComposite<IJoinTable>(
			this,
			inverseJoinColumnGroupPane,
			buildInverseJoinColumnsEditor()
		);
	}

	private void updateInverseJoinColumns() {

		if (isPopulating()) {
			return;
		}

		IJoinTable subject = subject();
		boolean selected = overrideDefaultInverseJoinColumnsCheckBox.getSelection();
		joinColumnsComposite.enableWidgets(selected);
		setPopulating(true);

		try {
			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				IJoinColumn defaultJoinColumn = subject.getDefaultInverseJoinColumn(); //TODO null check, override default button disabled

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IJoinColumn joinColumn = subject.addSpecifiedInverseJoinColumn(0);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
			}
			else {
				for (int index = subject.specifiedInverseJoinColumnsSize(); --index >= 0; ) {
					subject.removeSpecifiedInverseJoinColumn(index);
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

		IJoinTable subject = subject();
		boolean selected = overrideDefaultJoinColumnsCheckBox.getSelection();
		joinColumnsComposite.enableWidgets(selected);
		setPopulating(true);

		try {
			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				IJoinColumn defaultJoinColumn = subject.getDefaultJoinColumn(); //TODO null check, override default button disabled

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IJoinColumn joinColumn = subject.addSpecifiedJoinColumn(0);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
			}
			else {
				for (int index = subject.specifiedJoinColumnsSize(); --index >= 0; ) {
					subject.removeSpecifiedJoinColumn(index);
				}
			}
		}
		finally {
			setPopulating(false);
		}
	}

	private class InverseJoinColumnsProvider implements IJoinColumnsEditor<IJoinTable> {

		public void addJoinColumn(IJoinTable subject) {
			JoinTableComposite.this.addInverseJoinColumn(subject);
		}

		public void editJoinColumn(IJoinTable subject, IJoinColumn joinColumn) {
			JoinTableComposite.this.editInverseJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(IJoinTable subject) {
			return subject.containsSpecifiedInverseJoinColumns();
		}

		public ListIterator<IJoinColumn> joinColumns(IJoinTable subject) {
			return subject.inverseJoinColumns();
		}

		public String[] propertyNames() {
			return new String[] {
				IJoinTable.DEFAULT_INVERSE_JOIN_COLUMN,//TODO
				IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST
			};
		}

		public void removeJoinColumns(IJoinTable subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedInverseJoinColumn(selectedIndices[index]);
			}
		}
	}

	private class JoinColumnsProvider implements IJoinColumnsEditor<IJoinTable> {

		public void addJoinColumn(IJoinTable subject) {
			JoinTableComposite.this.addJoinColumn(subject);
		}

		public void editJoinColumn(IJoinTable subject, IJoinColumn joinColumn) {
			JoinTableComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(IJoinTable subject) {
			return subject.containsSpecifiedJoinColumns();
		}

		public ListIterator<IJoinColumn> joinColumns(IJoinTable subject) {
			return subject.joinColumns();
		}

		public String[] propertyNames() {
			return new String[] {
				IJoinTable.DEFAULT_JOIN_COLUMN, //TODO
				IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST
			};
		}

		public void removeJoinColumns(IJoinTable subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}
	}
}