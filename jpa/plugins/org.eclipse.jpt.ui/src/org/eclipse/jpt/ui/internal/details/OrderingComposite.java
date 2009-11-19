/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.MultiRelationshipMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
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
 * @see MultiRelationshipMapping
 * @see ManyToManyMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 *
 * @version 3.0
 * @since 1.0
 */
public class OrderingComposite extends FormPane<MultiRelationshipMapping>
{
	/**
	 * Creates a new <code>OrderingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public OrderingComposite(FormPane<? extends MultiRelationshipMapping> parentPane,
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
	public OrderingComposite(PropertyValueModel<? extends MultiRelationshipMapping> subjectHolder,
	                         Composite parent,
	                         WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		// Order By group
		Group orderByGroup = addTitledGroup(
			container,
			JptUiDetailsMessages.OrderingComposite_orderingGroup,
			JpaHelpContextIds.MAPPING_ORDER_BY);

		// No Ordering radio button
		addRadioButton(
			addSubPane(orderByGroup, 8),
			JptUiDetailsMessages.OrderingComposite_none,
			buildNoOrderingHolder(),
			JpaHelpContextIds.MAPPING_ORDER_BY_NO_ORDERING
		);

		// Order by Primary Key radio button
		addRadioButton(
			orderByGroup,
			JptUiDetailsMessages.OrderingComposite_primaryKey,
			buildPrimaryKeyOrderingHolder(),
			JpaHelpContextIds.MAPPING_ORDER_BY_PRIMARY_KEY_ORDERING
		);

		// Custom Ordering radio button
		addRadioButton(
			orderByGroup,
			JptUiDetailsMessages.OrderingComposite_custom,
			buildCustomOrderingHolder(),
			JpaHelpContextIds.MAPPING_ORDER_BY_CUSTOM_ORDERING
		);

		// Custom Ordering text field
		Text customOrderingText = addUnmanagedText(
			addSubPane(orderByGroup, 0, 16),
			buildSpecifiedOrderByHolder(),
			JpaHelpContextIds.MAPPING_ORDER_BY
		);

		installCustomTextEnabler(customOrderingText);
	}

	private WritablePropertyValueModel<Boolean> buildNoOrderingHolder() {
		return new PropertyAspectAdapter<MultiRelationshipMapping, Boolean>(getSubjectHolder(), MultiRelationshipMapping.NO_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isNoOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setNoOrdering(value.booleanValue());
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildPrimaryKeyOrderingHolder() {
		return new PropertyAspectAdapter<MultiRelationshipMapping, Boolean>(getSubjectHolder(), MultiRelationshipMapping.PK_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isPkOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setPkOrdering(value.booleanValue());
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildCustomOrderingHolder() {
		return new PropertyAspectAdapter<MultiRelationshipMapping, Boolean>(getSubjectHolder(), MultiRelationshipMapping.CUSTOM_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isCustomOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setCustomOrdering(value.booleanValue());
			}
		};
	}

	protected WritablePropertyValueModel<String> buildSpecifiedOrderByHolder() {
		return new PropertyAspectAdapter<MultiRelationshipMapping, String>(getSubjectHolder(), MultiRelationshipMapping.SPECIFIED_ORDER_BY_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getSpecifiedOrderBy();
			}

			@Override
			protected void setValue_(String value) {
				this.subject.setSpecifiedOrderBy(value);
			}
		};
	}

	private void installCustomTextEnabler(Text text) {
		SWTTools.controlEnabledState(buildCustomOrderingHolder(), text);
	}

}