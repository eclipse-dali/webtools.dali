package org.eclipse.jpt.jpadiagrameditor.ui.internal.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class JpaProjectFacetVersionPropertyTester extends PropertyTester{

	public static final String ID = "jpt.jpa"; //$NON-NLS-1$
	public static final IProjectFacet FACET = ProjectFacetsManager.getProjectFacet(ID);

	public boolean test(Object receiver, String property, Object[] args,
			Object value) {
		
		String[] subValues = ((String) value).split(":"); //$NON-NLS-1$
		String receiverId = subValues[0];
		String receiverVersion = subValues[1];

		if (receiver instanceof JpaNode) {
			JpaProject project = ((JpaNode)receiver).getJpaProject();
			try {
				IFacetedProject fproj = ProjectFacetsManager.create(project.getProject());
				if(receiverId.equals(ID) && receiverVersion.equals(fproj.getProjectFacetVersion(FACET).getVersionString())){
					return true;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		return false;
	}
	
}
