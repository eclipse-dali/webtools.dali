/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public class JaxbFacetInstallDelegate
		implements IDelegate {
	
	public final void execute(
			IProject project, IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException {
		
		this.execute_(project, fv, config, monitor);
	}
	
	protected void execute_(
			IProject project, IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException {
		
		SubMonitor subMonitor = SubMonitor.convert(monitor, 7);
		
		JaxbFacetInstallConfig jaxbConfig = (JaxbFacetInstallConfig) config;
		
		// project settings
		JaxbPlatformDescription platform = jaxbConfig.getPlatform();
		JptJaxbCorePlugin.setJaxbPlatform(project, platform);
		subMonitor.worked(1);
		
		// defaults settings
		JptJaxbCorePlugin.setDefaultJaxbPlatform(fv, platform);
		subMonitor.worked(1);
		
		//Delegate to LibraryInstallDelegate to configure the project classpath
		jaxbConfig.getLibraryInstallDelegate().execute(subMonitor.newChild(1));
	}
}
