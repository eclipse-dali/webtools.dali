/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible to create the {@link JpaDetailsPage}
 * responsible to show the information for a given content node id.
 *
 * @see JpaDetailsPage
 *
 * @version 3.0
 * @since 2.0
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaDetailsProvider
{
	/**
	 * Return whether this provider returns a details page for the given structure node
	 */
	boolean providesDetails(JpaStructureNode structureNode);
	
	/**
	 * Creates a new details page based on the given content node id.
	 *
	 * @param parent The parent container
	 * @param contentNodeId The unique identifier used to determine which details
	 * page to create
	 * @param widgetFactory The factory used to create various widgets
	 */
	//TODO Should we pass in JpaUiFactory so these pages can be built using the factory and overriden?
	JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(
			Composite parent,
			WidgetFactory widgetFactory);
}
