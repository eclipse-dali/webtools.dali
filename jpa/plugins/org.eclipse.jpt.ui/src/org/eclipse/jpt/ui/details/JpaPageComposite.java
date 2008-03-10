/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.details;

import org.eclipse.swt.graphics.Image;

/**
 * A {@link JpaPageComposite} defines the common behavior of the JPA
 * related widgets that is shown as a page within an editor.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaPageComposite<T> extends JpaComposite<T> {

	/**
	 * Returns the help ID. This ID will be used if the help button is invoked.
	 *
	 * @return Either the help ID of this page or <code>null</code> if no help
	 * is required
	 */
	String helpID();

	/**
	 * The image of the tab showing this page.
	 *
	 * @return The page's image
	 */
	Image pageImage();

	/**
	 * The text of the tab showing this page.
	 *
	 * @return The page's text
	 */
	String pageText();
}
