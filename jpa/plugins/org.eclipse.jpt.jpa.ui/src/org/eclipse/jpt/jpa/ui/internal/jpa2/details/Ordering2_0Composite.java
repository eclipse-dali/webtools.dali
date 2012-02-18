/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOrderingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmManyToManyMappingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.orm.OrmOneToManyMappingComposite;
import org.eclipse.swt.widgets.Composite;

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
 * @see OrmManyToManyMappingComposite - A container of this pane
 * @see OrmOneToManyMappingComposite - A container of this pane
 *
 * @version 2.3
 * @since 1.0
 */
public class Ordering2_0Composite extends AbstractOrderingComposite
{
	/**
	 * Creates a new <code>OrderingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public Ordering2_0Composite(Pane<? extends CollectionMapping> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	
	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<Orderable> orderableHolder = buildOrderableHolder();

		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages.OrderingComposite_orderingGroup
		);

		// No Ordering radio button
		addRadioButton(
			container,
			JptUiDetailsMessages.OrderingComposite_none,
			buildNoOrderingHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_BY_NO_ORDERING
		);

		// Order by Primary Key radio button
		addRadioButton(
			container,
			JptUiDetailsMessages.OrderingComposite_primaryKey,
			buildPrimaryKeyOrderingHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_BY_PRIMARY_KEY_ORDERING
		);

		// Custom Ordering radio button
		addRadioButton(
			container,
			JptUiDetailsMessages.OrderingComposite_custom,
			buildCustomOrderingHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_BY_CUSTOM_ORDERING
		);

		// Custom Ordering text field
		addText(
			addSubPane(container, 0, 16),
			buildSpecifiedOrderByHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_BY,
			buildCustomOrderingHolder(orderableHolder)
		);

		
		// Order Column Ordering radio button
		addRadioButton(
			container,
			JptUiDetailsMessages2_0.OrderingComposite_orderColumn,
			buildOrderColumnOrderingHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_COLUMN_ORDERING
		);

		OrderColumnComposite orderColumnComposite = new OrderColumnComposite(
			this,
			buildOrderColumnHolder(orderableHolder), 
			addSubPane(container, 0, 16));

		installOrderColumnCompositeEnabler(orderableHolder, orderColumnComposite);
	}
	
	protected void installOrderColumnCompositeEnabler(PropertyValueModel<Orderable> orderableHolder, OrderColumnComposite pane) {
		new PaneEnabler(buildPaneEnablerHolder(orderableHolder), pane);
	}
	
	private PropertyValueModel<Boolean> buildPaneEnablerHolder(PropertyValueModel<Orderable> orderableHolder) {
		return buildOrderColumnOrderingHolder(orderableHolder);
	}


	protected ModifiablePropertyValueModel<Boolean> buildOrderColumnOrderingHolder(PropertyValueModel<Orderable> orderableHolder) {
		return new PropertyAspectAdapter<Orderable, Boolean>(orderableHolder, Orderable2_0.ORDER_COLUMN_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(((Orderable2_0) this.subject).isOrderColumnOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				((Orderable2_0) this.subject).setOrderColumnOrdering(value.booleanValue());
			}
		};
	}
	
	protected PropertyValueModel<OrderColumn2_0> buildOrderColumnHolder(PropertyValueModel<Orderable> orderableHolder) {
		return new PropertyAspectAdapter<Orderable, OrderColumn2_0>(orderableHolder) {
			@Override
			protected OrderColumn2_0 buildValue_() {
				return ((Orderable2_0) this.subject).getOrderColumn();
			}
		};
	}
}