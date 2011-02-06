/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.proj.model;

import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetProjectCreationDataModelProperties;

public interface JpaProjectCreationDataModelProperties
		extends IFacetProjectCreationDataModelProperties {
	
	public static final String MODULE_FACET_DATA_MODEL 
			= "JpaProjectCreationDataModelProperties.MODULE_FACET_DATA_MODEL"; //$NON-NLS-1$
	
	public static final String ADDED_UTILITY_FACET
			= "JpaProjectCreationDataModelProperties.ADDED_UTILITY_FACET"; //$NON-NLS-1$
	
	public static final String ADD_TO_EAR 
			= "JpaProjectCreationDataModelProperties.ADD_TO_EAR"; //$NON-NLS-1$
	
	public static final String EAR_PROJECT_NAME 
			= "JpaProjectCreationDataModelProperties.EAR_PROJECT_NAME"; //$NON-NLS-1$
}
