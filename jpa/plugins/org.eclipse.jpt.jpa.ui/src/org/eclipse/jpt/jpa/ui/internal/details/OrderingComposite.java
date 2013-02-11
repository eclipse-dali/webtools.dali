/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
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
 * @see ManyToManyMappingComposite
 * @see OneToManyMappingComposite
 */
public class OrderingComposite
	extends AbstractOrderingComposite<Orderable>
{
	public OrderingComposite(Pane<? extends CollectionMapping> parentPane, Composite parentComposite) {
		super(parentPane, parentComposite);
	}

	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<Orderable> orderableHolder = buildOrderableModel();

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
	}
}