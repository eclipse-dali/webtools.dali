/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jpt.core.internal.context.base.IMultiRelationshipMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
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
 * | |   |                                                                 | | |
 * | |   ------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IMultiRelationshipMapping
 * @see ManyToManyMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class OrderingComposite extends AbstractFormPane<IMultiRelationshipMapping>
{
	private Text customOrderingText;

	/**
	 * Creates a new <code>OrderingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	protected OrderingComposite(AbstractFormPane<? extends IMultiRelationshipMapping> parentPane,
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
	public OrderingComposite(PropertyValueModel<? extends IMultiRelationshipMapping> subjectHolder,
	                         Composite parent,
	                         IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(IMultiRelationshipMapping.ORDER_BY_PROPERTY);
	}

	private WritablePropertyValueModel<Boolean> buildCustomOrderingHolder() {
		return new PropertyAspectAdapter<IMultiRelationshipMapping, Boolean>(getSubjectHolder(), IMultiRelationshipMapping.ORDER_BY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isCustomOrdering();
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value) {
					subject.setOrderBy("");
				}
			}
		};
	}

	private ModifyListener buildCustomTextModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}

				Text text = (Text) e.widget;
				subject().setOrderBy(text.getText());
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildNoOrderingHolder() {
		return new PropertyAspectAdapter<IMultiRelationshipMapping, Boolean>(getSubjectHolder(), IMultiRelationshipMapping.ORDER_BY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isNoOrdering();
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value) {
					subject.setNoOrdering();
				}
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildPrimaryKeyOrderingHolder() {
		return new PropertyAspectAdapter<IMultiRelationshipMapping, Boolean>(getSubjectHolder(), IMultiRelationshipMapping.ORDER_BY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.isOrderByPk();
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value) {
					subject.setOrderByPk();
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
		Group orderByGroup = buildTitledPane(
			container,
			JptUiMappingsMessages.OrderByComposite_orderByGroup,
			IJpaHelpContextIds.MAPPING_ORDER_BY);

		// No Ordering radio button
		buildRadioButton(
			orderByGroup,
			JptUiMappingsMessages.OrderByComposite_noOrdering,
			buildNoOrderingHolder(),
			IJpaHelpContextIds.MAPPING_ORDER_BY_NO_ORDERING
		);

		// Order by Primary Key radio button
		buildRadioButton(
			orderByGroup,
			JptUiMappingsMessages.OrderByComposite_primaryKeyOrdering,
			buildPrimaryKeyOrderingHolder(),
			IJpaHelpContextIds.MAPPING_ORDER_BY_PRIMARY_KEY_ORDERING
		);

		// Custom Ordering radio button
		buildRadioButton(
			orderByGroup,
			JptUiMappingsMessages.OrderByComposite_customOrdering,
			buildCustomOrderingHolder(),
			IJpaHelpContextIds.MAPPING_ORDER_BY_CUSTOM_ORDERING
		);

		// Custom Ordering text field
		customOrderingText = buildText(
			buildSubPane(orderByGroup, 0, 16),
			IJpaHelpContextIds.MAPPING_ORDER_BY
		);

		customOrderingText.addModifyListener(buildCustomTextModifyListener());
		installCustomTextEnabler(customOrderingText);
	}

	private void installCustomTextEnabler(Text text) {
		new ControlEnabler(buildCustomOrderingHolder(), text);
	}

	private void populateCustomOrdering() {

		if (customOrderingText.isDisposed()) {
			return;
		}

		if ((subject() != null) && subject().isCustomOrdering()) {
			customOrderingText.setText(subject().getOrderBy());
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

		if (propertyName == IMultiRelationshipMapping.ORDER_BY_PROPERTY) {
			populateCustomOrdering();
		}
	}
}