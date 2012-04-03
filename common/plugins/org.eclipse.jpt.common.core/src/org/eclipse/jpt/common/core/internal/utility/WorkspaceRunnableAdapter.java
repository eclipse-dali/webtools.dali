/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Convenience implementation of {@link IWorkspaceRunnable}.
 */
public class WorkspaceRunnableAdapter
	implements IWorkspaceRunnable
{
	public void run(IProgressMonitor monitor) throws CoreException {
		// NOP
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
}
