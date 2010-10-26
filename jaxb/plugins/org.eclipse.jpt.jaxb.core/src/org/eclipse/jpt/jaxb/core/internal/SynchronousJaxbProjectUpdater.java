/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.utility.Command;
import org.eclipse.jpt.utility.internal.synchronizers.CallbackSynchronizer;
import org.eclipse.jpt.utility.internal.synchronizers.CallbackSynchronousSynchronizer;

/**
 * Adapt the "callback synchronizer" interface to the JAXB project "updater"
 * interface.
 * <p>
 * This updater will "update" the JAXB project immediately and not return until
 * the "update" and all resulting "updates" are complete. This implementation
 * should be used sparingly and for as short a time as possible, as it increases
 * the probability of deadlocks. A deadlock can occur when a JAXB project is
 * updated from multiple threads and various resources are locked in varying
 * orders.
 */
public class SynchronousJaxbProjectUpdater
	extends AbstractSynchronizerJaxbProjectUpdater
{
	public SynchronousJaxbProjectUpdater(JaxbProject jaxbProject) {
		super(jaxbProject);
	}

	@Override
	protected CallbackSynchronizer buildSynchronizer() {
		return new CallbackSynchronousSynchronizer(this.buildCommand());
	}

	protected Command buildCommand() {
		return new Command() {
			public void execute() {
				SynchronousJaxbProjectUpdater.this.jaxbProject.update(new NullProgressMonitor());
			}
		};
	}

}
