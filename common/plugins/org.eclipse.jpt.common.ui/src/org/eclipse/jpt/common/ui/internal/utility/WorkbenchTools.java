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

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * {@link IWorkbench} utility methods.
 */
public class WorkbenchTools {

	/**
	 * Close all the views in the platform workbench with the specified ID.
	 * @see #closeAllViews(IWorkbench, String)
	 * @see org.eclipse.ui.IWorkbenchPartSite#getId()
	 */
	public static void closeAllViews(String viewID) {
		closeAllViews(PlatformUI.getWorkbench(), viewID);
	}

	/**
	 * Close all the views in the specified workbench with the specified ID.
	 * @see #closeAllViews(String)
	 */
	public static void closeAllViews(IWorkbench workbench, String viewID) {
		for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
			WorkbenchWindowTools.closeAllViews(window, viewID);
		}
	}
 

	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private WorkbenchTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
