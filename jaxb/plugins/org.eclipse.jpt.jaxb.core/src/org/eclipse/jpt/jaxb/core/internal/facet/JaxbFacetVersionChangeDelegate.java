package org.eclipse.jpt.jaxb.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public class JaxbFacetVersionChangeDelegate
		extends JaxbFacetDelegate {
	
	@Override
	protected void execute_(
			IProject project, IProjectFacetVersion fv, Object config,
			IProgressMonitor monitor) throws CoreException {
		
		SubMonitor sm = SubMonitor.convert(monitor, 2);
		super.execute_(project, fv, config, sm.newChild(1));
		
		JptJaxbCorePlugin.getProjectManager().rebuildJaxbProject(project);
		sm.worked(1);
		// nothing further to do here *just* yet
	}
}
