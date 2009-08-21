/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.listener;

import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;

/**
 * Convenience implementation of {@link ListChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class ListChangeAdapter implements ListChangeListener {

	/**
	 * Default constructor.
	 */
	public ListChangeAdapter() {
		super();
	}

	public void itemsAdded(ListAddEvent event) {
		// do nothing
	}

	public void itemsRemoved(ListRemoveEvent event) {
		// do nothing
	}

	public void itemsReplaced(ListReplaceEvent event) {
		// do nothing
	}

	public void itemsMoved(ListMoveEvent event) {
		// do nothing
	}

	public void listCleared(ListClearEvent event) {
		// do nothing
	}

	public void listChanged(ListChangeEvent event) {
		// do nothing
	}

}
