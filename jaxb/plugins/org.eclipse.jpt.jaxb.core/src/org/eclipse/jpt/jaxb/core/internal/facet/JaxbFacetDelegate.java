package org.eclipse.jpt.jaxb.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
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
		
		JaxbFacetConfig jaxbConfig = (JaxbFacetConfig) config;
		
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
