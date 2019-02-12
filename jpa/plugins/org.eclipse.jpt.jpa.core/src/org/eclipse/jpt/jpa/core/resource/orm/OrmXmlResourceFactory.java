/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
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
public class OrmXmlResourceFactory
	extends TranslatorResourceFactory
{
	public OrmXmlResourceFactory() {
		this(RendererFactory.getDefaultRendererFactory());
	}
	
	public OrmXmlResourceFactory(RendererFactory aRendererFactory) {
		super(aRendererFactory);
	}
	
	public OrmXmlResourceFactory(RendererFactory aRendererFactory, boolean listeningForUpdates) {
		super(aRendererFactory, listeningForUpdates);
	}
	
	
	@Override
	protected TranslatorResource createResource(URI uri, Renderer renderer) {
		return new JptXmlResource(uri, renderer, XmlEntityMappings.CONTENT_TYPE, XmlEntityMappings.getRootTranslator());
	}
}
