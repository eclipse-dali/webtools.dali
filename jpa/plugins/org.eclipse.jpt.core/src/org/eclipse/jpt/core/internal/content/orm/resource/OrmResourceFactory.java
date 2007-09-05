package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.RendererFactory;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceFactory;

public class OrmResourceFactory extends TranslatorResourceFactory
{
	public OrmResourceFactory() {
		this(RendererFactory.getDefaultRendererFactory());
	}
	
	public OrmResourceFactory(RendererFactory aRendererFactory, boolean listeningForUpdates) {
		super(aRendererFactory, listeningForUpdates);
	}
	
	public OrmResourceFactory(RendererFactory aRendererFactory) {
		super(aRendererFactory);
	}
	
	
	/**
	 * @see TranslatorResourceFactory#createResource(URI, Renderer)
	 */
	protected TranslatorResource createResource(URI uri, Renderer renderer) {
		return new OrmResourceImpl(uri, renderer);
	}
}
