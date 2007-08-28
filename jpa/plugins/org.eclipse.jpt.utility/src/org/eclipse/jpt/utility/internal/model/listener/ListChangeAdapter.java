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

import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;

/**
 * Convenience implementation of ListChangeListener.
 */
public class ListChangeAdapter implements ListChangeListener {

	/**
	 * Default constructor.
	 */
	public ListChangeAdapter() {
		super();
	}

	public void itemsAdded(ListChangeEvent event) {
		// do nothing
	}

	public void itemsRemoved(ListChangeEvent event) {
		// do nothing
	}

	public void itemsReplaced(ListChangeEvent event) {
		// do nothing
	}

	public void listChanged(ListChangeEvent event) {
		// do nothing
	}

}
