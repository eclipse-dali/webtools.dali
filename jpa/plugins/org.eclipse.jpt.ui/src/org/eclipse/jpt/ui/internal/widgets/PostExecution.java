/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jface.dialogs.Dialog;

/**
 * This <code>PostExecution</code> is used to post execute a portion of code
 * once a dialog, that was launched into a different UI thread, has been
 * disposed.
 *
 * @version 2.0
 * @since 1.0
 */
public interface PostExecution<T extends Dialog> {

	/**
	 * Notifies this post exection the dialog that was launched into a different
	 * UI thread has been disposed.
	 *
	 * @param dialog The dialog that was launched into a different thread
	 */
	public void execute(T dialog);
}
