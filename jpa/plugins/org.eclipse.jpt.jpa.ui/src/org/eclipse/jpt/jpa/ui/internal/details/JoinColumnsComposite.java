/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.AbstractAdapter;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JoiningStrategyJoinColumnsComposite - A container of this pane
 * @see JoinTableComposite - A container of this pane
 * @see EntityOverridesComposite - A container of this pane
 *
 * @version 3.0
 * @since 2.0
 */
public class JoinColumnsComposite<T extends JpaNode> extends Pane<T>
{
	/**
	 * The editor used to perform the common behaviors defined in the list pane.
	 */
	JoinColumnsEditor<T> joinColumnsEditor;

	ModifiableCollectionValueModel<JoinColumn> selectedJoinColumnsModel;

	/**
	 * Creates a new <code>JoinColumnsComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 * @param joinColumnsEditor The editor used to perform the common behaviors
	 * defined in the list pane
	 */
	public JoinColumnsComposite(Pane<? extends T> parentPane,
	                            Composite parent,
	                            JoinColumnsEditor<T> joinColumnsEditor,
	                            PropertyValueModel<Boolean> enabledModel) {

		super(parentPane, parent, enabledModel);
		this.joinColumnsEditor = joinColumnsEditor;
		initializeLayout2();
	}

	/**
	 * Creates a new <code>JoinColumnsComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinColumnsComposite(PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            WidgetFactory widgetFactory,
	                            JoinColumnsEditor<T> joinColumnsEditor) {

		super(subjectHolder, parent, widgetFactory);
		this.joinColumnsEditor = joinColumnsEditor;
		initializeLayout2();
	}

	@Override
	public Composite getControl() {
		return (Composite) super.getControl();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedJoinColumnsModel = this.buildSelectedJoinColumnsModel();
	}

	@Override
	protected void initializeLayout(Composite container) {
		//see intiailizeLayout2()
	}

	private void initializeLayout2() {
		new AddRemoveListPane<T, JoinColumn>(
			this,
			getControl(),
			buildJoinColumnsAdapter(),
			buildJoinColumnsListModel(),
			this.selectedJoinColumnsModel,
			buildJoinColumnsListLabelProvider(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);
	}

	private ModifiableCollectionValueModel<JoinColumn> buildSelectedJoinColumnsModel() {
		return new SimpleCollectionValueModel<JoinColumn>();
	}

	String buildJoinColumnLabel(ReadOnlyJoinColumn joinColumn) {

		if (joinColumn.isVirtual()) {
			return NLS.bind(
				JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParamsDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}
		if (joinColumn.getSpecifiedName() == null) {

			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParamsBothDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParamsFirstDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParamsSecDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		return NLS.bind(
			JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParams,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private Adapter<JoinColumn> buildJoinColumnsAdapter() {
		return new AbstractAdapter<JoinColumn>() {

			public JoinColumn addNewItem() {
				return JoinColumnsComposite.this.joinColumnsEditor.addJoinColumn(getSubject());
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<JoinColumn> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<JoinColumn> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				JoinColumn joinColumn = selectedItemsModel.iterator().next();
				JoinColumnsComposite.this.joinColumnsEditor.removeJoinColumn(getSubject(), joinColumn);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiDetailsMessages.JoinColumnsComposite_edit;
			}

			@Override
			public void optionOnSelection(CollectionValueModel<JoinColumn> selectedItemsModel) {
				JoinColumn joinColumn = selectedItemsModel.iterator().next();
				JoinColumnsComposite.this.joinColumnsEditor.editJoinColumn(getSubject(), joinColumn);
			}
		};
	}

	private ListValueModel<ReadOnlyJoinColumn> buildJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<ReadOnlyJoinColumn>(buildJoinColumnsListHolder(),
			ReadOnlyNamedColumn.SPECIFIED_NAME_PROPERTY,
			ReadOnlyNamedColumn.DEFAULT_NAME_PROPERTY,
			ReadOnlyBaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			ReadOnlyBaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY);
	}

	private ListValueModel<ReadOnlyJoinColumn> buildJoinColumnsListHolder() {
		java.util.List<ListValueModel<ReadOnlyJoinColumn>> list = new ArrayList<ListValueModel<ReadOnlyJoinColumn>>();
		list.add(buildDefaultJoinColumnListHolder());
		list.add(buildSpecifiedJoinColumnsListHolder());
		return new CompositeListValueModel<ListValueModel<ReadOnlyJoinColumn>, ReadOnlyJoinColumn>(list);
	}

	private ListValueModel<ReadOnlyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<T, ReadOnlyJoinColumn>(getSubjectHolder(), this.joinColumnsEditor.getSpecifiedJoinColumnsListPropertyName()) {
			@Override
			protected ListIterable<ReadOnlyJoinColumn> getListIterable() {
				return JoinColumnsComposite.this.joinColumnsEditor.getSpecifiedJoinColumns(this.subject);
			}

			@Override
			protected int size_() {
				return JoinColumnsComposite.this.joinColumnsEditor.getSpecifiedJoinColumnsSize(this.subject);
			}
		};
	}


	private ListValueModel<ReadOnlyJoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<ReadOnlyJoinColumn>(buildDefaultJoinColumnHolder());

	}

	private PropertyValueModel<ReadOnlyJoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<T, ReadOnlyJoinColumn>(getSubjectHolder(), this.joinColumnsEditor.getDefaultPropertyName()) {
			@Override
			protected ReadOnlyJoinColumn buildValue_() {
				return JoinColumnsComposite.this.joinColumnsEditor.getDefaultJoinColumn(this.subject);
			}
		};
	}


	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				ReadOnlyJoinColumn joinColumn = (ReadOnlyJoinColumn) element;

				return buildJoinColumnLabel(joinColumn);
			}
		};
	}

	public void setSelectedJoinColumn(JoinColumn joinColumn) {
		this.selectedJoinColumnsModel.setValues(new SingleElementIterable<JoinColumn>(joinColumn));
	}

	/**
	 * The editor is used to complete the behavior of this pane.
	 */
	public static interface JoinColumnsEditor<T> {

		/**
		 * Add a join column to the given subject and return it
		 */
		JoinColumn addJoinColumn(T subject);

		/**
		 * Edit the given join column, the Edit button was pressed
		 * while this join column was selected.
		 */
		void editJoinColumn(T subject, ReadOnlyJoinColumn joinColumn);

		/**
		 * Return whether the subject has specified join columns
		 */
		boolean hasSpecifiedJoinColumns(T subject);

		/**
		 * Return the spcified join columns from the given subject
		 */
		ListIterable<ReadOnlyJoinColumn> getSpecifiedJoinColumns(T subject);

		/**
		 * Return the number of specified join columns on the given subject
		 */
		int getSpecifiedJoinColumnsSize(T subject);

		/**
		 * Return the default join column from the given subject or null.
		 */
		ReadOnlyJoinColumn getDefaultJoinColumn(T subject);

		/**
		 * Return the property name of the specified join columns list
		 */
		String getSpecifiedJoinColumnsListPropertyName();

		/**
		 * Return the property name of the specified join columns list
		 */
		String getDefaultPropertyName();

		/**
		 * Remove the join columns at the specified indices from the subject
		 */
		void removeJoinColumn(T subject, JoinColumn joinColumn);
	}
}