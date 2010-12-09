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

import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetVersionChangeConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;

public class JaxbFacetVersionChangeDataModelProvider
		extends JaxbFacetDataModelProvider 
		implements JaxbFacetVersionChangeDataModelProperties {
	
	public JaxbFacetVersionChangeDataModelProvider() {
		this(new JaxbFacetVersionChangeConfig());
	}
	
	public JaxbFacetVersionChangeDataModelProvider(JaxbFacetVersionChangeConfig config) {
		super(config);
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
}
