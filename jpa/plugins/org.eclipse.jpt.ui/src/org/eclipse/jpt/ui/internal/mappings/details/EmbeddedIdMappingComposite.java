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

import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This pane does not have any widgets.
 *
 * @see EmbeddedIdMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 *
 * @version 2.0
 * @since 1.0
 */
public class EmbeddedIdMappingComposite extends AbstractFormPane<EmbeddedIdMapping>
                                        implements JpaComposite<EmbeddedIdMapping>
{
	/**
	 * Creates a new <code>EmbeddedIdMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEmbeddedIdMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EmbeddedIdMappingComposite(PropertyValueModel<? extends EmbeddedIdMapping> subjectHolder,
	                                  Composite parent,
	                                  TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite composite) {
	}
}