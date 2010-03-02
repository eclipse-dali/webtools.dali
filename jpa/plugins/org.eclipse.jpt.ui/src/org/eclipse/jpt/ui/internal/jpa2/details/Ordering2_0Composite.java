/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.Orderable;
import org.eclipse.jpt.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.AbstractOrderingComposite;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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

	/**
	 * Creates a new <code>OrderingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IMultiRelationshipMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public Ordering2_0Composite(PropertyValueModel<? extends CollectionMapping> subjectHolder,
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
		Text customOrderingText = addUnmanagedText(
			addSubPane(container, 0, 16),
			buildSpecifiedOrderByHolder(orderableHolder),
			JpaHelpContextIds.MAPPING_ORDER_BY
		);

		installCustomTextEnabler(customOrderingText, orderableHolder);
		
		
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


	protected WritablePropertyValueModel<Boolean> buildOrderColumnOrderingHolder(PropertyValueModel<Orderable> orderableHolder) {
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