/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ReadOnlyModifiablePropertyValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.TenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.TenantDiscriminatorColumnsComposite.TenantDiscriminatorColumnsEditor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here is the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                        ----------------------------------------------- |  |
 * | Multitenant strategy:  | EnumComboViewer                           |v| |  |
 * |                        ----------------------------------------------- |  |
 * |                                                                           |
 * | - Tenant discriminator columns ---------------------------------------- | |
 * | |                                                                       | |
 * | | x Override Default                                                    | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | TenantDiscriminatorColumnsComposite                               | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 *
 * @version 3.1
 * @since 3.1
 */
public class EclipseLinkMultitenancyComposite extends Pane<EclipseLinkMultitenancy2_3>
{
	private TenantDiscriminatorColumnsComposite<EclipseLinkMultitenancy2_3> tenantDiscriminatorColumnsComposite;

	public EclipseLinkMultitenancyComposite(Pane<?> parentPane,
								PropertyValueModel<? extends EclipseLinkMultitenancy2_3> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Strategy widgets
		this.addCheckBox(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkMultitenancyComposite_strategy,
			buildMultitenantHolder(),
			null);
		this.addMultitenantStrategyCombo(container).getControl();

		// Include criteria tri-state check box
		TriStateCheckBox includeCriteriaCheckBox = addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkMultitenancyComposite_includeCriteria,
			buildIncludeCriteriaHolder(),
			buildIncludeCriteriaStringHolder(),
			EclipseLinkHelpContextIds.MULTITENANCY_INCLUDE_CRITERIA);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		includeCriteriaCheckBox.getCheckBox().setLayoutData(gridData);
		SWTTools.controlVisibleState(new EclipseLink2_4ProjectFlagModel<EclipseLinkMultitenancy2_3>(this.getSubjectHolder()), includeCriteriaCheckBox.getCheckBox());

		// Tenant discriminator columns group pane
		Group tenantDiscriminatorColumnGroupPane = addTitledGroup(
			container,
			EclipseLinkUiDetailsMessages.TenantDiscriminatorColumns_groupLabel
		);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		tenantDiscriminatorColumnGroupPane.setLayoutData(gridData);

		// Override Default Tenant Discriminator Columns check box
		addCheckBox(
			tenantDiscriminatorColumnGroupPane,
			EclipseLinkUiDetailsMessages.EclipseLinkMultitenancy_overrideDefaultTenantDiscriminatorColumns,
			buildOverrideDefaultTenantDiscriminatorColumnHolder(),
			null
		);

