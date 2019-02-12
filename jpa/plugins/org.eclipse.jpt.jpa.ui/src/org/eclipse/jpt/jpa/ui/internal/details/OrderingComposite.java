/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
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
 * | |   | Default (primary key)                                           | | |
 * | |   ------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 */
public class OrderingComposite
		extends AbstractOrderingComposite<Orderable> {
	
	public OrderingComposite(Pane<? extends CollectionMapping> parentPane, Composite parentComposite) {
		super(parentPane, parentComposite);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<Orderable> orderableHolder = buildOrderableModel();
		
		// No Ordering radio button
		addRadioButton(
				container,
				JptJpaUiDetailsMessages.ORDERING_COMPOSITE_NONE,
				buildNoOrderingHolder(orderableHolder),
				JpaHelpContextIds.MAPPING_ORDER_BY_NO_ORDERING);
		
		ModifiablePropertyValueModel<Boolean> orderByOrderingHolder = buildOrderByOrderingHolder(orderableHolder);
		
		// Order by radio button
		addRadioButton(
				container,
				JptJpaUiDetailsMessages.ORDERING_COMPOSITE_ORDER_BY,
				orderByOrderingHolder,
				JpaHelpContextIds.MAPPING_ORDER_BY_ORDERING);
		
		OrderByComposite orderByComposite = new OrderByComposite(
			this,
			buildOrderByHolder(orderableHolder),
			orderByOrderingHolder, 
			container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent = 16;
		orderByComposite.getControl().setLayoutData(gridData);
	}
}
