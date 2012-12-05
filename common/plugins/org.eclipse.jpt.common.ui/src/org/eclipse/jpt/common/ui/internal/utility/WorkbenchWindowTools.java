/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.utility;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * {@link IWorkbenchWindow} utility methods.
 */
public class WorkbenchWindowTools {

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
