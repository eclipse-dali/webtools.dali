/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * This updater will update the JPA project immediately and not return until
 * the update and all resulting updates are complete.
 */
public class SynchronousJpaProjectUpdater implements JpaProject.Updater {
	protected final JpaProject jpaProject;
	protected boolean updating;
	protected boolean again;

	protected static final IProgressMonitor NULL_PROGRESS_MONITOR = new NullProgressMonitor();

	public SynchronousJpaProjectUpdater(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
		this.updating = false;
		this.again = false;
	}

	public void update() {
		if (this.updating) {
			// recursion: we get here when IJpaProject#update() is called during the "update"
			this.again = true;
		} else {
			this.updating = true;
			do {
				this.again = false;
				this.jpaProject.update(NULL_PROGRESS_MONITOR);
			} while (this.again);
			this.updating = false;
		}
	}

	public void dispose() {
		// nothing to do
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.jpaProject);
	}

}

