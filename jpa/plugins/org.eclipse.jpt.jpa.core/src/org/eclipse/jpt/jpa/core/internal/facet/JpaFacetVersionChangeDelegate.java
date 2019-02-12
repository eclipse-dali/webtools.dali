/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaFacetVersionChangeDelegate
	extends JpaFacetActionDelegate
{
	@Override
	protected void execute_(
			IProject project, IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException
	{
		SubMonitor sm = SubMonitor.convert(monitor, 2);
		super.execute_(project, fv, config, sm.newChild(1));
		this.rebuildJpaProject(project);
		sm.worked(1);
		// nothing further to do here *just* yet
	}

	protected void rebuildJpaProject(IProject project) throws CoreException {
		try {
			this.rebuildJpaProject_(project);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			throw new CoreException(JptJpaCorePlugin.instance().buildStatus(IStatus.CANCEL, ex));
		}
	}

	protected void rebuildJpaProject_(IProject project) throws InterruptedException, CoreException {
		JpaProject.Reference ref = this.getJpaProjectReference(project);
		if (ref == null) {
			throw new CoreException(JptJpaCorePlugin.instance().buildStatus(IStatus.CANCEL, "Missing JPA project: " + project.getName())); //$NON-NLS-1$
		}
		ref.rebuild();
	}

	protected JpaProject.Reference getJpaProjectReference(IProject project) {
		return (JpaProject.Reference) project.getAdapter(JpaProject.Reference.class);
	}
}
