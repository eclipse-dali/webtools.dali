/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.internal.IJpaStructureNode;
import org.eclipse.swt.widgets.Control;

/**
 * A details page is used to show the propery pane for a given context node. The
 * <code>IJpaDetailsProvider</code> is responsible for creating the pane.
 *
 * @see IJpaDetailsProvider
 *
 * @version 2.0
 * @since 2.0
 */
public interface IJpaDetailsPage<T extends IJpaStructureNode>
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