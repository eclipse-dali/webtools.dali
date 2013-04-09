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
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbenchPage;

/**
 * Convenience implementation of {@link IPageListener}.
 */
public class PageAdapter
	implements IPageListener
{
	public void pageOpened(IWorkbenchPage page) {
		// NOP
	}

	public void pageActivated(IWorkbenchPage page) {
		// NOP
	}

	public void pageClosed(IWorkbenchPage page) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
