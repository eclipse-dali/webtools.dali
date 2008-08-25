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

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
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
 * | | ColumnComposite                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TemporalTypeComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see VersionMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see ColumnComposite
 * @see TemporalTypeComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class VersionMappingComposite extends FormPane<VersionMapping>
                                     implements JpaComposite
{
	/**
	 * Creates a new <code>VersionMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IVersionMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public VersionMappingComposite(PropertyValueModel<? extends VersionMapping> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private PropertyValueModel<Column> buildColumnHolder() {
		return new TransformationPropertyValueModel<VersionMapping, Column>(getSubjectHolder()) {
			@Override
			protected Column transform_(VersionMapping value) {
				return value.getColumn();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		// Column widgets
		new ColumnComposite(this, buildColumnHolder(), container);

		// Temporal Type widgets
		new TemporalTypeComposite(
			this,
			addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin)
		);
	}
}