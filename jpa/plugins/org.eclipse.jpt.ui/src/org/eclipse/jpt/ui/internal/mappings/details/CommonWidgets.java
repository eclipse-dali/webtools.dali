/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CommonWidgets
{
	public static Label buildCatalogLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.CatalogChooser_label);
	}

	public static Label buildSchemaLabel(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		return widgetFactory.createLabel(parent, JptUiMappingsMessages.SchemaChooser_label);
	}
}