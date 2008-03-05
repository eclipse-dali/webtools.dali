/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TargetEntityComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | CascadeComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrderingComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - Join Table ------------------------------------------------------------ |
 * | |                                                                       | |
 * | | JoinTableComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OneToManyMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see CascadeComposite
 * @see FetchTypeComposite
 * @see JoinTableComposite
 * @see OrderingComposite
 * @see TargetEntityComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class OneToManyMappingComposite extends AbstractFormPane<OneToManyMapping>
                                       implements JpaComposite<OneToManyMapping>
{
	/**
	 * Creates a new <code>OneToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OneToManyMappingComposite(PropertyValueModel<? extends OneToManyMapping> subjectHolder,
	                                 Composite parent,
	                                 TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<OneToManyMapping, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(OneToManyMapping value) {
				return value.getCascade();
			}
		};
	}

	private PropertyValueModel<JoinTable> buildJointTableHolder() {
		return new TransformationPropertyValueModel<OneToManyMapping, JoinTable>(getSubjectHolder()) {
			@Override
			protected JoinTable transform_(OneToManyMapping value) {
				return value.getJoinTable();
			}
		};
	}

	private void initializeGeneralPane(Composite container) {

		int groupBoxMargin = groupBoxMargin();
		Composite subPane = buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		// Target Entity widgets
		new TargetEntityComposite(this, subPane);

		// Fetch Type widgets
		new FetchTypeComposite(this, subPane);

		// Mapped By widgets
		new MappedByComposite(this, subPane);

		// Cascade widgets
		new CascadeComposite(this, buildCascadeHolder(), buildSubPane(container, 4));

		// Ordering widgets
		new OrderingComposite(this, container);
	}

	private void initializeJoinTablePane(Composite container) {

		container = buildSection(
			container,
			JptUiMappingsMessages.MultiRelationshipMappingComposite_joinTable
		);

		new JoinTableComposite(
			this,
			buildJointTableHolder(),
			container
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// General sub pane
		initializeGeneralPane(container);

		// Join Table sub pane
		initializeJoinTablePane(container);
	}
}