/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.resource.orm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
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
 * 
 * @version 2.1
 * @since 2.1
 */
public class EclipseLink1_1OrmXmlResourceFactory extends TranslatorResourceFactory
{
	public EclipseLink1_1OrmXmlResourceFactory() {
		this(RendererFactory.getDefaultRendererFactory());
	}
	
	public EclipseLink1_1OrmXmlResourceFactory(RendererFactory aRendererFactory) {
		super(aRendererFactory);
	}
	
	public EclipseLink1_1OrmXmlResourceFactory(RendererFactory aRendererFactory, boolean listeningForUpdates) {
		super(aRendererFactory, listeningForUpdates);
	}
	
	
	/**
	 * @see TranslatorResourceFactory#createResource(URI, Renderer)
	 */
	@Override
	protected TranslatorResource createResource(URI uri, Renderer renderer) {
		return new JpaXmlResource(uri, renderer, JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE, XmlEntityMappings.getRootTranslator());
	}
}
