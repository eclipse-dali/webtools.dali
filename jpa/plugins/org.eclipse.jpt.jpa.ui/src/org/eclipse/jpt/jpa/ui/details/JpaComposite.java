/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details;

import org.eclipse.swt.widgets.Control;

/**
 * A {@link JpaComposite} defines the common behavior of the JPA related
 * widgets.
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaComposite {

	/**
	 * Returns the actual <code>Composite</code>.
	 *
	 * @return This composite's actual widget
	 */
	Control getControl();

	/**
	 * Changes the enablement state of the widgets of this pane.
	 *
	 * @param enabled <code>true</code> to enable the widgets or <code>false</code>
	 * to disable them
	 */
	void enableWidgets(boolean enabled);

	/**
	 * Notifies this composite it should dispose any resources.
	 */
	void dispose();
}