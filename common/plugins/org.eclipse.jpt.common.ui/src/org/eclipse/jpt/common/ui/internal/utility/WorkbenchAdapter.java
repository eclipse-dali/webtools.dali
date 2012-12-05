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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;

/**
 * Convenience implementation of {@link IWorkbenchListener}.
 */
public class WorkbenchAdapter
	implements IWorkbenchListener
{
	public boolean preShutdown(IWorkbench workbench, boolean forced) {
		// allow the workbench to proceed with shutdown
		return true;
	}

	public void postShutdown(IWorkbench workbench) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
