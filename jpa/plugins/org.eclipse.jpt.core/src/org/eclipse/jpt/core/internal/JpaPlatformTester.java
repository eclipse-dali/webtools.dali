/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/        
package org.eclipse.jpt.core.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jst.common.project.facet.core.libprov.EnablementExpressionContext;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectBase;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.IFacetedProject.Action;
import org.eclipse.wst.common.project.facet.core.internal.FacetedProjectWorkingCopy;

public class JpaPlatformTester extends PropertyTester {
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (! property.equals("jpaPlatform")) { //$NON-NLS-1$
			return false;
		}
		
		String platformId = null;
		
		if (receiver instanceof IResource) {
			platformId = platformId(((IResource) receiver).getProject());
		} 
		else if (receiver instanceof IJavaElement) {
			platformId = platformId(((IJavaElement) receiver).getResource().getProject());
		} 
		else if (receiver instanceof EnablementExpressionContext) {
			EnablementExpressionContext context = (EnablementExpressionContext) receiver;
			IFacetedProjectBase fp = context.getFacetedProject();
			if (fp instanceof FacetedProjectWorkingCopy){
				FacetedProjectWorkingCopy fpwc = (FacetedProjectWorkingCopy) fp;
				IProjectFacet jpaFacet = ProjectFacetsManager.getProjectFacet(JptCorePlugin.FACET_ID);
				Action action =  fpwc.getProjectFacetAction(jpaFacet);
				if (action != null ) {
					// in project creation wizard
					IDataModel model = (IDataModel) action.getConfig();
	            	platformId = (String) model.getProperty(JpaFacetDataModelProperties.PLATFORM_ID);
				}
			} 
			else {
				// in facet property page
				platformId = platformId(fp.getProject());
			}
		}
		
		return platformId == null ? false : platformId.equals(expectedValue);
	}
	
	private String platformId(IProject project) {
		return (project == null) ? null : JptCorePlugin.getJpaPlatformId(project);
	}
}
