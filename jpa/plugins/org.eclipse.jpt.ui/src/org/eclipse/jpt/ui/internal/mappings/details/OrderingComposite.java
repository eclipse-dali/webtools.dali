/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;

import org.eclipse.jpt.core.context.MultiRelationshipMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Order By -------------------------------------------------------------- |
 * | |                                                                       | |
 * | | o No Ordering                                                         | |
 * | |                                                                       | |
 * | | o Primary Key Ordering                                                | |
 * | |                                                                       | |
 * | | o Custom Ordering                                                     | |
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
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class OrderingComposite extends FormPane<MultiRelationshipMapping>
{
	private Text customOrderingText;

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

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(MultiRelationshipMapping.ORDER_BY_PROPERTY);
	}

	private ModifyListener buildCustomTextModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!isPopulating()) {
					Text text = (Text) e.widget;
					valueChanged(text.getText());
				}
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateCustomOrdering();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Order By group
		Group orderByGroup = addTitledGroup(
			container,
			JptUiMappingsMessages.OrderByComposite_orderByGroup,
			JpaHelpContextIds.MAPPING_ORDER_BY);

		// No Ordering radio button
		addRadioButton(
			addSubPane(orderByGroup, 8),
			JptUiMappingsMessages.OrderByComposite_noOrdering,
			buildNoOrderingHolder(),
			JpaHelpContextIds.MAPPING_ORDER_BY_NO_ORDERING
		);

		// Order by Primary Key radio button
		addRadioButton(
			orderByGroup,
			JptUiMappingsMessages.OrderByComposite_primaryKeyOrdering,
			buildPrimaryKeyOrderingHolder(),
			JpaHelpContextIds.MAPPING_ORDER_BY_PRIMARY_KEY_ORDERING
		);

		// Custom Ordering radio button
		addRadioButton(
			orderByGroup,
			JptUiMappingsMessages.OrderByComposite_customOrdering,
			buildCustomOrderingHolder(),
			JpaHelpContextIds.MAPPING_ORDER_BY_CUSTOM_ORDERING
		);

		// Custom Ordering text field
		customOrderingText = addUnmanagedText(
			addSubPane(orderByGroup, 0, 16),
			JpaHelpContextIds.MAPPING_ORDER_BY
		);

		customOrderingText.addModifyListener(buildCustomTextModifyListener());
		installCustomTextEnabler(customOrderingText);
	}

	private void installCustomTextEnabler(Text text) {
		SWTTools.controlEnabledState(buildCustomOrderingHolder(), text);
	}

	private WritablePropertyValueModel<Boolean> buildCustomOrderingHolder() {
		return new PropertyAspectAdapter<MultiRelationshipMapping, Boolean>(getSubjectHolder(), MultiRelationshipMapping.CUSTOM_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(subject.isCustomOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setCustomOrdering(value.booleanValue());
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildNoOrderingHolder() {
		return new PropertyAspectAdapter<MultiRelationshipMapping, Boolean>(getSubjectHolder(), MultiRelationshipMapping.NO_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(subject.isNoOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setNoOrdering(value.booleanValue());
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildPrimaryKeyOrderingHolder() {
		return new PropertyAspectAdapter<MultiRelationshipMapping, Boolean>(getSubjectHolder(), MultiRelationshipMapping.PK_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(subject.isPkOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setPkOrdering(value.booleanValue());
			}
		};
	}
	private void populateCustomOrdering() {

		if ((getSubject() != null) && getSubject().getOrderBy() != null) {
			customOrderingText.setText(getSubject().getOrderBy());
		}
		else  {
			customOrderingText.setText("");
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == MultiRelationshipMapping.ORDER_BY_PROPERTY) {
			populateCustomOrdering();
		}
	}

	private void valueChanged(String value) {

		setPopulating(true);

		try {
			getSubject().setOrderBy(value);
		}
		finally {
			setPopulating(false);
		}
	}
}