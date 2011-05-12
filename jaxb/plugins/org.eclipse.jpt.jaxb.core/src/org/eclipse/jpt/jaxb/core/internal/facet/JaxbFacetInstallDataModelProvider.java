/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.facet;


public class JaxbFacetInstallDataModelProvider
		extends JaxbFacetDataModelProvider 
		implements JaxbFacetInstallDataModelProperties {
	
	public JaxbFacetInstallDataModelProvider() {
		super();
	}
	
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		
		if (propertyName.equals(FACET_VERSION)) {
			if (! getPlatform().supportsJaxbFacetVersion(getProjectFacetVersion())) {
				getDataModel().setProperty(PLATFORM, null);
			}
		}
		
		return ok;
	}
}
