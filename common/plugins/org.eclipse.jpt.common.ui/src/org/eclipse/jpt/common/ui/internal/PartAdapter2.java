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
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

/**
 * Convenience implementation of {@link IPartListener2}.
 */
public class PartAdapter2
	implements IPartListener2
{
	public void partOpened(IWorkbenchPartReference partRef) {
		// NOP
	}

	public void partActivated(IWorkbenchPartReference partRef) {
		// NOP
	}

	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		// NOP
	}

	public void partVisible(IWorkbenchPartReference partRef) {
		// NOP
	}

	public void partInputChanged(IWorkbenchPartReference partRef) {
		// NOP
	}

	public void partHidden(IWorkbenchPartReference partRef) {
		// NOP
	}

	public void partDeactivated(IWorkbenchPartReference partRef) {
		// NOP
	}

	public void partClosed(IWorkbenchPartReference partRef) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
