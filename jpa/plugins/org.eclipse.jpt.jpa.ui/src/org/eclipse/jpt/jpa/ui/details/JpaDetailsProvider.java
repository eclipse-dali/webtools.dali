/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.swt.widgets.Composite;

/**
 * This provider is responsible for creating the {@link JpaDetailsPageManager}
 * responsible for showing the information for a JPA structure node.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 * 
 * @see JpaDetailsPageManager
 */
public interface JpaDetailsProvider {

	/**
	 * Return the type of structure node for which the provider will build a
	 * details page manager.
	 * 
	 * @see JpaStructureNode#getStructureType()
	 */
	Class<? extends JpaStructureNode> getStructureType();

	/**
	 * Build a new details page manager with the specified parent, widget
	 * factory, and resource manager.
	 */
	// TODO pass in JpaUiFactory so these pages can be built using the factory and overridden?
	JpaDetailsPageManager buildDetailsPageManager(
			Composite parent,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager);
}
