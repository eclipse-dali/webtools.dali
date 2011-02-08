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
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;

public class JaxbFacetVersionChangeDataModelProvider
		extends JaxbFacetDataModelProvider 
		implements JaxbFacetVersionChangeDataModelProperties {
	
	public JaxbFacetVersionChangeDataModelProvider() {
		super();
	}
	
	
	@Override
	protected Iterable<JaxbPlatformDescription> buildValidPlatformDescriptions() {
		// add existing platform to list of choices
		Iterable<JaxbPlatformDescription> validPlatformDescs = super.buildValidPlatformDescriptions();
		if (! CollectionTools.contains(validPlatformDescs, getPlatform())) {
			validPlatformDescs = new CompositeIterable(getPlatform(), validPlatformDescs);
		}
		return validPlatformDescs;
	}
	
	@Override
	protected IStatus validatePlatform() {
		IStatus status = super.validatePlatform();
		
		if (status.isOK()) {
			if (! getPlatform().supportsJaxbFacetVersion(getProjectFacetVersion())) {
				status = buildErrorStatus(JptJaxbCoreMessages.JaxbFacetConfig_validatePlatformDoesNotSupportFacetVersion);
			}
		}
		
		return status;
	}
}
