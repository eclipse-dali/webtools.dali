package org.eclipse.jpt.jpadiagrameditor.ui.internal.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.jpa.ui.JpaRootContextNodeModel;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class JpaContentNodeFacetProjectVersionPropertyTester extends
		PropertyTester {


	public static final String ID = "jpt.jpa"; //$NON-NLS-1$
	public static final IProjectFacet FACET = ProjectFacetsManager.getProjectFacet(ID);

	public boolean test(Object receiver, String property, Object[] args,
			Object value) {
		
		String[] subValues = ((String) value).split(":"); //$NON-NLS-1$
		String receiverId = subValues[0];

		if (receiver instanceof JpaRootContextNodeModel) {
			if(receiverId.equals(ID)){
				return true;
			}			
		}
		return false;
	}	
}
