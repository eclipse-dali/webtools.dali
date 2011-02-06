/******************************************************************************
 * Copyright (c) 2008 BEA Systems, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Konstantin Komissarchik - initial implementation and ongoing maintenance
 *    Oracle - copied and modified from org.eclipse.wst.common.project.facet.core.internal.FacetedProjectPropertyTester
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;

public class JpaTester extends PropertyTester {
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (!property.equals("projectFacet")) { //$NON-NLS-1$
			return false;
		}
        if (receiver instanceof IJavaElement) {
        	IProject project = ((IJavaElement) receiver).getResource().getProject();        	
            if (project != null) {

                String val = (String) expectedValue;
                int colon = val.indexOf( ':' );                
                String facetID;
                String facetVer;
                if( colon == -1 || colon == val.length() - 1 )  {
                    facetID = val;
                    facetVer = null;
                } else {
                    facetID = val.substring( 0, colon );
                    facetVer = val.substring( colon + 1 );
                }            	
            	try {            		
            		return FacetedProjectFramework.hasProjectFacet( project, facetID, facetVer );            		
				} catch (CoreException e) {
					JptJpaCorePlugin.log(e.getStatus());
					return false;
				}
            }
        }     
        return false;
	}		
}
