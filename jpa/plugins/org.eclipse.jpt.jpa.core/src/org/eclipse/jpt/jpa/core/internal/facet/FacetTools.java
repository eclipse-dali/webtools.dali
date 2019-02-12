/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
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
