/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.events;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * Convenience implementation of {@link SelectionListener}.
 */
public class SelectionAdapter
	implements SelectionListener
{
	public void widgetSelected(SelectionEvent e) {
		// NOP
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
