/*******************************************************************************
 *  Copyright (c) 2006, 2007  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.internal.resource.persistence;

import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.RendererFactory;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory;

public class PersistenceResourceFactory extends TranslatorResourceFactory
{
	public PersistenceResourceFactory() {
		this(RendererFactory.getDefaultRendererFactory());
	}
	
	public PersistenceResourceFactory(RendererFactory aRendererFactory) {
		super(aRendererFactory);
	}
	
	public PersistenceResourceFactory(RendererFactory aRendererFactory, boolean listeningForUpdates) {
		super(aRendererFactory, listeningForUpdates);
	}
	
	
	/**
	 * @see TranslatorResourceFactory#createResource(URI, Renderer)
	 */
	protected TranslatorResource createResource(URI uri, Renderer renderer) {
		return new PersistenceResourceModel(uri, renderer);
	}
}
