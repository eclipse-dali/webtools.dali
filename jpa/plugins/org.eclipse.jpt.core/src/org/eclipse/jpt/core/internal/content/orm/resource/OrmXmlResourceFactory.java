/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.wst.common.componentcore.internal.impl.WTPResourceFactoryRegistry;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.RendererFactory;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory;
import org.eclipse.wst.xml.core.internal.emf2xml.EMF2DOMSSERendererFactory;

public class OrmXmlResourceFactory extends TranslatorResourceFactory
{	
	public static final String ORM_XML_FILE_NAME = "orm.xml"; //$NON-NLS-1$
	public static final URI ORM_XML_FILE_URI = URI.createURI(ORM_XML_FILE_NAME); //$NON-NLS-1$

	/**
	 * Register myself with the Resource.Factory.Registry
	 */
	public static void registerWith(RendererFactory rendererFactory) {
		WTPResourceFactoryRegistry.INSTANCE.registerLastFileSegment(ORM_XML_FILE_NAME, new OrmXmlResourceFactory(rendererFactory));
	}
	
	/**
	 * Register myself using the default renderer factory.
	 * @see #registerWith(RendererFactory)
	 */
	public static void register() {
		registerWith(EMF2DOMSSERendererFactory.INSTANCE);
	}
	
	public static Resource.Factory getRegisteredFactory() {
		return WTPResourceFactoryRegistry.INSTANCE.getFactory(ORM_XML_FILE_URI);
	}

	
	public OrmXmlResourceFactory(RendererFactory aRendererFactory, boolean listeningForUpdates) {
		super(aRendererFactory, listeningForUpdates);
	}

	public OrmXmlResourceFactory(RendererFactory aRendererFactory) {
		super(aRendererFactory);
	}
	
	/**
	 * @see TranslatorResourceFactory#createResource(URI, Renderer)
	 */
	protected TranslatorResource createResource(URI uri, Renderer renderer) {
		return new OrmXmlResourceImpl(uri, renderer);
	}
}
