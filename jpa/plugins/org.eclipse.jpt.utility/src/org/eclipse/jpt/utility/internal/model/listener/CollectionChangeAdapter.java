/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.listener;

import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;

/**
 * Convenience implementation of CollectionChangeListener.
 */
public class CollectionChangeAdapter implements CollectionChangeListener {

	/**
	 * Default constructor.
	 */
	public CollectionChangeAdapter() {
		super();
	}

	public void itemsAdded(CollectionChangeEvent event) {
		// do nothing
	}

	public void itemsRemoved(CollectionChangeEvent event) {
		// do nothing
	}

	public void collectionChanged(CollectionChangeEvent event) {
		// do nothing
	}

}
