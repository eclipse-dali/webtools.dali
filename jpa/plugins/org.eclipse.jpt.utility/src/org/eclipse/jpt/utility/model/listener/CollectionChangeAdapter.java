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

import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;

/**
 * Convenience implementation of {@link CollectionChangeListener}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class CollectionChangeAdapter implements CollectionChangeListener {

	/**
	 * Default constructor.
	 */
	public CollectionChangeAdapter() {
		super();
	}

	public void itemsAdded(CollectionAddEvent event) {
		// do nothing
	}

	public void itemsRemoved(CollectionRemoveEvent event) {
		// do nothing
	}

	public void collectionCleared(CollectionClearEvent event) {
		// do nothing
	}

	public void collectionChanged(CollectionChangeEvent event) {
		// do nothing
	}

}
