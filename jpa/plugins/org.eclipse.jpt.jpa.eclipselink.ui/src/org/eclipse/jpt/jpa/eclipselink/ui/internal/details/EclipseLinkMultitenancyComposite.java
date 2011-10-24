/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ReadOnlyWritablePropertyValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.TenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.TenantDiscriminatorColumnsComposite.TenantDiscriminatorColumnsEditor;
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

		super(parentPane, subjectHolder, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();

		Composite subPane = addSubPane(
			container, 0, groupBoxMargin, 0, groupBoxMargin
		);

		// Strategy widgets
		addLabeledComposite(
			subPane,
			EclipseLinkUiDetailsMessages.EclipseLinkMultitenancyComposite_strategy,
			addMultitenantStrategyCombo(subPane),
			EclipseLinkHelpContextIds.MULTITENANCY_STRATEGY
		);


		// Tenant discriminator columns group pane
		Group tenantDiscriminatorColumnGroupPane = addTitledGroup(
			container,
			EclipseLinkUiDetailsMessages.TenantDiscriminatorColumns_groupLabel
		);

		// Override Default Tenant Discriminator Columns check box
		addCheckBox(
			addSubPane(tenantDiscriminatorColumnGroupPane, 8),
			EclipseLinkUiDetailsMessages.EclipseLinkMultitenancy_overrideDefaultTenantDiscriminatorColumns,
			buildOverrideDefaultTenantDiscriminatorColumnHolder(),
			null
		);

		this.tenantDiscriminatorColumnsComposite = this.buildTenantDiscriminatorColumnsComposite(tenantDiscriminatorColumnGroupPane);

		this.tenantDiscriminatorColumnsComposite.installListPaneEnabler(new TenantDiscriminatorColumnPaneEnablerHolder());
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
				return buildDisplayString(
					EclipseLinkUiDetailsMessages.class,
					EclipseLinkMultitenancyComposite.class,
					value
				);
			}

			@Override
			protected EclipseLinkMultitenantType2_3 getValue() {
				return getSubject().getSpecifiedType();
			}

			@Override
			protected void setValue(EclipseLinkMultitenantType2_3 value) {
				getSubject().setSpecifiedType(value);
			}
		};
	}

	protected TenantDiscriminatorColumnsComposite<EclipseLinkMultitenancy2_3>  buildTenantDiscriminatorColumnsComposite(Composite container) {
		return new TenantDiscriminatorColumnsComposite<EclipseLinkMultitenancy2_3>(
			getSubjectHolder(),
			container,
			getWidgetFactory(),
			buildTenantDiscriminatorColumnsEditor());
	}


	protected TenantDiscriminatorColumnsEditor<EclipseLinkMultitenancy2_3> buildTenantDiscriminatorColumnsEditor() {
		return new TenantDiscriminatorColumnsProvider();
	}

	class TenantDiscriminatorColumnsProvider implements TenantDiscriminatorColumnsEditor<EclipseLinkMultitenancy2_3> {

		public void addTenantDiscriminatorColumn(EclipseLinkMultitenancy2_3 subject) {
			TenantDiscriminatorColumn2_3 column = subject.addSpecifiedTenantDiscriminatorColumn();
			column.setSpecifiedName(ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_NAME);
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

		public void removeTenantDiscriminatorColumns(EclipseLinkMultitenancy2_3 subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; index-- > 0; ) {
				subject.removeSpecifiedTenantDiscriminatorColumn(selectedIndices[index]);
			}
		}
	}

	void setSelectedTenantDiscriminatorColumn(ReadOnlyTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.tenantDiscriminatorColumnsComposite.setSelectedTenantDiscriminatorColumn(tenantDiscriminatorColumn);
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultTenantDiscriminatorColumnHolder() {
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
		implements WritablePropertyValueModel<Boolean> 
	{
		public OverrideDefaultTenantDiscriminatorColumnHolder() {
			super(buildSpecifiedTenantDiscriminatorColumnsListHolder());
		}

		@Override
		protected Boolean buildValue() {
			return Boolean.valueOf(this.listHolder.size() > 0);
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

	private class TenantDiscriminatorColumnPaneEnablerHolder 
		extends CachingTransformationPropertyValueModel<EclipseLinkMultitenancy2_3, Boolean>
	{
		private StateChangeListener stateChangeListener;


		public TenantDiscriminatorColumnPaneEnablerHolder() {
			super(
				new ValueListAdapter<EclipseLinkMultitenancy2_3>(
					new ReadOnlyWritablePropertyValueModelWrapper<EclipseLinkMultitenancy2_3>(getSubjectHolder()), 
					EclipseLinkMultitenancy2_3.SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST));
			this.stateChangeListener = buildStateChangeListener();
		}

		private StateChangeListener buildStateChangeListener() {
			return new StateChangeListener() {
				public void stateChanged(StateChangeEvent event) {
					valueStateChanged();
				}
			};
		}

		void valueStateChanged() {
			Object oldValue = this.cachedValue;
			Object newValue = transformNew(this.valueHolder.getValue());
			firePropertyChanged(VALUE, oldValue, newValue);
		}

		@Override
		protected Boolean transform(EclipseLinkMultitenancy2_3 value) {
			if (value == null) {
				return Boolean.FALSE;
			}
			return super.transform(value);
		}

		@Override
		protected Boolean transform_(EclipseLinkMultitenancy2_3 value) {
			return Boolean.valueOf(value.getSpecifiedTenantDiscriminatorColumnsSize() > 0);
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