package org.eclipse.jpt.core.internal.resource.orm;

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
	
	public OrmResourceFactory(RendererFactory aRendererFactory) {
		super(aRendererFactory);
	}
	
	public OrmResourceFactory(RendererFactory aRendererFactory, boolean listeningForUpdates) {
		super(aRendererFactory, listeningForUpdates);
	}
	
	
	/**
	 * @see TranslatorResourceFactory#createResource(URI, Renderer)
	 */
	protected TranslatorResource createResource(URI uri, Renderer renderer) {
		return new OrmResourceModel(uri, renderer);
	}
}
