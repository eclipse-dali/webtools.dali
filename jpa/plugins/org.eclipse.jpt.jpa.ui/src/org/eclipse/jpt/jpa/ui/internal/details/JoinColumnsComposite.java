/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.AbstractAdapter;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

public class JoinColumnsComposite<T extends JpaModel>
	extends Pane<T>
{
	/**
	 * The editor used to perform the common behaviors defined in the list pane.
	 */
	JoinColumnsEditor<T> joinColumnsEditor;

	ModifiableCollectionValueModel<SpecifiedJoinColumn> selectedJoinColumnsModel;


	public JoinColumnsComposite(
			Pane<? extends T> parent,
			Composite parentComposite,
			JoinColumnsEditor<T> joinColumnsEditor,
			PropertyValueModel<Boolean> enabledModel) {
		super(parent, parentComposite, enabledModel);
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
		new AddRemoveListPane<T, SpecifiedJoinColumn>(
			this,
			getControl(),
			buildJoinColumnsAdapter(),
			buildJoinColumnsListModel(),
			this.selectedJoinColumnsModel,
			buildJoinColumnsListLabelProvider(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);
	}

	private ModifiableCollectionValueModel<SpecifiedJoinColumn> buildSelectedJoinColumnsModel() {
		return new SimpleCollectionValueModel<SpecifiedJoinColumn>();
	}

	String buildJoinColumnLabel(JoinColumn joinColumn) {

		if (joinColumn.isVirtual()) {
			return NLS.bind(
				JptJpaUiDetailsMessages.JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS_DEFAULT,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}
		if (joinColumn.getSpecifiedName() == null) {

			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptJpaUiDetailsMessages.JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS_BOTH_DEFAULT,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptJpaUiDetailsMessages.JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS_FIRST_DEFAULT,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptJpaUiDetailsMessages.JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS_SECOND_DEFAULT,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		return NLS.bind(
			JptJpaUiDetailsMessages.JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private Adapter<SpecifiedJoinColumn> buildJoinColumnsAdapter() {
		return new AbstractAdapter<SpecifiedJoinColumn>() {

			public SpecifiedJoinColumn addNewItem() {
				return JoinColumnsComposite.this.joinColumnsEditor.addJoinColumn(getSubject());
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<SpecifiedJoinColumn> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<SpecifiedJoinColumn> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				SpecifiedJoinColumn joinColumn = selectedItemsModel.iterator().next();
				JoinColumnsComposite.this.joinColumnsEditor.removeJoinColumn(getSubject(), joinColumn);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptJpaUiDetailsMessages.JOIN_COLUMNS_COMPOSITE_EDIT;
			}

			@Override
			public void optionOnSelection(CollectionValueModel<SpecifiedJoinColumn> selectedItemsModel) {
				SpecifiedJoinColumn joinColumn = selectedItemsModel.iterator().next();
				JoinColumnsComposite.this.joinColumnsEditor.editJoinColumn(getSubject(), joinColumn);
			}
		};
	}

	private ListValueModel<JoinColumn> buildJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<JoinColumn>(buildJoinColumnsListHolder(),
			NamedColumn.SPECIFIED_NAME_PROPERTY,
			NamedColumn.DEFAULT_NAME_PROPERTY,
			BaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			BaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY);
	}

	private ListValueModel<JoinColumn> buildJoinColumnsListHolder() {
		java.util.List<ListValueModel<JoinColumn>> list = new ArrayList<ListValueModel<JoinColumn>>();
		list.add(buildDefaultJoinColumnListHolder());
		list.add(buildSpecifiedJoinColumnsListHolder());
		return CompositeListValueModel.forModels(list);
	}

	private ListValueModel<JoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<T, JoinColumn>(getSubjectHolder(), this.joinColumnsEditor.getSpecifiedJoinColumnsListPropertyName()) {
			@Override
			protected ListIterable<JoinColumn> getListIterable() {
				return JoinColumnsComposite.this.joinColumnsEditor.getSpecifiedJoinColumns(this.subject);
			}

			@Override
			protected int size_() {
				return JoinColumnsComposite.this.joinColumnsEditor.getSpecifiedJoinColumnsSize(this.subject);
			}
		};
	}


	private ListValueModel<JoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<JoinColumn>(buildDefaultJoinColumnHolder());

	}

	private PropertyValueModel<JoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapterXXXX<T, JoinColumn>(getSubjectHolder(), this.joinColumnsEditor.getDefaultPropertyName()) {
			@Override
			protected JoinColumn buildValue_() {
				return JoinColumnsComposite.this.joinColumnsEditor.getDefaultJoinColumn(this.subject);
			}
		};
	}


	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				JoinColumn joinColumn = (JoinColumn) element;

				return buildJoinColumnLabel(joinColumn);
			}
		};
	}

	public void setSelectedJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.selectedJoinColumnsModel.setValues(new SingleElementIterable<SpecifiedJoinColumn>(joinColumn));
	}

	/**
	 * The editor is used to complete the behavior of this pane.
	 */
	public static interface JoinColumnsEditor<T> {

		/**
		 * Add a join column to the given subject and return it
		 */
		SpecifiedJoinColumn addJoinColumn(T subject);

		/**
		 * Edit the given join column, the Edit button was pressed
		 * while this join column was selected.
		 */
		void editJoinColumn(T subject, JoinColumn joinColumn);

		/**
		 * Return whether the subject has specified join columns
		 */
		boolean hasSpecifiedJoinColumns(T subject);

		/**
		 * Return the spcified join columns from the given subject
		 */
		ListIterable<JoinColumn> getSpecifiedJoinColumns(T subject);

		/**
		 * Return the number of specified join columns on the given subject
		 */
		int getSpecifiedJoinColumnsSize(T subject);

		/**
		 * Return the default join column from the given subject or null.
		 */
		JoinColumn getDefaultJoinColumn(T subject);

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
		void removeJoinColumn(T subject, SpecifiedJoinColumn joinColumn);
	}
}