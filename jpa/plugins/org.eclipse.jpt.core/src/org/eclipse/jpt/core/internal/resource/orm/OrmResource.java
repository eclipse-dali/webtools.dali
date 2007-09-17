package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.internal.resource.orm.translators.EntityMappingsTranslator;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl;

public class OrmResource extends TranslatorResourceImpl
{
	public OrmResource(Renderer aRenderer) {
		super(aRenderer);
	}

	public OrmResource(URI uri, Renderer aRenderer) {
		super(uri, aRenderer);
	}
	
	/**
	 * @see TranslatorResourceImpl#getDefaultPublicId() 
	 */
	protected String getDefaultPublicId() {
		return null;
		// only applicable for DTD-based files
	}
	
	/**
	 * @see TranslatorResourceImpl#getDefaultSystemId() 
	 */
	protected String getDefaultSystemId() {
		return null;
		// only applicable for DTD-based files
	}
	
	/**
	 * @see TranslatorResourceImpl#getDefaultVersionId() 
	 */
	protected int getDefaultVersionID() {
		return 10;
		// this seems to be the default version of the spec for this doc
		// and the id 10 maps to the version 1.0
	}
	
	/**
	 * @see TranslatorResource#getDoctype() 
	 */
	public String getDoctype() {
		return null;
		// only applicable for DTD-based files
	}
	
	/**
	 * @see TranslatorResource#getRootTranslator() 
	 */
	public Translator getRootTranslator() {
		return EntityMappingsTranslator.INSTANCE;
	}
	
	public EntityMappings getEntityMappings() {
		return (EntityMappings) getRootObject();
	}
}
