/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.Orderable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
 * @see ManyToManyMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 *
 * @version 3.0
 * @since 1.0
 */
public class OrderingComposite extends AbstractOrderingComposite
{
	/**
	 * Creates a new <code>OrderingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public OrderingComposite(Pane<? extends CollectionMapping> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>OrderingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IMultiRelationshipMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrderingComposite(PropertyValueModel<? extends CollectionMapping> subjectHolder,
	                         Composite parent,
	                         WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
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
	}


}