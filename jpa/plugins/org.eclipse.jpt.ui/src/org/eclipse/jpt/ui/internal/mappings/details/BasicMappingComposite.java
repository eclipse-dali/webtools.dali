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

import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
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
 * | | ColumnComposite                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TemporalTypeComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EnumTypeComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OptionalComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * |  x Lob                                                                    |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IBasicMapping
 * @see BaseJpaUiFactory
 * @see ColumnComposite
 * @see EnumTypeComposite
 * @see FetchTypeComposite
 * @see OptionalComposite
 * @see TemporalTypeComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class BasicMappingComposite extends BaseJpaComposite<IBasicMapping>
{
	/**
	 * Creates a new <code>BasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IBasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public BasicMappingComposite(PropertyValueModel<IBasicMapping> subjectHolder,
	                             Composite parent,
	                             TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private PropertyValueModel<IColumn> buildColumnHolder() {
		return new TransformationPropertyValueModel<IBasicMapping, IColumn>(getSubjectHolder()) {
			@Override
			protected IColumn transform_(IBasicMapping value) {
				return value.getColumn();
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

		// Column widgets
		new ColumnComposite(this, buildColumnHolder(), container);

		// Fetch Type widgets
		new FetchTypeComposite(this, buildPane(container, groupBoxMargin));

		// Temporal Type widgets
		new TemporalTypeComposite(this, buildPane(container, groupBoxMargin));

		// Enumerated widgets
		new EnumTypeComposite(this, buildPane(container, groupBoxMargin));

		// Optional widgets
		new OptionalComposite(this, buildPane(container, groupBoxMargin));

		// Lob check box
		new LobCheckBox(this, buildPane(container, groupBoxMargin));
	}
}