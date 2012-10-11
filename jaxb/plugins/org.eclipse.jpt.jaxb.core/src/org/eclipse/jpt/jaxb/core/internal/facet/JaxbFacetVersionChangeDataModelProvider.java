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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;

public class JaxbFacetVersionChangeDataModelProvider
		extends JaxbFacetDataModelProvider 
		implements JaxbFacetVersionChangeDataModelProperties {
	
	public JaxbFacetVersionChangeDataModelProvider() {
		super();
	}
	
	
	@Override
	protected Iterable<JaxbPlatformConfig> buildValidPlatformConfigs() {
		// add existing platform to list of choices
		Iterable<JaxbPlatformConfig> validPlatformDescs = super.buildValidPlatformConfigs();
		if (! IterableTools.contains(validPlatformDescs, getPlatformConfig())) {
			validPlatformDescs = new CompositeIterable(getPlatformConfig(), validPlatformDescs);
		}
		return validPlatformDescs;
	}
	
	@Override
	protected IStatus validatePlatform() {
		IStatus status = super.validatePlatform();
		
		if (status.isOK()) {
			if (! getPlatformConfig().supportsJaxbFacetVersion(getProjectFacetVersion())) {
				status = buildErrorStatus(JptJaxbCoreMessages.JaxbFacetConfig_validatePlatformDoesNotSupportFacetVersion);
			}
		}
		
		return status;
	}
}
