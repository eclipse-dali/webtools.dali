/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JpaModelManager;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaFacetUninstallDelegate 
	implements IDelegate 
{
	public void execute(IProject project, IProjectFacetVersion fv,
			Object config, IProgressMonitor monitor) throws CoreException {
		
		if (monitor != null) {
			monitor.beginTask("", 1); //$NON-NLS-1$
		}
		
		// NB:  WTP Natures (including the JavaEMFNature)
		//  should have been added with the module facet 
		//  required by this facet.
		
		// TODO
		//   - remove classpath items?
		//   - remove persistence.xml
		
		IJpaProject jpaProject = JpaModelManager.instance().getJpaModel().getJpaProject(project);
		if (jpaProject != null) {
			JpaModelManager.instance().disposeJpaProject(jpaProject);
		}
		
		
		if (monitor != null) {
			monitor.worked(1);
		}
	}

}
