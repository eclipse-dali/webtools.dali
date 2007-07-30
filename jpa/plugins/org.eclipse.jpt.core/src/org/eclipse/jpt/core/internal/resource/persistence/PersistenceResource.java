package org.eclipse.jpt.core.internal.resource.persistence;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.internal.resource.persistence.translators.PersistenceTranslator;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl;

public class PersistenceResource extends TranslatorResourceImpl
{
	public PersistenceResource(Renderer aRenderer) {
		super(aRenderer);
	}

	public PersistenceResource(URI uri, Renderer aRenderer) {
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
		return PersistenceTranslator.INSTANCE;
	}
	
	/**
	 * @see PersistenceResource#getPersistence()
	 */
	public Persistence getPersistence() {
		return (Persistence) getRootObject();
	}
}
