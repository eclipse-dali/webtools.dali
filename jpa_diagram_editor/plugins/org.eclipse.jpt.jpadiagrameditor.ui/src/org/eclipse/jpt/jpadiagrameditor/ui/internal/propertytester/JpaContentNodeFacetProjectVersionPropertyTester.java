package org.eclipse.jpt.jpadiagrameditor.ui.internal.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaContextModelRootModel;

public class JpaContentNodeFacetProjectVersionPropertyTester extends
		PropertyTester {

	public boolean test(Object receiver, String property, Object[] args,
			Object value) {
		
		String[] subValues = ((String) value).split(":"); //$NON-NLS-1$
		String receiverId = subValues[0];

		if (receiver instanceof JpaContextModelRootModel) {
			if(receiverId.equals(JpaProject.FACET_ID)){
				return true;
			}			
		}
		return false;
	}	
}
