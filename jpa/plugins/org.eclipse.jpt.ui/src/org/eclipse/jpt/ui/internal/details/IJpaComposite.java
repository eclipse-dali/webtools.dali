/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.swt.widgets.Control;

/**
 * A <code>IJpaComposite</code> defines the common behavior of the JPA related
 * widgets.
 */
public interface IJpaComposite<T> {

	/**
	 * Notifies this composite it should populates its widgets using the given
	 * model object.
	 *
	 * @param model The model used to retrieve the information to be displayed
	 */
	void populate(T model);

	/**
	 * Returns the actual <code>Control</code>.
	 *
	 * @return This composite's actual widget
	 */
	Control getControl();

	/**
	 * Notifies this composite it should dispose any resources.
	 */
	void dispose();
}