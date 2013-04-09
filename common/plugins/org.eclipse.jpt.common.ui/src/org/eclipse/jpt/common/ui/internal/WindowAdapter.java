/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
