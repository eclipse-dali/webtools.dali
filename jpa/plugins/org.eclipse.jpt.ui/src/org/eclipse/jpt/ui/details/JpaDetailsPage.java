/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.details;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.swt.widgets.Control;

/**
 * A details page is used to show the propery pane for a given context node. The
 * {@link JpaDetailsProvider} is responsible for creating the pane.
 *
 * @see JpaDetailsProvider
 *
 * @version 2.0
 * @since 2.0
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaDetailsPage<T extends JpaStructureNode>
{
	/**
	 * Returns this details' page's widget.
	 *
	 * @return The container of the widgets shown by this details page
	 */
	Control getControl();

	/**
	 * Sets the subject for this details page.
	 *
	 * @param subject Either the new subject or <code>null</code> if the subject
	 * needs to be removed
	 */
	void setSubject(T subject);
}