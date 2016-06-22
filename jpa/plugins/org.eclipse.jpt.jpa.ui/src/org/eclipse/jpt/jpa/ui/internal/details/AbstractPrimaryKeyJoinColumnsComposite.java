/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.AbstractAdapter;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractPrimaryKeyJoinColumnsComposite<T extends Entity>
	extends Pane<T>
{
	protected ModifiableCollectionValueModel<SpecifiedPrimaryKeyJoinColumn> selectedPkJoinColumnsModel;

	public AbstractPrimaryKeyJoinColumnsComposite(Pane<? extends T> subjectHolder,
	                                      Composite parent) {

		super(subjectHolder, parent);
	}

	SpecifiedPrimaryKeyJoinColumn addJoinColumn(PrimaryKeyJoinColumnStateObject stateObject) {
		SpecifiedPrimaryKeyJoinColumn joinColumn = getSubject().addSpecifiedPrimaryKeyJoinColumn();
		stateObject.updateJoinColumn(joinColumn);
		return joinColumn;
	}

	SpecifiedPrimaryKeyJoinColumn addPrimaryKeyJoinColumn() {
		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(this.getShell(), this.getResourceManager(), this.getSubject());
		dialog.setBlockOnOpen(true);
		dialog.open();
		return dialog.wasConfirmed() ? this.addJoinColumn(dialog.getSubject()) : null;
	}

	protected abstract ListValueModel<? extends PrimaryKeyJoinColumn> buildDefaultJoinColumnsListModel();

	private ModifiableCollectionValueModel<SpecifiedPrimaryKeyJoinColumn> buildSelectedJoinColumnsModel() {
		return new SimpleCollectionValueModel<>();
	}

	String buildJoinColumnLabel(PrimaryKeyJoinColumn joinColumn) {
		if (joinColumn.isVirtual()) {
			return NLS.bind(
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS_DEFAULT,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS_BOTH_DEFAULT,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS_FIRST_DEFAULT,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS_SECOND_DEFAULT,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		return NLS.bind(
			JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_MAPPING_BETWEEN_TWO_PARAMS,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private Adapter<SpecifiedPrimaryKeyJoinColumn> buildJoinColumnsAdapter() {
		return new AbstractAdapter<SpecifiedPrimaryKeyJoinColumn>() {
			public SpecifiedPrimaryKeyJoinColumn addNewItem() {
				return addPrimaryKeyJoinColumn();
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_EDIT;
			}

			@Override
			public void optionOnSelection(CollectionValueModel<SpecifiedPrimaryKeyJoinColumn> selectedItemsModel) {
				editPrimaryKeyJoinColumn(selectedItemsModel.iterator().next());
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<SpecifiedPrimaryKeyJoinColumn> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<SpecifiedPrimaryKeyJoinColumn> selectedItemsModel) {
				SpecifiedPrimaryKeyJoinColumn joinColumn =selectedItemsModel.iterator().next();
				getSubject().removeSpecifiedPrimaryKeyJoinColumn(joinColumn);
			}
		};
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return buildJoinColumnLabel((PrimaryKeyJoinColumn) element);
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildOverrideDefaultPrimaryKeyJoinColumnModel() {
		return ListValueModelTools.isNotEmptyModifiablePropertyValueModel(this.buildSpecifiedJoinColumnsListModel(), new OverrideDefaultPrimaryKeyJoinColumnModelSetValueClosure());
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListModel() {
		List<ListValueModel<? extends PrimaryKeyJoinColumn>> list = new ArrayList<>();
		list.add(buildSpecifiedJoinColumnsListModel());
		list.add(buildDefaultJoinColumnsListModel());
		return CompositeListValueModel.forModels(list);
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildUIPrimaryKeyJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<>(
			buildPrimaryKeyJoinColumnsListModel(),
			NamedColumn.SPECIFIED_NAME_PROPERTY,
			NamedColumn.DEFAULT_NAME_PROPERTY,
			BaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			BaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY
		);
	}

	ListValueModel<SpecifiedPrimaryKeyJoinColumn> buildSpecifiedJoinColumnsListModel() {
		return new ListAspectAdapter<Entity, SpecifiedPrimaryKeyJoinColumn>(getSubjectHolder(), Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<SpecifiedPrimaryKeyJoinColumn> getListIterable() {
				return new SuperListIterableWrapper<>(this.subject.getSpecifiedPrimaryKeyJoinColumns());
			}

			@Override
			protected int size_() {
				return this.subject.getSpecifiedPrimaryKeyJoinColumnsSize();
			}
		};
	}

	void editJoinColumn(PrimaryKeyJoinColumnStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	void editPrimaryKeyJoinColumn(SpecifiedPrimaryKeyJoinColumn joinColumn) {
		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(this.getShell(), this.getResourceManager(), this.getSubject(), joinColumn);
		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			editJoinColumn(dialog.getSubject());
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.selectedPkJoinColumnsModel = buildSelectedJoinColumnsModel();
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addTitledGroup(
			parent,
			JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_PRIMARY_KEY_JOIN_COLUMN
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Override Default Join Columns check box
		addCheckBox(
			container,
			JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_OVERRIDE_DEFAULT_PRIMARY_KEY_JOIN_COLUMNS,
			buildOverrideDefaultPrimaryKeyJoinColumnModel(),
			null
		);
		// Primary Key Join Columns list pane
		new AddRemoveListPane<Entity, SpecifiedPrimaryKeyJoinColumn>(
			this,
			container,
			buildJoinColumnsAdapter(),
			buildUIPrimaryKeyJoinColumnsListModel(),
			this.selectedPkJoinColumnsModel,
			buildJoinColumnsListLabelProvider(),
			buildOverrideDefaultPrimaryKeyJoinColumnModel(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);
	}

	void updatePrimaryKeyJoinColumns(boolean selected) {
		if (this.isPopulating()) {
			return;
		}

		this.setPopulating(true);

		try {
			if (selected) {
				getSubject().convertDefaultPrimaryKeyJoinColumnsToSpecified();
			} else {
				getSubject().clearSpecifiedPrimaryKeyJoinColumns();
			}
		} finally {
			this.setPopulating(false);
		}
	}
	
	class OverrideDefaultPrimaryKeyJoinColumnModelSetValueClosure
		implements BooleanClosure.Adapter
	{
		public void execute(boolean value) {
			updatePrimaryKeyJoinColumns(value);
		}
	}
}
