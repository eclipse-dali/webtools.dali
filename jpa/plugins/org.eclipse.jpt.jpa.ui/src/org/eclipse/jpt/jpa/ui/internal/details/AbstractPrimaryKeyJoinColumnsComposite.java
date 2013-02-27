/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ModifiablePrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractPrimaryKeyJoinColumnsComposite<T extends Entity>
	extends Pane<T>
{
	protected ModifiableCollectionValueModel<ModifiablePrimaryKeyJoinColumn> selectedPkJoinColumnsModel;

	public AbstractPrimaryKeyJoinColumnsComposite(Pane<? extends T> subjectHolder,
	                                      Composite parent) {

		super(subjectHolder, parent);
	}

	ModifiablePrimaryKeyJoinColumn addJoinColumn(PrimaryKeyJoinColumnStateObject stateObject) {
		ModifiablePrimaryKeyJoinColumn joinColumn = getSubject().addSpecifiedPrimaryKeyJoinColumn();
		stateObject.updateJoinColumn(joinColumn);
		return joinColumn;
	}

	ModifiablePrimaryKeyJoinColumn addPrimaryKeyJoinColumn() {
		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(this.getShell(), this.getResourceManager(), this.getSubject());
		dialog.setBlockOnOpen(true);
		dialog.open();
		return dialog.wasConfirmed() ? this.addJoinColumn(dialog.getSubject()) : null;
	}

	protected abstract ListValueModel<? extends ReadOnlyPrimaryKeyJoinColumn> buildDefaultJoinColumnsListHolder();

	private ModifiableCollectionValueModel<ModifiablePrimaryKeyJoinColumn> buildSelectedJoinColumnsModel() {
		return new SimpleCollectionValueModel<ModifiablePrimaryKeyJoinColumn>();
	}

	String buildJoinColumnLabel(ReadOnlyPrimaryKeyJoinColumn joinColumn) {
		if (joinColumn.isVirtual()) {
			return NLS.bind(
				JptJpaUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptJpaUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsBothDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptJpaUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsFirstDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptJpaUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsSecDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		return NLS.bind(
			JptJpaUiDetailsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParams,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private Adapter<ModifiablePrimaryKeyJoinColumn> buildJoinColumnsAdapter() {
		return new AbstractAdapter<ModifiablePrimaryKeyJoinColumn>() {
			public ModifiablePrimaryKeyJoinColumn addNewItem() {
				return addPrimaryKeyJoinColumn();
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptJpaUiDetailsMessages.PrimaryKeyJoinColumnsComposite_edit;
			}

			@Override
			public void optionOnSelection(CollectionValueModel<ModifiablePrimaryKeyJoinColumn> selectedItemsModel) {
				editPrimaryKeyJoinColumn(selectedItemsModel.iterator().next());
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<ModifiablePrimaryKeyJoinColumn> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<ModifiablePrimaryKeyJoinColumn> selectedItemsModel) {
				ModifiablePrimaryKeyJoinColumn joinColumn =selectedItemsModel.iterator().next();
				getSubject().removeSpecifiedPrimaryKeyJoinColumn(joinColumn);
			}
		};
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return buildJoinColumnLabel((ReadOnlyPrimaryKeyJoinColumn) element);
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultPrimaryKeyJoinColumnHolder();
	}

	private ListValueModel<ReadOnlyPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListHolder() {
		List<ListValueModel<? extends ReadOnlyPrimaryKeyJoinColumn>> list = new ArrayList<ListValueModel<? extends ReadOnlyPrimaryKeyJoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnsListHolder());
		return CompositeListValueModel.forModels(list);
	}

	private ListValueModel<ReadOnlyPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<ReadOnlyPrimaryKeyJoinColumn>(
			buildPrimaryKeyJoinColumnsListHolder(),
			ReadOnlyNamedColumn.SPECIFIED_NAME_PROPERTY,
			ReadOnlyNamedColumn.DEFAULT_NAME_PROPERTY,
			ReadOnlyBaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			ReadOnlyBaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY
		);
	}

	ListValueModel<ModifiablePrimaryKeyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<Entity, ModifiablePrimaryKeyJoinColumn>(getSubjectHolder(), Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<ModifiablePrimaryKeyJoinColumn> getListIterable() {
				return new SuperListIterableWrapper<ModifiablePrimaryKeyJoinColumn>(subject.getSpecifiedPrimaryKeyJoinColumns());
			}

			@Override
			protected int size_() {
				return subject.getSpecifiedPrimaryKeyJoinColumnsSize();
			}
		};
	}

	void editJoinColumn(PrimaryKeyJoinColumnStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	void editPrimaryKeyJoinColumn(ModifiablePrimaryKeyJoinColumn joinColumn) {
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
			JptJpaUiDetailsMessages.PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Override Default Join Columns check box
		addCheckBox(
			container,
			JptJpaUiDetailsMessages.PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns,
			buildOverrideDefaultJoinColumnHolder(),
			null
		);
		// Primary Key Join Columns list pane
		new AddRemoveListPane<Entity, ModifiablePrimaryKeyJoinColumn>(
			this,
			container,
			buildJoinColumnsAdapter(),
			buildPrimaryKeyJoinColumnsListModel(),
			this.selectedPkJoinColumnsModel,
			buildJoinColumnsListLabelProvider(),
			buildOverrideDefaultJoinColumnHolder(),
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
	
	private class OverrideDefaultPrimaryKeyJoinColumnHolder extends ListPropertyValueModelAdapter<Boolean>
	                                              implements ModifiablePropertyValueModel<Boolean> {

		public OverrideDefaultPrimaryKeyJoinColumnHolder() {
			super(buildSpecifiedJoinColumnsListHolder());
		}

		@Override
		protected Boolean buildValue() {
			return listModel.size() > 0;
		}

		public void setValue(Boolean value) {
			updatePrimaryKeyJoinColumns(value.booleanValue());
		}
	}
}