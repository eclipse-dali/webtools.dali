/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.facet.model;

import org.eclipse.wst.common.frameworks.datamodel.IDataModelProperties;

public interface JaxbFacetInstallDataModelProperties 
		extends IDataModelProperties {
	
	public static final String JAXB_FACET_INSTALL_CONFIG 
			= "JaxbFacetInstallDataModelProperties.JAVA_FACET_INSTALL_CONFIG"; //$NON-NLS-1$
	
	public static final String PLATFORM
			= "JaxbFacetInstallDataModelProperties.PLATFORM"; //$NON-NLS-1$
	
	public static final String LIBRARY_INSTALL_DELEGATE
			= "JaxbFacetInstallDataModelProperties.LIBRARY_INSTALL_DELEGATE"; //$NON-NLS-1$
}
