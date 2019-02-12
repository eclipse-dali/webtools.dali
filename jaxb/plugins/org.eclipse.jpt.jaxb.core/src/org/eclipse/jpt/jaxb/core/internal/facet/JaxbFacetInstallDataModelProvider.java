/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
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
			if (! getPlatformConfig().supportsJaxbFacetVersion(getProjectFacetVersion())) {
				getDataModel().setProperty(PLATFORM, null);
			}
		}
		
		return ok;
	}
}
