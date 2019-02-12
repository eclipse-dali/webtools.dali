/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.command;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.utility.command.InterruptibleJobCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Convenience job command that does nothing.
 */
public class InterruptibleJobCommandAdapter
	implements InterruptibleJobCommand
{
	public IStatus execute(IProgressMonitor monitor) throws InterruptedException {
		return Status.OK_STATUS;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
