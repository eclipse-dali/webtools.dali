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
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
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

	protected abstract ListValueModel<? extends PrimaryKeyJoinColumn> buildDefaultJoinColumnsListHolder();

	private ModifiableCollectionValueModel<SpecifiedPrimaryKeyJoinColumn> buildSelectedJoinColumnsModel() {
		return new SimpleCollectionValueModel<SpecifiedPrimaryKeyJoinColumn>();
	}

	String buildJoinColumnLabel(PrimaryKeyJoinColumn joinColumn) {
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
				return JptJpaUiDetailsMessages.PrimaryKeyJoinColumnsComposite_edit;
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

	private ModifiablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultPrimaryKeyJoinColumnHolder();
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListHolder() {
		List<ListValueModel<? extends PrimaryKeyJoinColumn>> list = new ArrayList<ListValueModel<? extends PrimaryKeyJoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnsListHolder());
		return CompositeListValueModel.forModels(list);
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<PrimaryKeyJoinColumn>(
			buildPrimaryKeyJoinColumnsListHolder(),
			NamedColumn.SPECIFIED_NAME_PROPERTY,
			NamedColumn.DEFAULT_NAME_PROPERTY,
			BaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			BaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY
		);
	}

	ListValueModel<SpecifiedPrimaryKeyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<Entity, SpecifiedPrimaryKeyJoinColumn>(getSubjectHolder(), Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<SpecifiedPrimaryKeyJoinColumn> getListIterable() {
				return new SuperListIterableWrapper<SpecifiedPrimaryKeyJoinColumn>(subject.getSpecifiedPrimaryKeyJoinColumns());
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
		new AddRemoveListPane<Entity, SpecifiedPrimaryKeyJoinColumn>(
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