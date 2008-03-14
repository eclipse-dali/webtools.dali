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

import org.eclipse.jpt.core.context.BaseEmbeddedMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EmbeddedAttributeOverridesComposite                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see BaseEmbeddedMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see EmbeddedAttributeOverridesComposite
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class AbstractEmbeddedMappingComposite<T extends BaseEmbeddedMapping> extends AbstractFormPane<T>
                                      implements JpaComposite<T>
{
	/**
	 * Creates a new <code>AbstractEmbeddedMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>BaseEmbeddedMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractEmbeddedMappingComposite(PropertyValueModel<? extends T> subjectHolder,
	                                Composite parent,
	                                WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {

		new EmbeddedAttributeOverridesComposite(
			this,
			container
		);
	}
}