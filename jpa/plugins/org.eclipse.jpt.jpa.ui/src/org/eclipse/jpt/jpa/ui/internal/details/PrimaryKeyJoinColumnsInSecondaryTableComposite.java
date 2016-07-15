/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.closure.ClosureTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedOrVirtual;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.SpecifiedBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class PrimaryKeyJoinColumnsInSecondaryTableComposite
	extends Pane<SecondaryTable>
{
	private ModifiableCollectionValueModel<SpecifiedPrimaryKeyJoinColumn> selectedPkJoinColumnsModel;


	public PrimaryKeyJoinColumnsInSecondaryTableComposite(
			Pane<?> parent,
			PropertyValueModel<? extends SecondaryTable> tableModel,
			Composite parentComposite) {
		super(parent, tableModel, parentComposite);
	}

	SpecifiedPrimaryKeyJoinColumn addJoinColumn(PrimaryKeyJoinColumnInSecondaryTableStateObject stateObject) {

		SpecifiedSecondaryTable secondaryTable = (SpecifiedSecondaryTable) stateObject.getOwner();
		int index = secondaryTable.getSpecifiedPrimaryKeyJoinColumnsSize();

		SpecifiedPrimaryKeyJoinColumn joinColumn = secondaryTable.addSpecifiedPrimaryKeyJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);

		return joinColumn;
	}

	SpecifiedPrimaryKeyJoinColumn addPrimaryKeyJoinColumn() {
		PrimaryKeyJoinColumnInSecondaryTableDialog dialog = new PrimaryKeyJoinColumnInSecondaryTableDialog(this.getShell(), this.getResourceManager(), this.getSubject());
		dialog.setBlockOnOpen(true);
		dialog.open();
		return (dialog.wasConfirmed()) ? this.addJoinColumn(dialog.getSubject()) : null;
	}

	private PropertyValueModel<Boolean> buildControlBooleanModel() {
		return PropertyValueModelTools.valueAffirms(this.getSubjectHolder(), SpecifiedOrVirtual.IS_SPECIFIED_PREDICATE, false);
	}

	private PropertyValueModel<PrimaryKeyJoinColumn> buildDefaultJoinColumnModel() {
		return new PropertyAspectAdapterXXXX<SecondaryTable, PrimaryKeyJoinColumn>(getSubjectHolder(), SecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN) {
			@Override
			protected PrimaryKeyJoinColumn buildValue_() {
				return this.subject.getDefaultPrimaryKeyJoinColumn();
			}
		};
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildDefaultPrimaryKeyJoinColumnListModel() {
		return new PropertyListValueModelAdapter<>(buildDefaultJoinColumnModel());
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

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				PrimaryKeyJoinColumn joinColumn = (PrimaryKeyJoinColumn) element;
				return buildJoinColumnLabel(joinColumn);
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildOverrideDefaultPrimaryKeyJoinColumnModel() {
		PluggablePropertyValueModel.Adapter.Factory<Boolean> factory = ListValueModelTools.pluggablePropertyValueModelAdapterFactory(
				this.buildSpecifiedPrimaryKeyJoinColumnsListModel(),
				new OverrideDefaultPrimaryKeyJoinColumnModelTransformer()
			);
		Closure<Boolean> closure = ClosureTools.booleanClosure(new OverrideDefaultPrimaryKeyJoinColumnModelSetValueClosure());
		return PropertyValueModelTools.pluggableModifiablePropertyValueModel(factory, closure);
	}

	private AddRemovePane.Adapter<SpecifiedPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnAdapter() {
		return new AddRemovePane.AbstractAdapter<SpecifiedPrimaryKeyJoinColumn>() {

			public SpecifiedPrimaryKeyJoinColumn addNewItem() {
				return addPrimaryKeyJoinColumn();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<SpecifiedPrimaryKeyJoinColumn> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<SpecifiedPrimaryKeyJoinColumn> selectedItemsModel) {
				SpecifiedPrimaryKeyJoinColumn pkJoinColumn = selectedItemsModel.iterator().next();
				((SpecifiedSecondaryTable) getSubject()).removeSpecifiedPrimaryKeyJoinColumn(pkJoinColumn);
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
		};
	}

	private ModifiableCollectionValueModel<SpecifiedPrimaryKeyJoinColumn> buildSelectedPkJoinColumnsModel() {
		return new SimpleCollectionValueModel<>();
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListModel() {
		List<ListValueModel<PrimaryKeyJoinColumn>> list = new ArrayList<>();
		list.add(buildSpecifiedPrimaryKeyJoinColumnsListModel());
		list.add(buildDefaultPrimaryKeyJoinColumnListModel());
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

	ListValueModel<PrimaryKeyJoinColumn> buildSpecifiedPrimaryKeyJoinColumnsListModel() {
		return new ListAspectAdapter<SecondaryTable, PrimaryKeyJoinColumn>(getSubjectHolder(), SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<PrimaryKeyJoinColumn> getListIterable() {
				return new SuperListIterableWrapper<>(this.subject.getSpecifiedPrimaryKeyJoinColumns());
			}

			@Override
			protected int size_() {
				return this.subject.getSpecifiedPrimaryKeyJoinColumnsSize();
			}
		};
	}

	void editPrimaryKeyJoinColumn(SpecifiedPrimaryKeyJoinColumn joinColumn) {
		PrimaryKeyJoinColumnInSecondaryTableDialog dialog = new PrimaryKeyJoinColumnInSecondaryTableDialog(this.getShell(), this.getResourceManager(), this.getSubject(), joinColumn);
		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			editPrimaryKeyJoinColumn(dialog.getSubject());
		}
	}

	void editPrimaryKeyJoinColumn(PrimaryKeyJoinColumnInSecondaryTableStateObject stateObject) {
		stateObject.updateJoinColumn((SpecifiedBaseJoinColumn) stateObject.getJoinColumn());
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedPkJoinColumnsModel = buildSelectedPkJoinColumnsModel();
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addTitledGroup(
			parent,
			JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_PRIMARY_KEY_JOIN_COLUMN
		);
	}

	@SuppressWarnings("unused")
	@Override
	protected void initializeLayout(Composite container) {
		// Override Default check box
		Button overrideDefaultCheckBox = addCheckBox(
			container,
			JptJpaUiDetailsMessages.PRIMARY_KEY_JOIN_COLUMNS_COMPOSITE_OVERRIDE_DEFAULT_PRIMARY_KEY_JOIN_COLUMNS,
			buildOverrideDefaultPrimaryKeyJoinColumnModel(),
			null,
			buildControlBooleanModel()
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalIndent = 8;
		overrideDefaultCheckBox.setLayoutData(gridData);

		// Primary Key Join Columns list pane
		new AddRemoveListPane<SecondaryTable, SpecifiedPrimaryKeyJoinColumn>(
			this,
			container,
			buildPrimaryKeyJoinColumnAdapter(),
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

		SpecifiedSecondaryTable secondaryTable =  (SpecifiedSecondaryTable) this.getSubject();

		this.setPopulating(true);

		try {
			if (selected) {
				if (secondaryTable.getDefaultPrimaryKeyJoinColumn() != null) { //TODO can this be null?
					secondaryTable.convertDefaultPrimaryKeyJoinColumnsToSpecified();
				}
			} else {
				secondaryTable.clearSpecifiedPrimaryKeyJoinColumns();
			}
		} finally {
			this.setPopulating(false);
		}
	}

	class OverrideDefaultPrimaryKeyJoinColumnModelTransformer
		extends TransformerAdapter<Collection<?>, Boolean>
	{
		@Override
		public Boolean transform(Collection<?> specifiedPKJoinColumns) {
			return Boolean.valueOf(this.transform_(specifiedPKJoinColumns));
		}

		protected boolean transform_(Collection<?> specifiedPKJoinColumns) {
			SecondaryTable table = getSubject();
			return (table != null) && ! table.isVirtual() && specifiedPKJoinColumns.size() > 0;
		}
	}
		
	class OverrideDefaultPrimaryKeyJoinColumnModelSetValueClosure
		implements BooleanClosure.Adapter
	{
		public void execute(boolean value) {
			PrimaryKeyJoinColumnsInSecondaryTableComposite.this.updatePrimaryKeyJoinColumns(value);
		}
	}
}
