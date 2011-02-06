/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectBase;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;

public class RuntimeChangedListener
		implements IFacetedProjectListener {
	
	public void handleEvent(IFacetedProjectEvent facetedProjectEvent) {
		
		IFacetedProjectBase fpb = facetedProjectEvent.getWorkingCopy();
		if (fpb == null) {
			fpb = facetedProjectEvent.getProject();
		}
		IProjectFacetVersion pfv = fpb.getProjectFacetVersion(JpaFacet.FACET);
		if (pfv != null) {
			LibraryInstallDelegate lp = new LibraryInstallDelegate(fpb, pfv);
			try {
				lp.execute(new NullProgressMonitor());
			}
			catch (CoreException ce) {
				JptJpaCorePlugin.log(ce);
			}
		}
	}
	
	protected String getJpaPlatformId(IProject project) {
		JpaProject jpaProject = JptJpaCorePlugin.getJpaProject(project);
		return (jpaProject == null) ? null : jpaProject.getJpaPlatform().getId();
	}
}