		this.tenantDiscriminatorColumnsComposite = this.buildTenantDiscriminatorColumnsComposite(tenantDiscriminatorColumnGroupPane);
	}
	
	private ModifiablePropertyValueModel<Boolean> buildMultitenantHolder() {
		return new PropertyAspectAdapter<EclipseLinkMultitenancy2_3, Boolean>(getSubjectHolder(), EclipseLinkMultitenancy2_3.SPECIFIED_MULTITENANT_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isSpecifiedMultitenant());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedMultitenant(value.booleanValue());
			}
		};
	}
	private EnumFormComboViewer<EclipseLinkMultitenancy2_3, EclipseLinkMultitenantType2_3> addMultitenantStrategyCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkMultitenancy2_3, EclipseLinkMultitenantType2_3>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkMultitenancy2_3.DEFAULT_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkMultitenancy2_3.SPECIFIED_TYPE_PROPERTY);
			}

			@Override
			protected EclipseLinkMultitenantType2_3[] getChoices() {
				return EclipseLinkMultitenantType2_3.values();
			}

			@Override
			protected EclipseLinkMultitenantType2_3 getDefaultValue() {
				return getSubject().getDefaultType();
			}

			@Override
			protected String displayString(EclipseLinkMultitenantType2_3 value) {
				switch (value) {
					case SINGLE_TABLE :
						return EclipseLinkUiDetailsMessages.EclipseLinkMultitenancyComposite_single_table;
					case TABLE_PER_TENANT :
						return EclipseLinkUiDetailsMessages.EclipseLinkMultitenancyComposite_table_per_tenant;
					case VPD :
						return EclipseLinkUiDetailsMessages.EclipseLinkMultitenancyComposite_vpd;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkMultitenantType2_3 getValue() {
				return getSubject().getSpecifiedType();
			}

			@Override
			protected void setValue(EclipseLinkMultitenantType2_3 value) {
				getSubject().setSpecifiedType(value);
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.MULTITENANCY_STRATEGY;
			}
		};
	}

	ModifiablePropertyValueModel<Boolean> buildIncludeCriteriaHolder() {
		return new PropertyAspectAdapter<EclipseLinkMultitenancy2_3, Boolean>(getSubjectHolder(), EclipseLinkMultitenancy2_3.SPECIFIED_INCLUDE_CRITERIA_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedIncludeCriteria();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedIncludeCriteria(value);
			}
		};
	}

	PropertyValueModel<String> buildIncludeCriteriaStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultIncludeCriteriaHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiDetailsMessages.EclipseLinkMultitenancyComposite_includeCriteriaWithDefault, defaultStringValue);
				}
				return EclipseLinkUiDetailsMessages.EclipseLinkMultitenancyComposite_includeCriteria;
			}
		};
	}

	PropertyValueModel<Boolean> buildDefaultIncludeCriteriaHolder() {
		return new PropertyAspectAdapter<EclipseLinkMultitenancy2_3, Boolean>(
				getSubjectHolder(),
				EclipseLinkMultitenancy2_3.SPECIFIED_INCLUDE_CRITERIA_PROPERTY,
				EclipseLinkMultitenancy2_3.DEFAULT_INCLUDE_CRITERIA_PROPERTY) {

			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedIncludeCriteria() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isIncludeCriteria());
			}
		};
	}

	//TODO do i need to pass in the PaneEnabler from *this* pane as a Combined property value model?
	protected TenantDiscriminatorColumnsComposite<EclipseLinkMultitenancy2_3>  buildTenantDiscriminatorColumnsComposite(Composite container) {
		return new TenantDiscriminatorColumnsComposite<EclipseLinkMultitenancy2_3>(
			getSubjectHolder(),
			new TenantDiscriminatorColumnPaneEnablerHolder(),
			container,
			getWidgetFactory(),
			buildTenantDiscriminatorColumnsEditor());
	}

	protected TenantDiscriminatorColumnsEditor<EclipseLinkMultitenancy2_3> buildTenantDiscriminatorColumnsEditor() {
		return new TenantDiscriminatorColumnsProvider();
	}

	class TenantDiscriminatorColumnsProvider implements TenantDiscriminatorColumnsEditor<EclipseLinkMultitenancy2_3> {

		public ReadOnlyTenantDiscriminatorColumn2_3 addTenantDiscriminatorColumn(EclipseLinkMultitenancy2_3 subject) {
			TenantDiscriminatorColumn2_3 column = subject.addSpecifiedTenantDiscriminatorColumn();
			column.setSpecifiedName(ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_NAME);
			return column;
		}

		public ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns(EclipseLinkMultitenancy2_3 subject) {
			return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn2_3>(subject.getDefaultTenantDiscriminatorColumns());
		}

		public int getDefaultTenantDiscriminatorColumnsSize(EclipseLinkMultitenancy2_3 subject) {
			return subject.getDefaultTenantDiscriminatorColumnsSize();
		}

		public String getDefaultTenantDiscriminatorsListPropertyName() {
			return EclipseLinkMultitenancy2_3.DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}

		public ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns(EclipseLinkMultitenancy2_3 subject) {
			return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn2_3>(subject.getSpecifiedTenantDiscriminatorColumns());
		}

		public int getSpecifiedTenantDiscriminatorColumnsSize(EclipseLinkMultitenancy2_3 subject) {
			return subject.getSpecifiedTenantDiscriminatorColumnsSize();
		}

		public String getSpecifiedTenantDiscriminatorsListPropertyName() {
			return EclipseLinkMultitenancy2_3.SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}

		public boolean hasSpecifiedTenantDiscriminatorColumns(EclipseLinkMultitenancy2_3 subject) {
			return subject.hasSpecifiedTenantDiscriminatorColumns();
		}

		public void removeTenantDiscriminatorColumn(EclipseLinkMultitenancy2_3 subject, ReadOnlyTenantDiscriminatorColumn2_3 column) {
			subject.removeSpecifiedTenantDiscriminatorColumn((TenantDiscriminatorColumn2_3) column);
		}
	}

	private ModifiablePropertyValueModel<Boolean> buildOverrideDefaultTenantDiscriminatorColumnHolder() {
		return new OverrideDefaultTenantDiscriminatorColumnHolder();
	}

	ListValueModel<ReadOnlyTenantDiscriminatorColumn2_3> buildSpecifiedTenantDiscriminatorColumnsListHolder() {
		return new ListAspectAdapter<EclipseLinkMultitenancy2_3, ReadOnlyTenantDiscriminatorColumn2_3>(
				getSubjectHolder(), EclipseLinkMultitenancy2_3.SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST) {
			@Override
			protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getListIterable() {
				return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn2_3>(this.subject.getSpecifiedTenantDiscriminatorColumns());
			}
			@Override
			protected int size_() {
				return this.subject.getSpecifiedTenantDiscriminatorColumnsSize();
			}
		};
	}

	private class OverrideDefaultTenantDiscriminatorColumnHolder 
		extends ListPropertyValueModelAdapter<Boolean>
		implements ModifiablePropertyValueModel<Boolean> 
	{
		public OverrideDefaultTenantDiscriminatorColumnHolder() {
			super(buildSpecifiedTenantDiscriminatorColumnsListHolder());
		}

		@Override
		protected Boolean buildValue() {
			return Boolean.valueOf(this.listModel.size() > 0);
		}

		public void setValue(Boolean value) {
			updateTenantDiscriminatorColumns(value.booleanValue());
		}

		private void updateTenantDiscriminatorColumns(boolean selected) {
			if (isPopulating()) {
				return;
			}

			setPopulating(true);

			try {
				EclipseLinkMultitenancy2_3 subject = getSubject();

				if (selected) {
					TenantDiscriminatorColumn2_3 newTenantDiscriminatorColumn = subject.addSpecifiedTenantDiscriminatorColumn();
					newTenantDiscriminatorColumn.setSpecifiedName(ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_NAME);
				}
				// Remove all the specified tenant discriminator columns
				else {
					for (int index = subject.getSpecifiedTenantDiscriminatorColumnsSize(); --index >= 0; ) {
						subject.removeSpecifiedTenantDiscriminatorColumn(index);
					}
				}
			}
			finally {
				setPopulating(false);
			}
		}
	}

	/* CU private */ class TenantDiscriminatorColumnPaneEnablerHolder 
		extends TransformationPropertyValueModel<EclipseLinkMultitenancy2_3, Boolean>
	{
		private StateChangeListener stateChangeListener;

		TenantDiscriminatorColumnPaneEnablerHolder() {
			super(
				new ValueListAdapter<EclipseLinkMultitenancy2_3>(
					new ReadOnlyModifiablePropertyValueModelWrapper<EclipseLinkMultitenancy2_3>(getSubjectHolder()), 
					EclipseLinkMultitenancy2_3.SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST
				)
			);
			this.stateChangeListener = this.buildStateChangeListener();
		}

		private StateChangeListener buildStateChangeListener() {
			return new StateChangeListener() {
				public void stateChanged(StateChangeEvent event) {
					TenantDiscriminatorColumnPaneEnablerHolder.this.valueStateChanged();
				}
			};
		}

		void valueStateChanged() {
			Object old = this.value;
			this.value = this.transform(this.valueModel.getValue());
			this.firePropertyChanged(VALUE, old, this.value);
		}

		@Override
		protected Boolean transform(EclipseLinkMultitenancy2_3 v) {
			return (v == null) ? Boolean.FALSE : super.transform(v);
		}

		@Override
		protected Boolean transform_(EclipseLinkMultitenancy2_3 v) {
			return Boolean.valueOf(v.getSpecifiedTenantDiscriminatorColumnsSize() > 0);
		}

		@Override
		protected void engageModel() {
			super.engageModel();
			this.valueModel.addStateChangeListener(this.stateChangeListener);
		}

		@Override
		protected void disengageModel() {
			this.valueModel.removeStateChangeListener(this.stateChangeListener);
			super.disengageModel();
		}
	}
}
