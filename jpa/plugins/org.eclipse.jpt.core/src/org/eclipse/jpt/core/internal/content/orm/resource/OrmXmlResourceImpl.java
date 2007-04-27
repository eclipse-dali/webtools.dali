/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.core.internal.content.orm.OrmXmlResource;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl;

public class OrmXmlResourceImpl extends TranslatorResourceImpl
	implements OrmXmlResource
{

	private Translator rootTranslator;
	
	public OrmXmlResourceImpl(Renderer aRenderer) {
		super(aRenderer);
	}

	public OrmXmlResourceImpl(URI uri, Renderer aRenderer) {
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
		if (this.rootTranslator == null) {
			this.rootTranslator = buildRootTranslator();
		}
		return this.rootTranslator;
	}
	
	protected Translator buildRootTranslator() {
		return new EntityMappingsTranslator();
	}
	
	/**
	 * @see OrmXmlResource#getXmlFileContent()
	 */
	public EntityMappingsInternal getXmlFileContent() {
		return (EntityMappingsInternal) getRootObject();
	}
}
