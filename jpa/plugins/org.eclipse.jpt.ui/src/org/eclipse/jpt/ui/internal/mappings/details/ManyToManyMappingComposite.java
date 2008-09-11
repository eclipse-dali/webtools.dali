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
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

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
 * @see ManyToManyMapping
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
public class ManyToManyMappingComposite extends FormPane<ManyToManyMapping>
                                        implements JpaComposite
{
	/**
	 * Creates a new <code>ManyToManyMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IManyToManyMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public ManyToManyMappingComposite(PropertyValueModel<? extends ManyToManyMapping> subjectHolder,
	                                  Composite parent,
	                                  WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<ManyToManyMapping, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(ManyToManyMapping value) {
				return value.getCascade();
			}
		};
	}

	private PropertyValueModel<JoinTable> buildJoinTableHolder() {
		return new TransformationPropertyValueModel<ManyToManyMapping, JoinTable>(getSubjectHolder()) {
			@Override
			protected JoinTable transform_(ManyToManyMapping value) {
				return value.getJoinTable();
			}
		};
	}

	private Composite addPane(Composite container, int groupBoxMargin) {
		return addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
	}

	private void initializeGeneralPane(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		// Target Entity widgets
		new TargetEntityComposite(this, addPane(container, groupBoxMargin));

		// Fetch Type widgets
		new FetchTypeComposite(this, addPane(container, groupBoxMargin));

		// Mapped By widgets
		new MappedByComposite(this, addPane(container, groupBoxMargin));

		// Cascade widgets
		new CascadeComposite(this, buildCascadeHolder(), addSubPane(container, 5));

		// Ordering widgets
		new OrderingComposite(this, container);
	}

	private void initializeJoinTablePane(Composite container) {

		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.MultiRelationshipMappingComposite_joinTable
		);

		new JoinTableComposite(
			this,
			buildJoinTableHolder(),
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