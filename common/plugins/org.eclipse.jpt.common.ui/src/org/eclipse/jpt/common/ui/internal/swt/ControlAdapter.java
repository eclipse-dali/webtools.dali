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
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;

/**
 * Convenience implementation of {@link ControlListener}.
 */
public class ControlAdapter
	implements ControlListener
{
	public void controlMoved(ControlEvent e) {
		// NOP
	}

	public void controlResized(ControlEvent e) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
