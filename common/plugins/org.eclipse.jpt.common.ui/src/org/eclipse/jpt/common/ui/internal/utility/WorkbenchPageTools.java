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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;

/**
 * {@link IWorkbenchPage} utility methods.
 */
public class WorkbenchPageTools {

	/**
	 * Close all the views in the specified workbench page with the
	 * specified ID.
	 */
	public static void closeAllViews(IWorkbenchPage page, String viewID) {
		for (IViewReference ref : page.getViewReferences()) {
			if (ObjectTools.equals(ref.getId(), viewID)) {
				page.hideView(ref);
			}
		}
	}
 

	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private WorkbenchPageTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
