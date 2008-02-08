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

import org.eclipse.jpt.core.internal.context.base.ICascade;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
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
 * | | MappedByComposite                                                     | |
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
 * @see IOneToOneMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see CascadeComposite
 * @see FetchTypeComposite
 * @see JoinColumnComposite
 * @see MappedByComposite
 * @see OptionalComposite
 * @see TargetEntityComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class OneToOneMappingComposite extends AbstractFormPane<IOneToOneMapping>
                                      implements IJpaComposite<IOneToOneMapping>
{
	/**
	 * Creates a new <code>OneToOneMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IOneToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OneToOneMappingComposite(PropertyValueModel<? extends IOneToOneMapping> subjectHolder,
	                                Composite parent,
	                                TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private PropertyValueModel<ICascade> buildCascadeHolder() {
		return new TransformationPropertyValueModel<IOneToOneMapping, ICascade>(getSubjectHolder()) {
			@Override
			protected ICascade transform_(IOneToOneMapping value) {
				return value.getCascade();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();
		Composite subPane = buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		// Target Entity widgets
		new TargetEntityComposite(this, subPane);

		// Fetch Type widgets
		new FetchTypeComposite(this, subPane);

		// Mapped By widgets
		new MappedByComposite(this, subPane);

		// Optional check box
		new OptionalComposite(this, subPane);

		// Cascade widgets
		new CascadeComposite(this, buildCascadeHolder(), container);

		// Join Column widgets
		new JoinColumnComposite(this, container);
	}
}