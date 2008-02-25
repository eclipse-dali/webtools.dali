/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.details;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This provider is responsible to create the {@link JpaDetailsPage}
 * responsible to show the information for a given content node id.
 *
 * @see JpaDetailsPage
 *
 * @version 2.0
 * @since 2.0
 */
public interface JpaDetailsProvider
{
	/**
	 * Creates a new details page based on the given content node id.
	 *
	 * @param parent The parent container
	 * @param contentNodeId The unique identifier used to determine which details
	 * page to create
	 * @param widgetFactory The factory used to create various widgets
	 */
	JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
		Composite parent,
		Object contentNodeId,
		TabbedPropertySheetWidgetFactory widgetFactory);
}