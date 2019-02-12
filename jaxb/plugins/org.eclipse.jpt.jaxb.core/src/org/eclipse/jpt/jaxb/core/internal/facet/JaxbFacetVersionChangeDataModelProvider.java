/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.facet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.JptJaxbCoreMessages;
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
			validPlatformDescs = IterableTools.insert(getPlatformConfig(), validPlatformDescs);
		}
		return validPlatformDescs;
	}
	
	@Override
	protected IStatus validatePlatform() {
		IStatus status = super.validatePlatform();
		
		if (status.isOK()) {
			if (! getPlatformConfig().supportsJaxbFacetVersion(getProjectFacetVersion())) {
				status = buildErrorStatus(JptJaxbCoreMessages.JAXB_FACET_CONFIG_VALIDATE_PLATFORM_DOES_NOT_SUPPORT_FACET_VERSION);
			}
		}
		
		return status;
	}
}
