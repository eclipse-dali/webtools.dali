/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.utility.Synchronizer;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Adapt the "synchronizer" interface to the JPA project "updater" interface.
 * <p>
 * Subclasses must build the appropriate synchronizer.
 */
public abstract class AbstractSynchronizerJpaProjectUpdater
	implements JpaProject.Updater
{
	protected final Synchronizer synchronizer;


	protected AbstractSynchronizerJpaProjectUpdater(JpaProject jpaProject) {
		super();
		this.synchronizer = this.buildSynchronizer(jpaProject);
	}

	protected abstract Synchronizer buildSynchronizer(JpaProject jpaProject);


	// ********** JpaProject.Updater implementation **********

	public void start() {
		this.synchronizer.start();
	}

	// recursion: we come back here when IJpaProject#update() is called during the "update"
	public void update() {
		this.synchronizer.synchronize();
	}

	public void stop() {
		this.synchronizer.stop();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.synchronizer);
	}

}
