/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.persistence;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.persistence.translators.PersistenceTranslator;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.RendererFactory;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class PersistenceXmlResourceFactory extends TranslatorResourceFactory
{
	public PersistenceXmlResourceFactory() {
		this(RendererFactory.getDefaultRendererFactory());
	}
	
	public PersistenceXmlResourceFactory(RendererFactory aRendererFactory) {
		super(aRendererFactory);
	}
	
	public PersistenceXmlResourceFactory(RendererFactory aRendererFactory, boolean listeningForUpdates) {
		super(aRendererFactory, listeningForUpdates);
	}
	
	
	/**
	 * @see TranslatorResourceFactory#createResource(URI, Renderer)
	 */
	@Override
	protected TranslatorResource createResource(URI uri, Renderer renderer) {
		return new JpaXmlResource(uri, renderer, JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE, PersistenceTranslator.INSTANCE);
	}
}
