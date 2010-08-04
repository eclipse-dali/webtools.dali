package org.eclipse.jpt.core.internal.facet;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
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
			Map<String, Object> enablementVariables = new HashMap<String, Object>();
			enablementVariables.put(JpaLibraryProviderConstants.EXPR_VAR_JPA_PLATFORM, getJpaPlatformId(fpb.getProject()));
			LibraryInstallDelegate lp = new LibraryInstallDelegate(fpb, pfv, enablementVariables);
			try {
				lp.execute(new NullProgressMonitor());
			}
			catch (CoreException ce) {
				JptCorePlugin.log(ce);
			}
		}
	}
	
	protected String getJpaPlatformId(IProject project) {
		JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
		return (jpaProject == null) ? null : jpaProject.getJpaPlatform().getId();
	}
}
