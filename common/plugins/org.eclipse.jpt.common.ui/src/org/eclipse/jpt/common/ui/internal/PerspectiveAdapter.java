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
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener4;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;

/**
 * Convenience implementation of {@link IPerspectiveListener4} etc.
 */
public class PerspectiveAdapter
	implements IPerspectiveListener4
{
	// IPerspectiveListener
	public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		// NOP
	}

	// IPerspectiveListener
	public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
		// NOP
	}

	// IPerspectiveListener2
	public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, IWorkbenchPartReference partRef, String changeId) {
		// NOP
	}

	// IPerspectiveListener3
	public void perspectiveOpened(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		// NOP
	}

	// IPerspectiveListener3
	public void perspectiveClosed(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		// NOP
	}

	// IPerspectiveListener3
	public void perspectiveDeactivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		// NOP
	}

	// IPerspectiveListener3
	public void perspectiveSavedAs(IWorkbenchPage page, IPerspectiveDescriptor oldPerspective, IPerspectiveDescriptor newPerspective) {
		// NOP
	}

	// IPerspectiveListener4
	public void perspectivePreDeactivate(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
