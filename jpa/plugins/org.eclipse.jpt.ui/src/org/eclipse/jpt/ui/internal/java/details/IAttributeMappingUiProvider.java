/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.ui.internal.IJpaUiFactory;
import org.eclipse.jpt.ui.internal.details.IJpaComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public interface IAttributeMappingUiProvider
{
	/**
	 * A unique string that corresponds to the key of a MappingProvider in the core
	 * (IJavaAttributeMappingProvider and/or IXmlAttributeMappingProvider)
	 */
	String attributeMappingKey();

	/**
	 * A label to be displayed to the label as an option in the mapping type combo box
	 * @return
	 */
	String label();

	/**
	 * The IJpaComposite that correponds to this mapping type.  This will be displayed
	 * by the PersistentAttributeDetailsPage when the mapping key matches the key given
	 * by this provider.  The composites will be stored in a Map with the mapping key as the key.
	 * @param factory
	 * @param parent
	 * @param widgetFactory
	 *
	 * @return
	 */
	IJpaComposite<IAttributeMapping> buildAttributeMappingComposite(
			IJpaUiFactory factory, Composite parent, TabbedPropertySheetWidgetFactory widgetFactory);

}
