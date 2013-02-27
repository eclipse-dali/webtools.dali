/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ModifiableOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOrderingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmManyToManyMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmOneToManyMappingComposite;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Ordering -------------------------------------------------------------- |
 * | |                                                                       | |
 * | | o None                                                                | |
 * | |                                                                       | |
 * | | o Primary Key                                                         | |
 * | |                                                                       | |
 * | | o Custom                                                              | |
 * | |   ------------------------------------------------------------------- | |
 * | |   | I                                                               | | |
 * | |   ------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see CollectionMapping
 * @see OrmManyToManyMappingComposite
 * @see OrmOneToManyMappingComposite
 */
public class Ordering2_0Composite
	extends AbstractOrderingComposite<Orderable2_0>
{
	public Ordering2_0Composite(Pane<? extends CollectionMapping2_0> parentPane, Composite parentComposite) {
		super(parentPane, parentComposite);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<Orderable2_0> orderableHolder = buildOrderableModel();

		// No Ordering radio button
		addRadioButton(
			container,
			JptJpaUiDetailsMessages.OrderingComposite_none,
			buildNoOrderingHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_BY_NO_ORDERING
		);

		// Order by Primary Key radio button
		addRadioButton(
			container,
			JptJpaUiDetailsMessages.OrderingComposite_primaryKey,
			buildPrimaryKeyOrderingHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_BY_PRIMARY_KEY_ORDERING
		);

		// Custom Ordering radio button
		addRadioButton(
			container,
			JptJpaUiDetailsMessages.OrderingComposite_custom,
			buildCustomOrderingHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_BY_CUSTOM_ORDERING
		);

		// Custom Ordering text field
		Text orderingText = addText(
			container,
			buildSpecifiedOrderByHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_BY,
			buildCustomOrderingHolder(orderableHolder)
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = 16;
		orderingText.setLayoutData(gridData);

		
		// Order Column Ordering radio button
		addRadioButton(
			container,
			JptJpaUiDetailsMessages2_0.ORDERING_COMPOSITE_ORDER_COLUMN,
			buildOrderColumnOrderingHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_COLUMN_ORDERING
		);

		OrderColumnComposite orderColumnComposite = new OrderColumnComposite(
			this,
			buildOrderColumnHolder(orderableHolder),
			buildPaneEnablerHolder(orderableHolder), 
			container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = 16;
		orderColumnComposite.getControl().setLayoutData(gridData);

	}
	
	private PropertyValueModel<Boolean> buildPaneEnablerHolder(PropertyValueModel<Orderable2_0> orderableHolder) {
		return buildOrderColumnOrderingHolder(orderableHolder);
	}

	protected ModifiablePropertyValueModel<Boolean> buildOrderColumnOrderingHolder(PropertyValueModel<Orderable2_0> orderableHolder) {
		return new PropertyAspectAdapter<Orderable2_0, Boolean>(orderableHolder, Orderable2_0.ORDER_COLUMN_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isOrderColumnOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setOrderColumnOrdering(value.booleanValue());
			}
		};
	}
	
	protected PropertyValueModel<ModifiableOrderColumn2_0> buildOrderColumnHolder(PropertyValueModel<Orderable2_0> orderableHolder) {
		return new PropertyAspectAdapter<Orderable2_0, ModifiableOrderColumn2_0>(orderableHolder) {
			@Override
			protected ModifiableOrderColumn2_0 buildValue_() {
				return this.subject.getOrderColumn();
			}
		};
	}
}