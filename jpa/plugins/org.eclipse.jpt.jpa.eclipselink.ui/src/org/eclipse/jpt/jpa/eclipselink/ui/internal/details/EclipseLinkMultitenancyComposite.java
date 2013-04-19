/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.swt.bind.SWTBindTools;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PredicatePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ReadOnlyModifiablePropertyValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_4;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkTenantDiscriminatorColumnsComposite.TenantDiscriminatorColumnsEditor;
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
	private EclipseLinkTenantDiscriminatorColumnsComposite<EclipseLinkMultitenancy2_3> tenantDiscriminatorColumnsComposite;

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
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MULTITENANCY_COMPOSITE_STRATEGY,
			buildMultitenantHolder(),
			null);
		this.addMultitenantStrategyCombo(container).getControl();

		// Include criteria tri-state check box
		TriStateCheckBox includeCriteriaCheckBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MULTITENANCY_COMPOSITE_INCLUDE_CRITERIA,
			buildIncludeCriteriaHolder(),
			buildIncludeCriteriaStringHolder(),
			EclipseLinkHelpContextIds.MULTITENANCY_INCLUDE_CRITERIA);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		includeCriteriaCheckBox.getCheckBox().setLayoutData(gridData);
		SWTBindTools.controlVisibleState(this.buildIncludeCriteriaCheckBoxIsVisibleModel(), includeCriteriaCheckBox.getCheckBox());

		// Tenant discriminator columns group pane
		Group tenantDiscriminatorColumnGroupPane = addTitledGroup(
			container,
			JptJpaEclipseLinkUiDetailsMessages.TENANT_DISCRIMINATOR_COLUMNS_GROUP_LABEL
		);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		tenantDiscriminatorColumnGroupPane.setLayoutData(gridData);

		// Override Default Tenant Discriminator Columns check box
		addCheckBox(
			tenantDiscriminatorColumnGroupPane,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MULTITENANCY_OVERRIDE_DEFAULT_TENANT_DISCRIMINATOR_COLUMNS,
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
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MULTITENANCY_COMPOSITE_SINGLE_TABLE;
					case TABLE_PER_TENANT :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MULTITENANCY_COMPOSITE_TABLE_PER_TENANT;
					case VPD :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MULTITENANCY_COMPOSITE_VPD;
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
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MULTITENANCY_COMPOSITE_INCLUDE_CRITERIA_WITH_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_MULTITENANCY_COMPOSITE_INCLUDE_CRITERIA;
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

	protected EclipseLinkTenantDiscriminatorColumnsComposite<EclipseLinkMultitenancy2_3>  buildTenantDiscriminatorColumnsComposite(Composite container) {
		return new EclipseLinkTenantDiscriminatorColumnsComposite<EclipseLinkMultitenancy2_3>(
				this,
				this.getSubjectHolder(),
				new TenantDiscriminatorColumnPaneEnablerHolder(),
				container,
				this.buildTenantDiscriminatorColumnsEditor()
			);
	}

	protected TenantDiscriminatorColumnsEditor<EclipseLinkMultitenancy2_3> buildTenantDiscriminatorColumnsEditor() {
		return new TenantDiscriminatorColumnsProvider();
	}

	class TenantDiscriminatorColumnsProvider implements TenantDiscriminatorColumnsEditor<EclipseLinkMultitenancy2_3> {

		public EclipseLinkTenantDiscriminatorColumn2_3 addTenantDiscriminatorColumn(EclipseLinkMultitenancy2_3 subject) {
			EclipseLinkSpecifiedTenantDiscriminatorColumn2_3 column = subject.addSpecifiedTenantDiscriminatorColumn();
			column.setSpecifiedName(EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_NAME);
			return column;
		}

		public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns(EclipseLinkMultitenancy2_3 subject) {
			return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(subject.getDefaultTenantDiscriminatorColumns());
		}

		public int getDefaultTenantDiscriminatorColumnsSize(EclipseLinkMultitenancy2_3 subject) {
			return subject.getDefaultTenantDiscriminatorColumnsSize();
		}

		public String getDefaultTenantDiscriminatorsListPropertyName() {
			return EclipseLinkMultitenancy2_3.DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}

		public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns(EclipseLinkMultitenancy2_3 subject) {
			return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(subject.getSpecifiedTenantDiscriminatorColumns());
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

		public void removeTenantDiscriminatorColumn(EclipseLinkMultitenancy2_3 subject, EclipseLinkTenantDiscriminatorColumn2_3 column) {
			subject.removeSpecifiedTenantDiscriminatorColumn((EclipseLinkSpecifiedTenantDiscriminatorColumn2_3) column);
		}
	}

	private PropertyValueModel<Boolean> buildIncludeCriteriaCheckBoxIsVisibleModel() {
		return new PredicatePropertyValueModel<EclipseLinkMultitenancy2_3>(this.getSubjectHolder(), IS_COMPATIBLE_WITH_ECLIPSELINK_2_4);
	}

	private static final Predicate<EclipseLinkMultitenancy2_3> IS_COMPATIBLE_WITH_ECLIPSELINK_2_4 =
			PredicateTools.nullCheck(new EclipseLinkVersionIsCompatibleWith(EclipseLinkJpaPlatformFactory2_4.VERSION));

	private ModifiablePropertyValueModel<Boolean> buildOverrideDefaultTenantDiscriminatorColumnHolder() {
		return new OverrideDefaultTenantDiscriminatorColumnHolder();
	}

	ListValueModel<EclipseLinkTenantDiscriminatorColumn2_3> buildSpecifiedTenantDiscriminatorColumnsListHolder() {
		return new ListAspectAdapter<EclipseLinkMultitenancy2_3, EclipseLinkTenantDiscriminatorColumn2_3>(
				getSubjectHolder(), EclipseLinkMultitenancy2_3.SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST) {
			@Override
			protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(this.subject.getSpecifiedTenantDiscriminatorColumns());
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
					EclipseLinkSpecifiedTenantDiscriminatorColumn2_3 newTenantDiscriminatorColumn = subject.addSpecifiedTenantDiscriminatorColumn();
					newTenantDiscriminatorColumn.setSpecifiedName(EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_NAME);
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
