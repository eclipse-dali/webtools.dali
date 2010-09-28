/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.proj.model;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jst.common.project.facet.core.JavaFacet;
import org.eclipse.wst.common.componentcore.datamodel.FacetProjectCreationDataModelProvider;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetProjectCreationDataModelProperties;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;


public class JaxbProjectCreationDataModelProvider
		extends FacetProjectCreationDataModelProvider
		implements IFacetProjectCreationDataModelProperties {
	
	public JaxbProjectCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public void init() {
		super.init();
		
		Collection<IProjectFacet> requiredFacets = new ArrayList<IProjectFacet>();
		requiredFacets.add(JavaFacet.FACET);
		requiredFacets.add(JaxbFacet.FACET);
		setProperty(REQUIRED_FACETS_COLLECTION, requiredFacets);
	}
}
