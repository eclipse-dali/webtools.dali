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
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

/**
 * Convenience implementation of {@link FocusListener}.
 */
public class FocusAdapter
	implements FocusListener
{
	public void focusGained(FocusEvent e) {
		// NOP
	}

	public void focusLost(FocusEvent e) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
