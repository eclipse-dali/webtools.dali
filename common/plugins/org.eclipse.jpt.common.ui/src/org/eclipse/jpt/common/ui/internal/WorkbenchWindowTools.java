/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * {@link IWorkbenchWindow} utility methods.
 */
public final class WorkbenchWindowTools {

	/**
	 * Close all the views in the specified workbench window with the
	 * specified ID.
	 */
	public static void closeAllViews(IWorkbenchWindow window, String viewID) {
		for (IWorkbenchPage page : window.getPages()) {
			WorkbenchPageTools.closeAllViews(page, viewID);
		}
	}
 

	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private WorkbenchWindowTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
