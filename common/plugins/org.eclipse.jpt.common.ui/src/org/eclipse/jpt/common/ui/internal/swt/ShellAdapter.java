/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;

/**
 * Convenience implementation of {@link ShellListener}.
 */
public class ShellAdapter
	implements ShellListener
{
	public void shellActivated(ShellEvent e) {
		// NOP
	}

	public void shellDeactivated(ShellEvent e) {
		// NOP
	}

	public void shellIconified(ShellEvent e) {
		// NOP
	}

	public void shellDeiconified(ShellEvent e) {
		// NOP
	}

	public void shellClosed(ShellEvent e) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
