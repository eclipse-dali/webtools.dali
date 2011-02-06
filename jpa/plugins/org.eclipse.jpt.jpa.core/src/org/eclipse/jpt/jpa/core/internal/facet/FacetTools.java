/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.facet;

import org.eclipse.wst.common.project.facet.core.IFacetedProjectBase;
import org.eclipse.wst.common.project.facet.core.IGroup;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class FacetTools {
	
	public static IGroup MODULES_GROUP = ProjectFacetsManager.getGroup("modules");  //$NON-NLS-1$
	
	
	public static boolean hasModuleFacet(IFacetedProjectBase fproj) {
		for (IProjectFacetVersion fv : fproj.getProjectFacets()) {
			if (MODULES_GROUP.getMembers().contains(fv)) {
				return true;
			}
		}
		return false;
	}
	
	public static IProjectFacetVersion getModuleFacet(IFacetedProjectBase fproj) {
		for (IProjectFacetVersion fv : fproj.getProjectFacets()) {
			if (MODULES_GROUP.getMembers().contains(fv)) {
				return fv;
			}
		}
		return null;
	}
}
