/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaFacetVersionChangeDelegate
	extends JpaFacetActionDelegate
{
	@Override
	protected void execute_(
			IProject project, @SuppressWarnings("unused") IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException {
		
		super.execute_(project, fv, config, monitor);
		
		// nothing to do here *just* yet
	}
}
