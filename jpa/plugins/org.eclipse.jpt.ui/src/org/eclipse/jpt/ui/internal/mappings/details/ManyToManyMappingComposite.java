/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.ICascade;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
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
 * @see IManyToManyMapping
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
public class ManyToManyMappingComposite extends AbstractFormPane<IManyToManyMapping>
                                        implements IJpaComposite<IManyToManyMapping>
{
	/**
	 * Creates a new <code>ManyToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IManyToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public ManyToManyMappingComposite(PropertyValueModel<? extends IManyToManyMapping> subjectHolder,
	                                  Composite parent,
	                                  TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private PropertyValueModel<ICascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<IManyToManyMapping, ICascade>(getSubjectHolder()) {
			@Override
			protected ICascade transform_(IManyToManyMapping value) {
				return value.getCascade();
			}
		};
	}

	private PropertyValueModel<IJoinTable> buildJointTableHolder() {
		return new TransformationPropertyValueModel<IManyToManyMapping, IJoinTable>(getSubjectHolder()) {
			@Override
			protected IJoinTable transform_(IManyToManyMapping value) {
				return value.getJoinTable();
			}
		};
	}

	private Composite buildPane(Composite container, int groupBoxMargin) {
		return buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
	}

	private void initializeGeneralPane(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		// Target Entity widgets
		new TargetEntityComposite(this, buildPane(container, groupBoxMargin));

		// Fetch Type widgets
		new FetchTypeComposite(this, buildPane(container, groupBoxMargin));

		// Mapped By widgets
		new MappedByComposite(this, buildPane(container, groupBoxMargin));

		// Cascade widgets
		new CascadeComposite(this, buildCascadeHolder(), buildSubPane(container, 5));

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