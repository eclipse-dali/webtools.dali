/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaFacetUninstallDelegate 
	implements IDelegate 
{
	public void execute(IProject project, IProjectFacetVersion fv,
			Object config, IProgressMonitor monitor) throws CoreException {
		
		// There is nothing to do here.  Everything is taken care of by the 
		// JpaModelManager *whenever* the facet is removed (even via meta-file
		// editing and CVS updating), but this delegate needs to be here because
		// it is required by the facet extension point action element, and that 
		// is required by the facet UI to allow uninstallation of this facet.
	}

}
