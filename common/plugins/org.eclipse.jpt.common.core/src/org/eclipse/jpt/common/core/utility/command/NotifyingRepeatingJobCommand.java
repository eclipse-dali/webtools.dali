/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.command;

import java.util.EventListener;

/**
 * Extend the repeating job command to support listeners that are notified
 * when:
 * <ul>
 * <li>an execution "cycle" is complete; i.e. the job command has,
 *     for the moment, handled every execution request and quiesced
 *     (This notification is <em>not</em> guaranteed to occur with <em>every</em>
 *     execution "cycle"; since other, unrelated, executions can be triggered
 *     concurrently, causing the "cycle" to continue.)
 * <li>the job command has been canceled (typically by a user)
 * </ul>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface NotifyingRepeatingJobCommand
	extends RepeatingJobCommand
{
	/**
	 * Add the specified listener.
	 */
	void addListener(Listener listener);

	/**
	 * Remove the specified listener.
	 */
	void removeListener(Listener listener);


	// ********** listener **********

	/**
	 * Interface implemented by listeners to be notified whenever the
	 * job command has quiesced.
	 * @see NotifyingRepeatingJobCommand#addListener(Listener)
	 * @see NotifyingRepeatingJobCommand#removeListener(Listener)
	 */
	public interface Listener
		extends EventListener
	{
		/**
		 * The specified job command has quiesced.
		 */
		void executionQuiesced(JobCommand command);

		/**
		 * The specified job command has been canceled.
		 */
		void executionCanceled(JobCommand command);
	}
}
