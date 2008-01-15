/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * ----------------------------------------------------------------------------â??
 * | ------------------------------------------------------------------------â?? |
 * | |                                                                       | |
 * | | EmbeddedAttributeOverridesComposite                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IEmbeddedMapping
 * @see BaseJpaUiFactory
 * @see EmbeddedAttributeOverridesComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class EmbeddedMappingComposite extends BaseJpaComposite<IEmbeddedMapping>
{
	/**
	 * Creates a new <code>EmbeddedComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>ITransientMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EmbeddedMappingComposite(PropertyValueModel<? extends IEmbeddedMapping> subjectHolder,
	                         Composite parent,
	                         TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		EmbeddedAttributeOverridesComposite attributeOverridesComposite = new EmbeddedAttributeOverridesComposite(
			this,
			container
		);

		this.addPaneForAlignment(attributeOverridesComposite);
		this.registerSubPane(attributeOverridesComposite);
	}
}