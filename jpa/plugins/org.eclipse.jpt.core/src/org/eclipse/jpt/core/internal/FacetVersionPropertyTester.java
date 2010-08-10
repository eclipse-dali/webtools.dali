/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.wst.common.project.facet.core.IGroup;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class FacetVersionPropertyTester
		extends PropertyTester {
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (! property.equals("group")) { //$NON-NLS-1$
			return false;
		}
		
		if (! (receiver instanceof IProjectFacetVersion) || ! (expectedValue instanceof String)) {
			return false;
		}
		
		IGroup group = ProjectFacetsManager.getGroup((String) expectedValue);
		if (group == null) {
			return false;
		}
		
		return group.getMembers().contains((IProjectFacetVersion) receiver);
	}
}
