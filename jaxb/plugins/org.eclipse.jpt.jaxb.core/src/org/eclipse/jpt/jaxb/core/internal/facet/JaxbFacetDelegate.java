/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.jaxb.core.JaxbPreferences;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public abstract class JaxbFacetDelegate
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
		
		IDataModel jaxbConfig = (IDataModel) config;
		
		// project settings
		JaxbPlatformConfig platformConfig =
				(JaxbPlatformConfig) jaxbConfig.getProperty(JaxbFacetDataModelProperties.PLATFORM);
		JaxbPreferences.setJaxbPlatformID(project, platformConfig.getId());
		subMonitor.worked(1);
		
		// defaults settings
		JaxbPlatformManager jaxbPlatformManager = this.getJaxbPlatformManager();
		if (jaxbPlatformManager != null) {
			jaxbPlatformManager.setDefaultJaxbPlatformConfig(fv, platformConfig);
		}
		subMonitor.worked(1);
		
		//Delegate to LibraryInstallDelegate to configure the project classpath
		LibraryInstallDelegate lid = 
				(LibraryInstallDelegate) jaxbConfig.getProperty(JaxbFacetDataModelProperties.LIBRARY_INSTALL_DELEGATE);
		lid.execute(subMonitor.newChild(1));
	}

	protected JaxbPlatformManager getJaxbPlatformManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : this.getJaxbWorkspace().getJaxbPlatformManager();
	}

	protected JaxbProjectManager getJaxbProjectManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : this.getJaxbWorkspace().getJaxbProjectManager();
	}

	protected JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}
}
