/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jst.common.project.facet.core.JavaFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class JpaSEProjectCreationDataModelProvider
		extends JpaProjectCreationDataModelProvider {
	
	public JpaSEProjectCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public void init() {
		super.init();
		
		Collection<IProjectFacet> requiredFacets = new ArrayList<IProjectFacet>();
		requiredFacets.add(ProjectFacetsManager.getProjectFacet(JavaFacet.ID));
		requiredFacets.add(ProjectFacetsManager.getProjectFacet(JpaFacet.ID));
		setProperty(REQUIRED_FACETS_COLLECTION, requiredFacets);
	}
	
	@Override
	public boolean isPropertyEnabled(String propertyName) {
		if (ADD_TO_EAR.equals(propertyName) || EAR_PROJECT_NAME.equals(propertyName)) {
			return false;
		}
		return super.isPropertyEnabled(propertyName);
	}
}
