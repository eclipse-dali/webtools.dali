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
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
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
 * | | OptionalComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | CascadeComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | JoinColumnComposite                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see ManyToOneMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see CascadeComposite
 * @see FetchTypeComposite
 * @see JoinColumnComposite
 * @see OptionalComposite
 * @see TargetEntityComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class ManyToOneMappingComposite extends AbstractFormPane<ManyToOneMapping>
                                       implements JpaComposite
{
	/**
	 * Creates a new <code>ManyToOneMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IManyToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public ManyToOneMappingComposite(PropertyValueModel<? extends ManyToOneMapping> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private PropertyValueModel<Cascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<ManyToOneMapping, Cascade>(getSubjectHolder()) {
			@Override
			protected Cascade transform_(ManyToOneMapping value) {
				return value.getCascade();
			}
		};
	}

	private Composite buildPane(Composite container, int groupBoxMargin) {
		return buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();
		Composite subPane = buildPane(container, groupBoxMargin);

		// Target Entity widgets
		new TargetEntityComposite(this, subPane);

		// Fetch Type widgets
		new FetchTypeComposite(this, subPane);

		// Optional check box
		new OptionalComposite(this, buildSubPane(subPane, 4));

		// Cascade widgets
		new CascadeComposite(this, buildCascadeHolder(), container);

		// Join Column widgets
		new JoinColumnComposite(this, container);
	}
}
