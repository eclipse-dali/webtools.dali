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

import org.eclipse.jpt.utility.internal.model.event.TreeChangeEvent;

/**
 * Convenience implementation of TreeChangeListener.
 */
public class TreeChangeAdapter implements TreeChangeListener {

	/**
	 * Default constructor.
	 */
	public TreeChangeAdapter() {
		super();
	}

	public void nodeAdded(TreeChangeEvent event) {
		// do nothing
	}

	public void nodeRemoved(TreeChangeEvent event) {
		// do nothing
	}

	public void treeChanged(TreeChangeEvent event) {
		// do nothing
	}

}
