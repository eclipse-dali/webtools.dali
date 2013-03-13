/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.command;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

/**
 * Simple interface for implementing the GOF Command design pattern
 * in an Eclipse job
 * and allows for the command to throw an {@link InterruptedException}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jpt.common.core.utility.command.JobCommand
 */
public interface InterruptibleJobCommand {

	/**
	 * Execute the command. The semantics of the command
	 * is determined by the contract between the client and server.
	 * The command should check, as appropriate, whether the specified progress
	 * monitor is {@link IProgressMonitor#isCanceled() "canceled"}; if it is,
	 * the command should return a result
	 * status of severity {@link IStatus#CANCEL}. The singleton
	 * cancel status {@link org.eclipse.core.runtime.Status#CANCEL_STATUS} can be used for
	 * this purpose.
	 * <p>
	 * Nested methods can also check the progress monitor
	 * and, if it is "canceled", throw an
	 * {@link org.eclipse.core.runtime.OperationCanceledException OperationCanceledException}.
	 * 
	 * @see IProgressMonitor#isCanceled()
	 * @see org.eclipse.core.runtime.jobs.Job#run(IProgressMonitor monitor)
	 */
	IStatus execute(IProgressMonitor monitor) throws InterruptedException;
}
