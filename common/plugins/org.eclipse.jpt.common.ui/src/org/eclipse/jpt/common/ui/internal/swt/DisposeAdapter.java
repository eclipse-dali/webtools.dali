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

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

/**
 * Convenience SWT dispose listener.
 */
public class DisposeAdapter
	implements DisposeListener
{
	public void widgetDisposed(DisposeEvent e) {
		// NOP
	}
	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
}
