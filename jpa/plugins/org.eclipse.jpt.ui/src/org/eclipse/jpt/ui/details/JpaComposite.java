/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.details;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;

/**
 * A {@link Link JpaComposite} defines the common behavior of the JPA related
 * widgets.
 */
public interface JpaComposite<T> {

	/**
	 * Notifies this composite it should populates its widgets using the given
	 * model object.
	 *
	 * @param model The model used to retrieve the information to be displayed
	 */
	void populate();

	/**
	 * Returns the actual <code>Composite</code>.
	 *
	 * @return This composite's actual widget
	 */
	Control getControl();

	/**
	 * Notifies this composite it should dispose any resources.
	 */
	void dispose();
}