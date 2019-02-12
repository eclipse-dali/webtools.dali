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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Convenience implementation of {@link IWindowListener}.
 */
public class WindowAdapter
	implements IWindowListener
{
	public void windowOpened(IWorkbenchWindow window) {
		// NOP
	}

	public void windowActivated(IWorkbenchWindow window) {
		// NOP
	}

	public void windowDeactivated(IWorkbenchWindow window) {
		// NOP
	}

	public void windowClosed(IWorkbenchWindow window) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
