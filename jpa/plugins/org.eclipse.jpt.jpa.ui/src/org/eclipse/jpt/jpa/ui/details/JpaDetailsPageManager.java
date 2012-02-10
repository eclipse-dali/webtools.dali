/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
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
import org.eclipse.swt.widgets.Control;

/**
 * A details page manager is used to display the property page for a structure
 * node. The {@link JpaDetailsProvider} is responsible for creating the manager.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <T> the type of the details page manager's model
 * 
 * @version 2.0
 * @since 2.0
 * @see JpaDetailsProvider
 */
public interface JpaDetailsPageManager<T extends JpaStructureNode> {
	/**
	 * Return the details page manager's page.
	 */
	Control getPage();

	/**
	 * Return the details page manager's subject.
	 */
	T getSubject();

	/**
	 * Set the details page manager's subject.
	 * The subject must be of type <code>T</code>.
	 */
	void setSubject(Object subject);

	/**
	 * Dispose the details page manager and any resources it holds.
	 */
	void dispose();

	interface Factory {
		<T extends JpaStructureNode> JpaDetailsPageManager<T> buildPageManager(Composite parent, WidgetFactory widgetFactory);
	}
}
