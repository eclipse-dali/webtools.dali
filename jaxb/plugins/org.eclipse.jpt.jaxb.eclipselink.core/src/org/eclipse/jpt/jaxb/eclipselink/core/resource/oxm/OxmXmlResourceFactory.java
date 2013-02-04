/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMRenderer;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.RendererFactory;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class OxmXmlResourceFactory
		extends TranslatorResourceFactory {
	
	public OxmXmlResourceFactory() {
		this(RendererFactory.getDefaultRendererFactory());
	}
	
	public OxmXmlResourceFactory(RendererFactory aRendererFactory) {
		super(aRendererFactory);
	}
	
	public OxmXmlResourceFactory(RendererFactory aRendererFactory, boolean listeningForUpdates) {
		super(aRendererFactory, listeningForUpdates);
	}
	
	
	@Override
	protected TranslatorResource createResource(URI uri, Renderer renderer) {
		// set renderer to no validation => default-valued, unspecified attributes are not created
		if (renderer instanceof EMF2DOMRenderer) {
			((EMF2DOMRenderer) renderer).setValidating(false);
		}
		return new JptXmlResource(uri, renderer, EXmlBindings.CONTENT_TYPE, EXmlBindings.getRootTranslator());
	}
}
