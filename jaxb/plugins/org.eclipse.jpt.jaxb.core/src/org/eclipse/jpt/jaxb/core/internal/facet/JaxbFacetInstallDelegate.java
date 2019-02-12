/*******************************************************************************
 *  Copyright (c) 2010, 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.validation.ValidationFramework;


public class JaxbFacetInstallDelegate
		extends JaxbFacetDelegate {

	@Override
	protected void execute_(
			IProject project, IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException {
		
		ValidationFramework.getDefault().addValidationBuilder(project);
		super.execute_(project, fv, config, monitor);
	}
	
}
