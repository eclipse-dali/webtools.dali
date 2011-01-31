/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jface.dialogs.Dialog;

/**
 * A <code>null</code> instance of <code>PostExecution</code>.
 *
 * @version 2.0
 * @since 1.0
 */
public final class NullPostExecution implements PostExecution<Dialog> {

	/**
	 * The singleton instance of this <code>NullPostExecution</code>.
	 */
	private static PostExecution<Dialog> INSTANCE;

	/**
	 * Creates a new <code>NullPostExecution</code>.
	 */
	private NullPostExecution() {
		super();
	}

	/**
	 * Returns the singleton instance of this <code>NullPostExecution</code>.
	 *
	 * @param <T> The dialog where this <code>PostExecution</code> will be used
	 * @return The singleton instance with the proper type
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <T extends Dialog> PostExecution<T> instance() {

		if (INSTANCE == null) {
			INSTANCE = new NullPostExecution();
		}

		return (PostExecution<T>) INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 */
	public void execute(Dialog dialog) {
		// Nothing to do
	}
}
