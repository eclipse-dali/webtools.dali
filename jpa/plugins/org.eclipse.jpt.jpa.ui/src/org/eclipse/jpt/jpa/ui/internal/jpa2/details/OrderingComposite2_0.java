/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOrderingComposite;
import org.eclipse.jpt.jpa.ui.internal.details.OrderByComposite;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Ordering -------------------------------------------------------------- |
 * | |                                                                       | |
 * | | o None                                                                | |
 * | |                                                                       | |
 * | | o Order by:                                                           | |
 * | |   ------------------------------------------------------------------- | |
 * | |   | Default (primary key)I                                          | | |
 * | |   ------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 */
public class OrderingComposite2_0
		extends AbstractOrderingComposite<Orderable2_0> {
	
	public OrderingComposite2_0(Pane<? extends CollectionMapping2_0> parentPane, Composite parentComposite) {
		super(parentPane, parentComposite);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<Orderable2_0> orderableModel = buildOrderableModel();

		// No Ordering radio button
		addRadioButton(
				container,
				JptJpaUiDetailsMessages.ORDERING_COMPOSITE_NONE,
				buildNoOrderingModel(orderableModel),
				JpaHelpContextIds.MAPPING_ORDER_BY_NO_ORDERING);
		
		ModifiablePropertyValueModel<Boolean> orderByOrderingModel = buildOrderByOrderingModel(orderableModel);
		
		// Order by radio button
		addRadioButton(
				container,
				JptJpaUiDetailsMessages.ORDERING_COMPOSITE_ORDER_BY,
				orderByOrderingModel,
				JpaHelpContextIds.MAPPING_ORDER_BY_ORDERING);
		
		OrderByComposite orderByComposite = new OrderByComposite(
			this,
			buildOrderByModel(orderableModel),
			orderByOrderingModel, 
			container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = 16;
		orderByComposite.getControl().setLayoutData(gridData);
		
		// Order Column Ordering radio button
		addRadioButton(
			container,
			JptJpaUiDetailsMessages2_0.ORDERING_COMPOSITE_ORDER_COLUMN,
			buildOrderColumnOrderingModel(orderableModel),
			JpaHelpContextIds.MAPPING_ORDER_COLUMN_ORDERING
		);

		OrderColumnComposite2_0 orderColumnComposite = new OrderColumnComposite2_0(
			this,
			buildOrderColumnModel(orderableModel),
			buildPaneEnablerModel(orderableModel), 
			container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = 16;
		orderColumnComposite.getControl().setLayoutData(gridData);

	}
	
	private PropertyValueModel<Boolean> buildPaneEnablerModel(PropertyValueModel<Orderable2_0> orderableModel) {
		return buildOrderColumnOrderingModel(orderableModel);
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildOrderColumnOrderingModel(PropertyValueModel<Orderable2_0> orderableModel) {
		return new PropertyAspectAdapterXXXX<Orderable2_0, Boolean>(orderableModel, Orderable2_0.ORDER_COLUMN_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isOrderColumnOrdering());
			}

			@Override
			protected void setValue_(Boolean b) {
				if (b.booleanValue()) {
					this.subject.setOrderColumnOrdering();
				}
			}
		};
	}
	
	protected PropertyValueModel<SpecifiedOrderColumn2_0> buildOrderColumnModel(PropertyValueModel<Orderable2_0> orderableModel) {
		return PropertyValueModelTools.transform(orderableModel, m -> m.getOrderColumn());
	}
}
