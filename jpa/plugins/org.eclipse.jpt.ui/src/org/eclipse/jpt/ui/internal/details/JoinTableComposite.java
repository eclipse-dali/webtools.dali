/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.ListIterator;

import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.JoinColumnsComposite.JoinColumnsEditor;
import org.eclipse.jpt.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.ui.internal.details.db.TableCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ReadOnlyWritablePropertyValueModelWrapper;
import org.eclipse.jpt.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
 * @see {@link JoinTable}
 * @see {@link JoinTableJoiningStrategyPane}
 * @see {@link JoinColumnsComposite
 *
 * @version 2.1
 * @since 1.0
 */
public class JoinTableComposite extends ReferenceTableComposite<JoinTable>
{
	private Button overrideDefaultInverseJoinColumnsCheckBox;

	private JoinColumnsComposite<JoinTable> inverseJoinColumnsComposite;
	/**
	 * Creates a new <code>JoinTableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public JoinTableComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends JoinTable> subjectHolder,
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
	public JoinTableComposite(PropertyValueModel<? extends JoinTable> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected boolean isParentVirtual(JoinTable joinTable) {
		return joinTable.getParent().getRelationshipReference().isParentVirtual();
	}
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		// Name widgets
		TableCombo<JoinTable> tableCombo = addTableCombo(container);
		Composite tablePane = addPane(container, groupBoxMargin);
		addLabeledComposite(
				tablePane,
			JptUiDetailsMessages.JoinTableComposite_name,
			tableCombo.getControl(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_NAME
		);
		
		// schema widgets
		SchemaCombo<JoinTable> schemaCombo = addSchemaCombo(container);

		addLabeledComposite(
			tablePane,
			JptUiDetailsMessages.JoinTableComposite_schema,
			schemaCombo.getControl(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_SCHEMA
		);
		
		// catalog widgets
		CatalogCombo<JoinTable> catalogCombo = addCatalogCombo(container);

		addLabeledComposite(
			tablePane,
			JptUiDetailsMessages.JoinTableComposite_catalog,
			catalogCombo.getControl(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_CATALOG
		);

		// Join Columns group pane
		Group joinColumnGroupPane = addTitledGroup(
			container,
			JptUiDetailsMessages.JoinTableComposite_joinColumn
		);

		// Override Default Join Columns check box
		this.overrideDefaultJoinColumnsCheckBox = addCheckBox(
			addSubPane(joinColumnGroupPane, 8),
			JptUiDetailsMessages.JoinTableComposite_overrideDefaultJoinColumns,
			buildOverrideDefaultJoinColumnHolder(),
			null
		);

		this.joinColumnsComposite = new JoinColumnsComposite<JoinTable>(
			this,
			joinColumnGroupPane,
			buildJoinColumnsEditor()
		);

		installJoinColumnsPaneEnabler(this.joinColumnsComposite);

		// Inverse Join Columns group pane
		Group inverseJoinColumnGroupPane = addTitledGroup(
			container,
			JptUiDetailsMessages.JoinTableComposite_inverseJoinColumn
		);

		// Override Default Inverse Join Columns check box
		this.overrideDefaultInverseJoinColumnsCheckBox = addCheckBox(
			addSubPane(inverseJoinColumnGroupPane, 8),
			JptUiDetailsMessages.JoinTableComposite_overrideDefaultInverseJoinColumns,
			buildOverrideDefaultInverseJoinColumnHolder(),
			null
		);

		this.inverseJoinColumnsComposite = new JoinColumnsComposite<JoinTable>(
			this,
			inverseJoinColumnGroupPane,
			buildInverseJoinColumnsEditor()
		);

		installInverseJoinColumnsPaneEnabler(this.inverseJoinColumnsComposite);
	}

	private void installInverseJoinColumnsPaneEnabler(JoinColumnsComposite<JoinTable> pane) {
		pane.installJoinColumnsPaneEnabler(new InverseJoinColumnPaneEnablerHolder());
	}

	private void addInverseJoinColumn(JoinTable joinTable) {

		InverseJoinColumnInJoinTableDialog dialog =
			new InverseJoinColumnInJoinTableDialog(getShell(), joinTable, null);

		dialog.openDialog(buildAddInverseJoinColumnPostExecution());
	}

	private void addInverseJoinColumnFromDialog(InverseJoinColumnInJoinTableStateObject stateObject) {

		JoinTable subject = getSubject();
		int index = subject.specifiedInverseJoinColumnsSize();

		JoinColumn joinColumn = subject.addSpecifiedInverseJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
		this.setSelectedInverseJoinColumn(joinColumn);
	}

	private void setSelectedInverseJoinColumn(JoinColumn joinColumn) {
		this.inverseJoinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}

	private PostExecution<InverseJoinColumnInJoinTableDialog> buildAddInverseJoinColumnPostExecution() {
		return new PostExecution<InverseJoinColumnInJoinTableDialog>() {
			public void execute(InverseJoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					addInverseJoinColumnFromDialog(dialog.getSubject());
				}
			}
		};
	}

	private PostExecution<InverseJoinColumnInJoinTableDialog> buildEditInverseJoinColumnPostExecution() {
		return new PostExecution<InverseJoinColumnInJoinTableDialog>() {
			public void execute(InverseJoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editInverseJoinColumn(dialog.getSubject());
				}
			}
		};
	}


	private InverseJoinColumnsProvider buildInverseJoinColumnsEditor() {
		return new InverseJoinColumnsProvider();
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultInverseJoinColumnHolder() {
		return new OverrideDefaultInverseJoinColumnHolder();
	}
	
	private ListValueModel<JoinColumn> buildSpecifiedInverseJoinColumnsListHolder() {
		return new ListAspectAdapter<JoinTable, JoinColumn>(getSubjectHolder(), JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<JoinColumn> listIterator_() {
				return this.subject.specifiedInverseJoinColumns();
			}

			@Override
			protected int size_() {
				return this.subject.specifiedInverseJoinColumnsSize();
			}
		};
	}


	private void editInverseJoinColumn(InverseJoinColumnInJoinTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	private void editInverseJoinColumn(JoinColumn joinColumn) {

		InverseJoinColumnInJoinTableDialog dialog =
			new InverseJoinColumnInJoinTableDialog(getShell(), getSubject(), joinColumn);

		dialog.openDialog(buildEditInverseJoinColumnPostExecution());
	}

	private void updateInverseJoinColumns() {
		if (this.isPopulating()) {
			return;
		}
		
		JoinTable joinTable = this.getSubject();
		if (joinTable == null) {
			return;
		}
		
		boolean selected = this.overrideDefaultInverseJoinColumnsCheckBox.getSelection();
		this.setPopulating(true);

		try {
			if (selected) {
				joinTable.convertDefaultToSpecifiedInverseJoinColumn();
				setSelectedInverseJoinColumn(joinTable.specifiedInverseJoinColumns().next());
			} else {
				joinTable.clearSpecifiedInverseJoinColumns();
			}
		} finally {
			this.setPopulating(false);
		}
	}



	private class InverseJoinColumnsProvider implements JoinColumnsEditor<JoinTable> {

		public void addJoinColumn(JoinTable subject) {
			JoinTableComposite.this.addInverseJoinColumn(subject);
		}

		public JoinColumn getDefaultJoinColumn(JoinTable subject) {
			return subject.getDefaultInverseJoinColumn();
		}

		public String getDefaultPropertyName() {
			return JoinTable.DEFAULT_INVERSE_JOIN_COLUMN;
		}

		public void editJoinColumn(JoinTable subject, JoinColumn joinColumn) {
			JoinTableComposite.this.editInverseJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(JoinTable subject) {
			return subject.hasSpecifiedInverseJoinColumns();
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

		public String getSpecifiedJoinColumnsListPropertyName() {
			return JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST;
		}
	}
	
	private class OverrideDefaultInverseJoinColumnHolder extends ListPropertyValueModelAdapter<Boolean>
	    implements WritablePropertyValueModel<Boolean> {
	
		public OverrideDefaultInverseJoinColumnHolder() {
			super(buildSpecifiedInverseJoinColumnsListHolder());
		}
	
		@Override
		protected Boolean buildValue() {
			return Boolean.valueOf(this.listHolder.size() > 0);
		}
	
		public void setValue(Boolean value) {
			updateInverseJoinColumns();
		}
	}

	
	private class InverseJoinColumnPaneEnablerHolder 
		extends CachingTransformationPropertyValueModel<JoinTable, Boolean>
	{
		private StateChangeListener stateChangeListener;
		
		
		public InverseJoinColumnPaneEnablerHolder() {
			super(
				new ValueListAdapter<JoinTable>(
					new ReadOnlyWritablePropertyValueModelWrapper(getSubjectHolder()), 
					JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST));
			this.stateChangeListener = buildStateChangeListener();
		}
		
		
		private StateChangeListener buildStateChangeListener() {
			return new StateChangeListener() {
				public void stateChanged(StateChangeEvent event) {
					valueStateChanged(event);
				}
			};
		}
		
		private void valueStateChanged(StateChangeEvent event) {
			Object oldValue = this.cachedValue;
			Object newValue = transformNew(this.valueHolder.getValue());
			firePropertyChanged(VALUE, oldValue, newValue);
		}
		
		@Override
		protected Boolean transform(JoinTable value) {
			if (value == null) {
				return false;
			}
			return super.transform(value);
		}
		
		@Override
		protected Boolean transform_(JoinTable value) {
			boolean virtual = JoinTableComposite.this.isParentVirtual(value);
			return Boolean.valueOf(! virtual && value.specifiedInverseJoinColumnsSize() > 0);
		}
		
		@Override
		protected void engageModel() {
			super.engageModel();
			this.valueHolder.addStateChangeListener(this.stateChangeListener);
		}
		
		@Override
		protected void disengageModel() {
			this.valueHolder.removeStateChangeListener(this.stateChangeListener);
			super.disengageModel();
		}
	}
}