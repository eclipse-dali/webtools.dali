/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.persistence.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.wst.common.componentcore.internal.impl.WTPResourceFactoryRegistry;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.RendererFactory;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory;
import org.eclipse.wst.xml.core.internal.emf2xml.EMF2DOMSSERendererFactory;

public class PersistenceXmlResourceFactory extends TranslatorResourceFactory
{
	public static final String PERSISTENCE_XML_FILE_NAME = "persistence.xml"; //$NON-NLS-1$
	public static final URI PERSISTENCE_XML_FILE_URI = URI.createURI(PERSISTENCE_XML_FILE_NAME); //$NON-NLS-1$

	/**
	 * Register myself with the Resource.Factory.Registry
	 */
	public static void registerWith(RendererFactory rendererFactory) {
		WTPResourceFactoryRegistry.INSTANCE.registerLastFileSegment(PERSISTENCE_XML_FILE_NAME, new PersistenceXmlResourceFactory(rendererFactory));
	}
	
	/**
	 * Register myself using the default renderer factory.
	 * @see #registerWith(RendererFactory)
	 */
	public static void register() {
		registerWith(EMF2DOMSSERendererFactory.INSTANCE);
	}
	
	public static Resource.Factory getRegisteredFactory() {
		return WTPResourceFactoryRegistry.INSTANCE.getFactory(PERSISTENCE_XML_FILE_URI);
	}
	
	public PersistenceXmlResourceFactory(RendererFactory aRendererFactory, boolean listeningForUpdates) {
		super(aRendererFactory, listeningForUpdates);
	}

	public PersistenceXmlResourceFactory(RendererFactory aRendererFactory) {
		super(aRendererFactory);
	}
	
	/**
	 * @see TranslatorResourceFactory#createResource(URI, Renderer)
	 */
	protected TranslatorResource createResource(URI uri, Renderer renderer) {
		return new PersistenceResource(uri, renderer);
	}
}
