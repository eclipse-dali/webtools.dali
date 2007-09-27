/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.internal.resource.persistence;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.resource.common.JptXmlResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.translators.PersistenceTranslator;
import org.eclipse.wst.common.internal.emf.resource.Renderer;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResource;

public class PersistenceResourceModel extends JptXmlResourceModel
{
	public PersistenceResourceModel(Renderer aRenderer) {
		super(aRenderer);
	}

	public PersistenceResourceModel(URI uri, Renderer aRenderer) {
		super(uri, aRenderer);
	}
	
	/**
	 * @see TranslatorResource#getRootTranslator() 
	 */
	public Translator getRootTranslator() {
		return PersistenceTranslator.INSTANCE;
	}
	
	/**
	 * @see IResourceModel#getResourceType()
	 */
	public String getResourceType() {
		return IResourceModel.PERSISTENCE_RESOURCE_TYPE;
	}
	
	/**
	 * @see IResourceModel#getContentNode(int)
	 */
	public IJpaContentNode getContentNode(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see IResourceModel#handleJavaElementChangedEvent(ElementChangedEvent)
	 */
	public void handleJavaElementChangedEvent(ElementChangedEvent event) {
		// TODO Auto-generated method stub	
	}
	
	public Persistence getPersistence() {
		return (Persistence) getRootObject();
	}
}
