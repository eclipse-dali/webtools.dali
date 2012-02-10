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

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbenchPage;

/**
 * Convenience implementation of {@link IPageListener}.
 */
public class PageAdapter
	implements IPageListener
{
	public void pageOpened(IWorkbenchPage page) {
		// do nothing
	}
	public void pageActivated(IWorkbenchPage page) {
		// do nothing
	}
	public void pageClosed(IWorkbenchPage page) {
		// do nothing
	}
	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
}
